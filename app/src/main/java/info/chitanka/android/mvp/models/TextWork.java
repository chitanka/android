package info.chitanka.android.mvp.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import info.chitanka.android.Constants;

/**
 * Created by nmp on 18.01.17.
 */

@Parcel
public class TextWork {
    int id, votes;
    double rating;
    String slug, title, subtitle;
    ArrayList<String> formats;
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

    public ArrayList<String> getFormats() {
        return formats;
    }

    public int getVotes() {
        return votes;
    }

    public double getRating() {
        return rating;
    }

    public String getDownloadUrl() {
        return "";
    }

    public String getChitankaUrl() {
        return Constants.CHITANKA_INFO_API + "text/" + id + "-" + slug;
    }
}
