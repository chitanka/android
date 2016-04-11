package info.chitanka.android.mvp.presenters.book;

import info.chitanka.android.mvp.presenters.BaseViewPresenter;
import info.chitanka.android.mvp.views.BookView;

/**
 * Created by nmp on 16-3-24.
 */
public interface BookPresenter extends BaseViewPresenter<BookView> {
    void loadBooksDetails(int id);
}
