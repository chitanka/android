package info.chitanka.android.mvp.presenters.authors;

import info.chitanka.android.mvp.presenters.BaseViewPresenter;
import info.chitanka.android.mvp.views.AuthorsView;

/**
 * Created by nmp on 16-3-11.
 */
public interface AuthorsPresenter extends BaseViewPresenter<AuthorsView> {
    void searchAuthors(String name);
}
