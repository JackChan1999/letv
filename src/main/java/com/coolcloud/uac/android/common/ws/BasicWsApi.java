package com.coolcloud.uac.android.common.ws;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.coolcloud.uac.android.common.http.HTTPAgent;
import com.coolcloud.uac.android.common.http.HTTPTransporter;
import com.coolcloud.uac.android.common.http.Host.FileUpload;
import com.coolcloud.uac.android.common.util.Base64;
import com.coolcloud.uac.android.common.util.EncryptUtils;
import com.coolcloud.uac.android.common.util.Executor;
import com.coolcloud.uac.android.common.util.Executor.RunNoThrowable;
import com.coolcloud.uac.android.common.util.LOG;
import com.coolcloud.uac.android.common.util.PhotoUtil;
import com.coolcloud.uac.android.common.util.TextUtils;
import com.letv.core.constant.LiveRoomConstant;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicWsApi {
    private static final String TAG = "BasicWsApi";
    public Context context = null;
    public Handler handler = null;
    public RequestBuilder requestBuilder = null;

    public interface OnListener {
    }

    public interface OnCommonListener extends OnListener {
        void onDone(int i);
    }

    public interface OnCaptchaListener extends OnListener {
        void onDone(int i, byte[] bArr);
    }

    public interface OnLoginListener extends OnListener {
        void onDone(int i, String str, String str2);
    }

    public interface OnActivateListener extends OnListener {
        void onDone(int i, String str, String str2, String str3);
    }

    public interface OnTokenListener extends OnListener {
        void onDone(int i, String str, String str2, String str3, long j);
    }

    public interface OnGetQihooTokenListener extends OnListener {
        void onDone(int i, String str, String str2, String str3, String str4, String str5);
    }

    public interface OnAuthCodeListener extends OnListener {
        void onDone(int i, String str);
    }

    public interface OnBundleListener extends OnListener {
        void onDone(int i, Bundle bundle);
    }

    public interface OnDownLogoListener extends OnListener {
        void onDone(int i, byte[] bArr);
    }

    public interface OnRegisterListener extends OnListener {
        void onDone(int i, String str);
    }

    public static abstract class CallbackHandler {
        private Handler handler = null;
        private OnListener listener = null;
        private String prefix = null;

        protected abstract void callback();

        public CallbackHandler(String prefix, Handler handler, OnListener listener) {
            this.handler = handler;
            this.prefix = prefix;
            this.listener = listener;
        }

        public void exec() {
            if (this.handler != null) {
                this.handler.post(new 1(this, this.prefix));
            } else if (this.listener != null) {
                callback();
            }
        }
    }

    public interface OnArrayListener<V> extends OnListener {
        void onDone(int i, V[] vArr);
    }

    public interface OnBindAndAuthListener extends OnListener {
        void onDone(int i, String str, String str2, String str3, long j);
    }

    public interface OnBundlesListener extends OnListener {
        void onDone(int i, List<Bundle> list);
    }

    public interface OnCheckActivateListener extends OnListener {
        void onDone(int i, String str);
    }

    public interface OnDetailScoreInfoListener extends OnListener {
        void onDone(int i, int i2, int i3, int i4);
    }

    public interface OnExchangeRateListener extends OnListener {
        void onDone(int i, int i2);
    }

    public interface OnGetBindDeviceListener extends OnListener {
        void onDone(int i, List<Bundle> list);
    }

    public interface OnGetBindInfoListener extends OnListener {
        void onDone(int i, List<Bundle> list);
    }

    public interface OnGetFreeCallListener extends OnListener {
        void onDone(int i, String str, String str2, String str3, String str4);
    }

    public interface OnGetLoginAppListener extends OnListener {
        void onDone(int i, List<Bundle> list);
    }

    public interface OnGetUserLogoListener extends OnListener {
        void onDone(int i, String str);
    }

    public interface OnLogin4appListener extends OnListener {
        void onDone(int i, String str);
    }

    public interface OnLoginThirdListener extends OnListener {
        void onDone(int i, String str, String str2, String str3, String str4);
    }

    public interface OnQihooLoginListener extends OnListener {
        void onDone(int i, String str, String str2, String str3);
    }

    public interface OnRegisterQuicklyListener extends OnListener {
        void onDone(int i, String str, String str2, String str3, String str4);
    }

    public interface OnReloginListener extends OnListener {
        void onDone(int i, String str);
    }

    public interface OnThirdBindListener extends OnListener {
        void onDone(int i, String str, String str2, String str3);
    }

    public interface OnThirdLoginAndBindListener extends OnListener {
        void onDone(int i, String str, String str2);
    }

    public interface OnThirdLoginAndCreateTempListener extends OnListener {
        void onDone(int i, String str, String str2, String str3);
    }

    public interface OnThirdLoginOauthListener extends OnListener {
        void onDone(int i, String str, String str2, String str3, String str4, String str5);
    }

    public interface OnTotalScoreListener extends OnListener {
        void onDone(int i, int i2);
    }

    public interface OnUnBindDeviceListener extends OnListener {
        void onDone(int i);
    }

    public interface OnUploadLogoListener extends OnListener {
        void onDone(int i, String str);
    }

    public BasicWsApi(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.requestBuilder = RequestBuilder.create(context);
    }

    public boolean getSMSChannels(String appId, OnArrayListener<String> listener) {
        String prefix = "[getSMSChannels][appId:" + appId + "]";
        LOG.d(TAG, prefix + " get sms channels ...");
        final String str = appId;
        final String str2 = prefix;
        final OnArrayListener<String> onArrayListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r15 = this;
                r5 = 1;
                r2 = 0;
                r10 = java.lang.System.currentTimeMillis();
                r6 = 0;
                r12 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r12 = r12.context;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r8 = com.coolcloud.uac.android.common.util.SystemUtils.getDefaultSimInfo(r12);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                if (r8 == 0) goto L_0x0071;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
            L_0x0012:
                r1 = r8.getCCID();	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
            L_0x0016:
                if (r8 == 0) goto L_0x0074;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
            L_0x0018:
                r4 = r8.getIMSI();	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
            L_0x001c:
                r12 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r12 = r12.requestBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r3;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r0 = r12.buildGetSMSChannels(r1, r4, r13);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r0.execute();	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r12 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r6 = r12 - r10;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r12 = r0.ok();	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                if (r12 != 0) goto L_0x0077;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
            L_0x0035:
                r5 = r0.getRcode();	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r12 = "BasicWsApi";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13.<init>();	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r14 = r4;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r14 = "[millis:";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r6);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r14 = "] get sms channels failed(";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r5);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r14 = ")";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.toString();	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                com.coolcloud.uac.android.common.util.LOG.e(r12, r13);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
            L_0x0067:
                r12 = r5;
                if (r12 == 0) goto L_0x0070;
            L_0x006b:
                r12 = r5;
                r12.onDone(r5, r2);
            L_0x0070:
                return;
            L_0x0071:
                r1 = "";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                goto L_0x0016;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
            L_0x0074:
                r4 = "";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                goto L_0x001c;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
            L_0x0077:
                r12 = "smschannel";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r9 = r0.getString(r12);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r12 = " ";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = "";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r12 = r9.replace(r12, r13);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = ";";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r2 = r12.split(r13);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r5 = 0;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r12 = "BasicWsApi";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13.<init>();	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r14 = r4;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r14 = "[millis:";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r6);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r14 = "] get sms channels ok(";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r9);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r14 = ")";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.toString();	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                com.coolcloud.uac.android.common.util.LOG.i(r12, r13);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                goto L_0x0067;
            L_0x00bc:
                r3 = move-exception;
                r12 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r6 = r12 - r10;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r12 = "BasicWsApi";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13.<init>();	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r14 = r4;	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r14 = "[millis:";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r6);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r14 = "] get sms channels failed(Exception)";	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r13 = r13.toString();	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                com.coolcloud.uac.android.common.util.LOG.e(r12, r13, r3);	 Catch:{ Exception -> 0x00bc, all -> 0x00f1 }
                r12 = r5;
                if (r12 == 0) goto L_0x0070;
            L_0x00eb:
                r12 = r5;
                r12.onDone(r5, r2);
                goto L_0x0070;
            L_0x00f1:
                r12 = move-exception;
                r13 = r5;
                if (r13 == 0) goto L_0x00fb;
            L_0x00f6:
                r13 = r5;
                r13.onDone(r5, r2);
            L_0x00fb:
                throw r12;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.1.rundo():void");
            }
        });
        return true;
    }

    public boolean login(String account, String password, String appId, OnLoginListener listener) {
        final String md5 = EncryptUtils.getMD5String(password);
        String prefix = "[login][account:" + account + "][pwd:..." + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " login ...");
        final String str = account;
        final String str2 = appId;
        final String str3 = prefix;
        final OnLoginListener onLoginListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r13 = this;
                r4 = 1;
                r8 = 0;
                r5 = 0;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r9 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r9 = r9.requestBuilder;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r3;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r11 = r4;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r12 = r5;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r0 = r9.buildLogin(r10, r11, r12);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r0.execute();	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r2 = r10 - r6;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r9 = r0.ok();	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                if (r9 != 0) goto L_0x0062;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
            L_0x0026:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r9 = "BasicWsApi";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10.<init>();	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r11 = r6;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r11);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r11 = "[millis:";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r11);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r2);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r11 = "] login failed(";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r11);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r4);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r11 = ")";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r11);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.toString();	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                com.coolcloud.uac.android.common.util.LOG.e(r9, r10);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
            L_0x0058:
                r9 = r7;
                if (r9 == 0) goto L_0x0061;
            L_0x005c:
                r9 = r7;
                r9.onDone(r4, r8, r5);
            L_0x0061:
                return;
            L_0x0062:
                r9 = "uid";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r8 = r0.getString(r9);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r9 = "rtkt";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r5 = r0.getString(r9);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r4 = 0;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r9 = "BasicWsApi";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10.<init>();	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r11 = r6;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r11);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r11 = "[millis:";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r11);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r2);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r11 = "] login ok(";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r11);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r8);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r11 = ",";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r11);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r5);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r11 = ")";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r11);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.toString();	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                com.coolcloud.uac.android.common.util.LOG.i(r9, r10);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                goto L_0x0058;
            L_0x00aa:
                r1 = move-exception;
                r10 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r2 = r10 - r6;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r9 = "BasicWsApi";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10.<init>();	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r11 = r6;	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r11);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r11 = "[millis:";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r11);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r2);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r11 = "] login failed(Exception)";	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.append(r11);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r10 = r10.toString();	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                com.coolcloud.uac.android.common.util.LOG.e(r9, r10, r1);	 Catch:{ Exception -> 0x00aa, all -> 0x00df }
                r9 = r7;
                if (r9 == 0) goto L_0x0061;
            L_0x00d9:
                r9 = r7;
                r9.onDone(r4, r8, r5);
                goto L_0x0061;
            L_0x00df:
                r9 = move-exception;
                r10 = r7;
                if (r10 == 0) goto L_0x00e9;
            L_0x00e4:
                r10 = r7;
                r10.onDone(r4, r8, r5);
            L_0x00e9:
                throw r9;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.2.rundo():void");
            }
        });
        return true;
    }

    public boolean relogin(String uid, String password, String appId, OnReloginListener listener) {
        final String md5 = EncryptUtils.getMD5String(password);
        String prefix = "[relogin][uid:" + uid + "][pwd:" + md5 + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " relogin ...");
        final String str = uid;
        final String str2 = appId;
        final String str3 = prefix;
        final OnReloginListener onReloginListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r5 = 0;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r8 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = r8.requestBuilder;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r3;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = r4;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r11 = r5;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r0 = r8.buildRelogin(r9, r10, r11);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r0.execute();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = r0.ok();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                if (r8 != 0) goto L_0x0061;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
            L_0x0025:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9.<init>();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = r6;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = "] relogin failed(";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = ")";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
            L_0x0057:
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x005b:
                r8 = r7;
                r8.onDone(r4, r5);
            L_0x0060:
                return;
            L_0x0061:
                r8 = "rtkt";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r5 = r0.getString(r8);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r4 = 0;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9.<init>();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = r6;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = "] relogin ok(";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r5);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = ")";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                com.coolcloud.uac.android.common.util.LOG.i(r8, r9);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                goto L_0x0057;
            L_0x0098:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9.<init>();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = r6;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = "] relogin failed(Exception)";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9, r1);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x00c7:
                r8 = r7;
                r8.onDone(r4, r5);
                goto L_0x0060;
            L_0x00cd:
                r8 = move-exception;
                r9 = r7;
                if (r9 == 0) goto L_0x00d7;
            L_0x00d2:
                r9 = r7;
                r9.onDone(r4, r5);
            L_0x00d7:
                throw r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.3.rundo():void");
            }
        });
        return true;
    }

    public boolean login4app(String uid, String rtkt, String appId, OnLogin4appListener listener) {
        String prefix = "[login4app][uid:" + uid + "][rtkt:" + rtkt + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " login4app ...");
        final String str = uid;
        final String str2 = rtkt;
        final String str3 = appId;
        final String str4 = prefix;
        final OnLogin4appListener onLogin4appListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r5 = 0;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r8 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r8 = r8.requestBuilder;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r3;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = r4;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r11 = r5;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r0 = r8.buildLogin4app(r9, r10, r11);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r0.execute();	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r8 = r0.ok();	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                if (r8 != 0) goto L_0x0061;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
            L_0x0025:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9.<init>();	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = r6;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = "] login4app failed(";	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = ")";	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
            L_0x0057:
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x005b:
                r8 = r7;
                r8.onDone(r4, r5);
            L_0x0060:
                return;
            L_0x0061:
                r8 = "tkt";	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r5 = r0.getString(r8);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r4 = 0;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9.<init>();	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = r6;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = "] login4app ok(";	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = r3;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = ",";	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r5);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = ")";	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                com.coolcloud.uac.android.common.util.LOG.i(r8, r9);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                goto L_0x0057;
            L_0x00a4:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9.<init>();	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = r6;	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r10 = "] login4app failed(Exception)";	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9, r1);	 Catch:{ Exception -> 0x00a4, all -> 0x00d9 }
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x00d3:
                r8 = r7;
                r8.onDone(r4, r5);
                goto L_0x0060;
            L_0x00d9:
                r8 = move-exception;
                r9 = r7;
                if (r9 == 0) goto L_0x00e3;
            L_0x00de:
                r9 = r7;
                r9.onDone(r4, r5);
            L_0x00e3:
                throw r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.4.rundo():void");
            }
        });
        return true;
    }

    public boolean loginThird(String account, String password, String appId, OnLoginThirdListener listener) {
        String prefix = "[loginThird][account:" + account + "][pwd:" + password + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " login third ...");
        final String str = password;
        final String str2 = appId;
        final String str3 = prefix;
        final OnLoginThirdListener onLoginThirdListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                int rcode = 1;
                String tid = null;
                String srcHash = null;
                String uid = null;
                String account = null;
                long start = System.currentTimeMillis();
                try {
                    HTTPAgent agent = BasicWsApi.this.requestBuilder.buildLoginThird(null, str, str2);
                    agent.execute();
                    long millis = System.currentTimeMillis() - start;
                    if (agent.ok()) {
                        tid = agent.getString("thirdid");
                        uid = agent.getString("uid");
                        srcHash = agent.getString("access_token");
                        account = agent.getString("account");
                        rcode = 0;
                        LOG.i(BasicWsApi.TAG, str3 + "[millis:" + millis + "] login third ok(" + tid + "," + srcHash + "," + uid + "," + account + ")");
                    } else {
                        rcode = agent.getRcode();
                        LOG.e(BasicWsApi.TAG, str3 + "[millis:" + millis + "] login third failed(" + rcode + ")");
                    }
                    if (onLoginThirdListener != null) {
                        onLoginThirdListener.onDone(rcode, tid, srcHash, uid, account);
                    }
                } catch (Exception e) {
                    LOG.e(BasicWsApi.TAG, str3 + "[millis:" + (System.currentTimeMillis() - start) + "] login third failed(Exception)", e);
                    if (onLoginThirdListener != null) {
                        onLoginThirdListener.onDone(rcode, tid, srcHash, uid, account);
                    }
                } catch (Throwable th) {
                    Throwable th2 = th;
                    if (onLoginThirdListener != null) {
                        onLoginThirdListener.onDone(rcode, tid, srcHash, uid, account);
                    }
                }
            }
        });
        return true;
    }

    public boolean checkPassword(String uid, String account, String password, String appId, OnCommonListener listener) {
        final String md5 = EncryptUtils.getMD5String(password);
        String prefix = "[checkPassword][uid:" + uid + "][account:" + account + "][pwd:" + md5 + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " check password ...");
        final String str = uid;
        final String str2 = account;
        final String str3 = appId;
        final String str4 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r3;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r4;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r10 = r5;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r11 = r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0 = r5.buildCheckPassword(r8, r9, r10, r11);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0.execute();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                if (r5 != 0) goto L_0x0062;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0026:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] check password failed(";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = ")";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0058:
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x005c:
                r5 = r8;
                r5.onDone(r4);
            L_0x0061:
                return;
            L_0x0062:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] check password ok";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                goto L_0x0058;
            L_0x0088:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] check password failed(Exception)";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x00b7:
                r5 = r8;
                r5.onDone(r4);
                goto L_0x0061;
            L_0x00bd:
                r5 = move-exception;
                r8 = r8;
                if (r8 == 0) goto L_0x00c7;
            L_0x00c2:
                r8 = r8;
                r8.onDone(r4);
            L_0x00c7:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.6.rundo():void");
            }
        });
        return true;
    }

    public boolean checkTKT(String uid, String account, String tkt, String appId, OnCommonListener listener) {
        String prefix = "[checkTKT][uid:" + uid + "][account:" + account + "][tkt:" + tkt + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " check tkt ...");
        final String str = uid;
        final String str2 = account;
        final String str3 = tkt;
        final String str4 = appId;
        final String str5 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r3;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r4;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r10 = r5;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r11 = r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0 = r5.buildCheckTKT(r8, r9, r10, r11);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0.execute();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                if (r5 != 0) goto L_0x0062;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0026:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] check tkt failed(";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = ")";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0058:
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x005c:
                r5 = r8;
                r5.onDone(r4);
            L_0x0061:
                return;
            L_0x0062:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] check tkt ok";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                goto L_0x0058;
            L_0x0088:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] check tkt failed(Exception)";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x00b7:
                r5 = r8;
                r5.onDone(r4);
                goto L_0x0061;
            L_0x00bd:
                r5 = move-exception;
                r8 = r8;
                if (r8 == 0) goto L_0x00c7;
            L_0x00c2:
                r8 = r8;
                r8.onDone(r4);
            L_0x00c7:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.7.rundo():void");
            }
        });
        return true;
    }

    public boolean checkToken(String openid, String access_token, String appId, OnCommonListener listener) {
        String prefix = "[checkToken][openid:" + openid + "][access_token:" + access_token + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " check token ...");
        final String str = openid;
        final String str2 = access_token;
        final String str3 = appId;
        final String str4 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r11 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r3;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = r4;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r10 = r5;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r0 = r5.buildCheckTKT(r8, r9, r10);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r0.execute();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                if (r5 != 0) goto L_0x0060;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
            L_0x0024:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8.<init>();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = r6;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = "] check token failed(";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = ")";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
            L_0x0056:
                r5 = r7;
                if (r5 == 0) goto L_0x005f;
            L_0x005a:
                r5 = r7;
                r5.onDone(r4);
            L_0x005f:
                return;
            L_0x0060:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8.<init>();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = r6;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = "] check token ok";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                goto L_0x0056;
            L_0x0086:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8.<init>();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = r6;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = "] check token failed(Exception)";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r5 = r7;
                if (r5 == 0) goto L_0x005f;
            L_0x00b5:
                r5 = r7;
                r5.onDone(r4);
                goto L_0x005f;
            L_0x00bb:
                r5 = move-exception;
                r8 = r7;
                if (r8 == 0) goto L_0x00c5;
            L_0x00c0:
                r8 = r7;
                r8.onDone(r4);
            L_0x00c5:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.8.rundo():void");
            }
        });
        return true;
    }

    public boolean logout(String uid, String tkt, String password, String appId, OnCommonListener listener) {
        final String md5 = EncryptUtils.getMD5String(password);
        String prefix = "[logout][uid:" + uid + "][tkt:" + tkt + "][pwd:" + md5 + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " logout ...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = appId;
        final String str4 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r3;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r4;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r10 = r5;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r11 = r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0 = r5.buildLogout(r8, r9, r10, r11);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0.execute();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                if (r5 != 0) goto L_0x0062;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0026:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] logout failed(";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = ")";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0058:
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x005c:
                r5 = r8;
                r5.onDone(r4);
            L_0x0061:
                return;
            L_0x0062:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] logout ok";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                goto L_0x0058;
            L_0x0088:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] logout failed(Exception)";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x00b7:
                r5 = r8;
                r5.onDone(r4);
                goto L_0x0061;
            L_0x00bd:
                r5 = move-exception;
                r8 = r8;
                if (r8 == 0) goto L_0x00c7;
            L_0x00c2:
                r8 = r8;
                r8.onDone(r4);
            L_0x00c7:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.9.rundo():void");
            }
        });
        return true;
    }

    public boolean getAppInfo(String appId, OnBundleListener listener) {
        String prefix = "[getAppInfo][appId:" + appId + "]";
        LOG.d(TAG, prefix + " get appInfo ...");
        final String str = appId;
        final String str2 = prefix;
        final OnBundleListener onBundleListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r11 = this;
                r3 = 1;
                r1 = 0;
                r6 = java.lang.System.currentTimeMillis();
                r4 = 0;
                r8 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r8 = r8.requestBuilder;	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r3;	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r0 = r8.buildGetAppInfo(r9);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r0.execute();	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r4 = r8 - r6;	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r8 = r0.ok();	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                if (r8 != 0) goto L_0x005d;	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
            L_0x0021:
                r3 = r0.getRcode();	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9.<init>();	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r10 = r4;	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r10 = "] get app info failed(";	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r3);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r10 = ")";	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
            L_0x0053:
                r8 = r5;
                if (r8 == 0) goto L_0x005c;
            L_0x0057:
                r8 = r5;
                r8.onDone(r3, r1);
            L_0x005c:
                return;
            L_0x005d:
                r1 = r0.getBundle();	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r3 = 0;	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9.<init>();	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r10 = r4;	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r10 = "] get app info ok(";	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r1);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r10 = ")";	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                com.coolcloud.uac.android.common.util.LOG.i(r8, r9);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                goto L_0x0053;
            L_0x0091:
                r2 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r4 = r8 - r6;	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9.<init>();	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r10 = r4;	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r10 = "] get app info failed(Exception)";	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9, r2);	 Catch:{ Exception -> 0x0091, all -> 0x00c6 }
                r8 = r5;
                if (r8 == 0) goto L_0x005c;
            L_0x00c0:
                r8 = r5;
                r8.onDone(r3, r1);
                goto L_0x005c;
            L_0x00c6:
                r8 = move-exception;
                r9 = r5;
                if (r9 == 0) goto L_0x00d0;
            L_0x00cb:
                r9 = r5;
                r9.onDone(r3, r1);
            L_0x00d0:
                throw r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.10.rundo():void");
            }
        });
        return true;
    }

    public boolean getBasicUserInfo(String uid, String tkt, String appId, OnBundleListener listener) {
        String prefix = "[getBasicUserInfo][uid:" + uid + "][tkt:" + tkt + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " get basic user info ...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = appId;
        final String str4 = prefix;
        final OnBundleListener onBundleListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r5 = 0;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r8 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = r8.requestBuilder;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r3;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = r4;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r11 = r5;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r0 = r8.buildGetBasicUserInfo(r9, r10, r11);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r0.execute();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = r0.ok();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                if (r8 != 0) goto L_0x0061;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
            L_0x0025:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9.<init>();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = r6;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = "] get basic user info failed(";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = ")";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
            L_0x0057:
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x005b:
                r8 = r7;
                r8.onDone(r4, r5);
            L_0x0060:
                return;
            L_0x0061:
                r5 = r0.getBundle();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r4 = 0;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9.<init>();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = r6;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = "] get basic user info ok(";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r5);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = ")";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                com.coolcloud.uac.android.common.util.LOG.i(r8, r9);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                goto L_0x0057;
            L_0x0095:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9.<init>();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = r6;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = "] get basic user info failed(Exception)";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9, r1);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x00c4:
                r8 = r7;
                r8.onDone(r4, r5);
                goto L_0x0060;
            L_0x00ca:
                r8 = move-exception;
                r9 = r7;
                if (r9 == 0) goto L_0x00d4;
            L_0x00cf:
                r9 = r7;
                r9.onDone(r4, r5);
            L_0x00d4:
                throw r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.11.rundo():void");
            }
        });
        return true;
    }

    public boolean getDetailUserInfo(String uid, String tkt, String appId, OnBundleListener listener) {
        String prefix = "[getDetailUserInfo][uid:" + uid + "][tkt:" + tkt + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " get detail user info ...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = appId;
        final String str4 = prefix;
        final OnBundleListener onBundleListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r5 = 0;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r8 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = r8.requestBuilder;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r3;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = r4;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r11 = r5;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r0 = r8.buildGetDetailUserInfo(r9, r10, r11);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r0.execute();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = r0.ok();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                if (r8 != 0) goto L_0x0061;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
            L_0x0025:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9.<init>();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = r6;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = "] get detail user info failed(";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = ")";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
            L_0x0057:
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x005b:
                r8 = r7;
                r8.onDone(r4, r5);
            L_0x0060:
                return;
            L_0x0061:
                r5 = r0.getBundle();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r4 = 0;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9.<init>();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = r6;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = "] get detail user info ok(";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r5);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = ")";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                com.coolcloud.uac.android.common.util.LOG.i(r8, r9);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                goto L_0x0057;
            L_0x0095:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9.<init>();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = r6;	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r10 = "] get detail user info failed(Exception)";	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9, r1);	 Catch:{ Exception -> 0x0095, all -> 0x00ca }
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x00c4:
                r8 = r7;
                r8.onDone(r4, r5);
                goto L_0x0060;
            L_0x00ca:
                r8 = move-exception;
                r9 = r7;
                if (r9 == 0) goto L_0x00d4;
            L_0x00cf:
                r9 = r7;
                r9.onDone(r4, r5);
            L_0x00d4:
                throw r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.12.rundo():void");
            }
        });
        return true;
    }

    private String b64(String source) {
        try {
            return new String(Base64.encodeBase64(source.getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.e(TAG, "[source:" + source + "] base64 failed(UnsupportedEncodingException)", e);
            return "";
        }
    }

    public boolean uploadLogo(String uid, String tkt, String logoUrl, OnUploadLogoListener listener) {
        String prefix = "[uid:" + uid + "][tkt:" + tkt + "][logoUrl:" + logoUrl + "]";
        LOG.d(TAG, prefix + " upload logo ...");
        final String str = tkt;
        final String str2 = uid;
        final String str3 = logoUrl;
        final OnUploadLogoListener onUploadLogoListener = listener;
        final String str4 = prefix;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                Throwable e;
                Throwable th;
                String turl = "/rest/2.0/upload/headimg?method=upload&access_token=" + str + "&uid=" + str2 + "&channel=uac";
                long start = System.currentTimeMillis();
                RandomAccessFile localRandomAccessFile = null;
                try {
                    File photoFile = new File(str3);
                    int len = (int) photoFile.length();
                    RandomAccessFile localRandomAccessFile2 = new RandomAccessFile(photoFile, "r");
                    try {
                        byte[] buffer = new byte[len];
                        localRandomAccessFile2.readFully(buffer);
                        Map<String, String> headers = new HashMap();
                        headers.put(HttpRequest.HEADER_ACCEPT_ENCODING, "deflate");
                        headers.put("Content-Disposition", "attachment, filename=\"" + BasicWsApi.this.b64(photoFile.getName()) + "\"");
                        headers.put("Content-Range", "bytes 0-" + (photoFile.length() - 1) + "/" + photoFile.length());
                        headers.put(HttpRequest.HEADER_CONTENT_TYPE, "application/octet-stream");
                        headers.put("Charset", "UTF-8");
                        headers.put("Session-ID", System.currentTimeMillis() + "");
                        HTTPTransporter.createTransporter().upload(FileUpload.getHosts(), turl, buffer, headers, new 1(this));
                        if (localRandomAccessFile2 != null) {
                            try {
                                localRandomAccessFile2.close();
                                return;
                            } catch (IOException e2) {
                                LOG.e(BasicWsApi.TAG, str4 + "] close file failed(Throwable)", e2);
                                localRandomAccessFile = localRandomAccessFile2;
                                return;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        localRandomAccessFile = localRandomAccessFile2;
                        if (localRandomAccessFile != null) {
                            localRandomAccessFile.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    e = th3;
                    LOG.e(BasicWsApi.TAG, str4 + "[millis:" + (System.currentTimeMillis() - start) + "] get logo failed(Throwable)", e);
                    if (onUploadLogoListener != null) {
                        onUploadLogoListener.onDone(1, null);
                    }
                    if (localRandomAccessFile != null) {
                        localRandomAccessFile.close();
                    }
                }
            }
        });
        return true;
    }

    public boolean updateHeadImage(String uid, String tkt, String key, String value, String appId, OnBundleListener listener) {
        final String prefix = "[updateHeadImage][uid:" + uid + "][tkt:" + tkt + "][key:" + key + "][value:" + value + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + "update user head image...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = key;
        final String str4 = value;
        final String str5 = appId;
        final OnBundleListener onBundleListener = listener;
        Executor.execute(new Runnable() {
            public void run() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r14 = this;
                r10 = 1;
                r11 = 0;
                r12 = java.lang.System.currentTimeMillis();
                r8 = 0;
                r0 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = r0.requestBuilder;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r2;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = r3;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r3 = r4;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r4 = r5;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r5 = r6;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r6 = r0.buildUpdateHeadImage(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r6.execute();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = r6.ok();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                if (r0 != 0) goto L_0x0065;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
            L_0x0029:
                r10 = r6.getRcode();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1.<init>();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = r7;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = "] update head image failed(";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = ")";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
            L_0x005b:
                r0 = r8;
                if (r0 == 0) goto L_0x0064;
            L_0x005f:
                r0 = r8;
                r0.onDone(r10, r11);
            L_0x0064:
                return;
            L_0x0065:
                r10 = 0;
                r11 = r6.getBundle();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1.<init>();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = r7;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = "] update head image ok!";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                com.coolcloud.uac.android.common.util.LOG.i(r0, r1);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                goto L_0x005b;
            L_0x008f:
                r7 = move-exception;
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1.<init>();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = r7;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = "] update head image failed(Exception)";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1, r7);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = r8;
                if (r0 == 0) goto L_0x0064;
            L_0x00be:
                r0 = r8;
                r0.onDone(r10, r11);
                goto L_0x0064;
            L_0x00c4:
                r0 = move-exception;
                r1 = r8;
                if (r1 == 0) goto L_0x00ce;
            L_0x00c9:
                r1 = r8;
                r1.onDone(r10, r11);
            L_0x00ce:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.14.run():void");
            }
        });
        return true;
    }

    public boolean updateUserItem(String uid, String tkt, String key, String value, String appId, OnCommonListener listener) {
        String prefix = "[updateUserItem][uid:" + uid + "][tkt:" + tkt + "][key:" + key + "][value:" + value + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + "] update user item ...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = key;
        final String str4 = value;
        final String str5 = appId;
        final String str6 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r14 = this;
                r10 = 1;
                r12 = java.lang.System.currentTimeMillis();
                r8 = 0;
                r0 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r0.requestBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r4;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r3 = r5;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r4 = r6;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r5 = r7;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r6 = r0.buildUpdateUserItem(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r6.execute();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r6.ok();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                if (r0 != 0) goto L_0x0064;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
            L_0x0028:
                r10 = r6.getRcode();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] update user item failed(";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = ")";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
            L_0x005a:
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x005e:
                r0 = r9;
                r0.onDone(r10);
            L_0x0063:
                return;
            L_0x0064:
                r10 = 0;
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] update user item ok";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.i(r0, r1);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                goto L_0x005a;
            L_0x008a:
                r7 = move-exception;
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] update user item failed(Exception)";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1, r7);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x00b9:
                r0 = r9;
                r0.onDone(r10);
                goto L_0x0063;
            L_0x00bf:
                r0 = move-exception;
                r1 = r9;
                if (r1 == 0) goto L_0x00c9;
            L_0x00c4:
                r1 = r9;
                r1.onDone(r10);
            L_0x00c9:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.15.rundo():void");
            }
        });
        return true;
    }

    public boolean checkAuthorized(String uid, String appId, String scope, OnCommonListener listener) {
        String prefix = "[checkAuthorized][uid:" + uid + "][appId:" + appId + "][scope:" + scope + "]";
        LOG.d(TAG, prefix + "] check authorized ...");
        final String str = uid;
        final String str2 = appId;
        final String str3 = scope;
        final String str4 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r11 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r3;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = r4;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r10 = r5;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r0 = r5.buildCheckAuthorized(r8, r9, r10);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r0.execute();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                if (r5 != 0) goto L_0x0060;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
            L_0x0024:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8.<init>();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = r6;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = "] check authorized failed(";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = ")";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
            L_0x0056:
                r5 = r7;
                if (r5 == 0) goto L_0x005f;
            L_0x005a:
                r5 = r7;
                r5.onDone(r4);
            L_0x005f:
                return;
            L_0x0060:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8.<init>();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = r6;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = "] check authorized ok";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                goto L_0x0056;
            L_0x0086:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8.<init>();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = r6;	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r9 = "] check authorized failed(Exception)";	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0086, all -> 0x00bb }
                r5 = r7;
                if (r5 == 0) goto L_0x005f;
            L_0x00b5:
                r5 = r7;
                r5.onDone(r4);
                goto L_0x005f;
            L_0x00bb:
                r5 = move-exception;
                r8 = r7;
                if (r8 == 0) goto L_0x00c5;
            L_0x00c0:
                r8 = r7;
                r8.onDone(r4);
            L_0x00c5:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.16.rundo():void");
            }
        });
        return true;
    }

    public boolean getTokenImplicit(String uid, String tkt, String appId, String scope, OnTokenListener listener) {
        String prefix = "[uid:" + uid + "][tkt:" + tkt + "][appId:" + appId + "][scope:" + scope + "]";
        LOG.d(TAG, prefix + " get token ...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = appId;
        final String str4 = scope;
        final String str5 = prefix;
        final OnTokenListener onTokenListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                int rcode = 1;
                String openId = null;
                String accessToken = null;
                String refreshToken = null;
                long expiresin = 0;
                long start = System.currentTimeMillis();
                long millis;
                try {
                    HTTPAgent agent = BasicWsApi.this.requestBuilder.buildGetTokenImplicit(str, str2, str3, str4);
                    agent.execute();
                    millis = System.currentTimeMillis() - start;
                    if (agent.ok()) {
                        openId = agent.getString("openid");
                        accessToken = agent.getString("access_token");
                        refreshToken = agent.getString(Oauth2AccessToken.KEY_REFRESH_TOKEN);
                        expiresin = Long.valueOf(agent.getString("expires_in")).longValue();
                        if (TextUtils.isEmpty(openId)) {
                            rcode = LiveRoomConstant.LIVE_ROOM_LOADER_HK_MUSIC_ID;
                            LOG.e(BasicWsApi.TAG, str5 + "[millis:" + millis + "] get token implicit invalid(" + openId + "," + accessToken + "," + refreshToken + "," + expiresin + ")");
                        } else if (TextUtils.isEmpty(accessToken)) {
                            rcode = LiveRoomConstant.LIVE_ROOM_LOADER_HK_MUSIC_ID;
                            LOG.e(BasicWsApi.TAG, str5 + "[millis:" + millis + "] get token implicit invalid(" + openId + "," + accessToken + "," + refreshToken + "," + expiresin + ")");
                        } else {
                            rcode = 0;
                            LOG.i(BasicWsApi.TAG, str5 + "[millis:" + millis + "] get token implicit ok(" + openId + "," + accessToken + "," + refreshToken + "," + expiresin + ")");
                        }
                    } else {
                        rcode = agent.getRcode();
                        LOG.e(BasicWsApi.TAG, str5 + "[millis:" + millis + "] get token implicit failed(" + rcode + ")");
                    }
                    if (onTokenListener != null) {
                        onTokenListener.onDone(rcode, openId, accessToken, refreshToken, expiresin);
                    }
                } catch (Exception e) {
                    millis = System.currentTimeMillis() - start;
                    LOG.e(BasicWsApi.TAG, str5 + "] get token implicit failed(Exception)", e);
                    if (onTokenListener != null) {
                        onTokenListener.onDone(rcode, openId, accessToken, refreshToken, expiresin);
                    }
                } catch (Throwable th) {
                    Throwable th2 = th;
                    if (onTokenListener != null) {
                        onTokenListener.onDone(rcode, openId, accessToken, refreshToken, expiresin);
                    }
                }
            }
        });
        return true;
    }

    public boolean getAuthCode(String uid, String tkt, String appId, String scope, OnAuthCodeListener listener) {
        final String prefix = "[uid:" + uid + "][tkt:" + tkt + "][appId:" + appId + "][scope:" + scope + "]";
        LOG.d(TAG, prefix + " get auth code ...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = appId;
        final String str4 = scope;
        final OnAuthCodeListener onAuthCodeListener = listener;
        Executor.execute(new Runnable() {
            public void run() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r13 = this;
                r3 = 1;
                r1 = 0;
                r6 = java.lang.System.currentTimeMillis();
                r4 = 0;
                r8 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r8 = r8.requestBuilder;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r2;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = r3;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r11 = r4;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r12 = r5;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r0 = r8.buildGetAuthCode(r9, r10, r11, r12);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r0.execute();	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r4 = r8 - r6;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r8 = r0.ok();	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                if (r8 != 0) goto L_0x0063;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
            L_0x0027:
                r3 = r0.getRcode();	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9.<init>();	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = r6;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = "[millis:";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = "] get auth code failed(";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r3);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = ")";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.toString();	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
            L_0x0059:
                r8 = r7;
                if (r8 == 0) goto L_0x0062;
            L_0x005d:
                r8 = r7;
                r8.onDone(r3, r1);
            L_0x0062:
                return;
            L_0x0063:
                r8 = "code";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r1 = r0.getString(r8);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r8 = com.coolcloud.uac.android.common.util.TextUtils.isEmpty(r1);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                if (r8 == 0) goto L_0x00cb;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
            L_0x006f:
                r3 = 5001; // 0x1389 float:7.008E-42 double:2.471E-320;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9.<init>();	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = r6;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = "[millis:";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = "] get auth code invalid(";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r1);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = ")";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.toString();	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                goto L_0x0059;
            L_0x00a0:
                r2 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r4 = r8 - r6;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9.<init>();	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = r6;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = "] get auth code failed(Exception)";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.toString();	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9, r2);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r8 = r7;
                if (r8 == 0) goto L_0x0062;
            L_0x00c5:
                r8 = r7;
                r8.onDone(r3, r1);
                goto L_0x0062;
            L_0x00cb:
                r3 = 0;
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9.<init>();	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = r6;	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = "[millis:";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = "] get auth code ok(";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r1);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r10 = ")";	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                r9 = r9.toString();	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                com.coolcloud.uac.android.common.util.LOG.i(r8, r9);	 Catch:{ Exception -> 0x00a0, all -> 0x00fc }
                goto L_0x0059;
            L_0x00fc:
                r8 = move-exception;
                r9 = r7;
                if (r9 == 0) goto L_0x0106;
            L_0x0101:
                r9 = r7;
                r9.onDone(r3, r1);
            L_0x0106:
                throw r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.18.run():void");
            }
        });
        return true;
    }

    public boolean checkPresentOnActivate(String ccid, String imsi, String deviceId, String deviceModel, String appId, OnCheckActivateListener listener) {
        String prefix = "[ccid:" + ccid + "][imsi:" + imsi + "][deviceId:" + deviceId + "][deviceModel:" + deviceModel + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " check present on activate ...");
        final String str = ccid;
        final String str2 = imsi;
        final String str3 = appId;
        final String str4 = prefix;
        final OnCheckActivateListener onCheckActivateListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r5 = 1;
                r4 = 0;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r8 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r8 = r8.requestBuilder;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r3;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r10 = r4;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r11 = r5;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r0 = r8.buildCheckPresentOnActivate(r9, r10, r11);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r0.execute();	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r8 = r0.ok();	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                if (r8 != 0) goto L_0x0072;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
            L_0x0025:
                r5 = r0.getRcode();	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r8 = "phone";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r4 = r0.getString(r8);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9.<init>();	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r10 = r6;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r10 = "[millis:";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r10 = "] check present on activate failed(";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r5);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r10 = ",";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r10 = ")";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.toString();	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
            L_0x0068:
                r8 = r7;
                if (r8 == 0) goto L_0x0071;
            L_0x006c:
                r8 = r7;
                r8.onDone(r5, r4);
            L_0x0071:
                return;
            L_0x0072:
                r5 = 0;
                r8 = "phone";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r4 = r0.getString(r8);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9.<init>();	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r10 = r6;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r10 = "[millis:";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r10 = "] check present on activate code ok(";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r10 = ")";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.toString();	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                com.coolcloud.uac.android.common.util.LOG.i(r8, r9);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                goto L_0x0068;
            L_0x00a9:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9.<init>();	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r10 = r6;	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r10 = "[millis:";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r10 = "] check present on activate failed(Exception)";	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r9 = r9.toString();	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9, r1);	 Catch:{ Exception -> 0x00a9, all -> 0x00de }
                r8 = r7;
                if (r8 == 0) goto L_0x0071;
            L_0x00d8:
                r8 = r7;
                r8.onDone(r5, r4);
                goto L_0x0071;
            L_0x00de:
                r8 = move-exception;
                r9 = r7;
                if (r9 == 0) goto L_0x00e8;
            L_0x00e3:
                r9 = r7;
                r9.onDone(r5, r4);
            L_0x00e8:
                throw r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.19.rundo():void");
            }
        });
        return true;
    }

    public boolean pollingOnActivate(String simId, String deviceId, String deviceModel, String appId, OnActivateListener listener) {
        String prefix = "[simId:" + simId + "][deviceId:" + deviceId + "][deviceModel:" + deviceModel + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " polling on activate ...");
        final String str = simId;
        final String str2 = appId;
        final String str3 = prefix;
        final OnActivateListener onActivateListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r13 = this;
                r5 = 1;
                r4 = 0;
                r9 = 0;
                r8 = 0;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r10 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r10 = r10.requestBuilder;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r3;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r12 = r4;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r0 = r10.buildPollingOnActivate(r11, r12);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r0.execute();	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r10 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r2 = r10 - r6;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r10 = r0.ok();	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                if (r10 != 0) goto L_0x0072;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
            L_0x0025:
                r5 = r0.getRcode();	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r10 = "phone";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r4 = r0.getString(r10);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r10 = "BasicWsApi";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11.<init>();	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r12 = r5;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r12 = "[millis:";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r2);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r12 = "] polling on activate failed(";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r5);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r12 = ",";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r4);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r12 = ")";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.toString();	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                com.coolcloud.uac.android.common.util.LOG.e(r10, r11);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
            L_0x0068:
                r10 = r6;
                if (r10 == 0) goto L_0x0071;
            L_0x006c:
                r10 = r6;
                r10.onDone(r5, r4, r9, r8);
            L_0x0071:
                return;
            L_0x0072:
                r10 = "phone";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r4 = r0.getString(r10);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r10 = "uid";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r9 = r0.getString(r10);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r10 = "tkt";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r8 = r0.getString(r10);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r5 = 0;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r10 = "BasicWsApi";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11.<init>();	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r12 = r5;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r12 = "[millis:";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r2);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r12 = "] polling on activate code ok";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.toString();	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                com.coolcloud.uac.android.common.util.LOG.i(r10, r11);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                goto L_0x0068;
            L_0x00ad:
                r1 = move-exception;
                r10 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r2 = r10 - r6;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r10 = "BasicWsApi";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11.<init>();	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r12 = r5;	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r12 = "[millis:";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r2);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r12 = "] polling on activate failed(Exception)";	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r11 = r11.toString();	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                com.coolcloud.uac.android.common.util.LOG.e(r10, r11, r1);	 Catch:{ Exception -> 0x00ad, all -> 0x00e2 }
                r10 = r6;
                if (r10 == 0) goto L_0x0071;
            L_0x00dc:
                r10 = r6;
                r10.onDone(r5, r4, r9, r8);
                goto L_0x0071;
            L_0x00e2:
                r10 = move-exception;
                r11 = r6;
                if (r11 == 0) goto L_0x00ec;
            L_0x00e7:
                r11 = r6;
                r11.onDone(r5, r4, r9, r8);
            L_0x00ec:
                throw r10;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.20.rundo():void");
            }
        });
        return true;
    }

    public boolean registerPhoneGetActivateCode(String phone, String appId, OnCommonListener listener) {
        String prefix = "[phone:" + phone + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " register phone get activate code ...");
        final String str = phone;
        final String str2 = appId;
        final String str3 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r10 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r3;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r4;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r0 = r5.buildRegisterPhoneGetActivateCode(r8, r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r0.execute();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                if (r5 != 0) goto L_0x005e;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
            L_0x0022:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] register phone get activate code failed(";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = ")";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
            L_0x0054:
                r5 = r6;
                if (r5 == 0) goto L_0x005d;
            L_0x0058:
                r5 = r6;
                r5.onDone(r4);
            L_0x005d:
                return;
            L_0x005e:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] register phone get activate code ok";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                goto L_0x0054;
            L_0x0084:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] register phone get activate code failed(Exception)";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r6;
                if (r5 == 0) goto L_0x005d;
            L_0x00b3:
                r5 = r6;
                r5.onDone(r4);
                goto L_0x005d;
            L_0x00b9:
                r5 = move-exception;
                r8 = r6;
                if (r8 == 0) goto L_0x00c3;
            L_0x00be:
                r8 = r6;
                r8.onDone(r4);
            L_0x00c3:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.21.rundo():void");
            }
        });
        return true;
    }

    public boolean registerPhone(String phone, String code, String password, String nickname, String appId, OnRegisterListener listener) {
        final String md5 = EncryptUtils.getMD5String(password);
        String prefix = "[phone:" + phone + "][code:" + code + "][pwd:" + md5 + "][nickname:" + nickname + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " register phone ...");
        final String str = phone;
        final String str2 = code;
        final String str3 = nickname;
        final String str4 = appId;
        final String str5 = prefix;
        final OnRegisterListener onRegisterListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r14 = this;
                r9 = 1;
                r6 = 0;
                r12 = java.lang.System.currentTimeMillis();
                r10 = 0;
                r0 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r0 = r0.requestBuilder;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r3;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r2 = r4;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r3 = r5;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r4 = r6;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r5 = r7;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r7 = r0.buildRegisterPhone(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r7.execute();	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r10 = r0 - r12;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r0 = r7.ok();	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                if (r0 != 0) goto L_0x0065;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
            L_0x0029:
                r9 = r7.getRcode();	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1.<init>();	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r2 = r8;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r2 = "] register phone failed(";	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r9);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r2 = ")";	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
            L_0x005b:
                r0 = r9;
                if (r0 == 0) goto L_0x0064;
            L_0x005f:
                r0 = r9;
                r0.onDone(r9, r6);
            L_0x0064:
                return;
            L_0x0065:
                r9 = 0;
                r0 = "accountid";	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r6 = r7.getString(r0);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1.<init>();	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r2 = r8;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r2 = "] register phone ok(";	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r6);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r2 = ")";	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                com.coolcloud.uac.android.common.util.LOG.i(r0, r1);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                goto L_0x005b;
            L_0x009b:
                r8 = move-exception;
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r10 = r0 - r12;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1.<init>();	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r2 = r8;	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r2 = "] register phone failed(Exception)";	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1, r8);	 Catch:{ Exception -> 0x009b, all -> 0x00d0 }
                r0 = r9;
                if (r0 == 0) goto L_0x0064;
            L_0x00ca:
                r0 = r9;
                r0.onDone(r9, r6);
                goto L_0x0064;
            L_0x00d0:
                r0 = move-exception;
                r1 = r9;
                if (r1 == 0) goto L_0x00da;
            L_0x00d5:
                r1 = r9;
                r1.onDone(r9, r6);
            L_0x00da:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.22.rundo():void");
            }
        });
        return true;
    }

    public boolean forwardPhoneGetActivateCode(String phone, String appId, OnCommonListener listener) {
        String prefix = "[phone:" + phone + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " forward phone get activate code ...");
        final String str = phone;
        final String str2 = appId;
        final String str3 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r10 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r3;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r4;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r0 = r5.buildForwardPhoneGetActivateCode(r8, r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r0.execute();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                if (r5 != 0) goto L_0x005e;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
            L_0x0022:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] forward phone get activate code failed(";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = ")";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
            L_0x0054:
                r5 = r6;
                if (r5 == 0) goto L_0x005d;
            L_0x0058:
                r5 = r6;
                r5.onDone(r4);
            L_0x005d:
                return;
            L_0x005e:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] forward phone get activate code ok";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                goto L_0x0054;
            L_0x0084:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] forward phone get activate code failed(Exception)";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r6;
                if (r5 == 0) goto L_0x005d;
            L_0x00b3:
                r5 = r6;
                r5.onDone(r4);
                goto L_0x005d;
            L_0x00b9:
                r5 = move-exception;
                r8 = r6;
                if (r8 == 0) goto L_0x00c3;
            L_0x00be:
                r8 = r6;
                r8.onDone(r4);
            L_0x00c3:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.23.rundo():void");
            }
        });
        return true;
    }

    public boolean forwardPhone(String phone, String activateCode, String uid, String password, String appId, OnCommonListener listener) {
        final String md5 = EncryptUtils.getMD5String(password);
        String prefix = "[phone:" + phone + "][activateCode:" + activateCode + "][uid:" + uid + "][pwd:" + md5 + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " forward phone ...");
        final String str = phone;
        final String str2 = activateCode;
        final String str3 = uid;
        final String str4 = appId;
        final String str5 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r14 = this;
                r10 = 1;
                r12 = java.lang.System.currentTimeMillis();
                r8 = 0;
                r0 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r0.requestBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r4;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r3 = r5;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r4 = r6;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r5 = r7;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r6 = r0.buildForwardPhone(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r6.execute();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r6.ok();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                if (r0 != 0) goto L_0x0064;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
            L_0x0028:
                r10 = r6.getRcode();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] forward phone failed(";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = ")";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
            L_0x005a:
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x005e:
                r0 = r9;
                r0.onDone(r10);
            L_0x0063:
                return;
            L_0x0064:
                r10 = 0;
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] forward phone code ok";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.i(r0, r1);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                goto L_0x005a;
            L_0x008a:
                r7 = move-exception;
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] bind phone failed(Exception)";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1, r7);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x00b9:
                r0 = r9;
                r0.onDone(r10);
                goto L_0x0063;
            L_0x00bf:
                r0 = move-exception;
                r1 = r9;
                if (r1 == 0) goto L_0x00c9;
            L_0x00c4:
                r1 = r9;
                r1.onDone(r10);
            L_0x00c9:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.24.rundo():void");
            }
        });
        return true;
    }

    public boolean forwardThird(String tid, String account, String password, String appId, OnCommonListener listener) {
        final String md5 = EncryptUtils.getMD5String(password);
        String prefix = "[tid:" + tid + "][account:" + account + "][pwd:" + md5 + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " forward third ...");
        final String str = tid;
        final String str2 = account;
        final String str3 = appId;
        final String str4 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r3;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r4;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r10 = r5;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r11 = r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0 = r5.buildForwardThird(r8, r9, r10, r11);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0.execute();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                if (r5 != 0) goto L_0x0062;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0026:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] forward third failed(";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = ")";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0058:
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x005c:
                r5 = r8;
                r5.onDone(r4);
            L_0x0061:
                return;
            L_0x0062:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] forward third code ok";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                goto L_0x0058;
            L_0x0088:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] forward third failed(Exception)";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x00b7:
                r5 = r8;
                r5.onDone(r4);
                goto L_0x0061;
            L_0x00bd:
                r5 = move-exception;
                r8 = r8;
                if (r8 == 0) goto L_0x00c7;
            L_0x00c2:
                r8 = r8;
                r8.onDone(r4);
            L_0x00c7:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.25.rundo():void");
            }
        });
        return true;
    }

    public boolean bindPhoneGetActivateCode(String phone, String appId, OnCommonListener listener) {
        String prefix = "[phone:" + phone + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " bind phone get activate code ...");
        final String str = phone;
        final String str2 = appId;
        final String str3 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r10 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r3;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r4;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r0 = r5.buildBindPhoneGetActivateCode(r8, r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r0.execute();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                if (r5 != 0) goto L_0x005e;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
            L_0x0022:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] bind phone get activate code failed(";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = ")";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
            L_0x0054:
                r5 = r6;
                if (r5 == 0) goto L_0x005d;
            L_0x0058:
                r5 = r6;
                r5.onDone(r4);
            L_0x005d:
                return;
            L_0x005e:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] bind phone get activate code ok";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                goto L_0x0054;
            L_0x0084:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] bind phone get activate code failed(Exception)";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r6;
                if (r5 == 0) goto L_0x005d;
            L_0x00b3:
                r5 = r6;
                r5.onDone(r4);
                goto L_0x005d;
            L_0x00b9:
                r5 = move-exception;
                r8 = r6;
                if (r8 == 0) goto L_0x00c3;
            L_0x00be:
                r8 = r6;
                r8.onDone(r4);
            L_0x00c3:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.26.rundo():void");
            }
        });
        return true;
    }

    public boolean bindPhone(String phone, String rtkt, String activateCode, String uid, String password, String appId, OnCommonListener listener) {
        final String md5 = EncryptUtils.getMD5String(password);
        String prefix = "[phone:" + phone + "][activateCode:" + activateCode + "][uid:" + uid + "][pwd:" + md5 + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " bind phone ...");
        final String str = phone;
        final String str2 = rtkt;
        final String str3 = activateCode;
        final String str4 = uid;
        final String str5 = appId;
        final String str6 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r14 = this;
                r9 = 1;
                r12 = java.lang.System.currentTimeMillis();
                r10 = 0;
                r0 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r0 = r0.requestBuilder;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r4;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r2 = r5;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r3 = r6;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r4 = r7;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r5 = r8;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r6 = r9;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r7 = r0.buildBindPhone(r1, r2, r3, r4, r5, r6);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r7.execute();	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r10 = r0 - r12;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r0 = r7.ok();	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                if (r0 != 0) goto L_0x0066;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
            L_0x002a:
                r9 = r7.getRcode();	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1.<init>();	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r2 = r10;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r2 = "] bind phone failed(";	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r9);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r2 = ")";	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
            L_0x005c:
                r0 = r11;
                if (r0 == 0) goto L_0x0065;
            L_0x0060:
                r0 = r11;
                r0.onDone(r9);
            L_0x0065:
                return;
            L_0x0066:
                r9 = 0;
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1.<init>();	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r2 = r10;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r2 = "] bind phone code ok";	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                com.coolcloud.uac.android.common.util.LOG.i(r0, r1);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                goto L_0x005c;
            L_0x008c:
                r8 = move-exception;
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r10 = r0 - r12;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1.<init>();	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r2 = r10;	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r2 = "] bind phone failed(Exception)";	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1, r8);	 Catch:{ Exception -> 0x008c, all -> 0x00c1 }
                r0 = r11;
                if (r0 == 0) goto L_0x0065;
            L_0x00bb:
                r0 = r11;
                r0.onDone(r9);
                goto L_0x0065;
            L_0x00c1:
                r0 = move-exception;
                r1 = r11;
                if (r1 == 0) goto L_0x00cb;
            L_0x00c6:
                r1 = r11;
                r1.onDone(r9);
            L_0x00cb:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.27.rundo():void");
            }
        });
        return true;
    }

    public boolean bindEmail(String email, String uid, String tkt, String password, String appId, OnCommonListener listener) {
        final String md5 = EncryptUtils.getMD5String(password);
        String prefix = "[email:" + email + "][uid:" + uid + "][pwd:" + md5 + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " bind email ...");
        final String str = email;
        final String str2 = tkt;
        final String str3 = uid;
        final String str4 = appId;
        final String str5 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r14 = this;
                r10 = 1;
                r12 = java.lang.System.currentTimeMillis();
                r8 = 0;
                r0 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r0.requestBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r4;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r3 = r5;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r4 = r6;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r5 = r7;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r6 = r0.buildBindEmail(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r6.execute();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r6.ok();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                if (r0 != 0) goto L_0x0064;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
            L_0x0028:
                r10 = r6.getRcode();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] bind email failed(";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = ")";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
            L_0x005a:
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x005e:
                r0 = r9;
                r0.onDone(r10);
            L_0x0063:
                return;
            L_0x0064:
                r10 = 0;
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] bind email code ok";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.i(r0, r1);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                goto L_0x005a;
            L_0x008a:
                r7 = move-exception;
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] bind email failed(Exception)";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1, r7);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x00b9:
                r0 = r9;
                r0.onDone(r10);
                goto L_0x0063;
            L_0x00bf:
                r0 = move-exception;
                r1 = r9;
                if (r1 == 0) goto L_0x00c9;
            L_0x00c4:
                r1 = r9;
                r1.onDone(r10);
            L_0x00c9:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.28.rundo():void");
            }
        });
        return true;
    }

    public boolean findpwdPhoneGetActivateCode(String phone, String appId, OnCommonListener listener) {
        String prefix = "[phone:" + phone + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " findpwd phone get activate code ...");
        final String str = phone;
        final String str2 = appId;
        final String str3 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r10 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r3;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r4;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r0 = r5.buildFindpwdPhoneGetActivateCode(r8, r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r0.execute();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                if (r5 != 0) goto L_0x005e;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
            L_0x0022:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] findpwd phone get activate code failed(";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = ")";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
            L_0x0054:
                r5 = r6;
                if (r5 == 0) goto L_0x005d;
            L_0x0058:
                r5 = r6;
                r5.onDone(r4);
            L_0x005d:
                return;
            L_0x005e:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] findpwd phone get activate code ok";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                goto L_0x0054;
            L_0x0084:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] findpwd phone get activate code failed(Exception)";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r6;
                if (r5 == 0) goto L_0x005d;
            L_0x00b3:
                r5 = r6;
                r5.onDone(r4);
                goto L_0x005d;
            L_0x00b9:
                r5 = move-exception;
                r8 = r6;
                if (r8 == 0) goto L_0x00c3;
            L_0x00be:
                r8 = r6;
                r8.onDone(r4);
            L_0x00c3:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.29.rundo():void");
            }
        });
        return true;
    }

    public boolean findpwdPhoneSetPwd(String phone, String activateCode, String password, String appId, OnCommonListener listener) {
        final String md5 = EncryptUtils.getMD5String(password);
        String prefix = "[phone:" + phone + "][activateCode:" + activateCode + "][pwd:" + md5 + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " findpwd phone set pwd ...");
        final String str = phone;
        final String str2 = activateCode;
        final String str3 = appId;
        final String str4 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r3;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r4;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r10 = r5;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r11 = r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0 = r5.buildFindpwdPhoneSetPwd(r8, r9, r10, r11);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0.execute();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                if (r5 != 0) goto L_0x0062;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0026:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] findpwd phone set pwd failed(";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = ")";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0058:
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x005c:
                r5 = r8;
                r5.onDone(r4);
            L_0x0061:
                return;
            L_0x0062:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] findpwd phone set pwd ok";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                goto L_0x0058;
            L_0x0088:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] findpwd phone set pwd failed(Exception)";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x00b7:
                r5 = r8;
                r5.onDone(r4);
                goto L_0x0061;
            L_0x00bd:
                r5 = move-exception;
                r8 = r8;
                if (r8 == 0) goto L_0x00c7;
            L_0x00c2:
                r8 = r8;
                r8.onDone(r4);
            L_0x00c7:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.30.rundo():void");
            }
        });
        return true;
    }

    public boolean findpwdEmail(String email, String appId, OnCommonListener listener) {
        String prefix = "[email:" + email + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " findpwd email ...");
        final String str = email;
        final String str2 = appId;
        final String str3 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r10 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r3;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r4;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r0 = r5.buildFindpwdEmail(r8, r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r0.execute();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                if (r5 != 0) goto L_0x005e;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
            L_0x0022:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] findpwd email failed(";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = ")";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
            L_0x0054:
                r5 = r6;
                if (r5 == 0) goto L_0x005d;
            L_0x0058:
                r5 = r6;
                r5.onDone(r4);
            L_0x005d:
                return;
            L_0x005e:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] findpwd email ok";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                goto L_0x0054;
            L_0x0084:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8.<init>();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = r5;	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r9 = "] findpwd email failed(Exception)";	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0084, all -> 0x00b9 }
                r5 = r6;
                if (r5 == 0) goto L_0x005d;
            L_0x00b3:
                r5 = r6;
                r5.onDone(r4);
                goto L_0x005d;
            L_0x00b9:
                r5 = move-exception;
                r8 = r6;
                if (r8 == 0) goto L_0x00c3;
            L_0x00be:
                r8 = r6;
                r8.onDone(r4);
            L_0x00c3:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.31.rundo():void");
            }
        });
        return true;
    }

    public boolean registerQuickly(String appId, OnRegisterQuicklyListener listener) {
        String prefix = "[appId:" + appId + "]";
        LOG.d(TAG, prefix + " register quickly ...");
        final String str = appId;
        final String str2 = prefix;
        final OnRegisterQuicklyListener onRegisterQuicklyListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                int rcode = 1;
                String accountId = null;
                String account = null;
                String password = null;
                String nickname = null;
                long start = System.currentTimeMillis();
                try {
                    HTTPAgent agent = BasicWsApi.this.requestBuilder.buildQuickRegister(str);
                    agent.execute();
                    long millis = System.currentTimeMillis() - start;
                    if (agent.ok()) {
                        accountId = agent.getString("accountid");
                        account = agent.getString("account");
                        password = agent.getString("pwd");
                        nickname = agent.getString("nickname");
                        rcode = 0;
                        LOG.i(BasicWsApi.TAG, str2 + "[millis:" + millis + "] register quickly ok(" + accountId + "," + account + "," + password + "," + nickname + ")");
                    } else {
                        rcode = agent.getRcode();
                        LOG.e(BasicWsApi.TAG, str2 + "[millis:" + millis + "] register quickly failed(" + rcode + ")");
                    }
                    if (onRegisterQuicklyListener != null) {
                        onRegisterQuicklyListener.onDone(rcode, accountId, account, password, nickname);
                    }
                } catch (Exception e) {
                    LOG.e(BasicWsApi.TAG, str2 + "[millis:" + (System.currentTimeMillis() - start) + "] register quickly failed(Exception)", e);
                    if (onRegisterQuicklyListener != null) {
                        onRegisterQuicklyListener.onDone(rcode, accountId, account, password, nickname);
                    }
                } catch (Throwable th) {
                    Throwable th2 = th;
                    if (onRegisterQuicklyListener != null) {
                        onRegisterQuicklyListener.onDone(rcode, accountId, account, password, nickname);
                    }
                }
            }
        });
        return true;
    }

    public boolean changePassword(String uid, String tkt, String oldPassword, String newPassword, String appId, OnCommonListener listener) {
        final String oldmd5 = EncryptUtils.getMD5String(oldPassword);
        final String newmd5 = EncryptUtils.getMD5String(newPassword);
        String prefix = "[uid:" + uid + "][oldpwd:" + oldmd5 + "][newpwd:" + newmd5 + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " change password ...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = appId;
        final String str4 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r14 = this;
                r10 = 1;
                r12 = java.lang.System.currentTimeMillis();
                r8 = 0;
                r0 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r0 = r0.requestBuilder;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r3;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r2 = r4;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r3 = r5;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r4 = r6;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r5 = r7;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r6 = r0.buildSetPwd(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r6.execute();	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r0 = r6.ok();	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                if (r0 != 0) goto L_0x0064;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
            L_0x0028:
                r10 = r6.getRcode();	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1.<init>();	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r2 = r8;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r2 = "] change password failed(";	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r2 = ")";	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
            L_0x005a:
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x005e:
                r0 = r9;
                r0.onDone(r10);
            L_0x0063:
                return;
            L_0x0064:
                r10 = 0;
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1.<init>();	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r2 = r8;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r2 = "] change password result(";	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r2 = ")";	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                com.coolcloud.uac.android.common.util.LOG.i(r0, r1);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                goto L_0x005a;
            L_0x0094:
                r7 = move-exception;
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1.<init>();	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r2 = r8;	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r2 = "] change password failed(Exception)";	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1, r7);	 Catch:{ Exception -> 0x0094, all -> 0x00c9 }
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x00c3:
                r0 = r9;
                r0.onDone(r10);
                goto L_0x0063;
            L_0x00c9:
                r0 = move-exception;
                r1 = r9;
                if (r1 == 0) goto L_0x00d3;
            L_0x00ce:
                r1 = r9;
                r1.onDone(r10);
            L_0x00d3:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.33.rundo():void");
            }
        });
        return true;
    }

    public boolean downLogo(String logoUrl, OnDownLogoListener listener) {
        String prefix = "[logoUrl:" + logoUrl + "]";
        LOG.d(TAG, prefix + " download logo ...");
        final String str = logoUrl;
        final String str2 = prefix;
        final OnDownLogoListener onDownLogoListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                long start = System.currentTimeMillis();
                try {
                    HTTPTransporter.createTransporter().download(str, new 1(this, start), null);
                } catch (Throwable e) {
                    LOG.e(BasicWsApi.TAG, str2 + "[millis:" + (System.currentTimeMillis() - start) + "] download logo failed(Throwable)", e);
                    if (onDownLogoListener != null) {
                        onDownLogoListener.onDone(1, null);
                    }
                }
            }
        });
        return true;
    }

    public boolean getUserLogo(String uid, String PhotoUrl, OnGetUserLogoListener listener) {
        String prefix = "[logoUrl:" + PhotoUrl + "][uid:" + uid + "]";
        LOG.d(TAG, prefix + " get User Logo ...");
        final String str = PhotoUrl;
        final String str2 = prefix;
        final OnGetUserLogoListener onGetUserLogoListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                long start = System.currentTimeMillis();
                File photoFile = new File(PhotoUtil.createCacheFilepath(str));
                if (photoFile.exists()) {
                    onGetUserLogoListener.onDone(0, photoFile.getAbsolutePath());
                } else {
                    BasicWsApi.this.downLogo(str, new 1(this, start, photoFile));
                }
            }
        });
        return true;
    }

    public boolean getTotalScore(String uid, String tkt, String appId, OnTotalScoreListener listener) {
        String prefix = "[uid:" + uid + "][tkt:" + tkt + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " get total score ...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = appId;
        final String str4 = prefix;
        final OnTotalScoreListener onTotalScoreListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r5 = 0;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r8 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = r8.requestBuilder;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r3;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = r4;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r11 = r5;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r0 = r8.buildGetTotalScore(r9, r10, r11);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r0.execute();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = r0.ok();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                if (r8 != 0) goto L_0x0061;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
            L_0x0025:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9.<init>();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = r6;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = "] get total score failed(";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = ")";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
            L_0x0057:
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x005b:
                r8 = r7;
                r8.onDone(r4, r5);
            L_0x0060:
                return;
            L_0x0061:
                r8 = "total_score";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r5 = r0.getInt(r8);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r4 = 0;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9.<init>();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = r6;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = "] get total score ok(";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r5);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = ")";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                com.coolcloud.uac.android.common.util.LOG.i(r8, r9);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                goto L_0x0057;
            L_0x0098:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9.<init>();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = r6;	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r10 = "] get total score failed(Exception)";	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9, r1);	 Catch:{ Exception -> 0x0098, all -> 0x00cd }
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x00c7:
                r8 = r7;
                r8.onDone(r4, r5);
                goto L_0x0060;
            L_0x00cd:
                r8 = move-exception;
                r9 = r7;
                if (r9 == 0) goto L_0x00d7;
            L_0x00d2:
                r9 = r7;
                r9.onDone(r4, r5);
            L_0x00d7:
                throw r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.36.rundo():void");
            }
        });
        return true;
    }

    public boolean getDetailScoreInfo(String uid, String tkt, String appId, OnDetailScoreInfoListener listener) {
        String prefix = "[uid:" + uid + "][tkt:" + tkt + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " get detail score info ...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = appId;
        final String str4 = prefix;
        final OnDetailScoreInfoListener onDetailScoreInfoListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r14 = this;
                r6 = 1;
                r7 = 0;
                r3 = 0;
                r2 = 0;
                r8 = java.lang.System.currentTimeMillis();
                r4 = 0;
                r10 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r10 = r10.requestBuilder;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r3;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = r4;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r13 = r5;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r0 = r10.buildGetDetailScoreInfo(r11, r12, r13);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r0.execute();	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r10 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r4 = r10 - r8;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r10 = r0.ok();	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                if (r10 != 0) goto L_0x0063;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
            L_0x0027:
                r6 = r0.getRcode();	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r10 = "BasicWsApi";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11.<init>();	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = r6;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = "[millis:";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r4);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = "] get detail score info failed(";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r6);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = ")";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.toString();	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                com.coolcloud.uac.android.common.util.LOG.e(r10, r11);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
            L_0x0059:
                r10 = r7;
                if (r10 == 0) goto L_0x0062;
            L_0x005d:
                r10 = r7;
                r10.onDone(r6, r7, r3, r2);
            L_0x0062:
                return;
            L_0x0063:
                r10 = "total_score";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r7 = r0.getInt(r10);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r10 = "exchanged_score";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r3 = r0.getInt(r10);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r10 = "exchangable_score";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r2 = r0.getInt(r10);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r6 = 0;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r10 = "BasicWsApi";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11.<init>();	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = r6;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = "[millis:";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r4);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = "] get detail score info ok(";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r7);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = ",";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r3);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = ",";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r2);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = ")";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.toString();	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                com.coolcloud.uac.android.common.util.LOG.i(r10, r11);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                goto L_0x0059;
            L_0x00ba:
                r1 = move-exception;
                r10 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r4 = r10 - r8;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r10 = "BasicWsApi";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11.<init>();	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = r6;	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = "[millis:";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r4);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r12 = "] get detail score info failed(Exception)";	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.append(r12);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r11 = r11.toString();	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                com.coolcloud.uac.android.common.util.LOG.e(r10, r11, r1);	 Catch:{ Exception -> 0x00ba, all -> 0x00f0 }
                r10 = r7;
                if (r10 == 0) goto L_0x0062;
            L_0x00e9:
                r10 = r7;
                r10.onDone(r6, r7, r3, r2);
                goto L_0x0062;
            L_0x00f0:
                r10 = move-exception;
                r11 = r7;
                if (r11 == 0) goto L_0x00fa;
            L_0x00f5:
                r11 = r7;
                r11.onDone(r6, r7, r3, r2);
            L_0x00fa:
                throw r10;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.37.rundo():void");
            }
        });
        return true;
    }

    public boolean getExchangeRate(String uid, String tkt, String appId, OnExchangeRateListener listener) {
        String prefix = "[uid:" + uid + "][tkt:" + tkt + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " get exchange rate ...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = appId;
        final String str4 = prefix;
        final OnExchangeRateListener onExchangeRateListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r5 = 1;
                r4 = 0;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r8 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r8 = r8.requestBuilder;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r3;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r10 = r4;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r11 = r5;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r0 = r8.buildGetDetailScoreInfo(r9, r10, r11);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r0.execute();	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r8 = r0.ok();	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                if (r8 != 0) goto L_0x0061;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
            L_0x0025:
                r5 = r0.getRcode();	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9.<init>();	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r10 = r6;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r10 = "] get exchange rate failed(";	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r5);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r10 = ")";	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
            L_0x0057:
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x005b:
                r8 = r7;
                r8.onDone(r5, r4);
            L_0x0060:
                return;
            L_0x0061:
                r8 = "exchange_rate";	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r4 = r0.getInt(r8);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r5 = 0;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9.<init>();	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r10 = r6;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r10 = "] get exchange rate ok(";	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r10 = ")";	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                com.coolcloud.uac.android.common.util.LOG.i(r8, r9);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                goto L_0x0057;
            L_0x0097:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9.<init>();	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r10 = r6;	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r10 = "] get exchange rate failed(Exception)";	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9, r1);	 Catch:{ Exception -> 0x0097, all -> 0x00cc }
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x00c6:
                r8 = r7;
                r8.onDone(r5, r4);
                goto L_0x0060;
            L_0x00cc:
                r8 = move-exception;
                r9 = r7;
                if (r9 == 0) goto L_0x00d6;
            L_0x00d1:
                r9 = r7;
                r9.onDone(r5, r4);
            L_0x00d6:
                throw r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.38.rundo():void");
            }
        });
        return true;
    }

    public boolean getExchangeRecords(String uid, String tkt, String appId, int pageOffset, int pageSize, int pageNum, OnBundlesListener listener) {
        String prefix = "[uid:" + uid + "][tkt:" + tkt + "][appId:" + appId + "][pageOffset:" + pageOffset + "][pageSize:" + pageSize + "][pageNum:" + pageNum + "]";
        LOG.d(TAG, prefix + " get exchange records ...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = appId;
        final int i = pageOffset;
        final int i2 = pageSize;
        final int i3 = pageNum;
        final String str4 = prefix;
        final OnBundlesListener onBundlesListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r18 = this;
                r13 = 1;
                r11 = 0;
                r16 = java.lang.System.currentTimeMillis();
                r14 = 0;
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r2 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r2 = r2.requestBuilder;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r4;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r4 = r5;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r5 = r6;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r6 = "";	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r7 = r7;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r8 = r8;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r9 = r9;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r10 = r2.buildGetExchangeRecords(r3, r4, r5, r6, r7, r8, r9);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r10.execute();	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r14 = r2 - r16;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r2 = r10.ok();	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                if (r2 != 0) goto L_0x007d;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
            L_0x003b:
                r13 = r10.getRcode();	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3.<init>();	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r4 = r10;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r14);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r4 = "] get exchange records failed(";	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r13);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r4 = ")";	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
            L_0x006f:
                r0 = r18;
                r2 = r11;
                if (r2 == 0) goto L_0x007c;
            L_0x0075:
                r0 = r18;
                r2 = r11;
                r2.onDone(r13, r11);
            L_0x007c:
                return;
            L_0x007d:
                r11 = r10.getBundles();	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r13 = 0;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3.<init>();	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r4 = r10;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r14);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r4 = "] get exchange records ok(";	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r4 = r11.size();	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r4 = ")";	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                com.coolcloud.uac.android.common.util.LOG.i(r2, r3);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                goto L_0x006f;
            L_0x00b7:
                r12 = move-exception;
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r14 = r2 - r16;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3.<init>();	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r4 = r10;	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r14);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r4 = "] get exchange records failed(Exception)";	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3, r12);	 Catch:{ Exception -> 0x00b7, all -> 0x00f2 }
                r0 = r18;
                r2 = r11;
                if (r2 == 0) goto L_0x007c;
            L_0x00ea:
                r0 = r18;
                r2 = r11;
                r2.onDone(r13, r11);
                goto L_0x007c;
            L_0x00f2:
                r2 = move-exception;
                r0 = r18;
                r3 = r11;
                if (r3 == 0) goto L_0x0100;
            L_0x00f9:
                r0 = r18;
                r3 = r11;
                r3.onDone(r13, r11);
            L_0x0100:
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.39.rundo():void");
            }
        });
        return true;
    }

    public boolean getConsumeRecords(String uid, String tkt, String appId, int pageOffset, int pageSize, int pageNum, OnBundlesListener listener) {
        String prefix = "[uid:" + uid + "][tkt:" + tkt + "][appId:" + appId + "][pageOffset:" + pageOffset + "][pageSize:" + pageSize + "][pageNum:" + pageNum + "]";
        LOG.d(TAG, prefix + " get consume records ...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = appId;
        final int i = pageOffset;
        final int i2 = pageSize;
        final int i3 = pageNum;
        final String str4 = prefix;
        final OnBundlesListener onBundlesListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r18 = this;
                r13 = 1;
                r11 = 0;
                r16 = java.lang.System.currentTimeMillis();
                r14 = 0;
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r2 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r2 = r2.requestBuilder;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r4;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r4 = r5;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r5 = r6;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r6 = "";	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r7 = r7;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r8 = r8;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r9 = r9;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r10 = r2.buildGetConsumeRecords(r3, r4, r5, r6, r7, r8, r9);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r10.execute();	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r14 = r2 - r16;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r2 = r10.ok();	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                if (r2 != 0) goto L_0x007d;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
            L_0x003b:
                r13 = r10.getRcode();	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3.<init>();	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r4 = r10;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r14);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r4 = "] get consume records failed(";	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r13);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r4 = ")";	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
            L_0x006f:
                r0 = r18;
                r2 = r11;
                if (r2 == 0) goto L_0x007c;
            L_0x0075:
                r0 = r18;
                r2 = r11;
                r2.onDone(r13, r11);
            L_0x007c:
                return;
            L_0x007d:
                r11 = r10.getBundles();	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r13 = 0;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3.<init>();	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r4 = r10;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r14);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r4 = "] get consume records ok(";	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r4 = r11.size();	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r4 = ")";	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                com.coolcloud.uac.android.common.util.LOG.i(r2, r3);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                goto L_0x006f;
            L_0x00b7:
                r12 = move-exception;
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3.<init>();	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r0 = r18;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r4 = r10;	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r14);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r4 = "] get consume records failed(Exception)";	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3, r12);	 Catch:{ Exception -> 0x00b7, all -> 0x00ec }
                r0 = r18;
                r2 = r11;
                if (r2 == 0) goto L_0x007c;
            L_0x00e4:
                r0 = r18;
                r2 = r11;
                r2.onDone(r13, r11);
                goto L_0x007c;
            L_0x00ec:
                r2 = move-exception;
                r0 = r18;
                r3 = r11;
                if (r3 == 0) goto L_0x00fa;
            L_0x00f3:
                r0 = r18;
                r3 = r11;
                r3.onDone(r13, r11);
            L_0x00fa:
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.40.rundo():void");
            }
        });
        return true;
    }

    public boolean thirdOauth(String inputaccount, String appid, String tappname, String tappid, String ttoken, OnThirdLoginOauthListener listener) {
        String prefix = "[appid:" + appid + "][tappname:" + tappname + "][tappid:" + tappid + "][ttoken:" + ttoken + "]";
        LOG.d(TAG, prefix + " third Oauth...");
        final String str = appid;
        final String str2 = tappname;
        final String str3 = tappid;
        final String str4 = ttoken;
        final String str5 = prefix;
        final OnThirdLoginOauthListener onThirdLoginOauthListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                int rcode = 1;
                long start = System.currentTimeMillis();
                String ruid = null;
                String raccount = null;
                String rrtkt = null;
                String rtuid = null;
                String rnickname = null;
                try {
                    HTTPAgent agent = BasicWsApi.this.requestBuilder.buildThidOauth(str, str2, str3, str4);
                    agent.execute();
                    long millis = System.currentTimeMillis() - start;
                    rcode = agent.getRcode();
                    if (rcode == 7004 || rcode == 0) {
                        ruid = agent.getString("uid");
                        raccount = agent.getString("account");
                        rrtkt = agent.getString("rtkt");
                        rtuid = agent.getString("tuid");
                        rnickname = agent.getString("tnickname");
                        LOG.i(BasicWsApi.TAG, str5 + "[millis:" + millis + "] third Oauth code ok");
                    } else {
                        rcode = agent.getRcode();
                        LOG.e(BasicWsApi.TAG, str5 + "[millis:" + millis + "] third Oauth failed(" + rcode + ")");
                    }
                    if (onThirdLoginOauthListener != null) {
                        onThirdLoginOauthListener.onDone(rcode, ruid, raccount, rrtkt, rtuid, rnickname);
                    }
                } catch (Exception e) {
                    LOG.e(BasicWsApi.TAG, str5 + "[millis:" + (System.currentTimeMillis() - start) + "] third Login Qihoo failed(Exception)", e);
                    if (onThirdLoginOauthListener != null) {
                        onThirdLoginOauthListener.onDone(rcode, null, null, null, null, null);
                    }
                } catch (Throwable th) {
                    Throwable th2 = th;
                    if (onThirdLoginOauthListener != null) {
                        onThirdLoginOauthListener.onDone(rcode, null, null, null, null, null);
                    }
                }
            }
        });
        return true;
    }

    public boolean thirdLoginAndCreateTemp(String inputaccount, String appid, String tappname, String tappid, String tuid, String tnickname, String ttoken, OnThirdLoginAndCreateTempListener listener) {
        String prefix = "[appid:" + appid + "][tappname:" + tappname + "][tappid:" + tappid + "][tuid:" + tuid + "][tnickname:" + tnickname + "][ttoken:" + ttoken + "]";
        LOG.d(TAG, prefix + " third Login And Create Temp Account ...");
        final String str = appid;
        final String str2 = tappname;
        final String str3 = tappid;
        final String str4 = tuid;
        final String str5 = tnickname;
        final String str6 = ttoken;
        final String str7 = prefix;
        final OnThirdLoginAndCreateTempListener onThirdLoginAndCreateTempListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r20 = this;
                r14 = 1;
                r18 = java.lang.System.currentTimeMillis();
                r12 = 0;
                r16 = 0;
                r11 = 0;
                r15 = 0;
                r0 = r20;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = r2.requestBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r20;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r4;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r20;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = r5;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r20;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r5 = r6;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r20;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r6 = r7;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r20;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r7 = r8;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r20;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r8 = r9;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r9 = r2.buildThirdLoginAndCreateTemp(r3, r4, r5, r6, r7, r8);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r9.execute();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r12 = r2 - r18;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = r9.ok();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                if (r2 != 0) goto L_0x0080;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
            L_0x003c:
                r14 = r9.getRcode();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3.<init>();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r20;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = r10;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r12);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "] third Login And Create Temp Account failed(";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = ")";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
            L_0x0070:
                r0 = r20;
                r2 = r11;
                if (r2 == 0) goto L_0x007f;
            L_0x0076:
                r0 = r20;
                r2 = r11;
                r0 = r16;
                r2.onDone(r14, r0, r11, r15);
            L_0x007f:
                return;
            L_0x0080:
                r14 = 0;
                r2 = "uid";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r16 = r9.getString(r2);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "account";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r11 = r9.getString(r2);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "rtkt";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r15 = r9.getString(r2);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3.<init>();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r20;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = r10;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r12);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "] third Login And Create Temp Account code ok";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                com.coolcloud.uac.android.common.util.LOG.i(r2, r3);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                goto L_0x0070;
            L_0x00bc:
                r10 = move-exception;
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r12 = r2 - r18;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3.<init>();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r20;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = r10;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r12);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "] third Login And Create Temp Account failed(Exception)";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3, r10);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r20;
                r2 = r11;
                if (r2 == 0) goto L_0x007f;
            L_0x00ef:
                r0 = r20;
                r2 = r11;
                r0 = r16;
                r2.onDone(r14, r0, r11, r15);
                goto L_0x007f;
            L_0x00f9:
                r2 = move-exception;
                r0 = r20;
                r3 = r11;
                if (r3 == 0) goto L_0x0109;
            L_0x0100:
                r0 = r20;
                r3 = r11;
                r0 = r16;
                r3.onDone(r14, r0, r11, r15);
            L_0x0109:
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.42.rundo():void");
            }
        });
        return true;
    }

    public boolean thirdLoginAndBind(String inputaccount, String appid, String account, String password, String tappname, String tappid, String tuid, String tnickname, String ttoken, OnThirdLoginAndBindListener listener) {
        final String md5 = EncryptUtils.getMD5String(password);
        String prefix = "[appid:" + appid + "][account:" + account + "][password:" + "..." + "][tappname:" + tappname + "][tappid:" + tappid + "][tuid:" + tuid + "][tnickname:" + tnickname + "][ttoken:" + ttoken + "]";
        LOG.d(TAG, prefix + " third Login And Bind  ...");
        final String str = appid;
        final String str2 = account;
        final String str3 = tappname;
        final String str4 = tappid;
        final String str5 = tuid;
        final String str6 = tnickname;
        final String str7 = ttoken;
        final String str8 = prefix;
        final OnThirdLoginAndBindListener onThirdLoginAndBindListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r20 = this;
                r13 = 1;
                r18 = java.lang.System.currentTimeMillis();
                r14 = 0;
                r17 = 0;
                r16 = 0;
                r0 = r20;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r2 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r2 = r2.requestBuilder;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r0 = r20;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r4;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r0 = r20;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r4 = r5;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r0 = r20;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r5 = r6;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r0 = r20;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r6 = r7;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r0 = r20;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r7 = r8;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r0 = r20;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r8 = r9;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r0 = r20;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r9 = r10;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r0 = r20;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r10 = r11;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r11 = r2.buildThidLoginAndBind(r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r11.execute();	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r14 = r2 - r18;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r2 = r11.ok();	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                if (r2 != 0) goto L_0x008a;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
            L_0x0044:
                r13 = r11.getRcode();	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3.<init>();	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r0 = r20;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r4 = r12;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r14);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r4 = "] third Login And Bind failed(";	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r13);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r4 = ")";	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
            L_0x0078:
                r0 = r20;
                r2 = r13;
                if (r2 == 0) goto L_0x0089;
            L_0x007e:
                r0 = r20;
                r2 = r13;
                r0 = r17;
                r1 = r16;
                r2.onDone(r13, r0, r1);
            L_0x0089:
                return;
            L_0x008a:
                r13 = 0;
                r2 = "uid";	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r17 = r11.getString(r2);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r2 = "rtkt";	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r16 = r11.getString(r2);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3.<init>();	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r0 = r20;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r4 = r12;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r14);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r4 = "]third Login And Bind code ok";	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                com.coolcloud.uac.android.common.util.LOG.i(r2, r3);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                goto L_0x0078;
            L_0x00c0:
                r12 = move-exception;
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r14 = r2 - r18;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3.<init>();	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r0 = r20;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r4 = r12;	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r14);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r4 = "] third Login And Bind failed(Exception)";	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3, r12);	 Catch:{ Exception -> 0x00c0, all -> 0x00ff }
                r0 = r20;
                r2 = r13;
                if (r2 == 0) goto L_0x0089;
            L_0x00f3:
                r0 = r20;
                r2 = r13;
                r0 = r17;
                r1 = r16;
                r2.onDone(r13, r0, r1);
                goto L_0x0089;
            L_0x00ff:
                r2 = move-exception;
                r0 = r20;
                r3 = r13;
                if (r3 == 0) goto L_0x0111;
            L_0x0106:
                r0 = r20;
                r3 = r13;
                r0 = r17;
                r1 = r16;
                r3.onDone(r13, r0, r1);
            L_0x0111:
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.43.rundo():void");
            }
        });
        return true;
    }

    public boolean thirdBind(String appid, String uid, String tkt, String tappname, String tappid, String ttoken, OnThirdBindListener listener) {
        String prefix = "[appid:" + appid + "][uid:" + uid + "][tkt:" + tkt + "][tappname:" + tappname + "][tappid:" + tappid + "][ttoken:" + ttoken + "]";
        LOG.d(TAG, prefix + " third Bind  ...");
        final String str = appid;
        final String str2 = uid;
        final String str3 = tkt;
        final String str4 = tappname;
        final String str5 = tappid;
        final String str6 = ttoken;
        final String str7 = prefix;
        final OnThirdBindListener onThirdBindListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r19 = this;
                r14 = 1;
                r16 = java.lang.System.currentTimeMillis();
                r12 = 0;
                r18 = 0;
                r15 = 0;
                r11 = 0;
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = r2.requestBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r4;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = r5;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r5 = r6;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r6 = r7;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r7 = r8;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r8 = r9;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r9 = r2.buildThidBind(r3, r4, r5, r6, r7, r8);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r9.execute();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r12 = r2 - r16;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = r9.ok();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                if (r2 != 0) goto L_0x0080;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
            L_0x003c:
                r14 = r9.getRcode();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3.<init>();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = r10;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r12);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "] third Bind failed(";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = ")";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
            L_0x0070:
                r0 = r19;
                r2 = r11;
                if (r2 == 0) goto L_0x007f;
            L_0x0076:
                r0 = r19;
                r2 = r11;
                r0 = r18;
                r2.onDone(r14, r0, r15, r11);
            L_0x007f:
                return;
            L_0x0080:
                r14 = 0;
                r2 = "tuid";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r18 = r9.getString(r2);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "tnickname";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r15 = r9.getString(r2);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "istmp";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r11 = r9.getString(r2);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3.<init>();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = r10;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r12);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "]third Bind code ok";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                com.coolcloud.uac.android.common.util.LOG.i(r2, r3);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                goto L_0x0070;
            L_0x00bc:
                r10 = move-exception;
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r12 = r2 - r16;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3.<init>();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = r10;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r12);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "] third Bind failed(Exception)";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3, r10);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;
                r2 = r11;
                if (r2 == 0) goto L_0x007f;
            L_0x00ef:
                r0 = r19;
                r2 = r11;
                r0 = r18;
                r2.onDone(r14, r0, r15, r11);
                goto L_0x007f;
            L_0x00f9:
                r2 = move-exception;
                r0 = r19;
                r3 = r11;
                if (r3 == 0) goto L_0x0109;
            L_0x0100:
                r0 = r19;
                r3 = r11;
                r0 = r18;
                r3.onDone(r14, r0, r15, r11);
            L_0x0109:
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.44.rundo():void");
            }
        });
        return true;
    }

    public boolean getBindInfo(String appid, String uid, String tkt, OnGetBindInfoListener listener) {
        String prefix = "[appid:" + appid + "][uid:" + uid + "][tkt:" + tkt + "]";
        LOG.d(TAG, prefix + " get Bind Info  ...");
        final String str = appid;
        final String str2 = uid;
        final String str3 = tkt;
        final String str4 = prefix;
        final OnGetBindInfoListener onGetBindInfoListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r3 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r4 = 0;
                r2 = 0;
                r8 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r8 = r8.requestBuilder;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r3;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r10 = r4;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r11 = r5;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r0 = r8.buildGetBindInfo(r9, r10, r11);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r0.execute();	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r4 = r8 - r6;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r8 = r0.ok();	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                if (r8 != 0) goto L_0x0061;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
            L_0x0025:
                r3 = r0.getRcode();	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9.<init>();	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r10 = r6;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r10 = "] get Bind Info(";	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r3);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r10 = ")";	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
            L_0x0057:
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x005b:
                r8 = r7;
                r8.onDone(r3, r2);
            L_0x0060:
                return;
            L_0x0061:
                r3 = r0.getRcode();	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r2 = r0.getBundles();	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9.<init>();	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r10 = r6;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r10 = "]get Bind Info code ok";	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                com.coolcloud.uac.android.common.util.LOG.i(r8, r9);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                goto L_0x0057;
            L_0x008e:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r4 = r8 - r6;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9.<init>();	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r10 = r6;	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r10 = "] get Bind Info failed(Exception)";	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9, r1);	 Catch:{ Exception -> 0x008e, all -> 0x00c3 }
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x00bd:
                r8 = r7;
                r8.onDone(r3, r2);
                goto L_0x0060;
            L_0x00c3:
                r8 = move-exception;
                r9 = r7;
                if (r9 == 0) goto L_0x00cd;
            L_0x00c8:
                r9 = r7;
                r9.onDone(r3, r2);
            L_0x00cd:
                throw r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.45.rundo():void");
            }
        });
        return true;
    }

    public boolean LoginByQihoo(String qihooAccount, String qihooPassword, String appid, String tappname, String tappid, String tuseinfo, String isSkip, OnQihooLoginListener listener) {
        String prefix = "[appid:" + appid + "][tappname:" + tappname + "][tappid:" + tappid + "][tuseinfo:" + tuseinfo + "]";
        LOG.d(TAG, prefix + " Login By Qihoo  ...");
        final String str = appid;
        final String str2 = tappname;
        final String str3 = tappid;
        final String str4 = tuseinfo;
        final String str5 = isSkip;
        final String str6 = prefix;
        final OnQihooLoginListener onQihooLoginListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r18 = this;
                r13 = 1;
                r16 = java.lang.System.currentTimeMillis();
                r10 = 0;
                r15 = 0;
                r12 = 0;
                r14 = 0;
                r0 = r18;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r2 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r2 = r2.requestBuilder;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r0 = r18;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r4;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r0 = r18;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r4 = r5;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r0 = r18;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r5 = r6;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r0 = r18;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r6 = r7;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r0 = r18;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r7 = r8;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r8 = r2.buildQihooLogin(r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r8.execute();	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r10 = r2 - r16;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r2 = r8.ok();	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                if (r2 != 0) goto L_0x0079;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
            L_0x0037:
                r13 = r8.getRcode();	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3.<init>();	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r0 = r18;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r4 = r9;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r10);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r4 = "] Login By Qihoo failed(";	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r13);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r4 = ")";	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
            L_0x006b:
                r0 = r18;
                r2 = r10;
                if (r2 == 0) goto L_0x0078;
            L_0x0071:
                r0 = r18;
                r2 = r10;
                r2.onDone(r13, r15, r12, r14);
            L_0x0078:
                return;
            L_0x0079:
                r13 = 0;
                r2 = "uid";	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r15 = r8.getString(r2);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r2 = "account";	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r12 = r8.getString(r2);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r2 = "rtkt";	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r14 = r8.getString(r2);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3.<init>();	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r0 = r18;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r4 = r9;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r10);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r4 = "] Login By Qihoo code ok";	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                com.coolcloud.uac.android.common.util.LOG.i(r2, r3);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                goto L_0x006b;
            L_0x00b5:
                r9 = move-exception;
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r10 = r2 - r16;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3.<init>();	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r0 = r18;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r4 = r9;	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r10);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r4 = "] Login By Qihoo failed(Exception)";	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3, r9);	 Catch:{ Exception -> 0x00b5, all -> 0x00f0 }
                r0 = r18;
                r2 = r10;
                if (r2 == 0) goto L_0x0078;
            L_0x00e8:
                r0 = r18;
                r2 = r10;
                r2.onDone(r13, r15, r12, r14);
                goto L_0x0078;
            L_0x00f0:
                r2 = move-exception;
                r0 = r18;
                r3 = r10;
                if (r3 == 0) goto L_0x00fe;
            L_0x00f7:
                r0 = r18;
                r3 = r10;
                r3.onDone(r13, r15, r12, r14);
            L_0x00fe:
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.46.rundo():void");
            }
        });
        return true;
    }

    public boolean bindQihoo(String appid, String uid, String tkt, String tappname, String tappid, String tuserinfo, OnThirdBindListener listener) {
        String prefix = "[appid:" + appid + "][uid:" + uid + "][tkt:" + tkt + "][tappname:" + tappname + "][tappid:" + tappid + "][tuserinfo:" + tuserinfo + "]";
        LOG.d(TAG, prefix + " bind Qihoo  ...");
        final String str = appid;
        final String str2 = uid;
        final String str3 = tkt;
        final String str4 = tappname;
        final String str5 = tappid;
        final String str6 = tuserinfo;
        final String str7 = prefix;
        final OnThirdBindListener onThirdBindListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r19 = this;
                r14 = 1;
                r16 = java.lang.System.currentTimeMillis();
                r12 = 0;
                r18 = 0;
                r15 = 0;
                r11 = 0;
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = r2.requestBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r4;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = r5;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r5 = r6;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r6 = r7;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r7 = r8;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r8 = r9;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r9 = r2.buildQihooBind(r3, r4, r5, r6, r7, r8);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r9.execute();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r12 = r2 - r16;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = r9.ok();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                if (r2 != 0) goto L_0x0080;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
            L_0x003c:
                r14 = r9.getRcode();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3.<init>();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = r10;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r12);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "] bind Qihoo failed(";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r14);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = ")";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
            L_0x0070:
                r0 = r19;
                r2 = r11;
                if (r2 == 0) goto L_0x007f;
            L_0x0076:
                r0 = r19;
                r2 = r11;
                r0 = r18;
                r2.onDone(r14, r0, r15, r11);
            L_0x007f:
                return;
            L_0x0080:
                r14 = 0;
                r2 = "tuid";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r18 = r9.getString(r2);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "tnickname";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r15 = r9.getString(r2);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "istmp";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r11 = r9.getString(r2);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3.<init>();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = r10;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r12);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "]bind Qihoo code ok";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                com.coolcloud.uac.android.common.util.LOG.i(r2, r3);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                goto L_0x0070;
            L_0x00bc:
                r10 = move-exception;
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r12 = r2 - r16;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3.<init>();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = r10;	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r12);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r4 = "] bind Qihoo failed(Exception)";	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3, r10);	 Catch:{ Exception -> 0x00bc, all -> 0x00f9 }
                r0 = r19;
                r2 = r11;
                if (r2 == 0) goto L_0x007f;
            L_0x00ef:
                r0 = r19;
                r2 = r11;
                r0 = r18;
                r2.onDone(r14, r0, r15, r11);
                goto L_0x007f;
            L_0x00f9:
                r2 = move-exception;
                r0 = r19;
                r3 = r11;
                if (r3 == 0) goto L_0x0109;
            L_0x0100:
                r0 = r19;
                r3 = r11;
                r0 = r18;
                r3.onDone(r14, r0, r15, r11);
            L_0x0109:
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.47.rundo():void");
            }
        });
        return true;
    }

    public boolean getFreeCallInfo(String appid, String uid, String tkt, String type, String phone, String loginSource, OnGetFreeCallListener listener) {
        String prefix = "[appid:" + appid + "][uid:" + uid + "][tkt:" + tkt + "][phone:" + phone + "][loginSource:" + loginSource + "][type:" + type + "]";
        LOG.d(TAG, prefix + " get Free Call Info...");
        final String str = appid;
        final String str2 = uid;
        final String str3 = tkt;
        final String str4 = type;
        final String str5 = phone;
        final String str6 = loginSource;
        final String str7 = prefix;
        final OnGetFreeCallListener onGetFreeCallListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                int rcode;
                String tuid;
                String tphone;
                String tjid;
                Exception e;
                String tpwd;
                Throwable th;
                long start = System.currentTimeMillis();
                try {
                    HTTPAgent agent = BasicWsApi.this.requestBuilder.buildGetFreeCall(str, str2, str3, str4, str5, str6);
                    agent.execute();
                    long millis = System.currentTimeMillis() - start;
                    if (agent.ok()) {
                        rcode = 0;
                        tuid = agent.getString("uid");
                        try {
                            tphone = agent.getString("phone");
                            try {
                                tjid = agent.getString("jid");
                            } catch (Exception e2) {
                                e = e2;
                                tpwd = null;
                                tjid = null;
                                LOG.e(BasicWsApi.TAG, str7 + "[millis:" + (System.currentTimeMillis() - start) + "] bind Qihoo failed(Exception)", e);
                                if (onGetFreeCallListener != null) {
                                    onGetFreeCallListener.onDone(rcode, tuid, tphone, tjid, tpwd);
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                tpwd = null;
                                tjid = null;
                                if (onGetFreeCallListener != null) {
                                    onGetFreeCallListener.onDone(rcode, tuid, tphone, tjid, tpwd);
                                }
                                throw th;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            tpwd = null;
                            tjid = null;
                            tphone = null;
                            LOG.e(BasicWsApi.TAG, str7 + "[millis:" + (System.currentTimeMillis() - start) + "] bind Qihoo failed(Exception)", e);
                            if (onGetFreeCallListener != null) {
                                onGetFreeCallListener.onDone(rcode, tuid, tphone, tjid, tpwd);
                            }
                        } catch (Throwable th22) {
                            th = th22;
                            tpwd = null;
                            tjid = null;
                            tphone = null;
                            if (onGetFreeCallListener != null) {
                                onGetFreeCallListener.onDone(rcode, tuid, tphone, tjid, tpwd);
                            }
                            throw th;
                        }
                        try {
                            tpwd = agent.getString("pwd");
                            try {
                                LOG.i(BasicWsApi.TAG, str7 + "[millis:" + millis + "]get Free Call Info ok");
                            } catch (Exception e4) {
                                e = e4;
                                LOG.e(BasicWsApi.TAG, str7 + "[millis:" + (System.currentTimeMillis() - start) + "] bind Qihoo failed(Exception)", e);
                                if (onGetFreeCallListener != null) {
                                    onGetFreeCallListener.onDone(rcode, tuid, tphone, tjid, tpwd);
                                }
                            }
                        } catch (Exception e5) {
                            e = e5;
                            tpwd = null;
                            LOG.e(BasicWsApi.TAG, str7 + "[millis:" + (System.currentTimeMillis() - start) + "] bind Qihoo failed(Exception)", e);
                            if (onGetFreeCallListener != null) {
                                onGetFreeCallListener.onDone(rcode, tuid, tphone, tjid, tpwd);
                            }
                        } catch (Throwable th222) {
                            th = th222;
                            tpwd = null;
                            if (onGetFreeCallListener != null) {
                                onGetFreeCallListener.onDone(rcode, tuid, tphone, tjid, tpwd);
                            }
                            throw th;
                        }
                    }
                    rcode = agent.getRcode();
                    try {
                        LOG.e(BasicWsApi.TAG, str7 + "[millis:" + millis + "] get Free Call Info(" + rcode + ")");
                        tpwd = null;
                        tjid = null;
                        tphone = null;
                        tuid = null;
                    } catch (Exception e6) {
                        e = e6;
                        tpwd = null;
                        tjid = null;
                        tphone = null;
                        tuid = null;
                        try {
                            LOG.e(BasicWsApi.TAG, str7 + "[millis:" + (System.currentTimeMillis() - start) + "] bind Qihoo failed(Exception)", e);
                            if (onGetFreeCallListener != null) {
                                onGetFreeCallListener.onDone(rcode, tuid, tphone, tjid, tpwd);
                            }
                        } catch (Throwable th2222) {
                            th = th2222;
                            if (onGetFreeCallListener != null) {
                                onGetFreeCallListener.onDone(rcode, tuid, tphone, tjid, tpwd);
                            }
                            throw th;
                        }
                    } catch (Throwable th22222) {
                        th = th22222;
                        tpwd = null;
                        tjid = null;
                        tphone = null;
                        tuid = null;
                        if (onGetFreeCallListener != null) {
                            onGetFreeCallListener.onDone(rcode, tuid, tphone, tjid, tpwd);
                        }
                        throw th;
                    }
                    if (onGetFreeCallListener != null) {
                        onGetFreeCallListener.onDone(rcode, tuid, tphone, tjid, tpwd);
                    }
                } catch (Exception e7) {
                    e = e7;
                    tpwd = null;
                    tjid = null;
                    tphone = null;
                    tuid = null;
                    rcode = 1;
                    LOG.e(BasicWsApi.TAG, str7 + "[millis:" + (System.currentTimeMillis() - start) + "] bind Qihoo failed(Exception)", e);
                    if (onGetFreeCallListener != null) {
                        onGetFreeCallListener.onDone(rcode, tuid, tphone, tjid, tpwd);
                    }
                } catch (Throwable th222222) {
                    th = th222222;
                    tpwd = null;
                    tjid = null;
                    tphone = null;
                    tuid = null;
                    rcode = 1;
                    if (onGetFreeCallListener != null) {
                        onGetFreeCallListener.onDone(rcode, tuid, tphone, tjid, tpwd);
                    }
                    throw th;
                }
            }
        });
        return true;
    }

    public boolean loginAndBindQihoo(String appid, String account, String pwd, String tappname, String tappid, String tuserinfo, OnThirdLoginAndBindListener listener) {
        final String md5 = EncryptUtils.getMD5String(pwd);
        String prefix = "[appid:" + appid + "][account:" + account + "][password:" + "..." + "][tappname:" + tappname + "][tuserinfo:" + tuserinfo + "][tappid:" + tappid + "]";
        LOG.d(TAG, prefix + " third Login And Bind  ...");
        final String str = appid;
        final String str2 = account;
        final String str3 = tappname;
        final String str4 = tappid;
        final String str5 = tuserinfo;
        final String str6 = prefix;
        final OnThirdLoginAndBindListener onThirdLoginAndBindListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r18 = this;
                r11 = 1;
                r16 = java.lang.System.currentTimeMillis();
                r12 = 0;
                r15 = 0;
                r14 = 0;
                r0 = r18;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r2 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r2 = r2.requestBuilder;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r0 = r18;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r4;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r0 = r18;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r4 = r5;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r0 = r18;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r5 = r6;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r0 = r18;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r6 = r7;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r0 = r18;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r7 = r8;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r0 = r18;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r8 = r9;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r9 = r2.buildloginAndBindQihoo(r3, r4, r5, r6, r7, r8);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r9.execute();	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r12 = r2 - r16;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r2 = r9.ok();	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                if (r2 != 0) goto L_0x007c;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
            L_0x003a:
                r11 = r9.getRcode();	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3.<init>();	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r0 = r18;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r4 = r10;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r12);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r4 = "] third Login And Bind failed(";	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r11);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r4 = ")";	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
            L_0x006e:
                r0 = r18;
                r2 = r11;
                if (r2 == 0) goto L_0x007b;
            L_0x0074:
                r0 = r18;
                r2 = r11;
                r2.onDone(r11, r15, r14);
            L_0x007b:
                return;
            L_0x007c:
                r11 = 0;
                r2 = "uid";	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r15 = r9.getString(r2);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r2 = "rtkt";	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r14 = r9.getString(r2);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3.<init>();	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r0 = r18;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r4 = r10;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r12);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r4 = "]third Login And Bind code ok";	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                com.coolcloud.uac.android.common.util.LOG.i(r2, r3);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                goto L_0x006e;
            L_0x00b2:
                r10 = move-exception;
                r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r12 = r2 - r16;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r2 = "BasicWsApi";	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3.<init>();	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r0 = r18;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r4 = r10;	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r4 = "[millis:";	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r12);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r4 = "] third Login And Bind failed(Exception)";	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r3 = r3.toString();	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                com.coolcloud.uac.android.common.util.LOG.e(r2, r3, r10);	 Catch:{ Exception -> 0x00b2, all -> 0x00ed }
                r0 = r18;
                r2 = r11;
                if (r2 == 0) goto L_0x007b;
            L_0x00e5:
                r0 = r18;
                r2 = r11;
                r2.onDone(r11, r15, r14);
                goto L_0x007b;
            L_0x00ed:
                r2 = move-exception;
                r0 = r18;
                r3 = r11;
                if (r3 == 0) goto L_0x00fb;
            L_0x00f4:
                r0 = r18;
                r3 = r11;
                r3.onDone(r11, r15, r14);
            L_0x00fb:
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.49.rundo():void");
            }
        });
        return true;
    }

    public boolean getBindDevice(String appid, String uid, String tkt, String version, OnGetBindDeviceListener listener) {
        String prefix = "[appid:" + appid + "][uid:" + uid + "][tkt:" + tkt + "][version:" + version + "]";
        LOG.d(TAG, prefix + " get Bind Device  ...");
        final String str = appid;
        final String str2 = uid;
        final String str3 = tkt;
        final String str4 = version;
        final String str5 = prefix;
        final OnGetBindDeviceListener onGetBindDeviceListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r13 = this;
                r5 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r4 = 0;
                r8 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r8 = r8.requestBuilder;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r3;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r10 = r4;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r11 = r5;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r12 = r6;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r0 = r8.buildGetBindDevice(r9, r10, r11, r12);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r0.execute();	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r8 = r0.ok();	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                if (r8 != 0) goto L_0x0063;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
            L_0x0027:
                r5 = r0.getRcode();	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9.<init>();	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r10 = r7;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r10 = "] get Bind Device(";	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r5);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r10 = ")";	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
            L_0x0059:
                r8 = r8;
                if (r8 == 0) goto L_0x0062;
            L_0x005d:
                r8 = r8;
                r8.onDone(r5, r4);
            L_0x0062:
                return;
            L_0x0063:
                r5 = 0;
                r4 = r0.getBundles();	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9.<init>();	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r10 = r7;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r10 = "] get Bind Device code ok";	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                com.coolcloud.uac.android.common.util.LOG.i(r8, r9);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                goto L_0x0059;
            L_0x008d:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9.<init>();	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r10 = r7;	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r10 = "]  get Bind Device failed(Exception)";	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9, r1);	 Catch:{ Exception -> 0x008d, all -> 0x00c2 }
                r8 = r8;
                if (r8 == 0) goto L_0x0062;
            L_0x00bc:
                r8 = r8;
                r8.onDone(r5, r4);
                goto L_0x0062;
            L_0x00c2:
                r8 = move-exception;
                r9 = r8;
                if (r9 == 0) goto L_0x00cc;
            L_0x00c7:
                r9 = r8;
                r9.onDone(r5, r4);
            L_0x00cc:
                throw r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.50.rundo():void");
            }
        });
        return true;
    }

    public boolean unbindDevice(String appid, String uid, String tkt, String devlist, String version, OnUnBindDeviceListener listener) {
        String prefix = "[appid:" + appid + "][uid:" + uid + "][tkt:" + tkt + "][devlist:" + devlist + "][version:" + version + "]";
        LOG.d(TAG, prefix + " unbind Device  ...");
        final String str = appid;
        final String str2 = uid;
        final String str3 = tkt;
        final String str4 = devlist;
        final String str5 = version;
        final String str6 = prefix;
        final OnUnBindDeviceListener onUnBindDeviceListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r14 = this;
                r10 = 1;
                r12 = java.lang.System.currentTimeMillis();
                r8 = 0;
                r0 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r0.requestBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r4;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r3 = r5;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r4 = r6;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r5 = r7;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r6 = r0.buildUnBindDevice(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r6.execute();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r6.ok();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                if (r0 != 0) goto L_0x0064;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
            L_0x0028:
                r10 = r6.getRcode();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] unbind Device(";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = ")";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
            L_0x005a:
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x005e:
                r0 = r9;
                r0.onDone(r10);
            L_0x0063:
                return;
            L_0x0064:
                r10 = 0;
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] unbind Device code ok";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.i(r0, r1);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                goto L_0x005a;
            L_0x008a:
                r7 = move-exception;
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "]  unbind Device failed(Exception)";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1, r7);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x00b9:
                r0 = r9;
                r0.onDone(r10);
                goto L_0x0063;
            L_0x00bf:
                r0 = move-exception;
                r1 = r9;
                if (r1 == 0) goto L_0x00c9;
            L_0x00c4:
                r1 = r9;
                r1.onDone(r10);
            L_0x00c9:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.51.rundo():void");
            }
        });
        return true;
    }

    public boolean getLoginApp(String appid, String uid, String tkt, OnGetLoginAppListener listener) {
        String prefix = "[appid:" + appid + "][uid:" + uid + "][tkt:" + tkt + "]";
        LOG.d(TAG, prefix + " get Login App  ...");
        final String str = appid;
        final String str2 = uid;
        final String str3 = tkt;
        final String str4 = prefix;
        final OnGetLoginAppListener onGetLoginAppListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r5 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r4 = 0;
                r8 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r8 = r8.requestBuilder;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r3;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r10 = r4;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r11 = r5;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r0 = r8.buildGetLoginApp(r9, r10, r11);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r0.execute();	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r8 = r0.ok();	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                if (r8 != 0) goto L_0x0061;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
            L_0x0025:
                r5 = r0.getRcode();	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9.<init>();	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r10 = r6;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r10 = "] get Login App(";	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r5);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r10 = ")";	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
            L_0x0057:
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x005b:
                r8 = r7;
                r8.onDone(r5, r4);
            L_0x0060:
                return;
            L_0x0061:
                r5 = 0;
                r4 = r0.getBundles();	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9.<init>();	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r10 = r6;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r10 = "] get Login App code ok";	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                com.coolcloud.uac.android.common.util.LOG.i(r8, r9);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                goto L_0x0057;
            L_0x008b:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9.<init>();	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r10 = r6;	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r10 = "[millis:";	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r2);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r10 = "]  get Login App failed(Exception)";	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r9 = r9.toString();	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9, r1);	 Catch:{ Exception -> 0x008b, all -> 0x00c0 }
                r8 = r7;
                if (r8 == 0) goto L_0x0060;
            L_0x00ba:
                r8 = r7;
                r8.onDone(r5, r4);
                goto L_0x0060;
            L_0x00c0:
                r8 = move-exception;
                r9 = r7;
                if (r9 == 0) goto L_0x00ca;
            L_0x00c5:
                r9 = r7;
                r9.onDone(r5, r4);
            L_0x00ca:
                throw r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.52.rundo():void");
            }
        });
        return true;
    }

    public boolean changNickname(String appid, String uid, String tkt, String nickname, OnCommonListener listener) {
        String prefix = "[changNickname][uid:" + uid + "][tkt:" + tkt + "][appid:" + appid + "]";
        LOG.d(TAG, prefix + "] chang Nickname ...");
        final String str = appid;
        final String str2 = uid;
        final String str3 = tkt;
        final String str4 = nickname;
        final String str5 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r3;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r4;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r10 = r5;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r11 = r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0 = r5.buildChangeNickname(r8, r9, r10, r11);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0.execute();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                if (r5 != 0) goto L_0x0062;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0026:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] chang Nickname failed(";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = ")";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0058:
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x005c:
                r5 = r8;
                r5.onDone(r4);
            L_0x0061:
                return;
            L_0x0062:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] chang Nickname ok";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                goto L_0x0058;
            L_0x0088:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] chang Nickname failed(Exception)";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x00b7:
                r5 = r8;
                r5.onDone(r4);
                goto L_0x0061;
            L_0x00bd:
                r5 = move-exception;
                r8 = r8;
                if (r8 == 0) goto L_0x00c7;
            L_0x00c2:
                r8 = r8;
                r8.onDone(r4);
            L_0x00c7:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.53.rundo():void");
            }
        });
        return true;
    }

    public boolean UnBindThird(String appid, String uid, String tkt, String tappname, String tappid, OnCommonListener listener) {
        String prefix = "[appid:" + appid + "][uid:" + uid + "][tkt:" + tkt + "][tappname:" + tappname + "][tappid:" + tappid + "]";
        LOG.d(TAG, prefix + " UnBind Third   ...");
        final String str = appid;
        final String str2 = uid;
        final String str3 = tkt;
        final String str4 = tappname;
        final String str5 = tappid;
        final String str6 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r14 = this;
                r10 = 1;
                r12 = java.lang.System.currentTimeMillis();
                r8 = 0;
                r0 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r0.requestBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r4;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r3 = r5;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r4 = r6;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r5 = r7;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r6 = r0.buildUnBindThird(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r6.execute();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r6.ok();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                if (r0 != 0) goto L_0x0064;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
            L_0x0028:
                r10 = r6.getRcode();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] UnBind Third failed(";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = ")";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
            L_0x005a:
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x005e:
                r0 = r9;
                r0.onDone(r10);
            L_0x0063:
                return;
            L_0x0064:
                r10 = 0;
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] UnBind Third code ok";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.i(r0, r1);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                goto L_0x005a;
            L_0x008a:
                r7 = move-exception;
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] UnBind Third failed(Exception)";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1, r7);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x00b9:
                r0 = r9;
                r0.onDone(r10);
                goto L_0x0063;
            L_0x00bf:
                r0 = move-exception;
                r1 = r9;
                if (r1 == 0) goto L_0x00c9;
            L_0x00c4:
                r1 = r9;
                r1.onDone(r10);
            L_0x00c9:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.54.rundo():void");
            }
        });
        return true;
    }

    public boolean getQihooToken(String appid, String uid, String tkt, String access_token, String tappname, OnGetQihooTokenListener listener) {
        String prefix = "[appid:" + appid + "][uid:" + uid + "][tkt:" + tkt + "][access_token:" + access_token + "][tappname:" + tappname + "]";
        LOG.d(TAG, prefix + " getQihooToken...");
        final String str = appid;
        final String str2 = uid;
        final String str3 = tkt;
        final String str4 = access_token;
        final String str5 = tappname;
        final String str6 = prefix;
        final OnGetQihooTokenListener onGetQihooTokenListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                int rcode;
                String rtnmsg;
                String taccesstoken;
                Exception e;
                String tcookie;
                String tuid;
                Throwable th;
                long start = System.currentTimeMillis();
                String rtnmsg2 = "";
                String taccesstoken2 = "";
                String tuid2 = "";
                String tcookie2 = "";
                String qcookie = "";
                try {
                    HTTPAgent agent = BasicWsApi.this.requestBuilder.buildGetQihooToken(str, str2, str3, str4, str5);
                    agent.execute();
                    long millis = System.currentTimeMillis() - start;
                    if (agent.ok()) {
                        rcode = 0;
                        rtnmsg = agent.getString("rtnmsg");
                        try {
                            taccesstoken = agent.getString("taccesstoken");
                        } catch (Exception e2) {
                            e = e2;
                            tcookie = tcookie2;
                            tuid = tuid2;
                            taccesstoken = taccesstoken2;
                            LOG.e(BasicWsApi.TAG, str6 + "[millis:" + (System.currentTimeMillis() - start) + "] getQihooToken failed(Exception)", e);
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            tcookie = tcookie2;
                            tuid = tuid2;
                            taccesstoken = taccesstoken2;
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                            throw th;
                        }
                        try {
                            tuid = agent.getString("tuid");
                            try {
                                tcookie = agent.getString("tcookie");
                            } catch (Exception e3) {
                                e = e3;
                                tcookie = tcookie2;
                                LOG.e(BasicWsApi.TAG, str6 + "[millis:" + (System.currentTimeMillis() - start) + "] getQihooToken failed(Exception)", e);
                                if (onGetQihooTokenListener != null) {
                                    onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                                }
                            } catch (Throwable th22) {
                                th = th22;
                                tcookie = tcookie2;
                                if (onGetQihooTokenListener != null) {
                                    onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                                }
                                throw th;
                            }
                        } catch (Exception e4) {
                            e = e4;
                            tcookie = tcookie2;
                            tuid = tuid2;
                            LOG.e(BasicWsApi.TAG, str6 + "[millis:" + (System.currentTimeMillis() - start) + "] getQihooToken failed(Exception)", e);
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                        } catch (Throwable th222) {
                            th = th222;
                            tcookie = tcookie2;
                            tuid = tuid2;
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                            throw th;
                        }
                        try {
                            qcookie = agent.getString("qcookie");
                            LOG.i(BasicWsApi.TAG, str6 + "[millis:" + millis + "] getQihooToken code ok");
                        } catch (Exception e5) {
                            e = e5;
                            LOG.e(BasicWsApi.TAG, str6 + "[millis:" + (System.currentTimeMillis() - start) + "] getQihooToken failed(Exception)", e);
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                        }
                    }
                    rcode = agent.getRcode();
                    try {
                        LOG.e(BasicWsApi.TAG, str6 + "[millis:" + millis + "] getQihooToken failed(" + rcode + ")");
                        tcookie = tcookie2;
                        tuid = tuid2;
                        taccesstoken = taccesstoken2;
                        rtnmsg = rtnmsg2;
                    } catch (Exception e6) {
                        e = e6;
                        tcookie = tcookie2;
                        tuid = tuid2;
                        taccesstoken = taccesstoken2;
                        rtnmsg = rtnmsg2;
                        try {
                            LOG.e(BasicWsApi.TAG, str6 + "[millis:" + (System.currentTimeMillis() - start) + "] getQihooToken failed(Exception)", e);
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                        } catch (Throwable th2222) {
                            th = th2222;
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                            throw th;
                        }
                    } catch (Throwable th22222) {
                        th = th22222;
                        tcookie = tcookie2;
                        tuid = tuid2;
                        taccesstoken = taccesstoken2;
                        rtnmsg = rtnmsg2;
                        if (onGetQihooTokenListener != null) {
                            onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                        }
                        throw th;
                    }
                    if (onGetQihooTokenListener != null) {
                        onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                    }
                } catch (Exception e7) {
                    e = e7;
                    tcookie = tcookie2;
                    tuid = tuid2;
                    taccesstoken = taccesstoken2;
                    rtnmsg = rtnmsg2;
                    rcode = 1;
                    LOG.e(BasicWsApi.TAG, str6 + "[millis:" + (System.currentTimeMillis() - start) + "] getQihooToken failed(Exception)", e);
                    if (onGetQihooTokenListener != null) {
                        onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                    }
                } catch (Throwable th222222) {
                    th = th222222;
                    tcookie = tcookie2;
                    tuid = tuid2;
                    taccesstoken = taccesstoken2;
                    rtnmsg = rtnmsg2;
                    rcode = 1;
                    if (onGetQihooTokenListener != null) {
                        onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                    }
                    throw th;
                }
            }
        });
        return true;
    }

    public boolean refreshQihooToken(String appid, String uid, String tkt, String access_token, String tappname, OnGetQihooTokenListener listener) {
        String prefix = "[appid:" + appid + "][uid:" + uid + "][tkt:" + tkt + "][access_token:" + access_token + "][tappname:" + tappname + "]";
        LOG.d(TAG, prefix + " refreshQihooToken...");
        final String str = appid;
        final String str2 = uid;
        final String str3 = tkt;
        final String str4 = access_token;
        final String str5 = tappname;
        final String str6 = prefix;
        final OnGetQihooTokenListener onGetQihooTokenListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                int rcode;
                String rtnmsg;
                String taccesstoken;
                Exception e;
                String tcookie;
                String tuid;
                Throwable th;
                long start = System.currentTimeMillis();
                String rtnmsg2 = "";
                String taccesstoken2 = "";
                String tuid2 = "";
                String tcookie2 = "";
                String qcookie = "";
                try {
                    HTTPAgent agent = BasicWsApi.this.requestBuilder.buildRefreshQihooToken(str, str2, str3, str4, str5);
                    agent.execute();
                    long millis = System.currentTimeMillis() - start;
                    if (agent.ok()) {
                        rcode = 0;
                        rtnmsg = agent.getString("rtnmsg");
                        try {
                            taccesstoken = agent.getString("taccesstoken");
                        } catch (Exception e2) {
                            e = e2;
                            tcookie = tcookie2;
                            tuid = tuid2;
                            taccesstoken = taccesstoken2;
                            LOG.e(BasicWsApi.TAG, str6 + "[millis:" + (System.currentTimeMillis() - start) + "] refreshQihooToken failed(Exception)", e);
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            tcookie = tcookie2;
                            tuid = tuid2;
                            taccesstoken = taccesstoken2;
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                            throw th;
                        }
                        try {
                            tuid = agent.getString("tuid");
                            try {
                                tcookie = agent.getString("tcookie");
                            } catch (Exception e3) {
                                e = e3;
                                tcookie = tcookie2;
                                LOG.e(BasicWsApi.TAG, str6 + "[millis:" + (System.currentTimeMillis() - start) + "] refreshQihooToken failed(Exception)", e);
                                if (onGetQihooTokenListener != null) {
                                    onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                                }
                            } catch (Throwable th22) {
                                th = th22;
                                tcookie = tcookie2;
                                if (onGetQihooTokenListener != null) {
                                    onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                                }
                                throw th;
                            }
                        } catch (Exception e4) {
                            e = e4;
                            tcookie = tcookie2;
                            tuid = tuid2;
                            LOG.e(BasicWsApi.TAG, str6 + "[millis:" + (System.currentTimeMillis() - start) + "] refreshQihooToken failed(Exception)", e);
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                        } catch (Throwable th222) {
                            th = th222;
                            tcookie = tcookie2;
                            tuid = tuid2;
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                            throw th;
                        }
                        try {
                            qcookie = agent.getString("qcookie");
                            LOG.i(BasicWsApi.TAG, str6 + "[millis:" + millis + "] refreshQihooToken code ok");
                        } catch (Exception e5) {
                            e = e5;
                            LOG.e(BasicWsApi.TAG, str6 + "[millis:" + (System.currentTimeMillis() - start) + "] refreshQihooToken failed(Exception)", e);
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                        }
                    }
                    rcode = agent.getRcode();
                    try {
                        LOG.e(BasicWsApi.TAG, str6 + "[millis:" + millis + "] refreshQihooToken failed(" + rcode + ")");
                        tcookie = tcookie2;
                        tuid = tuid2;
                        taccesstoken = taccesstoken2;
                        rtnmsg = rtnmsg2;
                    } catch (Exception e6) {
                        e = e6;
                        tcookie = tcookie2;
                        tuid = tuid2;
                        taccesstoken = taccesstoken2;
                        rtnmsg = rtnmsg2;
                        try {
                            LOG.e(BasicWsApi.TAG, str6 + "[millis:" + (System.currentTimeMillis() - start) + "] refreshQihooToken failed(Exception)", e);
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                        } catch (Throwable th2222) {
                            th = th2222;
                            if (onGetQihooTokenListener != null) {
                                onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                            }
                            throw th;
                        }
                    } catch (Throwable th22222) {
                        th = th22222;
                        tcookie = tcookie2;
                        tuid = tuid2;
                        taccesstoken = taccesstoken2;
                        rtnmsg = rtnmsg2;
                        if (onGetQihooTokenListener != null) {
                            onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                        }
                        throw th;
                    }
                    if (onGetQihooTokenListener != null) {
                        onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                    }
                } catch (Exception e7) {
                    e = e7;
                    tcookie = tcookie2;
                    tuid = tuid2;
                    taccesstoken = taccesstoken2;
                    rtnmsg = rtnmsg2;
                    rcode = 1;
                    LOG.e(BasicWsApi.TAG, str6 + "[millis:" + (System.currentTimeMillis() - start) + "] refreshQihooToken failed(Exception)", e);
                    if (onGetQihooTokenListener != null) {
                        onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                    }
                } catch (Throwable th222222) {
                    th = th222222;
                    tcookie = tcookie2;
                    tuid = tuid2;
                    taccesstoken = taccesstoken2;
                    rtnmsg = rtnmsg2;
                    rcode = 1;
                    if (onGetQihooTokenListener != null) {
                        onGetQihooTokenListener.onDone(rcode, rtnmsg, taccesstoken, tuid, tcookie, qcookie);
                    }
                    throw th;
                }
            }
        });
        return true;
    }

    public boolean doPostHttp(String method, Bundle input, OnBundleListener listener) {
        String prefix = "doPostHttp [method:" + method + "][input:" + input + "]";
        LOG.d(TAG, prefix + " start...");
        final String str = method;
        final Bundle bundle = input;
        final String str2 = prefix;
        final OnBundleListener onBundleListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                Exception e;
                Throwable th;
                int rcode = 1;
                long start = System.currentTimeMillis();
                Bundle bundle = null;
                try {
                    HTTPAgent agent = BasicWsApi.this.requestBuilder.buildDoPostHttp(str, bundle);
                    agent.execute();
                    long millis = System.currentTimeMillis() - start;
                    if (agent.ok()) {
                        rcode = 0;
                        bundle = agent.getBundle();
                        LOG.i(BasicWsApi.TAG, str2 + "[millis:" + millis + "] doPostHttp code ok");
                    } else {
                        rcode = agent.getRcode();
                        Bundle ret = new Bundle();
                        try {
                            LOG.e(BasicWsApi.TAG, str2 + "[millis:" + millis + "] doPostHttp failed(" + rcode + ")");
                            bundle = ret;
                        } catch (Exception e2) {
                            e = e2;
                            bundle = ret;
                            try {
                                LOG.e(BasicWsApi.TAG, str2 + "[millis:" + (System.currentTimeMillis() - start) + "] doPostHttp failed(Exception)", e);
                                if (onBundleListener != null) {
                                    onBundleListener.onDone(rcode, bundle);
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                if (onBundleListener != null) {
                                    onBundleListener.onDone(rcode, bundle);
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            bundle = ret;
                            if (onBundleListener != null) {
                                onBundleListener.onDone(rcode, bundle);
                            }
                            throw th;
                        }
                    }
                    if (onBundleListener != null) {
                        onBundleListener.onDone(rcode, bundle);
                    }
                } catch (Exception e3) {
                    e = e3;
                    LOG.e(BasicWsApi.TAG, str2 + "[millis:" + (System.currentTimeMillis() - start) + "] doPostHttp failed(Exception)", e);
                    if (onBundleListener != null) {
                        onBundleListener.onDone(rcode, bundle);
                    }
                }
            }
        });
        return true;
    }

    public boolean doPostThirdHttp(String method, Bundle input, OnBundleListener listener) {
        String prefix = "doPostThirdHttp [method:" + method + "][input:" + input + "]";
        LOG.d(TAG, prefix + " start...");
        final String str = method;
        final Bundle bundle = input;
        final String str2 = prefix;
        final OnBundleListener onBundleListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                Exception e;
                Throwable th;
                int rcode = 1;
                long start = System.currentTimeMillis();
                Bundle bundle = null;
                try {
                    HTTPAgent agent = BasicWsApi.this.requestBuilder.buildDoThirdPostHttp(str, bundle);
                    agent.execute();
                    long millis = System.currentTimeMillis() - start;
                    if (agent.ok()) {
                        rcode = 0;
                        bundle = agent.getBundle();
                        LOG.i(BasicWsApi.TAG, str2 + "[millis:" + millis + "] doPostThirdHttp code ok");
                    } else {
                        rcode = agent.getRcode();
                        Bundle ret = new Bundle();
                        try {
                            LOG.e(BasicWsApi.TAG, str2 + "[millis:" + millis + "] doPostThirdHttp failed(" + rcode + ")");
                            bundle = ret;
                        } catch (Exception e2) {
                            e = e2;
                            bundle = ret;
                            try {
                                LOG.e(BasicWsApi.TAG, str2 + "[millis:" + (System.currentTimeMillis() - start) + "] doPostThirdHttp failed(Exception)", e);
                                if (onBundleListener != null) {
                                    onBundleListener.onDone(rcode, bundle);
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                if (onBundleListener != null) {
                                    onBundleListener.onDone(rcode, bundle);
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            bundle = ret;
                            if (onBundleListener != null) {
                                onBundleListener.onDone(rcode, bundle);
                            }
                            throw th;
                        }
                    }
                    if (onBundleListener != null) {
                        onBundleListener.onDone(rcode, bundle);
                    }
                } catch (Exception e3) {
                    e = e3;
                    LOG.e(BasicWsApi.TAG, str2 + "[millis:" + (System.currentTimeMillis() - start) + "] doPostThirdHttp failed(Exception)", e);
                    if (onBundleListener != null) {
                        onBundleListener.onDone(rcode, bundle);
                    }
                }
            }
        });
        return true;
    }

    public boolean getCaptchaImg(String appid, String captchaKey, String width, String height, String length, OnCaptchaListener listener) {
        String prefix = "[appid:" + appid + "][captchaKey:" + captchaKey + "][width:" + width + "][height:" + height + "][length:" + length + "]";
        LOG.d(TAG, prefix + " getCaptchaImg...");
        final String str = appid;
        final String str2 = captchaKey;
        final String str3 = width;
        final String str4 = height;
        final String str5 = length;
        final String str6 = prefix;
        final OnCaptchaListener onCaptchaListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r14 = this;
                r9 = 1;
                r12 = java.lang.System.currentTimeMillis();
                r10 = 0;
                r8 = 0;
                r0 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = r0.requestBuilder;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r3;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = r4;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r3 = r5;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r4 = r6;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r5 = r7;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r6 = r0.buildGetCaptchaImg(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r6.execute();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r10 = r0 - r12;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = r6.ok();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                if (r0 != 0) goto L_0x0065;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
            L_0x0029:
                r9 = r6.getRcode();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1.<init>();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = r8;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = "] getCaptchaImg failed(";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r9);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = ")";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
            L_0x005b:
                r0 = r9;
                if (r0 == 0) goto L_0x0064;
            L_0x005f:
                r0 = r9;
                r0.onDone(r9, r8);
            L_0x0064:
                return;
            L_0x0065:
                r9 = 0;
                r8 = r6.getResponseByte();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1.<init>();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = r8;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = "] getCaptchaImg code ok";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                com.coolcloud.uac.android.common.util.LOG.i(r0, r1);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                goto L_0x005b;
            L_0x008f:
                r7 = move-exception;
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r10 = r0 - r12;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1.<init>();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = r8;	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r2 = "] getCaptchaImg failed(Exception)";	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1, r7);	 Catch:{ Exception -> 0x008f, all -> 0x00c4 }
                r0 = r9;
                if (r0 == 0) goto L_0x0064;
            L_0x00be:
                r0 = r9;
                r0.onDone(r9, r8);
                goto L_0x0064;
            L_0x00c4:
                r0 = move-exception;
                r1 = r9;
                if (r1 == 0) goto L_0x00ce;
            L_0x00c9:
                r1 = r9;
                r1.onDone(r9, r8);
            L_0x00ce:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.59.rundo():void");
            }
        });
        return true;
    }

    public boolean registerPhoneGetActivateCodeSafe(String phone, String appId, String captchakey, String captchacode, OnCommonListener listener) {
        String prefix = "[phone:" + phone + "][appId:" + appId + "][captchakey:" + captchakey + "][captchacode:" + captchacode + "]";
        LOG.d(TAG, prefix + " register phone get activate code Safe...");
        final String str = phone;
        final String str2 = appId;
        final String str3 = captchakey;
        final String str4 = captchacode;
        final String str5 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r3;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r4;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r10 = r5;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r11 = r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0 = r5.buildRegisterPhoneGetActivateCodeSafe(r8, r9, r10, r11);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0.execute();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                if (r5 != 0) goto L_0x0062;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0026:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] register phone get activate Safe code failed(";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = ")";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0058:
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x005c:
                r5 = r8;
                r5.onDone(r4);
            L_0x0061:
                return;
            L_0x0062:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] register phone get activate Safe code ok";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                goto L_0x0058;
            L_0x0088:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] register phone get activate code Safe failed(Exception)";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x00b7:
                r5 = r8;
                r5.onDone(r4);
                goto L_0x0061;
            L_0x00bd:
                r5 = move-exception;
                r8 = r8;
                if (r8 == 0) goto L_0x00c7;
            L_0x00c2:
                r8 = r8;
                r8.onDone(r4);
            L_0x00c7:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.60.rundo():void");
            }
        });
        return true;
    }

    public boolean updateDeviceId(String uid, String tkt, String newdevid, String olddevid, String appid, OnCommonListener listener) {
        String prefix = "[uid:" + uid + "][appid:" + appid + "][tkt:" + tkt + "][newdevid:" + newdevid + "][olddevid:" + olddevid + "]";
        LOG.d(TAG, prefix + " update DeviceId...");
        final String str = uid;
        final String str2 = tkt;
        final String str3 = newdevid;
        final String str4 = olddevid;
        final String str5 = appid;
        final String str6 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r14 = this;
                r10 = 1;
                r12 = java.lang.System.currentTimeMillis();
                r8 = 0;
                r0 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r0.requestBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r3;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r4;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r3 = r5;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r4 = r6;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r5 = r7;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r6 = r0.buildupdateDeviceId(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r6.execute();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r6.ok();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                if (r0 != 0) goto L_0x0064;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
            L_0x0028:
                r10 = r6.getRcode();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] update DeviceId code failed(";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r10);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = ")";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
            L_0x005a:
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x005e:
                r0 = r9;
                r0.onDone(r10);
            L_0x0063:
                return;
            L_0x0064:
                r10 = 0;
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] update DeviceId code ok";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.i(r0, r1);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                goto L_0x005a;
            L_0x008a:
                r7 = move-exception;
                r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r8 = r0 - r12;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = "BasicWsApi";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = r8;	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "[millis:";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r2 = "] update DeviceId failed(Exception)";	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.append(r2);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                com.coolcloud.uac.android.common.util.LOG.e(r0, r1, r7);	 Catch:{ Exception -> 0x008a, all -> 0x00bf }
                r0 = r9;
                if (r0 == 0) goto L_0x0063;
            L_0x00b9:
                r0 = r9;
                r0.onDone(r10);
                goto L_0x0063;
            L_0x00bf:
                r0 = move-exception;
                r1 = r9;
                if (r1 == 0) goto L_0x00c9;
            L_0x00c4:
                r1 = r9;
                r1.onDone(r10);
            L_0x00c9:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.61.rundo():void");
            }
        });
        return true;
    }

    public boolean forwardPhoneGetActivateCodeSafe(String phone, String appId, String captchakey, String captchacode, OnCommonListener listener) {
        String prefix = "[phone:" + phone + "][appId:" + appId + "][captchakey:" + captchakey + "][captchacode:" + captchacode + "]";
        LOG.d(TAG, prefix + " forward phone get activate code Safe...");
        final String str = phone;
        final String str2 = appId;
        final String str3 = captchakey;
        final String str4 = captchacode;
        final String str5 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r3;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r4;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r10 = r5;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r11 = r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0 = r5.buildForwardPhoneGetActivateCodeSafe(r8, r9, r10, r11);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0.execute();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                if (r5 != 0) goto L_0x0062;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0026:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] forward phone get activate Safe code failed(";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = ")";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0058:
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x005c:
                r5 = r8;
                r5.onDone(r4);
            L_0x0061:
                return;
            L_0x0062:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] forward phone get activate Safe code ok";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                goto L_0x0058;
            L_0x0088:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] forward phone get activate code Safe failed(Exception)";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x00b7:
                r5 = r8;
                r5.onDone(r4);
                goto L_0x0061;
            L_0x00bd:
                r5 = move-exception;
                r8 = r8;
                if (r8 == 0) goto L_0x00c7;
            L_0x00c2:
                r8 = r8;
                r8.onDone(r4);
            L_0x00c7:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.62.rundo():void");
            }
        });
        return true;
    }

    public boolean findpwdPhoneGetActivateCodeSafe(String phone, String appId, String captchakey, String captchacode, OnCommonListener listener) {
        String prefix = "[phone:" + phone + "][appId:" + appId + "][captchakey:" + captchakey + "][captchacode:" + captchacode + "]";
        LOG.d(TAG, prefix + " findpwd phone get activate code Safe...");
        final String str = phone;
        final String str2 = appId;
        final String str3 = captchakey;
        final String str4 = captchacode;
        final String str5 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r3;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r4;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r10 = r5;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r11 = r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0 = r5.buildFindpwdPhoneGetActivateCodeSafe(r8, r9, r10, r11);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0.execute();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                if (r5 != 0) goto L_0x0062;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0026:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] findpwd phone get activate Safe code failed(";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = ")";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0058:
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x005c:
                r5 = r8;
                r5.onDone(r4);
            L_0x0061:
                return;
            L_0x0062:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] findpwd phone get activate Safe code ok";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                goto L_0x0058;
            L_0x0088:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] findpwd phone get activate code Safe failed(Exception)";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x00b7:
                r5 = r8;
                r5.onDone(r4);
                goto L_0x0061;
            L_0x00bd:
                r5 = move-exception;
                r8 = r8;
                if (r8 == 0) goto L_0x00c7;
            L_0x00c2:
                r8 = r8;
                r8.onDone(r4);
            L_0x00c7:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.63.rundo():void");
            }
        });
        return true;
    }

    public boolean bindPhoneGetActivateCodeSafe(String phone, String appId, String captchakey, String captchacode, OnCommonListener listener) {
        String prefix = "[phone:" + phone + "][appId:" + appId + "][captchakey:" + captchakey + "][captchacode:" + captchacode + "]";
        LOG.d(TAG, prefix + " bind phone get activate code Safe...");
        final String str = phone;
        final String str2 = appId;
        final String str3 = captchakey;
        final String str4 = captchacode;
        final String str5 = prefix;
        final OnCommonListener onCommonListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r12 = this;
                r4 = 1;
                r6 = java.lang.System.currentTimeMillis();
                r2 = 0;
                r5 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r5.requestBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r3;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r4;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r10 = r5;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r11 = r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0 = r5.buildBindPhoneGetActivateCodeSafe(r8, r9, r10, r11);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r0.execute();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r0.ok();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                if (r5 != 0) goto L_0x0062;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0026:
                r4 = r0.getRcode();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] bind phone get activate Safe code failed(";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r4);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = ")";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
            L_0x0058:
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x005c:
                r5 = r8;
                r5.onDone(r4);
            L_0x0061:
                return;
            L_0x0062:
                r4 = 0;
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] bind phone get activate Safe code ok";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.i(r5, r8);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                goto L_0x0058;
            L_0x0088:
                r1 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r2 = r8 - r6;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = "BasicWsApi";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "[millis:";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r2);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r9 = "] bind phone get activate code Safe failed(Exception)";	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.append(r9);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r8 = r8.toString();	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                com.coolcloud.uac.android.common.util.LOG.e(r5, r8, r1);	 Catch:{ Exception -> 0x0088, all -> 0x00bd }
                r5 = r8;
                if (r5 == 0) goto L_0x0061;
            L_0x00b7:
                r5 = r8;
                r5.onDone(r4);
                goto L_0x0061;
            L_0x00bd:
                r5 = move-exception;
                r8 = r8;
                if (r8 == 0) goto L_0x00c7;
            L_0x00c2:
                r8 = r8;
                r8.onDone(r4);
            L_0x00c7:
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.64.rundo():void");
            }
        });
        return true;
    }

    public boolean registerEmail(String email, String password, String nickname, String appId, OnRegisterListener listener) {
        final String md5 = EncryptUtils.getMD5String(password);
        String prefix = "[email:" + email + "][pwd:" + md5 + "][nickname:" + nickname + "][appId:" + appId + "]";
        LOG.d(TAG, prefix + " register Email ...");
        final String str = email;
        final String str2 = nickname;
        final String str3 = appId;
        final String str4 = prefix;
        final OnRegisterListener onRegisterListener = listener;
        Executor.execute(new RunNoThrowable(prefix) {
            public void rundo() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r13 = this;
                r3 = 1;
                r0 = 0;
                r6 = java.lang.System.currentTimeMillis();
                r4 = 0;
                r8 = com.coolcloud.uac.android.common.ws.BasicWsApi.this;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r8 = r8.requestBuilder;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r3;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r10 = r4;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r11 = r5;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r12 = r6;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r1 = r8.buildRegisterEmail(r9, r10, r11, r12);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r1.execute();	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r4 = r8 - r6;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r8 = r1.ok();	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                if (r8 != 0) goto L_0x0063;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
            L_0x0027:
                r3 = r1.getRcode();	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9.<init>();	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r10 = r7;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r10 = "] register Email failed(";	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r3);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r10 = ")";	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
            L_0x0059:
                r8 = r8;
                if (r8 == 0) goto L_0x0062;
            L_0x005d:
                r8 = r8;
                r8.onDone(r3, r0);
            L_0x0062:
                return;
            L_0x0063:
                r3 = 0;
                r8 = "accountid";	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r0 = r1.getString(r8);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9.<init>();	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r10 = r7;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r10 = "] register Email ok(";	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r0);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r10 = ")";	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                com.coolcloud.uac.android.common.util.LOG.i(r8, r9);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                goto L_0x0059;
            L_0x0099:
                r2 = move-exception;
                r8 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r4 = r8 - r6;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r8 = "BasicWsApi";	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9.<init>();	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r10 = r7;	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r10 = "[millis:";	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r4);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r10 = "] register Email failed(Exception)";	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.append(r10);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r9 = r9.toString();	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                com.coolcloud.uac.android.common.util.LOG.e(r8, r9, r2);	 Catch:{ Exception -> 0x0099, all -> 0x00ce }
                r8 = r8;
                if (r8 == 0) goto L_0x0062;
            L_0x00c8:
                r8 = r8;
                r8.onDone(r3, r0);
                goto L_0x0062;
            L_0x00ce:
                r8 = move-exception;
                r9 = r8;
                if (r9 == 0) goto L_0x00d8;
            L_0x00d3:
                r9 = r8;
                r9.onDone(r3, r0);
            L_0x00d8:
                throw r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.coolcloud.uac.android.common.ws.BasicWsApi.65.rundo():void");
            }
        });
        return true;
    }
}
