package info.chitanka.app.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

import javax.inject.Inject;

import info.chitanka.app.Constants;
import info.chitanka.app.R;
import info.chitanka.app.TrackingConstants;
import info.chitanka.app.components.AnalyticsService;
import info.chitanka.app.di.HasComponent;
import info.chitanka.app.di.presenters.DaggerPresenterComponent;
import info.chitanka.app.di.presenters.PresenterComponent;
import info.chitanka.app.ui.fragments.TextWorksFragment;
import info.chitanka.app.ui.fragments.books.AuthorBooksFragment;

public class AuthorDetailsActivity extends BaseActivity implements HasComponent<PresenterComponent> {
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private String authorSlug;
    private PresenterComponent presenterComponent;

    @Inject
    AnalyticsService analyticsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra(Constants.EXTRA_TITLE));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenterComponent = DaggerPresenterComponent.builder().applicationComponent(getApplicationComponent()).build();
        getComponent().inject(this);

        authorSlug = getIntent().getStringExtra(Constants.EXTRA_SLUG);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        analyticsService.logEvent(TrackingConstants.VIEW_AUTHOR_DETAILS, new HashMap<String, String>() {{ put("slug", authorSlug);}});
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public PresenterComponent getComponent() {
        return presenterComponent;
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return AuthorBooksFragment.newInstance(authorSlug);
                case 1:
                    return TextWorksFragment.newInstance(null, authorSlug);
            }

            return AuthorBooksFragment.newInstance(authorSlug);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_books);
                case 1:
                    return getString(R.string.title_textworks);
            }
            return null;
        }
    }
}
