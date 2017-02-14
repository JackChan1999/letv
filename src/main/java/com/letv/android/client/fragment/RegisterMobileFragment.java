package com.letv.android.client.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.view.DeleteButtonEditText;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.AcodeBean;
import com.letv.core.bean.ChechMobBean;
import com.letv.core.bean.RegisterResultBean;
import com.letv.core.bean.SendMobileMessageBean;
import com.letv.core.bean.UserBean;
import com.letv.core.constant.LetvConstant;
import com.letv.core.network.volley.VolleyRequest.HttpRequestMethod;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.AbeanParser;
import com.letv.core.parser.CheckMobParder;
import com.letv.core.parser.RegisterResultParser;
import com.letv.core.parser.SendMobileMessageParser;
import com.letv.core.parser.UserParser;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.MD5;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.http.client.HttpClient;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;

public class RegisterMobileFragment extends LetvBaseFragment implements OnClickListener {
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue(10);
    private static final ThreadFactory sThreadFactory = new 1();
    public boolean isTimes;
    private ImageView mAgreeView;
    private TextView mAgreeViewPhone;
    private DeleteButtonEditText mAuthCodeView;
    public String mCookid;
    private ImageView mDeleteVerIv;
    private Button mGetAuthCodeBtn;
    private Handler mHandler;
    private boolean mIsAgreeProtol;
    private View mLineMobile;
    private View mLinePwd;
    private View mLineVer;
    private View mLineVerify;
    private DeleteButtonEditText mPasswordEt;
    private DeleteButtonEditText mPhomeNumEt;
    private ImageView mRefreshIv;
    private ImageView mRegistVerCodeIv;
    private Button mRegisterBtn;
    private View mRootView;
    private EditText mVerificationCodeEt;
    public int times;

    public RegisterMobileFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mIsAgreeProtol = true;
        this.times = 60;
        this.isTimes = false;
        this.mHandler = new 3(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(R.layout.register_mobile, null, false);
        return this.mRootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        this.mAgreeView = (ImageView) this.mRootView.findViewById(R.id.regist_agreeCheckBtn);
        this.mPhomeNumEt = (DeleteButtonEditText) this.mRootView.findViewById(R.id.regist_phone_edit);
        this.mAuthCodeView = (DeleteButtonEditText) this.mRootView.findViewById(R.id.regist_phoneCheckNum_edit);
        this.mGetAuthCodeBtn = (Button) this.mRootView.findViewById(R.id.regist_getAuthCode);
        this.mPasswordEt = (DeleteButtonEditText) this.mRootView.findViewById(R.id.regist_password_edit);
        this.mAgreeViewPhone = (TextView) this.mRootView.findViewById(R.id.regist_protocol_txt_phone);
        this.mRegisterBtn = (Button) this.mRootView.findViewById(R.id.regist_btnLogin_phone);
        this.mLineMobile = this.mRootView.findViewById(R.id.line_mobile);
        this.mLineVerify = this.mRootView.findViewById(R.id.line_verify);
        this.mLinePwd = this.mRootView.findViewById(R.id.line_pwd);
        this.mLineVer = this.mRootView.findViewById(R.id.line_ver);
        this.mDeleteVerIv = (ImageView) this.mRootView.findViewById(R.id.delete_ver);
        this.mRefreshIv = (ImageView) this.mRootView.findViewById(R.id.refresh_ver);
        this.mRegistVerCodeIv = (ImageView) this.mRootView.findViewById(R.id.regist_VerificationCode);
        this.mDeleteVerIv.setOnClickListener(this);
        this.mRefreshIv.setOnClickListener(this);
        this.mVerificationCodeEt = (EditText) this.mRootView.findViewById(R.id.regist_getVerificationCode_edit);
        getVerificationCode();
        this.mPhomeNumEt.addTextChangedListener(new RegisterOnTextChangeListener(this.mLineMobile));
        this.mAuthCodeView.addTextChangedListener(new RegisterOnTextChangeListener(this.mLineVerify));
        this.mPasswordEt.addTextChangedListener(new RegisterOnTextChangeListener(this.mLinePwd));
        this.mVerificationCodeEt.addTextChangedListener(new RegisterOnTextChangeListener(this.mLineVer));
        this.mGetAuthCodeBtn.setOnClickListener(this);
        this.mAgreeViewPhone.setOnClickListener(this);
        this.mRegisterBtn.setOnClickListener(this);
        this.mAgreeView.setOnClickListener(this);
    }

