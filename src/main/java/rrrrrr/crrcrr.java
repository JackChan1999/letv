package rrrrrr;

import com.immersion.aws.tvm.Token;
import com.immersion.aws.tvm.Token.Type;
import com.letv.core.messagebus.config.LeMessageIds;

public class crrcrr extends rccrcr {
    public static int b04150415Е041504150415 = 2;
    public static int b0415ЕЕ041504150415 = 0;
    public static int bЕ0415Е041504150415 = 1;
    public static int bЕЕЕ041504150415 = 1;
    private final String bЛ041BЛ041B041BЛ;

    public crrcrr(String str) {
        this.bЛ041BЛ041B041BЛ = str;
    }

    public static int b0415Е0415041504150415() {
        return 1;
    }

    public static int bЕЕ0415041504150415() {
        return 83;
    }

    public rrcrcc handleResponse(int i, String str) {
        if (i != 200) {
            return new rrrccr(i, str);
        }
        try {
            String unwrap = rrrccc.unwrap(str, this.bЛ041BЛ041B041BЛ.substring(0, 64));
            String b044C044Cьь044Cь = rcrrrr.b044C044Cьь044Cь(unwrap, "policyURL");
            String b044C044Cьь044Cь2 = rcrrrr.b044C044Cьь044Cь(unwrap, "policyFileChecksum");
            String b044C044Cьь044Cь3 = rcrrrr.b044C044Cьь044Cь(unwrap, "accessKeyST");
            if (((bЕЕЕ041504150415 + b0415Е0415041504150415()) * bЕЕЕ041504150415) % b04150415Е041504150415 != b0415ЕЕ041504150415) {
                bЕЕЕ041504150415 = 25;
                b0415ЕЕ041504150415 = 21;
            }
            String b044C044Cьь044Cь4 = rcrrrr.b044C044Cьь044Cь(unwrap, "secretKeyST");
            String b044C044Cьь044Cь5 = rcrrrr.b044C044Cьь044Cь(unwrap, "securityTokenST");
            String b044C044Cьь044Cь6 = rcrrrr.b044C044Cьь044Cь(unwrap, "expirationDateST");
            String b044C044Cьь044Cь7 = rcrrrr.b044C044Cьь044Cь(unwrap, "accessKeyLT");
            String b044C044Cьь044Cь8 = rcrrrr.b044C044Cьь044Cь(unwrap, "secretKeyLT");
            String b044C044Cьь044Cь9 = rcrrrr.b044C044Cьь044Cь(unwrap, "securityTokenLT");
            unwrap = rcrrrr.b044C044Cьь044Cь(unwrap, "expirationDateLT");
            Token bьььь044Cь = Token.bьььь044Cь(Type.SHORT_TERM);
            bьььь044Cь.b044Cььь044Cь(b044C044Cьь044Cь3, b044C044Cьь044Cь4, b044C044Cьь044Cь5, b044C044Cьь044Cь6);
            bьььь044Cь.setPolicyFileData(b044C044Cьь044Cь, b044C044Cьь044Cь2);
            Token bьььь044Cь2 = Token.bьььь044Cь(Type.LONG_TERM);
            bьььь044Cь2.b044Cььь044Cь(b044C044Cьь044Cь7, b044C044Cьь044Cь8, b044C044Cьь044Cь9, unwrap);
            bьььь044Cь2.setPolicyFileData(b044C044Cьь044Cь, b044C044Cьь044Cь2);
            return new rrrccr(bьььь044Cь2, bьььь044Cь);
        } catch (Exception e) {
            try {
                return new rrrccr((int) LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA, e.getMessage() == null ? "Exception: null message in LoginResponseHandler" : e.getMessage());
            } catch (Exception e2) {
                throw e2;
            }
        }
    }
}
