package com.letv.android.client.commonlib.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.android.wo.ex.IWoFlowManager.ORDER_STATE;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils;
import com.letv.core.messagebus.config.LeIntentConfig;
import com.letv.core.messagebus.config.LeIntentConfig.IntentFlag;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.NetworkUtils;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class LetvWebViewActivityConfig extends LeIntentConfig {
    public static final String FROM = "from";
    public static final String IS_FROM_NATIVE = "isFromNative";
    public static final String IS_RED_ENVELOPE = "isRedEnvelope";
    public static final String IS_SHOW_SHARE = "isShowShare";
    public static final String JUMP_TYPE = "jumpType";
    public static final String LOAD_TYPE = "loadType";
    public static final int M_DEALBACK = 10001;
    public static final String URL = "url";

    public LetvWebViewActivityConfig(Context context) {
        super(context);
    }

    public LetvWebViewActivityConfig createWithRequestCode(String url, int requestCode) {
        getIntent().putExtra("url", url);
        if (requestCode > 0) {
            setIntentFlag(IntentFlag.START_ACTIVITY_FOR_RESULT);
            setRequestCode(requestCode);
        }
        return this;
    }

    public LetvWebViewActivityConfig create(String url, int from) {
        Intent intent = getIntent();
        intent.putExtra("url", url);
        intent.putExtra("from", from);
        return this;
    }

    public LetvWebViewActivityConfig create(String url, String loadType, int jumpType) {
        Intent intent = getIntent();
        intent.putExtra("url", url);
        intent.putExtra(LOAD_TYPE, loadType);
        intent.putExtra(JUMP_TYPE, jumpType);
        return this;
    }

    public LetvWebViewActivityConfig create(String url, String loadType, boolean isShowShare, boolean isRedEnvelope) {
        Intent intent = getIntent();
        intent.putExtra("url", url);
        intent.putExtra(LOAD_TYPE, loadType);
        intent.putExtra(IS_SHOW_SHARE, isShowShare);
        intent.putExtra(IS_RED_ENVELOPE, isRedEnvelope);
        return this;
    }

    public LetvWebViewActivityConfig createNative(String url, String titles) {
        Intent intent = getIntent();
        intent.putExtra("url", url);
        intent.putExtra(LOAD_TYPE, titles);
        intent.putExtra(IS_SHOW_SHARE, false);
        intent.putExtra(IS_RED_ENVELOPE, false);
        intent.putExtra(IS_FROM_NATIVE, true);
        return this;
    }

    public void launch(String url, String title) {
        if (!LetvUtils.isGooglePlay()) {
            if (LetvUtils.isInHongKong()) {
                launch(url, title, false, false);
            } else {
                launch(url, title, true, false);
            }
        }
    }

    public void launch(String url) {
        launch(url, false, true, 25);
    }

    public void launch(String url, String title, boolean isShowShare, boolean isRedEnvelope) {
        boolean isInnerUrl = LetvUtils.judgeInnerUrl(url);
        url = url.replaceAll(" ", "");
        if (!(url == null || url.startsWith("http://") || url.startsWith("https://"))) {
            url = "http://" + url;
        }
        String tmp_url = url;
        if (!isInnerUrl) {
            ORDER_STATE state = ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).getUserOrderInfo(this.mContext);
            if (NetworkUtils.isUnicom3G(false) && (state == ORDER_STATE.ORDER || state == ORDER_STATE.UNORDER)) {
                UnicomWoFlowDialogUtils.woWebViewNotPlayDialog(this.mContext, new 1(this, tmp_url, title));
            } else if (!launchWebJudgeUrl(tmp_url)) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, create(tmp_url, title, isShowShare, isRedEnvelope)));
            }
        } else if (!launchWebJudgeUrl(tmp_url)) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, create(tmp_url, title, isShowShare, isRedEnvelope)));
        }
    }

    public void launch(String url, boolean forcedOpenWithWebview, boolean finishSelf, int from) {
        if (!LetvUtils.isGooglePlay()) {
            boolean isInnerUrl = LetvUtils.judgeInnerUrl(url);
            url = url.replaceAll(" ", "");
            if (!(url == null || url.startsWith("http://") || url.startsWith("https://"))) {
                url = "http://" + url;
            }
            String tmp_url = url;
            if (!isInnerUrl) {
                ORDER_STATE state = ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).getUserOrderInfo(this.mContext);
                if (NetworkUtils.isUnicom3G(false) && (state == ORDER_STATE.ORDER || state == ORDER_STATE.UNORDER)) {
                    UnicomWoFlowDialogUtils.woWebViewNotPlayDialog(this.mContext, new 2(this, forcedOpenWithWebview, tmp_url));
                } else if (forcedOpenWithWebview || !launchWebJudgeUrl(tmp_url)) {
                    if (finishSelf && (this.mContext instanceof Activity)) {
                        ((Activity) this.mContext).finish();
                    }
                    if (from == -1) {
                        from = 20;
                    }
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, create(tmp_url, from)));
                }
            } else if (forcedOpenWithWebview || !launchWebJudgeUrl(tmp_url)) {
                if (finishSelf && (this.mContext instanceof Activity)) {
                    ((Activity) this.mContext).finish();
                }
                if (from == -1) {
                    from = 20;
                }
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, create(tmp_url, from)));
            }
        }
    }

    public void launch(String url, String title, int jumpType) {
        if (!LetvUtils.isGooglePlay()) {
            boolean isInnerUrl = LetvUtils.judgeInnerUrl(url);
            url = url.replaceAll(" ", "").replaceAll(" ", "");
            if (!(url == null || url.startsWith("http://") || url.startsWith("https://"))) {
                url = "http://" + url;
            }
            String tmp_url = url;
            if (!isInnerUrl) {
                ORDER_STATE state = ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).getUserOrderInfo(this.mContext);
                if (NetworkUtils.isUnicom3G(false) && (state == ORDER_STATE.ORDER || state == ORDER_STATE.UNORDER)) {
                    UnicomWoFlowDialogUtils.woWebViewNotPlayDialog(this.mContext, new 3(this, tmp_url, title, jumpType));
                } else if (!launchWebJudgeUrl(tmp_url)) {
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, create(tmp_url, title, jumpType)));
                }
            } else if (!launchWebJudgeUrl(tmp_url)) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, create(tmp_url, title, jumpType)));
            }
        }
    }

    private boolean launchWebJudgeUrl(String url) {
        if (TextUtils.isEmpty(url) || !url.toLowerCase().contains("vplay_") || url.lastIndexOf("/") <= 0) {
            return false;
        }
        String u = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        if (!u.contains("vplay_")) {
            return false;
        }
        String vplayId = u.substring(u.lastIndexOf(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR) + 1, u.length());
        if (TextUtils.isEmpty(vplayId)) {
            return false;
        }
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mContext).createForWebPay(0, BaseTypeUtils.stol(vplayId), 0, false)));
        return true;
    }
}
