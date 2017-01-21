package info.chitanka.android.components;

import com.flurry.android.FlurryAgent;

import java.util.HashMap;

/**
 * Created by nmp on 21.01.17.
 */

public class AnalyticsService {
    public void logEvent(String eventName, HashMap<String, String> map) {
        FlurryAgent.logEvent(eventName, map);
    }

    public void logEvent(String eventName) {
        FlurryAgent.logEvent(eventName);
    }

    public void setUserId(String userId) {
        FlurryAgent.setUserId(userId);
    }
}
