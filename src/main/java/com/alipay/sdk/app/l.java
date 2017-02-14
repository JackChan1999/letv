package com.alipay.sdk.app;

public final class l {
    static String a;

    public static String a() {
        m a = m.a(m.CANCELED.a());
        return a(a.a(), a.b(), "");
    }

    public static String a(int i, String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("resultStatus={").append(i).append("};memo={").append(str).append("};result={").append(str2).append("}");
        return stringBuilder.toString();
    }

    private static void a(String str) {
        a = str;
    }

    public static String b() {
        m a = m.a(m.PARAMS_ERROR.a());
        return a(a.a(), a.b(), "");
    }

    private static String c() {
        return a;
    }
}
