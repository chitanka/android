package info.chitanka.android.mvp.presenters.my;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import info.chitanka.android.mvp.presenters.BasePresenter;
import info.chitanka.android.mvp.views.MyLibraryView;
import info.chitanka.android.ui.BookReader;
import info.chitanka.android.utils.FileUtils;

/**
 * Created by joro on 23.01.17.
 */

public class MyLibraryPresenterImpl extends BasePresenter<MyLibraryView> implements MyLibraryPresenter {

    private final BookReader bookReader;

    public MyLibraryPresenterImpl(BookReader bookReader) {
        this.bookReader = bookReader;
    }

    @Override
    public void startPresenting() {
        getView().requestPermissionFromUser();
    }

    @Override
    public void stopPresenting() {

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

    @Override
    public void readBook(String path) {
        bookReader.readBook(path);
    }
}
