package info.chitanka.android.ui.adapters;

import android.content.res.Resources;
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
    private final Resources res;
    private List<TextWork> textWorks;

    public TextWorksAdapter(List<TextWork> textWorks, Resources res) {
        this.textWorks = textWorks;
        this.res = res;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_textwork, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextWork textWork = textWorks.get(position);
        holder.tvTitle.setText(textWork.getTitle());
        holder.tvSubtitle.setText(textWork.getSubtitle());
        holder.tvRating.setText(res.getString(R.string.item_text_work_rating, textWork.getRating(), textWork.getVotes()));
        if (textWork.getAuthors() != null && textWork.getAuthors().size() > 0) {
            holder.tvAuthorName.setText(res.getString(R.string.item_text_work_author, textWork.getAuthors().get(0).getName()));
        }


    }

    @Override
    public int getItemCount() {
        return textWorks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView tvTitle;

        @Bind(R.id.tv_subtitle)
        TextView tvSubtitle;

        @Bind(R.id.tv_author_name)
        TextView tvAuthorName;

        @Bind(R.id.tv_rating)
        TextView tvRating;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
