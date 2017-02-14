package com.letv.android.client.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.OrderDetailPayKindAdapter;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.fragment.AlipayAutoPayAlreadyOpenDialog;
import com.letv.android.client.fragment.AlipayAutoPayDialog;
import com.letv.android.client.listener.AlipayAutoPayUserSignStatusCallback;
import com.letv.android.client.listener.AlipayConfirmCallback;
import com.letv.android.client.listener.AlipayOneKeyGetOrderInfoCallback;
import com.letv.android.client.listener.AlipayOneKeyPayCallback;
import com.letv.android.client.listener.AlipayOneKeySignCallback;
import com.letv.android.client.module.LetvAlipayManager;
import com.letv.android.client.share.ShareUtils;
import com.letv.android.client.task.RequestAutoPayOneKeyPayTask;
import com.letv.android.client.task.RequestAutoPayOneKeySignTask;
import com.letv.android.client.task.RequestAutoSignPayStatusTask;
import com.letv.android.client.task.RequestLedianPayTask;
import com.letv.android.client.task.RequestVipPackageTask;
import com.letv.android.client.utils.UIs;
import com.letv.core.api.UserCenterApi;
import com.letv.core.bean.AlipayOneKeyPayBean;
import com.letv.core.bean.AlipayOneKeySignBean;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.LeDianBean;
import com.letv.core.bean.PaymentMethodBean;
import com.letv.core.bean.PaymentMethodBean.PaymentMethod;
import com.letv.core.bean.VipProductBean.ProductBean;
import com.letv.core.constant.AlipayConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.external.alipay.AlipayCallback;
import com.letv.core.utils.external.alipay.AlipayUtils;
import com.letv.core.utils.external.alipay.AlipayUtils.PayChannel;
import com.letv.core.utils.external.alipay.BaseHelper;
import com.letv.core.utils.external.alipay.PayResult;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import com.sina.weibo.sdk.component.ShareRequestParam;
import com.tencent.open.yyb.TitleBar;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VipOrderDetailActivity extends LetvBaseActivity implements OnClickListener, AlipayCallback, AlipayConfirmCallback, AlipayAutoPayUserSignStatusCallback, AlipayOneKeySignCallback, AlipayOneKeyPayCallback, AlipayOneKeyGetOrderInfoCallback {
    private static final String ALIPAY_HTTP_HEADER = "http://d.alipay.com/?scheme=";
    private static final String ALIPAY_HTTP_SCHEME_KEY = "alipays://platformapi/startapp?appId=20000067&url=";
    private static final String ALIPAY_ID_TYPE = "23";
    private static final String ALIPAY_PACKAGE_NAME = "com.eg.android.AlipayGphone";
    private static final String ALIPAY_SCHEME_HEADER = "alipays://platformapi/startapp?appId=20000067&preAuth=YES&url=";
    private static final String ENCODE_UTF_8 = "UTF-8";
    public static final int HANDLER_KEY_BACK = 4080;
    private static final String OPEN_ALIPAY_AUTO_PAY = "700061";
    private static final String SCHEME_HOST_VIP_ORDER_DETAIL = "viporderdetail";
    private static final String SCHEME_LETVCLIENT = "letvclient";
    public static String mCorderId;
    private final int PAY_ALIPAY;
    private final int PAY_ALIPAY_WEB;
    private final int PAY_LEDIAN;
    private final int PAY_PHONE;
    private final int PAY_WEIXIN;
    private boolean canBack;
    private int discount;
    private LinearLayout mAgreeProtocolLayout;
    private TextView mAgreeProtocolTv;
    private TextView mAgreement;
    private AlipayAutoPayAlreadyOpenDialog mAlipayAutoPayAlreadyOpenDialog;
    private AlipayAutoPayDialog mAlipayAutoPayDialog;
    private LinearLayout mAlipayAutoPayLayout;
    private TextView mAlipayAutoPayOpenContent;
    private int mAllScreenPayType;
    private ImageView mBackImageview;
    private ImageView mCheckProtocolImageView;
    private float mCurrentPrice;
    private Handler mHandler;
    private boolean mIsFromAlipayAutoPayFlag;
    private boolean mIsMobileVipFlag;
    private int mIsOneKeySignWithAlipayFlag;
    private int mLedianNumber;
    private TextView mLedianPriceTv;
    private int mMobilePayType;
    private String mOpenContent;
    private TextView mOrderDetailDescriptionTv;
    private TextView mOrderDetailDiscountTv;
    private ImageView mOrderDetailImageView;
    private TextView mOrderDetailName;
    private OrderDetailPayKindAdapter mOrderDetailPayKindAdapter;
    private TextView mOrderDetailTime;
    private ImageView mPayMethodImageView;
    private TextView mPayMethodNameDescTv;
    private TextView mPayMethodNameTv;
    private Button mPayNowBtn;
    private ListView mPayOrderListView;
    private PaymentMethodBean mPaymentMethodBean;
    private int mPosition;
    private RelativeLayout mPriceLayout;
    private ProductBean mProductBean;
    private TextView mRealPriceTv;
    private RequestAutoPayOneKeyPayTask mRequestAutoPayOneKeyPayTask;
    private RequestAutoPayOneKeySignTask mRequestAutoPayOneKeySignTask;
    private RequestAutoSignPayStatusTask mRequestAutoSignPayStatusTask;
    private PublicLoadLayout mRootView;
    private TextView mTotalPriceTv;
    private String mVipKind;
    private boolean sCheckProtocol;
    private Handler updateUiHandler;

    public VipOrderDetailActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.sCheckProtocol = true;
        this.mProductBean = new ProductBean();
        this.mPosition = 0;
        this.PAY_ALIPAY = 0;
        this.PAY_ALIPAY_WEB = 1;
        this.PAY_WEIXIN = 2;
        this.PAY_PHONE = 4;
        this.PAY_LEDIAN = 5;
        this.mLedianNumber = -1;
        this.mIsOneKeySignWithAlipayFlag = -1;
        this.mIsMobileVipFlag = true;
        this.mAlipayAutoPayDialog = null;
        this.mAlipayAutoPayAlreadyOpenDialog = null;
        this.mRequestAutoSignPayStatusTask = null;
        this.mRequestAutoPayOneKeySignTask = null;
        this.mRequestAutoPayOneKeyPayTask = null;
        this.mIsFromAlipayAutoPayFlag = false;
        this.updateUiHandler = new Handler(this) {
            final /* synthetic */ VipOrderDetailActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void handleMessage(Message msg) {
                if (msg.obj != null) {
                    this.this$0.updatePaykindUI((PaymentMethodBean) msg.obj);
                }
            }
        };
        this.canBack = true;
        this.mHandler = new Handler(this) {
            final /* synthetic */ VipOrderDetailActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void handleMessage(Message msg) {
                if (msg.what == VipOrderDetailActivity.HANDLER_KEY_BACK) {
                    this.this$0.canBack = true;
                }
            }
        };
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRootView = PublicLoadLayout.createPage((Context) this, (int) R.layout.vip_order_detail_activity);
        setContentView(this.mRootView);
        initUI();
        initSchemeParams();
    }

    private void initSchemeParams() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            String scheme = intent.getScheme();
            if (!TextUtils.isEmpty(scheme) && "letvclient".equalsIgnoreCase(scheme)) {
                Uri data = intent.getData();
                if (data != null && data.getHost().equalsIgnoreCase(SCHEME_HOST_VIP_ORDER_DETAIL)) {
                    PaySucceedActivity.launch(this, PreferencesManager.getInstance().getAlipayAutoProductName(), PreferencesManager.getInstance().getAlipayAutoProductExpire(), PreferencesManager.getInstance().getAlipayAutoProductPrice(), PreferencesManager.getInstance().getAlipayAutoProductPayType(), mCorderId, true);
                }
            }
        }
    }

    private void initUI() {
        if (getIntent() != null) {
            if (getIntent().getBundleExtra(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA) != null) {
                this.mProductBean = (ProductBean) getIntent().getBundleExtra(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA).getSerializable("product_bean");
                this.mVipKind = getIntent().getBundleExtra(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA).getString("vip_kind", "1");
            }
            this.mIsOneKeySignWithAlipayFlag = getIntent().getIntExtra(AlipayConstant.IS_ONE_KEY_SIGN_PAY_WITH_ALIPAY, -1);
            this.mIsMobileVipFlag = getIntent().getBooleanExtra(AlipayConstant.IS_MOBILE_VIP_FLAG, false);
            if (getIntent().hasExtra(AlipayConstant.IS_ONE_KEY_SIGN_PAY_WITH_ALIPAY) || getIntent().hasExtra(AlipayConstant.IS_MOBILE_VIP_FLAG)) {
                this.mIsFromAlipayAutoPayFlag = true;
            }
        }
        this.mOpenContent = TipUtils.getTipMessage(OPEN_ALIPAY_AUTO_PAY, getString(2131099740));
        this.mOpenContent = this.mOpenContent.replace("\\n", "<br>");
        this.mOpenContent = this.mOpenContent.replace("\n", "<br>");
        this.mPayOrderListView = (ListView) findViewById(R.id.pay_order_listview);
        this.mAlipayAutoPayLayout = (LinearLayout) findViewById(R.id.alipay_auto_pay_order);
        this.mAlipayAutoPayOpenContent = (TextView) findViewById(R.id.alipay_auto_pay_open_content);
        this.mAgreement = (TextView) findViewById(R.id.alipay_auto_pay_agreement);
        this.mAgreeProtocolLayout = (LinearLayout) findViewById(R.id.agree_service_protocol_layout);
        this.mCheckProtocolImageView = (ImageView) findViewById(R.id.agree_service_protocol_check_iv);
        this.mCheckProtocolImageView.setOnClickListener(this);
        this.mOrderDetailPayKindAdapter = new OrderDetailPayKindAdapter(this);
        this.mBackImageview = (ImageView) findViewById(R.id.vip_back_btn);
        this.mBackImageview.setOnClickListener(this);
        this.mOrderDetailName = (TextView) findViewById(R.id.order_detail_name);
        this.mOrderDetailName.setText(getString(2131101106) + this.mProductBean.mName);
        this.mOrderDetailImageView = (ImageView) findViewById(R.id.order_detail_iamgeview);
        ImageDownloader.getInstance().download(this.mOrderDetailImageView, this.mProductBean.mMobileIma);
        this.mOrderDetailTime = (TextView) findViewById(R.id.order_detail_time);
        if (this.mProductBean.mDays != 0) {
            this.mOrderDetailTime.setText(getString(2131101107) + (this.mProductBean.mDays / 31) + getString(2131100377) + getString(2131100107));
        }
        this.mOrderDetailDescriptionTv = (TextView) findViewById(R.id.order_detail_discription);
        this.mOrderDetailDescriptionTv.setText(getString(2131101104) + this.mProductBean.mVipDesc);
        this.mOrderDetailDiscountTv = (TextView) findViewById(R.id.order_detail_discount);
        if (TextUtils.isEmpty(this.mProductBean.mPackageText)) {
            this.mOrderDetailDiscountTv.setVisibility(8);
        } else {
            this.mOrderDetailDiscountTv.setText(getString(2131101105) + this.mProductBean.mPackageText);
        }
        this.mTotalPriceTv = (TextView) findViewById(R.id.vip_order_total_price);
        this.mRealPriceTv = (TextView) findViewById(R.id.vip_order_real_price);
        this.mAgreeProtocolTv = (TextView) findViewById(R.id.agree_service_protocol_textview);
        this.mAgreeProtocolTv.getPaint().setFlags(8);
        this.mAgreeProtocolTv.getPaint().setAntiAlias(true);
        this.mAgreeProtocolTv.setOnClickListener(this);
        this.discount = Integer.parseInt(PreferencesManager.getInstance().getContinueDiscount(PreferencesManager.getInstance().getUserId()));
        if (this.discount <= 0 || this.mIsFromAlipayAutoPayFlag) {
            this.mCurrentPrice = this.mProductBean.getNormalPrice();
            this.mTotalPriceTv.setText(" ¥" + LetvUtils.formatDoubleNum(Double.valueOf((double) this.mProductBean.getNormalPrice()).doubleValue(), 2));
            this.mRealPriceTv.setText(" ¥" + LetvUtils.formatDoubleNum(Double.valueOf((double) this.mProductBean.originPrice).doubleValue(), 2));
        } else {
            float f;
            if (this.mProductBean.getNormalPrice() - ((float) this.discount) < 0.0f) {
                f = 0.0f;
            } else {
                f = this.mProductBean.getNormalPrice() - ((float) this.discount);
            }
            this.mCurrentPrice = f;
            this.mTotalPriceTv.setText(" ¥" + LetvUtils.formatDoubleNum(Double.valueOf((double) this.mCurrentPrice).doubleValue(), 2));
            this.mRealPriceTv.setText(" ¥" + LetvUtils.formatDoubleNum(Double.valueOf((double) this.mProductBean.originPrice).doubleValue(), 2) + " " + getString(2131100572, new Object[]{Integer.valueOf(this.discount)}));
        }
        this.mPriceLayout = (RelativeLayout) findViewById(R.id.price_layout);
        this.mLedianPriceTv = (TextView) findViewById(R.id.ledian_price_tv);
        this.mLedianPriceTv.setText(getString(2131100235, new Object[]{Integer.valueOf(((int) this.mProductBean.getLedianPrice()) * 10)}) + "");
        this.mPayNowBtn = (Button) findViewById(R.id.pay_now);
        this.mPayMethodImageView = (ImageView) findViewById(R.id.pay_kind_imageview);
        this.mPayMethodNameTv = (TextView) findViewById(R.id.pay_kind_name_tv);
        this.mPayMethodNameDescTv = (TextView) findViewById(R.id.pay_kind_description_tv);
        this.mPayNowBtn.setOnClickListener(this);
        this.mOrderDetailPayKindAdapter.changeFlag(0);
        this.mPayOrderListView.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ VipOrderDetailActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                this.this$0.mOrderDetailPayKindAdapter.initFlag();
                this.this$0.mOrderDetailPayKindAdapter.changeFlag(arg2);
                this.this$0.mPosition = arg2;
                if (5 == Integer.parseInt(((PaymentMethod) this.this$0.mPaymentMethodBean.paymentMethodList.get(this.this$0.mPosition)).payType)) {
                    this.this$0.mLedianPriceTv.setVisibility(0);
                    this.this$0.mPriceLayout.setVisibility(8);
                } else {
                    this.this$0.mLedianPriceTv.setVisibility(8);
                    this.this$0.mPriceLayout.setVisibility(0);
                }
                if (4 == Integer.parseInt(((PaymentMethod) this.this$0.mPaymentMethodBean.paymentMethodList.get(this.this$0.mPosition)).payType) || this.this$0.mIsFromAlipayAutoPayFlag) {
                    this.this$0.mTotalPriceTv.setText(" ¥" + LetvUtils.formatDoubleNum(Double.valueOf(20.0d).doubleValue(), 2));
                    this.this$0.mRealPriceTv.setText(" ¥" + LetvUtils.formatDoubleNum(Double.valueOf((double) this.this$0.mProductBean.originPrice).doubleValue(), 2));
                    return;
                }
                this.this$0.mTotalPriceTv.setText(" ¥" + LetvUtils.formatDoubleNum(Double.valueOf((double) this.this$0.mCurrentPrice).doubleValue(), 2));
                if (this.this$0.discount > 0) {
                    this.this$0.mRealPriceTv.setText(" ¥" + LetvUtils.formatDoubleNum(Double.valueOf((double) this.this$0.mProductBean.originPrice).doubleValue(), 2) + " " + this.this$0.getString(2131100572, new Object[]{Integer.valueOf(this.this$0.discount)}));
                }
            }
        });
        getOrderPackageTask();
        if (this.mIsFromAlipayAutoPayFlag) {
            this.mAlipayAutoPayLayout.setVisibility(0);
            this.mPayOrderListView.setVisibility(8);
            this.mAgreement.setPaintFlags(this.mAgreement.getPaintFlags() | 8);
            this.mAgreement.setOnClickListener(this);
            this.mAlipayAutoPayOpenContent.setText(Html.fromHtml(this.mOpenContent));
            this.mAgreeProtocolLayout.setVisibility(8);
        } else {
            this.mAlipayAutoPayLayout.setVisibility(8);
            this.mPayOrderListView.setVisibility(0);
            this.mAgreeProtocolLayout.setVisibility(0);
            requestLedianTask();
        }
        StatisticsUtils.staticticsInfoPost(this, "19", NetworkUtils.DELIMITER_LINE, null, -1, null, PageIdConstant.orderDetailInfoPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
    }

    public void onClick(View arg0) {
        boolean z = true;
        switch (arg0.getId()) {
            case R.id.alipay_auto_pay_agreement /*2131362137*/:
                new LetvWebViewActivityConfig(this).launch(AlipayConstant.ALIAPY_AUTO_PAY_AGREEMENT_URL, getString(2131099731), true, false);
                return;
            case R.id.agree_service_protocol_check_iv /*2131362852*/:
                if (this.sCheckProtocol) {
                    z = false;
                }
                this.sCheckProtocol = z;
                if (this.sCheckProtocol) {
                    this.mCheckProtocolImageView.setBackgroundResource(2130837871);
                    this.mPayNowBtn.setBackgroundResource(2130837751);
                    return;
                }
                this.mCheckProtocolImageView.setBackgroundResource(2130837870);
                this.mPayNowBtn.setBackgroundResource(2130837631);
                return;
            case R.id.agree_service_protocol_textview /*2131362853*/:
                new LetvWebViewActivityConfig(this).launch(UserCenterApi.getLetvVipServiceProtocolUrl(), getString(2131100370), true, false);
                return;
            case R.id.vip_back_btn /*2131363571*/:
                finish();
                return;
            case R.id.pay_now /*2131364471*/:
                if (this.sCheckProtocol) {
                    pay();
                    return;
                } else {
                    ToastUtils.showToast(2131099709);
                    return;
                }
            default:
                return;
        }
    }

    private void pay() {
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            int position = 1;
            if (this.mPaymentMethodBean != null && this.mPaymentMethodBean.paymentMethodList != null) {
                switch (Integer.parseInt(((PaymentMethod) this.mPaymentMethodBean.paymentMethodList.get(this.mPosition)).payType)) {
                    case 0:
                        position = 2;
                        payUseAliPay();
                        break;
                    case 1:
                        position = 5;
                        payUseAliPay();
                        break;
                    case 2:
                        position = 3;
                        payUseWeixin();
                        break;
                    case 4:
                        payUsePhone();
                        break;
                    case 5:
                        position = 4;
                        payUseLedian();
                        break;
                }
                StatisticsUtils.staticticsInfoPost(this.mContext, "0", "b35", ((PaymentMethod) this.mPaymentMethodBean.paymentMethodList.get(this.mPosition)).title, position, null, PageIdConstant.orderDetailInfoPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                return;
            }
            return;
        }
        ToastUtils.showToast(this.mContext, 2131100493);
    }

    private void payUseAliPay() {
        LogInfo.log("CRL 支付宝支付开始1 == " + this.mProductBean.mMonthType);
        if (this.mProductBean.mMonthType == 42 || this.mProductBean.mMonthType == 52) {
            if (this.mRequestAutoSignPayStatusTask == null) {
                this.mRequestAutoSignPayStatusTask = new RequestAutoSignPayStatusTask(this);
            }
            this.mRequestAutoSignPayStatusTask.setVipType(this.mIsMobileVipFlag ? "1" : "9");
            this.mRequestAutoSignPayStatusTask.setAutoSignUserStatusCallback(this);
            this.mRequestAutoSignPayStatusTask.start();
            return;
        }
        LetvAlipayManager.getInstance().setFromFlag(2);
        initAlipayClient((int) (this.mProductBean.getNormalPrice() * 100.0f));
        LetvAlipayManager.getInstance().doAlipayClientTask(this);
    }

    private void payUseWeixin() {
        initAlipayClient((int) (this.mProductBean.getNormalPrice() * 100.0f));
        if (ShareUtils.checkBrowser(this, "com.tencent.mm")) {
            LetvAlipayManager.getInstance().doWxpayClientTask(2);
        } else {
            ToastUtils.showToast((Context) this, 2131101007);
        }
    }

    private void payUsePhone() {
        PhonePayActivity.launch(this, this.mVipKind);
    }

    private void payUseLedian() {
        if (((float) this.mLedianNumber) < this.mProductBean.getLedianPrice() * TitleBar.SHAREBTN_RIGHT_MARGIN) {
            UIs.call((Activity) this, 2131101008, null);
        } else {
            new RequestLedianPayTask(this).ledianPayTask(this, maxActivityId((PaymentMethod) this.mPaymentMethodBean.paymentMethodList.get(this.mPosition)), this.mProductBean, this.mVipKind);
        }
    }

    private String maxActivityId(PaymentMethod mPaymentMethod) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(this.mProductBean.mActivityPackageId)) {
            if (this.mProductBean.mUseCurrentPrice) {
                sb.append(this.mProductBean.mActivityPackageId).append(",");
            } else {
                sb.append(this.mProductBean.mActivityPackageId);
            }
        }
        if (!TextUtils.isEmpty(mPaymentMethod.activityPackageId)) {
            sb.append(mPaymentMethod.activityPackageId);
        }
        return sb.toString();
    }

    private void initAlipayClient(int price) {
        String str;
        PaymentMethod mPaymentMethod = (PaymentMethod) this.mPaymentMethodBean.paymentMethodList.get(this.mPosition);
        String str2 = this.mProductBean.mDays + "";
        String valueOf = String.valueOf(this.mProductBean.mMonthType);
        String str3 = this.mProductBean.mName;
        String str4 = "2";
        String valueOf2 = String.valueOf(price);
        String str5 = this.mProductBean.mVipDesc;
        String str6 = PayChannel.CLIENT;
        String str7 = "2";
        String uid = LetvUtils.getUID();
        String loginUserName = LetvUtils.getLoginUserName();
        String valueOf3 = String.valueOf(0);
        String str8 = this.mVipKind;
        String maxActivityId = maxActivityId(mPaymentMethod);
        String str9 = mPaymentMethod.title;
        if (PreferencesManager.getInstance().getContinueDiscount(PreferencesManager.getInstance().getUserId()).equals("0")) {
            str = "0";
        } else {
            str = "1";
        }
        LetvAlipayManager.getInstance().initBulid(this, AlipayUtils.getRequestValue(str2, valueOf, str3, str4, valueOf2, str5, str6, str7, uid, loginUserName, valueOf3, str8, maxActivityId, str9, str));
        LetvAlipayManager.getInstance().setPayPrice(this.mCurrentPrice);
    }

    private void getOrderPackageTask() {
        this.mRootView.loading(true);
        RequestVipPackageTask.getPayKindTask(getActivity(), this.mProductBean.mMonthType, this.mVipKind, new SimpleResponse<PaymentMethodBean>(this) {
            final /* synthetic */ VipOrderDetailActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<PaymentMethodBean> volleyRequest, PaymentMethodBean result, DataHull hull, NetworkResponseState state) {
                switch (state) {
                    case SUCCESS:
                        this.this$0.updateUiPostHandler(result);
                        return;
                    case RESULT_NOT_UPDATE:
                        return;
                    default:
                        if (this.this$0.mPaymentMethodBean == null) {
                            this.this$0.mRootView.netError(true);
                            return;
                        }
                        return;
                }
            }

            public void onCacheResponse(VolleyRequest<PaymentMethodBean> volleyRequest, PaymentMethodBean result, DataHull hull, CacheResponseState state) {
                if (state == CacheResponseState.SUCCESS) {
                    this.this$0.updateUiPostHandler(result);
                }
            }
        });
    }

    private void updateUiPostHandler(PaymentMethodBean result) {
        this.mRootView.finish();
        if (result != null) {
            Message message = new Message();
            message.obj = result;
            this.updateUiHandler.sendMessage(message);
        }
    }

    private void updatePaykindUI(PaymentMethodBean result) {
        if (result != null) {
            ArrayList<PaymentMethod> paymentMethodList = result.paymentMethodList;
            if (this.mIsFromAlipayAutoPayFlag) {
                this.mPaymentMethodBean = result;
                int nSize = paymentMethodList.size();
                PaymentMethod productMethod = null;
                for (int i = 0; i < nSize; i++) {
                    if (((PaymentMethod) paymentMethodList.get(i)).id.equals("23")) {
                        productMethod = (PaymentMethod) paymentMethodList.get(i);
                        break;
                    }
                }
                if (productMethod != null) {
                    ImageDownloader.getInstance().download(this.mPayMethodImageView, productMethod.logoUrl);
                    this.mPayMethodNameTv.setText(productMethod.title);
                    this.mPayMethodNameDescTv.setText(productMethod.subTitle);
                    if ("0".equals(productMethod.activityStatus)) {
                        this.mPayMethodNameDescTv.setTextColor(this.mContext.getResources().getColor(2131493280));
                        return;
                    } else {
                        this.mPayMethodNameDescTv.setTextColor(this.mContext.getResources().getColor(2131493192));
                        return;
                    }
                }
                return;
            }
            PaymentMethod mPaymentMethod;
            Iterator it = paymentMethodList.iterator();
            while (it.hasNext()) {
                mPaymentMethod = (PaymentMethod) it.next();
                if ("1".equals(this.mVipKind)) {
                    if ("5".equals(mPaymentMethod.payType)) {
                        paymentMethodList.remove(mPaymentMethod);
                        break;
                    }
                } else if ("4".equals(mPaymentMethod.payType)) {
                    paymentMethodList.remove(mPaymentMethod);
                    break;
                }
            }
            it = paymentMethodList.iterator();
            while (it.hasNext()) {
                mPaymentMethod = (PaymentMethod) it.next();
                if ("1".equals(this.mVipKind) && this.mProductBean.mMonthType != 2 && "4".equals(mPaymentMethod.payType)) {
                    paymentMethodList.remove(mPaymentMethod);
                    break;
                }
            }
            this.mPaymentMethodBean = result;
            this.mOrderDetailPayKindAdapter.setList(result.paymentMethodList);
            this.mPayOrderListView.setAdapter(this.mOrderDetailPayKindAdapter);
        }
    }

    private void requestLedianTask() {
        RequestVipPackageTask.getLedianTask(getActivity(), new SimpleResponse<LeDianBean>(this) {
            final /* synthetic */ VipOrderDetailActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<LeDianBean> volleyRequest, LeDianBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("VipProductsActivity", "requestLedianTask onNetworkResponse == " + state);
                if (state == NetworkResponseState.SUCCESS) {
                    this.this$0.mLedianNumber = result.balance;
                    this.this$0.mOrderDetailPayKindAdapter.setLedianNumber(this.this$0.mLedianNumber);
                    return;
                }
                ToastUtils.showToast(this.this$0, 2131100493);
            }
        });
    }

    private void requestAlipayAutoSign(String corderId) {
        LogInfo.log("CRL 支付宝自动续费  corderId ==  " + corderId + "  mIsOneKeySignWithAlipayFlag == " + this.mIsOneKeySignWithAlipayFlag);
        String monthType = this.mProductBean.mMonthType + "";
        String currentPrice = this.mProductBean.getNormalPrice() + "";
        String autoRenewFlag = "1";
        if (this.mIsOneKeySignWithAlipayFlag == 2) {
            if (this.mRequestAutoPayOneKeyPayTask == null) {
                this.mRequestAutoPayOneKeyPayTask = new RequestAutoPayOneKeyPayTask(this, "", corderId, monthType, currentPrice, this.mIsMobileVipFlag ? "1" : "9", autoRenewFlag, this.mIsMobileVipFlag ? this.mMobilePayType + "" : this.mAllScreenPayType + "");
            }
            this.mRequestAutoPayOneKeyPayTask.setAlipayOneKeyPayCallback(this);
            this.mRequestAutoPayOneKeyPayTask.start();
        } else if (this.mIsOneKeySignWithAlipayFlag == 3) {
            PreferencesManager.getInstance().setAlipayAutoProductName(this.mProductBean.mName);
            PreferencesManager.getInstance().setAlipayAutoProductExpire(this.mProductBean.mExpire);
            PreferencesManager.getInstance().setAlipayAutoProductPrice(this.mProductBean.getNormalPrice() + "");
            PreferencesManager.getInstance().setAlipayAutoProductPayType(this.mProductBean.mVipDesc);
            if (this.mRequestAutoPayOneKeySignTask == null) {
                this.mRequestAutoPayOneKeySignTask = new RequestAutoPayOneKeySignTask(this, "", corderId, monthType, currentPrice, this.mIsMobileVipFlag ? "1" : "9", autoRenewFlag);
            }
            this.mRequestAutoPayOneKeySignTask.setAlipayOneKeySignCallback(this);
            this.mRequestAutoPayOneKeySignTask.start();
        }
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return VipOrderDetailActivity.class.getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }

    public void onAlipaySuccess(PayResult payResult, String tradeNo) {
        LogInfo.log("resultStatus = " + payResult.getResultStatus() + " , memo = " + payResult.getMemo() + " , result = " + payResult.getResult());
        PaySucceedActivity.launch(this, this.mProductBean.mName, this.mProductBean.mExpire, this.mCurrentPrice + "", this.mProductBean.mVipDesc, tradeNo);
    }

    public void onAlipayFail(PayResult payResult) {
        String errorMsg;
        if (TextUtils.isEmpty(payResult.getMemo())) {
            errorMsg = getString(2131099748);
        } else {
            errorMsg = payResult.getMemo();
        }
        LogInfo.log("resultStatus = " + payResult.getResultStatus() + " , memo = " + payResult.getMemo() + " , result = " + payResult.getResult());
        BaseHelper.showDialog(this, getString(2131100003), errorMsg, 2130838270);
    }

    public void onAlipayConfirm(boolean isAutoPayFlag) {
        LogInfo.log("CRL 支付宝支付开始 开通&恢复&暂停 连续包月服务， 关闭对话框isAutoPayFlag == " + isAutoPayFlag);
        if (isAutoPayFlag) {
            initAlipayClient((int) (this.mProductBean.getNormalPrice() * 100.0f));
            LetvAlipayManager.getInstance().setGetOrderInfoCallback(this);
            LetvAlipayManager.getInstance().doAlipayContinuePayClientTask(this);
            return;
        }
        finish();
    }

    @TargetApi(17)
    private void showAlipayDialog(boolean notOpenFlag) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null && getActivity() != null && !getActivity().isDestroyed()) {
            FragmentTransaction ft = fm.beginTransaction();
            this.mAlipayAutoPayDialog = (AlipayAutoPayDialog) fm.findFragmentByTag("showmAlipayPayDialog");
            Bundle bundle = new Bundle();
            bundle.putBoolean(AlipayConstant.NOT_OPEN_CONTINUE_MONTHLY, notOpenFlag);
            bundle.putBoolean(AlipayConstant.IS_MOBILE_VIP_FLAG, this.mIsMobileVipFlag);
            if (this.mAlipayAutoPayDialog == null) {
                this.mAlipayAutoPayDialog = new AlipayAutoPayDialog();
            } else {
                ft.remove(this.mAlipayAutoPayDialog);
            }
            this.mAlipayAutoPayDialog.setAlipayConfirmCallback(this);
            if (this.mAlipayAutoPayDialog.getArguments() == null) {
                this.mAlipayAutoPayDialog.setArguments(bundle);
            }
            ft.add(this.mAlipayAutoPayDialog, "showmAlipayPayDialog");
            ft.commitAllowingStateLoss();
        }
    }

    @TargetApi(17)
    private void showAlreadyOpenDialog() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null && getActivity() != null && !getActivity().isDestroyed()) {
            FragmentTransaction ft = fm.beginTransaction();
            this.mAlipayAutoPayAlreadyOpenDialog = (AlipayAutoPayAlreadyOpenDialog) fm.findFragmentByTag("showmAlipayAutoPayAlreadyOpenDialog");
            if (this.mAlipayAutoPayAlreadyOpenDialog == null) {
                this.mAlipayAutoPayAlreadyOpenDialog = new AlipayAutoPayAlreadyOpenDialog();
            } else {
                ft.remove(this.mAlipayAutoPayAlreadyOpenDialog);
            }
            this.mAlipayAutoPayAlreadyOpenDialog.setAlipayConfirmCallback(this);
            ft.add(this.mAlipayAutoPayAlreadyOpenDialog, "showmAlipayAutoPayAlreadyOpenDialog");
            ft.commitAllowingStateLoss();
        }
    }

    public void onOneKeyPayCallback(AlipayOneKeyPayBean result) {
        LogInfo.log("CRL 支付宝付费 一键回调支付 result  == " + result + " result.code == " + result.code);
        if (result.code == 0) {
            if (TextUtils.isEmpty(result.info)) {
                if (!TextUtils.isEmpty(result.msg)) {
                    ToastUtils.showToast(result.msg);
                }
                if (this.mProductBean != null) {
                    PaySucceedActivity.launch(this, this.mProductBean.mName, this.mProductBean.mExpire, this.mProductBean.getNormalPrice() + "", this.mProductBean.mVipDesc, mCorderId, true);
                }
                setResult(257);
                finish();
                return;
            }
            redirectAndStartAlipay(result.info);
        } else if (!TextUtils.isEmpty(result.msg)) {
            ToastUtils.showToast(result.msg);
        }
    }

    public void onOneKeySignCallback(AlipayOneKeySignBean result) {
        LogInfo.log("CRL 支付宝付费 一键签约回调 result  == " + result + " result.code == " + result.status + " orderId == " + result.corderid);
        mCorderId = result.corderid;
        if (result.status == 1) {
            PreferencesManager.getInstance().setAlipayAutoPayPartnerId(result.partnerId);
            if (!TextUtils.isEmpty(result.info)) {
                redirectAndStartAlipay(result.info);
            } else if (!TextUtils.isEmpty(result.errormsg)) {
                ToastUtils.showToast(result.errormsg);
            }
        } else if (!TextUtils.isEmpty(result.errormsg)) {
            ToastUtils.showToast(result.errormsg);
        }
    }

    private boolean isPackageAvailable(Context context, String packageName) {
        List<PackageInfo> pinfo = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (((PackageInfo) pinfo.get(i)).packageName.equalsIgnoreCase(packageName)) {
                return true;
            }
        }
        return false;
    }

    private void redirectAndStartAlipay(String redirectUrl) {
        LogInfo.log("CRL 支付宝支付 跳转并打开支付宝钱包 == " + redirectUrl);
        LogInfo.log("CRL 支付宝支付 跳转并打开支付宝钱包 isPackageAvailable== " + isPackageAvailable(this, "com.eg.android.AlipayGphone"));
        String requestUrl = "";
        if (isPackageAvailable(this, "com.eg.android.AlipayGphone")) {
            try {
                requestUrl = ALIPAY_SCHEME_HEADER + URLEncoder.encode(redirectUrl, "UTF-8");
                LogInfo.log("CRL 支付宝支付 跳转并打开支付宝钱包 requestUrl== " + requestUrl);
                Intent intent = null;
                try {
                    intent = Intent.parseUri(requestUrl, 1);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
                return;
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
                return;
            }
        }
        try {
            requestUrl = ALIPAY_HTTP_HEADER + URLEncoder.encode(ALIPAY_HTTP_SCHEME_KEY + URLEncoder.encode(redirectUrl, "UTF-8"), "UTF-8");
            LogInfo.log("CRL 支付宝支付 跳转并打开支付宝钱包 requestUrl== " + requestUrl);
        } catch (UnsupportedEncodingException e22) {
            e22.printStackTrace();
        }
        if (!TextUtils.isEmpty(requestUrl)) {
            new LetvWebViewActivityConfig(this).launch(requestUrl, "", true, false);
        }
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 257) {
            setResult(resultCode);
            finish();
        }
    }

    public void onAutoPayUserSignStatusMobileCallback(int signFlag, int payType, boolean isNotOpenFlag) {
        this.mMobilePayType = payType;
        showAlipayDialog(signFlag, isNotOpenFlag);
    }

    public void onAutoPayUserSignStatusAllScreenCallback(int signFlag, int payType, boolean isNotOpenFlag) {
        this.mAllScreenPayType = payType;
        showAlipayDialog(signFlag, isNotOpenFlag);
    }

    private void showAlipayDialog(int signFlag, boolean isNotOpenFlag) {
        LogInfo.log("CRL 支付宝支付开始2 第一个 URL返回后 signFlag== " + signFlag);
        if (signFlag == 3) {
            showAlipayDialog(isNotOpenFlag);
        } else if (signFlag == 2) {
            showAlipayDialog(isNotOpenFlag);
        } else if (signFlag == 1) {
            showAlreadyOpenDialog();
        }
    }

    public void enableKeyback(boolean canBack, long delayMillis) {
        this.canBack = canBack;
        if (canBack) {
            this.mHandler.removeMessages(HANDLER_KEY_BACK);
        } else {
            this.mHandler.sendEmptyMessageDelayed(HANDLER_KEY_BACK, delayMillis);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (!this.canBack) {
            return true;
        }
        setResult(258);
        finish();
        return true;
    }

    public void onBackPressed() {
        if (this.canBack) {
            setResult(258);
            finish();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mRequestAutoSignPayStatusTask != null) {
            this.mRequestAutoSignPayStatusTask.setAutoSignUserStatusCallback(null);
        }
    }

    public void onOneKeyGetOrderInfoCallback(String corderId) {
        LogInfo.log("CRL 支付宝支付开始 订单信息开始  corderId ==  " + corderId);
        if (TextUtils.isEmpty(corderId)) {
            LogInfo.log("alipay", "onOneKeyGetOrderInfoCallback corderId is Empty!");
            return;
        }
        mCorderId = corderId;
        requestAlipayAutoSign(corderId);
    }
}
