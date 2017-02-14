package com.letv.android.client.share;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import com.letv.android.client.commonlib.share.ShareResultObserver;
import com.letv.android.client.tencentlogin.TencentInstance;
import com.letv.cache.LetvCacheTools.StringTool;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.share.sina.ex.BSsoHandler;
import com.letv.share.sina.ex.RequestListener;
import com.letv.share.tencent.weibo.ex.ITWeiboNew.TWeiboListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SignSharePageActivity extends FragmentActivity {
    private static final String SHARE_FROM = "share mFrom";
    private static final String SHARE_IMAGE_URL = "share url";
    private static final String SHARE_TITLE = "share title";
    private static final String SHARE_WEB_URL = "web url";
    public static List<ShareResultObserver> observers = new ArrayList();
    private boolean isInvite;
    private boolean letvStarIsLogin;
    private boolean letvStarIsShare;
    private String mFragId;
    private int mFrom;
    Handler mHandler;
    private String mImageUrl;
    private String mPicLocalPath;
    private String mShareContent;
    private BSsoHandler mSsoHandler;
    private String mStaticsId;
    private String mTitle;
    private boolean qqIsShare;
    private boolean qzoomIsShare;
    private String shareType;
    private boolean sinaIsLogin;
    private boolean sinaIsShare;
    private boolean tencentIsShare;
    private boolean tencentQzoneIsLogin;
    private boolean tencentWeiboIsLogin;
    private String webUrl;

    private class RequestTask {
        private Context context;
        final /* synthetic */ SignSharePageActivity this$0;

        public RequestTask(SignSharePageActivity signSharePageActivity, Context context) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = signSharePageActivity;
            this.context = context;
        }

        public void start() {
            this.this$0.mHandler.post(new 1(this));
        }

        public Void doInBackground() {
            if (this.this$0.sinaIsLogin && this.this$0.sinaIsShare) {
                RequestListener sinaListener = new 2(this);
                if (this.this$0.isInvite) {
                    LetvSinaShareSSO.shareSinaToInvite(this.this$0, this.this$0.mShareContent, this.this$0.mImageUrl, this.this$0.shareType, sinaListener);
                } else {
                    LetvSinaShareSSO.share(this.this$0, this.this$0.getShareContent(this.this$0.mShareContent), this.this$0.mImageUrl, -1, sinaListener);
                }
            }
            if (this.this$0.tencentWeiboIsLogin && this.this$0.tencentIsShare) {
                TWeiboListener listener = new 3(this);
                if (this.this$0.isInvite) {
                    LetvTencentWeiboShare.shareTencentToInvite(this.this$0, this.this$0.mShareContent, this.this$0.webUrl, this.this$0.shareType, listener);
                } else {
                    LetvTencentWeiboShare.share(this.this$0, this.this$0.getShareContent(this.this$0.mShareContent), this.this$0.webUrl, false, listener);
                }
            }
            if (this.this$0.qzoomIsShare && this.this$0.mFrom == 3) {
                if (this.this$0.isInvite) {
                    LetvQZoneShare.getInstance(this.this$0).shareQzoneToInvite(this.this$0, this.this$0.mTitle, this.this$0.mShareContent, this.this$0.mImageUrl, this.this$0.webUrl, this.this$0.mPicLocalPath, this.this$0.shareType, this.this$0.mStaticsId, this.this$0.mFragId);
                } else {
                    LetvQZoneShare.getInstance(this.this$0).shareLiveToQzone(this.this$0, this.this$0.mTitle, this.this$0.mShareContent, this.this$0.mImageUrl, this.this$0.webUrl, this.this$0.mPicLocalPath, this.this$0.mStaticsId, this.this$0.mFragId);
                }
            }
            if (this.this$0.qqIsShare && this.this$0.mFrom == 6) {
                letvTencentShare.getInstance(this.this$0).shareLiveToTencent(this.this$0, this.this$0.mTitle, this.this$0.mShareContent, this.this$0.mImageUrl, this.this$0.webUrl, this.this$0.mPicLocalPath, this.this$0.mStaticsId, this.this$0.mFragId);
            }
            return null;
        }

        public void onPostExecute(Void result) {
        }
    }

    public SignSharePageActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.sinaIsLogin = true;
        this.tencentWeiboIsLogin = true;
        this.sinaIsShare = false;
        this.tencentIsShare = false;
        this.qzoomIsShare = false;
        this.qqIsShare = false;
        this.letvStarIsShare = false;
        this.mFrom = 0;
        this.isInvite = false;
        this.shareType = "";
        this.webUrl = "";
        this.mStaticsId = "";
        this.mFragId = "";
        this.mHandler = new Handler(Looper.myLooper());
    }

    public static void launch(Context activity, int from, String title, String imageUrl, String webUrl, String staticsId, String fragId) {
        Intent intent = new Intent(activity, SignSharePageActivity.class);
        intent.putExtra(SHARE_FROM, from);
        intent.putExtra(SHARE_TITLE, title);
        intent.putExtra(SHARE_IMAGE_URL, imageUrl);
        intent.putExtra("isInvite", false);
        intent.putExtra("shareType", "");
        intent.putExtra(SHARE_WEB_URL, webUrl);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    public static void launch(Context activity, int from, String title, String imageUrl, String shareType, boolean isInvite, String webUrl, String staticsId, String fragId) {
        Intent intent = new Intent(activity, SignSharePageActivity.class);
        intent.putExtra(SHARE_FROM, from);
        intent.putExtra(SHARE_TITLE, title);
        intent.putExtra(SHARE_IMAGE_URL, imageUrl);
        intent.putExtra("isInvite", isInvite);
        intent.putExtra("shareType", shareType);
        intent.putExtra(SHARE_WEB_URL, webUrl);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.mFrom = intent.getIntExtra(SHARE_FROM, 0);
        this.mTitle = intent.getStringExtra(SHARE_TITLE);
        LogInfo.log("lxx", "onCreate mTitle: " + this.mTitle);
        this.mImageUrl = intent.getStringExtra(SHARE_IMAGE_URL);
        this.isInvite = intent.getBooleanExtra("isInvite", false);
        this.shareType = intent.getStringExtra("shareType");
        this.mStaticsId = intent.getStringExtra("staticsId");
        this.mFragId = intent.getStringExtra("fragId");
        this.mShareContent = LetvTools.getTextTitleFromServer("80002", getResources().getString(2131100855));
        if (this.isInvite) {
            this.mShareContent = getTitleText();
        }
        new Thread(this) {
            final /* synthetic */ SignSharePageActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                if (Environment.getExternalStorageState().equals("mounted")) {
                    this.this$0.mPicLocalPath = this.this$0.getSignShareImagePath(this.this$0.mImageUrl);
                } else {
                    this.this$0.mPicLocalPath = this.this$0.mImageUrl;
                }
            }
        }.start();
    }

    public String getSignShareImagePath(String imgUrl) {
        Exception e;
        Throwable th;
        String picturePath = "";
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            BufferedInputStream inputStream2 = new BufferedInputStream(new URL(imgUrl).openStream());
            try {
                File localDirFile = new File(StringTool.createFilePath(imgUrl));
                if (localDirFile.exists()) {
                    LogInfo.log("SignSharePageActivity", "local load complete! ImageFilePath = " + localDirFile.getAbsolutePath());
                    picturePath = localDirFile.getAbsolutePath();
                } else {
                    BufferedOutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(localDirFile));
                    try {
                        byte[] buf = new byte[1024];
                        while (true) {
                            int length = inputStream2.read(buf);
                            if (length == -1) {
                                break;
                            }
                            outputStream2.write(buf, 0, length);
                        }
                        LogInfo.log("SignSharePageActivity", "download complete! ImageFilePath = " + localDirFile.getAbsolutePath());
                        picturePath = localDirFile.getAbsolutePath();
                        outputStream = outputStream2;
                    } catch (Exception e2) {
                        e = e2;
                        outputStream = outputStream2;
                        inputStream = inputStream2;
                        try {
                            e.printStackTrace();
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (Exception e32) {
                                    e32.printStackTrace();
                                }
                            }
                            return picturePath;
                        } catch (Throwable th2) {
                            th = th2;
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (Exception e322) {
                                    e322.printStackTrace();
                                }
                            }
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (Exception e3222) {
                                    e3222.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        outputStream = outputStream2;
                        inputStream = inputStream2;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        throw th;
                    }
                }
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (Exception e32222) {
                        e32222.printStackTrace();
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (Exception e322222) {
                            e322222.printStackTrace();
                        }
                    }
                    return picturePath;
                }
                inputStream = inputStream2;
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e4) {
                e322222 = e4;
                inputStream = inputStream2;
                e322222.printStackTrace();
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                return picturePath;
            } catch (Throwable th4) {
                th = th4;
                inputStream = inputStream2;
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                throw th;
            }
        } catch (Exception e5) {
            e322222 = e5;
            e322222.printStackTrace();
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            return picturePath;
        }
        return picturePath;
    }

    private String getTitleText() {
        String titleText = "";
        switch (this.mFrom) {
            case 1:
                return getString(2131100839);
            case 2:
                return getString(2131100835);
            case 3:
                return getString(2131100836);
            case 4:
                return titleText;
            case 5:
                return getString(2131100818);
            case 6:
                return getString(2131100841);
            default:
                return getString(2131100840);
        }
    }

    private String getShareContent(String test) {
        String content = getString(2131100812, new Object[]{""});
        if (test.length() <= 0) {
            return content;
        }
        return getString(2131100812, new Object[]{userc});
    }

    private String getShareCalculateContent(String text) {
        String content = getString(2131100813, new Object[]{""});
        if (text.length() <= 0) {
            return content;
        }
        return getString(2131100813, new Object[]{userc});
    }

    public int count(String text) {
        int doubleC = 0;
        String str = getShareCalculateContent(text);
        while (Pattern.compile("[^\\x00-\\xff]").matcher(str).find()) {
            doubleC++;
        }
        int singelC = str.length() - doubleC;
        if (singelC % 2 != 0) {
            return doubleC + ((singelC + 1) / 2);
        }
        return doubleC + (singelC / 2);
    }

    public int counts(String text) {
        int doubleC = 0;
        while (Pattern.compile("[^\\x00-\\xff]").matcher(text).find()) {
            doubleC++;
        }
        int singelC = text.length() - doubleC;
        if (singelC % 2 != 0) {
            return doubleC + ((singelC + 1) / 2);
        }
        return doubleC + (singelC / 2);
    }

    protected void onResume() {
        super.onResume();
        PreferencesManager.getInstance().setSinaIsShare(false);
        PreferencesManager.getInstance().setTencentIsShare(false);
        PreferencesManager.getInstance().setQzoneIsShare(false);
        PreferencesManager.getInstance().setLestarIsShare(false);
        PreferencesManager.getInstance().setQQIsShare(false);
        switch (this.mFrom) {
            case 1:
                PreferencesManager.getInstance().setSinaIsShare(true);
                break;
            case 2:
                PreferencesManager.getInstance().setTencentIsShare(true);
                break;
            case 3:
                PreferencesManager.getInstance().setQzoneIsShare(true);
                break;
            case 5:
                PreferencesManager.getInstance().setLestarIsShare(true);
                break;
            case 6:
                PreferencesManager.getInstance().setQQIsShare(true);
                break;
        }
        isBind();
        requestData();
    }

    public void isBind() {
        boolean z = true;
        this.sinaIsLogin = LetvSinaShareSSO.isLogin(this) == 1;
        if (LetvTencentWeiboShare.isLogin(this) != 1) {
            z = false;
        }
        this.tencentWeiboIsLogin = z;
        this.sinaIsShare = PreferencesManager.getInstance().sinaIsShare();
        this.tencentIsShare = PreferencesManager.getInstance().tencentIsShare();
        this.qzoomIsShare = PreferencesManager.getInstance().qzoneIsShare();
        this.qqIsShare = PreferencesManager.getInstance().qqIsShare();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.mSsoHandler != null) {
            this.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    private void requestData() {
        if ((!this.sinaIsLogin || !this.sinaIsShare) && ((!this.tencentWeiboIsLogin || !this.tencentIsShare) && !this.qzoomIsShare && !this.qqIsShare && (!this.letvStarIsLogin || !this.letvStarIsShare))) {
            return;
        }
        if (this.qqIsShare && this.mFrom == 6 && !TencentInstance.getInstance(this).isSupportSSOLogin(this)) {
            ToastUtils.showToast((Context) this, getString(2131100834));
        } else if (this.qzoomIsShare && this.mFrom == 3 && !TencentInstance.getInstance(this).isSupportSSOLogin(this)) {
            ToastUtils.showToast((Context) this, getString(2131100834));
        } else {
            new RequestTask(this, this).start();
            UIsUtils.hideSoftkeyboard(this);
        }
    }

    private void sendNotifycation(int notifyId, int textId, int drawableId) {
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        Notification notification = new Notification();
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
        notification.icon = drawableId;
        notification.tickerText = StringUtils.getString(textId);
        notification.defaults |= 1;
        notification.flags = 16;
        notification.setLatestEventInfo(this, null, null, contentIntent);
        notificationManager.notify(notifyId, notification);
        notificationManager.cancel(notifyId);
        if (LetvUtils.getBrandName().toLowerCase().contains("xiaomi")) {
            ToastUtils.showToast((Context) this, textId);
        }
    }
}
