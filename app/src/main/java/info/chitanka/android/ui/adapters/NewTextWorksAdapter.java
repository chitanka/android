package info.chitanka.android.ui.adapters;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import butterknife.Bind;
import info.chitanka.android.R;
import info.chitanka.android.mvp.models.NewTextWorksResult;

/**
 * Created by nmp on 23.01.17.
 */

public class NewTextWorksAdapter extends AdvancedSectionedRecyclerViewAdapter<NewTextWorksAdapter.SectionViewHolder, TextWorksAdapter.ViewHolder> {

    private final Resources res;
    LinkedTreeMap<String, List<NewTextWorksResult>> map;

    public NewTextWorksAdapter(LinkedTreeMap<String, List<NewTextWorksResult>> map, Resources res) {
        this.map = map;
        this.res = res;
    }

    @Override
    public int getGroupCount() {
        return map.keySet().size();
    }

    @Override
    public int getChildCount(int group) {
        List<NewTextWorksResult> textWorks = getTextWorks(group);
        return textWorks != null ? textWorks.size() : 0;
    }

    private List<NewTextWorksResult> getTextWorks(int keyPosition) {
        int i = 0;
        for (String key : map.keySet()) {
            if (i == keyPosition) {
                return map.get(key);
            }
            i++;
        }

        return null;
    }

    private String getDate(int keyPosition) {
        int i = 0;
        for (String key : map.keySet()) {
            if (i == keyPosition) {
                return key;
            }
            i++;
        }

        return "";
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_date_header, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public TextWorksAdapter.ViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_textwork, parent, false);
        return new TextWorksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder holder, int position, List<Object> payloads) {
        holder.tvHeader.setText(getDate(position));
    }

    @Override
    public void onBindChildViewHolder(TextWorksAdapter.ViewHolder holder, int belongingGroup, int position, List<Object> payloads) {
        NewTextWorksResult newTextWorksResults = getTextWorks(belongingGroup).get(position);
        holder.bind(newTextWorksResults.getText(), res);
    }

    static class SectionViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_header)
        TextView tvHeader;

        public SectionViewHolder(View itemView) {
            super(itemView);
            tvHeader = (TextView) itemView.findViewById(R.id.tv_header);
        }
    }
}
