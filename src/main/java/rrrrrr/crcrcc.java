package rrrrrr;

import com.immersion.hapticmediasdk.controllers.MediaController;

public class crcrcc implements Runnable {
    public static int b043B043B043Bлл043B = 25;
    public static int b043Bлл043Bл043B = 2;
    public static int bллл043Bл043B = 1;
    public final /* synthetic */ MediaController b0414ДД041404140414;

    public crcrcc(MediaController mediaController) {
        try {
            this.b0414ДД041404140414 = mediaController;
            try {
                while (true) {
                    try {
                        int[] iArr = new int[-1];
                    } catch (Exception e) {
                        return;
                    }
                }
            } catch (Exception e2) {
                throw e2;
            }
        } catch (Exception e22) {
            throw e22;
        }
    }

    public static int bл043Bл043Bл043B() {
        return 83;
    }

    public void run() {
        try {
            if (this.b0414ДД041404140414.isPlaying() && MediaController.b04490449щщщ0449(this.b0414ДД041404140414) != null) {
                MediaController mediaController = this.b0414ДД041404140414;
                int i = b043B043B043Bлл043B;
                switch ((i * (bллл043Bл043B + i)) % b043Bлл043Bл043B) {
                    case 0:
                        break;
                    default:
                        b043B043B043Bлл043B = 95;
                        bллл043Bл043B = bл043Bл043Bл043B();
                        break;
                }
                MediaController.b04490449щщщ0449(mediaController).syncUpdate(this.b0414ДД041404140414.getCurrentPosition(), this.b0414ДД041404140414.getReferenceTimeForCurrentPosition());
                try {
                    MediaController.b04490449щщщ0449(this.b0414ДД041404140414).getHandler().removeCallbacks(this);
                    MediaController.b04490449щщщ0449(this.b0414ДД041404140414).getHandler().postDelayed(this, 1000);
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
