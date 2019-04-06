package info.chitanka.app.mvp.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import info.chitanka.app.Constants;

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

    public TextWork() {
        // TODO: Replace with data from server
        // When server guys just don't want to add a single object field
        this.formats = new ArrayList<String>() {{
            add("fb2.zip");
            add("epub");
            add("txt.zip");
            add("sfb.zip");
        }};
    }

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

    public String getReadingFileName() {
        return id + "-" + slug + ".epub";
    }

    public String getDownloadUrl() {
        return Constants.CHITANKA_INFO_API + "text/" + id + ".%s";
    }

    public String getChitankaUrl() {
        return Constants.CHITANKA_INFO_API + "text/" + id + "-" + slug;
    }
}
