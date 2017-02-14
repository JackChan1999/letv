package com.letv.lepaysdk.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.letv.lepaysdk.ELePayState;
import com.letv.lepaysdk.model.PayCard;
import com.letv.lepaysdk.model.Paymodes;
import com.letv.lepaysdk.model.Result.ResultConstant;
import com.letv.lepaysdk.model.TradeInfo;
import com.letv.lepaysdk.network.CardPayHelper;
import com.letv.lepaysdk.task.TaskListener;
import com.letv.lepaysdk.task.TaskResult;
import com.letv.lepaysdk.utils.LOG;
import com.letv.lepaysdk.utils.ResourceUtil;
import com.letv.lepaysdk.utils.StringUtil;
import com.letv.lepaysdk.utils.ToastUtils;
import com.letv.lepaysdk.view.ClearEditText;
import com.letv.lepaysdk.view.ClearEditText.OnTextChangedListener;
import com.letv.lepaysdk.view.LePayActionBar;
import com.letv.lepaysdk.view.MontmorilloniteLayer;
import org.json.JSONObject;

public class BindedCardPayActivity extends BaseActivity implements OnClickListener {
    private String bankcode;
    private String bankname;
    Context context;
    final Handler handler = new Handler();
    boolean hasToast = true;
    private Button lepay_bt_checkcode;
    private TextView lepay_cashier_moeny;
    private ClearEditText lepay_et_checkcode;
    private LinearLayout lepay_ll_parent;
    private TextView lepay_pay_ok;
    private MontmorilloniteLayer lepay_payload_layer;
    private TextView lepay_tv_checkcode_hint;
    private TextView lepay_tv_sendmsg_number;
    private LePayActionBar mActionBar;
    private MessageCountTimer mMessageTimer;
    CardPayHelper payHelper;
    private Paymodes paymodes;
    private ProgressBar progress;
    int runCount = 0;
    private String sendby;

    private class MessageCountTimer extends CountDownTimer {
        public MessageCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            BindedCardPayActivity.this.lepay_bt_checkcode.setClickable(true);
            BindedCardPayActivity.this.lepay_bt_checkcode.setText(ResourceUtil.getStringResource(BindedCardPayActivity.this.context, "lepay_creditCards_getcheckcode"));
            BindedCardPayActivity.this.lepay_bt_checkcode.setBackgroundResource(ResourceUtil.getDrawableResource(BindedCardPayActivity.this.context, "lepay_count_sms"));
        }

        public void onTick(long millisUntilFinished) {
            BindedCardPayActivity.this.lepay_bt_checkcode.setBackgroundResource(ResourceUtil.getDrawableResource(BindedCardPayActivity.this.context, "lepay_count_sms_gray"));
            BindedCardPayActivity.this.lepay_bt_checkcode.setText((millisUntilFinished / 1000) + "秒后继续");
            BindedCardPayActivity.this.lepay_bt_checkcode.setClickable(false);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        checkData();
        initCardPayHelper();
        initContentView();
        initView();
        initText();
    }

    void checkData() {
        this.paymodes = (Paymodes) getIntent().getSerializableExtra(CashierAcitivity.PaymodesTAG);
        this.mTradeInfo = (TradeInfo) getIntent().getSerializableExtra(CashierAcitivity.TradeinfoTAG);
        if (this.paymodes == null || this.mTradeInfo == null) {
            LOG.logI("参数错误");
            onBackPressed();
        }
    }

    void initContentView() {
        setContentView(ResourceUtil.getLayoutResource(this.context, "leypay_bindedcard_pay_activity"));
    }

