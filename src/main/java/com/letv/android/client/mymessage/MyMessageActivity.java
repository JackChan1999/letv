package com.letv.android.client.mymessage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MyMessageActivity extends LetvBaseActivity implements OnClickListener, MyMessageActivityCallback {
    private boolean enShowEditButton;
    private boolean isLogin;
    private ImageView mBack;
    private TextView mEdit;
    private MyMessageTabPageIndicator mIndicator;
    private ViewPager mPager;
    private MyMessageViewPagerAdapter mPagerAdapter;
    private PublicLoadLayout mRoot;

    public MyMessageActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MyMessageActivity.class);
        if (!(context instanceof Activity)) {
            intent.setFlags(268435456);
        }
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        this.isLogin = PreferencesManager.getInstance().isLogin();
        findView();
    }

    private void findView() {
        this.mBack = (ImageView) findViewById(R.id.back_btn);
        this.mEdit = (TextView) findViewById(R.id.edit);
        this.mBack.setOnClickListener(this);
        this.mRoot = (PublicLoadLayout) findViewById(R.id.my_message_content_container_layout);
        this.mRoot.addContent(R.layout.my_message_pager_view);
        this.mPager = (ViewPager) findViewById(2131363227);
        this.mIndicator = (MyMessageTabPageIndicator) findViewById(R.id.page_tab);
        this.mIndicator.setWidth(UIsUtils.getScreenWidth());
        this.mIndicator.setVisibility(8);
        this.mPagerAdapter = new MyMessageViewPagerAdapter(getSupportFragmentManager(), this);
        this.mPager.setAdapter(this.mPagerAdapter);
        this.mIndicator.setViewPager(this.mPager);
        this.mIndicator.setCurrentItem(1);
        this.mIndicator.setOnPageChangeListener(new OnPageChangeListener(this) {
            final /* synthetic */ MyMessageActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                this.this$0.mPagerAdapter.allExitEditMode();
                this.this$0.setEdit(false);
                if (!this.this$0.enShowEditButton) {
                    return;
                }
                if (this.this$0.isLogin && position == 1) {
                    this.this$0.mEdit.setVisibility(0);
                } else {
                    this.this$0.mEdit.setVisibility(8);
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        if (this.isLogin) {
            this.mEdit.setOnClickListener(this);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn /*2131361912*/:
                finish();
                return;
            case R.id.edit /*2131361999*/:
                Object tag = this.mEdit.getTag();
                if (tag == null) {
                    setEdit(true);
                    return;
                } else if (((Boolean) tag).booleanValue()) {
                    setEdit(false);
                    return;
                } else {
                    setEdit(true);
                    return;
                }
            default:
                return;
        }
    }

    private void setEdit(boolean isEdit) {
        this.mEdit.setTag(Boolean.valueOf(isEdit));
        this.mPagerAdapter.setEditMode(this.mPager.getCurrentItem(), isEdit);
        if (isEdit) {
            this.mEdit.setText(2131101581);
        } else {
            this.mEdit.setText(2131100474);
        }
    }

    public String[] getAllFragmentTags() {
        return new String[0];
    }

    public String getActivityName() {
        return getClass().getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }

    public void enShowEditButton() {
        this.enShowEditButton = true;
        this.mEdit.setVisibility(0);
    }

    public void selectTab(int position) {
        this.mPager.setCurrentItem(position, false);
    }

    public void loadFinish() {
        this.mIndicator.setVisibility(0);
    }
}
