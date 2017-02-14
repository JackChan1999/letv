package com.flurry.sdk;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class it<ObjectType> extends is<ObjectType> {
    public it(iv<ObjectType> ivVar) {
        super(ivVar);
    }

    public void a(OutputStream outputStream, ObjectType objectType) throws IOException {
        Closeable gZIPOutputStream;
        Throwable th;
        if (outputStream != null) {
            try {
                gZIPOutputStream = new GZIPOutputStream(outputStream);
                try {
                    super.a(gZIPOutputStream, objectType);
                    jn.a(gZIPOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    jn.a(gZIPOutputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                gZIPOutputStream = null;
                jn.a(gZIPOutputStream);
                throw th;
            }
        }
    }

    public ObjectType b(InputStream inputStream) throws IOException {
        Closeable gZIPInputStream;
        Throwable th;
        ObjectType objectType = null;
        if (inputStream != null) {
            try {
                gZIPInputStream = new GZIPInputStream(inputStream);
                try {
                    objectType = super.b(gZIPInputStream);
                    jn.a(gZIPInputStream);
                } catch (Throwable th2) {
                    th = th2;
                    jn.a(gZIPInputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                gZIPInputStream = null;
                th = th4;
                jn.a(gZIPInputStream);
                throw th;
            }
        }
        return objectType;
    }
}
