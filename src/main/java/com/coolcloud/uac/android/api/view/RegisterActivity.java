package com.coolcloud.uac.android.api.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.coolcloud.uac.android.api.view.basic.BasicActivity;
import com.coolcloud.uac.android.api.view.basic.L10NString;
import com.coolcloud.uac.android.common.QikuCaptchaData;
import com.coolcloud.uac.android.common.Rcode;
import com.coolcloud.uac.android.common.util.Device;
import com.coolcloud.uac.android.common.util.KVUtils;
import com.coolcloud.uac.android.common.util.LOG;
import com.coolcloud.uac.android.common.util.ToastHelper;
import com.coolcloud.uac.android.common.util.ValidUtils;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnCaptchaListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnCommonListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnRegisterListener;
import com.coolcloud.uac.android.common.ws.SMSHelper.OnRecvListener;
import com.letv.core.constant.LiveRoomConstant;

public class RegisterActivity extends BasicActivity implements OnClickListener {
    private static final String TAG = "RegisterActivity";
    private boolean abortCountdown = true;
    TextWatcher mAccountTextWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(RegisterActivity.this.mInputAccount.getText().toString())) {
                RegisterActivity.this.mClearInputAccount.setVisibility(8);
            } else {
                RegisterActivity.this.mClearInputAccount.setVisibility(0);
            }
        }
    };
    private TextView mAgreeClause1 = null;
    private TextView mAgreeClauseUser = null;
    private TextView mAgreement = null;
    private TextView mAnd = null;
    private ImageView mCaptchaImg;
    private Button mClearConfirmPassword = null;
    private Button mClearInputAccount = null;
    private Button mClearInputPassword = null;
    private EditText mConfirmPassword = null;
    TextWatcher mConfirmPasswordTextWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            if (com.coolcloud.uac.android.common.util.TextUtils.empty(RegisterActivity.this.mConfirmPassword.getText().toString())) {
                RegisterActivity.this.mClearConfirmPassword.setVisibility(4);
            } else {
                RegisterActivity.this.mClearConfirmPassword.setVisibility(0);
            }
        }
    };
    private Context mContext;
    private TextView mErrorPrompt = null;
    private Button mGetAuthCode = null;
    private int mGetCaptchaNum = 0;
    private EditText mInputAccount = null;
    private EditText mInputCaptcha;
    private EditText mInputCode = null;
    private EditText mInputPassword = null;
    TextWatcher mInputPasswordTextWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            if (com.coolcloud.uac.android.common.util.TextUtils.empty(RegisterActivity.this.mInputPassword.getText().toString())) {
                RegisterActivity.this.mClearInputPassword.setVisibility(4);
            } else {
                RegisterActivity.this.mClearInputPassword.setVisibility(0);
            }
        }
    };
    private TextView mPrivacy = null;
    private QikuCaptchaData mQikuCaptchaData = null;
    private Button mRegister = null;
    private CheckBox mShowConfirmPassword = null;
    private CheckBox mShowPassword = null;
    private View mStatusBar = null;

    protected void onCreate(Bundle savedInstanceState) {
        LOG.d(TAG, "on create ...");
        this.mContext = this;
        super.onCreate(savedInstanceState, "uac_sdk_register", "umgr_register_header", "umgr_title_register");
        this.mStatusBar = this.mRootView.findViewWithTag("umgr_register_header");
        initView();
        beautyView();
        LOG.i(TAG, "[appId:" + this.appId + "] on create done ...");
    }

    private void beautyView() {
        beautyEditText(this.mInputAccount, L10NString.getString("umgr_please_input_phone"), this.mAccountTextWatcher);
        beautyCleanButton(this.mClearInputAccount, this);
        beautyEditText(this.mInputCode, L10NString.getString("umgr_please_input_authcode"), null);
        beautyButtonGray(this.mGetAuthCode, L10NString.getString("umgr_get_authcode"), this);
        beautyEditText(this.mInputCaptcha, L10NString.getString("uac_captcha_hint"), null);
        beautyCaptchaImV(this.mCaptchaImg, this);
        refreshCaptchaImg();
        beautyEditText(this.mInputPassword, L10NString.getString("umgr_please_input_new_password"), this.mInputPasswordTextWatcher);
        beautyCleanButton(this.mClearInputPassword, this);
        beautyEditText(this.mConfirmPassword, L10NString.getString("umgr_please_confirm_new_password"), this.mConfirmPasswordTextWatcher);
        beautyCleanButton(this.mClearConfirmPassword, this);
        beautyCheckButton(this.mShowPassword, new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RegisterActivity.this.mInputPassword.setTransformationMethod(RegisterActivity.this.mShowPassword.isChecked() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
            }
        });
        beautyCheckButton(this.mShowConfirmPassword, new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RegisterActivity.this.mConfirmPassword.setTransformationMethod(RegisterActivity.this.mShowConfirmPassword.isChecked() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
            }
        });
        beautyButtonGreen(this.mRegister, L10NString.getString("umgr_complete_register"), this);
        beautyTextView(this.mAgreeClause1, L10NString.getString("umgr_login_agree_clause_1"));
        beautyTextView(this.mAgreeClauseUser, L10NString.getString("umgr_agree_clause_2_user"));
        beautyColorTextView(this.mAgreement, "#0099e5", true, L10NString.getString("umgr_agree_clause_2_agreement"), this);
        beautyTextView(this.mAnd, L10NString.getString("umgr_agree_clause_2_and"));
        beautyColorTextView(this.mPrivacy, "#0099e5", true, L10NString.getString("umgr_agree_clause_2_privacy"), this);
        this.mClearInputPassword.setOnClickListener(this);
        this.mClearConfirmPassword.setOnClickListener(this);
        loadPrivateConfig();
    }

    private void initView() {
        this.mInputAccount = (EditText) this.mRootView.findViewWithTag("umgr_register_input_username");
        this.mClearInputAccount = (Button) this.mRootView.findViewWithTag("umgr_register_clear_input_username");
        this.mInputCode = (EditText) this.mRootView.findViewWithTag("umgr_register_input_code");
        this.mGetAuthCode = (Button) this.mRootView.findViewWithTag("umgr_register_request_code");
        this.mInputCaptcha = (EditText) this.mRootView.findViewWithTag("umgr_register_input_captcha");
        this.mCaptchaImg = (ImageView) this.mRootView.findViewWithTag("umgr_register_request_captcha");
        this.mInputPassword = (EditText) this.mRootView.findViewWithTag("umgr_register_input_password");
        this.mClearInputPassword = (Button) this.mRootView.findViewWithTag("umgr_register_clear_input_password");
        this.mConfirmPassword = (EditText) this.mRootView.findViewWithTag("umgr_register_confirm_password");
        this.mClearConfirmPassword = (Button) this.mRootView.findViewWithTag("umgr_register_clear_confirm_password");
        this.mShowPassword = (CheckBox) this.mRootView.findViewWithTag("umgr_register_show_password");
        this.mShowConfirmPassword = (CheckBox) this.mRootView.findViewWithTag("umgr_register_show_confirm_password");
        this.mRegister = (Button) this.mRootView.findViewWithTag("umgr_register_submit");
        this.mAgreeClause1 = (TextView) this.mRootView.findViewWithTag("umgr_agree_clause_1");
        this.mAgreeClauseUser = (TextView) this.mRootView.findViewWithTag("umgr_agree_clause_2_user");
        this.mAgreement = (TextView) this.mRootView.findViewWithTag("umgr_agree_clause_2_agreement");
        this.mAnd = (TextView) this.mRootView.findViewWithTag("umgr_agree_clause_2_and");
        this.mPrivacy = (TextView) this.mRootView.findViewWithTag("umgr_agree_clause_2_privacy");
        this.mErrorPrompt = (TextView) this.mRootView.findViewWithTag("umgr_register_error_prompt");
    }

    protected void onDestroy() {
        this.abortCountdown = true;
        super.onDestroy();
    }

    private void refreshCaptchaImg() {
        final String captchaKey = Device.getMeidId(this) + System.currentTimeMillis();
        int height = dip2px(48.0f);
        int width = dip2px(120.0f);
        int length = this.mGetCaptchaNum + 4;
        if (length >= 5) {
            length = 5;
        }
        getWsApi().getCaptchaImg(this.appId, captchaKey, width + "", height + "", length + "", new OnCaptchaListener() {
            public void onDone(int rcode, byte[] img) {
                if (rcode != 0 || img == null) {
                    RegisterActivity.this.showPrompt(RegisterActivity.this.mErrorPrompt, rcode);
                    return;
                }
                RegisterActivity.this.mQikuCaptchaData = new QikuCaptchaData(captchaKey, img);
                RegisterActivity.this.refeshCaptcha(RegisterActivity.this.mQikuCaptchaData);
                RegisterActivity.this.mGetCaptchaNum = RegisterActivity.this.mGetCaptchaNum + 1;
            }
        });
    }

    public void refeshCaptcha(QikuCaptchaData mQikuCaptchaData) {
        this.mQikuCaptchaData = mQikuCaptchaData;
        byte[] bytes = mQikuCaptchaData.getCaptchaByte();
        if (bytes == null || bytes.length <= 0) {
            ToastHelper.getInstance().shortToast(this.mContext, "获取图形验证码失败 请点击重新获取");
            return;
        }
        this.mCaptchaImg.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        this.mInputCaptcha.setText("");
    }

    public void onClick(View v) {
        showPrompt(this.mErrorPrompt, "");
        String tag = String.valueOf(v.getTag());
        if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_register_request_code")) {
            this.abortCountdown = true;
            doGetActivateCode();
        } else if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_register_submit")) {
            this.abortCountdown = true;
            doRegisterPhone();
        } else if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_agree_clause_2_agreement")) {
            startAssistView("umgr_title_agreement", "http://passport.%s.com/help/agreement.html");
        } else if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_agree_clause_2_privacy")) {
            startAssistView("umgr_title_privacy", "http://passport.%s.com/help/privacy.html");
        } else if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_register_clear_input_password")) {
            this.mInputPassword.setText("");
        } else if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_register_clear_confirm_password")) {
            this.mConfirmPassword.setText("");
        } else if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_register_clear_input_username")) {
            this.mInputAccount.setText("");
        } else if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_register_request_captcha")) {
            refreshCaptchaImg();
        }
    }

    private void doGetActivateCode() {
        showPrompt(this.mErrorPrompt, "");
        final String account = this.mInputAccount.getText().toString();
        String captchaValue = this.mInputCaptcha.getText().toString();
        if (this.mQikuCaptchaData == null || this.mQikuCaptchaData.getCaptchaKey() == null) {
            showPrompt(this.mErrorPrompt, 1063);
            return;
        }
        String captcahKey = this.mQikuCaptchaData.getCaptchaKey();
        if (com.coolcloud.uac.android.common.util.TextUtils.isEmpty(captchaValue)) {
            showPrompt(this.mErrorPrompt, 1063);
        } else if (com.coolcloud.uac.android.common.util.TextUtils.isPhone(account)) {
            showProgress(true);
            getWsApi().registerPhoneGetActivateCodeSafe(account, this.appId, captcahKey, captchaValue, new OnCommonListener() {
                public void onDone(int rcode) {
                    LOG.i(RegisterActivity.TAG, "[account:" + account + "][appId:" + RegisterActivity.this.appId + "] register phone get activate code callback(" + rcode + ")");
                    RegisterActivity.this.showProgress(false);
                    if (rcode == 0) {
                        RegisterActivity.this.handleGetActivateCodeCallback(rcode);
                    } else if (Rcode.needRefreshCaptcha(rcode)) {
                        RegisterActivity.this.mInputCaptcha.setText("");
                        RegisterActivity.this.showPrompt(RegisterActivity.this.mErrorPrompt, rcode);
                        RegisterActivity.this.refreshCaptchaImg();
                    } else {
                        RegisterActivity.this.mInputCaptcha.setText("");
                        RegisterActivity.this.refreshCaptchaImg();
                        RegisterActivity.this.showPrompt(RegisterActivity.this.mErrorPrompt, rcode);
                    }
                }
            });
        } else {
            showPrompt(this.mErrorPrompt, 1000);
        }
    }

    private void handleGetActivateCodeCallback(int rcode) {
        if (rcode == 0) {
            this.abortCountdown = false;
            this.mGetAuthCode.setClickable(false);
            doCountDown(180, 0);
            getSMSAgent().recvAuthCode(new OnRecvListener() {
                public void onReceived(String code) {
                    LOG.i(RegisterActivity.TAG, "receive sms code callback(" + code + ")");
                    RegisterActivity.this.handleSMSAuthCodeCallback(code);
                }
            });
            return;
        }
        showPrompt(this.mErrorPrompt, rcode);
    }

    private void doCountDown(final int countdown, long delayMillis) {
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                int count = countdown;
                if (RegisterActivity.this.abortCountdown || count <= 0) {
                    LOG.i(RegisterActivity.TAG, "[abortCountdown:" + RegisterActivity.this.abortCountdown + "][count:" + count + "] countdown over ...");
                    RegisterActivity.this.mGetAuthCode.setText(L10NString.getString("umgr_get_authcode"));
                    RegisterActivity.this.mGetAuthCode.setClickable(true);
                    return;
                }
                String format = L10NString.getString("umgr_countdown_format");
                RegisterActivity.this.mGetAuthCode.setText(String.format(format, new Object[]{Integer.valueOf(count)}));
                RegisterActivity.this.doCountDown(count - 1, 1000);
            }
        }, delayMillis);
    }

    private void handleSMSAuthCodeCallback(final String code) {
        new Handler(getMainLooper()).post(new Runnable() {
            public void run() {
                RegisterActivity.this.mInputCode.setText(code);
            }
        });
    }

    private void doRegisterPhone() {
        final String code = new String(this.mInputCode.getText().toString());
        final String account = new String(this.mInputAccount.getText().toString());
        final String inputPassword = new String(this.mInputPassword.getText().toString());
        String confirmPassword = new String(this.mConfirmPassword.getText().toString());
        if (!com.coolcloud.uac.android.common.util.TextUtils.isPhone(account)) {
            showPrompt(this.mErrorPrompt, 1000);
        } else if (!ValidUtils.isCodeValid(code)) {
            showPrompt(this.mErrorPrompt, 5001);
        } else if (!ValidUtils.isPasswordValid(inputPassword)) {
            showPrompt(this.mErrorPrompt, (int) LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID);
        } else if (com.coolcloud.uac.android.common.util.TextUtils.equal(inputPassword, confirmPassword)) {
            showProgress(true);
            getWsApi().registerPhone(account, code, inputPassword, "", this.appId, new OnRegisterListener() {
                public void onDone(int rcode, String accountId) {
                    LOG.i(RegisterActivity.TAG, "[account:" + account + "][code:" + code + "][appId:" + RegisterActivity.this.appId + "] register phone callback(" + rcode + "," + accountId + ")");
                    RegisterActivity.this.showProgress(false);
                    RegisterActivity.this.handleRegisterCallback(rcode, account, inputPassword);
                }
            });
        } else {
            showPrompt(this.mErrorPrompt, 5002);
        }
    }

    private void handleRegisterCallback(int rcode, String account, String password) {
        if (rcode == 0) {
            Bundle result = new Bundle();
            KVUtils.put(result, "username", account);
            KVUtils.put(result, "password", password);
            handleResultOnFinish(result);
            return;
        }
        showPrompt(this.mErrorPrompt, rcode);
    }
}
