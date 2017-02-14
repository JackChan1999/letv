package com.letv.android.client.dlna.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.letv.android.client.commonlib.messagemodel.DLNAProtocol;
import com.letv.android.client.commonlib.messagemodel.DLNAToPlayerProtocol;
import com.letv.android.client.commonlib.view.LoadingDialog;
import com.letv.android.client.dlna.R;
import com.letv.android.client.dlna.engine.DLNAContainer;
import com.letv.android.client.dlna.engine.DLNAContainer.DeviceChangeListener;
import com.letv.android.client.dlna.engine.MultiPointController;
import com.letv.android.client.dlna.inter.DLNAListener;
import com.letv.android.client.dlna.inter.IDLNAController;
import com.letv.android.client.dlna.service.DLNAService;
import com.letv.android.client.dlna.utils.DLNAUtil;
import com.letv.android.client.dlna.utils.DLNAUtil.DLNATransportState;
import com.letv.android.client.dlna.view.DLNAPublicPlayingView;
import com.letv.core.BaseApplication;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.cybergarage.upnp.Device;

public abstract class DLNAController implements DLNAListener, DLNAProtocol {
    private static final String NOT_IMPLEMENTED = "NOT_IMPLEMENTED";
    private static final int PUSH_TIME_OUT = 30000;
    private static final int RETRY_TIME = 1000;
    private static final int SEARCH_TIME_OUT = 10000;
    protected Context mContext;
    private IDLNAController mController;
    private String mCurrPlayingUrl;
    private String mCurrentPosition;
    private DLNADevicesDialogHelper mDevicesDialog;
    protected Handler mHandler;
    protected boolean mIsAlbum;
    protected boolean mIsPlayingDlna;
    public boolean mIsPlayingNext;
    protected boolean mIsRetry;
    protected boolean mIsSearchOtherDevices;
    private String mLastTransportState;
    private int mMediaDuration;
    private Timer mMonitorStateTimer;
    protected Device mPlayingDevice;
    protected DLNAPublicPlayingView mPlayingView;
    protected int mPosition;
    private Timer mProgressTimer;
    private LoadingDialog mPushingDialog;
    private LoadingDialog mSearchDialog;
    private Timer mSearchTimer;
    protected int mSeek;
    public DLNAState mState;
    private Dialog mTimeOutDialog;
    protected DLNAToPlayerProtocol mToPlayerProtocol;

    public enum DLNAState {
        IDLE,
        SEARCHING,
        CONNECTING,
        PLAYING,
        PAUSE
    }

    protected abstract int getVideoDuration();

    protected abstract void onPause();

    protected abstract void onProcess(int i);

    protected abstract void onStart();

    protected abstract void onStartPlay();

    protected abstract void onStopPlay(boolean z);

    protected abstract String syncGetPlayUrl(Device device);

    public DLNAController(Context context) {
        this(context, null);
    }

    public DLNAController(Context context, View root) {
        this.mState = DLNAState.IDLE;
        this.mIsAlbum = false;
        this.mCurrPlayingUrl = "";
        this.mHandler = new Handler();
        this.mContext = context;
        DLNAContainer.getInstance().setDeviceChangeListener(new DeviceChangeListener() {
            public void onDeviceChange(Device device) {
                if (DLNAController.this.mState == DLNAState.SEARCHING) {
                    DLNAController.this.mHandler.post(new Runnable() {
                        public void run() {
                            if (!BaseTypeUtils.isListEmpty(DLNAContainer.getInstance().getDevices())) {
                                DLNAController.this.dismissSearchDialog();
                                DLNAController.this.dismissTimeOutDialog();
                                DLNAController.this.mDevicesDialog.refreshAdapter(DLNAContainer.getInstance().getDevices());
                            }
                        }
                    });
                }
            }
        });
        this.mController = new MultiPointController();
        if (this.mContext instanceof Activity) {
            if (root != null) {
                this.mPlayingView = new DLNAPublicPlayingView(root.findViewById(R.id.dlna_playing_root), this);
            } else {
                this.mPlayingView = new DLNAPublicPlayingView(((Activity) this.mContext).findViewById(R.id.dlna_playing_root), this);
            }
        }
        DLNAContainer.getInstance().clear();
        this.mDevicesDialog = new DLNADevicesDialogHelper(this.mContext, this);
    }

    public void protocolStart(int seek) {
        this.mSeek = seek;
        play(getDevice(), true);
    }

