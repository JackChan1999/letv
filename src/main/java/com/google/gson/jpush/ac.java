package com.google.gson.jpush;

import com.google.gson.jpush.internal.v;
import java.math.BigInteger;

public final class ac extends w {
    private static final Class<?>[] a = new Class[]{Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    private Object b;

    public ac(Boolean bool) {
        a((Object) bool);
    }

    public ac(Number number) {
        a((Object) number);
    }

    public ac(String str) {
        a((Object) str);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.lang.Object r8) {
        /*
        r7 = this;
        r1 = 1;
        r0 = 0;
        r2 = r8 instanceof java.lang.Character;
        if (r2 == 0) goto L_0x0013;
    L_0x0006:
        r8 = (java.lang.Character) r8;
        r0 = r8.charValue();
        r0 = java.lang.String.valueOf(r0);
        r7.b = r0;
    L_0x0012:
        return;
    L_0x0013:
        r2 = r8 instanceof java.lang.Number;
        if (r2 != 0) goto L_0x001e;
    L_0x0017:
        r2 = r8 instanceof java.lang.String;
        if (r2 == 0) goto L_0x0025;
    L_0x001b:
        r2 = r1;
    L_0x001c:
        if (r2 == 0) goto L_0x001f;
    L_0x001e:
        r0 = r1;
    L_0x001f:
        com.google.gson.jpush.internal.a.a(r0);
        r7.b = r8;
        goto L_0x0012;
    L_0x0025:
        r3 = r8.getClass();
        r4 = a;
        r5 = r4.length;
        r2 = r0;
    L_0x002d:
        if (r2 >= r5) goto L_0x003c;
    L_0x002f:
        r6 = r4[r2];
        r6 = r6.isAssignableFrom(r3);
        if (r6 == 0) goto L_0x0039;
    L_0x0037:
        r2 = r1;
        goto L_0x001c;
    L_0x0039:
        r2 = r2 + 1;
        goto L_0x002d;
    L_0x003c:
        r2 = r0;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.ac.a(java.lang.Object):void");
    }

    private static boolean a(ac acVar) {
        if (!(acVar.b instanceof Number)) {
            return false;
        }
        Number number = (Number) acVar.b;
        return (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
    }

    public final boolean a() {
        return this.b instanceof Boolean;
    }

    public final Number b() {
        return this.b instanceof String ? new v((String) this.b) : (Number) this.b;
    }

    public final String c() {
        return this.b instanceof Number ? b().toString() : this.b instanceof Boolean ? ((Boolean) this.b).toString() : (String) this.b;
    }

    public final double d() {
        return this.b instanceof Number ? b().doubleValue() : Double.parseDouble(c());
    }

    public final long e() {
        return this.b instanceof Number ? b().longValue() : Long.parseLong(c());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ac acVar = (ac) obj;
        if (this.b == null) {
            return acVar.b == null;
        } else {
            if (a(this) && a(acVar)) {
                return b().longValue() == acVar.b().longValue();
            } else {
                if (!(this.b instanceof Number) || !(acVar.b instanceof Number)) {
                    return this.b.equals(acVar.b);
                }
                double doubleValue = b().doubleValue();
                double doubleValue2 = acVar.b().doubleValue();
                return doubleValue != doubleValue2 ? Double.isNaN(doubleValue) && Double.isNaN(doubleValue2) : true;
            }
        }
    }

    public final int f() {
        return this.b instanceof Number ? b().intValue() : Integer.parseInt(c());
    }

    public final boolean g() {
        return this.b instanceof Boolean ? ((Boolean) this.b).booleanValue() : Boolean.parseBoolean(c());
    }

    public final int hashCode() {
        if (this.b == null) {
            return 31;
        }
        long longValue;
        if (a(this)) {
            longValue = b().longValue();
            return (int) (longValue ^ (longValue >>> 32));
        } else if (!(this.b instanceof Number)) {
            return this.b.hashCode();
        } else {
            longValue = Double.doubleToLongBits(b().doubleValue());
            return (int) (longValue ^ (longValue >>> 32));
        }
    }

    public final boolean k() {
        return this.b instanceof Number;
    }

    public final boolean l() {
        return this.b instanceof String;
    }
}
