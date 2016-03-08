package com.nmp90.chitankainfo.mvp.presenters.books;

import com.nmp90.chitankainfo.mvp.views.MainView;

/**
 * Created by nmp on 16-3-8.
 */
public interface BooksPresenter  {
    void searchBooks(String q);
    void setView(MainView view);
}
