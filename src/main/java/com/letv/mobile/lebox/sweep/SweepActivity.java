package com.letv.mobile.lebox.sweep;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.letv.mobile.http.utils.NetworkUtil;
import com.letv.mobile.http.utils.NetworkUtil.OnNetworkChangeListener;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.common.IFunction;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import com.letv.mobile.lebox.sweep.camera.CameraManager;
import com.letv.mobile.lebox.sweep.decoding.InactivityTimer;
import com.letv.mobile.lebox.sweep.result.ResultHandler;
import com.letv.mobile.lebox.sweep.result.ResultHandlerFactory;
import com.letv.mobile.lebox.sweep.view.ViewfinderView;
import com.letv.mobile.lebox.ui.qrcode.LeboxQrCodeBean;
import com.letv.mobile.lebox.utils.DialogUtils;
import com.letv.mobile.lebox.utils.HandlerUtils;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.SharedPreferencesUtil;
import com.letv.mobile.lebox.utils.Util;
import java.io.IOException;
import java.util.Vector;

public class SweepActivity extends Activity implements Callback, OnClickListener, OnNetworkChangeListener {
    private static final float BEEP_VOLUME = 0.1f;
    private static final String KEY_FIRST_ENTER_SWEEP = "is first enter in sweep";
    private static final String SWEEP_FIRST_ENTER = "sweep setting";
    private static final String TAG = SweepActivity.class.getSimpleName();
    private static final long VIBRATE_DURATION = 200;
    private static boolean sIsFirstEnterSweep = SharedPreferencesUtil.readData(KEY_FIRST_ENTER_SWEEP, false);
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
    private String characterSet;
    private Vector<BarcodeFormat> decodeFormats;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private Context mContext;
    private boolean mIsHelpViewShow = false;
    private boolean mIsNoNetTipShow = false;
    private boolean mIsPermissionTipShow = false;
    private String mLastObjString;
    private ImageView mNavigationBack;
    private ImageView mNavigationHelp;
    private ImageView mNoNetWorkBackground;
    private ImageView mNoNetWorkSign;
    private TextView mNoNetWorkTip;
    private ImageView mNoPermissionBackground;
    private TextView mNoPermissionTip1;
    private TextView mNoPermissionTip2;
    private boolean mNotToSweep;
    private SweepHelpView mSweepHelpView;
    private TextView mTopTitle;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private SurfaceView surfaceView;
    private boolean vibrate;
    private ViewfinderView viewfinderView;
    private RelativeLayout zxing_top;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        this.hasSurface = false;
        initView();
        initViewData();
    }

    private void initViewData() {
        if (sIsFirstEnterSweep) {
            HandlerUtils.getUiThreadHandler().postDelayed(new Runnable() {
                public void run() {
                    SweepActivity.this.OpenHelpWindows();
                }
            }, VIBRATE_DURATION);
            sIsFirstEnterSweep = false;
            SharedPreferencesUtil.writeData(KEY_FIRST_ENTER_SWEEP, sIsFirstEnterSweep);
        }
    }

    @SuppressLint({"ResourceAsColor"})
    private void initView() {
        setContentView(R.layout.activity_sweep);
        Logger.e("CaptureActivity", "oncreate()");
        this.zxing_top = (RelativeLayout) findViewById(R.id.id_sweep_head_layout);
        this.viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        this.viewfinderView.setBackgroundColor(getResources().getColor(R.color.letv_color_00ffffff));
        this.mTopTitle = (TextView) findViewById(R.id.common_nav_title);
        this.mTopTitle.setText(R.string.discovery_sweep);
        this.mNavigationBack = (ImageView) findViewById(R.id.common_nav_left);
        this.mNavigationBack.setOnClickListener(this);
        this.mNavigationHelp = (ImageView) findViewById(R.id.common_nav_right);
        this.mNavigationHelp.setVisibility(4);
        this.mNavigationHelp.setBackgroundResource(R.drawable.sweep_help_selector);
        this.mNavigationHelp.setOnClickListener(this);
        this.mNoNetWorkSign = (ImageView) findViewById(R.id.id_sweep_no_network_sign);
        this.mNoNetWorkTip = (TextView) findViewById(R.id.id_sweep_no_network_tip);
        this.mNoPermissionTip1 = (TextView) findViewById(R.id.id_sweep_no_permission_tip_1);
        this.mNoPermissionTip2 = (TextView) findViewById(R.id.id_sweep_no_permission_tip_2);
        this.mNoNetWorkBackground = (ImageView) findViewById(R.id.id_sweep_no_network_background);
        this.mNoPermissionBackground = (ImageView) findViewById(R.id.id_sweep_no_permission_background);
    }

    protected void onResume() {
        super.onResume();
        CameraManager.init(this.mContext);
        this.inactivityTimer = new InactivityTimer(this);
        initCapture();
        initBeepSound();
        closeNoNewworkTip();
        this.vibrate = true;
        this.mLastObjString = null;
    }

    private void initCapture() {
        this.surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = this.surfaceView.getHolder();
        if (this.hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(3);
        }
        this.decodeFormats = null;
        this.characterSet = null;
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
            closePermissionTip();
            if (this.handler == null) {
                this.handler = new CaptureActivityHandler(this, this.decodeFormats, this.characterSet);
            }
        } catch (IOException e) {
            openPermissionTip();
            if (this.mIsNoNetTipShow) {
                closeNoNewworkTip();
            }
        } catch (RuntimeException e2) {
        }
    }

    protected void onPause() {
        super.onPause();
        if (this.handler != null) {
            this.handler.quitSynchronously();
            this.handler = null;
        }
        CameraManager.get().closeDriver();
        this.surfaceView = null;
    }

    protected void onDestroy() {
        if (this.inactivityTimer != null) {
            this.inactivityTimer.shutdown();
        }
        this.surfaceView = null;
        NetworkUtil.unregisterNetworkChangeListener(this);
        super.onDestroy();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (!this.hasSurface) {
            this.hasSurface = true;
            initCamera(holder);
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return this.viewfinderView;
    }

    public Handler getHandler() {
        return this.handler;
    }

    public void drawViewfinder() {
        this.viewfinderView.drawViewfinder();
    }

    public void handleDecode(Result obj, Bitmap barcode) {
        if (!this.mNotToSweep) {
            this.inactivityTimer.onActivity();
            ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, obj);
            if (resultHandler != null) {
                int result = resultHandler.handleDecode();
                if (1 == result) {
                    if (!obj.toString().equals(this.mLastObjString)) {
                        Util.showToast(this.mContext, R.string.discovery_sweep_sweep_error);
                        playBeepSoundAndVibrate();
                    }
                    this.mLastObjString = obj.toString();
                    initSweep();
                } else if (2 == result) {
                    if (!obj.toString().equals(this.mLastObjString)) {
                        Util.showToast(this.mContext, R.string.discovery_sweep_no_login);
                        playBeepSoundAndVibrate();
                    }
                    this.mLastObjString = obj.toString();
                    initSweep();
                } else {
                    playBeepSoundAndVibrate();
                    finish();
                }
            }
        }
    }

    private void initBeepSound() {
        this.playBeep = true;
        if (((AudioManager) getSystemService("audio")).getRingerMode() != 2) {
            this.playBeep = false;
        }
        if (this.playBeep && this.mediaPlayer == null) {
            setVolumeControlStream(3);
            this.mediaPlayer = new MediaPlayer();
            this.mediaPlayer.setAudioStreamType(3);
            this.mediaPlayer.setOnCompletionListener(this.beepListener);
            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                this.mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                this.mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                this.mediaPlayer.prepare();
            } catch (IOException e) {
                this.mediaPlayer = null;
            }
        }
    }

    private void playBeepSoundAndVibrate() {
        if (this.playBeep && this.mediaPlayer != null) {
            this.mediaPlayer.start();
        }
        if (this.vibrate) {
            ((Vibrator) getSystemService("vibrator")).vibrate(VIBRATE_DURATION);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 25) {
            CameraManager.get().zoomIn();
            return true;
        } else if (keyCode == 24) {
            CameraManager.get().zoomOut();
            return true;
        } else if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        } else {
            if (TextUtils.isEmpty(LeboxQrCodeBean.getSsid()) || TextUtils.isEmpty(LeboxQrCodeBean.getPassword())) {
                PageJumpUtil.jumpToIntroduce(this);
            } else {
                PageJumpUtil.jumpLeBoxMainActivity(this);
            }
            finish();
            return true;
        }
    }

    public void onClick(View v) {
        if (v == this.mNavigationBack) {
            if (TextUtils.isEmpty(LeboxQrCodeBean.getSsid()) || TextUtils.isEmpty(LeboxQrCodeBean.getPassword())) {
                PageJumpUtil.jumpToIntroduce(this);
            } else {
                PageJumpUtil.jumpLeBoxMainActivity(this);
            }
            finish();
        } else if (v == this.mNavigationHelp) {
            OpenHelpWindows();
        }
    }

    private void OpenHelpWindows() {
        this.mNotToSweep = true;
        View mDialogView = getLayoutInflater().inflate(R.layout.activity_sweep_help_viewpager, null, false);
        this.mSweepHelpView = new SweepHelpView(mDialogView);
        this.mSweepHelpView.initView();
        final PopupWindow helpPopup = DialogUtils.dialog(mDialogView, 0, 0, 0, 0, -1, -1);
        this.mIsHelpViewShow = true;
        this.mSweepHelpView.setOnCloseListener(new IFunction<Void>() {
            public Void get() {
                SweepActivity.this.mIsHelpViewShow = false;
                SweepActivity.this.initSweep();
                DialogUtils.closeDialog(helpPopup);
                SweepActivity.this.mLastObjString = null;
                if (!NetworkUtil.isNetAvailable()) {
                    SweepActivity.this.mNotToSweep = true;
                    if (!SweepActivity.this.mIsPermissionTipShow) {
                        SweepActivity.this.openNoNewworkTip();
                    }
                }
                return null;
            }
        });
    }

    public void onBackPressed() {
        if (this.mIsHelpViewShow) {
            this.mSweepHelpView.mCallback.get();
        } else {
            super.onBackPressed();
        }
    }

    public void onNetworkChanged() {
        if (NetworkUtil.isNetAvailable()) {
            initSweep();
            closeNoNewworkTip();
            return;
        }
        this.mNotToSweep = true;
        if (!this.mIsPermissionTipShow) {
            openNoNewworkTip();
        }
    }

    public void initSweep() {
        this.mNotToSweep = false;
        this.handler = null;
        initCapture();
        initBeepSound();
        this.vibrate = true;
    }

    private void closeNoNewworkTip() {
        this.mIsNoNetTipShow = false;
        this.mNoNetWorkBackground.setVisibility(8);
        this.mNoNetWorkSign.setVisibility(8);
        this.mNoNetWorkTip.setVisibility(8);
    }

    private void openNoNewworkTip() {
        this.mIsNoNetTipShow = true;
        this.mNoNetWorkBackground.setVisibility(0);
        this.mNoNetWorkSign.setVisibility(0);
        this.mNoNetWorkTip.setVisibility(0);
    }

    private void openPermissionTip() {
        this.mIsPermissionTipShow = true;
        this.mNoPermissionBackground.setVisibility(0);
        this.mNoPermissionTip1.setVisibility(0);
        this.mNoPermissionTip2.setVisibility(0);
    }

    private void closePermissionTip() {
        this.mIsPermissionTipShow = false;
        this.mNoPermissionBackground.setVisibility(8);
        this.mNoPermissionTip1.setVisibility(8);
        this.mNoPermissionTip2.setVisibility(8);
    }
}
