package info.chitanka.app.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

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
