package com.nmp90.chitankainfo.mvp.views;

import com.nmp90.chitankainfo.mvp.models.Book;

import java.util.List;

/**
 * Created by nmp on 16-3-8.
 */
public interface BooksView extends BaseView {
    void loadBooks(List<Book> books);
}
