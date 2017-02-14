package com.letv.lemallsdk.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.letv.lemallsdk.LemallPlatform;
import com.letv.lemallsdk.view.LogonManager;

public class AccountChangeReceiver extends BroadcastReceiver {
    private static final String ACCOUNT_TYPE = "com.letv";
    private static Account[] accounts;
    private static AccountManager am;

    public void onReceive(Context context, Intent intent) {
        am = AccountManager.get(context);
        accounts = am.getAccountsByType(ACCOUNT_TYPE);
        if ("android.accounts.LOGIN_ACCOUNTS_CHANGED".equals(intent.getAction())) {
            String loginName = getLoginName();
            Object name = "";
            LemallPlatform platform = LemallPlatform.getInstance();
            for (String str : platform.getCookieLinkdata().split("&")) {
                if (str.contains("COOKIE_NICKNAME")) {
                    String[] split2 = str.split("COOKIE_NICKNAME=");
                    if (split2.length > 0) {
                        name = split2[1];
                    }
                }
            }
            if (!TextUtils.isEmpty(name) && !name.equals(loginName)) {
                platform.setSsoToken("");
                platform.setCookieLinkdata("");
                new LogonManager(context).deleteCookie();
            }
        }
    }

    public void registerLogon(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.accounts.LOGIN_ACCOUNTS_CHANGED");
        filter.addAction("com.letv.android.account.ACTION_LOGOUT");
        filter.addAction("com.letv.android.account.ACTION_LOGIN");
        filter.addAction("com.letv.android.account.ACTION_TOKEN_UPDATE");
        context.registerReceiver(this, filter);
    }

    public void unregisterLogon(Context context) {
        context.unregisterReceiver(this);
    }

    public static String getLoginName() {
        String loginName = "";
        if (accounts == null || accounts.length <= 0) {
            return loginName;
        }
        return accounts[0].name;
    }
}
