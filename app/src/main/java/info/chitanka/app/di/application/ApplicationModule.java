package info.chitanka.app.di.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.chitanka.app.Constants;
import info.chitanka.app.api.ChitankaApi;
import info.chitanka.app.api.ChitankaApiService;
import info.chitanka.app.components.AnalyticsService;
import info.chitanka.app.ui.BookReader;
import info.chitanka.app.utils.RxBus;

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
    public Gson providesGson() {
        return new GsonBuilder().setLenient().create();
    }

    @Provides
    @Singleton
    public ChitankaApi providesApi(Gson gson) {
        return ChitankaApiService.createChitankaApiService(gson);
    }

    @Provides
    @Singleton
    public AnalyticsService providesAnalytics() {
        return new AnalyticsService();
    }

    @Provides
    public BookReader providesBookReader(SharedPreferences sharedPreferences, Gson gson) {
        return new BookReader(sharedPreferences, gson);
    }
}
