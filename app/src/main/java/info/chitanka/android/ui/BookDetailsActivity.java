package info.chitanka.android.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;

import javax.inject.Inject;

import info.chitanka.android.Constants;
import info.chitanka.android.R;
import info.chitanka.android.TrackingConstants;
import info.chitanka.android.components.AnalyticsService;
import info.chitanka.android.databinding.ActivityBookDetailsBinding;
import info.chitanka.android.di.HasComponent;
import info.chitanka.android.di.presenters.DaggerPresenterComponent;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.mvp.models.Book;
import info.chitanka.android.mvp.models.BookDetails;
import info.chitanka.android.mvp.presenters.book.BookPresenter;
import info.chitanka.android.mvp.views.BookView;
import info.chitanka.android.ui.dialogs.DownloadDialog;
import info.chitanka.android.ui.fragments.DownloadFilePermissionsFragment;
import info.chitanka.android.utils.IntentUtils;

public class BookDetailsActivity extends BaseActivity implements HasComponent<PresenterComponent>, BookView {

    @Inject
    BookPresenter bookPresenter;

    @Inject
    AnalyticsService analyticsService;

    private PresenterComponent presenterComponent;
    private ActivityBookDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_details);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        presenterComponent = DaggerPresenterComponent.builder().applicationComponent(getApplicationComponent()).build();
        getComponent().inject(this);

        bookPresenter.onStart();
        bookPresenter.setView(this);

        int bookId = getIntent().getIntExtra(Constants.EXTRA_BOOK_ID, 0);
        bookPresenter.loadBooksDetails(bookId);
        analyticsService.logEvent(TrackingConstants.View_BOOK_DETAILS, new HashMap<String, String>() {{ put("bookId", String.valueOf(bookId));}});
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
        bookPresenter.onDestroy();
    }

    @Override
    public PresenterComponent getComponent() {
        return presenterComponent;
    }

    @Override
    public void presentBookDetails(BookDetails bookDetails) {
        Book book = bookDetails.getBook();
        setTitle(getText(book.getTitle()));
        binding.setBook(book);
        binding.containerBook.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View view = v.getChildAt(v.getChildCount() - 1);
                int diff = (view.getBottom()-(v.getHeight()+v.getScrollY()));

                // if diff is zero, then the bottom has been reached
                if(diff <= 0 && binding.fabMenu.getVisibility() == View.VISIBLE) {
                    binding.fabMenu.setVisibility(View.INVISIBLE);
                } else if (binding.fabMenu.getVisibility() != View.VISIBLE) {
                    binding.fabMenu.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.btnDownload.setOnClickListener(view -> {
            analyticsService.logEvent(TrackingConstants.DOWNLOAD_DETAILS_FILE, new HashMap<String, String>(){{
                put("fileName", book.getTitle());
            }});
            DownloadDialog.newInstance(book.getTitle(), book.getDownloadUrl(), book.getFormats()).show(getSupportFragmentManager(), DownloadDialog.TAG);
        });

        binding.btnRead.setOnClickListener(view -> {
            analyticsService.logEvent(TrackingConstants.READ_DETAILS_FILE, new HashMap<String, String>(){{
                put("fileName", book.getTitle());
            }});
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, DownloadFilePermissionsFragment.newInstance(book.getReadingFileName(), String.format(book.getDownloadUrl(), "epub")), DownloadFilePermissionsFragment.TAG)
                    .commit();
        });

        binding.btnWeb.setOnClickListener(view -> {
            analyticsService.logEvent(TrackingConstants.VIEW_WEB_DETAILS_FILE, new HashMap<String, String>(){{
                put("fileName", book.getTitle());
            }});
            IntentUtils.openWebUrl(book.getWebChitankaUrl(), this);
        });
    }



    private String getText(String text) {
        if(TextUtils.isEmpty(text)) {
            return "";
        }

        return text;
    }

    @Override
    public boolean isActive() {
        return !isFinishing();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {
        Snackbar.make(binding.containerBook, "Възникна проблем със зареждането на книга!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }
}