package info.chitanka.android.mvp.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by nmp on 16-3-15.
 */
@Parcel
public class Category {
    String slug, name;
    int id;
    int parent;
    int nrOfBooks;
    int level;

    List<Category> children;

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
