package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class iw implements iv<String> {
    public /* synthetic */ Object b(InputStream inputStream) throws IOException {
        return a(inputStream);
    }

    public void a(OutputStream outputStream, String str) throws IOException {
        if (outputStream != null && str != null) {
            byte[] bytes = str.getBytes("utf-8");
            outputStream.write(bytes, 0, bytes.length);
        }
    }

    public String a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        jn.a(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toString();
    }
}
