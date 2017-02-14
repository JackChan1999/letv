package com.letv.mobile.lebox.ui.download;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.bean.CommonResponse;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.heartbeat.HeartbeatManager;
import com.letv.mobile.lebox.heartbeat.HeartbeatService;
import com.letv.mobile.lebox.http.lebox.bean.StorageGetInfoBean;
import com.letv.mobile.lebox.http.lebox.request.StorageGetInfoHttpRequest;
import com.letv.mobile.lebox.httpmanager.HttpCacheAssistant;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;
import com.letv.mobile.lebox.ui.WrapActivity.IBatchDel;
import com.letv.mobile.lebox.utils.DialogUtil;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.Util;
import com.letv.mobile.lebox.view.TabPageIndicator;

public class MyDownloadActivity extends BaseBatchDelActivity implements OnClickListener {
    public static final int DOWNLOAD_STATE_DOWNLOADING = 1;
    public static final int DOWNLOAD_STATE_FINISH = 0;
    private static final String TAG = MyDownloadActivity.class.getSimpleName();
    ProgressBar mCapacityPBar = null;
    View mCapacityRoot = null;
    TextView mCapacityTextView = null;
    private int mCurrentPostion = 0;
    Button mDeleteBtn;
    DownloadFragmentPagerAdapter mDownloadFragmentPagerAdapter;
    ViewPager mDownloadViewPager;
    TextView mEditView;
    private TabPageIndicator mIndicator;
    OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        public void onPageScrollStateChanged(int arg0) {
            Logger.d(MyDownloadActivity.TAG, "---onPageScrollStateChanged---" + arg0);
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int arg0) {
            Logger.d(MyDownloadActivity.TAG, "onPageSelected arg0 : " + arg0);
            MyDownloadActivity.this.mCurrentPostion = arg0;
            MyDownloadActivity.this.initBatchDelState(arg0);
        }
    };
    Button mSelectBtn;
    private int page;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lebox_download_main);
        initBatchDelView();
        initView();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        this.mCapacityRoot = findViewById(R.id.my_download_layout_capacity_space);
        this.mCapacityTextView = (TextView) findViewById(R.id.my_download_textv_capacity);
        this.mCapacityPBar = (ProgressBar) findViewById(R.id.my_download_progressbar_capacity);
        initNavigationBar();
        initViewPager();
    }

    private void initViewPager() {
        this.mDownloadViewPager = (ViewPager) findViewById(R.id.viewpager);
        this.mDownloadFragmentPagerAdapter = new DownloadFragmentPagerAdapter(getSupportFragmentManager());
        this.mDownloadFragmentPagerAdapter.addFragment(new MyDownloadFinishFragment());
        if (HttpCacheAssistant.getInstanced().isAdmini()) {
            this.mDownloadFragmentPagerAdapter.addFragment(new MyDownloadingFragment());
        }
        this.mDownloadViewPager.setOffscreenPageLimit(this.mDownloadFragmentPagerAdapter.getCount());
        this.mDownloadViewPager.setAdapter(this.mDownloadFragmentPagerAdapter);
        this.mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        this.mIndicator.setViewPager(this.mDownloadViewPager);
        this.mIndicator.setOnPageChangeListener(this.mOnPageChangeListener);
        if (HttpCacheAssistant.getInstanced().isAdmini()) {
            this.mIndicator.setVisibility(0);
        } else {
            this.mIndicator.setVisibility(8);
        }
        this.mDownloadViewPager.setCurrentItem(this.page, false);
        this.mCurrentPostion = this.page;
    }

    public static void launch(Context context, int page) {
        if (context != null) {
            Intent intent = new Intent(context, MyDownloadActivity.class);
            intent.putExtra(MyDownloadActivityConfig.PAGE, page);
            intent.setFlags(268435456);
            context.startActivity(intent);
        }
    }

    public void showDownloadingNum(int num) {
        Logger.d(TAG, "showDownloadingNum num : " + num);
        this.mDownloadFragmentPagerAdapter.setDownloadingNum(num);
        this.mIndicator.notifyDataSetChanged();
    }

    protected void onRestart() {
        super.onRestart();
        restartData();
    }

    protected void onResume() {
        super.onResume();
        updateStoreSpace();
        initBatchDelState(this.mCurrentPostion);
        if (HttpCacheAssistant.getInstanced().getUnFinishList().size() > 0 && HttpCacheAssistant.getInstanced().isAdmini() && LeBoxNetworkManager.getInstance().isLeboxWifiAvailable()) {
            HeartbeatManager.getInstance().setCurrentHeartbeatTime(3000);
        }
    }

    protected void onPause() {
        super.onPause();
        if (HttpCacheAssistant.getInstanced().isAdmini()) {
            HeartbeatManager.getInstance().setCurrentHeartbeatTime(HeartbeatService.HEARTBEAT_INTERVAL_NOMAL);
        }
    }

    public int getFrom() {
        return this.page;
    }

    public boolean onHandleEditViewEvent() {
        return false;
    }

    public void initBatchDelState(int position) {
        if (this.mDownloadFragmentPagerAdapter.getItem(position) != null) {
            updateBatchDelView();
            getEditView().setTag(Integer.valueOf(position));
        }
    }

    private void initNavigationBar() {
        ImageView backImageView = (ImageView) findViewById(R.id.common_nav_left);
        ((TextView) findViewById(R.id.common_nav_title)).setText(getResources().getString(R.string.btn_text_my_download));
        backImageView.setOnClickListener(this);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        this.mDownloadViewPager.setCurrentItem(this.page, false);
        this.mCurrentPostion = this.page;
    }

    public void updateStoreSpace() {
        StorageGetInfoHttpRequest.getGetInfoRequest(this, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                if (code != 0) {
                    Logger.e(MyDownloadActivity.TAG, " --StorageGetInfoHttpRequest  errorCode : " + errorCode + "  msg :" + msg);
                } else if (object == null || !(object instanceof CommonResponse)) {
                    Logger.e(MyDownloadActivity.TAG, " --StorageGetInfoHttpRequest  error object : " + object);
                } else {
                    StorageGetInfoBean bean = (StorageGetInfoBean) ((CommonResponse) object).getData();
                    String used = bean.getUsed();
                    String total = bean.getTotal();
                    Logger.d(MyDownloadActivity.TAG, "--used :" + used + "\n --total：" + total);
                    long totalSize = Long.parseLong(total);
                    long availableSize = totalSize - Long.parseLong(used);
                    MyDownloadActivity.this.mCapacityTextView.setText(String.format(MyDownloadActivity.this.getResources().getString(R.string.lebox_storage_title_text), new Object[]{Util.getGB_Number(availableSize, 1), Util.getGB_Number(totalSize, 1)}));
                    MyDownloadActivity.this.mCapacityTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    int progress = 0;
                    if (totalSize != 0) {
                        progress = (int) (100.0f - ((((float) availableSize) / ((float) totalSize)) * 100.0f));
                    }
                    Logger.d(MyDownloadActivity.TAG, "--availableSize :" + availableSize + "\n --totalSize：" + totalSize + "\n --progress :" + progress);
                    MyDownloadActivity.this.mCapacityPBar.setProgress(progress);
                }
            }
        }).execute(StorageGetInfoHttpRequest.getStorageGetInfoParameter().combineParams());
    }

    public void onClick(View v) {
        if (R.id.common_nav_left == v.getId()) {
            finish();
        }
    }

    public void onShowEditState() {
        this.mCapacityRoot.setVisibility(8);
        if (getCurrentBatchDelFragment() != null) {
            getCurrentBatchDelFragment().onShowEditState();
        }
    }

    public void onCancelEditState() {
        if (HttpCacheAssistant.getInstanced().isAdmini()) {
            this.mCapacityRoot.setVisibility(0);
        } else {
            this.mCapacityRoot.setVisibility(8);
        }
        if (getCurrentBatchDelFragment() != null) {
            getCurrentBatchDelFragment().onCancelEditState();
        }
    }

    private String getBatchDelDialogTitle() {
        String title = "";
        switch (this.mCurrentPostion) {
            case 0:
                return getResources().getString(R.string.tip_del_download_all_dialog);
            case 1:
                return getResources().getString(R.string.tip_del_downloading_all_dialog);
            default:
                return title;
        }
    }

    public void onDoBatchDelete() {
        if (isSelectAll()) {
            DialogUtil.showDialog(this, getBatchDelDialogTitle(), "", "", null, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (MyDownloadActivity.this.getCurrentBatchDelFragment() != null) {
                        MyDownloadActivity.this.getCurrentBatchDelFragment().onDoBatchDelete();
                    }
                    dialog.dismiss();
                }
            });
        } else if (getCurrentBatchDelFragment() != null) {
            getCurrentBatchDelFragment().onDoBatchDelete();
        }
    }

    private void restartData() {
        if (getCurrentBatchDelFragment() != null) {
            getCurrentBatchDelFragment().onInitData();
        }
    }

    public void onSelectAll() {
        if (getCurrentBatchDelFragment() != null) {
            getCurrentBatchDelFragment().onSelectAll();
        }
    }

    private IBatchDel getCurrentBatchDelFragment() {
        Fragment fragment = (this.mDownloadFragmentPagerAdapter == null || this.mDownloadFragmentPagerAdapter.getCount() <= 0) ? null : this.mDownloadFragmentPagerAdapter.getItem(this.mCurrentPostion);
        Logger.d(TAG, "getCurrentBatchDelFragment currentPostion : " + this.mCurrentPostion + " fragment : " + fragment);
        if (fragment instanceof IBatchDel) {
            return (IBatchDel) fragment;
        }
        return null;
    }

    public boolean onIsAdapterEmpty() {
        if (getCurrentBatchDelFragment() != null) {
            return getCurrentBatchDelFragment().onIsAdapterEmpty();
        }
        return true;
    }

    public void onClearSelectAll() {
        if (getCurrentBatchDelFragment() != null) {
            getCurrentBatchDelFragment().onClearSelectAll();
        }
    }

    public int onSelectNum() {
        if (getCurrentBatchDelFragment() != null) {
            return getCurrentBatchDelFragment().onSelectNum();
        }
        return 0;
    }

    public void onInitData() {
        restartData();
    }
}
