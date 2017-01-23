package info.chitanka.android.ui.fragments.newest;

import android.support.v4.app.Fragment;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import info.chitanka.android.mvp.models.NewBooksResult;

/**
 * Created by nmp on 23.01.17.
 */

public class NewBooksFragment extends Fragment {
    public static final String TAG = NewBooksFragment.class.getSimpleName();

    public static NewBooksFragment newInstance() {
        return new NewBooksFragment();
    }

    public void displayBooks(LinkedTreeMap<String, List<NewBooksResult>> map) {

    }
}
