package com.letv.lepaysdk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.VType;
import com.letv.lepaysdk.ELePayState;
import com.letv.lepaysdk.LePay;
import com.letv.lepaysdk.LePayConfig;
import com.letv.lepaysdk.alipay.AliPay;
import com.letv.lepaysdk.alipay.AliPayCallback;
import com.letv.lepaysdk.alipay.AliPayResult;
import com.letv.lepaysdk.model.Paymodes;
import com.letv.lepaysdk.model.Result.ResultConstant;
import com.letv.lepaysdk.model.TradeInfo;
import com.letv.lepaysdk.network.CardPayHelper;
import com.letv.lepaysdk.network.LePaySDKException;
import com.letv.lepaysdk.utils.BitmapUtils;
import com.letv.lepaysdk.utils.LOG;
import com.letv.lepaysdk.utils.ResourceUtil;
import com.letv.lepaysdk.utils.StringUtil;
import com.letv.lepaysdk.utils.ThreadUtil;
import com.letv.lepaysdk.utils.ThreadUtil.IThreadTask;
import com.letv.lepaysdk.utils.ToastUtils;
import com.letv.lepaysdk.view.LePayActionBar;
import com.letv.lepaysdk.wxpay.WXPay;
import com.letv.lepaysdk.wxpay.WXPayCallback;
import com.letv.pp.utils.NetworkUtils;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.umeng.analytics.a;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONObject;

public class CashierAcitivity extends BaseActivity {
    public static final String PaymodesTAG = "PaymodesTAG";
    public static final String TradeinfoTAG = "TradeinfoTAG";
    private CardPayHelper cardPayHelper;
    private LePayConfig config;
    private Context context;
    private TextView lepay_cashier_moeny;
    private TextView lepay_cashier_next;
    private LinearLayout lepay_cashier_paytype_list;
    private TextView lepay_cashier_trade_exp;
    private TextView lepay_cashier_trade_no;
    private LePayActionBar mActionBar;
    private AliPay mAliPay;
    private LayoutInflater mLayoutInflater;
    private MessageCountTimer mMessageTimer;
    private WXPay mWxPay;
    private Paymodes paymodes;
    private ProgressBar progressBar;
    private RelativeLayout rl_leypay_ok;
    private String tradeNo;

    private class MessageCountTimer extends CountDownTimer {
        public MessageCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            ToastUtils.makeText(CashierAcitivity.this.context, "订单已失效");
        }

