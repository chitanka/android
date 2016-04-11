package info.chitanka.android.di.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.ui.BaseActivity;
import info.chitanka.android.utils.RxBus;

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
    RxBus getRxBus();
    ChitankaApi getChitankaApi();

    void inject(BaseActivity baseActivity);
}
