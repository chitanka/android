package com.nmp90.chitankainfo.mvp.views;

import com.nmp90.chitankainfo.mvp.models.BookDetails;

/**
 * Created by nmp on 16-3-24.
 */
public interface BookView extends BaseView {
    void presentBookDetails(BookDetails book);
}
