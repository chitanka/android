package info.chitanka.android.di.presenters;

import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.di.scopes.ActivityScope;
import info.chitanka.android.mvp.presenters.author_books.AuthorBooksPresenterImpl;
import info.chitanka.android.mvp.presenters.author_books.AuthorBooksPresenter;
import info.chitanka.android.mvp.presenters.authors.AuthorsPresenter;
import info.chitanka.android.mvp.presenters.authors.AuthorsPresenterImpl;
import info.chitanka.android.mvp.presenters.book.BookPresenter;
import info.chitanka.android.mvp.presenters.book.BookPresenterImpl;
import info.chitanka.android.mvp.presenters.books.BooksPresenter;
import info.chitanka.android.mvp.presenters.books.BooksPresenterImpl;
import info.chitanka.android.mvp.presenters.categories.CategoriesPresenter;
import info.chitanka.android.mvp.presenters.categories.CategoriesPresenterImpl;
import info.chitanka.android.mvp.presenters.category_books.CategoryBooksPresenter;
import info.chitanka.android.mvp.presenters.category_books.CategoryBooksPresenterImpl;

import dagger.Module;
import dagger.Provides;
import info.chitanka.android.mvp.presenters.search.SearchPresenter;
import info.chitanka.android.mvp.presenters.search.SearchPresenterImpl;

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

    @Provides
    @ActivityScope
    public BookPresenter providesBookPresenter(ChitankaApi chitankaApi) {
        return new BookPresenterImpl(chitankaApi);
    }

    @Provides
    @ActivityScope
    public SearchPresenter providesSearchPresenter(ChitankaApi chitankaApi) {
        return new SearchPresenterImpl(chitankaApi);
    }
}
