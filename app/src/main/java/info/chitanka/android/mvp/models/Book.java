package info.chitanka.android.mvp.models;

import info.chitanka.android.Constants;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joro on 16-3-8.
 */
@Parcel
public class Book {
    int id, year;
    String title, titleAuthor, cover, annotation, downloadUrl;
    Category category;
    ArrayList<String> formats;
    List<Author> authors;

    public Book() {
    }

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

    public int getYear() {
        return year;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
