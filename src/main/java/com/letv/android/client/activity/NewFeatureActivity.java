package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.bean.RedPacketFrom;
import com.letv.core.api.LetvRequest;
import com.letv.core.bean.RecommenApp;
import com.letv.core.constant.PlayConstant.LiveType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.List;

public class NewFeatureActivity extends LetvBaseActivity {
    private MyViewPagerAdapter adapter;
    private Drawable drawable;
    private LayoutInflater inflater;
    private boolean isSelect;
    private boolean isshowDesp;
    private int lastX;
    private RecommenApp mrecoApp;
    private TextView recoAppName;
    private Button reco_btn;
    private TextView reco_cb;
    private LinearLayout reco_desp;
    private RelativeLayout rl_recom;
    private ViewPager viewpager;
    private List<View> views;

    class MyViewPagerAdapter extends PagerAdapter {
        ImageView btn;
        final /* synthetic */ NewFeatureActivity this$0;

        MyViewPagerAdapter(NewFeatureActivity this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
        }

        public int getCount() {
            return this.this$0.views.size();
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView((View) this.this$0.views.get(position), 0);
            if (position == this.this$0.views.size() - 1) {
                this.btn = (ImageView) ((View) this.this$0.views.get(position)).findViewById(R.id.newfeatures_btn);
                if (this.this$0.mrecoApp == null || (this.this$0.mrecoApp != null && this.this$0.mrecoApp.isInstall())) {
                    this.btn.setVisibility(8);
                    this.btn.setOnClickListener(new 1(this));
                }
                if (!(this.this$0.mrecoApp == null || this.this$0.mrecoApp.isInstall() || !this.this$0.isshowDesp)) {
                    this.this$0.updateUI();
                    this.this$0.isshowDesp = false;
                }
            }
            return this.this$0.views.get(position);
        }

        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) this.this$0.views.get(position));
        }
    }

    public NewFeatureActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.isSelect = true;
        this.isshowDesp = true;
        this.lastX = 0;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfeature);
        statisticsLaunch(0, false);
        requestFeatureRecomData();
        setRedPacketFrom(new RedPacketFrom(0));
        this.viewpager = (ViewPager) findViewById(R.id.newf_vp);
        this.rl_recom = (RelativeLayout) findViewById(R.id.newf_vp_recco);
        this.recoAppName = (TextView) this.rl_recom.findViewById(R.id.nf_vp_recm_appname);
        this.reco_desp = (LinearLayout) this.rl_recom.findViewById(R.id.nf_vp_recm_desp);
        this.reco_btn = (Button) this.rl_recom.findViewById(R.id.nf_vp_recm_btn);
        this.reco_cb = (TextView) this.rl_recom.findViewById(R.id.nf_vp_recm_cb_text);
        initviews();
        this.adapter = new MyViewPagerAdapter(this);
        this.viewpager.setAdapter(this.adapter);
        this.viewpager.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ NewFeatureActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        this.this$0.lastX = (int) event.getX();
                        break;
                    case 2:
                        if (((float) this.this$0.lastX) - event.getX() > 100.0f && this.this$0.viewpager.getCurrentItem() == this.this$0.views.size() - 1) {
                            this.this$0.goMyFavoriteActivity();
                            break;
                        }
                }
                return false;
            }
        });
        this.viewpager.setOnPageChangeListener(new OnPageChangeListener(this) {
            private boolean isSkip;
            private int size;
            final /* synthetic */ NewFeatureActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
                this.isSkip = true;
                this.size = this.this$0.views.size() - 1;
            }

            public void onPageSelected(int position) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == this.size && positionOffset == 0.0f && positionOffsetPixels == 0 && this.isSkip) {
                    this.isSkip = false;
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initviews() {
        this.inflater = LayoutInflater.from(this);
        this.views = new ArrayList();
        this.views.add(this.inflater.inflate(R.layout.what_new_one, null));
        this.views.add(this.inflater.inflate(R.layout.what_new_two, null));
        this.views.add(getButtonView(R.layout.what_new_three));
    }

    protected void goMyFavoriteActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void updateUI() {
        this.recoAppName.setText(this.mrecoApp.getName());
        String[] split = this.mrecoApp.getDesc().split("/");
        if (split.length == 2) {
            this.reco_desp.setPadding(0, 0, 0, 60);
        }
        for (CharSequence text : split) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new LayoutParams(-1, -1, 1.0f));
            tv.setShadowLayer(3.0f, 3.0f, 1.0f, 2131493090);
            tv.setTextColor(-1);
            tv.setGravity(1);
            tv.setText(text);
            tv.setTextSize(18.0f);
            this.reco_desp.addView(tv);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        this.viewpager.removeAllViewsInLayout();
        this.viewpager.setAdapter(null);
        this.rl_recom.setVisibility(8);
        this.rl_recom.removeAllViews();
        this.rl_recom = null;
        this.viewpager = null;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && event.getRepeatCount() == 0) {
            PreferencesManager.getInstance().notShowNewFeaturesDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    private View getButtonView(int resId) {
        View view = this.inflater.inflate(resId, null);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if (metrics.widthPixels == 480) {
            ImageView imageView = (ImageView) view.findViewById(R.id.newfeatures_btn);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            params.bottomMargin = LiveType.PLAY_LIVE_OTHER;
            imageView.setLayoutParams(params);
        }
        return view;
    }

    private void requestFeatureRecomData() {
        new LetvRequest().setCache(new VolleyNoCache()).setRequestType(RequestManner.NETWORK_ONLY).setUrl("").setCallback(new SimpleResponse<RecommenApp>(this) {
            final /* synthetic */ NewFeatureActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }
        }).add();
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return NewFeatureActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
