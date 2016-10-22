package info.chitanka.android.mvp.presenters.author_books;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.mvp.presenters.BasePresenter;
import info.chitanka.android.mvp.views.BooksView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-13.
 */
public class AuthorBooksPresenterImpl extends BasePresenter implements AuthorBooksPresenter {
    private ChitankaApi chitankaApi;
    private WeakReference<BooksView> view;
    private Subscription subscription;

    public AuthorBooksPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void searchAuthorBooks(String slug) {
        if(!viewExists(view))
            return;

        view.get().showLoading();
        subscription = chitankaApi.getAuthorBooks(slug).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe((books) -> {
            if (!viewExists(view))
                return;
            view.get().hideLoading();
            view.get().presentAuthorBooks(books.getBooks());

        }, (error) -> {
            Timber.e(error, "Error receiving books!");
            if (!viewExists(view))
                return;
            view.get().hideLoading();
            view.get().presentAuthorBooks(new ArrayList<>());

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
    public void setView(BooksView view) {
        this.view = new WeakReference<>(view);
    }
}
