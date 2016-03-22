package com.nmp90.chitankainfo.ui.fragments.books;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.di.presenters.PresenterComponent;
import com.nmp90.chitankainfo.mvp.models.Book;
import com.nmp90.chitankainfo.mvp.presenters.books.BooksPresenter;
import com.nmp90.chitankainfo.ui.adapters.BooksAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by joro on 16-3-20.
 */
public class CategoryBooksFragment extends BaseBooksFragment {
    private static final String KEY_SLUG = "slug";

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount, page;

    LinearLayoutManager layoutManager;

    @Inject
    BooksPresenter booksPresenter;

    @Bind(R.id.rv_books)
    RecyclerView rvBooks;

    private Subscription subscription;
    private String slug;

    public CategoryBooksFragment() {
    }

    public static CategoryBooksFragment newInstance(String slug) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SLUG, slug);
        CategoryBooksFragment fragment = new CategoryBooksFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(PresenterComponent.class).inject(this);

        slug = getArguments().getString(KEY_SLUG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        ButterKnife.bind(this, view);

        booksPresenter.setView(this);
        booksPresenter.getBooksForCategory(slug, page);

        layoutManager = new LinearLayoutManager(getActivity());
        rvBooks.setLayoutManager(layoutManager);
        rvBooks.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            page++;
                            booksPresenter.getBooksForCategory(slug, page);

                        }
                    }
                }
            }
        });


        return view;
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
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        subscription.unsubscribe();
        booksPresenter.setView(null);
    }
}
