package com.nmp90.chitankainfo.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.mvp.models.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nmp on 16-3-22.
 */
public class DownloadActionsAdapter extends RecyclerView.Adapter<DownloadActionsAdapter.ViewHolder> {
    private final Context context;
    private final Book book;
    private List<String> formats = new ArrayList<>();

    public DownloadActionsAdapter(Context context, Book book) {
        this.context = context;
        this.formats = book.getFormats();
        this.book = book;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_download, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String format = formats.get(position);
        holder.btnFlat.setText(format);
        holder.btnFlat.setOnClickListener(v -> context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(book.getDownloadUrl(format)))));
    }

    @Override
    public int getItemCount() {
        return formats.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.btn_download)
        TextView btnFlat;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

