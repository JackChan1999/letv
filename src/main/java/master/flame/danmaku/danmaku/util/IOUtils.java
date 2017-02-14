package master.flame.danmaku.danmaku.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
    public static String getString(InputStream in) {
        byte[] data = getBytes(in);
        return data == null ? null : new String(data);
    }

    public static byte[] getBytes(InputStream in) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            while (true) {
                int len = in.read(buffer);
                if (len != -1) {
                    baos.write(buffer, 0, len);
                } else {
                    in.close();
                    return baos.toByteArray();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static void closeQuietly(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
            }
        }
    }
}
