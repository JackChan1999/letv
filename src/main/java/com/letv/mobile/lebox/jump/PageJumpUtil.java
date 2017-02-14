package com.letv.mobile.lebox.jump;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PageJumpUtil {
    public static final String IN_TO_ALBUM_NAME = "albumName";
    public static final String IN_TO_ALBUM_PID = "albumId";
    public static final String IS_SWEEP_JUMP = "from_sweep";

    public static void jumpToPageQrCodeShareActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.lebox.QrCodeShare");
        activity.startActivity(intent);
    }

    public static void jumpLeBoxMainActivity(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.letv.LeboxFunctionPage");
        context.startActivity(intent);
    }

    public static void jumpLeboxDownloadPage(Activity activity) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.letv.LeboxDownLoadPage");
        activity.startActivity(intent);
    }

    public static void jumpToPageDownSubPage(Activity activity, long pid, String albumName) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.lebox.LeboxDownSubLoadPage");
        intent.putExtra(IN_TO_ALBUM_PID, pid);
        intent.putExtra(IN_TO_ALBUM_NAME, albumName);
        activity.startActivity(intent);
    }

    public static void jumpQrCodeScan(Activity activity) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.lebox.QrCodeScan");
        activity.startActivity(intent);
    }

    public static void jumpBuyPage(Context context) {
        context.startActivity(new Intent("android.intent.action.letv.LeboxBuyPage"));
    }

    public static void jumpWifiManagerPage(Context context) {
        context.startActivity(new Intent("android.intent.action.letv.LeboxWifiManagerPage"));
    }

    public static void jumpTestPage(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.lebox.TestPage");
            intent.setFlags(268435456);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void jumpMyFollow(Context context) {
        context.startActivity(new Intent("android.intent.action.lebox.LeboxMyFollow"));
    }

    public static void jumpLeboxSettings(Context context) {
        context.startActivity(new Intent("android.intent.action.lebox.LeboxSettings"));
    }

    public static void jumpToPageSystemShare(Context context, String activityTitle, String msgTitle, String msgText, Uri imgUri) {
        Intent intent = new Intent("android.intent.action.SEND");
        if (imgUri == null) {
            intent.setType("text/plain");
        } else {
            intent.setType("image/*");
            intent.putExtra("android.intent.extra.STREAM", imgUri);
        }
        intent.putExtra("android.intent.extra.SUBJECT", msgTitle);
        intent.putExtra("android.intent.extra.TEXT", msgText);
        intent.setFlags(268435456);
        context.startActivity(Intent.createChooser(intent, activityTitle));
    }

    public static void jumpLeboxRipple(Context context) {
        jumpLeboxRipple(context, 0, "");
    }

    public static void jumpLeboxRipple(Context context, int delayTime, String delayShowText) {
        Intent intent = new Intent("android.intent.action.lebox.WaterRipplePage");
        intent.setFlags(268435456);
        intent.putExtra("delayTime", delayTime);
        intent.putExtra("delayShowText", delayShowText);
        context.startActivity(intent);
    }

    public static void jumpToIntroduce(Context context) {
        context.startActivity(new Intent("android.intent.action.lebox.LeboxIntroduce"));
    }

    public static void jumpToBrowser(Context context, String url) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }
}
