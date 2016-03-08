package com.nmp90.chitankainfo.di.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.nmp90.chitankainfo.parser.ChitankaParser;
import com.nmp90.chitankainfo.ui.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by joro on 16-3-8.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Context getContext();
    Application getApplication();
    SharedPreferences getSharedPreferences();
    Resources getResources();
    ChitankaParser getUrlParser();

    void inject(BaseActivity baseActivity);
}
