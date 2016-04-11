package info.chitanka.android.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import info.chitanka.android.Constants;
import info.chitanka.android.R;
import info.chitanka.android.di.HasComponent;
import info.chitanka.android.di.presenters.DaggerPresenterComponent;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.di.presenters.PresenterModule;
import info.chitanka.android.mvp.models.Book;
import info.chitanka.android.mvp.models.BookDetails;
import info.chitanka.android.mvp.presenters.book.BookPresenter;
import info.chitanka.android.mvp.views.BookView;
import info.chitanka.android.ui.dialogs.DownloadDialog;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookDetailsActivity extends BaseActivity implements HasComponent<PresenterComponent>, BookView {

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
        setTitle(getText(book.getTitle()));

        tvTitle.setText(getText(book.getTitle()));
        tvYear.setText(book.getYear() == 0 ? "" : (book.getYear() + ""));
        tvAuthors.setText(getText(book.getTitleAuthor()));
        tvDescription.setText(getText(book.getAnnotation()));
        tvCategory.setText(getText(book.getCategory().getName()));
        Glide.with(this).load(book.getCover()).crossFade().placeholder(R.drawable.ic_no_cover).into(ivCover);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> DownloadDialog.newInstance(book).show(getSupportFragmentManager(), DownloadDialog.TAG));
    }

    private String getText(String text) {
        if(TextUtils.isEmpty(text)) {
            return "";
        }

        return text;
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {
        Snackbar.make(containerBook, "Възникна проблем със зареждането на книга!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}