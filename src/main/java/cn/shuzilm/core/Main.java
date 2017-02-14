package cn.shuzilm.core;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {
    private static Main a = new Main();
    private static JSONObject b = null;
    private static Object c = null;
    private static boolean d = false;
    private static String e = "du";

    Main() {
        b = new JSONObject();
    }

    public static void go(Context context, String str, String str2) {
        try {
            System.loadLibrary(e);
            if (b == null) {
                b = new JSONObject();
                if (b == null) {
                    throw new NullPointerException();
                }
            }
            try {
                b.put("custom", str2);
                run(context, str, b.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (UnsatisfiedLinkError e2) {
            e2.printStackTrace();
        }
    }

    private static native void run(Context context, String str, String str2);

    public static synchronized int setData(String str, String str2) {
        int i;
        synchronized (Main.class) {
            i = 0;
            if (b == null) {
                b = new JSONObject();
                if (b == null) {
                    throw new NullPointerException();
                }
            }
            try {
                b.put(str, str2);
            } catch (Exception e) {
                i = -1;
            }
        }
        return i;
    }
}
