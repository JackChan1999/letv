package rrrrrr;

import com.immersion.Log;
import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.letv.pp.utils.NetworkUtils;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class rcccrr {
    private static final String b041B041B041BЛЛ041B = "TableName";
    public static final String b041B041BЛЛЛ041B = "us-west-2";
    private static final String b041BЛ041BЛЛ041B = "Item_Name";
    private static final String b041BЛЛЛЛ041B = "HttpRequest";
    public static int b04270427ЧЧ0427Ч = 1;
    public static int b0427Ч0427Ч0427Ч = 26;
    private static final String bЛ041B041BЛЛ041B = "Item";
    private static final String bЛ041BЛЛЛ041B = "https://dynamodb.us-west-2.amazonaws.com";
    public static final String bЛЛ041BЛЛ041B = "dynamodb";
    private static final String bЛЛЛ041BЛ041B = "HMSDK_Analytics_Mobile";
    public static int bЧ0427ЧЧ0427Ч = 0;
    public static int bЧЧ0427Ч0427Ч = 2;
    private Header[] b041BЛЛ041BЛ041B;
    private ArrayList bЛ041BЛ041BЛ041B;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public rcccrr() {
        /*
        r2 = this;
        r0 = 0;
        r2.<init>();
    L_0x0004:
        r0.length();	 Catch:{ Exception -> 0x0008 }
        goto L_0x0004;
    L_0x0008:
        r0 = move-exception;
        r0 = new java.util.ArrayList;
    L_0x000b:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x0014;
            case 1: goto L_0x000b;
            default: goto L_0x000f;
        };
    L_0x000f:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x000b;
            case 1: goto L_0x0014;
            default: goto L_0x0013;
        };
    L_0x0013:
        goto L_0x000f;
    L_0x0014:
        r0.<init>();
        r2.bЛ041BЛ041BЛ041B = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rcccrr.<init>():void");
    }

    public static int b0427ЧЧЧ0427Ч() {
        return 16;
    }

    private boolean b044C044C044Cьь044C(String str, String str2, String str3, String str4) throws ClientProtocolException, IOException {
        this.b041BЛЛ041BЛ041B = b044Cьь044Cь044C(str3);
        HttpPost httpPost = new HttpPost(bЛ041BЛЛЛ041B);
        String generateSignature = new crrcrc().generateSignature(str2, this.b041BЛЛ041BЛ041B, str4);
        httpPost.setEntity(new StringEntity(str4, "UTF-8"));
        httpPost.setHeaders(this.b041BЛЛ041BЛ041B);
        Object bььь044Cь044C = bььь044Cь044C(str, generateSignature, httpPost);
        HttpResponse execute = new DefaultHttpClient().execute(bььь044Cь044C);
        Log.d(b041BЛЛЛЛ041B, "All POST request headers:");
        for (Header header : bььь044Cь044C.getAllHeaders()) {
            Log.d(b041BЛЛЛЛ041B, header.getName() + NetworkUtils.DELIMITER_COLON + header.getValue());
        }
        Log.d(b041BЛЛЛЛ041B, "HTTP Request body: " + str4);
        String str5 = b041BЛЛЛЛ041B;
        StringBuilder append = new StringBuilder().append("HTTP Response: ");
        StatusLine statusLine = execute.getStatusLine();
        int b0427ЧЧЧ0427Ч = b0427ЧЧЧ0427Ч();
        switch ((b0427ЧЧЧ0427Ч * (b04270427ЧЧ0427Ч + b0427ЧЧЧ0427Ч)) % bЧЧ0427Ч0427Ч) {
            case 0:
                break;
            default:
                b0427Ч0427Ч0427Ч = b0427ЧЧЧ0427Ч();
                bЧ0427ЧЧ0427Ч = b0427ЧЧЧ0427Ч();
                break;
        }
        Log.d(str5, append.append(statusLine.toString()).toString());
        Log.d(b041BЛЛЛЛ041B, "HTTP Response: " + EntityUtils.toString(execute.getEntity()));
        return execute.getStatusLine().getStatusCode() == 200;
    }

    private Header[] b044Cьь044Cь044C(String str) {
        try {
            Header[] headerArr = new Header[5];
            try {
                headerArr[0] = new BasicHeader("content-type", "application/x-amz-json-1.0");
                headerArr[1] = new BasicHeader("host", "dynamodb.us-west-2.amazonaws.com");
                if (((b0427Ч0427Ч0427Ч + b04270427ЧЧ0427Ч) * b0427Ч0427Ч0427Ч) % bЧЧ0427Ч0427Ч != bЧ0427ЧЧ0427Ч) {
                    b0427Ч0427Ч0427Ч = 86;
                    bЧ0427ЧЧ0427Ч = b0427ЧЧЧ0427Ч();
                }
                headerArr[2] = new BasicHeader("x-amz-date", getYYYYMMDDTHHMMSSZ());
                headerArr[3] = new BasicHeader("x-amz-security-token", str);
                headerArr[4] = new BasicHeader("x-amz-target", "DynamoDB_20120810.PutItem");
                return headerArr;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static int bЧЧЧ04270427Ч() {
        return 0;
    }

    private JSONObject bь044C044Cьь044C() throws JSONException {
        String string;
        String str = "unique_device_id";
        String str2 = "";
        String str3 = getYYYYMMDD() + " " + getHHMMssSSS();
        Iterator it = this.bЛ041BЛ041BЛ041B.iterator();
        while (it.hasNext()) {
            AnalyticsDataCollector analyticsDataCollector = (AnalyticsDataCollector) it.next();
            if (analyticsDataCollector.getColumnName().equals(str)) {
                string = analyticsDataCollector.getData().getString("S");
                break;
            }
        }
        string = str2;
        StringBuilder append = new StringBuilder().append(string).append(NetworkUtils.DELIMITER_COLON).append(str3);
        if (((b0427Ч0427Ч0427Ч + b04270427ЧЧ0427Ч) * b0427Ч0427Ч0427Ч) % bЧЧ0427Ч0427Ч != bЧ0427ЧЧ0427Ч) {
            b0427Ч0427Ч0427Ч = b0427ЧЧЧ0427Ч();
            bЧ0427ЧЧ0427Ч = 84;
        }
        return new JSONObject().put("S", append.toString());
    }

    private HttpPost bььь044Cь044C(String str, String str2, HttpPost httpPost) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AWS4-HMAC-SHA256 ").append("Credential=").append(str).append("/").append(getYYYYMMDD()).append("/").append(b041B041BЛЛЛ041B).append("/").append(bЛЛ041BЛЛ041B).append("/").append("aws4_request").append(",");
            stringBuilder.append("SignedHeaders=").append(new crrcrc().getSignedHeaders(this.b041BЛЛ041BЛ041B)).append(",");
            try {
                stringBuilder.append("Signature=").append(str2);
                String str3 = b041BЛЛЛЛ041B;
                StringBuilder stringBuilder2 = new StringBuilder();
                String str4 = "POST Authorization String:\n";
                if (((b0427Ч0427Ч0427Ч + b04270427ЧЧ0427Ч) * b0427Ч0427Ч0427Ч) % bЧЧ0427Ч0427Ч != bЧ0427ЧЧ0427Ч) {
                    b0427Ч0427Ч0427Ч = b0427ЧЧЧ0427Ч();
                    bЧ0427ЧЧ0427Ч = b0427ЧЧЧ0427Ч();
                }
                Log.d(str3, stringBuilder2.append(str4).append(stringBuilder.toString()).toString());
                httpPost.addHeader(new BasicHeader(HttpRequest.HEADER_AUTHORIZATION, stringBuilder.toString()));
                return httpPost;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static String getHHMMssSSS() {
        try {
            String str = "HH:mm:ss.SSS";
            String str2 = "UTC";
            Date date = new Date(System.currentTimeMillis());
            int b0427ЧЧЧ0427Ч = b0427ЧЧЧ0427Ч();
            switch ((b0427ЧЧЧ0427Ч * (b04270427ЧЧ0427Ч + b0427ЧЧЧ0427Ч)) % bЧЧ0427Ч0427Ч) {
                case 0:
                    break;
                default:
                    b0427Ч0427Ч0427Ч = b0427ЧЧЧ0427Ч();
                    bЧ0427ЧЧ0427Ч = 9;
                    break;
            }
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone(str2));
                return simpleDateFormat.format(date);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getYYYYMMDD() {
        /*
        r4 = 1;
        r0 = b0427ЧЧЧ0427Ч();
        r1 = b04270427ЧЧ0427Ч;
        r0 = r0 + r1;
        r1 = b0427ЧЧЧ0427Ч();
        r0 = r0 * r1;
        r1 = bЧЧ0427Ч0427Ч;
        r0 = r0 % r1;
        r1 = bЧЧЧ04270427Ч();
        if (r0 == r1) goto L_0x0020;
    L_0x0016:
        r0 = b0427ЧЧЧ0427Ч();
        b0427Ч0427Ч0427Ч = r0;
        r0 = 65;
        bЧ0427ЧЧ0427Ч = r0;
    L_0x0020:
        r0 = new java.util.Date;
        r2 = java.lang.System.currentTimeMillis();
        r0.<init>(r2);
        r1 = new java.text.SimpleDateFormat;
        r2 = "yyyyMMdd";
        r1.<init>(r2);
        r2 = "gmt";
        r2 = java.util.TimeZone.getTimeZone(r2);
        r1.setTimeZone(r2);
        r0 = r1.format(r0);
    L_0x003e:
        switch(r4) {
            case 0: goto L_0x003e;
            case 1: goto L_0x0045;
            default: goto L_0x0041;
        };
    L_0x0041:
        switch(r4) {
            case 0: goto L_0x003e;
            case 1: goto L_0x0045;
            default: goto L_0x0044;
        };
    L_0x0044:
        goto L_0x0041;
    L_0x0045:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rcccrr.getYYYYMMDD():java.lang.String");
    }

    public static String getYYYYMMDDTHHMMSSZ() {
        try {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
            if (((b0427Ч0427Ч0427Ч + b04270427ЧЧ0427Ч) * b0427Ч0427Ч0427Ч) % bЧЧ0427Ч0427Ч != bЧ0427ЧЧ0427Ч) {
                b0427Ч0427Ч0427Ч = 23;
                bЧ0427ЧЧ0427Ч = b0427ЧЧЧ0427Ч();
            }
            try {
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("gmt"));
                return simpleDateFormat.format(date);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public void appendData(AnalyticsDataCollector analyticsDataCollector) {
        while (true) {
            try {
                int[] iArr = new int[-1];
            } catch (Exception e) {
                this.bЛ041BЛ041BЛ041B.add(analyticsDataCollector);
                return;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.json.JSONObject constructJsonObjectContainingAllDataPairs() throws org.json.JSONException {
        /*
        r5 = this;
        r4 = 1;
        r1 = new org.json.JSONObject;
        r1.<init>();
        r0 = r5.bЛ041BЛ041BЛ041B;
        r2 = r0.iterator();
    L_0x000c:
        r0 = r2.hasNext();
        if (r0 == 0) goto L_0x0024;
    L_0x0012:
        r0 = r2.next();
        r0 = (com.immersion.aws.analytics.AnalyticsDataCollector) r0;
        r3 = r0.getColumnName();
        r0 = r0.getData();
        r1.put(r3, r0);
        goto L_0x000c;
    L_0x0024:
        r0 = b0427Ч0427Ч0427Ч;
        r2 = b04270427ЧЧ0427Ч;
        r2 = r2 + r0;
        r0 = r0 * r2;
        r2 = bЧЧ0427Ч0427Ч;
        r0 = r0 % r2;
        switch(r0) {
            case 0: goto L_0x0038;
            default: goto L_0x0030;
        };
    L_0x0030:
        r0 = 86;
        b0427Ч0427Ч0427Ч = r0;
        r0 = 60;
        bЧ0427ЧЧ0427Ч = r0;
    L_0x0038:
        r0 = "Item_Name";
        r2 = r5.bь044C044Cьь044C();
        r1.put(r0, r2);
        r0 = new org.json.JSONObject;
        r0.<init>();
        r2 = "TableName";
        r3 = "HMSDK_Analytics_Mobile";
        r0.put(r2, r3);
        r2 = "Item";
    L_0x004f:
        switch(r4) {
            case 0: goto L_0x004f;
            case 1: goto L_0x0056;
            default: goto L_0x0052;
        };
    L_0x0052:
        switch(r4) {
            case 0: goto L_0x004f;
            case 1: goto L_0x0056;
            default: goto L_0x0055;
        };
    L_0x0055:
        goto L_0x0052;
    L_0x0056:
        r0.put(r2, r1);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rcccrr.constructJsonObjectContainingAllDataPairs():org.json.JSONObject");
    }

    public JSONObject getJsonData() throws JSONException {
        if (((b0427ЧЧЧ0427Ч() + b04270427ЧЧ0427Ч) * b0427ЧЧЧ0427Ч()) % bЧЧ0427Ч0427Ч != bЧ0427ЧЧ0427Ч) {
            bЧ0427ЧЧ0427Ч = b0427ЧЧЧ0427Ч();
        }
        try {
            return constructJsonObjectContainingAllDataPairs();
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean sendHttpRequest(String str, String str2, String str3) {
        try {
            return b044C044C044Cьь044C(str, str2, str3, constructJsonObjectContainingAllDataPairs().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendHttpRequestFromCache(String str, String str2, String str3, String str4) {
        try {
            return b044C044C044Cьь044C(str, str2, str3, str4);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
