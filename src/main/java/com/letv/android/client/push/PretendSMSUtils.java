package com.letv.android.client.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;

public class PretendSMSUtils {
    public PretendSMSUtils() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void createFakeSms(Context context, String sender, String body) {
        add(context, sender, body);
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(268435456);
        intent.setType("vnd.android-dir/mms-sms");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 444555, intent, 134217728);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        Notification notification = new Notification();
        notification.icon = 17301647;
        notification.tickerText = sender + NetworkUtils.DELIMITER_COLON + body;
        notification.flags = 16;
        notification.defaults |= 1;
        notification.setLatestEventInfo(context, sender, body, pendingIntent);
        notificationManager.notify(444555, notification);
    }

    private static void add(Context context, String phoneNum, String content) {
        String ADDRESS = "address";
        String DATE = "date";
        String READ = "read";
        String STATUS = "status";
        String TYPE = "type";
        String BODY = "body";
        ContentValues values = new ContentValues();
        values.put("address", phoneNum);
        values.put("date", System.currentTimeMillis() + "");
        values.put("read", Integer.valueOf(0));
        values.put("status", Integer.valueOf(0));
        values.put("type", Integer.valueOf(1));
        values.put("body", content);
        context.getContentResolver().insert(Uri.parse("content://sms"), values);
    }
}
