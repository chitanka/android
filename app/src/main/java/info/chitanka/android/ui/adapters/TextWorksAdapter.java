package info.chitanka.android.ui.adapters;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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
import info.chitanka.android.ui.dialogs.DownloadDialog;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by nmp on 21.01.17.
 */

public class TextWorksAdapter extends RecyclerView.Adapter<TextWorksAdapter.ViewHolder> {
    private final Resources res;
    private final FragmentManager fragmentManager;
    private final FragmentActivity activity;
    private List<TextWork> textWorks;
    private PublishSubject<TextWork> onWebClick = PublishSubject.create();

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
            onWebClick.onNext(textWork);
        });
        return viewHolder;
    }

    public Observable<TextWork> getOnWebClick() {
        return onWebClick.asObservable();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextWork textWork = textWorks.get(position);
        holder.bind(textWork, res);
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

        public void bind(TextWork textWork, Resources res) {
            tvTitle.setText(textWork.getTitle());
            tvSubtitle.setText(textWork.getSubtitle());
            tvRating.setText(res.getString(R.string.item_text_work_rating, textWork.getRating(), textWork.getVotes()));
            if (textWork.getAuthors() != null && textWork.getAuthors().size() > 0) {
                tvAuthorName.setText(res.getString(R.string.item_text_work_author, textWork.getAuthors().get(0).getName()));
            }

            if (textWork.getFormats() == null || textWork.getFormats().size() == 0) {
                tvDownload.setVisibility(View.GONE);
            } else {
                tvDownload.setVisibility(View.VISIBLE);
            }
        }
    }
}