        public void onTick(long millisUntilFinished) {
            long myhour = (millisUntilFinished / 1000) / 3600;
            long myminute = ((millisUntilFinished / 1000) - (3600 * myhour)) / 60;
            long mysecond = ((millisUntilFinished / 1000) - (3600 * myhour)) - (60 * myminute);
            StringBuffer sb = new StringBuffer();
            if (myhour > 9) {
                sb.append(myhour);
            } else {
                sb.append(myhour < 0 ? "00" : "0" + myhour);
            }
            sb.append(NetworkUtils.DELIMITER_COLON);
            if (myminute > 9) {
                sb.append(myminute);
            } else {
                sb.append("0" + myminute);
            }
            sb.append(NetworkUtils.DELIMITER_COLON);
            if (mysecond > 9) {
                sb.append(mysecond);
            } else {
                sb.append("0" + mysecond);
            }
            CashierAcitivity.this.reInitTime(sb.toString());
        }
    }

    class PaymodesComparator implements Comparator<Paymodes> {
        PaymodesComparator() {
        }

        public int compare(Paymodes lhs, Paymodes rhs) {
            return -lhs.getDisplay().compareTo(rhs.getDisplay());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        checkData();
        initContentView();
        initPayType();
        initView();
        initViewText();
        payCallBacks();
        setlistener();
        startTimer();
        payOrderStatus();
    }

    void checkData() {
        this.tradeNo = getIntent().getStringExtra(TradeInfo.TRADE_NO);
        this.config = (LePayConfig) getIntent().getSerializableExtra(TradeInfo.LEPAY_CONFIG);
    }

    private void initContentView() {
        setContentView(ResourceUtil.getLayoutResource(this.context, "lepay_cashier_acitivity"));
    }

    private void initPayType() {
        this.mAliPay = new AliPay();
        this.mWxPay = WXPay.getInstance(this);
        this.cardPayHelper = new CardPayHelper(this.context);
    }

    private void initView() {
        this.mLayoutInflater = getLayoutInflater();
        this.mActionBar = (LePayActionBar) findViewById(ResourceUtil.getIdResource(this, "lepay_actionbar"));
        this.mActionBar.setLeftButtonVisable(0);
        this.lepay_cashier_trade_no = (TextView) findViewById(ResourceUtil.getIdResource(this.context, "lepay_cashier_trade_no"));
        this.lepay_cashier_trade_exp = (TextView) findViewById(ResourceUtil.getIdResource(this.context, "lepay_cashier_trade_exp"));
        this.lepay_cashier_paytype_list = (LinearLayout) findViewById(ResourceUtil.getIdResource(this.context, "lepay_cashier_paytype_list"));
        this.lepay_cashier_moeny = (TextView) findViewById(ResourceUtil.getIdResource(this.context, "lepay_cashier_moeny"));
        this.lepay_cashier_next = (TextView) findViewById(ResourceUtil.getIdResource(this.context, "lepay_pay_ok"));
        this.progressBar = (ProgressBar) findViewById(ResourceUtil.getIdResource(this.context, "progress"));
        this.rl_leypay_ok = (RelativeLayout) findViewById(ResourceUtil.getIdResource(this.context, "rl_leypay_ok"));
        createViews(hasFilterType());
    }

    private void initViewText() {
        hasShowTimer();
        this.mActionBar.setTitle(getString(ResourceUtil.getStringResource(this.context, "lepay_leshi_pay")));
        this.lepay_cashier_next.setText(ResourceUtil.getStringResource(this.context, "lepay_activity_btn_next"));
        this.lepay_cashier_trade_no.setText(TextUtils.isEmpty(this.tradeNo) ? "" : this.tradeNo);
        reInitTime("0");
        String price = this.mTradeInfo.getPrice();
        TextView textView = this.lepay_cashier_moeny;
        if (TextUtils.isEmpty(price)) {
            price = "0";
        }
        textView.setText(price);
    }

    void reInitTime(String time) {
        this.lepay_cashier_trade_exp.setText(StringUtil.textSpan(this.context, "lepay_pay_reminder", time, "lepay_tv_password_dialog_forget_password_font_color", null, 4));
    }

    private void setlistener() {
        this.mActionBar.setLeftButtonOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                LePay.getInstance(CashierAcitivity.this).finishPay(CashierAcitivity.this.mTradeInfo.getKey(), ELePayState.CANCEL, "用户取消");
                CashierAcitivity.this.finish();
            }
        });
        this.mActionBar.setRightButtonOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
            }
        });
        this.lepay_cashier_next.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Object o = view.getTag();
                if (o == null) {
                    Toast.makeText(CashierAcitivity.this.context, "请选择支付类型", 0).show();
                    return;
                }
                CashierAcitivity.this.onItemSelectedClick(((Integer) o).intValue());
            }
        });
    }

    private void onItemSelectedClick(int position) {
        this.progressBar.setVisibility(0);
        String lepayOrderNo = this.mTradeInfo.getLepay_order_no();
        String merchantBusinessId = this.mTradeInfo.getMerchant_business_id();
        this.paymodes = (Paymodes) this.mTradeInfo.getPaylist().get(position);
        String pay_type = this.paymodes.getPay_type();
        if ("3".equals(pay_type)) {
            toBindedCoardPay(this.paymodes);
        } else if (VType.FLV_1080P6M_3D.equals(pay_type)) {
            toCreditCardPay(this.paymodes);
        } else if ("32".equals(pay_type)) {
            toDeditCardPay(this.paymodes);
        } else if ("1".equals(pay_type)) {
            this.lepay_cashier_next.setClickable(false);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    CashierAcitivity.this.lepay_cashier_next.setClickable(true);
                }
            }, 1000);
            alipayWxPay(this.paymodes, lepayOrderNo, merchantBusinessId);
        } else if ("2".equals(pay_type)) {
            this.lepay_cashier_next.setClickable(false);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    CashierAcitivity.this.lepay_cashier_next.setClickable(true);
                }
            }, 1000);
            alipayWxPay(this.paymodes, lepayOrderNo, merchantBusinessId);
        } else {
            ToastUtils.makeText(this, getString(ResourceUtil.getStringResource(this, "lepay_activity_nonsupport_paymode")));
            LOG.logI("执行其他");
        }
    }

    private boolean hasFilterType() {
        List<Paymodes> payList = this.mTradeInfo.getPaylist();
        int size = payList.size();
        for (int i = 0; i < size; i++) {
            if ("0".equals(((Paymodes) payList.get(i)).getDisplay())) {
                return true;
            }
        }
        return false;
    }

    private List<Paymodes> displayPaymodesList() {
        List<Paymodes> payList = this.mTradeInfo.getPaylist();
        Collections.sort(payList, new PaymodesComparator());
        return payList;
    }

    private void createViews(boolean hasMore) {
        List<Paymodes> payList = displayPaymodesList();
        final int size = payList.size();
        for (int i = 0; i < size; i++) {
            final Paymodes paymodes = (Paymodes) payList.get(i);
            View view = this.mLayoutInflater.inflate(ResourceUtil.getLayoutResource(this.context, "lepay_cashier_paychannel_listitem"), this.lepay_cashier_paytype_list, false);
            final ImageView itemIcon = (ImageView) view.findViewById(ResourceUtil.getIdResource(this.context, "lepay_paychannel_item_icon"));
            TextView itemTitle = (TextView) view.findViewById(ResourceUtil.getIdResource(this.context, "lepay_paychannel_item_title"));
            TextView itemCardNo = (TextView) view.findViewById(ResourceUtil.getIdResource(this.context, "lepay_paychannel_item_cardno"));
            TextView itemDesc = (TextView) view.findViewById(ResourceUtil.getIdResource(this, "lepay_paychannel_item_desc"));
            CheckBox checkbox = (CheckBox) view.findViewById(ResourceUtil.getIdResource(this, "lepay_paychannel_item_checkbox"));
            checkbox.setChecked(false);
            view.setTag(Integer.valueOf(i));
            checkbox.setTag(Boolean.FALSE);
            view.setVisibility(0);
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    int tag = ((Integer) view.getTag()).intValue();
                    CashierAcitivity.this.lepay_cashier_next.setTag(Integer.valueOf(tag));
                    for (int i = 0; i < size; i++) {
                        CheckBox checkbox = (CheckBox) CashierAcitivity.this.lepay_cashier_paytype_list.findViewWithTag(Integer.valueOf(i)).findViewById(ResourceUtil.getIdResource(CashierAcitivity.this, "lepay_paychannel_item_checkbox"));
                        if (i == tag) {
                            checkbox.setChecked(true);
                        } else {
                            checkbox.setChecked(false);
                        }
                    }
                }
            });
            ThreadUtil.exec(new Runnable() {
                public void run() {
                    BitmapUtils.loadBitmap(CashierAcitivity.this.mNetworkManager, CashierAcitivity.this.context, paymodes.getIcon_url(), 40, 40, itemIcon);
                }
            });
            itemTitle.setText(TextUtils.isEmpty(paymodes.getName()) ? "" : paymodes.getName());
            itemCardNo.setVisibility(8);
            itemDesc.setText(paymodes.getDesc());
            if ("0".equals(paymodes.getDisplay())) {
                view.setVisibility(8);
                checkbox.setTag(Boolean.TRUE);
            }
            this.lepay_cashier_paytype_list.addView(view);
        }
        if (this.lepay_cashier_paytype_list.getChildCount() > 0) {
            ((CheckBox) this.lepay_cashier_paytype_list.getChildAt(0).findViewById(ResourceUtil.getIdResource(this, "lepay_paychannel_item_checkbox"))).setChecked(true);
            this.lepay_cashier_next.setTag(Integer.valueOf(0));
        }
        if (hasMore) {
            this.lepay_cashier_paytype_list.addView(inflaterOthersView());
        }
    }

    private View inflaterOthersView() {
        final View othersView = View.inflate(this.context, ResourceUtil.getLayoutResource(this.context, "lepay_cashier_paychannel_other"), null);
        ImageView itemIcon = (ImageView) othersView.findViewById(ResourceUtil.getIdResource(this.context, "lepay_paychannel_item_icon"));
        TextView itemTitle = (TextView) othersView.findViewById(ResourceUtil.getIdResource(this.context, "lepay_paychannel_item_title"));
        final ImageView arrawicon = (ImageView) othersView.findViewById(ResourceUtil.getIdResource(this.context, "lepay_cashier_paytype_other_selector_icon"));
        arrawicon.setImageResource(ResourceUtil.getDrawableResource(this.context, "icon_down"));
        itemIcon.setImageResource(ResourceUtil.getDrawableResource(this.context, "lepay_icon_more"));
        itemTitle.setText(ResourceUtil.getStringResource(this.context, "lepay_ohters_paytype"));
        othersView.setTag(Boolean.FALSE);
        othersView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int count;
                int i;
                if (((Boolean) view.getTag()).booleanValue()) {
                    arrawicon.setImageResource(ResourceUtil.getDrawableResource(CashierAcitivity.this.context, "icon_down"));
                    count = CashierAcitivity.this.lepay_cashier_paytype_list.getChildCount();
                    for (i = 0; i < count; i++) {
                        View v = CashierAcitivity.this.lepay_cashier_paytype_list.getChildAt(i);
                        CheckBox checkBox = (CheckBox) v.findViewById(ResourceUtil.getIdResource(CashierAcitivity.this, "lepay_paychannel_item_checkbox"));
                        if (checkBox != null) {
                            if (((Boolean) checkBox.getTag()).booleanValue()) {
                                v.setVisibility(8);
                            } else {
                                v.setVisibility(0);
                            }
                        }
                    }
                    othersView.setTag(Boolean.FALSE);
                    return;
                }
                arrawicon.setImageResource(ResourceUtil.getDrawableResource(CashierAcitivity.this.context, "icon_up"));
                count = CashierAcitivity.this.lepay_cashier_paytype_list.getChildCount();
                for (i = 0; i < count; i++) {
                    CashierAcitivity.this.lepay_cashier_paytype_list.getChildAt(i).setVisibility(0);
                }
                othersView.setTag(Boolean.TRUE);
            }
        });
        othersView.performClick();
        othersView.performClick();
        return othersView;
    }

    private void payCallBacks() {
        this.mAliPay.setAliPayCallback(new AliPayCallback() {
            public void aliPayCalledBack(String result) {
                LOG.logI("支付宝" + result);
                CashierAcitivity.this.lepay_cashier_next.setClickable(true);
                CashierAcitivity.this.progressBar.setVisibility(8);
                AliPayResult payResult = new AliPayResult(result);
                String resultStatus = payResult.getStaus();
                if (TextUtils.equals(resultStatus, "9000")) {
                    CashierAcitivity.this.hasShowPaySuccess(CashierAcitivity.this.mTradeInfo.getKey(), ELePayState.OK, CashierAcitivity.this.mTradeInfo.getPrice());
                } else if (TextUtils.equals(resultStatus, "4000")) {
                    ToastUtils.makeText(CashierAcitivity.this.context, "支付宝钱包未安装，请下载安装！");
                } else {
                    CashierAcitivity.this.cardPayHelper.showPayStatus(CashierAcitivity.this.mTradeInfo.getKey(), ELePayState.FAILT, payResult.getResult());
                }
            }
        });
        this.mWxPay.setCallback(new WXPayCallback() {
            public void wxPayCallback(BaseResp resp) {
                CashierAcitivity.this.lepay_cashier_next.setClickable(true);
                CashierAcitivity.this.progressBar.setVisibility(8);
                switch (resp.errCode) {
                    case 0:
                        CashierAcitivity.this.hasShowPaySuccess(CashierAcitivity.this.mTradeInfo.getKey(), ELePayState.OK, CashierAcitivity.this.mTradeInfo.getPrice());
                        return;
                    default:
                        CashierAcitivity.this.cardPayHelper.showPayStatus(CashierAcitivity.this.mTradeInfo.getKey(), ELePayState.FAILT, TextUtils.isEmpty(resp.errStr) ? "支付失败" : resp.errStr);
                        return;
                }
            }
        });
    }

    protected void onResume() {
        this.lepay_cashier_next.setClickable(true);
        this.progressBar.setVisibility(8);
        super.onResume();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            hasShowPaySuccess(this.mTradeInfo.getKey(), ELePayState.OK, this.mTradeInfo.getPrice());
        }
    }

    private void alipayWxPay(final Paymodes paymodes, final String lepayOrderNo, final String merchantBusinessId) {
        ThreadUtil.execUi(new IThreadTask() {
            Message msg = new Message();

            public void didCommandFinishInvokeMainThread() {
                if (this.msg.arg1 == 0) {
                    JSONObject o = this.msg.obj;
                    if (o == null) {
                        ToastUtils.makeText(CashierAcitivity.this.context, this.msg.getData().getString(ResultConstant.errormsg));
                        CashierAcitivity.this.progressBar.setVisibility(8);
                        return;
                    }
                    String dContent = URLDecoder.decode(o.optString(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT));
                    String payType = paymodes.getPay_type();
                    if ("1".equals(payType)) {
                        CashierAcitivity.this.mAliPay.pay(CashierAcitivity.this, dContent);
                    } else if (!"2".equals(payType)) {
                        CashierAcitivity.this.progressBar.setVisibility(8);
                        ToastUtils.makeText(CashierAcitivity.this.context, CashierAcitivity.this.getString(ResourceUtil.getStringResource(CashierAcitivity.this, "lepay_activity_wx_no_install")));
                    } else if (!CashierAcitivity.this.mWxPay.isWXAppInstalled()) {
                        CashierAcitivity.this.progressBar.setVisibility(8);
                        ToastUtils.makeText(CashierAcitivity.this.context, "您还没安装微信");
                    } else if (CashierAcitivity.this.mWxPay.isSupportWXPay()) {
                        CashierAcitivity.this.mWxPay.wxpay(dContent);
                    } else {
                        CashierAcitivity.this.progressBar.setVisibility(8);
                        ToastUtils.makeText(CashierAcitivity.this.context, CashierAcitivity.this.getString(ResourceUtil.getStringResource(CashierAcitivity.this, "lepay_activity_wx_versions_nonsupport")));
                    }
                } else if (this.msg.arg1 == 1) {
                    CashierAcitivity.this.progressBar.setVisibility(8);
                    ToastUtils.makeText(CashierAcitivity.this.context, this.msg.getData().getString(ResultConstant.errormsg));
                } else {
                    CashierAcitivity.this.progressBar.setVisibility(8);
                    String string = this.msg.getData().getString(ResultConstant.errormsg);
                    ToastUtils.makeText(CashierAcitivity.this.context, "网络异常");
                }
            }

            public void didCommand() {
                try {
                    this.msg = CashierAcitivity.this.mNetworkManager.createPay(paymodes.getChannel_id(), lepayOrderNo, merchantBusinessId, null, null);
                } catch (LePaySDKException e) {
                    this.msg.arg1 = -1;
                    this.msg.getData().putString(ResultConstant.errormsg, "网络异常");
                    e.printStackTrace();
                }
            }
        });
    }

    void toBindedCoardPay(Paymodes paymodes) {
        Intent intent = new Intent(this.context, BindedCardPayActivity.class);
        intent.putExtra(PaymodesTAG, paymodes);
        intent.putExtra(TradeinfoTAG, this.mTradeInfo);
        startActivityForResult(intent, 1);
    }

    void toCreditCardPay(Paymodes paymodes) {
        Intent intent = new Intent(this.context, CreditCardPayActivity.class);
        intent.putExtra(PaymodesTAG, paymodes);
        intent.putExtra(TradeinfoTAG, this.mTradeInfo);
        startActivityForResult(intent, 2);
    }

    void toDeditCardPay(Paymodes paymodes) {
        Intent intent = new Intent(this.context, DebitCardPayActivity.class);
        intent.putExtra(PaymodesTAG, paymodes);
        intent.putExtra(TradeinfoTAG, this.mTradeInfo);
        startActivityForResult(intent, 3);
    }

    void startTimer() {
        this.mMessageTimer = new MessageCountTimer(a.h, 1000);
        this.mMessageTimer.start();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mMessageTimer != null) {
            this.mMessageTimer.cancel();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            LePay.getInstance(this).finishPay(this.mTradeInfo.getKey(), ELePayState.CANCEL, "用户取消");
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void hasShowTimer() {
        int i;
        int i2 = 0;
        TextView textView = this.lepay_cashier_trade_exp;
        if (this.config.hasShowTimer) {
            i = 0;
        } else {
            i = 8;
        }
        textView.setVisibility(i);
        View view = findViewById(ResourceUtil.getIdResource(this.context, "lepay_icon_line"));
        if (!this.config.hasShowTimer) {
            i2 = 8;
        }
        view.setVisibility(i2);
    }

    private void hasShowPaySuccess(String key, ELePayState ePayStatus, String content) {
        if (this.config.hasShowPaySuccess) {
            this.cardPayHelper.showPayStatus(key, ePayStatus, content);
            return;
        }
        LePay.getInstance(this).finishPay(key, ePayStatus, content);
        setResult(-1);
        finish();
    }

    void payOrderStatus() {
        if (this.mTradeInfo.getOrderstatus() == 1) {
            this.cardPayHelper.showPayStatus(this.mTradeInfo.getKey(), ELePayState.PAYED, "订单已支付，请勿重复支付");
        }
    }
}
