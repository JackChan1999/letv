package rrrrrr;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.immersion.hapticmediasdk.controllers.FileReaderFactory;
import com.immersion.hapticmediasdk.controllers.HapticPlaybackThread;
import com.immersion.hapticmediasdk.controllers.IHapticFileReader;

public class crcrrr extends Handler {
    public static int b041504150415ЕЕЕ = 0;
    public static int b0415ЕЕ0415ЕЕ = 2;
    public static int bЕ04150415ЕЕЕ = 27;
    public static int bЕЕЕ0415ЕЕ = 1;
    public final /* synthetic */ HapticPlaybackThread b041EОО041E041E041E;

    private crcrrr(HapticPlaybackThread hapticPlaybackThread) {
        int i = 2;
        this.b041EОО041E041E041E = hapticPlaybackThread;
        while (true) {
            try {
                i /= 0;
            } catch (Exception e) {
                return;
            }
        }
    }

    public /* synthetic */ crcrrr(HapticPlaybackThread hapticPlaybackThread, ccrccr rrrrrr_ccrccr) {
        if (((bЕ04150415ЕЕЕ + bЕЕЕ0415ЕЕ) * bЕ04150415ЕЕЕ) % b0415ЕЕ0415ЕЕ != b041504150415ЕЕЕ) {
            bЕ04150415ЕЕЕ = bЕ0415Е0415ЕЕ();
            b041504150415ЕЕЕ = bЕ0415Е0415ЕЕ();
        }
        try {
            this(hapticPlaybackThread);
        } catch (Exception e) {
            throw e;
        }
    }

    public static int bЕ0415Е0415ЕЕ() {
        return 96;
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                HapticPlaybackThread.bй0439043904390439й(this.b041EОО041E041E041E).removeCallbacks(HapticPlaybackThread.b0439й043904390439й(this.b041EОО041E041E041E));
                HapticPlaybackThread.b04390439043904390439й(this.b041EОО041E041E041E, message.arg1);
                HapticPlaybackThread.bййййй0439(this.b041EОО041E041E041E, message.arg2);
                HapticPlaybackThread.b0439йййй0439(this.b041EОО041E041E041E, 0);
                HapticPlaybackThread.bй0439ййй0439(this.b041EОО041E041E041E);
                return;
            case 2:
                Bundle data = message.getData();
                HapticPlaybackThread.b04390439ййй0439(this.b041EОО041E041E041E, data.getInt("playback_timecode"), data.getLong("playback_uptime"));
                return;
            case 3:
                if (HapticPlaybackThread.bйй0439йй0439(this.b041EОО041E041E041E) == null) {
                    HapticPlaybackThread.b0439й0439йй0439(this.b041EОО041E041E041E, FileReaderFactory.getHapticFileReaderInstance(HapticPlaybackThread.bй04390439йй0439(this.b041EОО041E041E041E), HapticPlaybackThread.b043904390439йй0439(this.b041EОО041E041E041E), this.b041EОО041E041E041E.bю044Eю044E044E044E));
                }
                IHapticFileReader bйй0439йй0439 = HapticPlaybackThread.bйй0439йй0439(this.b041EОО041E041E041E);
                if (((bЕ04150415ЕЕЕ + bЕЕЕ0415ЕЕ) * bЕ04150415ЕЕЕ) % b0415ЕЕ0415ЕЕ != b041504150415ЕЕЕ) {
                    bЕ04150415ЕЕЕ = bЕ0415Е0415ЕЕ();
                    b041504150415ЕЕЕ = 2;
                }
                if (bйй0439йй0439 != null && HapticPlaybackThread.b0439йй0439й0439(this.b041EОО041E041E041E) == 0) {
                    HapticPlaybackThread.bй0439й0439й0439(this.b041EОО041E041E041E, HapticPlaybackThread.bйй0439йй0439(this.b041EОО041E041E041E).getBlockSizeMS());
                }
                if (HapticPlaybackThread.bйй0439йй0439(this.b041EОО041E041E041E) != null) {
                    HapticPlaybackThread.bйй0439йй0439(this.b041EОО041E041E041E).setBytesAvailable(message.arg1);
                    return;
                }
                return;
            case 4:
                HapticPlaybackThread.b04390439й0439й0439(this.b041EОО041E041E041E);
                return;
            case 5:
                HapticPlaybackThread.bйй04390439й0439(this.b041EОО041E041E041E);
                return;
            case 8:
                HapticPlaybackThread.b0439й04390439й0439(this.b041EОО041E041E041E, message);
                return;
            case 9:
                HapticPlaybackThread.bй043904390439й0439(this.b041EОО041E041E041E);
                return;
            default:
                return;
        }
    }
}