    void initView() {
        this.lepay_payload_layer = (MontmorilloniteLayer) findViewById(ResourceUtil.getIdResource(this, "lepay_payload_layer"));
        this.lepay_ll_parent = (LinearLayout) findViewById(ResourceUtil.getIdResource(this, "lepay_ll_parent"));
        this.lepay_et_checkcode = (ClearEditText) findViewById(ResourceUtil.getIdResource(this, "lepay_et_checkcode"));
        this.lepay_tv_checkcode_hint = (TextView) findViewById(ResourceUtil.getIdResource(this, "lepay_tv_checkcode_hint"));
        this.lepay_bt_checkcode = (Button) findViewById(ResourceUtil.getIdResource(this, "lepay_bt_checkcode"));
        this.lepay_tv_sendmsg_number = (TextView) findViewById(ResourceUtil.getIdResource(this, "lepay_tv_sendmsg_number"));
        this.lepay_pay_ok = (TextView) findViewById(ResourceUtil.getIdResource(this, "lepay_pay_ok"));
        this.lepay_cashier_moeny = (TextView) findViewById(ResourceUtil.getIdResource(this, "lepay_cashier_moeny"));
        this.progress = (ProgressBar) findViewById(ResourceUtil.getIdResource(this, "progress"));
        this.mActionBar = (LePayActionBar) findViewById(ResourceUtil.getIdResource(this.context, "lepay_actionbar"));
        this.mActionBar.setLeftButtonVisable(0);
        this.mActionBar.setLeftButtonOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                BindedCardPayActivity.this.onBackPressed();
            }
        });
        this.lepay_bt_checkcode.setOnClickListener(this);
        this.lepay_pay_ok.setOnClickListener(this);
        this.lepay_et_checkcode.setOnTextChangedListener(new OnTextChangedListener() {
            public void onTextChanged(String s) {
                if (TextUtils.isEmpty(s) || "".equals(s.trim())) {
                    BindedCardPayActivity.this.lepay_tv_checkcode_hint.setText("请输入验证码");
                    BindedCardPayActivity.this.lepay_tv_checkcode_hint.setVisibility(0);
                    return;
                }
                BindedCardPayActivity.this.lepay_tv_checkcode_hint.setVisibility(4);
            }

            public void onFocusChange(boolean hasFocus) {
            }
        });
    }

    void initText() {
        this.mActionBar.setTitle(getString(ResourceUtil.getStringResource(this.context, "lepay_leshi_fastpay")));
        this.lepay_tv_sendmsg_number.setText(StringUtil.textSpan(this.context, "lepay_pay_sendmsg_nubmer_hint", TextUtils.isEmpty(this.paymodes.getPhone_no()) ? "" : this.paymodes.getPhone_no(), "cF45353", null, 4));
        this.lepay_pay_ok.setText(ResourceUtil.getStringResource(this.context, "lepay_activity_btn_okpay"));
        String price = this.mTradeInfo.getPrice();
        TextView textView = this.lepay_cashier_moeny;
        if (TextUtils.isEmpty(price)) {
            price = "";
        }
        textView.setText(price);
        checkCode(createGetVerifyCodeParams());
    }

    void initCardPayHelper() {
        this.payHelper = new CardPayHelper(this.context);
    }

    void checkCode(PayCard payCard) {
        this.payHelper.checkCode(payCard, new TaskListener<String>() {
            public void onFinish(TaskResult<String> result) {
                if (result.isOk()) {
                    BindedCardPayActivity.this.startTimer();
                    BindedCardPayActivity.this.sendby = (String) result.getResult();
                    return;
                }
                ToastUtils.makeText(BindedCardPayActivity.this.context, result.getDesc());
            }

            public void onPreExcuete() {
            }
        });
    }

    void showPayingMode() {
        visablePanel(true);
    }

    void pay() {
        queryVerifyCode(this.mTradeInfo.getLepay_order_no(), this.mTradeInfo.getMerchant_business_id(), this.paymodes.getPhone_no(), this.lepay_et_checkcode.getText().toString());
        this.hasToast = true;
        this.runCount = 0;
    }

    void queryVerifyCode(final String lepay_order_no, final String merchant_business_id, String phone, final String verifycode) {
        this.payHelper.queryVerifyCode(lepay_order_no, merchant_business_id, phone, verifycode, this.sendby, new TaskListener<Void>() {
            public void onFinish(TaskResult<Void> result) {
                if (result.isOk()) {
                    BindedCardPayActivity.this.validPayOk(BindedCardPayActivity.this.paymodes.getChannel_id(), lepay_order_no, merchant_business_id, verifycode);
                    return;
                }
                ToastUtils.makeText(BindedCardPayActivity.this.context, result.getDesc());
                BindedCardPayActivity.this.visablePanel(false);
            }

            public void onPreExcuete() {
            }
        });
    }

    void validPayOk(String channel_id, String lepay_order_no, String merchant_business_id, String verifycode) {
        this.payHelper.pay(channel_id, lepay_order_no, merchant_business_id, verifycode, this.sendby, new TaskListener<JSONObject>() {
            public void onFinish(TaskResult<JSONObject> result) {
                if (result.isOk()) {
                    JSONObject jsonObject = (JSONObject) result.getResult();
                    String lepay_payment_no = jsonObject.optString("lepay_payment_no");
                    BindedCardPayActivity.this.validOrderState(lepay_payment_no, jsonObject.optString("merchant_business_id"), lepay_payment_no);
                    return;
                }
                BindedCardPayActivity.this.visablePanel(false);
                BindedCardPayActivity.this.payHelper.showPayStatus(BindedCardPayActivity.this.mTradeInfo.getKey(), ELePayState.FAILT, result.getDesc());
            }

            public void onPreExcuete() {
            }
        });
    }

    void validOrderState(final String lepay_order_no, final String merchant_business_id, final String lepayPaymentNo) {
        this.payHelper.queryOrderState(lepay_order_no, merchant_business_id, lepayPaymentNo, new TaskListener<Bundle>() {
            public void onFinish(TaskResult<Bundle> result) {
                if (result.isOk()) {
                    BindedCardPayActivity.this.visablePanel(false);
                    BindedCardPayActivity.this.setResult(-1);
                    BindedCardPayActivity.this.finish();
                    return;
                }
                Bundle bundle = (Bundle) result.getResult();
                if (!bundle.containsKey(ResultConstant.errorcode)) {
                    if (BindedCardPayActivity.this.hasToast) {
                        BindedCardPayActivity.this.payHelper.showPayStatus(BindedCardPayActivity.this.mTradeInfo.getKey(), ELePayState.FAILT, result.getDesc());
                    }
                    BindedCardPayActivity.this.visablePanel(false);
                    BindedCardPayActivity.this.hasToast = false;
                } else if (bundle.getInt(ResultConstant.errorcode) == 2005) {
                    BindedCardPayActivity.this.getOrderStateTimerTask(lepay_order_no, merchant_business_id, lepayPaymentNo);
                } else {
                    if (BindedCardPayActivity.this.hasToast) {
                        BindedCardPayActivity.this.payHelper.showPayStatus(BindedCardPayActivity.this.mTradeInfo.getKey(), ELePayState.FAILT, result.getDesc());
                    }
                    BindedCardPayActivity.this.visablePanel(false);
                    BindedCardPayActivity.this.hasToast = false;
                }
            }

            public void onPreExcuete() {
            }
        });
    }

    void visablePanel(boolean b) {
        LOG.logI("visablePanel＝" + b);
        if (this.lepay_ll_parent.getVisibility() != 0) {
            return;
        }
        if (b) {
            this.lepay_pay_ok.setClickable(false);
            this.lepay_payload_layer.setVisibility(0);
            this.lepay_payload_layer.setFocusable(true);
            this.lepay_pay_ok.setText(ResourceUtil.getStringResource(this.context, "lepay_pay_wait"));
            this.progress.setVisibility(0);
            return;
        }
        this.lepay_pay_ok.setClickable(true);
        this.lepay_payload_layer.setVisibility(8);
        this.lepay_payload_layer.setFocusable(false);
        this.lepay_pay_ok.setText(ResourceUtil.getStringResource(this.context, "lepay_pay_ok"));
        this.progress.setVisibility(8);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == this.lepay_bt_checkcode.getId()) {
            checkCode(createGetVerifyCodeParams());
        } else if (id != this.lepay_pay_ok.getId()) {
        } else {
            if (verifyText()) {
                showPayingMode();
                pay();
                return;
            }
            this.lepay_tv_checkcode_hint.setVisibility(0);
            this.lepay_tv_checkcode_hint.setText("请输入验证码");
        }
    }

    boolean verifyText() {
        String checkcode = this.lepay_et_checkcode.getText().toString();
        if (TextUtils.isEmpty(checkcode) || "".equals(checkcode.trim())) {
            return false;
        }
        return true;
    }

    PayCard createGetVerifyCodeParams() {
        String lepay_order_no = this.mTradeInfo.getLepay_order_no();
        String merchant_business_id = this.mTradeInfo.getMerchant_business_id();
        PayCard payCard = new PayCard();
        payCard.setLepay_order_no(lepay_order_no);
        payCard.setMerchant_business_id(merchant_business_id);
        payCard.setCardno(this.paymodes.getCard_no());
        payCard.setPhone(this.paymodes.getPhone_no());
        payCard.setBind_id(this.paymodes.getBind_id());
        payCard.setChannel_id(this.paymodes.getChannel_id());
        return payCard;
    }

    void startTimer() {
        this.mMessageTimer = new MessageCountTimer(60000, 1000);
        this.mMessageTimer.start();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mMessageTimer != null) {
            this.mMessageTimer.cancel();
        }
    }

    void getOrderStateTimerTask(final String lepay_order_no, final String merchant_business_id, final String lepayPaymentNo) {
        this.handler.postDelayed(new Runnable() {
            public void run() {
                if (BindedCardPayActivity.this.runCount > 10) {
                    Log.e("Ta", "removeCallbacks runCount: " + BindedCardPayActivity.this.runCount);
                    BindedCardPayActivity.this.visablePanel(false);
                    BindedCardPayActivity.this.handler.removeCallbacks(this);
                    return;
                }
                BindedCardPayActivity.this.validOrderState(lepay_order_no, merchant_business_id, lepayPaymentNo);
                Log.e("Ta", "runCount: " + BindedCardPayActivity.this.runCount);
                BindedCardPayActivity bindedCardPayActivity = BindedCardPayActivity.this;
                bindedCardPayActivity.runCount++;
            }
        }, 3000);
    }
}
