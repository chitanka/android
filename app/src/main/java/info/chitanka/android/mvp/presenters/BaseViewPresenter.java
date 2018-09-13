package info.chitanka.android.mvp.presenters;

import info.chitanka.android.mvp.views.BaseView;

/**
 * Created by nmp on 16-3-15.
 */
public interface BaseViewPresenter<T extends BaseView> {
    void startPresenting();
    void stopPresenting();
    void setView(T view);
}
