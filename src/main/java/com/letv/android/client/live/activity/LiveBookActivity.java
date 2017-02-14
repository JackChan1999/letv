package com.letv.android.client.live.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.LiveBookFragmentAdapter;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.live.fragment.LiveBookFragment;
import com.letv.android.client.view.channel.ChannelTabPageIndicator;
import com.letv.core.bean.LiveBookTabBean;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.utils.LetvUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.List;

public class LiveBookActivity extends LetvBaseActivity {
    private ImageView mBtnBack;
    private LinearLayout mCNLayout;
    private FragmentTransaction mFragmentTransaction;
    private RelativeLayout mHKLayout;
    private LiveBookFragment mLivePreengageFragment;
    private LiveBookFragmentAdapter mPageAdapter;
    private ChannelTabPageIndicator mTabPageIndicator;
    private TextView mTitleText;
    private ViewPager mViewpager;
    OnPageChangeListener pageChangeListener;

    public LiveBookActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.pageChangeListener = new OnPageChangeListener(this) {
            final /* synthetic */ LiveBookActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
            }

            public void onPageScrollStateChanged(int state) {
            }
        };
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LiveBookActivity.class));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_book_layout);
        findView();
        initView();
    }

    private void findView() {
        this.mTitleText = (TextView) findViewById(2131362352);
        this.mTitleText.setText(2131100302);
        this.mBtnBack = (ImageView) findViewById(2131362351);
        this.mBtnBack.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ LiveBookActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                this.this$0.finish();
            }
        });
        this.mHKLayout = (RelativeLayout) findViewById(R.id.hk_layout);
        this.mCNLayout = (LinearLayout) findViewById(R.id.cn_layout);
        this.mTabPageIndicator = (ChannelTabPageIndicator) findViewById(R.id.live_indicator);
        this.mViewpager = (ViewPager) findViewById(R.id.live_viewpager);
    }

    private void initView() {
        int i;
        int i2 = 0;
        if (LetvUtils.isInHongKong()) {
            initHKFragment();
        } else {
            initCNViewPager();
        }
        LinearLayout linearLayout = this.mCNLayout;
        if (LetvUtils.isInHongKong()) {
            i = 8;
        } else {
            i = 0;
        }
        linearLayout.setVisibility(i);
        RelativeLayout relativeLayout = this.mHKLayout;
        if (!LetvUtils.isInHongKong()) {
            i2 = 8;
        }
        relativeLayout.setVisibility(i2);
    }

    private void initHKFragment() {
        if (this.mLivePreengageFragment == null) {
            this.mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            this.mLivePreengageFragment = new LiveBookFragment();
            Bundle bundle = new Bundle();
            bundle.putString("name", LiveRoomConstant.CHANNEL_TYPE_ALL);
            this.mLivePreengageFragment.setArguments(bundle);
            if (getSupportFragmentManager().findFragmentByTag("LiveBookFragment") != this.mLivePreengageFragment) {
                this.mFragmentTransaction.replace(R.id.hk_layout, this.mLivePreengageFragment, "LiveBookFragment");
                this.mFragmentTransaction.commit();
            }
        }
    }

    private void initCNViewPager() {
        this.mPageAdapter = new LiveBookFragmentAdapter(getSupportFragmentManager(), this.mContext);
        this.mPageAdapter.setData(getCNNavagation());
        this.mViewpager.setAdapter(this.mPageAdapter);
        this.mTabPageIndicator.isHome(false);
        this.mTabPageIndicator.setViewPager(this.mViewpager);
        this.mTabPageIndicator.setOnPageChangeListener(this.pageChangeListener);
        this.mTabPageIndicator.notifyDataSetChanged();
    }

    private List getCNNavagation() {
        return new ArrayList<LiveBookTabBean>(this) {
            private static final long serialVersionUID = 1;
            final /* synthetic */ LiveBookActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
                add(new LiveBookTabBean(this.this$0.mContext.getResources().getString(2131099828), LiveRoomConstant.CHANNEL_TYPE_ALL));
                add(new LiveBookTabBean(this.this$0.mContext.getResources().getString(2131100272), "sports"));
                add(new LiveBookTabBean(this.this$0.mContext.getResources().getString(2131100271), "music"));
                add(new LiveBookTabBean(this.this$0.mContext.getResources().getString(2131100263), "ent"));
                add(new LiveBookTabBean(this.this$0.mContext.getResources().getString(2131100273), "variety"));
                add(new LiveBookTabBean(this.this$0.mContext.getResources().getString(2131100265), "game"));
                add(new LiveBookTabBean(this.this$0.mContext.getResources().getString(2131100262), "brand"));
                add(new LiveBookTabBean(this.this$0.mContext.getResources().getString(2131100269), "information"));
                add(new LiveBookTabBean(this.this$0.mContext.getResources().getString(2131100264), "finance"));
            }
        };
    }

    protected void onDestroy() {
        removeFragment(this.mLivePreengageFragment);
        this.mLivePreengageFragment = null;
        super.onDestroy();
    }

    public Activity getActivity() {
        return this;
    }

    public String getActivityName() {
        return LiveBookActivity.class.getName();
    }

    public String[] getAllFragmentTags() {
        return null;
    }
}
