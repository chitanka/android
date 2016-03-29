package com.nmp90.chitankainfo.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nmp90.chitankainfo.Constants;
import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.di.HasComponent;
import com.nmp90.chitankainfo.di.presenters.DaggerPresenterComponent;
import com.nmp90.chitankainfo.di.presenters.PresenterComponent;
import com.nmp90.chitankainfo.di.presenters.PresenterModule;
import com.nmp90.chitankainfo.mvp.models.Book;
import com.nmp90.chitankainfo.mvp.models.BookDetails;
import com.nmp90.chitankainfo.mvp.presenters.book.BookPresenter;
import com.nmp90.chitankainfo.mvp.views.BookView;
import com.nmp90.chitankainfo.ui.dialogs.DownloadDialog;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookDetailsActivity extends BaseActivity implements HasComponent<PresenterComponent>,BookView {

    @Inject
    BookPresenter bookPresenter;

    @Bind(R.id.iv_cover)
    ImageView ivCover;

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.tv_authors)
    TextView tvAuthors;

    @Bind(R.id.tv_category)
    TextView tvCategory;

    @Bind(R.id.tv_year)
    TextView tvYear;

    @Bind(R.id.tv_description)
    TextView tvDescription;

    @Bind(R.id.container_book)
    NestedScrollView containerBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getComponent().inject(this);
        bookPresenter.setView(this);

        int bookId = getIntent().getIntExtra(Constants.EXTRA_BOOK_ID, 0);
        bookPresenter.loadBooksDetails(bookId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public PresenterComponent getComponent() {
        return DaggerPresenterComponent.builder().presenterModule(new PresenterModule()).applicationComponent(getApplicationComponent()).build();
    }

    @Override
    public void presentBookDetails(BookDetails bookDetails) {
        Book book = bookDetails.getBook();
        setTitle(book.getTitle());

        tvTitle.setText(book.getTitle());
        tvYear.setText(book.getYear() == 0 ? "" : (book.getYear() + ""));
        tvAuthors.setText(book.getTitleAuthor());
        tvDescription.setText(book.getAnnotation());
        tvCategory.setText(book.getCategory().getName());
        Glide.with(this).load(book.getCover()).crossFade().placeholder(R.drawable.ic_no_cover).into(ivCover);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> DownloadDialog.newInstance(book).show(getSupportFragmentManager(), DownloadDialog.TAG));
    }

    @Override
    public void showError() {
        Snackbar.make(containerBook, "Възникна проблем със зареждането на книга!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}