package com.nmp90.chitankainfo;

import android.app.Application;
import android.util.Log;

import com.nmp90.chitankainfo.di.application.ApplicationComponent;
import com.nmp90.chitankainfo.di.application.ApplicationModule;
import com.nmp90.chitankainfo.di.application.DaggerApplicationComponent;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

/**
 * Created by joro on 16-3-8.
 */
public class ChitankaApplication extends Application {
    private static ApplicationComponent applicationComponent;
    private static RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();

        refWatcher = LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
            //TODO: Add fake crash library or Crashlytics
            //FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    //FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    //FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
