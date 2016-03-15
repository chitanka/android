package com.nmp90.chitankainfo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.di.presenters.PresenterComponent;
import com.nmp90.chitankainfo.mvp.models.Category;
import com.nmp90.chitankainfo.mvp.presenters.categories.CategoriesPresenter;
import com.nmp90.chitankainfo.mvp.views.CategoriesView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-15.
 */
public class CategoriesFragment extends BaseFragment implements CategoriesView {

    @Inject
    CategoriesPresenter categoriesPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(PresenterComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, view);

        categoriesPresenter.setView(this);
        categoriesPresenter.loadCategories();

        return view;
    }

    @Override
    public void presentCategories(List<Category> categories) {
        for(Category category : categories) {
            Timber.d(category.getName());
        }
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }
}
