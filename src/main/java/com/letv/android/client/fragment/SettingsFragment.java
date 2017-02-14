package com.letv.android.client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.activity.ImageCacheClearActivity;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.activity.MoreAboutUsActivity;
import com.letv.android.client.activity.PlayerAndDownloadStreamSelectActivity;
import com.letv.android.client.activity.ShareActivity;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.push.LetvPushService;
import com.letv.android.client.task.RequestPraiseTask;
import com.letv.android.client.view.DownloadCacheDialog;
import com.letv.android.client.view.DownloadCacheDialog.OnDownloadDialogLister;
import com.letv.android.client.view.LetvSlipSwitch;
import com.letv.android.client.view.LetvSlipSwitch.OnSlipSwitchListener;
import com.letv.cache.LetvCacheMannager;
import com.letv.component.upgrade.bean.UpgradeInfo;
import com.letv.component.upgrade.core.upgrade.CheckUpgradeController;
import com.letv.component.upgrade.core.upgrade.UpgradeCallBack;
import com.letv.component.upgrade.core.upgrade.UpgradeManager;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.DialogUtil;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant;
import com.letv.download.manager.DownloadManager;
import com.letv.download.manager.StoreManager;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class SettingsFragment extends LetvBaseFragment implements OnClickListener {
    private TextView checkNewVersionView;
    private View mAboutUsLayout;
    private TextView mClearImageCacheTextView;
    private RelativeLayout mClearRelativeLayout;
    private RelativeLayout mDownloadPathLayout;
    private TextView mDownloadPathTv;
    private RelativeLayout mDownloadPrioritySelectLayout;
    private TextView mDownloadPriorityTxt;
    private Handler mHandler;
    private LetvSlipSwitch mPlayDownload3g2gBtn;
    private RelativeLayout mPlayPrioritySelectLayout;
    private TextView mPlayPriorityTxt;
    private LetvSlipSwitch mPlaySkipHeadTailBtn;
    private LetvSlipSwitch mRemindSetBtn;
    private PublicLoadLayout mRootViewLayout;
    private LinearLayout mSettingCenterClearCache;
    private LinearLayout mSettingCenterPlayDown;
    private LinearLayout mSettingCenterRemindSet;
    private LinearLayout mSettingCenterSoftwareInfo;
    private LetvSlipSwitch mShakeToPlayBtn;
    private TextView mShareTextView;
    private boolean mStreamLevelFlag;
    private RelativeLayout softwareInfoCheckVersionLayout;
    private UpgradeManager upgradeManager;

    public SettingsFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mStreamLevelFlag = false;
        this.mHandler = new Handler(this) {
            final /* synthetic */ SettingsFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        this.this$0.initData();
                        return;
                    default:
                        return;
                }
            }
        };
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootViewLayout = PublicLoadLayout.createPage(getActivity(), (int) R.layout.setting_center_fragment_layout);
        return this.mRootViewLayout;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        this.mSettingCenterPlayDown = (LinearLayout) this.mRootViewLayout.findViewById(R.id.setting_center_play_down);
        this.mPlayPrioritySelectLayout = (RelativeLayout) this.mSettingCenterPlayDown.findViewById(R.id.play_priority_select_layout);
        this.mPlayPriorityTxt = (TextView) this.mPlayPrioritySelectLayout.findViewById(R.id.play_priority_txt);
        this.mPlayPrioritySelectLayout.setOnClickListener(this);
        this.mDownloadPrioritySelectLayout = (RelativeLayout) this.mSettingCenterPlayDown.findViewById(R.id.download_priority_select_layout);
        this.mDownloadPrioritySelectLayout.setOnClickListener(this);
        this.mDownloadPriorityTxt = (TextView) this.mDownloadPrioritySelectLayout.findViewById(R.id.download_priority_txt);
        this.mPlaySkipHeadTailBtn = (LetvSlipSwitch) this.mSettingCenterPlayDown.findViewById(R.id.play_skip_head_tail_btn);
        this.mPlayDownload3g2gBtn = (LetvSlipSwitch) this.mSettingCenterPlayDown.findViewById(R.id.play_download_3g_2g_btn);
        this.mDownloadPathLayout = (RelativeLayout) this.mSettingCenterPlayDown.findViewById(R.id.download_path_layout);
        this.mDownloadPathLayout.setOnClickListener(this);
        this.mDownloadPathTv = (TextView) this.mDownloadPathLayout.findViewById(R.id.download_path_txt);
        this.mSettingCenterRemindSet = (LinearLayout) this.mRootViewLayout.findViewById(R.id.setting_center_remind_set);
        this.mRemindSetBtn = (LetvSlipSwitch) this.mSettingCenterRemindSet.findViewById(R.id.remind_set_btn);
        this.mSettingCenterClearCache = (LinearLayout) this.mRootViewLayout.findViewById(R.id.setting_center_clear_cache);
        this.mClearImageCacheTextView = (TextView) this.mSettingCenterClearCache.findViewById(R.id.settings_image_cache_clear);
        this.mClearImageCacheTextView.setOnClickListener(this);
        this.mShakeToPlayBtn = (LetvSlipSwitch) this.mSettingCenterClearCache.findViewById(R.id.shake_to_play_btn);
        this.mShareTextView = (TextView) this.mSettingCenterClearCache.findViewById(R.id.settings_shareset);
        this.mShareTextView.setOnClickListener(this);
        this.mSettingCenterSoftwareInfo = (LinearLayout) this.mRootViewLayout.findViewById(R.id.setting_center_software_info);
        this.softwareInfoCheckVersionLayout = (RelativeLayout) this.mSettingCenterSoftwareInfo.findViewById(R.id.software_info_check_version_layout);
        this.softwareInfoCheckVersionLayout.setOnClickListener(this);
        this.checkNewVersionView = (TextView) this.softwareInfoCheckVersionLayout.findViewById(R.id.check_new_version_view);
        this.mAboutUsLayout = this.mSettingCenterSoftwareInfo.findViewById(R.id.about_us);
        this.mAboutUsLayout.setOnClickListener(this);
        isSoftwareInfoCheckVersionLayoutShow();
    }

    private boolean isSoftwareInfoCheckVersionLayoutShow() {
        if (!LetvUtils.isGooglePlay()) {
            return true;
        }
        this.softwareInfoCheckVersionLayout.setVisibility(8);
        return false;
    }

    public void onResume() {
        super.onResume();
        initData();
        StatisticsUtils.statisticsSettings(this.mContext);
    }

    private void initData() {
        this.mStreamLevelFlag = LetvApplication.getInstance().getSuppportTssLevel() == 0;
        setPlayOrDownText();
        this.mPlaySkipHeadTailBtn.setSwitchState(PreferencesManager.getInstance().isSkip());
        this.mPlaySkipHeadTailBtn.setSlipSwitchListener(new OnSlipSwitchListener(this) {
            final /* synthetic */ SettingsFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onSwitched(boolean state) {
                this.this$0.setSkip(state);
            }
        });
        this.mPlayDownload3g2gBtn.setSwitchState(PreferencesManager.getInstance().isAllowMobileNetwork());
        this.mPlayDownload3g2gBtn.setSlipSwitchListener(new OnSlipSwitchListener(this) {
            final /* synthetic */ SettingsFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onSwitched(boolean state) {
                this.this$0.setAllowMobileNetwork(state);
                StatisticsUtils.staticticsInfoPost(this.this$0.getActivity(), "0", "e51", this.this$0.getString(2131100045), 1, null, PageIdConstant.settingPage, null, null, null, null, null);
            }
        });
        initDownloadPath();
        this.mRemindSetBtn.setSwitchState(PreferencesManager.getInstance().isPush());
        this.mRemindSetBtn.setSlipSwitchListener(new OnSlipSwitchListener(this) {
            final /* synthetic */ SettingsFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onSwitched(boolean state) {
                this.this$0.setReceivePushMsg(state);
                if (state) {
                    LetvPushService.schedule(this.this$0.getActivity());
                    LogInfo.LogStatistics(this.this$0.getString(2131100807));
                    StatisticsUtils.staticticsInfoPost(this.this$0.getActivity(), "0", "e51", this.this$0.getString(2131100809), 6, null, "043", null, null, null, null, null);
                    return;
                }
                LogInfo.LogStatistics(this.this$0.getString(2131100806));
                StatisticsUtils.staticticsInfoPost(this.this$0.getActivity(), "0", "e51", this.this$0.getString(2131100808), 6, null, "043", null, null, null, null, null);
            }
        });
        this.mShakeToPlayBtn.setSwitchState(PreferencesManager.getInstance().isShack());
        this.mShakeToPlayBtn.setSlipSwitchListener(new OnSlipSwitchListener(this) {
            final /* synthetic */ SettingsFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onSwitched(boolean state) {
                this.this$0.setShackToPlay(state);
            }
        });
        if (PreferencesManager.getInstance().isNeedUpdate()) {
            this.checkNewVersionView.setVisibility(0);
        } else {
            this.checkNewVersionView.setVisibility(8);
        }
        new Thread(new Runnable(this) {
            final /* synthetic */ SettingsFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                this.this$0.mHandler.sendMessage(Message.obtain(this.this$0.mHandler, 1, LetvCacheMannager.getCacheSize()));
            }
        }).start();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_image_cache_clear /*2131364212*/:
                ImageCacheClearActivity.launch(getActivity());
                return;
            case R.id.settings_shareset /*2131364213*/:
                ShareActivity.launch(getActivity());
                return;
            case R.id.play_priority_select_layout /*2131364264*/:
                PlayerAndDownloadStreamSelectActivity.launch(getActivity(), true);
                return;
            case R.id.download_priority_select_layout /*2131364267*/:
                PlayerAndDownloadStreamSelectActivity.launch(getActivity(), false);
                return;
            case R.id.download_path_layout /*2131364273*/:
                new DownloadCacheDialog(getActivity(), new OnDownloadDialogLister(this) {
                    final /* synthetic */ SettingsFragment this$0;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$0 = this$0;
                    }

                    public void changeView() {
                        this.this$0.initDownloadPath();
                    }
                }).showDialog();
                return;
            case R.id.software_info_check_version_layout /*2131364278*/:
                if (LetvUtils.checkClickEvent()) {
                    checkUpdateVersionInfo();
                }
                LogInfo.LogStatistics(getString(2131100417));
                StatisticsUtils.staticticsInfoPost(getActivity(), "0", "e51", getString(2131100417), 2, null, PageIdConstant.settingPage, null, null, null, null, null);
                return;
            case R.id.about_us /*2131364280*/:
                MoreAboutUsActivity.launch(getActivity());
                return;
            default:
                return;
        }
    }

    public String getTagName() {
        return FragmentConstant.TAG_SETTINGS_SETTINGS_MAIN;
    }

    public int getContainerId() {
        return R.id.setting_center_viewpage;
    }

    private void setPlayOrDownText() {
        switch (PreferencesManager.getInstance().getPlayLevel()) {
            case 0:
                if (!this.mStreamLevelFlag) {
                    this.mPlayPriorityTxt.setText(LetvUtils.getSpeedStreamText());
                    break;
                } else {
                    this.mPlayPriorityTxt.setText(LetvUtils.getSmoothStreamText());
                    break;
                }
            case 1:
                this.mPlayPriorityTxt.setText(LetvUtils.getSmoothStreamText());
                break;
            case 2:
                this.mPlayPriorityTxt.setText(LetvUtils.getStartStreamText());
                break;
            case 3:
                this.mPlayPriorityTxt.setText(LetvUtils.getHDstreamText());
                break;
        }
        if (PreferencesManager.getInstance().getCurrentDownloadStream() == 1) {
            this.mDownloadPriorityTxt.setText(LetvUtils.getHDstreamText());
        } else if (PreferencesManager.getInstance().getCurrentDownloadStream() == 0) {
            this.mDownloadPriorityTxt.setText(LetvUtils.getSmoothStreamText());
        } else {
            this.mDownloadPriorityTxt.setText(LetvUtils.getStartStreamText());
        }
    }

    private void setSkip(boolean isSkip) {
        PreferencesManager.getInstance().setSkip(isSkip);
    }

    private void setAllowMobileNetwork(boolean isAllow) {
        PreferencesManager.getInstance().setAllowMobileNetwork(isAllow);
    }

    private void setReceivePushMsg(boolean isAllow) {
        PreferencesManager.getInstance().setIsPush(isAllow);
    }

    private void setShackToPlay(boolean isAllow) {
        PreferencesManager.getInstance().setIsShack(isAllow);
    }

    private void checkUpdateVersionInfo() {
        if (PreferencesManager.getInstance().isNeedUpdate()) {
            this.upgradeManager = UpgradeManager.getInstance();
            this.upgradeManager.init(getActivity(), LetvConfig.getPcode(), false, LetvConfig.getAppKey(), R.layout.upgrade_dialog_view, 2131230964, "0", "00", DataConstant.P3);
            this.upgradeManager.upgrade(new UpgradeCallBack(this) {
                final /* synthetic */ SettingsFragment this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void exitApp() {
                    this.this$0.upgradeManager.exitApp();
                    Intent intent = new Intent(this.this$0.getActivity(), MainActivity.class);
                    intent.setFlags(67108864);
                    intent.putExtra("exitFlag", 1.0f);
                    if (this.this$0.getActivity() != null) {
                        this.this$0.getActivity().startActivity(intent);
                        this.this$0.getActivity().finish();
                    }
                    DownloadManager.pauseAllDownload();
                    LetvCacheMannager.getInstance().destroy();
                }

                public void setUpgradeData(UpgradeInfo upgradeInfo) {
                    if (upgradeInfo != null && 1 != upgradeInfo.getUptype() && 2 == upgradeInfo.getUptype()) {
                    }
                }

                public void setUpgradeDialog(int arg0, UpgradeInfo arg1) {
                }

                public void setUpgradeState(int arg0) {
                    if (200 == arg0) {
                        PreferencesManager.getInstance().setIsNeedUpdate(true);
                    } else {
                        PreferencesManager.getInstance().setIsNeedUpdate(false);
                    }
                }

                public void setUpgradeType(int upgradeType, int downloadState) {
                    if (1 == upgradeType && 200 == downloadState) {
                        this.this$0.upgradeManager.exitApp();
                    }
                    if (1 == upgradeType && 400 == downloadState) {
                        this.this$0.upgradeManager.exitApp();
                        Intent intent = new Intent(this.this$0.getActivity(), MainActivity.class);
                        intent.setFlags(67108864);
                        intent.putExtra("exitFlag", 1.0f);
                        this.this$0.getActivity().startActivity(intent);
                        this.this$0.getActivity().finish();
                        DownloadManager.pauseAllDownload();
                        LetvCacheMannager.getInstance().destroy();
                    }
                }

                public void upgradeData(UpgradeInfo arg0, int selectRelatedAppCount, int upgradeState) {
                    if (selectRelatedAppCount < 1 || upgradeState != 200) {
                        PreferencesManager.getInstance().savePraise(false);
                    } else {
                        PreferencesManager.getInstance().savePraise(true);
                        if (PreferencesManager.getInstance().isLogin()) {
                            RequestPraiseTask requestPraiseTask = new RequestPraiseTask(this.this$0.getActivity());
                        }
                    }
                    PreferencesManager.getInstance().setIsNeedUpdate(true);
                    if (upgradeState != 200 && upgradeState == 201) {
                    }
                    if (this.this$0.getActivity() == null) {
                    }
                }
            }, CheckUpgradeController.CHECK_BY_SELF, 2);
            return;
        }
        DialogUtil.showDialog(getActivity(), getResources().getString(2131101053, new Object[]{Global.VERSION}), getResources().getString(2131101054), null);
    }

    private void initDownloadPath() {
        switch (StoreManager.getCurrentStoreLocation()) {
            case 0:
                this.mDownloadPathTv.setText(getResources().getString(2131100057));
                return;
            case 1:
                this.mDownloadPathTv.setText(getResources().getString(2131100055));
                return;
            case 2:
                this.mDownloadPathTv.setText(getResources().getString(2131100062));
                return;
            default:
                return;
        }
    }
}
