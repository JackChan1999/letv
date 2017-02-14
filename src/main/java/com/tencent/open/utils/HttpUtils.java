package com.tencent.open.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.letv.core.constant.LetvConstant;
import com.tencent.connect.a.a;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.a.f;
import com.tencent.open.utils.Util.Statistic;
import com.tencent.tauth.IRequestListener;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.CharConversionException;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.NotActiveException;
import java.io.NotSerializableException;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.io.SyncFailedException;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.io.WriteAbortedException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileLockInterruptionException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnmappableCharacterException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.InvalidPropertiesFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLKeyException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import master.flame.danmaku.danmaku.parser.IDataSource;
import org.apache.http.ConnectionClosedException;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.MalformedChunkCodingException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class HttpUtils {
    private static final String a = HttpUtils.class.getName();

    /* compiled from: ProGuard */
    public static class CustomSSLSocketFactory extends SSLSocketFactory {
        private final SSLContext a = SSLContext.getInstance("TLS");

        public CustomSSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            MyX509TrustManager myX509TrustManager;
            super(keyStore);
            try {
                myX509TrustManager = new MyX509TrustManager();
            } catch (Exception e) {
                myX509TrustManager = null;
            }
            this.a.init(null, new TrustManager[]{myX509TrustManager}, null);
        }

        public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException, UnknownHostException {
            return this.a.getSocketFactory().createSocket(socket, str, i, z);
        }

        public Socket createSocket() throws IOException {
            return this.a.getSocketFactory().createSocket();
        }
    }

    /* compiled from: ProGuard */
    public static class HttpStatusException extends Exception {
        public static final String ERROR_INFO = "http status code error:";

        public HttpStatusException(String str) {
            super(str);
        }
    }

    /* compiled from: ProGuard */
    public static class MyX509TrustManager implements X509TrustManager {
        X509TrustManager a;

        MyX509TrustManager() throws Exception {
            KeyStore instance;
            Throwable th;
            FileInputStream fileInputStream;
            try {
                instance = KeyStore.getInstance("JKS");
            } catch (Exception e) {
                instance = null;
            }
            TrustManager[] trustManagerArr = new TrustManager[0];
            if (instance != null) {
                try {
                    InputStream fileInputStream2 = new FileInputStream("trustedCerts");
                    try {
                        instance.load(fileInputStream2, "passphrase".toCharArray());
                        TrustManagerFactory instance2 = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
                        instance2.init(instance);
                        TrustManager[] trustManagers = instance2.getTrustManagers();
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        InputStream inputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    Throwable th4 = th3;
                    fileInputStream = null;
                    th = th4;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
            }
            TrustManagerFactory instance3 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance3.init((KeyStore) null);
            trustManagers = instance3.getTrustManagers();
            for (int i = 0; i < trustManagers.length; i++) {
                if (trustManagers[i] instanceof X509TrustManager) {
                    this.a = (X509TrustManager) trustManagers[i];
                    return;
                }
            }
            throw new Exception("Couldn't initialize");
        }

        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            try {
                this.a.checkClientTrusted(x509CertificateArr, str);
            } catch (CertificateException e) {
            }
        }

        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            try {
                this.a.checkServerTrusted(x509CertificateArr, str);
            } catch (CertificateException e) {
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return this.a.getAcceptedIssuers();
        }
    }

    /* compiled from: ProGuard */
    public static class NetworkProxy {
        public final String host;
        public final int port;

        private NetworkProxy(String str, int i) {
            this.host = str;
            this.port = i;
        }
    }

    /* compiled from: ProGuard */
    public static class NetworkUnavailableException extends Exception {
        public static final String ERROR_INFO = "network unavailable";

        public NetworkUnavailableException(String str) {
            super(str);
        }
    }

    private HttpUtils() {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.json.JSONObject request(com.tencent.connect.auth.QQToken r20, android.content.Context r21, java.lang.String r22, android.os.Bundle r23, java.lang.String r24) throws java.io.IOException, org.json.JSONException, com.tencent.open.utils.HttpUtils.NetworkUnavailableException, com.tencent.open.utils.HttpUtils.HttpStatusException {
        /*
        r4 = com.tencent.open.a.f.d;
        r5 = "OpenApi request";
        com.tencent.open.a.f.a(r4, r5);
        r4 = r22.toLowerCase();
        r5 = "http";
        r4 = r4.startsWith(r5);
        if (r4 != 0) goto L_0x01c2;
    L_0x0013:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = com.tencent.open.utils.ServerSetting.getInstance();
        r6 = "https://openmobile.qq.com/";
        r0 = r21;
        r5 = r5.getEnvUrl(r0, r6);
        r4 = r4.append(r5);
        r0 = r22;
        r4 = r4.append(r0);
        r4 = r4.toString();
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = com.tencent.open.utils.ServerSetting.getInstance();
        r7 = "https://openmobile.qq.com/";
        r0 = r21;
        r6 = r6.getEnvUrl(r0, r7);
        r5 = r5.append(r6);
        r0 = r22;
        r5 = r5.append(r0);
        r5 = r5.toString();
    L_0x0051:
        r0 = r21;
        r1 = r20;
        r2 = r22;
        a(r0, r1, r2);
        r10 = 0;
        r8 = android.os.SystemClock.elapsedRealtime();
        r7 = 0;
        r6 = r20.getAppId();
        r0 = r21;
        r6 = com.tencent.open.utils.OpenConfig.getInstance(r0, r6);
        r11 = "Common_HttpRetryCount";
        r6 = r6.getInt(r11);
        r11 = "OpenConfig_test";
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = "config 1:Common_HttpRetryCount            config_value:";
        r12 = r12.append(r13);
        r12 = r12.append(r6);
        r13 = "   appid:";
        r12 = r12.append(r13);
        r13 = r20.getAppId();
        r12 = r12.append(r13);
        r13 = "     url:";
        r12 = r12.append(r13);
        r12 = r12.append(r5);
        r12 = r12.toString();
        com.tencent.open.a.f.b(r11, r12);
        if (r6 != 0) goto L_0x0100;
    L_0x00a2:
        r6 = 3;
        r13 = r6;
    L_0x00a4:
        r6 = "OpenConfig_test";
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "config 1:Common_HttpRetryCount            result_value:";
        r11 = r11.append(r12);
        r11 = r11.append(r13);
        r12 = "   appid:";
        r11 = r11.append(r12);
        r12 = r20.getAppId();
        r11 = r11.append(r12);
        r12 = "     url:";
        r11 = r11.append(r12);
        r11 = r11.append(r5);
        r11 = r11.toString();
        com.tencent.open.a.f.b(r6, r11);
        r18 = r7;
        r6 = r8;
        r8 = r18;
        r9 = r10;
    L_0x00da:
        r14 = r8 + 1;
        r0 = r21;
        r1 = r24;
        r2 = r23;
        r10 = openUrl2(r0, r4, r1, r2);	 Catch:{ ConnectTimeoutException -> 0x01b8, SocketTimeoutException -> 0x01b2, HttpStatusException -> 0x014c, NetworkUnavailableException -> 0x0174, MalformedURLException -> 0x0179, IOException -> 0x018b, JSONException -> 0x01a0 }
        r8 = r10.response;	 Catch:{ ConnectTimeoutException -> 0x01b8, SocketTimeoutException -> 0x01b2, HttpStatusException -> 0x014c, NetworkUnavailableException -> 0x0174, MalformedURLException -> 0x0179, IOException -> 0x018b, JSONException -> 0x01a0 }
        r15 = com.tencent.open.utils.Util.parseJson(r8);	 Catch:{ ConnectTimeoutException -> 0x01b8, SocketTimeoutException -> 0x01b2, HttpStatusException -> 0x014c, NetworkUnavailableException -> 0x0174, MalformedURLException -> 0x0179, IOException -> 0x018b, JSONException -> 0x01a0 }
        r8 = "ret";
        r12 = r15.getInt(r8);	 Catch:{ JSONException -> 0x0102, ConnectTimeoutException -> 0x0105, SocketTimeoutException -> 0x012b, HttpStatusException -> 0x014c, NetworkUnavailableException -> 0x0174, MalformedURLException -> 0x0179, IOException -> 0x018b }
    L_0x00f3:
        r8 = r10.reqSize;	 Catch:{ ConnectTimeoutException -> 0x0105, SocketTimeoutException -> 0x012b, HttpStatusException -> 0x014c, NetworkUnavailableException -> 0x0174, MalformedURLException -> 0x0179, IOException -> 0x018b, JSONException -> 0x01a0 }
        r10 = r10.rspSize;	 Catch:{ ConnectTimeoutException -> 0x0105, SocketTimeoutException -> 0x012b, HttpStatusException -> 0x014c, NetworkUnavailableException -> 0x0174, MalformedURLException -> 0x0179, IOException -> 0x018b, JSONException -> 0x01a0 }
        r13 = r15;
    L_0x00f8:
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        return r13;
    L_0x0100:
        r13 = r6;
        goto L_0x00a4;
    L_0x0102:
        r8 = move-exception;
        r12 = -4;
        goto L_0x00f3;
    L_0x0105:
        r8 = move-exception;
        r16 = r15;
        r15 = r8;
    L_0x0109:
        r15.printStackTrace();
        r12 = -7;
        r8 = 0;
        r10 = 0;
        if (r14 >= r13) goto L_0x0123;
    L_0x0113:
        r6 = android.os.SystemClock.elapsedRealtime();
        r18 = r8;
        r8 = r16;
        r16 = r18;
    L_0x011d:
        if (r14 < r13) goto L_0x01be;
    L_0x011f:
        r13 = r8;
        r8 = r16;
        goto L_0x00f8;
    L_0x0123:
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        throw r15;
    L_0x012b:
        r8 = move-exception;
        r16 = r15;
        r15 = r8;
    L_0x012f:
        r15.printStackTrace();
        r12 = -8;
        r8 = 0;
        r10 = 0;
        if (r14 >= r13) goto L_0x0144;
    L_0x0139:
        r6 = android.os.SystemClock.elapsedRealtime();
        r18 = r8;
        r8 = r16;
        r16 = r18;
        goto L_0x011d;
    L_0x0144:
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        throw r15;
    L_0x014c:
        r4 = move-exception;
        r13 = r4;
        r13.printStackTrace();
        r4 = r13.getMessage();
        r8 = "http status code error:";
        r9 = "";
        r4 = r4.replace(r8, r9);	 Catch:{ Exception -> 0x016d }
        r12 = java.lang.Integer.parseInt(r4);	 Catch:{ Exception -> 0x016d }
    L_0x0161:
        r8 = 0;
        r10 = 0;
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        throw r13;
    L_0x016d:
        r4 = move-exception;
        r4.printStackTrace();
        r12 = -9;
        goto L_0x0161;
    L_0x0174:
        r4 = move-exception;
        r4.printStackTrace();
        throw r4;
    L_0x0179:
        r4 = move-exception;
        r13 = r4;
        r13.printStackTrace();
        r12 = -3;
        r8 = 0;
        r10 = 0;
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        throw r13;
    L_0x018b:
        r4 = move-exception;
        r13 = r4;
        r13.printStackTrace();
        r12 = getErrorCodeFromException(r13);
        r8 = 0;
        r10 = 0;
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        throw r13;
    L_0x01a0:
        r4 = move-exception;
        r13 = r4;
        r13.printStackTrace();
        r12 = -4;
        r8 = 0;
        r10 = 0;
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        throw r13;
    L_0x01b2:
        r8 = move-exception;
        r15 = r8;
        r16 = r9;
        goto L_0x012f;
    L_0x01b8:
        r8 = move-exception;
        r15 = r8;
        r16 = r9;
        goto L_0x0109;
    L_0x01be:
        r9 = r8;
        r8 = r14;
        goto L_0x00da;
    L_0x01c2:
        r5 = r22;
        r4 = r22;
        goto L_0x0051;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.HttpUtils.request(com.tencent.connect.auth.QQToken, android.content.Context, java.lang.String, android.os.Bundle, java.lang.String):org.json.JSONObject");
    }

    public static void requestAsync(QQToken qQToken, Context context, String str, Bundle bundle, String str2, IRequestListener iRequestListener) {
        f.a(f.d, "OpenApi requestAsync");
        final QQToken qQToken2 = qQToken;
        final Context context2 = context;
        final String str3 = str;
        final Bundle bundle2 = bundle;
        final String str4 = str2;
        final IRequestListener iRequestListener2 = iRequestListener;
        new Thread() {
            public void run() {
                try {
                    JSONObject request = HttpUtils.request(qQToken2, context2, str3, bundle2, str4);
                    if (iRequestListener2 != null) {
                        iRequestListener2.onComplete(request);
                        f.b(f.d, "OpenApi onComplete");
                    }
                } catch (Throwable e) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onMalformedURLException(e);
                        f.b(f.d, "OpenApi requestAsync MalformedURLException", e);
                    }
                } catch (Throwable e2) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onConnectTimeoutException(e2);
                        f.b(f.d, "OpenApi requestAsync onConnectTimeoutException", e2);
                    }
                } catch (Throwable e22) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onSocketTimeoutException(e22);
                        f.b(f.d, "OpenApi requestAsync onSocketTimeoutException", e22);
                    }
                } catch (Throwable e222) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onNetworkUnavailableException(e222);
                        f.b(f.d, "OpenApi requestAsync onNetworkUnavailableException", e222);
                    }
                } catch (Throwable e2222) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onHttpStatusException(e2222);
                        f.b(f.d, "OpenApi requestAsync onHttpStatusException", e2222);
                    }
                } catch (Throwable e22222) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onIOException(e22222);
                        f.b(f.d, "OpenApi requestAsync IOException", e22222);
                    }
                } catch (Throwable e222222) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onJSONException(e222222);
                        f.b(f.d, "OpenApi requestAsync JSONException", e222222);
                    }
                } catch (Throwable e2222222) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onUnknowException(e2222222);
                        f.b(f.d, "OpenApi requestAsync onUnknowException", e2222222);
                    }
                }
            }
        }.start();
    }

    private static void a(Context context, QQToken qQToken, String str) {
        if (str.indexOf("add_share") > -1 || str.indexOf("upload_pic") > -1 || str.indexOf("add_topic") > -1 || str.indexOf("set_user_face") > -1 || str.indexOf("add_t") > -1 || str.indexOf("add_pic_t") > -1 || str.indexOf("add_pic_url") > -1 || str.indexOf("add_video") > -1) {
            a.a(context, qQToken, "requireApi", str);
        }
    }

    public static int getErrorCodeFromException(IOException iOException) {
        if (iOException instanceof CharConversionException) {
            return -20;
        }
        if (iOException instanceof MalformedInputException) {
            return -21;
        }
        if (iOException instanceof UnmappableCharacterException) {
            return -22;
        }
        if (iOException instanceof HttpResponseException) {
            return -23;
        }
        if (iOException instanceof ClosedChannelException) {
            return -24;
        }
        if (iOException instanceof ConnectionClosedException) {
            return -25;
        }
        if (iOException instanceof EOFException) {
            return -26;
        }
        if (iOException instanceof FileLockInterruptionException) {
            return -27;
        }
        if (iOException instanceof FileNotFoundException) {
            return -28;
        }
        if (iOException instanceof HttpRetryException) {
            return -29;
        }
        if (iOException instanceof ConnectTimeoutException) {
            return -7;
        }
        if (iOException instanceof SocketTimeoutException) {
            return -8;
        }
        if (iOException instanceof InvalidPropertiesFormatException) {
            return -30;
        }
        if (iOException instanceof MalformedChunkCodingException) {
            return -31;
        }
        if (iOException instanceof MalformedURLException) {
            return -3;
        }
        if (iOException instanceof NoHttpResponseException) {
            return -32;
        }
        if (iOException instanceof InvalidClassException) {
            return -33;
        }
        if (iOException instanceof InvalidObjectException) {
            return -34;
        }
        if (iOException instanceof NotActiveException) {
            return -35;
        }
        if (iOException instanceof NotSerializableException) {
            return -36;
        }
        if (iOException instanceof OptionalDataException) {
            return -37;
        }
        if (iOException instanceof StreamCorruptedException) {
            return -38;
        }
        if (iOException instanceof WriteAbortedException) {
            return -39;
        }
        if (iOException instanceof ProtocolException) {
            return -40;
        }
        if (iOException instanceof SSLHandshakeException) {
            return -41;
        }
        if (iOException instanceof SSLKeyException) {
            return -42;
        }
        if (iOException instanceof SSLPeerUnverifiedException) {
            return -43;
        }
        if (iOException instanceof SSLProtocolException) {
            return -44;
        }
        if (iOException instanceof BindException) {
            return -45;
        }
        if (iOException instanceof ConnectException) {
            return -46;
        }
        if (iOException instanceof NoRouteToHostException) {
            return -47;
        }
        if (iOException instanceof PortUnreachableException) {
            return -48;
        }
        if (iOException instanceof SyncFailedException) {
            return -49;
        }
        if (iOException instanceof UTFDataFormatException) {
            return -50;
        }
        if (iOException instanceof UnknownHostException) {
            return -51;
        }
        if (iOException instanceof UnknownServiceException) {
            return -52;
        }
        if (iOException instanceof UnsupportedEncodingException) {
            return -53;
        }
        if (iOException instanceof ZipException) {
            return -54;
        }
        return -2;
    }

    public static Statistic openUrl2(Context context, String str, String str2, Bundle bundle) throws MalformedURLException, IOException, NetworkUnavailableException, HttpStatusException {
        Bundle bundle2;
        HttpUriRequest httpUriRequest;
        int i;
        int size;
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                    throw new NetworkUnavailableException(NetworkUnavailableException.ERROR_INFO);
                }
            }
        }
        if (bundle != null) {
            bundle2 = new Bundle(bundle);
        } else {
            bundle2 = new Bundle();
        }
        String str3 = "";
        str3 = bundle2.getString("appid_for_getting_config");
        bundle2.remove("appid_for_getting_config");
        HttpClient httpClient = getHttpClient(context, str3, str);
        int length;
        if (str2.equals("GET")) {
            String encodeUrl = encodeUrl(bundle2);
            length = 0 + encodeUrl.length();
            f.b(a, "-->openUrl2 before url =" + str);
            if (str.indexOf("?") == -1) {
                str3 = str + "?";
            } else {
                str3 = str + "&";
            }
            f.b(a, "-->openUrl2 encodedParam =" + encodeUrl + " -- url = " + str3);
            HttpUriRequest httpGet = new HttpGet(str3 + encodeUrl);
            httpGet.addHeader(HttpRequest.HEADER_ACCEPT_ENCODING, HttpRequest.ENCODING_GZIP);
            int i2 = length;
            httpUriRequest = httpGet;
            i = i2;
        } else if (str2.equals("POST")) {
            Object obj;
            HttpPost httpPost = new HttpPost(str);
            httpPost.addHeader(HttpRequest.HEADER_ACCEPT_ENCODING, HttpRequest.ENCODING_GZIP);
            Bundle bundle3 = new Bundle();
            for (String str32 : bundle2.keySet()) {
                obj = bundle2.get(str32);
                if (obj instanceof byte[]) {
                    bundle3.putByteArray(str32, (byte[]) obj);
                }
            }
            if (!bundle2.containsKey("method")) {
                bundle2.putString("method", str2);
            }
            httpPost.setHeader(HttpRequest.HEADER_CONTENT_TYPE, "multipart/form-data; boundary=3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f");
            httpPost.setHeader("Connection", "Keep-Alive");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(Util.getBytesUTF8("--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n"));
            byteArrayOutputStream.write(Util.getBytesUTF8(encodePostBody(bundle2, "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f")));
            if (!bundle3.isEmpty()) {
                size = bundle3.size();
                byteArrayOutputStream.write(Util.getBytesUTF8("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n"));
                length = -1;
                for (String str322 : bundle3.keySet()) {
                    length++;
                    byteArrayOutputStream.write(Util.getBytesUTF8("Content-Disposition: form-data; name=\"" + str322 + "\"; filename=\"" + str322 + "\"" + "\r\n"));
                    byteArrayOutputStream.write(Util.getBytesUTF8("Content-Type: content/unknown\r\n\r\n"));
                    byte[] byteArray = bundle3.getByteArray(str322);
                    if (byteArray != null) {
                        byteArrayOutputStream.write(byteArray);
                    }
                    if (length < size - 1) {
                        byteArrayOutputStream.write(Util.getBytesUTF8("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n"));
                    }
                }
            }
            byteArrayOutputStream.write(Util.getBytesUTF8("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f--\r\n"));
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            i = toByteArray.length + 0;
            byteArrayOutputStream.close();
            httpPost.setEntity(new ByteArrayEntity(toByteArray));
            obj = httpPost;
        } else {
            httpUriRequest = null;
            i = 0;
        }
        HttpResponse execute = httpClient.execute(httpUriRequest);
        size = execute.getStatusLine().getStatusCode();
        if (size == 200) {
            return new Statistic(a(execute), i);
        }
        throw new HttpStatusException(HttpStatusException.ERROR_INFO + size);
    }

    private static String a(HttpResponse httpResponse) throws IllegalStateException, IOException {
        InputStream inputStream;
        String str = "";
        InputStream content = httpResponse.getEntity().getContent();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Header firstHeader = httpResponse.getFirstHeader(HttpRequest.HEADER_CONTENT_ENCODING);
        if (firstHeader == null || firstHeader.getValue().toLowerCase().indexOf(HttpRequest.ENCODING_GZIP) <= -1) {
            inputStream = content;
        } else {
            inputStream = new GZIPInputStream(content);
        }
        byte[] bArr = new byte[512];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    public static HttpClient getHttpClient(Context context, String str, String str2) {
        OpenConfig instance;
        int i;
        int i2 = 0;
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(IDataSource.SCHEME_HTTP_TAG, PlainSocketFactory.getSocketFactory(), 80));
        if (VERSION.SDK_INT < 16) {
            try {
                KeyStore instance2 = KeyStore.getInstance(KeyStore.getDefaultType());
                instance2.load(null, null);
                SocketFactory customSSLSocketFactory = new CustomSSLSocketFactory(instance2);
                customSSLSocketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
                schemeRegistry.register(new Scheme(IDataSource.SCHEME_HTTPS_TAG, customSSLSocketFactory, 443));
            } catch (Exception e) {
                schemeRegistry.register(new Scheme(IDataSource.SCHEME_HTTPS_TAG, SSLSocketFactory.getSocketFactory(), 443));
            }
        } else {
            schemeRegistry.register(new Scheme(IDataSource.SCHEME_HTTPS_TAG, SSLSocketFactory.getSocketFactory(), 443));
        }
        HttpParams basicHttpParams = new BasicHttpParams();
        if (context != null) {
            instance = OpenConfig.getInstance(context, str);
        } else {
            instance = null;
        }
        if (instance != null) {
            i = instance.getInt("Common_HttpConnectionTimeout");
            i2 = instance.getInt("Common_SocketConnectionTimeout");
        } else {
            i = 0;
        }
        if (i == 0) {
            i = LetvConstant.WIDGET_UPDATE_UI_TIME;
        }
        if (i2 == 0) {
            i2 = 30000;
        }
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, i);
        HttpConnectionParams.setSoTimeout(basicHttpParams, i2);
        HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(basicHttpParams, "UTF-8");
        HttpProtocolParams.setUserAgent(basicHttpParams, "AndroidSDK_" + VERSION.SDK + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + Build.DEVICE + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + VERSION.RELEASE);
        HttpClient defaultHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
        NetworkProxy proxy = getProxy(context);
        if (proxy != null) {
            defaultHttpClient.getParams().setParameter("http.route.default-proxy", new HttpHost(proxy.host, proxy.port));
        }
        return defaultHttpClient;
    }

    public static String encodeUrl(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (String str : bundle.keySet()) {
            Object obj2 = bundle.get(str);
            if ((obj2 instanceof String) || (obj2 instanceof String[])) {
                Object obj3;
                if (obj2 instanceof String[]) {
                    if (obj != null) {
                        obj = null;
                    } else {
                        stringBuilder.append("&");
                    }
                    stringBuilder.append(URLEncoder.encode(str) + SearchCriteria.EQ);
                    String[] stringArray = bundle.getStringArray(str);
                    if (stringArray != null) {
                        for (int i = 0; i < stringArray.length; i++) {
                            if (i == 0) {
                                stringBuilder.append(URLEncoder.encode(stringArray[i]));
                            } else {
                                stringBuilder.append(URLEncoder.encode("," + stringArray[i]));
                            }
                        }
                        obj3 = obj;
                    }
                } else {
                    if (obj != null) {
                        obj = null;
                    } else {
                        stringBuilder.append("&");
                    }
                    stringBuilder.append(URLEncoder.encode(str) + SearchCriteria.EQ + URLEncoder.encode(bundle.getString(str)));
                    obj3 = obj;
                }
                obj = obj3;
            }
        }
        return stringBuilder.toString();
    }

    public static String encodePostBody(Bundle bundle, String str) {
        if (bundle == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int size = bundle.size();
        int i = -1;
        for (String str2 : bundle.keySet()) {
            int i2 = i + 1;
            Object obj = bundle.get(str2);
            if (obj instanceof String) {
                stringBuilder.append("Content-Disposition: form-data; name=\"" + str2 + "\"" + "\r\n" + "\r\n" + ((String) obj));
                if (i2 < size - 1) {
                    stringBuilder.append("\r\n--" + str + "\r\n");
                }
                i = i2;
            } else {
                i = i2;
            }
        }
        return stringBuilder.toString();
    }

    public static NetworkProxy getProxy(Context context) {
        if (context == null) {
            return null;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return null;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return null;
        }
        if (activeNetworkInfo.getType() == 0) {
            Object b = b(context);
            int a = a(context);
            if (!TextUtils.isEmpty(b) && a >= 0) {
                return new NetworkProxy(b, a);
            }
        }
        return null;
    }

    private static int a(Context context) {
        int i = -1;
        if (VERSION.SDK_INT >= 11) {
            Object property = System.getProperty("http.proxyPort");
            if (TextUtils.isEmpty(property)) {
                return i;
            }
            try {
                return Integer.parseInt(property);
            } catch (NumberFormatException e) {
                return i;
            }
        } else if (context == null) {
            return Proxy.getDefaultPort();
        } else {
            i = Proxy.getPort(context);
            if (i < 0) {
                return Proxy.getDefaultPort();
            }
            return i;
        }
    }

    private static String b(Context context) {
        if (VERSION.SDK_INT >= 11) {
            return System.getProperty("http.proxyHost");
        }
        if (context == null) {
            return Proxy.getDefaultHost();
        }
        String host = Proxy.getHost(context);
        if (TextUtils.isEmpty(host)) {
            return Proxy.getDefaultHost();
        }
        return host;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.json.JSONObject upload(com.tencent.connect.auth.QQToken r20, android.content.Context r21, java.lang.String r22, android.os.Bundle r23) throws java.io.IOException, org.json.JSONException, com.tencent.open.utils.HttpUtils.NetworkUnavailableException, com.tencent.open.utils.HttpUtils.HttpStatusException {
        /*
        r4 = r22.toLowerCase();
        r5 = "http";
        r4 = r4.startsWith(r5);
        if (r4 != 0) goto L_0x01b9;
    L_0x000c:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = com.tencent.open.utils.ServerSetting.getInstance();
        r6 = "https://openmobile.qq.com/";
        r0 = r21;
        r5 = r5.getEnvUrl(r0, r6);
        r4 = r4.append(r5);
        r0 = r22;
        r4 = r4.append(r0);
        r4 = r4.toString();
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = com.tencent.open.utils.ServerSetting.getInstance();
        r7 = "https://openmobile.qq.com/";
        r0 = r21;
        r6 = r6.getEnvUrl(r0, r7);
        r5 = r5.append(r6);
        r0 = r22;
        r5 = r5.append(r0);
        r5 = r5.toString();
    L_0x004a:
        r0 = r21;
        r1 = r20;
        r2 = r22;
        a(r0, r1, r2);
        r10 = 0;
        r8 = android.os.SystemClock.elapsedRealtime();
        r7 = 0;
        r6 = r20.getAppId();
        r0 = r21;
        r6 = com.tencent.open.utils.OpenConfig.getInstance(r0, r6);
        r11 = "Common_HttpRetryCount";
        r6 = r6.getInt(r11);
        r11 = "OpenConfig_test";
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = "config 1:Common_HttpRetryCount            config_value:";
        r12 = r12.append(r13);
        r12 = r12.append(r6);
        r13 = "   appid:";
        r12 = r12.append(r13);
        r13 = r20.getAppId();
        r12 = r12.append(r13);
        r13 = "     url:";
        r12 = r12.append(r13);
        r12 = r12.append(r5);
        r12 = r12.toString();
        com.tencent.open.a.f.b(r11, r12);
        if (r6 != 0) goto L_0x00f7;
    L_0x009b:
        r6 = 3;
        r13 = r6;
    L_0x009d:
        r6 = "OpenConfig_test";
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "config 1:Common_HttpRetryCount            result_value:";
        r11 = r11.append(r12);
        r11 = r11.append(r13);
        r12 = "   appid:";
        r11 = r11.append(r12);
        r12 = r20.getAppId();
        r11 = r11.append(r12);
        r12 = "     url:";
        r11 = r11.append(r12);
        r11 = r11.append(r5);
        r11 = r11.toString();
        com.tencent.open.a.f.b(r6, r11);
        r18 = r7;
        r6 = r8;
        r8 = r18;
        r9 = r10;
    L_0x00d3:
        r14 = r8 + 1;
        r0 = r21;
        r1 = r23;
        r10 = com.tencent.open.utils.Util.upload(r0, r4, r1);	 Catch:{ ConnectTimeoutException -> 0x01af, SocketTimeoutException -> 0x01a9, HttpStatusException -> 0x0143, NetworkUnavailableException -> 0x016b, MalformedURLException -> 0x0170, IOException -> 0x0182, JSONException -> 0x0197 }
        r8 = r10.response;	 Catch:{ ConnectTimeoutException -> 0x01af, SocketTimeoutException -> 0x01a9, HttpStatusException -> 0x0143, NetworkUnavailableException -> 0x016b, MalformedURLException -> 0x0170, IOException -> 0x0182, JSONException -> 0x0197 }
        r15 = com.tencent.open.utils.Util.parseJson(r8);	 Catch:{ ConnectTimeoutException -> 0x01af, SocketTimeoutException -> 0x01a9, HttpStatusException -> 0x0143, NetworkUnavailableException -> 0x016b, MalformedURLException -> 0x0170, IOException -> 0x0182, JSONException -> 0x0197 }
        r8 = "ret";
        r12 = r15.getInt(r8);	 Catch:{ JSONException -> 0x00f9, ConnectTimeoutException -> 0x00fc, SocketTimeoutException -> 0x0122, HttpStatusException -> 0x0143, NetworkUnavailableException -> 0x016b, MalformedURLException -> 0x0170, IOException -> 0x0182 }
    L_0x00ea:
        r8 = r10.reqSize;	 Catch:{ ConnectTimeoutException -> 0x00fc, SocketTimeoutException -> 0x0122, HttpStatusException -> 0x0143, NetworkUnavailableException -> 0x016b, MalformedURLException -> 0x0170, IOException -> 0x0182, JSONException -> 0x0197 }
        r10 = r10.rspSize;	 Catch:{ ConnectTimeoutException -> 0x00fc, SocketTimeoutException -> 0x0122, HttpStatusException -> 0x0143, NetworkUnavailableException -> 0x016b, MalformedURLException -> 0x0170, IOException -> 0x0182, JSONException -> 0x0197 }
        r13 = r15;
    L_0x00ef:
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        return r13;
    L_0x00f7:
        r13 = r6;
        goto L_0x009d;
    L_0x00f9:
        r8 = move-exception;
        r12 = -4;
        goto L_0x00ea;
    L_0x00fc:
        r8 = move-exception;
        r16 = r15;
        r15 = r8;
    L_0x0100:
        r15.printStackTrace();
        r12 = -7;
        r8 = 0;
        r10 = 0;
        if (r14 >= r13) goto L_0x011a;
    L_0x010a:
        r6 = android.os.SystemClock.elapsedRealtime();
        r18 = r8;
        r8 = r16;
        r16 = r18;
    L_0x0114:
        if (r14 < r13) goto L_0x01b5;
    L_0x0116:
        r13 = r8;
        r8 = r16;
        goto L_0x00ef;
    L_0x011a:
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        throw r15;
    L_0x0122:
        r8 = move-exception;
        r16 = r15;
        r15 = r8;
    L_0x0126:
        r15.printStackTrace();
        r12 = -8;
        r8 = 0;
        r10 = 0;
        if (r14 >= r13) goto L_0x013b;
    L_0x0130:
        r6 = android.os.SystemClock.elapsedRealtime();
        r18 = r8;
        r8 = r16;
        r16 = r18;
        goto L_0x0114;
    L_0x013b:
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        throw r15;
    L_0x0143:
        r4 = move-exception;
        r13 = r4;
        r13.printStackTrace();
        r4 = r13.getMessage();
        r8 = "http status code error:";
        r9 = "";
        r4 = r4.replace(r8, r9);	 Catch:{ Exception -> 0x0164 }
        r12 = java.lang.Integer.parseInt(r4);	 Catch:{ Exception -> 0x0164 }
    L_0x0158:
        r8 = 0;
        r10 = 0;
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        throw r13;
    L_0x0164:
        r4 = move-exception;
        r4.printStackTrace();
        r12 = -9;
        goto L_0x0158;
    L_0x016b:
        r4 = move-exception;
        r4.printStackTrace();
        throw r4;
    L_0x0170:
        r4 = move-exception;
        r13 = r4;
        r13.printStackTrace();
        r12 = -3;
        r8 = 0;
        r10 = 0;
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        throw r13;
    L_0x0182:
        r4 = move-exception;
        r13 = r4;
        r13.printStackTrace();
        r12 = getErrorCodeFromException(r13);
        r8 = 0;
        r10 = 0;
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        throw r13;
    L_0x0197:
        r4 = move-exception;
        r13 = r4;
        r13.printStackTrace();
        r12 = -4;
        r8 = 0;
        r10 = 0;
        r4 = com.tencent.open.b.g.a();
        r4.a(r5, r6, r8, r10, r12);
        throw r13;
    L_0x01a9:
        r8 = move-exception;
        r15 = r8;
        r16 = r9;
        goto L_0x0126;
    L_0x01af:
        r8 = move-exception;
        r15 = r8;
        r16 = r9;
        goto L_0x0100;
    L_0x01b5:
        r9 = r8;
        r8 = r14;
        goto L_0x00d3;
    L_0x01b9:
        r5 = r22;
        r4 = r22;
        goto L_0x004a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.HttpUtils.upload(com.tencent.connect.auth.QQToken, android.content.Context, java.lang.String, android.os.Bundle):org.json.JSONObject");
    }
}
