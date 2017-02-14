package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.commonlib.view.LoadingDialog;
import com.letv.android.client.utils.UIs;
import com.letv.android.client.view.TopViewBack;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class ChinaMobileWebPayActivity extends WrapActivity {
    private ImageView backBtn;
    private String expire;
    private String loadUrlString;
    private LoadingDialog loadingDialog;
    private String pname;
    private String price;
    private TopViewBack top;
    private String urlString;
    private WebView zfbwebview_activity_webview;
    private WebViewClient zfbwebview_activity_webview_client;

    public ChinaMobileWebPayActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void launch(Activity context, String url, String pname, String expire, String price) {
        Intent intent = new Intent(context, ChinaMobileWebPayActivity.class);
        intent.putExtra("posturl", url);
        intent.putExtra("pname", pname);
        intent.putExtra("expire", expire);
        intent.putExtra("price", price);
        context.startActivityForResult(intent, 17);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zfbwebview_activity);
        this.urlString = getIntent().getStringExtra("posturl");
        LogInfo.log("vip_", getClass().getSimpleName() + " payChinaMobile url = " + this.urlString);
        this.pname = getIntent().getStringExtra("pname");
        this.expire = getIntent().getStringExtra("expire");
        this.price = getIntent().getStringExtra("price");
        this.zfbwebview_activity_webview = (WebView) findViewById(R.id.zfbwebview_activity_webview);
        this.top = (TopViewBack) findViewById(2131361833);
        this.top.setTitle(this.mContext.getString(2131100577));
        this.backBtn = (ImageView) findViewById(R.id.top_button);
        this.backBtn.setVisibility(0);
        this.backBtn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ChinaMobileWebPayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                this.this$0.onKeyDown(4, null);
            }
        });
        this.zfbwebview_activity_webview.getSettings().setJavaScriptEnabled(true);
        this.zfbwebview_activity_webview.getSettings().setSupportZoom(true);
        this.zfbwebview_activity_webview.requestFocus();
        this.zfbwebview_activity_webview_client = new WebViewClient(this) {
            final /* synthetic */ ChinaMobileWebPayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                this.this$0.loadUrlString = url;
                if (this.this$0.loadUrlString.startsWith("http://zhifu.letv.com/pagenotify")) {
                    view.stopLoading();
                    PaySucceedActivity.launch(this.this$0, this.this$0.pname, this.this$0.expire, this.this$0.price, this.this$0.getResources().getString(2131099772), "");
                    this.this$0.setResult(257);
                    this.this$0.finish();
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                this.this$0.showLoadingDialog();
            }

            public void onPageFinished(WebView view, String url) {
                this.this$0.dismisLoadingDialog();
            }
        };
        this.zfbwebview_activity_webview.setWebViewClient(this.zfbwebview_activity_webview_client);
        this.zfbwebview_activity_webview.loadUrl(this.urlString);
    }

    private void showLoadingDialog() {
        dismisLoadingDialog();
        this.loadingDialog = new LoadingDialog((Context) this, 2131100006);
        this.loadingDialog.show();
        this.loadingDialog.setOnCancelListener(new OnCancelListener(this) {
            final /* synthetic */ ChinaMobileWebPayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onCancel(DialogInterface dialog) {
                this.this$0.showFailedDialog();
            }
        });
    }

    private void dismisLoadingDialog() {
        if (this.loadingDialog != null && this.loadingDialog.isShowing()) {
            this.loadingDialog.dismiss();
        }
        this.loadingDialog = null;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private void showFailedDialog() {
        UIs.call(this, 2131099760, 2131099683, new DialogInterface.OnClickListener(this) {
            final /* synthetic */ ChinaMobileWebPayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(DialogInterface dialog, int which) {
                this.this$0.finish();
            }
        }, new DialogInterface.OnClickListener(this) {
            final /* synthetic */ ChinaMobileWebPayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 17) {
            setResult(resultCode);
        }
        finish();
    }

    public String getActivityName() {
        return ChinaMobileWebPayActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
