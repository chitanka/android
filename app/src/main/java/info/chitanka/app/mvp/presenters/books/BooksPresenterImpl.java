package info.chitanka.app.mvp.presenters.books;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import info.chitanka.app.api.ChitankaApi;
import info.chitanka.app.mvp.presenters.BasePresenter;
import info.chitanka.app.mvp.views.BooksView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-8.
 */
public class BooksPresenterImpl extends BasePresenter<BooksView> implements BooksPresenter {
    private ChitankaApi chitankaApi;
    private Subscription subscription;

    public BooksPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void searchBooks(String q) {
        if (!viewExists())
            return;

        getView().showLoading();
        subscription = chitankaApi.searchBooks(q).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe((books) -> {
            if (!viewExists())
                return;
            getView().hideLoading();
            getView().presentAuthorBooks(books.getBooks());
        }, (error) -> {
            Timber.e(error, "Error receiving books!");
            if (!viewExists())
                return;
            getView().hideLoading();
            getView().presentAuthorBooks(new ArrayList<>());
        });
    }


    @Override
    public void startPresenting() {
    }

    @Override
    public void stopPresenting() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void setView(BooksView view) {
        this.view = new WeakReference<BooksView>(view);
    }
}
