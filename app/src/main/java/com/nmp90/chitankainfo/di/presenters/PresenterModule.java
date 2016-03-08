package com.nmp90.chitankainfo.di.presenters;

import com.nmp90.chitankainfo.di.scopes.ActivityScope;
import com.nmp90.chitankainfo.mvp.presenters.books.BooksPresenter;
import com.nmp90.chitankainfo.mvp.presenters.books.BooksPresenterImpl;
import com.nmp90.chitankainfo.utils.ChitankaParser;

import dagger.Module;
import dagger.Provides;

/**
 * Created by joro on 16-3-8.
 */
@Module
public class PresenterModule {

    @Provides
    @ActivityScope
    public BooksPresenter providesBooksPresenter(ChitankaParser webParser) {
        return new BooksPresenterImpl(webParser);
    }
}
