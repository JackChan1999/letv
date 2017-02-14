package cn.qguang.signal;

import android.media.AudioRecord;
import android.media.AudioRecord.OnRecordPositionUpdateListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.soundink.lib.c.b;

public class SignalCheck {
    private static int c = 44100;
    private Thread a = null;
    private Thread b;
    private int d = ((c * 25) / 10);
    private int e = (this.d * 6);
    private byte[] f = new byte[this.e];
    private int g = 0;
    private AudioRecord h = null;
    private OnRecordPositionUpdateListener i = null;
    private int j = 0;
    private boolean k = false;
    private short[] l = new short[this.d];
    private int m;
    private int n;
    private int o = 0;
    private int p = 0;
    private int q;
    private Handler r;
    private Handler s = new 1(this);

    public native void ResetMQMethod(int i);

    public native void SetMQMethod(int i);

    public native long analysisSignal(short[] sArr, int i, int i2);

    public native void detectdestroy();

    static {
        System.loadLibrary("algorithm");
    }

    public final void a() {
        if (this.k) {
            if (!(this.b == null || !this.b.isAlive() || this.b.isInterrupted())) {
                this.b.interrupt();
            }
            this.k = false;
        }
        this.r.sendEmptyMessage(257);
        d();
        ResetMQMethod(1);
    }

    private void d() {
        this.o = 0;
        this.p = 0;
        this.g = 0;
        this.f = new byte[this.e];
        this.l = new short[this.d];
    }

    public final void b() {
        if (this.k) {
            if (!(this.b == null || !this.b.isAlive() || this.b.isInterrupted())) {
                this.b.interrupt();
            }
            this.k = false;
            d();
            detectdestroy();
        }
    }

    public final void a(Handler handler) {
        b.a("start checking");
        if (!this.k) {
            this.i = new 2(this);
            this.b = new 3(this);
            this.b.start();
            this.k = true;
        }
        this.r = handler;
        this.r.sendEmptyMessage(256);
    }

    static /* synthetic */ void a(SignalCheck signalCheck, Message message) {
        message.what = 0;
        Bundle bundle = new Bundle();
        if (signalCheck.n == 1 || signalCheck.n == 3) {
            signalCheck.n = -1;
            signalCheck.m = -1;
        }
        bundle.putInt("kukid", signalCheck.m);
        bundle.putInt("kukid_type", signalCheck.n);
        message.setData(bundle);
        signalCheck.r.handleMessage(message);
        if (signalCheck.m < 0) {
            b.a("kukid is no exists");
            return;
        }
        b.a("kukid is the " + (signalCheck.q == signalCheck.m ? "same" : "different"));
        signalCheck.q = signalCheck.m;
    }

    static /* synthetic */ short[] a(SignalCheck signalCheck, byte[] bArr, int i, int i2) {
        short[] sArr = new short[i2];
        int i3 = 0;
        while (i3 < i2 && 0 + i3 < bArr.length - 1) {
            int i4 = i3 * 2;
            sArr[i3] = (short) ((bArr[i4] & 255) | (bArr[i4 + 1] << 8));
            i3++;
        }
        return sArr;
    }
}
