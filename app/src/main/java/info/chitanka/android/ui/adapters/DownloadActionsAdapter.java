package info.chitanka.android.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.chitanka.android.R;

/**
 * Created by nmp on 16-3-22.
 */
public class DownloadActionsAdapter extends RecyclerView.Adapter<DownloadActionsAdapter.ViewHolder> {
    private final Context context;
    private final String downloadUrl;
    private List<String> formats = new ArrayList<>();

    public DownloadActionsAdapter(Context context, String downloadUrl, List<String> formats) {
        this.context = context;
        this.formats = formats;
        this.downloadUrl = downloadUrl;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_download, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.btnFlat.setOnClickListener(view1 -> {
            String format = formats.get(viewHolder.getAdapterPosition());
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, Uri.parse(String.format(downloadUrl, format)));
            Intent chooser = Intent.createChooser(intent, context.getString(R.string.title_download));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(chooser);
            } else {

            }
        });
        return viewHolder;
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

        @Bind(R.id.btn_download)
        TextView btnFlat;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

