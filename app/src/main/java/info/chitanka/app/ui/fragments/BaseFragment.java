package info.chitanka.app.ui.fragments;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxFragment;

import info.chitanka.app.R;
import info.chitanka.app.di.HasComponent;
import info.chitanka.app.mvp.views.BaseView;

/**
 * Created by joro on 16-3-8.
 */
public abstract class BaseFragment extends RxFragment implements BaseView {

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
            Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_LONG).show();
        } else {
            Snackbar.make(getView(), R.string.error_loading_data, Snackbar.LENGTH_LONG).show();
        }
    }

    public String getArgument(String key, Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(key)) {
            return savedInstanceState.getString(key);
        }

        if (getArguments() != null && getArguments().containsKey(key)) {
            return getArguments().getString(key);
        }

        return null;
    }

    public abstract String getTitle();
}
