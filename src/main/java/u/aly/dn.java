package u.aly;

/* compiled from: TTransportException */
public class dn extends cf {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 4;
    private static final long g = 1;
    protected int f = 0;

    public dn(int i) {
        this.f = i;
    }

    public dn(int i, String str) {
        super(str);
        this.f = i;
    }

    public dn(String str) {
        super(str);
    }

    public dn(int i, Throwable th) {
        super(th);
        this.f = i;
    }

    public dn(Throwable th) {
        super(th);
    }

    public dn(String str, Throwable th) {
        super(str, th);
    }

    public dn(int i, String str, Throwable th) {
        super(str, th);
        this.f = i;
    }

    public int a() {
        return this.f;
    }
}
