package info.chitanka.android.mvp.presenters.search;

import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.mvp.presenters.BasePresenter;
import info.chitanka.android.mvp.views.SearchView;

/**
 * Created by nmp on 18.01.17.
 */

public class SearchPresenterImpl extends BasePresenter<SearchView> implements SearchPresenter {

    private final ChitankaApi chitankaApi;

    public SearchPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void search(String q) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void setView(SearchView view) {

    }
}
