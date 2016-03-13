package com.nmp90.chitankainfo.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmp90.chitankainfo.Constants;
import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.mvp.models.Author;
import com.nmp90.chitankainfo.ui.AuthorBooksActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nmp on 16-3-13.
 */
public class AuthorsAdapter extends RecyclerView.Adapter<AuthorsAdapter.ViewHolder> {
    private final Context context;
    private List<Author> authors = new ArrayList<>();

    public AuthorsAdapter(Context context, List<Author> authors) {
        this.context = context;
        this.authors = authors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_author, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Author author = authors.get(position);
        holder.tvAuthorCountry.setText(author.getCountry());
        holder.tvAuthorName.setText(author.getName());
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AuthorBooksActivity.class);
            intent.putExtra(Constants.EXTRA_AUTHOR_NAME, author.getName());
            intent.putExtra(Constants.EXTRA_AUTHOR_LINK, author.getLink());
           context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.card_view)
        CardView cardView;

        @Bind(R.id.tv_author_country)
        TextView tvAuthorCountry;

        @Bind(R.id.tv_author_name)
        TextView tvAuthorName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
