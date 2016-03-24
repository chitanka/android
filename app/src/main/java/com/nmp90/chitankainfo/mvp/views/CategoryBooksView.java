package com.nmp90.chitankainfo.mvp.views;

import com.nmp90.chitankainfo.mvp.models.Book;

import java.util.List;

/**
 * Created by nmp on 16-3-22.
 */
public interface CategoryBooksView extends BaseView {
    void presentCategoryBooks(List<Book> books, int totalItemCount);
}
