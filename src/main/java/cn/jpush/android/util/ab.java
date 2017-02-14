package cn.jpush.android.util;

import cn.jpush.android.api.d;
import java.util.Set;
import java.util.regex.Pattern;

public final class ab {
    public static final Pattern a;
    public static final Pattern b;
    public static final Pattern c;
    public static final Pattern d;
    public static final Pattern e;
    public static final Pattern f;
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 3;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "'I两\u0015鿿I?\u001dYw\u0003S\tb\u0005$i\u0014\u0014nIo\u0000";
        r0 = -1;
        r4 = r3;
    L_0x0008:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002d;
    L_0x0011:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0016:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0075;
            case 1: goto L_0x0078;
            case 2: goto L_0x007b;
            case 3: goto L_0x007e;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 90;
    L_0x001f:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002b;
    L_0x0027:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0016;
    L_0x002b:
        r5 = r1;
        r1 = r7;
    L_0x002d:
        if (r5 > r6) goto L_0x0011;
    L_0x002f:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0043;
            case 1: goto L_0x004b;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = "'I两\u0015鿿I?\u001dYw\u0003S\tb\u0005$9\u0000";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = "Q:\f\u0010\u0001\u0018?^yw#\"\t\u0001úTퟭ襤\u0015ﶕﶉ?ￋe\u0001\u0018?^yw#\"\t\u0001úTퟭ襤\u0015ﶕﶉ?ￋdw$8\r\u0012\u0001\u0018?^yw#\"\t\u0001úTퟭ襤\u0015ﶕﶉ?ￋe\u0006W;\u000f";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004b:
        r3[r2] = r1;
        z = r4;
        r1 = "Q:E](\u0016nEJ*\u0018nEK3\u0018nEc9\u001dwB_3\u0015JW+\u000baPM-\u0001hy\u0011&QpMB&\u001bIEZ>\u001ctCP3\u0013JW(\nfRO#\u0003O\rDr\u001asPD9\u0016X[5\u0016bX[\u0001\u0018q@^=\u0011{OT7\u0017}VM,\u0001k^es\u0005v]0\u0012KB\u0007\u0005:A\\/\u0005w[?\u001e`WL/$;X^\u0001\u0010xOU5\u000bOX\u0010=\u0016dX_\u0001\u0018p@]<\u001ezMT7\u0017bUJ)\rgSA\u0007PnLc1\u0014|VL/$n\fQ4\u001f}XQ4\rnMc>\u001c~IV5\b`WL\u0007Pn\fR5\u001baXR\u0001\u001cKH\u0007PnOc?\u001ezMU4\t`SA $nHc;\u001bqMS(\nfQN#$n\fU3\u0015nIW8\u0010nIM)\u001cgID7\"sG\\?\u001ezOT7\u0017}TI(\nfQN-\u0001k^es\u0005:JY7\u001cnJ].\u0005|Y9\u001ctCQ6\u0016bVM $;X\u00105\u000buXW7Pn\fH(\u0016nTc;\u001ctCP1\u0015JJ)\re]es\u0005cED(\"wKK/\u000eOXK\u0001\u0018pG\\?\u001ezMR1\u0015JW(\rgRA $n\fL?\u0015nPJ;\u000fwHD.\"q@^=\u0011xOT7\u0017}TJ.\u000fe^es\u0005gY=\u0012a]B\u0007\u0005dY9\u001cuMV/$nSc<\nOX\u0010\"\u0017N\tdwIhSUoOvX@4%?x\u0015kHp\u0011Z)Js\u001dY0OuX@4%?x\u0015bIsOP8\u0000yJRn\u001fn\\V\u0006TN\t\u0001.Mp\u0015\t#\u0010'ED\"\u0017N\tdw\u001dwFYj\u0018vX@4%?x\u0015=Oe\u0016\rk\u001dn\\V\u0006TN\tP=\u001by\u0012Y0Nt\u0011\u000b8\u001bsX@4%?x\u00152\u0015qN\u000e;\u0000s\u001d])\u001a%ED\"\u0017N\tdw\u0013jET*\u001d~TD\"\u0017N\tdw\u0012uF]9\u0011fRD\"\u0017N\tdw\u0003qOB;\u0011;XA\u0001\u001cfQe&\u0003IEU-$;";
        r0 = -1;
    L_0x0052:
        r1 = r1.toCharArray();
        r2 = r1.length;
        r3 = 0;
        r4 = 1;
        if (r2 > r4) goto L_0x0106;
    L_0x005b:
        r4 = r1;
        r5 = r3;
        r11 = r2;
        r2 = r1;
        r1 = r11;
    L_0x0060:
        r7 = r2[r3];
        r6 = r5 % 5;
        switch(r6) {
            case 0: goto L_0x00f4;
            case 1: goto L_0x00f8;
            case 2: goto L_0x00fc;
            case 3: goto L_0x0100;
            default: goto L_0x0067;
        };
    L_0x0067:
        r6 = 90;
    L_0x0069:
        r6 = r6 ^ r7;
        r6 = (char) r6;
        r2[r3] = r6;
        r3 = r5 + 1;
        if (r1 != 0) goto L_0x0104;
    L_0x0071:
        r2 = r4;
        r5 = r3;
        r3 = r1;
        goto L_0x0060;
    L_0x0075:
        r9 = 121; // 0x79 float:1.7E-43 double:6.0E-322;
        goto L_0x001f;
    L_0x0078:
        r9 = 18;
        goto L_0x001f;
    L_0x007b:
        r9 = 36;
        goto L_0x001f;
    L_0x007e:
        r9 = 56;
        goto L_0x001f;
    L_0x0081:
        r0 = java.util.regex.Pattern.compile(r1);
        c = r0;
        r0 = new java.lang.StringBuilder;
        r1 = z;
        r2 = 2;
        r1 = r1[r2];
        r0.<init>(r1);
        r1 = a;
        r6 = r0.append(r1);
        r0 = "Pn";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x00ca;
    L_0x00a2:
        r3 = r0;
        r4 = r2;
        r11 = r1;
        r1 = r0;
        r0 = r11;
    L_0x00a7:
        r7 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x00bc;
            case 1: goto L_0x00bf;
            case 2: goto L_0x00c2;
            case 3: goto L_0x00c5;
            default: goto L_0x00ae;
        };
    L_0x00ae:
        r5 = 90;
    L_0x00b0:
        r5 = r5 ^ r7;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x00c8;
    L_0x00b8:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x00a7;
    L_0x00bc:
        r5 = 121; // 0x79 float:1.7E-43 double:6.0E-322;
        goto L_0x00b0;
    L_0x00bf:
        r5 = 18;
        goto L_0x00b0;
    L_0x00c2:
        r5 = 36;
        goto L_0x00b0;
    L_0x00c5:
        r5 = 56;
        goto L_0x00b0;
    L_0x00c8:
        r1 = r0;
        r0 = r3;
    L_0x00ca:
        if (r1 > r2) goto L_0x00a2;
    L_0x00cc:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        r0 = r6.append(r0);
        r1 = c;
        r0 = r0.append(r1);
        r1 = ")";
        r0 = r0.append(r1);
        r0 = r0.toString();
        r0 = java.util.regex.Pattern.compile(r0);
        d = r0;
        r1 = "\"s\tB\u001bTH\u0014\u0015c%9x\u0016\u0006&N\u0001dw%9yCkU \u0011\u000e'%RYw\u0003S\tbjT+yc;The\u0015\u0000I?\u001ddw$i\u0014\u0014lMo\fdt\"s\tB\u001bTH\u0014\u0015c$IE\u0015 8?~\bw@N\te!I>\u0016\r'P9";
        r0 = 2;
        goto L_0x0052;
    L_0x00f4:
        r6 = 121; // 0x79 float:1.7E-43 double:6.0E-322;
        goto L_0x0069;
    L_0x00f8:
        r6 = 18;
        goto L_0x0069;
    L_0x00fc:
        r6 = 36;
        goto L_0x0069;
    L_0x0100:
        r6 = 56;
        goto L_0x0069;
    L_0x0104:
        r2 = r1;
        r1 = r4;
    L_0x0106:
        if (r2 > r3) goto L_0x005b;
    L_0x0108:
        r2 = new java.lang.String;
        r2.<init>(r1);
        r1 = r2.intern();
        switch(r0) {
            case 0: goto L_0x011f;
            case 1: goto L_0x0081;
            case 2: goto L_0x012a;
            case 3: goto L_0x0135;
            default: goto L_0x0114;
        };
    L_0x0114:
        r0 = java.util.regex.Pattern.compile(r1);
        a = r0;
        r1 = "Q:\u001b\u0002r\u0011fPH&\u0011fPH)\u0005ZPL*\u0005ZPL*\nnVL)\tnvL)\t;\u001edu%=\f\u0007`Q-\u001ec;The\u0015\u0000I?\u001dd~%?xg\u0006WN\u000fd{%8x\u001f\u0006QN\rdv%)x\u0007\u0006_N\u0019e&Q-\u001ed\"s\t^\u001bTT\u0014\u0015c$i\u0016EsPi\u0015\u0014lMo\f\u0007`%(\f\u0007`\"s\tB\u001bTH\u0014\u0015c%6x\u0015\u0006&N\ndq%3x\u0012\u0006^N\fds%>x\u0003\u0006FN\u0002dg$n\f\u0007`%7Yw\u001fS\t~jT+yCh\u0004;\rCkU \u0011EsFNd\u0011eP-\f\u0010eC:\u001b\u0002\u0001\u0018?^yw#\"\t\u0001úTퟭ襤\u0015ﶕﶉ?ￋe\u0001\u0018?^yw#\"\t\u0001úTퟭ襤\u0015ﶕﶉ?ￋdw$i\u0014\u0014lMox\u0016sR:\u001b\u0002rF(E](\u0016nEJ*\u0018nEK3\u0018nEc9\u001dwB_3\u0015JW+\u000baPM-\u0001hy\u0011&Q-\u001eZ3\u0003nFc;\u001bvA^=\u0011{NU4\u0016`WL,\u000ek^es\u0005:\u001b\u00029\u0018fX[5\u0014nGW5\tnGc;\u001avB_2\u0010yHU4\u0016`QN\"\u0000hy\u0011&\u001dIAR1\u0014}^e&Q-\u001e]>\fnAc9\u001cuVK.\fO\rD<\"{NS7\u0016`yDrF(CW,\u0005uY8\u001dwB_2\u0010~IV*\b`WL/\u000eky\u0011&\u0011IOU4\u000bfQe&Q-\u001eQ4\u001f}XQ4\rnMc>\u001c~IV5\b`WL\u0007Pn\f\u0007`\u0013}FK&\u0013IAU5\tO\rD1\"wCP3\u0014|TJ-\u0000hyD6\"sF[3\u0012`WL/\u000fkyDrF(IQ6\u0005KZ3\u0005QK?\fXU\u0001\u0018q@]=\u0011yHU4\u0016bUJ)\rgRO\"\u0000hy\u0011&Q-\u001eV;\u0014wXV?\rnJc;\u001awB_3\u0015}TJ/\u0003O\rDrF(KJ=\u0005}I\u0011&Q-\u001eH(\u0016nTc;\u001ctCP1\u0015JJ)\re]es\u0005cED(\"wKK/\u000eOXK\u0001\u0018pG\\?\u001ezMR1\u0015JW(\rgRA $n\f\u0007`\rwHD.\u000bsR]6\u0005f[>\u001fuLR1\u0015JW*\u000bfRO $;XM\u0001\u0018uOK#\u0003OXN\u0001\u0018qA_3\u0017gyD-\"tWe&Q-\u001e@4%?x\u0015j\u0003eI\rl\u001dn\\V\u0006TN\t\tk\u001b'FKi\u0018+ERl\u001en\\V\u0006TN\t\u0000j\u0018yLZ#\u0012|N\f<\u0005jJdw%?\u001dLn\u001b#\u0015A3LsX@4%?x\u0015>\u001cpE\b;\u001dn\\V\u0006TN\t_l\u000e \u0011\t>\u0005jJdw%?L_8\u0012$ERm\u001f'\u0017Z8\u0018n\\V\u0006TN\tP6\u001ax\u0012Y#\u0018+AK9NsX@4%?x\u00150\u0001sHH>\u0015bX@4%?x\u00151\u001epA[2\rdX@4%?x\u0015 \u001ay^Y2Pn]c?\rgyD \"sIO\u0007P;X\u0010eC:\u001b\u0002hLI\u0014\u0015o$n\u0016cjT&ycjT+yD\u0001I?\u0015e\u0001I?\u001de!KoXckT+ycjT+yD\u0001H?\u001des%<\f\u0007`K'\bwLOX\n\u0001I?\u0010e\u0001I?\u001de&\"\"\t\t\u0007\"\"\t\u0001\u0007\u0002 YD\u0001H?\u001de\u0001I?\u001de&\"#\t\u0001\u0007\u0005\"\rdtQ-\u001e\no\"\"\t\r\u0007\u0005 \bwMO\bw@OXcjT#ycjT+yCh\u0004n\tw@O\bw@OXckT+yDjPN\n\u0010eC \u0011cjT'yDh\"\"\t\f\u0007\"\"\t\u0001\u0007\u0005I\u0014\u0015k$I\u0014\u0015c$i\u0016E&\"#\t\u0001\u0007\"\"\t\u0001\u0007\u0005I\u0014\u0015c$;\r\u0011rF(x\u0002\u0006\u001di\u0015\u0014o\u0004;\u001b\u0011r%=\f\u0007`Q-\u001ec;The\u0015\u0000I?\u001dw힆裸\tﷷﶪT�x\u0003\u0006VN\u001bd`%Rx\u001e\u0006DN\u0007d$%?x\u0016\u0006RN\u0005dp%5x\u0010\u0006PN\bd\u0005$;X\u0010eCN\u0001c;Tte\u0015\u001cI?\u001de!Ko\r\u0011pP-\f\u0007`%pX\u001cs";
        r0 = 0;
        goto L_0x0052;
    L_0x011f:
        r0 = java.util.regex.Pattern.compile(r1);
        b = r0;
        r1 = "Q:\u0016\r\u0001I?\u0011e&KI\u0014\u0015n$I\u0014\u0015c$n\bwHO\bw@O_\n'\u0005I\u0015\u0015c$I\u0014\u0015c$n\tw@O\rdtQ \u0011cjT'yDh\"\"\t\f\u0007\"\"\t\u0001\u0007\u0005I\u0014\u0015k$I\u0014\u0015c$i\u0016E&\"#\t\u0001\u0007\"\"\t\u0001\u0007\u0005I\u0015\u0015c$n\u0014\u0011\u0006W:\u0016\r\u0001I?\u0011e&KI\u0014\u0015n$I\u0014\u0015c$n\bwHO\bw@O_\n'\u0005I\u0015\u0015c$I\u0014\u0015c$n\tw@OX\bs%<\f\no\"\"\t\r\u0007\u0005 \bwMO\bw@OXcjT#ycjT+yCh\u0004n\tw@O\bw@OXcjT+y\u0011s";
        r0 = 1;
        goto L_0x0052;
    L_0x012a:
        r0 = java.util.regex.Pattern.compile(r1);
        e = r0;
        r1 = "QN\u000fcjT+y\u0013\u0001%?\u0004dt$8\r\u0007r%:\bw@O\u000fds\"N\t\u0018\u0006WO\u000e\u0011eQI\u0014\u0015c$I\u0014\u0015c%?\u0004dt$I\u0014\u0015c%?\u0004dt$9\bw@O\r";
        r0 = 3;
        goto L_0x0052;
    L_0x0135:
        r0 = java.util.regex.Pattern.compile(r1);
        f = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.ab.<clinit>():void");
    }

    public static int a(Set<String> set) {
        if (set == null || set.isEmpty()) {
            return 0;
        }
        if (set.size() > 100) {
            return d.g;
        }
        for (String str : set) {
            if (str == null) {
                return d.e;
            }
            if (str.getBytes().length > 40) {
                return d.f;
            }
            if (!Pattern.compile(z[1]).matcher(str).matches()) {
                return d.e;
            }
        }
        return 0;
    }

    public static boolean a(String str) {
        return str == null ? false : Pattern.compile(z[0]).matcher(str).matches();
    }

    public static int b(String str) {
        return (str == null || ai.a(str)) ? 0 : str.getBytes().length > 40 ? d.d : !Pattern.compile(z[1]).matcher(str).matches() ? d.c : 0;
    }
}
