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
import com.coolcloud.uac.android.common.ws.SMSHelper.OnRecvListener;
import com.letv.core.constant.LiveRoomConstant;

public class FindpwdActivity extends BasicActivity implements OnClickListener {
    private static final String TAG = "FindpwdActivity";
    private boolean abortCountdown = true;
    TextWatcher mAccountTextWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(FindpwdActivity.this.mInputAccount.getText().toString())) {
                FindpwdActivity.this.mClearInputAccount.setVisibility(8);
            } else {
                FindpwdActivity.this.mClearInputAccount.setVisibility(0);
            }
        }
    };
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
            if (com.coolcloud.uac.android.common.util.TextUtils.empty(FindpwdActivity.this.mConfirmPassword.getText().toString())) {
                FindpwdActivity.this.mClearConfirmPassword.setVisibility(4);
            } else {
                FindpwdActivity.this.mClearConfirmPassword.setVisibility(0);
            }
        }
    };
    private Context mContext;
    private TextView mErrorPrompt = null;
    private Button mFindpwd = null;
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
            if (com.coolcloud.uac.android.common.util.TextUtils.empty(FindpwdActivity.this.mInputPassword.getText().toString())) {
                FindpwdActivity.this.mClearInputPassword.setVisibility(4);
            } else {
                FindpwdActivity.this.mClearInputPassword.setVisibility(0);
            }
        }
    };
    private QikuCaptchaData mQikuCaptchaData = null;
    private CheckBox mShowConfirmPassword = null;
    private CheckBox mShowPassword = null;
    private View mStatusBar = null;

    protected void onCreate(Bundle savedInstanceState) {
        LOG.d(TAG, "on create ...");
        this.mContext = this;
        super.onCreate(savedInstanceState, "uac_sdk_findpwd", "umgr_findpwd_header", "umgr_title_findpwd");
        this.mStatusBar = this.mRootView.findViewWithTag("umgr_findpwd_header");
        initView();
        beautyView();
        String phone = KVUtils.get(getIntent(), "phone");
        boolean canChange = KVUtils.get(getIntent(), "account_can_change", false);
        if (com.coolcloud.uac.android.common.util.TextUtils.isPhone(phone)) {
            this.mInputAccount.setText(phone);
            this.mInputAccount.setFocusable(canChange);
            this.mInputCode.setFocusable(true);
        }
        LOG.i(TAG, "[phone:" + phone + "][canChange:" + canChange + "][appId:" + this.appId + "] on create done ...");
    }

    private void beautyView() {
        beautyCleanButton(this.mClearInputAccount, this);
        this.mInputAccount.setOnClickListener(this);
        beautyEditText(this.mInputAccount, L10NString.getString("umgr_please_input_phone"), this.mAccountTextWatcher);
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
                FindpwdActivity.this.mInputPassword.setTransformationMethod(FindpwdActivity.this.mShowPassword.isChecked() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
            }
        });
        beautyCheckButton(this.mShowConfirmPassword, new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FindpwdActivity.this.mConfirmPassword.setTransformationMethod(FindpwdActivity.this.mShowConfirmPassword.isChecked() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
            }
        });
        beautyButtonGreen(this.mFindpwd, L10NString.getString("umgr_findpwd"), this);
        loadPrivateConfig();
    }

    private void initView() {
        this.mInputAccount = (EditText) this.mRootView.findViewWithTag("umgr_findpwd_input_username");
        this.mClearInputAccount = (Button) this.mRootView.findViewWithTag("umgr_findpwd_clear_input_username");
        this.mGetAuthCode = (Button) this.mRootView.findViewWithTag("umgr_findpwd_request_code");
        this.mInputCode = (EditText) this.mRootView.findViewWithTag("umgr_findpwd_input_code");
        this.mInputCaptcha = (EditText) this.mRootView.findViewWithTag("umgr_findpwd_input_captcha");
        this.mCaptchaImg = (ImageView) this.mRootView.findViewWithTag("umgr_findpwd_request_captcha");
        this.mInputPassword = (EditText) this.mRootView.findViewWithTag("umgr_findpwd_input_password");
        this.mClearInputPassword = (Button) this.mRootView.findViewWithTag("umgr_findpwd_clear_input_password");
        this.mConfirmPassword = (EditText) this.mRootView.findViewWithTag("umgr_findpwd_confirm_password");
        this.mClearConfirmPassword = (Button) this.mRootView.findViewWithTag("umgr_findpwd_clear_confirm_password");
        this.mErrorPrompt = (TextView) this.mRootView.findViewWithTag("umgr_findpwd_error_prompt");
        this.mFindpwd = (Button) this.mRootView.findViewWithTag("umgr_findpwd_submit");
        this.mShowPassword = (CheckBox) this.mRootView.findViewWithTag("umgr_findpwd_show_password");
        this.mShowConfirmPassword = (CheckBox) this.mRootView.findViewWithTag("umgr_findpwd_show_onfirm_password");
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
                    FindpwdActivity.this.showPrompt(FindpwdActivity.this.mErrorPrompt, rcode);
                    return;
                }
                FindpwdActivity.this.mQikuCaptchaData = new QikuCaptchaData(captchaKey, img);
                FindpwdActivity.this.refeshCaptcha(FindpwdActivity.this.mQikuCaptchaData);
                FindpwdActivity.this.mGetCaptchaNum = FindpwdActivity.this.mGetCaptchaNum + 1;
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

    protected void onDestroy() {
        this.abortCountdown = true;
        super.onDestroy();
    }

    public void onClick(View v) {
        showPrompt(this.mErrorPrompt, "");
        String tag = String.valueOf(v.getTag());
        if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_findpwd_request_code")) {
            this.abortCountdown = true;
            doGetActivateCode();
        } else if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_findpwd_submit")) {
            this.abortCountdown = true;
            doFindpwd();
        } else if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_findpwd_clear_input_password")) {
            this.mInputPassword.setText("");
        } else if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_findpwd_clear_confirm_password")) {
            this.mConfirmPassword.setText("");
        } else if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_findpwd_clear_input_username")) {
            this.mInputAccount.setText("");
        } else if (com.coolcloud.uac.android.common.util.TextUtils.equal(tag, "umgr_findpwd_request_captcha")) {
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
            getWsApi().findpwdPhoneGetActivateCodeSafe(account, this.appId, captcahKey, captchaValue, new OnCommonListener() {
                public void onDone(int rcode) {
                    LOG.i(FindpwdActivity.TAG, "[account:" + account + "][appId:" + FindpwdActivity.this.appId + "] findpwd phone get activate code callback(" + rcode + ")");
                    FindpwdActivity.this.showProgress(false);
                    if (rcode == 0) {
                        FindpwdActivity.this.handleGetActivateCodeCallback(rcode);
                    } else if (Rcode.needRefreshCaptcha(rcode)) {
                        FindpwdActivity.this.mInputCaptcha.setText("");
                        FindpwdActivity.this.showPrompt(FindpwdActivity.this.mErrorPrompt, rcode);
                        FindpwdActivity.this.refreshCaptchaImg();
                    } else {
                        FindpwdActivity.this.mInputCaptcha.setText("");
                        FindpwdActivity.this.refreshCaptchaImg();
                        FindpwdActivity.this.showPrompt(FindpwdActivity.this.mErrorPrompt, rcode);
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
                    LOG.i(FindpwdActivity.TAG, "receive sms code callback(" + code + ")");
                    FindpwdActivity.this.handleSMSAuthCodeCallback(code);
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
                if (FindpwdActivity.this.abortCountdown || count <= 0) {
                    LOG.i(FindpwdActivity.TAG, "[abortCountdown:" + FindpwdActivity.this.abortCountdown + "][count:" + count + "] countdown over ...");
                    FindpwdActivity.this.mGetAuthCode.setText(L10NString.getString("umgr_get_authcode"));
                    FindpwdActivity.this.mGetAuthCode.setClickable(true);
                    return;
                }
                String format = L10NString.getString("umgr_countdown_format");
                FindpwdActivity.this.mGetAuthCode.setText(String.format(format, new Object[]{Integer.valueOf(count)}));
                FindpwdActivity.this.doCountDown(count - 1, 1000);
            }
        }, delayMillis);
    }

    private void handleSMSAuthCodeCallback(final String code) {
        new Handler(getMainLooper()).post(new Runnable() {
            public void run() {
                FindpwdActivity.this.mInputCode.setText(code);
            }
        });
    }

    private void doFindpwd() {
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
            getWsApi().findpwdPhoneSetPwd(account, code, inputPassword, this.appId, new OnCommonListener() {
                public void onDone(int rcode) {
                    LOG.i(FindpwdActivity.TAG, "[account:" + account + "[code:" + code + "][appId:" + FindpwdActivity.this.appId + "] findpwd phone set pwd callback(" + rcode + ")");
                    FindpwdActivity.this.showProgress(false);
                    FindpwdActivity.this.handleFindpwdCallback(rcode, account, inputPassword);
                }
            });
        } else {
            showPrompt(this.mErrorPrompt, 5002);
        }
    }

    private void handleFindpwdCallback(int rcode, String account, String password) {
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
