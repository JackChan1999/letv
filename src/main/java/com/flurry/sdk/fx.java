package com.flurry.sdk;

import com.flurry.sdk.gz.a;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public final class fx {
    private static final String a = fx.class.getSimpleName();

    public static gz a(File file) {
        Throwable e;
        if (file == null || !file.exists()) {
            return null;
        }
        a aVar = new a();
        Closeable fileInputStream;
        Closeable dataInputStream;
        gz gzVar;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                dataInputStream = new DataInputStream(fileInputStream);
            } catch (Exception e2) {
                e = e2;
                dataInputStream = null;
                try {
                    ib.a(3, a, "Error loading legacy agent data.", e);
                    jn.a(dataInputStream);
                    jn.a(fileInputStream);
                    gzVar = null;
                    return gzVar;
                } catch (Throwable th) {
                    e = th;
                    jn.a(dataInputStream);
                    jn.a(fileInputStream);
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                dataInputStream = null;
                jn.a(dataInputStream);
                jn.a(fileInputStream);
                throw e;
            }
            try {
                if (dataInputStream.readUnsignedShort() != 46586) {
                    ib.a(3, a, "Unexpected file type");
                    jn.a(dataInputStream);
                    jn.a(fileInputStream);
                    return null;
                }
                int readUnsignedShort = dataInputStream.readUnsignedShort();
                if (readUnsignedShort != 2) {
                    ib.a(6, a, "Unknown agent file version: " + readUnsignedShort);
                    jn.a(dataInputStream);
                    jn.a(fileInputStream);
                    return null;
                }
                gzVar = (gz) aVar.b(dataInputStream);
                jn.a(dataInputStream);
                jn.a(fileInputStream);
                return gzVar;
            } catch (Exception e3) {
                e = e3;
                ib.a(3, a, "Error loading legacy agent data.", e);
                jn.a(dataInputStream);
                jn.a(fileInputStream);
                gzVar = null;
                return gzVar;
            }
        } catch (Exception e4) {
            e = e4;
            dataInputStream = null;
            fileInputStream = null;
            ib.a(3, a, "Error loading legacy agent data.", e);
            jn.a(dataInputStream);
            jn.a(fileInputStream);
            gzVar = null;
            return gzVar;
        } catch (Throwable th3) {
            e = th3;
            dataInputStream = null;
            fileInputStream = null;
            jn.a(dataInputStream);
            jn.a(fileInputStream);
            throw e;
        }
    }
}
