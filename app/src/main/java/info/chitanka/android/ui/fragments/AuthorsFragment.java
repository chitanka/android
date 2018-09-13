package info.chitanka.android.ui.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import info.chitanka.android.Constants;
import info.chitanka.android.R;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.events.SearchBookEvent;
import info.chitanka.android.events.SearchClosedEvent;
import info.chitanka.android.mvp.models.Authors;
import info.chitanka.android.mvp.presenters.authors.AuthorsPresenter;
import info.chitanka.android.mvp.views.AuthorsView;
import info.chitanka.android.ui.adapters.AuthorsAdapter;
import info.chitanka.android.ui.views.containers.ScrollRecyclerView;
import info.chitanka.android.utils.RxBus;

/**
 * Created by nmp on 16-3-11.
 */
public class AuthorsFragment extends BaseFragment implements AuthorsView {
    public static final String TAG = AuthorsFragment.class.getSimpleName();
    private static final String KEY_QUERY = "QUERY";
    private int currentPage = 1, pageSize = 35;

    private AuthorsAdapter adapter;

    @Inject
    AuthorsPresenter authorsPresenter;

    @Inject
    RxBus rxBus;

    @BindView(R.id.rv_authors)
    ScrollRecyclerView rvAuthors;

    @BindView(R.id.container_empty)
    RelativeLayout containerEmpty;

    @BindView(R.id.loading)
    CircularProgressBar loading;

    private String query;
    private Unbinder unbinder;

    public static AuthorsFragment newInstance(String searchTerm) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_SEARCH_TERM, searchTerm);
        AuthorsFragment fragment = new AuthorsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(PresenterComponent.class).inject(this);

        authorsPresenter.setView(this);
        authorsPresenter.startPresenting();

        rxBus.toObserverable()
                .compose(bindToLifecycle())
                .subscribe((event) -> {
                    if (event instanceof SearchBookEvent) {
                        containerEmpty.setVisibility(View.GONE);
                        rvAuthors.setVisibility(View.GONE);
                        query = ((SearchBookEvent) event).getName();
                        authorsPresenter.searchAuthors(query);
                    } else if (event instanceof SearchClosedEvent) {
                        currentPage = 1;
                        authorsPresenter.loadAuthors(currentPage, pageSize);
                    }
                });

        if (savedInstanceState != null) {
            query = savedInstanceState.getString(KEY_QUERY);
        } else {
            query = getArguments().getString(Constants.EXTRA_SEARCH_TERM);
        }

        if (TextUtils.isEmpty(query)) {
            authorsPresenter.loadAuthors(currentPage, pageSize);
        } else {
            authorsPresenter.searchAuthors(query);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authors, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvAuthors.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        }

        rvAuthors.setOnEndReachedListener(() -> {
            currentPage++;
            authorsPresenter.loadAuthors(currentPage, pageSize);
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_QUERY, query);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
        authorsPresenter.stopPresenting();
        authorsPresenter = null;
    }

    @Override
    public void presentAuthors(Authors authors) {
        if (authors.getPersons() == null || authors.getPersons().size() == 0) {
            rvAuthors.setVisibility(View.GONE);
            containerEmpty.setVisibility(View.VISIBLE);
            return;
        } else {
            rvAuthors.setVisibility(View.VISIBLE);
            containerEmpty.setVisibility(View.GONE);
        }

        if (adapter == null) {
            adapter = new AuthorsAdapter(getActivity(), authors.getPersons());
            rvAuthors.setAdapter(adapter, authors.getPager().getTotalCount());
        } else {
            adapter.addAll(authors.getPersons());
        }
    }

    @Override
    public void presentSearch(Authors authors) {
        if (authors.getPersons() == null || authors.getPersons().size() == 0) {
            rvAuthors.setVisibility(View.GONE);
            containerEmpty.setVisibility(View.VISIBLE);
            return;
        } else {
            rvAuthors.setVisibility(View.VISIBLE);
            containerEmpty.setVisibility(View.GONE);
        }

        adapter = new AuthorsAdapter(getActivity(), authors.getPersons());
        rvAuthors.setAdapter(adapter, authors.getPager().getTotalCount());

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void hideLoading() {
        if (loading != null) {
            loading.progressiveStop();
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {
        if (loading != null) {
            loading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public String getTitle() {
        return TAG;
    }
}
