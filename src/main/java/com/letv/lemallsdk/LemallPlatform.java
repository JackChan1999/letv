package com.letv.lemallsdk;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.letv.component.upgrade.core.AppDownloadConfiguration.ConfigurationBuild;
import com.letv.lemallsdk.activity.LemallSdkMainActivity;
import com.letv.lemallsdk.api.HttpTask;
import com.letv.lemallsdk.api.ILemallPlatformListener;
import com.letv.lemallsdk.model.AppInfo;
import com.letv.lemallsdk.model.BaseBean;
import com.letv.lemallsdk.model.ILetvBridge;
import com.letv.lemallsdk.model.TitleStatus;
import com.letv.lemallsdk.util.Constants;
import com.letv.lemallsdk.util.EALogger;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.List;
import master.flame.danmaku.danmaku.parser.IDataSource;

public final class LemallPlatform {
    public static final String PAYSUCCESSACTION = "com.letv.letvshop.lemall.action.paysuccess_action";
    private static LemallPlatform letvShopPlatform;
    private ILemallPlatformListener callbackListener;
    private String cookieLinkdata;
    private Context ctx;
    private AppInfo mAppInfo;
    public String orderId = "";
    public String payOrderId = "";
    public String payOrderPrice = "";
    public Boolean paySuccess = Boolean.valueOf(false);
    private String ssoToken;
    private List<BaseBean> titleList;
    public String uuid = "";

    public static void Init(Context context, AppInfo appInfo, ILemallPlatformListener listener) {
        if (letvShopPlatform == null) {
            if (appInfo == null) {
                throw new NullPointerException("AppInfo is Null");
            }
            letvShopPlatform = new LemallPlatform(context, appInfo, listener);
        }
        letvShopPlatform.getInitData();
    }

    private LemallPlatform(Context context, AppInfo appInfo, ILemallPlatformListener listener) {
        this.ctx = context;
        this.mAppInfo = appInfo;
        this.callbackListener = listener;
        initImageLoader(context);
    }

    public static LemallPlatform getInstance() {
        if (letvShopPlatform != null) {
            return letvShopPlatform;
        }
        throw new IllegalStateException("使用商城SDK前请先调用LetvShopPlatform.Init()且AppInfo参数不可为null");
    }

    private void getInitData() {
        HttpTask.getEncryBodyMap().put("appid", this.mAppInfo.getId());
        HttpTask.getTitleStatus(new ILetvBridge() {
            public void goldData(Object successObj) {
                BaseBean list = (BaseBean) successObj;
                if ("200".equals(list.getStatus())) {
                    LemallPlatform.this.titleList = list.getBeanList();
                }
            }
        });
    }

    public void openSdkPage(String pageFlag, String ssoToken) {
        if (this.ctx != null) {
            setSsoToken(ssoToken);
            Intent intent = new Intent(this.ctx, LemallSdkMainActivity.class);
            intent.setFlags(268435456);
            intent.putExtra(Constants.BUY_BY_WATCHING, false);
            intent.putExtra(Constants.PAGE_FLAG, pageFlag);
            this.ctx.startActivity(intent);
        }
    }

    public void openSdkPage(String pageFlag, String value, String ssoToken) {
        if (this.ctx != null) {
            setSsoToken(ssoToken);
            Intent intent = new Intent(this.ctx, LemallSdkMainActivity.class);
            intent.setFlags(268435456);
            intent.putExtra(Constants.BUY_BY_WATCHING, false);
            intent.putExtra(Constants.PAGE_FLAG, pageFlag);
            intent.putExtra(Constants.VALUE_ID, value);
            this.ctx.startActivity(intent);
        }
    }

    public void openSdkPageByBuyWatching(String pageFlag, String uuid, String ssoToken) {
        if (this.ctx != null) {
            setSsoToken(ssoToken);
            Intent intent = new Intent(this.ctx, LemallSdkMainActivity.class);
            intent.setFlags(268435456);
            intent.putExtra(Constants.BUY_BY_WATCHING, true);
            intent.putExtra(Constants.PAGE_FLAG, pageFlag);
            intent.putExtra(Constants.UUID, uuid);
            this.ctx.startActivity(intent);
        }
    }

    public void openSdkPageByBuyWatching(String pageFlag, String value, String uuid, String ssoToken) {
        if (this.ctx != null) {
            setSsoToken(ssoToken);
            Intent intent = new Intent(this.ctx, LemallSdkMainActivity.class);
            intent.setFlags(268435456);
            intent.putExtra(Constants.BUY_BY_WATCHING, true);
            intent.putExtra(Constants.PAGE_FLAG, pageFlag);
            intent.putExtra(Constants.VALUE_ID, value);
            intent.putExtra(Constants.UUID, uuid);
            this.ctx.startActivity(intent);
        }
    }

    public void closeSdkPage() {
    }

    public Context getContext() {
        return this.ctx;
    }

    public AppInfo getmAppInfo() {
        return this.mAppInfo;
    }

    public ILemallPlatformListener getCallbackListener() {
        return this.callbackListener;
    }

    public TitleStatus getTitleInfo(String pageflag) {
        if (!(this.titleList == null || this.titleList.size() <= 0 || TextUtils.isEmpty(pageflag))) {
            for (int i = 0; i < this.titleList.size(); i++) {
                if (pageflag.equals(((TitleStatus) this.titleList.get(i)).getPageFlag())) {
                    TitleStatus titleStatus = (TitleStatus) this.titleList.get(i);
                    EALogger.i(IDataSource.SCHEME_HTTP_TAG, "标题栏信息：" + titleStatus.getPageFlag() + titleStatus.getTitle() + titleStatus.getHasShare() + titleStatus.getHasMore() + titleStatus.getMenuList().size());
                    return titleStatus;
                }
            }
        }
        return null;
    }

    private static void initImageLoader(Context context) {
        Builder config = new Builder(context);
        config.threadPoolSize(3);
        config.memoryCache(new WeakMemoryCache());
        config.memoryCacheSize(2097152);
        config.discCacheSize(100);
        config.threadPriority(3);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(ConfigurationBuild.DEFAULT_DEFAULT_SDCARD_SIZE);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();
        ImageLoader.getInstance().init(config.build());
    }

    public String getSsoToken() {
        return this.ssoToken;
    }

    public void setSsoToken(String ssoToken) {
        this.ssoToken = ssoToken;
        if (TextUtils.isEmpty(ssoToken)) {
            setCookieLinkdata("");
        }
    }

    public String getCookieLinkdata() {
        return this.cookieLinkdata;
    }

    public void setCookieLinkdata(String cookieLinkdata) {
        this.cookieLinkdata = cookieLinkdata;
        EALogger.i(CommonUtils.SDK, "登录完成设置完Cookie信息：" + cookieLinkdata);
    }
}
