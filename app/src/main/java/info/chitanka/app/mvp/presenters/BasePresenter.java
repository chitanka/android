package info.chitanka.app.mvp.presenters;

import java.lang.ref.WeakReference;

import info.chitanka.app.mvp.views.BaseView;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by nmp on 16-3-11.
 */
public abstract class BasePresenter<T extends BaseView> {

    protected  WeakReference<T> view;
    protected CompositeSubscription compositeSubscription;

    protected boolean viewExists() {
        return view != null && view.get() != null && view.get().isActive();
    }

    protected T getView() {
        return view.get();
    }
}
