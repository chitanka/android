package info.chitanka.android.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.chitanka.android.R;
import info.chitanka.android.ui.adapters.DownloadActionsAdapter;

/**
 * Created by nmp on 16-3-22.
 */
public class DownloadDialog extends DialogFragment {

    public static final String TAG = DownloadDialog.class.getName();
    private static final String KEY_TITLE = "title";
    private static final String KEY_FORMATS = "formats";
    private static final String KEY_DOWNLOAD_URL = "download_url";

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.container_actions)
    RecyclerView rvContainerActions;

    private String title;
    private String downloadUrl;
    private ArrayList<String> formats;
    private AppCompatActivity activity;

    public static DownloadDialog newInstance(String title, String downloadUrl, ArrayList<String> formats) {

        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_DOWNLOAD_URL, downloadUrl);
        args.putStringArrayList(KEY_FORMATS, formats);

        DownloadDialog fragment = new DownloadDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(KEY_TITLE);
        formats = getArguments().getStringArrayList(KEY_FORMATS);
        downloadUrl = getArguments().getString(KEY_DOWNLOAD_URL);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.dialog_download, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        rvContainerActions.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        rvContainerActions.setAdapter(new DownloadActionsAdapter(activity, downloadUrl, formats));
        tvTitle.setText(title);

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (AppCompatActivity) activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
