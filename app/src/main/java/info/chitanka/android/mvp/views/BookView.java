package info.chitanka.android.mvp.views;

import info.chitanka.android.mvp.models.BookDetails;

/**
 * Created by nmp on 16-3-24.
 */
public interface BookView extends BaseView {
    void presentBookDetails(BookDetails book);
}
