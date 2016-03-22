package com.nmp90.chitankainfo.di.presenters;

import com.nmp90.chitankainfo.api.ChitankaApi;
import com.nmp90.chitankainfo.di.scopes.ActivityScope;
import com.nmp90.chitankainfo.mvp.presenters.author_books.AuthorBooksPresenterImpl;
import com.nmp90.chitankainfo.mvp.presenters.author_books.AuthorBooksPresenter;
import com.nmp90.chitankainfo.mvp.presenters.authors.AuthorsPresenter;
import com.nmp90.chitankainfo.mvp.presenters.authors.AuthorsPresenterImpl;
import com.nmp90.chitankainfo.mvp.presenters.books.BooksPresenter;
import com.nmp90.chitankainfo.mvp.presenters.books.BooksPresenterImpl;
import com.nmp90.chitankainfo.mvp.presenters.categories.CategoriesPresenter;
import com.nmp90.chitankainfo.mvp.presenters.categories.CategoriesPresenterImpl;
import com.nmp90.chitankainfo.mvp.presenters.category_books.CategoryBooksPresenter;
import com.nmp90.chitankainfo.mvp.presenters.category_books.CategoryBooksPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by joro on 16-3-8.
 */
@Module
public class PresenterModule {

    @Provides
    @ActivityScope
    public BooksPresenter providesBooksPresenter(ChitankaApi chitankaApi) {
        return new BooksPresenterImpl(chitankaApi);
    }

    @Provides
    @ActivityScope
    public AuthorsPresenter providesAuthorsPresenter(ChitankaApi chitankaApi) {
        return new AuthorsPresenterImpl(chitankaApi);
    }

    @Provides
    @ActivityScope
    public AuthorBooksPresenter providesAuthorBooksPresenter(ChitankaApi chitankaApi) {
        return new AuthorBooksPresenterImpl(chitankaApi);
    }

    @Provides
    @ActivityScope
    public CategoriesPresenter providesCategoriesPresenter(ChitankaApi chitankaApi) {
        return new CategoriesPresenterImpl(chitankaApi);
    }

    @Provides
    @ActivityScope
    public CategoryBooksPresenter providesCategoryBooksPresenter(ChitankaApi chitankaApi) {
        return new CategoryBooksPresenterImpl(chitankaApi);
    }
}
