package com.nmp90.chitankainfo.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nmp90.chitankainfo.ui.fragments.AuthorsFragment;
import com.nmp90.chitankainfo.ui.fragments.BooksFragment;
import com.nmp90.chitankainfo.ui.fragments.CategoriesFragment;

/**
 * Created by nmp on 16-3-11.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new BooksFragment();
            case 1:
                return new AuthorsFragment();
            case 2:
                return new CategoriesFragment();
            default:
                return new BooksFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Книги";
            case 1:
                return "Автори";
            case 2:
                return "Категории";
            default:
                return "Книги";
        }
    }
}
