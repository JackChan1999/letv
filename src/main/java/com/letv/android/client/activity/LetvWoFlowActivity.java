package com.letv.android.client.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.utils.UIControllerUtils;
import com.letv.android.client.task.RequestLetvWoOpenAnsyTask;
import com.letv.android.client.task.RequestUserByTokenTask;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.android.wo.ex.IWoUtil;
import com.letv.android.wo.ex.WoInterface.LetvWoFlowListener;
import com.letv.component.player.http.parser.BaseParser.CODE_VALUES;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.UserCenterApi;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.LetvOrderInfo;
import com.letv.core.bean.LetvOrderInfo.Data;
import com.letv.core.bean.UserBean;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.LetvOrderInfoParse;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.sina.weibo.sdk.component.ShareRequestParam;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LetvWoFlowActivity extends LetvBaseActivity {
    private static final int SHOW_NOT_UNORDER = 9;
    private static final int SHOW_ORDER = 1;
    private static final int SHOW_ORDER_ALREADY = 5;
    private static final int SHOW_ORDER_FAIL = 3;
    private static final int SHOW_ORDER_NOT_SAME_VIP = 8;
    private static final int SHOW_ORDER_SUCCESS = 2;
    private static final int SHOW_SEE_FILM = 4;
    private static final int SHOW_UN_ORDER_FAIL = 7;
    private static final int SHOW_UN_ORDER_SUCCESS = 6;
    public static OnWoActivityLoginSuccessListener onLoginSuccessListener;
    private ImageView back;
    private String deadTime;
    private OnClickListener mBackListener;
    private RelativeLayout mGoSeeVideo;
    private TextView mGoSeeVideoButton;
    private OnClickListener mGoSeeVideoListener;
    private Handler mHandler;
    private TextView mNotUnorder;
    private TextView mOrderButton;
    private OnClickListener mOrderListener;
    private RelativeLayout mOrderNotSameVip;
    private TextView mOrderNotSameVipExit;
    private TextView mOrderNotSameVipInfo;
    private TextView mOrderPhoneNum;
    private TextView mOrderSuccessPhoneNum;
    private TextView mOrderSuccessUnOrderButton;
    private OnClickListener mOrderSuccessUnOrderListener;
    private TextView mOrderText;
    private TextView mOrderYewufangwei;
    private TextView mOrder_already;
    private TextView mOrder_fail;
    private RelativeLayout mOrder_success;
    private RelativeLayout mOreder;
    private TextView mUnOrderText;
    private TextView mUnOrderYewufangwei;
    private TextView mUnOrder_fail;
    private TextView mUnOrder_success;
    private String phoneNum;
    private boolean unOrder;
    private String username;
    private String yewofanwei;
    private String yewotiaokuan;

    public interface OnWoActivityLoginSuccessListener {
        void loginSuccess();
    }

    public LetvWoFlowActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.phoneNum = "131****4843";
        this.deadTime = "2016-4-16";
        this.unOrder = false;
        this.mHandler = new Handler(this) {
            final /* synthetic */ LetvWoFlowActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        this.this$0.showOrderUI();
                        return;
                    case 2:
                        this.this$0.showOrderSuccessUI();
                        return;
                    case 3:
                        this.this$0.showOrderFailUI();
                        return;
                    case 4:
                        this.this$0.showSeeFilmUI();
                        return;
                    case 5:
                        this.this$0.showOrderAlreadyUI();
                        return;
                    case 6:
                        this.this$0.showUnOrderSuccessUI();
                        return;
                    case 7:
                        this.this$0.showUnOrderFailUI();
                        return;
                    case 8:
                        this.this$0.showOrderNotSameVipUI();
                        return;
                    case 9:
                        this.this$0.showNotUnOrder();
                        return;
                    default:
                        return;
                }
            }
        };
        this.mBackListener = new OnClickListener(this) {
            final /* synthetic */ LetvWoFlowActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View view) {
                this.this$0.finish();
            }
        };
        this.mOrderSuccessUnOrderListener = new OnClickListener(this) {
            final /* synthetic */ LetvWoFlowActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View view) {
                if (NetworkUtils.getNetworkType() == 0) {
                    ToastUtils.showToast(this.this$0, 2131101181);
                } else {
                    ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this.this$0.getActivity(), JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).showUnOrderSureDialog(this.this$0.getActivity(), new LetvWoFlowListener(this) {
                        final /* synthetic */ AnonymousClass4 this$1;

                        {
                            if (HotFix.PREVENT_VERIFY) {
                                System.out.println(VerifyLoad.class);
                            }
                            this.this$1 = this$1;
                        }

                        public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                            if (this.this$1.this$0.getActivity() != null) {
                                if (isUnOrderSure) {
                                    RequestLetvWoOpenAnsyTask requestLetvWoOpenAnsyTask = new RequestLetvWoOpenAnsyTask(this.this$1.this$0.getActivity(), false);
                                    this.this$1.this$0.mHandler.sendEmptyMessage(6);
                                    return;
                                }
                                this.this$1.this$0.mHandler.sendEmptyMessage(7);
                            }
                        }
                    });
                }
            }
        };
        this.mOrderListener = new OnClickListener(this) {
            final /* synthetic */ LetvWoFlowActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View view) {
                if (NetworkUtils.getNetworkType() == 0) {
                    ToastUtils.showToast(this.this$0, 2131101181);
                    return;
                }
                StatisticsUtils.staticticsInfoPost(this.this$0, "0", "h51", null, 1, null, PageIdConstant.woOrderPage, null, null, null, null, null);
                if (PreferencesManager.getInstance().isLogin()) {
                    this.this$0.getRequestLetvOpenAnsyTask();
                    return;
                }
                LetvLoginActivity.launch(18, this.this$0);
                LetvWoFlowActivity.setOnWoActivityLoginSuccessListener(new OnWoActivityLoginSuccessListener(this) {
                    final /* synthetic */ AnonymousClass7 this$1;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$1 = this$1;
                    }

                    public void loginSuccess() {
                        this.this$1.this$0.getRequestLetvOpenAnsyTask();
                    }
                });
            }
        };
        this.mGoSeeVideoListener = new OnClickListener(this) {
            final /* synthetic */ LetvWoFlowActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View view) {
                Channel channel = UIControllerUtils.getVipChannel(BaseApplication.getInstance());
                if (channel != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("channel", channel);
                    bundle.putInt("from", 0);
                    Intent intent = new Intent();
                    intent.putExtra("tag", FragmentConstant.TAG_FRAGMENT_CHANNEL_DETAIL);
                    intent.putExtra(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA, bundle);
                    intent.setClass(this.this$0.mContext, MainActivity.class);
                    this.this$0.mContext.startActivity(intent);
                }
                this.this$0.finish();
            }
        };
    }

    public static void launchOrderActivity(Context context) {
        Intent intent = new Intent(context, LetvWoFlowActivity.class);
        intent.putExtra("unorder", false);
        context.startActivity(intent);
    }

    public static void launchUnOrderActivity(Context context) {
        Intent intent = new Intent(context, LetvWoFlowActivity.class);
        intent.putExtra("unorder", true);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.letv_wo_flow_layout);
        findView();
        Intent intent = getIntent();
        if (intent != null) {
            this.unOrder = intent.getExtras().getBoolean("unorder", false);
            LogInfo.log("ZSM", "去退订 unOrder == " + this.unOrder);
        }
        showUI();
    }

    public void findView() {
        this.back = (ImageView) findViewById(R.id.letv_wo_back_iv);
        this.mOrder_already = (TextView) findViewById(R.id.wo_order_success);
        this.mOrder_fail = (TextView) findViewById(R.id.wo_order_fail);
        this.mOrder_success = (RelativeLayout) findViewById(R.id.wo_order_info);
        this.mUnOrder_success = (TextView) findViewById(R.id.wo_un_order_success);
        this.mUnOrder_fail = (TextView) findViewById(R.id.wo_un_order_fail);
        this.mOrderSuccessPhoneNum = (TextView) findViewById(R.id.wo_order_info_one);
        this.mOrderSuccessUnOrderButton = (TextView) findViewById(R.id.letv_wo_order_button);
        this.mNotUnorder = (TextView) findViewById(R.id.wo_not_unorder);
        this.mOreder = (RelativeLayout) findViewById(R.id.wo_un_order_info);
        this.mOrderPhoneNum = (TextView) findViewById(R.id.wo_un_order_info_one);
        this.mOrderText = (TextView) findViewById(R.id.wo_un_order_info_six);
        this.mUnOrderText = (TextView) findViewById(R.id.wo_un_order_info_six_);
        this.mOrderButton = (TextView) findViewById(R.id.letv_wo_un_order_button);
        this.mGoSeeVideo = (RelativeLayout) findViewById(R.id.wo_order_film_layout);
        this.mGoSeeVideoButton = (TextView) findViewById(R.id.wo_order_film_button);
        this.mOrderNotSameVip = (RelativeLayout) findViewById(R.id.wo_order_not_same_layout);
        this.mOrderNotSameVipInfo = (TextView) findViewById(R.id.wo_order_not_same_info);
        this.mOrderYewufangwei = (TextView) findViewById(R.id.wo_order_info_three);
        this.mUnOrderYewufangwei = (TextView) findViewById(R.id.wo_un_order_info_three);
        this.back.setOnClickListener(this.mBackListener);
        this.mOrderSuccessUnOrderButton.setOnClickListener(this.mOrderSuccessUnOrderListener);
        this.mOrderButton.setOnClickListener(this.mOrderListener);
        this.mGoSeeVideoButton.setOnClickListener(this.mGoSeeVideoListener);
    }

    public void showUI() {
        IWoUtil woUtil = (IWoUtil) JarLoader.invokeStaticMethod(JarLoader.loadClass(this, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "util.WoUtil"), "getInstance", null, null);
        this.yewofanwei = woUtil.getWoBusinessScope(this);
        this.yewotiaokuan = woUtil.getWoBusinessTerms(this);
        this.mOrderYewufangwei.setText(getString(2131101211, new Object[]{this.yewofanwei}));
        this.mUnOrderYewufangwei.setText(getString(2131101211, new Object[]{this.yewofanwei}));
        this.mOrderText.setText(Html.fromHtml(this.yewotiaokuan));
        this.mUnOrderText.setText(Html.fromHtml(this.yewotiaokuan));
        final IWoFlowManager woFlowManager = (IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null);
        woFlowManager.isUserOrder(this, new LetvWoFlowListener() {
            public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                if (LetvWoFlowActivity.this != null) {
                    LogInfo.log("ZSM", "showUI == > " + isOrder);
                    LetvWoFlowActivity.this.phoneNum = woFlowManager.getMobilePhoneNumber(LetvWoFlowActivity.this);
                    if (TextUtils.isEmpty(woFlowManager.getPhoneNum(LetvWoFlowActivity.this))) {
                        LogInfo.log("king", "LetvWoFlowActivity.this.finish()");
                        LetvWoFlowActivity.this.finish();
                    }
                    if (!isOrder && LetvWoFlowActivity.this.unOrder) {
                        LogInfo.log("ZSM", "未订购  退订  进入无需退订页");
                        LetvWoFlowActivity.this.mHandler.sendEmptyMessage(9);
                    } else if (isOrder && LetvWoFlowActivity.this.unOrder) {
                        LogInfo.log("ZSM", "已经订购 退订  进入退订页");
                        LetvWoFlowActivity.this.mHandler.sendEmptyMessage(2);
                    } else if (!isOrder || woFlowManager.isUserUnOrder()) {
                        LogInfo.log("ZSM", "未订购  非退订  进入订购页");
                        LetvWoFlowActivity.this.mHandler.sendEmptyMessage(1);
                    } else if (isOrder) {
                        LetvWoFlowActivity.this.mHandler.sendEmptyMessage(4);
                    }
                }
            }
        }, true, true);
    }

    public void showOrderUI() {
        this.mOrder_already.setVisibility(8);
        this.mOrder_fail.setVisibility(8);
        this.mOrder_success.setVisibility(8);
        this.mGoSeeVideo.setVisibility(8);
        this.mUnOrder_success.setVisibility(8);
        this.mUnOrder_fail.setVisibility(8);
        this.mOrderNotSameVip.setVisibility(8);
        this.mNotUnorder.setVisibility(8);
        this.mOreder.setVisibility(0);
        SpannableString sp = new SpannableString(getString(2131101219, new Object[]{this.phoneNum}));
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#ec8e1f")), 7, 18, 34);
        this.mOrderPhoneNum.setText(sp);
        LogInfo.log("ZSM", "showOrderUI staticticsInfoPost");
        StatisticsUtils.staticticsInfoPost(this, "19", "h51", null, -1, null, PageIdConstant.woOrderPage, null, null, null, null, null);
    }

    public void showOrderSuccessUI() {
        this.mOrder_already.setVisibility(8);
        this.mOrder_fail.setVisibility(8);
        this.mOreder.setVisibility(8);
        this.mGoSeeVideo.setVisibility(8);
        this.mUnOrder_success.setVisibility(8);
        this.mUnOrder_fail.setVisibility(8);
        this.mOrderNotSameVip.setVisibility(8);
        this.mNotUnorder.setVisibility(8);
        this.mOrder_success.setVisibility(0);
        SpannableString sp = new SpannableString(getString(2131101210, new Object[]{this.phoneNum, this.phoneNum}));
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#ec8e1f")), 7, 18, 34);
        this.mOrderSuccessPhoneNum.setText(sp);
        StatisticsUtils.staticticsInfoPost(this, "19", null, null, -1, null, PageIdConstant.woOrderSuccessPage, null, null, null, null, null);
    }

    public void showOrderFailUI() {
        this.mOrder_already.setVisibility(8);
        this.mOrder_success.setVisibility(8);
        this.mOreder.setVisibility(8);
        this.mGoSeeVideo.setVisibility(8);
        this.mUnOrder_success.setVisibility(8);
        this.mUnOrder_fail.setVisibility(8);
        this.mOrderNotSameVip.setVisibility(8);
        this.mNotUnorder.setVisibility(8);
        this.mOrder_fail.setVisibility(0);
        StatisticsUtils.staticticsInfoPost(this, "19", null, null, -1, null, PageIdConstant.woOrderFailPage, null, null, null, null, null);
    }

    public void showUnOrderSuccessUI() {
        this.mOrder_already.setVisibility(8);
        this.mOrder_fail.setVisibility(8);
        this.mOreder.setVisibility(8);
        this.mGoSeeVideo.setVisibility(8);
        this.mUnOrder_fail.setVisibility(8);
        this.mOrder_success.setVisibility(8);
        this.mOrderNotSameVip.setVisibility(8);
        this.mNotUnorder.setVisibility(8);
        this.mUnOrder_success.setVisibility(0);
        SpannableString sp = new SpannableString(getString(2131101210, new Object[]{this.phoneNum, this.phoneNum}));
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#ec8e1f")), 7, 18, 34);
        this.mOrderSuccessPhoneNum.setText(sp);
        StatisticsUtils.staticticsInfoPost(this, "19", null, null, -1, null, PageIdConstant.woUnOrderSuccessPage, null, null, null, null, null);
    }

    public void showUnOrderFailUI() {
        this.mOrder_already.setVisibility(8);
        this.mOrder_success.setVisibility(8);
        this.mOreder.setVisibility(8);
        this.mGoSeeVideo.setVisibility(8);
        this.mUnOrder_success.setVisibility(8);
        this.mOrder_fail.setVisibility(8);
        this.mOrderNotSameVip.setVisibility(8);
        this.mNotUnorder.setVisibility(8);
        this.mUnOrder_fail.setVisibility(0);
        StatisticsUtils.staticticsInfoPost(this, "19", null, null, -1, null, PageIdConstant.woUnOrderFailPage, null, null, null, null, null);
    }

    public void showOrderAlreadyUI() {
        this.mOrder_fail.setVisibility(8);
        this.mOrder_success.setVisibility(8);
        this.mOreder.setVisibility(8);
        this.mGoSeeVideo.setVisibility(8);
        this.mUnOrder_success.setVisibility(8);
        this.mUnOrder_fail.setVisibility(8);
        this.mOrderNotSameVip.setVisibility(8);
        this.mNotUnorder.setVisibility(8);
        this.mOrder_already.setVisibility(0);
        long vipCancelTime = PreferencesManager.getInstance().getVipCancelTime();
        this.deadTime = StringUtils.timeString(vipCancelTime) + "";
        if (vipCancelTime <= 0) {
            this.deadTime = addDate(this.deadTime);
            this.mOrder_already.setText(getString(2131101214, new Object[]{this.deadTime}));
            return;
        }
        this.mOrder_already.setText(getString(2131101214, new Object[]{this.deadTime}));
    }

    public void showSeeFilmUI() {
        this.mOrder_fail.setVisibility(8);
        this.mOrder_success.setVisibility(8);
        this.mOreder.setVisibility(8);
        this.mOrder_already.setVisibility(8);
        this.mUnOrder_success.setVisibility(8);
        this.mUnOrder_fail.setVisibility(8);
        this.mOrderNotSameVip.setVisibility(8);
        this.mNotUnorder.setVisibility(8);
        this.mGoSeeVideo.setVisibility(0);
    }

    public void showOrderNotSameVipUI() {
        this.mOrderNotSameVipInfo.setText(getString(2131101213, new Object[]{this.username}));
        this.mOrder_fail.setVisibility(8);
        this.mOrder_success.setVisibility(8);
        this.mOreder.setVisibility(8);
        this.mOrder_already.setVisibility(8);
        this.mUnOrder_success.setVisibility(8);
        this.mUnOrder_fail.setVisibility(8);
        this.mGoSeeVideo.setVisibility(8);
        this.mNotUnorder.setVisibility(8);
        this.mOrderNotSameVip.setVisibility(0);
    }

    public void showNotUnOrder() {
        this.mOrder_fail.setVisibility(8);
        this.mOrder_success.setVisibility(8);
        this.mOreder.setVisibility(8);
        this.mOrder_already.setVisibility(8);
        this.mUnOrder_success.setVisibility(8);
        this.mUnOrder_fail.setVisibility(8);
        this.mGoSeeVideo.setVisibility(8);
        this.mOrderNotSameVip.setVisibility(8);
        this.mNotUnorder.setVisibility(0);
    }

    private void getUserBeanTask() {
        RequestUserByTokenTask.getUserByTokenTask(this, PreferencesManager.getInstance().getSso_tk(), new SimpleResponse<UserBean>(this) {
            final /* synthetic */ LetvWoFlowActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onCacheResponse(VolleyRequest<UserBean> volleyRequest, UserBean result, DataHull hull, CacheResponseState state) {
                if (state == CacheResponseState.SUCCESS) {
                    this.this$0.mHandler.sendEmptyMessage(5);
                }
            }
        });
    }

    private void getRequestLetvOpenAnsyTask() {
        String loginName = PreferencesManager.getInstance().getLoginName();
        if (loginName.equals("")) {
            loginName = PreferencesManager.getInstance().getUserName();
        }
        new LetvRequest(LetvOrderInfo.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(UserCenterApi.requestLetvOrderInfo(0, ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).getPhoneNum(this), PreferencesManager.getInstance().getUserId(), loginName, "0", "unicom", LetvApplication.getInstance().getIp().clientIp)).setCache(new VolleyNoCache()).setParser(new LetvOrderInfoParse()).setCallback(new SimpleResponse<LetvOrderInfo>(this) {
            final /* synthetic */ LetvWoFlowActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<LetvOrderInfo> volleyRequest, LetvOrderInfo result, DataHull hull, NetworkResponseState state) {
                switch (state) {
                    case SUCCESS:
                        if (result == null) {
                            ToastUtils.showToast(this.this$0.getActivity(), this.this$0.getActivity().getString(2131101170));
                            return;
                        } else if (result.code.equals(CODE_VALUES.SUCCESS)) {
                            Data data = result.data;
                            if (data != null && data.canorder.equals("1")) {
                                ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this.this$0, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).showOrderSureDialog(this.this$0, new 1(this));
                                return;
                            } else if (data != null && data.canorder.equals("2")) {
                                this.this$0.woVipCancelDialog(this.this$0);
                                return;
                            } else if (data != null && data.canorder.equals("3")) {
                                this.this$0.mHandler.sendEmptyMessage(4);
                                return;
                            } else if (data != null && data.canorder.equals("4")) {
                                this.this$0.username = data.username;
                                this.this$0.mHandler.sendEmptyMessage(8);
                                return;
                            } else if (data != null && data.canorder.equals("99")) {
                                ToastUtils.showToast(this.this$0.getActivity(), 2131101185);
                                return;
                            } else if (data == null) {
                                ToastUtils.showToast(this.this$0.getActivity(), 2131101185);
                                return;
                            } else {
                                return;
                            }
                        } else {
                            ToastUtils.showToast(this.this$0.getActivity(), 2131101185);
                            return;
                        }
                    default:
                        return;
                }
            }

            public void onErrorReport(VolleyRequest<LetvOrderInfo> request, String errorInfo) {
                LogInfo.log("ZSM", "mineListRequestTask onErrorReport == " + errorInfo);
                super.onErrorReport(request, errorInfo);
            }
        }).add();
    }

    public static void setOnWoActivityLoginSuccessListener(OnWoActivityLoginSuccessListener listener) {
        onLoginSuccessListener = listener;
    }

    protected void onStop() {
        super.onStop();
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return LetvWoFlowActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }

    public static String addDate(String time) {
        Calendar objCalendar = Calendar.getInstance();
        String[] arry = time.split(com.letv.pp.utils.NetworkUtils.DELIMITER_LINE);
        int nian = 0;
        int yue = 0;
        int ri = 0;
        if (arry != null && arry.length == 3) {
            nian = Integer.parseInt(arry[0]);
            yue = Integer.parseInt(arry[1]);
            ri = Integer.parseInt(arry[2]);
        }
        if (nian == 1970 || time.equals("0") || time.equals("")) {
            String[] infos = BaseApplication.getInstance().getLiveDateInfo().date.split(" ");
            if (infos != null && infos.length >= 1) {
                arry = infos[0].split(com.letv.pp.utils.NetworkUtils.DELIMITER_LINE);
                nian = Integer.parseInt(arry[0]);
                yue = Integer.parseInt(arry[1]);
                ri = Integer.parseInt(arry[2]);
            }
        }
        objCalendar.set(nian, yue - 1, ri);
        objCalendar.add(5, 31);
        Date objDate = new Date();
        return new SimpleDateFormat("yyyy-MM-dd").format(objCalendar.getTime());
    }

    public void woVipCancelDialog(Context context) {
        if (context != null) {
            try {
                final Dialog dialog = new Builder(context).create();
                View view = LayoutInflater.from(context).inflate(2130903568, null);
                view.setOnTouchListener(new OnTouchListener(this) {
                    final /* synthetic */ LetvWoFlowActivity this$0;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$0 = this$0;
                    }

                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                ((TextView) view.findViewById(2131364542)).setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ LetvWoFlowActivity this$0;

                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getWindow().setContentView(view);
                dialog.setCanceledOnTouchOutside(false);
            } catch (Exception e) {
            }
        }
    }
}
