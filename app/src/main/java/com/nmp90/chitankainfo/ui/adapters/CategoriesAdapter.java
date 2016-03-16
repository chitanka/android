package com.nmp90.chitankainfo.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.mvp.models.Category;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = categories.get(position);

        holder.tvCategoryName.setText(category.getName());
        holder.tvBooksCount.setText(String.format("%s", category.getNrOfBooks()));
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
        @Bind(R.id.tv_name)
        TextView tvCategoryName;

        @Bind(R.id.tv_books_count)
        TextView tvBooksCount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
