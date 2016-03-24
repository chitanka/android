package com.nmp90.chitankainfo.mvp.presenters.book;

import com.nmp90.chitankainfo.mvp.presenters.BaseViewPresenter;
import com.nmp90.chitankainfo.mvp.views.BookView;

/**
 * Created by nmp on 16-3-24.
 */
public interface BookPresenter extends BaseViewPresenter<BookView> {
    void loadBooksDetails(int id);
}
