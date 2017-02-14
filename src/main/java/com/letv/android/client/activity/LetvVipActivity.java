package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.bean.RedPacketFrom;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.fragment.MobileDevicesVipFragment;
import com.letv.android.client.fragment.SuperVipFragment;
import com.letv.android.client.listener.AlipayAutoPayUserSignStatusCallback;
import com.letv.android.client.task.RequestAutoSignPayStatusTask;
import com.letv.android.client.utils.UIs;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PayCenterApi;
import com.letv.core.api.UserCenterApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.VipProductBean;
import com.letv.core.constant.AlipayConstant;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.LoginConstant;
import com.letv.core.constant.VipProductContant;
import com.letv.core.constant.VipProductContant.PaySuccessType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.VipProductParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;

public class LetvVipActivity extends LetvBaseActivity implements OnClickListener, AlipayAutoPayUserSignStatusCallback {
    private int mAllScreenSignFlag;
    private ImageView mBackIv;
    private Button mExchangeBtn;
    private View mHead;
    private int mIsOneKeySignWithAlipayFlag;
    private boolean mIsSeniorVip;
    private TextView mLetvLoginTextView;
    private TextView mMemberShipServiceAgreement;
    private Button mMobileDevicesVipButton;
    private MobileDevicesVipFragment mMobileDevicesVipFragment;
    private int mMobileSignFlag;
    private ImageView mProductImageView;
    private RefreshData mRefreshData;
    private RequestAutoSignPayStatusTask mRequestAutoSignPayStatusTask;
    private PublicLoadLayout mRootView;
    private ScrollView mScrollView;
    private Button mSuperVipButton;
    private SuperVipFragment mSuperVipFragment;
    private TextView mSuperVipTipTv;
    private ImageView mVipImageView;
    private TextView mVipNameTv;

