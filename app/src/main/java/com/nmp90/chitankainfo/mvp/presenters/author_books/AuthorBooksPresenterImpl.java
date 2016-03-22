package com.nmp90.chitankainfo.mvp.presenters.author_books;

import com.nmp90.chitankainfo.mvp.presenters.BasePresenter;
import com.nmp90.chitankainfo.mvp.views.BooksView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-13.
 */
public class AuthorBooksPresenterImpl extends BasePresenter implements AuthorBooksPresenter {
    private ChitankaParser webParser;
    private WeakReference<BooksView> view;

    public AuthorBooksPresenterImpl(ChitankaParser webParser) {
        this.webParser = webParser;
    }

    @Override
    public void searchAuthorBooks(String link) {
        if(!viewExists(view))
            return;

        view.get().showLoading();
        webParser.searchAuthorBooks(link).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe((books) -> {
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
    public void setView(BooksView view) {
        this.view = new WeakReference<>(view);
    }
}
