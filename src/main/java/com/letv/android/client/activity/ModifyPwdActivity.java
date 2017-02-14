package com.letv.android.client.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.utils.UIs;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.api.PlayRecordApi.MODIFYPWD_PARAMETERS;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.ModifyPwdResultBean;
import com.letv.core.config.LetvConfig;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.HttpRequestMethod;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.parser.LetvMobileParser;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.MD5;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class ModifyPwdActivity extends PimBaseActivity implements OnClickListener {
    private static final String OLD_PASSWORD_INCORRECT = "1804";
    private static final String PARAMS_INCORRECT = "1009";
    private static final String REQUEST_SOURCE_INCORRECT = "1042";
    private static final String TOKEN_OUT_OF_DATE = "1020";
    private static Handler mHandler = new Handler();
    private EditText mEditPwdConfirm;
    private EditText mEditPwdNew;
    private EditText mEditPwdOld;
    private Button mSaveBtn;

    public ModifyPwdActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mSaveBtn = null;
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initUI();
    }

    public void initUI() {
        super.initUI();
        init();
    }

    private void init() {
        setTitle(2131100617);
        this.mEditPwdOld = (EditText) findViewById(R.id.edit_pwd_old);
        this.mEditPwdNew = (EditText) findViewById(R.id.edit_pwd_new);
        this.mEditPwdConfirm = (EditText) findViewById(R.id.edit_pwd_confirm);
        this.mSaveBtn = (Button) findViewById(R.id.btn_save);
        this.mSaveBtn.setOnClickListener(this);
    }

    public int getContentView() {
        return R.layout.pim_modify_pwd;
    }

    private boolean checkPasswordFormat() {
        if (TextUtils.isEmpty(this.mEditPwdOld.getText().toString())) {
            UIs.call((Activity) this, 2131100394, new DialogInterface.OnClickListener(this) {
                final /* synthetic */ ModifyPwdActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialog, int which) {
                    this.this$0.mEditPwdOld.requestFocus();
                }
            });
            return false;
        } else if (!LetvUtils.passwordFormat(this.mEditPwdOld.getText().toString())) {
            UIs.call((Activity) this, 2131100393, new DialogInterface.OnClickListener(this) {
                final /* synthetic */ ModifyPwdActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialog, int which) {
                    this.this$0.mEditPwdOld.setText("");
                    this.this$0.mEditPwdOld.requestFocus();
                }
            });
            return false;
        } else if (TextUtils.isEmpty(this.mEditPwdNew.getText().toString())) {
            UIs.call((Activity) this, 2131100392, new DialogInterface.OnClickListener(this) {
                final /* synthetic */ ModifyPwdActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialog, int which) {
                    this.this$0.mEditPwdNew.requestFocus();
                }
            });
            return false;
        } else if (!LetvUtils.passwordFormat(this.mEditPwdNew.getText().toString())) {
            UIs.call((Activity) this, 2131100391, new DialogInterface.OnClickListener(this) {
                final /* synthetic */ ModifyPwdActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialog, int which) {
                    this.this$0.mEditPwdNew.setText("");
                    this.this$0.mEditPwdNew.requestFocus();
                }
            });
            return false;
        } else if (TextUtils.isEmpty(this.mEditPwdConfirm.getText().toString())) {
            UIs.call((Activity) this, 2131100396, new DialogInterface.OnClickListener(this) {
                final /* synthetic */ ModifyPwdActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialog, int which) {
                    this.this$0.mEditPwdConfirm.requestFocus();
                }
            });
            return false;
        } else if (!LetvUtils.passwordFormat(this.mEditPwdConfirm.getText().toString())) {
            UIs.call((Activity) this, 2131100395, new DialogInterface.OnClickListener(this) {
                final /* synthetic */ ModifyPwdActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialog, int which) {
                    this.this$0.mEditPwdConfirm.setText("");
                    this.this$0.mEditPwdConfirm.requestFocus();
                }
            });
            return false;
        } else if (this.mEditPwdNew.getText().toString().equals(this.mEditPwdConfirm.getText().toString())) {
            return true;
        } else {
            UIs.call((Activity) this, TipUtils.getTipMessage("1083", getString(2131100390)), new DialogInterface.OnClickListener(this) {
                final /* synthetic */ ModifyPwdActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialog, int which) {
                    this.this$0.mEditPwdConfirm.setText("");
                    this.this$0.mEditPwdConfirm.requestFocus();
                }
            });
            return false;
        }
    }

    private void doModifyFinish() {
        if (checkPasswordFormat()) {
            this.mRootView.loading(true);
            new LetvRequest(ModifyPwdResultBean.class).setRequestType(RequestManner.NETWORK_ONLY).setHttpMethod(HttpRequestMethod.POST).setUrl(PlayRecordApi.getInstance().modifyPwdBaseUrl(0)).addPostParam(MODIFYPWD_PARAMETERS.TK_KEY, PreferencesManager.getInstance().getSso_tk()).addPostParam(MODIFYPWD_PARAMETERS.OLDPWD_KEY, this.mEditPwdOld.getText().toString()).addPostParam(MODIFYPWD_PARAMETERS.NEWPWD_KEY, this.mEditPwdNew.getText().toString()).addPostParam(MODIFYPWD_PARAMETERS.NEED_TK_KEY, "true").addPostParam(MODIFYPWD_PARAMETERS.PLAT_KEY, "mobile_tv").addPostParam(MODIFYPWD_PARAMETERS.APISIGN_KEY, MD5.toMd5("need_tk=true&newpwd=" + this.mEditPwdNew.getText().toString() + "&oldpwd=" + this.mEditPwdOld.getText().toString() + "&pcode=" + LetvConfig.getPcode() + "&plat=mobile_tv" + "&tk=" + PreferencesManager.getInstance().getSso_tk() + "&version=" + LetvUtils.getClientVersionName() + "&poi345")).setParser(new LetvMobileParser()).setNeedCheckToken(true).setCallback(new SimpleResponse<ModifyPwdResultBean>(this) {
                final /* synthetic */ ModifyPwdActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onNetworkResponse(VolleyRequest<ModifyPwdResultBean> volleyRequest, ModifyPwdResultBean result, DataHull hull, NetworkResponseState state) {
                    LogInfo.log("ZSM", "requestRechargeRecords onNetworkResponse == " + state);
                    this.this$0.mRootView.finish();
                    UIsUtils.hideSoftkeyboard(this.this$0);
                    if (state == NetworkResponseState.SUCCESS) {
                        if (result == null || result.resultBean == null) {
                            ToastUtils.showToast(this.this$0.getActivity(), 2131100384);
                        } else if (!TextUtils.isEmpty(result.resultBean.message)) {
                            ToastUtils.showToast(this.this$0.getActivity(), result.resultBean.message);
                        } else if (!TextUtils.isEmpty(result.resultBean.errorCode) && !"0".equals(result.resultBean.errorCode)) {
                            String errorCode = result.resultBean.errorCode;
                            if (errorCode.equals("1009")) {
                                ToastUtils.showToast(this.this$0.getActivity(), 2131100386);
                            } else if (errorCode.equals(ModifyPwdActivity.OLD_PASSWORD_INCORRECT)) {
                                ToastUtils.showToast(this.this$0.getActivity(), 2131100385);
                            } else if (errorCode.equals(ModifyPwdActivity.TOKEN_OUT_OF_DATE)) {
                                ToastUtils.showToast(this.this$0.getActivity(), 2131100389);
                            } else if (errorCode.equals(ModifyPwdActivity.REQUEST_SOURCE_INCORRECT)) {
                                ToastUtils.showToast(this.this$0.getActivity(), 2131100387);
                            } else {
                                ToastUtils.showToast(this.this$0.getActivity(), TipUtils.getTipMessage("1805", this.this$0.getString(2131100384)));
                            }
                        } else if (result.resultBean.dataBean == null || result.resultBean.status != 1) {
                            ToastUtils.showToast(this.this$0.getActivity(), 2131100384);
                        } else {
                            if (!TextUtils.isEmpty(result.resultBean.dataBean.sso_tk)) {
                                PreferencesManager.getInstance().setSso_tk(result.resultBean.dataBean.sso_tk);
                            }
                            ToastUtils.showToast(this.this$0.getActivity(), 2131101010);
                            this.this$0.finish();
                        }
                    } else if (state == NetworkResponseState.NETWORK_ERROR) {
                        UIs.call(this.this$0, 2131100986, null);
                    } else if (state == NetworkResponseState.RESULT_ERROR) {
                        UIs.call(this.this$0, 2131100388, null);
                    } else {
                        ToastUtils.showToast(this.this$0.getActivity(), 2131100493);
                    }
                }

                public void onErrorReport(VolleyRequest<ModifyPwdResultBean> request, String errorInfo) {
                    LogInfo.log("ZSM", "mineListRequestTask onErrorReport == " + errorInfo);
                    this.this$0.mRootView.finish();
                    super.onErrorReport(request, errorInfo);
                    DataStatistics.getInstance().sendErrorInfo(LetvApplication.getInstance(), "0", "0", LetvErrorCode.LTURLModule_UC_ChangePassword, null, errorInfo, null, null, null, null);
                }
            }).add();
        }
    }

    public void onClick(View v) {
        if (v == this.mSaveBtn) {
            doModifyFinish();
        }
    }

    public String getActivityName() {
        return ModifyPwdActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
