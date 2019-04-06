package info.chitanka.app.mvp.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by nmp on 18.01.17.
 */

@Parcel
public class SearchResult {
    List<Book> books;
    List<Author> persons;
    List<TextWork> texts;

    public SearchResult() {
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Author> getPersons() {
        return persons;
    }

    public List<TextWork> getTexts() {
        return texts;
    }
}
