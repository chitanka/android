package info.chitanka.android.mvp.models;

import java.util.List;

/**
 * Created by nmp on 16-3-22.
 */
public class CategoryBooks {
    private List<Book> books;
    private Pagination pager;

    public List<Book> getBooks() {
        return books;
    }

    public Pagination getPager() {
        return pager;
    }
}
