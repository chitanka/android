package info.chitanka.app.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import info.chitanka.app.R;
import info.chitanka.app.ui.dialogs.DownloadDialog;
import info.chitanka.app.ui.fragments.DownloadFilePermissionsFragment;

/**
 * Created by nmp on 04.02.17.
 */

public class BindingAdapters {

    @BindingAdapter({"downloadTitle", "downloadUrl", "downloadFormats", "context"})
    public static void displayDownloadDialog(TextView textView, String title, String url, ArrayList<String> formats, Context context) {
        textView.setOnClickListener(null);
        textView.setOnClickListener(view -> DownloadDialog.newInstance(title, url, formats).show(((FragmentActivity)context).getSupportFragmentManager(), DownloadDialog.TAG));
    }

    @BindingAdapter({"downloadTitle", "downloadUrl", "context"})
    public static void displayDownloadDialog(TextView textView, String title, String url, Context context) {
        textView.setOnClickListener(null);
        textView.setOnClickListener(view -> {
            FragmentManager supportFragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            Fragment downloadFragment = supportFragmentManager.findFragmentByTag(DownloadFilePermissionsFragment.TAG);
            if (downloadFragment == null) {
                supportFragmentManager
                        .beginTransaction()
                        .add(android.R.id.content, DownloadFilePermissionsFragment.newInstance(title, url), DownloadFilePermissionsFragment.TAG)
                        .commit();
            }
        });
    }

    @BindingAdapter({"imageUrl", "context"})
    public static void loadImageUrl(ImageView imageView, String imageUrl, Context context) {
        Glide.with(context).load(imageUrl).fitCenter().crossFade().placeholder(R.drawable.ic_no_cover).into(imageView);
    }
}
