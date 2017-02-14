package cn.jpush.android.service;

import cn.jpush.android.a;
import cn.jpush.android.util.z;
import com.google.gson.jpush.k;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SisInfo {
    private static Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX);
    private static final String IPV4_REGEX;
    private static final String TAG;
    private static final k _gson = new k();
    private static final String[] z;
    boolean invalidSis = false;
    List<String> ips;
    String mainConnIp;
    int mainConnPort;
    List<String> op_conns;
    List<String> optionConnIp = new ArrayList();
    List<Integer> optionConnPort = new ArrayList();
    String originSis;
    List<String> ssl_ips;
    List<String> ssl_op_conns;
    List<String> udp_report;
    String user;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 7;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u0003k]6E\u001a\u001e9]X\u0006z[}Y\u0007q{ZF\u0002?.43\u001bn2[3\u001bn?[4\u0005jz.Z\u001e\u00186+]v\u001f(/APp{.@ps+75\u0014\u00186+Qv87*ZVjz.Zps+25ps+?5\u0002?.4]ps+35\u0002j";
        r0 = 6;
        r4 = r3;
    L_0x0008:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x003a;
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
            case 0: goto L_0x008e;
            case 1: goto L_0x0091;
            case 2: goto L_0x0094;
            case 3: goto L_0x0096;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 104; // 0x68 float:1.46E-43 double:5.14E-322;
    L_0x001f:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x0038;
    L_0x0027:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0016;
    L_0x002b:
        IPV4_REGEX = r1;
        r1 = "x*uO\u0006M,";
        r0 = 7;
        goto L_0x0008;
    L_0x0032:
        TAG = r1;
        r1 = "m\"oj\rOcriH[\"tu\r\u000b*vuE\u0019c+&\fN%gs\u0004_covF";
        r0 = -1;
        goto L_0x0008;
    L_0x0038:
        r5 = r1;
        r1 = r7;
    L_0x003a:
        if (r5 > r6) goto L_0x0011;
    L_0x003c:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0050;
            case 1: goto L_0x0058;
            case 2: goto L_0x0061;
            case 3: goto L_0x006a;
            case 4: goto L_0x0072;
            case 5: goto L_0x007a;
            case 6: goto L_0x002b;
            case 7: goto L_0x0032;
            default: goto L_0x0048;
        };
    L_0x0048:
        r3[r2] = r1;
        r2 = 1;
        r1 = "e,&t\r[,trHI\"em\u001d[covF";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0050:
        r3[r2] = r1;
        r2 = 2;
        r1 = "d-jHF\"ohHB3&o\u0006\u000b0ouF";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x0058:
        r3[r2] = r1;
        r2 = 3;
        r1 = "~-c~\u0018N rc\f\u000bn&o\u0006]\"jo\f\u000b0ouH\u0006cov\u001b\u000b\"tt\tRcjc\u0006\u000b*u&X";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0061:
        r3[r2] = r1;
        r2 = 4;
        r1 = "~-c~\u0018N rc\f\u000bn&O\u0006]\"jo\f\u000b0ouH\u0006chiHB3u&\u0003N:(";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x006a:
        r3[r2] = r1;
        r2 = 5;
        r1 = "m\"oj\rOcriH[\"tu\r\u000b,vY\u000bD-h&E\u000b";
        r0 = 4;
        r3 = r4;
        goto L_0x0008;
    L_0x0072:
        r3[r2] = r1;
        r2 = 6;
        r1 = "m\"oj\rOcriH[\"tu\r\u000b*vuE\u001ac+&\u0005J*h&\u0001[m";
        r0 = 5;
        r3 = r4;
        goto L_0x0008;
    L_0x007a:
        r3[r2] = r1;
        z = r4;
        r0 = new com.google.gson.jpush.k;
        r0.<init>();
        _gson = r0;
        r0 = IPV4_REGEX;
        r0 = java.util.regex.Pattern.compile(r0);
        IPV4_PATTERN = r0;
        return;
    L_0x008e:
        r9 = 43;
        goto L_0x001f;
    L_0x0091:
        r9 = 67;
        goto L_0x001f;
    L_0x0094:
        r9 = 6;
        goto L_0x001f;
    L_0x0096:
        r9 = 6;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.SisInfo.<clinit>():void");
    }

    public static SisInfo fromJson(String str) {
        return (SisInfo) _gson.a(str, SisInfo.class);
    }

    public static boolean isValidIPV4(String str) {
        return IPV4_PATTERN.matcher(str).matches();
    }

    public void configure() {
        int size = this.ips.size();
        if (size != 0) {
            a.f(this.originSis);
            if (size > 1) {
                try {
                    q qVar = new q((String) this.ips.get(1));
                    a.c(qVar.a);
                    a.c(qVar.b);
                } catch (Throwable e) {
                    z.a(TAG, z[0], e);
                }
            } else {
                z.d(TAG, z[2]);
            }
            if (size > 2) {
                a.d((String) this.ips.get(2));
            } else {
                z.d(TAG, z[1]);
            }
            if (this.user != null) {
                a.e(this.user);
            }
        }
    }

    public String getMainConnIp() {
        return this.mainConnIp;
    }

    public int getMainConnPort() {
        return this.mainConnPort;
    }

    public List<String> getOptionConnIp() {
        return this.optionConnIp;
    }

    public List<Integer> getOptionConnPort() {
        return this.optionConnPort;
    }

    public String getOriginSis() {
        return this.originSis;
    }

    public boolean isInvalidSis() {
        return this.invalidSis;
    }

    public void parseAndSet(String str) {
        this.originSis = str;
        if (this.ips == null) {
            z.e(TAG, z[4]);
            this.invalidSis = true;
        } else if (this.ips.size() == 0) {
            z.e(TAG, z[3]);
            this.invalidSis = true;
        } else {
            try {
                q qVar = new q((String) this.ips.get(0));
                this.mainConnIp = qVar.a;
                this.mainConnPort = qVar.b;
                if (this.op_conns == null) {
                    z.b();
                    return;
                }
                for (String str2 : this.op_conns) {
                    try {
                        q qVar2 = new q(str2);
                        this.optionConnIp.add(qVar2.a);
                        this.optionConnPort.add(Integer.valueOf(qVar2.b));
                    } catch (Exception e) {
                        new StringBuilder(z[5]).append(str2);
                        z.i();
                    }
                }
            } catch (Throwable e2) {
                z.a(TAG, z[6], e2);
                this.invalidSis = true;
            }
        }
    }
}
