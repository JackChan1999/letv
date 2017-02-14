package com.google.gson.jpush.b;

import com.letv.pp.utils.NetworkUtils;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class d implements Closeable, Flushable {
    private static final String[] a = new String[128];
    private static final String[] b;
    private static final String[] z;
    private final Writer c;
    private int[] d = new int[32];
    private int e = 0;
    private String f;
    private String g;
    private boolean h;
    private boolean i;
    private String j;
    private boolean k;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 18;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "$S";
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
            case 0: goto L_0x011c;
            case 1: goto L_0x0120;
            case 2: goto L_0x0124;
            case 3: goto L_0x0128;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 105; // 0x69 float:1.47E-43 double:5.2E-322;
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
            case 3: goto L_0x005d;
            case 4: goto L_0x0066;
            case 5: goto L_0x006e;
            case 6: goto L_0x0076;
            case 7: goto L_0x007f;
            case 8: goto L_0x0089;
            case 9: goto L_0x0094;
            case 10: goto L_0x009f;
            case 11: goto L_0x00ab;
            case 12: goto L_0x00b6;
            case 13: goto L_0x00c1;
            case 14: goto L_0x00cc;
            case 15: goto L_0x00d7;
            case 16: goto L_0x00e3;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "T \u0019'Is\u0006%\u001dIv\u0012 \fIq\u001d:\u0010Iq\u001d3I\u001dq\u0003{\u0005\fh\u0016:I\u001f\u001f#\fG";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "T \u0019'Is\u0006%\u001dIm\u00077\u001b\u001d>\u0004?\u001d\u0001>\u00128I\bl\u00017\u0010Iq\u0001v\b\u0007>\u001c4\u0003\f}\u0007x";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "P\u0016%\u001d\u0000p\u0014v\u0019\u001bq\u0011:\f\u00040";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "q\u0006\"IT#S8\u001c\u0005r";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005d:
        r3[r2] = r1;
        r2 = 5;
        r1 = "x\u0012:\u001a\f";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0066:
        r3[r2] = r1;
        r2 = 6;
        r1 = "j\u0001#\f";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006e:
        r3[r2] = r1;
        r2 = 7;
        r1 = "W\u001d5\u0006\u0004n\u001f3\u001d\f>\u00179\n\u001cs\u00168\u001d";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0076:
        r3[r2] = r1;
        r2 = 8;
        r1 = "W\u001d0\u0000\u0007w\u0007/";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007f:
        r3[r2] = r1;
        r2 = 9;
        r1 = "P\u0006;\f\u001bw\u0010v\u001f\br\u00063\u001aIs\u0006%\u001dI|\u0016v\u000f\u0000p\u001a\"\fE>\u0011#\u001dIi\u0012%I";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0089:
        r3[r2] = r1;
        r2 = 10;
        r1 = "3:8\u000f\u0000p\u001a\"\u0010";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0094:
        r3[r2] = r1;
        r2 = 11;
        r1 = "P\u0012\u0018";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009f:
        r3[r2] = r1;
        r2 = 12;
        r1 = "p\u0006:\u0005";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00ab:
        r3[r2] = r1;
        r2 = 13;
        r1 = "T\u00009\u0007>l\u001a\"\f\u001b>\u001a%I\nr\u001c%\f\r0";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b6:
        r3[r2] = r1;
        r2 = 14;
        r1 = "Z\u00128\u000e\u0005w\u001d1I\u0007\u001e3SI";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c1:
        r3[r2] = r1;
        r2 = 15;
        r1 = "B\u0006dY['";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00cc:
        r3[r2] = r1;
        r2 = 16;
        r1 = "B\u0006dY[&";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d7:
        r3[r2] = r1;
        r2 = 17;
        r1 = "p\u0012;\fI#Nv\u0007\u001cr\u001f";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e3:
        r3[r2] = r1;
        z = r4;
        r0 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r0 = new java.lang.String[r0];
        a = r0;
        r3 = 0;
    L_0x00ee:
        r0 = 31;
        if (r3 > r0) goto L_0x015b;
    L_0x00f2:
        r2 = a;
        r1 = "B\u0006sY]f";
        r0 = -1;
        r4 = r2;
        r2 = r3;
    L_0x00f9:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x013a;
    L_0x0102:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0107:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x012c;
            case 1: goto L_0x012f;
            case 2: goto L_0x0132;
            case 3: goto L_0x0135;
            default: goto L_0x010e;
        };
    L_0x010e:
        r9 = 105; // 0x69 float:1.47E-43 double:5.2E-322;
    L_0x0110:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x0138;
    L_0x0118:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0107;
    L_0x011c:
        r9 = 30;
        goto L_0x0020;
    L_0x0120:
        r9 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        goto L_0x0020;
    L_0x0124:
        r9 = 86;
        goto L_0x0020;
    L_0x0128:
        r9 = 105; // 0x69 float:1.47E-43 double:5.2E-322;
        goto L_0x0020;
    L_0x012c:
        r9 = 30;
        goto L_0x0110;
    L_0x012f:
        r9 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        goto L_0x0110;
    L_0x0132:
        r9 = 86;
        goto L_0x0110;
    L_0x0135:
        r9 = 105; // 0x69 float:1.47E-43 double:5.2E-322;
        goto L_0x0110;
    L_0x0138:
        r5 = r1;
        r1 = r7;
    L_0x013a:
        if (r5 > r6) goto L_0x0102;
    L_0x013c:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0163;
            case 1: goto L_0x016d;
            case 2: goto L_0x0177;
            case 3: goto L_0x0182;
            case 4: goto L_0x018d;
            case 5: goto L_0x0198;
            case 6: goto L_0x01a3;
            case 7: goto L_0x01bb;
            case 8: goto L_0x01c7;
            case 9: goto L_0x01d3;
            case 10: goto L_0x01df;
            case 11: goto L_0x01eb;
            default: goto L_0x0148;
        };
    L_0x0148:
        r0 = 1;
        r0 = new java.lang.Object[r0];
        r5 = 0;
        r6 = java.lang.Integer.valueOf(r3);
        r0[r5] = r6;
        r0 = java.lang.String.format(r1, r0);
        r4[r2] = r0;
        r3 = r3 + 1;
        goto L_0x00ee;
    L_0x015b:
        r4 = a;
        r2 = 34;
        r1 = "BQ";
        r0 = 0;
        goto L_0x00f9;
    L_0x0163:
        r4[r2] = r1;
        r4 = a;
        r2 = 92;
        r1 = "B/";
        r0 = 1;
        goto L_0x00f9;
    L_0x016d:
        r4[r2] = r1;
        r4 = a;
        r2 = 9;
        r1 = "B\u0007";
        r0 = 2;
        goto L_0x00f9;
    L_0x0177:
        r4[r2] = r1;
        r4 = a;
        r2 = 8;
        r1 = "B\u0011";
        r0 = 3;
        goto L_0x00f9;
    L_0x0182:
        r4[r2] = r1;
        r4 = a;
        r2 = 10;
        r1 = "B\u001d";
        r0 = 4;
        goto L_0x00f9;
    L_0x018d:
        r4[r2] = r1;
        r4 = a;
        r2 = 13;
        r1 = "B\u0001";
        r0 = 5;
        goto L_0x00f9;
    L_0x0198:
        r4[r2] = r1;
        r4 = a;
        r2 = 12;
        r1 = "B\u0015";
        r0 = 6;
        goto L_0x00f9;
    L_0x01a3:
        r4[r2] = r1;
        r0 = a;
        r0 = r0.clone();
        r0 = (java.lang.String[]) r0;
        b = r0;
        r4 = 60;
        r2 = "B\u0006fYZ}";
        r1 = 7;
        r11 = r1;
        r1 = r2;
        r2 = r4;
        r4 = r0;
        r0 = r11;
        goto L_0x00f9;
    L_0x01bb:
        r4[r2] = r1;
        r4 = b;
        r2 = 62;
        r1 = "B\u0006fYZ{";
        r0 = 8;
        goto L_0x00f9;
    L_0x01c7:
        r4[r2] = r1;
        r4 = b;
        r2 = 38;
        r1 = "B\u0006fY[(";
        r0 = 9;
        goto L_0x00f9;
    L_0x01d3:
        r4[r2] = r1;
        r4 = b;
        r2 = 61;
        r1 = "B\u0006fYZz";
        r0 = 10;
        goto L_0x00f9;
    L_0x01df:
        r4[r2] = r1;
        r4 = b;
        r2 = 39;
        r1 = "B\u0006fY[)";
        r0 = 11;
        goto L_0x00f9;
    L_0x01eb:
        r4[r2] = r1;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.b.d.<clinit>():void");
    }

    public d(Writer writer) {
        a(6);
        this.g = NetworkUtils.DELIMITER_COLON;
        this.k = true;
        if (writer == null) {
            throw new NullPointerException(z[4]);
        }
        this.c = writer;
    }

    private int a() {
        if (this.e != 0) {
            return this.d[this.e - 1];
        }
        throw new IllegalStateException(z[13]);
    }

    private d a(int i, int i2, String str) {
        int a = a();
        if (a != i2 && a != i) {
            throw new IllegalStateException(z[3]);
        } else if (this.j != null) {
            throw new IllegalStateException(new StringBuilder(z[14]).append(this.j).toString());
        } else {
            this.e--;
            if (a == i2) {
                k();
            }
            this.c.write(str);
            return this;
        }
    }

    private d a(int i, String str) {
        e(true);
        a(i);
        this.c.write(str);
        return this;
    }

    private void a(int i) {
        if (this.e == this.d.length) {
            Object obj = new int[(this.e * 2)];
            System.arraycopy(this.d, 0, obj, 0, this.e);
            this.d = obj;
        }
        int[] iArr = this.d;
        int i2 = this.e;
        this.e = i2 + 1;
        iArr[i2] = i;
    }

    private void b(int i) {
        this.d[this.e - 1] = i;
    }

    private void d(String str) {
        int i = 0;
        String[] strArr = this.i ? b : a;
        this.c.write("\"");
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            String str2;
            if (charAt < '') {
                str2 = strArr[charAt];
                if (str2 == null) {
                }
                if (i < i2) {
                    this.c.write(str, i, i2 - i);
                }
                this.c.write(str2);
                i = i2 + 1;
            } else {
                if (charAt == ' ') {
                    str2 = z[16];
                } else if (charAt == ' ') {
                    str2 = z[15];
                }
                if (i < i2) {
                    this.c.write(str, i, i2 - i);
                }
                this.c.write(str2);
                i = i2 + 1;
            }
        }
        if (i < length) {
            this.c.write(str, i, length - i);
        }
        this.c.write("\"");
    }

    private void e(boolean z) {
        switch (a()) {
            case 1:
                b(2);
                k();
                return;
            case 2:
                this.c.append(',');
                k();
                return;
            case 4:
                this.c.append(this.g);
                b(5);
                return;
            case 6:
                break;
            case 7:
                if (!this.h) {
                    throw new IllegalStateException(z[1]);
                }
                break;
            default:
                throw new IllegalStateException(z[3]);
        }
        if (this.h || z) {
            b(7);
            return;
        }
        throw new IllegalStateException(z[2]);
    }

    private void j() {
        if (this.j != null) {
            int a = a();
            if (a == 5) {
                this.c.write(44);
            } else if (a != 3) {
                throw new IllegalStateException(z[3]);
            }
            k();
            b(4);
            d(this.j);
            this.j = null;
        }
    }

    private void k() {
        if (this.f != null) {
            this.c.write("\n");
            int i = this.e;
            for (int i2 = 1; i2 < i; i2++) {
                this.c.write(this.f);
            }
        }
    }

    public d a(long j) {
        j();
        e(false);
        this.c.write(Long.toString(j));
        return this;
    }

    public d a(Number number) {
        if (number == null) {
            return f();
        }
        j();
        CharSequence obj = number.toString();
        if (this.h || !(obj.equals(z[10]) || obj.equals(z[8]) || obj.equals(z[11]))) {
            e(false);
            this.c.append(obj);
            return this;
        }
        throw new IllegalArgumentException(new StringBuilder(z[9]).append(number).toString());
    }

    public d a(String str) {
        if (str == null) {
            throw new NullPointerException(z[17]);
        } else if (this.j != null) {
            throw new IllegalStateException();
        } else if (this.e == 0) {
            throw new IllegalStateException(z[13]);
        } else {
            this.j = str;
            return this;
        }
    }

    public d a(boolean z) {
        j();
        e(false);
        this.c.write(z ? z[6] : z[5]);
        return this;
    }

    public d b() {
        j();
        return a(1, "[");
    }

    public d b(String str) {
        if (str == null) {
            return f();
        }
        j();
        e(false);
        d(str);
        return this;
    }

    public final void b(boolean z) {
        this.h = z;
    }

    public d c() {
        return a(1, 2, "]");
    }

    public final void c(String str) {
        if (str.length() == 0) {
            this.f = null;
            this.g = NetworkUtils.DELIMITER_COLON;
            return;
        }
        this.f = str;
        this.g = z[0];
    }

    public final void c(boolean z) {
        this.i = z;
    }

    public void close() {
        this.c.close();
        int i = this.e;
        if (i > 1 || (i == 1 && this.d[i - 1] != 7)) {
            throw new IOException(z[7]);
        }
        this.e = 0;
    }

    public d d() {
        j();
        return a(3, "{");
    }

    public final void d(boolean z) {
        this.k = z;
    }

    public d e() {
        return a(3, 5, "}");
    }

    public d f() {
        if (this.j != null) {
            if (this.k) {
                j();
            } else {
                this.j = null;
                return this;
            }
        }
        e(false);
        this.c.write(z[12]);
        return this;
    }

    public void flush() {
        if (this.e == 0) {
            throw new IllegalStateException(z[13]);
        }
        this.c.flush();
    }

    public final boolean g() {
        return this.h;
    }

    public final boolean h() {
        return this.i;
    }

    public final boolean i() {
        return this.k;
    }
}
