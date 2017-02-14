package com.letv.android.client.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.utils.UIControllerUtils;
import com.letv.android.client.utils.UIs;
import com.letv.android.client.view.ExitRetainController.ExitRetainCallBack;
import com.letv.android.client.view.ExitRetainMyPopupwindow.DismissListener;
import com.letv.cache.LetvCacheMannager;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.HomeMetaData;
import com.letv.core.bean.MyFocusImageDataListBean;
import com.letv.core.config.LetvConfig;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.MyFocusImageListParser;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import java.util.HashMap;

public class ExitRetainPopupwindow {
    public static final int FIRST_DATA_KEY = 1;
    private static final int FIRST_HEIGHT = 178;
    private static final int FIRST_WIDTH = 280;
    private static final String REALSE_AD_CMS_ID1 = "2132";
    private static final String REALSE_AD_CMS_ID2 = "2133";
    private static final String REALSE_NEW_USER_CMS_ID1 = "5270";
    private static final String REALSE_NEW_USER_CMS_ID2 = "5271";
    public static final int SECOND_DATA_KEY = 2;
    private static final int SECOND_HEIGHT = 178;
    private static final int SECOND_WIDTH = 280;
    private static final String TAG = ExitRetainPopupwindow.class.getSimpleName();
    private static final String TEST_AD_CMS_ID2 = "1498";
    private static final String TEST_NEW_USER_CMS_ID1 = "5270";
    private static final String TEST_NEW_USER_CMS_ID2 = "5271";
    private String AD_CMS_ID1;
    private String AD_CMS_ID2;
    private String TEST_AD_CMS_ID1;
    private View contentView;
    private int from;
    private boolean isClickLookBtn;
    private boolean isNewUser;
    private Activity mActivity;
    private Context mContext;
    private HomeMetaData mCurrentHomeMetaData;
    private String mCurrentpageID;
    private ViewGroup mDialogView;
    private ExitRetainCallBack mExitRetainCallBack;
    ExitRetainController mExitRetainController;
    private HashMap<Integer, MyFocusImageDataListBean> mExitRetainDataMap;
    OnClickListener mExitRetainListener;
    private ViewStub mExitRetainviewStub1;
    private ViewStub mExitRetainviewStub2;
    private ViewStub mExitRetainviewStub3;
    private ViewStub mExitRetainviewStub4;
    OnClickListener mLookListener;
    private ExitRetainMyPopupwindow mPopupWindow;
    private boolean pressBackKey;
    private SharedPreferences sp;

    public interface RequestSuccessCallBack {
        void onRequestFaild();

        void onRequestOldUserCmds();

        void onRequestSuccess(MyFocusImageDataListBean myFocusImageDataListBean);
    }

