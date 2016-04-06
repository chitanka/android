package com.nmp90.chitankainfo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nmp90.chitankainfo.Constants;
import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.di.presenters.PresenterComponent;
import com.nmp90.chitankainfo.events.SearchBookEvent;
import com.nmp90.chitankainfo.mvp.models.Author;
import com.nmp90.chitankainfo.mvp.presenters.authors.AuthorsPresenter;
import com.nmp90.chitankainfo.mvp.views.AuthorsView;
import com.nmp90.chitankainfo.ui.adapters.AuthorsAdapter;
import com.nmp90.chitankainfo.utils.RxBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import rx.Subscription;

/**
 * Created by nmp on 16-3-11.
 */
public class AuthorsFragment extends BaseFragment implements AuthorsView {

    private static final String KEY_QUERY = "QUERY";

    @Inject
    AuthorsPresenter authorsPresenter;

    @Inject
    RxBus rxBus;

    @Bind(R.id.rv_authors)
    RecyclerView rvAuthors;

    @Bind(R.id.container_empty)
    RelativeLayout containerEmpty;

    @Bind(R.id.loading)
    CircularProgressBar loading;

    private Subscription subscription;
    private String query;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            query = savedInstanceState.getString(KEY_QUERY);
        } else {
            query = Constants.INITIAL_SEARCH_AUTHOR_NAME;
        }

        getComponent(PresenterComponent.class).inject(this);

        subscription = rxBus.toObserverable().subscribe((event) -> {
            if(event instanceof SearchBookEvent) {
                containerEmpty.setVisibility(View.GONE);
                rvAuthors.setVisibility(View.GONE);
                query = ((SearchBookEvent)event).getName();
                authorsPresenter.searchAuthors(query);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authors, container, false);
        ButterKnife.bind(this, view);

        authorsPresenter.setView(this);
        authorsPresenter.searchAuthors(query);

        rvAuthors.setLayoutManager(new GridLayoutManager(getActivity(), Constants.AUTHORS_PER_ROW));
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_QUERY, query);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        subscription.unsubscribe();
        authorsPresenter = null;
    }

    @Override
    public void presentAuthors(List<Author> authors) {
        if(authors.size() == 0) {
            rvAuthors.setVisibility(View.GONE);
            containerEmpty.setVisibility(View.VISIBLE);
        } else {
            rvAuthors.setVisibility(View.VISIBLE);
            containerEmpty.setVisibility(View.GONE);
        }

        rvAuthors.setAdapter(new AuthorsAdapter(getActivity(), authors));
    }

    @Override
    public void hideLoading() {
        if(loading != null) {
            loading.progressiveStop();
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {
        if(loading != null) {
            loading.setVisibility(View.VISIBLE);
        }
    }
}
