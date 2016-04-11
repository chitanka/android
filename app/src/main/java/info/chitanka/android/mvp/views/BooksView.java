package info.chitanka.android.mvp.views;

import info.chitanka.android.mvp.models.Book;

import java.util.List;

/**
 * Created by nmp on 16-3-8.
 */
public interface BooksView extends BaseView {
    void presentAuthorBooks(List<Book> books);
}
