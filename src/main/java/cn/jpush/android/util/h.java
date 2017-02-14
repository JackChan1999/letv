package cn.jpush.android.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public final class h {
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
        r4 = 3;
        r3 = 2;
        r2 = 1;
        r1 = 0;
        r0 = 5;
        r6 = new java.lang.String[r0];
        r5 = "xbA>1{&\u0000v=5e\u0018w=a&\u0012w4p<T";
        r0 = -1;
        r7 = r6;
        r8 = r6;
        r6 = r1;
    L_0x000e:
        r5 = r5.toCharArray();
        r9 = r5.length;
        if (r9 > r2) goto L_0x0076;
    L_0x0015:
        r10 = r1;
    L_0x0016:
        r11 = r5;
        r12 = r10;
        r15 = r9;
        r9 = r5;
        r5 = r15;
    L_0x001b:
        r14 = r9[r10];
        r13 = r12 % 5;
        switch(r13) {
            case 0: goto L_0x006b;
            case 1: goto L_0x006e;
            case 2: goto L_0x0070;
            case 3: goto L_0x0073;
            default: goto L_0x0022;
        };
    L_0x0022:
        r13 = 88;
    L_0x0024:
        r13 = r13 ^ r14;
        r13 = (char) r13;
        r9[r10] = r13;
        r10 = r12 + 1;
        if (r5 != 0) goto L_0x0030;
    L_0x002c:
        r9 = r11;
        r12 = r10;
        r10 = r5;
        goto L_0x001b;
    L_0x0030:
        r9 = r5;
        r5 = r11;
    L_0x0032:
        if (r9 > r10) goto L_0x0016;
    L_0x0034:
        r9 = new java.lang.String;
        r9.<init>(r5);
        r5 = r9.intern();
        switch(r0) {
            case 0: goto L_0x0049;
            case 1: goto L_0x0053;
            case 2: goto L_0x005c;
            case 3: goto L_0x0066;
            default: goto L_0x0040;
        };
    L_0x0040:
        r7[r6] = r5;
        r0 = "5b\u001b>6zrTp=pbT}0pe\u001f>\u0015Q3T}7qcX>*pr\u0001l65r\u0006k=";
        r5 = r0;
        r6 = r2;
        r7 = r8;
        r0 = r1;
        goto L_0x000e;
    L_0x0049:
        r7[r6] = r5;
        r0 = "xbA>>gi\u0019>+pt\u0002{*5u\u001dz=/&";
        r5 = r0;
        r6 = r3;
        r7 = r8;
        r0 = r2;
        goto L_0x000e;
    L_0x0053:
        r7[r6] = r5;
        r0 = "an\u0011>5q3Tx*zkTm=gp\u0011lx|uN>";
        r5 = r0;
        r6 = r4;
        r7 = r8;
        r0 = r3;
        goto L_0x000e;
    L_0x005c:
        r7[r6] = r5;
        r5 = 4;
        r0 = "XBA";
        r6 = r5;
        r7 = r8;
        r5 = r0;
        r0 = r4;
        goto L_0x000e;
    L_0x0066:
        r7[r6] = r5;
        z = r8;
        return;
    L_0x006b:
        r13 = 21;
        goto L_0x0024;
    L_0x006e:
        r13 = 6;
        goto L_0x0024;
    L_0x0070:
        r13 = 116; // 0x74 float:1.63E-43 double:5.73E-322;
        goto L_0x0024;
    L_0x0073:
        r13 = 30;
        goto L_0x0024;
    L_0x0076:
        r10 = r1;
        goto L_0x0032;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.h.<clinit>():void");
    }

    public static boolean a(String str, File file) {
        new StringBuilder(z[2]).append(str);
        z.b();
        if (str == null || "".equals(str)) {
            new StringBuilder(z[3]).append(str).append(z[1]);
            z.b();
            return true;
        } else if (!file.exists() || file.length() == 0) {
            return false;
        } else {
            String b = b(file);
            new StringBuilder(z[0]).append(b);
            z.b();
            if (b == null || "".equals(b) || !b.equals(str)) {
                z.b();
                return false;
            }
            z.b();
            return true;
        }
    }

    private static byte[] a(File file) {
        InputStream inputStream;
        Throwable th;
        InputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[1024];
                MessageDigest instance = MessageDigest.getInstance(z[4]);
                int read;
                do {
                    read = fileInputStream.read(bArr);
                    if (read > 0) {
                        instance.update(bArr, 0, read);
                    }
                } while (read != -1);
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        z.g();
                        return null;
                    }
                }
                return instance.digest();
            } catch (Exception e2) {
                try {
                    z.g();
                    if (fileInputStream != null) {
                        return null;
                    }
                    try {
                        fileInputStream.close();
                        return null;
                    } catch (IOException e3) {
                        z.g();
                        return null;
                    }
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    inputStream = fileInputStream;
                    th = th3;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e4) {
                            z.g();
                            return null;
                        }
                    }
                    throw th;
                }
            }
        } catch (Exception e5) {
            fileInputStream = null;
            z.g();
            if (fileInputStream != null) {
                return null;
            }
            fileInputStream.close();
            return null;
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
    }

    private static String b(File file) {
        byte[] a = a(file);
        String str = "";
        if (a != null && a.length > 0) {
            for (byte b : a) {
                str = str + Integer.toString((b & 255) + 256, 16).substring(1);
            }
        }
        return str;
    }
}
