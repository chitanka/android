package info.chitanka.android.ui.fragments.books;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.chitanka.android.R;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.events.SearchBookEvent;
import info.chitanka.android.mvp.presenters.author_books.AuthorBooksPresenter;
import info.chitanka.android.mvp.views.BooksView;
import info.chitanka.android.utils.RxBus;
import rx.Subscription;

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

    @Bind(R.id.rv_books)
    RecyclerView rvBooks;

    private Subscription subscription;
    private String link;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(PresenterComponent.class).inject(this);
        authorBooksPresenter.onStart();

        link = getArguments().getString(KEY_LINK);

        subscription = rxBus.toObserverable().subscribe((event) -> {
            if (event instanceof SearchBookEvent) {
                containerEmpty.setVisibility(View.GONE);
                rvBooks.setVisibility(View.GONE);
                link = ((SearchBookEvent) event).getName();

                authorBooksPresenter.searchAuthorBooks(link);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        ButterKnife.bind(this, view);

        authorBooksPresenter.setView(this);
        authorBooksPresenter.searchAuthorBooks(link);

        rvBooks.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        subscription.unsubscribe();
        authorBooksPresenter.onDestroy();
        authorBooksPresenter.setView(null);
    }

    @Override
    public String getTitle() {
        return TAG;
    }
}
