package info.chitanka.android.ui.fragments.books;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import info.chitanka.android.R;
import info.chitanka.android.mvp.models.Book;
import info.chitanka.android.mvp.views.BooksView;
import info.chitanka.android.ui.adapters.BooksAdapter;
import info.chitanka.android.ui.fragments.BaseFragment;

import java.util.List;

import butterknife.Bind;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by joro on 16-3-20.
 */
public abstract class BaseBooksFragment extends BaseFragment implements BooksView {

    @Bind(R.id.loading)
    CircularProgressBar loading;

    @Bind(R.id.rv_books)
    RecyclerView rvBooks;

    @Bind(R.id.container_empty)
    RelativeLayout containerEmpty;

    @Override
    public void presentAuthorBooks(List<Book> books) {
        if(books == null || books.size() == 0) {
            rvBooks.setVisibility(View.GONE);
            containerEmpty.setVisibility(View.VISIBLE);
        } else {
            rvBooks.setVisibility(View.VISIBLE);
            containerEmpty.setVisibility(View.GONE);
        }

        rvBooks.setAdapter(new BooksAdapter(getActivity(), books, getActivity().getSupportFragmentManager()));
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
