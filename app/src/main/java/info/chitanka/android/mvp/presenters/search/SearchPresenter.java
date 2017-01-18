package info.chitanka.android.mvp.presenters.search;

import info.chitanka.android.mvp.presenters.BaseViewPresenter;
import info.chitanka.android.mvp.views.SearchView;

/**
 * Created by nmp on 16-3-8.
 */
public interface SearchPresenter extends BaseViewPresenter<SearchView> {
    void search(String q);
}
