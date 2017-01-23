package info.chitanka.android.ui.fragments.newest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import info.chitanka.android.R;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.mvp.models.Book;
import info.chitanka.android.mvp.models.TextWork;
import info.chitanka.android.mvp.presenters.newest.NewBooksAndTextWorksPresenter;
import info.chitanka.android.mvp.views.NewBooksAndTextWorksView;
import info.chitanka.android.ui.fragments.BaseFragment;

/**
 * Created by joro on 23.01.17.
 */

public class NewBooksAndTextworksFragment extends BaseFragment implements NewBooksAndTextWorksView {
    public static final String TAG = NewBooksAndTextworksFragment.class.getSimpleName();

    @Inject
    NewBooksAndTextWorksPresenter presenter;

    public static NewBooksAndTextworksFragment newInstance() {
        return new NewBooksAndTextworksFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(PresenterComponent.class).inject(this);
        presenter.onStart();
        presenter.loadNewBooksAndTextworks();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_books_textworks, container, false);
        ButterKnife.bind(this, view);
        return view;
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

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void presentNewBooksAndTextWorks(List<Book> books, List<TextWork> textWorks) {

    }
}
