package info.chitanka.app.ui.fragments.newest;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.internal.LinkedTreeMap;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import info.chitanka.app.R;
import info.chitanka.app.TrackingConstants;
import info.chitanka.app.components.AnalyticsService;
import info.chitanka.app.di.HasComponent;
import info.chitanka.app.di.presenters.PresenterComponent;
import info.chitanka.app.mvp.models.NewTextWorksResult;
import info.chitanka.app.ui.adapters.NewTextWorksAdapter;
import info.chitanka.app.utils.IntentUtils;

/**
 * Created by nmp on 23.01.17.
 */

public class NewTextWorksFragment extends RxFragment {
    public static final String TAG = NewTextWorksFragment.class.getSimpleName();
    public static NewTextWorksFragment newInstance() {
        return new NewTextWorksFragment();
    }


    @Inject
    AnalyticsService analyticsService;

    @BindView(R.id.rv_textworks)
    RecyclerView rvTextWorks;

    @BindView(R.id.loading)
    CircularProgressBar loading;

    private Unbinder unbinder;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PresenterComponent.class.cast(((HasComponent<PresenterComponent>) getActivity()).getComponent()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_works, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void displayTextWorks(LinkedTreeMap<String, List<NewTextWorksResult>> map) {
        if (!isAdded()) {
            return;
        }

        loading.progressiveStop();
        loading.setVisibility(View.GONE);

        rvTextWorks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        NewTextWorksAdapter adapter = new NewTextWorksAdapter(map, getResources(), getChildFragmentManager());
        adapter.getOnWebClick()
                .compose(bindToLifecycle())
                .subscribe(textwork -> {
            IntentUtils.openWebUrl(textwork.getChitankaUrl(), getActivity());
            analyticsService.logEvent(TrackingConstants.CLICK_WEB_NEW_TEXTWORKS);
        });
        rvTextWorks.setAdapter(adapter);
    }
}
