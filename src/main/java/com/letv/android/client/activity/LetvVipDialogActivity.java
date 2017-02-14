package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.bean.RedPacketFrom;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.module.LetvAlipayManager;
import com.letv.android.client.share.ShareUtils;
import com.letv.android.client.task.RequestUserByTokenTask;
import com.letv.android.client.task.RequestVipPackageTask;
import com.letv.android.client.widget.LeftSuperscriptView;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PayCenterApi;
import com.letv.core.api.UserCenterApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.PaymentMethodBean;
import com.letv.core.bean.PaymentMethodBean.PaymentMethod;
import com.letv.core.bean.UserBean;
import com.letv.core.bean.VipSimpleProductBean;
import com.letv.core.bean.VipSimpleProductBean.SimpleProductBean;
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
import com.letv.core.parser.SimpleProductParser;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.core.utils.external.alipay.AlipayCallback;
import com.letv.core.utils.external.alipay.AlipayUtils;
import com.letv.core.utils.external.alipay.AlipayUtils.PayChannel;
import com.letv.core.utils.external.alipay.PayResult;
import com.letv.core.utils.external.alipay.RequestValue;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class LetvVipDialogActivity extends LetvBaseActivity implements OnClickListener, AlipayCallback {
    private final int PAY_ALIPAY;
    private final int PAY_WEIXIN;
    private LinearLayout mAgreeProtocolLayout;
    private TextView mAgreeProtocolTv;
    private TextView mCancleTv;
    private ImageView mCheckProtocolImageView;
    private PaymentMethod mDefaultPayMethodBean;
    private SimpleProductBean mDefaultSimpleProductBean;
    private ImageView mLeftPayImageView;
    private LinearLayout mLeftPayLayout;
    private TextView mLeftPayTypeNameTv;
    private FrameLayout mLoadingView;
    private TextView mMemberShipServiceAgreement;
    private SimpleProductBean mMonthSimpleProductBean;
    private RelativeLayout mMoreVipPackageLayout;
    private TextView mOrderPayNow;
    private RelativeLayout mPackageMonthLayout;
    private TextView mPackageMonthNameTv;
    private TextView mPackageMonthPriceTv;
    private TextView mPackageOneActivityTv;
    private LinearLayout mPackageOneDiscountLayout;
    private LeftSuperscriptView mPackageOneDiscountView;
    private TextView mPackageTwoActivityTv;
    private LinearLayout mPackageTwoDiscountLayout;
    private LeftSuperscriptView mPackageTwoDiscountView;
    private TextView mPackageTwoEvaluationPrice;
    private TextView mPackageYearEvaluationPrice;
    private RelativeLayout mPackageYearLayout;
    private TextView mPackageYearNameTv;
    private TextView mPackageYearPriceTv;
    private TextView mPayRightTypeNameTv;
    private PaymentMethod mPaymentMethodLeft;
    private PaymentMethod mPaymentMethodRight;
    private ImageView mRightImageView;
    private LinearLayout mRightLayout;
    private LinearLayout mRootLayout;
    private String mVipKind;
    private SimpleProductBean mYearSimpleProductBean;
    private int position;
    private boolean sCheckProtocol;

    public LetvVipDialogActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.PAY_ALIPAY = 1;
        this.PAY_WEIXIN = 2;
        this.mVipKind = "0";
        this.position = 1;
        this.sCheckProtocol = true;
    }

    public static void launch(Context context, String videoName) {
        if (LoginConstant.isHongKong()) {
            LetvVipActivity.launch(context, "");
        } else if (NetworkUtils.isNetworkAvailable()) {
            VipProductContant.setVideoTitle(videoName);
            Intent intent = new Intent(context, LetvVipDialogActivity.class);
            if (context instanceof Activity) {
                intent.setFlags(268435456);
            }
            context.startActivity(intent);
        } else {
            UIsUtils.showToast(2131100493);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);
        setContentView(R.layout.letv_vip_dialog_activity);
        if (getIntent() != null && (getIntent().getStringExtra("title") instanceof String)) {
            VipProductContant.setVideoTitle(getIntent().getStringExtra("title"));
        }
        initUI();
        setRedPacketFrom(new RedPacketFrom(0));
        StatisticsUtils.staticticsInfoPost(this, "19", "vp11", null, -1, null, UIsUtils.isLandscape(this.mContext) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE);
    }

    protected void onResume() {
        super.onResume();
    }

    private void initUI() {
        getSimpleProductTask();
        this.mPackageYearNameTv = (TextView) findViewById(R.id.package_one_name_tv);
        this.mPackageYearPriceTv = (TextView) findViewById(R.id.package_one_price_tv);
        this.mPackageYearEvaluationPrice = (TextView) findViewById(R.id.package_one_evaluation_price_tv);
        this.mPackageTwoEvaluationPrice = (TextView) findViewById(R.id.package_two_evaluation_price_tv);
        this.mPackageMonthNameTv = (TextView) findViewById(R.id.package_two_name_tv);
        this.mPackageMonthPriceTv = (TextView) findViewById(R.id.package_two_price_tv);
        this.mLeftPayImageView = (ImageView) findViewById(R.id.pay_type_left_iv);
        this.mRightImageView = (ImageView) findViewById(R.id.pay_type_right_iv);
        this.mLeftPayLayout = (LinearLayout) findViewById(R.id.left_pay_layout);
        this.mRightLayout = (LinearLayout) findViewById(R.id.pay_right_layout);
        this.mLeftPayTypeNameTv = (TextView) findViewById(R.id.pay_type_left_name);
        this.mPayRightTypeNameTv = (TextView) findViewById(R.id.pay_type_right_name);
        this.mCancleTv = (TextView) findViewById(R.id.cancle);
        this.mOrderPayNow = (TextView) findViewById(R.id.pay);
        this.mOrderPayNow.setClickable(false);
        this.mMemberShipServiceAgreement = (TextView) findViewById(R.id.membershipa_service_agreement);
        this.mMoreVipPackageLayout = (RelativeLayout) findViewById(R.id.package_more_layout);
        this.mPackageYearLayout = (RelativeLayout) findViewById(R.id.package_one_layout);
        this.mPackageMonthLayout = (RelativeLayout) findViewById(R.id.package_two_layout);
        this.mPackageOneDiscountView = (LeftSuperscriptView) findViewById(R.id.package_one_discount_view);
        this.mPackageTwoDiscountView = (LeftSuperscriptView) findViewById(R.id.package_two_discount_view);
        this.mPackageOneDiscountLayout = (LinearLayout) findViewById(R.id.package_one_discount_layout);
        this.mPackageTwoDiscountLayout = (LinearLayout) findViewById(R.id.package_two_discount__layout);
        this.mRootLayout = (LinearLayout) findViewById(R.id.dialog_activity_content_layout);
        this.mLoadingView = (FrameLayout) findViewById(R.id.loading_framelayout);
        this.mPackageOneActivityTv = (TextView) findViewById(R.id.package_one_activity_name_tv);
        this.mPackageTwoActivityTv = (TextView) findViewById(R.id.package_two_activity_name_tv);
        this.mAgreeProtocolLayout = (LinearLayout) findViewById(R.id.agree_service_protocol_layout);
        this.mCheckProtocolImageView = (ImageView) findViewById(R.id.agree_service_protocol_check_iv);
        this.mAgreeProtocolTv = (TextView) findViewById(R.id.agree_service_protocol_textview);
        this.mAgreeProtocolTv.getPaint().setFlags(8);
        this.mAgreeProtocolTv.getPaint().setAntiAlias(true);
        initViewClick();
    }

    private void initViewClick() {
        this.mPackageYearLayout.setOnClickListener(this);
        this.mPackageMonthLayout.setOnClickListener(this);
        this.mCancleTv.setOnClickListener(this);
        this.mOrderPayNow.setOnClickListener(this);
        this.mLeftPayLayout.setOnClickListener(this);
        this.mRightLayout.setOnClickListener(this);
        this.mMemberShipServiceAgreement.setOnClickListener(this);
        this.mMoreVipPackageLayout.setOnClickListener(this);
        this.mCheckProtocolImageView.setOnClickListener(this);
        this.mAgreeProtocolTv.setOnClickListener(this);
    }

    public void onClick(View v) {
        boolean z = true;
        switch (v.getId()) {
            case R.id.agree_service_protocol_check_iv /*2131362852*/:
                if (this.sCheckProtocol) {
                    z = false;
                }
                this.sCheckProtocol = z;
                if (this.sCheckProtocol) {
                    this.mCheckProtocolImageView.setBackgroundResource(2130837871);
                    return;
                } else {
                    this.mCheckProtocolImageView.setBackgroundResource(2130837870);
                    return;
                }
            case R.id.agree_service_protocol_textview /*2131362853*/:
                new LetvWebViewActivityConfig(this).launch(UserCenterApi.getLetvVipServiceProtocolUrl(), getString(2131100370), true, false);
                return;
            case R.id.membershipa_service_agreement /*2131363570*/:
                new LetvWebViewActivityConfig(this).launch("http://minisite.letv.com/zt2015/servicenew/index.shtml", getString(2131100370), true, false);
                StatisticsUtils.staticticsInfoPost(this, "0", "vp11", null, 4, null, UIsUtils.isLandscape(this.mContext) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE);
                return;
            case R.id.package_one_layout /*2131363573*/:
                this.position = 1;
                this.mPackageYearLayout.setBackgroundResource(2130839180);
                this.mPackageMonthLayout.setBackgroundResource(2130839177);
                this.mDefaultSimpleProductBean = this.mYearSimpleProductBean;
                return;
            case R.id.package_two_layout /*2131363580*/:
                this.position = 2;
                this.mPackageYearLayout.setBackgroundResource(2130839177);
                this.mPackageMonthLayout.setBackgroundResource(2130839180);
                this.mDefaultSimpleProductBean = this.mMonthSimpleProductBean;
                return;
            case R.id.package_more_layout /*2131363587*/:
                VipProductContant.setPaySuccessType(PaySuccessType.PLAYER);
                LetvVipActivity.launch((Activity) this, getString(2131100645));
                StatisticsUtils.staticticsInfoPost(this, "0", "vp11", null, 3, null, UIsUtils.isLandscape(this.mContext) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE);
                return;
            case R.id.left_pay_layout /*2131363590*/:
                this.mDefaultPayMethodBean = this.mPaymentMethodLeft;
                this.mRightLayout.setBackgroundResource(2130839177);
                this.mLeftPayLayout.setBackgroundResource(2130839180);
                return;
            case R.id.pay_right_layout /*2131363594*/:
                this.mDefaultPayMethodBean = this.mPaymentMethodRight;
                this.mLeftPayLayout.setBackgroundResource(2130839177);
                this.mRightLayout.setBackgroundResource(2130839180);
                return;
            case R.id.cancle /*2131363598*/:
                finish();
                return;
            case R.id.pay /*2131363599*/:
                if (this.mDefaultSimpleProductBean == null) {
                    return;
                }
                if (!this.sCheckProtocol) {
                    ToastUtils.showToast(2131099709);
                    return;
                } else if (NetworkUtils.isNetworkAvailable()) {
                    VipProductContant.setDialogDefaultProductBean(this.mDefaultSimpleProductBean);
                    VipProductContant.setPayMethodBean(this.mDefaultPayMethodBean);
                    if (PreferencesManager.getInstance().isLogin()) {
                        orderPayNow();
                    } else {
                        LetvLoginActivity.launch((Activity) this);
                    }
                    StatisticsUtils.staticticsInfoPost(this, "0", "vp11", this.mDefaultSimpleProductBean.getName(), 5, null, UIsUtils.isLandscape(this.mContext) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE);
                    return;
                } else {
                    ToastUtils.showToast(this.mContext, 2131100493);
                    return;
                }
            default:
                return;
        }
    }

    private void orderPayNow() {
        if (this.mDefaultSimpleProductBean == null) {
            this.mDefaultSimpleProductBean = VipProductContant.getDefaultSimpleProductBean();
        }
        if (this.mDefaultPayMethodBean == null) {
            this.mDefaultPayMethodBean = VipProductContant.getDefaultPayMethod();
        }
        if (this.mDefaultSimpleProductBean != null && this.mDefaultPayMethodBean != null) {
            if (Integer.parseInt(this.mDefaultPayMethodBean.payType) != 2) {
                LetvAlipayManager.getInstance().setFromFlag(1);
                initAlipayClient((int) (this.mDefaultSimpleProductBean.getCurrentPrice() * 100.0f));
                LetvAlipayManager.getInstance().doAlipayClientTask(this);
            } else if (ShareUtils.checkBrowser(this, "com.tencent.mm")) {
                initAlipayClient((int) (this.mDefaultSimpleProductBean.getCurrentPrice() * 100.0f));
                setResult(257);
                LetvAlipayManager.getInstance().doWxpayClientTask(1);
            } else {
                ToastUtils.showToast((Context) this, 2131101007);
            }
        }
    }

    private void initAlipayClient(int price) {
        String str;
        float mCurrentPrice;
        String str2 = this.mDefaultSimpleProductBean.getDays() + "";
        String valueOf = String.valueOf(this.mDefaultSimpleProductBean.getMonthType());
        String name = this.mDefaultSimpleProductBean.getName();
        String str3 = "2";
        String valueOf2 = String.valueOf(price);
        String vipDesc = this.mDefaultSimpleProductBean.getVipDesc();
        String str4 = PayChannel.CLIENT;
        String str5 = "2";
        String uid = LetvUtils.getUID();
        String loginUserName = LetvUtils.getLoginUserName();
        String valueOf3 = String.valueOf(0);
        String str6 = this.mVipKind;
        String maxActivityId = maxActivityId(this.mDefaultPayMethodBean);
        String str7 = this.mDefaultPayMethodBean.title;
        if (PreferencesManager.getInstance().getContinueDiscount(PreferencesManager.getInstance().getUserId()).equals("0")) {
            str = "0";
        } else {
            str = "1";
        }
        RequestValue requestValue = AlipayUtils.getRequestValue(str2, valueOf, name, str3, valueOf2, vipDesc, str4, str5, uid, loginUserName, valueOf3, str6, maxActivityId, str7, str);
        int discount = Integer.parseInt(PreferencesManager.getInstance().getContinueDiscount(PreferencesManager.getInstance().getUserId()));
        if (this.mDefaultSimpleProductBean.getCurrentPrice() - ((float) discount) < 0.0f) {
            mCurrentPrice = 0.0f;
        } else {
            mCurrentPrice = this.mDefaultSimpleProductBean.getCurrentPrice() - ((float) discount);
        }
        LetvAlipayManager.getInstance().setPayPrice(mCurrentPrice);
        LetvAlipayManager.getInstance().initBulid(this, requestValue);
    }

    private String maxActivityId(PaymentMethod mPaymentMethod) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(this.mDefaultSimpleProductBean.getActivityPakageId())) {
            if (this.mDefaultSimpleProductBean.isUseCurrentPrice()) {
                sb.append(this.mDefaultSimpleProductBean.getActivityPakageId()).append(",");
            } else {
                sb.append(this.mDefaultSimpleProductBean.getActivityPakageId());
            }
        }
        if (!TextUtils.isEmpty(mPaymentMethod.activityPackageId)) {
            sb.append(mPaymentMethod.activityPackageId);
        }
        return sb.toString();
    }

    private void getSimpleProductTask() {
        LogInfo.log("LetvVipDialogActivity", "getSimpleProductTask start == " + PayCenterApi.getInstance().requestSimpleVipPackage("0"));
        new LetvRequest(VipSimpleProductBean.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setParser(new SimpleProductParser()).setCache(new VolleyDiskCache("requsetSimpleProductsTask")).setCallback(new SimpleResponse<VipSimpleProductBean>(this) {
            final /* synthetic */ LetvVipDialogActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<VipSimpleProductBean> volleyRequest, VipSimpleProductBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("LetvVipActivity", "getSimpleProductTask onNetworkResponse == " + state);
                switch (state) {
                    case SUCCESS:
                        this.this$0.updateVipProductPackageUI(result);
                        this.this$0.getOrderPackageTask();
                        return;
                    case RESULT_NOT_UPDATE:
                        this.this$0.getOrderPackageTask();
                        return;
                    case PRE_FAIL:
                    case NETWORK_NOT_AVAILABLE:
                    case NETWORK_ERROR:
                        ToastUtils.showToast(2131100007);
                        return;
                    case RESULT_ERROR:
                        ToastUtils.showToast(2131099920);
                        return;
                    default:
                        return;
                }
            }

            public void onCacheResponse(VolleyRequest<VipSimpleProductBean> request, VipSimpleProductBean result, DataHull hull, CacheResponseState state) {
                if (state == CacheResponseState.SUCCESS) {
                    this.this$0.updateVipProductPackageUI(result);
                    this.this$0.getOrderPackageTask();
                }
                request.setUrl(PayCenterApi.getInstance().requestSimpleVipPackage(hull.markId));
            }
        }).add();
    }

    private void updateVipProductPackageUI(VipSimpleProductBean result) {
        if (result != null) {
            ArrayList<SimpleProductBean> mSimpleProductList = result.getSimpleProductList();
            if (mSimpleProductList == null || mSimpleProductList.size() < 2) {
                ToastUtils.showToast(getString(2131099954));
                return;
            }
            updatePackageOneUi((SimpleProductBean) mSimpleProductList.get(0));
            updatePackageTwoUi((SimpleProductBean) mSimpleProductList.get(1));
        }
    }

    private void updatePackageOneUi(SimpleProductBean simpleProductBean) {
        this.mYearSimpleProductBean = simpleProductBean;
        this.mDefaultSimpleProductBean = this.mYearSimpleProductBean;
        this.mPackageYearNameTv.setText(simpleProductBean.getName());
        this.mPackageYearPriceTv.setText("¥ " + formatPrice(simpleProductBean.getCurrentPrice()));
        if (simpleProductBean.getDays() > 31) {
            this.mPackageYearEvaluationPrice.setVisibility(0);
            this.mPackageYearEvaluationPrice.setText(getString(2131100579, new Object[]{Integer.valueOf((int) (simpleProductBean.getCurrentPrice() / ((float) (simpleProductBean.getDays() / 31))))}));
        } else {
            this.mPackageYearEvaluationPrice.setVisibility(8);
        }
        if (simpleProductBean.getSubscript() == 0) {
            this.mPackageOneDiscountLayout.setVisibility(8);
        } else {
            this.mPackageOneDiscountLayout.setVisibility(0);
            this.mPackageOneDiscountView.setText(simpleProductBean.getSubscriptText());
        }
        if (!TextUtils.isEmpty(simpleProductBean.getPackageText()) || simpleProductBean.getLeftQuota() > 0) {
            this.mPackageOneActivityTv.setVisibility(0);
            if (simpleProductBean.getLeftQuota() > 0) {
                this.mPackageOneActivityTv.setText(simpleProductBean.getPackageText() + this.mContext.getString(2131100578, new Object[]{Integer.valueOf(simpleProductBean.getLeftQuota())}));
                return;
            }
            this.mPackageOneActivityTv.setText(simpleProductBean.getPackageText());
            return;
        }
        this.mPackageOneActivityTv.setVisibility(8);
    }

    private void updatePackageTwoUi(SimpleProductBean simpleProductBean) {
        this.mMonthSimpleProductBean = simpleProductBean;
        this.mPackageMonthNameTv.setText(simpleProductBean.getName());
        this.mPackageMonthPriceTv.setText("¥ " + formatPrice(simpleProductBean.getCurrentPrice()));
        if (simpleProductBean.getDays() > 31) {
            this.mPackageTwoEvaluationPrice.setVisibility(0);
            this.mPackageTwoEvaluationPrice.setText(getString(2131100579, new Object[]{Integer.valueOf((int) (simpleProductBean.getCurrentPrice() / ((float) (simpleProductBean.getDays() / 31))))}));
        } else {
            this.mPackageTwoEvaluationPrice.setVisibility(8);
        }
        if (simpleProductBean.getSubscript() == 0) {
            this.mPackageTwoDiscountLayout.setVisibility(8);
        } else {
            this.mPackageTwoDiscountLayout.setVisibility(0);
            this.mPackageTwoDiscountView.setText(simpleProductBean.getSubscriptText());
        }
        if (!TextUtils.isEmpty(simpleProductBean.getPackageText()) || simpleProductBean.getLeftQuota() > 0) {
            this.mPackageTwoActivityTv.setVisibility(0);
            if (simpleProductBean.getLeftQuota() > 0) {
                this.mPackageTwoActivityTv.setText(simpleProductBean.getPackageText() + this.mContext.getString(2131100578, new Object[]{Integer.valueOf(simpleProductBean.getLeftQuota())}));
                return;
            }
            this.mPackageTwoActivityTv.setText(simpleProductBean.getPackageText());
            return;
        }
        this.mPackageTwoActivityTv.setVisibility(8);
    }

    private String formatPrice(float price) {
        return new DecimalFormat("0.00").format((double) price);
    }

    private void getOrderPackageTask() {
        RequestVipPackageTask.getPayKindTask(getActivity(), 2, "1", new SimpleResponse<PaymentMethodBean>(this) {
            final /* synthetic */ LetvVipDialogActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<PaymentMethodBean> volleyRequest, PaymentMethodBean result, DataHull hull, NetworkResponseState state) {
                switch (state) {
                    case SUCCESS:
                        this.this$0.updatePaykindUI(result);
                        return;
                    case RESULT_NOT_UPDATE:
                        return;
                    default:
                        ToastUtils.showToast(this.this$0.mContext, 2131100493);
                        this.this$0.mLoadingView.setVisibility(8);
                        return;
                }
            }

            public void onCacheResponse(VolleyRequest<PaymentMethodBean> volleyRequest, PaymentMethodBean result, DataHull hull, CacheResponseState state) {
                if (state == CacheResponseState.SUCCESS) {
                    this.this$0.updatePaykindUI(result);
                }
            }
        });
    }

    private void updatePaykindUI(PaymentMethodBean result) {
        if (result != null) {
            this.mRootLayout.setVisibility(0);
            this.mLoadingView.setVisibility(8);
            ArrayList<PaymentMethod> paymentMethodList = result.paymentMethodList;
            for (int i = paymentMethodList.size() - 1; i > 0; i--) {
                int payType = Integer.parseInt(((PaymentMethod) paymentMethodList.get(i)).payType);
                if (!(2 == payType || 1 == payType)) {
                    paymentMethodList.remove(i);
                }
            }
            if (paymentMethodList.size() == 2) {
                this.mPaymentMethodLeft = (PaymentMethod) paymentMethodList.get(0);
                this.mPaymentMethodRight = (PaymentMethod) paymentMethodList.get(1);
            }
            if (this.mPaymentMethodLeft != null) {
                this.mDefaultPayMethodBean = this.mPaymentMethodLeft;
                ImageDownloader.getInstance().download(this.mLeftPayImageView, this.mPaymentMethodLeft.logoUrl);
                this.mLeftPayTypeNameTv.setText(Integer.parseInt(this.mPaymentMethodLeft.payType) == 2 ? getString(2131101165) : getString(2131100737));
            }
            if (this.mPaymentMethodRight != null) {
                ImageDownloader.getInstance().download(this.mRightImageView, this.mPaymentMethodRight.logoUrl);
                this.mPayRightTypeNameTv.setText(Integer.parseInt(this.mPaymentMethodRight.payType) == 2 ? getString(2131101165) : getString(2131100737));
                this.mOrderPayNow.setClickable(true);
            }
        }
    }

    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        if (arg1 == 1) {
            if (PreferencesManager.getInstance().isVip()) {
                setResult(1);
                finish();
                return;
            }
            orderPayNow();
        } else if (arg1 == 257) {
            setResult(257);
            finish();
        }
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }

    public void onAlipaySuccess(PayResult payResult, String tradeNo) {
        RequestUserByTokenTask.getUserByTokenTask(getActivity(), PreferencesManager.getInstance().getSso_tk(), new SimpleResponse<UserBean>(this) {
            final /* synthetic */ LetvVipDialogActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onCacheResponse(VolleyRequest<UserBean> volleyRequest, UserBean result, DataHull hull, CacheResponseState state) {
                ToastUtils.showToast(this.this$0.getString(2131100587));
                if (PreferencesManager.getInstance().isVip()) {
                    LetvUtils.sendBroadcast(this.this$0.getActivity(), VipProductContant.ACTION_VIP_AUTH_PASS);
                }
                this.this$0.setResult(257);
                this.this$0.finish();
            }
        });
    }

    public void onAlipayFail(PayResult payResult) {
        ToastUtils.showToast(getString(2131100574));
    }
}
