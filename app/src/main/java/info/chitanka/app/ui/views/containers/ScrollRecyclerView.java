package info.chitanka.app.ui.views.containers;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.chitanka.app.R;
import info.chitanka.app.ui.views.listeners.EndlessRecyclerScrollListener;
import info.chitanka.app.ui.views.listeners.OnEndReachedListener;

/**
 * Created by nmp on 16-3-22.
 */
public class ScrollRecyclerView extends LinearLayout {
    private View view;
    private LayoutInflater inflater;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private int totalItemsCount = -1;
    private OnEndReachedListener listener;
    private EndlessRecyclerScrollListener scrollListener;
    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, view);
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
        unbinder.unbind();
    }

    public EndlessRecyclerScrollListener getScrollListener() {
        return scrollListener;
    }

    public void setLayoutManager(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        recyclerView.setLayoutManager(layoutManager);
    }
}
