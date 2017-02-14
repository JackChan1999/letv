package cn.jpush.android.helpers;

import android.content.Context;
import android.os.RemoteException;
import cn.jpush.android.e;
import cn.jpush.android.util.af;
import cn.jpush.android.util.z;

public final class b {
    public static void a(Context context, String str, int i) {
        try {
            e.o.b(str, i);
        } catch (RemoteException e) {
            z.d();
        } catch (NullPointerException e2) {
            z.a();
            af.a(context, str, i);
        }
    }

    public static void a(Context context, String str, long j) {
        try {
            e.o.b(str, j);
        } catch (RemoteException e) {
            z.d();
        } catch (NullPointerException e2) {
            z.a();
            af.a(context, str, j);
        }
    }

    public static void a(Context context, String str, String str2) {
        try {
            e.o.b(str, str2);
        } catch (RemoteException e) {
            z.d();
        } catch (NullPointerException e2) {
            z.a();
            af.a(context, str, str2);
        }
    }

    public static void a(Context context, String str, boolean z) {
        try {
            e.o.b(str, z);
        } catch (RemoteException e) {
            z.d();
        } catch (NullPointerException e2) {
            z.a();
            af.a(context, str, z);
        }
    }

    public static int b(Context context, String str, int i) {
        try {
            return e.o.a(str, i);
        } catch (RemoteException e) {
            z.d();
            return i;
        } catch (NullPointerException e2) {
            z.a();
            return af.b(context, str, i);
        }
    }

    public static long b(Context context, String str, long j) {
        long j2 = 0;
        try {
            return e.o.a(str, 0);
        } catch (RemoteException e) {
            z.d();
            return j2;
        } catch (NullPointerException e2) {
            z.a();
            return af.b(context, str, j2);
        }
    }

    public static String b(Context context, String str, String str2) {
        try {
            return e.o.a(str, str2);
        } catch (RemoteException e) {
            z.d();
            return str2;
        } catch (NullPointerException e2) {
            z.a();
            return af.b(context, str, str2);
        }
    }

    public static boolean b(Context context, String str, boolean z) {
        try {
            return e.o.a(str, z);
        } catch (RemoteException e) {
            z.d();
            return z;
        } catch (NullPointerException e2) {
            z.a();
            return af.b(context, str, z);
        }
    }
}
