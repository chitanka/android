package info.chitanka.android.mvp.presenters.authors;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.mvp.models.Authors;
import info.chitanka.android.mvp.models.Pagination;
import info.chitanka.android.mvp.presenters.BasePresenter;
import info.chitanka.android.mvp.views.AuthorsView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-11.
 */
public class AuthorsPresenterImpl extends BasePresenter implements AuthorsPresenter {
    private final ChitankaApi chitankaApi;
    private WeakReference<AuthorsView> authorsView;
    private CompositeSubscription compositeSubscription;

    public AuthorsPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void onStart() {
        this.compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onDestroy() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }

    @Override
    public void setView(AuthorsView authorsViewWeakReference) {
        this.authorsView = new WeakReference<>(authorsViewWeakReference);
    }

    @Override
    public void searchAuthors(String name) {
        if (viewExists()) {
            authorsView.get().showLoading();
            compositeSubscription.add(
                    chitankaApi.searchAuthors(name)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((authors) -> {
                                if (!viewExists(authorsView))
                                    return;
                                authorsView.get().hideLoading();
                                authorsView.get().presentSearch(new Authors(new Pagination(), authors));
                            }, (error) -> {
                                Timber.e(error, "Error printing authors!");
                                if (!viewExists(authorsView))
                                    return;
                                authorsView.get().hideLoading();
                                authorsView.get().presentSearch(new Authors(new Pagination(), new ArrayList<>()));
                            })
            );
        }
    }

    private boolean viewExists() {
        return authorsView != null && authorsView.get() != null;
    }

    @Override
    public void loadAuthors(int page, int pageSize) {
        if (viewExists()) {
            authorsView.get().showLoading();
            compositeSubscription.add(chitankaApi.getAuthors(page, pageSize)
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
                    })
            );
        }
    }
}
