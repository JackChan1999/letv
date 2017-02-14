package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.fragment.SettingsFragment;
import com.letv.core.constant.FragmentConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class SettingsMainActivity extends LetvBaseActivity implements OnClickListener {
    private ImageView mBackImageView;
    private SettingsFragment mSettingsFragment;

    public SettingsMainActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mSettingsFragment = new SettingsFragment();
    }

    public static void launch(Context fromActivity) {
        fromActivity.startActivity(new Intent(fromActivity, SettingsMainActivity.class));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main_activity);
        initUI();
    }

    private void initUI() {
        this.mBackImageView = (ImageView) findViewById(R.id.back_btn);
        this.mBackImageView.setOnClickListener(this);
    }

    protected void onResume() {
        super.onResume();
        showFragmentIfNeeded(this.mSettingsFragment);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public String[] getAllFragmentTags() {
        return FragmentConstant.TAG_SETTINGS;
    }

    public String getActivityName() {
        return SettingsMainActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn /*2131361912*/:
                finish();
                return;
            default:
                return;
        }
    }
}
