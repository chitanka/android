package info.chitanka.android.components;

import com.flurry.android.FlurryAgent;

import java.util.HashMap;

import info.chitanka.android.BuildConfig;

/**
 * Created by nmp on 21.01.17.
 */

public class AnalyticsService {
    public void logEvent(String eventName, HashMap<String, String> map) {
        if (!BuildConfig.DEBUG) {
            FlurryAgent.logEvent(eventName, map);
        }
    }

    public void logEvent(String eventName) {
        if (!BuildConfig.DEBUG) {
            FlurryAgent.logEvent(eventName);
        }
    }

    public void setUserId(String userId) {
        if (!BuildConfig.DEBUG) {
            FlurryAgent.setUserId(userId);
        }
    }
}
