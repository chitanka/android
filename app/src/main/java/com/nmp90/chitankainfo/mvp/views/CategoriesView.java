package com.nmp90.chitankainfo.mvp.views;

import com.nmp90.chitankainfo.mvp.models.Category;

import java.util.List;

/**
 * Created by nmp on 16-3-15.
 */
public interface CategoriesView extends BaseView {
    void presentCategories(List<Category> categories);
}
