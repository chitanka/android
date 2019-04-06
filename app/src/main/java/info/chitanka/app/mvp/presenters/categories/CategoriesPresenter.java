package info.chitanka.app.mvp.presenters.categories;

import info.chitanka.app.mvp.presenters.BaseViewPresenter;
import info.chitanka.app.mvp.views.CategoriesView;

/**
 * Created by nmp on 16-3-15.
 */
public interface CategoriesPresenter extends BaseViewPresenter<CategoriesView> {
    void loadCategories();
}
