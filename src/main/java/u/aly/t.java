package u.aly;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.letv.component.upgrade.utils.AppUpgradeConstants;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.a;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;

/* compiled from: NetworkHelper */
public class t {
    private String a;
    private String b = AppUpgradeConstants.CM_WAP_PROXY;
    private int c = 80;
    private Context d;
    private r e;

    public t(Context context) {
        this.d = context;
        this.a = a(context);
    }

    public void a(r rVar) {
        this.e = rVar;
    }

    public byte[] a(byte[] bArr) {
        byte[] bArr2 = null;
        for (String a : a.f) {
            bArr2 = a(bArr, a);
            if (bArr2 != null) {
                if (this.e != null) {
                    this.e.c();
                }
                return bArr2;
            }
            if (this.e != null) {
                this.e.d();
            }
        }
        return bArr2;
    }

    private boolean a() {
        if (this.d.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", this.d.getPackageName()) != 0) {
            return false;
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.d.getSystemService("connectivity")).getActiveNetworkInfo();
            if (!(activeNetworkInfo == null || activeNetworkInfo.getType() == 1)) {
                String extraInfo = activeNetworkInfo.getExtraInfo();
                if (extraInfo != null && (extraInfo.equals("cmwap") || extraInfo.equals("3gwap") || extraInfo.equals("uniwap"))) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private byte[] a(byte[] bArr, String str) {
        Throwable e;
        HttpURLConnection httpURLConnection;
        try {
            if (this.e != null) {
                this.e.a();
            }
            if (a()) {
                httpURLConnection = (HttpURLConnection) new URL(str).openConnection(new Proxy(Type.HTTP, new InetSocketAddress(this.b, this.c)));
            } else {
                httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            }
            InputStream inputStream;
            try {
                httpURLConnection.setRequestProperty("X-Umeng-UTC", String.valueOf(System.currentTimeMillis()));
                httpURLConnection.setRequestProperty("X-Umeng-Sdk", this.a);
                httpURLConnection.setRequestProperty("Msg-Type", "envelope");
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(30000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setChunkedStreamingMode(0);
                if (Integer.parseInt(VERSION.SDK) < 8) {
                    System.setProperty("http.keepAlive", "false");
                }
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(bArr);
                outputStream.flush();
                outputStream.close();
                if (this.e != null) {
                    this.e.b();
                }
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    Object headerField = httpURLConnection.getHeaderField(HttpRequest.HEADER_CONTENT_TYPE);
                    if (TextUtils.isEmpty(headerField) || !headerField.equalsIgnoreCase("application/thrift")) {
                        headerField = null;
                    } else {
                        headerField = 1;
                    }
                    bv.c("status code: " + responseCode);
                    if (headerField != null) {
                        bv.d("Send message to " + str);
                        inputStream = httpURLConnection.getInputStream();
                        byte[] b = bu.b(inputStream);
                        bu.c(inputStream);
                        if (httpURLConnection == null) {
                            return b;
                        }
                        httpURLConnection.disconnect();
                        return b;
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return null;
                }
                bv.f("status code: " + responseCode);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return null;
            } catch (Exception e2) {
                e = e2;
                try {
                    bv.e("IOException,Failed to send message.", e);
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return null;
                } catch (Throwable th) {
                    e = th;
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw e;
                }
            } catch (Throwable th2) {
                bu.c(inputStream);
            }
        } catch (Exception e3) {
            e = e3;
            httpURLConnection = null;
            bv.e("IOException,Failed to send message.", e);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        } catch (Throwable th3) {
            e = th3;
            httpURLConnection = null;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw e;
        }
    }

    private String a(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Android");
        stringBuffer.append("/");
        stringBuffer.append(a.c);
        stringBuffer.append(" ");
        try {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(bt.w(context));
            stringBuffer2.append("/");
            stringBuffer2.append(bt.d(context));
            stringBuffer2.append(" ");
            stringBuffer2.append(Build.MODEL);
            stringBuffer2.append("/");
            stringBuffer2.append(VERSION.RELEASE);
            stringBuffer2.append(" ");
            stringBuffer2.append(bu.a(AnalyticsConfig.getAppkey(context)));
            stringBuffer.append(URLEncoder.encode(stringBuffer2.toString(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
