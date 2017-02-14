package com.letv.component.player.core;

import android.content.Context;
import android.os.Handler;
import com.letv.component.player.http.HttpRequestManager;
import com.letv.component.player.http.HttpServerConfig;
import com.letv.component.player.http.bean.Cmdinfo;
import com.letv.component.player.http.parser.LetvFeedbackInitParser;
import com.letv.component.player.http.request.HttpFeedbackInitRequest;
import com.letv.component.player.http.request.HttpFeedbackInitRequest.getResultCallback;
import com.letv.component.player.http.request.LetvUpLogTask;
import com.letv.component.player.http.request.LetvUpLogTask.FeedCallBack;
import com.letv.component.player.utils.HardDecodeUtils;
import com.letv.component.player.utils.LogTag;
import com.letv.component.player.utils.PreferenceUtil;
import com.letv.component.player.utils.ZipUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LetvMediaPlayerManager {
    public static final int HARD_DECODE_BLACK = 0;
    public static final int HARD_DECODE_GRAY_CONFIGURABLE = 2;
    public static final int HARD_DECODE_GRAY_UNCONFIGURABLE = 3;
    public static final int HARD_DECODE_WHITE = 1;
    public static final int SUPPORT_MP4_LEVEL = 0;
    public static final int SUPPORT_NO_SUPPORT_LEVEL = -1;
    public static final int SUPPORT_TS1000K_LEVEL = 3;
    public static final int SUPPORT_TS1080P_LEVEL = 7;
    public static final int SUPPORT_TS1300K_LEVEL = 4;
    public static final int SUPPORT_TS180K_LEVEL = 5;
    public static final int SUPPORT_TS350K_LEVEL = 1;
    public static final int SUPPORT_TS720P_LEVEL = 6;
    public static final int SUPPORT_TS800K_LEVEL = 2;
    private static final String TAG = "LetvMediaPlayerManager";
    private static LetvMediaPlayerManager mManager = new LetvMediaPlayerManager();
    private File file;
    private String fileName = "VideoPlayLog.log";
    private Handler handler = new Handler();
    private Configuration mConfig;
    private Context mContext;
    boolean mDecodeRequestFinished = false;
    private ExecutorService mExecutorService;

    private LetvMediaPlayerManager() {
    }

    public static LetvMediaPlayerManager getInstance() {
        return mManager;
    }

    public void init(Context context, String appKey, String appID, String pCode, String appVer) {
        AppInfo.appKey = appKey;
        AppInfo.pCode = pCode;
        AppInfo.appVer = appVer;
        AppInfo.appID = appID;
        this.mContext = context;
        this.mConfig = Configuration.getInstance(context);
        HttpRequestManager.getInstance(this.mContext).requestCapability();
    }

    public int getHardDecodeState() {
        return this.mConfig.getHardDecodeState();
    }

    public int getHardDecodeSupportLevel() {
        if (this.mConfig.mHardDecodeCapability.isSupport720p) {
            return 6;
        }
        if (this.mConfig.mHardDecodeCapability.isSupport1300k) {
            LogTag.i(TAG, "HARD_SUPPORT_TS1300K_LEVEL");
            return 4;
        } else if (this.mConfig.mHardDecodeCapability.isSupport1000k) {
            LogTag.i(TAG, "HARD_SUPPORT_TS1000K_LEVEL");
            return 3;
        } else if (this.mConfig.mHardDecodeCapability.isSupport350k) {
            LogTag.i(TAG, "HARD_SUPPORT_TS350K_LEVEL");
            return 1;
        } else if (this.mConfig.mHardDecodeCapability.isSupport180k) {
            LogTag.i(TAG, "HARD_SUPPORT_TS180K_LEVEL");
            return 5;
        } else {
            LogTag.i(TAG, "HARD_SUPPORT_NO_SUPPORT_LEVEL");
            return -1;
        }
    }

    public int getSoftDecodeSupportLevel() {
        if (this.mConfig.mSoftDecodeCapability.isSupport720p) {
            LogTag.i(TAG, "SOFT_SUPPORT_TS720p_LEVEL");
            return 6;
        } else if (this.mConfig.mSoftDecodeCapability.isSupport1300k) {
            LogTag.i(TAG, "SOFT_SUPPORT_TS1300K_LEVEL");
            return 4;
        } else if (this.mConfig.mSoftDecodeCapability.isSupport1000k) {
            LogTag.i(TAG, "SOFT_SUPPORT_TS1000K_LEVEL");
            return 3;
        } else if (this.mConfig.mSoftDecodeCapability.isSupport350k) {
            LogTag.i(TAG, "SOFT_SUPPORT_TS350K_LEVEL");
            return 1;
        } else if (this.mConfig.mSoftDecodeCapability.isSupport180k) {
            LogTag.i(TAG, "SOFT_SUPPORT_TS180K_LEVEL");
            return 5;
        } else {
            LogTag.i(TAG, "SOFT_SUPPORT_NO_SUPPORT_LEVEL");
            return -1;
        }
    }

    public String getSdkVersion() {
        return Configuration.SDK_VERSION;
    }

    public void setDebugMode(boolean debug) {
        HttpServerConfig.setDebugMode(debug);
    }

    public void setLogMode(boolean debug) {
        LogTag.setDebugMode(debug);
    }

    public void feedbackCommit() {
        this.file = new File(getSaveDirc() + File.separator + this.fileName);
        if (this.file.exists()) {
            new HttpFeedbackInitRequest(this.mContext, new getResultCallback() {
                public void response(String res) {
                    try {
                        Object object = new LetvFeedbackInitParser().parse(res);
                        if (object != null) {
                            final Long fbId = ((Cmdinfo) object).getFbId();
                            boolean supportHWDecodeUseNative = HardDecodeUtils.isSupportHWDecodeUseNative();
                            PreferenceUtil.setLocalCapcity(LetvMediaPlayerManager.this.mContext, "本地解码能力：supportHWDecodeUseNative  " + supportHWDecodeUseNative + "  avcLevel = " + HardDecodeUtils.getAVCLevel());
                            if (LetvMediaPlayerManager.this.mExecutorService == null) {
                                LetvMediaPlayerManager.this.mExecutorService = Executors.newCachedThreadPool();
                            }
                            LetvMediaPlayerManager.this.mExecutorService.execute(new Runnable() {
                                public void run() {
                                    LetvMediaPlayerManager.this.writeLog();
                                    Handler access$4 = LetvMediaPlayerManager.this.handler;
                                    final Long l = fbId;
                                    access$4.post(new Runnable() {
                                        public void run() {
                                            LetvMediaPlayerManager.upLog(LetvMediaPlayerManager.this.getSaveDirc(), l.toString());
                                        }
                                    });
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).executeOnExecutor(Executors.newCachedThreadPool(), new String[]{""});
            return;
        }
        LogTag.i("file is null or not exist");
    }

    private static synchronized void upLog(String path, String fbid) {
        Throwable th;
        synchronized (LetvMediaPlayerManager.class) {
            try {
                List<File> files = new ArrayList();
                File fileParent = new File(path);
                if (fileParent.exists()) {
                    files = list2(fileParent);
                    if (files.size() != 0) {
                        File zipFile;
                        File file;
                        try {
                            if (!files.isEmpty()) {
                                zipFile = new File(fileParent + "/logFile.zip");
                                try {
                                    LogTag.i("zipFile path=" + fileParent + "/logFile.zip");
                                    ZipUtils.zipFiles(files, zipFile);
                                    if (zipFile != null) {
                                        if (zipFile.exists()) {
                                            new LetvUpLogTask(files, zipFile, new FeedCallBack() {
                                                public void response(String res) {
                                                }
                                            }, fbid).executeOnExecutor(Executors.newCachedThreadPool(), new String[]{""});
                                            file = zipFile;
                                        }
                                    }
                                    file = zipFile;
                                } catch (Exception e) {
                                    file = zipFile;
                                }
                            }
                        } catch (Exception e2) {
                        } catch (Throwable th2) {
                            th = th2;
                            file = zipFile;
                            throw th;
                        }
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private static synchronized List<File> list2(File dir) {
        List<File> fileList;
        synchronized (LetvMediaPlayerManager.class) {
            fileList = new ArrayList();
            File[] all = dir.listFiles();
            for (File d : all) {
                if (d.isFile()) {
                    fileList.add(d);
                }
            }
        }
        return fileList;
    }

    private void writeLog() {
        try {
            FileOutputStream fos = new FileOutputStream(new File(createFileIfNotExist(getSaveDirc())), true);
            fos.write(new StringBuilder(String.valueOf(PreferenceUtil.getQuestParams(this.mContext))).append("\r\n").append(PreferenceUtil.getQuestResult(this.mContext)).append("\r\n").append(PreferenceUtil.getLocalCapcity(this.mContext)).toString().getBytes());
            fos.close();
        } catch (Exception e) {
        }
    }

    private String createFileIfNotExist(String pathDownload) {
        try {
            File dir = new File(pathDownload);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            this.file = new File(new StringBuilder(String.valueOf(pathDownload)).append(File.separator).append(this.fileName).toString());
            if (!this.file.exists()) {
                this.file.createNewFile();
            }
            return this.file.getAbsolutePath();
        } catch (Exception e) {
            return "";
        }
    }

    private String getSaveDirc() {
        String pathDownload = this.mContext.getDir("LetvLog", 3).getPath();
        LogTag.i("pathDownload=" + pathDownload);
        return pathDownload;
    }

    public void writePlayLog(String content) {
        LogTag.i(content);
        writePlayLog(content, false);
    }

    public void writePlayLog(final String content, final boolean isRemoveHistoryFileInfo) {
        if (this.mExecutorService == null) {
            this.mExecutorService = Executors.newCachedThreadPool();
        }
        this.mExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    synchronized (LetvMediaPlayerManager.this) {
                        if (isRemoveHistoryFileInfo && LetvMediaPlayerManager.this.file != null && LetvMediaPlayerManager.this.file.exists()) {
                            LetvMediaPlayerManager.this.file.delete();
                        }
                        FileWriter writer = new FileWriter(new File(LetvMediaPlayerManager.this.createFileIfNotExist(LetvMediaPlayerManager.this.getSaveDirc())), true);
                        writer.write(content + "\r\n");
                        writer.flush();
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
