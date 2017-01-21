package info.chitanka.android.di.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.chitanka.android.Constants;
import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.api.ChitankaApiService;
import info.chitanka.android.components.AnalyticsService;
import info.chitanka.android.utils.RxBus;

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

    @Provides
    @Singleton
    public AnalyticsService providesAnalytics() {
        return new AnalyticsService();
    }
}
