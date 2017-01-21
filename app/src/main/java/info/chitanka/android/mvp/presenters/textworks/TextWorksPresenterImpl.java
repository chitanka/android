package info.chitanka.android.mvp.presenters.textworks;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.mvp.presenters.BasePresenter;
import info.chitanka.android.mvp.views.TextWorksView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by nmp on 21.01.17.
 */

public class TextWorksPresenterImpl extends BasePresenter<TextWorksView> implements TextWorksPresenter {

    private final ChitankaApi chitankaApi;

    public TextWorksPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void onStart() {
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onDestroy() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }

    @Override
    public void setView(TextWorksView view) {
        this.view = new WeakReference<>(view);
    }

    @Override
    public void searchTextWorks(String searchTerm) {
        getView().showLoading();
        compositeSubscription.add(
                chitankaApi
                        .searchTextWorks(searchTerm)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(textworks -> {
                            if(viewExists()) {
                                getView().hideLoading();
                                getView().presentTextWorks(textworks.getTexts());
                            }
                        }, (error) -> {
                            Timber.e(error, "Error loading textworks!");
                            if (!viewExists())
                                return;
                            getView().hideLoading();
                            getView().presentTextWorks(new ArrayList<>());
                        })
        );
    }

    @Override
    public void getAuthorTextWorks(String authorSlug) {
        getView().showLoading();
        compositeSubscription.add(
                chitankaApi
                        .getAuthorTextWorks(authorSlug)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(textworks -> {
                            if(viewExists()) {
                                getView().hideLoading();
                                getView().presentTextWorks(textworks.getTexts());
                            }
                        }, (error) -> {
                            Timber.e(error, "Error loading textworks!");
                            if (!viewExists())
                                return;
                            getView().hideLoading();
                            getView().presentTextWorks(new ArrayList<>());
                        })
        );
    }
}