    private void getVerificationCode() {
        String tm = System.currentTimeMillis() + "";
        StringBuilder md5Sb = new StringBuilder();
        md5Sb.append(tm + ",");
        md5Sb.append("U2dDsP92GbV81Dl");
        String key = MD5.toMd5(md5Sb.toString());
        if (NetworkUtils.isNetworkAvailable()) {
            getRequestGetverificationTask(key, tm);
        } else {
            ToastUtils.showToast(getActivity(), 2131100495);
        }
    }

    private void getRequestGetverificationTask(String key, String tm) {
        new LetvRequest(AcodeBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PlayRecordApi.getInstance().requestGetverificationCode(0, key, tm)).setCache(new VolleyNoCache()).setParser(new AbeanParser()).setHttpMethod(HttpRequestMethod.GET).setCallback(new 2(this)).add();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regist_btnLogin_phone /*2131364004*/:
                if (this.mIsAgreeProtol) {
                    doRegister();
                    StatisticsUtils.staticticsInfoPost(getActivity(), "a7", null, 2, -1, null, null, null, null, null);
                    StatisticsUtils.staticticsInfoPost(getActivity(), "0", "c831", null, 2, null, PageIdConstant.registerPage, null, null, null, null, null);
                    return;
                }
                ToastUtils.showToast(getActivity(), TipUtils.getTipMessage("1406", getString(2131099708)));
                return;
            case R.id.delete_ver /*2131364194*/:
                this.mVerificationCodeEt.setText("");
                return;
            case R.id.refresh_ver /*2131364196*/:
                getVerificationCode();
                return;
            case R.id.regist_getAuthCode /*2131364199*/:
                CheckMob();
                StatisticsUtils.staticticsInfoPost(getActivity(), "a7", null, 1, -1, null, null, null, null, null);
                StatisticsUtils.staticticsInfoPost(getActivity(), "0", "c831", null, 1, null, PageIdConstant.registerPage, null, null, null, null, null);
                return;
            case R.id.regist_agreeCheckBtn /*2131364202*/:
                if (this.mIsAgreeProtol) {
                    this.mIsAgreeProtol = false;
                    this.mAgreeView.setImageResource(2130837823);
                    return;
                }
                this.mAgreeView.setImageResource(2130837822);
                this.mIsAgreeProtol = true;
                return;
            case R.id.regist_protocol_txt_phone /*2131364203*/:
                gotoLetvProtocol();
                return;
            default:
                return;
        }
    }

    private void doGetAuthCode() {
        if (TextUtils.isEmpty(this.mPhomeNumEt.getText().toString())) {
            this.mPhomeNumEt.requestFocus();
            redMobileLine();
            ToastUtils.showToast(getActivity(), 2131100193);
        } else if (!LetvUtils.mobileNumberFormat(this.mPhomeNumEt.getText().toString())) {
            this.mPhomeNumEt.requestFocus();
            redMobileLine();
            ToastUtils.showToast(getActivity(), 2131100196);
        } else if (TextUtils.isEmpty(this.mVerificationCodeEt.getText().toString())) {
            ToastUtils.showToast(getActivity(), getString(2131100996));
            this.mVerificationCodeEt.requestFocus();
            redVerLine();
        } else if (this.mGetAuthCodeBtn.isEnabled()) {
            this.mGetAuthCodeBtn.setEnabled(false);
            sendPhoneMessage();
        }
    }

    private void sendPhoneMessage() {
        this.mGetAuthCodeBtn.setEnabled(false);
        new LetvRequest(SendMobileMessageBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PlayRecordApi.getInstance().s_sendMobile(0, this.mPhomeNumEt.getText().toString(), this.mVerificationCodeEt.getText().toString(), this.mCookid)).setCache(new VolleyNoCache()).setParser(new SendMobileMessageParser()).setHttpMethod(HttpRequestMethod.GET).setCallback(new 4(this)).add();
    }

    private boolean checkPhoneFormat() {
        if (TextUtils.isEmpty(this.mPhomeNumEt.getText().toString())) {
            ToastUtils.showToast(getActivity(), TipUtils.getTipMessage("1401", getString(2131100193)));
            this.mPhomeNumEt.requestFocus();
            redMobileLine();
            return false;
        } else if (!LetvUtils.mobileNumberFormat(this.mPhomeNumEt.getText().toString())) {
            ToastUtils.showToast(getActivity(), TipUtils.getTipMessage("1403", getString(2131100196)));
            this.mPhomeNumEt.requestFocus();
            redMobileLine();
            return false;
        } else if (TextUtils.isEmpty(this.mPasswordEt.getText().toString())) {
            ToastUtils.showToast(getActivity(), TipUtils.getTipMessage(LetvErrorCode.GET_TM_TANALYSISOF_URL_FAILURE, getString(2131100194)));
            this.mPasswordEt.requestFocus();
            redPwdLine();
            return false;
        } else if (!LetvUtils.passwordFormat(this.mPasswordEt.getText().toString())) {
            ToastUtils.showToast(getActivity(), TipUtils.getTipMessage("1407", getString(2131100708)));
            this.mPasswordEt.requestFocus();
            redPwdLine();
            return false;
        } else if (TextUtils.isEmpty(this.mVerificationCodeEt.getText().toString())) {
            ToastUtils.showToast(getActivity(), getString(2131100996));
            this.mVerificationCodeEt.requestFocus();
            redVerLine();
            return false;
        } else if (!TextUtils.isEmpty(this.mAuthCodeView.getText().toString().trim())) {
            return true;
        } else {
            ToastUtils.showToast(getActivity(), LetvTools.getTextFromServer("1404", getActivity().getResources().getString(2131100198)));
            this.mAuthCodeView.requestFocus();
            redVerifyLine();
            return false;
        }
    }

    private void gotoLetvProtocol() {
        new LetvWebViewActivityConfig(getActivity()).launch(LetvConstant.USER_PROTOCOL_URL, getResources().getString(2131100236));
    }

    private void doRegister() {
        if (!checkPhoneFormat()) {
            return;
        }
        if (NetworkUtils.isNetworkAvailable()) {
            requestRegisterTask(this.mPhomeNumEt.getText().toString(), this.mPasswordEt.getText().toString(), null, null, this.mAuthCodeView.getText().toString());
            return;
        }
        ToastUtils.showToast(getActivity(), 2131100495);
    }

    private void requestRegisterTask(String mobile, String password, String nickname, String gender, String vcode) {
        new LetvRequest(RegisterResultBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PlayRecordApi.getInstance().registerBaseUrl()).addPostParams(PlayRecordApi.getInstance().registerParameter(0, null, mobile, password, nickname, gender, "mapp", vcode)).setCache(new VolleyNoCache()).setParser(new RegisterResultParser()).setCallback(new 5(this, mobile, password)).add();
    }

    public void requestLoginTask(String mobile, String password) {
        String str = mobile;
        String str2 = password;
        new LetvRequest(UserBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PlayRecordApi.getInstance().loginBaseUrl()).addPostParams(PlayRecordApi.getInstance().login(0, str, str2, "mapp", "1", LetvUtils.getGSMInfo(getActivity()))).setCache(new VolleyNoCache()).setParser(new UserParser()).setCallback(new 6(this, mobile, password)).add();
    }

    private void CheckMob() {
        String mobile = this.mPhomeNumEt.getText().toString();
        if (TextUtils.isEmpty(this.mPhomeNumEt.getText().toString())) {
            ToastUtils.showToast(getActivity(), 2131100193);
            this.mPhomeNumEt.requestFocus();
            redMobileLine();
            return;
        }
        StringBuilder md5Sb = new StringBuilder();
        md5Sb.append(mobile + ",");
        md5Sb.append("3Des2Ts0ItfS32G");
        String key = MD5.toMd5(md5Sb.toString());
        if (NetworkUtils.isNetworkAvailable()) {
            requestCheckMob(mobile, key);
        } else {
            ToastUtils.showToast(getActivity(), 2131100495);
        }
    }

    private void requestCheckMob(String mobile, String key) {
        new LetvRequest(ChechMobBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PlayRecordApi.getInstance().requestCheckMob(0, mobile, key)).setCache(new VolleyNoCache()).setParser(new CheckMobParder()).setHttpMethod(HttpRequestMethod.GET).setCallback(new 7(this)).add();
    }

    private String getCaptchaId(HttpClient httpClient) {
        List<Cookie> cookies = ((AbstractHttpClient) httpClient).getCookieStore().getCookies();
        String captchaId = null;
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = (Cookie) cookies.get(i);
            String cookieName = cookie.getName();
            if (!TextUtils.isEmpty(cookieName) && cookieName.equals("captchaId")) {
                captchaId = cookie.getValue();
            }
        }
        return captchaId;
    }

    public String getTagName() {
        return null;
    }

    public int getContainerId() {
        return 0;
    }

    private void changeLineColor(View line, int color) {
        line.setBackgroundResource(color);
    }

    private void redVerifyLine() {
        changeLineColor(this.mLineVerify, 2131493369);
    }

    private void redMobileLine() {
        changeLineColor(this.mLineMobile, 2131493369);
    }

    private void redPwdLine() {
        changeLineColor(this.mLinePwd, 2131493369);
    }

    private void redVerLine() {
        changeLineColor(this.mLineVer, 2131493369);
    }

    public void onDestroy() {
        super.onDestroy();
        this.isTimes = false;
        this.mHandler.removeMessages(0);
    }
}
