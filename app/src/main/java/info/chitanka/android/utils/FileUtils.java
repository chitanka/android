package info.chitanka.android.utils;

import android.os.Environment;

import java.io.File;

import info.chitanka.android.Constants;

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


    private static boolean isFolderAvailable() {
        File file = new File(getChitankaEpubFolderPath());
        return file.isDirectory();
    }
}
