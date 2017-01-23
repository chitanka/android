package info.chitanka.android.mvp.presenters.newest;

import java.io.IOException;
import java.lang.ref.WeakReference;

import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.mvp.presenters.BasePresenter;
import info.chitanka.android.mvp.views.NewBooksAndTextWorksView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by joro on 23.01.17.
 */
public class NewBooksAndTextWorksPresenterImpl extends BasePresenter<NewBooksAndTextWorksView> implements NewBooksAndTextWorksPresenter {
    private final ChitankaApi chitankaApi;

    public NewBooksAndTextWorksPresenterImpl(ChitankaApi chitankaApi ) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void setView(NewBooksAndTextWorksView view) {
        this.view = new WeakReference<>(view);
    }


    @Override
    public void loadNewBooksAndTextworks() {
        chitankaApi.getNewBooksAndTextworks()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    try {
                        Timber.d(result.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, err -> {
                   Timber.e(err);
                });
    }
}
