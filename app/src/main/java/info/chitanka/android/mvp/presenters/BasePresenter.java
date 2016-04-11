package info.chitanka.android.mvp.presenters;

import java.lang.ref.WeakReference;

/**
 * Created by nmp on 16-3-11.
 */
public abstract class BasePresenter {

    public boolean viewExists(WeakReference reference) {
        return reference != null && reference.get() != null;
    }
}
