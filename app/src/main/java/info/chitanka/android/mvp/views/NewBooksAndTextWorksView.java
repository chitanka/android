package info.chitanka.android.mvp.views;

import java.util.List;

import info.chitanka.android.mvp.models.Book;
import info.chitanka.android.mvp.models.TextWork;

/**
 * Created by joro on 23.01.17.
 */

public interface NewBooksAndTextWorksView extends BaseView {
    void presentNewBooksAndTextWorks(List<Book> books, List<TextWork> textWorks);
}
