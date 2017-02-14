package com.google.protobuf.jpush;

import java.util.Collection;

public abstract class b<BuilderType extends b> implements l {
    private static final String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "D\u0005|q&x\u0007=s=y\r=tot\u0019ipow\u0012ot66\u0014ug*a@|{o_/Xm,s\u0010i| x@5f'y\u0015qqox\u0005kp=6\b|e?s\u000e4;";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0027;
    L_0x000b:
        r3 = r0;
        r4 = r2;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x0010:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0035;
            case 1: goto L_0x0038;
            case 2: goto L_0x003b;
            case 3: goto L_0x003e;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 79;
    L_0x0019:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0025;
    L_0x0021:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0010;
    L_0x0025:
        r1 = r0;
        r0 = r3;
    L_0x0027:
        if (r1 > r2) goto L_0x000b;
    L_0x0029:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        return;
    L_0x0035:
        r5 = 22;
        goto L_0x0019;
    L_0x0038:
        r5 = 96;
        goto L_0x0019;
    L_0x003b:
        r5 = 29;
        goto L_0x0019;
    L_0x003e:
        r5 = 21;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.jpush.b.<clinit>():void");
    }

    protected static <T> void a(Iterable<T> iterable, Collection<? super T> collection) {
        for (T t : iterable) {
            if (t == null) {
                throw new NullPointerException();
            }
        }
        if (iterable instanceof Collection) {
            collection.addAll((Collection) iterable);
            return;
        }
        for (T t2 : iterable) {
            collection.add(t2);
        }
    }

    public abstract BuilderType a(d dVar, g gVar);

    public final BuilderType a(byte[] bArr, int i, int i2) {
        try {
            d a = d.a(bArr, 0, i2);
            a(a, g.a());
            a.a(0);
            return this;
        } catch (j e) {
            throw e;
        } catch (Throwable e2) {
            throw new RuntimeException(z, e2);
        }
    }

    public /* synthetic */ l b(d dVar, g gVar) {
        return a(dVar, gVar);
    }

    public /* synthetic */ Object clone() {
        return d();
    }

    public abstract BuilderType d();
}
