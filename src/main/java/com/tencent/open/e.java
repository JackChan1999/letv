package com.tencent.open;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import com.tencent.open.a.f;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/* compiled from: ProGuard */
public class e extends AsyncTask<Bitmap, Void, HashMap<String, Object>> {
    private static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd-HHmmss", Locale.CHINA);
    private a b;

    /* compiled from: ProGuard */
    public interface a {
        void a(String str);

        void b(String str);
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return a((Bitmap[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        a((HashMap) obj);
    }

    public e(a aVar) {
        this.b = aVar;
    }

    protected HashMap<String, Object> a(Bitmap... bitmapArr) {
        HashMap<String, Object> hashMap = new HashMap();
        try {
            Bitmap bitmap = bitmapArr[0];
            if (bitmap != null) {
                Object b;
                String str = "";
                if (bitmap.getWidth() > 320 || bitmap.getHeight() > 320) {
                    Bitmap a = a(bitmap);
                    b = b(a);
                    a.recycle();
                } else {
                    b = b(bitmap);
                }
                bitmap.recycle();
                hashMap.put("ResultType", Integer.valueOf(1));
                hashMap.put("ResultValue", b);
            }
        } catch (Exception e) {
            hashMap.put("ResultType", Integer.valueOf(0));
            hashMap.put("ResultValue", e.getMessage());
        }
        return hashMap;
    }

    protected void a(HashMap<String, Object> hashMap) {
        if (((Integer) hashMap.get("ResultType")).intValue() == 1) {
            this.b.a((String) hashMap.get("ResultValue"));
        } else {
            this.b.b((String) hashMap.get("ResultValue"));
        }
        super.onPostExecute(hashMap);
    }

    private Bitmap a(Bitmap bitmap) {
        int i = 1;
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, byteArrayOutputStream);
        if (byteArrayOutputStream.toByteArray().length / 1024 > 1024) {
            byteArrayOutputStream.reset();
            bitmap.compress(CompressFormat.JPEG, 50, byteArrayOutputStream);
        }
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(byteArrayInputStream, null, options);
        options.inJustDecodeBounds = false;
        int a = a(options, 320, 320);
        if (a > 0) {
            i = a;
        }
        f.c("comp", "comp be=" + i);
        options.inSampleSize = i;
        return BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), null, options);
    }

    private int a(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 <= i2 && i4 <= i) {
            return 1;
        }
        int round = Math.round(((float) i3) / ((float) i2));
        i3 = Math.round(((float) i4) / ((float) i));
        return round < i3 ? round : i3;
    }

    public static void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                File file = new File(str);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
            }
        }
    }

    private String b(Bitmap bitmap) {
        OutputStream outputStream;
        Throwable th;
        FileOutputStream fileOutputStream = null;
        String str = "";
        try {
            str = a(System.currentTimeMillis()) + ".png";
            String str2 = b() + File.separator + ".AppCenterWebBuffer";
            str = str2 + File.separator + str;
            File file = new File(str2);
            if (file.exists() || !file.mkdirs()) {
                file = new File(str);
            } else {
                file = new File(str);
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            OutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                bitmap.compress(CompressFormat.PNG, 100, fileOutputStream2);
                fileOutputStream2.flush();
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e) {
                    }
                }
            } catch (Exception e2) {
                outputStream = fileOutputStream2;
                try {
                    str = "";
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                        }
                    }
                    return str;
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                outputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (Exception e5) {
            str = "";
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            return str;
        }
        return str;
    }

    private String b() {
        String str = ".";
        if (Environment.getExternalStorageState().equals("mounted")) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        if (new File("/mnt/sdcard-ext").isDirectory()) {
            return "/mnt/sdcard-ext";
        }
        return str;
    }

    private String a(long j) {
        return a.format(new Date(j));
    }

    public static boolean a() {
        if (Environment.getExternalStorageState().equals("mounted") || new File("/mnt/sdcard-ext").isDirectory()) {
            return true;
        }
        return false;
    }
}
