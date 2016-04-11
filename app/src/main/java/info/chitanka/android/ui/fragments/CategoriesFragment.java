package info.chitanka.android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.chitanka.android.R;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.mvp.models.Category;
import info.chitanka.android.mvp.presenters.categories.CategoriesPresenter;
import info.chitanka.android.mvp.views.CategoriesView;
import info.chitanka.android.ui.adapters.CategoriesAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nmp on 16-3-15.
 */
public class CategoriesFragment extends BaseFragment implements CategoriesView {

    @Inject
    CategoriesPresenter categoriesPresenter;

    @Bind(R.id.rv_categories)
    RecyclerView rvCategories;

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

        rvCategories.setLayoutManager(new LinearLayoutManager(getActivity()));

        categoriesPresenter.setView(this);
        categoriesPresenter.loadCategories();

        return view;
    }

    List<Category> flatCategories = new ArrayList<>();

    @Override
    public void presentCategories(List<Category> categories, int level) {
        populateCategoriesLevel(categories, level);
        rvCategories.setAdapter(new CategoriesAdapter(getActivity(), flatCategories));
    }

    private void populateCategoriesLevel(List<Category> categories, int level) {
        level++;
        for(Category category : categories) {
            if(category.getNrOfBooks() == 0)
                continue;
            category.setLevel(level);
            flatCategories.add(category);
            if(category.getChildren() != null && category.getChildren().size() > 0) {
                populateCategoriesLevel(category.getChildren(), level);
            }
        }
    }


    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }
}
