package com.nmp90.chitankainfo.mvp.presenters.category_books;

import com.nmp90.chitankainfo.mvp.presenters.BaseViewPresenter;
import com.nmp90.chitankainfo.mvp.views.CategoryBooksView;

/**
 * Created by nmp on 16-3-13.
 */
public interface CategoryBooksPresenter  extends BaseViewPresenter<CategoryBooksView> {
    void getBooksForCategory(String categorySlug, int page);
}
