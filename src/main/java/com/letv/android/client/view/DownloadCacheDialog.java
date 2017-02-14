package com.letv.android.client.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.UIsUtils;
import com.letv.download.manager.StoreManager;
import com.letv.download.manager.StoreManager.StoreDeviceInfo;
import com.letv.download.util.L;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class DownloadCacheDialog extends Dialog {
    private static final String TAG = DownloadCacheDialog.class.getSimpleName();
    private Context context;
    private ImageView iv_mobile;
    private ImageView iv_sd;
    StoreDeviceInfo mPhoneStoreDeviceInfo;
    StoreDeviceInfo mSdCardStoreDeviceInfo;
    OnDownloadDialogLister mlistener;
    private RelativeLayout rl_sd;
    private RelativeLayout rl_storage;
    private View root;
    private TextView tv_cancel;
    private TextView tv_mobile;
    private TextView tv_mobile_txt;
    private TextView tv_sd;
    private TextView tv_sd_txt;

    public interface OnDownloadDialogLister {
        void changeView();
    }

    protected DownloadCacheDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context, cancelable, cancelListener);
    }

    protected DownloadCacheDialog(Context context, int theme) {
        super(context, theme);
    }

    public DownloadCacheDialog(Context context, OnDownloadDialogLister listener) {
        this(context, 2131230820);
        this.context = context;
        setCanceledOnTouchOutside(true);
        this.mlistener = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogInfo.log("ljnalex", "DownloadCacheDialog:onCreate");
        this.root = UIsUtils.inflate(this.context, R.layout.download_cache_dialog, null);
        this.rl_storage = (RelativeLayout) this.root.findViewById(R.id.download_cache_root_storage);
        this.rl_sd = (RelativeLayout) this.root.findViewById(R.id.download_cache_root_sd);
        this.tv_mobile = (TextView) this.root.findViewById(R.id.download_cache_storage_space);
        this.tv_sd = (TextView) this.root.findViewById(R.id.download_cache_sd_space);
        this.tv_cancel = (TextView) this.root.findViewById(R.id.download_cache_cancel);
        this.tv_mobile_txt = (TextView) this.root.findViewById(R.id.download_cache_storage_ivtxt);
        this.tv_sd_txt = (TextView) this.root.findViewById(R.id.download_cache_sd_ivtxt);
        this.iv_mobile = (ImageView) this.root.findViewById(R.id.download_cache_storage_iv);
        this.iv_sd = (ImageView) this.root.findViewById(R.id.download_cache_sd_iv);
        UIsUtils.zoomViewWidth(300, this.root);
        UIsUtils.zoomView(284, 58, this.rl_storage);
        UIsUtils.zoomView(284, 58, this.rl_sd);
        try {
            this.mPhoneStoreDeviceInfo = (StoreDeviceInfo) StoreManager.getPhoneStoreDeviceInfo().clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mSdCardStoreDeviceInfo = StoreManager.getSdCardStoreDeviceInfo();
        L.v(TAG, "mPhoneStoreDeviceInfo : " + this.mPhoneStoreDeviceInfo);
        L.v(TAG, "mSdCardStoreDeviceInfo : " + this.mSdCardStoreDeviceInfo);
        if (this.mPhoneStoreDeviceInfo != null) {
            setPhoneStoreView(this.mPhoneStoreDeviceInfo);
        } else {
            this.rl_storage.setVisibility(8);
        }
        this.rl_sd.setEnabled(true);
        if (this.mSdCardStoreDeviceInfo != null) {
            setSDCardPathView(this.mSdCardStoreDeviceInfo);
        } else if (StoreManager.isSdCardPull()) {
            this.mSdCardStoreDeviceInfo = new StoreDeviceInfo(StoreManager.getSdCardStorePath(), StoreManager.getSdCardStorePath(), false, false);
            setSDCardPathView(this.mSdCardStoreDeviceInfo);
            this.rl_sd.setEnabled(false);
        } else {
            this.rl_sd.setVisibility(8);
        }
        this.rl_storage.setOnClickListener(new 1(this));
        this.rl_sd.setOnClickListener(new 2(this));
        this.tv_cancel.setOnClickListener(new 3(this));
    }

    public void showDialog() {
        show();
        getWindow().setContentView(this.root);
    }

    public void setPhoneStoreView(StoreDeviceInfo storeDeviceInfo) {
        this.rl_storage.setVisibility(0);
        this.tv_mobile.setText(this.context.getResources().getString(2131100058, new Object[]{LetvUtils.getGBNumber(storeDeviceInfo.mAvailable, 1), LetvUtils.getGBNumber(storeDeviceInfo.mTotalSpace, 1)}));
        if (storeDeviceInfo.mAvailable < StoreManager.DEFAULT_SDCARD_SIZE) {
            this.tv_mobile_txt.setVisibility(0);
        }
        this.iv_mobile.setImageResource(storeDeviceInfo.mIsSelect ? 2130837822 : 2130837823);
    }

    public void setSDCardPathView(StoreDeviceInfo storeDeviceInfo) {
        this.rl_sd.setVisibility(0);
        if (storeDeviceInfo.mIsMount) {
            this.tv_sd.setText(this.context.getResources().getString(2131100058, new Object[]{LetvUtils.getGBNumber(storeDeviceInfo.mAvailable, 1), LetvUtils.getGBNumber(storeDeviceInfo.mTotalSpace, 1)}));
        } else {
            this.tv_sd.setText(this.context.getResources().getString(2131100059));
            this.tv_sd.setTextColor(this.context.getResources().getColor(2131493312));
        }
        if (storeDeviceInfo.mIsMount && storeDeviceInfo.mAvailable < StoreManager.DEFAULT_SDCARD_SIZE) {
            this.tv_sd_txt.setVisibility(0);
        }
        this.iv_sd.setImageResource(storeDeviceInfo.mIsSelect ? 2130837822 : 2130837823);
    }
}
