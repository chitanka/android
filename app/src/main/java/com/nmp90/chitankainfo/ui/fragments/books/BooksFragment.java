package com.nmp90.chitankainfo.ui.fragments.books;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmp90.chitankainfo.Constants;
import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.di.presenters.PresenterComponent;
import com.nmp90.chitankainfo.events.SearchBookEvent;
import com.nmp90.chitankainfo.mvp.presenters.books.BooksPresenter;
import com.nmp90.chitankainfo.mvp.views.BooksView;
import com.nmp90.chitankainfo.utils.RxBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * A placeholder fragment containing a simple view.
 */
public class BooksFragment extends BaseBooksFragment implements BooksView {

    private static final String KEY_QUERY = "query";

    @Inject
    BooksPresenter booksPresenter;

    @Inject
    RxBus rxBus;

    private Subscription subscription;
    private String query;

    public BooksFragment() {
    }

    public static BooksFragment newInstance() {
        return new BooksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(PresenterComponent.class).inject(this);

        if(savedInstanceState != null) {
            query = savedInstanceState.getString(KEY_QUERY);
        } else {
            query = Constants.INITIAL_SEARCH_BOOK_NAME;
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

        booksPresenter.setView(this);
        booksPresenter.searchBooks(query);

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
        booksPresenter = null;
    }
}
