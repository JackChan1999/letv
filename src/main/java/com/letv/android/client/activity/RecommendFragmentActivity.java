package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.letv.android.client.R;
import com.letv.android.client.fragment.TopRecommendFragment;
import com.letv.core.utils.LetvUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RecommendFragmentActivity extends PimBaseActivity {
    private TopRecommendFragment fragment;

    public RecommendFragmentActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void launch(Context context) {
        if (!LetvUtils.isGooglePlay()) {
            Intent i = new Intent();
            i.setClass(context, RecommendFragmentActivity.class);
            context.startActivity(i);
        }
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initUI();
        findView();
        setTitle(2131100361);
    }

    public int getContentView() {
        return R.layout.recommend_activity;
    }

    public void initUI() {
        super.initUI();
    }

    public void findView() {
        this.fragment = (TopRecommendFragment) getSupportFragmentManager().findFragmentById(R.id.recommend_fragment);
    }

    public String getActivityName() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }
}
