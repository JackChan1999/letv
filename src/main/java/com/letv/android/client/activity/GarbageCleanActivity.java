package com.letv.android.client.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.GarbageFileListAdapter;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import com.sina.weibo.sdk.utils.UIUtils;
import com.tencent.tmsecurelite.commom.DataEntity;
import com.tencent.tmsecurelite.commom.DataEntityKeyConst;
import com.tencent.tmsecurelite.commom.ITmsCallback;
import com.tencent.tmsecurelite.commom.ServiceManager;
import com.tencent.tmsecurelite.commom.TmsCallbackStub;
import com.tencent.tmsecurelite.optimize.IRubbishScanListener;
import com.tencent.tmsecurelite.optimize.ISystemOptimize;
import com.tencent.tmsecurelite.optimize.RubbishConst;
import com.tencent.tmsecurelite.optimize.RubbishScanListenerStub;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;

public class GarbageCleanActivity extends WrapActivity implements OnClickListener {
    private static final int MSG_APP_CACHE_SCAN_FINISHED = 1;
    private static final int MSG_CLEANING = 5;
    private static final int MSG_GARBAGE_SCAN_FINISHED = 2;
    private static final int MSG_REFRESH = 0;
    private static final int MSG_REMOVE_ITEM = 3;
    private static final int MSG_SCANING = 4;
    public static final long ONE_GB = 1073741824;
    public static final long ONE_KB = 1024;
    public static final long ONE_MB = 1048576;
    public static final long ONE_TB = 1099511627776L;
    private static final String TAG = "CleanPhoneGarbageActivity";
    private final int BACKGROUND_VIEW_COLOR_FINISH;
    private final int BACKGROUND_VIEW_COLOR_SELECTING;
    private final int BACKGROUND_VIEW_RESOURCE;
    private final int HOUSEKEEPER_VIEW_RES_FINISH;
    private final int HOUSEKEEPER_VIEW_RES_INITIAL;
    private final int HOUSEKEEPER_VIEW_RES_SELECTED;
    private long mAccumulatedCleanSize;
    private boolean mAllSelected;
    private ITmsCallback mAppCacheCallback;
    private boolean mAppCacheScanFinished;
    private long mAppCacheSize;
    private ImageView mBack;
    private ImageView mBackgroundView;
    private ISystemOptimize mCleanService;
    private ArrayList<GarbageFileInfo> mGarbageFileList;
    private TextView mGarbageFileText;
    private long mGarbageFileTotalSize;
    private TextView mGarbageFileTotalSizeTextView;
    private ArrayList<GARBAGE_FILE_TYPE> mGarbageFileTypeSelectionList;
    private TextView mGarbageFileUnitTextView;
    GarbageHolder mGarbageHolder;
    private boolean mGarbageScanFinished;
    private Handler mHandler;
    private ImageView mHousekeeperView;
    private RelativeLayout mLayout;
    private GarbageFileListAdapter mListAdapter;
    private ListView mListView;
    private OPERATION_STATE mOperationState;
    private ITmsCallback mRedundantApkCallback;
    private ArrayList<String> mRedundantApkFilePathList;
    private IRubbishScanListener mRubbishScanListener;
    private Button mScanButton;
    private ITmsCallback mSysCacheCallback;
    private ArrayList<String> mSysCacheFilePathList;
    private ITmsCallback mSysGarbageCallback;
    private ArrayList<String> mSysGarbageFilePathList;
    private ServiceConnection mTmsServiceConnection;
    private ITmsCallback mUninstallRemainsCacheCallback;
    private ArrayList<String> mUninstallRemainsFilePathList;

    public enum GARBAGE_FILE_TYPE {
        RUBBISH_TOTAL,
        APP_CACHE,
        SYS_CACHE,
        SYS_GARBAGE,
        UNINSTALL_REMAINS,
        REDUNDANT_APK
    }

