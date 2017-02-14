package u.aly;

import u.aly.cs.a;

/* compiled from: TProtocolUtil */
public class db {
    private static int a = Integer.MAX_VALUE;

    public static void a(int i) {
        a = i;
    }

    public static void a(cy cyVar, byte b) throws cf {
        a(cyVar, b, a);
    }

    public static void a(cy cyVar, byte b, int i) throws cf {
        int i2 = 0;
        if (i <= 0) {
            throw new cf("Maximum skip depth exceeded");
        }
        switch (b) {
            case (byte) 2:
                cyVar.t();
                return;
            case (byte) 3:
                cyVar.u();
                return;
            case (byte) 4:
                cyVar.y();
                return;
            case (byte) 6:
                cyVar.v();
                return;
            case (byte) 8:
                cyVar.w();
                return;
            case (byte) 10:
                cyVar.x();
                return;
            case (byte) 11:
                cyVar.A();
                return;
            case (byte) 12:
                cyVar.j();
                while (true) {
                    ct l = cyVar.l();
                    if (l.b == (byte) 0) {
                        cyVar.k();
                        return;
                    } else {
                        a(cyVar, l.b, i - 1);
                        cyVar.m();
                    }
                }
            case (byte) 13:
                cv n = cyVar.n();
                while (i2 < n.c) {
                    a(cyVar, n.a, i - 1);
                    a(cyVar, n.b, i - 1);
                    i2++;
                }
                cyVar.o();
                return;
            case (byte) 14:
                dc r = cyVar.r();
                while (i2 < r.b) {
                    a(cyVar, r.a, i - 1);
                    i2++;
                }
                cyVar.s();
                return;
            case (byte) 15:
                cu p = cyVar.p();
                while (i2 < p.b) {
                    a(cyVar, p.a, i - 1);
                    i2++;
                }
                cyVar.q();
                return;
            default:
                return;
        }
    }

    public static da a(byte[] bArr, da daVar) {
        if (bArr[0] > df.n) {
            return new a();
        }
        if (bArr.length <= 1 || (bArr[1] & 128) == 0) {
            return daVar;
        }
        return new a();
    }
}
