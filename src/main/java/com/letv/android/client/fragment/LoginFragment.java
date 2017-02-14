package com.letv.android.client.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.internal.view.SupportMenu;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.activity.FindLetvAccountPasswordActivity;
import com.letv.android.client.activity.RegisterActivity;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.task.RequestUserByTokenTask;
import com.letv.android.client.thirdpartlogin.ThirdPartLoginLayout;
import com.letv.android.client.thirdpartlogin.ThirdPartLoginLayout.ThirdPartLoginSuccessCallBack;
import com.letv.android.client.view.DeleteButtonEditText;
import com.letv.android.client.view.EmailAutoCompleteTextView;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.UserBean;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.UserParser;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

@SuppressLint({"ValidFragment"})
public class LoginFragment extends LetvBaseFragment implements OnClickListener, ThirdPartLoginSuccessCallBack {
    private ImageView mBackImageView;
    private TextView mCallPhoneTv;
    private TextView mFindPasswordTv;
    private Button mLetvLoginBtn;
    private LoginFrom mLoginFrom;
    private LoginSuccessCallback mLoginSuccessCallback;
    private String mPassword;
    private DeleteButtonEditText mPasswordBtnEt;
    private View mPasswordView;
    private TextView mRegisterText;
    private PublicLoadLayout mRootView;
    public ThirdPartLoginLayout mThirdPartLoginLayout;
    private View mTileView;
    private TextView mTitleTv;
    private String mUserName;
    private EmailAutoCompleteTextView mUserNameTv;
    private View mUserView;

    public interface LoginSuccessCallback {
        void loginSuccess();
    }

    public enum LoginFrom {
        LOGIN,
        SETTINGS_LOGIN
    }

    static class RegisterOnTextChangeListener implements TextWatcher {
        View line;

        public RegisterOnTextChangeListener(View line) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.line = line;
        }

