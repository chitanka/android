package info.chitanka.app.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import java.io.File;

import info.chitanka.app.Constants;
import info.chitanka.app.ui.services.DownloadService;

/**
 * Created by joro on 03.02.17.
 */

public class FileUtils {
    public static String getChitankaEpubFolderPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + Constants.CHITANKA_FOLDER_ROOT;
    }

    public static File[] listChitankaFiles() {
        String path = getChitankaEpubFolderPath();
        File directory = new File(path);
        boolean createdDirectory = true;
        if (!directory.exists()) {
            createdDirectory = directory.mkdir();
        }

        if (!createdDirectory) {
            return new File[] {};
        }

        return directory.listFiles();
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static void downloadFile(String title, String url, Context context) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(Constants.EXTRA_FILE_URL, url);
        intent.putExtra(Constants.EXTRA_FILE_NAME, title);
        intent.putExtra(Constants.EXTRA_FOLDER_PATH, FileUtils.getChitankaEpubFolderPath());
        context.startService(intent);
    }

    public static boolean deleteFile(File file) {
        if (file != null && file.exists()) {
            return file.delete();
        }
        return false;
    }
}
