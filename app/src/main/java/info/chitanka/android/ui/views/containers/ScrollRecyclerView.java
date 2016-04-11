package info.chitanka.android.ui.views.containers;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import info.chitanka.android.R;
import info.chitanka.android.ui.views.listeners.EndlessRecyclerScrollListener;
import info.chitanka.android.ui.views.listeners.OnEndReachedListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nmp on 16-3-22.
 */
public class ScrollRecyclerView extends LinearLayout {
    private View view;
    private LayoutInflater inflater;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private int totalItemsCount = -1;
    private OnEndReachedListener listener;
    private EndlessRecyclerScrollListener scrollListener;

    public ScrollRecyclerView(Context context) {
        super(context);
        if(!isInEditMode()) {
            init();
        }
    }

    public ScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()) {
            init();
        }
    }

    public ScrollRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode()) {
            init();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if(!isInEditMode()) {
            init();
        }
    }

    private void init() {
        view = getLayoutInflater().inflate(R.layout.view_scroll_recyclerview, this, true);
        ButterKnife.bind(this, view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
    }

    public void smoothScrollToPosition(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    public void setOnEndReachedListener(final OnEndReachedListener listener) {
        this.listener = listener;
    }

    public void setAdapter(RecyclerView.Adapter adapter, int totalItemsCount) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
        this.totalItemsCount = totalItemsCount;

        recyclerView.addOnScrollListener(new EndlessRecyclerScrollListener(new OnEndReachedListener() {
            @Override
            public void onEndReached() {
                if(ScrollRecyclerView.this.totalItemsCount != -1) {
                    if (ScrollRecyclerView.this.adapter.getItemCount() > ScrollRecyclerView.this.totalItemsCount) {
                        return;
                    }
                }
                ScrollRecyclerView.this.listener.onEndReached();
            }
        }, layoutManager));
    }

    public void setAdapter(RecyclerView.Adapter adapter, int totalItemsCount, int visibleThreshold) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
        this.totalItemsCount = totalItemsCount;

        scrollListener = new EndlessRecyclerScrollListener(new OnEndReachedListener() {
            @Override
            public void onEndReached() {
                if (ScrollRecyclerView.this.totalItemsCount != -1) {
                    if (ScrollRecyclerView.this.adapter.getItemCount() >= ScrollRecyclerView.this.totalItemsCount) {
                        return;
                    }
                }
                ScrollRecyclerView.this.listener.onEndReached();
            }
        }, layoutManager);

        scrollListener.setVisibleThreshold(visibleThreshold);
        recyclerView.addOnScrollListener(scrollListener);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public RecyclerView.Adapter getAdapter() {
        return recyclerView.getAdapter();
    }

    public void resetAdapter() {
        adapter = null;
        recyclerView.removeAllViews();
        recyclerView.setAdapter(null);
    }

    protected LayoutInflater getLayoutInflater() {
        if(inflater == null) {
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        return inflater;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ButterKnife.unbind(this);
    }

    public EndlessRecyclerScrollListener getScrollListener() {
        return scrollListener;
    }
}
