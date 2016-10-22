package info.chitanka.android.mvp.presenters.categories;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.mvp.presenters.BasePresenter;
import info.chitanka.android.mvp.views.CategoriesView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-15.
 */
public class CategoriesPresenterImpl extends BasePresenter implements CategoriesPresenter {

    private WeakReference<CategoriesView> categoriesView;
    private ChitankaApi chitankaApi;
    private Subscription subscription;

    public CategoriesPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void setView(CategoriesView view) {
        this.categoriesView = new WeakReference<>(view);
    }

    @Override
    public void loadCategories() {
        if(viewExists(categoriesView)) {
            categoriesView.get().showLoading();
        }

        subscription = chitankaApi
                .getCategories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> {
                    if(viewExists(categoriesView)) {
                        categoriesView.get().presentCategories(categories.getCategories(), 0);
                    }
                }, (error) -> {
                    Timber.e(error, "Error loading categories!");
                    if (!viewExists(categoriesView))
                        return;
                    categoriesView.get().hideLoading();
                    categoriesView.get().presentCategories(new ArrayList<>(), 0);
                });
    }
}
