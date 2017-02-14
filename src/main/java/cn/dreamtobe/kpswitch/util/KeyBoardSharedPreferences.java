package cn.dreamtobe.kpswitch.util;

import android.content.Context;
import android.content.SharedPreferences;

class KeyBoardSharedPreferences {
    private static final String FILE_NAME = "keyboard.common";
    private static final String KEY_KEYBOARD_HEIGHT = "sp.key.keyboard.height";
    private static volatile SharedPreferences SP;

    KeyBoardSharedPreferences() {
    }

    public static boolean save(Context context, int keyboardHeight) {
        return with(context).edit().putInt(KEY_KEYBOARD_HEIGHT, keyboardHeight).commit();
    }

    private static SharedPreferences with(Context context) {
        if (SP == null) {
            synchronized (KeyBoardSharedPreferences.class) {
                if (SP == null) {
                    SP = context.getSharedPreferences(FILE_NAME, 0);
                }
            }
        }
        return SP;
    }

    public static int get(Context context, int defaultHeight) {
        return with(context).getInt(KEY_KEYBOARD_HEIGHT, defaultHeight);
    }
}
