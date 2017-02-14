package com.flurry.sdk;

public class jj {
    private static final String a = jj.class.getSimpleName();
    private long b = 1000;
    private boolean c = true;
    private boolean d = false;
    private jp e = new jp(this) {
        final /* synthetic */ jj a;

        {
            this.a = r1;
        }

        public void a() {
            new jh().b();
            if (this.a.c && this.a.d) {
                hn.a().b(this.a.e, this.a.b);
            }
        }
    };

    public void a(long j) {
        this.b = j;
    }

    public void a(boolean z) {
        this.c = z;
    }

    public synchronized void a() {
        if (!this.d) {
            hn.a().b(this.e, this.b);
            this.d = true;
        }
    }

    public synchronized void b() {
        if (this.d) {
            hn.a().c(this.e);
            this.d = false;
        }
    }
}
