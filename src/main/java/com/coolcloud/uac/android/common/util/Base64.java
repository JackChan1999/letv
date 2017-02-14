package com.coolcloud.uac.android.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import u.aly.df;

public class Base64 {
    static final byte[] CHUNK_SEPARATOR = new byte[]{df.k, (byte) 10};
    static final int CHUNK_SIZE = 76;
    private static final int MASK_6BITS = 63;
    private static final int MASK_8BITS = 255;
    private static final byte PAD = (byte) 61;
    private static final byte[] base64ToInt = new byte[]{(byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 62, (byte) -1, (byte) -1, (byte) -1, (byte) 63, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 58, (byte) 59, (byte) 60, PAD, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, df.k, df.l, df.m, df.n, (byte) 17, (byte) 18, (byte) 19, (byte) 20, (byte) 21, (byte) 22, (byte) 23, (byte) 24, (byte) 25, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 26, (byte) 27, (byte) 28, (byte) 29, (byte) 30, (byte) 31, (byte) 32, (byte) 33, (byte) 34, (byte) 35, (byte) 36, (byte) 37, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 43, (byte) 44, (byte) 45, (byte) 46, (byte) 47, (byte) 48, (byte) 49, (byte) 50, (byte) 51};
    private static final byte[] intToBase64 = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};
    private byte[] buf;
    private int currentLinePos;
    private final int decodeSize;
    private final int encodeSize;
    private boolean eof;
    private final int lineLength;
    private final byte[] lineSeparator;
    private int modulus;
    private int pos;
    private int readPos;
    private int x;

    public Base64() {
        this(CHUNK_SIZE, CHUNK_SEPARATOR);
    }

    public Base64(int lineLength) {
        this(lineLength, CHUNK_SEPARATOR);
    }

