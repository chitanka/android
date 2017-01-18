package info.chitanka.android.ui.fragments.books;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import info.chitanka.android.Constants;
import info.chitanka.android.R;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.events.SearchBookEvent;
import info.chitanka.android.mvp.presenters.books.BooksPresenter;
import info.chitanka.android.mvp.views.BooksView;
import info.chitanka.android.utils.RxBus;
import rx.Subscription;

/**
 * A placeholder fragment containing a simple view.
 */
public class BooksFragment extends BaseBooksFragment implements BooksView {
    public static final String TAG = BooksFragment.class.getSimpleName();
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

    public static BooksFragment newInstance(String searchTerm) {

        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_SEARCH_TERM, searchTerm);
        BooksFragment fragment = new BooksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(PresenterComponent.class).inject(this);
        booksPresenter.onStart();

        if(savedInstanceState != null) {
            query = savedInstanceState.getString(KEY_QUERY);
        } else {
            if (getArguments() != null && getArguments().containsKey(Constants.EXTRA_SEARCH_TERM)) {
                query = getArguments().getString(Constants.EXTRA_SEARCH_TERM);
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
        booksPresenter.onDestroy();
        booksPresenter = null;
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