    public ExitRetainPopupwindow() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.AD_CMS_ID1 = REALSE_AD_CMS_ID1;
        this.AD_CMS_ID2 = REALSE_AD_CMS_ID2;
        this.TEST_AD_CMS_ID1 = "1497";
        this.mExitRetainDataMap = new HashMap();
        this.pressBackKey = false;
        this.isClickLookBtn = false;
        this.isNewUser = true;
        this.mExitRetainListener = new OnClickListener(this) {
            final /* synthetic */ ExitRetainPopupwindow this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                if (this.this$0.mExitRetainCallBack != null) {
                    this.this$0.isClickLookBtn = false;
                    this.this$0.mPopupWindow.dismiss();
                    this.this$0.mExitRetainCallBack.onClickExitBtnReportData(this.this$0.mCurrentpageID);
                    this.this$0.mExitRetainCallBack.onExitAppliation();
                }
            }
        };
        this.mLookListener = new OnClickListener(this) {
            final /* synthetic */ ExitRetainPopupwindow this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                if (this.this$0.mCurrentHomeMetaData != null) {
                    this.this$0.isClickLookBtn = true;
                    this.this$0.mPopupWindow.dismiss();
                    LogInfo.log(ExitRetainPopupwindow.TAG, "mLookListener from : " + this.this$0.from);
                    StatisticsUtils.setActionProperty("tc01", 2, this.this$0.mCurrentpageID);
                    if (this.this$0.mActivity != null) {
                        UIControllerUtils.gotoActivity(this.this$0.mActivity, this.this$0.mCurrentHomeMetaData, 0, this.this$0.from);
                    } else {
                        UIControllerUtils.gotoActivity(this.this$0.mContext, this.this$0.mCurrentHomeMetaData, 0, this.this$0.from);
                    }
                    LogInfo.LogStatistics("ref=" + this.this$0.mCurrentpageID);
                    this.this$0.mExitRetainCallBack.onClickLookBtnReportData(this.this$0.mCurrentpageID);
                }
            }
        };
        this.mExitRetainController = new ExitRetainController(this) {
            private static final String TAG = "ExitRetainController";
            final /* synthetic */ ExitRetainPopupwindow this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void clearCacheCmsID() {
                this.this$0.sp.edit().clear().commit();
            }

            public boolean showExitRetainPopupwindow(View view) {
                if (VERSION.SDK_INT < 11 && this.this$0.mPopupWindow != null && this.this$0.mPopupWindow.isShowing()) {
                    if (!(this.this$0.mCurrentHomeMetaData == null || this.this$0.mCurrentHomeMetaData.isFristShow || this.this$0.mExitRetainCallBack == null)) {
                        LogInfo.log(TAG, "initPopupwindow222  onExitAppliation >> ");
                        this.this$0.mExitRetainCallBack.onExitAppliation();
                    }
                    this.this$0.mPopupWindow.dismiss();
                    this.this$0.mExitRetainDataMap.clear();
                    this.this$0.mExitRetainCallBack.onDismissPopWindow();
                }
                if (this.this$0.mPopupWindow == null || this.this$0.mExitRetainDataMap.size() == 0) {
                    return false;
                }
                this.this$0.getOneExitRetainData();
                LogInfo.log(TAG, "showExitRetainPopupwindow mCurrentpageID >> " + this.this$0.mCurrentpageID + " mCurrentHomeMetaData : " + this.this$0.mCurrentHomeMetaData);
                if (this.this$0.mCurrentHomeMetaData == null) {
                    return false;
                }
                this.this$0.initExitRetainPopupwindow(this.this$0.mCurrentHomeMetaData);
                this.this$0.mPopupWindow.showAtLocation(view, 17, 0, 0);
                if (VERSION.SDK_INT < 11) {
                    this.this$0.mPopupWindow.setFocusable(true);
                    this.this$0.mPopupWindow.update();
                }
                this.this$0.mExitRetainCallBack.onShowPopWindow();
                this.this$0.mExitRetainCallBack.onShowReportData(this.this$0.mCurrentpageID);
                LogInfo.log("onKeyDown", "showExitRetainPopupwindow END : ");
                this.this$0.dismissExitRetainPopupwindowByTime();
                this.this$0.setPopTouchListener();
                return true;
            }

            public void setExitRetainPopupwindowCallBack(ExitRetainCallBack exitRetainCallBack) {
                this.this$0.mExitRetainCallBack = exitRetainCallBack;
            }

            public void getExitRetainData() {
                this.this$0.getExitRetainData1(new Runnable(this) {
                    final /* synthetic */ AnonymousClass8 this$1;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$1 = this$1;
                    }

                    public void run() {
                        this.this$1.this$0.getExitRetainData2();
                    }
                });
            }

            public void dismissPopupwindow() {
                if (this.this$0.mPopupWindow != null) {
                    this.this$0.mPopupWindow.dismiss();
                    this.this$0.mExitRetainDataMap.clear();
                }
            }

            public void setCurrentPageID(String pageID) {
                this.this$0.mCurrentpageID = pageID;
                this.this$0.pageIDToFromID();
            }

            public String getCurrentPageID() {
                LogInfo.log(TAG, "getCurrentPageID : " + this.this$0.mCurrentpageID);
                return this.this$0.mCurrentpageID;
            }

            public void setActivity(Activity activity) {
                this.this$0.mActivity = activity;
            }

            public boolean isShow() {
                return this.this$0.mPopupWindow != null && this.this$0.mPopupWindow.isShowing();
            }
        };
        this.mContext = LetvApplication.getInstance();
        initBlockID();
        initPopupwindow();
        initExitReationViewStub();
        this.sp = this.mContext.getSharedPreferences("exit_retain", 0);
        initDefaultSecondData();
    }

    private void initBlockID() {
        LogInfo.log(TAG, "initBlockID isNewUser " + isNewUser());
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            if (isNewUser()) {
                this.AD_CMS_ID1 = "5270";
                this.AD_CMS_ID2 = "5271";
                return;
            }
            this.AD_CMS_ID1 = this.TEST_AD_CMS_ID1;
            this.AD_CMS_ID2 = TEST_AD_CMS_ID2;
        } else if (isNewUser()) {
            this.AD_CMS_ID1 = "5270";
            this.AD_CMS_ID2 = "5271";
        } else {
            this.AD_CMS_ID1 = REALSE_AD_CMS_ID1;
            this.AD_CMS_ID2 = REALSE_AD_CMS_ID2;
        }
    }

    private boolean isNewUser() {
        if (!LetvUtils.isNewUser()) {
            return false;
        }
        LogInfo.log(TAG, " isNewUser >> isNewUser " + this.isNewUser);
        return this.isNewUser;
    }

    private void initPopupwindow() {
        this.mPopupWindow = new ExitRetainMyPopupwindow(LetvApplication.getInstance());
        this.mPopupWindow.setWidth(UIs.getScreenWidth());
        this.mPopupWindow.setHeight(UIs.getScreenHeight());
        if (VERSION.SDK_INT >= 11) {
            this.mPopupWindow.setFocusable(true);
        }
        this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable(this.mContext.getResources()));
        this.contentView = LayoutInflater.from(this.mContext).inflate(R.layout.exit_retain_popupwindow, null);
        this.mPopupWindow.setContentView(this.contentView);
        this.mPopupWindow.setOnDismissListener(new OnDismissListener(this) {
            final /* synthetic */ ExitRetainPopupwindow this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onDismiss() {
                LogInfo.log(ExitRetainPopupwindow.TAG, " onDismiss isClickLookBtn : " + this.this$0.isClickLookBtn + " isNewUser " + this.this$0.isNewUser() + " isFristShow : " + this.this$0.mCurrentHomeMetaData.isFristShow);
                if (this.this$0.mCurrentHomeMetaData == null || this.this$0.mCurrentHomeMetaData.isFristShow || !this.this$0.isNewUser() || this.this$0.mExitRetainDataMap.size() <= 1) {
                    if (!this.this$0.isClickLookBtn || !this.this$0.isNewUser()) {
                        this.this$0.mExitRetainDataMap.clear();
                    } else if (this.this$0.isNewUser()) {
                        this.this$0.isClickLookBtn = false;
                    }
                    this.this$0.mExitRetainCallBack.onDismissPopWindow();
                    return;
                }
                LogInfo.log(" 当前对话框是不带按钮的 ");
            }
        });
        this.mPopupWindow.setDismissListener(new DismissListener(this) {
            final /* synthetic */ ExitRetainPopupwindow this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onPreDismiss() {
                LogInfo.log(ExitRetainPopupwindow.TAG, "initPopupwindow  onPreDismiss >> ");
                if (this.this$0.isClickBackKey()) {
                    this.this$0.pressBackKey = true;
                }
            }

            public void onDismissed() {
                LogInfo.log(ExitRetainPopupwindow.TAG, "initPopupwindow  onDismissed >> " + this.this$0.pressBackKey);
                if (!this.this$0.pressBackKey || this.this$0.mCurrentHomeMetaData == null || this.this$0.mCurrentHomeMetaData.isFristShow || this.this$0.mExitRetainCallBack == null) {
                    this.this$0.pressBackKey = false;
                    return;
                }
                LogInfo.log(ExitRetainPopupwindow.TAG, "initPopupwindow  onExitAppliation >> ");
                this.this$0.mExitRetainCallBack.onExitAppliation();
            }
        });
    }

    private boolean isClickBackKey() {
        View v = (View) this.mPopupWindow.getContentView().getParent();
        LogInfo.log(TAG, "isClickBackKey  v : " + v);
        if (v == null) {
            return false;
        }
        boolean res = v.getKeyDispatcherState().isTracking(new KeyEvent(0, 4));
        LogInfo.log(TAG, "isClickBackKey RES : " + res);
        return res;
    }

    private void initDefaultSecondData() {
        MyFocusImageDataListBean myFocusImageDataList = new MyFocusImageDataListBean();
        HomeMetaData homeMetaData = new HomeMetaData();
        homeMetaData.nameCn = this.mContext.getString(2131100112);
        homeMetaData.cmsid = "11111";
        homeMetaData.isFristShow = false;
        myFocusImageDataList.add(homeMetaData);
        this.mExitRetainDataMap.put(Integer.valueOf(2), myFocusImageDataList);
    }

    private void initExitReationViewStub() {
        this.mExitRetainviewStub1 = (ViewStub) this.contentView.findViewById(R.id.exit_retain_style1_id);
        this.mExitRetainviewStub2 = (ViewStub) this.contentView.findViewById(R.id.exit_retain_style2_id);
        this.mExitRetainviewStub3 = (ViewStub) this.contentView.findViewById(R.id.exit_retain_style3_id);
        this.mExitRetainviewStub4 = (ViewStub) this.contentView.findViewById(R.id.exit_retain_style4_id);
    }

    public ExitRetainController getExitRetainController() {
        return this.mExitRetainController;
    }

    private void getOneExitRetainData() {
        this.mCurrentHomeMetaData = getOneExitRetainData(1);
        if (this.mCurrentHomeMetaData == null) {
            this.mCurrentHomeMetaData = getOneExitRetainData(2);
            if (this.mCurrentHomeMetaData != null) {
                this.mCurrentHomeMetaData.isFristShow = false;
                addCachecmsid(this.mCurrentHomeMetaData.cmsid);
                return;
            }
            boolean isNewUser = isNewUser();
            LogInfo.log(TAG, " getOneExitRetainData isNewUser " + isNewUser + " mExitRetainDataMap.size() : " + this.mExitRetainDataMap.size());
            if (this.mExitRetainDataMap.size() > 0 && isNewUser) {
                this.sp.edit().clear().commit();
                getOneExitRetainData();
                return;
            }
            return;
        }
        this.mCurrentHomeMetaData.isFristShow = true;
        addCachecmsid(this.mCurrentHomeMetaData.cmsid);
    }

    private void initView1(HomeMetaData homeMetaData) {
        this.mExitRetainviewStub1.setVisibility(0);
        LayoutParams params = this.mExitRetainviewStub1.getLayoutParams();
        params.width = UIs.dipToPx(280.0f);
        params.height = UIs.dipToPx(178.0f);
        this.mExitRetainviewStub1.setLayoutParams(params);
        ((TextView) this.contentView.findViewById(R.id.exit_btn)).setOnClickListener(this.mExitRetainListener);
        ((TextView) this.contentView.findViewById(R.id.look_btn)).setOnClickListener(this.mLookListener);
        final ImageView imageView = (ImageView) this.contentView.findViewById(R.id.exit_reation_image);
        imageView.setImageResource(2130838795);
        LetvCacheMannager.getInstance().loadImage(homeMetaData.mobilePic, imageView, new ImageLoadingListener(this) {
            final /* synthetic */ ExitRetainPopupwindow this$0;

            public void onLoadingStarted(String imageUri, View view) {
            }

            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }

            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setScaleType(ScaleType.CENTER_CROP);
            }

            public void onLoadingCancelled(String imageUri, View view) {
            }
        });
    }

    private void initView2(HomeMetaData homeMetaData) {
        this.mExitRetainviewStub2.setVisibility(0);
        LayoutParams params = this.mExitRetainviewStub2.getLayoutParams();
        params.width = UIs.dipToPx(280.0f);
        params.height = -2;
        this.mExitRetainviewStub2.setLayoutParams(params);
        ((TextView) this.contentView.findViewById(R.id.exit_btn)).setOnClickListener(this.mExitRetainListener);
        ((TextView) this.contentView.findViewById(R.id.look_btn)).setOnClickListener(this.mLookListener);
        ((TextView) this.contentView.findViewById(R.id.exit_retation_tv)).setText(homeMetaData.nameCn);
    }

    private void initView3(HomeMetaData homeMetaData) {
        this.mExitRetainviewStub3.setVisibility(0);
        LayoutParams params = this.mExitRetainviewStub3.getLayoutParams();
        params.width = UIs.dipToPx(280.0f);
        params.height = -2;
        this.mExitRetainviewStub3.setLayoutParams(params);
        ((TextView) this.contentView.findViewById(R.id.exit_retation_tv)).setText(homeMetaData.nameCn);
    }

    private void initView4(HomeMetaData homeMetaData) {
        this.mExitRetainviewStub4.setVisibility(0);
        LayoutParams params = this.mExitRetainviewStub4.getLayoutParams();
        params.width = UIs.dipToPx(280.0f);
        params.height = UIs.dipToPx(178.0f);
        this.mExitRetainviewStub4.setLayoutParams(params);
        ImageView imageView = (ImageView) this.contentView.findViewById(R.id.exit_retation_image);
        imageView.setImageResource(2130838795);
        LetvCacheMannager.getInstance().loadImage(homeMetaData.mobilePic, imageView);
    }

    private void initExitRetainPopupwindow(HomeMetaData homeMetaData) {
        if (!TextUtils.isEmpty(homeMetaData.mobilePic) && homeMetaData.isFristShow) {
            this.mExitRetainviewStub3.setVisibility(8);
            this.mExitRetainviewStub4.setVisibility(8);
            this.mExitRetainviewStub2.setVisibility(8);
            initView1(homeMetaData);
        } else if (homeMetaData.isFristShow) {
            this.mExitRetainviewStub3.setVisibility(8);
            this.mExitRetainviewStub4.setVisibility(8);
            this.mExitRetainviewStub1.setVisibility(8);
            initView2(homeMetaData);
        } else if (!TextUtils.isEmpty(homeMetaData.mobilePic) && !homeMetaData.isFristShow) {
            this.mExitRetainviewStub3.setVisibility(8);
            this.mExitRetainviewStub2.setVisibility(8);
            this.mExitRetainviewStub1.setVisibility(8);
            initView4(homeMetaData);
        } else if (!homeMetaData.isFristShow) {
            this.mExitRetainviewStub4.setVisibility(8);
            this.mExitRetainviewStub2.setVisibility(8);
            this.mExitRetainviewStub1.setVisibility(8);
            initView3(homeMetaData);
        }
        try {
            this.mDialogView = (ViewGroup) this.contentView.findViewById(2131362537);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void dismissExitRetainPopupwindowByTime() {
        new Handler().postDelayed(new Runnable(this) {
            final /* synthetic */ ExitRetainPopupwindow this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                try {
                    LogInfo.log("onKeyDown", "dismissExitRetainPopupwindowByTime END : ");
                    if (this.this$0.mPopupWindow != null && this.this$0.mPopupWindow.isShowing() && this.this$0.mCurrentHomeMetaData != null && !this.this$0.mCurrentHomeMetaData.isFristShow) {
                        this.this$0.mPopupWindow.dismiss();
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }, 5000);
    }

    private HomeMetaData getOneExitRetainData(int key) {
        MyFocusImageDataListBean myFocusImageDataList = (MyFocusImageDataListBean) this.mExitRetainDataMap.get(Integer.valueOf(key));
        if (myFocusImageDataList == null) {
            LogInfo.log("getOneExitRetainData", "myFocusImageDataList == null key : " + key);
            return null;
        }
        LogInfo.log("getOneExitRetainData", "myFocusImageDataList size  : " + myFocusImageDataList.mFocusImageDataList.size() + " key : " + key);
        synchronized (myFocusImageDataList) {
            for (HomeMetaData hmData : myFocusImageDataList.mFocusImageDataList) {
                LogInfo.log(TAG, " hmData >> " + hmData.nameCn + " key : " + key);
                if (!isExistCachecmsid(hmData.cmsid)) {
                    return hmData;
                }
            }
            return null;
        }
    }

    private void setPopTouchListener() {
        if (this.contentView == null) {
            LogInfo.log(TAG, "contentView == null !!!!");
        } else {
            this.contentView.setOnTouchListener(new OnTouchListener(this) {
                final /* synthetic */ ExitRetainPopupwindow this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public boolean onTouch(View v, MotionEvent event) {
                    if (this.this$0.mDialogView != null) {
                        LogInfo.log(ExitRetainPopupwindow.TAG, "mDialogView != null");
                        Rect outRect = new Rect();
                        this.this$0.mDialogView.getHitRect(outRect);
                        if (!outRect.contains((int) event.getX(), (int) event.getY())) {
                            LogInfo.log(ExitRetainPopupwindow.TAG, "outRect.contains>>");
                            if (this.this$0.mPopupWindow != null) {
                                this.this$0.mPopupWindow.dismiss();
                            }
                        }
                    }
                    return false;
                }
            });
        }
    }

    private void pageIDToFromID() {
    }

    public void getExitRetainData1(final Runnable runnable) {
        LogInfo.log(TAG, "getExitRetainData1>>>");
        requestExitRetaionData(this.AD_CMS_ID1, new RequestSuccessCallBack(this) {
            final /* synthetic */ ExitRetainPopupwindow this$0;

            public void onRequestOldUserCmds() {
                LogInfo.log(ExitRetainPopupwindow.TAG, "getExitRetainData1 onRequestOldUserCmds >>>>");
                this.this$0.isNewUser = false;
                this.this$0.initBlockID();
                this.this$0.getExitRetainData1(new 1(this));
            }

            public void onRequestSuccess(MyFocusImageDataListBean myFocusImageDataListBean) {
                if (myFocusImageDataListBean != null) {
                    synchronized (this.this$0.mExitRetainDataMap) {
                        if (this.this$0.mExitRetainDataMap.size() != 0) {
                            this.this$0.mExitRetainDataMap.put(Integer.valueOf(1), myFocusImageDataListBean);
                        }
                    }
                }
                runnable.run();
            }

            public void onRequestFaild() {
                runnable.run();
            }
        });
    }

    public void getExitRetainData2() {
        LogInfo.log(TAG, "getExitRetainData2>>>");
        requestExitRetaionData(this.AD_CMS_ID2, new RequestSuccessCallBack(this) {
            final /* synthetic */ ExitRetainPopupwindow this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onRequestOldUserCmds() {
            }

            public void onRequestSuccess(MyFocusImageDataListBean myFocusImageDataListBean) {
                MyFocusImageDataListBean defaultFocusImageDataList = (MyFocusImageDataListBean) this.this$0.mExitRetainDataMap.get(Integer.valueOf(2));
                if (defaultFocusImageDataList != null && myFocusImageDataListBean != null && myFocusImageDataListBean.mFocusImageDataList.size() > 0 && defaultFocusImageDataList.mFocusImageDataList.size() != 0) {
                    defaultFocusImageDataList.mFocusImageDataList.clear();
                    defaultFocusImageDataList.mFocusImageDataList.addAll(myFocusImageDataListBean.mFocusImageDataList);
                }
            }

            public void onRequestFaild() {
            }
        });
    }

    private void addCachecmsid(String cmdsid) {
        if (!isExistCachecmsid(cmdsid)) {
            this.sp.edit().putBoolean(cmdsid, true).commit();
        }
    }

    private boolean isExistCachecmsid(String cmdsid) {
        LogInfo.log(TAG, "isExistCachecmsid cmdsid : " + cmdsid + " sp.getBoolean : " + this.sp.getBoolean(cmdsid, false));
        return this.sp.getBoolean(cmdsid, false);
    }

    private void requestExitRetaionData(String cmdid, final RequestSuccessCallBack requestSuccessCallBack) {
        String url = PlayRecordApi.getInstance().getMineFocusImageUrl(cmdid);
        LogInfo.log(TAG, "requestExitRetaionData cmdid : " + cmdid + " url : " + url);
        new LetvRequest(MyFocusImageDataListBean.class).setUrl(url).setCache(new VolleyNoCache()).setParser(new MyFocusImageListParser()).setCallback(new SimpleResponse<MyFocusImageDataListBean>(this) {
            final /* synthetic */ ExitRetainPopupwindow this$0;

            public void onNetworkResponse(VolleyRequest<MyFocusImageDataListBean> volleyRequest, MyFocusImageDataListBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("ZSM", "requestFocusImageTask == " + state);
                if (state == NetworkResponseState.SUCCESS) {
                    LogInfo.log(ExitRetainPopupwindow.TAG, " requestExitRetaionData onNetworkResponse isNewUser : " + this.this$0.isNewUser() + " mFocusImageDataList.size : " + result.mFocusImageDataList.size());
                    if (this.this$0.isNewUser() && result.mFocusImageDataList.size() == 0) {
                        requestSuccessCallBack.onRequestOldUserCmds();
                        return;
                    } else {
                        requestSuccessCallBack.onRequestSuccess(result);
                        return;
                    }
                }
                requestSuccessCallBack.onRequestFaild();
            }
        }).add();
    }
}
