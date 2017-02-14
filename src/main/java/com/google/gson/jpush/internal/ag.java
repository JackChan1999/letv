package com.google.gson.jpush.internal;

import com.google.gson.jpush.af;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.internal.a.z;
import com.google.gson.jpush.w;
import com.google.gson.jpush.x;
import com.google.gson.jpush.y;
import java.io.Writer;

public final class ag {
    public static w a(a aVar) {
        Object obj = 1;
        try {
            aVar.f();
            obj = null;
            return (w) z.P.a(aVar);
        } catch (Throwable e) {
            if (obj != null) {
                return y.a;
            }
            throw new af(e);
        } catch (Throwable e2) {
            throw new af(e2);
        } catch (Throwable e22) {
            throw new x(e22);
        } catch (Throwable e222) {
            throw new af(e222);
        }
    }

    public static Writer a(Appendable appendable) {
        return appendable instanceof Writer ? (Writer) appendable : new ah(appendable, (byte) 0);
    }

    public static void a(w wVar, d dVar) {
        z.P.a(dVar, wVar);
    }
}
