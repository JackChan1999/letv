package cn.jpush.android.api;

import android.app.Notification;
import java.util.Map;

public interface PushNotificationBuilder {
    Notification a(String str, Map<String, String> map);

    String a();
}
