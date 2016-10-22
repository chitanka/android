package info.chitanka.android.mvp.presenters.book;

import java.lang.ref.WeakReference;

import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.mvp.presenters.BasePresenter;
import info.chitanka.android.mvp.views.BookView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-24.
 */
public class BookPresenterImpl extends BasePresenter implements BookPresenter {

    WeakReference<BookView> view;
    ChitankaApi chitankaApi;
    private Subscription subscription;

    public BookPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void loadBooksDetails(int id) {
        subscription = chitankaApi.getBookDetails(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(book -> {
                    if(viewExists(view))
                        view.get().presentBookDetails(book);
                }, err -> {
                    Timber.e(err, "Error with loading book!");
                    if (!viewExists(view))
                        return;

                    view.get().showError();
                });
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void setView(BookView view) {
        this.view = new WeakReference<BookView>(view);
    }
}
