package info.chitanka.app.ui.fragments.my;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.chitanka.app.R;
import info.chitanka.app.TrackingConstants;
import info.chitanka.app.components.AnalyticsService;
import info.chitanka.app.di.presenters.PresenterComponent;
import info.chitanka.app.mvp.presenters.my.MyLibraryPresenter;
import info.chitanka.app.mvp.views.MyLibraryView;
import info.chitanka.app.ui.adapters.FilesAdapter;
import info.chitanka.app.ui.fragments.BaseFragment;
import info.chitanka.app.utils.FileUtils;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by joro on 29.01.17.
 */
@RuntimePermissions
public class MyLibraryFragment extends BaseFragment implements MyLibraryView{
    public static final String TAG = MyLibraryFragment.class.getSimpleName();

    public static MyLibraryFragment newInstance() {

        Bundle args = new Bundle();

        MyLibraryFragment fragment = new MyLibraryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    MyLibraryPresenter presenter;

    @Inject
    AnalyticsService analyticsService;

    @BindView(R.id.container)
    RelativeLayout container;

    @BindView(R.id.rv_files)
    RecyclerView rvFiles;

    @BindView(R.id.container_empty)
    RelativeLayout containerEmpty;

    @BindView(R.id.tv_empty)
    TextView tvNoFiles;

    private FilesAdapter adapter;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(PresenterComponent.class).inject(this);
        analyticsService.logEvent(TrackingConstants.VIEW_FILES);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.startPresenting();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stopPresenting();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_library, container, false);
        unbinder = ButterKnife.bind(this, view);
        rvFiles.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_my_lib, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh) {
            presenter.readFiles();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (getString(R.string.delete).equals(item.getTitle())) {
            File file = adapter.getFile(adapter.getPosition());
            FileUtils.deleteFile(file);
            presenter.readFiles();
            analyticsService.logEvent(TrackingConstants.DELETE_FILE);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @NeedsPermission(value = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void readFiles() {
        presenter.readFiles();
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
        Snackbar.make(container, getString(R.string.storage_denied), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public String getTitle() {
        return TAG;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void requestPermissionFromUser() {
        MyLibraryFragmentPermissionsDispatcher.readFilesWithCheck(this);
    }

    @Override
    public void displayFilesList(List<File> files) {
        if (files == null || files.size() == 0) {
            rvFiles.setVisibility(View.GONE);
            containerEmpty.setVisibility(View.VISIBLE);
            tvNoFiles.setText(R.string.my_lib_no_files);
            return;
        } else {
            containerEmpty.setVisibility(View.GONE);
            rvFiles.setVisibility(View.VISIBLE);
        }
        adapter = new FilesAdapter(files);
        adapter.getOnFileClick().compose(bindToLifecycle()).subscribe(file -> {
            analyticsService.logEvent(TrackingConstants.VIEW_FILE, new HashMap<String, String>() {{
                put("file", file.getName());
            }});


            presenter.readBook(file.getAbsolutePath());
        });

        rvFiles.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyLibraryFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
