package rrrrrr;

public class crrrcr {
    private static final String b041B041B041B041BЛ041B = "Internal Server Error";
    public static int b042704270427ЧЧ0427 = 1;
    public static int b0427Ч0427ЧЧ0427 = 39;
    private static final String bЛ041B041B041BЛ041B = "TokenVendingMachineService";
    public static int bЧ04270427ЧЧ0427 = 0;
    public static int bЧЧЧ0427Ч0427 = 2;

    public static int b0427ЧЧ0427Ч0427() {
        return 19;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getResponse(java.net.HttpURLConnection r5) {
        /*
        r2 = new java.io.ByteArrayOutputStream;	 Catch:{ Exception -> 0x006d }
        r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r2.<init>(r0);	 Catch:{ Exception -> 0x006d }
        r0 = b0427Ч0427ЧЧ0427;
        r1 = b042704270427ЧЧ0427;
        r0 = r0 + r1;
        r1 = b0427Ч0427ЧЧ0427;
        r0 = r0 * r1;
        r1 = bЧЧЧ0427Ч0427;
        r0 = r0 % r1;
        r1 = bЧ04270427ЧЧ0427;
        if (r0 == r1) goto L_0x0022;
    L_0x0016:
        r0 = b0427ЧЧ0427Ч0427();
        b0427Ч0427ЧЧ0427 = r0;
        r0 = b0427ЧЧ0427Ч0427();
        bЧ04270427ЧЧ0427 = r0;
    L_0x0022:
        r1 = new java.io.ByteArrayOutputStream;	 Catch:{ Exception -> 0x008d, all -> 0x006f }
        r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r1.<init>(r0);	 Catch:{ Exception -> 0x008d, all -> 0x006f }
        r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r2 = new byte[r0];	 Catch:{ Exception -> 0x0045 }
        r0 = r5.getResponseCode();	 Catch:{ Exception -> 0x0045 }
        r3 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r0 != r3) goto L_0x0055;
    L_0x0035:
        r0 = r5.getInputStream();	 Catch:{ Exception -> 0x0045 }
    L_0x0039:
        r3 = r0.read(r2);	 Catch:{ Exception -> 0x0045 }
        r4 = -1;
        if (r3 == r4) goto L_0x005a;
    L_0x0040:
        r4 = 0;
        r1.write(r2, r4, r3);	 Catch:{ Exception -> 0x0045 }
        goto L_0x0039;
    L_0x0045:
        r0 = move-exception;
    L_0x0046:
        r2 = "TokenVendingMachineService";
        r0 = r0.getMessage();	 Catch:{ all -> 0x0090 }
        com.immersion.Log.d(r2, r0);	 Catch:{ all -> 0x0090 }
        r0 = "Internal Server Error";
        r1.close();	 Catch:{ Exception -> 0x0077 }
    L_0x0054:
        return r0;
    L_0x0055:
        r0 = r5.getErrorStream();	 Catch:{ Exception -> 0x0045 }
        goto L_0x0039;
    L_0x005a:
        r0 = r1.toString();	 Catch:{ Exception -> 0x0045 }
        r1.close();	 Catch:{ Exception -> 0x0062 }
        goto L_0x0054;
    L_0x0062:
        r1 = move-exception;
        r2 = "TokenVendingMachineService";
        r1 = r1.getMessage();	 Catch:{ Exception -> 0x006d }
        com.immersion.Log.d(r2, r1);	 Catch:{ Exception -> 0x006d }
        goto L_0x0054;
    L_0x006d:
        r0 = move-exception;
        throw r0;
    L_0x006f:
        r0 = move-exception;
        r1 = r2;
    L_0x0071:
        r1.close();	 Catch:{ Exception -> 0x0082 }
    L_0x0074:
        throw r0;	 Catch:{ Exception -> 0x0075 }
    L_0x0075:
        r0 = move-exception;
        throw r0;
    L_0x0077:
        r1 = move-exception;
        r2 = "TokenVendingMachineService";
        r1 = r1.getMessage();	 Catch:{ Exception -> 0x0075 }
        com.immersion.Log.d(r2, r1);	 Catch:{ Exception -> 0x0075 }
        goto L_0x0054;
    L_0x0082:
        r1 = move-exception;
        r2 = "TokenVendingMachineService";
        r1 = r1.getMessage();	 Catch:{ Exception -> 0x0075 }
        com.immersion.Log.d(r2, r1);	 Catch:{ Exception -> 0x0075 }
        goto L_0x0074;
    L_0x008d:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0046;
    L_0x0090:
        r0 = move-exception;
        goto L_0x0071;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.crrrcr.getResponse(java.net.HttpURLConnection):java.lang.String");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static rrrrrr.rrcrcc sendRequest(rrrrrr.rccrcc r6, rrrrrr.rccrcr r7, int r8) {
        /*
        r2 = 0;
        r0 = 1;
        r3 = 0;
    L_0x0003:
        switch(r0) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000a;
            default: goto L_0x0006;
        };
    L_0x0006:
        switch(r0) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000a;
            default: goto L_0x0009;
        };
    L_0x0009:
        goto L_0x0006;
    L_0x000a:
        r1 = r6.buildRequestUrl();	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x00df, Exception -> 0x0139 }
        r0 = "TokenVendingMachineService";
        r4 = new java.lang.StringBuilder;	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r4.<init>();	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r5 = "Sending Request : [";
        r4 = r4.append(r5);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r4 = r4.append(r1);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r5 = "]";
        r4 = r4.append(r5);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r4 = r4.toString();	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        com.immersion.Log.d(r0, r4);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r0 = new java.net.URL;	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r0.<init>(r1);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r0 = r0.openConnection();	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r0.setConnectTimeout(r8);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r3 = r0.getResponseCode();	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r2 = getResponse(r0);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r0 = "TokenVendingMachineService";
        r4 = new java.lang.StringBuilder;	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r4.<init>();	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r5 = "Response ";
        r4 = r4.append(r5);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r4 = r4.append(r3);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r5 = ": [";
        r4 = r4.append(r5);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r4 = r4.append(r2);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r5 = "]";
        r4 = r4.append(r5);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r4 = r4.toString();	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        com.immersion.Log.d(r0, r4);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
        r0 = r7.handleResponse(r3, r2);	 Catch:{ SocketTimeoutException -> 0x0090, IOException -> 0x0145, Exception -> 0x0139 }
    L_0x006e:
        return r0;
    L_0x006f:
        r0 = r0.getMessage();
    L_0x0073:
        r3 = "TokenVendingMachineService";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Exception: ";
        r4 = r4.append(r5);
        r0 = r4.append(r0);
        r0 = r0.toString();
        com.immersion.Log.d(r3, r0);
        r0 = r7.handleResponse(r1, r2);
        goto L_0x006e;
    L_0x0090:
        r0 = move-exception;
        r1 = r0.getMessage();
        if (r1 != 0) goto L_0x00da;
    L_0x0097:
        r0 = "Null Message";
    L_0x0099:
        r1 = "TokenVendingMachineService";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "SocketTimeoutException: ";
        r2 = r2.append(r3);
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.immersion.Log.d(r1, r0);
        r0 = 408; // 0x198 float:5.72E-43 double:2.016E-321;
        r1 = "Request Timed Out";
        r0 = r7.handleResponse(r0, r1);
        goto L_0x006e;
    L_0x00ba:
        r0 = 404; // 0x194 float:5.66E-43 double:1.996E-321;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Unable to reach resource at [";
        r2 = r2.append(r3);
        r1 = r2.append(r1);
        r2 = "]";
        r1 = r1.append(r2);
        r1 = r1.toString();
        r0 = r7.handleResponse(r0, r1);
        goto L_0x006e;
    L_0x00da:
        r0 = r0.getMessage();
        goto L_0x0099;
    L_0x00df:
        r0 = move-exception;
        r1 = r2;
    L_0x00e1:
        r2 = r0.getMessage();
        if (r2 != 0) goto L_0x0134;
    L_0x00e7:
        r0 = "Null Message";
    L_0x00e9:
        r2 = "TokenVendingMachineService";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "IOException: ";
        r3 = r3.append(r4);
        r3 = r3.append(r0);
        r3 = r3.toString();
        com.immersion.Log.d(r2, r3);
        r2 = "Received authentication challenge is null";
        r2 = r0.equals(r2);
        if (r2 != 0) goto L_0x012a;
    L_0x0109:
        r2 = "No authentication challenges found";
        r0 = r0.equals(r2);
        if (r0 == 0) goto L_0x00ba;
    L_0x0111:
        r0 = b0427Ч0427ЧЧ0427;
        r1 = b042704270427ЧЧ0427;
        r0 = r0 + r1;
        r1 = b0427Ч0427ЧЧ0427;
        r0 = r0 * r1;
        r1 = bЧЧЧ0427Ч0427;
        r0 = r0 % r1;
        r1 = bЧ04270427ЧЧ0427;
        if (r0 == r1) goto L_0x012a;
    L_0x0120:
        r0 = 70;
        b0427Ч0427ЧЧ0427 = r0;
        r0 = b0427ЧЧ0427Ч0427();
        bЧ04270427ЧЧ0427 = r0;
    L_0x012a:
        r0 = 401; // 0x191 float:5.62E-43 double:1.98E-321;
        r1 = "Unauthorized token request";
        r0 = r7.handleResponse(r0, r1);
        goto L_0x006e;
    L_0x0134:
        r0 = r0.getMessage();
        goto L_0x00e9;
    L_0x0139:
        r0 = move-exception;
        r1 = r3;
        r3 = r0.getMessage();
        if (r3 != 0) goto L_0x006f;
    L_0x0141:
        r0 = "Null Message";
        goto L_0x0073;
    L_0x0145:
        r0 = move-exception;
        goto L_0x00e1;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.crrrcr.sendRequest(rrrrrr.rccrcc, rrrrrr.rccrcr, int):rrrrrr.rrcrcc");
    }
}
