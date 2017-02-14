package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.fragment.PersonalInfoFragment;
import com.letv.core.bean.UserBean;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.sina.weibo.sdk.component.ShareRequestParam;

public class PersonalInfoActivity extends LetvBaseActivity implements OnClickListener {
    private ImageView mBackImageView;
    private PersonalInfoFragment mPersonalInfoFragment;
    private TextView mTitleTv;
    private UserBean mUserBean;

    public PersonalInfoActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void launch(Context fromActivity, UserBean userBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("userbean", userBean);
        Intent intent = new Intent();
        intent.putExtra(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA, bundle);
        intent.setClass(fromActivity, PersonalInfoActivity.class);
        fromActivity.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main_activity);
        initUI();
    }

    private void initUI() {
        this.mBackImageView = (ImageView) findViewById(R.id.back_btn);
        this.mBackImageView.setOnClickListener(this);
        this.mTitleTv = (TextView) findViewById(2131362147);
        this.mTitleTv.setText(getActivity().getResources().getString(2131100413));
        this.mUserBean = (UserBean) getIntent().getBundleExtra(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA).getSerializable("userbean");
        this.mPersonalInfoFragment = new PersonalInfoFragment(this.mUserBean);
    }

    protected void onResume() {
        super.onResume();
        if (!PreferencesManager.getInstance().isLogin()) {
            finish();
        }
        showFragmentIfNeeded(this.mPersonalInfoFragment);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public String[] getAllFragmentTags() {
        return FragmentConstant.TAG_SETTINGS;
    }

    public String getActivityName() {
        return PersonalInfoActivity.class.getName();
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

    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        if (arg1 == -1) {
            finish();
        }
    }
}
