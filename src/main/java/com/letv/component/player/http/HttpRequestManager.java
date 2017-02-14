package com.letv.component.player.http;

import android.content.Context;
import android.text.TextUtils;
import com.letv.component.core.async.TaskCallBack;
import com.letv.component.player.core.AppInfo;
import com.letv.component.player.core.Configuration;
import com.letv.component.player.core.LetvMediaPlayerManager;
import com.letv.component.player.core.PlayUrl.StreamType;
import com.letv.component.player.http.bean.HardSoftDecodeCapability;
import com.letv.component.player.http.request.HttpHardSoftDecodeCapabilityRequest;
import com.letv.component.player.http.request.HttpHardSoftDecodeReportRequest;
import com.letv.component.player.utils.LogTag;
import com.letv.component.player.utils.PreferenceUtil;

public class HttpRequestManager {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$letv$component$player$core$PlayUrl$StreamType;
    private static HttpRequestManager sRequestManager = null;
    private TaskCallBack hardSoftDecodeCapabilityCallback = new TaskCallBack() {
        public void callback(int code, String msg, Object object) {
            if (code == 0) {
                String result = ((HardSoftDecodeCapability) object).mResult;
                if (!TextUtils.isEmpty(result)) {
                    String[] temp = result.split(",");
                    if (temp.length == 2) {
                        try {
                            HttpRequestManager.this.mConfiguration.update(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
                            HttpRequestManager.this.mDecodeRequestFinished = true;
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            } else if (code != 1 && code != 2) {
            }
        }
    };
    private Configuration mConfiguration;
    private Context mContext;
    boolean mDecodeRequestFinished = false;

    static /* synthetic */ int[] $SWITCH_TABLE$com$letv$component$player$core$PlayUrl$StreamType() {
        int[] iArr = $SWITCH_TABLE$com$letv$component$player$core$PlayUrl$StreamType;
        if (iArr == null) {
            iArr = new int[StreamType.values().length];
            try {
                iArr[StreamType.STREAM_TYPE_1000K.ordinal()] = 4;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[StreamType.STREAM_TYPE_1080P.ordinal()] = 7;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[StreamType.STREAM_TYPE_1300K.ordinal()] = 5;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[StreamType.STREAM_TYPE_180K.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[StreamType.STREAM_TYPE_350K.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[StreamType.STREAM_TYPE_720P.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[StreamType.STREAM_TYPE_UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            $SWITCH_TABLE$com$letv$component$player$core$PlayUrl$StreamType = iArr;
        }
        return iArr;
    }

    public static synchronized void createConfig(Context context) {
        synchronized (HttpRequestManager.class) {
            if (sRequestManager == null) {
                sRequestManager = new HttpRequestManager(context);
            }
        }
    }

    public static HttpRequestManager getInstance(Context context) {
        if (sRequestManager == null) {
            createConfig(context);
        }
        return sRequestManager;
    }

    private HttpRequestManager(Context context) {
        this.mContext = context;
        this.mConfiguration = Configuration.getInstance(context);
    }

    public boolean isAdaptered(StreamType clarity) {
        switch ($SWITCH_TABLE$com$letv$component$player$core$PlayUrl$StreamType()[clarity.ordinal()]) {
            case 2:
                return this.mConfiguration.mHardDecodeCapability.is180kAdapted;
            case 3:
                return this.mConfiguration.mHardDecodeCapability.is350kAdapted;
            case 4:
                return this.mConfiguration.mHardDecodeCapability.is1000kAdapted;
            case 5:
                return this.mConfiguration.mHardDecodeCapability.is1300kAdapted;
            case 6:
                return this.mConfiguration.mHardDecodeCapability.is720pAdapted;
            case 7:
                return this.mConfiguration.mHardDecodeCapability.is1080pAdapted;
            default:
                return false;
        }
    }

    public void requestCapability() {
        try {
            if (!this.mDecodeRequestFinished) {
                new HttpHardSoftDecodeCapabilityRequest(this.mContext, this.hardSoftDecodeCapabilityCallback).execute(new Object[]{AppInfo.appKey, AppInfo.pCode, AppInfo.appVer});
            }
        } catch (Exception e) {
        }
    }

    public void hardDecodeReport(int vid, String url, int isSuc, int errCode, StreamType clarity, int isSmooth) {
        int hardDecodeState = LetvMediaPlayerManager.getInstance().getHardDecodeState();
        LogTag.i("clarity:" + clarity);
        LogTag.i(isAdaptered(clarity) + ":isAdaptered");
        if (hardDecodeState == 2 && !isAdaptered(clarity)) {
            String clarityValue = clarity.value();
            int level = -1;
            if (clarityValue.equals("1300K")) {
                level = 4;
            } else if (clarityValue.equals("1000K")) {
                level = 3;
            } else if (clarityValue.equals("350K")) {
                level = 2;
            } else if (clarityValue.equals("180K")) {
                level = 1;
            } else if (clarityValue.equals("720P")) {
                level = 6;
            } else if (clarityValue.equals("1080P")) {
                level = 7;
            }
            LogTag.i("level:" + level);
            int sharedLevel = PreferenceUtil.getFirsHardDecode(this.mContext);
            LogTag.i("sharedLevel:" + sharedLevel);
            final int clarityLevel = level;
            if (clarityLevel > sharedLevel) {
                try {
                    LogTag.i("------>hardDecodeReport");
                    new HttpHardSoftDecodeReportRequest(this.mContext, new TaskCallBack() {
                        public void callback(int code, String msg, Object object) {
                            if (code == 0) {
                                PreferenceUtil.setFirsHardDecode(HttpRequestManager.this.mContext, clarityLevel);
                                LogTag.i("硬解上报成功");
                            } else if (code == 1) {
                                LogTag.i("数据错误，硬解上报成功");
                            } else if (code == 2) {
                                LogTag.i("网咯连接错误，硬解上报失败");
                            } else if (code == 3) {
                                LogTag.i("无网络，硬解上报失败");
                            }
                        }
                    }).execute(new Object[]{AppInfo.appKey, AppInfo.pCode, AppInfo.appVer, "1", Integer.valueOf(vid), url, Integer.valueOf(isSuc), Integer.valueOf(errCode), clarity.value(), Integer.valueOf(isSmooth)});
                } catch (Exception e) {
                }
            }
        }
    }
}
