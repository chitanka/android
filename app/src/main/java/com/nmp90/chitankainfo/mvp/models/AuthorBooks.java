package com.nmp90.chitankainfo.mvp.models;

import java.util.List;

/**
 * Created by nmp on 16-3-22.
 */
public class AuthorBooks {
    private List<Book> books;
    private Author person;

    public List<Book> getBooks() {
        return books;
    }

    public Author getPerson() {
        return person;
    }
}
