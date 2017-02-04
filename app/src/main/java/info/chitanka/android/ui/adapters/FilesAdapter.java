package info.chitanka.android.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.chitanka.android.R;
import rx.subjects.PublishSubject;

/**
 * Created by joro on 03.02.17.
 */

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {
    private List<File> files;
    private PublishSubject<File> onFileClick = PublishSubject.create();

    public FilesAdapter(List<File> files) {
        this.files = files;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_file, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.llContainer.setOnClickListener(view1 -> {
            File file = files.get(viewHolder.getAdapterPosition());
            onFileClick.onNext(file);
        });

        return viewHolder;
    }

    public rx.Observable<File> getOnFileClick() {
        return onFileClick.asObservable();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvFileName.setText(files.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.container)
        LinearLayout llContainer;

        @Bind(R.id.tv_file_name)
        TextView tvFileName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
