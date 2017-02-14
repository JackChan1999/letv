package com.flurry.sdk;

import java.util.Timer;
import java.util.TimerTask;

class jc {
    private Timer a;
    private a b;

    class a extends TimerTask {
        final /* synthetic */ jc a;

        a(jc jcVar) {
            this.a = jcVar;
        }

        public void run() {
            this.a.a();
            new jd().b();
        }
    }

    jc() {
    }

    public synchronized void a(long j) {
        if (b()) {
            a();
        }
        this.a = new Timer("FlurrySessionTimer");
        this.b = new a(this);
        this.a.schedule(this.b, j);
    }

    public synchronized void a() {
        if (this.a != null) {
            this.a.cancel();
            this.a = null;
        }
        this.b = null;
    }

    public boolean b() {
        if (this.a != null) {
            return true;
        }
        return false;
    }
}
