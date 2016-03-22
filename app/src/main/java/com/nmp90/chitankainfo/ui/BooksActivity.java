package com.nmp90.chitankainfo.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.nmp90.chitankainfo.Constants;
import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.di.HasComponent;
import com.nmp90.chitankainfo.di.presenters.DaggerPresenterComponent;
import com.nmp90.chitankainfo.di.presenters.PresenterComponent;
import com.nmp90.chitankainfo.di.presenters.PresenterModule;
import com.nmp90.chitankainfo.mvp.models.SearchTerms;
import com.nmp90.chitankainfo.ui.fragments.books.AuthorBooksFragment;
import com.nmp90.chitankainfo.ui.fragments.books.CategoryBooksFragment;

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

        Fragment fragment;
        if(searchTerm.equals(SearchTerms.AUTHOR.toString())) {
            String link = getIntent().getStringExtra(Constants.EXTRA_LINK);
            fragment = AuthorBooksFragment.newInstance(link);
        } else {
            String slug = getIntent().getStringExtra(Constants.EXTRA_SLUG);
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
