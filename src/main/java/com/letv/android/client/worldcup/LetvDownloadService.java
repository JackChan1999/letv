package com.letv.android.client.worldcup;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.letv.android.client.worldcup.async.LetvApi;
import com.letv.android.client.worldcup.async.inter.RequestCallBack;
import com.letv.android.client.worldcup.bean.DownloadDBBean;
import com.letv.android.client.worldcup.bean.DownloadStatus;
import com.letv.android.client.worldcup.bean.Video;
import com.letv.android.client.worldcup.bean.WorldCupBlock;
import com.letv.android.client.worldcup.bean.WorldCupMetaData;
import com.letv.android.client.worldcup.dao.PreferencesManager;
import com.letv.android.client.worldcup.db.WorldCupTraceHandler;
import com.letv.android.client.worldcup.download.WorldCupDownloadManager;
import com.letv.android.client.worldcup.receiver.NetStateReceiver;
import com.letv.android.client.worldcup.receiver.WorldCupDownloadReceiver;
import com.letv.android.client.worldcup.util.Constants;
import com.letv.android.client.worldcup.util.DownloadUtil;
import com.letv.android.client.worldcup.util.LetvServiceConfiguration;
import com.letv.android.client.worldcup.util.LetvUtil;
import com.letv.android.client.worldcup.util.NetWorkTypeUtils;
import com.letv.android.client.worldcup.util.StoreUtils;
import java.io.File;

