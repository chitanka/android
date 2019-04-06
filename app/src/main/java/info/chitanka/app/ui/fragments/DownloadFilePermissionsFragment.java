package info.chitanka.app.ui.fragments;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.chitanka.app.Constants;
import info.chitanka.app.R;
import info.chitanka.app.utils.FileUtils;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import timber.log.Timber;

/**
 * Created by nmp on 04.02.17.
 */

@RuntimePermissions
public class DownloadFilePermissionsFragment extends Fragment {
    public static final String TAG = DownloadFilePermissionsFragment.class.getSimpleName();
    private static final String KEY_FILE_DOWNLOADED = "file_downloaded";

    private String url;
    private String title;

    @BindView(R.id.container)
    LinearLayout llContainer;

    private Unbinder unbinder;

    public static DownloadFilePermissionsFragment newInstance(String title, String url) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_FILE_URL, url);
        args.putString(Constants.EXTRA_FILE_NAME, title);
        DownloadFilePermissionsFragment fragment = new DownloadFilePermissionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString(Constants.EXTRA_FILE_URL);
        title = getArguments().getString(Constants.EXTRA_FILE_NAME);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_file_permissions, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (FileUtils.isExternalStorageWritable()) {
            DownloadFilePermissionsFragmentPermissionsDispatcher.downloadFileWithCheck(this);
        } else {
            Snackbar.make(llContainer, getString(R.string.storage_denied), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @NeedsPermission(value = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void downloadFile() {
        FileUtils.downloadFile(title, url, getActivity());
        Toast.makeText(getActivity(), R.string.downloading, Toast.LENGTH_SHORT).show();
        try {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForLocation(final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.message_storage_needed)
                .setPositiveButton(R.string.button_allow, (dialog, button) -> request.proceed())
                .setNegativeButton(R.string.button_deny, (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDeniedForLocation() {
        Snackbar.make(llContainer, getString(R.string.storage_denied), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        DownloadFilePermissionsFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
