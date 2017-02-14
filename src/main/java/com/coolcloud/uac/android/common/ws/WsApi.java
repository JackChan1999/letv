package com.coolcloud.uac.android.common.ws;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.coolcloud.uac.android.common.Rcode;
import com.coolcloud.uac.android.common.provider.Provider;
import com.coolcloud.uac.android.common.provider.RTKTEntity;
import com.coolcloud.uac.android.common.provider.TKTEntity;
import com.coolcloud.uac.android.common.stat.DataEyeUtils;
import com.coolcloud.uac.android.common.util.KVUtils;
import com.coolcloud.uac.android.common.util.LOG;
import com.coolcloud.uac.android.common.util.TextUtils;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnActivateListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnArrayListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnAuthCodeListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnBundleListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnBundlesListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnCaptchaListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnCheckActivateListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnCommonListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnDownLogoListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnGetBindDeviceListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnGetBindInfoListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnGetFreeCallListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnGetLoginAppListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnGetUserLogoListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnLogin4appListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnLoginListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnLoginThirdListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnQihooLoginListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnRegisterListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnRegisterQuicklyListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnReloginListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnThirdBindListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnThirdLoginAndBindListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnThirdLoginAndCreateTempListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnThirdLoginOauthListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnTokenListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnTotalScoreListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnUnBindDeviceListener;
import java.util.List;

public class WsApi extends BasicWsApi {
    private static final String TAG = "WsApi";
    private Provider provider = null;

    private WsApi(Context context, Handler handler, Provider provider) {
        super(context, handler);
        this.provider = provider;
    }

    public static WsApi get(Context context, Handler handler, Provider provider) {
        return new WsApi(context.getApplicationContext(), handler, provider);
    }

    public boolean getSMSChannels(String appId, final OnArrayListener<String> listener) {
        return super.getSMSChannels(appId, new OnArrayListener<String>() {
            public void onDone(int rcode, String[] array) {
                new 1(this, "getSMSChannels", WsApi.this.handler, listener, rcode, array).exec();
            }
        });
    }

    public boolean login(final String account, final String password, String appId, final OnLoginListener listener) {
        return super.login(account, password, appId, new OnLoginListener() {
            public void onDone(int rcode, String uid, String rtkt) {
                if (rcode == 0) {
                    rcode = WsApi.this.provider.putRTKT(new RTKTEntity(account, password, uid, rtkt, "qiku", account, "", "", "")) ? 0 : 5003;
                    if (rcode != 0) {
                        LOG.e(WsApi.TAG, "[account:" + account + "][password:" + password + "][uid:" + uid + "][rtkt:" + rtkt + "] set rtkt failed");
                    } else {
                        try {
                            DataEyeUtils.report1Param("com.dataeye.DCAccount", "login", String.class, uid);
                        } catch (Throwable e) {
                            LOG.w(WsApi.TAG, "DataEye  login erroe : " + e);
                        }
                    }
                }
                new 1(this, "login", WsApi.this.handler, listener, rcode, uid, rtkt).exec();
            }
        });
    }

    public boolean relogin(String account, String uid, String password, String appId, OnReloginListener listener) {
        final String str = account;
        final String str2 = password;
        final String str3 = uid;
        final String str4 = appId;
        final OnReloginListener onReloginListener = listener;
        return super.relogin(uid, password, appId, new OnReloginListener() {
            public void onDone(int rcode, String rtkt) {
                if (rcode == 0) {
                    rcode = WsApi.this.provider.putRTKT(new RTKTEntity(str, str2, str3, rtkt, "qiku", str, "", "", "")) ? 0 : 5003;
                    if (rcode != 0) {
                        LOG.e(WsApi.TAG, "[account:" + str + "][uid:" + str3 + "][appId:" + str4 + "][rtkt:" + rtkt + "] set rtkt failed");
                    }
                }
                new 1(this, "relogin", WsApi.this.handler, onReloginListener, rcode, rtkt).exec();
            }
        });
    }