    public void protocolStop(boolean isActive, boolean needStopTV) {
        stop(isActive, needStopTV);
    }

    public void protocolDisconnect() {
        if (this.mState == DLNAState.PLAYING) {
            LogInfo.log("dlna", "DLNA正在播放中，网络WLAN断开了");
            ToastUtils.showToast(this.mContext.getString(R.string.dlna_phone_network_disconnected));
            stop(true, false);
        }
    }

    public void protocolPlayNext() {
        playNext();
    }

    public void protocolPlayOther() {
        stop(false, true);
    }

    public void protocolClickPauseOrPlay() {
        if (this.mState == DLNAState.PLAYING) {
            pause();
        } else {
            start();
        }
    }

    public void protocolStopTracking(int seek) {
        getProgress();
        seek(seek);
    }

    public void protocolStartTracking() {
        stopProgressTimer();
    }

    public void protocolSearch() {
        startSearch(true);
    }

    public void protocolSeek(int seek) {
        seek(seek);
    }

    public boolean isPlayingDlna() {
        return this.mIsPlayingDlna;
    }

    public void protocolDestory() {
        stopDLNAService();
    }

    private void startDLNAService() {
        BaseApplication.getInstance().startService(new Intent(BaseApplication.getInstance(), DLNAService.class));
    }

    public void stopDLNAService() {
        stopProgressTimer();
        cancelMinitorState();
        BaseApplication.getInstance().stopService(new Intent(BaseApplication.getInstance(), DLNAService.class));
        DLNAContainer.getInstance().setDeviceChangeListener(null);
        this.mToPlayerProtocol = null;
    }

    public void startSearch(boolean isFirstSearch) {
        boolean z;
        LogInfo.log("dlna", "isFirstSearch = " + isFirstSearch);
        if (isFirstSearch) {
            this.mState = DLNAState.SEARCHING;
        }
        if (isFirstSearch) {
            z = false;
        } else {
            z = true;
        }
        this.mIsSearchOtherDevices = z;
        LogInfo.log("dlna", "mIsSearchOtherDevices = " + this.mIsSearchOtherDevices);
        List<Device> list = DLNAContainer.getInstance().getDevices();
        if (this.mIsSearchOtherDevices) {
            LogInfo.log("dlna", "list size = " + list.size());
            if (BaseTypeUtils.isListEmpty(list) || list.size() == 1) {
                showSearchDialog();
                startTimeOutTimer(true);
            } else {
                this.mDevicesDialog.refreshAdapter(list);
                this.mIsSearchOtherDevices = false;
            }
        } else {
            LogInfo.log("dlna", "isListEmpty 分支");
            DLNAContainer.getInstance().clear();
            showSearchDialog();
            startTimeOutTimer(true);
        }
        startDLNAService();
    }

    private void stopSearch() {
        Intent intent = new Intent(BaseApplication.getInstance(), DLNAService.class);
        intent.putExtra(DLNAService.SEARCH_KEY, "stop");
        BaseApplication.getInstance().startService(intent);
    }

    private void startTimeOutTimer(final boolean isNoMedia) {
        cancelTimeOutTimer();
        this.mSearchTimer = new Timer();
        this.mSearchTimer.schedule(new TimerTask() {
            public void run() {
                if (DLNAController.this.mIsSearchOtherDevices) {
                    DLNAController.this.mIsSearchOtherDevices = false;
                    DLNAController.this.mHandler.post(new Runnable() {
                        public void run() {
                            DLNAController.this.dismissSearchDialog();
                            DLNAController.this.mDevicesDialog.refreshAdapter(DLNAContainer.getInstance().getDevices());
                        }
                    });
                } else if (isNoMedia) {
                    DLNAController.this.stopSearch();
                    if (BaseTypeUtils.isListEmpty(DLNAContainer.getInstance().getDevices())) {
                        DLNAController.this.mHandler.post(new Runnable() {
                            public void run() {
                                DLNAController.this.showTimeOutDialog(true);
                            }
                        });
                        DLNAController.this.mState = DLNAState.IDLE;
                    }
                } else if (DLNAController.this.mState == DLNAState.CONNECTING) {
                    DLNAController.this.mHandler.post(new Runnable() {
                        public void run() {
                            DLNAController.this.showTimeOutDialog(false);
                        }
                    });
                }
            }
        }, isNoMedia ? 10000 : 30000);
    }

