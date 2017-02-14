package com.letv.lepaysdk.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import com.letv.lepaysdk.ELePayState;
import com.letv.lepaysdk.model.PayCard;
import com.letv.lepaysdk.task.TaskListener;
import com.letv.lepaysdk.utils.ResourceUtil;
import com.letv.lepaysdk.utils.StringUtil;
import com.letv.lepaysdk.utils.ThreadUtil;
import com.letv.lepaysdk.utils.UIUtils;
import com.letv.lepaysdk.view.LePayCustomDialog;
import com.letv.lepaysdk.view.LePayCustomDialog.Builder;
import java.util.List;
import org.json.JSONObject;

public class CardPayHelper {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$letv$lepaysdk$ELePayState;
    private final int FP = -1;
    private final int PER_ROW_COUNT = 3;
    private final int WC = -2;
    protected Context context;
    protected NetworkManager mNetworkManager;

    public static class BankInfo {
        public String logourl;
        public String name;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$letv$lepaysdk$ELePayState() {
        int[] iArr = $SWITCH_TABLE$com$letv$lepaysdk$ELePayState;
        if (iArr == null) {
            iArr = new int[ELePayState.values().length];
            try {
                iArr[ELePayState.CANCEL.ordinal()] = 5;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[ELePayState.FAILT.ordinal()] = 3;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[ELePayState.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[ELePayState.NONETWORK.ordinal()] = 6;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[ELePayState.OK.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[ELePayState.PAYED.ordinal()] = 8;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[ELePayState.UNDEFINED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[ELePayState.WAITTING.ordinal()] = 4;
            } catch (NoSuchFieldError e8) {
            }
            $SWITCH_TABLE$com$letv$lepaysdk$ELePayState = iArr;
        }
        return iArr;
    }

    public CardPayHelper(Context context) {
        this.context = context;
        if (this.context == null) {
            throw new NumberFormatException("CardPayHelper Context is null ");
        }
        this.mNetworkManager = NetworkManager.getInstance(context);
    }

    public void checkCode(PayCard payCard, TaskListener<String> listener) {
        ThreadUtil.execUi(new 1(this, payCard, listener), new String[0]);
    }

    public void pay(String channel_id, String lepay_order_no, String merchant_business_id, String verifycode, String sendBy, TaskListener<JSONObject> listener) {
        ThreadUtil.execUi(new 2(this, listener, channel_id, lepay_order_no, merchant_business_id, verifycode, sendBy), new String[0]);
    }

    public void queryOrderState(String lepayOrderNo, String merchantBusinessId, String lepayPaymentNo, TaskListener<Bundle> listener) {
        ThreadUtil.execUi(new 3(this, listener, lepayOrderNo, merchantBusinessId, lepayPaymentNo));
    }

    public void queryVerifyCode(String lepay_order_no, String merchant_business_id, String phone, String verifycode, String sendBy, TaskListener<Void> listener) {
        ThreadUtil.execUi(new 4(this, lepay_order_no, merchant_business_id, phone, verifycode, sendBy, listener));
    }

    public void getCardInfo(String merchant_business_id, String lepay_order_no, TaskListener<JSONObject> listener) {
        ThreadUtil.execUi(new 5(this, merchant_business_id, lepay_order_no, listener));
    }

    public void getSupportBanklist(String card_type, TaskListener<List<BankInfo>> listener) {
        ThreadUtil.execUi(new 6(this, listener, card_type));
    }

    public void showPayStatus(String key, ELePayState ePayStatus, String content) {
        if (!((Activity) this.context).isFinishing()) {
            showPayStatusDialog(key, ePayStatus, content);
        }
    }

    private void showPayStatusDialog(String key, ELePayState ePayStatus, String content) {
        String newContent;
        if (TextUtils.isEmpty(content)) {
            newContent = "";
        } else {
            newContent = content;
        }
        View view = View.inflate(this.context, ResourceUtil.getLayoutResource(this.context, "lepay_pay_status"), null);
        ImageView lepay_state_icon = (ImageView) view.findViewById(ResourceUtil.getIdResource(this.context, "lepay_state_icon"));
        TextView lepay_tv_state = (TextView) view.findViewById(ResourceUtil.getIdResource(this.context, "lepay_tv_state"));
        TextView lepay_tv_stateDes = (TextView) view.findViewById(ResourceUtil.getIdResource(this.context, "lepay_tv_stateDes"));
        TextView lepay_tv_ok = (TextView) view.findViewById(ResourceUtil.getIdResource(this.context, "lepay_tv_ok"));
        switch ($SWITCH_TABLE$com$letv$lepaysdk$ELePayState()[ePayStatus.ordinal()]) {
            case 2:
                lepay_tv_state.setText("支付成功");
                lepay_tv_stateDes.setText(StringUtil.textSpan(this.context, "lepay_pay_state_content", newContent, "c555555", null, 7));
                lepay_state_icon.setImageResource(ResourceUtil.getDrawableResource(this.context, "lepay_icon_chenggong"));
                lepay_tv_ok.setText("支付完成");
                break;
            case 3:
                lepay_tv_state.setText("支付失败");
                lepay_tv_stateDes.setText(newContent);
                lepay_state_icon.setImageResource(ResourceUtil.getDrawableResource(this.context, "lepay_icon_shibai"));
                lepay_tv_ok.setText("重新支付");
                break;
            case 4:
                lepay_state_icon.setImageResource(ResourceUtil.getDrawableResource(this.context, "lepay_icon_shibai"));
                lepay_tv_state.setText("支付等待中");
                lepay_tv_stateDes.setText(newContent);
                lepay_tv_ok.setText("支付中");
                break;
            case 8:
                lepay_state_icon.setImageResource(ResourceUtil.getDrawableResource(this.context, "lepay_icon_shibai"));
                lepay_tv_state.setText("订单异常");
                lepay_tv_stateDes.setText(newContent);
                lepay_tv_ok.setText("支付完成");
                break;
        }
        Builder builder = new Builder(this.context);
        builder.setOnCloseListener(new 7(this, ePayStatus, key, content));
        builder.setContentView(view);
        LePayCustomDialog lePayCustomDialog = builder.create();
        lepay_tv_ok.setOnClickListener(new 8(this, ePayStatus, key, content, lePayCustomDialog));
        lePayCustomDialog.setCanceledOnTouchOutside(false);
        builder.getTv_Title().setVisibility(8);
        lePayCustomDialog.show();
    }

    void sendEStatus(ELePayState ePayStatus) {
        Intent intent = new Intent();
        intent.setAction(ePayStatus.toString());
        this.context.sendBroadcast(intent);
    }

    public void createSupportBanks(int titleResId, List<BankInfo> bankInfos) {
        int rows;
        LayoutParams ll;
        View view = View.inflate(this.context, ResourceUtil.getLayoutResource(this.context, "lepay_debitcard_supportbank_list"), null);
        TableLayout tableLayout = (TableLayout) view.findViewById(ResourceUtil.getIdResource(this.context, "tb_supportbanklist"));
        int size = bankInfos.size();
        if (size % 3 == 0) {
            rows = size / 3;
        } else {
            rows = (size / 3) + 1;
        }
        int start = 0;
        int end = 0;
        for (int row = 0; row < rows && end >= start && start <= size; row++) {
            end = (row + 1) * 3;
            if (end > size) {
                end = size;
            }
            List<BankInfo> newbankInfos = bankInfos.subList(start, end);
            start = (row + 1) * 3;
            View tableRow = new TableRow(this.context);
            for (int col = 0; col < 3; col++) {
                if (newbankInfos != null) {
                    try {
                        BankInfo info = (BankInfo) newbankInfos.get(col);
                        ImageView imageView = new ImageView(this.context);
                        ThreadUtil.exec(new 9(this, info, (int) this.context.getResources().getDimension(ResourceUtil.getDimenResource(this.context, "lepay_layer_w")), imageView));
                        ll = new LayoutParams(UIUtils.dipToPx(this.context, 240), -2);
                        ll.weight = 1.0f;
                        tableRow.addView(imageView, ll);
                        tableRow.setPadding(5, 5, 5, 5);
                    } catch (Exception e) {
                        ll = new LayoutParams(UIUtils.dipToPx(this.context, 240), -2);
                        ll.weight = 1.0f;
                        tableRow.addView(new ImageView(this.context), ll);
                        tableRow.setPadding(5, 5, 5, 5);
                    }
                }
            }
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(-1, -2));
        }
        Builder builder = new Builder(this.context);
        builder.setTitle(titleResId);
        builder.setContentView(view);
        builder.create().show();
    }
}
