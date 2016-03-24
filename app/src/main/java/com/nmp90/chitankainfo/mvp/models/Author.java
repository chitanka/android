package com.nmp90.chitankainfo.mvp.models;

import org.parceler.Parcel;

/**
 * Created by nmp on 16-3-11.
 */

@Parcel
public class Author {
    String name, country, slug;

    public Author() {
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
