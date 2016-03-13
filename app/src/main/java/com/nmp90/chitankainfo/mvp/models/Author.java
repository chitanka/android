package com.nmp90.chitankainfo.mvp.models;

/**
 * Created by nmp on 16-3-11.
 */
public class Author {
    private String name, country, link;

    public Author(String name, String country, String link) {
        this.name = name;
        this.link = link;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getCountry() {
        return country;
    }
}
