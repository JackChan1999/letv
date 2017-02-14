package com.letv.android.client.ui.downloadpage;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.activity.LetvLoginActivity;
import com.letv.android.client.album.half.controller.AlbumHalfController;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.thirdpartlogin.HongKongLoginWebview;
import com.letv.android.client.utils.UIs;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.DownloadPageConfig;
import com.letv.core.bean.VideoBean;
import com.letv.core.bean.VideoListBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.core.utils.VideoStreamHandler;
import com.letv.download.manager.StoreManager;
import com.letv.download.manager.StoreManager.StoreDeviceInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.mobile.lebox.http.lebox.request.TaskAddHttpRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class DownloadVideoPageActivity extends WrapActivity implements OnClickListener, IDownloadPage {
    public static final int CONSTANT_FROM_DOWNLOAD = 2;
    public static final int CONSTANT_FROM_FULLPLAY = 3;
    public static final int CONSTANT_FROM_HALFPLAY = 1;
    public static final int HALF_PLAY_RESULT_CODE = 1002;
    public static final int HD_STREAM = 1;
    private static final String LOGIN_TEXT = "登录";
    public static final int LOW_STREAM = 0;
    public static final int STANDARD_STREAM = 2;
    public static final String TAG = "DownloadPage";
    public static boolean sIsFromRecom = false;
    public long aid;
    private AlbumInfo album;
    private boolean animIsShow;
    private TranslateAnimation animTopIn;
    private ImageView arrowImageView;
    private ImageView backView;
    public int curPage;
    private int currentStream;
    private String currentYear;
    private TextView down_load_videos_manage_title;
    private boolean downloadPageLoginTagIsHandCancel;
    private ProgressBar downloadProgressBar;
    private View downloadSpaceRoot;
    private TextView downloadVideosManageTxt;
    private String episode;
    private TextView highTextView;
    private boolean isCupSupport;
    private boolean isLoginSuccess;
    private boolean isVideoNormal;
    private int launchFrom;
    private View layoutView;
    private TextView lowTextView;
    private LinearLayout low_or_high_layout;
    private PublicLoadLayout mContent;
    private DownloadPageConfig mDownloadConfig;
    private SimpleResponse<VideoListBean> mEpisodeSimpleResponse;
    private IDownloadVideoFragment mIDownloadVideoFragment;
    private SimpleResponse<VideoListBean> mPeriodsSimpleResponse;
    private PopupWindow mPopupWindow;
    private View mRoot;
    private VideoListBean mVideoList;
    private VideoStreamHandler mVideoStreamHandler;
    private LinkedHashMap<String, VideoListBean> mVideosMap;
    private TextView moveGridView;
    private TextView moveListView;
    private int screenHeight;
    private int screenWidth;
    private LinearLayout selectLayout;
    private TextView standardTextView;
    private ImageView toLoginClose;
    private RelativeLayout toLoginLayout;
    private long vid;
    private TextView videosStreamSelect;
    private VolleyRequest<VideoListBean> volleyRequest;

    public DownloadVideoPageActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.curPage = 1;
        this.isCupSupport = true;
        this.isLoginSuccess = false;
        this.animIsShow = false;
        this.downloadPageLoginTagIsHandCancel = false;
        this.mVideosMap = new LinkedHashMap();
        this.screenWidth = 0;
        this.screenHeight = 0;
        this.mPeriodsSimpleResponse = new SimpleResponse<VideoListBean>(this) {
            final /* synthetic */ DownloadVideoPageActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<VideoListBean> request, VideoListBean result, DataHull hull, NetworkResponseState state) {
                super.onNetworkResponse(request, result, hull, state);
                if (state == NetworkResponseState.SUCCESS) {
                    DownloadPageConfig.initDownloadPageConfig(result, true);
                    if (result.periodHashMap != null) {
                        this.this$0.mVideosMap.putAll(result.periodHashMap);
                    }
                    VideoListBean videoListBean = (VideoListBean) this.this$0.mVideosMap.get(this.this$0.currentYear);
                    if (videoListBean != null) {
                        this.this$0.initContentStyle(videoListBean);
                    }
                    this.this$0.mContent.finish();
                } else if (state == NetworkResponseState.NETWORK_ERROR) {
                    if (this.this$0.mVideosMap.size() == 0) {
                        this.this$0.mContent.error(2131100493);
                        return;
                    }
                    this.this$0.mContent.finish();
                    UIsUtils.showToast(2131100493);
                } else if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE) {
                    if (this.this$0.mVideosMap.size() == 0) {
                        this.this$0.mContent._error(2131100495);
                        return;
                    }
                    this.this$0.mContent.finish();
                    UIsUtils.showToast(2131100495);
                } else if (state != NetworkResponseState.RESULT_ERROR) {
                } else {
                    if (this.this$0.mVideosMap.size() == 0) {
                        this.this$0.mContent.error(2131100149);
                        return;
                    }
                    this.this$0.mContent.finish();
                    UIsUtils.showToast(2131100149);
                }
            }

            public void onCacheResponse(VolleyRequest<VideoListBean> request, VideoListBean result, DataHull hull, CacheResponseState state) {
                super.onCacheResponse(request, result, hull, state);
                if (result != null && state == CacheResponseState.SUCCESS) {
                    onNetworkResponse((VolleyRequest) request, result, hull, NetworkResponseState.SUCCESS);
                }
            }
        };
        this.mEpisodeSimpleResponse = new SimpleResponse<VideoListBean>(this) {
            final /* synthetic */ DownloadVideoPageActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onCacheResponse(VolleyRequest<VideoListBean> request, VideoListBean result, DataHull hull, CacheResponseState state) {
                super.onCacheResponse(request, result, hull, state);
                LogInfo.log(DownloadVideoPageActivity.TAG, "initData onCacheResponse >> state " + state);
                if (result != null && state == CacheResponseState.SUCCESS) {
                    onNetworkResponse((VolleyRequest) request, result, hull, NetworkResponseState.SUCCESS);
                }
            }

            public void onNetworkResponse(VolleyRequest<VideoListBean> request, VideoListBean result, DataHull hull, NetworkResponseState state) {
                super.onNetworkResponse(request, result, hull, state);
                if (this.this$0.isFinishing()) {
                    LogInfo.log(DownloadVideoPageActivity.TAG, "initContentStyle initContentStyle Finishing");
                } else if (state == NetworkResponseState.SUCCESS) {
                    if (result.style != 3) {
                        this.this$0.curPage = result.currPage + 1;
                        if (result.size() == 0) {
                            this.this$0.mContent.error(2131100149);
                            return;
                        } else if (this.this$0.mVideosMap.get(String.valueOf(this.this$0.curPage)) == null) {
                            this.this$0.mVideosMap.put(String.valueOf(this.this$0.curPage), result);
                        }
                    } else if (result.periodHashMap != null) {
                        this.this$0.mVideosMap.putAll(result.periodHashMap);
                    }
                    DownloadPageConfig.initDownloadPageConfig(result, true);
                    this.this$0.initContentStyle(result);
                    this.this$0.mContent.finish();
                } else if (state == NetworkResponseState.NETWORK_ERROR) {
                    if (this.this$0.mVideosMap.size() == 0) {
                        this.this$0.mContent.error(2131100493);
                        return;
                    }
                    this.this$0.mContent.finish();
                    UIsUtils.showToast(2131100493);
                } else if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE) {
                    if (this.this$0.mVideosMap.size() == 0) {
                        this.this$0.mContent._error(2131100495);
                        return;
                    }
                    this.this$0.mContent.finish();
                    UIsUtils.showToast(2131100495);
                } else if (state != NetworkResponseState.RESULT_ERROR) {
                } else {
                    if (this.this$0.mVideosMap.size() == 0) {
                        this.this$0.mContent.error(2131100149);
                        return;
                    }
                    this.this$0.mContent.finish();
                    UIsUtils.showToast(2131100149);
                }
            }
        };
    }

    public String getActivityName() {
        return "DownloadVideoPageActivity";
    }

    public Activity getActivity() {
        return this;
    }

    public Map<String, VideoListBean> getVideoMap() {
        return this.mVideosMap;
    }

    public void setCurPage(int page) {
        LogInfo.log("xx", " setCurPage : " + page);
        this.curPage = page;
    }

    public int getCurPage() {
        return this.curPage;
    }

    public AlbumInfo getAlbumNew() {
        return this.album;
    }

    public VideoStreamHandler getVideoStreamHandler() {
        return this.mVideoStreamHandler;
    }

    public static void launch(Activity activity, long aid, int page, AlbumInfo albumInfo, long vid, boolean isVideoNormal, int... flag) {
        if (albumInfo != null && albumInfo.pid == 0) {
            albumInfo.pid = aid;
        }
        Intent intent = new Intent(activity, DownloadVideoPageActivity.class);
        intent.putExtra(MyDownloadActivityConfig.PAGE, page);
        intent.putExtra("aid", aid);
        intent.putExtra("vid", vid);
        intent.putExtra("from", 1);
        intent.putExtra("albumInfo", albumInfo);
        intent.putExtra("isVideoNormal", isVideoNormal);
        if (flag != null && flag.length > 0) {
            intent.putExtra(TaskAddHttpRequest.stream, flag[0]);
        }
        if (flag != null && flag.length > 0) {
            LogInfo.log(TAG, "launch flag : " + flag[0]);
            intent.setFlags(flag[0]);
        }
        activity.startActivityForResult(intent, 1002);
    }

    public static void launch(Activity activity, long aid, int page, AlbumInfo albumInfo, long vid, boolean isVideoNormal, String episode) {
        if (albumInfo != null && albumInfo.pid == 0) {
            albumInfo.pid = aid;
        }
        Intent intent = new Intent(activity, DownloadVideoPageActivity.class);
        intent.putExtra(MyDownloadActivityConfig.PAGE, page);
        intent.putExtra("aid", aid);
        intent.putExtra("vid", vid);
        intent.putExtra("from", 1);
        intent.putExtra("albumInfo", albumInfo);
        intent.putExtra("isVideoNormal", isVideoNormal);
        intent.putExtra("episode", episode);
        activity.startActivityForResult(intent, 1002);
    }

    public static void launch(Activity activity, long aid, int page, int from) {
        LetvApplication.getInstance().setmVideoList(null);
        Intent intent = new Intent(activity, DownloadVideoPageActivity.class);
        intent.putExtra(MyDownloadActivityConfig.PAGE, page);
        intent.putExtra("aid", aid);
        intent.putExtra("from", from);
        activity.startActivityForResult(intent, 1002);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (!this.isLoginSuccess) {
            setResult(1002);
        }
        this.isLoginSuccess = false;
        finish();
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LetvApplication.getInstance().setFromHalf(false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.screenWidth = UIsUtils.getScreenWidth();
        this.screenHeight = UIsUtils.getScreenHeight();
        this.mRoot = LayoutInflater.from(this.mContext).inflate(R.layout.download_videos_manage_layout, null);
        setContentView(this.mRoot);
        this.mDownloadConfig = DownloadPageConfig.sConfig;
        readIntent();
        initDownloadStream();
        initView();
        initData();
    }

    public void requestEpisodeVideolist(int curPage, SimpleResponse simpleResponse) {
        requestEpisodeVideolist(this.aid, curPage, 50, simpleResponse);
    }

    public void requestPeriodsVideolist(String year, SimpleResponse simpleResponse) {
        requestPeriodsVideolist(this.aid, year, simpleResponse);
    }

    private void requestEpisodeVideolist(long pid, int curPage, int pageSize, SimpleResponse simpleResponse) {
        String url = MediaAssetApi.getInstance().getEpisodeVListUrl(String.valueOf(pid), String.valueOf(curPage), String.valueOf(pageSize));
        LogInfo.log(TAG, "requestEpisodeVideolist URL : " + url);
        this.volleyRequest = new LetvRequest(VideoListBean.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setUrl(url).setParser(new DownloadPagePeriodsParser()).setTag(AlbumHalfController.REQUEST_EPISODE_EVIDEO_LIST_TAG).setCallback(simpleResponse).add();
    }

    public VolleyRequest<VideoListBean> requestPeriodsVideolist(long pid, String year, SimpleResponse simpleResponse) {
        return new LetvRequest(VideoListBean.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setUrl(MediaAssetApi.getInstance().getPeriodsVListUrl(String.valueOf(pid), year, "")).setParser(new DownloadPagePeriodsParser()).setTag(AlbumHalfController.REQUEST_PERIODS_EVIDEO_LIST_TAG).setCallback(simpleResponse).add();
    }

    private void initContentStyle(VideoListBean videoListBean) {
        if (videoListBean == null) {
            return;
        }
        if (isFinishing()) {
            LogInfo.log(TAG, "initContent activity Finish !!! ");
            return;
        }
        this.mIDownloadVideoFragment = (IDownloadVideoFragment) Fragment.instantiate(this.mContext, DownloadVideoViewPagerFragment.class.getName());
        getSupportFragmentManager().beginTransaction().replace(2131362522, (DownloadVideoViewPagerFragment) this.mIDownloadVideoFragment).commitAllowingStateLoss();
    }

    private void initData() {
        sIsFromRecom = false;
        LogInfo.log(TAG, "initData episode : " + this.episode + " isVideoNormal : " + this.isVideoNormal);
        if (LetvApplication.getInstance().getVideoListPlayerLibs() != null) {
            this.mVideoList = LetvApplication.getInstance().getVideoListPlayerLibs();
            this.mVideosMap.put(String.valueOf(this.curPage), this.mVideoList);
            this.mVideoList.style = 2;
            DownloadPageConfig.initDownloadPageConfig(this.mVideoList, false);
            initContentStyle(this.mVideoList);
            sIsFromRecom = true;
        } else if (!TextUtils.isEmpty(this.episode) && this.episode.length() > 1) {
            this.currentYear = getYear(this.episode);
            LogInfo.log(TAG, "initData currentYear : " + this.currentYear + " episode : " + this.episode);
            if (this.mVideosMap.get(this.currentYear) == null) {
                if (this.mContent != null) {
                    this.mContent.loading(false);
                }
                requestPeriodsVideolist(this.currentYear, this.mPeriodsSimpleResponse);
            }
        } else if (this.mVideosMap.get(Integer.valueOf(this.curPage)) == null) {
            if (this.mContent != null) {
                this.mContent.loading(false);
            }
            requestEpisodeVideolist(this.aid, this.curPage, 50, this.mEpisodeSimpleResponse);
        }
    }

    public String getYear(String time) {
        Calendar c = formatTime(time);
        String year = "";
        if (c != null) {
            year = String.valueOf(c.get(1));
        }
        LogInfo.log(TAG, "getYear year : " + year);
        return year;
    }

    public String getMonth(String time) {
        Calendar c = formatTime(time);
        String month = "";
        if (c != null) {
            month = String.valueOf(c.get(2) + 1);
        }
        LogInfo.log(TAG, "month month : " + month);
        return month;
    }

    public Calendar formatTime(String time) {
        LogInfo.log(TAG, "formatTime time : " + time);
        try {
            Date date = new SimpleDateFormat("yyyyMMdd").parse(time);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SpannableString highlightLoginText(String inputString) {
        SpannableString spanString = new SpannableString(inputString);
        if (!TextUtils.isEmpty(inputString)) {
            int beginPos = inputString.indexOf(LOGIN_TEXT);
            if (beginPos > -1) {
                spanString.setSpan(new ForegroundColorSpan(getResources().getColor(2131493195)), beginPos, beginPos + 2, 33);
            }
        }
        return spanString;
    }

    private void initView() {
        initStreamListPopWindow();
        this.backView = (ImageView) findViewById(2131362035);
        this.backView.setOnClickListener(this);
        this.down_load_videos_manage_title = (TextView) findViewById(2131362515);
        this.down_load_videos_manage_title.setOnClickListener(this);
        this.toLoginLayout = (RelativeLayout) findViewById(2131362516);
        this.toLoginLayout.setOnClickListener(this);
        this.toLoginClose = (ImageView) findViewById(2131362518);
        this.toLoginClose.setOnClickListener(this);
        ((TextView) this.toLoginLayout.findViewById(2131362517)).setText(highlightLoginText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_20025, 2131100046)));
        this.downloadVideosManageTxt = (TextView) findViewById(2131362520);
        this.downloadVideosManageTxt.setOnClickListener(this);
        this.downloadSpaceRoot = findViewById(2131362519);
        if (this.launchFrom == 2) {
            this.downloadSpaceRoot.setEnabled(false);
            this.downloadVideosManageTxt.setEnabled(false);
            UIs.zoomViewHeight(0, this.downloadSpaceRoot);
        } else {
            UIs.zoomViewHeight(36, this.downloadSpaceRoot);
        }
        this.downloadProgressBar = (ProgressBar) findViewById(2131361940);
        this.moveGridView = (TextView) findViewById(2131362523);
        this.moveListView = (TextView) findViewById(2131362524);
        UIs.zoomView(53, 53, this.moveGridView);
        UIs.zoomView(UIsUtils.getScreenWidth(), 53, this.moveListView);
        this.mContent = (PublicLoadLayout) findViewById(2131362522);
        this.mContent.setRefreshData(new RefreshData(this) {
            final /* synthetic */ DownloadVideoPageActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                LogInfo.log(DownloadVideoPageActivity.TAG, " refreshData initData ");
                this.this$0.initData();
            }
        });
    }

    public TextView getMoveGridView() {
        return this.moveGridView;
    }

    public TextView getMoveListView() {
        return this.moveListView;
    }

    private void initStreamListPopWindow() {
        this.videosStreamSelect = (TextView) findViewById(2131362513);
        this.arrowImageView = (ImageView) findViewById(2131362514);
        this.selectLayout = (LinearLayout) findViewById(2131362512);
        this.selectLayout.setOnClickListener(this);
        this.layoutView = View.inflate(this.mContext, R.layout.download_videos_manage_stream_list, null);
        this.low_or_high_layout = (LinearLayout) this.layoutView.findViewById(2131362525);
        this.animTopIn = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.5f, 1, 0.0f);
        this.animTopIn.setDuration(300);
        this.layoutView.setAnimation(this.animTopIn);
        this.mPopupWindow = new PopupWindow(this.layoutView, -2, -2);
        this.mPopupWindow.setFocusable(false);
        this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        this.highTextView = (TextView) this.layoutView.findViewById(2131362526);
        this.lowTextView = (TextView) this.layoutView.findViewById(2131362528);
        this.standardTextView = (TextView) this.layoutView.findViewById(2131362527);
        this.standardTextView.setText(TipUtils.getTipTitle("100037", getString(2131100892)));
        this.highTextView.setText(TipUtils.getTipTitle("100038", getString(2131100888)));
        this.lowTextView.setText(TipUtils.getTipTitle("100036", getString(2131100891)));
        if (VideoStreamHandler.specificPhone()) {
            this.mVideoStreamHandler.setCurrentStream(0);
            this.highTextView.setVisibility(8);
            this.standardTextView.setVisibility(8);
            this.lowTextView.setOnClickListener(this);
        }
        new Thread(new Runnable(this) {
            final /* synthetic */ DownloadVideoPageActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                boolean isSupportHD = this.this$0.mVideoStreamHandler.isCPUSupportHD();
                LogInfo.log(DownloadVideoPageActivity.TAG, "initStreamListPopWindow isSupportHD : " + isSupportHD);
                new Handler(this.this$0.getMainLooper()).post(new 1(this, isSupportHD));
            }
        }).start();
        setStreamSelect();
    }

    private void setStreamSelect() {
        int currentStream = this.mVideoStreamHandler.getCurrentStream();
        Resources res = getResources();
        if (this.isCupSupport) {
            this.standardTextView.setTextColor(res.getColor(2131493116));
            this.standardTextView.setBackgroundResource(2131493377);
            this.highTextView.setTextColor(res.getColor(2131493116));
            this.highTextView.setBackgroundResource(2130837953);
        } else {
            this.standardTextView.setTextColor(res.getColor(2131493116));
            this.standardTextView.setBackgroundResource(2130837953);
        }
        this.lowTextView.setTextColor(res.getColor(2131493116));
        this.lowTextView.setBackgroundResource(2130837933);
        switch (currentStream) {
            case 0:
                this.lowTextView.setTextColor(res.getColor(2131493377));
                this.lowTextView.setBackgroundResource(2131493116);
                this.videosStreamSelect.setText(TipUtils.getTipTitle("100036", getString(2131100891)));
                break;
            case 1:
                this.highTextView.setTextColor(res.getColor(2131493377));
                this.highTextView.setBackgroundResource(2131493116);
                this.videosStreamSelect.setText(TipUtils.getTipTitle("100038", getString(2131100888)));
                break;
            case 2:
                this.standardTextView.setTextColor(res.getColor(2131493377));
                this.standardTextView.setBackgroundResource(2131493116);
                this.videosStreamSelect.setText(TipUtils.getTipTitle("100037", getString(2131100892)));
                break;
        }
        dismissStreamPopupWindow();
    }

    protected void onResume() {
        super.onResume();
        updateSdcardSpace();
        if (PreferencesManager.getInstance().isLogin() || this.downloadPageLoginTagIsHandCancel) {
            this.toLoginLayout.setVisibility(8);
            return;
        }
        this.toLoginLayout.setVisibility(0);
        StatisticsUtils.staticticsInfoPost(this.mContext, "19", "a54", null, -1, null, null, null, null, null);
    }

    public void updateSdcardSpace() {
        StoreDeviceInfo storeDeviceInfo = StoreManager.getDefaultDownloadDeviceInfo();
        LogInfo.log(TAG, "updateSdcardSpace storeDeviceInfo >> " + storeDeviceInfo);
        if (storeDeviceInfo != null && storeDeviceInfo.mIsMount) {
            long availableSize = storeDeviceInfo.mAvailable;
            long totalSize = storeDeviceInfo.mTotalSpace;
            if (!TextUtils.isEmpty(storeDeviceInfo.mPath) && !((Activity) this.mContext).isFinishing() && !this.mContext.isRestricted()) {
                if (Environment.getExternalStorageState().equals("mounted")) {
                    this.downloadVideosManageTxt.setText(getString(2131100074, new Object[]{LetvUtils.getGBNumber(availableSize, 1)}));
                } else {
                    this.downloadVideosManageTxt.setText(2131100075);
                }
                int progress = 0;
                if (totalSize != 0) {
                    progress = (int) (100.0f - ((((float) availableSize) / ((float) totalSize)) * 100.0f));
                }
                this.downloadProgressBar.setProgress(progress);
            }
        }
    }

    public void dismissStreamPopupWindow() {
        if (this.mPopupWindow != null && this.mPopupWindow.isShowing()) {
            this.mPopupWindow.dismiss();
        }
        if (this.arrowImageView != null) {
            this.arrowImageView.setImageResource(2130837928);
        }
    }

    private void initDownloadStream() {
        this.mVideoStreamHandler = new VideoStreamHandler(this.currentStream, true);
        this.mVideoStreamHandler.setCurrentStream(PreferencesManager.getInstance().getCurrentDownloadStream());
    }

    private void readIntent() {
        Intent intent = getIntent();
        this.curPage = intent.getIntExtra(MyDownloadActivityConfig.PAGE, 1);
        this.aid = intent.getLongExtra("aid", 0);
        this.vid = intent.getLongExtra("vid", -1);
        this.launchFrom = intent.getIntExtra("from", 1);
        this.isVideoNormal = intent.getBooleanExtra("isVideoNormal", true);
        this.album = (AlbumInfo) intent.getSerializableExtra("albumInfo");
        this.currentStream = intent.getIntExtra(TaskAddHttpRequest.stream, 1);
        this.episode = intent.getStringExtra("episode");
    }

    public void controlStreamPop(View v) {
        if (this.mPopupWindow.isShowing()) {
            dismissStreamPopupWindow();
        } else {
            showStreamPopupWindow(v);
        }
    }

    private void showStreamPopWindowForView(View view) {
        this.animTopIn = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.5f, 1, 0.0f);
        this.animTopIn.setDuration(300);
        this.layoutView.setAnimation(this.animTopIn);
        int[] arrayOfInf = new int[2];
        view.getLocationInWindow(arrayOfInf);
        int x = arrayOfInf[0];
        int y = arrayOfInf[1] + ((int) UIs.dipToPxFloat(45));
        this.mPopupWindow.setAnimationStyle(2131230951);
        this.mPopupWindow.showAtLocation(view, 51, x - 30, y - 40);
    }

    private void showStreamPopupWindow(View v) {
        if (!(this.mPopupWindow == null || this.mPopupWindow.isShowing())) {
            showStreamPopWindowForView(v);
        }
        if (this.arrowImageView != null) {
            this.arrowImageView.setImageResource(2130839062);
        }
    }

    private void setStreamItemWidth() {
        if (this.selectLayout != null) {
            int width = (int) UIs.dipToPxFloat(70);
            LayoutParams lp = this.low_or_high_layout.getLayoutParams();
            lp.width = width;
            this.low_or_high_layout.setLayoutParams(lp);
            LogInfo.log("", "setStreamItemWidth width : " + width);
        }
    }

    private void setStreamSelectItem() {
        setStreamSelect();
        if (this.mIDownloadVideoFragment != null) {
            this.mIDownloadVideoFragment.notifyAdapter();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case 2131362035:
            case 2131362515:
                if (!this.isLoginSuccess) {
                    setResult(1002);
                }
                this.isLoginSuccess = false;
                finish();
                return;
            case 2131362512:
                controlStreamPop(v);
                setStreamItemWidth();
                return;
            case 2131362516:
                if (LetvUtils.isInHongKong()) {
                    HongKongLoginWebview.launch((Activity) this.mContext);
                } else {
                    Intent intent = new Intent(this.mContext, LetvLoginActivity.class);
                    intent.setFlags(268435456);
                    this.mContext.startActivity(intent);
                }
                StatisticsUtils.staticticsInfoPost(this.mContext, "a54", null, 0, -1, null, null, null, null, null);
                return;
            case 2131362518:
                this.toLoginLayout.setVisibility(8);
                this.downloadPageLoginTagIsHandCancel = true;
                StatisticsUtils.staticticsInfoPost(this.mContext, "a54", null, 1, -1, null, null, null, null, null);
                return;
            case 2131362520:
                if (!LetvUtils.isInHongKong()) {
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MyDownloadActivityConfig(this).create(1)));
                    StatisticsUtils.staticticsInfoPost(this.mContext, "a54", null, 0, -1, null, null, null, null, null);
                    return;
                }
                return;
            case 2131362526:
                this.mVideoStreamHandler.setCurrentStream(1);
                setStreamSelectItem();
                if (this.album != null) {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "a53", "高清", 1, -1, this.album.cid + "", this.aid + "", null, null, null);
                    return;
                }
                return;
            case 2131362527:
                this.mVideoStreamHandler.setCurrentStream(2);
                setStreamSelectItem();
                if (this.album != null) {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "a53", "标清", 3, -1, this.album.cid + "", this.aid + "", null, null, null);
                    return;
                }
                return;
            case 2131362528:
                this.mVideoStreamHandler.setCurrentStream(0);
                setStreamSelectItem();
                if (this.album != null) {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "a53", "流畅", 3, -1, this.album.cid + "", this.aid + "", null, null, null);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public DownloadPageConfig getDownloadPageConfig() {
        return DownloadPageConfig.sConfig;
    }

    public long getCurrentPlayVid() {
        return this.vid;
    }

    public boolean getIsVideoNormal() {
        return this.isVideoNormal;
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.volleyRequest != null) {
            this.volleyRequest.cancel();
        }
        LetvApplication.getInstance().setmVideoList(null);
    }

    public void startDownLoadinitAnimation(TextView imageView, float X, float Y, VideoBean video, int position) {
    }
}
