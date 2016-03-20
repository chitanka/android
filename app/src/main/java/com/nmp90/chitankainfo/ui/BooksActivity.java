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
import com.nmp90.chitankainfo.ui.fragments.AuthorBooksFragment;
import com.nmp90.chitankainfo.ui.fragments.BooksFragment;

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
        String link = getIntent().getStringExtra(Constants.EXTRA_LINK);

        Fragment fragment;
        if(searchTerm.equals(SearchTerms.AUTHOR.toString())) {
         fragment = AuthorBooksFragment.newInstance(link);
        } else {
            fragment = BooksFragment.newInstance(searchTerm, link);
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
