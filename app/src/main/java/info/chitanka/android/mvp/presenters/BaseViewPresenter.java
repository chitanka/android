package info.chitanka.android.mvp.presenters;

/**
 * Created by nmp on 16-3-15.
 */
public interface BaseViewPresenter<T> {
    void onStart();
    void onDestroy();
    void setView(T view);
}
