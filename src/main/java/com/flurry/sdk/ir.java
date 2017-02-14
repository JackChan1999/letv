package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ir implements iv<byte[]> {
    public /* synthetic */ Object b(InputStream inputStream) throws IOException {
        return a(inputStream);
    }

    public void a(OutputStream outputStream, byte[] bArr) throws IOException {
        if (outputStream != null && bArr != null) {
            outputStream.write(bArr, 0, bArr.length);
        }
    }

    public byte[] a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        jn.a(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