public class LetvDownloadService extends Service implements RequestCallBack<WorldCupBlock> {
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_PAUSE_ALL = "action_pause_all";
    public static final String ACTION_PENDING = "action_pending";
    public static final String ACTION_REMOVE = "action_remove";
    public static final String ACTION_REMOVE_ALL = "action_remove_all";
    public static final String ACTION_REQUEST_SERVER = "action_request_server";
    public static final String ACTION_RESUME = "action_resume";
    public static final String ACTION_START_ALL = "action_start_all";
    public static final String ACTION_STOP_SERVICE = "action_stop_service";
    public static final int MAX_DOWNLOAD_WORLDCUP_SIZE = 10;
    private static LetvDownloadService instence;
    private boolean isDestory = false;
    private boolean isHd;
    private boolean isNetStateReceiver;
    private boolean isRegDownloadReceiver;
    private NetStateReceiver mNetStateReceiver;
    private WorldCupDownloadReceiver mReceiver;
    private volatile ServiceHandler mServiceHandler;
    private volatile Looper mServiceLooper;
    private WorldCupDownloadManager mWorldCupManager;
    private RequestCallBack videoCallBack = new RequestCallBack<Video>() {
        public boolean onPreExecute() {
            return true;
        }

        public void onPostExecute(int updateId, Video result) {
            Constants.debug("===========RequestVideoDetail:onPostExecute()");
            if (result != null && result.canPlay() && !result.needJump() && !result.needPay()) {
                Constants.debug("===========RequestVideoDetail:result.canPlay() && !result.needJump() && !result.needPay()");
                LetvDownloadService.this.startDownload(DownloadDBBean.getInstance(LetvDownloadService.this.getApplicationContext(), result, result.getPid(), LetvDownloadService.this.isHd ? 1 : 0, result.getNameCn()));
            }
        }

        public void netNull() {
        }

        public void netErr(int updateId, String errMsg) {
        }

        public void dataNull(int updateId, String errMsg) {
        }
    };
    private WorldCupBlock worldCupBlock;
    private WorldCupTraceHandler worldCupTraceHandler;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            LetvDownloadService.this.onHandleIntent((Intent) msg.obj);
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        Constants.debug("------------------LetvDownloadService_onCreate System time:" + LetvUtil.timeString(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss"), "worldcup");
        this.worldCupTraceHandler = new WorldCupTraceHandler(getApplicationContext());
        this.mWorldCupManager = WorldCupDownloadManager.getInstance(getApplicationContext());
        this.isHd = PreferencesManager.getInstance().isDownloadHd(getApplicationContext());
        instence = this;
        LetvApi.getInstance().requestWorldCupBlock(getApplicationContext(), this);
        registerReceiver();
        HandlerThread thread = new HandlerThread("IntentService[LetvWorldCupDownloadService]");
        thread.start();
        this.mServiceLooper = thread.getLooper();
        this.mServiceHandler = new ServiceHandler(this.mServiceLooper);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Constants.debug("------------------LetvDownloadService_onStartCommand System time:" + LetvUtil.timeString(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss"), "worldcup");
        Message msg = this.mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        this.mServiceHandler.sendMessage(msg);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        Constants.debug("------------------LetvDownloadService onDestory System time:" + LetvUtil.timeString(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss"), "worldcup");
        super.onDestroy();
        this.isDestory = true;
        unregisterReceiver();
        this.mServiceLooper.quit();
        this.mServiceHandler = null;
        this.mWorldCupManager = null;
    }

    private void registerReceiver() {
        try {
            this.mReceiver = new WorldCupDownloadReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.letv.android.client.worldcup.download");
            registerReceiver(this.mReceiver, filter);
            this.isRegDownloadReceiver = true;
            this.mNetStateReceiver = new NetStateReceiver();
            registerReceiver(this.mNetStateReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            this.isNetStateReceiver = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unregisterReceiver() {
        try {
            this.mWorldCupManager.removeAllDownload();
            if (this.isRegDownloadReceiver) {
                unregisterReceiver(this.mReceiver);
                this.isRegDownloadReceiver = false;
            }
            if (this.isNetStateReceiver) {
                unregisterReceiver(this.mNetStateReceiver);
                this.isNetStateReceiver = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onPreExecute() {
        Constants.debug("------------------LetvDownloadService onPreExecute");
        return true;
    }

    public void onPostExecute(int updateId, WorldCupBlock result) {
        int size = 10;
        if (result != null) {
            this.worldCupBlock = result;
            Constants.debug("===========RequestVideoDetail:onPostExecute()，worldCupBlock.size" + result.size());
            if (!checkDataHasChange() || !NetWorkTypeUtils.isWifi(this)) {
                Constants.debug("===========RequestData repeat，worldCupBlock.size" + result.size(), "worldcup");
                if (this.worldCupTraceHandler.getNumInStatus(DownloadStatus.FINISHED.toInt()) == this.worldCupTraceHandler.getAllTrace().size()) {
                    Constants.debug("LetvDownloadService", " worldCupTraceHandler getNumInStatus >>>");
                    stopSelf();
                }
            } else if (!this.isDestory) {
                if (this.mWorldCupManager.getDownloadingNum() > 0) {
                    this.mWorldCupManager.pauseAll();
                }
                this.worldCupTraceHandler.deleteAll(this);
                if (this.worldCupBlock.size() <= 10) {
                    size = this.worldCupBlock.size();
                }
                for (int i = 0; i < size; i++) {
                    WorldCupMetaData metaData = (WorldCupMetaData) this.worldCupBlock.get(i);
                    Constants.debug("===========WorldCupMetaData_id:" + metaData.getVid());
                    LetvApi.getInstance().requestVideoDetail(getApplicationContext(), 0, String.valueOf(metaData.getVid()), this.videoCallBack);
                }
            }
        }
    }

    public void netNull() {
    }

    public void netErr(int updateId, String errMsg) {
    }

    public void dataNull(int updateId, String errMsg) {
    }

    public boolean checkDataHasChange() {
        if (this.worldCupBlock != null && this.worldCupBlock.size() > 0) {
            if (this.worldCupTraceHandler.getAllTrace().size() == 0) {
                return true;
            }
            for (int i = 0; i < this.worldCupTraceHandler.getAllTrace().size(); i++) {
                if (this.worldCupTraceHandler.has((long) ((WorldCupMetaData) this.worldCupBlock.get(0)).getVid()) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean startDownload(DownloadDBBean mDownloadDBBean) {
        if (!StoreUtils.isSdcardAvailable()) {
            Constants.debug("===========startDownload:StoreUtils.isSdcardAvailable() = false");
            return false;
        } else if (StoreUtils.getSdcardAvailableSpace(getApplicationContext()) < StoreUtils.DEFAULT_SDCARD_SIZE) {
            return false;
        } else {
            Constants.debug("===========StoreUtils.getSdcardAvailableSpace(getApplicationContext()) >= fileSize");
            File file = DownloadUtil.getCurrentDownloadFile(getApplicationContext(), (long) mDownloadDBBean.getEpisodeid());
            Constants.debug("===========file.getPath()" + file.getAbsolutePath());
            if (file == null) {
                file = new File(DownloadUtil.getDownloadDir(getApplicationContext()), DownloadUtil.createFileName((long) mDownloadDBBean.getEpisodeid()));
            }
            Constants.debug("===========addDownloadLetv before");
            this.worldCupTraceHandler.save(mDownloadDBBean);
            WorldCupDownloadManager.getInstance(getApplicationContext()).addDownloadLetv(new StringBuilder(String.valueOf(mDownloadDBBean.getEpisodeid())).toString(), file.getParent(), file.getName(), mDownloadDBBean.getMmsid(), new StringBuilder(String.valueOf(mDownloadDBBean.getIsHd())).toString(), LetvServiceConfiguration.getPcode(getApplicationContext()), LetvServiceConfiguration.getVersion(getApplicationContext()));
            return true;
        }
    }

    public static LetvDownloadService getInstence() {
        return instence;
    }

    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && Constants.isConnect(this)) {
                String id = intent.getStringExtra("id");
                Constants.debug("onHandleIntent:" + action);
                if (action.equals(ACTION_REQUEST_SERVER)) {
                    LetvApi.getInstance().requestWorldCupBlock(this, this);
                }
                if (action.equals(ACTION_PAUSE)) {
                    WorldCupDownloadManager.getInstance(this).pauseDownload(id);
                } else if (action.equals(ACTION_RESUME)) {
                    WorldCupDownloadManager.getInstance(this).resumeDownload(id);
                } else if (action.equals(ACTION_REMOVE)) {
                    WorldCupDownloadManager.getInstance(this).removeDownload(id);
                    this.worldCupTraceHandler.deleteDownloadById(getApplicationContext(), id);
                } else if (action.equals(ACTION_REMOVE_ALL)) {
                    WorldCupDownloadManager.getInstance(this).removeAllDownload();
                    this.worldCupTraceHandler.deleteAll(this);
                } else if (action.equals(ACTION_START_ALL)) {
                    WorldCupDownloadManager.getInstance(this).startAll();
                } else if (action.equals(ACTION_PAUSE_ALL)) {
                    if (intent.getBooleanExtra("isError", false)) {
                        WorldCupDownloadManager.getInstance(this).errorPauseAll();
                    } else {
                        WorldCupDownloadManager.getInstance(this).pauseAll();
                    }
                } else if (action.equals(ACTION_STOP_SERVICE)) {
                    stopSelf();
                }
            }
        }
    }

    public static void pauseDowload(Context mContext, String episodeId) {
        if (!TextUtils.isEmpty(episodeId)) {
            Intent i = new Intent(mContext, LetvDownloadService.class);
            i.setAction(ACTION_PAUSE);
            i.putExtra("id", episodeId);
            mContext.startService(i);
        }
    }

    public static void pauseAllDownload(Context mContext, boolean isError) {
        Intent i = new Intent(mContext, LetvDownloadService.class);
        i.setAction(ACTION_PAUSE_ALL);
        i.putExtra("isError", isError);
        mContext.startService(i);
    }

    public static void resumeDownload(Context mContext, String episodeId) {
        if (!TextUtils.isEmpty(episodeId)) {
            Intent i = new Intent(mContext, LetvDownloadService.class);
            i.setAction(ACTION_RESUME);
            i.putExtra("id", episodeId);
            mContext.startService(i);
        }
    }

    public static void removeDownload(Context mContext, String episodeId) {
        if (!TextUtils.isEmpty(episodeId)) {
            Intent i = new Intent(mContext, LetvDownloadService.class);
            i.setAction(ACTION_REMOVE);
            i.putExtra("id", episodeId);
            mContext.startService(i);
        }
    }

    public static void removeAllDownload(Context mContext) {
        Intent i = new Intent(mContext, LetvDownloadService.class);
        i.setAction(ACTION_REMOVE_ALL);
        mContext.startService(i);
    }

    public static void startAllDownload(Context mContext) {
        Intent i = new Intent(mContext, LetvDownloadService.class);
        i.setAction(ACTION_START_ALL);
        mContext.startService(i);
    }

    public static void stopService(Context context) {
        Intent i = new Intent(context, LetvDownloadService.class);
        i.setAction(ACTION_STOP_SERVICE);
        context.startService(i);
    }
}
