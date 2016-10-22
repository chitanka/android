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
public class CategoryBooksPresenterImpl extends BasePresenter implements CategoryBooksPresenter {
    private ChitankaApi chitankaApi;
    private WeakReference<CategoryBooksView> view;
    private Subscription subscription;

    public CategoryBooksPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void getBooksForCategory(String categorySlug, int page) {
        if(viewExists(view))
            view.get().showLoading();

        subscription = chitankaApi.getBooksForCategory(categorySlug, page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if(!viewExists(view))
                        return;

                    view.get().hideLoading();
                    view.get().presentCategoryBooks(model.getBooks(), model.getPager().getTotalCount());
                }, err -> {
                    Timber.e(err, "Error loading category books!");
                    if (!viewExists(view))
                        return;
                    view.get().hideLoading();
                    view.get().presentCategoryBooks(new ArrayList<>(), 0);
                });
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
    public void setView(CategoryBooksView view) {
        this.view = new WeakReference<>(view);
    }
}
