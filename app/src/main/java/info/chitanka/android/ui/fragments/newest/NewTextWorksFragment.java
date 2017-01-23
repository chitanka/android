package info.chitanka.android.ui.fragments.newest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import info.chitanka.android.R;
import info.chitanka.android.mvp.models.NewTextWorksResult;
import info.chitanka.android.ui.adapters.NewTextWorksAdapter;

/**
 * Created by nmp on 23.01.17.
 */

public class NewTextWorksFragment extends Fragment {
    public static final String TAG = NewTextWorksFragment.class.getSimpleName();

    public static NewTextWorksFragment newInstance() {
        return new NewTextWorksFragment();
    }

    @Bind(R.id.rv_textworks)
    RecyclerView rvTextWorks;

    @Bind(R.id.loading)
    CircularProgressBar loading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_works, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void displayTextWorks(LinkedTreeMap<String, List<NewTextWorksResult>> map) {
        loading.progressiveStop();
        loading.setVisibility(View.GONE);
        rvTextWorks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvTextWorks.setAdapter(new NewTextWorksAdapter(map, getResources()));
    }
}
