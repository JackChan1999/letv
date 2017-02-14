package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.fragment.StarRankOldListFragment;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class StarOldRankListActivity extends LetvBaseActivity implements OnClickListener {
    private ImageView mBackImageView;
    private StarRankOldListFragment mFragment;
    private FragmentTransaction mFragmentTransaction;

    public StarOldRankListActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_rank_old_list);
        findView();
        initFragment();
    }

    public static void launch(Context context, int from) {
        if (context != null) {
            context.startActivity(new Intent(context, StarOldRankListActivity.class));
        }
    }

    private void initFragment() {
        if (this.mFragment == null) {
            this.mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            this.mFragment = new StarRankOldListFragment();
            if (getSupportFragmentManager().findFragmentByTag("StarRankOldListFragment") != this.mFragment) {
                this.mFragmentTransaction.replace(R.id.star_rank_list_view, this.mFragment, "StarRankOldListFragment");
                this.mFragmentTransaction.commit();
            }
        }
    }

    private void findView() {
        this.mBackImageView = (ImageView) findViewById(R.id.star_btn_back);
        this.mBackImageView.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.star_btn_back /*2131362043*/:
                finish();
                return;
            default:
                return;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.sink_in, R.anim.out_to_right);
        this.mFragment = null;
        this.mFragmentTransaction = null;
    }

    public String getActivityName() {
        return StarOldRankListActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }

    public String[] getAllFragmentTags() {
        return new String[0];
    }
}
