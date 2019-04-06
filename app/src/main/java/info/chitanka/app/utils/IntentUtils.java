package info.chitanka.app.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import info.chitanka.app.R;

/**
 * Created by nmp on 22.01.17.
 */

public class IntentUtils {
    public static void openWebUrl(String url, Activity activity) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        Intent chooser = Intent.createChooser(sendIntent, activity.getString(R.string.title_open_with));
        if (chooser.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(chooser);
        } else {
            Toast.makeText(activity, activity.getString(R.string.web_no_app), Toast.LENGTH_SHORT).show();
        }
    }

}
