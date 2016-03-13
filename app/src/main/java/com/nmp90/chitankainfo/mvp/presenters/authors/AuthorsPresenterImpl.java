package com.nmp90.chitankainfo.mvp.presenters.authors;

import com.nmp90.chitankainfo.mvp.views.AuthorsView;
import com.nmp90.chitankainfo.utils.ChitankaParser;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nmp on 16-3-11.
 */
public class AuthorsPresenterImpl implements AuthorsPresenter {
    private final ChitankaParser webParser;
    private WeakReference<AuthorsView> authorsViewWeakReference;

    public AuthorsPresenterImpl(ChitankaParser webParser) {
        this.webParser = webParser;
    }

    @Override
    public void setView(AuthorsView authorsViewWeakReference) {
        this.authorsViewWeakReference = new WeakReference<>(authorsViewWeakReference);
    }

    @Override
    public void searchAuthors(String name) {
        if(viewExists()) {
            authorsViewWeakReference.get().showLoading();
            webParser.searchAuthors(name)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((authors) -> {
                        authorsViewWeakReference.get().hideLoading();
                        authorsViewWeakReference.get().presentAuthors(authors);
                    }, (error) -> {
                        authorsViewWeakReference.get().hideLoading();
                        authorsViewWeakReference.get().presentAuthors(new ArrayList<>());
                    });
        }
    }

    private boolean viewExists() {
        return authorsViewWeakReference != null && authorsViewWeakReference.get() != null;
    }
}