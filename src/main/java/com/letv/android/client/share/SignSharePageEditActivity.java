package com.letv.android.client.share;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.PimBaseActivity;
import com.letv.android.client.commonlib.share.ShareResultObserver;
import com.letv.android.client.tencentlogin.TencentInstance;
import com.letv.android.client.utils.UIs;
import com.letv.cache.LetvCacheTools.StringTool;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.lepaysdk.model.TradeInfo;
import com.letv.share.sina.ex.BSsoHandler;
import com.letv.share.sina.ex.RequestListener;
import com.letv.share.tencent.weibo.ex.ITWeiboNew.TWeiboListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class SignSharePageEditActivity extends PimBaseActivity implements OnClickListener {
    private static final String SHARE_DESC = "share desc";
    private static final String SHARE_FROM = "share mFrom";
    private static final String SHARE_IMAGE_URL = "share url";
    private static final String SHARE_TITLE = "share title";
    private static final String SHARE_WEB_URL = "web url";
    public static List<ShareResultObserver> observers = new ArrayList();
    private boolean isInvite;
    private TextView lastLength;
    private boolean letvStarIsLogin;
    private boolean letvStarIsShare;
    private String mFragId;
    private int mFrom;
    private Handler mHandler;
    private String mImageUrl;
    private String mPicLocalPath;
    private String mShareContent;
    private BSsoHandler mSsoHandler;
    private String mStaticsId;
    private String mTitle;
    private int maxLength;
    private int pos_cursor;
    private boolean qqIsShare;
    private boolean qzoomIsShare;
    private String shareType;
    private boolean sinaIsLogin;
    private boolean sinaIsShare;
    private boolean tencentIsShare;
    private boolean tencentQzoneIsLogin;
    private boolean tencentWeiboIsLogin;
    private TextView top_title;
    private EditText userContent;
    private String webUrl;

    private class RequestTask {
        private Context mContext;
        final /* synthetic */ SignSharePageEditActivity this$0;

        public RequestTask(SignSharePageEditActivity signSharePageEditActivity, Context context) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = signSharePageEditActivity;
            this.mContext = context;
        }

        public void start() {
            this.this$0.mHandler.post(new 1(this));
        }

        public Void doInBackground() {
            LogInfo.log("LM", TradeInfo.SIGN);
            if (this.this$0.sinaIsLogin && this.this$0.sinaIsShare) {
                RequestListener sinaListener = new 2(this);
                LogInfo.log("fornia", "signsign mTitle:" + this.this$0.mTitle);
                if (this.this$0.isInvite) {
                    LetvSinaShareSSO.shareSinaToInvite(this.this$0, this.this$0.mTitle, this.this$0.mImageUrl, this.this$0.shareType, sinaListener);
                } else {
                    LetvSinaShareSSO.share(this.this$0, this.this$0.getShareContent(this.this$0.mTitle), this.this$0.mImageUrl, -1, sinaListener);
                }
            }
            if (this.this$0.tencentWeiboIsLogin && this.this$0.tencentIsShare) {
                TWeiboListener listener = new 3(this);
                if (this.this$0.isInvite) {
                    LetvTencentWeiboShare.shareTencentToInvite(this.this$0, this.this$0.userContent.getText().toString(), this.this$0.mImageUrl, this.this$0.shareType, listener);
                } else {
                    LetvTencentWeiboShare.share(this.this$0, this.this$0.getShareContent(this.this$0.userContent.getText().toString()), this.this$0.mImageUrl, false, listener);
                }
            }
            if (this.this$0.qzoomIsShare && this.this$0.mFrom == 3) {
                if (this.this$0.isInvite) {
                    LetvQZoneShare.getInstance(this.this$0).shareQzoneToInvite(this.this$0, this.this$0.mTitle, this.this$0.userContent.getText().toString(), this.this$0.mImageUrl, this.this$0.webUrl, this.this$0.mPicLocalPath, this.this$0.shareType, this.this$0.mStaticsId, this.this$0.mFragId);
                } else {
                    LetvQZoneShare.getInstance(this.this$0).shareLiveToQzone(this.this$0, this.this$0.mTitle, this.this$0.userContent.getText().toString(), this.this$0.mImageUrl, this.this$0.webUrl, this.this$0.mPicLocalPath, this.this$0.mStaticsId, this.this$0.mFragId);
                }
            }
            if (this.this$0.qqIsShare && this.this$0.mFrom == 6) {
                letvTencentShare.getInstance(this.this$0).shareLiveToTencent(this.this$0, this.this$0.mTitle, this.this$0.userContent.getText().toString(), this.this$0.mImageUrl, this.this$0.webUrl, this.this$0.mPicLocalPath, this.this$0.mStaticsId, this.this$0.mFragId);
            }
            return null;
        }

        public void onPostExecute(Void result) {
        }
    }

    public SignSharePageEditActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.maxLength = 140;
        this.sinaIsLogin = true;
        this.tencentWeiboIsLogin = true;
        this.sinaIsShare = false;
        this.tencentIsShare = false;
        this.qzoomIsShare = false;
        this.qqIsShare = false;
        this.letvStarIsShare = false;
        this.mFrom = 0;
        this.pos_cursor = 0;
        this.isInvite = false;
        this.shareType = "";
        this.webUrl = "";
        this.mStaticsId = "";
        this.mFragId = "";
        this.mHandler = new Handler(Looper.myLooper());
    }

    public static void launch(Context activity, int from, String title, String webUrl, String staticsId, String fragId) {
        Intent intent = new Intent(activity, SignSharePageEditActivity.class);
        intent.putExtra(SHARE_FROM, from);
        intent.putExtra(SHARE_TITLE, title);
        intent.putExtra("isInvite", false);
        intent.putExtra("shareType", "");
        intent.putExtra(SHARE_WEB_URL, webUrl);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    public static void launch(Context activity, int from, String title, String imageUrl, String webUrl, String staticsId, String fragId) {
        Intent intent = new Intent(activity, SignSharePageEditActivity.class);
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

    public static void launch(Context activity, int from, String title, String desc, String imageUrl, String webUrl, String shareType, boolean isInvite, String staticsId, String fragId) {
        Intent intent = new Intent(activity, SignSharePageEditActivity.class);
        intent.putExtra(SHARE_FROM, from);
        intent.putExtra(SHARE_TITLE, title);
        intent.putExtra(SHARE_DESC, desc);
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
        this.mShareContent = intent.getStringExtra(SHARE_DESC);
        this.isInvite = intent.getBooleanExtra("isInvite", false);
        this.shareType = intent.getStringExtra("shareType");
        this.webUrl = intent.getStringExtra(SHARE_WEB_URL);
        LogInfo.log("fornia", "editact mFrom:" + this.mFrom + "mTitle" + this.mTitle + "mImageUrl" + this.mImageUrl + "isInvite" + this.isInvite + "shareType" + this.shareType + "webUrl" + this.webUrl);
        this.mStaticsId = intent.getStringExtra("staticsId");
        this.mFragId = intent.getStringExtra("fragId");
        findView();
        this.pos_cursor = this.userContent.getEditableText().length();
        new Thread(this) {
            final /* synthetic */ SignSharePageEditActivity this$0;

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
                    LogInfo.log("SignSharePageEditActivity", "local load complete! ImageFilePath = " + localDirFile.getAbsolutePath());
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
                        LogInfo.log("SignSharePageEditActivity", "download complete! ImageFilePath = " + localDirFile.getAbsolutePath());
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

    public void findView() {
        this.userContent = (EditText) findViewById(R.id.ShareText);
        this.lastLength = (TextView) findViewById(R.id.maxlength);
        this.top_title = (TextView) findViewById(R.id.top_title);
        findViewById(R.id.top_button_Share).setOnClickListener(this);
        findViewById(R.id.top_button).setOnClickListener(this);
        this.mShareContent = LetvTools.getTextTitleFromServer("80002", getResources().getString(2131100855));
        this.userContent.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ SignSharePageEditActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                String ss = s.toString();
                this.this$0.maxLength = 140 - this.this$0.counts(ss);
                LogInfo.log("LM", "maxLength  " + this.this$0.maxLength + "    COUNT " + this.this$0.counts(ss));
                if (this.this$0.maxLength < 0) {
                    this.this$0.lastLength.setTextColor(this.this$0.getResources().getColor(2131493369));
                } else {
                    this.this$0.lastLength.setTextColor(this.this$0.getResources().getColor(2131493233));
                }
                this.this$0.lastLength.setText(this.this$0.maxLength + "");
                this.this$0.pos_cursor = this.this$0.userContent.getSelectionStart();
            }
        });
        if (this.isInvite) {
            LogInfo.log("fornia", "editact mTitle:" + this.mTitle + this.webUrl);
            this.userContent.setText(this.mTitle);
            LogInfo.log("lxx", "mShareContent: " + this.mShareContent);
        } else {
            LogInfo.log("fornia", "editact mShareContent:" + this.mShareContent);
            this.userContent.setText(this.mShareContent);
        }
        this.userContent.setSelection(this.userContent.getEditableText().length());
    }

    public void setTopTitle() {
        switch (this.mFrom) {
            case 1:
                this.top_title.setText(2131100839);
                return;
            case 2:
                this.top_title.setText(2131100835);
                return;
            case 3:
                this.top_title.setText(2131100836);
                return;
            case 4:
                return;
            case 5:
                this.top_title.setText(2131100818);
                return;
            case 6:
                this.top_title.setText(2131100841);
                return;
            default:
                this.top_title.setText(2131100840);
                return;
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
        setTopTitle();
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

    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.top_button /*2131364326*/:
                Iterator it = observers.iterator();
                if (it.hasNext()) {
                    ((ShareResultObserver) it.next()).onCanneled();
                }
                UIsUtils.hideSoftkeyboard(this);
                finish();
                return;
            case R.id.top_button_Share /*2131364328*/:
                if (this.maxLength < 0) {
                    UIs.callDialogMsgPositiveButton(this, DialogMsgConstantId.SEVEN_ZERO_TWO_CONSTANT, null);
                    return;
                } else if ((!this.sinaIsLogin || !this.sinaIsShare) && ((!this.tencentWeiboIsLogin || !this.tencentIsShare) && !this.qzoomIsShare && !this.qqIsShare && (!this.letvStarIsLogin || !this.letvStarIsShare))) {
                    return;
                } else {
                    if (this.qqIsShare && this.mFrom == 6 && !TencentInstance.getInstance(this).isSupportSSOLogin(this)) {
                        UIsUtils.showToast(2131100834);
                        return;
                    } else if (this.qzoomIsShare && this.mFrom == 3 && !TencentInstance.getInstance(this).isSupportSSOLogin(this)) {
                        UIsUtils.showToast(2131100834);
                        return;
                    } else {
                        new RequestTask(this, this).start();
                        UIsUtils.hideSoftkeyboard(this);
                        return;
                    }
                }
            default:
                return;
        }
    }

    public int getContentView() {
        return R.layout.share_page;
    }

    private void sendNotifycation(int notifyId, int textId, int drawableId) {
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        Notification notification = new Notification();
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
        notification.icon = drawableId;
        notification.tickerText = ShareUtils.getString(textId);
        notification.defaults |= 1;
        notification.flags = 16;
        notification.setLatestEventInfo(this, null, null, contentIntent);
        notificationManager.notify(notifyId, notification);
        notificationManager.cancel(notifyId);
        if (LetvUtils.getBrandName().toLowerCase().contains("xiaomi")) {
            ToastUtils.showToast((Context) this, textId);
        }
    }

    public String getActivityName() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }
}
