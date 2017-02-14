package com.flurry.sdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class im<ReportInfo extends il> {
    private static final String a = im.class.getSimpleName();
    private final int b = Integer.MAX_VALUE;
    private final hu<List<ReportInfo>> c;
    private final List<ReportInfo> d = new ArrayList();
    private boolean e;
    private int f;
    private long g;
    private final Runnable h = new jp(this) {
        final /* synthetic */ im a;

        {
            this.a = r1;
        }

        public void a() {
            this.a.b();
        }
    };
    private final hw<hg> i = new hw<hg>(this) {
        final /* synthetic */ im a;

        {
            this.a = r1;
        }

        public void a(hg hgVar) {
            if (hgVar.a) {
                this.a.b();
            }
        }
    };

    protected abstract hu<List<ReportInfo>> a();

    protected abstract void a(ReportInfo reportInfo);

    public im() {
        hx.a().a("com.flurry.android.sdk.NetworkStateEvent", this.i);
        this.c = a();
        this.g = 10000;
        this.f = -1;
        hn.a().b(new jp(this) {
            final /* synthetic */ im a;

            {
                this.a = r1;
            }

            public void a() {
                this.a.a(this.a.d);
                this.a.b();
            }
        });
    }

    public void c() {
        hn.a().c(this.h);
        i();
    }

    public void d() {
        this.e = true;
    }

    public void e() {
        this.e = false;
        hn.a().b(new jp(this) {
            final /* synthetic */ im a;

            {
                this.a = r1;
            }

            public void a() {
                this.a.b();
            }
        });
    }

    public synchronized void b(ReportInfo reportInfo) {
        if (reportInfo != null) {
            this.d.add(reportInfo);
            hn.a().b(new jp(this) {
                final /* synthetic */ im a;

                {
                    this.a = r1;
                }

                public void a() {
                    this.a.b();
                }
            });
        }
    }

    protected synchronized void c(ReportInfo reportInfo) {
        reportInfo.a(true);
        hn.a().b(new jp(this) {
            final /* synthetic */ im a;

            {
                this.a = r1;
            }

            public void a() {
                this.a.f();
            }
        });
    }

    protected synchronized void d(ReportInfo reportInfo) {
        reportInfo.i();
        hn.a().b(new jp(this) {
            final /* synthetic */ im a;

            {
                this.a = r1;
            }

            public void a() {
                this.a.f();
            }
        });
    }

    private synchronized void b() {
        if (!this.e) {
            if (this.f >= 0) {
                ib.a(3, a, "Transmit is in progress");
            } else {
                h();
                if (this.d.isEmpty()) {
                    this.g = 10000;
                    this.f = -1;
                } else {
                    this.f = 0;
                    hn.a().b(new jp(this) {
                        final /* synthetic */ im a;

                        {
                            this.a = r1;
                        }

                        public void a() {
                            this.a.f();
                        }
                    });
                }
            }
        }
    }

    private synchronized void f() {
        il ilVar;
        jn.b();
        if (hh.a().c()) {
            while (this.f < this.d.size()) {
                List list = this.d;
                int i = this.f;
                this.f = i + 1;
                ilVar = (il) list.get(i);
                if (!ilVar.e()) {
                    break;
                }
            }
            ilVar = null;
        } else {
            ib.a(3, a, "Network is not available, aborting transmission");
            ilVar = null;
        }
        if (ilVar == null) {
            g();
        } else {
            a(ilVar);
        }
    }

    private synchronized void g() {
        h();
        b(this.d);
        if (this.e) {
            ib.a(3, a, "Reporter paused");
            this.g = 10000;
        } else if (this.d.isEmpty()) {
            ib.a(3, a, "All reports sent successfully");
            this.g = 10000;
        } else {
            this.g <<= 1;
            ib.a(3, a, "One or more reports failed to send, backing off: " + this.g + "ms");
            hn.a().b(this.h, this.g);
        }
        this.f = -1;
    }

    private synchronized void a(List<ReportInfo> list) {
        jn.b();
        List list2 = (List) this.c.a();
        if (list2 != null) {
            list.addAll(list2);
        }
    }

    private synchronized void b(List<ReportInfo> list) {
        jn.b();
        this.c.a(new ArrayList(list));
    }

    private synchronized void h() {
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            il ilVar = (il) it.next();
            if (ilVar.e() || ilVar.f() >= Integer.MAX_VALUE || System.currentTimeMillis() > ilVar.d()) {
                it.remove();
            }
        }
    }

    private void i() {
        hx.a().b("com.flurry.android.sdk.NetworkStateEvent", this.i);
    }
}
