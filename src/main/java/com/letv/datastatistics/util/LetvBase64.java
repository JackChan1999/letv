package com.letv.datastatistics.util;

import android.support.v4.view.MotionEventCompat;
import com.letv.core.utils.LetvUtils;
import u.aly.df;

public class LetvBase64 {
    private static char[] _$6698 = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', LetvUtils.CHARACTER_BACKSLASH};
    private static byte[] _$6699 = new byte[]{(byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 62, (byte) -1, (byte) 63, (byte) -1, (byte) 63, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 58, (byte) 59, (byte) 60, (byte) 61, (byte) -1, (byte) -1, (byte) -1, (byte) 0, (byte) -1, (byte) -1, (byte) -1, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, df.k, df.l, df.m, df.n, (byte) 17, (byte) 18, (byte) 19, (byte) 20, (byte) 21, (byte) 22, (byte) 23, (byte) 24, (byte) 25, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 26, (byte) 27, (byte) 28, (byte) 29, (byte) 30, (byte) 31, (byte) 32, (byte) 33, (byte) 34, (byte) 35, (byte) 36, (byte) 37, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 43, (byte) 44, (byte) 45, (byte) 46, (byte) 47, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1};

    public static String encode(byte[] b) {
        int code = 0;
        StringBuffer sb = new StringBuffer(((b.length - 1) / 3) << 6);
        int i = 0;
        while (i < b.length) {
            code |= (b[i] << (16 - ((i % 3) * 8))) & (255 << (16 - ((i % 3) * 8)));
            if (i % 3 == 2 || i == b.length - 1) {
                sb.append(_$6698[(16515072 & code) >>> 18]);
                sb.append(_$6698[(258048 & code) >>> 12]);
                sb.append(_$6698[(code & 4032) >>> 6]);
                sb.append(_$6698[code & 63]);
                code = 0;
            }
            i++;
        }
        if (b.length % 3 > 0) {
            sb.setCharAt(sb.length() - 1, LetvUtils.CHARACTER_EQUAL);
        }
        if (b.length % 3 == 1) {
            sb.setCharAt(sb.length() - 2, LetvUtils.CHARACTER_EQUAL);
        }
        return sb.toString();
    }

    public static byte[] decode(String code) {
        if (code == null) {
            return null;
        }
        int len = code.length();
        if (len % 4 != 0) {
            throw new IllegalArgumentException("Base64 string length must be 4*n");
        } else if (code.length() == 0) {
            return new byte[0];
        } else {
            int pad = 0;
            if (code.charAt(len - 1) == LetvUtils.CHARACTER_EQUAL) {
                pad = 0 + 1;
            }
            if (code.charAt(len - 2) == LetvUtils.CHARACTER_EQUAL) {
                pad++;
            }
            int retLen = ((len / 4) * 3) - pad;
            byte[] ret = new byte[retLen];
            for (int i = 0; i < len; i += 4) {
                int j = (i / 4) * 3;
                int tmp = (((_$6699[code.charAt(i)] << 18) | (_$6699[code.charAt(i + 1)] << 12)) | (_$6699[code.charAt(i + 2)] << 6)) | _$6699[code.charAt(i + 3)];
                ret[j] = (byte) ((16711680 & tmp) >> 16);
                if (i < len - 4) {
                    ret[j + 1] = (byte) ((tmp & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
                    ret[j + 2] = (byte) (tmp & 255);
                } else {
                    if (j + 1 < retLen) {
                        ret[j + 1] = (byte) ((tmp & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
                    }
                    if (j + 2 < retLen) {
                        ret[j + 2] = (byte) (tmp & 255);
                    }
                }
            }
            return ret;
        }
    }
}
