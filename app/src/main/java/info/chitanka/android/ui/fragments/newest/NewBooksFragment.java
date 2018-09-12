package info.chitanka.android.ui.fragments.newest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.internal.LinkedTreeMap;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import info.chitanka.android.R;
import info.chitanka.android.TrackingConstants;
import info.chitanka.android.components.AnalyticsService;
import info.chitanka.android.di.HasComponent;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.mvp.models.NewBooksResult;
import info.chitanka.android.ui.adapters.NewBooksAdapter;
import info.chitanka.android.utils.IntentUtils;

/**
 * Created by nmp on 23.01.17.
 */

public class NewBooksFragment extends RxFragment {
    public static final String TAG = NewBooksFragment.class.getSimpleName();


    @Inject
    AnalyticsService analyticsService;

    @BindView(R.id.rv_books)
    RecyclerView rvBooks;

    @BindView(R.id.loading)
    CircularProgressBar loading;

    private Unbinder unbinder;

    public static NewBooksFragment newInstance() {
        return new NewBooksFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PresenterComponent.class.cast(((HasComponent<PresenterComponent>) getActivity()).getComponent()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void displayBooks(LinkedTreeMap<String, List<NewBooksResult>> map) {
        if (!isAdded()) {
            return;
        }

        loading.progressiveStop();
        loading.setVisibility(View.GONE);

        rvBooks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        NewBooksAdapter adapter = new NewBooksAdapter(map, getActivity());
        adapter.getOnWebClick()
                .compose(bindToLifecycle())
                .subscribe(book -> {
                    IntentUtils.openWebUrl(book.getWebChitankaUrl(), getActivity());
                    analyticsService.logEvent(TrackingConstants.CLICK_WEB_NEW_BOOK);
                });

        adapter.getOnReadClick()
                .compose(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(book -> {
                    IntentUtils.openWebUrl(book.getWebChitankaUrl(), getActivity());
                    analyticsService.logEvent(TrackingConstants.CLICK_WEB_NEW_BOOK);
                });
        rvBooks.setAdapter(adapter);
    }
}
