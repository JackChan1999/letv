package com.soundink.lib.c;

import android.os.Environment;
import com.letv.pp.utils.NetworkUtils;
import com.soundink.lib.a.a;
import com.soundink.lib.e;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class b {
    private static String a = "";

    public static Properties a() {
        Properties properties = new Properties();
        try {
            properties.load(b.class.getResourceAsStream("/assets/soundinkSdkConfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void a(String str) {
        a(System.currentTimeMillis() + NetworkUtils.DELIMITER_LINE + e.a(new StringBuilder(String.valueOf(System.currentTimeMillis())).toString(), "MM-dd HH:mm:ss") + ", message: " + str + "\n", "SoundInkSdkDataLog.txt");
    }

    public static void a(String str, String str2) {
        if (a.a) {
            if (Environment.getExternalStorageState().equals("mounted")) {
                a = Environment.getExternalStorageDirectory().getAbsolutePath();
            }
            if (a != null && !"".equals(a)) {
                try {
                    File file = new File(a, str2);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    OutputStream fileOutputStream = new FileOutputStream(file, true);
                    fileOutputStream.write(str.getBytes());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
