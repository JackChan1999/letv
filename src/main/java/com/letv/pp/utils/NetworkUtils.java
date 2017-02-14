package com.letv.pp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import com.letv.lemallsdk.util.Constants;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class NetworkUtils {
    public static final String DELIMITER_COLON = ":";
    public static final String DELIMITER_LINE = "-";
    public static final String DELIMITER_NULL = null;
    public static String ETH_MAC = "eth0";
    private static final String NETWORK_NAME_2G = "2g";
    private static final String NETWORK_NAME_3G = "3g";
    private static final String NETWORK_NAME_4G = "4g";
    private static final String NETWORK_NAME_5G = "5g";
    private static final String NETWORK_NAME_CDMA2000 = "CDMA2000";
    private static final String NETWORK_NAME_ETHERNET = "wired";
    private static final String NETWORK_NAME_MOBILE = "mun";
    private static final String NETWORK_NAME_MORE = "un";
    private static final String NETWORK_NAME_NO_NETWORK = "no network";
    private static final String NETWORK_NAME_NO_PERMISSION = "no permission";
    private static final String NETWORK_NAME_PPPOE = "pppoe";
    private static final String NETWORK_NAME_TD_SCDMA = "TD-SCDMA";
    private static final String NETWORK_NAME_WCDMA = "WCDMA";
    private static final String NETWORK_NAME_WIFI = "wifi";
    public static final int NETWORK_TYPE_2G = 4;
    public static final int NETWORK_TYPE_3G = 5;
    public static final int NETWORK_TYPE_4G = 6;
    public static final int NETWORK_TYPE_5G = 7;
    public static final int NETWORK_TYPE_ETHERNET = 1;
    public static final int NETWORK_TYPE_MOBILE = 2;
    public static final int NETWORK_TYPE_MORE = 8;
    public static final int NETWORK_TYPE_NO_NETWORK = 0;
    public static final int NETWORK_TYPE_NO_PERMISSION = -1;
    public static final int NETWORK_TYPE_WIFI = 3;
    private static final String TAG = "NetworkUtils";
    public static String WALN_MAC = "wlan0";
    public static final ByteArrayPool sByteArrayPool = new ByteArrayPool(10240);
    private static String sNetworkName;
    private static int sNetworkType;

    public static void detectNetwork(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
            String networkName = NETWORK_NAME_NO_NETWORK;
            if (networkInfo != null && networkInfo.isAvailable()) {
                networkName = networkInfo.getTypeName();
                switch (networkInfo.getType()) {
                    case 0:
                        networkName = networkInfo.getSubtypeName();
                        switch (networkInfo.getSubtype()) {
                            case 1:
                            case 2:
                            case 4:
                            case 7:
                            case 11:
                                sNetworkType = 4;
                                sNetworkName = NETWORK_NAME_2G;
                                break;
                            case 3:
                            case 5:
                            case 6:
                            case 8:
                            case 9:
                            case 10:
                            case 12:
                            case 14:
                            case 15:
                                sNetworkType = 5;
                                sNetworkName = NETWORK_NAME_3G;
                                break;
                            case 13:
                                sNetworkType = 6;
                                sNetworkName = NETWORK_NAME_4G;
                                break;
                            default:
                                if (!NETWORK_NAME_TD_SCDMA.equalsIgnoreCase(networkName) && !NETWORK_NAME_WCDMA.equalsIgnoreCase(networkName) && !NETWORK_NAME_CDMA2000.equalsIgnoreCase(networkName)) {
                                    sNetworkType = 2;
                                    sNetworkName = NETWORK_NAME_MOBILE;
                                    break;
                                }
                                sNetworkType = 5;
                                sNetworkName = NETWORK_NAME_3G;
                                break;
                                break;
                        }
                    case 1:
                        sNetworkType = 3;
                        sNetworkName = NETWORK_NAME_WIFI;
                        break;
                    case 9:
                        sNetworkType = 1;
                        sNetworkName = NETWORK_NAME_ETHERNET;
                        break;
                    default:
                        if (!NETWORK_NAME_PPPOE.equalsIgnoreCase(networkName)) {
                            sNetworkType = 8;
                            sNetworkName = NETWORK_NAME_MORE;
                            break;
                        }
                        sNetworkType = 1;
                        sNetworkName = NETWORK_NAME_ETHERNET;
                        break;
                }
            }
            sNetworkType = 0;
            sNetworkName = NETWORK_NAME_NO_NETWORK;
            LogTool.i(TAG, "detectNetwork. network name(%s), network type(%s), ip(%s)", networkName, Integer.valueOf(sNetworkType), getIP());
        } catch (Exception e) {
            LogTool.e(TAG, "detectNetwork. " + e.toString());
            sNetworkType = -1;
            sNetworkName = NETWORK_NAME_NO_PERMISSION;
            LogTool.i(TAG, "detectNetwork. network name(%s), network type(%s), ip(%s)", sNetworkName, Integer.valueOf(sNetworkType), getIP());
        }
    }

    public static void setNetwork(int networkType, String networkName) {
        sNetworkType = networkType;
        sNetworkName = networkName;
    }

    public static int getNetworkType() {
        return sNetworkType;
    }

    public static String getNetworkName() {
        return sNetworkName;
    }

    public static boolean hasNetwork() {
        return sNetworkType != 0;
    }

    public static boolean isEthernetNetwork() {
        return sNetworkType == 1;
    }

    public static boolean isWifiNetwork() {
        return sNetworkType == 3;
    }

    public static boolean isMobileNetwork() {
        return sNetworkType == 4 || sNetworkType == 5 || sNetworkType == 6 || sNetworkType == 7 || sNetworkType == 2;
    }

    public static boolean unknownNetwork() {
        return sNetworkType == 8;
    }

    public static boolean noPermissionNetwork() {
        return sNetworkType == -1;
    }

    public static void report(String url, String data) {
        Exception e;
        Throwable th;
        LogTool.i(TAG, "report. url(%s), data(%s)", url, data);
        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(data)) {
            HttpURLConnection httpURLConnection = null;
            PrintWriter printWriter = null;
            try {
                httpURLConnection = getHttpURLConnection(url, 10, 10);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.addRequestProperty(HttpRequest.PARAM_CHARSET, "utf-8");
                httpURLConnection.addRequestProperty("connection", Constants.PAGE_FLAG_CLOSE);
                httpURLConnection.addRequestProperty(HttpRequest.HEADER_CONTENT_TYPE, HttpRequest.CONTENT_TYPE_FORM);
                httpURLConnection.setFixedLengthStreamingMode(data.length());
                httpURLConnection.setDoOutput(true);
                PrintWriter printWriter2 = new PrintWriter(httpURLConnection.getOutputStream());
                try {
                    printWriter2.write(data.toString());
                    printWriter2.flush();
                    if (httpURLConnection.getResponseCode() != 200) {
                        LogTool.w(TAG, "report. abnormal response code(%s), url(%s)", Integer.valueOf(httpURLConnection.getResponseCode()), url);
                    }
                    IOUtils.closeSilently(printWriter2);
                    if (httpURLConnection != null) {
                        try {
                            httpURLConnection.disconnect();
                            printWriter = printWriter2;
                            return;
                        } catch (Throwable e2) {
                            LogTool.e(TAG, "report. " + e2.toString());
                        }
                    }
                    printWriter = printWriter2;
                } catch (Exception e3) {
                    e = e3;
                    printWriter = printWriter2;
                    try {
                        LogTool.e(TAG, "report. %s, url(%s)", e.toString(), url);
                        IOUtils.closeSilently(printWriter);
                        if (httpURLConnection != null) {
                            try {
                                httpURLConnection.disconnect();
                            } catch (Throwable e22) {
                                LogTool.e(TAG, "report. " + e22.toString());
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        IOUtils.closeSilently(printWriter);
                        if (httpURLConnection != null) {
                            try {
                                httpURLConnection.disconnect();
                            } catch (Throwable e222) {
                                LogTool.e(TAG, "report. " + e222.toString());
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    printWriter = printWriter2;
                    IOUtils.closeSilently(printWriter);
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                LogTool.e(TAG, "report. %s, url(%s)", e.toString(), url);
                IOUtils.closeSilently(printWriter);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
        }
    }

    public static String doHttpGet(String url) {
        Exception e;
        Throwable th;
        String str = null;
        if (!TextUtils.isEmpty(url)) {
            InputStream inputStream = null;
            BufferedReader bufferedReader = null;
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = getHttpURLConnection(url, 0, 0);
                if (httpURLConnection.getResponseCode() != 200) {
                    LogTool.w(TAG, "doHttpGet. abnormal response code(%s), url(%s)", Integer.valueOf(httpURLConnection.getResponseCode()), url);
                    IOUtils.closeSilently(null);
                    IOUtils.closeSilently(null);
                    if (httpURLConnection != null) {
                        try {
                            httpURLConnection.disconnect();
                        } catch (Throwable e2) {
                            LogTool.e(TAG, "doHttpGet. " + e2.toString());
                        }
                    }
                } else {
                    inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream));
                    try {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(bufferedReader2.readLine());
                        if (stringBuffer.toString().startsWith("{\"errCode\"")) {
                            str = stringBuffer.toString();
                            IOUtils.closeSilently(bufferedReader2);
                            IOUtils.closeSilently(inputStream);
                            if (httpURLConnection != null) {
                                try {
                                    httpURLConnection.disconnect();
                                } catch (Throwable e22) {
                                    LogTool.e(TAG, "doHttpGet. " + e22.toString());
                                }
                            }
                        } else {
                            IOUtils.closeSilently(bufferedReader2);
                            IOUtils.closeSilently(inputStream);
                            if (httpURLConnection != null) {
                                try {
                                    httpURLConnection.disconnect();
                                } catch (Throwable e222) {
                                    LogTool.e(TAG, "doHttpGet. " + e222.toString());
                                }
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        bufferedReader = bufferedReader2;
                        try {
                            LogTool.e(TAG, "doHttpGet. %s, url(%s)", e.toString(), url);
                            IOUtils.closeSilently(bufferedReader);
                            IOUtils.closeSilently(inputStream);
                            if (httpURLConnection != null) {
                                try {
                                    httpURLConnection.disconnect();
                                } catch (Throwable e2222) {
                                    LogTool.e(TAG, "doHttpGet. " + e2222.toString());
                                }
                            }
                            return str;
                        } catch (Throwable th2) {
                            th = th2;
                            IOUtils.closeSilently(bufferedReader);
                            IOUtils.closeSilently(inputStream);
                            if (httpURLConnection != null) {
                                try {
                                    httpURLConnection.disconnect();
                                } catch (Throwable e22222) {
                                    LogTool.e(TAG, "doHttpGet. " + e22222.toString());
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedReader = bufferedReader2;
                        IOUtils.closeSilently(bufferedReader);
                        IOUtils.closeSilently(inputStream);
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        throw th;
                    }
                }
            } catch (Exception e4) {
                e = e4;
                LogTool.e(TAG, "doHttpGet. %s, url(%s)", e.toString(), url);
                IOUtils.closeSilently(bufferedReader);
                IOUtils.closeSilently(inputStream);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return str;
            }
        }
        return str;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String doHttpGet(java.lang.String r19, boolean r20, int r21, int r22, int r23) {
        /*
        r4 = 0;
        r7 = 0;
        r9 = 0;
        r6 = 0;
        r0 = r19;
        r1 = r21;
        r2 = r22;
        r6 = getHttpURLConnection(r0, r1, r2);	 Catch:{ Exception -> 0x018c }
        r11 = r6.getResponseCode();	 Catch:{ Exception -> 0x018c }
        r14 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r11 == r14) goto L_0x005d;
    L_0x0016:
        r14 = "NetworkUtils";
        r15 = "doHttpGet. abnormal response code(%s), url(%s)";
        r16 = 2;
        r0 = r16;
        r0 = new java.lang.Object[r0];	 Catch:{ Exception -> 0x018c }
        r16 = r0;
        r17 = 0;
        r18 = java.lang.Integer.valueOf(r11);	 Catch:{ Exception -> 0x018c }
        r16[r17] = r18;	 Catch:{ Exception -> 0x018c }
        r17 = 1;
        r16[r17] = r19;	 Catch:{ Exception -> 0x018c }
        com.letv.pp.utils.LogTool.w(r14, r15, r16);	 Catch:{ Exception -> 0x018c }
    L_0x0031:
        r14 = sByteArrayPool;
        r14.returnBuf(r4);
        com.letv.pp.utils.IOUtils.closeSilently(r9);
        com.letv.pp.utils.IOUtils.closeSilently(r7);
        if (r6 == 0) goto L_0x0041;
    L_0x003e:
        r6.disconnect();	 Catch:{ Throwable -> 0x0043 }
    L_0x0041:
        r14 = 0;
    L_0x0042:
        return r14;
    L_0x0043:
        r5 = move-exception;
        r14 = "NetworkUtils";
        r15 = new java.lang.StringBuilder;
        r16 = "doHttpGet. ";
        r15.<init>(r16);
        r16 = r5.toString();
        r15 = r15.append(r16);
        r15 = r15.toString();
        com.letv.pp.utils.LogTool.e(r14, r15);
        goto L_0x0041;
    L_0x005d:
        if (r20 == 0) goto L_0x0031;
    L_0x005f:
        r7 = r6.getInputStream();	 Catch:{ Exception -> 0x018c }
        r10 = new com.letv.pp.utils.NetworkUtils$PoolingByteArrayOutputStream;	 Catch:{ Exception -> 0x018c }
        r14 = sByteArrayPool;	 Catch:{ Exception -> 0x018c }
        r10.<init>(r14);	 Catch:{ Exception -> 0x018c }
        r14 = sByteArrayPool;	 Catch:{ Exception -> 0x00f8, all -> 0x0189 }
        r15 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r4 = r14.getBuf(r15);	 Catch:{ Exception -> 0x00f8, all -> 0x0189 }
        r8 = -1;
        r12 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00f8, all -> 0x0189 }
    L_0x0077:
        r8 = r7.read(r4);	 Catch:{ Exception -> 0x00f8, all -> 0x0189 }
        r14 = -1;
        if (r8 != r14) goto L_0x0094;
    L_0x007e:
        r14 = r10.toString();	 Catch:{ Exception -> 0x00f8, all -> 0x0189 }
        r15 = sByteArrayPool;
        r15.returnBuf(r4);
        com.letv.pp.utils.IOUtils.closeSilently(r10);
        com.letv.pp.utils.IOUtils.closeSilently(r7);
        if (r6 == 0) goto L_0x0092;
    L_0x008f:
        r6.disconnect();	 Catch:{ Throwable -> 0x0128 }
    L_0x0092:
        r9 = r10;
        goto L_0x0042;
    L_0x0094:
        if (r23 <= 0) goto L_0x00f3;
    L_0x0096:
        r14 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00f8, all -> 0x0189 }
        r14 = r14 - r12;
        r0 = r23;
        r0 = r0 * 1000;
        r16 = r0;
        r0 = r16;
        r0 = (long) r0;	 Catch:{ Exception -> 0x00f8, all -> 0x0189 }
        r16 = r0;
        r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r14 <= 0) goto L_0x00f3;
    L_0x00aa:
        r14 = "NetworkUtils";
        r15 = "doHttpGet. download timeout(%s s), url(%s)";
        r16 = 2;
        r0 = r16;
        r0 = new java.lang.Object[r0];	 Catch:{ Exception -> 0x00f8, all -> 0x0189 }
        r16 = r0;
        r17 = 0;
        r18 = java.lang.Integer.valueOf(r23);	 Catch:{ Exception -> 0x00f8, all -> 0x0189 }
        r16[r17] = r18;	 Catch:{ Exception -> 0x00f8, all -> 0x0189 }
        r17 = 1;
        r16[r17] = r19;	 Catch:{ Exception -> 0x00f8, all -> 0x0189 }
        com.letv.pp.utils.LogTool.w(r14, r15, r16);	 Catch:{ Exception -> 0x00f8, all -> 0x0189 }
        r14 = sByteArrayPool;
        r14.returnBuf(r4);
        com.letv.pp.utils.IOUtils.closeSilently(r10);
        com.letv.pp.utils.IOUtils.closeSilently(r7);
        if (r6 == 0) goto L_0x00d5;
    L_0x00d2:
        r6.disconnect();	 Catch:{ Throwable -> 0x00d9 }
    L_0x00d5:
        r14 = 0;
        r9 = r10;
        goto L_0x0042;
    L_0x00d9:
        r5 = move-exception;
        r14 = "NetworkUtils";
        r15 = new java.lang.StringBuilder;
        r16 = "doHttpGet. ";
        r15.<init>(r16);
        r16 = r5.toString();
        r15 = r15.append(r16);
        r15 = r15.toString();
        com.letv.pp.utils.LogTool.e(r14, r15);
        goto L_0x00d5;
    L_0x00f3:
        r14 = 0;
        r10.write(r4, r14, r8);	 Catch:{ Exception -> 0x00f8, all -> 0x0189 }
        goto L_0x0077;
    L_0x00f8:
        r5 = move-exception;
        r9 = r10;
    L_0x00fa:
        r14 = "NetworkUtils";
        r15 = "doHttpGet. %s, url(%s)";
        r16 = 2;
        r0 = r16;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x015d }
        r16 = r0;
        r17 = 0;
        r18 = r5.toString();	 Catch:{ all -> 0x015d }
        r16[r17] = r18;	 Catch:{ all -> 0x015d }
        r17 = 1;
        r16[r17] = r19;	 Catch:{ all -> 0x015d }
        com.letv.pp.utils.LogTool.e(r14, r15, r16);	 Catch:{ all -> 0x015d }
        r14 = sByteArrayPool;
        r14.returnBuf(r4);
        com.letv.pp.utils.IOUtils.closeSilently(r9);
        com.letv.pp.utils.IOUtils.closeSilently(r7);
        if (r6 == 0) goto L_0x0125;
    L_0x0122:
        r6.disconnect();	 Catch:{ Throwable -> 0x0143 }
    L_0x0125:
        r14 = 0;
        goto L_0x0042;
    L_0x0128:
        r5 = move-exception;
        r15 = "NetworkUtils";
        r16 = new java.lang.StringBuilder;
        r17 = "doHttpGet. ";
        r16.<init>(r17);
        r17 = r5.toString();
        r16 = r16.append(r17);
        r16 = r16.toString();
        com.letv.pp.utils.LogTool.e(r15, r16);
        goto L_0x0092;
    L_0x0143:
        r5 = move-exception;
        r14 = "NetworkUtils";
        r15 = new java.lang.StringBuilder;
        r16 = "doHttpGet. ";
        r15.<init>(r16);
        r16 = r5.toString();
        r15 = r15.append(r16);
        r15 = r15.toString();
        com.letv.pp.utils.LogTool.e(r14, r15);
        goto L_0x0125;
    L_0x015d:
        r14 = move-exception;
    L_0x015e:
        r15 = sByteArrayPool;
        r15.returnBuf(r4);
        com.letv.pp.utils.IOUtils.closeSilently(r9);
        com.letv.pp.utils.IOUtils.closeSilently(r7);
        if (r6 == 0) goto L_0x016e;
    L_0x016b:
        r6.disconnect();	 Catch:{ Throwable -> 0x016f }
    L_0x016e:
        throw r14;
    L_0x016f:
        r5 = move-exception;
        r15 = "NetworkUtils";
        r16 = new java.lang.StringBuilder;
        r17 = "doHttpGet. ";
        r16.<init>(r17);
        r17 = r5.toString();
        r16 = r16.append(r17);
        r16 = r16.toString();
        com.letv.pp.utils.LogTool.e(r15, r16);
        goto L_0x016e;
    L_0x0189:
        r14 = move-exception;
        r9 = r10;
        goto L_0x015e;
    L_0x018c:
        r5 = move-exception;
        goto L_0x00fa;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.pp.utils.NetworkUtils.doHttpGet(java.lang.String, boolean, int, int, int):java.lang.String");
    }

    public static HttpURLConnection getHttpURLConnection(String url, int connectTimeout, int readTimeout) throws MalformedURLException, IOException {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        HttpURLConnection httpURLConnection;
        if (TextUtils.isEmpty(Proxy.getDefaultHost())) {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        } else if (url.startsWith("http://127.0.0.1")) {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection(java.net.Proxy.NO_PROXY);
        } else {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestProperty("http.proxyHost", Proxy.getDefaultHost());
            httpURLConnection.setRequestProperty("http.proxyPort", String.valueOf(Proxy.getDefaultPort()));
        }
        httpURLConnection.setUseCaches(false);
        if (connectTimeout > 0) {
            httpURLConnection.setConnectTimeout(connectTimeout * 1000);
        }
        if (readTimeout <= 0) {
            return httpURLConnection;
        }
        httpURLConnection.setReadTimeout(readTimeout * 1000);
        return httpURLConnection;
    }

    public static boolean hasConnect(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivity == null) {
                return false;
            }
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info == null || !info.isAvailable()) {
                return false;
            }
            return true;
        } catch (Throwable e) {
            LogTool.e(TAG, "", e);
            return false;
        }
    }

    public static String getWlanMac(String delimiter) {
        try {
            Enumeration<?> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface item = (NetworkInterface) e.nextElement();
                byte[] mac = item.getHardwareAddress();
                if (mac != null && mac.length > 0 && WALN_MAC.equalsIgnoreCase(item.getName())) {
                    return bytesToHexWithDelimiter(mac, delimiter);
                }
            }
        } catch (Throwable e2) {
            LogTool.e(TAG, "", e2);
        }
        return "";
    }

    public static String getEthMac(String delimiter) {
        try {
            Enumeration<?> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface item = (NetworkInterface) e.nextElement();
                byte[] mac = item.getHardwareAddress();
                if (mac != null && mac.length > 0 && ETH_MAC.equalsIgnoreCase(item.getName())) {
                    return bytesToHexWithDelimiter(mac, delimiter);
                }
            }
        } catch (Throwable e2) {
            LogTool.e(TAG, "", e2);
        }
        return "";
    }

    public static Map<String, String> getAllMac(String delimiter) {
        try {
            Map<String, String> macMap = new HashMap();
            Enumeration<?> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface item = (NetworkInterface) e.nextElement();
                byte[] mac = item.getHardwareAddress();
                if (mac != null && mac.length > 0) {
                    if (ETH_MAC.equalsIgnoreCase(item.getName()) || WALN_MAC.equalsIgnoreCase(item.getName())) {
                        macMap.put(item.getName(), bytesToHexWithDelimiter(mac, delimiter));
                    }
                }
            }
            return macMap;
        } catch (Throwable e2) {
            LogTool.e(TAG, "", e2);
            return null;
        }
    }

    public static String getIP() {
        try {
            Enumeration<?> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = ((NetworkInterface) e.nextElement()).getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!(inetAddress.isLoopbackAddress() || inetAddress.isLinkLocalAddress())) {
                        String ip = inetAddress.getHostAddress().toString();
                        String[] nums = ip.split("\\.");
                        if (nums.length == 4 && !"0".equals(nums[1])) {
                            return ip;
                        }
                    }
                }
            }
        } catch (Throwable e2) {
            LogTool.e(TAG, "", e2);
        }
        return "";
    }

    public static String getGatewayIP(Context context) {
        try {
            return Formatter.formatIpAddress(((WifiManager) context.getSystemService(NETWORK_NAME_WIFI)).getDhcpInfo().gateway);
        } catch (Throwable e) {
            LogTool.e(TAG, "", e);
            return "";
        }
    }

    @SuppressLint({"DefaultLocale"})
    private static String bytesToHexWithDelimiter(byte[] b, String delimiter) {
        if (b == null) {
            return null;
        }
        StringBuilder hs = new StringBuilder();
        for (byte b2 : b) {
            if (!StringUtils.isEmpty(delimiter) && hs.length() > 0) {
                hs.append(delimiter);
            }
            String stmp = Integer.toHexString(b2 & 255);
            if (1 == stmp.length()) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }

    private NetworkUtils() {
    }
}
