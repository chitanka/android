package info.chitanka.android.mvp.presenters.newest;

import info.chitanka.android.mvp.presenters.BaseViewPresenter;
import info.chitanka.android.mvp.views.NewBooksAndTextWorksView;

/**
 * Created by joro on 23.01.17.
 */

public interface NewBooksAndTextWorksPresenter extends BaseViewPresenter<NewBooksAndTextWorksView> {
    void loadNewBooksAndTextworks();
}
