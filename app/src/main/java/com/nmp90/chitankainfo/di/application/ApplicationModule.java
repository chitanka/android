package com.nmp90.chitankainfo.di.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.nmp90.chitankainfo.Constants;
import com.nmp90.chitankainfo.api.ChitankaApi;
import com.nmp90.chitankainfo.api.ChitankaApiService;
import com.nmp90.chitankainfo.utils.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by joro on 16-3-8.
 */
@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Context providesContext() {
        return application;
    }

    @Provides
    @Singleton
    public Resources providesResources(Context context) {
        return context.getResources();
    }

    @Provides
    @Singleton
    public SharedPreferences providesSharedPreferences() {
        return application.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public RxBus providesEventBus() {
        return new RxBus();
    }

    @Provides
    @Singleton
    public ChitankaApi providesApi() {
        return ChitankaApiService.createChitankaApiService();
    }
}
