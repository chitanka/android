package info.chitanka.android.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.chitanka.android.Constants;
import info.chitanka.android.R;
import info.chitanka.android.mvp.models.Category;
import info.chitanka.android.mvp.models.SearchTerms;
import info.chitanka.android.ui.BooksActivity;

/**
 * Created by nmp on 16-3-16.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private final List<Category> categories;
    private final Context context;

    public CategoriesAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.containerCategory.setOnClickListener(v -> {
            Category category = categories.get(viewHolder.getAdapterPosition());
            if(category.getNrOfBooks() == 0) {
                Snackbar snackbar = Snackbar
                        .make(viewHolder.containerCategory, "Няма книги в категорията!", Snackbar.LENGTH_SHORT);

                snackbar.show();
            } else {
                Intent intent = new Intent(context, BooksActivity.class);
                intent.putExtra(Constants.EXTRA_SEARCH_TERM, SearchTerms.CATEGORY.toString());
                intent.putExtra(Constants.EXTRA_TITLE, category.getName());
                intent.putExtra(Constants.EXTRA_SLUG, category.getSlug());

                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = categories.get(position);
        int textSize;
        if(category.getLevel() == 1) {
            textSize = 22;
        } else if(category.getLevel() == 2) {
            textSize = 20;
            holder.divider.setVisibility(View.VISIBLE);
        } else {
            textSize = 18;
            holder.divider.setVisibility(View.GONE);
        }

        holder.tvCategoryName.setTextSize(textSize);
        holder.tvCategoryName.setText(category.getName());
        holder.tvBooksCount.setText(String.format("%s", category.getNrOfBooks()));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(category.getLevel() * 40, 0, 0, 0);
        params.addRule(RelativeLayout.LEFT_OF, R.id.tv_books_count);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        holder.tvCategoryName.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        ButterKnife.unbind(this);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.container_category)
        RelativeLayout containerCategory;

        @Bind(R.id.tv_name)
        TextView tvCategoryName;

        @Bind(R.id.tv_books_count)
        TextView tvBooksCount;

        @Bind(R.id.divider)
        View divider;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
