package info.chitanka.app.mvp.presenters.category_books;

import info.chitanka.app.mvp.presenters.BaseViewPresenter;
import info.chitanka.app.mvp.views.CategoryBooksView;

/**
 * Created by nmp on 16-3-13.
 */
public interface CategoryBooksPresenter  extends BaseViewPresenter<CategoryBooksView> {
    void getBooksForCategory(String categorySlug, int page);
}
