package com.nmp90.chitankainfo.mvp.presenters.books;

import com.nmp90.chitankainfo.mvp.presenters.BasePresenter;
import com.nmp90.chitankainfo.mvp.views.BooksView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-8.
 */
public class BooksPresenterImpl extends BasePresenter implements BooksPresenter {
    private ChitankaParser webParser;
    private WeakReference<BooksView> view;

    public BooksPresenterImpl(ChitankaParser webParser) {
        this.webParser = webParser;
    }

    @Override
    public void searchBooks(String q) {
        if (!viewExists(view))
            return;

        view.get().showLoading();
        webParser.searchBooks(q).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe((books) -> {
            if (!viewExists(view))
                return;
            view.get().hideLoading();
            view.get().loadBooks(books);
        }, (error) -> {
            Timber.e(error, "Error receiving books!");
            if (!viewExists(view))
                return;
            view.get().hideLoading();
            view.get().loadBooks(new ArrayList<>());
        });
    }

    @Override
    public void loadBooksForCategory(String categorySlug, int page) {

    }

    @Override
    public void setView(BooksView view) {
        this.view = new WeakReference<>(view);
    }
}
