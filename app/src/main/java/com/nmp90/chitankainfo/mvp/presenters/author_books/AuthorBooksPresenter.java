package com.nmp90.chitankainfo.mvp.presenters.author_books;

import com.nmp90.chitankainfo.mvp.presenters.BaseViewPresenter;
import com.nmp90.chitankainfo.mvp.views.BooksView;

/**
 * Created by nmp on 16-3-13.
 */
public interface AuthorBooksPresenter extends BaseViewPresenter<BooksView> {
    void searchAuthorBooks(String url);
}
