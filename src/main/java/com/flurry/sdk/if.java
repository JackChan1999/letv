package com.flurry.sdk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class if<ObjectType> {
    private static final String a = if.class.getSimpleName();
    private static final byte[] b = new byte[]{(byte) 113, (byte) -92, (byte) -8, (byte) 125, (byte) 121, (byte) 107, (byte) -65, (byte) -61, (byte) -74, (byte) -114, (byte) -32, (byte) 0, (byte) -57, (byte) -87, (byte) -35, (byte) -56, (byte) -6, (byte) -52, (byte) 51, (byte) 126, (byte) -104, (byte) 49, (byte) 79, (byte) -52, (byte) 118, (byte) -84, (byte) 99, (byte) -52, (byte) -14, (byte) -126, (byte) -27, (byte) -64};
    private String c;
    private iv<ObjectType> d;

    public static void a(byte[] bArr) {
        if (bArr != null) {
            int length = bArr.length;
            int length2 = b.length;
            for (int i = 0; i < length; i++) {
                bArr[i] = (byte) ((bArr[i] ^ b[i % length2]) ^ ((i * 31) % 251));
            }
        }
    }

    public static void b(byte[] bArr) {
        a(bArr);
    }

    public static int c(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        ht htVar = new ht();
        htVar.update(bArr);
        return htVar.b();
    }

    public if(String str, iv<ObjectType> ivVar) {
        this.c = str;
        this.d = ivVar;
    }

    public byte[] a(ObjectType objectType) throws IOException {
        if (objectType == null) {
            throw new IOException("Encoding: " + this.c + ": Nothing to encode");
        }
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.d.a(byteArrayOutputStream, objectType);
        Object toByteArray = byteArrayOutputStream.toByteArray();
        ib.a(3, a, "Encoding " + this.c + ": " + new String(toByteArray));
        iv itVar = new it(new ir());
        OutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        itVar.a(byteArrayOutputStream2, toByteArray);
        byte[] toByteArray2 = byteArrayOutputStream2.toByteArray();
        a(toByteArray2);
        return toByteArray2;
    }

    public ObjectType d(byte[] bArr) throws IOException {
        if (bArr == null) {
            throw new IOException("Decoding: " + this.c + ": Nothing to decode");
        }
        b(bArr);
        byte[] bArr2 = (byte[]) new it(new ir()).b(new ByteArrayInputStream(bArr));
        ib.a(3, a, "Decoding: " + this.c + ": " + new String(bArr2));
        return this.d.b(new ByteArrayInputStream(bArr2));
    }
}
