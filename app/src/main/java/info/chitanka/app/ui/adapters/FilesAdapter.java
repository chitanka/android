package info.chitanka.app.ui.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.chitanka.app.R;
import rx.subjects.PublishSubject;

/**
 * Created by joro on 03.02.17.
 */

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> implements View.OnCreateContextMenuListener {
    private List<File> files;
    private PublishSubject<File> onFileClick = PublishSubject.create();
    private int position;

    public FilesAdapter(List<File> files) {
        this.files = files;
    }

    public int getPosition() {
        return position;
    }

    public File getFile(int position) {
        return files.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_file, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.llContainer.setOnLongClickListener(view12 -> {
            if (viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                this.position = viewHolder.getAdapterPosition();
            }

            return false;
        });
        viewHolder.llContainer.setOnClickListener(view1 -> {
            if (viewHolder.getAdapterPosition() == RecyclerView.NO_POSITION) {
                return;
            }

            File file = files.get(viewHolder.getAdapterPosition());
            onFileClick.onNext(file);
        });
        viewHolder.llContainer.setOnCreateContextMenuListener(this);

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

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        Context context = view.getContext();
        contextMenu.setHeaderTitle(context.getString(R.string.files_delete_title));
        contextMenu.add(0, view.getId(), 0, context.getString(R.string.delete));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)
        LinearLayout llContainer;

        @BindView(R.id.tv_file_name)
        TextView tvFileName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
