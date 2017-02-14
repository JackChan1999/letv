package com.google.protobuf.jpush;

import android.support.v4.media.TransportMediator;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class d {
    private static final String[] z;
    private final byte[] a;
    private int b;
    private int c;
    private int d;
    private final InputStream e;
    private int f;
    private int g;
    private int h = Integer.MAX_VALUE;
    private int i;
    private int j = 64;
    private int k = 67108864;

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
        r12 = 3;
        r2 = 1;
        r1 = 0;
        r4 = new java.lang.String[r12];
        r3 = "\td'L>J^?\\jPD=Ln\u0010&DnoU\"LpwQ;@qm\u0010&Z>aE(Ng-";
        r0 = -1;
        r5 = r4;
        r6 = r4;
        r4 = r1;
    L_0x000b:
        r3 = r3.toCharArray();
        r7 = r3.length;
        if (r7 > r2) goto L_0x0061;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r3;
        r10 = r8;
        r14 = r7;
        r7 = r3;
        r3 = r14;
    L_0x0018:
        r13 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x0056;
            case 1: goto L_0x0058;
            case 2: goto L_0x005b;
            case 3: goto L_0x005e;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 30;
    L_0x0021:
        r11 = r11 ^ r13;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r3 != 0) goto L_0x002d;
    L_0x0029:
        r7 = r9;
        r10 = r8;
        r8 = r3;
        goto L_0x0018;
    L_0x002d:
        r7 = r3;
        r3 = r9;
    L_0x002f:
        if (r7 > r8) goto L_0x0013;
    L_0x0031:
        r7 = new java.lang.String;
        r7.<init>(r3);
        r3 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0046;
            case 1: goto L_0x0051;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r4] = r3;
        r0 = "J^?\\jPD=Ln\u0013=Lg\u0018-Pjfk\u0012\u0000>qU;\\lmU+\twmF.Ewg\u0010=Lmv\\;\u0013>";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r4] = r3;
        r3 = 2;
        r0 = "qU)@ror:OxfBg\u0000>`Q#E{g\u00108A{m\u0010-\\xeU=\tibC!\u000ej#U\"Yjz\u001e";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000b;
    L_0x0051:
        r5[r4] = r3;
        z = r6;
        return;
    L_0x0056:
        r11 = r12;
        goto L_0x0021;
    L_0x0058:
        r11 = 48;
        goto L_0x0021;
    L_0x005b:
        r11 = 79;
        goto L_0x0021;
    L_0x005e:
        r11 = 41;
        goto L_0x0021;
    L_0x0061:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.jpush.d.<clinit>():void");
    }

    private d(byte[] bArr, int i, int i2) {
        this.a = bArr;
        this.b = i + i2;
        this.d = i;
        this.g = -i;
        this.e = null;
    }

    public static d a(byte[] bArr, int i, int i2) {
        d dVar = new d(bArr, i, i2);
        try {
            dVar.c(i2);
            return dVar;
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    private boolean a(boolean z) {
        if (this.d < this.b) {
            throw new IllegalStateException(z[2]);
        } else if (this.g + this.b != this.h) {
            this.g += this.b;
            this.d = 0;
            this.b = this.e == null ? -1 : this.e.read(this.a);
            if (this.b == 0 || this.b < -1) {
                throw new IllegalStateException(new StringBuilder(z[1]).append(this.b).append(z[0]).toString());
            } else if (this.b == -1) {
                this.b = 0;
                if (!z) {
                    return false;
                }
                throw j.a();
            } else {
                h();
                int i = (this.g + this.b) + this.c;
                if (i <= this.k && i >= 0) {
                    return true;
                }
                throw j.h();
            }
        } else if (!z) {
            return false;
        } else {
            throw j.a();
        }
    }

    private void e(int i) {
        if (i < 0) {
            throw j.b();
        } else if ((this.g + this.d) + i > this.h) {
            e((this.h - this.g) - this.d);
            throw j.a();
        } else if (i <= this.b - this.d) {
            this.d += i;
        } else {
            int i2 = this.b - this.d;
            this.d = this.b;
            a(true);
            while (i - i2 > this.b) {
                i2 += this.b;
                this.d = this.b;
                a(true);
            }
            this.d = i - i2;
        }
    }

    private void h() {
        this.b += this.c;
        int i = this.g + this.b;
        if (i > this.h) {
            this.c = i - this.h;
            this.b -= this.c;
            return;
        }
        this.c = 0;
    }

    private byte i() {
        if (this.d == this.b) {
            a(true);
        }
        byte[] bArr = this.a;
        int i = this.d;
        this.d = i + 1;
        return bArr[i];
    }

    public final int a() {
        boolean z = this.d == this.b && !a(false);
        if (z) {
            this.f = 0;
            return 0;
        }
        this.f = f();
        if (n.b(this.f) != 0) {
            return this.f;
        }
        throw j.d();
    }

    public final void a(int i) {
        if (this.f != i) {
            throw j.e();
        }
    }

    public final void a(l lVar, g gVar) {
        int f = f();
        if (this.i >= this.j) {
            throw j.g();
        }
        f = c(f);
        this.i++;
        lVar.b(this, gVar);
        a(0);
        this.i--;
        d(f);
    }

    public final long b() {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte i2 = i();
            j |= ((long) (i2 & TransportMediator.KEYCODE_MEDIA_PAUSE)) << i;
            if ((i2 & 128) == 0) {
                return j;
            }
        }
        throw j.c();
    }

    public final boolean b(int i) {
        switch (n.a(i)) {
            case 0:
                f();
                return true;
            case 1:
                byte i2 = i();
                byte i3 = i();
                long j = (((long) i3) & 255) << 8;
                j = ((((((j | (((long) i2) & 255)) | ((((long) i()) & 255) << 16)) | ((((long) i()) & 255) << 24)) | ((((long) i()) & 255) << 32)) | ((((long) i()) & 255) << 40)) | ((((long) i()) & 255) << 48)) | ((((long) i()) & 255) << 56);
                return true;
            case 2:
                e(f());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                int i4 = (((i() & 255) | ((i() & 255) << 8)) | ((i() & 255) << 16)) | ((i() & 255) << 24);
                return true;
            default:
                throw j.f();
        }
        do {
            i4 = a();
            if (i4 != 0) {
            }
            a(n.a(n.b(i), 4));
            return true;
        } while (b(i4));
        a(n.a(n.b(i), 4));
        return true;
    }

    public final int c() {
        return f();
    }

    public final int c(int i) {
        if (i < 0) {
            throw j.b();
        }
        int i2 = (this.g + this.d) + i;
        int i3 = this.h;
        if (i2 > i3) {
            throw j.a();
        }
        this.h = i2;
        h();
        return i3;
    }

    public final c d() {
        int f = f();
        if (f == 0) {
            return c.a;
        }
        if (f <= this.b - this.d && f > 0) {
            c a = c.a(this.a, this.d, f);
            this.d += f;
            return a;
        } else if (f < 0) {
            throw j.b();
        } else if ((this.g + this.d) + f > this.h) {
            e((this.h - this.g) - this.d);
            throw j.a();
        } else {
            byte[] bArr;
            if (f <= this.b - this.d) {
                bArr = new byte[f];
                System.arraycopy(this.a, this.d, bArr, 0, f);
                this.d += f;
            } else if (f < 4096) {
                Object obj = new byte[f];
                r0 = this.b - this.d;
                System.arraycopy(this.a, this.d, obj, 0, r0);
                this.d = this.b;
                a(true);
                while (f - r0 > this.b) {
                    System.arraycopy(this.a, 0, obj, r0, this.b);
                    r0 += this.b;
                    this.d = this.b;
                    a(true);
                }
                System.arraycopy(this.a, 0, obj, r0, f - r0);
                this.d = f - r0;
                r0 = obj;
            } else {
                int read;
                int i = this.d;
                int i2 = this.b;
                this.g += this.b;
                this.d = 0;
                this.b = 0;
                r0 = f - (i2 - i);
                List<byte[]> arrayList = new ArrayList();
                int i3 = r0;
                while (i3 > 0) {
                    Object obj2 = new byte[Math.min(i3, 4096)];
                    r0 = 0;
                    while (r0 < obj2.length) {
                        read = this.e == null ? -1 : this.e.read(obj2, r0, obj2.length - r0);
                        if (read == -1) {
                            throw j.a();
                        }
                        this.g += read;
                        r0 += read;
                    }
                    r0 = i3 - obj2.length;
                    arrayList.add(obj2);
                    i3 = r0;
                }
                Object obj3 = new byte[f];
                r0 = i2 - i;
                System.arraycopy(this.a, i, obj3, 0, r0);
                read = r0;
                for (byte[] bArr2 : arrayList) {
                    System.arraycopy(bArr2, 0, obj3, read, bArr2.length);
                    read = bArr2.length + read;
                }
                r0 = obj3;
            }
            return c.a(bArr2);
        }
    }

    public final void d(int i) {
        this.h = i;
        h();
    }

    public final int e() {
        return f();
    }

    public final int f() {
        byte i = i();
        if (i >= (byte) 0) {
            return i;
        }
        int i2 = i & TransportMediator.KEYCODE_MEDIA_PAUSE;
        byte i3 = i();
        if (i3 >= (byte) 0) {
            return i2 | (i3 << 7);
        }
        i2 |= (i3 & TransportMediator.KEYCODE_MEDIA_PAUSE) << 7;
        i3 = i();
        if (i3 >= (byte) 0) {
            return i2 | (i3 << 14);
        }
        i2 |= (i3 & TransportMediator.KEYCODE_MEDIA_PAUSE) << 14;
        i3 = i();
        if (i3 >= (byte) 0) {
            return i2 | (i3 << 21);
        }
        i2 |= (i3 & TransportMediator.KEYCODE_MEDIA_PAUSE) << 21;
        i3 = i();
        i2 |= i3 << 28;
        if (i3 >= (byte) 0) {
            return i2;
        }
        for (int i4 = 0; i4 < 5; i4++) {
            if (i() >= (byte) 0) {
                return i2;
            }
        }
        throw j.c();
    }

    public final int g() {
        if (this.h == Integer.MAX_VALUE) {
            return -1;
        }
        return this.h - (this.g + this.d);
    }
}
