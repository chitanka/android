package com.nmp90.chitankainfo.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.mvp.models.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nmp on 16-3-8.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    private final Context context;
    private List<Book> books = new ArrayList<>();

    public BooksAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.tvBookName.setText(book.getTitle());
        holder.tvBookCategory.setText(book.getCategory());
        holder.tvBookAuthor.setText(book.getAuthor());
        Glide.with(context).load(book.getCover()).fitCenter().crossFade().into(holder.ivCover);

        holder.tvShare.setOnClickListener((view) -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, String.format(context.getResources().getString(R.string.share_text), book.getTitle(), book.getAuthor()));
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        });

        holder.tvDownload.setOnClickListener((view) -> {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(book.getDownloadUrl())));
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name)
        TextView tvBookName;

        @Bind(R.id.tv_category)
        TextView tvBookCategory;

        @Bind(R.id.tv_author)
        TextView tvBookAuthor;

        @Bind(R.id.iv_cover)
        ImageView ivCover;

        @Bind(R.id.tv_share)
        TextView tvShare;

        @Bind(R.id.tv_download)
        TextView tvDownload;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
