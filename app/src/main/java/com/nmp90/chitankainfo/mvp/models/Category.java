package com.nmp90.chitankainfo.mvp.models;

import java.util.List;

/**
 * Created by nmp on 16-3-15.
 */
public class Category {
    private String slug, name;
    private int id;
    private int parent;
    private int nrOfBooks;
    private int level;

    private List<Category> children;

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getParent() {
        return parent;
    }

    public int getNrOfBooks() {
        return nrOfBooks;
    }

    public List<Category> getChildren() {
        return children;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
