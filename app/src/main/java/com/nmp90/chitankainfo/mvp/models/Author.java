package com.nmp90.chitankainfo.mvp.models;

/**
 * Created by nmp on 16-3-11.
 */
public class Author {
    private String name, country, slug;

    public Author(String name, String country, String slug) {
        this.name = name;
        this.slug = slug;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getCountry() {
        return country;
    }
}
