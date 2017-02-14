package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.android.client.fragment.MyPlayRecordFragment;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.Observable;

public class MyPlayRecordActivity extends LetvBaseActivity {
    private ImageView mBackImageView;
    private TextView mEditView;
    private MyPlayRecordFragment mFragment;
    private FragmentTransaction mFragmentTransaction;
    private boolean mIsDelete;
    private RelativeLayout mLoginTip;
    private TextView mTitleView;
    private StateObservable mWatched;
    private OnClickListener onClickEvent;

    private class StateObservable extends Observable {
        private StateObservable() {
        }

        /* synthetic */ StateObservable(MyPlayRecordActivity x0, AnonymousClass1 x1) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this();
        }

        public void setDeleteClickChanged() {
            setChanged();
            Bundle bundle = new Bundle();
            bundle.putInt("state", 101);
            bundle.putBoolean("mIsDelete", MyPlayRecordActivity.this.mIsDelete);
            notifyObservers(bundle);
        }
    }

    public MyPlayRecordActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mIsDelete = false;
        this.mWatched = new StateObservable();
        this.onClickEvent = new OnClickListener(this) {
            final /* synthetic */ MyPlayRecordActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                switch (v.getId()) {
                    case 2131362351:
                        LogInfo.LogStatistics("playrecord nav back");
                        StatisticsUtils.staticticsInfoPost(this.this$0, "0", "h73", "返回", -1, null, "051", null, null, null, null, null);
                        this.this$0.finish();
                        return;
                    case 2131362354:
                        if (this.this$0.mIsDelete) {
                            this.this$0.mIsDelete = false;
                            this.this$0.mFragment.setAllPick(false);
                            this.this$0.mEditView.setText(this.this$0.getString(2131099788));
                            LogInfo.LogStatistics("common nav edit");
                            StatisticsUtils.staticticsInfoPost(this.this$0, "0", "h71", "完成", 1, null, "051", null, null, null, null, null);
                        } else {
                            this.this$0.mIsDelete = true;
                            this.this$0.mFragment.setAllPick(false);
                            this.this$0.mEditView.setText(this.this$0.getString(2131099798));
                            LogInfo.LogStatistics("common nav cancel");
                            StatisticsUtils.staticticsInfoPost(this.this$0, "0", "h71", "编辑", 1, null, "051", null, null, null, null, null);
                        }
                        this.this$0.mWatched.setDeleteClickChanged();
                        return;
                    default:
                        return;
                }
            }
        };
    }

    public static void launch(Context context, int page) {
        if (context != null) {
            Intent intent = new Intent(context, MyPlayRecordActivity.class);
            intent.putExtra(MyDownloadActivityConfig.PAGE, page);
            context.startActivity(intent);
        }
    }

    public static void launch(Activity activity, int page) {
        if (activity != null) {
            Intent intent = new Intent(activity, MyPlayRecordActivity.class);
            intent.putExtra(MyDownloadActivityConfig.PAGE, page);
            activity.startActivityForResult(intent, page);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect_main);
        initNavigationBar();
        if (this.mFragment == null) {
            this.mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            this.mFragment = new MyPlayRecordFragment();
            this.mFragment.setObservable(this.mWatched);
            if (getSupportFragmentManager().findFragmentByTag("MyPlayRecordFragment") != this.mFragment) {
                this.mFragmentTransaction.replace(R.id.my_collect_content, this.mFragment, "MyPlayRecordFragment");
                this.mFragmentTransaction.commit();
            }
        }
    }

    private void initNavigationBar() {
        RelativeLayout my_playrecrod_content = (RelativeLayout) findViewById(R.id.my_collect_content);
        LayoutParams params = (LayoutParams) my_playrecrod_content.getLayoutParams();
        params.bottomMargin = 0;
        my_playrecrod_content.setLayoutParams(params);
        this.mBackImageView = (ImageView) findViewById(2131362351);
        this.mEditView = (TextView) findViewById(2131362354);
        this.mEditView.setText(2131099788);
        this.mEditView.setTextColor(this.mContext.getResources().getColor(2131493261));
        this.mTitleView = (TextView) findViewById(2131362352);
        this.mLoginTip = (RelativeLayout) findViewById(R.id.my_collect_bottom_login_btn);
        this.mLoginTip.setVisibility(8);
        this.mTitleView.setText(getResources().getString(2131100479));
        this.mBackImageView.setOnClickListener(this.onClickEvent);
        this.mTitleView.setOnClickListener(this.onClickEvent);
        this.mEditView.setOnClickListener(this.onClickEvent);
    }

    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.sink_in, R.anim.out_to_right);
        this.mFragment = null;
        this.mFragmentTransaction = null;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.mFragment.refreshLogin();
    }

    public void showDelBtn(boolean isShow) {
        this.mEditView.setVisibility(isShow ? 0 : 8);
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }
}