    public static class GarbageFileInfo {
        public long size;
        public GARBAGE_FILE_TYPE type;

        public GarbageFileInfo(GARBAGE_FILE_TYPE type, long size) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.type = type;
            this.size = size;
        }
    }

    private class GarbageHolder {
        public long redundantApkSize;
        public long sysCacheSize;
        public long sysGarbageFileSize;
        public long uninstallRetainsSize;

        private GarbageHolder() {
        }

        /* synthetic */ GarbageHolder(GarbageCleanActivity x0, AnonymousClass1 x1) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this();
        }
    }

    private enum OPERATION_STATE {
        SCAN,
        SCANING,
        CLEAN,
        CLEANING,
        FINISH
    }

    public GarbageCleanActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.BACKGROUND_VIEW_RESOURCE = 2130837643;
        this.BACKGROUND_VIEW_COLOR_SELECTING = Color.parseColor("#FFBB33");
        this.BACKGROUND_VIEW_COLOR_FINISH = Color.parseColor("#66CC33");
        this.HOUSEKEEPER_VIEW_RES_INITIAL = 2130838207;
        this.HOUSEKEEPER_VIEW_RES_SELECTED = 2130838208;
        this.HOUSEKEEPER_VIEW_RES_FINISH = 2130838206;
        this.mGarbageFileTypeSelectionList = new ArrayList();
        this.mGarbageFileList = new ArrayList();
        this.mAppCacheScanFinished = false;
        this.mGarbageScanFinished = false;
        this.mGarbageFileTotalSize = 0;
        this.mAppCacheSize = 0;
        this.mAllSelected = false;
        this.mAccumulatedCleanSize = 0;
        this.mOperationState = OPERATION_STATE.SCAN;
        this.mSysCacheFilePathList = new ArrayList();
        this.mSysGarbageFilePathList = new ArrayList();
        this.mUninstallRemainsFilePathList = new ArrayList();
        this.mRedundantApkFilePathList = new ArrayList();
        this.mTmsServiceConnection = new ServiceConnection(this) {
            final /* synthetic */ GarbageCleanActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onServiceDisconnected(ComponentName name) {
                this.this$0.mCleanService = null;
            }

            public void onServiceConnected(ComponentName name, IBinder binder) {
                this.this$0.mCleanService = (ISystemOptimize) ServiceManager.getInterface(0, binder);
                this.this$0.performScan();
            }
        };
        this.mHandler = new Handler(this) {
            final /* synthetic */ GarbageCleanActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void handleMessage(Message msg) {
                this.this$0.updateGarbageFileInfoArea(OPERATION_STATE.SCAN, this.this$0.mGarbageFileTotalSize);
                switch (msg.what) {
                    case 0:
                        this.this$0.mListAdapter.notifyDataSetChanged();
                        return;
                    case 1:
                        this.this$0.mAppCacheScanFinished = true;
                        if (this.this$0.mGarbageScanFinished) {
                            LogInfo.log("wuxinrong", "APP_CACHE_SCAN完成");
                            this.this$0.mOperationState = OPERATION_STATE.SCAN;
                            return;
                        }
                        return;
                    case 2:
                        this.this$0.mGarbageScanFinished = true;
                        if (this.this$0.mAppCacheScanFinished) {
                            LogInfo.log("wuxinrong", "GARBAGE_SCAN完成");
                            this.this$0.mOperationState = OPERATION_STATE.SCAN;
                            return;
                        }
                        return;
                    case 3:
                        GARBAGE_FILE_TYPE type = msg.obj;
                        if (type != null) {
                            this.this$0.calcAccumulatedCleanSize(type);
                            this.this$0.removeGarbageFileInfoItem(type);
                            this.this$0.mGarbageFileTypeSelectionList.remove(type);
                            if (this.this$0.mGarbageFileTypeSelectionList.size() == 0) {
                                this.this$0.mOperationState = OPERATION_STATE.FINISH;
                                this.this$0.mScanButton.setBackgroundResource(2130838924);
                                this.this$0.mScanButton.setText(this.this$0.mContext.getResources().getString(2131099789));
                                this.this$0.mScanButton.setTextColor(this.this$0.getResources().getColor(2131493098));
                                this.this$0.mListView.setVisibility(4);
                                this.this$0.mBackgroundView.setBackgroundColor(this.this$0.BACKGROUND_VIEW_COLOR_FINISH);
                                this.this$0.mHousekeeperView.setBackgroundResource(2130838206);
                                this.this$0.updateGarbageFileInfoArea(OPERATION_STATE.FINISH, this.this$0.mAccumulatedCleanSize);
                                ((RelativeLayout) this.this$0.findViewById(R.id.rl_garbage_file_clean_activity)).setBackgroundColor(this.this$0.getResources().getColor(2131493333));
                            }
                            this.this$0.mListAdapter.notifyDataSetChanged();
                            return;
                        }
                        return;
                    case 4:
                        this.this$0.mOperationState = OPERATION_STATE.SCANING;
                        return;
                    case 5:
                        this.this$0.mOperationState = OPERATION_STATE.CLEANING;
                        return;
                    default:
                        return;
                }
            }
        };
        this.mAppCacheCallback = new TmsCallbackStub(this) {
            final /* synthetic */ GarbageCleanActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onResultGot(int err, DataEntity result) throws RemoteException {
                this.this$0.sendCustomMessage(3, GARBAGE_FILE_TYPE.APP_CACHE);
            }

            public void onArrayResultGot(int err, ArrayList<DataEntity> result) throws RemoteException {
                try {
                    Iterator it = result.iterator();
                    while (it.hasNext()) {
                        this.this$0.mAppCacheSize = this.this$0.mAppCacheSize + ((DataEntity) it.next()).getLong(DataEntityKeyConst.CacheSize_LONG);
                    }
                    if (this.this$0.mAppCacheSize > 0) {
                        this.this$0.addGarbageFileInfoItem(GARBAGE_FILE_TYPE.APP_CACHE, this.this$0.mAppCacheSize);
                        this.this$0.mGarbageFileTotalSize = this.this$0.mGarbageFileTotalSize + this.this$0.mAppCacheSize;
                        this.this$0.addGarbageFileInfoItem(GARBAGE_FILE_TYPE.RUBBISH_TOTAL, this.this$0.mGarbageFileTotalSize);
                        this.this$0.mHandler.sendEmptyMessage(1);
                    }
                } catch (Exception e) {
                }
            }
        };
        this.mSysCacheCallback = new TmsCallbackStub(this) {
            final /* synthetic */ GarbageCleanActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onResultGot(int arg0, DataEntity arg1) throws RemoteException {
                this.this$0.sendCustomMessage(3, GARBAGE_FILE_TYPE.SYS_CACHE);
            }

            public void onArrayResultGot(int arg0, ArrayList<DataEntity> arrayList) throws RemoteException {
            }
        };
        this.mSysGarbageCallback = new TmsCallbackStub(this) {
            final /* synthetic */ GarbageCleanActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onResultGot(int arg0, DataEntity arg1) throws RemoteException {
                this.this$0.sendCustomMessage(3, GARBAGE_FILE_TYPE.SYS_GARBAGE);
            }

            public void onArrayResultGot(int arg0, ArrayList<DataEntity> arrayList) throws RemoteException {
            }
        };
        this.mRedundantApkCallback = new TmsCallbackStub(this) {
            final /* synthetic */ GarbageCleanActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onResultGot(int arg0, DataEntity arg1) throws RemoteException {
                this.this$0.sendCustomMessage(3, GARBAGE_FILE_TYPE.REDUNDANT_APK);
            }

            public void onArrayResultGot(int arg0, ArrayList<DataEntity> arrayList) throws RemoteException {
            }
        };
        this.mUninstallRemainsCacheCallback = new TmsCallbackStub(this) {
            final /* synthetic */ GarbageCleanActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onArrayResultGot(int arg0, ArrayList<DataEntity> arrayList) throws RemoteException {
            }

            public void onResultGot(int arg0, DataEntity arg1) throws RemoteException {
                this.this$0.sendCustomMessage(3, GARBAGE_FILE_TYPE.UNINSTALL_REMAINS);
            }
        };
        this.mRubbishScanListener = new RubbishScanListenerStub(this) {
            final /* synthetic */ GarbageCleanActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onScanStarted() throws RemoteException {
                this.this$0.mGarbageHolder = new GarbageHolder(this.this$0, null);
            }

            public void onScanProgressChanged(int progress) throws RemoteException {
            }

            public void onScanFinished() throws RemoteException {
                this.this$0.mHandler.sendEmptyMessage(2);
            }

            public void onScanCanceled() throws RemoteException {
            }

            public void onRubbishFound(int type, DataEntity entity) throws RemoteException {
                try {
                    long size = entity.getLong(RubbishConst.key_Size_Long);
                    GarbageHolder garbageHolder;
                    switch (type) {
                        case 1:
                            this.this$0.mGarbageFileTotalSize = this.this$0.mGarbageFileTotalSize + size;
                            garbageHolder = this.this$0.mGarbageHolder;
                            garbageHolder.sysCacheSize += size;
                            this.this$0.addGarbageFileInfoItem(GARBAGE_FILE_TYPE.SYS_CACHE, this.this$0.mGarbageHolder.sysCacheSize);
                            this.this$0.addGarbageFilePathList(this.this$0.mSysCacheFilePathList, entity);
                            break;
                        case 2:
                            this.this$0.mGarbageFileTotalSize = this.this$0.mGarbageFileTotalSize + size;
                            garbageHolder = this.this$0.mGarbageHolder;
                            garbageHolder.redundantApkSize += size;
                            this.this$0.addGarbageFileInfoItem(GARBAGE_FILE_TYPE.REDUNDANT_APK, this.this$0.mGarbageHolder.redundantApkSize);
                            this.this$0.addGarbageFilePathList(this.this$0.mRedundantApkFilePathList, entity);
                            break;
                        case 3:
                            this.this$0.mGarbageFileTotalSize = this.this$0.mGarbageFileTotalSize + size;
                            garbageHolder = this.this$0.mGarbageHolder;
                            garbageHolder.uninstallRetainsSize += size;
                            this.this$0.addGarbageFileInfoItem(GARBAGE_FILE_TYPE.UNINSTALL_REMAINS, this.this$0.mGarbageHolder.uninstallRetainsSize);
                            this.this$0.addGarbageFilePathList(this.this$0.mUninstallRemainsFilePathList, entity);
                            break;
                        case 4:
                            this.this$0.mGarbageFileTotalSize = this.this$0.mGarbageFileTotalSize + size;
                            garbageHolder = this.this$0.mGarbageHolder;
                            garbageHolder.sysGarbageFileSize += size;
                            this.this$0.addGarbageFileInfoItem(GARBAGE_FILE_TYPE.SYS_GARBAGE, this.this$0.mGarbageHolder.sysGarbageFileSize);
                            this.this$0.addGarbageFilePathList(this.this$0.mSysGarbageFilePathList, entity);
                            break;
                    }
                    this.this$0.addGarbageFileInfoItem(GARBAGE_FILE_TYPE.RUBBISH_TOTAL, this.this$0.mGarbageFileTotalSize);
                } catch (JSONException e) {
                }
            }
        };
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, GarbageCleanActivity.class));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_center_garbage_clean_layout);
        readAccumulatedCleanValue();
        initView();
        initListener();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    private void readAccumulatedCleanValue() {
        this.mAccumulatedCleanSize = PreferencesManager.getInstance().getAccumulatedCleanSize();
    }

    private void writeAccumulatedCleanValue() {
        PreferencesManager.getInstance().setAccumulatedCleanSize(this.mAccumulatedCleanSize);
    }

    private void performScan() {
        scanAppCache();
        scanRubbish();
    }

    private void performClean() {
        sendCustomMessage(5, null);
        cleanAppCache();
        cleanRubbish();
    }

    private void cleanAppCache() {
        if (this.mGarbageFileTypeSelectionList.contains(GARBAGE_FILE_TYPE.APP_CACHE)) {
            new Thread(new Runnable(this) {
                final /* synthetic */ GarbageCleanActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void run() {
                    try {
                        this.this$0.mCleanService.clearAppsCacheAsync(this.this$0.mAppCacheCallback);
                    } catch (RemoteException e) {
                    }
                }
            }).start();
        }
    }

    private void cleanRubbish() {
        try {
            if (this.mGarbageFileTypeSelectionList.contains(GARBAGE_FILE_TYPE.SYS_CACHE)) {
                LogInfo.log("wuxinrong", "清理 系统缓存");
                this.mCleanService.cleanRubbishAsync(this.mSysCacheCallback, this.mSysCacheFilePathList);
            }
            if (this.mGarbageFileTypeSelectionList.contains(GARBAGE_FILE_TYPE.SYS_GARBAGE)) {
                LogInfo.log("wuxinrong", "清理 系统垃圾");
                this.mCleanService.cleanRubbishAsync(this.mSysGarbageCallback, this.mSysGarbageFilePathList);
            }
            if (this.mGarbageFileTypeSelectionList.contains(GARBAGE_FILE_TYPE.REDUNDANT_APK)) {
                LogInfo.log("wuxinrong", "清理 冗余APK");
                this.mCleanService.cleanRubbishAsync(this.mRedundantApkCallback, this.mRedundantApkFilePathList);
            }
            if (this.mGarbageFileTypeSelectionList.contains(GARBAGE_FILE_TYPE.UNINSTALL_REMAINS)) {
                LogInfo.log("wuxinrong", "清理 卸载残留文件");
                this.mCleanService.cleanRubbishAsync(this.mUninstallRemainsCacheCallback, this.mUninstallRemainsFilePathList);
            }
        } catch (RemoteException e) {
        }
    }

    private void calcAccumulatedCleanSize(GARBAGE_FILE_TYPE type) {
        if (type.equals(GARBAGE_FILE_TYPE.APP_CACHE)) {
            this.mAccumulatedCleanSize += this.mAppCacheSize;
        } else if (type.equals(GARBAGE_FILE_TYPE.SYS_CACHE)) {
            this.mAccumulatedCleanSize += this.mGarbageHolder.sysCacheSize;
        } else if (type.equals(GARBAGE_FILE_TYPE.SYS_GARBAGE)) {
            this.mAccumulatedCleanSize += this.mGarbageHolder.sysGarbageFileSize;
        } else if (type.equals(GARBAGE_FILE_TYPE.REDUNDANT_APK)) {
            this.mAccumulatedCleanSize += this.mGarbageHolder.redundantApkSize;
        } else if (type.equals(GARBAGE_FILE_TYPE.UNINSTALL_REMAINS)) {
            this.mAccumulatedCleanSize += this.mGarbageHolder.uninstallRetainsSize;
        } else {
            throw new IllegalArgumentException("calcAccumulatedCleanSize param type illegal !");
        }
    }

    private void initView() {
        this.mBack = (ImageView) findViewById(R.id.phone_garbage_clean_back);
        this.mScanButton = (Button) findViewById(R.id.phone_garbage_clean_scan);
        this.mBackgroundView = (ImageView) findViewById(R.id.bg_phone_garbage_clean_image);
        this.mBackgroundView.setBackgroundResource(2130837643);
        this.mHousekeeperView = (ImageView) findViewById(R.id.iv_phone_garbage_housekeeper);
        this.mHousekeeperView.setBackgroundResource(2130838207);
        this.mGarbageFileTotalSizeTextView = (TextView) findViewById(R.id.garbage_file_total_size);
        this.mGarbageFileUnitTextView = (TextView) findViewById(R.id.garbage_file_size_unit);
        this.mGarbageFileText = (TextView) findViewById(R.id.garbage_file_text);
        this.mLayout = (RelativeLayout) findViewById(R.id.rl_garbage_file_info);
        this.mLayout.setVisibility(4);
        this.mListAdapter = new GarbageFileListAdapter(this, this.mGarbageFileList);
        this.mListAdapter.setSelectionList(this.mGarbageFileTypeSelectionList);
        this.mListView = (ListView) findViewById(R.id.garbage_file_list);
        this.mListView.setAdapter(this.mListAdapter);
        this.mListView.setChoiceMode(1);
        this.mListView.setVisibility(4);
    }

    private void addGarbageFileInfoItem(GARBAGE_FILE_TYPE type, long size) {
        if (size != 0 || type == GARBAGE_FILE_TYPE.RUBBISH_TOTAL) {
            boolean found = false;
            GarbageFileInfo info = null;
            for (int i = 0; i < this.mGarbageFileList.size(); i++) {
                info = (GarbageFileInfo) this.mGarbageFileList.get(i);
                if (info != null && type == info.type) {
                    found = true;
                    break;
                }
            }
            if (found) {
                info.size = size;
            } else {
                this.mGarbageFileList.add(new GarbageFileInfo(type, size));
            }
            this.mHandler.sendEmptyMessage(0);
        }
    }

    private void removeGarbageFileInfoItem(GARBAGE_FILE_TYPE type) {
        int index = getIndexByType(type);
        if (index >= 0) {
            this.mGarbageFileList.remove(index);
            this.mHandler.sendEmptyMessage(0);
        }
    }

    private int getIndexByType(GARBAGE_FILE_TYPE type) {
        for (int i = 0; i < this.mGarbageFileList.size(); i++) {
            if (type.equals(((GarbageFileInfo) this.mGarbageFileList.get(i)).type)) {
                return i;
            }
        }
        return -1;
    }

    private void selectAll() {
        if (this.mAllSelected) {
            this.mGarbageFileTypeSelectionList.clear();
            this.mAllSelected = false;
            return;
        }
        this.mGarbageFileTypeSelectionList.clear();
        for (int pos = 0; pos < this.mListAdapter.getCount(); pos++) {
            GarbageFileInfo info = this.mListAdapter.getItem(pos);
            if (pos != 0) {
                this.mGarbageFileTypeSelectionList.add(info.type);
            }
        }
        this.mAllSelected = true;
    }

    private void initListener() {
        this.mBack.setOnClickListener(this);
        this.mScanButton.setOnClickListener(this);
        this.mListView.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ GarbageCleanActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (this.this$0.mOperationState != OPERATION_STATE.SCANING && this.this$0.mOperationState != OPERATION_STATE.CLEANING) {
                    int preSize = this.this$0.mGarbageFileTypeSelectionList.size();
                    ImageView iv = (ImageView) view.findViewById(R.id.iv_garbage_file_selection);
                    if (iv.getVisibility() != 0) {
                        iv.setVisibility(0);
                        this.this$0.mGarbageFileTypeSelectionList.add(this.this$0.mListAdapter.getItem(position).type);
                        if (this.this$0.mGarbageFileTypeSelectionList.size() == this.this$0.mListAdapter.getCount() - 1) {
                            this.this$0.selectAll();
                        }
                    } else {
                        iv.setVisibility(4);
                        if (position != 0) {
                            this.this$0.mGarbageFileTypeSelectionList.remove(this.this$0.mListAdapter.getItem(position).type);
                            this.this$0.mAllSelected = false;
                        }
                    }
                    if (position == 0) {
                        LogInfo.log("wuxinrong", "点击首项选择了全部");
                        this.this$0.selectAll();
                    }
                    if (this.this$0.mGarbageFileTypeSelectionList.size() > 0) {
                        long totalSelected;
                        if (preSize == 0) {
                            this.this$0.mBackgroundView.setBackgroundColor(this.this$0.BACKGROUND_VIEW_COLOR_SELECTING);
                            this.this$0.mHousekeeperView.setBackgroundResource(2130838208);
                        }
                        if (this.this$0.mGarbageFileTypeSelectionList.size() < this.this$0.mListAdapter.getCount() - 1) {
                            totalSelected = 0;
                        } else {
                            totalSelected = 0;
                        }
                        for (int i = 0; i < this.this$0.mGarbageFileTypeSelectionList.size(); i++) {
                            totalSelected += this.this$0.mListAdapter.getItem((GARBAGE_FILE_TYPE) this.this$0.mGarbageFileTypeSelectionList.get(i)).size;
                        }
                        this.this$0.updateGarbageFileInfoArea(OPERATION_STATE.SCAN, totalSelected);
                        String buttonText = this.this$0.mContext.getResources().getString(2131100140, new Object[]{GarbageCleanActivity.transformShortType(totalSelected) + GarbageCleanActivity.transformUnit(totalSelected)});
                        this.this$0.mScanButton.setBackgroundResource(2130838923);
                        this.this$0.mScanButton.setText(buttonText);
                        this.this$0.mOperationState = OPERATION_STATE.CLEAN;
                    } else {
                        this.this$0.mBackgroundView.setBackgroundResource(2130837643);
                        this.this$0.mHousekeeperView.setBackgroundResource(2130838207);
                        this.this$0.mLayout.setVisibility(4);
                        this.this$0.mScanButton.setBackgroundResource(2130838925);
                        this.this$0.mScanButton.setText(this.this$0.mContext.getResources().getString(2131100147));
                        this.this$0.mOperationState = OPERATION_STATE.SCAN;
                    }
                    this.this$0.mListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_garbage_clean_back /*2131364242*/:
                onBack();
                return;
            case R.id.phone_garbage_clean_scan /*2131364252*/:
                if (this.mOperationState.equals(OPERATION_STATE.SCAN)) {
                    sendCustomMessage(4, null);
                    reset();
                    addGarbageFileInfoItem(GARBAGE_FILE_TYPE.RUBBISH_TOTAL, 0);
                    new Thread(new Runnable(this) {
                        final /* synthetic */ GarbageCleanActivity this$0;

                        {
                            if (HotFix.PREVENT_VERIFY) {
                                System.out.println(VerifyLoad.class);
                            }
                            this.this$0 = this$0;
                        }

                        public void run() {
                            this.this$0.scanGarbageFile();
                        }
                    }).start();
                    this.mListView.setVisibility(0);
                    return;
                } else if (this.mOperationState.equals(OPERATION_STATE.CLEAN)) {
                    performClean();
                    return;
                } else if (this.mOperationState.equals(OPERATION_STATE.FINISH)) {
                    onBack();
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    private void onBack() {
        writeAccumulatedCleanValue();
        finish();
    }

    private void reset() {
        this.mLayout.setVisibility(4);
        this.mAppCacheScanFinished = false;
        this.mGarbageScanFinished = false;
        this.mGarbageFileTotalSize = 0;
        this.mAppCacheSize = 0;
        this.mGarbageFileTypeSelectionList.clear();
        this.mGarbageFileList.clear();
        addGarbageFileInfoItem(GARBAGE_FILE_TYPE.RUBBISH_TOTAL, 0);
    }

    private void updateGarbageFileInfoArea(OPERATION_STATE state, long size) {
        if (size > 0) {
            this.mLayout.setVisibility(0);
            this.mGarbageFileTotalSizeTextView.setText(transformShortType(size));
            this.mGarbageFileUnitTextView.setText(transformUnit(size));
            if (state.equals(OPERATION_STATE.FINISH)) {
                this.mGarbageFileText.setText(2131099699);
            } else {
                this.mGarbageFileText.setText(2131100139);
            }
        }
    }

    private void sendCustomMessage(int what, GARBAGE_FILE_TYPE type) {
        Message msg = this.mHandler.obtainMessage();
        msg.what = what;
        msg.obj = type;
        this.mHandler.sendMessage(msg);
    }

    private void addGarbageFilePathList(ArrayList<String> list, DataEntity de) throws JSONException {
        JSONArray array = de.getJSONArray(RubbishConst.key_Encrypted_Path_JSONArray);
        int len = array.length();
        for (int i = 0; i < len; i++) {
            list.add(array.get(i).toString());
        }
    }

    private boolean bindTMSLiteService() {
        LogInfo.log("wuxinrong", "绑定到TMS 服务...");
        return bindService(ServiceManager.getIntent(0), this.mTmsServiceConnection, 1);
    }

    private void scanGarbageFile() {
        if (this.mCleanService != null) {
            LogInfo.log("wuxinrong", "执行扫描...");
            performScan();
        } else if (!bindTMSLiteService()) {
            UIUtils.showToast((Context) this, getString(2131100938), 3000);
        }
    }

    private void scanAppCache() {
        try {
            this.mCleanService.findAppsWithCacheAsync(this.mAppCacheCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scanRubbish() {
        try {
            this.mCleanService.startScanRubbish(this.mRubbishScanListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static String transformUnit(long bytes) {
        int level = 0;
        for (long unit = 1024; bytes / unit > 0; unit *= 1024) {
            level++;
        }
        String result = "K";
        switch (level) {
            case 1:
                return "K";
            case 2:
                return "M";
            case 3:
                return "G";
            case 4:
                return "T";
            default:
                return result;
        }
    }

    public static String transformShortType(long bytes) {
        long currenUnit = 1024;
        int unitLevel = 0;
        boolean isNegative = false;
        if (bytes < 0) {
            isNegative = true;
            bytes *= -1;
        }
        while (bytes / currenUnit > 0) {
            unitLevel++;
            currenUnit *= 1024;
        }
        String result_text = null;
        switch (unitLevel) {
            case 0:
                result_text = "0";
                break;
            case 1:
                result_text = getFloatValue((double) (bytes / 1024), 2);
                break;
            case 2:
                result_text = getFloatValue((((double) bytes) * 1.0d) / 1048576.0d, 2);
                break;
            case 3:
                result_text = getFloatValue((((double) bytes) * 1.0d) / 1.073741824E9d, 2);
                break;
            case 4:
                result_text = getFloatValue((((double) bytes) * 1.0d) / 1.099511627776E12d, 2);
                break;
        }
        if (isNegative) {
            return NetworkUtils.DELIMITER_LINE + result_text;
        }
        return result_text;
    }

    private static String getFloatValue(double oldValue, int decimals) {
        if (oldValue >= 1000.0d) {
            decimals = 0;
        } else if (oldValue >= 100.0d) {
            decimals = 1;
        }
        BigDecimal b = new BigDecimal(oldValue);
        if (decimals <= 0) {
            try {
                oldValue = (double) b.setScale(0, 1).floatValue();
            } catch (ArithmeticException e) {
                Log.w("Unit.getFloatValue", e.getMessage());
            }
        } else {
            oldValue = (double) b.setScale(decimals, 1).floatValue();
        }
        String decimalStr = "";
        if (decimals <= 0) {
            decimalStr = "#";
        } else {
            for (int i = 0; i < decimals; i++) {
                decimalStr = decimalStr + "#";
            }
        }
        return new DecimalFormat("###." + decimalStr).format(oldValue);
    }

    public String getActivityName() {
        return TAG;
    }

    public Activity getActivity() {
        return this;
    }
}
