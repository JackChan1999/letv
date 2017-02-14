package u.aly;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import u.aly.cs.a;

/* compiled from: TDeserializer */
public class cc {
    private final cy a;
    private final dl b;

    public cc() {
        this(new a());
    }

    public cc(da daVar) {
        this.b = new dl();
        this.a = daVar.a(this.b);
    }

    public void a(bz bzVar, byte[] bArr) throws cf {
        try {
            this.b.a(bArr);
            bzVar.a(this.a);
        } finally {
            this.b.e();
            this.a.B();
        }
    }

    public void a(bz bzVar, String str, String str2) throws cf {
        try {
            a(bzVar, str.getBytes(str2));
            this.a.B();
        } catch (UnsupportedEncodingException e) {
            throw new cf("JVM DOES NOT SUPPORT ENCODING: " + str2);
        } catch (Throwable th) {
            this.a.B();
        }
    }

    public void a(bz bzVar, byte[] bArr, cg cgVar, cg... cgVarArr) throws cf {
        try {
            if (j(bArr, cgVar, cgVarArr) != null) {
                bzVar.a(this.a);
            }
            this.b.e();
            this.a.B();
        } catch (Throwable e) {
            throw new cf(e);
        } catch (Throwable th) {
            this.b.e();
            this.a.B();
        }
    }

    public Boolean a(byte[] bArr, cg cgVar, cg... cgVarArr) throws cf {
        return (Boolean) a((byte) 2, bArr, cgVar, cgVarArr);
    }

    public Byte b(byte[] bArr, cg cgVar, cg... cgVarArr) throws cf {
        return (Byte) a((byte) 3, bArr, cgVar, cgVarArr);
    }

    public Double c(byte[] bArr, cg cgVar, cg... cgVarArr) throws cf {
        return (Double) a((byte) 4, bArr, cgVar, cgVarArr);
    }

    public Short d(byte[] bArr, cg cgVar, cg... cgVarArr) throws cf {
        return (Short) a((byte) 6, bArr, cgVar, cgVarArr);
    }

    public Integer e(byte[] bArr, cg cgVar, cg... cgVarArr) throws cf {
        return (Integer) a((byte) 8, bArr, cgVar, cgVarArr);
    }

    public Long f(byte[] bArr, cg cgVar, cg... cgVarArr) throws cf {
        return (Long) a((byte) 10, bArr, cgVar, cgVarArr);
    }

    public String g(byte[] bArr, cg cgVar, cg... cgVarArr) throws cf {
        return (String) a((byte) 11, bArr, cgVar, cgVarArr);
    }

    public ByteBuffer h(byte[] bArr, cg cgVar, cg... cgVarArr) throws cf {
        return (ByteBuffer) a((byte) 100, bArr, cgVar, cgVarArr);
    }

    public Short i(byte[] bArr, cg cgVar, cg... cgVarArr) throws cf {
        try {
            if (j(bArr, cgVar, cgVarArr) != null) {
                this.a.j();
                Short valueOf = Short.valueOf(this.a.l().c);
                this.b.e();
                this.a.B();
                return valueOf;
            }
            this.b.e();
            this.a.B();
            return null;
        } catch (Throwable e) {
            throw new cf(e);
        } catch (Throwable th) {
            this.b.e();
            this.a.B();
        }
    }

    private Object a(byte b, byte[] bArr, cg cgVar, cg... cgVarArr) throws cf {
        try {
            ct j = j(bArr, cgVar, cgVarArr);
            if (j != null) {
                Object valueOf;
                switch (b) {
                    case (byte) 2:
                        if (j.b == (byte) 2) {
                            valueOf = Boolean.valueOf(this.a.t());
                            this.b.e();
                            this.a.B();
                            return valueOf;
                        }
                        break;
                    case (byte) 3:
                        if (j.b == (byte) 3) {
                            valueOf = Byte.valueOf(this.a.u());
                            this.b.e();
                            this.a.B();
                            return valueOf;
                        }
                        break;
                    case (byte) 4:
                        if (j.b == (byte) 4) {
                            valueOf = Double.valueOf(this.a.y());
                            this.b.e();
                            this.a.B();
                            return valueOf;
                        }
                        break;
                    case (byte) 6:
                        if (j.b == (byte) 6) {
                            valueOf = Short.valueOf(this.a.v());
                            this.b.e();
                            this.a.B();
                            return valueOf;
                        }
                        break;
                    case (byte) 8:
                        if (j.b == (byte) 8) {
                            valueOf = Integer.valueOf(this.a.w());
                            this.b.e();
                            this.a.B();
                            return valueOf;
                        }
                        break;
                    case (byte) 10:
                        if (j.b == (byte) 10) {
                            valueOf = Long.valueOf(this.a.x());
                            this.b.e();
                            this.a.B();
                            return valueOf;
                        }
                        break;
                    case (byte) 11:
                        if (j.b == (byte) 11) {
                            valueOf = this.a.z();
                            this.b.e();
                            this.a.B();
                            return valueOf;
                        }
                        break;
                    case (byte) 100:
                        if (j.b == (byte) 11) {
                            valueOf = this.a.A();
                            this.b.e();
                            this.a.B();
                            return valueOf;
                        }
                        break;
                }
            }
            this.b.e();
            this.a.B();
            return null;
        } catch (Throwable e) {
            throw new cf(e);
        } catch (Throwable th) {
            this.b.e();
            this.a.B();
        }
    }

    private ct j(byte[] bArr, cg cgVar, cg... cgVarArr) throws cf {
        int i = 0;
        this.b.a(bArr);
        cg[] cgVarArr2 = new cg[(cgVarArr.length + 1)];
        cgVarArr2[0] = cgVar;
        for (int i2 = 0; i2 < cgVarArr.length; i2++) {
            cgVarArr2[i2 + 1] = cgVarArr[i2];
        }
        this.a.j();
        ct ctVar = null;
        while (i < cgVarArr2.length) {
            ctVar = this.a.l();
            if (ctVar.b == (byte) 0 || ctVar.c > cgVarArr2[i].a()) {
                return null;
            }
            if (ctVar.c != cgVarArr2[i].a()) {
                db.a(this.a, ctVar.b);
                this.a.m();
            } else {
                i++;
                if (i < cgVarArr2.length) {
                    this.a.j();
                }
            }
        }
        return ctVar;
    }

    public void a(bz bzVar, String str) throws cf {
        a(bzVar, str.getBytes());
    }
}
