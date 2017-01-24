package info.chitanka.android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.chitanka.android.R;
import info.chitanka.android.TrackingConstants;
import info.chitanka.android.components.AnalyticsService;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.mvp.models.Category;
import info.chitanka.android.mvp.presenters.categories.CategoriesPresenter;
import info.chitanka.android.mvp.views.CategoriesView;
import info.chitanka.android.ui.fragments.books.CategoryBooksFragment;

/**
 * Created by nmp on 16-3-15.
 */
public class CategoriesFragment extends BaseFragment implements CategoriesView {
    public static final String TAG = CategoriesFragment.class.getSimpleName();
    private static final String KEY_CATEGORY_INDEX = "category_index";

    @Inject
    CategoriesPresenter categoriesPresenter;

    @Inject
    AnalyticsService analyticsService;

    @Bind(R.id.spinner_category)
    SearchableSpinner searchableSpinner;

    @Bind(R.id.container)
    FrameLayout container;

    int selectedCategory = -1;


    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(PresenterComponent.class).inject(this);
        categoriesPresenter.onStart();
        categoriesPresenter.setView(this);
        categoriesPresenter.loadCategories();
        analyticsService.logEvent(TrackingConstants.VIEW_CATEGORIES);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            selectedCategory = savedInstanceState.getInt(KEY_CATEGORY_INDEX);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_CATEGORY_INDEX, selectedCategory);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        categoriesPresenter.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void presentCategories(List<Category> categories, int level) {
        List<String> cats = Stream.of(categories)
                .map(x -> x.getName() + " (" + x.getNrOfBooks() + ")")
                .collect(Collectors.toList());

        searchableSpinner.setTitle(getString(R.string.categories_pick));
        searchableSpinner.setPositiveButton(getString(R.string.OK));
        searchableSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, cats));
        searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCategory = position;
                Category category = categories.get(position);
                CategoryBooksFragment categoryBooksFragment = (CategoryBooksFragment) getChildFragmentManager().findFragmentByTag(CategoryBooksFragment.TAG);
                if (categoryBooksFragment == null) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.container, CategoryBooksFragment.newInstance(category.getSlug()), CategoryBooksFragment.TAG)
                            .commitAllowingStateLoss();
                } else {
                    categoryBooksFragment.setSlug(category.getSlug());
                }

                analyticsService.logEvent(TrackingConstants.CHANGE_CATEGORIES, new HashMap<String, String>() {{put("category", category.getName());}});
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchableSpinner.setSelection(selectedCategory == -1 ? 0 : selectedCategory);
    }

    @Override
    public void onPause() {
        super.onPause();
        removeSearchableDialog();
    }

    public void removeSearchableDialog() {
        android.app.Fragment searchableSpinnerDialog = getActivity().getFragmentManager().findFragmentByTag("TAG");
        if (searchableSpinnerDialog != null && searchableSpinnerDialog.isAdded()) {
            getActivity().getFragmentManager().beginTransaction().remove(searchableSpinnerDialog).commit();
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public String getTitle() {
        return TAG;
    }
}
