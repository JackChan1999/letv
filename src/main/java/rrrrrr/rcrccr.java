package rrrrrr;

import com.immersion.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

public abstract class rcrccr {
    private static final String b042D042DЭ042D042D042D = "AbstractPolicyDownloadConnection";
    public static int b044A044A044Aъъ044A = 19;
    public static int b044A044Aъ044Aъ044A = 0;
    public static int b044Aъъ044Aъ044A = 2;
    public static int bъъъ044Aъ044A = 1;
    public URLConnection mConnection;
    public File mTargetLocation;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public rcrccr(java.io.File r3) {
        /*
        r2 = this;
        r0 = 1;
    L_0x0001:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0004;
        };
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0004;
    L_0x0008:
        r2.<init>();
        r0 = b044A044A044Aъъ044A;
        r1 = bъъъ044Aъ044A;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = b044Aъъ044Aъ044A;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0023;
            default: goto L_0x0017;
        };
    L_0x0017:
        r0 = bъ044Aъ044Aъ044A();
        b044A044A044Aъъ044A = r0;
        r0 = bъ044Aъ044Aъ044A();
        bъъъ044Aъ044A = r0;
    L_0x0023:
        r2.mTargetLocation = r3;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rcrccr.<init>(java.io.File):void");
    }

    public static int bъ044Aъ044Aъ044A() {
        return 16;
    }

    public abstract void b04490449щщ0449щ();

    public abstract boolean bщ0449щщ0449щ();

    public boolean bщщ0449щ0449щ() {
        Log.d(b042D042DЭ042D042D042D, "Policy File Length: " + this.mConnection.getContentLength());
        try {
            InputStream inputStream = this.mConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(this.mTargetLocation);
            if (((b044A044A044Aъъ044A + bъъъ044Aъ044A) * b044A044A044Aъъ044A) % b044Aъъ044Aъ044A != b044A044Aъ044Aъ044A) {
                b044A044A044Aъъ044A = 35;
                b044A044Aъ044Aъ044A = bъ044Aъ044Aъ044A();
            }
            byte[] bArr = new byte[1024];
            while (true) {
                try {
                    int read = inputStream.read(bArr);
                    if (read != -1) {
                        fileOutputStream.write(bArr, 0, read);
                    }
                } catch (IOException e) {
                    Log.e(b042D042DЭ042D042D042D, "Error downloading pm file");
                }
                try {
                    break;
                } catch (IOException e2) {
                    Log.e(b042D042DЭ042D042D042D, "Fail to close input/output stream");
                }
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            return true;
        } catch (FileNotFoundException e3) {
            Log.e(b042D042DЭ042D042D042D, "Target policy file is not found.");
            return false;
        } catch (IOException e4) {
            e4.printStackTrace();
            return false;
        }
    }
}
