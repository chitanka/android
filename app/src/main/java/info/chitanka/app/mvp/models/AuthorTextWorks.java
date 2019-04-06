package info.chitanka.app.mvp.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by nmp on 21.01.17.
 */
@Parcel
public class AuthorTextWorks {
    List<TextWork> texts;

    public List<TextWork> getTexts() {
        return texts;
    }
}
