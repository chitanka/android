package info.chitanka.android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by nmp on 24.01.17.
 */

public class DateUtils {

    public static String getReadableDateWithTime(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date newDate = formatter.parse(date);

            return new SimpleDateFormat("EEE, dd MMM, yyyy", Locale.getDefault()).format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Timber.e(e, "Date parse!");
            return "";
        }
    }

}
