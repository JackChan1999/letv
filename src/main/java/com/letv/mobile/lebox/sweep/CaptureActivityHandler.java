package com.letv.mobile.lebox.sweep;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.letv.mobile.lebox.sweep.camera.CameraManager;
import com.letv.mobile.lebox.sweep.decoding.DecodeThread;
import com.letv.mobile.lebox.sweep.view.ViewfinderResultPointCallback;
import com.letv.mobile.lebox.utils.Logger;
import java.util.Vector;

public final class CaptureActivityHandler extends Handler {
    private static final String TAG = CaptureActivityHandler.class.getSimpleName();
    public static final int auto_focus = 10;
    public static final int decode = 16;
    public static final int decode_failed = 13;
    public static final int decode_succeeded = 12;
    public static final int launch_product_query = 15;
    public static final int quit = 17;
    public static final int restart_preview = 11;
    public static final int return_scan_result = 14;
    private final SweepActivity activity;
    private final DecodeThread decodeThread;
    private State state = State.SUCCESS;

    public CaptureActivityHandler(SweepActivity activity, Vector<BarcodeFormat> decodeFormats, String characterSet) {
        this.activity = activity;
        this.decodeThread = new DecodeThread(activity, decodeFormats, characterSet, new ViewfinderResultPointCallback(activity.getViewfinderView()));
        this.decodeThread.start();
        CameraManager.get().startPreview();
        restartPreviewAndDecode();
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 10:
                if (this.state == State.PREVIEW) {
                    CameraManager.get().requestAutoFocus(this, 10);
                    return;
                }
                return;
            case 11:
                Logger.d(TAG, "Got restart preview message");
                restartPreviewAndDecode();
                return;
            case 12:
                Bitmap barcode;
                Logger.d(TAG, "Got decode succeeded message");
                this.state = State.SUCCESS;
                Bundle bundle = message.getData();
                if (bundle == null) {
                    barcode = null;
                } else {
                    barcode = (Bitmap) bundle.getParcelable("barcode_bitmap");
                }
                this.activity.handleDecode((Result) message.obj, barcode);
                return;
            case 13:
                this.state = State.PREVIEW;
                CameraManager.get().requestPreviewFrame(this.decodeThread.getHandler(), 16);
                return;
            case 14:
                Logger.d(TAG, "Got return scan result message");
                this.activity.setResult(-1, (Intent) message.obj);
                this.activity.finish();
                return;
            case 15:
                Logger.d(TAG, "Got product query message");
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(message.obj));
                intent.addFlags(524288);
                this.activity.startActivity(intent);
                return;
            default:
                return;
        }
    }

    public void quitSynchronously() {
        this.state = State.DONE;
        CameraManager.get().stopPreview();
        if (this.decodeThread.getHandler() != null) {
            Message.obtain(this.decodeThread.getHandler(), 17).sendToTarget();
        }
        try {
            this.decodeThread.join();
        } catch (InterruptedException e) {
        }
        removeMessages(12);
        removeMessages(13);
    }

    private void restartPreviewAndDecode() {
        if (this.state == State.SUCCESS) {
            this.state = State.PREVIEW;
            CameraManager.get().requestPreviewFrame(this.decodeThread.getHandler(), 16);
            CameraManager.get().requestAutoFocus(this, 10);
            this.activity.drawViewfinder();
        }
    }
}
