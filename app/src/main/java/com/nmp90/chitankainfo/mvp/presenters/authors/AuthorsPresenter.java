package com.nmp90.chitankainfo.mvp.presenters.authors;

import com.nmp90.chitankainfo.mvp.presenters.BaseViewPresenter;
import com.nmp90.chitankainfo.mvp.views.AuthorsView;

/**
 * Created by nmp on 16-3-11.
 */
public interface AuthorsPresenter extends BaseViewPresenter<AuthorsView> {
    void searchAuthors(String name);
}
