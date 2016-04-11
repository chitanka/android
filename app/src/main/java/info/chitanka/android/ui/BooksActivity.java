package info.chitanka.android.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import info.chitanka.android.Constants;
import info.chitanka.android.R;
import info.chitanka.android.di.HasComponent;
import info.chitanka.android.di.presenters.DaggerPresenterComponent;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.di.presenters.PresenterModule;
import info.chitanka.android.mvp.models.SearchTerms;
import info.chitanka.android.ui.fragments.books.AuthorBooksFragment;
import info.chitanka.android.ui.fragments.books.CategoryBooksFragment;

public class BooksActivity extends BaseActivity implements HasComponent<PresenterComponent> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_books);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupActionBar();

        getComponent().inject(this);

        String searchTerm = getIntent().getStringExtra(Constants.EXTRA_SEARCH_TERM);
        String title = getIntent().getStringExtra(Constants.EXTRA_TITLE);
        String slug = getIntent().getStringExtra(Constants.EXTRA_SLUG);

        Fragment fragment;
        if(searchTerm.equals(SearchTerms.AUTHOR.toString())) {
            fragment = AuthorBooksFragment.newInstance(slug);
        } else {
            fragment = CategoryBooksFragment.newInstance(slug);
        }

        getSupportFragmentManager().beginTransaction().add( R.id.container, fragment).commit();

        setTitle(title);
    }

    @Override
    public PresenterComponent getComponent() {
        return DaggerPresenterComponent.builder().applicationComponent(getApplicationComponent()).presenterModule(new PresenterModule()).build();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
