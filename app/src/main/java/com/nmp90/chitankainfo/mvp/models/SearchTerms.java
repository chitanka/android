package com.nmp90.chitankainfo.mvp.models;

/**
 * Created by joro on 16-3-20.
 */
public enum SearchTerms {
    AUTHOR("author"),
    CATEGORY("category")
    ;

    private final String text;

    /**
     * @param text
     */
    SearchTerms(final String text) {
        this.text = text;
    }



    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
