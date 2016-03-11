package com.nmp90.chitankainfo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmp90.chitankainfo.R;
import com.nmp90.chitankainfo.di.presenters.PresenterComponent;
import com.nmp90.chitankainfo.mvp.presenters.authors.AuthorsPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by nmp on 16-3-11.
 */
public class AuthorsFragment extends BaseFragment {

    @Inject
    AuthorsPresenter authorsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authors, container, false);
        ButterKnife.bind(this, view);

        getComponent(PresenterComponent.class).inject(this);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        authorsPresenter = null;
    }
}
