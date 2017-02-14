package com.letv.android.client.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.FeedBackActivity;
import com.letv.android.client.activity.LetvLoginActivity;
import com.letv.android.client.activity.LetvWoFlowActivity;
import com.letv.android.client.activity.MineCustomActivity;
import com.letv.android.client.activity.MyElectronicTicketActivity;
import com.letv.android.client.activity.MyFollowActivity;
import com.letv.android.client.activity.SettingsMainActivity;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.config.MyCollectActivityConfig;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.android.client.commonlib.config.MyPlayRecordActivityConfig;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.controller.RedPacketSdkController;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.android.wo.ex.IWoFlowManager.ORDER_STATE;
import com.letv.core.bean.MyProfileListBean;
import com.letv.core.bean.MyProfileListBean.MyProfileBean;
import com.letv.core.bean.UserBean;
import com.letv.core.constant.LoginConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.download.manager.DownloadManager;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.lemallsdk.LemallPlatform;
import com.letv.lemallsdk.util.Constants;
import com.letv.mobile.lebox.LeboxApiManager;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import java.util.List;

public class MineListViewAdapter extends LetvBaseAdapter<MyProfileBean> {
    private static final int TYPE_CARD_CUSTOM = 18;
    private static final int TYPE_LEBOX = 17;
    public static final String USER_CENTER_URL = "http://minisite.letv.com/zt2015/membercenter/index.shtml?ref=androidhyzx";
    private final int TYPE_BRIBERY_MONEY;
    private final int TYPE_COLLECTIONS;
    private final int TYPE_DOWNLOADS;
    private final int TYPE_FEEDBACK;
    private final int TYPE_MALL_ORDER_CENTER;
    private final int TYPE_MY_FOLLOW;
    private final int TYPE_PLAY_RECORDS;
    private final int TYPE_POINTS;
    private final int TYPE_REGISTRATIONS;
    private final int TYPE_SETTINGS;
    private final int TYPE_SHOPPING;
    private final int TYPE_TICKET;
    private final int TYPE_UNICOM;
    private int[] groupStartIndex;
    private boolean isOrder;
    private Activity mActivity;
    private Context mContext;
    private String mDefaultLetvMallWebsite;
    private String mDefaultTextMemberArea;
    private String mDefaultTextOrderUnicom;
    private String mDefaultTextReverseOrderUnicom;
    private boolean mDownloadingFlag;
    private boolean mIsSupportProvince;
    private MyProfileListBean mMyFullProfileList;
    private MyProfileListBean mMyProfileList;
    private UserBean user;

