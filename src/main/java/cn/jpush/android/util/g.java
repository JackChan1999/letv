package cn.jpush.android.util;

import cn.jpush.android.api.c;
import cn.jpush.b.a.a.h;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public final class g {
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 6;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "zEn\u0007*@}x\u0003*An~\rx\u0004ox\u0003iOH~\u0003iA&";
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
            case 0: goto L_0x006a;
            case 1: goto L_0x006d;
            case 2: goto L_0x0070;
            case 3: goto L_0x0073;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 98;
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
            case 0: goto L_0x0045;
            case 1: goto L_0x004d;
            case 2: goto L_0x0055;
            case 3: goto L_0x005d;
            case 4: goto L_0x0065;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "vFex\u0007hQzj\u0007x\u001e";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0045:
        r3[r2] = r1;
        r2 = 2;
        r1 = "H]hi Bzi\u0010_Pu`\u0011";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004d:
        r3[r2] = r1;
        r2 = 3;
        r1 = "`vy\u0012eJoiBcW<b\u0017fH";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0055:
        r3[r2] = r1;
        r2 = 4;
        r1 = "h]hi Bzi\u0010*Mrj\r0";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005d:
        r3[r2] = r1;
        r2 = 5;
        r1 = "h]hi Bzi\u0010*Mo,\fHp";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0065:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x006a:
        r9 = 10;
        goto L_0x0020;
    L_0x006d:
        r9 = 36;
        goto L_0x0020;
    L_0x0070:
        r9 = 28;
        goto L_0x0020;
    L_0x0073:
        r9 = 12;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.g.<clinit>():void");
    }

    public static int a(ByteBuffer byteBuffer, h hVar) {
        try {
            return byteBuffer.getInt();
        } catch (BufferUnderflowException e) {
            a(e.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return -1;
        } catch (BufferOverflowException e2) {
            a(e2.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return -1;
        } catch (Exception e3) {
            a(e3.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return -1;
        }
    }

    public static ByteBuffer a(ByteBuffer byteBuffer, byte[] bArr, h hVar) {
        try {
            return byteBuffer.get(bArr);
        } catch (BufferUnderflowException e) {
            a(e.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return null;
        } catch (BufferOverflowException e2) {
            a(e2.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return null;
        } catch (Exception e3) {
            a(e3.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return null;
        }
    }

    private static void a(Throwable th, h hVar, ByteBuffer byteBuffer) {
        c a = c.a();
        StringBuilder stringBuilder = new StringBuilder();
        if (hVar != null) {
            stringBuilder.append(hVar == null ? z[3] : hVar.toString());
            stringBuilder.append(new StringBuilder(z[1]).append(byteBuffer == null ? z[5] : byteBuffer.toString()).toString());
        }
        z.e(z[2], new StringBuilder(z[4]).append(stringBuilder.toString()).toString());
        z.e(z[2], new StringBuilder(z[0]).append(th.getStackTrace().toString()).toString());
        a.a(th, stringBuilder.toString());
    }

    public static short b(ByteBuffer byteBuffer, h hVar) {
        try {
            return byteBuffer.getShort();
        } catch (BufferUnderflowException e) {
            a(e.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return (short) -1;
        } catch (BufferOverflowException e2) {
            a(e2.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return (short) -1;
        } catch (Exception e3) {
            a(e3.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return (short) -1;
        }
    }

    public static Byte c(ByteBuffer byteBuffer, h hVar) {
        try {
            return Byte.valueOf(byteBuffer.get());
        } catch (BufferUnderflowException e) {
            a(e.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return null;
        } catch (BufferOverflowException e2) {
            a(e2.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return null;
        } catch (Exception e3) {
            a(e3.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return null;
        }
    }

    public static long d(ByteBuffer byteBuffer, h hVar) {
        try {
            return byteBuffer.getLong();
        } catch (BufferUnderflowException e) {
            a(e.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return 0;
        } catch (BufferOverflowException e2) {
            a(e2.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return 0;
        } catch (Exception e3) {
            a(e3.fillInStackTrace(), hVar, byteBuffer);
            if (hVar != null) {
                hVar.g = 10000;
            }
            return 0;
        }
    }
}
