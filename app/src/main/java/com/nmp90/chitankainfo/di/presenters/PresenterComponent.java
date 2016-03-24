package com.nmp90.chitankainfo.di.presenters;

import com.nmp90.chitankainfo.di.application.ApplicationComponent;
import com.nmp90.chitankainfo.di.scopes.ActivityScope;
import com.nmp90.chitankainfo.ui.BookDetailsActivity;
import com.nmp90.chitankainfo.ui.BooksActivity;
import com.nmp90.chitankainfo.ui.MainActivity;
import com.nmp90.chitankainfo.ui.fragments.AuthorsFragment;
import com.nmp90.chitankainfo.ui.fragments.CategoriesFragment;
import com.nmp90.chitankainfo.ui.fragments.books.AuthorBooksFragment;
import com.nmp90.chitankainfo.ui.fragments.books.BooksFragment;
import com.nmp90.chitankainfo.ui.fragments.books.CategoryBooksFragment;

import dagger.Component;

/**
 * Created by joro on 16-3-8.
 */
@ActivityScope
@Component(modules = {PresenterModule.class}, dependencies = ApplicationComponent.class)
public interface PresenterComponent {

    void inject(MainActivity activity);
    void inject(BooksActivity booksActivity);
    void inject(BookDetailsActivity bookDetailsActivity);

    void inject(BooksFragment fragment);
    void inject(AuthorsFragment authorsFragment);
    void inject(CategoriesFragment categoriesFragment);
    void inject(AuthorBooksFragment authorBooksFragment);
    void inject(CategoryBooksFragment categoryBooksFragment);
}
