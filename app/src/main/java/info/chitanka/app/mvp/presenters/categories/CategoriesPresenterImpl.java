package info.chitanka.app.mvp.presenters.categories;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import info.chitanka.app.api.ChitankaApi;
import info.chitanka.app.mvp.models.Category;
import info.chitanka.app.mvp.presenters.BasePresenter;
import info.chitanka.app.mvp.views.CategoriesView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-15.
 */
public class CategoriesPresenterImpl extends BasePresenter<CategoriesView> implements CategoriesPresenter {

    private ChitankaApi chitankaApi;
    private Subscription subscription;
    private List<Category> flatCategories;

    public CategoriesPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void startPresenting() {

    }

    @Override
    public void stopPresenting() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void setView(CategoriesView view) {
        this.view = new WeakReference<>(view);
    }

    @Override
    public void loadCategories() {
        flatCategories = new ArrayList<>();
        if(viewExists()) {
            getView().showLoading();
        }

        subscription = chitankaApi
                .getCategories()
                .map(x -> {
                    populateCategoriesLevel(x.getCategories(), 0);
                    return flatCategories;
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> {
                    if(viewExists()) {
                        getView().presentCategories(categories, 0);
                    }
                }, (error) -> {
                    Timber.e(error, "Error loading categories!");
                    if (!viewExists())
                        return;
                    getView().hideLoading();
                    getView().presentCategories(new ArrayList<>(), 0);
                });
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
}
