package info.chitanka.android.di.presenters;

import dagger.Component;
import info.chitanka.android.di.application.ApplicationComponent;
import info.chitanka.android.di.scopes.ActivityScope;
import info.chitanka.android.ui.AuthorBooksActivity;
import info.chitanka.android.ui.AuthorDetailsActivity;
import info.chitanka.android.ui.BookDetailsActivity;
import info.chitanka.android.ui.MainActivity;
import info.chitanka.android.ui.ReadersActivity;
import info.chitanka.android.ui.SearchActivity;
import info.chitanka.android.ui.dialogs.DownloadDialog;
import info.chitanka.android.ui.fragments.AuthorsFragment;
import info.chitanka.android.ui.fragments.CategoriesFragment;
import info.chitanka.android.ui.fragments.TextWorksFragment;
import info.chitanka.android.ui.fragments.books.AuthorBooksFragment;
import info.chitanka.android.ui.fragments.books.BooksFragment;
import info.chitanka.android.ui.fragments.books.CategoryBooksFragment;

/**
 * Created by joro on 16-3-8.
 */
@ActivityScope
@Component(modules = {PresenterModule.class}, dependencies = ApplicationComponent.class)
public interface PresenterComponent {

    void inject(MainActivity activity);
    void inject(AuthorBooksActivity authorBooksActivity);
    void inject(BookDetailsActivity bookDetailsActivity);
    void inject(SearchActivity searchActivity);
    void inject(ReadersActivity readersActivity);
    void inject(AuthorDetailsActivity authorDetailsActivity);

    void inject(BooksFragment fragment);
    void inject(AuthorsFragment authorsFragment);
    void inject(CategoriesFragment categoriesFragment);
    void inject(AuthorBooksFragment authorBooksFragment);
    void inject(CategoryBooksFragment categoryBooksFragment);
    void inject(TextWorksFragment textWorksFragment);
    void inject(DownloadDialog downloadDialog);
}
