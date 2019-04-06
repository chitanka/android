package info.chitanka.app.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.chitanka.app.Constants;
import info.chitanka.app.R;
import info.chitanka.app.mvp.models.Author;
import info.chitanka.app.ui.AuthorDetailsActivity;

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
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(v -> {
            if (viewHolder.getAdapterPosition() == RecyclerView.NO_POSITION) {
                return;
            }

            Author author = authors.get(viewHolder.getAdapterPosition());
            Intent intent = new Intent(context, AuthorDetailsActivity.class);
            intent.putExtra(Constants.EXTRA_TITLE, author.getName());
            intent.putExtra(Constants.EXTRA_SLUG, author.getSlug());
            context.startActivity(intent);
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Author author = authors.get(position);
        holder.tvAuthorName.setText(author.getName());
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }

    public void addAll(List<Author> authors) {
        this.authors.addAll(authors);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container_authors)
        LinearLayout cardView;

        @BindView(R.id.tv_author_name)
        TextView tvAuthorName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
