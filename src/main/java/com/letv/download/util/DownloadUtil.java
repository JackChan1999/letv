package com.letv.download.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Formatter;
import com.letv.core.BaseApplication;
import com.letv.core.audiotrack.AudioTrackManager;
import com.letv.core.bean.TimestampBean;
import com.letv.core.bean.VideoFileBean;
import com.letv.core.bean.VideoFileBean.VideoSchedulingAddress;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StringUtils;
import com.letv.download.bean.DownloadUrl;
import com.letv.download.bean.DownloadVideo;
import com.letv.download.db.DownloadDBDao;
import com.letv.download.exception.NetWorkErrorException;
import com.letv.download.exception.ServerErrorException;
import com.letv.download.http.HttpApiV1;
import com.letv.download.manager.DownloadAudioTrackManager;
import com.letv.download.manager.DownloadSubtitleManager;
import com.letv.download.manager.VideoFileManager;
import com.letv.pp.func.CdeHelper;
import com.tencent.connect.common.Constants;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class DownloadUtil {
    public static final String CDELOG1 = (Environment.getExternalStorageDirectory() + "/letv/exceptionInfo/cde.txt");
    public static final int DOWNLOAD_TRY_MAX_COUNT = 3;
    private static final int DUBI_STREAM_TYPE = 3;
    private static final int HD_STREAM_TYPE = 1;
    private static final int LOW_STREAM_TYPE = 0;
    private static final int STANDARD_STREAM_TYPE = 2;
    private static final String TAG = DownloadUtil.class.getSimpleName();
    private static CdeHelper mCdeHelper;
    static ExecutorService pool;
    Context mContext;
    DownloadVideo mDownloadVideo;
    String mmsid;
    String pcode;
    int streamType;
    String version;
    String vid;

    public DownloadUtil(String vid, DownloadVideo downloadVideo, String mmsid, int streamType, String pcode, String version, Context mContext) {
        this.vid = vid;
        this.mmsid = mmsid;
        this.streamType = streamType;
        this.pcode = pcode;
        this.version = version;
        this.mContext = mContext;
        this.mDownloadVideo = downloadVideo;
    }

    protected static void afreshDownload(Context context, DownloadVideo downloadVideo) {
        if (downloadVideo == null) {
            L.e(TAG, "afreshDownload", "downloadVideo == null !!!");
            return;
        }
        File file = new File(downloadVideo.filePath, VideoFileManager.createFileName(downloadVideo.vid));
        L.v(TAG, "afreshDownload file is exists : " + file.exists());
        if (file.exists()) {
            file.delete();
        }
        DownloadDBDao.getInstance(context).removeAllPartInfo(downloadVideo);
        downloadVideo.mParts = null;
        downloadVideo.downloaded = 0;
        downloadVideo.totalsize = 0;
    }

    private void checkStorePath(String newStorePath) {
        if (this.mDownloadVideo == null) {
            L.e(TAG, "checkStorePath", " mDownloadVideo == null !!!!! ");
            return;
        }
        L.v(TAG, "checkStorePath newStorePath: " + newStorePath + " storePath : " + this.mDownloadVideo.storePath);
        if (!(TextUtils.isEmpty(this.mDownloadVideo.storePath) || this.mDownloadVideo.storePath.equals(newStorePath))) {
            L.v(TAG, "checkStorePath storePath failed afresh download ");
            afreshDownload(this.mContext, this.mDownloadVideo);
        }
        this.mDownloadVideo.storePath = newStorePath;
    }

    private DownloadUrl getDDUrlByStreamType(VideoFileBean videoFile) {
        DownloadUrl du = new DownloadUrl();
        du.streamType = this.streamType;
        String[] urls = new String[4];
        VideoSchedulingAddress address = null;
        switch (this.streamType) {
            case 1:
                LogInfo.log(" using HD address");
                address = videoFile.mp4_1300;
                if (address == null) {
                    LogInfo.log(" not HD address !!!!!!!! ");
                    break;
                }
                break;
            case 2:
                LogInfo.log(" using standard address");
                address = videoFile.mp4_1000;
                if (address == null) {
                    LogInfo.log(" not standard address !!!!!!!!!! ");
                    break;
                }
                break;
            case 3:
                getDubiUril(urls, videoFile);
                break;
            default:
                LogInfo.log(" using liuchang address ");
                address = videoFile.mp4_350;
                break;
        }
        if (address != null) {
            String traceId = "";
            HashMap<String, String> audioTracksMap = address.audioTracksMap;
            if (!BaseTypeUtils.isMapEmpty(address.audioTracksMap)) {
                String atype = "";
                switch (this.streamType) {
                    case 1:
                        atype = "1002";
                        break;
                    case 2:
                        atype = "1001";
                        break;
                    default:
                        atype = Constants.DEFAULT_UIN;
                        break;
                }
                String code = AudioTrackManager.getInstance().getAudioTrackCode();
                if (TextUtils.isEmpty(traceId) && !TextUtils.isEmpty(code)) {
                    if (!audioTracksMap.containsKey(code + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + atype)) {
                        switch (this.streamType) {
                            case 1:
                                address = videoFile.mp4_1000;
                                if (address != null) {
                                    if (!BaseTypeUtils.isMapEmpty(address.audioTracksMap) && address.audioTracksMap.containsKey(code + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "1001")) {
                                        traceId = (String) address.audioTracksMap.get(code + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "1001");
                                        if (!TextUtils.isEmpty(traceId)) {
                                            du.streamType = 2;
                                            this.streamType = 2;
                                            break;
                                        }
                                    }
                                    address = videoFile.mp4_350;
                                    if (!BaseTypeUtils.isMapEmpty(address.audioTracksMap) && address.audioTracksMap.containsKey(code + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + Constants.DEFAULT_UIN)) {
                                        traceId = (String) address.audioTracksMap.get(code + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + Constants.DEFAULT_UIN);
                                        if (!TextUtils.isEmpty(traceId)) {
                                            du.streamType = 0;
                                            this.streamType = 0;
                                            break;
                                        }
                                    }
                                }
                                break;
                            case 2:
                                address = videoFile.mp4_350;
                                if (!BaseTypeUtils.isMapEmpty(address.audioTracksMap) && address.audioTracksMap.containsKey(code + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + Constants.DEFAULT_UIN)) {
                                    traceId = (String) address.audioTracksMap.get(code + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + Constants.DEFAULT_UIN);
                                    if (!TextUtils.isEmpty(traceId)) {
                                        du.streamType = 0;
                                        this.streamType = 0;
                                        break;
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    traceId = (String) audioTracksMap.get(code + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + atype);
                }
                if (TextUtils.isEmpty(traceId)) {
                    traceId = DownloadAudioTrackManager.getAudioTrackId(this.mContext, audioTracksMap, atype, videoFile.defaultAudioTrackMap);
                }
            }
            urls[0] = address.getMainUrl();
            urls[1] = address.getBackUrl0();
            urls[2] = address.getBackUrl1();
            urls[3] = address.getBackUrl2();
            if (!TextUtils.isEmpty(traceId)) {
                du.isMultipleAudio = true;
                du.multipleAudioCode = getAudioCodeFromTrackId(traceId, audioTracksMap);
                urls[0] = urls[0] + "&a_idx=" + traceId;
                urls[1] = urls[1] + "&a_idx=" + traceId;
                urls[2] = urls[2] + "&a_idx=" + traceId;
                urls[3] = urls[3] + "&a_idx=" + traceId;
            }
        }
        this.mDownloadVideo.serverTotalSize = address.filesize;
        if (address != null) {
            checkStorePath(address.getStorepath());
        }
        du.ddurls = urls;
        return du;
    }

    private String getAudioCodeFromTrackId(String id, HashMap<String, String> audioTracksMap) {
        String code = "";
        String lang_atype = "";
        if (BaseTypeUtils.isMapEmpty(audioTracksMap)) {
            return code;
        }
        for (String key : audioTracksMap.keySet()) {
            if (((String) audioTracksMap.get(key)).equals(id)) {
                lang_atype = key;
                break;
            }
        }
        if (TextUtils.isEmpty(lang_atype)) {
            return code;
        }
        return lang_atype.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)[0];
    }

    private String[] getDubiUril(String[] urls, VideoFileBean videoFile) {
        VideoSchedulingAddress address = videoFile.mp4_800_db;
        if (address == null) {
            address = videoFile.mp4_1300_db;
        } else {
            System.out.println("杜比800");
        }
        if (address == null) {
            address = videoFile.mp4_720p_db;
        } else {
            System.out.println("杜比1300");
        }
        if (address == null) {
            address = videoFile.mp4_1000;
        } else {
            System.out.println("杜比1080p");
        }
        if (address == null) {
            address = videoFile.mp4_350;
        } else {
            System.out.println("普通800");
        }
        urls[0] = address.getMainUrl();
        urls[1] = address.getBackUrl0();
        urls[2] = address.getBackUrl1();
        urls[3] = address.getBackUrl2();
        return urls;
    }

    public DownloadUrl getDDUrls() throws Exception {
        int tm = TimestampBean.getTm().getCurServerTime();
        VideoFileBean videoFile = HttpApiV1.getInstance(this.mContext).getVideoFile(String.valueOf(this.vid), this.mmsid, "no", String.valueOf(tm), TimestampBean.generateVideoFileKey(String.valueOf(this.mmsid), String.valueOf(tm)), this.pcode, this.version);
        if (videoFile == null) {
            return null;
        }
        if (videoFile.isErr() && HttpApiV1.getInstance(this.mContext).getTimestamp() != null) {
            tm = TimestampBean.getTm().getCurServerTime();
            videoFile = HttpApiV1.getInstance(this.mContext).getVideoFile(String.valueOf(this.vid), this.mmsid, "no", String.valueOf(tm), TimestampBean.generateVideoFileKey(String.valueOf(this.mmsid), String.valueOf(tm)), this.pcode, this.version);
        }
        return DownloadSubtitleManager.checkSubtitle(this.mContext, videoFile, getDDUrlByStreamType(videoFile));
    }

    public DownloadUrl getDownloadUrl() throws Exception {
        DownloadUrl du = getDDUrls();
        String[] ddUrls = du.ddurls;
        if (ddUrls == null || ddUrls.length == 0) {
            throw new ServerErrorException("ddUrls == null or  ddUrls.length == 0");
        }
        int length = ddUrls.length;
        int i = 0;
        while (i < length) {
            String url = ddUrls[i];
            try {
                if (TextUtils.isEmpty(url)) {
                    LogInfo.log("--------step 5----->ddUrl can`t used, return null", "filedownloader");
                    i++;
                } else {
                    boolean isLogin = PreferencesManager.getInstance().isLogin();
                    L.v(TAG, "getDownloadUrl isLogin : " + isLogin + " uInfo: " + PreferencesManager.getInstance().getUInfo());
                    url = url + "&pcode=" + this.pcode + "&version=" + this.version + "&uuid=" + LetvUtils.getUUID(this.mContext);
                    if (isLogin) {
                        url = (url + "&iscpn=f9050") + "&uinfo=" + PreferencesManager.getInstance().getUInfo();
                    }
                    saveException(this.vid, " video name : " + this.mDownloadVideo.name + " DDUrl url : " + url, "200", String.valueOf(this.mDownloadVideo.downloaded), String.valueOf(this.mDownloadVideo.serverTotalSize));
                    String cdeUrl = getURLFromLinkShell(url);
                    L.v(TAG, "--------step 555----->getRealUrlByDDUrl()  new ddurl2:  cde url = " + cdeUrl);
                    if (!TextUtils.isEmpty(cdeUrl)) {
                        url = cdeUrl;
                    }
                    if (url.contains("&vtype=")) {
                        String subStr = url.substring(url.indexOf("vtype="));
                        int start = subStr.indexOf(SearchCriteria.EQ) + 1;
                        int end = subStr.indexOf("&");
                        PreferencesManager.getInstance().setDownloadFileStreamLevel(this.mDownloadVideo != null ? this.mDownloadVideo.vid + "" : "", end < 0 ? subStr.substring(start) : subStr.substring(start, end));
                    }
                    saveException(this.vid, " getURLFromLinkShell url : " + url, "200", String.valueOf(this.mDownloadVideo.downloaded), String.valueOf(this.mDownloadVideo.serverTotalSize));
                    L.v(TAG, "--------step 555----->getRealUrlByDDUrl()  ddurl2:  url = " + url);
                    String realUrl = HttpApiV1.getInstance(this.mContext).getDownloadUrl(url);
                    L.v(TAG, "--------step 5----->getRealUrlByDDUrl() request real url by ddurl:  realUrl = " + realUrl);
                    saveException(this.vid, realUrl, "200", String.valueOf(this.mDownloadVideo.downloaded), String.valueOf(this.mDownloadVideo.serverTotalSize));
                    du.videoDownloadUrl = realUrl;
                    return du;
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogInfo.log("--------step 5----->getRealUrlByDDUrl() request real url Exception:  statusCode = " + e.getMessage() + "; url = " + url, "filedownloader");
                if ((e instanceof ServerErrorException) || (e instanceof NetWorkErrorException)) {
                    if (e instanceof ServerErrorException) {
                        ((ServerErrorException) e).vid = String.valueOf(this.vid);
                    }
                    throw e;
                }
            }
        }
        LogInfo.log("--------step 5----->getRealUrlByDDUrl() request all real url(contain backupUrl) failure", "filedownloader");
        return null;
    }

    public static long stringToLong(String str) {
        long l = 0;
        try {
            l = Long.parseLong(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public static int stringToInteger(String str) {
        int i = 0;
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static boolean sdCardMounted() {
        String state = Environment.getExternalStorageState();
        return state.equals("mounted") && !state.equals("mounted_ro");
    }

    public static File getDownloadDir(Context context) {
        File downloadDir = new File(getDownload_path(context));
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }
        return downloadDir;
    }

    public static String getDownload_path(Context context) {
        return PreferencesManager.getInstance().getDownloadPath(context);
    }

    public static void asyUpdateFileData() {
        try {
            if (pool == null) {
                pool = Executors.newSingleThreadExecutor();
            }
            pool.submit(new 1());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static String calculateDownloadSpeed(long timestamp, long curtime, long downloadedSize) {
        long time = (curtime - timestamp) / 1000;
        if (time <= 0 || downloadedSize <= 0) {
            return "";
        }
        return Formatter.formatFileSize(BaseApplication.getInstance(), downloadedSize / time) + "/s";
    }

    public static void saveException(DownloadVideo downloadVideo, String errorCode) {
        saveException(String.valueOf(downloadVideo.vid), downloadVideo.downloadUrl, errorCode, String.valueOf(downloadVideo.downloaded), String.valueOf(downloadVideo.totalsize));
    }

    public static void saveException(String exceptionInfo) {
        try {
            LetvLogApiTool.getInstance().saveExceptionInfo(StringUtils.getTimeStamp() + "  LetvDownload loginfo >>: " + exceptionInfo);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void saveException(String vid, String downloadUrl, String errorCode, String downloadSize, String serverDownloadSize) {
        String qos;
        try {
            qos = "";
            if (!TextUtils.isEmpty(downloadUrl) && downloadUrl.contains("&qos=5")) {
                String newUrl = downloadUrl.substring(downloadUrl.indexOf("&qos=") + 5, downloadUrl.length());
                qos = newUrl.substring(0, newUrl.indexOf("&"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable e2) {
            e2.printStackTrace();
            return;
        }
        StringBuilder exceptionSB = new StringBuilder();
        exceptionSB.append(StringUtils.getTimeStamp()).append("  ").append(" LetvDownload saveException >> vid : ").append(vid).append(" ").append(" downloadUrl : ").append(downloadUrl).append(" ").append(" errorCode : ").append(errorCode).append(" qos : ").append(qos).append(" downloaded :").append(downloadSize).append(" serverDownloadSize: ").append(serverDownloadSize);
        LogInfo.log(TAG, "downlaod saveException " + exceptionSB.toString());
        LetvLogApiTool.getInstance().saveExceptionInfo(exceptionSB.toString());
    }

    public static CdeHelper getCdeHelper() {
        return mCdeHelper;
    }

    public static void startCde() {
        mCdeHelper = CdeHelper.getInstance(BaseApplication.getInstance(), "port=6990&app_id=" + "3000" + "&ostype=android&channel_default_multi=0&log_type=4&log_file=" + CDELOG1 + "&channel_default_multi=1&channel_max_count=2&dcache_enabled=1&dcache_capacity=50&show_letv_cks=1");
        mCdeHelper.start();
    }

    public static String getURLFromLinkShell(String input) {
        String url = input;
        LogInfo.log("", "getURLFromLinkShell mCdeHelper " + mCdeHelper);
        if (mCdeHelper != null) {
            return mCdeHelper.getLinkshellUrl(input);
        }
        return url;
    }

    public static void stopCde() {
        if (mCdeHelper != null) {
            mCdeHelper.stop();
        }
        mCdeHelper = null;
    }

    public static boolean isBackground(Context context) {
        for (RunningAppProcessInfo appProcess : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (appProcess.processName.equals("com.letv.android.client")) {
                if (appProcess.importance != 100) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
