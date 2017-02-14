package com.nostra13.universalimageloader.core.assist;

import java.io.IOException;
import java.io.InputStream;

public class ContentLengthInputStream extends InputStream {
    private final int length;
    private final InputStream stream;

    public ContentLengthInputStream(InputStream stream, int length) {
        this.stream = stream;
        this.length = length;
    }

    public int available() {
        return this.length;
    }

    public void close() throws IOException {
        this.stream.close();
    }

    public void mark(int readLimit) {
        this.stream.mark(readLimit);
    }

    public int read() throws IOException {
        return this.stream.read();
    }

    public int read(byte[] buffer) throws IOException {
        return this.stream.read(buffer);
    }

    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        return this.stream.read(buffer, byteOffset, byteCount);
    }

    public void reset() throws IOException {
        this.stream.reset();
    }

    public long skip(long byteCount) throws IOException {
        return this.stream.skip(byteCount);
    }

    public boolean markSupported() {
        return this.stream.markSupported();
    }
}
