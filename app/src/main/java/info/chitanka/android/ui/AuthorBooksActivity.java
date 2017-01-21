package info.chitanka.android.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import java.util.HashMap;

import javax.inject.Inject;

import info.chitanka.android.Constants;
import info.chitanka.android.R;
import info.chitanka.android.TrackingConstants;
import info.chitanka.android.components.AnalyticsService;
import info.chitanka.android.di.HasComponent;
import info.chitanka.android.di.presenters.DaggerPresenterComponent;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.di.presenters.PresenterModule;
import info.chitanka.android.ui.fragments.books.AuthorBooksFragment;

public class AuthorBooksActivity extends BaseActivity implements HasComponent<PresenterComponent> {

    private PresenterComponent presenterComponent;

    @Inject
    AnalyticsService analyticsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_books);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupActionBar();

        presenterComponent = DaggerPresenterComponent.builder().applicationComponent(getApplicationComponent()).presenterModule(new PresenterModule()).build();
        getComponent().inject(this);

        Intent intent = getIntent();
        String title = intent.getStringExtra(Constants.EXTRA_TITLE);
        String slug = intent.getStringExtra(Constants.EXTRA_SLUG);

        Fragment fragment = AuthorBooksFragment.newInstance(slug);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        setTitle(title);

        analyticsService.logEvent(TrackingConstants.VIEW_AUTHOR_BOOKS, new HashMap<String, String>() {{ put("authorName", title);}});
    }

    @Override
    public PresenterComponent getComponent() {
        return presenterComponent;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
