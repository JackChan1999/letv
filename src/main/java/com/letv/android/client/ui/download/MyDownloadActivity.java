package com.letv.android.client.ui.download;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.BaseBatchDelActivity;
import com.letv.android.client.activity.LetvVipActivity;
import com.letv.android.client.commonlib.activity.WrapActivity.IBatchDel;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.android.client.commonlib.view.title.TabPageIndicator;
import com.letv.android.client.utils.UIs;
import com.letv.android.client.view.DownloadCacheDialog;
import com.letv.android.client.view.DownloadCacheDialog.OnDownloadDialogLister;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.DialogUtil;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.download.bean.DownloadAlbum;
import com.letv.download.bean.DownloadVideo;
import com.letv.download.db.DownloadDBDao;
import com.letv.download.manager.DownloadManager;
import com.letv.download.manager.StoreManager;
import com.letv.download.manager.StoreManager.StoreDeviceInfo;
import com.letv.download.util.DownloadUtil;
import com.letv.download.util.L;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import java.util.ArrayList;
import master.flame.danmaku.danmaku.parser.IDataSource;

public class MyDownloadActivity extends BaseBatchDelActivity implements OnClickListener {
    private static final String TAG = MyDownloadActivity.class.getSimpleName();
    private boolean fromNotification;
    private boolean isNetStateReceiver;
    private boolean isWifi;
    ProgressBar mCapacityPBar;
    View mCapacityRoot;
    TextView mCapacityTextView;
    private int mCurrentPostion;
    Button mDeleteBtn;
    DownloadFragmentPagerAdapter mDownloadFragmentPagerAdapter;
    ViewPager mDownloadViewPager;
    TextView mEditView;
    private TabPageIndicator mIndicator;
    private IntentFilter mIntentFilter;
    OnPageChangeListener mOnPageChangeListener;
    private TextView mOpenVipTV;
    private BroadcastReceiver mSdcardMountReceiver;
    Button mSelectBtn;
    private ValueAnimator mTabPIValueAnimator;
    private View mTipLayout;
    private TextView mVipTipTV;
    private int page;
    private int startTabPiHeight;
    private boolean toDownload;

