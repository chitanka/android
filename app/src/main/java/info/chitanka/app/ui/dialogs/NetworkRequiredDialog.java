package info.chitanka.app.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import info.chitanka.app.R;
import timber.log.Timber;

/**
 * Created by nmp on 17.01.17.
 */

public class NetworkRequiredDialog extends DialogFragment {
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.network_required_no_internet)
                .setMessage(R.string.network_required)
                .setPositiveButton(R.string.OK, (dialogInterface, i) -> dismiss());

        return builder.create();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException e) {
            Timber.d(e);
        }
    }
}
