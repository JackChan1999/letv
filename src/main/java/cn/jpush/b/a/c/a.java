package cn.jpush.b.a.c;

import cn.jpush.android.util.g;
import cn.jpush.b.a.a.h;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public final class a {
    private static final String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "0\u001a\u001eMy";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0027;
    L_0x000b:
        r3 = r0;
        r4 = r2;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x0010:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0035;
            case 1: goto L_0x0038;
            case 2: goto L_0x003b;
            case 3: goto L_0x003e;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 65;
    L_0x0019:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0025;
    L_0x0021:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0010;
    L_0x0025:
        r1 = r0;
        r0 = r3;
    L_0x0027:
        if (r1 > r2) goto L_0x000b;
    L_0x0029:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        return;
    L_0x0035:
        r5 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        goto L_0x0019;
    L_0x0038:
        r5 = 78;
        goto L_0x0019;
    L_0x003b:
        r5 = 88;
        goto L_0x0019;
    L_0x003e:
        r5 = 96;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.c.a.<clinit>():void");
    }

    public static String a(ByteBuffer byteBuffer, h hVar) {
        byte[] bArr = new byte[g.b(byteBuffer, hVar)];
        g.a(byteBuffer, bArr, hVar);
        try {
            return new String(bArr, z);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static void a(byte[] bArr, int i, int i2) {
        System.arraycopy(new byte[]{(byte) (i >>> 24), (byte) (i >>> 16), (byte) (i >>> 8), (byte) i}, 0, bArr, i2, 4);
    }

    public static void a(byte[] bArr, String str, int i) {
        Object bytes = str.getBytes();
        System.arraycopy(bytes, 0, bArr, i, bytes.length);
    }

    public static byte[] a(String str) {
        if (str == null || "".equals(str)) {
            return new byte[]{(byte) 0, (byte) 0};
        }
        Object obj = null;
        try {
            obj = str.getBytes(z);
        } catch (UnsupportedEncodingException e) {
        }
        short length = (short) obj.length;
        Object obj2 = new byte[(length + 2)];
        System.arraycopy(new byte[]{(byte) (length >>> 8), (byte) length}, 0, obj2, 0, 2);
        System.arraycopy(obj, 0, obj2, 2, length);
        return obj2;
    }

    public static byte[] a(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[byteBuffer.remaining()];
        byteBuffer.get(bArr);
        return bArr;
    }

    public static String b(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[byteBuffer.getShort()];
        byteBuffer.get(bArr);
        try {
            return new String(bArr, z);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
