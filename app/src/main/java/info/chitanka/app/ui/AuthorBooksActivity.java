package info.chitanka.app.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;

import javax.inject.Inject;

import info.chitanka.app.Constants;
import info.chitanka.app.R;
import info.chitanka.app.TrackingConstants;
import info.chitanka.app.components.AnalyticsService;
import info.chitanka.app.di.HasComponent;
import info.chitanka.app.di.presenters.DaggerPresenterComponent;
import info.chitanka.app.di.presenters.PresenterComponent;
import info.chitanka.app.di.presenters.PresenterModule;
import info.chitanka.app.ui.fragments.books.AuthorBooksFragment;

public class AuthorBooksActivity extends BaseActivity implements HasComponent<PresenterComponent> {

    private PresenterComponent presenterComponent;

    @Inject
    AnalyticsService analyticsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_books);

        Toolbar toolbar = findViewById(R.id.toolbar);
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

    private void setupActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
