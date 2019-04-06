package info.chitanka.app.mvp.presenters.books;

import info.chitanka.app.mvp.presenters.BaseViewPresenter;
import info.chitanka.app.mvp.views.BooksView;

/**
 * Created by nmp on 16-3-8.
 */
public interface BooksPresenter extends BaseViewPresenter<BooksView> {
    void searchBooks(String q);
}
