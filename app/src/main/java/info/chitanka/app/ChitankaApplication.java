package info.chitanka.app;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.flurry.android.FlurryAgent;
import com.kobakei.ratethisapp.RateThisApp;

import info.chitanka.app.di.application.ApplicationComponent;
import info.chitanka.app.di.application.ApplicationModule;
import info.chitanka.app.di.application.DaggerApplicationComponent;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by joro on 16-3-8.
 */
public class ChitankaApplication extends Application {
    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            new FlurryAgent.Builder()
                    .withLogEnabled(true)
                    .withCaptureUncaughtExceptions(true)
                    .withContinueSessionMillis(10000)
                    .withLogLevel(Log.VERBOSE)
                    .build(this, BuildConfig.FLURRY_KEY);

            Fabric.with(this, new Crashlytics(), new Answers());
            Timber.plant(new CrashReportingTree());
        }

        initRatingDialog();
    }

    private void initRatingDialog() {
        RateThisApp.Config config = new RateThisApp.Config();
        config.setTitle(R.string.erd_title);
        config.setMessage(R.string.erd_message);
        config.setYesButtonText(R.string.erd_rate_now);
        config.setNoButtonText(R.string.erd_no_thanks);
        config.setCancelButtonText(R.string.erd_remind_me_later);
        RateThisApp.init(config);
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
