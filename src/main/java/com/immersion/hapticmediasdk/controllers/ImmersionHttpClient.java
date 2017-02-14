package com.immersion.hapticmediasdk.controllers;

import com.immersion.hapticmediasdk.utils.Log;
import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class ImmersionHttpClient {
    public static int b04170417041704170417З = 79;
    public static int b0417ЗЗЗЗ0417 = 1;
    private static final String b043D043Dнн043Dн = "ImmersionHttpClient";
    public static int bЗ0417ЗЗЗ0417 = 2;
    public static int bЗЗЗЗЗ0417;
    private DefaultHttpClient bнн043Dн043Dн;

    private ImmersionHttpClient() {
        if (((b04170417041704170417З + b0417ЗЗЗЗ0417) * b04170417041704170417З) % bЗ0417ЗЗЗ0417 != bЗЗ0417ЗЗ0417()) {
            b04170417041704170417З = b04170417ЗЗЗ0417();
            bЗЗЗЗЗ0417 = 56;
        }
        this.bнн043Dн043Dн = null;
    }

    public static int b04170417ЗЗЗ0417() {
        return 51;
    }

    public static int b0417З0417ЗЗ0417() {
        return 1;
    }

    private HttpResponse b0449щ0449щщ0449(HttpUriRequest httpUriRequest, Map map, int i) throws Exception {
        URI uri = httpUriRequest.getURI();
        String trim = uri.getHost() != null ? uri.getHost().trim() : "";
        if (trim.length() > 0) {
            httpUriRequest.setHeader("Host", trim);
        }
        if (map != null) {
            for (Object next : map.entrySet()) {
                if (((b04170417041704170417З + b0417ЗЗЗЗ0417) * b04170417041704170417З) % bЗ0417ЗЗЗ0417 != bЗЗЗЗЗ0417) {
                    b04170417041704170417З = 81;
                    bЗЗЗЗЗ0417 = 31;
                }
                Entry entry = (Entry) next;
                httpUriRequest.setHeader((String) entry.getKey(), (String) entry.getValue());
            }
        }
        Header[] allHeaders = httpUriRequest.getAllHeaders();
        Log.d(b043D043Dнн043Dн, "request URI [" + httpUriRequest.getURI() + "]");
        for (Object obj : allHeaders) {
            Log.d(b043D043Dнн043Dн, "request header [" + obj.toString() + "]");
        }
        HttpConnectionParams.setSoTimeout(this.bнн043Dн043Dн.getParams(), i);
        HttpResponse execute = this.bнн043Dн043Dн.execute(httpUriRequest);
        if (execute != null) {
            return execute;
        }
        throw new RuntimeException("Null response returned.");
    }

    public static int bЗ04170417ЗЗ0417() {
        return 2;
    }

    public static int bЗЗ0417ЗЗ0417() {
        return 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void bщщ0449щщ0449() {
        /*
        r6 = this;
        r0 = r6.bнн043Dн043Dн;
        if (r0 != 0) goto L_0x0069;
    L_0x0004:
        r0 = new org.apache.http.params.BasicHttpParams;
        r0.<init>();
        r1 = 5;
        org.apache.http.conn.params.ConnManagerParams.setMaxTotalConnections(r0, r1);
        r1 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
    L_0x000f:
        r2 = 1;
        switch(r2) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0018;
            default: goto L_0x0013;
        };
    L_0x0013:
        r2 = 0;
        switch(r2) {
            case 0: goto L_0x0018;
            case 1: goto L_0x000f;
            default: goto L_0x0017;
        };
    L_0x0017:
        goto L_0x0013;
    L_0x0018:
        org.apache.http.params.HttpConnectionParams.setConnectionTimeout(r0, r1);
        r1 = new org.apache.http.conn.scheme.SchemeRegistry;
        r1.<init>();
        r2 = b04170417041704170417З;
        r3 = b0417З0417ЗЗ0417();
        r2 = r2 + r3;
        r3 = b04170417041704170417З;
        r2 = r2 * r3;
        r3 = bЗ0417ЗЗЗ0417;
        r2 = r2 % r3;
        r3 = bЗЗЗЗЗ0417;
        if (r2 == r3) goto L_0x003d;
    L_0x0031:
        r2 = b04170417ЗЗЗ0417();
        b04170417041704170417З = r2;
        r2 = b04170417ЗЗЗ0417();
        bЗЗЗЗЗ0417 = r2;
    L_0x003d:
        r2 = new org.apache.http.conn.scheme.Scheme;
        r3 = "http";
        r4 = org.apache.http.conn.scheme.PlainSocketFactory.getSocketFactory();
        r5 = 80;
        r2.<init>(r3, r4, r5);
        r1.register(r2);
        r2 = new org.apache.http.conn.scheme.Scheme;
        r3 = "https";
        r4 = org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory();
        r5 = 443; // 0x1bb float:6.21E-43 double:2.19E-321;
        r2.<init>(r3, r4, r5);
        r1.register(r2);
        r2 = new org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
        r2.<init>(r0, r1);
        r1 = new org.apache.http.impl.client.DefaultHttpClient;
        r1.<init>(r2, r0);
        r6.bнн043Dн043Dн = r1;
    L_0x0069:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.ImmersionHttpClient.bщщ0449щщ0449():void");
    }

    public static ImmersionHttpClient getHttpClient() {
        if (((b04170417041704170417З + b0417ЗЗЗЗ0417) * b04170417041704170417З) % bЗ0417ЗЗЗ0417 != bЗЗЗЗЗ0417) {
            b04170417041704170417З = 75;
            bЗЗЗЗЗ0417 = b04170417ЗЗЗ0417();
        }
        try {
            ImmersionHttpClient immersionHttpClient = new ImmersionHttpClient();
            try {
                immersionHttpClient.bщщ0449щщ0449();
                return immersionHttpClient;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public HttpResponse executeDelete(String str, Map map, int i) throws Exception {
        return b0449щ0449щщ0449(new HttpDelete(str), map, i);
    }

    public HttpResponse executeGet(String str, Map map, int i) throws Exception {
        HttpResponse b0449щ0449щщ0449 = b0449щ0449щщ0449(new HttpGet(str), map, i);
        if (((b04170417041704170417З + b0417ЗЗЗЗ0417) * b04170417041704170417З) % bЗ0417ЗЗЗ0417 != bЗЗЗЗЗ0417) {
            b04170417041704170417З = b04170417ЗЗЗ0417();
            bЗЗЗЗЗ0417 = b04170417ЗЗЗ0417();
        }
        return b0449щ0449щщ0449;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.HttpResponse executePost(java.lang.String r4, java.util.Map r5, int r6) throws java.lang.Exception {
        /*
        r3 = this;
        r0 = new org.apache.http.client.methods.HttpPost;
        r0.<init>(r4);
        r1 = b04170417041704170417З;
        r2 = b0417ЗЗЗЗ0417;
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = bЗ04170417ЗЗ0417();
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x001b;
            default: goto L_0x0013;
        };
    L_0x0013:
        r1 = 37;
        b04170417041704170417З = r1;
        r1 = 62;
        bЗЗЗЗЗ0417 = r1;
    L_0x001b:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x001b;
            case 1: goto L_0x0024;
            default: goto L_0x001f;
        };
    L_0x001f:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x0024;
            case 1: goto L_0x001b;
            default: goto L_0x0023;
        };
    L_0x0023:
        goto L_0x001f;
    L_0x0024:
        r0 = r3.b0449щ0449щщ0449(r0, r5, r6);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.ImmersionHttpClient.executePost(java.lang.String, java.util.Map, int):org.apache.http.HttpResponse");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.HttpResponse executePostWithBody(java.lang.String r5, java.lang.String r6, java.util.Map r7, int r8) throws java.lang.Exception {
        /*
        r4 = this;
        r2 = 0;
        r0 = new org.apache.http.client.methods.HttpPost;
        r0.<init>(r5);
        r1 = new org.apache.http.entity.StringEntity;	 Catch:{ UnsupportedEncodingException -> 0x0037 }
    L_0x0008:
        switch(r2) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0008;
            default: goto L_0x000b;
        };
    L_0x000b:
        switch(r2) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0008;
            default: goto L_0x000e;
        };
    L_0x000e:
        goto L_0x000b;
    L_0x000f:
        r2 = b04170417041704170417З;
        r3 = b0417ЗЗЗЗ0417;
        r2 = r2 + r3;
        r3 = b04170417041704170417З;
        r2 = r2 * r3;
        r3 = bЗ0417ЗЗЗ0417;
        r2 = r2 % r3;
        r3 = bЗЗЗЗЗ0417;
        if (r2 == r3) goto L_0x002a;
    L_0x001e:
        r2 = b04170417ЗЗЗ0417();
        b04170417041704170417З = r2;
        r2 = b04170417ЗЗЗ0417();
        bЗЗЗЗЗ0417 = r2;
    L_0x002a:
        r2 = "UTF-8";
        r1.<init>(r6, r2);	 Catch:{ UnsupportedEncodingException -> 0x0037 }
        r0.setEntity(r1);
        r0 = r4.b0449щ0449щщ0449(r0, r7, r8);
        return r0;
    L_0x0037:
        r0 = move-exception;
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.ImmersionHttpClient.executePostWithBody(java.lang.String, java.lang.String, java.util.Map, int):org.apache.http.HttpResponse");
    }

    public HttpParams getParams() {
        try {
            DefaultHttpClient defaultHttpClient = this.bнн043Dн043Dн;
            if (((b04170417041704170417З + b0417ЗЗЗЗ0417) * b04170417041704170417З) % bЗ0417ЗЗЗ0417 != bЗЗЗЗЗ0417) {
                b04170417041704170417З = b04170417ЗЗЗ0417();
                bЗЗЗЗЗ0417 = 88;
            }
            try {
                return defaultHttpClient.getParams();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
