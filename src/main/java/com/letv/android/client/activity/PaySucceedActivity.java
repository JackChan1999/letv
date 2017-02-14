package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.commonlib.config.ConsumeRecordActivityConfig;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.listener.GiftShareAwardCallback;
import com.letv.android.client.commonlib.utils.UIControllerUtils;
import com.letv.android.client.commonlib.view.RedPacketDialog;
import com.letv.android.client.listener.ShowRedPackageIconCallback;
import com.letv.android.client.share.ShareUtils;
import com.letv.android.client.task.RequestUserByTokenTask;
import com.letv.android.client.view.HalfPlaySharePopwindow;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.RedPacketBean;
import com.letv.core.bean.UserBean;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.LoginConstant;
import com.letv.core.constant.VipProductContant;
import com.letv.core.constant.VipProductContant.PaySuccessType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.parser.RedPacketParser;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.sina.weibo.sdk.component.ShareRequestParam;

public class PaySucceedActivity extends WrapActivity implements OnClickListener, ShowRedPackageIconCallback {
    public static boolean mShowRedPackage = false;
    private final String GET_RED_PACKAGE;
    public boolean isNormalClick;
    private Button mBackBtn;
    private Button mBackGoOnSeeMovieBtn;
    private Button mBackJoinActivityBtn;
    private TextView mDescriptionTv;
    private RedPacketDialog mDialog;
    private String mExpire;
    private boolean mFromAlipayContinuePay;
    private GiftShareAwardCallback mGiftShareAwardCallback;
    private HalfPlaySharePopwindow mHalfPlaySharePopwindow;
    private Button mLookConsume;
    private String mOrderId;
    private String mPayType;
    private String mPname;
    private TextView mPnameView;
    private LinearLayout mPointMeSendRedPackageLayout;
    private String mPrice;
    private TextView mPriceView;
    private RedPacketBean mRedPacketBean;
    private View mRoot;
    private Button mSeeMovieBtn;
    private boolean mShowDialogFlag;
    private TextView mTipTextView;

