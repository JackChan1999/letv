package com.letv.android.client.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.RegisterFragmentPagerAdapter;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.view.title.TabPageIndicator;
import com.letv.android.client.fragment.RegisterMessageFragment;
import com.letv.android.client.fragment.RegisterMobileFragment;
import com.letv.android.client.thirdpartlogin.ThirdPartLoginLayout;
import com.letv.android.client.thirdpartlogin.ThirdPartLoginLayout.ThirdPartLoginSuccessCallBack;
import com.letv.android.client.view.SettingViewPager;
import com.letv.core.bean.UserBean;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RegisterActivity extends LetvBaseActivity implements OnClickListener, ThirdPartLoginSuccessCallBack {
    public static String registedAction = "registed_finish_action";
    private ImageView mBackImageView;
    private TextView mCallPhoneTv;
    private TabPageIndicator mIndicator;
    private RegisterFragmentPagerAdapter mRegisterFragmentPagerAdapter;
    private SettingViewPager mRegisterViewPager;
    private ThirdPartLoginLayout mThirdPartLoginLayout;
    private BroadcastReceiver recever;

    public RegisterActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.recever = new BroadcastReceiver(this) {
            final /* synthetic */ RegisterActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onReceive(Context arg0, Intent arg1) {
                this.this$0.finish();
            }
        };
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main_activity);
        initUI();
    }

    private void initUI() {
        registerReceiver(this.recever, new IntentFilter(registedAction));
        this.mIndicator = (TabPageIndicator) findViewById(R.id.register_indicator);
        this.mRegisterViewPager = (SettingViewPager) findViewById(R.id.register_viewpager);
        this.mRegisterFragmentPagerAdapter = new RegisterFragmentPagerAdapter(getSupportFragmentManager());
        this.mRegisterViewPager.setPagingEnabled(true);
        this.mRegisterFragmentPagerAdapter.addFragment(new RegisterMobileFragment());
        this.mRegisterFragmentPagerAdapter.addFragment(new RegisterMessageFragment());
        this.mRegisterViewPager.setOffscreenPageLimit(this.mRegisterFragmentPagerAdapter.getCount());
        this.mRegisterViewPager.setAdapter(this.mRegisterFragmentPagerAdapter);
        this.mIndicator.setViewPager(this.mRegisterViewPager);
        this.mIndicator.setOnPageChangeListener(new OnPageChangeListener(this) {
            final /* synthetic */ RegisterActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onPageScrolled(int i, float v, int i1) {
            }

            public void onPageSelected(int i) {
                LogInfo.LogStatistics("注册tab点击曝光");
                StatisticsUtils.staticticsInfoPost(this.this$0.mContext, "0", "c83", null, i == 0 ? 1 : 3, "-1", PageIdConstant.registerPage, null, null, null);
            }

            public void onPageScrollStateChanged(int i) {
            }
        });
        changeViewPageHight();
        this.mBackImageView = (ImageView) findViewById(R.id.register_btn_back);
        this.mBackImageView.setOnClickListener(this);
        this.mCallPhoneTv = (TextView) findViewById(R.id.include_supertv_call_phone);
        this.mCallPhoneTv.setOnClickListener(this);
        this.mThirdPartLoginLayout = (ThirdPartLoginLayout) findViewById(R.id.register_third_part);
        this.mThirdPartLoginLayout.initActivity(getActivity(), this);
        this.mThirdPartLoginLayout.initThirtPart();
        StatisticsUtils.staticticsInfoPost(this, "19", null, null, -1, null, PageIdConstant.registerPage, null, null, null, null, null);
    }

    @SuppressLint({"NewApi"})
    private void changeViewPageHight() {
        final int w = MeasureSpec.makeMeasureSpec(0, 0);
        final int h = MeasureSpec.makeMeasureSpec(0, 0);
        this.mRegisterViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
            final /* synthetic */ RegisterActivity this$0;

            public void onGlobalLayout() {
                if (VERSION.SDK_INT >= 16) {
                    this.this$0.mRegisterViewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    this.this$0.mRegisterViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                View view = this.this$0.mRegisterViewPager.getChildAt(this.this$0.mRegisterViewPager.getCurrentItem());
                view.measure(w, h);
                LayoutParams params = new LayoutParams(-1, -2);
                params.height = view.getMeasuredHeight();
                this.this$0.mRegisterViewPager.setLayoutParams(params);
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.include_supertv_call_phone /*2131362895*/:
                call(getResources().getString(2131100804));
                return;
            case R.id.register_btn_back /*2131364174*/:
                StatisticsUtils.staticticsInfoPost(this, "0", "c81", null, 1, null, PageIdConstant.registerPage, null, null, null, null, null);
                finish();
                return;
            default:
                return;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.recever != null) {
            unregisterReceiver(this.recever);
        }
    }

    private void call(String mobile) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + mobile));
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            this.mThirdPartLoginLayout.getUserByToken();
        }
        this.mThirdPartLoginLayout.setSsoHandler(requestCode, resultCode, data);
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return RegisterActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }

    public void thirdPartLoginSuccess(UserBean userBean) {
        setResult(1);
        finish();
    }

    public void loading(boolean isLoading) {
    }
}
