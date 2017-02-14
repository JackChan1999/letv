package cn.com.iresearch.vvtracker.a.a;

import com.letv.core.constant.PlayConstant.LiveType;
import com.letv.core.messagebus.config.LeMessageIds;
import u.aly.df;

public final class a extends b {
    private static byte[] b = new byte[]{df.k, (byte) 10};
    private static final byte[] c = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};
    private static final byte[] d = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 45, (byte) 95};
    private static final byte[] e;
    private final byte[] f;
    private final byte[] g;

    static {
        byte[] bArr = new byte[123];
        bArr[0] = (byte) -1;
        bArr[1] = (byte) -1;
        bArr[2] = (byte) -1;
        bArr[3] = (byte) -1;
        bArr[4] = (byte) -1;
        bArr[5] = (byte) -1;
        bArr[6] = (byte) -1;
        bArr[7] = (byte) -1;
        bArr[8] = (byte) -1;
        bArr[9] = (byte) -1;
        bArr[10] = (byte) -1;
        bArr[11] = (byte) -1;
        bArr[12] = (byte) -1;
        bArr[13] = (byte) -1;
        bArr[14] = (byte) -1;
        bArr[15] = (byte) -1;
        bArr[16] = (byte) -1;
        bArr[17] = (byte) -1;
        bArr[18] = (byte) -1;
        bArr[19] = (byte) -1;
        bArr[20] = (byte) -1;
        bArr[21] = (byte) -1;
        bArr[22] = (byte) -1;
        bArr[23] = (byte) -1;
        bArr[24] = (byte) -1;
        bArr[25] = (byte) -1;
        bArr[26] = (byte) -1;
        bArr[27] = (byte) -1;
        bArr[28] = (byte) -1;
        bArr[29] = (byte) -1;
        bArr[30] = (byte) -1;
        bArr[31] = (byte) -1;
        bArr[32] = (byte) -1;
        bArr[33] = (byte) -1;
        bArr[34] = (byte) -1;
        bArr[35] = (byte) -1;
        bArr[36] = (byte) -1;
        bArr[37] = (byte) -1;
        bArr[38] = (byte) -1;
        bArr[39] = (byte) -1;
        bArr[40] = (byte) -1;
        bArr[41] = (byte) -1;
        bArr[42] = (byte) -1;
        bArr[43] = (byte) 62;
        bArr[44] = (byte) -1;
        bArr[45] = (byte) 62;
        bArr[46] = (byte) -1;
        bArr[47] = (byte) 63;
        bArr[48] = (byte) 52;
        bArr[49] = (byte) 53;
        bArr[50] = (byte) 54;
        bArr[51] = (byte) 55;
        bArr[52] = (byte) 56;
        bArr[53] = (byte) 57;
        bArr[54] = (byte) 58;
        bArr[55] = (byte) 59;
        bArr[56] = (byte) 60;
        bArr[57] = (byte) 61;
        bArr[58] = (byte) -1;
        bArr[59] = (byte) -1;
        bArr[60] = (byte) -1;
        bArr[61] = (byte) -1;
        bArr[62] = (byte) -1;
        bArr[63] = (byte) -1;
        bArr[64] = (byte) -1;
        bArr[66] = (byte) 1;
        bArr[67] = (byte) 2;
        bArr[68] = (byte) 3;
        bArr[69] = (byte) 4;
        bArr[70] = (byte) 5;
        bArr[71] = (byte) 6;
        bArr[72] = (byte) 7;
        bArr[73] = (byte) 8;
        bArr[74] = (byte) 9;
        bArr[75] = (byte) 10;
        bArr[76] = (byte) 11;
        bArr[77] = (byte) 12;
        bArr[78] = df.k;
        bArr[79] = df.l;
        bArr[80] = df.m;
        bArr[81] = df.n;
        bArr[82] = (byte) 17;
        bArr[83] = (byte) 18;
        bArr[84] = (byte) 19;
        bArr[85] = (byte) 20;
        bArr[86] = (byte) 21;
        bArr[87] = (byte) 22;
        bArr[88] = (byte) 23;
        bArr[89] = (byte) 24;
        bArr[90] = (byte) 25;
        bArr[91] = (byte) -1;
        bArr[92] = (byte) -1;
        bArr[93] = (byte) -1;
        bArr[94] = (byte) -1;
        bArr[95] = (byte) 63;
        bArr[96] = (byte) -1;
        bArr[97] = (byte) 26;
        bArr[98] = (byte) 27;
        bArr[99] = (byte) 28;
        bArr[100] = (byte) 29;
        bArr[101] = (byte) 30;
        bArr[102] = (byte) 31;
        bArr[103] = (byte) 32;
        bArr[104] = (byte) 33;
        bArr[105] = (byte) 34;
        bArr[106] = (byte) 35;
        bArr[107] = (byte) 36;
        bArr[108] = (byte) 37;
        bArr[109] = (byte) 38;
        bArr[LiveType.PLAY_LIVE_OTHER] = (byte) 39;
        bArr[111] = (byte) 40;
        bArr[112] = (byte) 41;
        bArr[113] = (byte) 42;
        bArr[114] = (byte) 43;
        bArr[115] = (byte) 44;
        bArr[116] = (byte) 45;
        bArr[117] = (byte) 46;
        bArr[LiveType.PLAY_LIVE_HK_MUSIC] = (byte) 47;
        bArr[119] = (byte) 48;
        bArr[LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT] = (byte) 49;
        bArr[LeMessageIds.MSG_ALBUM_FETCH_PLAY_NEXT_CONTROLLER] = (byte) 50;
        bArr[122] = (byte) 51;
        e = bArr;
    }

    public a() {
        this((byte) 0);
    }

    private a(byte b) {
        this(b);
    }

    private a(byte[] bArr) {
        this(bArr, false);
    }

    private a(byte[] bArr, boolean z) {
        int i;
        if (bArr == null) {
            i = 0;
        } else {
            i = bArr.length;
        }
        super(i);
        this.g = e;
        if (bArr == null || !b(bArr)) {
            byte[] bArr2;
            if (z) {
                bArr2 = d;
            } else {
                bArr2 = c;
            }
            this.f = bArr2;
            return;
        }
        throw new IllegalArgumentException("lineSeparator must not contain base64 characters: [" + cn.com.iresearch.vvtracker.dao.a.newStringUtf8(bArr) + "]");
    }

    final void a(byte[] bArr, int i, int i2, c cVar) {
        if (!cVar.e) {
            int i3;
            int i4;
            if (i2 < 0) {
                cVar.e = true;
                if (cVar.g != 0) {
                    byte[] a = b.a(4, cVar);
                    i3 = cVar.c;
                    switch (cVar.g) {
                        case 0:
                            break;
                        case 1:
                            i4 = cVar.c;
                            cVar.c = i4 + 1;
                            a[i4] = this.f[(cVar.a >> 2) & 63];
                            i4 = cVar.c;
                            cVar.c = i4 + 1;
                            a[i4] = this.f[(cVar.a << 4) & 63];
                            if (this.f == c) {
                                i4 = cVar.c;
                                cVar.c = i4 + 1;
                                a[i4] = (byte) 61;
                                i4 = cVar.c;
                                cVar.c = i4 + 1;
                                a[i4] = (byte) 61;
                                break;
                            }
                            break;
                        case 2:
                            i4 = cVar.c;
                            cVar.c = i4 + 1;
                            a[i4] = this.f[(cVar.a >> 10) & 63];
                            i4 = cVar.c;
                            cVar.c = i4 + 1;
                            a[i4] = this.f[(cVar.a >> 4) & 63];
                            i4 = cVar.c;
                            cVar.c = i4 + 1;
                            a[i4] = this.f[(cVar.a << 2) & 63];
                            if (this.f == c) {
                                i4 = cVar.c;
                                cVar.c = i4 + 1;
                                a[i4] = (byte) 61;
                                break;
                            }
                            break;
                        default:
                            throw new IllegalStateException("Impossible modulus " + cVar.g);
                    }
                    cVar.f += cVar.c - i3;
                    return;
                }
                return;
            }
            i3 = 0;
            while (i3 < i2) {
                byte[] a2 = b.a(4, cVar);
                cVar.g = (cVar.g + 1) % 3;
                i4 = i + 1;
                int i5 = bArr[i];
                if (i5 < 0) {
                    i5 += 256;
                }
                cVar.a = i5 + (cVar.a << 8);
                if (cVar.g == 0) {
                    i5 = cVar.c;
                    cVar.c = i5 + 1;
                    a2[i5] = this.f[(cVar.a >> 18) & 63];
                    i5 = cVar.c;
                    cVar.c = i5 + 1;
                    a2[i5] = this.f[(cVar.a >> 12) & 63];
                    i5 = cVar.c;
                    cVar.c = i5 + 1;
                    a2[i5] = this.f[(cVar.a >> 6) & 63];
                    i5 = cVar.c;
                    cVar.c = i5 + 1;
                    a2[i5] = this.f[cVar.a & 63];
                    cVar.f += 4;
                }
                i3++;
                i = i4;
            }
        }
    }

    public static String a(byte[] bArr) {
        if (!(bArr == null || bArr.length == 0)) {
            b aVar = new a(b, true);
            long length = ((long) (((bArr.length + 3) - 1) / 3)) << 2;
            if (length > 2147483647L) {
                throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + length + ") than the specified maximum size of 2147483647");
            } else if (!(bArr == null || bArr.length == 0)) {
                c cVar = new c();
                aVar.a(bArr, 0, bArr.length, cVar);
                aVar.a(bArr, 0, -1, cVar);
                bArr = new byte[(cVar.c - cVar.d)];
                int length2 = bArr.length;
                if (cVar.b != null) {
                    int min = Math.min(cVar.b != null ? cVar.c - cVar.d : 0, length2);
                    System.arraycopy(cVar.b, cVar.d, bArr, 0, min);
                    cVar.d = min + cVar.d;
                    if (cVar.d >= cVar.c) {
                        cVar.b = null;
                    }
                } else {
                    boolean z = cVar.e;
                }
            }
        }
        return cn.com.iresearch.vvtracker.dao.a.newStringUtf8(bArr);
    }

    protected final boolean a(byte b) {
        return b >= (byte) 0 && b < this.g.length && this.g[b] != (byte) -1;
    }
}
