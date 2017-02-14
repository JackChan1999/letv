package com.coolcloud.uac.android.api.view.basic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.coolcloud.uac.android.api.provider.LocalComplexProvider;
import com.coolcloud.uac.android.api.view.AssistActivity;
import com.coolcloud.uac.android.common.Config;
import com.coolcloud.uac.android.common.callback.ActivityResponse;
import com.coolcloud.uac.android.common.provider.Provider;
import com.coolcloud.uac.android.common.stat.DataEyeUtils;
import com.coolcloud.uac.android.common.util.KVUtils;
import com.coolcloud.uac.android.common.util.LOG;
import com.coolcloud.uac.android.common.util.SDKUtils;
import com.coolcloud.uac.android.common.util.SystemUtils;
import com.coolcloud.uac.android.common.util.TextUtils;
import com.coolcloud.uac.android.common.util.ToastHelper;
import com.coolcloud.uac.android.common.ws.SMSHelper;
import com.coolcloud.uac.android.common.ws.WsApi;
import com.facebook.internal.NativeProtocol;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BasicActivity extends Activity {
    private static final String CHANHECOLOR = "changCol";
    private static final String TAG = "BasicActivity";
    static List<Map<String, String>> mReplaceStrList = new ArrayList();
    protected String account;
    protected String appId;
    protected CustomResourceMgmt crMgmt;
    private ImageView mBackImage = null;
    protected RelativeLayout mBackLayout = null;
    Map<String, String> mReplaceStr;
    protected View mRootView;
    protected TextView mTitle = null;
    private WsApi mWsApi = null;
    private ProgressDialog progressDlg = null;
    protected int screenOrientation = 1;

    protected void onCreate(Bundle icicle, String layoutResId, String titleResId, String titleNameResId) {
        super.onCreate(icicle);
        if (this.crMgmt == null) {
            this.crMgmt = CustomResourceMgmt.get(getApplicationContext());
        }
        try {
            DataEyeUtils.report1Param("com.dataeye.DCAgent", "setReportMode", Integer.TYPE, Integer.valueOf(((Integer) DataEyeUtils.getField("com.dataeye.DCReportMode", "DC_AFTER_LOGIN")).intValue()));
        } catch (Throwable e) {
            LOG.w(TAG, "DCReportMode erroe : " + e);
        }
        setScreenOrientation();
        this.appId = KVUtils.get(getIntent(), "appId", "1000000");
        if (!TextUtils.empty(layoutResId)) {
            boolean isLandscape;
            if (getResources().getConfiguration().orientation == 2) {
                isLandscape = true;
            } else {
                isLandscape = false;
            }
            this.mRootView = CustomResourceMgmt.get(this).getLayout(layoutResId, isLandscape);
            setContentView(this.mRootView);
            if (!TextUtils.isEmpty(titleResId)) {
                onCreateTitle(titleResId, titleNameResId);
            }
            if (isNoTitleBarFullScreen()) {
                this.mRootView.findViewWithTag(titleResId).setVisibility(8);
            }
        }
        if (this.progressDlg == null) {
            this.progressDlg = new ProgressDialog(this);
            this.progressDlg.setCancelable(false);
            this.progressDlg.hide();
        }
    }

    protected void onCreate(Bundle icicle, int layoutResId, String titleResId, String titleNameResId) {
        super.onCreate(icicle);
        if (this.crMgmt == null) {
            this.crMgmt = CustomResourceMgmt.get(getApplicationContext());
        }
        try {
            DataEyeUtils.report1Param("com.dataeye.DCAgent", "setReportMode", Integer.TYPE, Integer.valueOf(((Integer) DataEyeUtils.getField("com.dataeye.DCReportMode", "DC_AFTER_LOGIN")).intValue()));
        } catch (Throwable e) {
            LOG.w(TAG, "DCReportMode erroe : " + e);
        }
        setScreenOrientation();
        this.appId = KVUtils.get(getIntent(), "appId", "1000000");
        if (layoutResId > 0) {
            this.mRootView = LayoutInflater.from(this).inflate(layoutResId, null);
            setContentView(this.mRootView);
            if (!TextUtils.isEmpty(titleResId)) {
                onCreateTitle(titleResId, titleNameResId);
            }
            if (isNoTitleBarFullScreen()) {
                this.mRootView.findViewWithTag(titleResId).setVisibility(8);
            }
        }
        if (this.progressDlg == null) {
            this.progressDlg = new ProgressDialog(this);
            this.progressDlg.setCancelable(false);
            this.progressDlg.hide();
        }
    }

    private boolean isNoTitleBarFullScreen() {
        return 101 == KVUtils.getInt(getIntent(), "style", 100);
    }

    protected void loadPrivateConfig() {
        String config = this.crMgmt.getString("uacConfig");
        if (!TextUtils.isEmpty(config)) {
            try {
                JSONArray mConfig = new JSONArray(config);
                for (int i = 0; i < mConfig.length(); i++) {
                    JSONObject mJSONObject = mConfig.getJSONObject(i);
                    if (TextUtils.equal(mJSONObject.getString("operation"), CHANHECOLOR)) {
                        doChangeTvBg(mJSONObject);
                    }
                }
            } catch (JSONException e) {
                LOG.w(TAG, "loadPrivateConfig error : ", e);
            }
        }
    }

    private void doChangeTvBg(JSONObject mJSONObject) {
        try {
            String mobject = mJSONObject.getString("object");
            String mMethods = mJSONObject.getString("methods");
            String mParameter = mJSONObject.getString("parameter");
            Field field = getClass().getDeclaredField(mobject);
            field.setAccessible(true);
            Object obj = field.get(this);
            obj.getClass().getMethod(mMethods, new Class[]{Integer.TYPE}).invoke(obj, new Object[]{Integer.valueOf(Color.parseColor(mParameter))});
        } catch (Exception e) {
            LOG.w(TAG, "doExecute error : ", e);
        }
    }

    @Deprecated
    protected void onCreate(Bundle icicle) {
        onCreate(icicle, null, null, null);
    }

    protected void onDestroy() {
        if (this.progressDlg != null) {
            this.progressDlg.dismiss();
            this.progressDlg = null;
        }
        super.onDestroy();
    }

    public Provider getProvider() {
        return LocalComplexProvider.get(this);
    }

    public SMSHelper getSMSAgent() {
        return SMSHelper.get(this);
    }

    public synchronized WsApi getWsApi() {
        if (this.mWsApi == null) {
            if (getMainLooper() != null) {
                this.mWsApi = WsApi.get(this, new Handler(getMainLooper()), getProvider());
            } else {
                this.mWsApi = WsApi.get(this, null, getProvider());
            }
        }
        return this.mWsApi;
    }

    public void showPrompt(final TextView view, final int error) {
        new Handler(getMainLooper()).post(new Runnable() {
            public void run() {
                if (view != null) {
                    view.setText(PromptResource.getResId(error));
                }
            }
        });
    }

    public void showPrompt(final TextView view, final String error) {
        new Handler(getMainLooper()).post(new Runnable() {
            public void run() {
                if (view != null) {
                    view.setText(error);
                }
            }
        });
    }

    public void showProgress(final boolean visible, final String resId) {
        String from = KVUtils.get(getIntent(), "from");
        if (!TextUtils.equal(from, "360gamecenter") && !TextUtils.equal(from, "360phonegame")) {
            new Handler(getMainLooper()).post(new Runnable() {
                public void run() {
                    if (visible) {
                        if (BasicActivity.this.progressDlg != null && !BasicActivity.this.progressDlg.isShowing()) {
                            BasicActivity.this.progressDlg.setMessage(L10NString.getString(resId));
                            BasicActivity.this.progressDlg.show();
                        }
                    } else if (BasicActivity.this.progressDlg != null && BasicActivity.this.progressDlg.isShowing()) {
                        BasicActivity.this.progressDlg.dismiss();
                    }
                }
            });
        }
    }

    public void showProgress(boolean visible) {
        showProgress(visible, "umgr_please_holdon");
    }

    public void showShortToast(int error) {
        showToast(PromptResource.getResId(error), 0);
    }

    public void showLongToast(int error) {
        showToast(PromptResource.getResId(error), 1);
    }

    public void showToast(String resId, int duration) {
        String res = null;
        if (TextUtils.empty(resId)) {
            resId = "umgr_rcode_fail";
        }
        try {
            res = L10NString.getString(resId);
        } catch (NotFoundException e) {
            LOG.e(TAG, "[resId:" + resId + "] get string failed(NotFoundException)", e);
        }
        try {
            if (!TextUtils.empty(res)) {
                ToastHelper.getInstance().shortOrLongToast((Context) this, res, duration);
            }
        } catch (NotFoundException e2) {
            LOG.e(TAG, "[resId:" + resId + "] show toast failed(NotFoundException)", e2);
        }
    }

    @SuppressLint({"NewApi"})
    private void setStatusBarTransparent(String titleResId) {
        ViewGroup title = (ViewGroup) this.mRootView.findViewWithTag(titleResId);
        if (VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(1280);
            getWindow().addFlags(67108864);
            if (title != null) {
                LayoutParams lp = title.getLayoutParams();
                lp.height = dip2px(72.0f);
                title.setLayoutParams(lp);
                RelativeLayout back = (RelativeLayout) this.mRootView.findViewWithTag("umgr_title_back_layout");
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) back.getLayoutParams();
                rlp.topMargin = dip2px(24.0f);
                back.setLayoutParams(rlp);
                RelativeLayout t = (RelativeLayout) this.mRootView.findViewWithTag("umgr_title_tv_layout");
                RelativeLayout.LayoutParams rrlp = (RelativeLayout.LayoutParams) t.getLayoutParams();
                rrlp.topMargin = dip2px(24.0f);
                t.setLayoutParams(rrlp);
            }
        }
    }

    protected int dip2px(float dpValue) {
        return (int) ((dpValue * getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void setScreenOrientation() {
        this.screenOrientation = KVUtils.getInt(getIntent(), "screenOrientation", 1);
        setRequestedOrientation(this.screenOrientation);
    }

    protected boolean getScreenOrientation() {
        Intent input = getIntent();
        if (input == null || !input.hasExtra("screenOrientation")) {
            return false;
        }
        if (KVUtils.getInt(input, "screenOrientation", 1) == 0) {
            return true;
        }
        return false;
    }

    private void onCreateTitle(String titleResId, String titleNameResId) {
        this.mTitle = (TextView) this.mRootView.findViewWithTag("umgr_title_middle_name");
        this.mBackLayout = (RelativeLayout) this.mRootView.findViewWithTag("umgr_title_back_layout");
        this.mBackImage = (ImageView) this.mRootView.findViewWithTag("umgr_title_back");
        SDKUtils.setBackground(this.mBackImage, this.crMgmt.getDrawable("uac_title_back_new", false));
        this.mBackLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                BasicActivity.this.handleCancelOnFinish();
                ((InputMethodManager) BasicActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(BasicActivity.this.mBackLayout.getWindowToken(), 2);
            }
        });
        setStatusBarTransparent(titleResId);
        if (!TextUtils.empty(titleNameResId)) {
            String title = L10NString.getString(titleNameResId);
            if (TextUtils.empty(title)) {
                LOG.e(TAG, "[titleResId:" + titleResId + "] can't get title");
            } else {
                this.mTitle.setText(title);
            }
        }
    }

    protected void setViewFocus(EditText mInputEditText) {
        if (mInputEditText != null) {
            mInputEditText.setFocusable(true);
            mInputEditText.setFocusableInTouchMode(true);
            mInputEditText.requestFocus();
            mInputEditText.requestFocusFromTouch();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (4 == keyCode && event.getRepeatCount() == 0) {
            handleCancelOnFinish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void handleResultOnFinish(Bundle result) {
        LOG.i(TAG, "[result:" + result + "] handle result on finish ...");
        ActivityResponse response = (ActivityResponse) getIntent().getParcelableExtra("activityResponse");
        if (response != null) {
            response.onResult(result);
        }
        Intent data = new Intent();
        KVUtils.put(data, result);
        KVUtils.put(data, NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE, 0);
        setResult(-1, data);
        finish();
    }

    public void handleErrorOnFinish(int error, String message) {
        LOG.i(TAG, "[error:" + error + "][message:" + message + "] handle error on finish ...");
        ActivityResponse response = (ActivityResponse) getIntent().getParcelableExtra("activityResponse");
        if (response != null) {
            response.onError(error, message);
        }
        Intent data = new Intent();
        KVUtils.put(data, NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE, error);
        KVUtils.put(data, "message", message);
        setResult(-1, data);
        finish();
    }

    public void handleCancelOnFinish() {
        LOG.i(TAG, "handle cancel on finish ...");
        ActivityResponse response = (ActivityResponse) getIntent().getParcelableExtra("activityResponse");
        if (response != null) {
            response.onCancel();
        }
        Intent data = new Intent();
        KVUtils.put(data, NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE, -1);
        setResult(0, data);
        finish();
    }

    public Builder buildAlertDialog(Context context) {
        return SDKUtils.buildAlertDialog(context);
    }

    protected void beautyTextView(TextView mTextView, String str) {
        mTextView.setText(str);
        mTextView.setTextColor(Color.parseColor("#7b7b7b"));
        mTextView.setTextSize(16.0f);
    }

    protected void beautyHeadTextView(TextView mTextView, String str) {
        mTextView.setText(str);
        mTextView.setTextColor(Color.parseColor("#ffffff"));
        mTextView.setTextSize(16.0f);
        mTextView.setSingleLine(true);
    }

    protected void beautyColorTextView(TextView mTextView, String mColorStr, boolean isUnderLine, String str, OnClickListener mOnClickListener) {
        mTextView.setText(str);
        mTextView.setTextSize(16.0f);
        if (mColorStr == null) {
            mTextView.setTextColor(Color.parseColor("#7b7b7b"));
        } else {
            mTextView.setTextColor(Color.parseColor(mColorStr));
        }
        if (isUnderLine) {
            mTextView.getPaint().setFlags(8);
        }
        if (mOnClickListener != null) {
            mTextView.setOnClickListener(mOnClickListener);
        }
    }

    protected void beautyEditText(final EditText mEditText, String hintStr, TextWatcher mTextWatcher) {
        mEditText.setHint(hintStr);
        mEditText.setHintTextColor(Color.parseColor("#1e0d0d0d"));
        mEditText.setTextColor(Color.parseColor("#0d0d0d"));
        SDKUtils.setBackground(mEditText, this.crMgmt.getDrawable("uac_input", true));
        mEditText.setTextSize(16.0f);
        if (mTextWatcher != null) {
            mEditText.addTextChangedListener(mTextWatcher);
        }
        mEditText.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mEditText.setSelection(mEditText.length());
                mEditText.requestFocus();
                mEditText.setFocusable(true);
            }
        });
    }

    protected void beautyButtonGray(Button mButton, String str, OnClickListener mOnClickListener) {
        mButton.setText(str);
        mButton.setTextSize(16.0f);
        mButton.setOnClickListener(mOnClickListener);
        SDKUtils.setBackground(mButton, this.crMgmt.getStatusDrawable("common_btn_gray_pressed", "common_btn_gray_normal", true));
        mButton.setTextColor(this.crMgmt.createSelector("#868b8f", "#000000"));
    }

    protected void beautyButtonGreen(Button mButton, String str, OnClickListener mOnClickListener) {
        mButton.setText(str);
        mButton.setTextSize(16.0f);
        mButton.setOnClickListener(mOnClickListener);
        SDKUtils.setBackground(mButton, this.crMgmt.getStatusDrawable("common_btn_blue_pressed", "common_btn_blue_normal", true));
        mButton.setTextColor(this.crMgmt.createSelector("#ffffff", "#ffffff"));
    }

    protected void beautyCaptchaImV(ImageView mCaptchaImg, OnClickListener mOnClickListener) {
        mCaptchaImg.setOnClickListener(mOnClickListener);
    }

    protected void beautyCleanButton(Button mButton, OnClickListener mOnClickListener) {
        SDKUtils.setBackground(mButton, this.crMgmt.getStatusDrawable("uac_delete_press", "uac_delete_normal", false));
        mButton.setOnClickListener(mOnClickListener);
    }

    protected void beautyCheckButton(CheckBox mCheckBox, OnCheckedChangeListener mOnCheckedChangeListener) {
        mCheckBox.setButtonDrawable(this.crMgmt.getCheckStatusDrawable("uac_showpwd_normal", "uac_showpwd_press", false));
        mCheckBox.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    protected void beautyLicensButton(CheckBox mCheckBox, OnCheckedChangeListener mOnCheckedChangeListener) {
        mCheckBox.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    public void showPopupDialog(Dialog dialog) {
        try {
            Field field = dialog.getClass().getDeclaredField("mAlert");
            field.setAccessible(true);
            Object obj = field.get(dialog);
            field = obj.getClass().getDeclaredField("mHandler");
            field.setAccessible(true);
            field.set(obj, new ButtonHandler(dialog));
        } catch (SecurityException e) {
            LOG.e(TAG, "set button handler failed(SecurityException)", e);
        } catch (NoSuchFieldException e2) {
            LOG.e(TAG, "set button handler failed(NoSuchFieldException)", e2);
        } catch (IllegalArgumentException e3) {
            LOG.e(TAG, "set button handler failed(IllegalArgumentException)", e3);
        } catch (IllegalAccessException e4) {
            LOG.e(TAG, "set button handler failed(IllegalAccessException)", e4);
        } catch (Throwable t) {
            LOG.e(TAG, "set button handler failed(Throwable)", t);
        }
        dialog.show();
    }

    public void setViewFocus(View view) {
        if (view != null) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.requestFocusFromTouch();
        }
    }

    public void displaySoftInput(Context context, View view) {
        if (context != null && view != null) {
            ((InputMethodManager) context.getSystemService("input_method")).showSoftInput(view, 0);
        }
    }

    protected void onResume() {
        super.onResume();
        try {
            DataEyeUtils.report1Param("com.dataeye.DCAgent", "onResume", Context.class, this);
        } catch (Throwable e) {
            LOG.w(TAG, "onResume error : ", e);
        }
    }

    protected void onPause() {
        super.onPause();
        try {
            DataEyeUtils.report1Param("com.dataeye.DCAgent", "onPause", Context.class, this);
        } catch (Throwable e) {
            LOG.w(TAG, "onResume error : ", e);
        }
    }

    protected void setStatusBarTransparent() {
        if (VERSION.SDK_INT >= 19) {
            requestWindowFeature(9);
            requestWindowFeature(10);
            getWindow().getDecorView().setSystemUiVisibility(1280);
            getWindow().addFlags(67108864);
            LOG.d(TAG, "VERSION.SDK_INT =" + VERSION.SDK_INT);
            return;
        }
        LOG.d("CP_Common", "SDK < 19");
    }

    protected void startAssistView(String titleResId, String url) {
        if (SystemUtils.isConnectNet(this)) {
            try {
                if (TextUtils.equal(url, "http://passport.%s.com/help/agreement.html") || TextUtils.equal(url, "http://passport.%s.com/help/privacy.html")) {
                    String args = "qiku";
                    if (TextUtils.equal(Config.getUacBrand(), "coolpad")) {
                        args = "coolyun";
                    }
                    url = String.format(url, new Object[]{args});
                }
                Intent i = AssistActivity.getIntent(this, titleResId, url, this.appId);
                KVUtils.put(i, getIntent(), "screenOrientation");
                startActivity(i);
                return;
            } catch (Throwable t) {
                LOG.e(TAG, "[url:" + url + "][appId:" + this.appId + "] start assist activity failed(Throwable)", t);
                return;
            }
        }
        ToastHelper.getInstance().shortToast((Context) this, PromptResource.getResId(3000));
    }
}
