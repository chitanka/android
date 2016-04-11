package info.chitanka.android;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import info.chitanka.android.di.application.ApplicationComponent;
import info.chitanka.android.di.application.ApplicationModule;
import info.chitanka.android.di.application.DaggerApplicationComponent;
import io.fabric.sdk.android.Fabric;
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

        if (BuildConfig.DEBUG) {
            refWatcher = LeakCanary.install(this);
            Timber.plant(new Timber.DebugTree());
        } else {
            Fabric.with(this, new Crashlytics());
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

            Crashlytics.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    Crashlytics.logException(t);
                }
            }
        }
    }
}
