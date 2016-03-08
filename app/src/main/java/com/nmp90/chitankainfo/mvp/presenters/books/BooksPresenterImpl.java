package com.nmp90.chitankainfo.mvp.presenters.books;

import com.nmp90.chitankainfo.mvp.views.MainView;
import com.nmp90.chitankainfo.utils.ChitankaParser;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-8.
 */
public class BooksPresenterImpl implements BooksPresenter {
    private ChitankaParser webParser;
    private MainView view;

    public BooksPresenterImpl(ChitankaParser webParser) {
        this.webParser = webParser;
    }

    @Override
    public void searchBooks(String q) {
        webParser.searchBooks(q).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe((books) -> {
           if(view != null) {
               view.loadBooks(books);
           }
        }, (error) -> {
            Timber.e(error, "Error receiving books!");
            error.printStackTrace();
            view.loadBooks(new ArrayList<>());
        });
    }

    @Override
    public void setView(MainView view) {
        this.view = view;
    }
}
