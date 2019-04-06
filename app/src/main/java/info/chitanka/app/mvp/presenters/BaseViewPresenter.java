package info.chitanka.app.mvp.presenters;

import info.chitanka.app.mvp.views.BaseView;

/**
 * Created by nmp on 16-3-15.
 */
public interface BaseViewPresenter<T extends BaseView> {
    void startPresenting();
    void stopPresenting();
    void setView(T view);
}
