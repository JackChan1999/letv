package com.letv.lemallsdk.api.http;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

public class SimpleMultipartEntity implements HttpEntity {
    private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private String boundary = null;
    boolean isSetFirst = false;
    boolean isSetLast = false;
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    public SimpleMultipartEntity() {
        StringBuffer buf = new StringBuffer();
        Random rand = new Random();
        for (int i = 0; i < 30; i++) {
            buf.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }
        this.boundary = buf.toString();
    }

    public void writeFirstBoundaryIfNeeds() {
        if (!this.isSetFirst) {
            try {
                this.out.write(("--" + this.boundary + "\r\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.isSetFirst = true;
    }

    public void writeLastBoundaryIfNeeds() {
        if (!this.isSetLast) {
            try {
                this.out.write(("\r\n--" + this.boundary + "--\r\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.isSetLast = true;
        }
    }

    public void addPart(String key, String value) {
        writeFirstBoundaryIfNeeds();
        try {
            this.out.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
            this.out.write(value.getBytes());
            this.out.write(("\r\n--" + this.boundary + "\r\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPart(String key, String fileName, InputStream fin, boolean isLast) {
        addPart(key, fileName, fin, "application/octet-stream", isLast);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addPart(java.lang.String r7, java.lang.String r8, java.io.InputStream r9, java.lang.String r10, boolean r11) {
        /*
        r6 = this;
        r6.writeFirstBoundaryIfNeeds();
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0092 }
        r4 = "Content-Type: ";
        r3.<init>(r4);	 Catch:{ IOException -> 0x0092 }
        r3 = r3.append(r10);	 Catch:{ IOException -> 0x0092 }
        r4 = "\r\n";
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x0092 }
        r10 = r3.toString();	 Catch:{ IOException -> 0x0092 }
        r3 = r6.out;	 Catch:{ IOException -> 0x0092 }
        r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0092 }
        r5 = "Content-Disposition: form-data; name=\"";
        r4.<init>(r5);	 Catch:{ IOException -> 0x0092 }
        r4 = r4.append(r7);	 Catch:{ IOException -> 0x0092 }
        r5 = "\"; filename=\"";
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x0092 }
        r4 = r4.append(r8);	 Catch:{ IOException -> 0x0092 }
        r5 = "\"\r\n";
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x0092 }
        r4 = r4.toString();	 Catch:{ IOException -> 0x0092 }
        r4 = r4.getBytes();	 Catch:{ IOException -> 0x0092 }
        r3.write(r4);	 Catch:{ IOException -> 0x0092 }
        r3 = r6.out;	 Catch:{ IOException -> 0x0092 }
        r4 = r10.getBytes();	 Catch:{ IOException -> 0x0092 }
        r3.write(r4);	 Catch:{ IOException -> 0x0092 }
        r3 = r6.out;	 Catch:{ IOException -> 0x0092 }
        r4 = "Content-Transfer-Encoding: binary\r\n\r\n";
        r4 = r4.getBytes();	 Catch:{ IOException -> 0x0092 }
        r3.write(r4);	 Catch:{ IOException -> 0x0092 }
        r3 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r2 = new byte[r3];	 Catch:{ IOException -> 0x0092 }
        r1 = 0;
    L_0x0059:
        r1 = r9.read(r2);	 Catch:{ IOException -> 0x0092 }
        r3 = -1;
        if (r1 != r3) goto L_0x008b;
    L_0x0060:
        if (r11 != 0) goto L_0x0082;
    L_0x0062:
        r3 = r6.out;	 Catch:{ IOException -> 0x0092 }
        r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0092 }
        r5 = "\r\n--";
        r4.<init>(r5);	 Catch:{ IOException -> 0x0092 }
        r5 = r6.boundary;	 Catch:{ IOException -> 0x0092 }
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x0092 }
        r5 = "\r\n";
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x0092 }
        r4 = r4.toString();	 Catch:{ IOException -> 0x0092 }
        r4 = r4.getBytes();	 Catch:{ IOException -> 0x0092 }
        r3.write(r4);	 Catch:{ IOException -> 0x0092 }
    L_0x0082:
        r3 = r6.out;	 Catch:{ IOException -> 0x0092 }
        r3.flush();	 Catch:{ IOException -> 0x0092 }
        r9.close();	 Catch:{ IOException -> 0x00a9 }
    L_0x008a:
        return;
    L_0x008b:
        r3 = r6.out;	 Catch:{ IOException -> 0x0092 }
        r4 = 0;
        r3.write(r2, r4, r1);	 Catch:{ IOException -> 0x0092 }
        goto L_0x0059;
    L_0x0092:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x009f }
        r9.close();	 Catch:{ IOException -> 0x009a }
        goto L_0x008a;
    L_0x009a:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x008a;
    L_0x009f:
        r3 = move-exception;
        r9.close();	 Catch:{ IOException -> 0x00a4 }
    L_0x00a3:
        throw r3;
    L_0x00a4:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x00a3;
    L_0x00a9:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x008a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.lemallsdk.api.http.SimpleMultipartEntity.addPart(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String, boolean):void");
    }

    public void addPart(String key, File value, boolean isLast) {
        try {
            addPart(key, value.getName(), new FileInputStream(value), isLast);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public long getContentLength() {
        writeLastBoundaryIfNeeds();
        return (long) this.out.toByteArray().length;
    }

    public Header getContentType() {
        return new BasicHeader(HttpRequest.HEADER_CONTENT_TYPE, "multipart/form-data; boundary=" + this.boundary);
    }

    public boolean isChunked() {
        return false;
    }

    public boolean isRepeatable() {
        return false;
    }

    public boolean isStreaming() {
        return false;
    }

    public void writeTo(OutputStream outstream) throws IOException {
        outstream.write(this.out.toByteArray());
    }

    public Header getContentEncoding() {
        return null;
    }

    public void consumeContent() throws IOException, UnsupportedOperationException {
        if (isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() throws IOException, UnsupportedOperationException {
        return new ByteArrayInputStream(this.out.toByteArray());
    }
}
