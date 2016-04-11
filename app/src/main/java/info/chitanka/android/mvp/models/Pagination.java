package info.chitanka.android.mvp.models;

/**
 * Created by joro on 16-3-20.
 */
public class Pagination {
    private int page, countPerPage, totalCount, pageCount, nextPage;

    public int getPage() {
        return page;
    }

    public int getCountPerPage() {
        return countPerPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getNextPage() {
        return nextPage;
    }
}
