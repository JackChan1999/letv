package com.nostra13.universalimageloader.core.assist;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FlushedInputStream extends FilterInputStream {
    public FlushedInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public long skip(long n) throws IOException {
        long totalBytesSkipped = 0;
        while (totalBytesSkipped < n) {
            long bytesSkipped = this.in.skip(n - totalBytesSkipped);
            if (bytesSkipped == 0) {
                if (read() < 0) {
                    break;
                }
                bytesSkipped = 1;
            }
            totalBytesSkipped += bytesSkipped;
        }
        return totalBytesSkipped;
    }
}
