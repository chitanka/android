package com.nmp90.chitankainfo.mvp.presenters.authors;

import com.nmp90.chitankainfo.mvp.presenters.BasePresenter;
import com.nmp90.chitankainfo.mvp.views.AuthorsView;
import com.nmp90.chitankainfo.utils.ChitankaParser;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-11.
 */
public class AuthorsPresenterImpl extends BasePresenter implements AuthorsPresenter {
    private final ChitankaParser webParser;
    private WeakReference<AuthorsView> authorsView;

    public AuthorsPresenterImpl(ChitankaParser webParser) {
        this.webParser = webParser;
    }

    @Override
    public void setView(AuthorsView authorsViewWeakReference) {
        this.authorsView = new WeakReference<>(authorsViewWeakReference);
    }

    @Override
    public void searchAuthors(String name) {
        if(viewExists()) {
            authorsView.get().showLoading();
            webParser.searchAuthors(name)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((authors) -> {
                        if (!viewExists(authorsView))
                            return;
                        authorsView.get().hideLoading();
                        authorsView.get().presentAuthors(authors);
                    }, (error) -> {
                        Timber.e(error, "Error printing authors!");
                        if (!viewExists(authorsView))
                            return;
                        authorsView.get().hideLoading();
                        authorsView.get().presentAuthors(new ArrayList<>());
                    });
        }
    }

    private boolean viewExists() {
        return authorsView != null && authorsView.get() != null;
    }
}
