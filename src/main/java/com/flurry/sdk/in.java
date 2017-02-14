package com.flurry.sdk;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class in {
    private hw<hg> a = new hw<hg>(this) {
        final /* synthetic */ in a;

        {
            this.a = r1;
        }

        public void a(hg hgVar) {
            ib.a(4, this.a.c, "onNetworkStateChanged : isNetworkEnable = " + hgVar.a);
            if (hgVar.a) {
                this.a.e();
            }
        }
    };
    protected final String c;
    Set<String> d = new HashSet();
    ip e;
    protected String f = "defaultDataKey_";

    public interface a {
        void a();
    }

    protected abstract void a(byte[] bArr, String str, String str2);

    public in(String str, String str2) {
        this.c = str2;
        hx.a().a("com.flurry.android.sdk.NetworkStateEvent", this.a);
        a(str);
    }

    protected void a(jp jpVar) {
        hn.a().b(jpVar);
    }

    protected void a(final String str) {
        a(new jp(this) {
            final /* synthetic */ in b;

            public void a() {
                this.b.e = new ip(str);
            }
        });
    }

    public void b(byte[] bArr, String str, String str2) {
        a(bArr, str, str2, null);
    }

    public void a(byte[] bArr, String str, String str2, a aVar) {
        if (bArr == null || bArr.length == 0) {
            ib.a(6, this.c, "Report that has to be sent is EMPTY or NULL");
            return;
        }
        c(bArr, str, str2);
        a(aVar);
    }

    public int d() {
        return this.d.size();
    }

    protected void c(final byte[] bArr, final String str, final String str2) {
        a(new jp(this) {
            final /* synthetic */ in d;

            public void a() {
                this.d.d(bArr, str, str2);
            }
        });
    }

    protected void e() {
        a(null);
    }

    protected void a(final a aVar) {
        a(new jp(this) {
            final /* synthetic */ in b;

            public void a() {
                this.b.g();
                if (aVar != null) {
                    aVar.a();
                }
            }
        });
    }

    protected boolean f() {
        return d() <= 5;
    }

    public String a(String str, String str2) {
        return this.f + str + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + str2;
    }

    protected void d(byte[] bArr, String str, String str2) {
        String a = a(str, str2);
        io ioVar = new io(bArr);
        String a2 = ioVar.a();
        new hu(hn.a().c().getFileStreamPath(io.a(a2)), ".yflurrydatasenderblock.", 1, new iy<io>(this) {
            final /* synthetic */ in a;

            {
                this.a = r1;
            }

            public iv<io> a(int i) {
                return new com.flurry.sdk.io.a();
            }
        }).a(ioVar);
        ib.a(5, this.c, "Saving Block File " + a2 + " at " + hn.a().c().getFileStreamPath(io.a(a2)));
        this.e.a(ioVar, a);
    }

    protected void g() {
        if (hh.a().c()) {
            List<String> a = this.e.a();
            if (a == null || a.isEmpty()) {
                ib.a(4, this.c, "No more reports to send.");
                return;
            }
            for (String str : a) {
                if (f()) {
                    List c = this.e.c(str);
                    ib.a(4, this.c, "Number of not sent blocks = " + c.size());
                    for (int i = 0; i < c.size(); i++) {
                        String str2 = (String) c.get(i);
                        if (!this.d.contains(str2)) {
                            if (!f()) {
                                break;
                            }
                            io ioVar = (io) new hu(hn.a().c().getFileStreamPath(io.a(str2)), ".yflurrydatasenderblock.", 1, new iy<io>(this) {
                                final /* synthetic */ in a;

                                {
                                    this.a = r1;
                                }

                                public iv<io> a(int i) {
                                    return new com.flurry.sdk.io.a();
                                }
                            }).a();
                            if (ioVar == null) {
                                ib.a(6, this.c, "Internal ERROR! Cannot read!");
                                this.e.a(str2, str);
                            } else {
                                byte[] b = ioVar.b();
                                if (b == null || b.length == 0) {
                                    ib.a(6, this.c, "Internal ERROR! Report is empty!");
                                    this.e.a(str2, str);
                                } else {
                                    ib.a(5, this.c, "Reading block info " + str2);
                                    this.d.add(str2);
                                    a(b, str2, str);
                                }
                            }
                        }
                    }
                } else {
                    return;
                }
            }
            return;
        }
        ib.a(5, this.c, "Reports were not sent! No Internet connection!");
    }

    protected void a(final String str, final String str2, int i) {
        a(new jp(this) {
            final /* synthetic */ in c;

            public void a() {
                if (!this.c.e.a(str, str2)) {
                    ib.a(6, this.c.c, "Internal error. Block wasn't deleted with id = " + str);
                }
                if (!this.c.d.remove(str)) {
                    ib.a(6, this.c.c, "Internal error. Block with id = " + str + " was not in progress state");
                }
            }
        });
    }

    protected void b(final String str, String str2) {
        a(new jp(this) {
            final /* synthetic */ in b;

            public void a() {
                if (!this.b.d.remove(str)) {
                    ib.a(6, this.b.c, "Internal error. Block with id = " + str + " was not in progress state");
                }
            }
        });
    }

    protected void c(String str, String str2) {
        if (!this.e.a(str, str2)) {
            ib.a(6, this.c, "Internal error. Block wasn't deleted with id = " + str);
        }
        if (!this.d.remove(str)) {
            ib.a(6, this.c, "Internal error. Block with id = " + str + " was not in progress state");
        }
    }
}
