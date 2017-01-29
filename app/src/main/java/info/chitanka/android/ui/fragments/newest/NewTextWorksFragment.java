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

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import info.chitanka.android.R;
import info.chitanka.android.TrackingConstants;
import info.chitanka.android.components.AnalyticsService;
import info.chitanka.android.di.HasComponent;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.mvp.models.NewTextWorksResult;
import info.chitanka.android.ui.adapters.NewTextWorksAdapter;
import info.chitanka.android.utils.IntentUtils;
import rx.Subscription;

/**
 * Created by nmp on 23.01.17.
 */

public class NewTextWorksFragment extends Fragment {
    public static final String TAG = NewTextWorksFragment.class.getSimpleName();

    public static NewTextWorksFragment newInstance() {
        return new NewTextWorksFragment();
    }

    private Subscription subscription;

    @Inject
    AnalyticsService analyticsService;

    @Bind(R.id.rv_textworks)
    RecyclerView rvTextWorks;

    @Bind(R.id.loading)
    CircularProgressBar loading;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PresenterComponent.class.cast(((HasComponent<PresenterComponent>) getActivity()).getComponent()).inject(this);
    }

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
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void displayTextWorks(LinkedTreeMap<String, List<NewTextWorksResult>> map) {
        if (!isAdded()) {
            return;
        }

        loading.progressiveStop();
        loading.setVisibility(View.GONE);

        rvTextWorks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        NewTextWorksAdapter adapter = new NewTextWorksAdapter(map, getResources(), getChildFragmentManager());
        subscription = adapter.getOnWebClick().subscribe(textwork -> {
            IntentUtils.openWebUrl(textwork.getChitankaUrl(), getActivity());
            analyticsService.logEvent(TrackingConstants.CLICK_WEB_NEW_TEXTWORKS);
        });
        rvTextWorks.setAdapter(adapter);
    }
}
