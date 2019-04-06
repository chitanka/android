package info.chitanka.app.mvp.presenters.my;

import info.chitanka.app.mvp.presenters.BaseViewPresenter;
import info.chitanka.app.mvp.views.MyLibraryView;

/**
 * Created by joro on 29.01.17.
 */

public interface MyLibraryPresenter extends BaseViewPresenter<MyLibraryView> {
    void readFiles();

    void readBook(String path);
}
