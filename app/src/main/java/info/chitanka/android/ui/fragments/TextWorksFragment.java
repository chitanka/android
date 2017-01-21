package info.chitanka.android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import info.chitanka.android.Constants;
import info.chitanka.android.R;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.mvp.models.TextWork;
import info.chitanka.android.mvp.presenters.textworks.TextWorksPresenter;
import info.chitanka.android.mvp.views.TextWorksView;
import info.chitanka.android.ui.fragments.books.TextWorksAdapter;

/**
 * Created by nmp on 21.01.17.
 */

public class TextWorksFragment extends BaseFragment implements TextWorksView {
    public static final String TAG = TextWorksFragment.class.getSimpleName();

    private static final String KEY_SEARCH_TERM = "search_term";

    private String searchTerm;

    @Inject
    TextWorksPresenter presenter;

    @Bind(R.id.rv_textworks)
    RecyclerView rvTextWorks;

    @Bind(R.id.container_empty)
    RelativeLayout containerEmpty;

    @Bind(R.id.loading)
    CircularProgressBar loading;

    public static TextWorksFragment newInstance(String searchTerm) {

        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_SEARCH_TERM, searchTerm);
        TextWorksFragment fragment = new TextWorksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            searchTerm = savedInstanceState.getString(KEY_SEARCH_TERM);
        } else {
            searchTerm = getArguments().getString(Constants.EXTRA_SEARCH_TERM);
        }

        getComponent(PresenterComponent.class).inject(this);
        presenter.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_works, container, false);
        ButterKnife.bind(this, view);
        presenter.setView(this);

        rvTextWorks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.searchTextWorks(searchTerm);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SEARCH_TERM, searchTerm);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ButterKnife.unbind(this);
        presenter.onDestroy();
    }

    @Override
    public String getTitle() {
        return TAG;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void hideLoading() {
        loading.progressiveStop();
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void presentTextWorks(List<TextWork> texts) {
        if(texts.size() == 0) {
            rvTextWorks.setVisibility(View.GONE);
            containerEmpty.setVisibility(View.VISIBLE);
            return;
        }

        rvTextWorks.setVisibility(View.VISIBLE);
        containerEmpty.setVisibility(View.GONE);
        rvTextWorks.setAdapter(new TextWorksAdapter(texts));
    }
}
