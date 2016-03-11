package com.nmp90.chitankainfo.mvp.presenters.authors;

import com.nmp90.chitankainfo.mvp.views.AuthorsView;
import com.nmp90.chitankainfo.utils.ChitankaParser;

import java.lang.ref.WeakReference;

/**
 * Created by nmp on 16-3-11.
 */
public class AuthorsPresenterImpl implements AuthorsPresenter {
    private final ChitankaParser webParser;
    private WeakReference<AuthorsView> authorsViewWeakReference;

    public AuthorsPresenterImpl(ChitankaParser webParser) {
        this.webParser = webParser;
    }

    public void setAuthorsViewWeakReference(AuthorsView authorsViewWeakReference) {
        this.authorsViewWeakReference = new WeakReference<AuthorsView>(authorsViewWeakReference);
    }

    @Override
    public void searchAuthors(String name) {
        if(viewExists()) {
            //TODO
        }
    }

    private boolean viewExists() {
        return authorsViewWeakReference != null && authorsViewWeakReference.get() != null;
    }
}
