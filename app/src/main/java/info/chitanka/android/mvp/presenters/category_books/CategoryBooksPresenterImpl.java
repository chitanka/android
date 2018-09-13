package info.chitanka.android.mvp.presenters.category_books;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.mvp.presenters.BasePresenter;
import info.chitanka.android.mvp.views.CategoryBooksView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-13.
 */
public class CategoryBooksPresenterImpl extends BasePresenter<CategoryBooksView> implements CategoryBooksPresenter {
    private ChitankaApi chitankaApi;
    private Subscription subscription;

    public CategoryBooksPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void getBooksForCategory(String categorySlug, int page) {
        getView().showLoading();

        subscription = chitankaApi.getBooksForCategory(categorySlug, page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if(!viewExists())
                        return;

                    getView().hideLoading();
                    getView().presentCategoryBooks(model.getBooks(), model.getPager().getTotalCount());
                }, err -> {
                    Timber.e(err, "Error loading category books!");
                    if (!viewExists())
                        return;
                    getView().hideLoading();
                    getView().presentCategoryBooks(new ArrayList<>(), 0);
                });
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
    public void setView(CategoryBooksView view) {
        this.view = new WeakReference<>(view);
    }
}
