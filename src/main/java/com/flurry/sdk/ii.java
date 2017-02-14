package com.flurry.sdk;

import com.flurry.sdk.ij.c;
import java.io.InputStream;
import java.io.OutputStream;

public class ii<RequestObjectType, ResponseObjectType> extends ij {
    private a<RequestObjectType, ResponseObjectType> a;
    private RequestObjectType b;
    private ResponseObjectType c;
    private iv<RequestObjectType> d;
    private iv<ResponseObjectType> e;

    public interface a<RequestObjectType, ResponseObjectType> {
        void a(ii<RequestObjectType, ResponseObjectType> iiVar, ResponseObjectType responseObjectType);
    }

    public void a(RequestObjectType requestObjectType) {
        this.b = requestObjectType;
    }

    public void a(iv<RequestObjectType> ivVar) {
        this.d = ivVar;
    }

    public void b(iv<ResponseObjectType> ivVar) {
        this.e = ivVar;
    }

    public void a(a<RequestObjectType, ResponseObjectType> aVar) {
        this.a = aVar;
    }

    public void a() {
        n();
        super.a();
    }

    private void n() {
        a(new c(this) {
            final /* synthetic */ ii a;

            {
                this.a = r1;
            }

            public void a(ij ijVar, OutputStream outputStream) throws Exception {
                if (this.a.b != null && this.a.d != null) {
                    this.a.d.a(outputStream, this.a.b);
                }
            }

            public void a(ij ijVar, InputStream inputStream) throws Exception {
                if (ijVar.e() && this.a.e != null) {
                    this.a.c = this.a.e.b(inputStream);
                }
            }

            public void a(ij ijVar) {
                this.a.o();
            }
        });
    }

    private void o() {
        if (this.a != null && !c()) {
            this.a.a(this, this.c);
        }
    }
}
