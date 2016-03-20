package com.nmp90.chitankainfo.mvp.presenters.categories;

import com.nmp90.chitankainfo.api.ChitankaApi;
import com.nmp90.chitankainfo.mvp.presenters.BasePresenter;
import com.nmp90.chitankainfo.mvp.views.CategoriesView;

import java.lang.ref.WeakReference;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nmp on 16-3-15.
 */
public class CategoriesPresenterImpl extends BasePresenter implements CategoriesPresenter {

    private WeakReference<CategoriesView> categoriesView;
    private ChitankaApi chitankaApi;

    public CategoriesPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void setView(CategoriesView view) {
        this.categoriesView = new WeakReference<>(view);
    }

    @Override
    public void loadCategories() {
    chitankaApi
        .getCategories()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(categories -> {
            if(viewExists(categoriesView)) {
                categoriesView.get().presentCategories(categories.getCategories(), 0);
            }
        });
    }
}
