package com.letv.plugin.pluginconfig.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.letv.core.utils.ActivityUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.apk.compat.PackageManagerCompat;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.topic.ex.LetvTopicProxyActivity;

public class JarLaunchUtils {
    public static void launchSportGameDefault(Context context) {
        if (!LetvUtils.isGooglePlay()) {
            Intent gameIntent = context.getPackageManager().getLaunchIntentForPackage(JarConstant.LETV_SPORT_GAME_PACKAGENAME);
            if (gameIntent != null) {
                gameIntent.addFlags(268435456);
                context.startActivity(gameIntent);
            }
        }
    }

    public static void launchLitePlayerDefault(Context context, Intent mIntent) {
        Intent liteIntent = context.getPackageManager().getLaunchIntentForPackage("com.letv.android.lite");
        LogInfo.log("plugin", "intent is null?" + (liteIntent == null));
        if (liteIntent != null && mIntent != null) {
            LogInfo.log("sguotao", "launchMode:" + mIntent.getIntExtra("launchMode", 0));
            LogInfo.log("sguotao", "from:" + mIntent.getStringExtra("packageName"));
            LogInfo.log("sguotao", "pcode:" + mIntent.getStringExtra("pcode"));
            LogInfo.log("sguotao", "vid:" + mIntent.getIntExtra("vid", 0));
            LogInfo.log("sguotao", "jsonData:" + mIntent.getStringExtra("jsonData"));
            liteIntent.putExtra("launchMode", mIntent.getIntExtra("launchMode", 0));
            liteIntent.putExtra("pcode", mIntent.getStringExtra("pcode"));
            liteIntent.putExtra("packageName", mIntent.getStringExtra("packageName"));
            liteIntent.putExtra("vid", mIntent.getIntExtra("vid", 0));
            liteIntent.putExtra("jsonData", mIntent.getStringExtra("jsonData"));
            liteIntent.addFlags(536870912);
            liteIntent.addFlags(268435456);
            liteIntent.addFlags(67108864);
            try {
                context.startActivity(liteIntent);
            } catch (Exception e) {
                LogInfo.log("zhuqiao", "launchLitePlayerDefault error:" + e.getMessage());
                ApkManager.getInstance().setPluginInstallState("com.letv.android.lite", PackageManagerCompat.INSTALL_FAILED_INTERNAL_ERROR);
                System.exit(0);
            }
            ActivityUtils.getInstance().removeAll();
        }
    }

    public static void launchTopicActivity(Context context) {
        Intent intent = new Intent(context, LetvTopicProxyActivity.class);
        intent.putExtra("extra.jarname", JarConstant.LETV_TOPIC_NAME);
        intent.putExtra("extra.packagename", JarConstant.LETV_TOPIC_PACKAGENAME);
        intent.putExtra("extra.class", JarConstant.LETV_TOPIC_CLASS_NAME);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void testNonStaticMethodCall(Context context) {
        JarLoader.invokeMethodByObj(JarLoader.newInstance(JarLoader.loadClass(context, JarConstant.PLUGIN_APK_NAME_WINDOW_PLAYER, JarConstant.PLUGIN_PACKAGE_NAME_WINDOW_PLAYER, JarConstant.PLUGIN_CLASS_NAME_WINDOW_PLAYER_HELPER), new Class[0], new Object[0]), "test", null, null);
    }
}
