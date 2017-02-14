package com.letv.android.client.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.commonlib.popdialog.ApkDownloadAsyncTask;
import com.letv.android.client.commonlib.view.LoadingDialog;
import com.letv.android.client.utils.UIs;
import com.letv.core.bean.TipMapBean.TipBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.TipUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.tencent.tmsecurelite.commom.ServiceManager;
import com.tencent.tmsecurelite.optimize.ISystemOptimize;

public class ImageCacheClearActivity extends WrapActivity implements OnClickListener {
    private static final String TAG = "ImageCacheClearActivity";
    private static final String TECENT_MOBILE_HOUSEKEEPER_DOWNLOAD_URL = "http://misc.wcd.qq.com/app?packageName=com.tencent.qqpimsecure&channelId=85614";
    private ImageView mBack;
    private boolean mBinded;
    private TextView mCleanPhoneGrabage;
    private TextView mClearImageCache;
    private volatile ISystemOptimize mGarbageCleanService;
    private ServiceConnection mTmsServiceConnection;

    public ImageCacheClearActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mBinded = false;
        this.mTmsServiceConnection = new ServiceConnection(this) {
            final /* synthetic */ ImageCacheClearActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onServiceDisconnected(ComponentName name) {
                this.this$0.mGarbageCleanService = null;
                this.this$0.mBinded = false;
                LogInfo.log("wuxinrong", "ImageCacheClearActivity onServiceDisconnected");
            }

            public void onServiceConnected(ComponentName name, IBinder binder) {
                LogInfo.log("wuxinrong", "ImageCacheClearActivity onServiceConnected");
                this.this$0.mGarbageCleanService = (ISystemOptimize) ServiceManager.getInterface(0, binder);
                this.this$0.mBinded = true;
            }
        };
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ImageCacheClearActivity.class));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_center_image_cache_clear_layout);
        initView();
    }

    protected void onDestroy() {
        if (this.mGarbageCleanService != null && this.mBinded) {
            unbindService(this.mTmsServiceConnection);
        }
        super.onDestroy();
    }

    private void initView() {
        this.mClearImageCache = (TextView) findViewById(R.id.textview_clear_image_cache);
        this.mClearImageCache.setOnClickListener(this);
        this.mCleanPhoneGrabage = (TextView) findViewById(R.id.textview_clean_phone_garbage);
        this.mCleanPhoneGrabage.setOnClickListener(this);
        this.mBack = (ImageView) findViewById(R.id.imageview_back);
        this.mBack.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_back /*2131364255*/:
                finish();
                return;
            case R.id.textview_clear_image_cache /*2131364258*/:
                clearImageCache();
                return;
            case R.id.textview_clean_phone_garbage /*2131364260*/:
                cleanPhoneGarbage();
                return;
            default:
                return;
        }
    }

    public String getActivityName() {
        return TAG;
    }

    public Activity getActivity() {
        return this;
    }

    private void clearImageCache() {
        UIs.showDialog(getActivity(), getActivity().getString(2131100405), null, 0, "", "", null, new DialogInterface.OnClickListener(this) {
            final /* synthetic */ ImageCacheClearActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(DialogInterface dialog, int which) {
                String msg;
                dialog.dismiss();
                TipBean tipBean = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_20007);
                if (tipBean == null) {
                    msg = this.this$0.getActivity().getResources().getText(2131100000).toString();
                } else {
                    msg = tipBean.message;
                }
                LoadingDialog dlg = new LoadingDialog(this.this$0.getActivity(), msg);
                dlg.setCancelable(false);
                ImageDownloader.deleteAllFile(this.this$0.mContext, new 1(this, dlg));
            }
        }, 0, 0, 0, 0);
    }

    private void cleanPhoneGarbage() {
        Intent intent = ServiceManager.getIntent(0);
        LogInfo.log("wuxinrong", "开始绑定Service...");
        this.mBinded = bindService(intent, this.mTmsServiceConnection, 1);
        if (this.mBinded) {
            performPhoneGarbageClean();
        } else {
            showTencentHousekeeperDownloadDialog();
        }
    }

    private void showTencentHousekeeperDownloadDialog() {
        DialogInterface.OnClickListener confirmListener = new DialogInterface.OnClickListener(this) {
            final /* synthetic */ ImageCacheClearActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                this.this$0.downloadHousekeeperApk();
            }
        };
        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener(this) {
            final /* synthetic */ ImageCacheClearActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        Activity activity = getActivity();
        UIs.showDialog(activity, activity.getString(2131100185), null, 0, activity.getString(2131100742), activity.getString(2131099999), confirmListener, cancelListener, 0, 0, 2131493248, 0);
    }

    private void performPhoneGarbageClean() {
        if (this.mGarbageCleanService != null) {
            unbindService(this.mTmsServiceConnection);
            this.mBinded = false;
        }
        GarbageCleanActivity.launch(this.mContext);
    }

    private void downloadHousekeeperApk() {
        ApkDownloadAsyncTask.downloadApk(getActivity(), TECENT_MOBILE_HOUSEKEEPER_DOWNLOAD_URL, getActivity().getString(2131100184));
    }
}
