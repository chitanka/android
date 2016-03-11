package com.nmp90.chitankainfo.mvp.models;

/**
 * Created by nmp on 16-3-11.
 */
public class Author {
    private String name, link;

    public Author(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }
}
