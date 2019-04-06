package info.chitanka.app.mvp.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import info.chitanka.app.Constants;

/**
 * Created by joro on 16-3-8.
 */
@Parcel
public class Book {
    int id, year;
    String title, slug, titleAuthor, cover, annotation, downloadUrl;
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

    public String getSlug() {
        return slug;
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

    public ArrayList<String> getFormats() {
        return formats;
    }

    public int getYear() {
        return year;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getDownloadUrl() {
        return Constants.CHITANKA_INFO_API + "book/" + id + ".%s";
    }

    public String getReadingFileName() {
        return id + "-" + slug + ".epub";
    }

    public String getWebChitankaUrl() {
        return Constants.CHITANKA_INFO_API + "book/" + id + "-" + slug;
    }
}
