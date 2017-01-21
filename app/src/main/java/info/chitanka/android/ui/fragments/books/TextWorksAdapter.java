package info.chitanka.android.ui.fragments.books;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.chitanka.android.R;
import info.chitanka.android.mvp.models.TextWork;

/**
 * Created by nmp on 21.01.17.
 */

public class TextWorksAdapter extends RecyclerView.Adapter<TextWorksAdapter.ViewHolder> {
    private List<TextWork> textWorks;

    public TextWorksAdapter(List<TextWork> textWorks) {
        this.textWorks = textWorks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_textwork, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(textWorks.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return textWorks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
