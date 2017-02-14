package com.flurry.sdk;

import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class hb {
    private static final String a = hb.class.getSimpleName();
    private static hb b;
    private final Set<String> c = v();
    private final Map<hj, byte[]> d = new HashMap();
    private final hw<ja> e = new hw<ja>(this) {
        final /* synthetic */ hb a;

        {
            this.a = r1;
        }

        public void a(ja jaVar) {
            switch (jaVar.c) {
                case START:
                    if (this.a.c()) {
                        hn.a().b(new jp(this) {
                            final /* synthetic */ AnonymousClass1 a;

                            {
                                this.a = r1;
                            }

                            public void a() {
                                this.a.a.j();
                            }
                        });
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private a f = a.NONE;
    private Info g;
    private String h;
    private byte[] i;

    enum a {
        NONE,
        ADVERTISING,
        DEVICE,
        HASHED_IMEI,
        REPORTED_IDS,
        FINISHED
    }

    public static synchronized hb a() {
        hb hbVar;
        synchronized (hb.class) {
            if (b == null) {
                b = new hb();
            }
            hbVar = b;
        }
        return hbVar;
    }

    public static void b() {
        b = null;
    }

    private hb() {
        hx.a().a("com.flurry.android.sdk.FlurrySessionEvent", this.e);
        hn.a().b(new jp(this) {
            final /* synthetic */ hb a;

            {
                this.a = r1;
            }

            public void a() {
                this.a.i();
            }
        });
    }

    public boolean c() {
        return a.FINISHED.equals(this.f);
    }

    public String d() {
        if (this.g == null) {
            return null;
        }
        return this.g.getId();
    }

    public boolean e() {
        if (this.g != null && this.g.isLimitAdTrackingEnabled()) {
            return false;
        }
        return true;
    }

    public String f() {
        return this.h;
    }

    public byte[] g() {
        return this.i;
    }

    public Map<hj, byte[]> h() {
        return Collections.unmodifiableMap(this.d);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void i() {
        /*
        r5 = this;
    L_0x0000:
        r0 = com.flurry.sdk.hb.a.FINISHED;
        r1 = r5.f;
        r0 = r0.equals(r1);
        if (r0 != 0) goto L_0x0075;
    L_0x000a:
        r0 = com.flurry.sdk.hb.AnonymousClass4.b;
        r1 = r5.f;
        r1 = r1.ordinal();
        r0 = r0[r1];
        switch(r0) {
            case 1: goto L_0x0050;
            case 2: goto L_0x0055;
            case 3: goto L_0x005a;
            case 4: goto L_0x005f;
            case 5: goto L_0x0064;
            default: goto L_0x0017;
        };
    L_0x0017:
        r0 = com.flurry.sdk.hb.AnonymousClass4.b;	 Catch:{ Exception -> 0x0029 }
        r1 = r5.f;	 Catch:{ Exception -> 0x0029 }
        r1 = r1.ordinal();	 Catch:{ Exception -> 0x0029 }
        r0 = r0[r1];	 Catch:{ Exception -> 0x0029 }
        switch(r0) {
            case 2: goto L_0x0025;
            case 3: goto L_0x0069;
            case 4: goto L_0x006d;
            case 5: goto L_0x0071;
            default: goto L_0x0024;
        };	 Catch:{ Exception -> 0x0029 }
    L_0x0024:
        goto L_0x0000;
    L_0x0025:
        r5.j();	 Catch:{ Exception -> 0x0029 }
        goto L_0x0000;
    L_0x0029:
        r0 = move-exception;
        r1 = 4;
        r2 = a;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Exception during id fetch:";
        r3 = r3.append(r4);
        r4 = r5.f;
        r3 = r3.append(r4);
        r4 = ", ";
        r3 = r3.append(r4);
        r0 = r3.append(r0);
        r0 = r0.toString();
        com.flurry.sdk.ib.a(r1, r2, r0);
        goto L_0x0000;
    L_0x0050:
        r0 = com.flurry.sdk.hb.a.ADVERTISING;
        r5.f = r0;
        goto L_0x0017;
    L_0x0055:
        r0 = com.flurry.sdk.hb.a.DEVICE;
        r5.f = r0;
        goto L_0x0017;
    L_0x005a:
        r0 = com.flurry.sdk.hb.a.HASHED_IMEI;
        r5.f = r0;
        goto L_0x0017;
    L_0x005f:
        r0 = com.flurry.sdk.hb.a.REPORTED_IDS;
        r5.f = r0;
        goto L_0x0017;
    L_0x0064:
        r0 = com.flurry.sdk.hb.a.FINISHED;
        r5.f = r0;
        goto L_0x0017;
    L_0x0069:
        r5.k();	 Catch:{ Exception -> 0x0029 }
        goto L_0x0000;
    L_0x006d:
        r5.l();	 Catch:{ Exception -> 0x0029 }
        goto L_0x0000;
    L_0x0071:
        r5.x();	 Catch:{ Exception -> 0x0029 }
        goto L_0x0000;
    L_0x0075:
        r0 = new com.flurry.sdk.hc;
        r0.<init>();
        r1 = com.flurry.sdk.hx.a();
        r1.a(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.hb.i():void");
    }

    private void j() {
        jn.b();
        if (m()) {
            this.g = n();
            if (c()) {
                x();
                hx.a().a(new hd());
            }
        }
    }

    private void k() {
        jn.b();
        this.h = o();
    }

    private void l() {
        if (hn.a().c().checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0) {
            w();
        }
    }

    private boolean m() {
        try {
            int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(hn.a().c());
            if (isGooglePlayServicesAvailable == 0) {
                return true;
            }
            ib.e(a, "Google Play Services not available - connection result: " + isGooglePlayServicesAvailable);
            return false;
        } catch (NoClassDefFoundError e) {
            ib.b(a, "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.");
            return false;
        } catch (Exception e2) {
            ib.b(a, "GOOGLE PLAY SERVICES EXCEPTION: " + e2.getMessage());
            ib.b(a, "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.");
            return false;
        }
    }

    private Info n() {
        Info info = null;
        try {
            info = AdvertisingIdClient.getAdvertisingIdInfo(hn.a().c());
        } catch (Exception e) {
            ib.b(a, "GOOGLE PLAY SERVICES ERROR: " + e.getMessage());
            ib.b(a, "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.");
        }
        return info;
    }

    private String o() {
        Object p = p();
        return !TextUtils.isEmpty(p) ? p : q();
    }

    private String p() {
        String string = Secure.getString(hn.a().c().getContentResolver(), "android_id");
        if (a(string)) {
            return "AND" + string;
        }
        return null;
    }

    private String q() {
        String s = s();
        if (TextUtils.isEmpty(s)) {
            s = t();
            if (TextUtils.isEmpty(s)) {
                s = r();
            }
            b(s);
        }
        return s;
    }

    private boolean a(String str) {
        if (TextUtils.isEmpty(str) || c(str.toLowerCase(Locale.US))) {
            return false;
        }
        return true;
    }

    private String r() {
        return "ID" + Long.toString(Double.doubleToLongBits(Math.random()) + ((System.nanoTime() + (jn.i(jk.c(hn.a().c())) * 37)) * 37), 16);
    }

    private void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            File fileStreamPath = hn.a().c().getFileStreamPath(u());
            if (jm.a(fileStreamPath)) {
                a(str, fileStreamPath);
            }
        }
    }

    private void a(String str, File file) {
        Closeable dataOutputStream;
        Throwable th;
        try {
            dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            try {
                a(str, (DataOutput) dataOutputStream);
                jn.a(dataOutputStream);
            } catch (Throwable th2) {
                th = th2;
                try {
                    ib.a(6, a, "Error when saving deviceId", th);
                    jn.a(dataOutputStream);
                } catch (Throwable th3) {
                    th = th3;
                    jn.a(dataOutputStream);
                    throw th;
                }
            }
        } catch (Throwable th4) {
            th = th4;
            dataOutputStream = null;
            jn.a(dataOutputStream);
            throw th;
        }
    }

    private String s() {
        Closeable dataInputStream;
        Throwable th;
        Throwable th2;
        String str = null;
        File fileStreamPath = hn.a().c().getFileStreamPath(u());
        if (fileStreamPath != null && fileStreamPath.exists()) {
            try {
                dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                try {
                    str = a((DataInput) dataInputStream);
                    jn.a(dataInputStream);
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        ib.a(6, a, "Error when loading deviceId", th);
                        jn.a(dataInputStream);
                        return str;
                    } catch (Throwable th4) {
                        th2 = th4;
                        jn.a(dataInputStream);
                        throw th2;
                    }
                }
            } catch (Throwable th5) {
                dataInputStream = str;
                th2 = th5;
                jn.a(dataInputStream);
                throw th2;
            }
        }
        return str;
    }

    private String t() {
        Throwable th;
        Throwable th2;
        String str = null;
        File filesDir = hn.a().c().getFilesDir();
        if (filesDir != null) {
            String[] list = filesDir.list(new FilenameFilter(this) {
                final /* synthetic */ hb a;

                {
                    this.a = r1;
                }

                public boolean accept(File file, String str) {
                    return str.startsWith(".flurryagent.");
                }
            });
            if (!(list == null || list.length == 0)) {
                filesDir = hn.a().c().getFileStreamPath(list[0]);
                if (filesDir != null && filesDir.exists()) {
                    Closeable dataInputStream;
                    try {
                        dataInputStream = new DataInputStream(new FileInputStream(filesDir));
                        try {
                            str = b((DataInput) dataInputStream);
                            jn.a(dataInputStream);
                        } catch (Throwable th3) {
                            th = th3;
                            try {
                                ib.a(6, a, "Error when loading deviceId", th);
                                jn.a(dataInputStream);
                                return str;
                            } catch (Throwable th4) {
                                th2 = th4;
                                jn.a(dataInputStream);
                                throw th2;
                            }
                        }
                    } catch (Throwable th5) {
                        dataInputStream = str;
                        th2 = th5;
                        jn.a(dataInputStream);
                        throw th2;
                    }
                }
            }
        }
        return str;
    }

    private void a(String str, DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(1);
        dataOutput.writeUTF(str);
    }

    private String a(DataInput dataInput) throws IOException {
        if (1 != dataInput.readInt()) {
            return null;
        }
        return dataInput.readUTF();
    }

    private String b(DataInput dataInput) throws IOException {
        if (46586 != dataInput.readUnsignedShort() || 2 != dataInput.readUnsignedShort()) {
            return null;
        }
        dataInput.readUTF();
        return dataInput.readUTF();
    }

    private String u() {
        return ".flurryb.";
    }

    private boolean c(String str) {
        return this.c.contains(str);
    }

    private Set<String> v() {
        Set hashSet = new HashSet();
        hashSet.add("null");
        hashSet.add("9774d56d682e549c");
        hashSet.add("dead00beef");
        return Collections.unmodifiableSet(hashSet);
    }

    private void w() {
        TelephonyManager telephonyManager = (TelephonyManager) hn.a().c().getSystemService("phone");
        if (telephonyManager != null) {
            String deviceId = telephonyManager.getDeviceId();
            if (deviceId != null && deviceId.trim().length() > 0) {
                try {
                    byte[] f = jn.f(deviceId);
                    if (f == null || f.length != 20) {
                        ib.a(6, a, "sha1 is not " + 20 + " bytes long: " + Arrays.toString(f));
                    } else {
                        this.i = f;
                    }
                } catch (Exception e) {
                    ib.a(6, a, "Exception in generateHashedImei()");
                }
            }
        }
    }

    private void x() {
        String d = d();
        if (d != null) {
            ib.a(3, a, "Fetched advertising id");
            this.d.put(hj.AndroidAdvertisingId, jn.e(d));
        }
        d = f();
        if (d != null) {
            ib.a(3, a, "Fetched device id");
            this.d.put(hj.DeviceId, jn.e(d));
        }
        Object g = g();
        if (g != null) {
            ib.a(3, a, "Fetched hashed IMEI");
            this.d.put(hj.Sha1Imei, g);
        }
    }
}
