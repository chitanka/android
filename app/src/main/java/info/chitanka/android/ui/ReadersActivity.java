package info.chitanka.android.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.chitanka.android.R;

public class ReadersActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readers);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Четци на книги");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.left)
    public void onAlReaderClick() {
        openInMarket("com.neverland.alreader");
    }

    @OnClick(R.id.right)
    public void onCoolReaderClick() {
        openInMarket("org.coolreader");
    }

    @OnClick(R.id.bottom_left)
    public void onEbookDroidClick() {
        openInMarket("org.ebookdroid");
    }

    @OnClick(R.id.bottom_right)
    public void onFbReader() {
        openInMarket("org.geometerplus.zlibrary.ui.android");
    }


    public void openInMarket(String appPackage) {
        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appPackage));
        marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            marketIntent.setData(Uri.parse("market://details?id=" + appPackage));
            startActivity(marketIntent);
        } catch (android.content.ActivityNotFoundException anfe) {
            marketIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + appPackage));
            startActivity(marketIntent);
        }
    }
}
