package rrrrrr;

import com.immersion.aws.tvm.TVMAuthenticator;
import com.immersion.hapticmediasdk.HapticContentSDK;
import org.apache.http.auth.AuthenticationException;

public class rrrrrc implements Runnable {
    public static int b0417041704170417ЗЗ = 74;
    public static int b0417ЗЗЗ0417З = 1;
    public static int bЗ041704170417ЗЗ = 2;
    public static int bЗЗЗЗ0417З;
    public final /* synthetic */ TVMAuthenticator b043Dн043Dннн;
    public final /* synthetic */ HapticContentSDK bн043D043Dннн;

    public rrrrrc(HapticContentSDK hapticContentSDK, TVMAuthenticator tVMAuthenticator) {
        int bЗЗ04170417ЗЗ = bЗЗ04170417ЗЗ();
        switch ((bЗЗ04170417ЗЗ * (b0417З04170417ЗЗ() + bЗЗ04170417ЗЗ)) % bЗ041704170417ЗЗ) {
            case 0:
                break;
            default:
                bЗ041704170417ЗЗ = bЗЗ04170417ЗЗ();
                break;
        }
        try {
            this.bн043D043Dннн = hapticContentSDK;
            try {
                this.b043Dн043Dннн = tVMAuthenticator;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static int b0417З04170417ЗЗ() {
        return 1;
    }

    public static int bЗЗ04170417ЗЗ() {
        return 65;
    }

    public void run() {
        try {
            this.b043Dн043Dннн.authenticate();
            if (((b0417041704170417ЗЗ + b0417ЗЗЗ0417З) * b0417041704170417ЗЗ) % bЗ041704170417ЗЗ != bЗЗЗЗ0417З) {
                b0417041704170417ЗЗ = bЗЗ04170417ЗЗ();
                bЗЗЗЗ0417З = bЗЗ04170417ЗЗ();
            }
        } catch (AuthenticationException e) {
            try {
                e.printStackTrace();
            } catch (Exception e2) {
                throw e2;
            }
        }
    }
}
