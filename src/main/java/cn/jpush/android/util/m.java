package cn.jpush.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;

public final class m {
    public static final String a;
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 7;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u0001>";
        r0 = -1;
        r4 = r3;
    L_0x0008:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002d;
    L_0x0011:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0016:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x00a1;
            case 1: goto L_0x00a5;
            case 2: goto L_0x00a9;
            case 3: goto L_0x00ad;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
    L_0x001f:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002b;
    L_0x0027:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0016;
    L_0x002b:
        r5 = r1;
        r1 = r7;
    L_0x002d:
        if (r5 > r6) goto L_0x0011;
    L_0x002f:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0043;
            case 1: goto L_0x004b;
            case 2: goto L_0x0054;
            case 3: goto L_0x005c;
            case 4: goto L_0x0064;
            case 5: goto L_0x006d;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u0001=";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u0001Nb\u0005\u001fAfhN\tO{mN";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004b:
        r3[r2] = r1;
        r2 = 3;
        r1 = "zgiA\u0019O}k\u0004\u0019\u000eke\u0013W\u000e";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "Jf~";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "Jnx\u0000MJf~[M";
        r0 = 4;
        r3 = r4;
        goto L_0x0008;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "pT<LTst?\u001cI";
        r0 = 5;
        r3 = r4;
        goto L_0x0008;
    L_0x006d:
        r3[r2] = r1;
        z = r4;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = java.io.File.separator;
        r6 = r0.append(r1);
        r0 = "\\fo\t";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x00bf;
    L_0x0087:
        r3 = r0;
        r4 = r2;
        r11 = r1;
        r1 = r0;
        r0 = r11;
    L_0x008c:
        r7 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x00b1;
            case 1: goto L_0x00b4;
            case 2: goto L_0x00b7;
            case 3: goto L_0x00ba;
            default: goto L_0x0093;
        };
    L_0x0093:
        r5 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
    L_0x0095:
        r5 = r5 ^ r7;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x00bd;
    L_0x009d:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x008c;
    L_0x00a1:
        r9 = 46;
        goto L_0x001f;
    L_0x00a5:
        r9 = 15;
        goto L_0x001f;
    L_0x00a9:
        r9 = 12;
        goto L_0x001f;
    L_0x00ad:
        r9 = 97;
        goto L_0x001f;
    L_0x00b1:
        r5 = 46;
        goto L_0x0095;
    L_0x00b4:
        r5 = 15;
        goto L_0x0095;
    L_0x00b7:
        r5 = 12;
        goto L_0x0095;
    L_0x00ba:
        r5 = 97;
        goto L_0x0095;
    L_0x00bd:
        r1 = r0;
        r0 = r3;
    L_0x00bf:
        if (r1 > r2) goto L_0x0087;
    L_0x00c1:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        r0 = r6.append(r0);
        r1 = java.io.File.separator;
        r0 = r0.append(r1);
        r0 = r0.toString();
        a = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.m.<clinit>():void");
    }

    public static String a(Context context) {
        if (!a.a()) {
            return "";
        }
        String str = a(Environment.getExternalStorageDirectory().getAbsolutePath()) + e(context) + z[0];
        if (!new File(str).isDirectory()) {
            d(context);
        }
        return str + File.separator;
    }

    public static String a(Context context, String str) {
        String str2 = context.getFilesDir() + "/" + str;
        try {
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str2 + "/";
    }

    private static String a(String str) {
        return TextUtils.isEmpty(str) ? "" : str.lastIndexOf(File.separator) != 0 ? str + File.separator : str;
    }

    private static boolean a(File file) {
        boolean z = false;
        try {
            if (!file.exists()) {
                return z;
            }
            if (file.isFile()) {
                return file.delete();
            }
            String[] list = file.list();
            int length = list.length;
            for (int i = z; i < length; i++) {
                File file2 = new File(file, list[i]);
                if (file2.isDirectory()) {
                    a(file2);
                } else {
                    file2.delete();
                }
            }
            return file.delete();
        } catch (Exception e) {
            z.e();
            return z;
        }
    }

    public static String b(Context context) {
        if (!a.a()) {
            return "";
        }
        String str = a(Environment.getExternalStorageDirectory().getAbsolutePath()) + e(context) + z[1];
        if (!new File(str).isDirectory()) {
            d(context);
        }
        return str + "/";
    }

    public static String b(Context context, String str) {
        try {
            File file;
            if (a.a()) {
                String str2 = Environment.getExternalStorageDirectory().getAbsolutePath() + z[2] + context.getPackageName() + File.separator + str + File.separator;
                file = new File(str2);
                if (file.exists()) {
                    return str2;
                }
                file.mkdirs();
                return str2;
            }
            file = new File(context.getFilesDir() + a);
            if (file.exists() && file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles.length > 10) {
                    Arrays.sort(listFiles, new n());
                    a(listFiles[listFiles.length - 1]);
                }
            }
            return c(context, str);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String c(Context context, String str) {
        String str2 = context.getFilesDir() + a + str;
        try {
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str2 + "/";
    }

    public static void c(Context context) {
        try {
            for (File file : context.getFilesDir().listFiles()) {
                CharSequence name = file.getName();
                if (TextUtils.isEmpty(name) ? false : Pattern.compile(z[6]).matcher(name).matches()) {
                    o.a(file.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void d(Context context) {
        try {
            if (a.a()) {
                String e = e(context);
                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                File file = new File(absolutePath + e);
                if (!file.isDirectory()) {
                    file.mkdirs();
                }
                file = new File(absolutePath + e + z[0]);
                if (!file.isDirectory()) {
                    file.mkdirs();
                }
                file = new File(absolutePath + e + z[1]);
                if (!file.isDirectory()) {
                    file.mkdirs();
                }
                file = new File(absolutePath + e + File.separator + context.getPackageName());
                if (!file.isDirectory()) {
                    file.mkdirs();
                    return;
                }
                return;
            }
            z.e();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static String e(Context context) {
        String str;
        Exception e;
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Object string = defaultSharedPreferences.getString(z[4], "");
        if (TextUtils.isEmpty(string)) {
            String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String str2 = z[2];
            File file = new File(absolutePath + str2);
            try {
                if (file.exists()) {
                    ArrayList arrayList = new ArrayList();
                    for (File file2 : file.listFiles()) {
                        if (file2.isDirectory()) {
                            arrayList.add(file2.getName());
                            new StringBuilder(z[5]).append(file2.getName());
                            z.a();
                        }
                    }
                    int size = arrayList.size();
                    if (size > 0) {
                        try {
                            str = str2 + ((String) arrayList.get(size / 2));
                        } catch (Exception e2) {
                            e = e2;
                            e.printStackTrace();
                            new StringBuilder(z[3]).append(str);
                            z.c();
                            defaultSharedPreferences.edit().putString(z[4], str).commit();
                            return string;
                        }
                    }
                    str = str2 + UUID.randomUUID().toString().substring(0, 5);
                } else {
                    file.mkdirs();
                    str = str2 + UUID.randomUUID().toString().substring(0, 5);
                }
            } catch (Exception e3) {
                Exception exception = e3;
                str = null;
                e = exception;
                e.printStackTrace();
                new StringBuilder(z[3]).append(str);
                z.c();
                defaultSharedPreferences.edit().putString(z[4], str).commit();
                return string;
            }
            new StringBuilder(z[3]).append(str);
            z.c();
            defaultSharedPreferences.edit().putString(z[4], str).commit();
        }
        return string;
    }
}
