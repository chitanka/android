package info.chitanka.app.mvp.presenters.authors;

import info.chitanka.app.mvp.presenters.BaseViewPresenter;
import info.chitanka.app.mvp.views.AuthorsView;

/**
 * Created by nmp on 16-3-11.
 */
public interface AuthorsPresenter extends BaseViewPresenter<AuthorsView> {
    void loadAuthors(int page, int pageSize);
    void searchAuthors(String query);
}
