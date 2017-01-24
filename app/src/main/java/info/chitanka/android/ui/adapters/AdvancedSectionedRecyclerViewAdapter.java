package info.chitanka.android.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by nmp on 23.01.17.
 */

public abstract class AdvancedSectionedRecyclerViewAdapter<SectionVH extends RecyclerView.ViewHolder, ChildVH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final static int LOWER_16BIT_MASK = 0x0000ffff;

    private final static int SECTION_VIEW_HOLDER = 0x00010000;

    private final static int CHILD_VIEW_HOLDER = 0x00020000;


    public abstract int getGroupCount();

    public abstract int getChildCount(int group);


    public abstract SectionVH onCreateSectionViewHolder(ViewGroup parent, int viewType);

    public abstract ChildVH onCreateChildViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindSectionViewHolder(SectionVH holder, int position, List<Object> payloads);

    public abstract void onBindChildViewHolder(ChildVH holder, int belongingGroup, int position, List<Object> payloads);

    public int getSectionViewHolderType(int position) {
        return 0;
    }

    public int getChildViewHolderType(int position) {
        return 0;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (isSectionViewType(viewType)) {
            return onCreateSectionViewHolder(parent, viewType & ~SECTION_VIEW_HOLDER);
        } else {
            return onCreateChildViewHolder(parent, viewType & ~CHILD_VIEW_HOLDER);
        }

    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {

        int itemViewType = holder.getItemViewType();
        if (isSectionViewType(itemViewType)) {
            onBindSectionViewHolder((SectionVH) holder, getSectionPositionFromAbsPosition(position), payloads);
        } else {
            int belongingGroup = getBelongingGroup(position);
            int childPosition = getChildPositionFromAbsPosition(position);
            onBindChildViewHolder((ChildVH) holder, belongingGroup, childPosition, payloads);
        }

    }

    private int getBelongingGroup(int position) {

        int offset = 0;

        int groupCount = getGroupCount();

        for (int i = 0; i < groupCount; i++) {

            int firstPosition = i + offset;
            int lastPosition = firstPosition + getChildCount(i);

            if (position > firstPosition && position <= lastPosition) {
                return i;
            }

            offset += getChildCount(i);

        }

        return RecyclerView.NO_POSITION;
    }

    private int getChildPositionFromAbsPosition(int position) {
        int offset = 0;

        int groupCount = getGroupCount();

        for (int i = 0; i < groupCount; i++) {

            int firstPosition = i + offset;
            int lastPosition = firstPosition + getChildCount(i);

            if (position > firstPosition && position <= lastPosition) {
                return position - firstPosition - 1;
            }


            offset += getChildCount(i);

        }

        return RecyclerView.NO_POSITION;
    }


    private int getSectionPositionFromAbsPosition(int position) {

        int offset = 0;

        int groupCount = getGroupCount();

        for (int i = 0; i < groupCount; i++) {
            if (position == i + offset) {
                return i;
            }

            offset += getChildCount(i);

        }

        return RecyclerView.NO_POSITION;
    }

    public int getFirstAbsPositionOf(int group) {
        int offset = 0;

        int groupCount = getGroupCount();

        for (int i = 0; i < groupCount; i++) {
            if (group == i) {
                return i + offset;
            }

            offset += getChildCount(i);

        }

        return RecyclerView.NO_POSITION;
    }

    public boolean isSectionViewType(int itemViewType) {
        return (itemViewType & SECTION_VIEW_HOLDER) == SECTION_VIEW_HOLDER;
    }

    @Override
    public int getItemCount() {
        int totalChildItems = 0;

        for (int i = 0; i < getGroupCount(); i++) {
            totalChildItems += getChildCount(i);
        }

        return totalChildItems + getGroupCount();
    }


    protected boolean isFirstInSection(int group, int position) {
        return position == 0;
    }

    protected boolean isLastInSection(int group, int position) {

        return getChildCount(group) == position + 1;


    }

    @Override
    public int getItemViewType(int position) {

        if (isSection(position)) {
            return (getSectionViewHolderType(position) & LOWER_16BIT_MASK) | SECTION_VIEW_HOLDER;
        }

        return (getChildViewHolderType(position) & LOWER_16BIT_MASK) | CHILD_VIEW_HOLDER;
    }

    private boolean isSection(int position) {

        int offset = 0;

        int groupCount = getGroupCount();

        for (int i = 0; i < groupCount; i++) {
            if (position == i + offset) {
                return true;
            }

            offset += getChildCount(i);

        }

        return false;
    }
}
