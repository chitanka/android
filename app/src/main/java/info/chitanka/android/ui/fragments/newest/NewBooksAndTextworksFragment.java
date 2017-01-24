package info.chitanka.android.ui.fragments.newest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.chitanka.android.R;
import info.chitanka.android.components.AnalyticsService;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.mvp.models.NewBooksResult;
import info.chitanka.android.mvp.models.NewTextWorksResult;
import info.chitanka.android.mvp.presenters.newest.NewBooksAndTextWorksPresenter;
import info.chitanka.android.mvp.views.NewBooksAndTextWorksView;
import info.chitanka.android.ui.fragments.BaseFragment;

/**
 * Created by joro on 23.01.17.
 */

public class NewBooksAndTextworksFragment extends BaseFragment implements NewBooksAndTextWorksView {
    public static final String TAG = NewBooksAndTextworksFragment.class.getSimpleName();

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Inject
    NewBooksAndTextWorksPresenter presenter;

    @Inject
    AnalyticsService analyticsService;

    @Bind(R.id.container)
    ViewPager mViewPager;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Bind(R.id.app_bar)
    AppBarLayout appBar;

    public static NewBooksAndTextworksFragment newInstance() {
        return new NewBooksAndTextworksFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(PresenterComponent.class).inject(this);
        presenter.setView(this);
        presenter.onStart();
        presenter.loadNewBooksAndTextworks();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_books_textworks, container, false);
        ButterKnife.bind(this, view);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());

        tabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ButterKnife.unbind(this);
        presenter.onDestroy();
    }

    @Override
    public String getTitle() {
        return TAG;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void presentNewBooksAndTextWorks(LinkedTreeMap<String, List<NewBooksResult>> books, LinkedTreeMap<String, List<NewTextWorksResult>> textWorks) {
        ((NewBooksFragment) mSectionsPagerAdapter.getRegisteredFragment(0)).displayBooks(books);
        ((NewTextWorksFragment) mSectionsPagerAdapter.getRegisteredFragment(1)).displayTextWorks(textWorks);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SparseArray<Fragment> registeredFragments = new SparseArray<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NewBooksFragment.newInstance();
                case 1:
                    return NewTextWorksFragment.newInstance();
            }
            return NewBooksFragment.newInstance();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }


        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_new_books);
                case 1:
                    return getString(R.string.title_new_textworks);
            }
            return null;
        }
    }
}
