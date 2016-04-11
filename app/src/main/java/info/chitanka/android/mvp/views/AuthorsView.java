package info.chitanka.android.mvp.views;

import info.chitanka.android.mvp.models.Author;

import java.util.List;

/**
 * Created by nmp on 16-3-11.
 */
public interface AuthorsView extends BaseView {
    void presentAuthors(List<Author> authors);
}
