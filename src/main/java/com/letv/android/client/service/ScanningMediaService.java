package com.letv.android.client.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.widget.Toast;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.task.LocalVideoScannerThread;
import com.letv.android.client.task.LocalVideoScannerThread.OnScannerListener;
import com.letv.core.bean.DownloadLocalVideoItemBean;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import org.cybergarage.upnp.std.av.server.object.item.ItemNode;

public class ScanningMediaService extends Service implements OnScannerListener {
    public static final int POSTEXECUTE = 2;
    public static final int PRESCANNER = 1;
    public static final int PROGRESSUPDATE = 3;
    public static final int SHOW_TOAST = 3;
    public static final int START_CMD = 1;
    public static final int STOP_CMD = 2;
    private static Object lock = new Object();
    LocalVideoScannerThread mScannerLocalVideoAsync;
    private Messenger messenger;

    public static class LetvToast {
        private static Toast mToast = null;

        public LetvToast() {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
        }

        public static void showToast(String text) {
            if (LetvApplication.getInstance() != null) {
                ToastUtils.showToast(LetvApplication.getInstance(), text);
            }
            new Handler().postDelayed(new 1(), 1000);
        }
    }

    public ScanningMediaService() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            LogInfo.log("onStartCommand", "intent == null>>");
            return super.onStartCommand(intent, flags, startId);
        }
        switch (intent.getIntExtra("cmd", 0)) {
            case 1:
                this.messenger = (Messenger) intent.getParcelableExtra("handler");
                this.mScannerLocalVideoAsync = new LocalVideoScannerThread(this);
                this.mScannerLocalVideoAsync.start();
                break;
            case 2:
                if (this.mScannerLocalVideoAsync != null) {
                    this.mScannerLocalVideoAsync.cancelTask(true);
                    this.mScannerLocalVideoAsync = null;
                }
                stopSelf();
                break;
            case 3:
                String tip = intent.getStringExtra("tip");
                LogInfo.log(" ", "showToast tip >>" + tip);
                LetvToast.showToast(tip);
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public static void stopScanningService() {
        LetvApplication.getInstance().stopService(new Intent(LetvApplication.getInstance(), ScanningMediaService.class));
    }

    public static void startScanningService() {
        LetvApplication.getInstance().startService(new Intent(LetvApplication.getInstance(), ScanningMediaService.class));
    }

    public static void startScanning(Context context, Handler handler) {
        Intent intent = new Intent(context, ScanningMediaService.class);
        intent.putExtra("cmd", 1);
        intent.putExtra("handler", new Messenger(handler));
        context.startService(intent);
    }

    public static void stopScanning(Context context) {
        Intent intent = new Intent(context, ScanningMediaService.class);
        intent.putExtra("cmd", 2);
        context.startService(intent);
    }

    public static void showToast(Context context, String tip) {
        LogInfo.log("", "showToast>><<<");
        Intent intent = new Intent(context, ScanningMediaService.class);
        intent.putExtra("cmd", 3);
        intent.putExtra("tip", tip);
        context.startService(intent);
    }

    private void sendData(ArrayList<DownloadLocalVideoItemBean> list, int cmd) {
        synchronized (lock) {
            if (this.messenger != null) {
                Message msg = Message.obtain();
                msg.what = cmd;
                Bundle data = new Bundle();
                data.putParcelableArrayList("list", list);
                msg.setData(data);
                try {
                    this.messenger.send(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendData(Parcelable obj, int cmd) {
        synchronized (lock) {
            if (this.messenger != null) {
                LogInfo.log("sendData", "sendData cmd : " + cmd + " obj : " + obj);
                Message msg = Message.obtain();
                Bundle data = new Bundle();
                data.putParcelable(ItemNode.NAME, obj);
                msg.setData(data);
                msg.what = cmd;
                try {
                    this.messenger.send(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendData(int cmd) {
        if (this.messenger != null) {
            Message msg = Message.obtain();
            msg.what = cmd;
            try {
                this.messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onPreScanner() {
        sendData(1);
    }

    public void onProgressUpdate(DownloadLocalVideoItemBean item) {
        LogInfo.log("onProgressUpdate", "onProgressUpdate>>");
        sendData((Parcelable) item, 3);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onPostExecute(ArrayList<DownloadLocalVideoItemBean> list) {
        sendData((ArrayList) list, 2);
        stopSelf();
    }
}
