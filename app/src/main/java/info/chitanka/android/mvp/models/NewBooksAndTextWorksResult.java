package info.chitanka.android.mvp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by joro on 23.01.17.
 */

public class NewBooksAndTextWorksResult {
    @SerializedName("book_revisions_by_date")
    String books;

    @SerializedName("text_revisions_by_date")
    String textWorks;

    public String getBooks() {
        return books;
    }

    public String getTextWorks() {
        return textWorks;
    }
}
