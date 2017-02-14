package com.letv.lemallsdk.view;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorDescription;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.letv.lemallsdk.LemallPlatform;
import com.letv.lemallsdk.api.HttpTask;
import java.io.IOException;

public class LogonManager {
    private String ACCOUNT_TYPE = "com.letv";
    private String AUTH_TOKEN_TYPE_LETV = "tokenTypeLetv";
    private AccountManager accountManager;
    private Account[] accounts;
    private Context context;

    private class LogonCallBack implements AccountManagerCallback<Bundle> {
        private LogonCallBack() {
        }

        public void run(AccountManagerFuture<Bundle> arg0) {
            try {
                Bundle result = (Bundle) arg0.getResult();
                if (result != null) {
                    LemallPlatform.getInstance().setSsoToken(result.getString("authtoken"));
                }
            } catch (OperationCanceledException e) {
                e.printStackTrace();
            } catch (AuthenticatorException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    public LogonManager(Context context) {
        this.context = context;
    }

    public void showLogon4Inlay() {
        this.accountManager = AccountManager.get(this.context);
        if (!hasLetvAuthenticator()) {
            return;
        }
        if (isLetvLogined()) {
            syncLogon4LetvPhone();
        } else {
            addAccount((Activity) this.context, new LogonCallBack());
        }
    }

    private void syncLogon4LetvPhone() {
        new Thread() {
            public void run() {
                try {
                    String blockingGetAuthToken = LogonManager.this.accountManager.blockingGetAuthToken(LogonManager.this.accounts[0], LogonManager.this.AUTH_TOKEN_TYPE_LETV, true);
                    LemallPlatform.getInstance().setSsoToken(blockingGetAuthToken);
                    LogonManager.this.syncToken4Logon(blockingGetAuthToken, (IWebviewListener) LogonManager.this.context);
                } catch (OperationCanceledException e) {
                    e.printStackTrace();
                } catch (AuthenticatorException e2) {
                    e2.printStackTrace();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }.start();
    }

    private synchronized void addAccount(Activity activity, AccountManagerCallback<Bundle> callback) {
        Bundle options = new Bundle();
        options.putBoolean("loginFinish", true);
        this.accountManager.addAccount(this.ACCOUNT_TYPE, this.AUTH_TOKEN_TYPE_LETV, null, options, activity, callback, null);
    }

    private boolean isLetvLogined() {
        this.accounts = this.accountManager.getAccountsByType(this.ACCOUNT_TYPE);
        if (this.accounts == null || this.accounts.length <= 0) {
            return false;
        }
        return true;
    }

    private boolean hasLetvAuthenticator() {
        for (AuthenticatorDescription authenticatorType : this.accountManager.getAuthenticatorTypes()) {
            if (this.ACCOUNT_TYPE.equals(authenticatorType.type)) {
                return true;
            }
        }
        return false;
    }

    public void syncToken4Logon(String ssoToken, IWebviewListener iLetvBrideg) {
        if (TextUtils.isEmpty(ssoToken)) {
            deleteCookie();
        } else {
            HttpTask.getLogon(iLetvBrideg);
        }
    }

    public void syncCookieByGuest(IWebviewListener iLetvBrideg) {
        if (TextUtils.isEmpty(LemallPlatform.getInstance().getCookieLinkdata())) {
            HttpTask.getGuestLogon(iLetvBrideg);
        }
    }

    public void deleteCookie() {
        CookieSyncManager.createInstance(this.context).sync();
        CookieManager instance = CookieManager.getInstance();
        instance.setAcceptCookie(true);
        instance.removeAllCookie();
        LemallPlatform.getInstance().setCookieLinkdata("");
        System.gc();
    }
}
