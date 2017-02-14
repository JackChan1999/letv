package u.aly;

import android.util.Log;
import com.umeng.analytics.a;
import java.util.Formatter;
import java.util.Locale;

/* compiled from: MLog */
public class bv {
    public static boolean a = false;
    private static String b = a.e;

    private bv() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void a(String str) {
        b = str;
    }

    public static String a() {
        return b;
    }

    public static void a(Locale locale, String str, Object... objArr) {
        try {
            c(b, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void b(Locale locale, String str, Object... objArr) {
        try {
            b(b, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void c(Locale locale, String str, Object... objArr) {
        try {
            e(b, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void d(Locale locale, String str, Object... objArr) {
        try {
            a(b, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void e(Locale locale, String str, Object... objArr) {
        try {
            d(b, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void a(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                c(b, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            c(str, str2, null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void b(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                b(b, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            b(str, str2, null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void c(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                e(b, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            e(str, str2, null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void d(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                a(b, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            a(str, str2, null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void e(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                d(b, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            d(str, str2, null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void a(Throwable th) {
        c(b, null, th);
    }

    public static void b(Throwable th) {
        a(b, null, th);
    }

    public static void c(Throwable th) {
        d(b, null, th);
    }

    public static void d(Throwable th) {
        b(b, null, th);
    }

    public static void e(Throwable th) {
        e(b, null, th);
    }

    public static void a(String str, Throwable th) {
        c(b, str, th);
    }

    public static void b(String str, Throwable th) {
        a(b, str, th);
    }

    public static void c(String str, Throwable th) {
        d(b, str, th);
    }

    public static void d(String str, Throwable th) {
        b(b, str, th);
    }

    public static void e(String str, Throwable th) {
        e(b, str, th);
    }

    public static void b(String str) {
        a(b, str, null);
    }

    public static void c(String str) {
        b(b, str, null);
    }

    public static void d(String str) {
        c(b, str, null);
    }

    public static void e(String str) {
        d(b, str, null);
    }

    public static void f(String str) {
        e(b, str, null);
    }

    public static void a(String str, String str2, Throwable th) {
        if (!a) {
            return;
        }
        if (th != null) {
            if (str2 != null) {
                Log.v(str, th.toString() + ":  [" + str2 + "]");
            } else {
                Log.v(str, th.toString());
            }
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                Log.v(str, "        at  " + stackTraceElement.toString());
            }
        } else if (str2 == null) {
            try {
                Log.w(str, "the msg is null!");
            } catch (Throwable th2) {
            }
        } else {
            Log.v(str, str2);
        }
    }

    public static void b(String str, String str2, Throwable th) {
        if (!a) {
            return;
        }
        if (th != null) {
            if (str2 != null) {
                Log.d(str, th.toString() + ":  [" + str2 + "]");
            } else {
                Log.d(str, th.toString());
            }
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                Log.d(str, "        at  " + stackTraceElement.toString());
            }
        } else if (str2 == null) {
            try {
                Log.w(str, "the msg is null!");
            } catch (Throwable th2) {
            }
        } else {
            Log.d(str, str2);
        }
    }

    public static void c(String str, String str2, Throwable th) {
        if (!a) {
            return;
        }
        if (th != null) {
            if (str2 != null) {
                Log.i(str, th.toString() + ":  [" + str2 + "]");
            } else {
                Log.i(str, th.toString());
            }
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                Log.i(str, "        at  " + stackTraceElement.toString());
            }
        } else if (str2 == null) {
            try {
                Log.w(str, "the msg is null!");
            } catch (Throwable th2) {
            }
        } else {
            Log.i(str, str2);
        }
    }

    public static void d(String str, String str2, Throwable th) {
        if (!a) {
            return;
        }
        if (th != null) {
            if (str2 != null) {
                Log.w(str, th.toString() + ":  [" + str2 + "]");
            } else {
                Log.w(str, th.toString());
            }
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                Log.w(str, "        at  " + stackTraceElement.toString());
            }
        } else if (str2 == null) {
            try {
                Log.w(str, "the msg is null!");
            } catch (Throwable th2) {
            }
        } else {
            Log.w(str, str2);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (!a) {
            return;
        }
        if (th != null) {
            if (str2 != null) {
                Log.e(str, th.toString() + ":  [" + str2 + "]");
            } else {
                Log.e(str, th.toString());
            }
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                Log.e(str, "        at  " + stackTraceElement.toString());
            }
        } else if (str2 == null) {
            try {
                Log.w(str, "the msg is null!");
            } catch (Throwable th2) {
            }
        } else {
            Log.e(str, str2);
        }
    }
}
