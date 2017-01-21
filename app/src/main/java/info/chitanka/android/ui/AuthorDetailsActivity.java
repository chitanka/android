package info.chitanka.android.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import info.chitanka.android.Constants;
import info.chitanka.android.R;
import info.chitanka.android.di.HasComponent;
import info.chitanka.android.di.presenters.DaggerPresenterComponent;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.ui.fragments.TextWorksFragment;
import info.chitanka.android.ui.fragments.books.AuthorBooksFragment;

public class AuthorDetailsActivity extends BaseActivity implements HasComponent<PresenterComponent> {
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private String authorSlug;
    private PresenterComponent presenterComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra(Constants.EXTRA_TITLE));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenterComponent = DaggerPresenterComponent.builder().applicationComponent(getApplicationComponent()).build();
        getComponent().inject(this);

        authorSlug = getIntent().getStringExtra(Constants.EXTRA_SLUG);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

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

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return AuthorBooksFragment.newInstance(authorSlug);
                case 1:
                    return TextWorksFragment.newInstance(null, authorSlug);
            }

            return null;
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
