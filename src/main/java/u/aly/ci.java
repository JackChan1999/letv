package u.aly;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import u.aly.cs.a;

/* compiled from: TSerializer */
public class ci {
    private final ByteArrayOutputStream a;
    private final dk b;
    private cy c;

    public ci() {
        this(new a());
    }

    public ci(da daVar) {
        this.a = new ByteArrayOutputStream();
        this.b = new dk(this.a);
        this.c = daVar.a(this.b);
    }

    public byte[] a(bz bzVar) throws cf {
        this.a.reset();
        bzVar.b(this.c);
        return this.a.toByteArray();
    }

    public String a(bz bzVar, String str) throws cf {
        try {
            return new String(a(bzVar), str);
        } catch (UnsupportedEncodingException e) {
            throw new cf("JVM DOES NOT SUPPORT ENCODING: " + str);
        }
    }

    public String b(bz bzVar) throws cf {
        return new String(a(bzVar));
    }
}
