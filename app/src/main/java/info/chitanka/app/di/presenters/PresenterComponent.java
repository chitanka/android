package info.chitanka.app.di.presenters;

import dagger.Component;
import info.chitanka.app.di.application.ApplicationComponent;
import info.chitanka.app.di.scopes.ActivityScope;
import info.chitanka.app.ui.AuthorBooksActivity;
import info.chitanka.app.ui.AuthorDetailsActivity;
import info.chitanka.app.ui.BookDetailsActivity;
import info.chitanka.app.ui.MainActivity;
import info.chitanka.app.ui.ReadersActivity;
import info.chitanka.app.ui.SearchAllActivity;
import info.chitanka.app.ui.dialogs.DownloadDialog;
import info.chitanka.app.ui.fragments.AuthorsFragment;
import info.chitanka.app.ui.fragments.CategoriesFragment;
import info.chitanka.app.ui.fragments.TextWorksFragment;
import info.chitanka.app.ui.fragments.books.AuthorBooksFragment;
import info.chitanka.app.ui.fragments.books.BooksFragment;
import info.chitanka.app.ui.fragments.books.CategoryBooksFragment;
import info.chitanka.app.ui.fragments.my.MyLibraryFragment;
import info.chitanka.app.ui.fragments.newest.NewBooksAndTextworksFragment;
import info.chitanka.app.ui.fragments.newest.NewBooksFragment;
import info.chitanka.app.ui.fragments.newest.NewTextWorksFragment;

/**
 * Created by joro on 16-3-8.
 */
@ActivityScope
@Component(modules = {PresenterModule.class}, dependencies = ApplicationComponent.class)
public interface PresenterComponent {

    void inject(MainActivity activity);
    void inject(AuthorBooksActivity authorBooksActivity);
    void inject(BookDetailsActivity bookDetailsActivity);
    void inject(SearchAllActivity searchAllActivity);
    void inject(ReadersActivity readersActivity);
    void inject(AuthorDetailsActivity authorDetailsActivity);

    void inject(BooksFragment fragment);
    void inject(AuthorsFragment authorsFragment);
    void inject(CategoriesFragment categoriesFragment);
    void inject(AuthorBooksFragment authorBooksFragment);
    void inject(CategoryBooksFragment categoryBooksFragment);
    void inject(TextWorksFragment textWorksFragment);
    void inject(DownloadDialog downloadDialog);
    void inject(NewBooksAndTextworksFragment newBooksAndTextworksFragment);
    void inject(NewTextWorksFragment newTextWorksFragment);
    void inject(NewBooksFragment newBooksFragment);
    void inject(MyLibraryFragment myLibraryFragment);
}
