package com.letv.mobile.lebox.ui.album;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.bean.CommonResponse;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.http.lebox.bean.StorageGetInfoBean;
import com.letv.mobile.lebox.http.lebox.request.StorageGetInfoHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.TaskAddHttpRequest;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import com.letv.mobile.lebox.ui.WrapActivity;
import com.letv.mobile.lebox.ui.album.IDownloadPage.DownloadPageConfig;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.Util;
import com.letv.mobile.lebox.view.PublicLoadLayout;
import com.letv.mobile.letvhttplib.api.LetvRequest;
import com.letv.mobile.letvhttplib.bean.DataHull;
import com.letv.mobile.letvhttplib.bean.VideoBean;
import com.letv.mobile.letvhttplib.bean.VideoListBean;
import com.letv.mobile.letvhttplib.db.PreferencesManager;
import com.letv.mobile.letvhttplib.parser.OldVideoListParser;
import com.letv.mobile.letvhttplib.volley.VolleyRequest;
import com.letv.mobile.letvhttplib.volley.VolleyResponse.NetworkResponseState;
import com.letv.mobile.letvhttplib.volley.listener.OnEntryResponse;
import com.letv.mobile.letvhttplib.volley.toolbox.SimpleResponse;
import com.letv.mobile.letvhttplib.volley.toolbox.VolleyNoCache;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DownloadVideoAlbumPageActivity extends WrapActivity implements OnClickListener, IDownloadPage {
    public static final int CONSTANT_FROM_DOWNLOAD = 2;
    public static final int CONSTANT_FROM_FULLPLAY = 3;
    public static final int CONSTANT_FROM_HALFPLAY = 1;
    public static final int HALF_PLAY_RESULT_CODE = 1002;
    public static final int HD_STREAM = 1;
    private static final String LOGIN_TEXT = "登录";
    public static final int LOW_STREAM = 0;
    public static final int STANDARD_STREAM = 2;
    private static final String TAG = DownloadVideoAlbumPageActivity.class.getSimpleName();
    public long aid;
    private AlbumInfo album;
    private final boolean animIsShow = false;
    private TranslateAnimation animTopIn;
    private ImageView arrowImageView;
    private ImageView backView;
    public int curPage;
    private int currentStream;
    private TextView down_load_videos_manage_title;
    private boolean downloadPageLoginTagIsHandCancel = false;
    private ProgressBar downloadProgressBar;
    private View downloadSpaceRoot;
    private TextView downloadVideosManageTxt;
    private String episode;
    private TextView highTextView;
    private boolean isCupSupport = true;
    private boolean isLoginSuccess = false;
    private boolean isVideoNormal;
    private int launchFrom;
    private View layoutView;
    private TextView lowTextView;
    private LinearLayout low_or_high_layout;
    private PublicLoadLayout mContent;
    private DownloadPageConfig mDownloadConfig;
    private IDownloadVideoFragment mIDownloadVideoFragment;
    private PopupWindow mPopupWindow;
    private View mRoot;
    private VideoListBean mVideoList;
    private VideoStreamHandler mVideoStreamHandler;
    private final Map<Integer, VideoListBean> mVideosMap = Collections.synchronizedMap(new HashMap());
    private TextView moveGridView;
    private TextView moveListView;
    private int screenHeight = 0;
    private int screenWidth = 0;
    private LinearLayout selectLayout;
    private TextView standardTextView;
    private ImageView toLoginClose;
    private RelativeLayout toLoginLayout;
    private long vid;
    private TextView videosStreamSelect;

    public String getActivityName() {
        return TAG;
    }

    public Activity getActivity() {
        return this;
    }

    public Map<Integer, VideoListBean> getVideoMap() {
        return this.mVideosMap;
    }

    public void setCurPage(int page) {
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
        Intent intent = new Intent(activity, DownloadVideoAlbumPageActivity.class);
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
            Logger.d(TAG, "launch flag : " + flag[0]);
            intent.setFlags(flag[0]);
        }
        activity.startActivityForResult(intent, 1002);
    }

    public static void launch(Activity activity, long aid, int page, AlbumInfo albumInfo, long vid, boolean isVideoNormal, String episode) {
        if (albumInfo != null && albumInfo.pid == 0) {
            albumInfo.pid = aid;
        }
        Intent intent = new Intent(activity, DownloadVideoAlbumPageActivity.class);
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
        Intent intent = new Intent(activity, DownloadVideoAlbumPageActivity.class);
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
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.screenWidth = Util.getScreenWidth();
        this.screenHeight = Util.getScreenHeight();
        this.mRoot = LayoutInflater.from(this).inflate(R.layout.lebox_download_videos_manage_layout, null);
        setContentView(this.mRoot);
        this.mDownloadConfig = DownloadPageConfig.sConfig;
        readIntent();
        initDownloadStream();
        initView();
        initPage();
    }

    public void requestAlbumTask(Context context, long aid, OnEntryResponse<AlbumInfo> onEntryResponse) {
        String url = LetvUrlMaker.getAlbumVideoInfoUrl(String.valueOf(aid));
        Logger.d(TAG, "requestAlbumTask url : " + url);
        new LetvRequest(AlbumInfo.class).setUrl(url).setParser(new AlbumInfoParser()).setCache(new VolleyNoCache()).setCallback(onEntryResponse).add();
    }

    public void requestVideoTask(Context context, long aid, int page, OnEntryResponse<VideoListBean> onEntryResponse) {
        String url = LetvUrlMaker.getVideolistUrl(String.valueOf(aid), "0", String.valueOf(page), String.valueOf(50), String.valueOf(this.mDownloadConfig.order), String.valueOf(this.mDownloadConfig.merge));
        Logger.d(TAG, "requestVideoTask url : " + url + " page " + page);
        new LetvRequest(VideoListBean.class).setUrl(url).setParser(new OldVideoListParser(page)).setCallback(onEntryResponse).add();
    }

    private void initPage() {
        this.mContent = (PublicLoadLayout) findViewById(R.id.download_page_content);
        this.mContent.loading(false);
        if (this.launchFrom == 2 || this.album == null) {
            requestAlbumTask(this, this.aid, new SimpleResponse<AlbumInfo>() {
                public void onNetworkResponse(VolleyRequest<AlbumInfo> request, AlbumInfo result, DataHull hull, NetworkResponseState state) {
                    super.onNetworkResponse(request, result, hull, state);
                    Logger.d(DownloadVideoAlbumPageActivity.TAG, "-----" + DownloadVideoAlbumPageActivity.this.aid + "---state=" + state + "--hull=" + hull + "--result=" + result + "--request=" + request);
                    if (DownloadVideoAlbumPageActivity.this.isFinishing()) {
                        Logger.d(DownloadVideoAlbumPageActivity.TAG, "initPage DownloadVideoPageActivity Finishing");
                    } else if (state == NetworkResponseState.SUCCESS) {
                        if (result != null) {
                            DownloadVideoAlbumPageActivity.this.mContent.finish();
                            DownloadVideoAlbumPageActivity.this.album = result;
                            DownloadPageConfig.initDownloadPageConfig(result, DownloadVideoAlbumPageActivity.this.isVideoNormal, DownloadVideoAlbumPageActivity.this.mVideosMap.size() == 0);
                            try {
                                DownloadVideoAlbumPageActivity.this.initContent();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (state == NetworkResponseState.NETWORK_ERROR) {
                        if (DownloadVideoAlbumPageActivity.this.mVideosMap.size() == 0) {
                            DownloadVideoAlbumPageActivity.this.mContent.error(R.string.net_error);
                            return;
                        }
                        DownloadVideoAlbumPageActivity.this.mContent.finish();
                        Util.showToast(R.string.net_error);
                    } else if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE) {
                        if (DownloadVideoAlbumPageActivity.this.mVideosMap.size() == 0) {
                            DownloadVideoAlbumPageActivity.this.mContent._error(R.string.net_no);
                            return;
                        }
                        DownloadVideoAlbumPageActivity.this.mContent.finish();
                        Util.showToast(R.string.net_no);
                    } else if (state != NetworkResponseState.RESULT_ERROR) {
                    } else {
                        if (DownloadVideoAlbumPageActivity.this.mVideosMap.size() == 0) {
                            DownloadVideoAlbumPageActivity.this.mContent.error(R.string.get_data_error);
                            return;
                        }
                        DownloadVideoAlbumPageActivity.this.mContent.finish();
                        Util.showToast(R.string.get_data_error);
                    }
                }
            });
        } else if (this.album != null) {
            boolean z;
            Logger.d(TAG, "initPage mVideoList " + this.mVideoList + " album : " + this.album);
            AlbumInfo albumInfo = this.album;
            boolean z2 = this.isVideoNormal;
            if (this.mVideosMap.size() == 0) {
                z = true;
            } else {
                z = false;
            }
            DownloadPageConfig.initDownloadPageConfig(albumInfo, z2, z);
            initContent();
        }
    }

    private String getShowEpisode() {
        String episode = null;
        if (this.mVideoList != null && this.mVideoList.size() > 0) {
            Iterator it = this.mVideoList.iterator();
            while (it.hasNext()) {
                VideoBean v = (VideoBean) it.next();
                if (v != null && this.vid == v.vid) {
                    episode = v.episode;
                    Logger.d(TAG, "episode >> : " + episode);
                }
            }
        }
        return episode;
    }

    private void initContent() {
        if (isFinishing()) {
            Logger.d(TAG, "initContent activity Finish !!! ");
            return;
        }
        int currentStyple = this.mDownloadConfig.mCurrentStyple;
        Logger.d(TAG, "---mVideoList=" + this.mVideoList);
        if (!(this.mVideoList == null || this.mVideoList.size() <= 0 || this.launchFrom == 2)) {
            this.mVideosMap.put(Integer.valueOf(this.curPage), this.mVideoList);
        }
        Logger.d(TAG, "---initContent------currentStyple=" + currentStyple);
        switch (currentStyple) {
            case 1:
                this.mIDownloadVideoFragment = (IDownloadVideoFragment) Fragment.instantiate(this, DownloadVideoListFragment.class.getName());
                getSupportFragmentManager().beginTransaction().replace(R.id.download_page_content, (DownloadVideoListFragment) this.mIDownloadVideoFragment).commitAllowingStateLoss();
                return;
            case 2:
                this.mIDownloadVideoFragment = (IDownloadVideoFragment) Fragment.instantiate(this, DownloadVideoGridFragment.class.getName());
                getSupportFragmentManager().beginTransaction().replace(R.id.download_page_content, (DownloadVideoGridFragment) this.mIDownloadVideoFragment).commitAllowingStateLoss();
                return;
            case 3:
                this.mIDownloadVideoFragment = (IDownloadVideoFragment) Fragment.instantiate(this, DownloadVideoExpandFragment.class.getName());
                DownloadVideoExpandFragment dExpandFragment = this.mIDownloadVideoFragment;
                Bundle bundle = new Bundle();
                bundle.putBoolean("isPositive", this.isVideoNormal);
                bundle.putString("episode", this.episode);
                bundle.putString("aid", String.valueOf(this.aid));
                bundle.putString("vid", String.valueOf(this.vid));
                dExpandFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.download_page_content, dExpandFragment).commitAllowingStateLoss();
                return;
            default:
                return;
        }
    }

    public SpannableString highlightLoginText(String inputString) {
        SpannableString spanString = new SpannableString(inputString);
        if (!TextUtils.isEmpty(inputString)) {
            int beginPos = inputString.indexOf(LOGIN_TEXT);
            if (beginPos > -1) {
                spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.letv_color_ee7a24)), beginPos, beginPos + 2, 33);
            }
        }
        return spanString;
    }

    private void initView() {
        initStreamListPopWindow();
        this.backView = (ImageView) findViewById(R.id.back_iv);
        this.backView.setOnClickListener(this);
        this.down_load_videos_manage_title = (TextView) findViewById(R.id.down_load_videos_manage_title);
        this.down_load_videos_manage_title.setOnClickListener(this);
        this.toLoginLayout = (RelativeLayout) findViewById(R.id.to_login_layout);
        this.toLoginLayout.setOnClickListener(this);
        this.toLoginClose = (ImageView) findViewById(R.id.to_login_close);
        this.toLoginClose.setOnClickListener(this);
        TextView toLoginDetailTag = (TextView) this.toLoginLayout.findViewById(R.id.to_login_tag);
        this.downloadVideosManageTxt = (TextView) findViewById(R.id.textv_available_capacity);
        this.downloadVideosManageTxt.setOnClickListener(this);
        this.downloadSpaceRoot = findViewById(R.id.download_videos_manage_space);
        if (this.launchFrom == 2) {
            this.downloadSpaceRoot.setEnabled(false);
            this.downloadVideosManageTxt.setEnabled(false);
            Util.zoomViewHeight(0, this.downloadSpaceRoot);
        } else {
            Util.zoomViewHeight(36, this.downloadSpaceRoot);
        }
        this.downloadProgressBar = (ProgressBar) findViewById(R.id.progressbar_capacity);
        this.moveGridView = (TextView) findViewById(R.id.move_grid);
        this.moveListView = (TextView) findViewById(R.id.move_list);
        Util.zoomView(53, 53, this.moveGridView);
        Util.zoomView(Util.getScreenWidth(), 53, this.moveListView);
    }

    public TextView getMoveGridView() {
        return this.moveGridView;
    }

    public TextView getMoveListView() {
        return this.moveListView;
    }

    public void startDownLoadinitAnimation(TextView imageView, float X, float Y, VideoBean video, int position) {
    }

    private void initStreamListPopWindow() {
        this.videosStreamSelect = (TextView) findViewById(R.id.down_load_videos_manage_select);
        this.arrowImageView = (ImageView) findViewById(R.id.down_load_videos_manage_arrow);
        this.selectLayout = (LinearLayout) findViewById(R.id.down_load_videos_manage_select_layout);
        this.selectLayout.setOnClickListener(this);
        this.layoutView = View.inflate(this, R.layout.lebox_download_videos_manage_stream_list, null);
        this.low_or_high_layout = (LinearLayout) this.layoutView.findViewById(R.id.low_or_high_layout);
        this.animTopIn = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.5f, 1, 0.0f);
        this.animTopIn.setDuration(300);
        this.layoutView.setAnimation(this.animTopIn);
        this.mPopupWindow = new PopupWindow(this.layoutView, -2, -2);
        this.mPopupWindow.setFocusable(false);
        this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        this.highTextView = (TextView) this.layoutView.findViewById(R.id.high_text);
        this.lowTextView = (TextView) this.layoutView.findViewById(R.id.low_text);
        this.standardTextView = (TextView) this.layoutView.findViewById(R.id.standard_text);
        if (VideoStreamHandler.specificPhone()) {
            this.mVideoStreamHandler.setCurrentStream(0);
            this.highTextView.setVisibility(8);
            this.standardTextView.setVisibility(8);
            this.lowTextView.setOnClickListener(this);
        }
        new Thread(new Runnable() {
            public void run() {
                boolean isSupportHD = DownloadVideoAlbumPageActivity.this.mVideoStreamHandler.isCPUSupportHD();
                Logger.d(DownloadVideoAlbumPageActivity.TAG, "initStreamListPopWindow isSupportHD : " + isSupportHD);
                new Handler(DownloadVideoAlbumPageActivity.this.getMainLooper()).post(new 1(this, isSupportHD));
            }
        }).start();
        setStreamSelect();
    }

    private void setStreamSelect() {
        int currentStream = this.mVideoStreamHandler.getCurrentStream();
        Resources res = getResources();
        if (this.isCupSupport) {
            this.standardTextView.setTextColor(res.getColor(R.color.letv_color_5895ed));
            this.standardTextView.setBackgroundResource(R.color.letv_color_ffffffff);
            this.highTextView.setTextColor(res.getColor(R.color.letv_color_5895ed));
            this.highTextView.setBackgroundResource(R.drawable.download_top_borde_bg);
        } else {
            this.standardTextView.setTextColor(res.getColor(R.color.letv_color_5895ed));
            this.standardTextView.setBackgroundResource(R.drawable.download_top_borde_bg);
        }
        this.lowTextView.setTextColor(res.getColor(R.color.letv_color_5895ed));
        this.lowTextView.setBackgroundResource(R.drawable.download_bottom_borde_bg);
        switch (currentStream) {
            case 0:
                this.lowTextView.setTextColor(res.getColor(R.color.letv_color_ffffffff));
                this.lowTextView.setBackgroundResource(R.color.letv_color_5895ed);
                this.videosStreamSelect.setText(getString(R.string.stream_smooth));
                break;
            case 1:
                this.highTextView.setTextColor(res.getColor(R.color.letv_color_ffffffff));
                this.highTextView.setBackgroundResource(R.color.letv_color_5895ed);
                this.videosStreamSelect.setText(getString(R.string.stream_hd));
                break;
            case 2:
                this.standardTextView.setTextColor(res.getColor(R.color.letv_color_ffffffff));
                this.standardTextView.setBackgroundResource(R.color.letv_color_5895ed);
                this.videosStreamSelect.setText(getString(R.string.stream_standard));
                break;
        }
        dismissStreamPopupWindow();
    }

    protected void onResume() {
        super.onResume();
        if (this.launchFrom != 2) {
            updateSdcardSpace();
        }
    }

    public void updateSdcardSpace() {
        StorageGetInfoHttpRequest.getGetInfoRequest(this, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                if (code != 0) {
                    Logger.e(DownloadVideoAlbumPageActivity.TAG, " --StorageGetInfoHttpRequest  errorCode : " + errorCode + "  msg :" + msg);
                } else if (object == null || !(object instanceof CommonResponse)) {
                    Logger.e(DownloadVideoAlbumPageActivity.TAG, " --StorageGetInfoHttpRequest  error object : " + object);
                } else {
                    StorageGetInfoBean bean = (StorageGetInfoBean) ((CommonResponse) object).getData();
                    String used = bean.getUsed();
                    String total = bean.getTotal();
                    Logger.d(DownloadVideoAlbumPageActivity.TAG, "--used :" + used + "\n --total：" + total);
                    long totalSize = Long.parseLong(total);
                    long availableSize = totalSize - Long.parseLong(used);
                    DownloadVideoAlbumPageActivity.this.downloadVideosManageTxt.setText(String.format(DownloadVideoAlbumPageActivity.this.getResources().getString(R.string.lebox_download_videos_manage_space_usedpace), new Object[]{Util.getGB_Number(availableSize, 1)}));
                    DownloadVideoAlbumPageActivity.this.downloadVideosManageTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    int progress = 0;
                    if (totalSize != 0) {
                        progress = (int) (100.0f - ((((float) availableSize) / ((float) totalSize)) * 100.0f));
                    }
                    Logger.d(DownloadVideoAlbumPageActivity.TAG, "--availableSize :" + availableSize + "\n --totalSize：" + totalSize + "\n --progress :" + progress);
                    DownloadVideoAlbumPageActivity.this.downloadProgressBar.setProgress(progress);
                }
            }
        }).execute(StorageGetInfoHttpRequest.getStorageGetInfoParameter().combineParams());
    }

    public void dismissStreamPopupWindow() {
        if (this.mPopupWindow != null && this.mPopupWindow.isShowing()) {
            this.mPopupWindow.dismiss();
        }
        if (this.arrowImageView != null) {
            this.arrowImageView.setImageResource(R.drawable.down_arrow_sel);
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
        this.launchFrom = intent.getIntExtra("from", 2);
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
        int y = arrayOfInf[1] + Util.dipToPx(45.0f);
        this.mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        this.mPopupWindow.showAtLocation(view, 51, x, y);
    }

    private void showStreamPopupWindow(View v) {
        if (!(this.mPopupWindow == null || this.mPopupWindow.isShowing())) {
            showStreamPopWindowForView(v);
        }
        if (this.arrowImageView != null) {
            this.arrowImageView.setImageResource(R.drawable.up_arrow_nor);
        }
    }

    private void setStreamItemWidth() {
        if (this.selectLayout != null) {
            int width = this.selectLayout.getWidth();
            LayoutParams lp = this.low_or_high_layout.getLayoutParams();
            lp.width = width;
            this.low_or_high_layout.setLayoutParams(lp);
            Logger.d("", "setStreamItemWidth width : " + width);
        }
    }

    private void setStreamSelectItem() {
        setStreamSelect();
        if (this.mIDownloadVideoFragment != null) {
            this.mIDownloadVideoFragment.notifyAdapter();
        }
    }

    public void onClick(View v) {
        if (v == this.downloadVideosManageTxt) {
            PageJumpUtil.jumpLeboxDownloadPage(this);
        } else if (v == this.toLoginLayout) {
        } else {
            if (v == this.toLoginClose) {
                this.toLoginLayout.setVisibility(8);
                this.downloadPageLoginTagIsHandCancel = true;
            } else if (v == this.backView || v == this.down_load_videos_manage_title) {
                if (!this.isLoginSuccess) {
                    setResult(1002);
                }
                this.isLoginSuccess = false;
                finish();
            } else if (v == this.selectLayout) {
                controlStreamPop(v);
                setStreamItemWidth();
            } else if (v == this.highTextView) {
                this.mVideoStreamHandler.setCurrentStream(1);
                setStreamSelectItem();
                if (this.album == null) {
                }
            } else if (v == this.lowTextView) {
                this.mVideoStreamHandler.setCurrentStream(0);
                setStreamSelectItem();
                if (this.album == null) {
                }
            } else if (v == this.standardTextView) {
                this.mVideoStreamHandler.setCurrentStream(2);
                setStreamSelectItem();
                if (this.album == null) {
                }
            }
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
}
