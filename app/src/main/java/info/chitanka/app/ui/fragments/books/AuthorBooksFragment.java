package info.chitanka.app.ui.fragments.books;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.chitanka.app.R;
import info.chitanka.app.di.presenters.PresenterComponent;
import info.chitanka.app.events.SearchBookEvent;
import info.chitanka.app.mvp.presenters.author_books.AuthorBooksPresenter;
import info.chitanka.app.mvp.views.BooksView;
import info.chitanka.app.utils.RxBus;

/**
 * A placeholder fragment containing a simple view.
 */
public class AuthorBooksFragment extends BaseBooksFragment implements BooksView {
    public static final String TAG = AuthorBooksFragment.class.getSimpleName();
    private static final String KEY_LINK = "link";

    @Inject
    AuthorBooksPresenter authorBooksPresenter;

    @Inject
    RxBus rxBus;

    @BindView(R.id.rv_books)
    RecyclerView rvBooks;

    private String link;
    private Unbinder unbinder;

    public AuthorBooksFragment() {
    }

    public static AuthorBooksFragment newInstance(String link) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_LINK, link);
        AuthorBooksFragment fragment = new AuthorBooksFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(PresenterComponent.class).inject(this);

        rxBus.toObserverable()
                .compose(bindToLifecycle())
                .subscribe((event) -> {
                    if (event instanceof SearchBookEvent) {
                        containerEmpty.setVisibility(View.GONE);
                        rvBooks.setVisibility(View.GONE);
                        link = ((SearchBookEvent) event).getName();

                        authorBooksPresenter.searchAuthorBooks(link);
                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        link = getArgument(KEY_LINK, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_LINK, link);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvBooks.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            rvBooks.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        authorBooksPresenter.startPresenting();

        authorBooksPresenter.setView(this);
        authorBooksPresenter.searchAuthorBooks(link);
    }

    @Override
    public void onPause() {
        super.onPause();
        authorBooksPresenter.stopPresenting();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public String getTitle() {
        return TAG;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
