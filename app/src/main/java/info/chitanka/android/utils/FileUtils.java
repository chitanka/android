package info.chitanka.android.utils;

import android.os.Environment;
import android.util.Log;

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

        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "FileName:" + files[i].getName());
        }

        return files;
    }

    private static boolean isFolderAvailable() {
        File file = new File(getChitankaEpubFolderPath());
        return file.isDirectory();
    }
}
