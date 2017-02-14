package cn.com.iresearch.vvtracker.a.a;

public abstract class b {
    protected final int a = 0;
    private final int b;

    abstract void a(byte[] bArr, int i, int i2, c cVar);

    protected abstract boolean a(byte b);

    protected b(int i) {
        this.b = i;
    }

    protected static byte[] a(int i, c cVar) {
        if (cVar.b != null && cVar.b.length >= cVar.c + 4) {
            return cVar.b;
        }
        if (cVar.b == null) {
            cVar.b = new byte[8192];
            cVar.c = 0;
            cVar.d = 0;
        } else {
            Object obj = new byte[(cVar.b.length << 1)];
            System.arraycopy(cVar.b, 0, obj, 0, cVar.b.length);
            cVar.b = obj;
        }
        return cVar.b;
    }

    protected final boolean b(byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        for (byte b : bArr) {
            if ((byte) 61 == b || a(b)) {
                return true;
            }
        }
        return false;
    }
}
