package com.umeng.analytics.social;

import android.os.Build.VERSION;
import android.text.TextUtils;
import com.umeng.analytics.a;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/* compiled from: UMNetwork */
public abstract class c {
    protected static String a(String str) {
        HttpURLConnection httpURLConnection;
        Exception exception;
        Throwable th;
        HttpURLConnection httpURLConnection2 = null;
        int nextInt = new Random().nextInt(1000);
        try {
            String property = System.getProperty("line.separator");
            if (str.length() <= 1) {
                b.b(a.e, nextInt + ":\tInvalid baseUrl.");
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                return httpURLConnection2;
            }
            HttpURLConnection httpURLConnection3 = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection3.setConnectTimeout(10000);
                httpURLConnection3.setReadTimeout(20000);
                httpURLConnection3.setRequestMethod("GET");
                if (Integer.parseInt(VERSION.SDK) < 8) {
                    System.setProperty("http.keepAlive", "false");
                }
                b.a(a.e, nextInt + ": GET_URL: " + str);
                if (httpURLConnection3.getResponseCode() == 200) {
                    InputStream gZIPInputStream;
                    InputStream inputStream = httpURLConnection3.getInputStream();
                    Object headerField = httpURLConnection3.getHeaderField(HttpRequest.HEADER_CONTENT_ENCODING);
                    if (!TextUtils.isEmpty(headerField) && headerField.equalsIgnoreCase(HttpRequest.ENCODING_GZIP)) {
                        b.a(a.e, nextInt + "  Use GZIPInputStream get data....");
                        gZIPInputStream = new GZIPInputStream(inputStream);
                    } else if (TextUtils.isEmpty(headerField) || !headerField.equalsIgnoreCase("deflate")) {
                        gZIPInputStream = inputStream;
                    } else {
                        b.a(a.e, nextInt + "  Use InflaterInputStream get data....");
                        gZIPInputStream = new InflaterInputStream(inputStream);
                    }
                    String a = a(gZIPInputStream);
                    b.a(a.e, nextInt + ":  response: " + property + a);
                    if (a == null) {
                        if (httpURLConnection3 != null) {
                            httpURLConnection3.disconnect();
                        }
                        return httpURLConnection2;
                    }
                    if (httpURLConnection3 != null) {
                        httpURLConnection3.disconnect();
                    }
                    return a;
                }
                b.a(a.e, nextInt + ":  Failed to get message." + str);
                if (httpURLConnection3 != null) {
                    httpURLConnection3.disconnect();
                }
                return httpURLConnection2;
            } catch (Exception e) {
                Exception exception2 = e;
                httpURLConnection = httpURLConnection3;
                exception = exception2;
                try {
                    b.c(a.e, nextInt + ":\tException,Failed to send message." + str, exception);
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return httpURLConnection2;
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
            httpURLConnection = httpURLConnection2;
            b.c(a.e, nextInt + ":\tException,Failed to send message." + str, exception);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return httpURLConnection2;
        } catch (Throwable th5) {
            th = th5;
            if (httpURLConnection2 != null) {
                httpURLConnection2.disconnect();
            }
            throw th;
        }
    }

    private static String a(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8192);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuilder.append(readLine + "\n");
            } catch (Exception e) {
                stringBuilder = a.e;
                b.b(stringBuilder, "Caught IOException in convertStreamToString()", e);
                return null;
            } finally {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    b.b(a.e, "Caught IOException in convertStreamToString()", e2);
                    return null;
                }
            }
        }
        return stringBuilder.toString();
    }

    protected static String a(String str, String str2) {
        HttpURLConnection httpURLConnection;
        Exception exception;
        Throwable th;
        int nextInt = new Random().nextInt(1000);
        try {
            String property = System.getProperty("line.separator");
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection2.setConnectTimeout(10000);
                httpURLConnection2.setReadTimeout(20000);
                httpURLConnection2.setDoOutput(true);
                httpURLConnection2.setDoInput(true);
                httpURLConnection2.setUseCaches(false);
                httpURLConnection2.setChunkedStreamingMode(0);
                httpURLConnection2.setRequestMethod("POST");
                b.a(a.e, nextInt + ": POST_URL: " + str);
                if (Integer.parseInt(VERSION.SDK) < 8) {
                    System.setProperty("http.keepAlive", "false");
                }
                if (!TextUtils.isEmpty(str2)) {
                    b.a(a.e, nextInt + ": POST_BODY: " + str2);
                    List arrayList = new ArrayList();
                    arrayList.add("data=" + str2);
                    OutputStream outputStream = httpURLConnection2.getOutputStream();
                    outputStream.write(URLEncoder.encode(arrayList.toString()).getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
                if (httpURLConnection2.getResponseCode() == 200) {
                    InputStream inputStream;
                    InputStream inputStream2 = httpURLConnection2.getInputStream();
                    Object headerField = httpURLConnection2.getHeaderField(HttpRequest.HEADER_CONTENT_ENCODING);
                    if (TextUtils.isEmpty(headerField) || !headerField.equalsIgnoreCase(HttpRequest.ENCODING_GZIP)) {
                        inputStream = inputStream2;
                    } else {
                        inputStream = new InflaterInputStream(inputStream2);
                    }
                    String a = a(inputStream);
                    b.a(a.e, nextInt + ":  response: " + property + a);
                    if (a == null) {
                        if (httpURLConnection2 != null) {
                            httpURLConnection2.disconnect();
                        }
                        return null;
                    }
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    return a;
                }
                b.c(a.e, nextInt + ":  Failed to send message." + str);
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                return null;
            } catch (Exception e) {
                Exception exception2 = e;
                httpURLConnection = httpURLConnection2;
                exception = exception2;
                try {
                    b.c(a.e, nextInt + ":\tException,Failed to send message." + str, exception);
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                httpURLConnection = httpURLConnection2;
                th = th3;
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                throw th;
            }
        } catch (Exception e2) {
            exception = e2;
            httpURLConnection = null;
            b.c(a.e, nextInt + ":\tException,Failed to send message." + str, exception);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        } catch (Throwable th4) {
            th = th4;
            httpURLConnection = null;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw th;
        }
    }
}
