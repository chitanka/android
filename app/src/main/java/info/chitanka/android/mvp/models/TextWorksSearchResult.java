package info.chitanka.android.mvp.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by nmp on 21.01.17.
 */

@Parcel
public class TextWorksSearchResult {
    List<TextWork> texts;

    public TextWorksSearchResult() {
    }

    public List<TextWork> getTexts() {
        return texts;
    }
}
