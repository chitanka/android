package com.nmp90.chitankainfo.mvp.presenters.authors;

import com.nmp90.chitankainfo.api.ChitankaApi;
import com.nmp90.chitankainfo.mvp.presenters.BasePresenter;
import com.nmp90.chitankainfo.mvp.views.AuthorsView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nmp on 16-3-11.
 */
public class AuthorsPresenterImpl extends BasePresenter implements AuthorsPresenter {
    private final ChitankaApi chitankaApi;
    private WeakReference<AuthorsView> authorsView;

    public AuthorsPresenterImpl(ChitankaApi chitankaApi) {
        this.chitankaApi = chitankaApi;
    }

    @Override
    public void setView(AuthorsView authorsViewWeakReference) {
        this.authorsView = new WeakReference<>(authorsViewWeakReference);
    }

    @Override
    public void searchAuthors(String name) {
        if(viewExists()) {
            authorsView.get().showLoading();
            chitankaApi.searchAuthors(name)
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