    protected void cancelTimeOutTimer() {
        if (this.mSearchTimer != null) {
            this.mSearchTimer.cancel();
            this.mSearchTimer = null;
        }
    }

    public void play(Device device) {
        play(device, false);
    }

    public void play(final Device device, boolean forcePlay) {
        if (device != null) {
            if (forcePlay || this.mPlayingDevice == null || !this.mPlayingDevice.getUDN().equalsIgnoreCase(device.getUDN()) || this.mState != DLNAState.PLAYING) {
                boolean shouldStopPrePushing;
                if (this.mPlayingDevice == null || this.mPlayingDevice.getUDN().equalsIgnoreCase(device.getUDN())) {
                    shouldStopPrePushing = false;
                } else {
                    shouldStopPrePushing = true;
                }
                this.mState = DLNAState.CONNECTING;
                this.mPlayingDevice = device;
                this.mPosition = 0;
                resetValues();
                stopProgressTimer();
                new Thread() {
                    public void run() {
                        DLNAController.this.doPlay(device, shouldStopPrePushing);
                    }
                }.start();
                return;
            }
            UIsUtils.showToast(R.string.dlna_has_played_with_same_device);
        }
    }

    public void rePlay(Device device) {
        this.mSeek = this.mPosition;
        this.mIsRetry = true;
        play(device);
    }

    public void playNext() {
        stop(false, true);
    }

