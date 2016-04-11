package info.chitanka.android.mvp.views;

import info.chitanka.android.mvp.models.Category;

import java.util.List;

/**
 * Created by nmp on 16-3-15.
 */
public interface CategoriesView extends BaseView {
    void presentCategories(List<Category> categories, int i);
}
