package com.nmp90.chitankainfo.mvp.views;

import com.nmp90.chitankainfo.mvp.models.Author;

import java.util.List;

/**
 * Created by nmp on 16-3-11.
 */
public interface AuthorsView extends BaseView {
    void presentAuthors(List<Author> authors);
}
