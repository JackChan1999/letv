package com.letv.core.messagebus.manager;

import com.letv.core.BaseApplication;
import com.letv.core.utils.LogInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PackageHelper {
    private static final String SUFFIX = "Static";

    public static void loadAllStaticClass() {
        Exception e;
        Throwable th;
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(BaseApplication.getInstance().getResources().getAssets().open("static.txt")));
            while (true) {
                try {
                    String line = bufReader.readLine();
                    if (line == null) {
                        break;
                    } else if (line.startsWith("com.letv")) {
                        try {
                            Class.forName(line);
                            LogInfo.log(SUFFIX, "load success:" + line);
                        } catch (Throwable th2) {
                            LogInfo.log(SUFFIX, "load error:" + line);
                        }
                    } else {
                        LogInfo.log(SUFFIX, "load error:" + line);
                    }
                } catch (Exception e2) {
                    e = e2;
                    bufferedReader = bufReader;
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader = bufReader;
                }
            }
            if (bufReader != null) {
                try {
                    bufReader.close();
                    bufferedReader = bufReader;
                    return;
                } catch (IOException e3) {
                    e3.printStackTrace();
                    bufferedReader = bufReader;
                    return;
                }
            }
        } catch (Exception e4) {
            e = e4;
            try {
                e.printStackTrace();
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e32) {
                        e32.printStackTrace();
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e322) {
                        e322.printStackTrace();
                    }
                }
                throw th;
            }
        }
    }
}
