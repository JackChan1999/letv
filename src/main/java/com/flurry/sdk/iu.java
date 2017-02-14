package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class iu<T> implements iv<List<T>> {
    iv<T> a;

    public /* synthetic */ Object b(InputStream inputStream) throws IOException {
        return a(inputStream);
    }

    public iu(iv<T> ivVar) {
        if (ivVar == null) {
            throw new IllegalArgumentException("recordSerializer cannot be null");
        }
        this.a = ivVar;
    }

    public void a(OutputStream outputStream, List<T> list) throws IOException {
        int i = 0;
        if (outputStream != null) {
            int size;
            DataOutputStream anonymousClass1 = new DataOutputStream(this, outputStream) {
                final /* synthetic */ iu a;

                public void close() {
                }
            };
            if (list != null) {
                size = list.size();
            } else {
                size = 0;
            }
            anonymousClass1.writeInt(size);
            while (i < size) {
                this.a.a(outputStream, list.get(i));
                i++;
            }
            anonymousClass1.flush();
        }
    }

    public List<T> a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        int readInt = new DataInputStream(this, inputStream) {
            final /* synthetic */ iu a;

            public void close() {
            }
        }.readInt();
        List<T> arrayList = new ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            Object b = this.a.b(inputStream);
            if (b == null) {
                throw new IOException("Missing record.");
            }
            arrayList.add(b);
        }
        return arrayList;
    }
}
