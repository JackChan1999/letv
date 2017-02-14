package rrrrrr;

public class crrrrr extends rccrcc {
    public static int b04210421С0421С0421 = 88;
    public static int b0421С04210421С0421 = 1;
    public static int bС042104210421С0421 = 2;
    public static int bСС04210421С0421;
    private final String b041E041E041E041EО041E;
    private final String b041EО041E041EО041E;
    private final String b041EООО041E041E;
    private final String bО041E041E041EО041E;
    private final String bОО041E041EО041E;
    private final boolean bОООО041E041E;

    public crrrrr(String str, boolean z, String str2, String str3, String str4) {
        int i = 3;
        String str5 = null;
        this.bОО041E041EО041E = str;
        this.bОООО041E041E = z;
        this.b041E041E041E041EО041E = str2;
        this.b041EО041E041EО041E = str3;
        this.bО041E041E041EО041E = str4;
        while (true) {
            try {
                str5.length();
            } catch (Exception e) {
                while (true) {
                    try {
                        i /= 0;
                    } catch (Exception e2) {
                        while (true) {
                            try {
                                str5.length();
                            } catch (Exception e3) {
                                this.b041EООО041E041E = computeDecryptionKey();
                                while (true) {
                                    switch (null) {
                                        case null:
                                            return;
                                        case 1:
                                            break;
                                        default:
                                            while (true) {
                                                switch (null) {
                                                    case null:
                                                        return;
                                                    case 1:
                                                        break;
                                                    default:
                                                }
                                            }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static int b0421042104210421С0421() {
        return 60;
    }

    public String buildRequestUrl() {
        StringBuilder stringBuilder = new StringBuilder(this.bОООО041E041E ? "https://" : "http://");
        stringBuilder.append(this.bОО041E041EО041E);
        stringBuilder.append("/");
        String bь044Cьь044Cь = rcrrrr.bь044Cьь044Cь();
        String bьь044Cь044Cь = rcrrrr.bьь044Cь044Cь(bь044Cьь044Cь, this.b041EООО041E041E);
        stringBuilder.append("login");
        stringBuilder.append("?username=" + rcrrrr.b044Cь044Cь044Cь(this.b041EО041E041EО041E, false));
        stringBuilder.append("&timestamp=" + rcrrrr.b044Cь044Cь044Cь(bь044Cьь044Cь, false));
        stringBuilder.append("&signature=" + rcrrrr.b044Cь044Cь044Cь(bьь044Cь044Cь, false));
        return stringBuilder.toString();
    }

    public String computeDecryptionKey() {
        try {
            String str = this.b041EО041E041EО041E + ccccrr.getAppName();
            if (((b0421042104210421С0421() + b0421С04210421С0421) * b0421042104210421С0421()) % bС042104210421С0421 != bСС04210421С0421) {
                b04210421С0421С0421 = b0421042104210421С0421();
                bСС04210421С0421 = 8;
            }
            return rcrrrr.bьь044Cь044Cь(str, this.bО041E041E041EО041E);
        } catch (Exception e) {
            return null;
        }
    }

    public String getDecryptionKey() {
        if (((b04210421С0421С0421 + b0421С04210421С0421) * b04210421С0421С0421) % bС042104210421С0421 != bСС04210421С0421) {
            b04210421С0421С0421 = 83;
            bСС04210421С0421 = 42;
        }
        try {
            return this.b041EООО041E041E;
        } catch (Exception e) {
            throw e;
        }
    }
}
