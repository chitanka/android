package info.chitanka.android.mvp.views;

import java.io.File;
import java.util.List;

/**
 * Created by joro on 29.01.17.
 */

public interface MyLibraryView extends BaseView {
    void requestPermissionFromUser();
    void displayFilesList(List<File> fileNames);
}
