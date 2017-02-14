package com.letv.android.client.task;

import android.app.Dialog;
import android.content.Context;
import com.letv.android.client.view.CustomLoadingDialog;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PayCenterApi;
import com.letv.core.bean.PayResult;
import com.letv.core.bean.VipProductBean.ProductBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.PayResultParser;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RequestLedianPayTask {
    private CustomLoadingDialog mDialog;

    public RequestLedianPayTask(Context mContext) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public void ledianPayTask(Context context, String activityId, ProductBean mProductBean, String sVip) {
        this.mDialog = new CustomLoadingDialog(context);
        this.mDialog.setCanceledOnTouchOutside(false);
        this.mDialog.setOnCancelListener(new 1(this));
        showDialog(this.mDialog);
        String url = PayCenterApi.getInstance().pay(0, "130", PreferencesManager.getInstance().getUserName(), mProductBean.mName, ((int) (mProductBean.getLedianPrice() * 100.0f)) + "", "0", "ledian", "consume", "0", String.valueOf(mProductBean.mMonthType), sVip, activityId);
        LogInfo.log("ZSM", "ledian url == " + url);
        new LetvRequest(PayResult.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(url).setCache(new VolleyNoCache()).setParser(new PayResultParser()).setCallback(new 2(this, mProductBean, context)).add();
    }

    private void showDialog(Dialog dlg) {
        if (dlg != null) {
            if (dlg.isShowing()) {
                dlg.cancel();
            } else {
                dlg.show();
            }
        }
    }
}
