package info.chitanka.android.ui.fragments.my;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.folioreader.activity.FolioActivity;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.chitanka.android.R;
import info.chitanka.android.di.presenters.PresenterComponent;
import info.chitanka.android.mvp.presenters.my.MyLibraryPresenter;
import info.chitanka.android.mvp.views.MyLibraryView;
import info.chitanka.android.ui.adapters.FilesAdapter;
import info.chitanka.android.ui.fragments.BaseFragment;
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

    @Bind(R.id.container)
    RelativeLayout container;

    @Bind(R.id.rv_files)
    RecyclerView rvFiles;

    @Bind(R.id.container_empty)
    RelativeLayout containerEmpty;

    @Bind(R.id.tv_empty)
    TextView tvNoFiles;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(PresenterComponent.class).inject(this);
        presenter.setView(this);
        presenter.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_library, container, false);
        ButterKnife.bind(this, view);
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        presenter.onDestroy();
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
        } else {
            containerEmpty.setVisibility(View.GONE);
            rvFiles.setVisibility(View.VISIBLE);
        }
        FilesAdapter adapter = new FilesAdapter(files);
        adapter.getOnFileClick().compose(bindToLifecycle()).subscribe(file -> {
            Intent intent = new Intent(getActivity(), FolioActivity.class);
            intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_TYPE, FolioActivity.EpubSourceType.SD_CARD);
            intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_PATH, file.getAbsolutePath());
            startActivity(intent);
        });

        rvFiles.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyLibraryFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