    public PaySucceedActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mFromAlipayContinuePay = false;
        this.mDialog = null;
        this.mShowDialogFlag = false;
        this.mHalfPlaySharePopwindow = null;
        this.isNormalClick = false;
        this.GET_RED_PACKAGE = "get_red_package";
        this.mGiftShareAwardCallback = new GiftShareAwardCallback(this) {
            final /* synthetic */ PaySucceedActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void showAwardPage(String awardUrl) {
                this.this$0.mPointMeSendRedPackageLayout.setVisibility(8);
                PaySucceedActivity.mShowRedPackage = false;
                new LetvRequest(RedPacketBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(awardUrl).setParser(new RedPacketParser()).setCallback(new SimpleResponse<RedPacketBean>(this) {
                    final /* synthetic */ AnonymousClass1 this$1;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$1 = this$1;
                    }

                    public void onNetworkResponse(VolleyRequest<RedPacketBean> request, RedPacketBean result, DataHull hull, NetworkResponseState state) {
                        super.onNetworkResponse(request, result, hull, state);
                        if (result != null) {
                            LogInfo.log("YDD", "=====分享成功回调=====" + result.code);
                        }
                    }
                }).add();
            }
        };
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.pay_success);
        findView();
        init();
        updateUI();
        setResult(257);
        getRedPackage();
    }

    public static void launch(Activity context, String pname, String expire, String price, String pdescription, String orderId, boolean isFromAlipayContinuePay) {
        Intent intent = new Intent(context, PaySucceedActivity.class);
        intent.putExtra("pname", pname);
        intent.putExtra("expire", expire);
        intent.putExtra("price", price);
        intent.putExtra("payType", pdescription);
        intent.putExtra("alipayContinuePay", isFromAlipayContinuePay);
        intent.putExtra("orderId", orderId);
        context.startActivityForResult(intent, 17);
    }

    public static void launch(Activity context, String pname, String expire, String price, String pdescription, String orderId) {
        Intent intent = new Intent(context, PaySucceedActivity.class);
        intent.putExtra("pname", pname);
        intent.putExtra("expire", expire);
        intent.putExtra("price", price);
        intent.putExtra("payType", pdescription);
        intent.putExtra("orderId", orderId);
        context.startActivityForResult(intent, 17);
    }

    public static void launch(Activity context, String pname, String expire, String price, String pdescription, PaySuccessType mPaySuccessType, String activityName, String orderId) {
        Intent intent = new Intent(context, PaySucceedActivity.class);
        intent.putExtra("pname", pname);
        intent.putExtra("expire", expire);
        intent.putExtra("price", price);
        intent.putExtra("payType", pdescription);
        intent.putExtra("orderId", orderId);
        context.startActivityForResult(intent, 17);
    }

    private void getRedPackage() {
        LogInfo.log("YDD", "===OrderId===" + this.mOrderId);
        new LetvRequest(RedPacketBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(LetvUrlMaker.getPopRedPackageUrl(this.mOrderId)).setParser(new RedPacketParser()).setTag("get_red_package").setCallback(new SimpleResponse<RedPacketBean>(this) {
            final /* synthetic */ PaySucceedActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<RedPacketBean> request, RedPacketBean result, DataHull hull, NetworkResponseState state) {
                super.onNetworkResponse(request, result, hull, state);
                if (result != null) {
                    this.this$0.mRedPacketBean = result;
                    LogInfo.log("YDD", "===content===" + result.shareDesc + "===code===" + result.code);
                    if ("1".equals(result.code)) {
                        if (!this.this$0.getActivity().isFinishing()) {
                            PaySucceedActivity.mShowRedPackage = true;
                            this.this$0.mDialog = new RedPacketDialog(this.this$0, 2131230954, false, result, new 1(this), new 2(this));
                            this.this$0.mDialog.setCurrentPageId(PageIdConstant.woOrderSuccessPage);
                            this.this$0.mDialog.getWindow().setGravity(49);
                            this.this$0.mDialog.show();
                            this.this$0.mDialog.setOnDismissListener(new 3(this));
                        }
                    } else if (!"0".equals(result.code)) {
                    }
                }
            }
        }).add();
    }

    protected void onDestroy() {
        super.onDestroy();
        Volley.getQueue().cancelWithTag("get_red_package");
        mShowRedPackage = false;
        this.mPointMeSendRedPackageLayout.setVisibility(8);
    }

    protected void onResume() {
        super.onResume();
        getUserByToken();
        if (mShowRedPackage && !this.mDialog.isShowing()) {
            this.mPointMeSendRedPackageLayout.setVisibility(0);
        }
    }

    private void getUserByToken() {
        LogInfo.log("+-->", "Letv--->>>getUserByToken");
        RequestUserByTokenTask.getUserByTokenTask(getActivity(), PreferencesManager.getInstance().getSso_tk(), new SimpleResponse<UserBean>(this) {
            final /* synthetic */ PaySucceedActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onCacheResponse(VolleyRequest<UserBean> request, UserBean result, DataHull hull, CacheResponseState state) {
                super.onCacheResponse(request, result, hull, state);
                if (PreferencesManager.getInstance().isVip()) {
                    LetvUtils.sendBroadcast(this.this$0.getActivity(), VipProductContant.ACTION_VIP_AUTH_PASS);
                }
            }
        });
    }

    private void findView() {
        this.mPnameView = (TextView) findViewById(R.id.pay_result_pname);
        this.mPriceView = (TextView) findViewById(R.id.pay_result_price);
        this.mLookConsume = (Button) findViewById(R.id.btn_look_consume);
        this.mSeeMovieBtn = (Button) findViewById(R.id.btn_see_movie);
        this.mDescriptionTv = (TextView) findViewById(R.id.pay_result_description);
        this.mBackBtn = (Button) findViewById(R.id.pay_success_back_btn);
        this.mBackGoOnSeeMovieBtn = (Button) findViewById(R.id.btn_back_see_movie);
        this.mBackJoinActivityBtn = (Button) findViewById(R.id.btn_back_join_activity);
        this.mPointMeSendRedPackageLayout = (LinearLayout) findViewById(R.id.pointme_sendredpackage);
        this.mTipTextView = (TextView) findViewById(R.id.hongkong_tip);
        this.mPointMeSendRedPackageLayout.setOnClickListener(this);
        this.mRoot = findViewById(R.id.root);
        mShowRedPackage = false;
        this.mPointMeSendRedPackageLayout.setVisibility(8);
        if (LoginConstant.isHongKong()) {
            this.mTipTextView.setVisibility(0);
        } else {
            this.mTipTextView.setVisibility(8);
        }
    }

    private void init() {
        Intent intent = getIntent();
        this.mPname = intent.getStringExtra("pname");
        this.mExpire = intent.getStringExtra("expire");
        this.mPrice = intent.getStringExtra("price");
        this.mPayType = intent.getStringExtra("payType");
        this.mOrderId = intent.getStringExtra("orderId");
        if (intent.hasExtra("alipayContinuePay")) {
            this.mFromAlipayContinuePay = intent.getBooleanExtra("alipayContinuePay", false);
        }
        this.mLookConsume.setOnClickListener(this);
        this.mSeeMovieBtn.setOnClickListener(this);
        this.mBackBtn.setOnClickListener(this);
        this.mBackGoOnSeeMovieBtn.setOnClickListener(this);
        this.mBackJoinActivityBtn.setOnClickListener(this);
    }

    private void updateUI() {
        this.mPnameView.setText(getResources().getString(2131100583, new Object[]{this.mPname}));
        String price = this.mPrice.contains(getString(2131100233)) ? this.mPrice : LetvUtils.formatDoubleNum(Double.valueOf(this.mPrice).doubleValue(), 2);
        this.mPriceView.setText(getResources().getString(2131100584, new Object[]{price}));
        this.mDescriptionTv.setText(getString(2131100582, new Object[]{this.mPayType}));
        switch (VipProductContant.getPaySuccessType()) {
            case NORMAL:
                this.mSeeMovieBtn.setVisibility(0);
                this.mLookConsume.setVisibility(0);
                return;
            case PLAYER:
                this.mBackGoOnSeeMovieBtn.setVisibility(0);
                this.mBackGoOnSeeMovieBtn.setText(getString(2131100581, new Object[]{VipProductContant.getVideoTitle()}));
                this.mLookConsume.setVisibility(0);
                return;
            case H5ACTIVITY:
                this.mSeeMovieBtn.setVisibility(0);
                this.mBackJoinActivityBtn.setVisibility(0);
                this.mBackJoinActivityBtn.setText(getString(2131100580, new Object[]{VipProductContant.getH5ActivityTitle()}));
                this.mBackJoinActivityBtn.setOnClickListener(this);
                return;
            default:
                return;
        }
    }

    public void onClick(View v) {
        VipProductContant.setPaySuccessType(PaySuccessType.NORMAL);
        switch (v.getId()) {
            case R.id.pay_success_back_btn /*2131363976*/:
                setResult(257);
                finish();
                return;
            case R.id.btn_see_movie /*2131363981*/:
                Channel channel = UIControllerUtils.getVipChannel(BaseApplication.getInstance());
                if (channel != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("channel", channel);
                    bundle.putInt("from", 0);
                    Intent intent = new Intent();
                    intent.putExtra("tag", FragmentConstant.TAG_FRAGMENT_CHANNEL_DETAIL);
                    intent.putExtra(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA, bundle);
                    intent.setClass(this.mContext, MainActivity.class);
                    this.mContext.startActivity(intent);
                    return;
                }
                return;
            case R.id.btn_back_see_movie /*2131363982*/:
                LetvUtils.sendBroadcast(this.mContext, VipProductContant.ACTION_VIP_AUTH_PASS);
                if (this.mFromAlipayContinuePay) {
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mContext)));
                    return;
                }
                setResult(257);
                finish();
                return;
            case R.id.btn_back_join_activity /*2131363983*/:
                new LetvWebViewActivityConfig(this).launch(VipProductContant.getH5ActivityUrl(), VipProductContant.getH5ActivityTitle(), true, false);
                finish();
                return;
            case R.id.btn_look_consume /*2131363984*/:
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new ConsumeRecordActivityConfig(this)));
                setResult(257);
                finish();
                return;
            case R.id.pointme_sendredpackage /*2131363985*/:
                this.mPointMeSendRedPackageLayout.setVisibility(8);
                mShowRedPackage = true;
                pointMeSendRedPackage();
                statistics("su03", 1);
                return;
            default:
                return;
        }
    }

    private void pointMeSendRedPackage() {
        if (this.mHalfPlaySharePopwindow == null) {
            ShareUtils.RequestShareLink(this);
            if (this.mRedPacketBean != null) {
                this.mHalfPlaySharePopwindow = new HalfPlaySharePopwindow(this, 8, this.mRedPacketBean.title, this.mRedPacketBean.url, LetvUrlMaker.getSharedSucceedUrl(this.mRedPacketBean.channelId + "", this.mOrderId), this.mRedPacketBean.mobilePic, this.mRedPacketBean.shareDesc, this.mGiftShareAwardCallback);
                this.mHalfPlaySharePopwindow.showPopupWindow(this.mRoot);
                this.mHalfPlaySharePopwindow.setOnDismissListener(new OnDismissListener(this) {
                    final /* synthetic */ PaySucceedActivity this$0;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$0 = this$0;
                    }

                    public void onDismiss() {
                        this.this$0.mHalfPlaySharePopwindow = null;
                    }
                });
            }
        }
    }

    public String getActivityName() {
        return PaySucceedActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }

    private void statistics(String fl, int wz) {
        if (this.mRedPacketBean != null) {
            StatisticsUtils.statisticsActionInfo(this, PageIdConstant.woOrderSuccessPage, "0", fl, null, wz, "hbid=" + this.mRedPacketBean.channelId);
        }
    }

    public void showRedPackage(boolean flag) {
        mShowRedPackage = false;
        if (flag) {
            LogInfo.log("YDD", "===WX==回调显示" + flag);
            this.mPointMeSendRedPackageLayout.setVisibility(0);
        }
    }

    protected void onPause() {
        super.onPause();
        if (this.mDialog != null && this.mDialog.isShowing()) {
            this.mShowDialogFlag = true;
        }
    }

    protected void onRestart() {
        super.onRestart();
        if (this.mShowDialogFlag) {
            this.mShowDialogFlag = false;
            this.mDialog.show();
        }
    }
}
