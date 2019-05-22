package info.chitanka.app.ui;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.ButterKnife;
import info.chitanka.app.Constants;
import info.chitanka.app.R;
import info.chitanka.app.TrackingConstants;
import info.chitanka.app.components.AnalyticsService;
import info.chitanka.app.di.HasComponent;
import info.chitanka.app.di.presenters.DaggerPresenterComponent;
import info.chitanka.app.di.presenters.PresenterComponent;
import info.chitanka.app.di.presenters.PresenterModule;
import info.chitanka.app.events.SearchBookEvent;
import info.chitanka.app.ui.fragments.AuthorsFragment;
import info.chitanka.app.ui.fragments.TextWorksFragment;
import info.chitanka.app.ui.fragments.books.BooksFragment;
import info.chitanka.app.utils.RxBus;

public class SearchAllActivity extends BaseActivity implements HasComponent<PresenterComponent> {

    @Inject
    RxBus rxBus;

    @Inject
    AnalyticsService analyticsService;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private PresenterComponent presenterComponent;
    private String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_all);

        presenterComponent = DaggerPresenterComponent.builder().applicationComponent(getApplicationComponent()).presenterModule(new PresenterModule()).build();
        getComponent().inject(this);
        ButterKnife.bind(this);

        searchTerm = getIntent().getStringExtra(Constants.EXTRA_SEARCH_TERM);
        setTitle(searchTerm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        analyticsService.logEvent(TrackingConstants.VIEW_SEARCH, new HashMap<String, String>() {{ put("searchTerm", searchTerm);}});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chitanka_main, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setQueryHint(getString(R.string.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!isFinishing()) {
                    rxBus.send(new SearchBookEvent(s));
                    setTitle(s);
                }

                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public PresenterComponent getComponent() {
        return presenterComponent;
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return BooksFragment.newInstance(searchTerm);
                case 1:
                    return AuthorsFragment.newInstance(searchTerm);
                case 2:
                    return TextWorksFragment.newInstance(searchTerm, null);
            }
            return BooksFragment.newInstance(searchTerm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_books);
                case 1:
                    return getString(R.string.title_authors);
                case 2:
                    return getString(R.string.title_textworks);
            }
            return null;
        }
    }
}
