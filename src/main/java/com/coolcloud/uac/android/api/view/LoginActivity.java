package com.coolcloud.uac.android.api.view;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.coolcloud.uac.android.api.view.basic.BasicActivity;
import com.coolcloud.uac.android.api.view.basic.L10NString;
import com.coolcloud.uac.android.common.util.KVUtils;
import com.coolcloud.uac.android.common.util.LOG;
import com.coolcloud.uac.android.common.util.PreferenceUtil;
import com.coolcloud.uac.android.common.util.ValidUtils;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnActivateListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnLoginListener;
import com.coolcloud.uac.android.common.ws.EasyActivateHelper;
import com.coolcloud.uac.android.common.ws.SMSHelper;
import com.letv.core.constant.LiveRoomConstant;

public class LoginActivity extends BasicActivity implements OnClickListener {
    private static final int REQ_CODE_FINDPWD = 100;
    private static final int REQ_CODE_REGISTER = 101;
    private static final String TAG = "LoginActivity";
    TextWatcher mAccountTextWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(LoginActivity.this.mInputAccount.getText().toString())) {
                LoginActivity.this.mClearInputAccount.setVisibility(8);
            } else {
                LoginActivity.this.mClearInputAccount.setVisibility(0);
            }
        }
    };
    private TextView mAgreeClause1 = null;
    private TextView mAgreeClauseUser = null;
    private TextView mAgreement = null;
    private TextView mAnd = null;
    private Button mClearInputAccount = null;
    private Button mClearInputPassword = null;
    private Context mContext;
    private TextView mErrorPrompt = null;
    private TextView mFindpwd = null;
    private EditText mInputAccount = null;
    private RelativeLayout mInputAccountLayout = null;
    private EditText mInputPassword = null;
    private TextView mJoinUserPlanTv = null;
    private TextView mLicens = null;
    private Button mLogin = null;
    TextWatcher mPasswordTextWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(LoginActivity.this.mInputPassword.getText().toString())) {
                LoginActivity.this.mClearInputPassword.setVisibility(8);
            } else {
                LoginActivity.this.mClearInputPassword.setVisibility(0);
            }
        }
    };
    private TextView mPrivacy = null;
    private TextView mRegister = null;
    private CheckBox mShowPwd;
    private View mStatusBar = null;
    private TextView mSwitchAccount = null;
    private CheckBox mUserPlanChb = null;
    private TextView mUserPlanTv = null;
    private TextView mWith = null;

    protected void onCreate(Bundle icicle) {
        LOG.d(TAG, "on create ...");
        super.onCreate(icicle, "uac_sdk_login", "umgr_login_header", "umgr_title_login");
        this.mContext = this;
        this.mStatusBar = this.mRootView.findViewWithTag("umgr_login_header");
        initView();
        beautyView();
        init360Licens();
        String from = getIntent().getStringExtra("from");
        if (com.coolcloud.uac.android.common.util.TextUtils.equal("360phonegame", from) || com.coolcloud.uac.android.common.util.TextUtils.equal("360gamecenter", from)) {
            this.mSwitchAccount.setVisibility(0);
            this.mBackLayout.setVisibility(4);
            this.mWith.setVisibility(0);
            this.mLicens.setVisibility(0);
            this.mUserPlanChb.setVisibility(0);
            this.mJoinUserPlanTv.setVisibility(0);
            this.mUserPlanTv.setVisibility(0);
            beautyTextView(this.mAgreeClauseUser, "");
        } else {
            this.mSwitchAccount.setVisibility(8);
            this.mBackLayout.setVisibility(0);
            this.mWith.setVisibility(8);
            this.mLicens.setVisibility(8);
            this.mUserPlanChb.setVisibility(8);
            this.mJoinUserPlanTv.setVisibility(8);
            this.mUserPlanTv.setVisibility(8);
            beautyTextView(this.mAgreeClauseUser, L10NString.getString("umgr_agree_clause_2_user"));
        }
        String account = getIntent().getStringExtra("username");
        setAccountInfo(account, getIntent().getStringExtra("password"));
        LOG.i(TAG, "[account:" + account + "][password:..." + "[appId:" + this.appId + "[from:" + from + "] on create done ...");
    }

    private void beautyView() {
        beautyEditText(this.mInputAccount, L10NString.getString("umgr_please_input_username"), this.mAccountTextWatcher);
        beautyCleanButton(this.mClearInputAccount, this);
        this.mInputAccountLayout.setOnClickListener(this);
        beautyCleanButton(this.mClearInputPassword, this);
        this.mInputPassword.setOnClickListener(this);
        beautyEditText(this.mInputPassword, L10NString.getString("umgr_please_input_password"), this.mPasswordTextWatcher);
        beautyColorTextView(this.mRegister, "#007dc4", false, L10NString.getString("umgr_whether_register_ornot"), this);
        beautyColorTextView(this.mFindpwd, "#007dc4", false, L10NString.getString("umgr_whether_forget_password"), this);
        beautyColorTextView(this.mSwitchAccount, "#007dc4", false, L10NString.getString("umgr_third_login_qihoo_tip"), this);
        beautyButtonGreen(this.mLogin, L10NString.getString("umgr_login"), this);
        beautyTextView(this.mAgreeClause1, L10NString.getString("umgr_login_agree_clause_1"));
        beautyTextView(this.mAgreeClauseUser, L10NString.getString("umgr_agree_clause_2_user"));
        beautyColorTextView(this.mAgreement, "#0099e5", true, L10NString.getString("umgr_agree_clause_2_agreement"), this);
        beautyTextView(this.mAnd, L10NString.getString("umgr_agree_clause_2_and"));
        beautyColorTextView(this.mPrivacy, "#0099e5", true, L10NString.getString("umgr_agree_clause_2_privacy"), this);
        beautyCheckButton(this.mShowPwd, new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LoginActivity.this.mInputPassword.setTransformationMethod(LoginActivity.this.mShowPwd.isChecked() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
            }
        });
        loadPrivateConfig();
    }

    private void initView() {
        this.mInputAccountLayout = (RelativeLayout) this.mRootView.findViewWithTag("umgr_login_layout_input_username");
        this.mInputAccount = (EditText) this.mRootView.findViewWithTag("umgr_login_input_username");
        this.mClearInputAccount = (Button) this.mRootView.findViewWithTag("umgr_login_clear_input_username");
        this.mInputPassword = (EditText) this.mRootView.findViewWithTag("umgr_login_input_password");
        this.mClearInputPassword = (Button) this.mRootView.findViewWithTag("umgr_login_clear_input_password");
        this.mRegister = (TextView) this.mRootView.findViewWithTag("umgr_login_register");
        this.mFindpwd = (TextView) this.mRootView.findViewWithTag("umgr_login_findpwd");
        this.mErrorPrompt = (TextView) this.mRootView.findViewWithTag("umgr_login_error_prompt");
        this.mLogin = (Button) this.mRootView.findViewWithTag("umgr_login_submit");
        this.mAgreeClause1 = (TextView) this.mRootView.findViewWithTag("umgr_agree_clause_1");
        this.mAgreeClauseUser = (TextView) this.mRootView.findViewWithTag("umgr_agree_clause_2_user");
        this.mAgreement = (TextView) this.mRootView.findViewWithTag("umgr_agree_clause_2_agreement");
        this.mAnd = (TextView) this.mRootView.findViewWithTag("umgr_agree_clause_2_and");
        this.mPrivacy = (TextView) this.mRootView.findViewWithTag("umgr_agree_clause_2_privacy");
        this.mShowPwd = (CheckBox) this.mRootView.findViewWithTag("umgr_login_show_password");
        this.mSwitchAccount = (TextView) this.mRootView.findViewWithTag("umgr_login_switch");
    }

    public void onClick(View v) {
        showPrompt(this.mErrorPrompt, "");
        String mTag = String.valueOf(v.getTag());
        if (com.coolcloud.uac.android.common.util.TextUtils.equal(mTag, "umgr_login_clear_input_username")) {
            this.mInputAccount.setText("");
        }
        if (com.coolcloud.uac.android.common.util.TextUtils.equal(mTag, "umgr_login_clear_input_password")) {
            this.mInputPassword.setText("");
        }
        if (com.coolcloud.uac.android.common.util.TextUtils.equal(mTag, "umgr_login_findpwd")) {
            doFindpwd();
        }
        if (com.coolcloud.uac.android.common.util.TextUtils.equal(mTag, "umgr_login_switch")) {
            doQihooLogin();
        }
        if (com.coolcloud.uac.android.common.util.TextUtils.equal(mTag, "umgr_login_submit")) {
            doLogin();
        }
        if (com.coolcloud.uac.android.common.util.TextUtils.equal(mTag, "umgr_login_register")) {
            doRegister();
        }
        if (com.coolcloud.uac.android.common.util.TextUtils.equal(mTag, "umgr_agree_clause_2_agreement")) {
            startAssistView("umgr_title_agreement", "http://passport.%s.com/help/agreement.html");
        }
        if (com.coolcloud.uac.android.common.util.TextUtils.equal(mTag, "umgr_agree_clause_2_privacy")) {
            startAssistView("umgr_title_privacy", "http://passport.%s.com/help/privacy.html");
        }
    }

    private void doQihooLogin() {
        handleErrorOnFinish(10001, "");
    }

    private void handleLoginResultCallback(Bundle result) {
        handleResultOnFinish(result);
    }

    private void doLogin() {
        String account = this.mInputAccount.getText().toString();
        String password = this.mInputPassword.getText().toString();
        if (!ValidUtils.isAccountValid(account)) {
            showPrompt(this.mErrorPrompt, 5000);
        } else if (ValidUtils.isPasswordValid(password)) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.mInputAccount.getWindowToken(), 2);
            doLoginWithParams(account, password);
        } else {
            showPrompt(this.mErrorPrompt, (int) LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID);
        }
    }

    private void setAccountInfo(String account, String password) {
        if (!com.coolcloud.uac.android.common.util.TextUtils.empty(account)) {
            this.mInputAccount.setText(account);
            this.mInputAccount.setSelection(this.mInputAccount.length());
            if (!com.coolcloud.uac.android.common.util.TextUtils.empty(password)) {
                this.mInputPassword.setText(password);
                this.mInputPassword.setSelection(this.mInputPassword.length());
            }
        }
    }

    private void doLoginWithParams(final String account, final String password) {
        showProgress(true);
        getWsApi().login(account, password, this.appId, new OnLoginListener() {
            public void onDone(int rcode, String uid, String rtkt) {
                LOG.i(LoginActivity.TAG, "[account:" + account + "][password:..." + "][appId:" + LoginActivity.this.appId + "] login callback(" + rcode + "," + uid + "," + rtkt + ")");
                LoginActivity.this.showProgress(false);
                if (rcode == 0) {
                    Bundle result = new Bundle();
                    KVUtils.put(result, "uid", uid);
                    KVUtils.put(result, "rtkt", rtkt);
                    KVUtils.put(result, "username", account);
                    KVUtils.put(result, "loginsource", "qiku");
                    KVUtils.put(result, "inputaccount", account);
                    KVUtils.put(result, "password", password);
                    LoginActivity.this.handleLoginResultCallback(result);
                    return;
                }
                LoginActivity.this.showPrompt(LoginActivity.this.mErrorPrompt, rcode);
            }
        });
    }

    private void doFindpwd() {
        try {
            Intent i = new Intent(this, FindpwdActivity.class);
            KVUtils.put(i, "appId", this.appId);
            KVUtils.put(i, getIntent(), "screenOrientation");
            KVUtils.put(i, "phone", this.mInputAccount.getText().toString());
            KVUtils.put(i, "account_can_change", true);
            startActivityForResult(i, 100);
        } catch (Exception e) {
            LOG.e(TAG, "[appId:" + this.appId + "][reqeustCode:" + 100 + "] start findpwd activity failed(Exception)", e);
        }
    }

    private void doRegister() {
        Builder builder = buildAlertDialog(this);
        builder.setTitle(L10NString.getString("umgr_title_activate"));
        builder.setMessage(L10NString.getString("umgr_activate_clause"));
        builder.setNegativeButton(L10NString.getString("uac_dialog_other"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface di, int which) {
                di.dismiss();
                LoginActivity.this.doRegisterManually(101);
            }
        });
        builder.setPositiveButton(L10NString.getString("uac_dialog_activate"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface di, int which) {
                di.dismiss();
                LoginActivity.this.doEasyActivate(101);
            }
        });
        showPopupDialog(builder.create());
    }

    private void doRegisterManually(int type) {
        try {
            Intent i = new Intent(this, RegisterActivity.class);
            KVUtils.put(i, "appId", this.appId);
            KVUtils.put(i, getIntent(), "screenOrientation");
            startActivityForResult(i, type);
        } catch (Throwable t) {
            LOG.e(TAG, "[appId:" + this.appId + "][reqeustCode:" + type + "] start register activity failed(Exception)", t);
        }
    }

    private void doEasyActivate(final int type) {
        SMSHelper sh = SMSHelper.get(this);
        showProgress(true);
        EasyActivateHelper.get(this, this.appId, sh, getWsApi(), new Handler(getMainLooper()), new OnActivateListener() {
            public void onDone(int rcode, String phone, String uid, String tkt) {
                LOG.i(LoginActivity.TAG, "[appId:" + LoginActivity.this.appId + "] activate callback(" + rcode + "," + phone + "," + uid + "," + tkt + ")");
                LoginActivity.this.showProgress(false);
                if (rcode == 0) {
                    Bundle result = new Bundle();
                    KVUtils.put(result, "uid", uid);
                    KVUtils.put(result, "rtkt", tkt);
                    KVUtils.put(result, "username", phone);
                    KVUtils.put(result, "loginsource", "qiku");
                    KVUtils.put(result, "inputaccount", phone);
                    LoginActivity.this.handleResultOnFinish(result);
                } else if (1002 == rcode) {
                    LoginActivity.this.handlePhonePresent4Activate(phone, type);
                } else {
                    LoginActivity.this.showShortToast(10002);
                    LoginActivity.this.doRegisterManually(101);
                }
            }
        }).execute();
    }

    private void handlePhonePresent4Activate(String phone, final int type) {
        String fphone;
        if (com.coolcloud.uac.android.common.util.TextUtils.empty(phone)) {
            fphone = "";
        } else {
            fphone = phone;
        }
        Builder builder = buildAlertDialog(this);
        builder.setTitle(L10NString.getString("umgr_title_activate"));
        builder.setMessage(String.format(L10NString.getString("umgr_activate_phone_present_format"), new Object[]{fphone}));
        builder.setNegativeButton(L10NString.getString("uac_dialog_other"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface di, int which) {
                di.dismiss();
                LoginActivity.this.doRegisterManually(type);
            }
        });
        builder.setPositiveButton(L10NString.getString("umgr_login_directly"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface di, int which) {
                di.dismiss();
                LoginActivity.this.mInputAccount.setText(fphone);
                LoginActivity.this.setViewFocus(LoginActivity.this.mInputPassword);
            }
        });
        showPopupDialog(builder.create());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LOG.i(TAG, "[requestCode:" + requestCode + "] on activity result(" + resultCode + "," + data + "]");
        if (100 != requestCode && 101 != requestCode) {
            LOG.w(TAG, "[requestCode:" + requestCode + "] request code dismatch(" + 100 + "," + 101 + ")");
        } else if (-1 == resultCode && data != null) {
            handlerActivityResult(data.getExtras());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handlerActivityResult(final Bundle result) {
        if (result != null) {
            new Handler(getMainLooper()).post(new Runnable() {
                public void run() {
                    String account = KVUtils.get(result, "username");
                    String password = KVUtils.get(result, "password");
                    if (!com.coolcloud.uac.android.common.util.TextUtils.empty(account) && !com.coolcloud.uac.android.common.util.TextUtils.empty(password)) {
                        LoginActivity.this.mInputAccount.setText(account);
                        LoginActivity.this.mInputPassword.setText(password);
                        LoginActivity.this.doLogin();
                    }
                }
            });
        }
    }

    private void init360Licens() {
        this.mWith = (TextView) this.mRootView.findViewWithTag("umgr_agree_clause_2_with");
        this.mLicens = (TextView) this.mRootView.findViewWithTag("umgr_agree_clause_2_licens");
        this.mUserPlanChb = (CheckBox) this.mRootView.findViewWithTag("umgr_login_userplan_chb");
        this.mUserPlanChb.setChecked(PreferenceUtil.getInstance().getBoolean("360_user_plan", true));
        this.mJoinUserPlanTv = (TextView) this.mRootView.findViewWithTag("umgr_login_join_userplan_tv");
        this.mUserPlanTv = (TextView) this.mRootView.findViewWithTag("umgr_login_userplan_tv");
        beautyTextView(this.mWith, L10NString.getString("umgr_agree_clause_with"));
        beautyTextView(this.mJoinUserPlanTv, L10NString.getString("umgr_login_joinuserplan"));
        beautyColorTextView(this.mLicens, "#0099e5", true, L10NString.getString("umgr_agree_clause_licens"), new OnClickListener() {
            public void onClick(View v) {
                try {
                    LoginActivity.this.startActivity(AssistActivity.getIntent(LoginActivity.this.mContext, "umgr_login_licens_title", "http://qiku.gamebox.360.cn/1/common/serviceagreeoem"));
                } catch (Throwable t) {
                    LOG.e(LoginActivity.TAG, "start 360 licens (Throwable)", t);
                }
            }
        });
        beautyColorTextView(this.mUserPlanTv, "#0099e5", true, L10NString.getString("umgr_login_userplan"), new OnClickListener() {
            public void onClick(View v) {
                try {
                    LoginActivity.this.startActivity(AssistActivity.getIntent(LoginActivity.this.mContext, "umgr_login_userplan_title", "http://qiku.gamebox.360.cn/1/common/serviceimprovementoem"));
                } catch (Throwable t) {
                    LOG.e(LoginActivity.TAG, "start 360 licens (Throwable)", t);
                }
            }
        });
        beautyLicensButton(this.mUserPlanChb, new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceUtil.getInstance().putBoolean("360_user_plan", isChecked);
            }
        });
    }
}
