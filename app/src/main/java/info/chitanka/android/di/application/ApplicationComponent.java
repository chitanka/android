package info.chitanka.android.di.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.components.AnalyticsService;
import info.chitanka.android.ui.BaseActivity;
import info.chitanka.android.utils.RxBus;

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
    AnalyticsService getAnalyticsService();
    Gson getGSON();

    void inject(BaseActivity baseActivity);
}
