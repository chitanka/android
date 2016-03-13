package com.nmp90.chitankainfo.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nmp90.chitankainfo.Constants;
import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.di.presenters.PresenterComponent;
import com.nmp90.chitankainfo.events.SearchBookEvent;
import com.nmp90.chitankainfo.mvp.models.Book;
import com.nmp90.chitankainfo.mvp.presenters.author_books.AuthorBooksPresenter;
import com.nmp90.chitankainfo.mvp.presenters.books.BooksPresenter;
import com.nmp90.chitankainfo.mvp.views.BooksView;
import com.nmp90.chitankainfo.ui.adapters.BooksAdapter;
import com.nmp90.chitankainfo.utils.RxBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import rx.Subscription;

/**
 * A placeholder fragment containing a simple view.
 */
public class BooksFragment extends BaseFragment implements BooksView {

    private static final String KEY_QUERY = "query";
    private static final String KEY_LINK = "link";

    @Inject
    BooksPresenter booksPresenter;

    @Inject
    AuthorBooksPresenter authorBooksPresenter;

    @Inject
    RxBus rxBus;

    @Bind(R.id.rv_books)
    RecyclerView rvBooks;

    @Bind(R.id.container_empty)
    RelativeLayout containerEmpty;

    @Bind(R.id.loading)
    CircularProgressBar loading;

    private Subscription subscription;
    private String query, link;

    public BooksFragment() {
    }

    public static BooksFragment newInstance(String link) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_LINK, link);
        BooksFragment fragment = new BooksFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(PresenterComponent.class).inject(this);

        if(getArguments() != null) {
            link = getArguments().getString(KEY_LINK);
        } else {
            if(savedInstanceState != null) {
                query = savedInstanceState.getString(KEY_QUERY);
            } else {
                query = Constants.INITIAL_SEARCH_BOOK_NAME;
            }
        }

        subscription = rxBus.toObserverable().subscribe((event) -> {
            if (event instanceof SearchBookEvent) {
                containerEmpty.setVisibility(View.GONE);
                rvBooks.setVisibility(View.GONE);
                query = ((SearchBookEvent) event).getName();
                booksPresenter.searchBooks(query);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        ButterKnife.bind(this, view);

        if(TextUtils.isEmpty(link)) {
            booksPresenter.setView(this);
            booksPresenter.searchBooks(query);
        } else {
            authorBooksPresenter.setView(this);
            authorBooksPresenter.searchAuthorBooks(link);
        }

        rvBooks.setLayoutManager(new LinearLayoutManager(getActivity()));

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
        booksPresenter.setView(null);
        authorBooksPresenter.setView(null);
        booksPresenter = null;
    }

    @Override
    public void loadBooks(List<Book> books) {
        if(books.size() == 0) {
            rvBooks.setVisibility(View.GONE);
            containerEmpty.setVisibility(View.VISIBLE);
        } else {
            rvBooks.setVisibility(View.VISIBLE);
            containerEmpty.setVisibility(View.GONE);
        }

        rvBooks.setAdapter(new BooksAdapter(getActivity(), books));
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
}
