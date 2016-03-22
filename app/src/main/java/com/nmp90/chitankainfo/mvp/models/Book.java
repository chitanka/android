package com.nmp90.chitankainfo.mvp.models;

import com.nmp90.chitankainfo.Constants;

/**
 * Created by joro on 16-3-8.
 */
public class Book {
    private int id;
    private String title, titleAuthor, cover, annotation, downloadUrl;
    private Category category;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleAuthor() {
        return titleAuthor;
    }

    public Category getCategory() {
        return category;
    }

    public String getCover() {
        return cover;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getDownloadUrl(String format) {
        return Constants.CHITANKA_INFO_API + "/book/" + id + "." + format;
    }
}
