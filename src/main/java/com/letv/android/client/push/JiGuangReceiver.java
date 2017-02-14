package com.letv.android.client.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import cn.jpush.android.api.JPushInterface;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import org.json.JSONException;
import org.json.JSONObject;

public class JiGuangReceiver extends BroadcastReceiver {
    public JiGuangReceiver() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (TextUtils.equals(intent.getAction(), JPushInterface.ACTION_MESSAGE_RECEIVED)) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            if (!TextUtils.isEmpty(message)) {
                try {
                    if (!JSONObject.NULL.equals(new JSONObject(message))) {
                        context.stopService(new Intent(context, LetvPushService.class));
                        Intent serviceIntent = new Intent(context, LetvPushService.class);
                        serviceIntent.putExtra(JPushInterface.EXTRA_MESSAGE, message);
                        context.startService(serviceIntent);
                        if (!TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_MSG_ID))) {
                            JPushInterface.reportNotificationOpened(context, bundle.getString(JPushInterface.EXTRA_MSG_ID));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
