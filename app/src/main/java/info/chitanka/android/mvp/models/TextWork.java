package info.chitanka.android.mvp.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by nmp on 18.01.17.
 */

@Parcel
public class TextWork {
    int id, votes;
    double rating;
    String slug, title, subtitle;
    List<String> formats;
    List<Author> authors;

    public int getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<String> getFormats() {
        return formats;
    }

    public int getVotes() {
        return votes;
    }

    public double getRating() {
        return rating;
    }
}
