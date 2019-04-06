package info.chitanka.app.mvp.views;

import info.chitanka.app.mvp.models.Authors;

/**
 * Created by nmp on 16-3-11.
 */
public interface AuthorsView extends BaseView {
    void presentAuthors(Authors authors);
    void presentSearch(Authors authors);
}