    public MyDownloadActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mCapacityRoot = null;
        this.mCapacityTextView = null;
        this.mCapacityPBar = null;
        this.isNetStateReceiver = false;
        this.isWifi = true;
        this.startTabPiHeight = 0;
        this.mCurrentPostion = 0;
        this.mOnPageChangeListener = new OnPageChangeListener(this) {
            final /* synthetic */ MyDownloadActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onPageScrollStateChanged(int arg0) {
                LogInfo.log("ljnalex", "---onPageScrollStateChanged---" + arg0);
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageSelected(int arg0) {
                LogInfo.log(MyDownloadActivity.TAG, "onPageSelected arg0 : " + arg0);
                if (this.this$0.isEditing()) {
                    this.this$0.showTabPageIndicator();
                }
                this.this$0.mCurrentPostion = arg0;
                this.this$0.initBatchDelState(arg0);
                downloadStatistics(arg0);
            }

            private void downloadStatistics(int arg0) {
                String pageid = null;
                if (arg0 == 0) {
                    pageid = PageIdConstant.downloadFinishPage;
                } else if (arg0 == 1) {
                    pageid = PageIdConstant.downloadingPage;
                } else if (arg0 == 2) {
                    pageid = PageIdConstant.localPage;
                } else if (arg0 == 3) {
                    pageid = PageIdConstant.worldCupPage;
                }
                StatisticsUtils.staticticsInfoPost(this.this$0.mContext, "0", "a42", null, arg0 + 1, null, pageid, null, null, null, null, null);
            }
        };
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_download_main);
        initBatchDelView();
        readIntent();
        initView();
        registerSdCardReceiver();
    }

    private void saveDownloadLog() {
        ArrayList<DownloadAlbum> arrayAlbum = DownloadDBDao.getInstance(this.mContext).getAllDownloadAlbum();
        ArrayList<DownloadVideo> arrayVideo = DownloadDBDao.getInstance(this.mContext).getAllDownloadVideo();
        int albumCount = 0;
        int videoCount = 0;
        if (arrayAlbum != null) {
            albumCount = arrayAlbum.size();
        }
        if (arrayVideo != null) {
            videoCount = arrayVideo.size();
        }
        DownloadUtil.saveException("MyDownloadActivity  albumCount : " + albumCount + " videoCount : " + videoCount);
    }

    protected void onDestroy() {
        super.onDestroy();
        unRegisterSdCardReceiver();
    }

    private void initView() {
        this.mCapacityRoot = findViewById(2131361935);
        this.mCapacityTextView = (TextView) findViewById(2131361937);
        this.mCapacityPBar = (ProgressBar) findViewById(2131361936);
        this.mTipLayout = findViewById(R.id.download_vip_layout);
        this.mVipTipTV = (TextView) findViewById(R.id.download_vip_tip);
        this.mOpenVipTV = (TextView) findViewById(R.id.download_open_vip_btn);
        this.mOpenVipTV.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MyDownloadActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                LetvVipActivity.launch(this.this$0.mContext, this.this$0.mContext.getResources().getString(2131100645));
                StatisticsUtils.statisticsActionInfo(this.this$0.mContext, PageIdConstant.downloadingPage, "0", "vp13", "开通会员", 1, PreferencesManager.getInstance().isVip() ? "vip=1" : "vip=0");
            }
        });
        initNavigationBar();
        initViewPager();
    }

    private void readIntent() {
        this.page = getIntent().getIntExtra(MyDownloadActivityConfig.PAGE, 0);
        this.fromNotification = getIntent().getBooleanExtra("isdownload", false);
        this.toDownload = getIntent().getBooleanExtra("todownload", false);
    }

    private void doTabPageIndicatorAnimator(int start, int end) {
        this.mTabPIValueAnimator = ValueAnimator.ofInt(new int[]{start, end});
        this.mTabPIValueAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ MyDownloadActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                int value = ((Integer) animation.getAnimatedValue()).intValue();
                LayoutParams layoutParams = this.this$0.mIndicator.getLayoutParams();
                layoutParams.height = value;
                this.this$0.mIndicator.setLayoutParams(layoutParams);
            }
        });
        this.mTabPIValueAnimator.start();
    }

    protected void showTabPageIndicator() {
        LogInfo.log(TAG, " showTabPageIndicator startTabPiHeight>>> " + this.startTabPiHeight);
        if (VERSION.SDK_INT >= 14) {
            doTabPageIndicatorAnimator(0, this.startTabPiHeight);
        } else {
            this.mIndicator.setVisibility(0);
        }
    }

    protected void hideTabPageIndicator() {
        if (VERSION.SDK_INT >= 14) {
            if (this.startTabPiHeight == 0) {
                this.startTabPiHeight = this.mIndicator.getHeight();
            }
            doTabPageIndicatorAnimator(this.mIndicator.getHeight(), 0);
            return;
        }
        this.mIndicator.setVisibility(8);
    }

    private void initViewPager() {
        this.mDownloadViewPager = (ViewPager) findViewById(2131363227);
        this.mDownloadFragmentPagerAdapter = new DownloadFragmentPagerAdapter(getSupportFragmentManager());
        this.mDownloadFragmentPagerAdapter.addFragment(new MyDownloadFinishFragment());
        this.mDownloadFragmentPagerAdapter.addFragment(new MyDownloadingFragment());
        this.mDownloadFragmentPagerAdapter.addFragment(new MyLocalFragment());
        this.mDownloadFragmentPagerAdapter.addFragment(new MyRandomSeeFragment());
        this.mDownloadViewPager.setOffscreenPageLimit(this.mDownloadFragmentPagerAdapter.getCount());
        this.mDownloadViewPager.setAdapter(this.mDownloadFragmentPagerAdapter);
        this.mIndicator = (TabPageIndicator) findViewById(2131362128);
        this.mIndicator.setViewPager(this.mDownloadViewPager);
        this.mIndicator.setOnPageChangeListener(this.mOnPageChangeListener);
        if (this.fromNotification) {
            this.mDownloadViewPager.setCurrentItem(this.mDownloadFragmentPagerAdapter.getCount() - 1, false);
        } else {
            this.mDownloadViewPager.setCurrentItem(this.page, false);
        }
        this.mCurrentPostion = this.page;
    }

    public void showDownloadingNum(int num) {
        LogInfo.log("", "showDownloadingNum num : " + num);
        this.mDownloadFragmentPagerAdapter.setDownloadingNum(num);
        this.mIndicator.notifyDataSetChanged();
    }

    private void handleVipTip() {
        if (PreferencesManager.getInstance().isVip()) {
            this.mOpenVipTV.setVisibility(8);
            this.mVipTipTV.setText(getResources().getText(2131101285));
            return;
        }
        this.mOpenVipTV.setVisibility(0);
        this.mVipTipTV.setText(getResources().getText(2131101284));
        StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.downloadingPage, "19", "vp13", "开通会员", 1, PreferencesManager.getInstance().isVip() ? "vip=1" : "vip=0");
    }

    protected void onResume() {
        super.onResume();
        LogInfo.log(TAG, " MyDownloadActivity onResume ");
        DownloadManager.startDownloadService();
        DownloadManager.sendMyDownloadClass(MyDownloadActivity.class);
        updateStoreSpace();
        initBatchDelState(this.mCurrentPostion);
        handleVipTip();
        new Thread(new Runnable(this) {
            final /* synthetic */ MyDownloadActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                this.this$0.saveDownloadLog();
            }
        }).start();
    }

    public int getFrom() {
        return this.page;
    }

    public boolean onHandleEditViewEvent() {
        if (this.mTipLayout != null) {
            if (isEditing()) {
                this.mTipLayout.setVisibility(0);
            } else {
                this.mTipLayout.setVisibility(8);
            }
        }
        if (getEditView().getTag() == null || ((Integer) getEditView().getTag()).intValue() != 2) {
            return false;
        }
        UIs.showDialog(this, this.mContext.getResources().getString(2131100975), null, 0, "", "", null, new DialogInterface.OnClickListener(this) {
            final /* synthetic */ MyDownloadActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(DialogInterface dialog, int which) {
                StatisticsUtils.staticticsInfoPost(this.this$0.mContext, "0", "e34", "全部删除", 1, null, PageIdConstant.localPage, null, null, null, null, null);
                Fragment fragment = this.this$0.mDownloadFragmentPagerAdapter.getItem(this.this$0.mCurrentPostion);
                if (fragment != null && (fragment instanceof MyLocalFragment)) {
                    ((MyLocalFragment) fragment).deleteLocalVideo();
                }
                dialog.dismiss();
            }
        }, 0, 0, 0, 0);
        return true;
    }

    public void initBatchDelState(int position) {
        Fragment fragment = this.mDownloadFragmentPagerAdapter.getItem(position);
        if (fragment != null) {
            updateBatchDelView();
            if (fragment instanceof MyLocalFragment) {
                LogInfo.log(TAG, "initBatchDelState MyLocalFragment ");
                getEditView().setText(2131099786);
            }
            getEditView().setTag(Integer.valueOf(position));
        }
    }

    private void initNavigationBar() {
        ImageView backImageView = (ImageView) findViewById(2131362351);
        ((TextView) findViewById(2131362352)).setText(getResources().getString(2131100924));
        backImageView.setOnClickListener(this);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        readIntent();
        if (this.fromNotification) {
            this.mDownloadViewPager.setCurrentItem(this.mDownloadFragmentPagerAdapter.getCount() - 1, false);
        } else {
            this.mDownloadViewPager.setCurrentItem(this.page, false);
        }
        this.mCurrentPostion = this.page;
    }

    private void unRegisterSdCardReceiver() {
        if (this.mSdcardMountReceiver != null) {
            try {
                unregisterReceiver(this.mSdcardMountReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void registerSdCardReceiver() {
        if (this.mSdcardMountReceiver == null) {
            this.mSdcardMountReceiver = new BroadcastReceiver(this) {
                final /* synthetic */ MyDownloadActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onReceive(Context context, Intent intent) {
                    L.v(MyDownloadActivity.TAG, "BroadcastReceiver onReceive >>");
                    this.this$0.updateStoreSpace();
                }
            };
        }
        if (this.mIntentFilter == null) {
            this.mIntentFilter = new IntentFilter("android.intent.action.MEDIA_MOUNTED");
            this.mIntentFilter.addAction("android.intent.action.MEDIA_REMOVED");
            this.mIntentFilter.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
            this.mIntentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
            this.mIntentFilter.addAction("android.intent.action.MEDIA_EJECT");
            this.mIntentFilter.addAction("android.intent.action.MEDIA_SHARED");
            this.mIntentFilter.addDataScheme(IDataSource.SCHEME_FILE_TAG);
            this.mIntentFilter.setPriority(1000);
        }
        try {
            registerReceiver(this.mSdcardMountReceiver, this.mIntentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isCurrentSdCardPull() {
        LogInfo.log(TAG, "isCurrentPhoneStore : " + StoreManager.isCurrentPhoneStore() + " isSdCardPull : " + StoreManager.isSdCardPull());
        if (StoreManager.isCurrentPhoneStore() || !StoreManager.isSdCardPull()) {
            return false;
        }
        return true;
    }

    public void updateStoreSpace() {
        LogInfo.log(TAG, "updateSdcardSpace isStoreMounted : " + StoreManager.isStoreMounted());
        String downloadPath = StoreManager.getDownloadPath();
        StoreDeviceInfo storeDeviceInfo = StoreManager.getDefaultDownloadDeviceInfo();
        if (isCurrentSdCardPull() || !StoreManager.isStoreMounted() || TextUtils.isEmpty(downloadPath) || storeDeviceInfo == null) {
            this.mCapacityTextView.setText(getString(2131100057));
            this.mCapacityRoot.setOnClickListener(null);
            return;
        }
        UIsUtils.zoomViewHeight(36, this.mCapacityRoot);
        long availableSize = storeDeviceInfo.mAvailable;
        long totalSize = storeDeviceInfo.mTotalSpace;
        if (!StoreManager.isCurrentPhoneStore()) {
            String sdcardText;
            boolean hasPhoneStore = StoreManager.isHasPhoneStore();
            if (!StoreManager.isSdCardPull()) {
                sdcardText = getString(2131100062) + NetworkUtils.DELIMITER_COLON + getString(2131100073, new Object[]{LetvUtils.getGBNumber(availableSize, 1), LetvUtils.getGBNumber(totalSize, 1)});
            } else if (hasPhoneStore) {
                sdcardText = getString(2131100059);
            } else {
                sdcardText = getString(2131100060);
            }
            if (hasPhoneStore) {
                this.mCapacityTextView.setText(sdcardText + ", " + getString(2131099879) + " >");
                this.mCapacityRoot.setOnClickListener(this);
            } else {
                this.mCapacityTextView.setText(sdcardText);
                this.mCapacityTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                this.mCapacityRoot.setOnClickListener(null);
            }
        } else if (StoreManager.isHasSdCardStore()) {
            this.mCapacityTextView.setText(getString(2131100055) + NetworkUtils.DELIMITER_COLON + getString(2131100073, new Object[]{LetvUtils.getGBNumber(availableSize, 1), LetvUtils.getGBNumber(totalSize, 1)}) + ", " + getString(2131099879) + " >");
            this.mCapacityRoot.setOnClickListener(this);
        } else {
            this.mCapacityTextView.setText(getString(2131100055) + NetworkUtils.DELIMITER_COLON + getString(2131100073, new Object[]{LetvUtils.getGBNumber(availableSize, 1), LetvUtils.getGBNumber(totalSize, 1)}));
            this.mCapacityTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            this.mCapacityRoot.setOnClickListener(null);
        }
        int progress = 0;
        if (totalSize != 0) {
            progress = (int) (100.0f - ((((float) availableSize) / ((float) totalSize)) * 100.0f));
        }
        LogInfo.log("ljn", "--availableSize :" + availableSize + "\n --totalSize：" + totalSize + "\n --progress :" + progress);
        this.mCapacityPBar.setProgress(progress);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case 2131361935:
                L.v(TAG, " show select dialog ");
                new DownloadCacheDialog((Context) this, new OnDownloadDialogLister(this) {
                    final /* synthetic */ MyDownloadActivity this$0;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$0 = this$0;
                    }

                    public void changeView() {
                        this.this$0.updateStoreSpace();
                    }
                }).showDialog();
                return;
            case 2131362351:
                if (LetvUtils.checkClickEvent(700)) {
                    finish();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public Activity getActivity() {
        return this;
    }

    public void onShowEditState() {
        this.mCapacityRoot.setVisibility(8);
        if (getCurrentBatchDelFragment() != null) {
            getCurrentBatchDelFragment().onShowEditState();
        }
    }

    public void onCancelEditState() {
        this.mCapacityRoot.setVisibility(0);
        if (this.mTipLayout != null) {
            this.mTipLayout.setVisibility(0);
        }
        if (getCurrentBatchDelFragment() != null) {
            getCurrentBatchDelFragment().onCancelEditState();
        }
    }

    private String getBatchDelDialogTitle() {
        String title = "";
        switch (this.mCurrentPostion) {
            case 0:
                return TipUtils.getTipMessage(DialogMsgConstantId.TWO_ZERO_SIX_CONSTANT, 2131100973);
            case 1:
                return TipUtils.getTipMessage(DialogMsgConstantId.TWO_ZERO_SEVEN_CONSTANT, 2131100974);
            case 3:
                return this.mContext.getResources().getString(2131100976);
            default:
                return title;
        }
    }

    private void reportDelAll() {
        switch (this.mCurrentPostion) {
            case 0:
                StatisticsUtils.staticticsInfoPost(this.mContext, "0", "e32", "全部删除", 1, null, PageIdConstant.downloadFinishPage, null, null, null, null, null);
                return;
            case 1:
                StatisticsUtils.staticticsInfoPost(this.mContext, "0", "e31", "全部删除", 1, null, PageIdConstant.downloadingPage, null, null, null, null, null);
                return;
            case 3:
                StatisticsUtils.staticticsInfoPost(this.mContext, "0", "e35", "全部删除", 1, null, PageIdConstant.worldCupPage, null, null, null, null, null);
                return;
            default:
                return;
        }
    }

    public void onDoBatchDelete() {
        if (isSelectAll()) {
            DialogUtil.showDialog(this, getBatchDelDialogTitle(), "", "", null, new DialogInterface.OnClickListener(this) {
                final /* synthetic */ MyDownloadActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialog, int which) {
                    if (this.this$0.getCurrentBatchDelFragment() != null) {
                        this.this$0.getCurrentBatchDelFragment().onDoBatchDelete();
                    }
                    this.this$0.reportDelAll();
                    dialog.dismiss();
                }
            });
        } else if (getCurrentBatchDelFragment() != null) {
            getCurrentBatchDelFragment().onDoBatchDelete();
        }
    }

    public void onSelectAll() {
        if (getCurrentBatchDelFragment() != null) {
            getCurrentBatchDelFragment().onSelectAll();
        }
    }

    private IBatchDel getCurrentBatchDelFragment() {
        Fragment fragment = (this.mDownloadFragmentPagerAdapter == null || this.mDownloadFragmentPagerAdapter.getCount() <= 0) ? null : this.mDownloadFragmentPagerAdapter.getItem(this.mCurrentPostion);
        LogInfo.log(TAG, "getCurrentBatchDelFragment currentPostion : " + this.mCurrentPostion + " fragment : " + fragment);
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
        if (isEditing()) {
            hideTabPageIndicator();
        } else {
            showTabPageIndicator();
        }
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
}
