package info.chitanka.android.ui.adapters;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.chitanka.android.R;
import info.chitanka.android.mvp.models.TextWork;
import info.chitanka.android.ui.dialogs.DownloadDialog;

/**
 * Created by nmp on 21.01.17.
 */

public class TextWorksAdapter extends RecyclerView.Adapter<TextWorksAdapter.ViewHolder> {
    private final Resources res;
    private final FragmentManager fragmentManager;
    private final FragmentActivity activity;
    private List<TextWork> textWorks;

    public TextWorksAdapter(List<TextWork> textWorks, FragmentActivity activity) {
        this.textWorks = textWorks;
        this.activity = activity;
        this.res = activity.getResources();
        this.fragmentManager = activity.getSupportFragmentManager();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_textwork, null, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvDownload.setOnClickListener(view1 -> {
            TextWork textWork = textWorks.get(viewHolder.getAdapterPosition());
            DownloadDialog
                    .newInstance(textWork.getTitle(), textWork.getDownloadUrl(), textWork.getFormats())
                    .show(fragmentManager, DownloadDialog.TAG);
        });

        viewHolder.tvWeb.setOnClickListener(view1 -> {
            TextWork textWork = textWorks.get(viewHolder.getAdapterPosition());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(textWork.getChitankaUrl()));
            Intent chooser = Intent.createChooser(intent, res.getString(R.string.title_open_with));
            if (chooser.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
            } else {
                Toast.makeText(activity, activity.getString(R.string.download_no_app), Toast.LENGTH_SHORT).show();
            }

        });
        return viewHolder;
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

        if (textWork.getFormats() == null || textWork.getFormats().size() == 0) {
            holder.tvDownload.setVisibility(View.GONE);
        } else {
            holder.tvDownload.setVisibility(View.VISIBLE);
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

        @Bind(R.id.tv_web)
        TextView tvWeb;

        @Bind(R.id.tv_download)
        TextView tvDownload;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}