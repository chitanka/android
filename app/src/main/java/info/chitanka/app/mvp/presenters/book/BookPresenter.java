package info.chitanka.app.mvp.presenters.book;

import info.chitanka.app.mvp.presenters.BaseViewPresenter;
import info.chitanka.app.mvp.views.BookView;

/**
 * Created by nmp on 16-3-24.
 */
public interface BookPresenter extends BaseViewPresenter<BookView> {
    void loadBooksDetails(int id);
}
