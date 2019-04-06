package info.chitanka.app.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.chitanka.app.R;
import rx.subjects.PublishSubject;

/**
 * Created by nmp on 16-3-22.
 */
public class DownloadActionsAdapter extends RecyclerView.Adapter<DownloadActionsAdapter.ViewHolder> {
    private List<String> formats = new ArrayList<>();
    private PublishSubject<String> onClick = PublishSubject.create();

    public DownloadActionsAdapter(List<String> formats) {
        this.formats = formats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_download, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.btnFlat.setOnClickListener(view1 -> {
            if (viewHolder.getAdapterPosition() == RecyclerView.NO_POSITION) {
                return;
            }

            String format = formats.get(viewHolder.getAdapterPosition());
            onClick.onNext(format);
        });
        return viewHolder;
    }

    public rx.Observable<String> getOnDownloadClick() {
        return onClick.asObservable();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String format = formats.get(position);
        holder.btnFlat.setText(format);
    }

    @Override
    public int getItemCount() {
        return formats.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_download)
        TextView btnFlat;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

