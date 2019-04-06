package info.chitanka.app.mvp.presenters.newest;

import info.chitanka.app.mvp.presenters.BaseViewPresenter;
import info.chitanka.app.mvp.views.NewBooksAndTextWorksView;

/**
 * Created by joro on 23.01.17.
 */

public interface NewBooksAndTextWorksPresenter extends BaseViewPresenter<NewBooksAndTextWorksView> {
    void loadNewBooksAndTextworks();
}
