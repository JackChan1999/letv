package cn.com.iresearch.mapptracker;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Looper;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import cn.com.iresearch.mapptracker.a.a.a.a.a.a.d;
import cn.com.iresearch.mapptracker.b.a;
import cn.com.iresearch.mapptracker.util.DataProvider;
import com.letv.android.client.utils.IniFile;
import com.letv.core.constant.LetvConstant;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONObject;

public class IRMonitor {
    private static boolean a = false;
    private static IRCallBack b = null;
    private static IRMonitor c = new IRMonitor();
    private static Timer d;
    private static TimerTask e;
    private static boolean f = false;
    private static long g = 0;

    private IRMonitor() {
    }

    public static IRMonitor getInstance() {
        if (c == null) {
            c = new IRMonitor();
        }
        return c;
    }

    public boolean isPrintLog() {
        return a;
    }

    public void setAppChannel(Context context, String str) {
        a.a(context.getApplicationContext(), "apch", str);
    }

    public void init(Context context, String str, String str2) {
        init(context, str, str2, false, null);
    }

    public void init(Context context, String str, String str2, boolean z, IRCallBack iRCallBack) {
        if (TextUtils.isEmpty(str)) {
            throw new RuntimeException("appkey 为空");
        }
        a = z;
        Context applicationContext = context.getApplicationContext();
        if (!TextUtils.isEmpty(str2)) {
            a.a(applicationContext, "youuid", str2);
        }
        a.a(applicationContext, IniFile.APPKEY, str);
        if (iRCallBack != null && b == null) {
            b = iRCallBack;
        }
        new Thread(new a(this, applicationContext)).start();
    }

