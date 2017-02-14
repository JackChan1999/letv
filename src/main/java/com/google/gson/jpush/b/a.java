package com.google.gson.jpush.b;

import android.support.v4.view.MotionEventCompat;
import com.letv.core.constant.PlayConstant.LiveType;
import com.letv.core.utils.LetvUtils;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

public class a implements Closeable {
    private static final char[] a;
    private static final String[] z;
    private final Reader b;
    private boolean c = false;
    private final char[] d = new char[1024];
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private int h = 0;
    private int i = 0;
    private long j;
    private int k;
    private String l;
    private int[] m = new int[32];
    private int n = 0;
    private String[] o;
    private int[] p;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 36;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "JP\u0007Nt";
        r0 = -1;
        r4 = r3;
    L_0x0009:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002e;
    L_0x0012:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0017:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x01cf;
            case 1: goto L_0x01d3;
            case 2: goto L_0x01d7;
            case 3: goto L_0x01db;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 49;
    L_0x0020:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002c;
    L_0x0028:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0017;
    L_0x002c:
        r5 = r1;
        r1 = r7;
    L_0x002e:
        if (r5 > r6) goto L_0x0012;
    L_0x0030:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0044;
            case 1: goto L_0x004c;
            case 2: goto L_0x0054;
            case 3: goto L_0x005c;
            case 4: goto L_0x0065;
            case 5: goto L_0x006d;
            case 6: goto L_0x0075;
            case 7: goto L_0x007e;
            case 8: goto L_0x0088;
            case 9: goto L_0x0093;
            case 10: goto L_0x009e;
            case 11: goto L_0x00a9;
            case 12: goto L_0x00b4;
            case 13: goto L_0x00bf;
            case 14: goto L_0x00ca;
            case 15: goto L_0x00d5;
            case 16: goto L_0x00e0;
            case 17: goto L_0x00eb;
            case 18: goto L_0x00f6;
            case 19: goto L_0x0101;
            case 20: goto L_0x010c;
            case 21: goto L_0x0117;
            case 22: goto L_0x0122;
            case 23: goto L_0x012d;
            case 24: goto L_0x0138;
            case 25: goto L_0x0143;
            case 26: goto L_0x014e;
            case 27: goto L_0x0159;
            case 28: goto L_0x0164;
            case 29: goto L_0x016f;
            case 30: goto L_0x017a;
            case 31: goto L_0x0185;
            case 32: goto L_0x0190;
            case 33: goto L_0x019b;
            case 34: goto L_0x01a6;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "BD\u0007Q";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "bd'q";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "XC\u001eX";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "jp'nT";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "xc>x";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0065:
        r3[r2] = r1;
        r2 = 6;
        r1 = ",r$qDak";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006d:
        r3[r2] = r1;
        r2 = 7;
        r1 = ",p?=]e.=";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0075:
        r3[r2] = r1;
        r2 = 8;
        r1 = ",a*iY,";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007e:
        r3[r2] = r1;
        r2 = 9;
        r1 = "Ii;xRxt/=P,s$r]ip%=SyekjP1";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0088:
        r3[r2] = r1;
        r2 = 10;
        r1 = "Yb.={~%OTmu.o\u001ft?QTbx.sE$e9hT%1?r\u0011mr(xAx1&|]j~9pTh1\u0001N~B";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0093:
        r3[r2] = r1;
        r2 = 11;
        r1 = "Ii;xRxt/=Pb1\"sE,s>i\u0011{p8=";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009e:
        r3[r2] = r1;
        r2 = 12;
        r1 = "Ii;xRxt/=P,*pT,s>i\u0011{p8=";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a9:
        r3[r2] = r1;
        r2 = 13;
        r1 = "Y?xCax%|EiuknE~x%z";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b4:
        r3[r2] = r1;
        r2 = 14;
        r1 = "Y.eAir?xU,g*qDi";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00bf:
        r3[r2] = r1;
        r2 = 15;
        r1 = "Ii;xRxt/=\u001666";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00ca:
        r3[r2] = r1;
        r2 = 16;
        r1 = "Ii;xRxt/=Gm}>x";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d5:
        r3[r2] = r1;
        r2 = 17;
        r1 = "Y?xCax%|EiukrSft(i";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e0:
        r3[r2] = r1;
        r2 = 18;
        r1 = "Y?xCax%|Eiuk|C~p2";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00eb:
        r3[r2] = r1;
        r2 = 19;
        r1 = "Ii;xRxt/=_m|.";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f6:
        r3[r2] = r1;
        r2 = 20;
        r1 = "Fb$scip/xC,x8=R`~8xU";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0101:
        r3[r2] = r1;
        r2 = 21;
        r1 = "&>";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010c:
        r3[r2] = r1;
        r2 = 22;
        r1 = "Y?xCax%|Eiuk~^a|.sE";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x0117:
        r3[r2] = r1;
        r2 = 23;
        r1 = "I/=^j1\"sAyek|E,}\"sT,";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0122:
        r3[r2] = r1;
        r2 = 24;
        r1 = "FB\u0004S\u0011j~9XhbkSPB1*sU,x%{Xbx?tT+k";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x012d:
        r3[r2] = r1;
        r2 = 25;
        r1 = "Ii;xRxt/=P,u$hS`tkDx1<|B,";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x0138:
        r3[r2] = r1;
        r2 = 26;
        r1 = "Ii;xRxt/=sIV\u0002SnCS\u0001XrX1)hE,f*n\u0011";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x0143:
        r3[r2] = r1;
        r2 = 27;
        r1 = "Ii;xRxt/=P,}$sV,s>i\u0011{p8=";
        r0 = 26;
        r3 = r4;
        goto L_0x0009;
    L_0x014e:
        r3[r2] = r1;
        r2 = 28;
        r1 = "Ii;xRxt/=P,b?oXbvkDx1<|B,";
        r0 = 27;
        r3 = r4;
        goto L_0x0009;
    L_0x0159:
        r3[r2] = r1;
        r2 = 29;
        r1 = "Ii;xRxt/=tBU\u0014RsFT\bI\u0011nd?=Fmbk";
        r0 = 28;
        r3 = r4;
        goto L_0x0009;
    L_0x0164:
        r3[r2] = r1;
        r2 = 30;
        r1 = "Ii;xRxt/=sIV\u0002SnMC\u0019\\h,s>i\u0011{p8=";
        r0 = 29;
        r3 = r4;
        goto L_0x0009;
    L_0x016f:
        r3[r2] = r1;
        r2 = 31;
        r1 = "Ii;xRxt/=_y}'=SyekjP1";
        r0 = 30;
        r3 = r4;
        goto L_0x0009;
    L_0x017a:
        r3[r2] = r1;
        r2 = 32;
        r1 = "ek \f,>q]";
        r0 = 31;
        r3 = r4;
        goto L_0x0009;
    L_0x0185:
        r3[r2] = r1;
        r2 = 33;
        r1 = "Ii;xRxt/=tBU\u0014\\c^P\u0012=SyekjP1";
        r0 = 32;
        r3 = r4;
        goto L_0x0009;
    L_0x0190:
        r3[r2] = r1;
        r2 = 34;
        r1 = "Y?xCax%|EiukxBop;x\u0011t:hTbr.";
        r0 = 33;
        r3 = r4;
        goto L_0x0009;
    L_0x019b:
        r3[r2] = r1;
        r2 = 35;
        r1 = "Pd";
        r0 = 34;
        r3 = r4;
        goto L_0x0009;
    L_0x01a6:
        r3[r2] = r1;
        z = r4;
        r0 = "%L6:;";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x01ed;
    L_0x01b5:
        r3 = r0;
        r4 = r2;
        r11 = r1;
        r1 = r0;
        r0 = r11;
    L_0x01ba:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x01df;
            case 1: goto L_0x01e2;
            case 2: goto L_0x01e5;
            case 3: goto L_0x01e8;
            default: goto L_0x01c1;
        };
    L_0x01c1:
        r5 = 49;
    L_0x01c3:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x01eb;
    L_0x01cb:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x01ba;
    L_0x01cf:
        r9 = 12;
        goto L_0x0020;
    L_0x01d3:
        r9 = 17;
        goto L_0x0020;
    L_0x01d7:
        r9 = 75;
        goto L_0x0020;
    L_0x01db:
        r9 = 29;
        goto L_0x0020;
    L_0x01df:
        r5 = 12;
        goto L_0x01c3;
    L_0x01e2:
        r5 = 17;
        goto L_0x01c3;
    L_0x01e5:
        r5 = 75;
        goto L_0x01c3;
    L_0x01e8:
        r5 = 29;
        goto L_0x01c3;
    L_0x01eb:
        r1 = r0;
        r0 = r3;
    L_0x01ed:
        if (r1 > r2) goto L_0x01b5;
    L_0x01ef:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        r0 = r0.toCharArray();
        a = r0;
        r0 = new com.google.gson.jpush.b.b;
        r0.<init>();
        com.google.gson.jpush.internal.u.a = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.b.a.<clinit>():void");
    }

    public a(Reader reader) {
        int[] iArr = this.m;
        int i = this.n;
        this.n = i + 1;
        iArr[i] = 6;
        this.o = new String[32];
        this.p = new int[32];
        if (reader == null) {
            throw new NullPointerException(z[32]);
        }
        this.b = reader;
    }

    private IOException a(String str) {
        throw new e(str + z[7] + (this.g + 1) + z[6] + u() + z[8] + q());
    }

    private void a(int i) {
        if (this.n == this.m.length) {
            Object obj = new int[(this.n * 2)];
            Object obj2 = new int[(this.n * 2)];
            Object obj3 = new String[(this.n * 2)];
            System.arraycopy(this.m, 0, obj, 0, this.n);
            System.arraycopy(this.p, 0, obj2, 0, this.n);
            System.arraycopy(this.o, 0, obj3, 0, this.n);
            this.m = obj;
            this.p = obj2;
            this.o = obj3;
        }
        int[] iArr = this.m;
        int i2 = this.n;
        this.n = i2 + 1;
        iArr[i2] = i;
    }

    private boolean a(char c) {
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
            case MotionEventCompat.AXIS_GENERIC_13 /*44*/:
            case ':':
            case '[':
            case ']':
            case '{':
            case '}':
                break;
            case '#':
            case MotionEventCompat.AXIS_GENERIC_16 /*47*/:
            case ';':
            case '=':
            case '\\':
                v();
                break;
            default:
                return true;
        }
        return false;
    }

    private int b(boolean z) {
        char[] cArr = this.d;
        int i = this.e;
        int i2 = this.f;
        while (true) {
            if (i == i2) {
                this.e = i;
                if (b(1)) {
                    i = this.e;
                    i2 = this.f;
                } else if (!z) {
                    return -1;
                } else {
                    throw new EOFException(new StringBuilder(z[23]).append(this.g + 1).append(z[6]).append(u()).toString());
                }
            }
            int i3 = i + 1;
            char c = cArr[i];
            if (c == '\n') {
                this.g++;
                this.h = i3;
                i = i3;
            } else if (c == ' ' || c == '\r' || c == '\t') {
                i = i3;
            } else if (c == LetvUtils.CHARACTER_BACKSLASH) {
                this.e = i3;
                if (i3 == i2) {
                    this.e--;
                    boolean b = b(2);
                    this.e++;
                    if (!b) {
                        return c;
                    }
                }
                v();
                switch (cArr[this.e]) {
                    case '*':
                        this.e++;
                        String str = z[21];
                        while (true) {
                            if (this.e + str.length() <= this.f || b(str.length())) {
                                if (this.d[this.e] == '\n') {
                                    this.g++;
                                    this.h = this.e + 1;
                                } else {
                                    i2 = 0;
                                    while (i2 < str.length()) {
                                        if (this.d[this.e + i2] == str.charAt(i2)) {
                                            i2++;
                                        }
                                    }
                                    i2 = 1;
                                }
                                this.e++;
                            } else {
                                i2 = 0;
                            }
                            if (i2 != 0) {
                                i = this.e + 2;
                                i2 = this.f;
                                break;
                            }
                            throw a(z[22]);
                        }
                    case MotionEventCompat.AXIS_GENERIC_16 /*47*/:
                        this.e++;
                        w();
                        i = this.e;
                        i2 = this.f;
                        break;
                    default:
                        return c;
                }
            } else if (c == '#') {
                this.e = i3;
                v();
                w();
                i = this.e;
                i2 = this.f;
            } else {
                this.e = i3;
                return c;
            }
        }
    }

    private String b(char c) {
        char[] cArr = this.d;
        StringBuilder stringBuilder = new StringBuilder();
        do {
            int i = this.e;
            int i2 = this.f;
            int i3 = i;
            while (i3 < i2) {
                int i4 = i3 + 1;
                char c2 = cArr[i3];
                if (c2 == c) {
                    this.e = i4;
                    stringBuilder.append(cArr, i, (i4 - i) - 1);
                    return stringBuilder.toString();
                } else if (c2 == '\\') {
                    this.e = i4;
                    stringBuilder.append(cArr, i, (i4 - i) - 1);
                    stringBuilder.append(x());
                    i = this.e;
                    i2 = this.f;
                    i3 = i;
                } else {
                    if (c2 == '\n') {
                        this.g++;
                        this.h = i4;
                    }
                    i3 = i4;
                }
            }
            stringBuilder.append(cArr, i, i3 - i);
            this.e = i3;
        } while (b(1));
        throw a(z[13]);
    }

    private boolean b(int i) {
        Object obj = this.d;
        this.h -= this.e;
        if (this.f != this.e) {
            this.f -= this.e;
            System.arraycopy(obj, this.e, obj, 0, this.f);
        } else {
            this.f = 0;
        }
        this.e = 0;
        do {
            int read = this.b.read(obj, this.f, obj.length - this.f);
            if (read == -1) {
                return false;
            }
            this.f = read + this.f;
            if (this.g == 0 && this.h == 0 && this.f > 0 && obj[0] == '﻿') {
                this.e++;
                this.h++;
                i++;
            }
        } while (this.f < i);
        return true;
    }

    private void c(char c) {
        char[] cArr = this.d;
        do {
            int i = this.e;
            int i2 = this.f;
            while (i < i2) {
                int i3 = i + 1;
                char c2 = cArr[i];
                if (c2 == c) {
                    this.e = i3;
                    return;
                } else if (c2 == '\\') {
                    this.e = i3;
                    x();
                    i = this.e;
                    i2 = this.f;
                } else {
                    if (c2 == '\n') {
                        this.g++;
                        this.h = i3;
                    }
                    i = i3;
                }
            }
            this.e = i;
        } while (b(1));
        throw a(z[13]);
    }

    private int o() {
        int b;
        int i = this.m[this.n - 1];
        if (i == 1) {
            this.m[this.n - 1] = 2;
        } else if (i == 2) {
            switch (b(true)) {
                case MotionEventCompat.AXIS_GENERIC_13 /*44*/:
                    break;
                case 59:
                    v();
                    break;
                case 93:
                    this.i = 4;
                    return 4;
                default:
                    throw a(z[18]);
            }
        } else if (i == 3 || i == 5) {
            this.m[this.n - 1] = 4;
            if (i == 5) {
                switch (b(true)) {
                    case MotionEventCompat.AXIS_GENERIC_13 /*44*/:
                        break;
                    case 59:
                        v();
                        break;
                    case 125:
                        this.i = 2;
                        return 2;
                    default:
                        throw a(z[17]);
                }
            }
            b = b(true);
            switch (b) {
                case 34:
                    this.i = 13;
                    return 13;
                case 39:
                    v();
                    this.i = 12;
                    return 12;
                case 125:
                    if (i != 5) {
                        this.i = 2;
                        return 2;
                    }
                    throw a(z[19]);
                default:
                    v();
                    this.e--;
                    if (a((char) b)) {
                        this.i = 14;
                        return 14;
                    }
                    throw a(z[19]);
            }
        } else if (i == 4) {
            this.m[this.n - 1] = 5;
            switch (b(true)) {
                case 58:
                    break;
                case 61:
                    v();
                    if ((this.e < this.f || b(1)) && this.d[this.e] == '>') {
                        this.e++;
                        break;
                    }
                default:
                    throw a(z[15]);
            }
        } else if (i == 6) {
            if (this.c) {
                b(true);
                this.e--;
                if (this.e + a.length <= this.f || b(a.length)) {
                    for (b = 0; b < a.length; b++) {
                        if (this.d[this.e + b] != a[b]) {
                            break;
                        }
                    }
                    this.e += a.length;
                }
            }
            this.m[this.n - 1] = 7;
        } else if (i == 7) {
            if (b(false) == -1) {
                this.i = 17;
                return 17;
            }
            v();
            this.e--;
        } else if (i == 8) {
            throw new IllegalStateException(z[20]);
        }
        switch (b(true)) {
            case 34:
                if (this.n == 1) {
                    v();
                }
                this.i = 9;
                return 9;
            case 39:
                v();
                this.i = 8;
                return 8;
            case MotionEventCompat.AXIS_GENERIC_13 /*44*/:
            case 59:
                break;
            case 91:
                this.i = 3;
                return 3;
            case 93:
                if (i == 1) {
                    this.i = 4;
                    return 4;
                }
                break;
            case 123:
                this.i = 1;
                return 1;
            default:
                this.e--;
                if (this.n == 1) {
                    v();
                }
                b = r();
                if (b != 0) {
                    return b;
                }
                b = s();
                if (b != 0) {
                    return b;
                }
                if (a(this.d[this.e])) {
                    v();
                    this.i = 10;
                    return 10;
                }
                throw a(z[16]);
        }
        if (i == 1 || i == 2) {
            v();
            this.e--;
            this.i = 7;
            return 7;
        }
        throw a(z[14]);
    }

    private int r() {
        String str;
        int i = 5;
        int i2 = 1;
        char c = this.d[this.e];
        String str2;
        if (c == 't' || c == 'T') {
            str = z[5];
            str2 = z[3];
        } else if (c == 'f' || c == 'F') {
            str = z[4];
            str2 = z[0];
            i = 6;
        } else if (c != 'n' && c != 'N') {
            return 0;
        } else {
            str = z[2];
            str2 = z[1];
            i = 7;
        }
        int length = str.length();
        while (i2 < length) {
            if (this.e + i2 >= this.f && !b(i2 + 1)) {
                return 0;
            }
            char c2 = this.d[this.e + i2];
            if (c2 != str.charAt(i2) && c2 != r1.charAt(i2)) {
                return 0;
            }
            i2++;
        }
        if ((this.e + length < this.f || b(length + 1)) && a(this.d[this.e + length])) {
            return 0;
        }
        this.e += length;
        this.i = i;
        return i;
    }

    private int s() {
        char[] cArr = this.d;
        int i = this.e;
        long j = 0;
        Object obj = null;
        int i2 = 1;
        int i3 = 0;
        int i4 = 0;
        int i5 = this.f;
        int i6 = i;
        while (true) {
            Object obj2;
            if (i6 + i4 == i5) {
                if (i4 == cArr.length) {
                    return 0;
                }
                if (b(i4 + 1)) {
                    i6 = this.e;
                    i5 = this.f;
                } else if (i3 != 2 && i2 != 0 && (j != Long.MIN_VALUE || obj != null)) {
                    if (obj == null) {
                        j = -j;
                    }
                    this.j = j;
                    this.e += i4;
                    this.i = 15;
                    return 15;
                } else if (i3 == 2 && i3 != 4 && i3 != 7) {
                    return 0;
                } else {
                    this.k = i4;
                    this.i = 16;
                    return 16;
                }
            }
            char c = cArr[i6 + i4];
            int i7;
            switch (c) {
                case MotionEventCompat.AXIS_GENERIC_12 /*43*/:
                    if (i3 != 5) {
                        return 0;
                    }
                    i = 6;
                    i3 = i2;
                    obj2 = obj;
                    continue;
                case '-':
                    if (i3 == 0) {
                        i = 1;
                        i7 = i2;
                        obj2 = 1;
                        i3 = i7;
                        continue;
                    } else if (i3 == 5) {
                        i = 6;
                        i3 = i2;
                        obj2 = obj;
                        break;
                    } else {
                        return 0;
                    }
                case MotionEventCompat.AXIS_GENERIC_15 /*46*/:
                    if (i3 != 2) {
                        return 0;
                    }
                    i = 3;
                    i3 = i2;
                    obj2 = obj;
                    continue;
                case 'E':
                case 'e':
                    if (i3 != 2 && i3 != 4) {
                        return 0;
                    }
                    i = 5;
                    i3 = i2;
                    obj2 = obj;
                    continue;
                default:
                    if (c >= '0' && c <= '9') {
                        if (i3 != 1 && i3 != 0) {
                            if (i3 != 2) {
                                if (i3 != 3) {
                                    if (i3 != 5 && i3 != 6) {
                                        i = i3;
                                        i3 = i2;
                                        obj2 = obj;
                                        break;
                                    }
                                    i = 7;
                                    i3 = i2;
                                    obj2 = obj;
                                    break;
                                }
                                i = 4;
                                i3 = i2;
                                obj2 = obj;
                                break;
                            } else if (j != 0) {
                                long j2 = (10 * j) - ((long) (c - 48));
                                i = (j > -922337203685477580L || (j == -922337203685477580L && j2 < j)) ? 1 : 0;
                                i &= i2;
                                obj2 = obj;
                                j = j2;
                                i7 = i3;
                                i3 = i;
                                i = i7;
                                break;
                            } else {
                                return 0;
                            }
                        }
                        j = (long) (-(c - 48));
                        i = 2;
                        i3 = i2;
                        obj2 = obj;
                        continue;
                    } else if (a(c)) {
                        return 0;
                    }
                    break;
            }
            if (i3 != 2) {
            }
            if (i3 == 2) {
            }
            this.k = i4;
            this.i = 16;
            return 16;
            i4++;
            obj = obj2;
            i2 = i3;
            i3 = i;
        }
    }

    private String t() {
        StringBuilder stringBuilder = null;
        int i = 0;
        while (true) {
            String str;
            if (this.e + i < this.f) {
                switch (this.d[this.e + i]) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case MotionEventCompat.AXIS_GENERIC_13 /*44*/:
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case MotionEventCompat.AXIS_GENERIC_16 /*47*/:
                    case ';':
                    case '=':
                    case '\\':
                        v();
                        break;
                    default:
                        i++;
                        continue;
                }
            } else if (i >= this.d.length) {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder();
                }
                stringBuilder.append(this.d, this.e, i);
                this.e = i + this.e;
                i = !b(1) ? 0 : 0;
            } else if (b(i + 1)) {
            }
            if (stringBuilder == null) {
                str = new String(this.d, this.e, i);
            } else {
                stringBuilder.append(this.d, this.e, i);
                str = stringBuilder.toString();
            }
            this.e = i + this.e;
            return str;
        }
    }

    private int u() {
        return (this.e - this.h) + 1;
    }

    private void v() {
        if (!this.c) {
            throw a(z[10]);
        }
    }

    private void w() {
        while (true) {
            if (this.e < this.f || b(1)) {
                char[] cArr = this.d;
                int i = this.e;
                this.e = i + 1;
                char c = cArr[i];
                if (c == '\n') {
                    this.g++;
                    this.h = this.e;
                    return;
                } else if (c == '\r') {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private char x() {
        if (this.e != this.f || b(1)) {
            char[] cArr = this.d;
            int i = this.e;
            this.e = i + 1;
            char c = cArr[i];
            switch (c) {
                case '\n':
                    this.g++;
                    this.h = this.e;
                    return c;
                case 'b':
                    return '\b';
                case 'f':
                    return '\f';
                case LiveType.PLAY_LIVE_OTHER /*110*/:
                    return '\n';
                case 'r':
                    return '\r';
                case 't':
                    return '\t';
                case 'u':
                    if (this.e + 4 <= this.f || b(4)) {
                        int i2 = this.e;
                        int i3 = i2 + 4;
                        int i4 = i2;
                        c = '\u0000';
                        for (i = i4; i < i3; i++) {
                            char c2 = this.d[i];
                            c = (char) (c << 4);
                            if (c2 >= '0' && c2 <= '9') {
                                c = (char) (c + (c2 - 48));
                            } else if (c2 >= 'a' && c2 <= 'f') {
                                c = (char) (c + ((c2 - 97) + 10));
                            } else if (c2 < 'A' || c2 > 'F') {
                                throw new NumberFormatException(new StringBuilder(z[35]).append(new String(this.d, this.e, 4)).toString());
                            } else {
                                c = (char) (c + ((c2 - 65) + 10));
                            }
                        }
                        this.e += 4;
                        return c;
                    }
                    throw a(z[34]);
                default:
                    return c;
            }
        }
        throw a(z[34]);
    }

    public void a() {
        int i = this.i;
        if (i == 0) {
            i = o();
        }
        if (i == 3) {
            a(1);
            this.p[this.n - 1] = 0;
            this.i = 0;
            return;
        }
        throw new IllegalStateException(new StringBuilder(z[30]).append(f()).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
    }

    public final void a(boolean z) {
        this.c = z;
    }

    public void b() {
        int i = this.i;
        if (i == 0) {
            i = o();
        }
        if (i == 4) {
            this.n--;
            int[] iArr = this.p;
            int i2 = this.n - 1;
            iArr[i2] = iArr[i2] + 1;
            this.i = 0;
            return;
        }
        throw new IllegalStateException(new StringBuilder(z[33]).append(f()).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
    }

    public void c() {
        int i = this.i;
        if (i == 0) {
            i = o();
        }
        if (i == 1) {
            a(3);
            this.i = 0;
            return;
        }
        throw new IllegalStateException(new StringBuilder(z[26]).append(f()).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
    }

    public void close() {
        this.i = 0;
        this.m[0] = 8;
        this.n = 1;
        this.b.close();
    }

    public void d() {
        int i = this.i;
        if (i == 0) {
            i = o();
        }
        if (i == 2) {
            this.n--;
            this.o[this.n] = null;
            int[] iArr = this.p;
            int i2 = this.n - 1;
            iArr[i2] = iArr[i2] + 1;
            this.i = 0;
            return;
        }
        throw new IllegalStateException(new StringBuilder(z[29]).append(f()).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
    }

    public boolean e() {
        int i = this.i;
        if (i == 0) {
            i = o();
        }
        return (i == 2 || i == 4) ? false : true;
    }

    public c f() {
        int i = this.i;
        if (i == 0) {
            i = o();
        }
        switch (i) {
            case 1:
                return c.c;
            case 2:
                return c.d;
            case 3:
                return c.a;
            case 4:
                return c.b;
            case 5:
            case 6:
                return c.h;
            case 7:
                return c.i;
            case 8:
            case 9:
            case 10:
            case 11:
                return c.f;
            case 12:
            case 13:
            case 14:
                return c.e;
            case 15:
            case 16:
                return c.g;
            case 17:
                return c.j;
            default:
                throw new AssertionError();
        }
    }

    public String g() {
        String t;
        int i = this.i;
        if (i == 0) {
            i = o();
        }
        if (i == 14) {
            t = t();
        } else if (i == 12) {
            t = b('\'');
        } else if (i == 13) {
            t = b('\"');
        } else {
            throw new IllegalStateException(new StringBuilder(z[12]).append(f()).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
        }
        this.i = 0;
        this.o[this.n - 1] = t;
        return t;
    }

    public String h() {
        String t;
        int i = this.i;
        if (i == 0) {
            i = o();
        }
        if (i == 10) {
            t = t();
        } else if (i == 8) {
            t = b('\'');
        } else if (i == 9) {
            t = b('\"');
        } else if (i == 11) {
            t = this.l;
            this.l = null;
        } else if (i == 15) {
            t = Long.toString(this.j);
        } else if (i == 16) {
            t = new String(this.d, this.e, this.k);
            this.e += this.k;
        } else {
            throw new IllegalStateException(new StringBuilder(z[28]).append(f()).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
        }
        this.i = 0;
        int[] iArr = this.p;
        int i2 = this.n - 1;
        iArr[i2] = iArr[i2] + 1;
        return t;
    }

    public boolean i() {
        int i = this.i;
        if (i == 0) {
            i = o();
        }
        if (i == 5) {
            this.i = 0;
            int[] iArr = this.p;
            i = this.n - 1;
            iArr[i] = iArr[i] + 1;
            return true;
        } else if (i == 6) {
            this.i = 0;
            int[] iArr2 = this.p;
            int i2 = this.n - 1;
            iArr2[i2] = iArr2[i2] + 1;
            return false;
        } else {
            throw new IllegalStateException(new StringBuilder(z[9]).append(f()).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
        }
    }

    public void j() {
        int i = this.i;
        if (i == 0) {
            i = o();
        }
        if (i == 7) {
            this.i = 0;
            int[] iArr = this.p;
            int i2 = this.n - 1;
            iArr[i2] = iArr[i2] + 1;
            return;
        }
        throw new IllegalStateException(new StringBuilder(z[31]).append(f()).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
    }

    public double k() {
        int i = this.i;
        if (i == 0) {
            i = o();
        }
        if (i == 15) {
            this.i = 0;
            int[] iArr = this.p;
            int i2 = this.n - 1;
            iArr[i2] = iArr[i2] + 1;
            return (double) this.j;
        }
        if (i == 16) {
            this.l = new String(this.d, this.e, this.k);
            this.e += this.k;
        } else if (i == 8 || i == 9) {
            this.l = b(i == 8 ? '\'' : '\"');
        } else if (i == 10) {
            this.l = t();
        } else if (i != 11) {
            throw new IllegalStateException(new StringBuilder(z[25]).append(f()).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
        }
        this.i = 11;
        double parseDouble = Double.parseDouble(this.l);
        if (this.c || !(Double.isNaN(parseDouble) || Double.isInfinite(parseDouble))) {
            this.l = null;
            this.i = 0;
            int[] iArr2 = this.p;
            int i3 = this.n - 1;
            iArr2[i3] = iArr2[i3] + 1;
            return parseDouble;
        }
        throw new e(new StringBuilder(z[24]).append(parseDouble).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
    }

    public long l() {
        int i = this.i;
        if (i == 0) {
            i = o();
        }
        if (i == 15) {
            this.i = 0;
            int[] iArr = this.p;
            int i2 = this.n - 1;
            iArr[i2] = iArr[i2] + 1;
            return this.j;
        }
        long parseLong;
        if (i == 16) {
            this.l = new String(this.d, this.e, this.k);
            this.e += this.k;
        } else if (i == 8 || i == 9) {
            this.l = b(i == 8 ? '\'' : '\"');
            try {
                parseLong = Long.parseLong(this.l);
                this.i = 0;
                int[] iArr2 = this.p;
                int i3 = this.n - 1;
                iArr2[i3] = iArr2[i3] + 1;
                return parseLong;
            } catch (NumberFormatException e) {
            }
        } else {
            throw new IllegalStateException(new StringBuilder(z[27]).append(f()).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
        }
        this.i = 11;
        double parseDouble = Double.parseDouble(this.l);
        parseLong = (long) parseDouble;
        if (((double) parseLong) != parseDouble) {
            throw new NumberFormatException(new StringBuilder(z[27]).append(this.l).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
        }
        this.l = null;
        this.i = 0;
        iArr2 = this.p;
        i3 = this.n - 1;
        iArr2[i3] = iArr2[i3] + 1;
        return parseLong;
    }

    public int m() {
        int i = this.i;
        if (i == 0) {
            i = o();
        }
        int[] iArr;
        int i2;
        if (i == 15) {
            i = (int) this.j;
            if (this.j != ((long) i)) {
                throw new NumberFormatException(new StringBuilder(z[11]).append(this.j).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
            }
            this.i = 0;
            iArr = this.p;
            i2 = this.n - 1;
            iArr[i2] = iArr[i2] + 1;
        } else {
            if (i == 16) {
                this.l = new String(this.d, this.e, this.k);
                this.e += this.k;
            } else if (i == 8 || i == 9) {
                this.l = b(i == 8 ? '\'' : '\"');
                try {
                    i = Integer.parseInt(this.l);
                    this.i = 0;
                    iArr = this.p;
                    i2 = this.n - 1;
                    iArr[i2] = iArr[i2] + 1;
                } catch (NumberFormatException e) {
                }
            } else {
                throw new IllegalStateException(new StringBuilder(z[11]).append(f()).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
            }
            this.i = 11;
            double parseDouble = Double.parseDouble(this.l);
            i = (int) parseDouble;
            if (((double) i) != parseDouble) {
                throw new NumberFormatException(new StringBuilder(z[11]).append(this.l).append(z[7]).append(this.g + 1).append(z[6]).append(u()).append(z[8]).append(q()).toString());
            }
            this.l = null;
            this.i = 0;
            iArr = this.p;
            i2 = this.n - 1;
            iArr[i2] = iArr[i2] + 1;
        }
        return i;
    }

    public void n() {
        int i = 0;
        do {
            int i2 = this.i;
            if (i2 == 0) {
                i2 = o();
            }
            if (i2 == 3) {
                a(1);
                i++;
            } else if (i2 == 1) {
                a(3);
                i++;
            } else if (i2 == 4) {
                this.n--;
                i--;
            } else if (i2 == 2) {
                this.n--;
                i--;
            } else if (i2 == 14 || i2 == 10) {
                do {
                    i2 = 0;
                    while (this.e + i2 < this.f) {
                        switch (this.d[this.e + i2]) {
                            case '\t':
                            case '\n':
                            case '\f':
                            case '\r':
                            case ' ':
                            case MotionEventCompat.AXIS_GENERIC_13 /*44*/:
                            case ':':
                            case '[':
                            case ']':
                            case '{':
                            case '}':
                                break;
                            case '#':
                            case MotionEventCompat.AXIS_GENERIC_16 /*47*/:
                            case ';':
                            case '=':
                            case '\\':
                                v();
                                break;
                            default:
                                i2++;
                        }
                        this.e = i2 + this.e;
                    }
                    this.e = i2 + this.e;
                } while (b(1));
            } else if (i2 == 8 || i2 == 12) {
                c('\'');
            } else if (i2 == 9 || i2 == 13) {
                c('\"');
            } else if (i2 == 16) {
                this.e += this.k;
            }
            this.i = 0;
        } while (i != 0);
        int[] iArr = this.p;
        int i3 = this.n - 1;
        iArr[i3] = iArr[i3] + 1;
        this.o[this.n - 1] = z[2];
    }

    public final boolean p() {
        return this.c;
    }

    public final String q() {
        StringBuilder stringBuilder = new StringBuilder("$");
        int i = this.n;
        for (int i2 = 0; i2 < i; i2++) {
            switch (this.m[i2]) {
                case 1:
                case 2:
                    stringBuilder.append('[').append(this.p[i2]).append(']');
                    break;
                case 3:
                case 4:
                case 5:
                    stringBuilder.append('.');
                    if (this.o[i2] == null) {
                        break;
                    }
                    stringBuilder.append(this.o[i2]);
                    break;
                default:
                    break;
            }
        }
        return stringBuilder.toString();
    }

    public String toString() {
        return getClass().getSimpleName() + z[7] + (this.g + 1) + z[6] + u();
    }
}
