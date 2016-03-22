package com.nmp90.chitankainfo.mvp.presenters.category_books;

import com.nmp90.chitankainfo.api.ChitankaApi;
import com.nmp90.chitankainfo.mvp.presenters.BasePresenter;
import com.nmp90.chitankainfo.mvp.views.CategoryBooksView;

import java.lang.ref.WeakReference;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nmp on 16-3-13.
 */
public class CategoryBooksPresenterImpl extends BasePresenter implements CategoryBooksPresenter {
    private ChitankaApi chitankaApi;
    private WeakReference<CategoryBooksView> view;

    public CategoryBooksPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void getBooksForCategory(String categorySlug, int page) {
        chitankaApi.getBooksForCategory(categorySlug, page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if(!viewExists(view))
                        return;

                    view.get().hideLoading();
                    view.get().loadBooks(model.getBooks(), model.getPager().getTotalCount());
                });
    }

    @Override
    public void setView(CategoryBooksView view) {
        this.view = new WeakReference<>(view);
    }
}
