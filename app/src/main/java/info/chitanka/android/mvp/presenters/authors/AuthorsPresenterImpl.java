package info.chitanka.android.mvp.presenters.authors;

import com.annimon.stream.Stream;

import java.lang.ref.WeakReference;

import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.mvp.models.Authors;
import info.chitanka.android.mvp.presenters.BasePresenter;
import info.chitanka.android.mvp.views.AuthorsView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-11.
 */
public class AuthorsPresenterImpl extends BasePresenter implements AuthorsPresenter {
    private final ChitankaApi chitankaApi;
    private WeakReference<AuthorsView> authorsView;

    public AuthorsPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void setView(AuthorsView authorsViewWeakReference) {
        this.authorsView = new WeakReference<>(authorsViewWeakReference);
    }


//    public void searchAuthors(String name) {
//        if(viewExists()) {
//            authorsView.get().showLoading();
//            chitankaApi.searchAuthors(name)
//                    .subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe((authors) -> {
//                        if (!viewExists(authorsView))
//                            return;
//                        authorsView.get().hideLoading();
//                        authorsView.get().presentAuthors(authors);
//                    }, (error) -> {
//                        Timber.e(error, "Error printing authors!");
//                        if (!viewExists(authorsView))
//                            return;
//                        authorsView.get().hideLoading();
//                        authorsView.get().presentAuthors(new ArrayList<>());
//                    });
//        }
//    }

    private boolean viewExists() {
        return authorsView != null && authorsView.get() != null;
    }

    @Override
    public void loadAuthors(int page, int pageSize) {
        if(viewExists()) {
            authorsView.get().showLoading();
            chitankaApi.getAuthors(page, pageSize)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((authors) -> {
                        if (!viewExists(authorsView))
                            return;
                        authorsView.get().hideLoading();
                        authorsView.get().presentAuthors(authors);
                    }, (error) -> {
                        Timber.e(error, "Error printing authors!");
                        if (!viewExists(authorsView))
                            return;
                        authorsView.get().hideLoading();
                        authorsView.get().presentAuthors(new Authors());
                    });
        }
    }
}
