package com.nmp90.chitankainfo.mvp.presenters.categories;

import com.nmp90.chitankainfo.mvp.presenters.BaseViewPresenter;
import com.nmp90.chitankainfo.mvp.views.CategoriesView;

/**
 * Created by nmp on 16-3-15.
 */
public interface CategoriesPresenter extends BaseViewPresenter<CategoriesView> {
    void loadCategories();
}
