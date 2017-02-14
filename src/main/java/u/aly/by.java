package u.aly;

/* compiled from: TApplicationException */
public class by extends cf {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 4;
    public static final int f = 5;
    public static final int g = 6;
    public static final int h = 7;
    private static final dd j = new dd("TApplicationException");
    private static final ct k = new ct("message", (byte) 11, (short) 1);
    private static final ct l = new ct("type", (byte) 8, (short) 2);
    private static final long m = 1;
    protected int i = 0;

    public by(int i) {
        this.i = i;
    }

    public by(int i, String str) {
        super(str);
        this.i = i;
    }

    public by(String str) {
        super(str);
    }

    public int a() {
        return this.i;
    }

    public static by a(cy cyVar) throws cf {
        cyVar.j();
        String str = null;
        int i = 0;
        while (true) {
            ct l = cyVar.l();
            if (l.b == (byte) 0) {
                cyVar.k();
                return new by(i, str);
            }
            switch (l.c) {
                case (short) 1:
                    if (l.b != (byte) 11) {
                        db.a(cyVar, l.b);
                        break;
                    }
                    str = cyVar.z();
                    break;
                case (short) 2:
                    if (l.b != (byte) 8) {
                        db.a(cyVar, l.b);
                        break;
                    }
                    i = cyVar.w();
                    break;
                default:
                    db.a(cyVar, l.b);
                    break;
            }
            cyVar.m();
        }
    }

    public void b(cy cyVar) throws cf {
        cyVar.a(j);
        if (getMessage() != null) {
            cyVar.a(k);
            cyVar.a(getMessage());
            cyVar.c();
        }
        cyVar.a(l);
        cyVar.a(this.i);
        cyVar.c();
        cyVar.d();
        cyVar.b();
    }
}
