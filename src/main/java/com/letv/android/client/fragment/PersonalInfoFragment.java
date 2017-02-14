package com.letv.android.client.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.AlipayAutoPayCancelActivity;
import com.letv.android.client.activity.LetvVipActivity;
import com.letv.android.client.activity.ModifyPwdActivity;
import com.letv.android.client.activity.MyTicketActivity;
import com.letv.android.client.activity.RechargeRecordActivity;
import com.letv.android.client.activity.ShareActivity;
import com.letv.android.client.commonlib.config.ConsumeRecordActivityConfig;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.listener.AlipayAutoPayUserStatusCallback;
import com.letv.android.client.module.LoginManager;
import com.letv.android.client.task.RequestAutoPayUserStatusTask;
import com.letv.android.client.utils.UIs;
import com.letv.android.client.view.RoundImageView;
import com.letv.core.bean.UserBean;
import com.letv.core.constant.AlipayConstant;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.contentprovider.UserInfoTools;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.download.manager.DownloadManager;
import com.letv.redpacketsdk.RedPacketSdkManager;

@SuppressLint({"ValidFragment"})
public class PersonalInfoFragment extends LetvBaseFragment implements OnClickListener, AlipayAutoPayUserStatusCallback {
    private Button mBeVip;
    private View mBeVipLayout;
    private TextView mCancelAlipayAutoPay;
    private View mCancelAlipayAutoPayLine;
    private TextView mConsumeRecord;
    private String mCurVipTyp = "1";
    private RoundImageView mHeadView;
    private TextView mLogout;
    private TextView mModifyPwd;
    private TextView mMyTicket;
    private TextView mName;
    private TextView mRechargeRecord;
    private RequestAutoPayUserStatusTask mRequestAutoPayUserStatusTask;
    private PublicLoadLayout mRootView;
    private TextView mShareSet;
    private TextView mSvipExpireText;
    private TextView mSvipExpireValue;
    private UserBean mUserBean;
    private int mUserStatus = -1;
    private TextView mVipExpireText;
    private TextView mVipExpireValue;
    private RelativeLayout mVipInfoContainer;
    private TextView mWoFlowLogoutTipInfo;

    public PersonalInfoFragment(UserBean mUserBean) {
        this.mUserBean = mUserBean;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = PublicLoadLayout.createPage(getActivity(), (int) R.layout.personal_info_fragment_layout);
        return this.mRootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        requestAlipayAutoPayUserStatus("1");
    }

    private void requestAlipayAutoPayUserStatus(String vipType) {
        if (this.mRequestAutoPayUserStatusTask == null) {
            this.mRequestAutoPayUserStatusTask = new RequestAutoPayUserStatusTask(getActivity());
        }
        this.mRequestAutoPayUserStatusTask.setVipType(vipType);
        this.mRequestAutoPayUserStatusTask.setAutoSignUserStatusCallback(this);
        this.mRequestAutoPayUserStatusTask.start();
    }

    private void initUI() {
        this.mHeadView = (RoundImageView) this.mRootView.findViewById(R.id.pim_head);
        this.mName = (TextView) this.mRootView.findViewById(R.id.pim_name);
        this.mVipExpireText = (TextView) this.mRootView.findViewById(R.id.pim_vip_expire_time_text);
        this.mSvipExpireText = (TextView) this.mRootView.findViewById(R.id.pim_svip_expire_time_text);
        this.mVipInfoContainer = (RelativeLayout) this.mRootView.findViewById(R.id.vip_container);
        this.mBeVipLayout = this.mRootView.findViewById(R.id.pim_be_vip_layout);
        this.mSvipExpireValue = (TextView) this.mRootView.findViewById(R.id.pim_svip_expire_time);
        this.mWoFlowLogoutTipInfo = (TextView) this.mRootView.findViewById(R.id.wo_flow_logout_tips_info);
        this.mVipExpireValue = (TextView) this.mRootView.findViewById(R.id.pim_vip_expire_time);
        this.mBeVip = (Button) this.mRootView.findViewById(R.id.pim_be_vip);
        this.mConsumeRecord = (TextView) this.mRootView.findViewById(R.id.pim_consume_record);
        this.mConsumeRecord.setOnClickListener(this);
        this.mMyTicket = (TextView) this.mRootView.findViewById(R.id.pim_my_ticket);
        this.mMyTicket.setOnClickListener(this);
        this.mRechargeRecord = (TextView) this.mRootView.findViewById(R.id.pim_recharge_record);
        this.mRechargeRecord.setOnClickListener(this);
        this.mCancelAlipayAutoPay = (TextView) this.mRootView.findViewById(R.id.pim_cancel_alipay_auto_pay);
        this.mCancelAlipayAutoPay.setOnClickListener(this);
        this.mCancelAlipayAutoPayLine = this.mRootView.findViewById(R.id.pim_cancel_alipay_auto_pay_line);
        this.mShareSet = (TextView) this.mRootView.findViewById(R.id.pim_shareset);
        this.mShareSet.setOnClickListener(this);
        this.mLogout = (TextView) this.mRootView.findViewById(R.id.pim_logout);
        this.mLogout.setOnClickListener(this);
        this.mModifyPwd = (TextView) this.mRootView.findViewById(R.id.pim_modify_pwd);
        this.mModifyPwd.setOnClickListener(this);
        updateUI(this.mUserBean);
    }

