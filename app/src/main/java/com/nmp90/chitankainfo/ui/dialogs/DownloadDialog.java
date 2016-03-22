package com.nmp90.chitankainfo.ui.dialogs;

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

import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.mvp.models.Book;
import com.nmp90.chitankainfo.ui.adapters.DownloadActionsAdapter;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nmp on 16-3-22.
 */
public class DownloadDialog extends DialogFragment {

    public static final String TAG = DownloadDialog.class.getName();
    private static final String KEY_BOOK = "key_book";

    private AppCompatActivity activity;

    private Book book;

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.container_actions)
    RecyclerView rvContainerActions;

    public static DownloadDialog newInstance(Book book) {

        Bundle args = new Bundle();
        args.putParcelable(KEY_BOOK, Parcels.wrap(book));
        DownloadDialog fragment = new DownloadDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        book = Parcels.unwrap(getArguments().getParcelable(KEY_BOOK));
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
        rvContainerActions.setAdapter(new DownloadActionsAdapter(activity, book));
        tvTitle.setText(book.getTitle());

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
