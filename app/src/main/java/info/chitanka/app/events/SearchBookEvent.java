package info.chitanka.app.events;

/**
 * Created by nmp on 16-3-8.
 */
public class SearchBookEvent {
    private String name;

    public SearchBookEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
