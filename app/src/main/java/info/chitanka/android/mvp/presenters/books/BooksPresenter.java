package info.chitanka.android.mvp.presenters.books;

import info.chitanka.android.mvp.presenters.BaseViewPresenter;
import info.chitanka.android.mvp.views.BooksView;

/**
 * Created by nmp on 16-3-8.
 */
public interface BooksPresenter extends BaseViewPresenter<BooksView> {
    void searchBooks(String q);
}