    public boolean login4app(final String uid, String rtkt, String appId, final OnLogin4appListener listener) {
        return super.login4app(uid, rtkt, appId, new OnLogin4appListener() {
            public void onDone(int rcode, String tkt) {
                if (rcode == 0) {
                    try {
                        DataEyeUtils.report1Param("com.dataeye.DCAccount", "login", String.class, uid);
                    } catch (Throwable e) {
                        LOG.w(WsApi.TAG, "DataEye  login erroe : " + e);
                    }
                }
                new 1(this, "login4app", WsApi.this.handler, listener, rcode, tkt).exec();
            }
        });
    }

    public boolean loginThird(String account, String password, String appId, final OnLoginThirdListener listener) {
        return super.loginThird(account, password, appId, new OnLoginThirdListener() {
            public void onDone(int rcode, String tid, String srcHash, String uid, String account) {
                new 1(this, "loginThird", WsApi.this.handler, listener, rcode, tid, srcHash, uid, account).exec();
            }
        });
    }

    public boolean checkPassword(String uid, final String account, final String password, String appId, final OnCommonListener listener) {
        return super.checkPassword(uid, account, password, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                if (rcode == 0) {
                    boolean ok = true;
                    if (WsApi.this.provider != null) {
                        ok = WsApi.this.provider.updatePwd(account, password);
                    }
                    rcode = ok ? 0 : 5003;
                    if (rcode != 0) {
                        LOG.e(WsApi.TAG, "[account:" + account + "][pwd:" + password + "] check pwd failed");
                    }
                }
                new 1(this, "checkPassword", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean checkTKT(String uid, String account, String tkt, String appId, final OnCommonListener listener) {
        return super.checkTKT(uid, account, tkt, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "checkTKT", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean checkToken(String openid, String access_token, String appId, final OnCommonListener listener) {
        return super.checkToken(openid, access_token, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "checkToken", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean logout(final String uid, String tkt, String password, String appId, final OnCommonListener listener) {
        return super.logout(uid, tkt, password, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                if (Rcode.canLogout(rcode)) {
                    WsApi.this.provider.clearRTKT();
                    Bundle bundle = new Bundle();
                    bundle.putString("Uid", uid);
                    bundle.putString("uid", uid);
                    WsApi.this.sendMessage("com.qiku.account.LOGOUT", bundle);
                    WsApi.this.sendMessage("com.coolcloud.account.LOGOUT", bundle);
                    rcode = 0;
                    try {
                        DataEyeUtils.report("com.dataeye.DCAccount", "logout");
                    } catch (Throwable t) {
                        LOG.w(WsApi.TAG, "dataeye logout error : " + t);
                    }
                }
                new 1(this, "logout", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean logout4app(String uid, String tkt, String appId, final OnCommonListener listener) {
        return super.logout(uid, tkt, null, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "logout4app", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean getAppInfo(String appId, final OnBundleListener listener) {
        return super.getAppInfo(appId, new OnBundleListener() {
            public void onDone(int rcode, Bundle bundle) {
                new 1(this, "getAppInfo", WsApi.this.handler, listener, rcode, bundle).exec();
            }
        });
    }

    public boolean getBasicUserInfo(final String uid, String tkt, String appId, final OnBundleListener listener) {
        return super.getBasicUserInfo(uid, tkt, appId, new OnBundleListener() {
            public void onDone(int rcode, Bundle bundle) {
                if (rcode == 0) {
                    rcode = WsApi.this.provider.putUserInfo(uid, bundle) ? 0 : 5003;
                    if (rcode != 0) {
                        LOG.e(WsApi.TAG, "[uid:" + uid + "][bundle:" + bundle + "] put user info failed");
                    }
                    String PhotoUrl = KVUtils.get(bundle, "headimage");
                    if (!TextUtils.isEmpty(PhotoUrl)) {
                        WsApi.this.getUserLogo(uid, PhotoUrl, null);
                    }
                }
                new 1(this, "getBasicUserInfo", WsApi.this.handler, listener, rcode, bundle).exec();
            }
        });
    }

    public boolean getDetailUserInfo(final String uid, String tkt, String appId, final OnBundleListener listener) {
        return super.getDetailUserInfo(uid, tkt, appId, new OnBundleListener() {
            public void onDone(int rcode, Bundle bundle) {
                if (rcode != 0) {
                    LOG.e(WsApi.TAG, "[uid:" + uid + "][bundle:" + bundle + "] put user info failed");
                }
                new 1(this, "getDetailUserInfo", WsApi.this.handler, listener, rcode, bundle).exec();
            }
        });
    }

    public boolean updateHeadImage(String uid, String tkt, String key, String value, String appId, OnBundleListener listener) {
        final String str = uid;
        final String str2 = key;
        final String str3 = value;
        final OnBundleListener onBundleListener = listener;
        return super.updateHeadImage(uid, tkt, key, value, appId, new OnBundleListener() {
            public void onDone(int rcode, Bundle bundle) {
                if (rcode == 0) {
                    rcode = WsApi.this.provider.putUserItem(str, "headimage", KVUtils.get(bundle, "headimage")) ? 0 : 5003;
                    if (rcode != 0) {
                        LOG.e(WsApi.TAG, "[key:" + str2 + "][value:" + str3 + "] set user item failed");
                    }
                }
                new 1(this, "updateHeadImage", WsApi.this.handler, onBundleListener, rcode, bundle).exec();
            }
        });
    }

    public boolean updateUserItem(String uid, String tkt, String key, String value, String appId, OnCommonListener listener) {
        final String str = uid;
        final String str2 = key;
        final String str3 = value;
        final OnCommonListener onCommonListener = listener;
        return super.updateUserItem(uid, tkt, key, value, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                if (rcode == 0) {
                    rcode = WsApi.this.provider.putUserItem(str, str2, str3) ? 0 : 5003;
                    if (rcode != 0) {
                        LOG.e(WsApi.TAG, "[key:" + str2 + "][value:" + str3 + "] set user item failed");
                    }
                    Bundle bundle = new Bundle();
                    KVUtils.put(bundle, str2, str3);
                    WsApi.this.sendMessage("com.qiku.account.modify_basicInfo", bundle);
                    WsApi.this.sendMessage("com.coolcloud.account.modify_basicInfo", bundle);
                }
                new 1(this, "updateUserItem", WsApi.this.handler, onCommonListener, rcode).exec();
            }
        });
    }

    public boolean checkAuthorized(String uid, String appId, String scope, final OnCommonListener listener) {
        return super.checkAuthorized(uid, appId, scope, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "checkAuthorized", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean getTokenImplicit(String uid, String tkt, String appId, String scope, final OnTokenListener listener) {
        return super.getTokenImplicit(uid, tkt, appId, scope, new OnTokenListener() {
            public void onDone(int rcode, String openId, String accessToken, String refreshToken, long expiresin) {
                new 1(this, "getTokenImplicit", WsApi.this.handler, listener, rcode, openId, accessToken, refreshToken, expiresin).exec();
            }
        });
    }

    public boolean getAuthCode(String uid, String tkt, String appId, String scope, final OnAuthCodeListener listener) {
        return super.getAuthCode(uid, tkt, appId, scope, new OnAuthCodeListener() {
            public void onDone(int rcode, String code) {
                new 1(this, "getAuthCode", WsApi.this.handler, listener, rcode, code).exec();
            }
        });
    }

    public boolean checkPresentOnActivate(String ccid, String imsi, String deviceId, String deviceModel, String appId, final OnCheckActivateListener listener) {
        return super.checkPresentOnActivate(ccid, imsi, deviceId, deviceModel, appId, new OnCheckActivateListener() {
            public void onDone(int rcode, String phone) {
                new 1(this, "checkPresentOnActivate", WsApi.this.handler, listener, rcode, phone).exec();
            }
        });
    }

    public boolean pollingOnActivate(String simId, String deviceId, String deviceModel, String appId, final OnActivateListener listener) {
        return super.pollingOnActivate(simId, deviceId, deviceModel, appId, new OnActivateListener() {
            public void onDone(int rcode, String phone, String uid, String tkt) {
                if (rcode == 0) {
                    rcode = WsApi.this.provider.putRTKT(new RTKTEntity(phone, "", uid, tkt, "qiku", phone, "", "", "")) ? 0 : 5003;
                    if (rcode != 0) {
                        LOG.e(WsApi.TAG, "[account:" + phone + "][password:][uid:" + uid + "][tkt:" + tkt + "] set rtkt failed");
                    }
                }
                new 1(this, "pollingOnActivate", WsApi.this.handler, listener, rcode, phone, uid, tkt).exec();
            }
        });
    }

    public boolean registerPhoneGetActivateCode(String phone, String appId, final OnCommonListener listener) {
        return super.registerPhoneGetActivateCode(phone, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "registerPhoneGetActivateCode", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean registerPhone(String phone, String code, String password, String nickname, String appId, final OnRegisterListener listener) {
        return super.registerPhone(phone, code, password, nickname, appId, new OnRegisterListener() {
            public void onDone(int rcode, String accountId) {
                new 1(this, "registerPhone", WsApi.this.handler, listener, rcode, accountId).exec();
            }
        });
    }

    public boolean registerEmail(String email, String pwd, String nickname, String appId, final OnRegisterListener listener) {
        return super.registerEmail(email, pwd, nickname, appId, new OnRegisterListener() {
            public void onDone(int rcode, String accountId) {
                new 1(this, "registerEmail", WsApi.this.handler, listener, rcode, accountId).exec();
            }
        });
    }

    public boolean forwardPhoneGetActivateCode(String phone, String appId, final OnCommonListener listener) {
        return super.forwardPhoneGetActivateCode(phone, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "forwardPhoneGetActivateCode", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean forwardPhone(String oldUser, String phone, String tkt, String activateCode, String uid, String password, String appId, OnCommonListener listener) {
        final String str = oldUser;
        final String str2 = phone;
        final String str3 = appId;
        final String str4 = uid;
        final String str5 = tkt;
        final OnCommonListener onCommonListener = listener;
        return super.forwardPhone(phone, activateCode, uid, password, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                if (rcode == 0) {
                    boolean ok = WsApi.this.provider.updateUser(str, str2);
                    TKTEntity mTKTEntity = WsApi.this.provider.getTKT(str3);
                    if ((ok & WsApi.this.provider.putTKT(str3, new TKTEntity(str4, str5, str2, mTKTEntity.getLoginSource(), mTKTEntity.getInputAccount(), mTKTEntity.getQ(), mTKTEntity.getT(), mTKTEntity.getqid())) ? 0 : 5003) != 0) {
                        LOG.e(WsApi.TAG, "[account:" + str2 + "][password:][uid:" + str4 + "][tkt:" + str5 + "] set rtkt failed");
                    }
                }
                new 1(this, "forwardPhone", WsApi.this.handler, onCommonListener, rcode).exec();
            }
        });
    }

    public boolean forwardThird(String tid, String account, String password, String appId, final OnCommonListener listener) {
        return super.forwardThird(tid, account, password, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "forwardThird", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean bindPhoneGetActivateCode(String phone, String appId, final OnCommonListener listener) {
        return super.bindPhoneGetActivateCode(phone, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "bindPhoneGetActivateCode", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean bindPhone(final String account, String rtkt, final String phone, String activateCode, String uid, String password, String appId, OnCommonListener listener) {
        final OnCommonListener onCommonListener = listener;
        return super.bindPhone(phone, rtkt, activateCode, uid, password, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                if (rcode == 0) {
                    rcode = WsApi.this.provider.updateUser(account, phone) ? 0 : 5003;
                }
                new 1(this, "bindPhone", WsApi.this.handler, onCommonListener, rcode).exec();
            }
        });
    }

    public boolean bindEmail(String email, String uid, String tkt, String password, String appId, final OnCommonListener listener) {
        return super.bindEmail(email, uid, tkt, password, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "bindEmail", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean findpwdPhoneGetActivateCode(String phone, String appId, final OnCommonListener listener) {
        return super.findpwdPhoneGetActivateCode(phone, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "bindEmail", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean findpwdPhoneSetPwd(String phone, String activateCode, String password, String appId, final OnCommonListener listener) {
        return super.findpwdPhoneSetPwd(phone, activateCode, password, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "findpwdPhoneSetPwd", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean findpwdEmail(String email, String appId, final OnCommonListener listener) {
        return super.findpwdEmail(email, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "findpwdEmail", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean registerQuickly(String appId, final OnRegisterQuicklyListener listener) {
        return super.registerQuickly(appId, new OnRegisterQuicklyListener() {
            public void onDone(int rcode, String accountId, String account, String password, String nickname) {
                new 1(this, "registerQuickly", WsApi.this.handler, listener, rcode, accountId, account, password, nickname).exec();
            }
        });
    }

    public boolean changePassword(final String account, String uid, String tkt, String oldPassword, final String newPassword, String appId, final OnCommonListener listener) {
        return super.changePassword(uid, tkt, oldPassword, newPassword, appId, new OnCommonListener() {
            public void onDone(int rcode) {
                if (rcode == 0) {
                    if (WsApi.this.provider != null) {
                        rcode = WsApi.this.provider.updatePwd(account, newPassword) ? 0 : 5003;
                    }
                    if (rcode != 0) {
                        LOG.e(WsApi.TAG, "[account:" + account + "][pwd:" + newPassword + "] update pwd failed");
                    }
                }
                new 1(this, "changePassword", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean downLogo(String logoUrl, final OnDownLogoListener listener) {
        return super.downLogo(logoUrl, new OnDownLogoListener() {
            public void onDone(int rcode, byte[] content) {
                new 1(this, "downLogo", WsApi.this.handler, listener, rcode, content).exec();
            }
        });
    }

    public synchronized boolean getUserLogo(final String uid, final String PhotoUrl, final OnGetUserLogoListener listener) {
        return super.getUserLogo(uid, PhotoUrl, new OnGetUserLogoListener() {
            public void onDone(int rcode, String photoPath) {
                if (WsApi.this.provider != null) {
                    WsApi.this.provider.putUserItem(uid, "headimage", PhotoUrl);
                    WsApi.this.provider.putUserItem(uid, "localAvatarUrl", photoPath);
                }
                if (listener != null) {
                    new 1(this, "getUserLogo", WsApi.this.handler, listener, rcode, photoPath).exec();
                }
            }
        });
    }

    public boolean getTotalScore(String uid, String tkt, String appId, final OnTotalScoreListener listener) {
        return super.getTotalScore(uid, tkt, appId, new OnTotalScoreListener() {
            public void onDone(int rcode, int total) {
                new 1(this, "getTotalScore", WsApi.this.handler, listener, rcode, total).exec();
            }
        });
    }

    public boolean getExchangeRecords(String uid, String tkt, String appId, int pageOffset, int pageSize, int pageNum, final OnBundlesListener listener) {
        return super.getExchangeRecords(uid, tkt, appId, pageOffset, pageSize, pageNum, new OnBundlesListener() {
            public void onDone(int rcode, List<Bundle> bundles) {
                new 1(this, "getExchangeRecords", WsApi.this.handler, listener, rcode, bundles).exec();
            }
        });
    }

    public boolean getConsumeRecords(String uid, String tkt, String appId, int pageOffset, int pageSize, int pageNum, final OnBundlesListener listener) {
        return super.getConsumeRecords(uid, tkt, appId, pageOffset, pageSize, pageNum, new OnBundlesListener() {
            public void onDone(int rcode, List<Bundle> bundles) {
                new 1(this, "getConsumeRecords", WsApi.this.handler, listener, rcode, bundles).exec();
            }
        });
    }

    private void sendMessage(String action, Bundle bundle) {
        if (this.context != null) {
            Intent i = new Intent();
            i.setAction(action);
            i.putExtras(bundle);
            this.context.sendBroadcast(i);
        }
    }

    public boolean thirdOauth(final String inputaccount, String appid, final String tappname, String tappid, String ttoken, final OnThirdLoginOauthListener listener) {
        return super.thirdOauth(inputaccount, appid, tappname, tappid, ttoken, new OnThirdLoginOauthListener() {
            public void onDone(int rcode, String uid, String account, String rtkt, String tuid, String tnickname) {
                if (rcode == 0) {
                    if ((WsApi.this.provider.putRTKT(new RTKTEntity(account, new StringBuilder().append(account).append("uacshadow").toString(), uid, rtkt, tappname, inputaccount, "", "", "")) ? 0 : 5003) != 0) {
                        LOG.e(WsApi.TAG, "[account:" + account + "][password:..." + "][uid:" + uid + "][rtkt:" + rtkt + "] set rtkt failed");
                    } else {
                        try {
                            DataEyeUtils.report1Param("com.dataeye.DCAccount", "login", String.class, uid);
                        } catch (Throwable e) {
                            LOG.w(WsApi.TAG, "DataEye  login erroe : " + e);
                        }
                    }
                }
                new 1(this, "thirdLoginQihoo", WsApi.this.handler, listener, rcode, uid, account, rtkt, tuid, tnickname).exec();
            }
        });
    }

    public boolean thirdLoginAndCreateTemp(final String inputaccount, String appid, final String tappname, String tappid, String tuid, String tnickname, String ttoken, OnThirdLoginAndCreateTempListener listener) {
        final OnThirdLoginAndCreateTempListener onThirdLoginAndCreateTempListener = listener;
        return super.thirdLoginAndCreateTemp(inputaccount, appid, tappname, tappid, tuid, tnickname, ttoken, new OnThirdLoginAndCreateTempListener() {
            public void onDone(int rcode, String uid, String account, String rtkt) {
                if (rcode == 0) {
                    if ((WsApi.this.provider.putRTKT(new RTKTEntity(account, new StringBuilder().append(account).append("uacshadow").toString(), uid, rtkt, tappname, inputaccount, "", "", "")) ? 0 : 5003) != 0) {
                        LOG.e(WsApi.TAG, "[account:" + account + "][password:..." + "][uid:" + uid + "][rtkt:" + rtkt + "] set rtkt failed");
                    } else {
                        try {
                            DataEyeUtils.report1Param("com.dataeye.DCAccount", "login", String.class, uid);
                        } catch (Throwable e) {
                            LOG.w(WsApi.TAG, "DataEye  login erroe : " + e);
                        }
                    }
                }
                new 1(this, "thirdLoginQihoo", WsApi.this.handler, onThirdLoginAndCreateTempListener, rcode, uid, account, rtkt).exec();
            }
        });
    }

    public boolean thirdLoginAndBind(String inputaccount, String appid, String account, String pwd, String tappname, String tappid, String tuid, String tnickname, String ttoken, OnThirdLoginAndBindListener listener) {
        final String str = account;
        final String str2 = pwd;
        final String str3 = tappname;
        final String str4 = inputaccount;
        final OnThirdLoginAndBindListener onThirdLoginAndBindListener = listener;
        return super.thirdLoginAndBind(inputaccount, appid, account, pwd, tappname, tappid, tuid, tnickname, ttoken, new OnThirdLoginAndBindListener() {
            public void onDone(int rcode, String uid, String rtkt) {
                if (rcode == 0) {
                    if ((WsApi.this.provider.putRTKT(new RTKTEntity(str, str2, uid, rtkt, str3, str4, "", "", "")) ? 0 : 5003) != 0) {
                        LOG.e(WsApi.TAG, "[account:" + str + "][password:..." + "][uid:" + uid + "][rtkt:" + rtkt + "] set rtkt failed");
                    } else {
                        try {
                            DataEyeUtils.report1Param("com.dataeye.DCAccount", "login", String.class, uid);
                        } catch (Throwable e) {
                            LOG.w(WsApi.TAG, "DataEye  login erroe : " + e);
                        }
                    }
                }
                new 1(this, "thirdLoginQihoo", WsApi.this.handler, onThirdLoginAndBindListener, rcode, uid, rtkt).exec();
            }
        });
    }

    public boolean thirdBind(String appid, final String uid, String tkt, String tappname, String tappid, String ttoken, final OnThirdBindListener listener) {
        return super.thirdBind(appid, uid, tkt, tappname, tappid, ttoken, new OnThirdBindListener() {
            public void onDone(int rcode, String tuid, String tnickname, String isSupportUnbind) {
                new 1(this, "thirdBind", WsApi.this.handler, listener, rcode, tnickname, isSupportUnbind).exec();
            }
        });
    }

    public boolean getBindInfo(String appid, String uid, String tkt, final OnGetBindInfoListener listener) {
        return super.getBindInfo(appid, uid, tkt, new OnGetBindInfoListener() {
            public void onDone(int rcode, List<Bundle> mBindList) {
                if (rcode == 0) {
                    new 1(this, "getBindInfo", WsApi.this.handler, listener, rcode, mBindList).exec();
                } else {
                    new 1(this, "getBindInfo", WsApi.this.handler, listener, rcode, mBindList).exec();
                }
            }
        });
    }

    public boolean LoginByQihoo(String inputaccount, String qihooPassword, String Q, String T, String qid, String appid, String tappname, String tappid, String tuseinfo, String isSkip, OnQihooLoginListener listener) {
        final String str = tappname;
        final String str2 = inputaccount;
        final String str3 = Q;
        final String str4 = T;
        final String str5 = qid;
        final OnQihooLoginListener onQihooLoginListener = listener;
        return super.LoginByQihoo(inputaccount, qihooPassword, appid, tappname, tappid, tuseinfo, isSkip, new OnQihooLoginListener() {
            public void onDone(int rcode, String uid, String account, String rtkt) {
                if (rcode == 0) {
                    if ((WsApi.this.provider.putRTKT(new RTKTEntity(account, new StringBuilder().append(account).append("uacshadow").toString(), uid, rtkt, str, str2, str3, str4, str5)) ? 0 : 5003) != 0) {
                        LOG.e(WsApi.TAG, "[account:" + account + "][password:..." + "][uid:" + uid + "][rtkt:" + rtkt + "] set rtkt failed");
                    } else {
                        try {
                            DataEyeUtils.report1Param("com.dataeye.DCAccount", "login", String.class, uid);
                        } catch (Throwable e) {
                            LOG.w(WsApi.TAG, "DataEye  login erroe : " + e);
                        }
                    }
                }
                new 1(this, "thirdLoginQihoo", WsApi.this.handler, onQihooLoginListener, rcode, uid, account, rtkt).exec();
            }
        });
    }

    public boolean UnBindThird(String appid, String uid, String tkt, String tappname, String tappid, final OnCommonListener listener) {
        return super.UnBindThird(appid, uid, tkt, tappname, tappid, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "UnBindThird", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean bindQihoo(String appid, final String uid, String tkt, String tappname, String tappid, String tuserinfo, final OnThirdBindListener listener) {
        return super.bindQihoo(appid, uid, tkt, tappname, tappid, tuserinfo, new OnThirdBindListener() {
            public void onDone(int rcode, String tuid, String tnickname, String isSupportUnbind) {
                new 1(this, "bindQihoo", WsApi.this.handler, listener, rcode, tnickname, isSupportUnbind).exec();
            }
        });
    }

    public boolean loginAndBindQihoo(String appid, String account, String pwd, String tappname, String tappid, String tuserinfo, OnThirdLoginAndBindListener listener) {
        final String str = account;
        final String str2 = pwd;
        final String str3 = tappname;
        final OnThirdLoginAndBindListener onThirdLoginAndBindListener = listener;
        return super.loginAndBindQihoo(appid, account, pwd, tappname, tappid, tuserinfo, new OnThirdLoginAndBindListener() {
            public void onDone(int rcode, String uid, String rtkt) {
                if (rcode == 0) {
                    if ((WsApi.this.provider.putRTKT(new RTKTEntity(str, str2, uid, rtkt, str3, str, "", "", "")) ? 0 : 5003) != 0) {
                        LOG.e(WsApi.TAG, "[account:" + str + "][password:..." + "][uid:" + uid + "][rtkt:" + rtkt + "] set rtkt failed");
                    } else {
                        try {
                            DataEyeUtils.report1Param("com.dataeye.DCAccount", "login", String.class, uid);
                        } catch (Throwable e) {
                            LOG.w(WsApi.TAG, "DataEye  login erroe : " + e);
                        }
                    }
                }
                new 1(this, "thirdLoginQihoo", WsApi.this.handler, onThirdLoginAndBindListener, rcode, uid, rtkt).exec();
            }
        });
    }

    public boolean getFreeCallInfo(String appid, String uid, String tkt, String type, String phone, String loginSource, final OnGetFreeCallListener listener) {
        return super.getFreeCallInfo(appid, uid, tkt, type, phone, loginSource, new OnGetFreeCallListener() {
            public void onDone(int rcode, String uid, String phone, String jid, String pwd) {
                new 1(this, "getFreeCallInfo", WsApi.this.handler, listener, rcode, uid, phone, jid, pwd).exec();
            }
        });
    }

    public boolean getBindDevice(String appid, String uid, String tkt, String version, final OnGetBindDeviceListener listener) {
        return super.getBindDevice(appid, uid, tkt, version, new OnGetBindDeviceListener() {
            public void onDone(int rcode, List<Bundle> bundles) {
                new 1(this, "getBindDevice", WsApi.this.handler, listener, rcode, bundles).exec();
            }
        });
    }

    public boolean unbindDevice(String appid, String uid, String tkt, String devlist, String version, final OnUnBindDeviceListener listener) {
        return super.unbindDevice(appid, uid, tkt, devlist, version, new OnUnBindDeviceListener() {
            public void onDone(int rcode) {
                new 1(this, "unBindDevice", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean getLoginApp(String appid, String uid, String tkt, final OnGetLoginAppListener listener) {
        return super.getLoginApp(appid, uid, tkt, new OnGetLoginAppListener() {
            public void onDone(int rcode, List<Bundle> bundles) {
                new 1(this, "getLoginApp", WsApi.this.handler, listener, rcode, bundles).exec();
            }
        });
    }

    public boolean changNickname(String appid, String uid, String tkt, final String nickname, final OnCommonListener listener) {
        return super.changNickname(appid, uid, tkt, nickname, new OnCommonListener() {
            public void onDone(int rcode) {
                if (rcode == 0) {
                    Bundle bundle = new Bundle();
                    KVUtils.put(bundle, "nickname", nickname);
                    WsApi.this.sendMessage("com.qiku.account.modify_basicInfo", bundle);
                    WsApi.this.sendMessage("com.coolcloud.account.modify_basicInfo", bundle);
                }
                new 1(this, "changNickname", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean getCaptchaImg(String appid, String captchaKey, String width, String height, String length, final OnCaptchaListener listener) {
        return super.getCaptchaImg(appid, captchaKey, width, height, length, new OnCaptchaListener() {
            public void onDone(int rcode, byte[] img) {
                new 1(this, "getCaptchaImg", WsApi.this.handler, listener, rcode, img).exec();
            }
        });
    }

    public boolean registerPhoneGetActivateCodeSafe(String phone, String appId, String captchakey, String captchacode, final OnCommonListener listener) {
        return super.registerPhoneGetActivateCodeSafe(phone, appId, captchakey, captchacode, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "registerPhoneGetActivateCodeSafe", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean forwardPhoneGetActivateCodeSafe(String phone, String appId, String captchakey, String captchacode, final OnCommonListener listener) {
        return super.forwardPhoneGetActivateCodeSafe(phone, appId, captchakey, captchacode, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "forwardPhoneGetActivateCodeSafe", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean findpwdPhoneGetActivateCodeSafe(String phone, String appId, String captchakey, String captchacode, final OnCommonListener listener) {
        return super.findpwdPhoneGetActivateCodeSafe(phone, appId, captchakey, captchacode, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "findpwdPhoneGetActivateCodeSafe", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean bindPhoneGetActivateCodeSafe(String phone, String appId, String captchakey, String captchacode, final OnCommonListener listener) {
        return super.bindPhoneGetActivateCodeSafe(phone, appId, captchakey, captchacode, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "bindPhoneGetActivateCodeSafe", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }

    public boolean updateDeviceId(String uid, String tkt, String newdevid, String olddevid, String appid, final OnCommonListener listener) {
        return super.updateDeviceId(uid, tkt, newdevid, olddevid, appid, new OnCommonListener() {
            public void onDone(int rcode) {
                new 1(this, "updateDeviceId", WsApi.this.handler, listener, rcode).exec();
            }
        });
    }
}
