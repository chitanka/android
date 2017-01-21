package info.chitanka.android.ui;

import android.annotation.TargetApi;
import android.content.Intent;
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
import info.chitanka.android.ui.fragments.books.AuthorBooksFragment;

public class BooksActivity extends BaseActivity implements HasComponent<PresenterComponent> {

    private PresenterComponent presenterComponent;

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

        getSupportFragmentManager().beginTransaction().add( R.id.container, fragment).commit();
        setTitle(title);
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
