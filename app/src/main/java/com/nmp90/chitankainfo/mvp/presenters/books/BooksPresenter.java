package com.nmp90.chitankainfo.mvp.presenters.books;

import com.nmp90.chitankainfo.mvp.presenters.BaseViewPresenter;
import com.nmp90.chitankainfo.mvp.views.BooksView;

/**
 * Created by nmp on 16-3-8.
 */
public interface BooksPresenter extends BaseViewPresenter<BooksView> {
    void searchBooks(String q);
}
