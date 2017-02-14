package cn.jpush.android.util;

import android.content.Context;
import cn.jpush.android.a;
import cn.jpush.android.data.e;
import com.letv.core.constant.PlayConstant.LiveType;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class t {
    private static final String[] z;
    protected int a = 0;
    protected boolean b = false;
    private int c = 2;
    private int d = 0;
    private long e = 0;
    private long f = 0;
    private long g = 0;
    private long h = 0;
    private Context i;
    private ArrayList<e> j = new ArrayList();

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 20;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "RdI\u00120_";
        r0 = -1;
        r4 = r3;
    L_0x0009:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002e;
    L_0x0012:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0017:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0105;
            case 1: goto L_0x0109;
            case 2: goto L_0x010d;
            case 3: goto L_0x0111;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 85;
    L_0x0020:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002c;
    L_0x0028:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0017;
    L_0x002c:
        r5 = r1;
        r1 = r7;
    L_0x002e:
        if (r5 > r6) goto L_0x0012;
    L_0x0030:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0044;
            case 1: goto L_0x004c;
            case 2: goto L_0x0054;
            case 3: goto L_0x005c;
            case 4: goto L_0x0064;
            case 5: goto L_0x006c;
            case 6: goto L_0x0075;
            case 7: goto L_0x007f;
            case 8: goto L_0x008a;
            case 9: goto L_0x0096;
            case 10: goto L_0x00a2;
            case 11: goto L_0x00ae;
            case 12: goto L_0x00ba;
            case 13: goto L_0x00c5;
            case 14: goto L_0x00d1;
            case 15: goto L_0x00dd;
            case 16: goto L_0x00e8;
            case 17: goto L_0x00f4;
            case 18: goto L_0x0100;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "ErW\u0003";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "XN\u000b0";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "[{R\u0015=ngH\u00012Ty";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "EbJ\u0003";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "]nQ\u00039B";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "AjD\r0E";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "主抮樆彩ｏ";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0075:
        r3[r2] = r1;
        r2 = 8;
        r1 = "弱姀规柶{\u001f%";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007f:
        r3[r2] = r1;
        r2 = 9;
        r1 = "{GH\u00012Tyu\u0003%^yS.0]{B\u0014";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x008a:
        r3[r2] = r1;
        r2 = 10;
        r1 = "旔応筮绁ｏ";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0096:
        r3[r2] = r1;
        r2 = 11;
        r1 = "角枛外赃ｏ";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x00a2:
        r3[r2] = r1;
        r2 = 12;
        r1 = "角枛户勹";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00ae:
        r3[r2] = r1;
        r2 = 13;
        r1 = "主抮呏杹ｏ";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00ba:
        r3[r2] = r1;
        r2 = 14;
        r1 = "AnU\u000f:U";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c5:
        r3[r2] = r1;
        r2 = 15;
        r1 = "黩讯匢奁屚Ｋ:\u0017Ta\u0001";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00d1:
        r3[r2] = r1;
        r2 = 16;
        r1 = "攇刻未勇乞叠挌仃ｼ";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00dd:
        r3[r2] = r1;
        r2 = 17;
        r1 = "\\dC\u0003";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e8:
        r3[r2] = r1;
        r2 = 18;
        r1 = "旔応夀屩ｏ";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00f4:
        r3[r2] = r1;
        r2 = 19;
        r1 = "剘佒旑閒ｏ";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x0100:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0105:
        r9 = 49;
        goto L_0x0020;
    L_0x0109:
        r9 = 11;
        goto L_0x0020;
    L_0x010d:
        r9 = 39;
        goto L_0x0020;
    L_0x0111:
        r9 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.t.<clinit>():void");
    }

    protected t() {
    }

    private void a(String str) {
        int length = str.length();
        while (length > 0) {
            length--;
            switch (str.charAt(length)) {
                case 'd':
                    this.a |= 2;
                    break;
                case 'e':
                    this.a |= 16;
                    break;
                case 'i':
                    this.a |= 4;
                    break;
                case LiveType.PLAY_LIVE_HK_MUSIC /*118*/:
                    this.a |= 1;
                    break;
                case 'w':
                    this.a |= 8;
                    break;
                default:
                    break;
            }
        }
    }

    private void b() {
        JSONObject d = d();
        if (d != null) {
            z.b(z[9], d.toString());
            ac.b(this.i, d);
        }
    }

    private void c() {
        this.b = false;
        this.d = 0;
        this.h = 0;
        this.e = 0;
        this.f = 0;
        this.a = 0;
        this.g = 0;
        this.c = 2;
        this.i = null;
        this.j.clear();
    }

    private JSONObject d() {
        if (this.j == null) {
            return null;
        }
        int size = this.j.size();
        if (size <= 0) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < size; i++) {
            jSONArray.put(((e) this.j.get(i)).b());
        }
        if (jSONArray.length() <= 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(z[0], jSONArray);
            jSONObject.put(z[1], z[3]);
            jSONObject.put(z[2], a.j());
            return jSONObject;
        } catch (JSONException e) {
            return null;
        }
    }

    protected final void a() {
        if (!this.b) {
            return;
        }
        if (System.currentTimeMillis() - this.g >= this.h) {
            b();
            c();
        } else if (this.c == 1 && System.currentTimeMillis() - this.f >= this.e) {
            synchronized (this.j) {
                b();
                this.j.clear();
                this.d = 0;
                this.f = System.currentTimeMillis();
            }
        }
    }

    protected final void a(Context context, String str) {
        z.b(z[9], new StringBuilder(z[16]).append(str).toString());
        z.b(z[9], z[8]);
        try {
            if (this.b) {
                this.b = false;
                b();
                c();
            }
            this.i = context;
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(z[17]);
            String string2 = jSONObject.getString(z[5]);
            if (string != null) {
                if (string.equals(z[6])) {
                    this.c = 2;
                } else if (string.equals(z[14])) {
                    this.c = 1;
                }
            }
            a(string2);
            this.h = jSONObject.getLong(z[4]) * 1000;
            z.b(z[9], new StringBuilder(z[7]).append(string).toString());
            z.b(z[9], new StringBuilder(z[10]).append(string2).toString());
            if (this.c == 1) {
                this.e = jSONObject.getLong(z[14]) * 1000;
                this.f = System.currentTimeMillis();
                z.b(z[9], new StringBuilder(z[13]).append(this.e / 1000).append("s").toString());
                if (this.h < 300000) {
                    this.c = 2;
                }
            } else {
                z.b(z[9], z[15]);
            }
            this.g = System.currentTimeMillis();
            this.b = true;
            z.b(z[9], z[12]);
        } catch (JSONException e) {
            c();
            z.b(z[9], new StringBuilder(z[11]).append(e.getMessage()).toString());
        }
    }

    protected final void a(e eVar) {
        if (this.b) {
            synchronized (this.j) {
                this.d += eVar.a();
                z.b(z[9], new StringBuilder(z[18]).append(eVar.a()).toString());
                z.b(z[9], new StringBuilder(z[19]).append((this.h - (System.currentTimeMillis() - this.g)) / 1000).append("s").toString());
                switch (this.c) {
                    case 1:
                        if (System.currentTimeMillis() - this.g < this.h) {
                            if (System.currentTimeMillis() - this.f > this.e) {
                                synchronized (this.j) {
                                    b();
                                    this.j.clear();
                                    this.d = 0;
                                    this.f = System.currentTimeMillis();
                                }
                            }
                            this.j.add(eVar);
                            break;
                        }
                        b();
                        c();
                        break;
                    case 2:
                        if (System.currentTimeMillis() - this.g < this.h) {
                            if (((long) this.d) >= 10240) {
                                b();
                                this.j.clear();
                                this.d = eVar.a();
                            }
                            this.j.add(eVar);
                            break;
                        }
                        b();
                        c();
                        break;
                }
            }
        }
    }
}
