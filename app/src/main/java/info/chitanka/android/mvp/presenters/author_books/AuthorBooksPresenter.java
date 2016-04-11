package info.chitanka.android.mvp.presenters.author_books;

import info.chitanka.android.mvp.presenters.BaseViewPresenter;
import info.chitanka.android.mvp.views.BooksView;

/**
 * Created by nmp on 16-3-13.
 */
public interface AuthorBooksPresenter extends BaseViewPresenter<BooksView> {
    void searchAuthorBooks(String url);
}
