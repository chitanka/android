package info.chitanka.app.di.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import info.chitanka.app.api.ChitankaApi;
import info.chitanka.app.components.AnalyticsService;
import info.chitanka.app.ui.BaseActivity;
import info.chitanka.app.ui.BookReader;
import info.chitanka.app.utils.RxBus;

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
    BookReader getBookReader();

    void inject(BaseActivity baseActivity);
}
