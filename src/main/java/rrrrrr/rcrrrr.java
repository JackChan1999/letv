package rrrrrr;

import io.fabric.sdk.android.services.network.UrlUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

public class rcrrrr {
    private static final Pattern b041E041EОО041E041E;
    public static int b04210421СС04210421 = 2;
    public static int b0421ССС04210421 = 0;
    private static final String bО041EОО041E041E = "UTF-8";
    public static int bС0421СС04210421 = 1;
    public static int bСССС04210421 = 45;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Pattern.quote("+")).append("|").append(Pattern.quote("*")).append("|").append(Pattern.quote("%7E")).append("|").append(Pattern.quote("%2f"));
        b041E041EОО041E041E = Pattern.compile(stringBuilder.toString());
    }

    public rcrrrr() {
        if (((bСССС04210421 + bС0421СС04210421) * bСССС04210421) % b04210421СС04210421 != b0421ССС04210421) {
            bСССС04210421 = 68;
            b0421ССС04210421 = 30;
        }
        try {
        } catch (Exception e) {
            throw e;
        }
    }

    public static int b0421С0421С04210421() {
        return 69;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b044C044Cьь044Cь(java.lang.String r5, java.lang.String r6) {
        /*
        r1 = 1;
        r2 = 0;
        r0 = r5.indexOf(r6);
        r3 = -1;
        if (r0 == r3) goto L_0x0044;
    L_0x0009:
        r0 = r1;
    L_0x000a:
        if (r0 == 0) goto L_0x0046;
    L_0x000c:
        r0 = r5.indexOf(r6);
        r3 = "\"";
        r0 = r5.indexOf(r3, r0);
    L_0x0016:
        switch(r2) {
            case 0: goto L_0x001d;
            case 1: goto L_0x0016;
            default: goto L_0x0019;
        };
    L_0x0019:
        switch(r1) {
            case 0: goto L_0x0016;
            case 1: goto L_0x001d;
            default: goto L_0x001c;
        };
    L_0x001c:
        goto L_0x0019;
    L_0x001d:
        r1 = "\"";
        r2 = r0 + 1;
        r3 = bСССС04210421;
        r4 = bС0421СС04210421;
        r4 = r4 + r3;
        r3 = r3 * r4;
        r4 = b04210421СС04210421;
        r3 = r3 % r4;
        switch(r3) {
            case 0: goto L_0x0039;
            default: goto L_0x002d;
        };
    L_0x002d:
        r3 = b0421С0421С04210421();
        bСССС04210421 = r3;
        r3 = b0421С0421С04210421();
        b0421ССС04210421 = r3;
    L_0x0039:
        r1 = r5.indexOf(r1, r2);
        r0 = r0 + 1;
        r0 = r5.substring(r0, r1);
    L_0x0043:
        return r0;
    L_0x0044:
        r0 = r2;
        goto L_0x000a;
    L_0x0046:
        r0 = 0;
        goto L_0x0043;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rcrrrr.b044C044Cьь044Cь(java.lang.String, java.lang.String):java.lang.String");
    }

    public static String b044Cь044Cь044Cь(String str, boolean z) {
        if (str == null) {
            return null;
        }
        Object encode = URLEncoder.encode(str, "UTF-8");
        Matcher matcher = b041E041EОО041E041E.matcher(encode);
        StringBuffer stringBuffer = new StringBuffer(encode.length());
        while (matcher.find()) {
            String group = matcher.group(0);
            if ("+".equals(group)) {
                group = "%20";
            } else if ("*".equals(group)) {
                group = "%2A";
            } else {
                try {
                    if ("%7E".equals(group)) {
                        group = "~";
                        if (((bСССС04210421 + bС0421СС04210421) * bСССС04210421) % b04210421СС04210421 != b0421ССС04210421) {
                            bСССС04210421 = 0;
                            b0421ССС04210421 = 24;
                        }
                    } else if (z) {
                        if ("%2F".equals(group)) {
                            group = "/";
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    try {
                        e.printStackTrace();
                        return null;
                    } catch (Exception e2) {
                        throw e2;
                    }
                } catch (Exception e22) {
                    throw e22;
                }
            }
            try {
                matcher.appendReplacement(stringBuffer, group);
            } catch (UnsupportedEncodingException e3) {
                try {
                    e3.printStackTrace();
                    return null;
                } catch (Exception e222) {
                    throw e222;
                }
            }
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public static int bСС0421С04210421() {
        return 1;
    }

    public static String bь044Cьь044Cь() {
        try {
            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            if (((bСССС04210421 + bСС0421С04210421()) * bСССС04210421) % b04210421СС04210421 != b0421ССС04210421) {
                bСССС04210421 = b0421С0421С04210421();
                b0421ССС04210421 = 46;
            }
            simpleDateFormat.setTimeZone(timeZone);
            try {
                return simpleDateFormat.format(new Date());
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static String bьь044Cь044Cь(String str, String str2) {
        try {
            byte[] bytes = str.getBytes(UrlUtils.UTF8);
            Mac instance = Mac.getInstance("HmacSHA256");
            instance.init(new SecretKeySpec(str2.getBytes(UrlUtils.UTF8), "HmacSHA256"));
            bytes = instance.doFinal(bytes);
            if (((bСССС04210421 + bС0421СС04210421) * bСССС04210421) % b04210421СС04210421 != b0421ССС04210421) {
                bСССС04210421 = 84;
                b0421ССС04210421 = b0421С0421С04210421();
            }
            return new String(Hex.encodeHex(bytes));
        } catch (Exception e) {
            return "null";
        }
    }
}
