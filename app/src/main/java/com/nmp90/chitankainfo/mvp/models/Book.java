package com.nmp90.chitankainfo.mvp.models;

/**
 * Created by joro on 16-3-8.
 */
public class Book {
    private String title, author, category, image, description;

    public Book(String title, String author, String category, String image, String description) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.image = image;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
