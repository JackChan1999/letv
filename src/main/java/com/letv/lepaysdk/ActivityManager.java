package com.letv.lepaysdk;

import android.app.Activity;
import java.util.HashMap;

public class ActivityManager {
    private static ActivityManager activityManager;
    private HashMap<String, Activity> activityMap = new HashMap();

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        if (activityManager == null) {
            activityManager = new ActivityManager();
        }
        return activityManager;
    }

    public void removeActivityOnMap(String key) {
        if (this.activityMap != null && this.activityMap.containsKey(key)) {
            this.activityMap.remove(key);
        }
    }

    public Activity getActivityOnMap(String key) {
        if (this.activityMap == null || !this.activityMap.containsKey(key)) {
            return null;
        }
        return (Activity) this.activityMap.get(key);
    }

    public void removeActivityOnMap(Activity activity) {
        if (this.activityMap != null && this.activityMap.containsKey(activity.getClass().getSimpleName())) {
            this.activityMap.remove(activity.getClass().getSimpleName());
        }
    }

    public void finishActivity(String simpleName) {
        if (this.activityMap != null && this.activityMap.containsKey(simpleName)) {
            ((Activity) this.activityMap.get(simpleName)).finish();
        }
    }

    public void finishActivity(Activity activity) {
        if (this.activityMap != null && this.activityMap.containsKey(activity.getClass().getSimpleName())) {
            ((Activity) this.activityMap.get(activity.getClass().getSimpleName())).finish();
        }
    }

    public void finishActivitys() {
        if (this.activityMap != null) {
            for (String key : this.activityMap.keySet()) {
                finishActivity(key);
            }
        }
    }

    public void addActivityToMap(Activity activity) {
        if (this.activityMap != null) {
            this.activityMap.put(activity.getClass().getSimpleName(), activity);
        }
    }
}
