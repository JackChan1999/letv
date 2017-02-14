package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.share.LetvShareControl;
import com.letv.android.client.share.LetvSinaShareSSO;
import com.letv.android.client.share.LetvTencentWeiboShare;
import com.letv.android.client.task.WeiboGetUerNameTask;
import com.letv.android.client.utils.UIs;
import com.letv.core.bean.ShareAlbumBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.share.sina.ex.BSsoHandler;

public class ShareActivity extends PimBaseActivity implements OnClickListener {
    private ShareAlbumBean mShareAlbumBean;
    private RelativeLayout mSina;
    private int mSinaBindStatus;
    private TextView mSinaText;
    private BSsoHandler mSsoHandler;
    private int mTencentBindStatus;
    private RelativeLayout mWeibo;
    private TextView mWeiboText;

    public ShareActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mShareAlbumBean = LetvShareControl.mShareAlbum;
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ShareActivity.class));
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initUI();
        setTitle(2131100638);
    }

    protected void onResume() {
        super.onResume();
        refreshBindState();
        updateUI(0);
    }

    protected void onPause() {
        ToastUtils.cancelToast();
        super.onPause();
    }

    public int getContentView() {
        return R.layout.share_main;
    }

    public void initUI() {
        super.initUI();
        this.mSina = (RelativeLayout) findViewById(R.id.share_sina);
        this.mWeibo = (RelativeLayout) findViewById(R.id.share_tencent);
        this.mSinaText = (TextView) findViewById(R.id.share_sina_click_to_bundle);
        this.mWeiboText = (TextView) findViewById(R.id.share_tencent_click_to_bundle);
        this.mSina.setOnClickListener(this);
        this.mWeibo.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_sina /*2131364316*/:
                sina_weibo_unBind();
                return;
            case R.id.share_tencent /*2131364320*/:
                tencent_weibo_unBind();
                return;
            default:
                return;
        }
    }

    private void sina_weibo_unBind() {
        if (this.mSinaBindStatus == 1) {
            UIs.showDialog(this, getText(2131100769), null, 0, getText(2131100001), getText(2131100002), new DialogInterface.OnClickListener(this) {
                final /* synthetic */ ShareActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }, new DialogInterface.OnClickListener(this) {
                final /* synthetic */ ShareActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    this.this$0.showUnbindDialog(true);
                    dialogInterface.cancel();
                }
            }, 0, 0, 0, 0);
        } else if (NetworkUtils.isNetworkAvailable()) {
            this.mSsoHandler = LetvSinaShareSSO.login(getActivity(), this.mShareAlbumBean, null, 1, -1, "", -1, "", "");
            refreshBindState();
        } else {
            ToastUtils.showToast((Context) this, 2131101012);
        }
    }

    private void tencent_weibo_unBind() {
        if (this.mTencentBindStatus == 1) {
            UIs.showDialog(this, getText(2131100769), null, 0, getText(2131100001), getText(2131100002), new DialogInterface.OnClickListener(this) {
                final /* synthetic */ ShareActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }, new DialogInterface.OnClickListener(this) {
                final /* synthetic */ ShareActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    this.this$0.showUnbindDialog(false);
                    dialogInterface.cancel();
                }
            }, 0, 0, 0, 0);
        } else if (NetworkUtils.isNetworkAvailable()) {
            LetvTencentWeiboShare.login((Activity) this, this.mShareAlbumBean, 0, 1, -1, "", -1, "", "");
        } else {
            ToastUtils.showToast((Context) this, 2131101012);
        }
    }

    private void refreshBindState() {
        this.mSinaBindStatus = LetvSinaShareSSO.isLogin(this);
        LogInfo.log("ZSM", "LetvSinaShareSSO accessToken == " + LetvSinaShareSSO.accessToken.getToken() + "  nickName == " + PreferencesManager.getInstance().getNickName());
        if (!(LetvSinaShareSSO.accessToken == null || TextUtils.isEmpty(LetvSinaShareSSO.accessToken.getToken()))) {
            WeiboGetUerNameTask weiboGetUerNameTask = new WeiboGetUerNameTask(getActivity(), "3830215581", LetvSinaShareSSO.accessToken.getToken(), "");
        }
        if (!(LetvSinaShareSSO.accessToken2 == null || TextUtils.isEmpty(LetvSinaShareSSO.accessToken2.getToken()))) {
            weiboGetUerNameTask = new WeiboGetUerNameTask(getActivity(), "3830215581", LetvSinaShareSSO.accessToken2.getToken(), "");
        }
        this.mTencentBindStatus = LetvTencentWeiboShare.isLogin(this);
        updateUI(0);
    }

    private void updateUI(int type) {
        if (this.mSinaBindStatus == 0 || this.mTencentBindStatus == 2) {
            setViewText(this.mSinaText, false);
        } else if (this.mSinaBindStatus == 1) {
            setViewText(this.mSinaText, true);
        }
        if (this.mTencentBindStatus == 0 || this.mTencentBindStatus == 2) {
            setViewText(this.mWeiboText, false);
        } else if (this.mTencentBindStatus == 1) {
            setViewText(this.mWeiboText, true);
        }
    }

    private void setViewText(TextView v, boolean isBound) {
        if (isBound) {
            v.setText(2131100769);
            v.setTextColor(getResources().getColor(2131493280));
            return;
        }
        v.setText(2131099878);
        v.setTextColor(getResources().getColor(2131493248));
    }

    private void showUnbindDialog(boolean isSina) {
        if (isSina) {
            LetvSinaShareSSO.logout(this);
        } else {
            LetvTencentWeiboShare.logout(this);
        }
        refreshBindState();
        ToastUtils.showToast((Context) this, getString(2131101038));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mSsoHandler != null) {
            this.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public String getActivityName() {
        return ShareActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
