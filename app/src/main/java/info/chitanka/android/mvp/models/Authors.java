package info.chitanka.android.mvp.models;

import java.util.List;

/**
 * Created by nmp on 16-4-19.
 */
public class Authors {
    private Pagination pager;
    private List<Author> persons;

    public Authors() {
    }

    public Pagination getPager() {
        return pager;
    }

    public List<Author> getPersons() {
        return persons;
    }
}