    public void onResume(Context context) {
        if (!(context instanceof Activity)) {
            throw new RuntimeException("当前上下文不是Activity");
        } else if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("请在主线程调用");
        } else {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                try {
                    if (d == null) {
                        d = new Timer();
                    }
                    if (e == null) {
                        e = new b(this, applicationContext);
                    }
                    if (!f) {
                        f = true;
                        d.schedule(e, 0, 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            new Thread(new c(this, applicationContext)).start();
        }
    }

    public void onPause(Context context) {
    }

    public static d open$12ace190(String str, String str2) {
        ByteArrayInputStream byteArrayInputStream;
        OutputStream outputStream;
        ByteArrayInputStream byteArrayInputStream2;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        Exception exception;
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayInputStream byteArrayInputStream3;
        Throwable th;
        InputStream inputStream2;
        ByteArrayOutputStream byteArrayOutputStream2;
        ByteArrayOutputStream byteArrayOutputStream3 = null;
        d dVar = new d();
        OutputStream outputStream2;
        try {
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                byte[] bArr;
                httpURLConnection2.setDoInput(true);
                httpURLConnection2.setDoOutput(true);
                httpURLConnection2.setUseCaches(false);
                httpURLConnection2.setConnectTimeout(8000);
                httpURLConnection2.setReadTimeout(8000);
                if (TextUtils.isEmpty(str2)) {
                    httpURLConnection2.setRequestMethod("GET");
                    byteArrayInputStream = null;
                    outputStream = null;
                } else {
                    httpURLConnection2.setRequestMethod("POST");
                    outputStream2 = httpURLConnection2.getOutputStream();
                    try {
                        byteArrayInputStream2 = new ByteArrayInputStream(str2.getBytes());
                    } catch (Exception e) {
                        httpURLConnection = httpURLConnection2;
                        inputStream = null;
                        exception = e;
                        byteArrayOutputStream = null;
                        try {
                            exception.printStackTrace();
                            dVar.a = false;
                            dVar.b = exception.getMessage();
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            if (outputStream2 != null) {
                                outputStream2.close();
                            }
                            if (byteArrayOutputStream != null) {
                                byteArrayOutputStream.close();
                            }
                            if (byteArrayInputStream3 != null) {
                                byteArrayInputStream3.close();
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            return dVar;
                        } catch (Throwable th2) {
                            th = th2;
                            inputStream2 = inputStream;
                            outputStream = outputStream2;
                            byteArrayInputStream = byteArrayInputStream3;
                            byteArrayOutputStream3 = byteArrayOutputStream;
                            if (inputStream2 != null) {
                                try {
                                    inputStream2.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                    throw th;
                                }
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            if (byteArrayOutputStream3 != null) {
                                byteArrayOutputStream3.close();
                            }
                            if (byteArrayInputStream != null) {
                                byteArrayInputStream.close();
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        httpURLConnection = httpURLConnection2;
                        outputStream = outputStream2;
                        inputStream2 = null;
                        byteArrayInputStream = null;
                        th = th3;
                        if (inputStream2 != null) {
                            inputStream2.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (byteArrayOutputStream3 != null) {
                            byteArrayOutputStream3.close();
                        }
                        if (byteArrayInputStream != null) {
                            byteArrayInputStream.close();
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        throw th;
                    }
                    try {
                        outputStream2 = httpURLConnection2.getOutputStream();
                        bArr = new byte[2048];
                        while (true) {
                            int read = byteArrayInputStream2.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            outputStream2.write(bArr, 0, read);
                        }
                        outputStream2.flush();
                        outputStream2.close();
                        byteArrayInputStream2.close();
                        outputStream = outputStream2;
                        byteArrayInputStream = byteArrayInputStream2;
                    } catch (Exception e4) {
                        inputStream = null;
                        byteArrayInputStream3 = byteArrayInputStream2;
                        httpURLConnection = httpURLConnection2;
                        exception = e4;
                        byteArrayOutputStream = null;
                        exception.printStackTrace();
                        dVar.a = false;
                        dVar.b = exception.getMessage();
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (outputStream2 != null) {
                            outputStream2.close();
                        }
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                        if (byteArrayInputStream3 != null) {
                            byteArrayInputStream3.close();
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return dVar;
                    } catch (Throwable th32) {
                        outputStream = outputStream2;
                        inputStream2 = null;
                        byteArrayInputStream = byteArrayInputStream2;
                        httpURLConnection = httpURLConnection2;
                        th = th32;
                        if (inputStream2 != null) {
                            inputStream2.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (byteArrayOutputStream3 != null) {
                            byteArrayOutputStream3.close();
                        }
                        if (byteArrayInputStream != null) {
                            byteArrayInputStream.close();
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        throw th;
                    }
                }
                try {
                    if (httpURLConnection2.getResponseCode() == 200) {
                        inputStream2 = httpURLConnection2.getInputStream();
                        try {
                            byteArrayOutputStream2 = new ByteArrayOutputStream();
                            try {
                                bArr = new byte[2048];
                                while (true) {
                                    int read2 = inputStream2.read(bArr);
                                    if (read2 == -1) {
                                        break;
                                    }
                                    byteArrayOutputStream2.write(bArr, 0, read2);
                                }
                                byteArrayOutputStream2.flush();
                                bArr = byteArrayOutputStream2.toByteArray();
                                dVar.a = true;
                                dVar.b = new String(bArr);
                            } catch (Exception e42) {
                                byteArrayInputStream3 = byteArrayInputStream;
                                outputStream2 = outputStream;
                                inputStream = inputStream2;
                                HttpURLConnection httpURLConnection3 = httpURLConnection2;
                                exception = e42;
                                byteArrayOutputStream = byteArrayOutputStream2;
                                httpURLConnection = httpURLConnection3;
                            } catch (Throwable th322) {
                                byteArrayOutputStream3 = byteArrayOutputStream2;
                                httpURLConnection = httpURLConnection2;
                                th = th322;
                            }
                        } catch (Exception e422) {
                            httpURLConnection = httpURLConnection2;
                            exception = e422;
                            byteArrayOutputStream = null;
                            byteArrayInputStream3 = byteArrayInputStream;
                            outputStream2 = outputStream;
                            inputStream = inputStream2;
                            exception.printStackTrace();
                            dVar.a = false;
                            dVar.b = exception.getMessage();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (outputStream2 != null) {
                                outputStream2.close();
                            }
                            if (byteArrayOutputStream != null) {
                                byteArrayOutputStream.close();
                            }
                            if (byteArrayInputStream3 != null) {
                                byteArrayInputStream3.close();
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            return dVar;
                        } catch (Throwable th3222) {
                            httpURLConnection = httpURLConnection2;
                            th = th3222;
                            if (inputStream2 != null) {
                                inputStream2.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            if (byteArrayOutputStream3 != null) {
                                byteArrayOutputStream3.close();
                            }
                            if (byteArrayInputStream != null) {
                                byteArrayInputStream.close();
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            throw th;
                        }
                    }
                    byteArrayOutputStream2 = null;
                    inputStream2 = null;
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (byteArrayOutputStream2 != null) {
                        byteArrayOutputStream2.close();
                    }
                    if (byteArrayInputStream != null) {
                        byteArrayInputStream.close();
                    }
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                } catch (Exception e4222) {
                    httpURLConnection = httpURLConnection2;
                    exception = e4222;
                    byteArrayOutputStream = null;
                    ByteArrayInputStream byteArrayInputStream4 = byteArrayInputStream;
                    outputStream2 = outputStream;
                    inputStream = null;
                    byteArrayInputStream3 = byteArrayInputStream4;
                    exception.printStackTrace();
                    dVar.a = false;
                    dVar.b = exception.getMessage();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream2 != null) {
                        outputStream2.close();
                    }
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    if (byteArrayInputStream3 != null) {
                        byteArrayInputStream3.close();
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return dVar;
                } catch (Throwable th32222) {
                    httpURLConnection = httpURLConnection2;
                    inputStream2 = null;
                    th = th32222;
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (byteArrayOutputStream3 != null) {
                        byteArrayOutputStream3.close();
                    }
                    if (byteArrayInputStream != null) {
                        byteArrayInputStream.close();
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th;
                }
            } catch (Exception e42222) {
                httpURLConnection = httpURLConnection2;
                outputStream2 = null;
                inputStream = null;
                exception = e42222;
                byteArrayOutputStream = null;
                exception.printStackTrace();
                dVar.a = false;
                dVar.b = exception.getMessage();
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream2 != null) {
                    outputStream2.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (byteArrayInputStream3 != null) {
                    byteArrayInputStream3.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return dVar;
            } catch (Throwable th322222) {
                byteArrayInputStream = null;
                httpURLConnection = httpURLConnection2;
                outputStream = null;
                inputStream2 = null;
                th = th322222;
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (byteArrayOutputStream3 != null) {
                    byteArrayOutputStream3.close();
                }
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                throw th;
            }
        } catch (Exception e5) {
            exception = e5;
            byteArrayOutputStream = null;
            httpURLConnection = null;
            outputStream2 = null;
            inputStream = null;
            exception.printStackTrace();
            dVar.a = false;
            dVar.b = exception.getMessage();
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream2 != null) {
                outputStream2.close();
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            if (byteArrayInputStream3 != null) {
                byteArrayInputStream3.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return dVar;
        } catch (Throwable th4) {
            th = th4;
            byteArrayInputStream = null;
            httpURLConnection = null;
            outputStream = null;
            inputStream2 = null;
            if (inputStream2 != null) {
                inputStream2.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (byteArrayOutputStream3 != null) {
                byteArrayOutputStream3.close();
            }
            if (byteArrayInputStream != null) {
                byteArrayInputStream.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw th;
        }
        return dVar;
    }

    public static d post$12ace190(String str, String str2) {
        return open$12ace190(str, str2);
    }

    public String getVVUid(Context context) {
        String str = "emt";
        try {
            str = cn.com.iresearch.mapptracker.util.a.a(DataProvider.getVVUid(), d.a(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private static void g(Context context) {
        File file = new File(d.e(context));
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file2 : listFiles) {
                    long parseLong;
                    String name = file2.getName();
                    try {
                        parseLong = Long.parseLong(name);
                    } catch (NumberFormatException e) {
                        parseLong = 0;
                    }
                    if (Math.abs(System.currentTimeMillis() - parseLong) > 259200000 || 0 == parseLong) {
                        file2.delete();
                        d.a("delete history data:" + name);
                    }
                }
            }
        }
    }

    static /* synthetic */ void e(Context context) {
        if (d.b(context)) {
            a.a(context, "tsc", Integer.valueOf(1));
            if (d.c(context)) {
                long longValue;
                a(context, i(context).toString(), false);
                d.a("发送日数据成功");
                try {
                    longValue = Long.valueOf(d.a()).longValue();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    longValue = 0;
                }
                a.a(context, "ndd", Long.valueOf(longValue));
            }
        }
    }

    static /* synthetic */ void a(Context context) {
        if (d.c(context)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("lct", 0);
            long currentTimeMillis = System.currentTimeMillis() / 1000;
            if (Math.abs(currentTimeMillis - sharedPreferences.getLong("lct", 0)) >= LetvConstant.VIP_OVERDUE_TIME) {
                sharedPreferences.edit().putLong("lct", currentTimeMillis).commit();
                d open$12ace190 = open$12ace190("http://m.irs01.com/cfg/appkey-" + ((String) a.b(context, IniFile.APPKEY, "")), null);
                if (open$12ace190.a) {
                    Object obj = open$12ace190.b;
                    "context=" + obj;
                    try {
                        if (!TextUtils.isEmpty(obj)) {
                            long parseLong;
                            JSONObject jSONObject = new JSONObject(obj);
                            String str = "dauurl";
                            a.a(context, str, "http://" + jSONObject.getString("sip") + "/rec/se?_iwt_t=i&sv=2");
                            try {
                                parseLong = Long.parseLong(jSONObject.getString("itl"));
                            } catch (Exception e) {
                                e.printStackTrace();
                                parseLong = 1;
                            }
                            a.a(context, "itl", Long.valueOf(parseLong));
                            CharSequence string = jSONObject.getString("pd");
                            if (!TextUtils.isEmpty(string)) {
                                a.a(context, "Pd", string);
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    private static JSONObject h(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            jSONObject.put("event_list", new JSONArray());
            jSONObject.put("header", d.d(context));
            long longValue = ((Long) a.b(context, d.b("run_time"), Long.valueOf(0))).longValue();
            long longValue2 = ((Long) a.b(context, d.b("count"), Long.valueOf(0))).longValue();
            d.a("this_run_time:" + longValue);
            jSONObject.put("open_count", longValue2 + 1);
            jSONObject.put("page_count", longValue2 + 1);
            jSONObject.put("run_time", longValue);
            a.a(context, d.b("run_time"), Long.valueOf(0));
            a.a(context, d.b("count"), Long.valueOf(0));
            jSONObject.put("page_list", jSONArray);
            jSONObject.put("lat", "");
            jSONObject.put("lng", d.b() ? "1" : "0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private static JSONObject i(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            jSONObject.put("event_list", new JSONArray());
            jSONObject.put("header", d.d(context));
            jSONObject.put("open_count", 0);
            jSONObject.put("page_count", 0);
            jSONObject.put("run_time", 0);
            jSONObject.put("page_list", jSONArray);
            jSONObject.put("lat", "");
            jSONObject.put("lng", d.b() ? "1" : "0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    static /* synthetic */ void d(Context context) {
        a.a(context, d.b("run_time"), Long.valueOf(((Long) a.b(context, d.b("run_time"), Long.valueOf(0))).longValue() + g));
        g = 0;
    }

    static /* synthetic */ void f(Context context) {
        try {
            JSONObject h = h(context);
            long longValue = Long.valueOf(((Long) a.b(context, "itl", Long.valueOf(3))).longValue() * 60).longValue();
            String b = d.b("ck_upload_time");
            SharedPreferences sharedPreferences = context.getSharedPreferences(d.b(context.getPackageName()), 0);
            Editor edit = sharedPreferences.edit();
            long currentTimeMillis = System.currentTimeMillis() / 1000;
            Object obj = Math.abs(currentTimeMillis - sharedPreferences.getLong(b, 0)) > longValue ? 1 : null;
            if (obj != null) {
                edit.putLong(b, currentTimeMillis).commit();
            }
            if (obj == null) {
                long optLong = h.optLong("run_time", 0);
                long optLong2 = h.optLong("open_count", 0);
                a.a(context, d.b("run_time"), Long.valueOf(optLong));
                a.a(context, d.b("count"), Long.valueOf(optLong2));
                d.a(new StringBuilder(String.valueOf(longValue)).append("s cancel upload：total_run_time:").append(optLong).append(" total_count:").append(optLong2).toString());
                return;
            }
            Log.i("sout", "upload total_run_time:" + h.optLong("run_time", 0));
            if (d.c(context)) {
                d.a("new upload start...");
                a(context, h.toString(), false);
                g(context);
                j(context);
                return;
            }
            d.a("网络不畅通");
            d.a(d.a(context, h.toString()), d.e(context) + System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void j(Context context) {
        File file = new File(d.e(context));
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file2 : listFiles) {
                    a(context, d.a(file2), true);
                    file2.delete();
                }
            }
        }
    }

    private static d a(Context context, String str, boolean z) {
        if (!z) {
            str = d.a(context, str);
        }
        Log.i("sout", str);
        String str2 = "p=" + ((String) a.b(context, IniFile.APPKEY, "")) + "&etype=2&msg=" + str;
        if (b != null) {
            b.preSend();
        }
        StringBuilder stringBuilder = new StringBuilder(String.valueOf((String) a.b(context, "dauurl", "http://m.irs01.com/rec/se?_iwt_t=i&sv=2")));
        int intValue = ((Integer) a.b(context, "tsc", Integer.valueOf(1))).intValue();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("&sdkver=2.3.4");
        stringBuffer.append("&count=" + intValue);
        d post$12ace190 = post$12ace190(stringBuilder.append(stringBuffer.toString()).toString(), str2);
        if (post$12ace190.a) {
            a.a(context, "tsc", Integer.valueOf(((Integer) a.b(context, "tsc", Integer.valueOf(1))).intValue() + 1));
            d.a("mApptracker数据上传成功");
            if (b != null) {
                b.sendSuccess();
            }
        } else {
            d.a("mApptracker数据上传失败：" + post$12ace190.b);
            if (b != null) {
                b.sendFail(post$12ace190.b);
            }
        }
        return post$12ace190;
    }

    private static boolean k(Context context) {
        try {
            if (d.b(context, "android.permission.GET_TASKS")) {
                List runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1);
                if (!(runningTasks == null || runningTasks.isEmpty())) {
                    return ((RunningTaskInfo) runningTasks.get(0)).topActivity.getPackageName().equals(context.getPackageName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean l(Context context) {
        try {
            return ((Boolean) PowerManager.class.getMethod("isScreenOn", new Class[0]).invoke((PowerManager) context.getSystemService("power"), new Object[0])).booleanValue();
        } catch (Exception e) {
            return true;
        }
    }
}
