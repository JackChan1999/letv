package cn.jpush.android.service;

import android.text.TextUtils;
import cn.jpush.android.util.z;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public final class r {
    private static final HashMap<Integer, String> a;
    private static final HashMap<Integer, String> b;
    private static long c = 0;
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 8;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = ",\u001c\u001d&x";
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
            case 0: goto L_0x00aa;
            case 1: goto L_0x00ae;
            case 2: goto L_0x00b2;
            case 3: goto L_0x00b6;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 28;
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
            case 6: goto L_0x0074;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "9\u0015\u001d*";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = ",\u000f\u0019&s#";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "?\u0001\u001b";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = ",\u001c\u001d\u0010})\b2=q;";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\u0018\u0002\u0006!s:\u0002M=y=\u0003\u001f;<.\u0003\t*<`L";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "\u0018\u0002\u0006!s:\u0002M*n?\u0003\u001fo\"\b\bo1m";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = ",\b\t";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        z = r4;
        r3 = new java.util.HashMap;
        r3.<init>();
        a = r3;
        r0 = 0;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0002'";
        r0 = -1;
    L_0x0087:
        r1 = r1.toCharArray();
        r4 = r1.length;
        r5 = 0;
        r6 = 1;
        if (r4 > r6) goto L_0x00c8;
    L_0x0090:
        r6 = r1;
        r7 = r5;
        r11 = r4;
        r4 = r1;
        r1 = r11;
    L_0x0095:
        r9 = r4[r5];
        r8 = r7 % 5;
        switch(r8) {
            case 0: goto L_0x00ba;
            case 1: goto L_0x00bd;
            case 2: goto L_0x00c0;
            case 3: goto L_0x00c3;
            default: goto L_0x009c;
        };
    L_0x009c:
        r8 = 28;
    L_0x009e:
        r8 = r8 ^ r9;
        r8 = (char) r8;
        r4[r5] = r8;
        r5 = r7 + 1;
        if (r1 != 0) goto L_0x00c6;
    L_0x00a6:
        r4 = r6;
        r7 = r5;
        r5 = r1;
        goto L_0x0095;
    L_0x00aa:
        r9 = 77;
        goto L_0x0020;
    L_0x00ae:
        r9 = 108; // 0x6c float:1.51E-43 double:5.34E-322;
        goto L_0x0020;
    L_0x00b2:
        r9 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        goto L_0x0020;
    L_0x00b6:
        r9 = 79;
        goto L_0x0020;
    L_0x00ba:
        r8 = 77;
        goto L_0x009e;
    L_0x00bd:
        r8 = 108; // 0x6c float:1.51E-43 double:5.34E-322;
        goto L_0x009e;
    L_0x00c0:
        r8 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        goto L_0x009e;
    L_0x00c3:
        r8 = 79;
        goto L_0x009e;
    L_0x00c6:
        r4 = r1;
        r1 = r6;
    L_0x00c8:
        if (r4 > r5) goto L_0x0090;
    L_0x00ca:
        r4 = new java.lang.String;
        r4.<init>(r1);
        r1 = r4.intern();
        switch(r0) {
            case 0: goto L_0x00e5;
            case 1: goto L_0x00f4;
            case 2: goto L_0x0103;
            case 3: goto L_0x0113;
            case 4: goto L_0x0123;
            case 5: goto L_0x0133;
            case 6: goto L_0x0143;
            case 7: goto L_0x0153;
            case 8: goto L_0x0164;
            case 9: goto L_0x0175;
            case 10: goto L_0x0186;
            case 11: goto L_0x0197;
            case 12: goto L_0x01a8;
            case 13: goto L_0x01be;
            case 14: goto L_0x01cf;
            case 15: goto L_0x01e0;
            case 16: goto L_0x01f1;
            case 17: goto L_0x0202;
            case 18: goto L_0x0213;
            case 19: goto L_0x0224;
            case 20: goto L_0x0235;
            case 21: goto L_0x0246;
            case 22: goto L_0x0257;
            case 23: goto L_0x0268;
            case 24: goto L_0x0279;
            case 25: goto L_0x028a;
            case 26: goto L_0x029b;
            case 27: goto L_0x02ac;
            case 28: goto L_0x02bd;
            case 29: goto L_0x02ce;
            case 30: goto L_0x02df;
            case 31: goto L_0x02f0;
            case 32: goto L_0x0301;
            case 33: goto L_0x0312;
            case 34: goto L_0x0323;
            case 35: goto L_0x0334;
            case 36: goto L_0x0345;
            case 37: goto L_0x0356;
            case 38: goto L_0x0367;
            case 39: goto L_0x0378;
            case 40: goto L_0x0389;
            case 41: goto L_0x039a;
            default: goto L_0x00d6;
        };
    L_0x00d6:
        r3.put(r2, r1);
        r3 = a;
        r0 = -1001; // 0xfffffffffffffc17 float:NaN double:NaN;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\b\u0014\u000e*y)L\u000f:z+\t\u001foo$\u0016\ba<\u001d\u0000\b.o(L\u000e r9\r\u000e;<>\u0019\u001d?s?\u0018C";
        r0 = 0;
        goto L_0x0087;
    L_0x00e5:
        r3.put(r2, r1);
        r3 = a;
        r0 = -1000; // 0xfffffffffffffc18 float:NaN double:NaN;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u000e\u0003\u0003!y.\u0018\u0004 rm\n\f&p(\bCoL!\t\f<ym\u000f\u0005*&L\u0014 i?L\u000e r#\t\u000e;u\"\u0002M.r)L\u001f*h?\u0015M#}9\t\u001fn";
        r0 = 1;
        goto L_0x0087;
    L_0x00f4:
        r3.put(r2, r1);
        r3 = a;
        r0 = -998; // 0xfffffffffffffc1a float:NaN double:NaN;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u001e\t\u0003+u#\u000bM)}$\u0000\b+<\"\u001eM;u \t\u0002:hcL=#y,\u001f\boN(\u0018\u001f6<!\r\u0019*nl";
        r0 = 2;
        goto L_0x0087;
    L_0x0103:
        r3.put(r2, r1);
        r3 = a;
        r0 = -997; // 0xfffffffffffffc1b float:NaN double:NaN;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u001f\t\u000e*u;\u0005\u0003(<+\r\u0004#y)L\u0002=<9\u0005\u0000*s8\u0018CoL!\t\f<ym>\b;n4L\u0001.h(\u001eL";
        r0 = 3;
        goto L_0x0087;
    L_0x0113:
        r3.put(r2, r1);
        r3 = a;
        r0 = -996; // 0xfffffffffffffc1c float:NaN double:NaN;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u000e\u0003\u0003!y.\u0018\u0004 rm\u0005\u001eo!\u0003\u001e*xcL=#y,\u001f\boN(\u0018\u001f6<!\r\u0019*nl";
        r0 = 4;
        goto L_0x0087;
    L_0x0123:
        r3.put(r2, r1);
        r3 = a;
        r0 = -994; // 0xfffffffffffffc1e float:NaN double:NaN;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u001f\t\u001e?s#\u001f\boh$\u0001\b i9BM\u001fp(\r\u001e*<\u001f\t\u0019=em\u0000\f;y?M";
        r0 = 5;
        goto L_0x0087;
    L_0x0133:
        r3.put(r2, r1);
        r3 = a;
        r0 = -993; // 0xfffffffffffffc1f float:NaN double:NaN;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0004\u0002\u001b.p$\bM<s.\u0007\b;2m<\u0001*}>\tM\u001dy9\u001e\u0014op,\u0018\b==";
        r0 = 6;
        goto L_0x0087;
    L_0x0143:
        r3.put(r2, r1);
        r3 = a;
        r0 = 11;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u000b\r\u0004#y)L\u0019 <?\t\n&o9\t\u001fn";
        r0 = 7;
        goto L_0x0087;
    L_0x0153:
        r3.put(r2, r1);
        r3 = a;
        r0 = 1005; // 0x3ed float:1.408E-42 double:4.965E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0014\u0003\u0018=<,\u001c\u001d\u0004y4L\f!xm\r\u0003+n\"\u0005\tol,\u000f\u0006.{(L\u0003.q(L\f=ym\u0002\u0002;< \r\u0019,t(\bCoL!\t\f<ym\b\u0002:~!\tM,t(\u000f\u0006oh%\t\u0000o}.\u000f\u0002=x$\u0002\noh\"L,?l!\u0005\u000e.h$\u0003\u0003oe\"\u0019M,n(\r\u0019*xm\u0003\u0003oL\"\u001e\u0019.pc";
        r0 = 8;
        goto L_0x0087;
    L_0x0164:
        r3.put(r2, r1);
        r3 = a;
        r0 = 1006; // 0x3ee float:1.41E-42 double:4.97E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0014\u0003\u0018o}#\b\u001f u)L\u001d.&\r\n*<#\r\u0000*<$\u001fM!s9L\b7u>\u0018AoL!\t\f<ym\u001e\b(u>\u0018\b=<4\u0003\u0018=<=\r\u000e.w*\tM!} \tM&rm<\u0002=h,\u0000C";
        r0 = 9;
        goto L_0x0087;
    L_0x0175:
        r3.put(r2, r1);
        r3 = a;
        r0 = 1007; // 0x3ef float:1.411E-42 double:4.975E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0004\u0002\u001b.p$\bM\u0006q(\u0005AoN(\u000b\u0004<h(\u001eM.{,\u0005\u0003a";
        r0 = 10;
        goto L_0x0087;
    L_0x0186:
        r3.put(r2, r1);
        r3 = a;
        r0 = 1009; // 0x3f1 float:1.414E-42 double:4.985E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0014\u0003\u0018=<,\u001c\u001d\u0004y4L\u0004<<?\t\u0001.h(\bM;sm\rM!s#A,!x?\u0003\u0004+<\f\u001c\u001daL!\t\f<ym\u0019\u001e*<4\u0003\u0018=<\f\u0002\t=s$\bM\u000el=K\u001eo}=\u001c&*eaL\u0002=<,\b\to}#L,!x?\u0003\u0004+<,\u001c\u001doz\"\u001eM;t$\u001fM.l='\b62";
        r0 = 11;
        goto L_0x0087;
    L_0x0197:
        r3.put(r2, r1);
        r3 = a;
        r0 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u001f\t\u000e*u;\t\u001fox,\u0018\fol,\u001e\u001e*<(\u001e\u001f n";
        r0 = 12;
        goto L_0x0087;
    L_0x01a8:
        r3.put(r2, r1);
        r3 = new java.util.HashMap;
        r3.<init>();
        b = r3;
        r0 = 995; // 0x3e3 float:1.394E-42 double:4.916E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0000\t\u001e<}*\tM\u0005O\u0002\"M?}?\u001f\u0004!{m\u001f\u0018,(\t\t";
        r0 = 13;
        goto L_0x0087;
    L_0x01be:
        r3.put(r2, r1);
        r3 = b;
        r0 = 996; // 0x3e4 float:1.396E-42 double:4.92E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0000\t\u001e<}*\tM\u0005O\u0002\"M?}?\u001f\u0004!{m\n\f&p(\b";
        r0 = 14;
        goto L_0x0087;
    L_0x01cf:
        r3.put(r2, r1);
        r3 = b;
        r0 = 997; // 0x3e5 float:1.397E-42 double:4.926E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0000\t\u001e<}*\tM.p?\t\f+em\u001e\b,y$\u001a\b+0m\u000b\u00049ym\u0019\u001d";
        r0 = 15;
        goto L_0x0087;
    L_0x01e0:
        r3.put(r2, r1);
        r3 = b;
        r0 = 998; // 0x3e6 float:1.398E-42 double:4.93E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0000\t\u001e<}*\tM.p?\t\f+em\u001e\b,y$\u001a\b+0m\u001f\u0019&p!L\u001d=s.\t\u001e<";
        r0 = 16;
        goto L_0x0087;
    L_0x01f1:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0018\u001f\b=<.\u0000\u0004,w(\bM.r)L\u0002?y#\t\toh%\tM\u0002y>\u001f\f(y";
        r0 = 17;
        goto L_0x0087;
    L_0x0202:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1001; // 0x3e9 float:1.403E-42 double:4.946E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0000\t\u001e<}*\tM+s:\u0002\u0001 })L\u001e:.\t\b+";
        r0 = 18;
        goto L_0x0087;
    L_0x0213:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1002; // 0x3ea float:1.404E-42 double:4.95E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0000\t\u001e<}*\tM=y.\t\u00049y)L\u001e:.\t\b+";
        r0 = 19;
        goto L_0x0087;
    L_0x0224:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1003; // 0x3eb float:1.406E-42 double:4.955E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0000\t\u001e<}*\tM<u!\t\u0003,ym\b\u00028r!\u0003\f+<>\u0019\u000e,y(\b";
        r0 = 20;
        goto L_0x0087;
    L_0x0235:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1004; // 0x3ec float:1.407E-42 double:4.96E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u001b\u0005\t*sm\u001f\u0004#y#\u000f\box\"\u001b\u0003#}\"\bM<i.\u000f\b*x";
        r0 = 21;
        goto L_0x0087;
    L_0x0246:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1005; // 0x3ed float:1.408E-42 double:4.965E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0018\u001f\b=<.\u0000\u0004,w(\bM9u)\t\u0002o}#\bM%i \u001c\b+<9\u0003M:n!L *o>\r\n*<e\u000e\u001f k>\t\u001ff";
        r0 = 22;
        goto L_0x0087;
    L_0x0257:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1008; // 0x3f0 float:1.413E-42 double:4.98E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u001b\u0005\t*sm\u0005\u001eoz\"\u001e\u000e*<.\u0000\u0002<y)L\u000f6<8\u001f\b=";
        r0 = 23;
        goto L_0x0087;
    L_0x0268:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1007; // 0x3ef float:1.411E-42 double:4.975E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0018\u001f\b=<.\u0000\u0004,w(\bMhS\u0006K";
        r0 = 24;
        goto L_0x0087;
    L_0x0279:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1006; // 0x3ee float:1.41E-42 double:4.97E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0018\u001f\b=<.\u0000\u0004,w(\bMh_,\u0002\u000e*pj";
        r0 = 25;
        goto L_0x0087;
    L_0x028a:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1011; // 0x3f3 float:1.417E-42 double:4.995E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\t\u0003\u001a!p\"\r\toz,\u0005\u0001*x";
        r0 = 26;
        goto L_0x0087;
    L_0x029b:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1012; // 0x3f4 float:1.418E-42 double:5.0E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0018\u001f\b=<.\u0000\u0004,w(\bM;sm\b\u00028r!\u0003\f+<,\u000b\f&r";
        r0 = 27;
        goto L_0x0087;
    L_0x02ac:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1013; // 0x3f5 float:1.42E-42 double:5.005E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0019\u0004\boz$\u0000\bo}!\u001e\b.x4L\b7u>\u0018M.r)L\u001e.q(L\u001e&f(BM\u000bs#K\u0019ox\"\u001b\u0003#s,\bM.{,\u0005\u0003a";
        r0 = 28;
        goto L_0x0087;
    L_0x02bd:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1100; // 0x44c float:1.541E-42 double:5.435E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0004\u0002\u001b.p$\bM?}?\r\u0000os?L\u0018!y5\u001c\b,h(\bM=y>\u0019\u0001;2";
        r0 = 29;
        goto L_0x0087;
    L_0x02ce:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1014; // 0x3f6 float:1.421E-42 double:5.01E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u000b\r\u0004#y)L\u0019 <=\u001e\b#s,\bM=y<\u0019\u0004=y)L\u001f*o\"\u0019\u001f,y";
        r0 = 30;
        goto L_0x0087;
    L_0x02df:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1015; // 0x3f7 float:1.422E-42 double:5.015E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0018\u001f\b=<.\u0000\u0004,w(\bM&r>\u0018\f#pm\r\u0001*n9L\u0002!<>\u0018\f;i>L\u000f.nm\r\u000b;y?L\t k#\u0000\u0002.x$\u0002\noz$\u0002\u0004<t(\bC";
        r0 = 31;
        goto L_0x0087;
    L_0x02f0:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1016; // 0x3f8 float:1.424E-42 double:5.02E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0018\u001f\b=<.\u0000\u0004,w(\bM;t(L\u001a*~;\u0005\b8;>L\u0018=p";
        r0 = 32;
        goto L_0x0087;
    L_0x0301:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1017; // 0x3f9 float:1.425E-42 double:5.025E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0018\u001f\b=<.\u0000\u0004,w(\bM,}!\u0000M.9\u0005\u0002!";
        r0 = 33;
        goto L_0x0087;
    L_0x0312:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1018; // 0x3fa float:1.427E-42 double:5.03E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u0019\u0004\boQ(\u001f\u001e.{(L\u001e's:L\u0004!<9\u0004\boo9\r\u0019:om\u000e\f=";
        r0 = 34;
        goto L_0x0087;
    L_0x0323:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1019; // 0x3fb float:1.428E-42 double:5.035E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u000e\u0000\u0004,wm\r\u001d?p$\u001f\u0019o}#\bM<t\"\u001bM;t(L *o>\r\n*";
        r0 = 35;
        goto L_0x0087;
    L_0x0334:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1020; // 0x3fc float:1.43E-42 double:5.04E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\t\u0003\u001a!<$\u0001\f(ym\n\f&p(\b";
        r0 = 36;
        goto L_0x0087;
    L_0x0345:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1021; // 0x3fd float:1.431E-42 double:5.044E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\t\u0003\u001a!<%\u0018\u0000#<+\r\u0004#y)";
        r0 = 37;
        goto L_0x0087;
    L_0x0356:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1022; // 0x3fe float:1.432E-42 double:5.05E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\t\u0003\u001a!<\u0000\t\u001e<}*\tM)}$\u0000\b+";
        r0 = 38;
        goto L_0x0087;
    L_0x0367:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1030; // 0x406 float:1.443E-42 double:5.09E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\t\u0005\u001e,}?\bM;t(L\u0000*o>\r\n*</\t\u000e.i>\tM&hm\u0005\u001eor\"\u0018M&rm\u0018\u0005*<=\u0019\u001e'<9\u0005\u0000*";
        r0 = 39;
        goto L_0x0087;
    L_0x0378:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1031; // 0x407 float:1.445E-42 double:5.094E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u001e\u0018\u0002?<=\u0019\u001e'<>\t\u001f9u.\t";
        r0 = 40;
        goto L_0x0087;
    L_0x0389:
        r3.put(r2, r1);
        r3 = b;
        r0 = 1032; // 0x408 float:1.446E-42 double:5.1E-321;
        r2 = java.lang.Integer.valueOf(r0);
        r1 = "\u001f\t\u001e:q(L\u001d:o%L\u001e*n;\u0005\u000e*";
        r0 = 41;
        goto L_0x0087;
    L_0x039a:
        r3.put(r2, r1);
        r0 = 0;
        c = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.r.<clinit>():void");
    }

    public static String a(int i) {
        if (a.containsKey(Integer.valueOf(i))) {
            return (String) a.get(Integer.valueOf(i));
        }
        new StringBuilder(z[6]).append(i);
        z.b();
        return null;
    }

    public static JSONObject a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(z[2], z[7]);
            jSONObject.put(z[0], str);
            jSONObject.put(z[1], z[4]);
            return jSONObject;
        } catch (JSONException e) {
            return null;
        }
    }

    public static String b(int i) {
        if (b.containsKey(Integer.valueOf(i))) {
            return (String) b.get(Integer.valueOf(i));
        }
        new StringBuilder(z[5]).append(i);
        z.b();
        return "";
    }

    public static JSONObject b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(z[2], z[3]);
            jSONObject.put(z[0], str);
            jSONObject.put(z[1], z[4]);
            return jSONObject;
        } catch (JSONException e) {
            return null;
        }
    }
}
