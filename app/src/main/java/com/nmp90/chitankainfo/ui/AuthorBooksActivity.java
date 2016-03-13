package com.nmp90.chitankainfo.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.nmp90.chitankainfo.Constants;
import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.di.HasComponent;
import com.nmp90.chitankainfo.di.presenters.DaggerPresenterComponent;
import com.nmp90.chitankainfo.di.presenters.PresenterComponent;
import com.nmp90.chitankainfo.di.presenters.PresenterModule;
import com.nmp90.chitankainfo.ui.fragments.BooksFragment;

public class AuthorBooksActivity extends BaseActivity implements HasComponent<PresenterComponent> {

    String authorName, link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_books);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupActionBar();

        getComponent().inject(this);

        if (savedInstanceState == null) {
            authorName = getIntent().getStringExtra(Constants.EXTRA_AUTHOR_NAME);
            link = getIntent().getStringExtra(Constants.EXTRA_AUTHOR_LINK);
        } else {
            authorName = savedInstanceState.getString(Constants.EXTRA_AUTHOR_NAME);
            link = savedInstanceState.getString(Constants.EXTRA_AUTHOR_LINK);
        }

        getSupportFragmentManager().beginTransaction().add( R.id.container, BooksFragment.newInstance(link)).commit();
        setTitle(authorName);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.EXTRA_AUTHOR_NAME, authorName);
        outState.putString(Constants.EXTRA_AUTHOR_LINK, link);
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
