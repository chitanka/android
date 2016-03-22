package com.nmp90.chitankainfo.mvp.models;

import com.nmp90.chitankainfo.Constants;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by joro on 16-3-8.
 */
@Parcel
public class Book {
    int id;
    String title, titleAuthor, cover, annotation, downloadUrl;
    Category category;

    public Book() {
    }

    ArrayList<String> formats;

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
        return Constants.CHITANKA_INFO_API + "/" + cover;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getDownloadUrl(String format) {
        return Constants.CHITANKA_INFO_API + "book/" + id + "." + format;
    }

    public ArrayList<String> getFormats() {
        return formats;
    }
}
