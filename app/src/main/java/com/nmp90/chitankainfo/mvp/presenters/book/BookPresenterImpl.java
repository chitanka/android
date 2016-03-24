package com.nmp90.chitankainfo.mvp.presenters.book;

import com.nmp90.chitankainfo.api.ChitankaApi;
import com.nmp90.chitankainfo.mvp.presenters.BasePresenter;
import com.nmp90.chitankainfo.mvp.views.BookView;

import java.lang.ref.WeakReference;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-24.
 */
public class BookPresenterImpl extends BasePresenter implements BookPresenter {

    WeakReference<BookView> view;
    ChitankaApi chitankaApi;

    public BookPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void loadBooksDetails(int id) {
        chitankaApi.getBookDetails(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(book -> {
                    if(viewExists(view))
                        view.get().presentBookDetails(book);
                }, err -> {
                    Timber.e(err, "Error with loading book!");
                    if (!viewExists(view))
                        return;

                    view.get().showError();
                });
    }

    @Override
    public void setView(BookView view) {
        this.view = new WeakReference<BookView>(view);
    }
}
