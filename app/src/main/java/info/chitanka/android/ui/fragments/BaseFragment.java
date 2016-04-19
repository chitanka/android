package info.chitanka.android.ui.fragments;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import info.chitanka.android.di.HasComponent;
import info.chitanka.android.mvp.views.BaseView;

/**
 * Created by joro on 16-3-8.
 */
public abstract class BaseFragment extends Fragment implements BaseView {

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    @Override
    public void showError() {
        View view = getView();
        if(view == null) {
            Toast.makeText(getActivity(), "Възникна проблем със зареждането на данните!", Toast.LENGTH_LONG).show();
        } else {
            Snackbar.make(getView(), "Възникна проблем със зареждането на данните!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public abstract String getTitle();
}
