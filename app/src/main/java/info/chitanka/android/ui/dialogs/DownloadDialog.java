package info.chitanka.android.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.chitanka.android.R;
import info.chitanka.android.TrackingConstants;
import info.chitanka.android.components.AnalyticsService;
import info.chitanka.android.di.HasComponent;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.ui.adapters.DownloadActionsAdapter;
import info.chitanka.android.utils.FileUtils;
import rx.Subscription;

/**
 * Created by nmp on 16-3-22.
 */
public class DownloadDialog extends DialogFragment {

    public static final String TAG = DownloadDialog.class.getName();
    private static final String KEY_TITLE = "title";
    private static final String KEY_FORMATS = "formats";
    private static final String KEY_DOWNLOAD_URL = "download_url";

    @Inject
    AnalyticsService analyticsService;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.container_actions)
    RecyclerView rvContainerActions;

    private String title;
    private String downloadUrl;
    private ArrayList<String> formats;
    private AppCompatActivity activity;
    private Subscription subscription;
    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, view);
        builder.setView(view);
        rvContainerActions.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        DownloadActionsAdapter adapter = new DownloadActionsAdapter(formats);
        subscription = adapter.getOnDownloadClick().subscribe(format -> {

            try {
                downloadWithService(format);
                analyticsService.logEvent(TrackingConstants.NO_ACTIVITY_DOWNLOAD);
            } catch (Exception e) {
                downloadWithExternalApp(format);
            }

            analyticsService.logEvent(TrackingConstants.CLICK_DOWNLOAD_BOOK, new HashMap<String, String>() {{
                put("title", title);
                put("format", format);
            }});

            DownloadDialog.this.dismiss();
            Toast.makeText(activity, getString(R.string.downloading), Toast.LENGTH_SHORT).show();
        });
        rvContainerActions.setAdapter(adapter);
        tvTitle.setText(title);

        return builder.create();
    }

    private void downloadWithService(String format) {
        FileUtils.downloadFile(title, String.format(downloadUrl, format), getActivity());
    }

    private void downloadWithExternalApp(String format) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(downloadUrl, format)));
        Intent chooser = Intent.createChooser(intent, activity.getString(R.string.title_download));
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(chooser);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (AppCompatActivity) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((HasComponent<PresenterComponent>) getActivity()).getComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
        unbinder.unbind();
    }
}