    public Base64(int lineLength, byte[] lineSeparator) {
        this.lineLength = lineLength;
        this.lineSeparator = new byte[lineSeparator.length];
        System.arraycopy(lineSeparator, 0, this.lineSeparator, 0, lineSeparator.length);
        if (lineLength > 0) {
            this.encodeSize = lineSeparator.length + 4;
        } else {
            this.encodeSize = 4;
        }
        this.decodeSize = this.encodeSize - 1;
        if (containsBase64Byte(lineSeparator)) {
            String sep;
            try {
                sep = new String(lineSeparator, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                sep = new String(lineSeparator);
            }
            throw new IllegalArgumentException("lineSeperator must not contain base64 characters: [" + sep + "]");
        }
    }

    boolean hasData() {
        return this.buf != null;
    }

    int avail() {
        return this.buf != null ? this.pos - this.readPos : 0;
    }

    private void resizeBuf() {
        if (this.buf == null) {
            this.buf = new byte[8192];
            this.pos = 0;
            this.readPos = 0;
            return;
        }
        byte[] b = new byte[(this.buf.length * 2)];
        System.arraycopy(this.buf, 0, b, 0, this.buf.length);
        this.buf = b;
    }

    int readResults(byte[] b, int bPos, int bAvail) {
        if (this.buf != null) {
            int len = Math.min(avail(), bAvail);
            if (this.buf != b) {
                System.arraycopy(this.buf, this.readPos, b, bPos, len);
                this.readPos += len;
                if (this.readPos < this.pos) {
                    return len;
                }
                this.buf = null;
                return len;
            }
            this.buf = null;
            return len;
        }
        return this.eof ? -1 : 0;
    }

    void setInitialBuffer(byte[] out, int outPos, int outAvail) {
        if (out != null && out.length == outAvail) {
            this.buf = out;
            this.pos = outPos;
            this.readPos = outPos;
        }
    }

    void encode(byte[] in, int inPos, int inAvail) {
        if (!this.eof) {
            byte[] bArr;
            int i;
            if (inAvail < 0) {
                this.eof = true;
                if (this.buf == null || this.buf.length - this.pos < this.encodeSize) {
                    resizeBuf();
                }
                switch (this.modulus) {
                    case 1:
                        bArr = this.buf;
                        i = this.pos;
                        this.pos = i + 1;
                        bArr[i] = intToBase64[(this.x >> 2) & MASK_6BITS];
                        bArr = this.buf;
                        i = this.pos;
                        this.pos = i + 1;
                        bArr[i] = intToBase64[(this.x << 4) & MASK_6BITS];
                        bArr = this.buf;
                        i = this.pos;
                        this.pos = i + 1;
                        bArr[i] = PAD;
                        bArr = this.buf;
                        i = this.pos;
                        this.pos = i + 1;
                        bArr[i] = PAD;
                        break;
                    case 2:
                        bArr = this.buf;
                        i = this.pos;
                        this.pos = i + 1;
                        bArr[i] = intToBase64[(this.x >> 10) & MASK_6BITS];
                        bArr = this.buf;
                        i = this.pos;
                        this.pos = i + 1;
                        bArr[i] = intToBase64[(this.x >> 4) & MASK_6BITS];
                        bArr = this.buf;
                        i = this.pos;
                        this.pos = i + 1;
                        bArr[i] = intToBase64[(this.x << 2) & MASK_6BITS];
                        bArr = this.buf;
                        i = this.pos;
                        this.pos = i + 1;
                        bArr[i] = PAD;
                        break;
                }
                if (this.lineLength > 0) {
                    System.arraycopy(this.lineSeparator, 0, this.buf, this.pos, this.lineSeparator.length);
                    this.pos += this.lineSeparator.length;
                    return;
                }
                return;
            }
            int i2 = 0;
            int inPos2 = inPos;
            while (i2 < inAvail) {
                if (this.buf == null || this.buf.length - this.pos < this.encodeSize) {
                    resizeBuf();
                }
                int i3 = this.modulus + 1;
                this.modulus = i3;
                this.modulus = i3 % 3;
                inPos = inPos2 + 1;
                int b = in[inPos2];
                if (b < 0) {
                    b += 256;
                }
                this.x = (this.x << 8) + b;
                if (this.modulus == 0) {
                    bArr = this.buf;
                    i = this.pos;
                    this.pos = i + 1;
                    bArr[i] = intToBase64[(this.x >> 18) & MASK_6BITS];
                    bArr = this.buf;
                    i = this.pos;
                    this.pos = i + 1;
                    bArr[i] = intToBase64[(this.x >> 12) & MASK_6BITS];
                    bArr = this.buf;
                    i = this.pos;
                    this.pos = i + 1;
                    bArr[i] = intToBase64[(this.x >> 6) & MASK_6BITS];
                    bArr = this.buf;
                    i = this.pos;
                    this.pos = i + 1;
                    bArr[i] = intToBase64[this.x & MASK_6BITS];
                    this.currentLinePos += 4;
                    if (this.lineLength > 0 && this.lineLength <= this.currentLinePos) {
                        System.arraycopy(this.lineSeparator, 0, this.buf, this.pos, this.lineSeparator.length);
                        this.pos += this.lineSeparator.length;
                        this.currentLinePos = 0;
                    }
                }
                i2++;
                inPos2 = inPos;
            }
            inPos = inPos2;
        }
    }

    void decode(byte[] in, int inPos, int inAvail) {
        if (!this.eof) {
            if (inAvail < 0) {
                this.eof = true;
            }
            int i = 0;
            int inPos2 = inPos;
            while (i < inAvail) {
                if (this.buf == null || this.buf.length - this.pos < this.decodeSize) {
                    resizeBuf();
                }
                inPos = inPos2 + 1;
                byte b = in[inPos2];
                byte[] bArr;
                int i2;
                if (b == PAD) {
                    this.x <<= 6;
                    switch (this.modulus) {
                        case 2:
                            this.x <<= 6;
                            bArr = this.buf;
                            i2 = this.pos;
                            this.pos = i2 + 1;
                            bArr[i2] = (byte) ((this.x >> 16) & 255);
                            break;
                        case 3:
                            bArr = this.buf;
                            i2 = this.pos;
                            this.pos = i2 + 1;
                            bArr[i2] = (byte) ((this.x >> 16) & 255);
                            bArr = this.buf;
                            i2 = this.pos;
                            this.pos = i2 + 1;
                            bArr[i2] = (byte) ((this.x >> 8) & 255);
                            break;
                    }
                    this.eof = true;
                    return;
                }
                if (b >= (byte) 0 && b < base64ToInt.length) {
                    int result = base64ToInt[b];
                    if (result >= 0) {
                        int i3 = this.modulus + 1;
                        this.modulus = i3;
                        this.modulus = i3 % 4;
                        this.x = (this.x << 6) + result;
                        if (this.modulus == 0) {
                            bArr = this.buf;
                            i2 = this.pos;
                            this.pos = i2 + 1;
                            bArr[i2] = (byte) ((this.x >> 16) & 255);
                            bArr = this.buf;
                            i2 = this.pos;
                            this.pos = i2 + 1;
                            bArr[i2] = (byte) ((this.x >> 8) & 255);
                            bArr = this.buf;
                            i2 = this.pos;
                            this.pos = i2 + 1;
                            bArr[i2] = (byte) (this.x & 255);
                        }
                    }
                }
                i++;
                inPos2 = inPos;
            }
            inPos = inPos2;
        }
    }

    public static boolean isBase64(byte octet) {
        return octet == PAD || (octet >= (byte) 0 && octet < base64ToInt.length && base64ToInt[octet] != (byte) -1);
    }

    public static boolean isArrayByteBase64(byte[] arrayOctet) {
        int i = 0;
        while (i < arrayOctet.length) {
            if (!isBase64(arrayOctet[i]) && !isWhiteSpace(arrayOctet[i])) {
                return false;
            }
            i++;
        }
        return true;
    }

    private static boolean containsBase64Byte(byte[] arrayOctet) {
        for (byte isBase64 : arrayOctet) {
            if (isBase64(isBase64)) {
                return true;
            }
        }
        return false;
    }

    public static byte[] encodeBase64(byte[] binaryData) {
        return encodeBase64(binaryData, false);
    }

    public static byte[] encodeBase64Chunked(byte[] binaryData) {
        return encodeBase64(binaryData, true);
    }

    public byte[] decode(byte[] pArray) {
        return decodeBase64(pArray);
    }

    public static byte[] encodeBase64(byte[] binaryData, boolean isChunked) {
        if (binaryData == null || binaryData.length == 0) {
            return binaryData;
        }
        Base64 b64;
        if (isChunked) {
            b64 = new Base64();
        } else {
            b64 = new Base64(0);
        }
        long len = (long) ((binaryData.length * 4) / 3);
        long mod = len % 4;
        if (mod != 0) {
            len += 4 - mod;
        }
        if (isChunked) {
            len += (((len + 76) - 1) / 76) * ((long) CHUNK_SEPARATOR.length);
        }
        if (len > 2147483647L) {
            throw new IllegalArgumentException("Input array too big, output array would be bigger than Integer.MAX_VALUE=2147483647");
        }
        byte[] buf = new byte[((int) len)];
        b64.setInitialBuffer(buf, 0, buf.length);
        b64.encode(binaryData, 0, binaryData.length);
        b64.encode(binaryData, 0, -1);
        if (b64.buf == buf) {
            return buf;
        }
        b64.readResults(buf, 0, buf.length);
        return buf;
    }

    public static byte[] decodeBase64(byte[] base64Data) {
        if (base64Data == null || base64Data.length == 0) {
            return base64Data;
        }
        Base64 b64 = new Base64();
        byte[] buf = new byte[((int) ((long) ((base64Data.length * 3) / 4)))];
        b64.setInitialBuffer(buf, 0, buf.length);
        b64.decode(base64Data, 0, base64Data.length);
        b64.decode(base64Data, 0, -1);
        byte[] result = new byte[b64.pos];
        b64.readResults(result, 0, result.length);
        return result;
    }

    static byte[] discardWhitespace(byte[] data) {
        byte[] groomedData = new byte[data.length];
        int bytesCopied = 0;
        for (int i = 0; i < data.length; i++) {
            switch (data[i]) {
                case (byte) 9:
                case (byte) 10:
                case (byte) 13:
                case (byte) 32:
                    break;
                default:
                    int bytesCopied2 = bytesCopied + 1;
                    groomedData[bytesCopied] = data[i];
                    bytesCopied = bytesCopied2;
                    break;
            }
        }
        byte[] packedData = new byte[bytesCopied];
        System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
        return packedData;
    }

    private static boolean isWhiteSpace(byte byteToCheck) {
        switch (byteToCheck) {
            case (byte) 9:
            case (byte) 10:
            case (byte) 13:
            case (byte) 32:
                return true;
            default:
                return false;
        }
    }

    static byte[] discardNonBase64(byte[] data) {
        byte[] groomedData = new byte[data.length];
        int bytesCopied = 0;
        for (int i = 0; i < data.length; i++) {
            if (isBase64(data[i])) {
                int bytesCopied2 = bytesCopied + 1;
                groomedData[bytesCopied] = data[i];
                bytesCopied = bytesCopied2;
            }
        }
        byte[] packedData = new byte[bytesCopied];
        System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
        return packedData;
    }

    public byte[] encode(byte[] pArray) {
        return encodeBase64(pArray, false);
    }

    public static BigInteger decodeInteger(byte[] pArray) {
        return new BigInteger(1, decodeBase64(pArray));
    }

    public static byte[] encodeInteger(BigInteger bigInt) {
        if (bigInt != null) {
            return encodeBase64(toIntegerBytes(bigInt), false);
        }
        throw new NullPointerException("encodeInteger called with null parameter");
    }

    static byte[] toIntegerBytes(BigInteger bigInt) {
        int bitlen = ((bigInt.bitLength() + 7) >> 3) << 3;
        byte[] bigBytes = bigInt.toByteArray();
        if (bigInt.bitLength() % 8 != 0 && (bigInt.bitLength() / 8) + 1 == bitlen / 8) {
            return bigBytes;
        }
        int startSrc = 0;
        int len = bigBytes.length;
        if (bigInt.bitLength() % 8 == 0) {
            startSrc = 1;
            len--;
        }
        byte[] resizedBytes = new byte[(bitlen / 8)];
        System.arraycopy(bigBytes, startSrc, resizedBytes, (bitlen / 8) - len, len);
        return resizedBytes;
    }
}
