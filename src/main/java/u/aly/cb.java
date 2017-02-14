package u.aly;

import java.io.ByteArrayOutputStream;

/* compiled from: TByteArrayOutputStream */
public class cb extends ByteArrayOutputStream {
    public cb(int i) {
        super(i);
    }

    public byte[] a() {
        return this.buf;
    }

    public int b() {
        return this.count;
    }
}
