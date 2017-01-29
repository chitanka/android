package info.chitanka.android.ui.fragments.my;

import android.os.Bundle;

import info.chitanka.android.mvp.views.MyLibraryView;
import info.chitanka.android.ui.fragments.BaseFragment;

/**
 * Created by joro on 29.01.17.
 */

public class MyLibraryFragment extends BaseFragment implements MyLibraryView{
    public static final String TAG = MyLibraryFragment.class.getSimpleName();

    public static MyLibraryFragment newInstance() {

        Bundle args = new Bundle();

        MyLibraryFragment fragment = new MyLibraryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public String getTitle() {
        return TAG;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }
}
