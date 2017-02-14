package cn.com.iresearch.vvtracker.a;

import com.letv.datastatistics.util.DEs;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class a {
    private static byte[] a = new byte[]{(byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8};

    public static String a(String str, String str2) {
        AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(a);
        Key secretKeySpec = new SecretKeySpec(str.getBytes(), "DES");
        Cipher instance = Cipher.getInstance(DEs.ALGORITHM_DES);
        instance.init(1, secretKeySpec, ivParameterSpec);
        return cn.com.iresearch.vvtracker.a.a.a.a(instance.doFinal(str2.getBytes()));
    }
}
