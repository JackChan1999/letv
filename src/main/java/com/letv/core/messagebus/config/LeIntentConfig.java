package com.letv.core.messagebus.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.letv.core.config.LetvConfig;

public class LeIntentConfig {
    private Class<?> mComponentClass;
    protected Context mContext;
    private Intent mIntent;
    private IntentFlag mIntentFlag = IntentFlag.START_ACTIVITY;
    private int mRequestCode;

    public LeIntentConfig(Context context) {
        this.mContext = context;
        this.mIntent = new Intent();
    }

    public void setComponentClass(Class<?> cls) {
        if ((this.mContext == null || cls == null) && LetvConfig.isForTest()) {
            throw new NullPointerException("LeIntentConfig setComponentClass cls is null!");
        }
        this.mComponentClass = cls;
        this.mIntent.setClass(this.mContext, cls);
    }

    public void setIntentFlag(IntentFlag intentFlag) {
        this.mIntentFlag = intentFlag;
    }

    public void setRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
    }

    public void run() {
        if (this.mIntentFlag == IntentFlag.START_ACTIVITY) {
            startActivity();
        } else if (this.mIntentFlag == IntentFlag.START_ACTIVITY_FOR_RESULT) {
            startActivityForResult();
        }
    }

    private void startActivity() {
        if ((this.mContext == null || this.mIntent == null) && LetvConfig.isForTest()) {
            throw new NullPointerException("LeIntentConfig setComponentClass cls is null!");
        }
        if (!(this.mContext instanceof Activity)) {
            this.mIntent.addFlags(268435456);
        }
        this.mContext.startActivity(this.mIntent);
    }

    private void startActivityForResult() {
        if ((this.mContext == null || this.mIntent == null) && LetvConfig.isForTest()) {
            throw new NullPointerException("LeIntentConfig setComponentClass cls is null!");
        } else if (this.mContext instanceof Activity) {
            ((Activity) this.mContext).startActivityForResult(this.mIntent, this.mRequestCode);
        }
    }

    public Intent getIntent() {
        return this.mIntent;
    }
}
