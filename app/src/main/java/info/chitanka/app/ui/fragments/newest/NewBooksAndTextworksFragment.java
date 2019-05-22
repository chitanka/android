package info.chitanka.app.ui.fragments.newest;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.chitanka.app.R;
import info.chitanka.app.components.AnalyticsService;
import info.chitanka.app.di.presenters.PresenterComponent;
import info.chitanka.app.mvp.models.NewBooksResult;
import info.chitanka.app.mvp.models.NewTextWorksResult;
import info.chitanka.app.mvp.presenters.newest.NewBooksAndTextWorksPresenter;
import info.chitanka.app.mvp.views.NewBooksAndTextWorksView;
import info.chitanka.app.ui.fragments.BaseFragment;

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

    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    private Unbinder unbinder;

    public static NewBooksAndTextworksFragment newInstance() {
        return new NewBooksAndTextworksFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(PresenterComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_books_textworks, container, false);
        unbinder = ButterKnife.bind(this, view);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());

        tabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.startPresenting();
        presenter.loadNewBooksAndTextworks();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stopPresenting();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
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