    public void onClick(View v) {
        boolean z = false;
        switch (v.getId()) {
            case R.id.pim_consume_record /*2131363990*/:
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new ConsumeRecordActivityConfig(this.mContext)));
                return;
            case R.id.pim_my_ticket /*2131363991*/:
                MyTicketActivity.launch(getActivity(), "1".equals(this.mUserBean.isvip));
                return;
            case R.id.pim_recharge_record /*2131363992*/:
                RechargeRecordActivity.launch(getActivity());
                return;
            case R.id.pim_cancel_alipay_auto_pay /*2131363994*/:
                Activity activity = getActivity();
                if (this.mUserStatus == 1) {
                    z = true;
                }
                AlipayAutoPayCancelActivity.launch(activity, z, this.mCurVipTyp.equals("1"));
                return;
            case R.id.pim_shareset /*2131363995*/:
                ShareActivity.launch(getActivity());
                return;
            case R.id.pim_modify_pwd /*2131363996*/:
                getActivity().startActivity(new Intent(getActivity(), ModifyPwdActivity.class));
                return;
            case R.id.pim_logout /*2131363997*/:
                doLogout();
                LogInfo.log("glh", "退出");
                return;
            case R.id.pim_be_vip /*2131364312*/:
                LetvVipActivity.launch(getActivity(), getResources().getString(2131100645));
                LogInfo.LogStatistics("be vip----PersonalInfoFragment");
                StatisticsUtils.staticticsInfoPost(getActivity(), "713", getResources().getString(2131100645), 0, -1, null, null, null, null, null);
                return;
            default:
                return;
        }
    }

    public String getTagName() {
        return FragmentConstant.TAG_SETTINGS_PERSION_INFO;
    }

    public int getContainerId() {
        return R.id.setting_center_viewpage;
    }

    private void updateUI(UserBean mUserBean) {
        this.mUserBean = mUserBean;
        String icon = mUserBean.picture;
        String tag = (String) this.mHeadView.getTag();
        if (!TextUtils.isEmpty(tag) && TextUtils.isEmpty(icon) && tag.equalsIgnoreCase(icon)) {
            this.mHeadView.setImageDrawable(getActivity().getResources().getDrawable(2130837633));
        } else {
            ImageDownloader.getInstance().download(this.mHeadView, icon);
            this.mHeadView.setTag(icon);
        }
        this.mName.setText(TextUtils.isEmpty(mUserBean.nickname) ? getResources().getString(2131100240) : mUserBean.nickname);
        if ("1".equals(mUserBean.isvip)) {
            this.mVipInfoContainer.setVisibility(0);
            this.mBeVipLayout.setVisibility(8);
            this.mWoFlowLogoutTipInfo.setVisibility(0);
            if (mUserBean.vipInfo.vipType == 2) {
                this.mSvipExpireText.setVisibility(0);
                this.mSvipExpireValue.setVisibility(0);
                this.mSvipExpireValue.setText(getResources().getString(2131100490, new Object[]{StringUtils.timeString(mUserBean.vipInfo.seniorcanceltime)}));
                if (mUserBean.vipInfo.canceltime == 0 || mUserBean.vipInfo.canceltime == mUserBean.vipInfo.seniorcanceltime) {
                    this.mVipExpireText.setVisibility(8);
                    this.mVipExpireValue.setVisibility(8);
                    return;
                }
                this.mVipExpireText.setVisibility(0);
                this.mVipExpireValue.setVisibility(0);
                this.mVipExpireValue.setText(getResources().getString(2131100490, new Object[]{StringUtils.timeString(mUserBean.vipInfo.canceltime)}));
                return;
            }
            this.mSvipExpireText.setVisibility(8);
            this.mSvipExpireValue.setVisibility(8);
            this.mVipExpireValue.setText(getResources().getString(2131100490, new Object[]{StringUtils.timeString(mUserBean.vipInfo.canceltime)}));
            return;
        }
        this.mVipInfoContainer.setVisibility(8);
        this.mBeVipLayout.setVisibility(0);
        this.mWoFlowLogoutTipInfo.setVisibility(8);
        this.mBeVip.setOnClickListener(this);
    }

    private void doLogout() {
        UIs.showDialog(getActivity(), getActivity().getText(2131100596), null, 0, "", "", null, new 1(this), 0, 0, 0, 0);
    }

    public static void sysLogout(Activity activity) {
        if (PreferencesManager.getInstance().isVip()) {
            DownloadManager.pauseVipDownloadTask();
        }
        LoginManager.getLoginManager().unBindLogin(activity);
        LoginManager.getLoginManager().sendLogInOutIntent("logout_success", activity);
        PreferencesManager.getInstance().logoutUser();
        PreferencesManager.getInstance().setUserPhoneNumberBindState(false);
        LoginManager.getLoginManager().LogoutUser(activity);
        LogInfo.log("ZSM", "ID == " + PreferencesManager.getInstance().getUserId());
        UserInfoTools.logout(activity);
        RedPacketSdkManager.getInstance().setUid("");
        RedPacketSdkManager.getInstance().setToken("");
        try {
            LeMessageManager.getInstance().dispatchMessage(activity, new LeMessage(LeMessageIds.MSG_WEBVIEW_SYNC_LOGIN));
            activity.setResult(9528);
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCancelAlipayAutoPay() {
        if (this.mUserStatus == 1) {
            this.mCancelAlipayAutoPay.setVisibility(0);
            this.mCancelAlipayAutoPayLine.setVisibility(0);
        } else if (this.mUserStatus == 0 || this.mUserStatus == 2) {
            this.mCancelAlipayAutoPay.setVisibility(8);
            this.mCancelAlipayAutoPayLine.setVisibility(8);
        }
    }

    public void onAutoPayUserStatusCallback(int userStatus, int svip) {
        this.mUserStatus = userStatus;
        this.mCurVipTyp = String.valueOf(svip);
        showCancelAlipayAutoPay();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            this.mUserStatus = data.getIntExtra(AlipayConstant.CURRENT_OPEN_STATUS, 0);
            showCancelAlipayAutoPay();
        }
    }
}