        public void afterTextChanged(Editable s) {
            LogInfo.log("register", "afterTextChanged");
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            LogInfo.log("register", "beforeTextChanged");
            this.line.setBackgroundResource(2131493280);
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            LogInfo.log("register", "onTextChanged");
            this.line.setBackgroundResource(2131493321);
        }
    }

    public void thirdPartLoginSuccess(UserBean userBean) {
        setPlayerResult(userBean);
    }

    public void loading(boolean isLoading) {
        if (this.mRootView != null) {
            if (isLoading) {
                this.mRootView.loading(true);
            } else {
                this.mRootView.finish();
            }
        }
    }

    public LoginFragment(LoginFrom mLoginFrom, LoginSuccessCallback mLoginSuccessCallback) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mLoginFrom = mLoginFrom;
        this.mLoginSuccessCallback = mLoginSuccessCallback;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = PublicLoadLayout.createPage(getActivity(), (int) R.layout.letv_login);
        return this.mRootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    public void onResume() {
        super.onResume();
        LogInfo.log("LoginFragment ", "onResume");
        if (!TextUtils.isEmpty(PreferencesManager.getInstance().getLoginName()) && TextUtils.isEmpty(this.mUserNameTv.getText())) {
            this.mUserNameTv.setText(PreferencesManager.getInstance().getLoginName());
            this.mUserNameTv.setSelection(PreferencesManager.getInstance().getLoginName().length());
        }
        LogInfo.log("ZSM++ 登陆页曝光");
        LogInfo.LogStatistics("登陆页曝光");
        StatisticsUtils.staticticsInfoPost(this.mContext, "19", null, null, -1, null, PageIdConstant.loginPage, null, null, null, null, null);
    }

    private void initUI() {
        this.mTileView = this.mRootView.findViewById(R.id.setting_info_title);
        if (this.mLoginFrom == LoginFrom.SETTINGS_LOGIN) {
            this.mTileView.setVisibility(8);
        }
        this.mTitleTv = (TextView) this.mTileView.findViewById(2131362147);
        this.mTitleTv.setText(getActivity().getResources().getString(2131100344));
        this.mBackImageView = (ImageView) this.mTileView.findViewById(R.id.btn_back);
        this.mBackImageView.setOnClickListener(this);
        this.mLetvLoginBtn = (Button) this.mRootView.findViewById(R.id.letv_login_btn);
        this.mLetvLoginBtn.setOnClickListener(this);
        this.mUserNameTv = (EmailAutoCompleteTextView) this.mRootView.findViewById(R.id.letv_account);
        this.mPasswordBtnEt = (DeleteButtonEditText) this.mRootView.findViewById(R.id.letv_account_password);
        this.mUserView = this.mRootView.findViewById(R.id.username_line);
        this.mPasswordView = this.mRootView.findViewById(R.id.password_line);
        this.mRegisterText = (TextView) this.mRootView.findViewById(R.id.letv_account_registe_btn);
        this.mRegisterText.setOnClickListener(this);
        this.mFindPasswordTv = (TextView) this.mRootView.findViewById(R.id.letv_account_forgetpass_btn);
        this.mFindPasswordTv.setOnClickListener(this);
        this.mThirdPartLoginLayout = (ThirdPartLoginLayout) this.mRootView.findViewById(R.id.login_third_part);
        this.mThirdPartLoginLayout.initActivity(getActivity(), this);
        this.mThirdPartLoginLayout.initThirtPart();
        this.mUserNameTv.addTextChangedListener(new RegisterOnTextChangeListener(this.mUserView));
        this.mPasswordBtnEt.addTextChangedListener(new RegisterOnTextChangeListener(this.mPasswordView));
        this.mCallPhoneTv = (TextView) this.mRootView.findViewById(R.id.login_call_phone);
        this.mCallPhoneTv.setOnClickListener(this);
        this.mUserNameTv.setOnEditorActionListener(new OnEditorActionListener(this) {
            final /* synthetic */ LoginFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != 5) {
                    return false;
                }
                this.this$0.mPasswordBtnEt.requestFocus();
                return true;
            }
        });
        this.mPasswordBtnEt.setOnEditorActionListener(new OnEditorActionListener(this) {
            final /* synthetic */ LoginFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LogInfo.log("+-->", "actionId--_>" + actionId);
                if (actionId == 0 || actionId == 4 || actionId == 6) {
                    this.this$0.loginBtnClick();
                    UIsUtils.hideSoftkeyboard(this.this$0.getActivity());
                    StatisticsUtils.staticticsInfoPost(this.this$0.getActivity(), "a9", null, 0, -1, null, null, null, null, null);
                }
                return false;
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_call_phone /*2131362894*/:
                callPhone(getResources().getString(2131100804));
                return;
            case R.id.letv_login_btn /*2131363554*/:
                loginBtnClick();
                return;
            case R.id.letv_account_registe_btn /*2131363556*/:
                getActivity().startActivityForResult(new Intent(getActivity(), RegisterActivity.class), 0);
                StatisticsUtils.staticticsInfoPost(getActivity(), "0", "c74", null, 1, null, PageIdConstant.loginPage, null, null, null, null, null);
                return;
            case R.id.letv_account_forgetpass_btn /*2131363557*/:
                FindLetvAccountPasswordActivity.launch(getActivity());
                StatisticsUtils.staticticsInfoPost(getActivity(), "0", "c74", null, 2, null, PageIdConstant.loginPage, null, null, null, null, null);
                return;
            case R.id.btn_back /*2131363786*/:
                LogInfo.LogStatistics("返回按钮");
                StatisticsUtils.staticticsInfoPost(getActivity(), "0", "c71", "返回", 1, null, PageIdConstant.loginPage, null, null, null, null, null);
                UIsUtils.hideInputMethod(getActivity());
                getActivity().finish();
                return;
            default:
                return;
        }
    }

    private void callPhone(String mobile) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + mobile));
        startActivity(intent);
    }

    private void loginBtnClick() {
        LetvLogApiTool.getInstance().saveExceptionInfo("点击乐视账号登录 Current Time :" + StringUtils.getTimeStamp());
        if (NetworkUtils.isNetworkAvailable()) {
            this.mUserName = this.mUserNameTv.getText().toString();
            this.mPassword = this.mPasswordBtnEt.getText().toString();
            LogInfo.log("+-->", "logIn-->>>>>>");
            if (checkLogin()) {
                mRequestLoginTask(this.mUserName, this.mPassword);
            }
            UIsUtils.hideInputMethod(getActivity());
            StatisticsUtils.staticticsInfoPost(getActivity(), "0", "c73", null, 1, null, PageIdConstant.loginPage, null, null, null, null, null);
            return;
        }
        ToastUtils.showToast(2131101012);
    }

    public String getTagName() {
        return FragmentConstant.TAG_LOGIN_FRAGMENT;
    }

    public int getContainerId() {
        if (this.mLoginFrom == LoginFrom.SETTINGS_LOGIN) {
            return R.id.setting_center_viewpage;
        }
        return R.id.login_viewpage;
    }

    private boolean checkLogin() {
        String text;
        if (TextUtils.isEmpty(this.mUserName)) {
            text = LetvTools.getTextFromServer(DialogMsgConstantId.FIFTEEN_ZERO_ONE_CONSTANT, getString(2131100491));
            if (TextUtils.isEmpty(text)) {
                ToastUtils.showToast(2131101071);
            } else {
                ToastUtils.showToast(text);
            }
            this.mUserView.setBackgroundColor(SupportMenu.CATEGORY_MASK);
            this.mUserNameTv.requestFocus();
            return false;
        }
        this.mUserView.setBackgroundColor(getActivity().getResources().getColor(2131493321));
        if (TextUtils.isEmpty(this.mPassword)) {
            text = LetvTools.getTextFromServer(DialogMsgConstantId.FIFTEEN_ELEVEN_CONSTANT, getString(2131100709));
            if (TextUtils.isEmpty(text)) {
                ToastUtils.showToast(2131101071);
            } else {
                ToastUtils.showToast(text);
            }
            this.mPasswordView.setBackgroundColor(SupportMenu.CATEGORY_MASK);
            this.mPasswordBtnEt.requestFocus();
            return false;
        }
        this.mPasswordView.setBackgroundColor(getActivity().getResources().getColor(2131493321));
        return true;
    }

    private void mRequestLoginTask(final String username, final String password) {
        this.mRootView.loading(true);
        LogInfo.log("ZSM", "mRequestLoginTask url == " + PlayRecordApi.getInstance().loginBaseUrl());
        final UserParser userParser = new UserParser();
        String str = username;
        String str2 = password;
        new LetvRequest(UserBean.class).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setUrl(PlayRecordApi.getInstance().loginBaseUrl()).addPostParams(PlayRecordApi.getInstance().login(0, str, str2, "mapp", "1", LetvUtils.getGSMInfo(getActivity()))).setParser(userParser).setCallback(new SimpleResponse<UserBean>() {
            public void onNetworkResponse(VolleyRequest<UserBean> volleyRequest, UserBean result, DataHull hull, NetworkResponseState state) {
                LetvLogApiTool.getInstance().saveExceptionInfo("点击乐视账号登录接口请求回调 Current Time :" + StringUtils.getTimeStamp() + " state == " + state);
                switch (state) {
                    case SUCCESS:
                        LogInfo.log("+-->", "Letv--->>>onPostExecute");
                        if ("1".equals(result.status)) {
                            if (!TextUtils.isEmpty(result.vipday)) {
                                ToastUtils.showToast(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_10000, 2131100798));
                            }
                            if (userParser.activityStatus == 1) {
                                PreferencesManager.getInstance().setLoginName(username);
                                PreferencesManager.getInstance().setLoginPassword(password);
                                PreferencesManager.getInstance().setUserId(result.uid);
                                PreferencesManager.getInstance().setPicture(result.picture);
                                PreferencesManager.getInstance().setUserName(result.username);
                                PreferencesManager.getInstance().setNickName(result.nickname);
                                PreferencesManager.getInstance().setScore(result.score);
                                PreferencesManager.getInstance().setUserMobile(result.mobile);
                                PreferencesManager.getInstance().setSso_tk(result.tv_token);
                                LetvApplication.getInstance().setLogInTime(System.currentTimeMillis());
                                LogInfo.log("+-->", "Letv--->>>onPostExecute222");
                                LoginFragment.this.getUserByToken();
                            } else {
                                PreferencesManager.getInstance().setLoginName(username);
                                PreferencesManager.getInstance().setLoginPassword(password);
                                PreferencesManager.getInstance().setUserId(result.uid);
                                PreferencesManager.getInstance().setPicture(result.picture);
                                PreferencesManager.getInstance().setUserName(result.username);
                                PreferencesManager.getInstance().setNickName(result.nickname);
                                PreferencesManager.getInstance().setScore(result.score);
                                PreferencesManager.getInstance().setUserMobile(result.mobile);
                                PreferencesManager.getInstance().setSso_tk(result.tv_token);
                                LetvApplication.getInstance().setLogInTime(System.currentTimeMillis());
                                LogInfo.log("+-->", "Letv--->>>onPostExecute222");
                                LoginFragment.this.getUserByToken();
                            }
                        } else {
                            ToastUtils.showToast(userParser.getMessage());
                        }
                        LoginFragment.this.hideErrorLayoutMessage();
                        return;
                    case NETWORK_NOT_AVAILABLE:
                        LoginFragment.this.hideErrorLayoutMessage();
                        ToastUtils.showToast(2131100332);
                        return;
                    case NETWORK_ERROR:
                        LoginFragment.this.hideErrorLayoutMessage();
                        ToastUtils.showToast(TipUtils.getTipMessage("1201", 2131100332));
                        return;
                    case RESULT_ERROR:
                        String errMsg = "";
                        if (hull.errMsg == 1002) {
                            errMsg = TipUtils.getTipMessage("1502", 2131100231);
                        } else {
                            errMsg = userParser.getMessage();
                        }
                        if (TextUtils.isEmpty(errMsg)) {
                            errMsg = LoginFragment.this.getString(2131100388);
                        }
                        ToastUtils.showToast(errMsg);
                        LoginFragment.this.hideErrorLayoutMessage();
                        return;
                    default:
                        return;
                }
            }

            public void onErrorReport(VolleyRequest<UserBean> request, String errorInfo) {
                LogInfo.log("ZSM", "mineListRequestTask onErrorReport == " + errorInfo);
                super.onErrorReport(request, errorInfo);
                DataStatistics.getInstance().sendErrorInfo(LetvApplication.getInstance(), "0", "0", "10002", null, errorInfo, null, null, null, null);
            }
        }).add();
    }

    private void getUserByToken() {
        LogInfo.log("+-->", "Letv--->>>getUserByToken");
        RequestUserByTokenTask.getUserByTokenTask(getActivity(), PreferencesManager.getInstance().getSso_tk(), new SimpleResponse<UserBean>(this) {
            final /* synthetic */ LoginFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onCacheResponse(VolleyRequest<UserBean> volleyRequest, UserBean result, DataHull hull, CacheResponseState state) {
                if (state == CacheResponseState.SUCCESS) {
                    this.this$0.setPlayerResult(result);
                }
            }
        });
    }

    public void hideErrorLayoutMessage() {
        if (this.mRootView != null) {
            this.mRootView.finish();
        }
    }

    private void setPlayerResult(UserBean result) {
        if (result != null) {
            PreferencesManager.getInstance().setEmail(result.email);
            PreferencesManager.getInstance().setSsouid(result.ssouid);
            if (this.mLoginSuccessCallback != null) {
                this.mLoginSuccessCallback.loginSuccess();
            }
        }
    }
}
