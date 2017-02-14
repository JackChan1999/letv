package u.aly;

import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

/* compiled from: TMessage */
public final class cw {
    public final String a;
    public final byte b;
    public final int c;

    public cw() {
        this("", (byte) 0, 0);
    }

    public cw(String str, byte b, int i) {
        this.a = str;
        this.b = b;
        this.c = i;
    }

    public String toString() {
        return "<TMessage name:'" + this.a + "' type: " + this.b + " seqid:" + this.c + SearchCriteria.GT;
    }

    public boolean equals(Object obj) {
        if (obj instanceof cw) {
            return a((cw) obj);
        }
        return false;
    }

    public boolean a(cw cwVar) {
        return this.a.equals(cwVar.a) && this.b == cwVar.b && this.c == cwVar.c;
    }
}
