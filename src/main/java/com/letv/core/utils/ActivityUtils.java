package com.letv.core.utils;

import android.app.Activity;
import android.text.TextUtils;
import java.util.LinkedHashMap;
import java.util.Map;

public class ActivityUtils {
    private static ActivityUtils instance = null;
    private Map<String, Activity> mActivityMap = new LinkedHashMap();

    private ActivityUtils() {
    }

    public static ActivityUtils getInstance() {
        if (instance == null) {
            instance = new ActivityUtils();
        }
        return instance;
    }

    public void addActivity(String name, Activity activity) {
        if (!TextUtils.isEmpty(name) && activity != null) {
            removeActivity(name, true);
            this.mActivityMap.put(name, activity);
        }
    }

    public void removeActivity(String name, boolean finish) {
        if (!TextUtils.isEmpty(name) && this.mActivityMap.containsKey(name)) {
            if (finish) {
                Activity activity = (Activity) this.mActivityMap.get(name);
                if (!(activity == null || activity.isFinishing())) {
                    activity.finish();
                }
            }
            this.mActivityMap.remove(name);
        }
    }

    public void removeAll() {
        if (!this.mActivityMap.isEmpty()) {
            for (String key : this.mActivityMap.keySet()) {
                Activity activity = (Activity) this.mActivityMap.get(key);
                if (!(activity == null || activity.isFinishing())) {
                    activity.finish();
                }
            }
        }
        this.mActivityMap.clear();
    }
}
