package info.chitanka.android.mvp.presenters.categories;

import info.chitanka.android.mvp.presenters.BaseViewPresenter;
import info.chitanka.android.mvp.views.CategoriesView;

/**
 * Created by nmp on 16-3-15.
 */
public interface CategoriesPresenter extends BaseViewPresenter<CategoriesView> {
    void loadCategories();
}
