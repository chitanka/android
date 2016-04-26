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

    public Authors(Pagination pager, List<Author> persons) {
        this.pager = pager;
        this.persons = persons;
    }

    public Pagination getPager() {
        return pager;
    }

    public List<Author> getPersons() {
        return persons;
    }
}