    public LetvVipActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mMobileDevicesVipFragment = new MobileDevicesVipFragment();
        this.mSuperVipFragment = new SuperVipFragment();
        this.mMobileSignFlag = -1;
        this.mAllScreenSignFlag = -1;
        this.mIsOneKeySignWithAlipayFlag = -1;
        this.mRequestAutoSignPayStatusTask = null;
        this.mRefreshData = new RefreshData(this) {
            final /* synthetic */ LetvVipActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.mRootView.loading(true);
                this.this$0.requestAutoPaySignStatusTask("1");
            }
        };
    }

    public static void launch(Context context, String title, String activityTitle, String fromUrl) {
        VipProductContant.payFromH5(activityTitle, fromUrl);
        VipProductContant.setPaySuccessType(PaySuccessType.H5ACTIVITY);
        launch(context, title);
    }

    public static void launch(Context context, String title) {
        if (LoginConstant.isHongKong()) {
            HongKongVipActivity.launch((Activity) context, title);
            return;
        }
        Intent intent = new Intent(context, LetvVipActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("isSeniorVip", PreferencesManager.getInstance().isSViP());
        context.startActivity(intent);
    }

    public static void launch(Activity context, String title) {
        if (LoginConstant.isHongKong()) {
            HongKongVipActivity.launch(context, title);
            return;
        }
        Intent intent = new Intent(context, LetvVipActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("isSeniorVip", PreferencesManager.getInstance().isSViP());
        context.startActivityForResult(intent, 102);
    }

    public static void launch(Activity context, String title, int requestCode) {
        if (LoginConstant.isHongKong()) {
            HongKongVipActivity.launch(context, title);
            return;
        }
        Intent intent = new Intent(context, LetvVipActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("isSeniorVip", PreferencesManager.getInstance().isSViP());
        context.startActivityForResult(intent, requestCode);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRootView = PublicLoadLayout.createPage((Context) this, (int) R.layout.letv_vip_activity);
        this.mRootView.setRefreshData(this.mRefreshData);
        setContentView(this.mRootView);
        initUI();
    }

    private void initUI() {
        setRedPacketFrom(new RedPacketFrom(0));
        this.mRootView.loading(true);
        this.mHead = getLayoutInflater().inflate(R.layout.letv_vip_activity_header, null);
        this.mRootView.addView(this.mHead, new LayoutParams(-1, -2));
        this.mIsSeniorVip = getIntent().getBooleanExtra("isSeniorVip", false);
        this.mProductImageView = (ImageView) findViewById(R.id.sproduct_pic);
        this.mScrollView = (ScrollView) findViewById(R.id.letv_vip_activity_layout);
        this.mBackIv = (ImageView) this.mHead.findViewById(R.id.vip_back_btn);
        this.mBackIv.setOnClickListener(this);
        this.mMobileDevicesVipButton = (Button) findViewById(R.id.mobile_devices_vip_btn);
        this.mMobileDevicesVipButton.setText(TipUtils.getTipMessage("90067", 2131101101));
        this.mSuperVipButton = (Button) findViewById(R.id.super_vip_btn);
        this.mSuperVipButton.setText(TipUtils.getTipMessage("90068", 2131101134));
        this.mLetvLoginTextView = (TextView) findViewById(R.id.letv_vip_login_tv);
        this.mLetvLoginTextView.setOnClickListener(this);
        this.mMobileDevicesVipButton.setOnClickListener(this);
        this.mSuperVipButton.setOnClickListener(this);
        this.mVipImageView = (ImageView) findViewById(R.id.vip_imageview);
        this.mSuperVipTipTv = (TextView) findViewById(R.id.super_vip_tip_tv);
        this.mMemberShipServiceAgreement = (TextView) findViewById(R.id.membershipa_service_agreement);
        this.mMemberShipServiceAgreement.setOnClickListener(this);
        this.mExchangeBtn = (Button) findViewById(R.id.exchange_btn);
        this.mExchangeBtn.setOnClickListener(this);
        this.mVipNameTv = (TextView) findViewById(R.id.vip_name_tv);
        UIs.zoomView(320, 98, this.mProductImageView);
        requestAutoPaySignStatusTask("1");
        StatisticsUtils.staticticsInfoPost(this, "19", "b3", null, -1, null, PageIdConstant.vipPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
    }

    protected void onResume() {
        super.onResume();
        if (PreferencesManager.getInstance().isLogin()) {
            this.mLetvLoginTextView.setVisibility(8);
        } else {
            this.mLetvLoginTextView.setVisibility(0);
        }
    }

    public String[] getAllFragmentTags() {
        return FragmentConstant.TAG_VIP;
    }

    public String getActivityName() {
        return LetvVipActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mobile_devices_vip_btn /*2131363560*/:
                StatisticsUtils.staticticsInfoPost(this, "0", "b32", getResources().getString(2131101101), 1, NetworkUtils.DELIMITER_LINE, PageIdConstant.vipPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                updateVipUI(false);
                return;
            case R.id.super_vip_btn /*2131363561*/:
                StatisticsUtils.staticticsInfoPost(this, "0", "b32", getResources().getString(2131101134), 2, NetworkUtils.DELIMITER_LINE, PageIdConstant.vipPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                updateVipUI(true);
                return;
            case R.id.letv_vip_login_tv /*2131363567*/:
                StatisticsUtils.staticticsInfoPost(this, "0", "b33", "立即登录", 4, null, PageIdConstant.vipPage, null, null, null, null, null);
                LetvLoginActivity.launch(20, (Activity) this);
                return;
            case R.id.exchange_btn /*2131363569*/:
                StatisticsUtils.staticticsInfoPost(this.mContext, "0", "b36", NetworkUtils.DELIMITER_LINE, 1, null, PageIdConstant.vipPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                new LetvWebViewActivityConfig(getActivity()).launch("http://minisite.letv.com/msite/coffeecode/index.shtml", getString(2131100111), true, false);
                return;
            case R.id.membershipa_service_agreement /*2131363570*/:
                new LetvWebViewActivityConfig(getActivity()).launch(UserCenterApi.getLetvVipServiceProtocolUrl(), getString(2131100370), true, false);
                return;
            case R.id.vip_back_btn /*2131363571*/:
                finish();
                return;
            default:
                return;
        }
    }

    private void requsetProductsTask(final String svip) {
        LogInfo.log("LetvVipActivity", "requsetProductsTask start == " + PayCenterApi.getInstance().requestVipProduct(svip, ""));
        new LetvRequest(VipProductBean.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setCache(new VolleyDiskCache("requsetProductsTask" + svip)).setParser(new VipProductParser()).setCallback(new SimpleResponse<VipProductBean>(this) {
            final /* synthetic */ LetvVipActivity this$0;

            public void onNetworkResponse(VolleyRequest<VipProductBean> volleyRequest, VipProductBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("LetvVipActivity", "requsetProductsTask onNetworkResponse == " + state);
                switch (state) {
                    case SUCCESS:
                        if (svip.equals("1")) {
                            this.this$0.mMobileDevicesVipFragment.setmVipProductBean(result);
                            this.this$0.requestAutoPaySignStatusTask("9");
                            return;
                        }
                        this.this$0.mSuperVipFragment.setmVipProductBean(result);
                        this.this$0.mRootView.finish();
                        this.this$0.updateVipUI(this.this$0.mIsSeniorVip);
                        this.this$0.mScrollView.setVisibility(0);
                        return;
                    case PRE_FAIL:
                    case NETWORK_NOT_AVAILABLE:
                    case NETWORK_ERROR:
                        this.this$0.mRootView.netError(false);
                        return;
                    case RESULT_ERROR:
                        this.this$0.mRootView.dataError(false);
                        return;
                    default:
                        return;
                }
            }

            public void onCacheResponse(VolleyRequest<VipProductBean> request, VipProductBean result, DataHull hull, CacheResponseState state) {
                LogInfo.log("LetvVipActivity", "requsetProductsTask onCacheResponse == " + state);
                if (state == CacheResponseState.SUCCESS) {
                    if (svip.equals("1")) {
                        this.this$0.mMobileDevicesVipFragment.setmVipProductBean(result);
                        this.this$0.requestAutoPaySignStatusTask("9");
                    } else {
                        this.this$0.mSuperVipFragment.setmVipProductBean(result);
                        this.this$0.mRootView.finish();
                        this.this$0.updateVipUI(this.this$0.mIsSeniorVip);
                        this.this$0.mScrollView.setVisibility(0);
                    }
                }
                request.setUrl(PayCenterApi.getInstance().requestVipProduct(svip, hull.markId));
            }
        }).add();
    }

    private void requestAutoPaySignStatusTask(String svip) {
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            if (this.mRequestAutoSignPayStatusTask == null) {
                this.mRequestAutoSignPayStatusTask = new RequestAutoSignPayStatusTask(this);
            }
            this.mRequestAutoSignPayStatusTask.setVipType(svip);
            this.mRequestAutoSignPayStatusTask.setAutoSignUserStatusCallback(this);
            this.mRequestAutoSignPayStatusTask.start();
            return;
        }
        this.mRootView.netError(true);
    }

    private void setFragmentArguments(LetvBaseFragment fragment, int isOneKeySignWithAlipayFlag, boolean isSeniorVip) {
        Bundle bundle = new Bundle();
        bundle.putInt(AlipayConstant.IS_ONE_KEY_SIGN_PAY_WITH_ALIPAY, isOneKeySignWithAlipayFlag);
        bundle.putBoolean(AlipayConstant.IS_MOBILE_VIP_FLAG, !isSeniorVip);
        if (fragment.getArguments() != null) {
            fragment.getArguments().putAll(bundle);
        } else {
            fragment.setArguments(bundle);
        }
    }

    private void updateVipUI(boolean isSeniorVip) {
        boolean z = false;
        this.mIsSeniorVip = isSeniorVip;
        if (isSeniorVip) {
            this.mIsOneKeySignWithAlipayFlag = this.mAllScreenSignFlag;
            setFragmentArguments(this.mSuperVipFragment, this.mIsOneKeySignWithAlipayFlag, isSeniorVip);
            showFragmentIfNeeded(this.mSuperVipFragment);
            ImageDownloader.getInstance().download(this.mProductImageView, this.mSuperVipFragment.getmVipProductBean().mMobilePic);
            this.mSuperVipButton.setBackgroundResource(2130838846);
            this.mMobileDevicesVipButton.setBackgroundResource(2130838839);
            this.mMobileDevicesVipButton.setTextColor(getResources().getColor(2131493223));
            this.mSuperVipButton.setTextColor(getResources().getColor(2131493377));
            this.mVipImageView.setBackgroundResource(2130838990);
            Drawable drawable = getResources().getDrawable(2130839188);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.mSuperVipTipTv.setCompoundDrawables(drawable, null, null, null);
            this.mVipNameTv.setText(getString(2131101134));
            PreferencesManager instance = PreferencesManager.getInstance();
            if (this.mMobileSignFlag == 1 || this.mAllScreenSignFlag == 1) {
                z = true;
            }
            instance.setAlipayAutoOpenStatus(z);
            return;
        }
        this.mIsOneKeySignWithAlipayFlag = this.mMobileSignFlag;
        setFragmentArguments(this.mMobileDevicesVipFragment, this.mIsOneKeySignWithAlipayFlag, isSeniorVip);
        showFragmentIfNeeded(this.mMobileDevicesVipFragment);
        ImageDownloader.getInstance().download(this.mProductImageView, this.mMobileDevicesVipFragment.getmVipProductBean().mMobilePic, 2130838798, ScaleType.FIT_XY);
        this.mSuperVipButton.setBackgroundResource(2130838845);
        this.mMobileDevicesVipButton.setBackgroundResource(2130838840);
        this.mMobileDevicesVipButton.setTextColor(getResources().getColor(2131493377));
        this.mSuperVipButton.setTextColor(getResources().getColor(2131493223));
        this.mVipImageView.setBackgroundResource(2130838611);
        drawable = getResources().getDrawable(2130839198);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.mSuperVipTipTv.setCompoundDrawables(drawable, null, null, null);
        this.mVipNameTv.setText(getString(2131101101));
    }

    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        if (arg0 != 20) {
            if (arg1 == 1) {
                if (this.mIsSeniorVip) {
                    this.mSuperVipFragment.skipToOrderDetail();
                } else {
                    this.mMobileDevicesVipFragment.skipToOrderDetail();
                }
            } else if (arg1 == 257) {
                setResult(257);
                finish();
            }
        }
    }

    public void onAutoPayUserSignStatusMobileCallback(int signFlag, int payType, boolean isNotOpenFlag) {
        this.mMobileSignFlag = signFlag;
        requsetProductsTask("1");
    }

    public void onAutoPayUserSignStatusAllScreenCallback(int signFlag, int payType, boolean isNotOpenFlag) {
        this.mAllScreenSignFlag = signFlag;
        requsetProductsTask("9");
    }
}
