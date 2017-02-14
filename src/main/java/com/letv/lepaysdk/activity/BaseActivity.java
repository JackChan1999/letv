package com.letv.lepaysdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.letv.lepaysdk.ActivityManager;
import com.letv.lepaysdk.TradeManager;
import com.letv.lepaysdk.model.TradeInfo;
import com.letv.lepaysdk.network.NetworkManager;
import com.letv.lepaysdk.utils.LOG;

public class BaseActivity extends Activity {
    protected ActivityManager mActivityManager;
    protected NetworkManager mNetworkManager;
    protected int mState;
    protected TradeInfo mTradeInfo;
    protected TradeManager mTradeManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mNetworkManager = NetworkManager.getInstance(getApplicationContext());
        this.mTradeManager = TradeManager.getInstance();
        this.mActivityManager = ActivityManager.getInstance();
        this.mActivityManager.addActivityToMap(this);
        if (savedInstanceState != null) {
            getSaveInstanceState(savedInstanceState);
        } else {
            filterIntent();
        }
    }

    protected void filterIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(TradeInfo.TRADE_KEY)) {
                this.mTradeInfo = this.mTradeManager.getTradeInfo(intent.getStringExtra(TradeInfo.TRADE_KEY));
            }
            if (intent.hasExtra("state")) {
                this.mState = intent.getIntExtra("state", 0);
            }
            if (this.mState == 0) {
                LOG.logI("Activity Leak of Status");
            }
        }
    }

    protected void getSaveInstanceState(Bundle savedInstanceState) {
        this.mTradeInfo = (TradeInfo) savedInstanceState.getSerializable(TradeInfo.TRADE_KEY);
        this.mState = savedInstanceState.getInt("state");
        if (this.mTradeManager.getTradeInfo(this.mTradeInfo.getKey()) == null) {
            this.mTradeManager.addTradeInfo(this.mTradeInfo);
        }
        LOG.logI("异常退出数据获取：" + this.mState + "  " + this.mTradeInfo.getLepay_order_no());
    }

    protected void onDestroy() {
        this.mActivityManager.removeActivityOnMap((Activity) this);
        super.onDestroy();
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(TradeInfo.TRADE_KEY, this.mTradeInfo);
        outState.putInt("state", this.mState);
        super.onSaveInstanceState(outState);
    }
}
