package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.bean.RedPacketFrom;
import com.letv.android.client.fragment.ChannelDetailItemFragment;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.bean.channel.ChannelNavigation;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.StaticticsName;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import java.net.URLEncoder;

public class ChannelDetailItemActivity extends LetvBaseActivity {
    private ImageView mBackImageView;
    private Channel mChannel;
    private ChannelNavigation mChannelNavigation;
    private ChannelDetailItemFragment mFragment;
    private FragmentTransaction mFragmentTransaction;
    private Intent mIntent;
    private TextView mTitleView;
    private OnClickListener onClickEvent;
    private String title;

    public ChannelDetailItemActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.onClickEvent = new OnClickListener(this) {
            final /* synthetic */ ChannelDetailItemActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.back_btn /*2131361912*/:
                        this.this$0.finish();
                        return;
                    default:
                        return;
                }
            }
        };
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_detail_item);
        this.mIntent = getIntent();
        init();
        findView();
        initFragment();
    }

    private void init() {
        if (this.mIntent == null || this.mIntent.getSerializableExtra("channel") == null) {
            finish();
            return;
        }
        this.mChannel = (Channel) this.mIntent.getSerializableExtra("channel");
        this.mChannelNavigation = (ChannelNavigation) this.mIntent.getSerializableExtra("navigation");
        this.title = this.mIntent.getStringExtra("title");
        String pageId = "";
        if (this.mChannelNavigation != null) {
            pageId = this.mChannelNavigation.pageid + "";
        }
        setRedPacketFrom(new RedPacketFrom(4, this.mChannel.id + "", pageId));
    }

    private void initFragment() {
        if (this.mFragment == null) {
            this.mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            this.mFragment = new ChannelDetailItemFragment();
            Bundle bundle = this.mIntent.getExtras();
            if (bundle != null) {
                this.mFragment.setArguments(bundle);
                if (getSupportFragmentManager().findFragmentByTag("ChannelDetailItemFragment") != this.mFragment) {
                    this.mFragmentTransaction.replace(R.id.channel_detail_item_content, this.mFragment, "ChannelDetailItemFragment");
                    this.mFragmentTransaction.commit();
                }
            }
        }
    }

    private void findView() {
        this.mBackImageView = (ImageView) findViewById(R.id.back_btn);
        this.mTitleView = (TextView) findViewById(R.id.title_channel_name);
        this.mBackImageView.setOnClickListener(this.onClickEvent);
        TextView textView = this.mTitleView;
        CharSequence charSequence = (this.mChannelNavigation == null || TextUtils.isEmpty(this.mChannelNavigation.nameCn)) ? this.title : this.mChannelNavigation.nameCn;
        textView.setText(charSequence);
    }

    protected void onResume() {
        int i;
        super.onResume();
        StringBuffer sb = new StringBuffer();
        sb.append(StaticticsName.STATICTICS_NAM_NA).append(URLEncoder.encode(this.mTitleView.getText().toString())).append("&").append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.getPageIdByChannelId(this.mChannel.id)).append("&scid=").append(this.mChannelNavigation != null ? this.mChannelNavigation.pageid + "" : null);
        DataStatistics instance = DataStatistics.getInstance();
        Context context = this.mContext;
        String str = "0";
        String str2 = "0";
        String pcode = LetvUtils.getPcode();
        String str3 = "25";
        String stringBuffer = sb.toString();
        String str4 = "0";
        String str5 = this.mChannel.id + "";
        String uid = LetvUtils.getUID();
        String str6 = this.mChannelNavigation == null ? NetworkUtils.DELIMITER_LINE : this.mChannelNavigation.reid;
        String str7 = this.mChannelNavigation == null ? NetworkUtils.DELIMITER_LINE : this.mChannelNavigation.area;
        String str8 = this.mChannelNavigation == null ? NetworkUtils.DELIMITER_LINE : this.mChannelNavigation.bucket;
        String str9 = NetworkUtils.DELIMITER_LINE;
        if (PreferencesManager.getInstance().isLogin()) {
            i = 0;
        } else {
            i = 1;
        }
        instance.sendActionInfoBigData(context, str, str2, pcode, str3, stringBuffer, str4, str5, null, null, uid, str6, str7, str8, null, str9, i);
    }

    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.sink_in, R.anim.out_to_right);
        removeFragment(this.mFragment);
        this.mFragment = null;
        this.mFragmentTransaction = null;
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return ChannelDetailItemActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }

    public ChannelDetailItemFragment getChannelDetailItemFragment() {
        return this.mFragment;
    }
}
