package com.tencent.map.b;

import android.net.Proxy;
import com.letv.core.constant.LetvConstant;
import com.letv.pp.utils.NetworkUtils;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy.Type;
import java.net.URL;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

/* compiled from: ProGuard */
public final class q {
    private static int a = 0;
    private static boolean b;

    private static HttpURLConnection a(String str, boolean z) {
        HttpURLConnection httpURLConnection;
        Exception exception;
        Throwable th;
        HttpURLConnection httpURLConnection2 = null;
        try {
            boolean z2;
            URL url = new URL(str);
            if (l.c()) {
                z2 = false;
            } else {
                m.a();
                z2 = !b.a(Proxy.getDefaultHost());
            }
            if (z2) {
                String defaultHost;
                int defaultPort;
                if (a == 0 && !b) {
                    b = true;
                    try {
                        URL url2 = new URL("http://ls.map.soso.com/monitor/monitor.html");
                        defaultHost = Proxy.getDefaultHost();
                        defaultPort = Proxy.getDefaultPort();
                        if (defaultPort == -1) {
                            defaultPort = 80;
                        }
                        try {
                            HttpURLConnection httpURLConnection3 = (HttpURLConnection) url2.openConnection(new java.net.Proxy(Type.HTTP, new InetSocketAddress(defaultHost, defaultPort)));
                            try {
                                httpURLConnection3.setRequestMethod("GET");
                                httpURLConnection3.setConnectTimeout(LetvConstant.WIDGET_UPDATE_UI_TIME);
                                httpURLConnection3.setReadTimeout(45000);
                                httpURLConnection3.setRequestProperty("User-Agent", "QQ Map Mobile");
                                httpURLConnection3.setDoInput(true);
                                httpURLConnection3.setDoOutput(false);
                                httpURLConnection3.setUseCaches(false);
                                boolean a = a(httpURLConnection3);
                                httpURLConnection3.connect();
                                if (a) {
                                    a(1);
                                } else {
                                    a(2);
                                }
                                if (httpURLConnection3 != null) {
                                    httpURLConnection3.disconnect();
                                }
                            } catch (Exception e) {
                                Exception exception2 = e;
                                httpURLConnection = httpURLConnection3;
                                exception = exception2;
                                try {
                                    exception.printStackTrace();
                                    a(2);
                                    if (httpURLConnection != null) {
                                        httpURLConnection.disconnect();
                                    }
                                    switch (a) {
                                        case 2:
                                            return a(url, str);
                                        default:
                                            defaultHost = Proxy.getDefaultHost();
                                            defaultPort = Proxy.getDefaultPort();
                                            if (defaultPort == -1) {
                                                defaultPort = 80;
                                            }
                                            return (HttpURLConnection) url.openConnection(new java.net.Proxy(Type.HTTP, new InetSocketAddress(defaultHost, defaultPort)));
                                    }
                                    e.printStackTrace();
                                    return null;
                                } catch (Throwable th2) {
                                    th = th2;
                                    httpURLConnection2 = httpURLConnection;
                                    if (httpURLConnection2 != null) {
                                        httpURLConnection2.disconnect();
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                Throwable th4 = th3;
                                httpURLConnection2 = httpURLConnection3;
                                th = th4;
                                if (httpURLConnection2 != null) {
                                    httpURLConnection2.disconnect();
                                }
                                throw th;
                            }
                        } catch (Exception e2) {
                            exception = e2;
                            httpURLConnection = null;
                            exception.printStackTrace();
                            a(2);
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            switch (a) {
                                case 2:
                                    return a(url, str);
                                default:
                                    defaultHost = Proxy.getDefaultHost();
                                    defaultPort = Proxy.getDefaultPort();
                                    if (defaultPort == -1) {
                                        defaultPort = 80;
                                    }
                                    return (HttpURLConnection) url.openConnection(new java.net.Proxy(Type.HTTP, new InetSocketAddress(defaultHost, defaultPort)));
                            }
                            e.printStackTrace();
                            return null;
                        } catch (Throwable th5) {
                            th = th5;
                            if (httpURLConnection2 != null) {
                                httpURLConnection2.disconnect();
                            }
                            throw th;
                        }
                    } catch (MalformedURLException e3) {
                        b = false;
                    }
                }
                try {
                    switch (a) {
                        case 2:
                            return a(url, str);
                        default:
                            defaultHost = Proxy.getDefaultHost();
                            defaultPort = Proxy.getDefaultPort();
                            if (defaultPort == -1) {
                                defaultPort = 80;
                            }
                            return (HttpURLConnection) url.openConnection(new java.net.Proxy(Type.HTTP, new InetSocketAddress(defaultHost, defaultPort)));
                    }
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return null;
                }
                e4.printStackTrace();
                return null;
            }
            try {
                return (HttpURLConnection) url.openConnection();
            } catch (IOException e42) {
                e42.printStackTrace();
                return null;
            }
        } catch (MalformedURLException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.tencent.map.b.n a(boolean r6, java.lang.String r7, java.lang.String r8, java.lang.String r9, byte[] r10, boolean r11, boolean r12) throws java.lang.Exception {
        /*
        r2 = 1;
        r0 = 0;
        r1 = 0;
        r3 = com.tencent.map.b.l.d();
        if (r3 != 0) goto L_0x000f;
    L_0x0009:
        r0 = new com.tencent.map.b.r;
        r0.<init>();
        throw r0;
    L_0x000f:
        r3 = a(r7, r12);	 Catch:{ p -> 0x011c, Exception -> 0x0116, all -> 0x0110 }
        r4 = 0;
        r4 = com.tencent.map.b.b.a(r4);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        if (r4 == 0) goto L_0x00a0;
    L_0x001a:
        r4 = r3.getURL();	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r4 = r4.getHost();	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r4 = com.tencent.map.b.b.a(r4);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        if (r4 == 0) goto L_0x0028;
    L_0x0028:
        if (r6 == 0) goto L_0x00bb;
    L_0x002a:
        r4 = "GET";
        r3.setRequestMethod(r4);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
    L_0x002f:
        r4 = com.tencent.map.b.k.a();	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r3.setConnectTimeout(r4);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r4 = com.tencent.map.b.k.b();	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r3.setReadTimeout(r4);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r4 = "User-Agent";
        r3.setRequestProperty(r4, r8);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r4 = 1;
        r3.setDoInput(r4);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        if (r6 == 0) goto L_0x0049;
    L_0x0048:
        r2 = r0;
    L_0x0049:
        r3.setDoOutput(r2);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r2 = 0;
        r3.setUseCaches(r2);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        if (r11 == 0) goto L_0x0059;
    L_0x0052:
        r2 = "Connection";
        r4 = "Keep-Alive";
        r3.setRequestProperty(r2, r4);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
    L_0x0059:
        com.tencent.map.b.k.a(r3);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r3.connect();	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        com.tencent.map.b.k.c();	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        if (r6 != 0) goto L_0x007b;
    L_0x0064:
        if (r10 == 0) goto L_0x007b;
    L_0x0066:
        r2 = r10.length;	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        if (r2 == 0) goto L_0x007b;
    L_0x0069:
        r2 = new java.io.DataOutputStream;	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r4 = r3.getOutputStream();	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r2.<init>(r4);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r2.write(r10);	 Catch:{ p -> 0x011f, Exception -> 0x0119, all -> 0x0113 }
        r2.flush();	 Catch:{ p -> 0x011f, Exception -> 0x0119, all -> 0x0113 }
        r2.close();	 Catch:{ p -> 0x011f, Exception -> 0x0119, all -> 0x0113 }
    L_0x007b:
        r2 = r3.getResponseCode();	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r4 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r2 == r4) goto L_0x0087;
    L_0x0083:
        r4 = 206; // 0xce float:2.89E-43 double:1.02E-321;
        if (r2 != r4) goto L_0x00ca;
    L_0x0087:
        com.tencent.map.b.k.d();	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r2 = a(r3, r6);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        if (r2 == 0) goto L_0x0097;
    L_0x0090:
        r4 = r2.a;	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        if (r4 == 0) goto L_0x0097;
    L_0x0094:
        r0 = r2.a;	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r0 = r0.length;	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
    L_0x0097:
        com.tencent.map.b.k.a(r0);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        if (r3 == 0) goto L_0x009f;
    L_0x009c:
        r3.disconnect();
    L_0x009f:
        return r2;
    L_0x00a0:
        r4 = "Host";
        r5 = 0;
        r3.setRequestProperty(r4, r5);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        goto L_0x0028;
    L_0x00a7:
        r0 = move-exception;
        r2 = r3;
    L_0x00a9:
        r3 = 1;
        com.tencent.map.b.k.a(r3);	 Catch:{ all -> 0x00ae }
        throw r0;	 Catch:{ all -> 0x00ae }
    L_0x00ae:
        r0 = move-exception;
        r3 = r2;
    L_0x00b0:
        if (r1 == 0) goto L_0x00b5;
    L_0x00b2:
        r1.close();
    L_0x00b5:
        if (r3 == 0) goto L_0x00ba;
    L_0x00b7:
        r3.disconnect();
    L_0x00ba:
        throw r0;
    L_0x00bb:
        r4 = "POST";
        r3.setRequestMethod(r4);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        goto L_0x002f;
    L_0x00c2:
        r0 = move-exception;
    L_0x00c3:
        r2 = 0;
        com.tencent.map.b.k.a(r2);	 Catch:{ all -> 0x00c8 }
        throw r0;	 Catch:{ all -> 0x00c8 }
    L_0x00c8:
        r0 = move-exception;
        goto L_0x00b0;
    L_0x00ca:
        r0 = 202; // 0xca float:2.83E-43 double:1.0E-321;
        if (r2 == r0) goto L_0x00f2;
    L_0x00ce:
        r0 = 201; // 0xc9 float:2.82E-43 double:9.93E-322;
        if (r2 == r0) goto L_0x00f2;
    L_0x00d2:
        r0 = 204; // 0xcc float:2.86E-43 double:1.01E-321;
        if (r2 == r0) goto L_0x00f2;
    L_0x00d6:
        r0 = 205; // 0xcd float:2.87E-43 double:1.013E-321;
        if (r2 == r0) goto L_0x00f2;
    L_0x00da:
        r0 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        if (r2 == r0) goto L_0x00f2;
    L_0x00de:
        r0 = 305; // 0x131 float:4.27E-43 double:1.507E-321;
        if (r2 == r0) goto L_0x00f2;
    L_0x00e2:
        r0 = 408; // 0x198 float:5.72E-43 double:2.016E-321;
        if (r2 == r0) goto L_0x00f2;
    L_0x00e6:
        r0 = 502; // 0x1f6 float:7.03E-43 double:2.48E-321;
        if (r2 == r0) goto L_0x00f2;
    L_0x00ea:
        r0 = 504; // 0x1f8 float:7.06E-43 double:2.49E-321;
        if (r2 == r0) goto L_0x00f2;
    L_0x00ee:
        r0 = 503; // 0x1f7 float:7.05E-43 double:2.485E-321;
        if (r2 != r0) goto L_0x00fa;
    L_0x00f2:
        r0 = new java.io.IOException;	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r2 = "doGetOrPost retry";
        r0.<init>(r2);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        throw r0;	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
    L_0x00fa:
        r0 = new com.tencent.map.b.p;	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r4 = new java.lang.StringBuilder;	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r5 = "response code is ";
        r4.<init>(r5);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r2 = r4.append(r2);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r2 = r2.toString();	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        r0.<init>(r2);	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
        throw r0;	 Catch:{ p -> 0x00a7, Exception -> 0x00c2 }
    L_0x0110:
        r0 = move-exception;
        r3 = r1;
        goto L_0x00b0;
    L_0x0113:
        r0 = move-exception;
        r1 = r2;
        goto L_0x00b0;
    L_0x0116:
        r0 = move-exception;
        r3 = r1;
        goto L_0x00c3;
    L_0x0119:
        r0 = move-exception;
        r1 = r2;
        goto L_0x00c3;
    L_0x011c:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00a9;
    L_0x011f:
        r0 = move-exception;
        r1 = r2;
        r2 = r3;
        goto L_0x00a9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.q.a(boolean, java.lang.String, java.lang.String, java.lang.String, byte[], boolean, boolean):com.tencent.map.b.n");
    }

    private static n a(HttpURLConnection httpURLConnection, boolean z) throws IOException {
        int i = 1;
        int i2 = 0;
        InputStream inputStream = null;
        try {
            n nVar = new n();
            String contentType = httpURLConnection.getContentType();
            String str = "GBK";
            if (contentType != null) {
                String[] split = contentType.split(";");
                int length = split.length;
                int i3 = 0;
                while (i3 < length) {
                    String str2 = split[i3];
                    if (str2.contains(HttpRequest.PARAM_CHARSET)) {
                        String[] split2 = str2.split(SearchCriteria.EQ);
                        if (split2.length > 1) {
                            str = split2[1].trim();
                        }
                    } else {
                        i3++;
                    }
                }
            }
            nVar.b = str;
            if (z) {
                if (contentType == null || !contentType.contains("vnd.wap.wml")) {
                    i = 0;
                }
                if (i != 0) {
                    httpURLConnection.disconnect();
                    httpURLConnection.connect();
                }
            }
            inputStream = httpURLConnection.getInputStream();
            if (inputStream != null) {
                nVar.a = new byte[0];
                Object obj = new byte[1024];
                int read;
                do {
                    read = inputStream.read(obj);
                    if (read > 0) {
                        i2 += read;
                        Object obj2 = new byte[i2];
                        System.arraycopy(nVar.a, 0, obj2, 0, nVar.a.length);
                        System.arraycopy(obj, 0, obj2, nVar.a.length, read);
                        nVar.a = obj2;
                        continue;
                    }
                } while (read > 0);
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            return nVar;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                }
            }
        }
    }

    private static void a(int i) {
        if (a != i) {
            a = i;
        }
    }

    private static boolean a(HttpURLConnection httpURLConnection) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = httpURLConnection.getInputStream();
            if (!httpURLConnection.getContentType().equals("text/html")) {
                return false;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (inputStream.available() > 0) {
                byteArrayOutputStream.write(inputStream.read());
            }
            boolean equals = new String(byteArrayOutputStream.toByteArray()).trim().equals("1");
            if (inputStream == null) {
                return equals;
            }
            inputStream.close();
            return equals;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private static HttpURLConnection a(URL url, String str) throws IOException {
        String replaceFirst;
        int i = 80;
        String defaultHost = Proxy.getDefaultHost();
        int defaultPort = Proxy.getDefaultPort();
        if (defaultPort == -1) {
            defaultPort = 80;
        }
        String host = url.getHost();
        int port = url.getPort();
        if (port != -1) {
            i = port;
        }
        if (str.indexOf(new StringBuilder(String.valueOf(host)).append(NetworkUtils.DELIMITER_COLON).append(i).toString()) != -1) {
            replaceFirst = str.replaceFirst(new StringBuilder(String.valueOf(host)).append(NetworkUtils.DELIMITER_COLON).append(i).toString(), new StringBuilder(String.valueOf(defaultHost)).append(NetworkUtils.DELIMITER_COLON).append(defaultPort).toString());
        } else {
            replaceFirst = str.replaceFirst(host, new StringBuilder(String.valueOf(defaultHost)).append(NetworkUtils.DELIMITER_COLON).append(defaultPort).toString());
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(replaceFirst).openConnection();
            httpURLConnection.setRequestProperty("X-Online-Host", new StringBuilder(String.valueOf(host)).append(NetworkUtils.DELIMITER_COLON).append(i).toString());
            return httpURLConnection;
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
