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
import info.chitanka.android.mvp.models.NewBooksResult;
import info.chitanka.android.ui.adapters.NewBooksAdapter;
import info.chitanka.android.utils.IntentUtils;
import rx.Subscription;

/**
 * Created by nmp on 23.01.17.
 */

public class NewBooksFragment extends Fragment {
    public static final String TAG = NewBooksFragment.class.getSimpleName();

    private Subscription subscription;

    @Inject
    AnalyticsService analyticsService;

    @Bind(R.id.rv_books)
    RecyclerView rvBooks;

    @Bind(R.id.loading)
    CircularProgressBar loading;

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

    public void displayBooks(LinkedTreeMap<String, List<NewBooksResult>> map) {
        if (!isAdded()) {
            return;
        }

        loading.progressiveStop();
        loading.setVisibility(View.GONE);

        rvBooks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        NewBooksAdapter adapter = new NewBooksAdapter(map, getActivity());
        subscription = adapter.getOnWebClick().subscribe(book -> {
            IntentUtils.openWebUrl(book.getChitankaUrl(), getActivity());
            analyticsService.logEvent(TrackingConstants.CLICK_WEB_NEW_BOOK);
        });
        rvBooks.setAdapter(adapter);
    }
}
