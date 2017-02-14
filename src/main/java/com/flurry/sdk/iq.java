package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class iq {
    private String a;

    public static class a implements iv<iq> {
        public /* synthetic */ Object b(InputStream inputStream) throws IOException {
            return a(inputStream);
        }

        public void a(OutputStream outputStream, iq iqVar) throws IOException {
            if (outputStream != null && iqVar != null) {
                DataOutputStream anonymousClass1 = new DataOutputStream(this, outputStream) {
                    final /* synthetic */ a a;

                    public void close() {
                    }
                };
                anonymousClass1.writeUTF(iqVar.a);
                anonymousClass1.flush();
            }
        }

        public iq a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream anonymousClass2 = new DataInputStream(this, inputStream) {
                final /* synthetic */ a a;

                public void close() {
                }
            };
            iq iqVar = new iq();
            iqVar.a = anonymousClass2.readUTF();
            return iqVar;
        }
    }

    private iq() {
    }

    public iq(String str) {
        this.a = str;
    }

    public String a() {
        return this.a;
    }
}
