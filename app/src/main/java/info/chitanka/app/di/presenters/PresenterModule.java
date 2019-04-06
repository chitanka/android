package info.chitanka.app.di.presenters;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import info.chitanka.app.api.ChitankaApi;
import info.chitanka.app.di.scopes.ActivityScope;
import info.chitanka.app.mvp.presenters.author_books.AuthorBooksPresenter;
import info.chitanka.app.mvp.presenters.author_books.AuthorBooksPresenterImpl;
import info.chitanka.app.mvp.presenters.authors.AuthorsPresenter;
import info.chitanka.app.mvp.presenters.authors.AuthorsPresenterImpl;
import info.chitanka.app.mvp.presenters.book.BookPresenter;
import info.chitanka.app.mvp.presenters.book.BookPresenterImpl;
import info.chitanka.app.mvp.presenters.books.BooksPresenter;
import info.chitanka.app.mvp.presenters.books.BooksPresenterImpl;
import info.chitanka.app.mvp.presenters.categories.CategoriesPresenter;
import info.chitanka.app.mvp.presenters.categories.CategoriesPresenterImpl;
import info.chitanka.app.mvp.presenters.category_books.CategoryBooksPresenter;
import info.chitanka.app.mvp.presenters.category_books.CategoryBooksPresenterImpl;
import info.chitanka.app.mvp.presenters.my.MyLibraryPresenter;
import info.chitanka.app.mvp.presenters.my.MyLibraryPresenterImpl;
import info.chitanka.app.mvp.presenters.newest.NewBooksAndTextWorksPresenter;
import info.chitanka.app.mvp.presenters.newest.NewBooksAndTextWorksPresenterImpl;
import info.chitanka.app.mvp.presenters.textworks.TextWorksPresenter;
import info.chitanka.app.mvp.presenters.textworks.TextWorksPresenterImpl;
import info.chitanka.app.ui.BookReader;

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
    public TextWorksPresenter providesTextWorksPresenter(ChitankaApi chitankaApi) {
        return new TextWorksPresenterImpl(chitankaApi);
    }

    @Provides
    @ActivityScope
    public NewBooksAndTextWorksPresenter providesNewBooksAndTextWorksPresenter(ChitankaApi chitankaApi, Gson gson) {
        return new NewBooksAndTextWorksPresenterImpl(chitankaApi, gson);
    }

    @Provides
    @ActivityScope
    public MyLibraryPresenter providesMyLibraryPresenter(BookReader bookReader) {
        return new MyLibraryPresenterImpl(bookReader);
    }
}