    private void doPlay(Device device, boolean shouldStopPrePushing) {
        if (device != null) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    DLNAController.this.showPushingDialog();
                }
            });
            if (shouldStopPrePushing) {
                this.mController.stop(this.mPlayingDevice);
                stopProgressTimer();
            }
            startTimeOutTimer(false);
            LogInfo.log("dlna", "开始获取播放地址");
            String url = syncGetPlayUrl(device);
            this.mCurrPlayingUrl = url;
            if (TextUtils.isEmpty(url)) {
                dismissSearchDialog();
                cancelTimeOutTimer();
                this.mHandler.post(new Runnable() {
                    public void run() {
                        DLNAController.this.showTimeOutDialog(false);
                        StatisticsUtils.statisticsActionInfo(DLNAController.this.mContext, PageIdConstant.fullPlayPage, "19", "c6556", null, -1, null);
                    }
                });
            } else if (this.mController.play(device, url)) {
                this.mState = DLNAState.CONNECTING;
                LogInfo.log("dlna", "play dlna success!!!");
                this.mHandler.post(new Runnable() {
                    public void run() {
                        DLNAController.this.monitorState();
                        if (DLNAController.this.mIsAlbum) {
                            DLNAController.this.getProgress();
                            DLNAController.this.getMediaDuration();
                            return;
                        }
                        DLNAController.this.mState = DLNAState.CONNECTING;
                        DLNAController.this.onTransportStateChange("", "PLAYING");
                    }
                });
            } else {
                LogInfo.log("dlna", "play dlna failed..");
                this.mState = DLNAState.IDLE;
                cancelTimeOutTimer();
                this.mHandler.post(new Runnable() {
                    public void run() {
                        DLNAController.this.showTimeOutDialog(false);
                        StatisticsUtils.statisticsActionInfo(DLNAController.this.mContext, PageIdConstant.fullPlayPage, "19", "c6556", null, -1, null);
                    }
                });
            }
        }
    }

    private synchronized void getMediaDuration() {
        new Thread() {
            public void run() {
                if (DLNAController.this.mPlayingDevice != null) {
                    final String mediaDuration = DLNAController.this.mController.getMediaDuration(DLNAController.this.mPlayingDevice);
                    DLNAController.this.mMediaDuration = DLNAUtil.getIntLength(mediaDuration);
                    DLNAController.this.mHandler.post(new Runnable() {
                        public void run() {
                            if (TextUtils.isEmpty(mediaDuration) || DLNAController.NOT_IMPLEMENTED.equals(mediaDuration) || DLNAController.this.mMediaDuration <= 0) {
                                DLNAController.this.mHandler.postDelayed(new Runnable() {
                                    public void run() {
                                        DLNAController.this.getMediaDuration();
                                    }
                                }, 1000);
                                return;
                            }
                            if (DLNAController.this.mSeek > 0) {
                                DLNAController.this.seek(DLNAController.this.mSeek);
                            }
                            LogInfo.log("dlna", "Get media duration success, retry later.Duration:" + mediaDuration + "intLength:" + DLNAController.this.mMediaDuration);
                        }
                    });
                }
            }
        }.start();
    }

    public synchronized void getProgress() {
        stopProgressTimer();
        if (this.mPlayingDevice != null) {
            this.mProgressTimer = new Timer();
            this.mProgressTimer.schedule(new TimerTask() {
                public void run() {
                    boolean shouldChangeToPlaying = true;
                    if (DLNAController.this.mProgressTimer != null) {
                        String[] arr = DLNAController.this.mController.getPositionInfo(DLNAController.this.mPlayingDevice);
                        if (!BaseTypeUtils.isArrayEmpty(arr) && arr.length == 2) {
                            DLNAController.this.mCurrentPosition = arr[0];
                            LogInfo.log("dlna", "Get position info and the value is " + DLNAController.this.mCurrentPosition);
                            if (!TextUtils.isEmpty(DLNAController.this.mCurrentPosition) && !DLNAController.NOT_IMPLEMENTED.equals(DLNAController.this.mCurrentPosition)) {
                                int pos = DLNAUtil.getIntLength(DLNAController.this.mCurrentPosition);
                                final String url = arr[1];
                                if (pos <= 0 || DLNAController.this.mPosition == pos || DLNAController.this.mState == DLNAState.PLAYING) {
                                    shouldChangeToPlaying = false;
                                }
                                if (pos > 0 && DLNAController.this.mState != DLNAState.IDLE) {
                                    DLNAController.this.mPosition = pos;
                                    DLNAController.this.mHandler.post(new Runnable() {
                                        public void run() {
                                            if (TextUtils.isEmpty(url) || TextUtils.isEmpty(DLNAController.this.mCurrPlayingUrl) || TextUtils.equals(url, DLNAController.this.mCurrPlayingUrl)) {
                                                if ((DLNAController.this.mMediaDuration == 0 || shouldChangeToPlaying) && DLNAController.this.getVideoDuration() > 0) {
                                                    if (DLNAController.this.mMediaDuration == 0) {
                                                        DLNAController.this.mMediaDuration = DLNAController.this.getVideoDuration();
                                                    }
                                                    LogInfo.log("dlna", "容错：mediaDuration获取不到");
                                                    DLNAController.this.mState = DLNAState.CONNECTING;
                                                    DLNAController.this.onTransportStateChange("", "PLAYING");
                                                    if (DLNAController.this.mSeek > 0) {
                                                        DLNAController.this.seek(DLNAController.this.mSeek);
                                                    }
                                                }
                                                DLNAController.this.onProcess(DLNAController.this.mPosition);
                                                return;
                                            }
                                            LogInfo.log("dlna", "其他设备进行了投屏操作！！！");
                                            if (DLNAController.this.mPlayingView != null) {
                                                DLNAController.this.mPlayingView.tvExit();
                                            }
                                            DLNAController.this.stop(false, false);
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }, 0, 1000);
        }
    }

    public synchronized void stopProgressTimer() {
        if (this.mProgressTimer != null) {
            this.mProgressTimer.cancel();
            this.mProgressTimer = null;
        }
    }

    public synchronized void pause() {
        if (!(this.mPlayingDevice == null || this.mState == DLNAState.PAUSE)) {
            onPause();
            stopProgressTimer();
            this.mState = DLNAState.PAUSE;
            new Thread() {
                public void run() {
                    DLNAController.this.mController.pause(DLNAController.this.mPlayingDevice);
                }
            }.start();
        }
    }

    public synchronized void start() {
        if (!(this.mPlayingDevice == null || this.mState == DLNAState.PLAYING)) {
            if (this.mPlayingView != null && this.mPlayingView.isTvExit()) {
                rePlay(this.mPlayingDevice);
            } else if (this.mIsAlbum && (TextUtils.isEmpty(this.mCurrentPosition) || TextUtils.equals(this.mCurrentPosition, NOT_IMPLEMENTED))) {
                play(this.mPlayingDevice);
            } else {
                onStart();
                this.mState = DLNAState.PLAYING;
                new Thread() {
                    public void run() {
                        DLNAController.this.mController.goon(DLNAController.this.mPlayingDevice, DLNAController.this.mCurrentPosition + "");
                    }
                }.start();
            }
        }
    }

    public synchronized void seek(int time) {
        if (this.mPlayingDevice != null) {
            if (this.mMediaDuration == 0) {
                this.mSeek = time;
            } else {
                this.mSeek = 0;
                final String targetPosition = DLNAUtil.secToTime(time);
                LogInfo.log("dlna", "seek:" + targetPosition);
                stopProgressTimer();
                new Thread() {
                    public void run() {
                        DLNAController.this.mController.seek(DLNAController.this.mPlayingDevice, targetPosition);
                        DLNAController.this.mHandler.post(new Runnable() {
                            public void run() {
                                DLNAController.this.getProgress();
                                DLNAController.this.onStart();
                            }
                        });
                    }
                }.start();
            }
        }
    }

    public synchronized void stop(boolean isActive, boolean needStopTV) {
        if (this.mPlayingDevice != null) {
            this.mState = DLNAState.IDLE;
            resetValues();
            stopProgressTimer();
            cancelMinitorState();
            if (this.mPlayingView != null && isActive) {
                this.mPlayingView.hide();
            }
            if (this.mToPlayerProtocol != null) {
                this.mToPlayerProtocol.pause();
            }
            onStopPlay(isActive);
            if (needStopTV) {
                new Thread() {
                    public void run() {
                        DLNAController.this.mController.stop(DLNAController.this.mPlayingDevice);
                    }
                }.start();
            }
        }
    }

    public void onTransportStateChange(String oldState, String nowState) {
        LogInfo.log("dlna", "当前状态:" + BaseTypeUtils.ensureStringValidate(nowState) + ";上次状态:" + BaseTypeUtils.ensureStringValidate(oldState));
        if (TextUtils.equals(nowState, DLNATransportState.TRANSITIONING)) {
            this.mState = DLNAState.CONNECTING;
        } else if (TextUtils.equals(nowState, DLNATransportState.PAUSED_PLAYBACK)) {
            stopProgressTimer();
            this.mState = DLNAState.PAUSE;
            this.mHandler.post(new Runnable() {
                public void run() {
                    DLNAController.this.onPause();
                }
            });
        } else if (TextUtils.equals(nowState, "PLAYING") && this.mState == DLNAState.PAUSE) {
            this.mState = DLNAState.PLAYING;
            this.mHandler.post(new Runnable() {
                public void run() {
                    DLNAController.this.onStart();
                }
            });
        } else if (this.mState != DLNAState.PLAYING && this.mState != DLNAState.IDLE && !TextUtils.equals(oldState, "null") && TextUtils.equals(nowState, "PLAYING")) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    DLNAController.this.cancelTimeOutTimer();
                    DLNAController.this.mState = DLNAState.PLAYING;
                    DLNAController.this.dismissTimeOutDialog();
                    DLNAController.this.dismissPushingDialog();
                    DLNAController.this.stopSearch();
                    DLNAController.this.onStartPlay();
                    if (DLNAController.this.mPlayingView != null) {
                        DLNAController.this.mPlayingView.setTitle(DLNAController.this.mPlayingDevice.getFriendlyName());
                        DLNAController.this.mPlayingView.show();
                    }
                }
            });
        } else if (this.mState == DLNAState.PLAYING && TextUtils.equals(nowState, "STOPPED")) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    if (DLNAController.this.mPlayingView != null) {
                        DLNAController.this.mPlayingView.tvExit();
                    }
                    DLNAController.this.stop(false, true);
                }
            });
        }
    }

    public Device getDevice() {
        return this.mPlayingDevice;
    }

    private void monitorState() {
        if (this.mPlayingDevice != null) {
            cancelMinitorState();
            this.mMonitorStateTimer = new Timer();
            this.mMonitorStateTimer.schedule(new TimerTask() {
                public void run() {
                    String state = DLNAController.this.mController.getTransportState(DLNAController.this.mPlayingDevice);
                    if (!TextUtils.equals(state, DLNAController.this.mLastTransportState)) {
                        DLNAController.this.onTransportStateChange(DLNAController.this.mLastTransportState, state);
                        DLNAController.this.mLastTransportState = state;
                    }
                }
            }, 0, 1000);
        }
    }

    private void cancelMinitorState() {
        if (this.mMonitorStateTimer != null) {
            this.mMonitorStateTimer.cancel();
            this.mMonitorStateTimer = null;
        }
    }

    protected void showSearchDialog() {
        if (this.mContext != null) {
            if (this.mSearchDialog != null) {
                dismissSearchDialog();
            }
            this.mSearchDialog = new LoadingDialog(this.mContext, R.string.dlna_seaching);
            this.mSearchDialog.setOnDismissListener(new OnDismissListener() {
                public void onDismiss(DialogInterface dialog) {
                    DLNAController.this.mIsSearchOtherDevices = false;
                }
            });
            this.mSearchDialog.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == 4 && event.getRepeatCount() == 0) {
                        DLNAController.this.stopSearch();
                        DLNAController.this.cancelTimeOutTimer();
                    }
                    return false;
                }
            });
            try {
                this.mSearchDialog.show();
            } catch (Exception e) {
            }
        }
    }

    protected void dismissSearchDialog() {
        if (this.mSearchDialog != null && this.mContext != null) {
            try {
                this.mSearchDialog.dismiss();
            } catch (Exception e) {
            }
        }
    }

    protected void showPushingDialog() {
        if (this.mContext != null) {
            if (this.mPushingDialog == null || !this.mPushingDialog.isShowing()) {
                this.mPushingDialog = new LoadingDialog(this.mContext, R.string.dlna_connecting);
                this.mPushingDialog.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        DLNAController.this.cancelTimeOutTimer();
                    }
                });
                this.mPushingDialog.setOnKeyListener(new OnKeyListener() {
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == 4 && event.getRepeatCount() == 0) {
                            DLNAController.this.stop(true, true);
                        }
                        return false;
                    }
                });
                try {
                    this.mPushingDialog.show();
                } catch (Exception e) {
                }
            }
        }
    }

    protected void dismissPushingDialog() {
        if (this.mPushingDialog != null && this.mContext != null) {
            try {
                this.mPushingDialog.dismiss();
                this.mPushingDialog = null;
            } catch (Exception e) {
            }
        }
    }

    public void showTimeOutDialog(final boolean isNoMedia) {
        if (this.mContext != null) {
            if (this.mTimeOutDialog != null) {
                dismissTimeOutDialog();
            }
            dismissSearchDialog();
            dismissPushingDialog();
            stopProgressTimer();
            if (!isNoMedia) {
                StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.fullPlayPage, "19", "c6556", null, -1, null);
            }
            this.mTimeOutDialog = new Dialog(this.mContext, R.style.dlna_push_style);
            this.mTimeOutDialog.getWindow().setContentView(R.layout.layout_timeout);
            ((TextView) this.mTimeOutDialog.getWindow().findViewById(R.id.push_to_tv)).setText(isNoMedia ? R.string.dlna_no_media_present : R.string.dlna_push_failed);
            this.mTimeOutDialog.getWindow().findViewById(R.id.push_to_tv_title).setVisibility(8);
            this.mTimeOutDialog.getWindow().findViewById(R.id.push_to_tv_image).setVisibility(8);
            View cancel = this.mTimeOutDialog.getWindow().findViewById(R.id.cancel);
            TextView retry = (TextView) this.mTimeOutDialog.getWindow().findViewById(R.id.login);
            retry.setText(R.string.scr_proj_device_has_no_external_disc_retry);
            cancel.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    DLNAController.this.mTimeOutDialog.dismiss();
                    if (!isNoMedia && DLNAController.this.mPlayingView != null) {
                        DLNAController.this.mPlayingView.tvExit();
                    }
                }
            });
            retry.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    DLNAController.this.mTimeOutDialog.dismiss();
                    if (isNoMedia) {
                        DLNAController.this.startSearch(true);
                        return;
                    }
                    DLNAController.this.mState = DLNAState.IDLE;
                    DLNAController.this.play(DLNAController.this.mPlayingDevice);
                }
            });
            this.mTimeOutDialog.setOnDismissListener(new OnDismissListener() {
                public void onDismiss(DialogInterface dialog) {
                    DLNAController.this.dismissPushingDialog();
                }
            });
            this.mTimeOutDialog.setCanceledOnTouchOutside(false);
            this.mTimeOutDialog.getWindow().setLayout(UIsUtils.dipToPx(310.0f), -2);
            try {
                this.mTimeOutDialog.show();
            } catch (Exception e) {
            }
        }
    }

    protected void dismissTimeOutDialog() {
        if (this.mTimeOutDialog != null && this.mContext != null) {
            try {
                this.mTimeOutDialog.dismiss();
            } catch (Exception e) {
            }
        }
    }

    public DLNAState getState() {
        return this.mState;
    }

    private void resetValues() {
        this.mCurrentPosition = null;
        this.mMediaDuration = 0;
        this.mIsPlayingNext = false;
        this.mLastTransportState = null;
    }
}
