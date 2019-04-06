package info.chitanka.app.ui.views.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by nmp on 16-3-22.
 */

public class EndlessRecyclerScrollListener extends RecyclerView.OnScrollListener {
    private final LinearLayoutManager layoutManager;
    private OnEndReachedListener listener;

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 15; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }

        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            loading = true;
            listener.onEndReached();
        }
    }

    public EndlessRecyclerScrollListener(OnEndReachedListener listener, LinearLayoutManager layoutManager) {
        this.listener = listener;
        this.layoutManager = layoutManager;
    }

    public void setOnEndReachedListener(OnEndReachedListener listener) {
        this.listener = listener;
    }

    public void reset() {
        previousTotal = 0;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void setVisibleThreshold(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }
}
