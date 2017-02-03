package info.chitanka.android.mvp.presenters.my;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import info.chitanka.android.mvp.presenters.BasePresenter;
import info.chitanka.android.mvp.views.MyLibraryView;
import info.chitanka.android.utils.FileUtils;

/**
 * Created by joro on 23.01.17.
 */

public class MyLibraryPresenterImpl extends BasePresenter<MyLibraryView> implements MyLibraryPresenter {

    public MyLibraryPresenterImpl() {
    }

    @Override
    public void onStart() {
        getView().requestPermissionFromUser();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void setView(MyLibraryView view) {
        this.view = new WeakReference<MyLibraryView>(view);
    }

    @Override
    public void readFiles() {
        if (viewExists()) {
            getView().displayFilesList(Arrays.asList(FileUtils.listChitankaFiles()));
        }
    }
}
