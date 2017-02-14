package com.letv.android.client.activity;

import android.app.Activity;
import android.os.Bundle;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LivePlayVRActivity extends BasePlayActivity {
    public LivePlayVRActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    protected void onCreate(Bundle arg0) {
        this.mIsVr = true;
        super.onCreate(arg0);
    }

    public String getActivityName() {
        return LivePlayVRActivity.class.getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }
}
