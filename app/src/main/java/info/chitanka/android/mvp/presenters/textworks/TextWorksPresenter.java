package info.chitanka.android.mvp.presenters.textworks;

import info.chitanka.android.mvp.presenters.BaseViewPresenter;
import info.chitanka.android.mvp.views.TextWorksView;

/**
 * Created by nmp on 21.01.17.
 */

public interface TextWorksPresenter extends BaseViewPresenter<TextWorksView> {
    void searchTextWorks(String searchTerm);
    void getAuthorTextWorks(String authorSlug);
}
