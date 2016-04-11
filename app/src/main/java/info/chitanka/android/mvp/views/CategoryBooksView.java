package info.chitanka.android.mvp.views;

import info.chitanka.android.mvp.models.Book;

import java.util.List;

/**
 * Created by nmp on 16-3-22.
 */
public interface CategoryBooksView extends BaseView {
    void presentCategoryBooks(List<Book> books, int totalItemCount);
}
