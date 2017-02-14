package cn.jpush.android.service;

public class PushProtocol {
    private static final String z;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r1 = 0;
        r2 = "#\u0003zY<\u001dTeB8\u00146`O+\u0011\bp\u0017c\u001a\n|^1BK:";
        r0 = -1;
    L_0x0004:
        r2 = r2.toCharArray();
        r3 = r2.length;
        r4 = 1;
        if (r3 > r4) goto L_0x0060;
    L_0x000c:
        r4 = r1;
    L_0x000d:
        r5 = r2;
        r6 = r4;
        r9 = r3;
        r3 = r2;
        r2 = r9;
    L_0x0012:
        r8 = r3[r4];
        r7 = r6 % 5;
        switch(r7) {
            case 0: goto L_0x003e;
            case 1: goto L_0x0041;
            case 2: goto L_0x0044;
            case 3: goto L_0x0047;
            default: goto L_0x0019;
        };
    L_0x0019:
        r7 = 89;
    L_0x001b:
        r7 = r7 ^ r8;
        r7 = (char) r7;
        r3[r4] = r7;
        r4 = r6 + 1;
        if (r2 != 0) goto L_0x0027;
    L_0x0023:
        r3 = r5;
        r6 = r4;
        r4 = r2;
        goto L_0x0012;
    L_0x0027:
        r3 = r2;
        r2 = r5;
    L_0x0029:
        if (r3 > r4) goto L_0x000d;
    L_0x002b:
        r3 = new java.lang.String;
        r3.<init>(r2);
        r2 = r3.intern();
        switch(r0) {
            case 0: goto L_0x004a;
            default: goto L_0x0037;
        };
    L_0x0037:
        z = r2;
        r0 = "\u001a\n|^1BK:";	 Catch:{ Throwable -> 0x004e }
        r2 = r0;	 Catch:{ Throwable -> 0x004e }
        r0 = r1;	 Catch:{ Throwable -> 0x004e }
        goto L_0x0004;	 Catch:{ Throwable -> 0x004e }
    L_0x003e:
        r7 = 112; // 0x70 float:1.57E-43 double:5.53E-322;	 Catch:{ Throwable -> 0x004e }
        goto L_0x001b;	 Catch:{ Throwable -> 0x004e }
    L_0x0041:
        r7 = 122; // 0x7a float:1.71E-43 double:6.03E-322;	 Catch:{ Throwable -> 0x004e }
        goto L_0x001b;	 Catch:{ Throwable -> 0x004e }
    L_0x0044:
        r7 = 9;	 Catch:{ Throwable -> 0x004e }
        goto L_0x001b;	 Catch:{ Throwable -> 0x004e }
    L_0x0047:
        r7 = 45;	 Catch:{ Throwable -> 0x004e }
        goto L_0x001b;	 Catch:{ Throwable -> 0x004e }
    L_0x004a:
        java.lang.System.loadLibrary(r2);	 Catch:{ Throwable -> 0x004e }
    L_0x004d:
        return;
    L_0x004e:
        r0 = move-exception;
        r1 = new java.lang.StringBuilder;
        r2 = z;
        r1.<init>(r2);
        r1.append(r0);
        cn.jpush.android.util.z.e();
        r0.printStackTrace();
        goto L_0x004d;
    L_0x0060:
        r4 = r1;
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.PushProtocol.<clinit>():void");
    }

    public static native synchronized int Close(long j);

    public static native int GetSdkVersion();

    public static native int HbJPush(long j, long j2, int i, long j3, short s);

    public static native int IMProtocol(long j, byte[] bArr, int i);

    public static native synchronized long InitConn();

    public static native int InitPush(long j, String str, int i);

    public static native int LogPush(long j, long j2, byte[] bArr, long j3, String str, String str2, long j4, short s);

    public static native int MsgResponse(long j, int i, long j2, byte b, long j3, long j4, int i2);

    public static native int RecvPush(long j, byte[] bArr, int i);

    public static native int RegPush(long j, long j2, String str, String str2, String str3, String str4);

    public static native int Stop(long j);

    public static native int TagAlias(long j, long j2, int i, long j3, String str, String str2);
}
