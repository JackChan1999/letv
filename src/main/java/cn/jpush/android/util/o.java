package cn.jpush.android.util;

import android.content.Context;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public final class o {
    private static final String[] z;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r3 = 2;
        r2 = 1;
        r1 = 0;
        r0 = 4;
        r5 = new java.lang.String[r0];
        r4 = "\u0011\u001fB0D<%`\b\u0001'>";
        r0 = -1;
        r6 = r5;
        r7 = r5;
        r5 = r1;
    L_0x000c:
        r4 = r4.toCharArray();
        r8 = r4.length;
        if (r8 > r2) goto L_0x006c;
    L_0x0013:
        r9 = r1;
    L_0x0014:
        r10 = r4;
        r11 = r9;
        r14 = r8;
        r8 = r4;
        r4 = r14;
    L_0x0019:
        r13 = r8[r9];
        r12 = r11 % 5;
        switch(r12) {
            case 0: goto L_0x0060;
            case 1: goto L_0x0063;
            case 2: goto L_0x0066;
            case 3: goto L_0x0069;
            default: goto L_0x0020;
        };
    L_0x0020:
        r12 = 100;
    L_0x0022:
        r12 = r12 ^ r13;
        r12 = (char) r12;
        r8[r9] = r12;
        r9 = r11 + 1;
        if (r4 != 0) goto L_0x002e;
    L_0x002a:
        r8 = r10;
        r11 = r9;
        r9 = r4;
        goto L_0x0019;
    L_0x002e:
        r8 = r4;
        r4 = r10;
    L_0x0030:
        if (r8 > r9) goto L_0x0014;
    L_0x0032:
        r8 = new java.lang.String;
        r8.<init>(r4);
        r4 = r8.intern();
        switch(r0) {
            case 0: goto L_0x0047;
            case 1: goto L_0x0051;
            case 2: goto L_0x005b;
            default: goto L_0x003e;
        };
    L_0x003e:
        r6[r5] = r4;
        r0 = ">)z\u0015\u000b1pm\u000e\u0001>>k4\u00102&H\u0015\b:j#\\\u00026&k,\u0005+\"4";
        r4 = r0;
        r5 = r2;
        r6 = r7;
        r0 = r1;
        goto L_0x000c;
    L_0x0047:
        r6[r5] = r4;
        r0 = "sjm\u0013\n+/`\b^";
        r4 = r0;
        r5 = r3;
        r6 = r7;
        r0 = r2;
        goto L_0x000c;
    L_0x0051:
        r6[r5] = r4;
        r4 = 3;
        r0 = "\n\u001eHQ\\";
        r5 = r4;
        r6 = r7;
        r4 = r0;
        r0 = r3;
        goto L_0x000c;
    L_0x005b:
        r6[r5] = r4;
        z = r7;
        return;
    L_0x0060:
        r12 = 95;
        goto L_0x0022;
    L_0x0063:
        r12 = 74;
        goto L_0x0022;
    L_0x0066:
        r12 = 14;
        goto L_0x0022;
    L_0x0069:
        r12 = 124; // 0x7c float:1.74E-43 double:6.13E-322;
        goto L_0x0022;
    L_0x006c:
        r9 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.o.<clinit>():void");
    }

    public static ArrayList<String> a(InputStream inputStream) {
        ArrayList<String> arrayList = new ArrayList();
        try {
            Reader inputStreamReader = new InputStreamReader(inputStream, z[3]);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 2048);
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                readLine = readLine.trim();
                if (!"".equals(readLine)) {
                    arrayList.add(readLine);
                }
            }
            inputStreamReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.getMessage();
            z.e();
        }
        return arrayList;
    }

    public static void a(String str) {
        File file = new File(str);
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    a(file2.getAbsolutePath());
                    file2.delete();
                }
            }
            file.delete();
        }
    }

    public static boolean a(String str, String str2, Context context) {
        FileOutputStream fileOutputStream;
        Throwable th;
        if (context == null) {
            throw new IllegalArgumentException(z[0]);
        }
        new StringBuilder(z[1]).append(str).append(z[2]).append(str2);
        z.a();
        if (!(TextUtils.isEmpty(str) || TextUtils.isEmpty(str2))) {
            try {
                File file = new File(str);
                if (!file.exists()) {
                    file.createNewFile();
                }
                try {
                    fileOutputStream = new FileOutputStream(file);
                    try {
                        fileOutputStream.write(str2.getBytes(z[3]));
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        return true;
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = null;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            } catch (IOException e) {
                z.g();
            }
        }
        return false;
    }

    public static boolean a(String str, byte[] bArr) {
        FileOutputStream fileOutputStream;
        Throwable th;
        if (TextUtils.isEmpty(str) || bArr.length <= 0) {
            return false;
        }
        File file = new File(str);
        if (!file.exists()) {
            file.createNewFile();
        }
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(bArr);
                fileOutputStream.flush();
                fileOutputStream.close();
                return true;
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
    }

    public static String b(String str) {
        String str2 = ".";
        if (!ai.a(str)) {
            int lastIndexOf = str.lastIndexOf(str2);
            int length = str.length();
            if (lastIndexOf > 0 && lastIndexOf + 1 != length) {
                return str.substring(lastIndexOf, str.length());
            }
        }
        return "";
    }

    public static String c(String str) {
        return ai.a(str) ? "" : str.substring(str.lastIndexOf("/") + 1, str.length());
    }
}