    public MineListViewAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.TYPE_PLAY_RECORDS = 1;
        this.TYPE_COLLECTIONS = 2;
        this.TYPE_DOWNLOADS = 3;
        this.TYPE_BRIBERY_MONEY = 15;
        this.TYPE_REGISTRATIONS = 4;
        this.TYPE_POINTS = 5;
        this.TYPE_UNICOM = 8;
        this.TYPE_TICKET = 14;
        this.TYPE_SHOPPING = 16;
        this.TYPE_SETTINGS = 10;
        this.TYPE_FEEDBACK = 11;
        this.TYPE_MALL_ORDER_CENTER = 12;
        this.TYPE_MY_FOLLOW = 13;
        this.mIsSupportProvince = false;
        this.groupStartIndex = new int[]{0, 3, 6};
        this.isOrder = false;
        this.mContext = context;
        this.mActivity = (Activity) context;
        this.mMyFullProfileList = new MyProfileListBean();
        this.mMyProfileList = new MyProfileListBean();
        initDefaultTextData();
    }

    private void initDefaultTextData() {
        this.mDefaultTextMemberArea = LetvTools.getTextFromServer("90009", this.mContext.getString(2131100481));
        this.mDefaultTextOrderUnicom = this.mContext.getString(2131100486);
        this.mDefaultTextReverseOrderUnicom = this.mContext.getString(2131100487);
        this.mDefaultLetvMallWebsite = LetvTools.getTextFromServer("90043", LoginConstant.LETV_MALL_JIFEN_WEBSITE);
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public MyProfileListBean getFullList() {
        return this.mMyFullProfileList;
    }

    public MyProfileListBean getList() {
        return this.mMyProfileList;
    }

    public void setList(List<MyProfileBean> list) {
        if (list != null && list.size() != 0) {
            MyProfileBean profile;
            this.mMyFullProfileList.list = list;
            MyProfileListBean tempMyProfileList = new MyProfileListBean();
            for (MyProfileBean myProfile : list) {
                int typeValue = Integer.valueOf(myProfile.type).intValue();
                if (typeValue != 17 || PreferencesManager.getInstance().isLeboxEnable()) {
                    int displayValue = Integer.valueOf(myProfile.display).intValue();
                    if (typeValue != 8 && displayValue == 1) {
                        boolean needShow = LetvUtils.isInHongKong() || !LetvUtils.isGooglePlay();
                        if (needShow) {
                            tempMyProfileList.addMyProfileBean(myProfile);
                        } else if (!(typeValue == 5 || typeValue == 15 || typeValue == 17 || typeValue == 12 || typeValue == 4)) {
                            tempMyProfileList.addMyProfileBean(myProfile);
                        }
                    } else if (typeValue == 8 && PreferencesManager.getInstance().isChinaUnicomSwitch()) {
                        IWoFlowManager woFlowManager = (IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null);
                        ORDER_STATE state = woFlowManager.getUserOrderInfo(this.mActivity);
                        LogInfo.log("ZSM", "setList...isOrder=" + state + ",woFlowManager.getPhoneNum(mActivity)=" + woFlowManager.getPhoneNum(this.mActivity) + "   displayValue=" + displayValue + "  isSupportProvince=" + this.mIsSupportProvince);
                        if (!TextUtils.isEmpty(woFlowManager.getPhoneNum(this.mActivity)) && displayValue == 1) {
                            if (this.mIsSupportProvince || state == ORDER_STATE.ORDER) {
                                LogInfo.log("ZSM", "setList....tempMyProfileList.add");
                                tempMyProfileList.addMyProfileBean(myProfile);
                                this.groupStartIndex[2] = 6;
                            }
                        }
                    }
                }
            }
            if (!(LetvUtils.isInHongKong() || LetvUtils.isGooglePlay())) {
                profile = new MyProfileBean();
                profile.name = this.mContext.getString(2131100427);
                profile.type = "12";
                tempMyProfileList.addMyProfileBean(profile);
            }
            profile = new MyProfileBean();
            profile.name = this.mContext.getString(2131100422);
            profile.type = "11";
            tempMyProfileList.addMyProfileBean(profile);
            profile = new MyProfileBean();
            profile.name = this.mContext.getString(2131100416);
            profile.type = "10";
            tempMyProfileList.addMyProfileBean(profile);
            this.mMyProfileList = tempMyProfileList;
            LogInfo.log("ZSM", "mMyProfileList size=" + this.mMyProfileList.list.size());
            notifyDataSetChanged();
        }
    }

    public void setWoEntity(boolean isSupportProvince, boolean isOrder) {
        this.mIsSupportProvince = isSupportProvince;
        this.isOrder = isOrder;
    }

    private void clearViewData(ViewHolder handler) {
        if (handler != null) {
            if (handler.image != null) {
                handler.image.setImageDrawable(null);
            }
            if (handler.title != null) {
                handler.title.setText("");
            }
            if (handler.subTitle != null) {
                handler.subTitle.setText("");
            }
        }
    }

    private boolean isGroupStart(int position) {
        for (int i : this.groupStartIndex) {
            if (position == i) {
                return true;
            }
        }
        return false;
    }

    public int getCount() {
        return this.mMyProfileList.list.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (position < 0 || position >= getCount()) {
            return convertView;
        }
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = PublicLoadLayout.inflate(this.mContext, R.layout.fragment_top_my_new_item, null);
            viewHolder = new ViewHolder(this);
            viewHolder.layout = convertView.findViewById(R.id.fragment_top_my_item_layout);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.fragment_top_my_item_image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.fragment_top_my_item_title);
            viewHolder.subTitle = (TextView) convertView.findViewById(R.id.fragment_top_my_item_sub_title);
            viewHolder.lineTop = convertView.findViewById(R.id.fragment_top_my_line_top);
            viewHolder.lineBottom = convertView.findViewById(R.id.fragment_top_my_line_bottom);
            viewHolder.groupDivider = convertView.findViewById(R.id.fragment_top_my_item_group_divider);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            clearViewData(viewHolder);
        }
        setViewData(position, viewHolder);
        return convertView;
    }

    private void setViewData(int position, ViewHolder viewHolder) {
        MyProfileBean myProfile = (MyProfileBean) this.mMyProfileList.list.get(position);
        if (myProfile != null) {
            if (position != getCount() - 1) {
                viewHolder.lineBottom.setVisibility(8);
            }
            if (isGroupStart(position)) {
                viewHolder.lineTop.setVisibility(8);
            } else {
                viewHolder.lineTop.setVisibility(0);
                viewHolder.lineTop.setPadding(UIsUtils.dipToPx(15.0f), 0, 0, 0);
            }
            viewHolder.groupDivider.setVisibility(isGroupStart(position) ? 0 : 8);
            int type = Integer.valueOf(myProfile.type).intValue();
            int imageResId = 0;
            switch (type) {
                case 1:
                    imageResId = 2130838254;
                    break;
                case 2:
                    imageResId = 2130838251;
                    break;
                case 3:
                    imageResId = 2130838219;
                    this.mDownloadingFlag = DownloadManager.getDownloadingVideoNum() > 0;
                    break;
                case 4:
                    imageResId = 2130838267;
                    viewHolder.title.setText(2131100489);
                    if (!PreferencesManager.getInstance().isLogin()) {
                        if (!TextUtils.isEmpty(this.mDefaultTextMemberArea)) {
                            viewHolder.subTitle.setText(this.mDefaultTextMemberArea);
                            break;
                        } else {
                            viewHolder.subTitle.setText(2131100481);
                            break;
                        }
                    } else if (this.user != null && this.user.isvip != null) {
                        String accountPackageText = "";
                        if (!"1".equals(this.user.isvip)) {
                            if (!TextUtils.isEmpty(this.mDefaultTextMemberArea)) {
                                viewHolder.subTitle.setText(this.mDefaultTextMemberArea);
                                break;
                            } else {
                                viewHolder.subTitle.setText(2131100481);
                                break;
                            }
                        }
                        long lateEndTime;
                        if (this.user.vipInfo.vipType == 2) {
                            lateEndTime = this.user.vipInfo.seniorcanceltime;
                            accountPackageText = this.mContext.getString(2131101134);
                        } else {
                            lateEndTime = this.user.vipInfo.canceltime;
                            accountPackageText = this.mContext.getString(2131101101);
                        }
                        viewHolder.subTitle.setText(this.mContext.getResources().getString(2131100460, new Object[]{StringUtils.timeString(lateEndTime)}));
                        break;
                    } else {
                        viewHolder.subTitle.setText(2131100481);
                        break;
                    }
                    break;
                case 5:
                    imageResId = 2130838252;
                    break;
                case 8:
                    imageResId = 2130838264;
                    ORDER_STATE state = ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).getUserOrderInfo(this.mActivity);
                    if (state != ORDER_STATE.NOT_ORDER) {
                        if (state != ORDER_STATE.ORDER) {
                            if (state == ORDER_STATE.UNORDER) {
                                viewHolder.subTitle.setText(this.mContext.getResources().getString(2131100488));
                                break;
                            }
                        } else if (!TextUtils.isEmpty(this.mDefaultTextReverseOrderUnicom)) {
                            viewHolder.subTitle.setText(this.mDefaultTextReverseOrderUnicom);
                            break;
                        } else {
                            viewHolder.subTitle.setText(this.mContext.getResources().getString(2131100487));
                            break;
                        }
                    } else if (!TextUtils.isEmpty(this.mDefaultTextOrderUnicom)) {
                        viewHolder.subTitle.setText(this.mDefaultTextOrderUnicom);
                        break;
                    } else {
                        viewHolder.subTitle.setText(this.mContext.getResources().getString(2131100486));
                        break;
                    }
                    break;
                case 10:
                    imageResId = 2130838256;
                    break;
                case 11:
                    imageResId = 2130838220;
                    break;
                case 12:
                    imageResId = 2130838904;
                    break;
                case 13:
                    imageResId = 2130838253;
                    break;
                case 14:
                    imageResId = 2130838261;
                    break;
                case 15:
                    imageResId = 2130838610;
                    break;
                case 16:
                    imageResId = 2130838624;
                    break;
                case 17:
                    imageResId = 2130838298;
                    break;
                case 18:
                    imageResId = 2130838609;
                    break;
            }
            viewHolder.image.setImageResource(imageResId);
            viewHolder.title.setText(myProfile.name);
            viewHolder.layout.setOnClickListener(new 1(this, type, position));
        }
    }

    private void doItemClick(int type, View v, boolean isLogin, int position) {
        if (!LetvUtils.isInHongKong() || (type != 3 && type != 5 && type != 12 && type != 15 && type != 14)) {
            switch (type) {
                case 1:
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MyPlayRecordActivityConfig(this.mContext)));
                    LogInfo.LogStatistics(this.mActivity.getString(2131100479));
                    StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "d33", this.mActivity.getString(2131100479), 1, null, "033", null, null, null, null, null);
                    return;
                case 2:
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MyCollectActivityConfig(this.mActivity)));
                    StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "d33", this.mActivity.getString(2131100471), 2, null, "033", null, null, null, null, null);
                    LogInfo.LogStatistics(this.mActivity.getString(2131100471));
                    return;
                case 3:
                    if (this.mDownloadingFlag) {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MyDownloadActivityConfig(this.mContext).create(1)));
                    } else {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MyDownloadActivityConfig(this.mContext).create(0)));
                    }
                    LogInfo.LogStatistics("我的缓存");
                    StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "d33", "我的缓存", 3, null, "033", null, null, null, null, null);
                    return;
                case 4:
                    openVipClick();
                    return;
                case 5:
                    if (NetworkUtils.isNetworkAvailable()) {
                        new LetvWebViewActivityConfig(this.mContext).launch(this.mDefaultLetvMallWebsite, null);
                    } else {
                        ToastUtils.showToast(this.mContext, 2131100493);
                    }
                    LogInfo.LogStatistics(this.mActivity.getString(2131100480));
                    StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "d33", this.mActivity.getString(2131100480), 6, null, "033", null, null, null, null, null);
                    return;
                case 8:
                    if (!isLogin) {
                        LetvLoginActivity.launch(this.mActivity, 6);
                        StatisticsUtils.staticticsInfoPost(this.mActivity, "71", null, null, 1, null, null, null, null, null);
                    } else if (((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this.mActivity, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).getUserOrderInfo(this.mActivity) == ORDER_STATE.ORDER) {
                        if (NetworkUtils.getNetworkType() == 0) {
                            ToastUtils.showToast(this.mContext, 2131101181);
                            return;
                        }
                        LetvWoFlowActivity.launchUnOrderActivity(this.mActivity);
                    } else if (NetworkUtils.isUnicom3G(false)) {
                        if (this.mIsSupportProvince) {
                            LetvWoFlowActivity.launchOrderActivity(this.mActivity);
                        } else {
                            ToastUtils.showToast(this.mContext, 2131101184);
                        }
                    } else if (NetworkUtils.getNetworkType() != 0) {
                        ToastUtils.showToast(this.mContext, 2131101194);
                    } else if (NetworkUtils.getNetworkType() == 0) {
                        ToastUtils.showToast(this.mContext, 2131101181);
                    }
                    LogInfo.LogStatistics(this.mContext.getString(2131100485));
                    StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "d33", this.mContext.getString(2131100485), 9, null, "033", null, null, null, null, null);
                    return;
                case 10:
                    SettingsMainActivity.launch(this.mContext);
                    LogInfo.LogStatistics(this.mContext.getString(2131100416));
                    StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "d32", this.mContext.getString(2131100416), 11, null, "033", null, null, null, null, null);
                    return;
                case 11:
                    FeedBackActivity.launch(this.mActivity);
                    LogInfo.LogStatistics(this.mContext.getString(2131100443));
                    StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "e51", this.mContext.getString(2131100443), 10, null, PageIdConstant.settingPage, null, null, null, null, null);
                    return;
                case 12:
                    if (!LetvUtils.isGooglePlay()) {
                        try {
                            LemallPlatform.getInstance().openSdkPage(Constants.PAGE_FLAG_ORDERCENTER, PreferencesManager.getInstance().getSso_tk());
                            return;
                        } catch (Exception e) {
                            return;
                        }
                    }
                    return;
                case 13:
                    if (PreferencesManager.getInstance().getReactNativeEnable() == 0) {
                        MyFollowActivity.launch(this.mActivity);
                        return;
                    } else if (LeMessageManager.getInstance().dispatchMessage(this.mActivity, new LeMessage(LeMessageIds.MSG_WEBVIEW_LAUNCH_FOLLOW)) == null) {
                        MyFollowActivity.launch(this.mActivity);
                        return;
                    } else {
                        return;
                    }
                case 14:
                    MyElectronicTicketActivity.launch(this.mContext);
                    return;
                case 15:
                    RedPacketSdkController.gotoGiftPage(this.mActivity);
                    StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.myHomePage, "0", "d33", "我的红包", -1, null);
                    return;
                case 17:
                    if (!LetvUtils.isGooglePlay()) {
                        LeboxApiManager.gotoLeboxMainPage(this.mActivity);
                        return;
                    }
                    return;
                case 18:
                    StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.myHomePage, "0", "d33", "我的定制", position + 1, null);
                    MineCustomActivity.launch(this.mActivity);
                    return;
                default:
                    return;
            }
        }
    }

    private void openVipClick() {
        new LetvWebViewActivityConfig(this.mContext).launch(USER_CENTER_URL, this.mContext.getString(2131100489));
        StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "d33", this.mContext.getResources().getString(2131100489), 4, null, "033", null, null, null, null, null);
    }
}
