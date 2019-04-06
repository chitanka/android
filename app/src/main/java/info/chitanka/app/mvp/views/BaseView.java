package info.chitanka.app.mvp.views;

/**
 * Created by nmp on 16-3-8.
 */
public interface BaseView {
    boolean isActive();
    void hideLoading();
    void showLoading();
    void showError();
}
