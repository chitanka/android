package info.chitanka.android.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import info.chitanka.android.R;
import info.chitanka.android.ui.dialogs.DownloadDialog;

/**
 * Created by nmp on 04.02.17.
 */

public class BindingAdapters {

    @BindingAdapter({"app:downloadTitle", "app:downloadUrl", "app:downloadFormats", "app:context"})
    public static void displayDownloadDialog(TextView textView, String title, String url, ArrayList<String> formats, Context context) {
        textView.setOnClickListener(null);
        textView.setOnClickListener(view -> DownloadDialog.newInstance(title, url, formats).show(((FragmentActivity)context).getSupportFragmentManager(), DownloadDialog.TAG));
    }

    @BindingAdapter({"app:imageUrl", "app:context"})
    public static void loadImageUrl(ImageView imageView, String imageUrl, Context context) {
        Glide.with(context).load(imageUrl).fitCenter().crossFade().placeholder(R.drawable.ic_no_cover).into(imageView);
    }
}
