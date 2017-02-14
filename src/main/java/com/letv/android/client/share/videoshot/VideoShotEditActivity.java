package com.letv.android.client.share.videoshot;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.config.VideoShotEditActivityConfig;
import com.letv.android.client.commonlib.view.RoundedImageView;
import com.letv.android.client.commonlib.view.RoundedPagerDrawable;
import com.letv.android.client.share.ShareUtils;
import com.letv.android.client.view.HalfPlaySharePopwindow;
import com.letv.android.client.widget.JazzyViewPager;
import com.letv.android.client.widget.JazzyViewPager.TransitionEffect;
import com.letv.android.client.widget.videoshot.MyDialog;
import com.letv.android.client.widget.videoshot.OutlineContainer;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.PhotoFilesInfoBean;
import com.letv.core.bean.PhotoInfoBean;
import com.letv.core.bean.VideoShotFigureBodyBean;
import com.letv.core.bean.VideoShotFigureTextBean;
import com.letv.core.bean.VideoShotPicDataBean;
import com.letv.core.bean.VideoShotPicItemBean;
import com.letv.core.bean.VideoShotShareInfoBean;
import com.letv.core.bean.VideoShotTextItemBean;
import com.letv.core.download.image.ImageDownloadStateListener;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.VideoShotFigureParser;
import com.letv.core.parser.VideoShotPicParser;
import com.letv.core.utils.BlurUtils;
import com.letv.core.utils.FileUtils;
import com.letv.core.utils.LetvDateUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StoreUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import com.tencent.open.yyb.TitleBar;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class VideoShotEditActivity extends LetvBaseActivity implements OnClickListener {
    public static final int VIDEOSHOT_FIGURE_COUNT = 3;
    private static final int VIDEOSHOT_FIGURE_FIGHT = 2;
    private static final int VIDEOSHOT_FIGURE_HEART = 0;
    private static final int VIDEOSHOT_FIGURE_STORY = 1;
    public static final int VIDEOSHOT_PIC_DOWNLOAD_COUNT = 12;
    public static final int VIDEOSHOT_PIC_DOWNLOAD_FINISHD = 1016;
    public static final String VIDEOSHOT_PIC_TAB_1_HEAD = "pic1_1_b.png";
    public static final String VIDEOSHOT_PIC_TAB_1_NORMAL = "pic1.png";
    public static final String VIDEOSHOT_PIC_TAB_1_PRESSED = "pic1_s.png";
    public static final String VIDEOSHOT_PIC_TAB_1_TEXT = "pic1_2_b.png";
    public static final String VIDEOSHOT_PIC_TAB_2_HEAD = "pic2_1_b.png";
    public static final String VIDEOSHOT_PIC_TAB_2_NORMAL = "pic2.png";
    public static final String VIDEOSHOT_PIC_TAB_2_PRESSED = "pic2_s.png";
    public static final String VIDEOSHOT_PIC_TAB_2_TEXT = "pic2_2_b.png";
    public static final String VIDEOSHOT_PIC_TAB_3_HEAD = "pic3_1_b.png";
    public static final String VIDEOSHOT_PIC_TAB_3_NORMAL = "pic3.png";
    public static final String VIDEOSHOT_PIC_TAB_3_PRESSED = "pic3_s.png";
    public static final String VIDEOSHOT_PIC_TAB_3_TEXT = "pic3_2_b.png";
    public static final int VIDEOSHOT_VIEWPAGER_CHANGE_VOICE = 1015;
    public static final int VIDEOSHOT_VIEWPAGER_DELETE = 1012;
    public static final int VIDEOSHOT_VIEWPAGER_DESTORY = 1013;
    public static final int VIDEOSHOT_VIEWPAGER_FLIP = 1011;
    public static final int VIDEOSHOT_VIEWPAGER_SETUP = 1010;
    private static final int VIDEOSHOT_VOICE_TEXT_SIZE = 10;
    private static final int VIDEOSHOT_WATERMARK_BG_HEIGHT = 20;
    private static final int VIDEOSHOT_WATERMARK_BG_HEIGHT_DEFAULT_PX = 70;
    private static final int VIDEOSHOT_WATERMARK_TEXT_SIZE = 8;
    private static final int VIDEOSHOT_WATERMARK_TEXT_SIZE_DEFAULT_PX = 26;
    private static int currentFigureIndex = -1;
    private static boolean isOrigPicMode = false;
    public static boolean isUpdateFigureSuccessful = false;
    private static ArrayList<String> mFightVoice = new ArrayList();
    private static ArrayList<String> mHeartVoice = new ArrayList();
    private static ArrayList<String> mStoryVoice = new ArrayList();
    private final int[] FIGURE_SHOW_DRAWABLE;
    private final int[] FIGURE_VOICE_DRAWABLE;
    private int beforeDelIndex;
    private AnimationDrawable bgAnimation;
    private int currentIndex;
    private int currentPhotoPos;
    private Dialog editNoticeDialog;
    private long editTime;
    private boolean isContentEdited;
    private boolean isDelEnable;
    private boolean isDeleted;
    public boolean isHasCustomHead;
    private boolean isJazzyFlipped;
    private boolean isKeyboardOpened;
    private boolean isLargerMode;
    private boolean isOnCreate;
    private boolean isOrderChanged;
    private JazzyViewPager jazzy;
    private int lastIndex;
    private String lastdesc;
    private ImageView mBackImageView;
    private String mCid;
    private ImageView mDeleteImageView;
    private EditText mDescEditText;
    private ImageView mDownloadView;
    private int mFightVoicePos;
    private FigurePagerAdapter mFigurePagerAdapter;
    private ImageView mFigureTab0;
    private ImageView mFigureTab1;
    private ImageView mFigureTab2;
    private HalfPlaySharePopwindow mHalfPlaySharePopwindow;
    private int mHeartVoicePos;
    List<ImageView> mIVArray;
    private View mIncView;
    private LayoutInflater mInflater;
    private TextView mOrigText;
    private RelativeLayout mRLPopupInput;
    private RelativeLayout mRLVideoShotContent;
    private RelativeLayout mRootLayout;
    private TextView mShareView;
    private int mStoryVoicePos;
    private TextView mTitleView;
    private ImageView mUseOrigPic;
    private String mVideoShotPicDownloadDir;
    private TextView mVideoshotPos;
    private ViewPager mViewPager;
    private ImageView mViewPopupInput;
    private int maxIndex;
    private PhotoFilesInfoBean photoFileName;
    private Handler previewHandler;
    Random stdRandom;
    private VideoShotShareInfoBean vsShareInfoBean;

    private class FigurePagerAdapter extends PagerAdapter {
        final /* synthetic */ VideoShotEditActivity this$0;

        public FigurePagerAdapter(VideoShotEditActivity videoShotEditActivity) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = videoShotEditActivity;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            if (arg0 != null && arg0 != null && ((ViewPager) arg0).getChildCount() > arg1) {
                ((ViewPager) arg0).removeViewAt(arg1);
            }
        }

        public void finishUpdate(View arg0) {
        }

        public int getCount() {
            return 3;
        }

        public Object instantiateItem(View arg0, int arg1) {
            Object view = null;
            if (!(arg0 == null || arg0 == null)) {
                view = this.this$0.mInflater.inflate(R.layout.videoshot_figure_item, null);
                if (view != null) {
                    ImageView show = (ImageView) view.findViewById(R.id.iv_figure);
                    ImageView switchView = (ImageView) view.findViewById(R.id.tv_notice);
                    RelativeLayout mChangeVoiceLayout = (RelativeLayout) view.findViewById(R.id.rl_notice);
                    ((ImageView) view.findViewById(R.id.iv_voice)).setImageDrawable(this.this$0.getVoiceItemDrawable(arg1));
                    show.setImageDrawable(this.this$0.getHeadItemDrawable(arg1));
                    switchView.setImageDrawable(this.this$0.getSwitchDrawable(arg1));
                    mChangeVoiceLayout.setOnClickListener(new OnClickListener(this) {
                        final /* synthetic */ FigurePagerAdapter this$1;

                        {
                            if (HotFix.PREVENT_VERIFY) {
                                System.out.println(VerifyLoad.class);
                            }
                            this.this$1 = this$1;
                        }

                        public void onClick(View v) {
                            this.this$1.this$0.previewHandler.sendEmptyMessage(VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_CHANGE_VOICE);
                        }
                    });
                }
                ((ViewPager) arg0).addView(view, arg1);
            }
            return view;
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        public Parcelable saveState() {
            return null;
        }

        public void startUpdate(View arg0) {
        }
    }

    private class MainAdapter extends PagerAdapter {
        private MainAdapter() {
        }

        /* synthetic */ MainAdapter(VideoShotEditActivity x0, AnonymousClass1 x1) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this();
        }

        public Object instantiateItem(ViewGroup container, int position) {
            RoundedImageView imageView;
            if (VideoShotEditActivity.this.photoFileName.getPhoto().size() > 1 && position == 0) {
                imageView = VideoShotEditActivity.this.createImageViewFromFile(position, ((PhotoInfoBean) VideoShotEditActivity.this.photoFileName.getPhoto().get(VideoShotEditActivity.this.photoFileName.getPhoto().size() - 1)).photoPath);
            } else if (VideoShotEditActivity.this.photoFileName.getPhoto().size() > 1 && position == VideoShotEditActivity.this.photoFileName.getPhoto().size() + 1) {
                imageView = VideoShotEditActivity.this.createImageViewFromFile(position, ((PhotoInfoBean) VideoShotEditActivity.this.photoFileName.getPhoto().get(0)).photoPath);
            } else if (VideoShotEditActivity.this.photoFileName.getPhoto().size() > 1) {
                imageView = VideoShotEditActivity.this.createImageViewFromFile(position, ((PhotoInfoBean) VideoShotEditActivity.this.photoFileName.getPhoto().get(position - 1)).photoPath);
            } else {
                imageView = VideoShotEditActivity.this.createImageViewFromFile(position, ((PhotoInfoBean) VideoShotEditActivity.this.photoFileName.getPhoto().get(position)).photoPath);
            }
            LogInfo.log("fornia", "setupJazziness instantiateItem position:" + position + "|jazzy.getChildCount():" + VideoShotEditActivity.this.jazzy.getChildCount());
            if (imageView != null) {
                imageView.setTag(Integer.valueOf(position));
                imageView.setScaleType(ScaleType.FIT_XY);
                LayoutParams params_imageview = new LayoutParams(-1, -1);
                params_imageview.gravity = 17;
                imageView.setLayoutParams(params_imageview);
                container.addView(imageView, -1, -1);
                VideoShotEditActivity.this.jazzy.setObjectForPosition(imageView, position);
            }
            return imageView;
        }

        public void destroyItem(ViewGroup container, int position, Object obj) {
            container.removeView(VideoShotEditActivity.this.jazzy.findViewFromObject(position));
        }

        public int getCount() {
            if (VideoShotEditActivity.this.photoFileName.getPhoto().size() < 0 || VideoShotEditActivity.this.photoFileName.getPhoto().size() > 1) {
                return VideoShotEditActivity.this.photoFileName.getPhoto().size() + 2;
            }
            return VideoShotEditActivity.this.photoFileName.getPhoto().size();
        }

        public boolean isViewFromObject(View view, Object obj) {
            if (view instanceof OutlineContainer) {
                if (((OutlineContainer) view).getChildAt(0) == obj) {
                    return true;
                }
                return false;
            } else if (view != obj) {
                return false;
            } else {
                return true;
            }
        }

        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {
        final /* synthetic */ VideoShotEditActivity this$0;

        public MyOnPageChangeListener(VideoShotEditActivity this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
        }

        public void onPageSelected(int arg0) {
            LogInfo.log("fornia", "onPageSelected arg0:" + arg0);
            this.this$0.updateTab(arg0);
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    public class MyOnVideoShotPageChangeListener implements OnPageChangeListener {
        final /* synthetic */ VideoShotEditActivity this$0;

        public MyOnVideoShotPageChangeListener(VideoShotEditActivity this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
        }

        public void onPageSelected(int arg0) {
            LogInfo.log("fornia", "onPageSelected arg0:" + arg0);
            if (this.this$0.photoFileName.getPhoto().size() > 1 && arg0 == 0) {
                this.this$0.jazzy.setCurrentItem(this.this$0.photoFileName.getPhoto().size(), false);
                this.this$0.currentIndex = this.this$0.photoFileName.getPhoto().size();
            } else if (this.this$0.photoFileName.getPhoto().size() <= 1 || arg0 != this.this$0.photoFileName.getPhoto().size() + 1) {
                this.this$0.currentIndex = arg0;
            } else {
                this.this$0.jazzy.setCurrentItem(1, false);
                this.this$0.currentIndex = 1;
            }
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    public VideoShotEditActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mCid = "";
        this.mIVArray = new ArrayList();
        this.lastIndex = 0;
        this.lastdesc = "";
        this.currentIndex = 0;
        this.currentPhotoPos = 0;
        this.beforeDelIndex = -1;
        this.isJazzyFlipped = false;
        this.isLargerMode = true;
        this.isOrderChanged = false;
        this.isDeleted = false;
        this.maxIndex = 0;
        this.isDelEnable = true;
        this.FIGURE_VOICE_DRAWABLE = new int[]{2130838089, 2130838092, 2130838095};
        this.FIGURE_SHOW_DRAWABLE = new int[]{2130838956, 2130838957, 2130838958};
        this.mHeartVoicePos = -1;
        this.mStoryVoicePos = -1;
        this.mFightVoicePos = -1;
        this.stdRandom = null;
        this.isOnCreate = true;
        this.isContentEdited = false;
        this.isKeyboardOpened = false;
        this.isHasCustomHead = false;
        this.mVideoShotPicDownloadDir = "";
        this.photoFileName = new PhotoFilesInfoBean();
        this.mFigurePagerAdapter = null;
        this.mHalfPlaySharePopwindow = null;
        this.previewHandler = new Handler(this) {
            final /* synthetic */ VideoShotEditActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1010:
                        this.this$0.setupJazziness(TransitionEffect.Standard);
                        return;
                    case VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_FLIP /*1011*/:
                        this.this$0.currentIndex = this.this$0.jazzy.getCurrentItem();
                        if (TextUtils.isEmpty(this.this$0.lastdesc)) {
                            this.this$0.lastdesc = this.this$0.mDescEditText.getText().toString();
                        }
                        this.this$0.setLastPhotoDesc();
                        if (this.this$0.lastIndex != this.this$0.currentIndex) {
                            this.this$0.maxIndex = this.this$0.currentIndex > this.this$0.maxIndex ? this.this$0.currentIndex : this.this$0.maxIndex;
                            this.this$0.isDeleted = false;
                            this.this$0.isJazzyFlipped = true;
                            if (this.this$0.currentIndex > this.this$0.lastIndex) {
                                if (this.this$0.isLargerMode) {
                                    this.this$0.isOrderChanged = false;
                                } else {
                                    this.this$0.isOrderChanged = true;
                                    this.this$0.isLargerMode = true;
                                }
                            } else if (this.this$0.isLargerMode) {
                                this.this$0.isOrderChanged = true;
                                this.this$0.isLargerMode = false;
                            } else {
                                this.this$0.isOrderChanged = false;
                            }
                            this.this$0.lastIndex = this.this$0.currentIndex;
                            LogInfo.log("fornia", "VIDEOSHOT_VIEWPAGER_FLIP updateInputBlurBg");
                            this.this$0.updatePhotoPos();
                        }
                        LogInfo.log("fornia", "2lastIndex:" + this.this$0.lastIndex + "|lastdesc:" + this.this$0.lastdesc);
                        return;
                    case VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_DELETE /*1012*/:
                        LogInfo.log("fornia", "VIDEOSHOT_VIEWPAGER_DELETE isContentEdited = true");
                        this.this$0.isContentEdited = true;
                        this.this$0.isDelEnable = true;
                        return;
                    case VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_DESTORY /*1013*/:
                        this.this$0.finish();
                        return;
                    case VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_CHANGE_VOICE /*1015*/:
                        this.this$0.isContentEdited = true;
                        switch (VideoShotEditActivity.currentFigureIndex) {
                            case 0:
                                this.this$0.mHeartVoicePos = this.this$0.getRandomIndex(0, this.this$0.mHeartVoicePos);
                                break;
                            case 1:
                                this.this$0.mStoryVoicePos = this.this$0.getRandomIndex(1, this.this$0.mStoryVoicePos);
                                break;
                            case 2:
                                this.this$0.mFightVoicePos = this.this$0.getRandomIndex(2, this.this$0.mFightVoicePos);
                                break;
                        }
                        VideoShotEditActivity.isOrigPicMode = false;
                        this.this$0.mUseOrigPic.setSelected(VideoShotEditActivity.isOrigPicMode);
                        this.this$0.mRLPopupInput.setVisibility(0);
                        this.this$0.updatePicDesc();
                        return;
                    case VideoShotEditActivity.VIDEOSHOT_PIC_DOWNLOAD_FINISHD /*1016*/:
                        String path = msg.obj;
                        if (!TextUtils.isEmpty(path)) {
                            this.this$0.setViewBackground(path);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        };
        this.editTime = -1;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoshot_edit);
        if (getIntent() != null) {
            this.vsShareInfoBean = (VideoShotShareInfoBean) getIntent().getSerializableExtra(VideoShotEditActivityConfig.SHARE_VIDEOSHOT_DATA_BEAN);
            String str = (this.vsShareInfoBean == null || this.vsShareInfoBean.mAlbumInfo == null) ? "" : this.vsShareInfoBean.mAlbumInfo.cid + "";
            this.mCid = str;
            LogInfo.log("fornia", "截图视频类型  mCid:" + this.mCid);
        }
        loadVideoShot();
        initView();
        initContent();
        startFlashAnim();
        StatisticsUtils.staticticsInfoPost(this.mContext, "19", null, null, -1, null, PageIdConstant.pictureSharePage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (HalfPlaySharePopwindow.onFragmentResult != null) {
            HalfPlaySharePopwindow.onFragmentResult.onFragmentResult_back(requestCode, resultCode, data);
        }
    }

    private void loadVideoShot() {
        new Thread(new Runnable(this) {
            final /* synthetic */ VideoShotEditActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                this.this$0.loadData();
                if (this.this$0.photoFileName.getPhoto().size() == 0) {
                    this.this$0.previewHandler.sendEmptyMessage(VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_DESTORY);
                } else {
                    this.this$0.previewHandler.sendEmptyMessageDelayed(1010, 500);
                }
            }
        }).start();
    }

    private void requestFigureHead() {
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            String figureHeadUrl = MediaAssetApi.getInstance().requestVideoShotHead();
            if (!TextUtils.isEmpty(figureHeadUrl)) {
                new LetvRequest().setUrl(figureHeadUrl).setParser(new VideoShotPicParser()).setCache(new VolleyNoCache()).setMaxRetries(2).setRequestType(RequestManner.NETWORK_ONLY).setCallback(new SimpleResponse<VideoShotPicDataBean>(this) {
                    final /* synthetic */ VideoShotEditActivity this$0;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$0 = this$0;
                    }

                    public void onNetworkResponse(VolleyRequest<VideoShotPicDataBean> volleyRequest, VideoShotPicDataBean result, DataHull hull, NetworkResponseState state) {
                        LogInfo.log("fornia", "screenshot  result:" + result);
                        if (result != null && result.data != null && result.data.size() != 0) {
                            LogInfo.log("fornia", "screenshot  result:" + result.toString());
                            if (state == NetworkResponseState.SUCCESS) {
                                LogInfo.log("fornia", "screenshot  state == NetworkResponseState.SUCCESS:");
                                for (VideoShotPicItemBean item : result.data) {
                                    if (item != null && !TextUtils.isEmpty(item.cid) && this.this$0.mCid.equals(item.cid.trim())) {
                                        this.this$0.downloadFigureHead(item);
                                        break;
                                    }
                                }
                            }
                            if (state != NetworkResponseState.SUCCESS) {
                                LogInfo.log("fornia", "screenshot  state != NetworkResponseState.SUCCESS:" + state.toString());
                            }
                        }
                    }

                    public void onErrorReport(VolleyRequest<VideoShotPicDataBean> request, String errorInfo) {
                        super.onErrorReport(request, errorInfo);
                    }
                }).add();
            }
        }
    }

    private void downloadFigureHead(final VideoShotPicItemBean videoShotPicItemBean) {
        new Thread(new Runnable() {
            public void run() {
                if (videoShotPicItemBean != null && videoShotPicItemBean.picList != null && !TextUtils.isEmpty(videoShotPicItemBean.cid)) {
                    VideoShotEditActivity.this.downloadPic(videoShotPicItemBean.picList.pic1, StoreUtils.getLocalRestoreNomediaPath(VideoShotEditActivity.this, StoreUtils.VIDEOSHOT_PIC_HEAD_PATH + VideoShotEditActivity.this.mCid + File.separator) + VideoShotEditActivity.VIDEOSHOT_PIC_TAB_1_NORMAL);
                    VideoShotEditActivity.this.downloadPic(videoShotPicItemBean.picList.pic1_s, StoreUtils.getLocalRestoreNomediaPath(VideoShotEditActivity.this, StoreUtils.VIDEOSHOT_PIC_HEAD_PATH + VideoShotEditActivity.this.mCid + File.separator) + VideoShotEditActivity.VIDEOSHOT_PIC_TAB_1_PRESSED);
                    VideoShotEditActivity.this.downloadPic(videoShotPicItemBean.picList.pic1_2_b, StoreUtils.getLocalRestoreNomediaPath(VideoShotEditActivity.this, StoreUtils.VIDEOSHOT_PIC_HEAD_PATH + VideoShotEditActivity.this.mCid + File.separator) + VideoShotEditActivity.VIDEOSHOT_PIC_TAB_1_TEXT);
                    VideoShotEditActivity.this.downloadPic(videoShotPicItemBean.picList.pic1_1_b, StoreUtils.getLocalRestoreNomediaPath(VideoShotEditActivity.this, StoreUtils.VIDEOSHOT_PIC_HEAD_PATH + VideoShotEditActivity.this.mCid + File.separator) + VideoShotEditActivity.VIDEOSHOT_PIC_TAB_1_HEAD);
                    VideoShotEditActivity.this.downloadPic(videoShotPicItemBean.picList.pic2, StoreUtils.getLocalRestoreNomediaPath(VideoShotEditActivity.this, StoreUtils.VIDEOSHOT_PIC_HEAD_PATH + VideoShotEditActivity.this.mCid + File.separator) + VideoShotEditActivity.VIDEOSHOT_PIC_TAB_2_NORMAL);
                    VideoShotEditActivity.this.downloadPic(videoShotPicItemBean.picList.pic2_s, StoreUtils.getLocalRestoreNomediaPath(VideoShotEditActivity.this, StoreUtils.VIDEOSHOT_PIC_HEAD_PATH + VideoShotEditActivity.this.mCid + File.separator) + VideoShotEditActivity.VIDEOSHOT_PIC_TAB_2_PRESSED);
                    VideoShotEditActivity.this.downloadPic(videoShotPicItemBean.picList.pic2_2_b, StoreUtils.getLocalRestoreNomediaPath(VideoShotEditActivity.this, StoreUtils.VIDEOSHOT_PIC_HEAD_PATH + VideoShotEditActivity.this.mCid + File.separator) + VideoShotEditActivity.VIDEOSHOT_PIC_TAB_2_TEXT);
                    VideoShotEditActivity.this.downloadPic(videoShotPicItemBean.picList.pic2_1_b, StoreUtils.getLocalRestoreNomediaPath(VideoShotEditActivity.this, StoreUtils.VIDEOSHOT_PIC_HEAD_PATH + VideoShotEditActivity.this.mCid + File.separator) + VideoShotEditActivity.VIDEOSHOT_PIC_TAB_2_HEAD);
                    VideoShotEditActivity.this.downloadPic(videoShotPicItemBean.picList.pic3, StoreUtils.getLocalRestoreNomediaPath(VideoShotEditActivity.this, StoreUtils.VIDEOSHOT_PIC_HEAD_PATH + VideoShotEditActivity.this.mCid + File.separator) + VideoShotEditActivity.VIDEOSHOT_PIC_TAB_3_NORMAL);
                    VideoShotEditActivity.this.downloadPic(videoShotPicItemBean.picList.pic3_s, StoreUtils.getLocalRestoreNomediaPath(VideoShotEditActivity.this, StoreUtils.VIDEOSHOT_PIC_HEAD_PATH + VideoShotEditActivity.this.mCid + File.separator) + VideoShotEditActivity.VIDEOSHOT_PIC_TAB_3_PRESSED);
                    VideoShotEditActivity.this.downloadPic(videoShotPicItemBean.picList.pic3_2_b, StoreUtils.getLocalRestoreNomediaPath(VideoShotEditActivity.this, StoreUtils.VIDEOSHOT_PIC_HEAD_PATH + VideoShotEditActivity.this.mCid + File.separator) + VideoShotEditActivity.VIDEOSHOT_PIC_TAB_3_TEXT);
                    VideoShotEditActivity.this.downloadPic(videoShotPicItemBean.picList.pic3_1_b, StoreUtils.getLocalRestoreNomediaPath(VideoShotEditActivity.this, StoreUtils.VIDEOSHOT_PIC_HEAD_PATH + VideoShotEditActivity.this.mCid + File.separator) + VideoShotEditActivity.VIDEOSHOT_PIC_TAB_3_HEAD);
                }
            }
        }).start();
    }

    private void downloadPic(String url, String localPath) {
        LogInfo.log("fornia", "下载路径url：" + url + "|localPath" + localPath);
        ImageDownloader.getInstance().download(url, new ImageDownloadStateListener(this) {
            final /* synthetic */ VideoShotEditActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void loading() {
                LogInfo.log("fornia", "下载loading");
            }

            public void loadSuccess(Bitmap bitmap) {
                LogInfo.log("fornia", "下载了一张图片bitmap：" + bitmap);
            }

            public void loadSuccess(Bitmap bitmap, String localPath) {
                LogInfo.log("fornia", "下载了一张图片bitmap：" + bitmap + "|localPath" + localPath);
                if (bitmap != null && bitmap.getByteCount() > 0) {
                    new Thread(new 1(this, localPath, bitmap)).start();
                }
            }

            public void loadSuccess(View view, Bitmap bitmap, String localPath) {
            }

            public void loadFailed() {
                LogInfo.log("fornia", "下载loadFailed");
            }
        }, localPath);
    }

    private boolean isDownloadFinished(String path) {
        String dir = FileUtils.getFileDir(path);
        LogInfo.log("fornia", "下载文件目录 dir:" + dir);
        if (TextUtils.isEmpty(dir)) {
            return false;
        }
        List<File> pics = FileUtils.getFilesByFullPath(this.mContext, dir);
        LogInfo.log("fornia", "下载文件目录下的图片 pics:" + pics);
        if (pics == null || pics.size() == 0) {
            return false;
        }
        LogInfo.log("fornia", "下载文件个数 pics.size():" + pics.size());
        if (pics.size() >= 12) {
            return true;
        }
        return false;
    }

    private void setViewBackground(String path) {
        String dir = FileUtils.getFileDir(path);
        LogInfo.log("fornia", "下载文件目录 dir:" + dir);
        if (!TextUtils.isEmpty(dir)) {
            this.mFigureTab0.setImageDrawable(FileUtils.createStateDrawable(this.mContext, dir + VIDEOSHOT_PIC_TAB_1_NORMAL, dir + VIDEOSHOT_PIC_TAB_1_PRESSED, dir + VIDEOSHOT_PIC_TAB_1_PRESSED, dir + VIDEOSHOT_PIC_TAB_1_PRESSED));
            this.mFigureTab1.setImageDrawable(FileUtils.createStateDrawable(this.mContext, dir + VIDEOSHOT_PIC_TAB_2_NORMAL, dir + VIDEOSHOT_PIC_TAB_2_PRESSED, dir + VIDEOSHOT_PIC_TAB_2_PRESSED, dir + VIDEOSHOT_PIC_TAB_2_PRESSED));
            this.mFigureTab2.setImageDrawable(FileUtils.createStateDrawable(this.mContext, dir + VIDEOSHOT_PIC_TAB_3_NORMAL, dir + VIDEOSHOT_PIC_TAB_3_PRESSED, dir + VIDEOSHOT_PIC_TAB_3_PRESSED, dir + VIDEOSHOT_PIC_TAB_3_PRESSED));
            this.mFigurePagerAdapter = new FigurePagerAdapter(this);
            this.mViewPager.setAdapter(this.mFigurePagerAdapter);
            this.mViewPager.setOnPageChangeListener(new MyOnPageChangeListener(this));
            this.mViewPager.setCurrentItem(0);
        }
    }

    private void initView() {
        this.mInflater = LayoutInflater.from(this);
        this.mRLVideoShotContent = (RelativeLayout) findViewById(R.id.rl_videoshot_edit_content);
        this.mRLPopupInput = (RelativeLayout) findViewById(R.id.rl_popup_input);
        this.jazzy = (JazzyViewPager) findViewById(R.id.vPager_videoshot);
        this.mRootLayout = (RelativeLayout) findViewById(2131361881);
        this.mViewPopupInput = (ImageView) findViewById(R.id.v_popup_input);
        this.mIncView = findViewById(R.id.inc_videoshot_navigation);
        this.mBackImageView = (ImageView) findViewById(2131362351);
        this.mShareView = (TextView) findViewById(2131362354);
        this.mTitleView = (TextView) findViewById(2131362352);
        this.mDeleteImageView = (ImageView) findViewById(R.id.iv_delete);
        this.mDescEditText = (EditText) findViewById(R.id.et_popup_input);
        this.mDownloadView = (ImageView) findViewById(R.id.iv_download);
        this.mUseOrigPic = (ImageView) findViewById(R.id.iv_orig_check);
        this.mOrigText = (TextView) findViewById(R.id.tv_videoshot_original);
        this.mVideoshotPos = (TextView) findViewById(R.id.tv_videoshot_order);
        this.mViewPager = (ViewPager) findViewById(R.id.vPager_model);
        this.mFigureTab0 = (ImageView) findViewById(R.id.iv_tab0);
        this.mFigureTab1 = (ImageView) findViewById(R.id.iv_tab1);
        this.mFigureTab2 = (ImageView) findViewById(R.id.iv_tab2);
    }

    private void initContent() {
        ViewGroup.LayoutParams layoutParams = this.mRLVideoShotContent.getLayoutParams();
        layoutParams.width = UIsUtils.getScreenWidth() - UIsUtils.dipToPx(40.0f);
        layoutParams.height = ((UIsUtils.getScreenWidth() - UIsUtils.dipToPx(40.0f)) * UIsUtils.getScreenWidth()) / UIsUtils.getScreenHeight();
        this.mRLVideoShotContent.setLayoutParams(layoutParams);
        this.mTitleView.setText(getResources().getString(2131101091));
        this.mShareView.setText(getResources().getString(2131100840));
        this.mUseOrigPic.setSelected(isOrigPicMode);
        initFigureHead();
        mHeartVoice.addAll(Arrays.asList(getResources().getStringArray(R.array.figure_voice_heart)));
        mStoryVoice.addAll(Arrays.asList(getResources().getStringArray(R.array.figure_voice_story)));
        mFightVoice.addAll(Arrays.asList(getResources().getStringArray(R.array.figure_voice_fight)));
        this.stdRandom = new Random((long) (Calendar.getInstance().get(13) + 10));
        this.mHeartVoicePos = this.stdRandom.nextInt(mHeartVoice.size() - 1);
        this.mStoryVoicePos = this.stdRandom.nextInt(mStoryVoice.size() - 1);
        this.mFightVoicePos = this.stdRandom.nextInt(mFightVoice.size() - 1);
        LogInfo.log("fornia", "mHeartVoicePos:" + this.mHeartVoicePos + "|mStoryVoicePos:" + this.mStoryVoicePos + "|mFightVoicePos:" + this.mFightVoicePos);
        if (!TextUtils.isEmpty(this.mCid)) {
            requestFigureHead();
        }
        if (!isUpdateFigureSuccessful) {
            requestFigureText();
        }
        this.mRootLayout.setOnClickListener(this);
        this.mRootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
            final /* synthetic */ VideoShotEditActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onGlobalLayout() {
                if (this.this$0.isKeyboardOpened && System.currentTimeMillis() - this.this$0.editTime > 500) {
                    this.this$0.resumeEditTextStatus();
                }
            }
        });
        this.mBackImageView.setOnClickListener(this);
        this.mShareView.setOnClickListener(this);
        this.mFigureTab0.setOnClickListener(this);
        this.mFigureTab1.setOnClickListener(this);
        this.mFigureTab2.setOnClickListener(this);
        this.mDeleteImageView.setOnClickListener(this);
        this.mDownloadView.setOnClickListener(this);
        this.mUseOrigPic.setOnClickListener(this);
        this.mOrigText.setOnClickListener(this);
        this.jazzy.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ VideoShotEditActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onTouch(View v, MotionEvent event) {
                this.this$0.hideSoftKeyboard();
                return false;
            }
        });
        this.jazzy.setOnPageChangeListener(new MyOnVideoShotPageChangeListener(this));
        this.mDescEditText.setCursorVisible(false);
        this.mDescEditText.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ VideoShotEditActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onTouch(View v, MotionEvent event) {
                this.this$0.editTime = System.currentTimeMillis();
                this.this$0.isKeyboardOpened = true;
                this.this$0.mDescEditText.setCursorVisible(true);
                this.this$0.isContentEdited = true;
                this.this$0.hideFlashAnim();
                return false;
            }
        });
        this.mDescEditText.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ VideoShotEditActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                this.this$0.lastdesc = s.toString();
                this.this$0.setCurrentPhotoDesc(this.this$0.lastdesc);
            }
        });
        updateTab(0);
    }

    private void initFigureHead() {
        if (TextUtils.isEmpty(this.mCid)) {
            this.isHasCustomHead = false;
            this.mFigurePagerAdapter = new FigurePagerAdapter(this);
            this.mViewPager.setAdapter(this.mFigurePagerAdapter);
            this.mViewPager.setOnPageChangeListener(new MyOnPageChangeListener(this));
            this.mViewPager.setCurrentItem(0);
            return;
        }
        String path = StoreUtils.getLocalRestoreNomediaPath(this, StoreUtils.VIDEOSHOT_PIC_HEAD_PATH + this.mCid + File.separator);
        this.mVideoShotPicDownloadDir = path;
        if (isDownloadFinished(path)) {
            this.isHasCustomHead = true;
            setViewBackground(path);
            return;
        }
        this.isHasCustomHead = false;
        this.mFigurePagerAdapter = new FigurePagerAdapter(this);
        this.mViewPager.setAdapter(this.mFigurePagerAdapter);
        this.mViewPager.setOnPageChangeListener(new MyOnPageChangeListener(this));
        this.mViewPager.setCurrentItem(0);
    }

    private void requestFigureText() {
        String figureTextUrl = MediaAssetApi.getInstance().requestVideoShotText();
        if (!TextUtils.isEmpty(figureTextUrl)) {
            new LetvRequest().setUrl(figureTextUrl).setParser(new VideoShotFigureParser()).setCache(new VolleyNoCache()).setMaxRetries(2).setRequestType(RequestManner.NETWORK_ONLY).setCallback(new SimpleResponse<VideoShotFigureBodyBean>(this) {
                final /* synthetic */ VideoShotEditActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onNetworkResponse(VolleyRequest<VideoShotFigureBodyBean> volleyRequest, VideoShotFigureBodyBean result, DataHull hull, NetworkResponseState state) {
                    LogInfo.log("fornia", "screenshot  result:" + result);
                    if (result != null) {
                        LogInfo.log("fornia", "screenshot  result:" + result.toString());
                        if (state == NetworkResponseState.SUCCESS) {
                            LogInfo.log("fornia", "screenshot  state == NetworkResponseState.SUCCESS:");
                            VideoShotEditActivity.isUpdateFigureSuccessful = true;
                            this.this$0.updateFigureProbability(result);
                            this.this$0.updatePicDesc();
                        }
                        if (state != NetworkResponseState.SUCCESS) {
                            LogInfo.log("fornia", "screenshot  state != NetworkResponseState.SUCCESS:" + state.toString());
                        }
                    }
                }

                public void onErrorReport(VolleyRequest<VideoShotFigureBodyBean> request, String errorInfo) {
                    super.onErrorReport(request, errorInfo);
                }
            }).add();
        }
    }

    private void updateFigureProbability(VideoShotFigureBodyBean data) {
        List<VideoShotFigureTextBean> list = data.data;
        if (list == null || list.size() == 0) {
            LogInfo.log("fornia", "screenshot  list == null || list.size() == 0");
            return;
        }
        for (VideoShotFigureTextBean videoShotFigureTextBean : list) {
            if (!(videoShotFigureTextBean == null || TextUtils.isEmpty(videoShotFigureTextBean.contentId) || videoShotFigureTextBean.blockContents == null || videoShotFigureTextBean.blockContents.size() == 0)) {
                if (videoShotFigureTextBean.contentId.equalsIgnoreCase("3477")) {
                    for (VideoShotTextItemBean videoShotTextItemBean : videoShotFigureTextBean.blockContents) {
                        if (!(TextUtils.isEmpty(videoShotTextItemBean.remark) || mHeartVoice.contains(videoShotTextItemBean.remark))) {
                            mHeartVoice = times3(mHeartVoice, videoShotTextItemBean.remark);
                        }
                    }
                } else if (videoShotFigureTextBean.contentId.equalsIgnoreCase("3478")) {
                    for (VideoShotTextItemBean videoShotTextItemBean2 : videoShotFigureTextBean.blockContents) {
                        if (!(TextUtils.isEmpty(videoShotTextItemBean2.remark) || mStoryVoice.contains(videoShotTextItemBean2.remark))) {
                            mStoryVoice = times3(mStoryVoice, videoShotTextItemBean2.remark);
                        }
                    }
                } else if (videoShotFigureTextBean.contentId.equalsIgnoreCase("3479")) {
                    for (VideoShotTextItemBean videoShotTextItemBean22 : videoShotFigureTextBean.blockContents) {
                        if (!(TextUtils.isEmpty(videoShotTextItemBean22.remark) || mFightVoice.contains(videoShotTextItemBean22.remark))) {
                            mFightVoice = times3(mFightVoice, videoShotTextItemBean22.remark);
                        }
                    }
                }
            }
        }
    }

    private ArrayList<String> times3(ArrayList<String> list, String element) {
        list.add(element);
        list.add(element);
        list.add(element);
        return list;
    }

    private void startFlashAnim() {
        this.bgAnimation = (AnimationDrawable) this.mViewPopupInput.getDrawable();
        this.bgAnimation.start();
        this.mViewPopupInput.setVisibility(0);
    }

    private void hideFlashAnim() {
        this.mViewPopupInput.setVisibility(8);
    }

    public void deleteAnim(final View view) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "zhy", new float[]{1.0f, 0.0f}).setDuration(500);
        anim.start();
        anim.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ VideoShotEditActivity this$0;

            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = ((Float) animation.getAnimatedValue()).floatValue();
                LogInfo.log("fornia", "cVal:" + cVal + "删除的当前数据是currentIndex：" + this.this$0.currentIndex);
                if (cVal == 0.0f) {
                    this.this$0.isDeleted = true;
                    if (this.this$0.currentIndex - 1 >= 0 && this.this$0.currentIndex - 1 < this.this$0.photoFileName.getPhoto().size()) {
                        FileUtils.deleteFile(new File(((PhotoInfoBean) this.this$0.photoFileName.getPhoto().get(this.this$0.currentIndex - 1)).photoPath));
                        this.this$0.photoFileName.getPhoto().remove(this.this$0.currentIndex - 1);
                        this.this$0.jazzy.getAdapter().notifyDataSetChanged();
                        if (this.this$0.currentIndex - 1 >= this.this$0.photoFileName.getPhoto().size() && this.this$0.photoFileName.getPhoto().size() > 1) {
                            this.this$0.currentIndex = this.this$0.photoFileName.getPhoto().size() - 1;
                        }
                        this.this$0.setupJazziness(TransitionEffect.Standard);
                        this.this$0.previewHandler.sendEmptyMessageDelayed(VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_DELETE, 500);
                        return;
                    }
                    return;
                }
                view.setAlpha(cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
    }

    private void updatePicDesc() {
        LogInfo.log("fornia", "VIDEOSHOT_FIGURE_HEART" + currentFigureIndex);
        switch (currentFigureIndex) {
            case 0:
                LogInfo.log("fornia", "VIDEOSHOT_FIGURE_HEART");
                if (mHeartVoice != null && this.mHeartVoicePos >= 0 && this.mHeartVoicePos < mHeartVoice.size()) {
                    LogInfo.log("fornia", "VIDEOSHOT_FIGURE_HEART 初始化填充 et：" + ((String) mHeartVoice.get(this.mHeartVoicePos)));
                    this.mDescEditText.setText((CharSequence) mHeartVoice.get(this.mHeartVoicePos));
                    return;
                }
                return;
            case 1:
                if (mStoryVoice != null && this.mStoryVoicePos >= 0 && this.mStoryVoicePos < mStoryVoice.size()) {
                    this.mDescEditText.setText((CharSequence) mStoryVoice.get(this.mStoryVoicePos));
                    return;
                }
                return;
            case 2:
                if (mFightVoice != null && this.mFightVoicePos >= 0 && this.mFightVoicePos < mFightVoice.size()) {
                    this.mDescEditText.setText((CharSequence) mFightVoice.get(this.mFightVoicePos));
                    return;
                }
                return;
            default:
                return;
        }
    }

    private int getRandomIndex(int tab, int currentTextPos) {
        int result = 0;
        switch (tab) {
            case 0:
                LogInfo.log("fornia", "VIDEOSHOT_FIGURE_HEART");
                if (mHeartVoice != null && mHeartVoice.size() > 0) {
                    result = this.stdRandom.nextInt(mHeartVoice.size() - 1);
                    if (result == currentTextPos) {
                        getRandomIndex(tab, currentTextPos);
                        break;
                    }
                }
                break;
            case 1:
                if (mStoryVoice != null && mStoryVoice.size() > 0) {
                    result = this.stdRandom.nextInt(mStoryVoice.size() - 1);
                    if (result == currentTextPos) {
                        getRandomIndex(tab, currentTextPos);
                        break;
                    }
                }
                break;
            case 2:
                if (mFightVoice != null && mFightVoice.size() > 0) {
                    result = this.stdRandom.nextInt(mFightVoice.size() - 1);
                    if (result == currentTextPos) {
                        getRandomIndex(tab, currentTextPos);
                        break;
                    }
                }
                break;
        }
        return result;
    }

    private void updateTab(int index) {
        currentFigureIndex = index;
        switch (index) {
            case 0:
                this.mFigureTab0.setSelected(true);
                this.mFigureTab1.setSelected(false);
                this.mFigureTab2.setSelected(false);
                break;
            case 1:
                this.mFigureTab0.setSelected(false);
                this.mFigureTab1.setSelected(true);
                this.mFigureTab2.setSelected(false);
                break;
            case 2:
                this.mFigureTab0.setSelected(false);
                this.mFigureTab1.setSelected(false);
                this.mFigureTab2.setSelected(true);
                break;
        }
        updatePicDesc();
    }

    private void loadData() {
        List<File> pics = FileUtils.getFiles(this.mContext, StoreUtils.VIDEOSHOT_PIC_TEMP_PATH);
        LogInfo.log("fornia", "pics:" + pics);
        if (pics != null && pics.size() != 0) {
            LogInfo.log("fornia", "pics:" + pics.size());
            for (int i = pics.size() - 1; i >= 0; i--) {
                File file = (File) pics.get(i);
                if (file.length() > 0) {
                    this.photoFileName.getPhoto().add(new PhotoInfoBean(file.getAbsolutePath(), ""));
                }
            }
        }
    }

    private void setupJazziness(TransitionEffect effect) {
        this.jazzy.setTransitionEffect(effect);
        this.jazzy.setAdapter(new MainAdapter());
        if (this.beforeDelIndex == -1) {
            if (this.photoFileName.getPhoto().size() > 1) {
                this.currentIndex = 1;
            } else if (this.photoFileName.getPhoto().size() == 1) {
                this.currentIndex = 0;
            }
            this.jazzy.setCurrentItem(this.currentIndex);
            this.lastIndex = this.currentIndex;
        } else {
            this.jazzy.setCurrentItem(this.beforeDelIndex);
            this.lastIndex = this.beforeDelIndex;
        }
        this.jazzy.setPageMargin(0);
        this.jazzy.setPagingEnabled(true);
        this.jazzy.setHandler(this.previewHandler);
        LogInfo.log("fornia", "setupJazziness jazzy.getChildCount():" + this.jazzy.getChildCount());
        updatePhotoPos();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mIVArray != null && this.mIVArray.size() > 0) {
            for (ImageView iv : this.mIVArray) {
                if (iv != null) {
                    Drawable recycleDw = iv.getDrawable();
                    if (recycleDw != null && (recycleDw instanceof RoundedPagerDrawable)) {
                        Bitmap recycleBp = ((RoundedPagerDrawable) recycleDw).getSourceBitmap();
                        if (!(recycleBp == null || recycleBp.isRecycled())) {
                            LogInfo.log("fornia", "setupJazziness 333jazzy.getByteCount():" + recycleBp.getByteCount());
                            recycleBp.recycle();
                            recycleDw.setCallback(null);
                        }
                    }
                }
            }
            this.mIVArray.clear();
        }
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }

    private void updatePhotoPos() {
        if (this.photoFileName.getPhoto().size() == 1) {
            this.currentPhotoPos = this.jazzy.getCurrentItem();
            this.mVideoshotPos.setText((this.currentPhotoPos + 1) + "/" + this.photoFileName.getPhoto().size());
            if (this.isOnCreate) {
                this.isOnCreate = false;
            } else {
                LogInfo.log("fornia", "VIDEOSHOT_VIEWPAGER_FLIP isContentEdited = true" + this.jazzy.getChildCount());
                this.isContentEdited = true;
                if (!TextUtils.isEmpty(((PhotoInfoBean) this.photoFileName.getPhoto().get(this.jazzy.getCurrentItem())).getPhotoDesc())) {
                    this.mDescEditText.setText(((PhotoInfoBean) this.photoFileName.getPhoto().get(this.jazzy.getCurrentItem())).getPhotoDesc());
                }
            }
            this.lastdesc = ((PhotoInfoBean) this.photoFileName.getPhoto().get(this.currentPhotoPos)).getPhotoDesc();
        } else if (this.photoFileName.getPhoto().size() > 1) {
            this.currentPhotoPos = this.jazzy.getCurrentItem() - 1;
            this.mVideoshotPos.setText((this.currentPhotoPos + 1) + "/" + this.photoFileName.getPhoto().size());
            if (this.isOnCreate) {
                this.isOnCreate = false;
            } else {
                LogInfo.log("fornia", "VIDEOSHOT_VIEWPAGER_FLIP isContentEdited = true" + this.jazzy.getChildCount());
                this.isContentEdited = true;
                if (!TextUtils.isEmpty(((PhotoInfoBean) this.photoFileName.getPhoto().get(this.currentPhotoPos)).getPhotoDesc())) {
                    this.mDescEditText.setText(((PhotoInfoBean) this.photoFileName.getPhoto().get(this.currentPhotoPos)).getPhotoDesc());
                }
            }
            this.lastdesc = ((PhotoInfoBean) this.photoFileName.getPhoto().get(this.currentPhotoPos)).getPhotoDesc();
        }
    }

    private void setLastPhotoDesc() {
        if (this.photoFileName.getPhoto().size() == 1) {
            ((PhotoInfoBean) this.photoFileName.getPhoto().get(0)).setPhotoDesc(this.lastdesc);
        } else if (this.photoFileName.getPhoto().size() > 0 && this.currentPhotoPos < this.photoFileName.getPhoto().size()) {
            ((PhotoInfoBean) this.photoFileName.getPhoto().get(this.currentPhotoPos)).setPhotoDesc(this.lastdesc);
            Log.i("fornia", "lastIndex:" + this.lastIndex + "|lastdesc:" + this.lastdesc);
        }
    }

    private void setCurrentPhotoDesc(String desc) {
        if (this.photoFileName.getPhoto().size() == 1) {
            ((PhotoInfoBean) this.photoFileName.getPhoto().get(0)).setPhotoDesc(desc);
        } else if (this.photoFileName.getPhoto().size() > 0 && this.currentPhotoPos < this.photoFileName.getPhoto().size()) {
            ((PhotoInfoBean) this.photoFileName.getPhoto().get(this.currentPhotoPos)).setPhotoDesc(desc);
        }
    }

    private void updateInputBlurBg() {
        this.currentIndex = this.jazzy.getCurrentItem();
        LogInfo.log("fornia", "updateInputBlurBg jazzy.getChildCount():" + this.jazzy.getChildCount() + "currentIndex:" + this.currentIndex);
        View view = null;
        if (this.isDeleted) {
            if (this.photoFileName.getPhoto().size() == 2 && this.currentIndex == 1) {
                view = this.jazzy.getChildAt(1);
            } else if (this.photoFileName.getPhoto().size() == 2 && this.currentIndex == 2) {
                LogInfo.log("fornia", "printjazzy 删除 没有滑动过的情况");
                view = this.jazzy.getChildAt(1);
            } else if (this.currentIndex == 1 && (!this.isJazzyFlipped || this.maxIndex <= 2)) {
                LogInfo.log("fornia", "printjazzy 删除 没有滑动过的情况");
                view = this.jazzy.getChildAt(1);
            } else if (this.currentIndex == 1 && this.isJazzyFlipped && this.maxIndex > 2 && this.jazzy.getChildCount() > 1) {
                LogInfo.log("fornia", "printjazzy 删除  滑动过的情况");
                view = this.jazzy.getChildAt(1);
            } else if (this.jazzy.getChildCount() > 2) {
                view = this.jazzy.getChildAt(this.jazzy.getChildCount() / 2);
            }
        } else if (this.photoFileName.getPhoto().size() == 2 && this.currentIndex == 1) {
            view = this.jazzy.getChildAt(1);
        } else if (this.photoFileName.getPhoto().size() == 2 && this.currentIndex == 2) {
            LogInfo.log("fornia", "printjazzy 删除 没有滑动过的情况");
            view = this.jazzy.getChildAt(1);
        } else if (this.currentIndex == 1 && (!this.isJazzyFlipped || this.maxIndex <= 2)) {
            LogInfo.log("fornia", "printjazzy 没有滑动过的情况");
            view = this.jazzy.getChildAt(1);
        } else if (this.currentIndex == 1 && this.isJazzyFlipped && this.maxIndex > 2) {
            LogInfo.log("fornia", "printjazzy 滑动过的情况");
            view = this.jazzy.getChildAt(1);
        } else if (!this.isOrderChanged && this.currentIndex == 1) {
            view = this.jazzy.getChildAt(1);
        } else if (this.isOrderChanged && this.currentIndex == 1) {
            view = this.jazzy.getChildAt(1);
        } else if (!this.isOrderChanged && this.currentIndex == this.photoFileName.getPhoto().size() - 1) {
            view = this.jazzy.getChildAt(1);
        } else if (this.isOrderChanged && this.currentIndex == this.photoFileName.getPhoto().size() - 1) {
            view = this.jazzy.getChildAt(0);
        } else if (this.currentIndex == this.photoFileName.getPhoto().size()) {
            view = this.jazzy.getChildAt(1);
        } else if (this.jazzy.getChildCount() == 3 && !this.isOrderChanged) {
            view = this.jazzy.getChildAt(1);
        } else if (this.isOrderChanged && !this.isLargerMode && this.jazzy.getChildCount() == 3) {
            view = this.jazzy.getChildAt(0);
        } else if (this.isOrderChanged && this.isLargerMode && this.jazzy.getChildCount() == 3) {
            view = this.jazzy.getChildAt(1);
        }
        if (view != null) {
            LogInfo.log("fornia", "updateInputBlurBg view getTag:" + view.getTag());
            int width0 = view.getMeasuredWidth();
            int height0 = view.getMeasuredHeight();
            int width1 = this.mDescEditText.getMeasuredWidth();
            int height1 = this.mDescEditText.getMeasuredHeight();
            int[] location0 = new int[2];
            view.getLocationOnScreen(location0);
            int[] location1 = new int[2];
            this.mDescEditText.getLocationOnScreen(location1);
            if (((RoundedImageView) view).getDrawable() != null) {
                Bitmap bgBitmap = ((RoundedPagerDrawable) ((RoundedImageView) view).getDrawable()).getSourceBitmap();
                if (bgBitmap != null) {
                    float widthScale = (((float) width0) * 1.0f) / ((float) bgBitmap.getWidth());
                    float heightScale = (((float) height0) * 1.0f) / ((float) bgBitmap.getHeight());
                    LogInfo.log("fornia", "updateInputBlurBg bgBitmap:" + bgBitmap + "bgBitmap:" + bgBitmap.getWidth() + "|" + bgBitmap.getHeight() + "|width0:" + width0 + "|height0" + height0 + "location:" + location0[0] + "|" + location0[1] + "|" + location1[0] + "|" + location1[1]);
                    BlurUtils.blurAdvanced(this.mContext, FileUtils.clipRectBitmap(bgBitmap, Math.round(((float) (location1[0] - location0[0])) / widthScale), Math.round(((float) (location1[1] - location0[1])) / heightScale), Math.round(((float) width1) / widthScale), Math.round(((float) height1) / heightScale)), this.mDescEditText);
                }
            }
        }
    }

    private Drawable getVoiceItemDrawable(int position) {
        if (position < 0 || position > 2) {
            return null;
        }
        if (!this.isHasCustomHead) {
            return getResources().getDrawable(this.FIGURE_VOICE_DRAWABLE[position]);
        }
        switch (position) {
            case 0:
                return FileUtils.createDrawableByName(this.mContext, this.mVideoShotPicDownloadDir + VIDEOSHOT_PIC_TAB_1_TEXT);
            case 1:
                return FileUtils.createDrawableByName(this.mContext, this.mVideoShotPicDownloadDir + VIDEOSHOT_PIC_TAB_2_TEXT);
            case 2:
                return FileUtils.createDrawableByName(this.mContext, this.mVideoShotPicDownloadDir + VIDEOSHOT_PIC_TAB_3_TEXT);
            default:
                return null;
        }
    }

    private Drawable getHeadItemDrawable(int position) {
        if (position < 0 || position > 2) {
            return null;
        }
        if (!this.isHasCustomHead) {
            return getResources().getDrawable(this.FIGURE_SHOW_DRAWABLE[position]);
        }
        switch (position) {
            case 0:
                return FileUtils.createDrawableByName(this.mContext, this.mVideoShotPicDownloadDir + VIDEOSHOT_PIC_TAB_1_HEAD);
            case 1:
                return FileUtils.createDrawableByName(this.mContext, this.mVideoShotPicDownloadDir + VIDEOSHOT_PIC_TAB_2_HEAD);
            case 2:
                return FileUtils.createDrawableByName(this.mContext, this.mVideoShotPicDownloadDir + VIDEOSHOT_PIC_TAB_3_HEAD);
            default:
                return null;
        }
    }

    private Drawable getSwitchDrawable(int position) {
        if (position < 0 || position > 2) {
            return null;
        }
        switch (position) {
            case 0:
            case 2:
                return getResources().getDrawable(2130839002);
            case 1:
                return getResources().getDrawable(2130839001);
            default:
                return getResources().getDrawable(2130839002);
        }
    }

    private RoundedImageView createImageViewFromFile(int position, String filename) {
        if (!FileUtils.checkFileIsEnabledPath(filename)) {
            return null;
        }
        Options newOpts = new Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(filename, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        int be = 1;
        if (w > h && ((float) w) > 300.0f) {
            be = (int) (((float) newOpts.outWidth) / 300.0f);
        } else if (w < h && ((float) h) > 400.0f) {
            be = (int) (((float) newOpts.outHeight) / 400.0f);
        }
        if (be <= 0) {
            be = 1;
        }
        Options newOpts2 = new Options();
        newOpts2.inSampleSize = be;
        newOpts2.inJustDecodeBounds = false;
        Bitmap bitmap2 = BitmapFactory.decodeFile(filename, newOpts2);
        RoundedImageView imageView = (RoundedImageView) this.mInflater.inflate(R.layout.videoshot_rounded_item, null, false).findViewById(R.id.riv_imageView);
        imageView.setTag(Integer.valueOf(position));
        imageView.setImageBitmap(bitmap2);
        if (this.mIVArray != null) {
            this.mIVArray.add(imageView);
            return imageView;
        }
        this.mIVArray = new ArrayList();
        this.mIVArray.add(imageView);
        return imageView;
    }

    public void share() {
        if (this.currentPhotoPos < this.photoFileName.getPhoto().size() && this.vsShareInfoBean != null) {
            String str;
            String randText = ((PhotoInfoBean) this.photoFileName.getPhoto().get(this.currentPhotoPos)).getPhotoDesc();
            VideoShotShareInfoBean videoShotShareInfoBean = this.vsShareInfoBean;
            String str2 = ((PhotoInfoBean) this.photoFileName.getPhoto().get(this.currentPhotoPos)).photoPath;
            if (TextUtils.isEmpty(this.vsShareInfoBean.mVideoName)) {
                str = "";
            } else {
                str = this.vsShareInfoBean.mVideoName + " " + (this.vsShareInfoBean.mVideoBean == null ? "" : this.vsShareInfoBean.mVideoBean.episode);
            }
            videoShotShareInfoBean.mPhotoPath = getFileAddedHybridWatermark(str2, 2130838567, str, randText);
            VideoShotShareInfoBean videoShotShareInfoBean2 = this.vsShareInfoBean;
            if (TextUtils.isEmpty(randText)) {
                randText = getResources().getString(2131099925);
            }
            videoShotShareInfoBean2.mRandText = randText;
            if (this.mHalfPlaySharePopwindow == null) {
                ShareUtils.RequestShareLink(this.mContext);
                this.mHalfPlaySharePopwindow = new HalfPlaySharePopwindow(this, 4);
                this.mHalfPlaySharePopwindow.setVideoShotData(this.vsShareInfoBean);
                this.mHalfPlaySharePopwindow.showPopupWindow(this.mRootLayout);
                this.mHalfPlaySharePopwindow.setOnDismissListener(new OnDismissListener(this) {
                    final /* synthetic */ VideoShotEditActivity this$0;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$0 = this$0;
                    }

                    public void onDismiss() {
                        this.this$0.mHalfPlaySharePopwindow = null;
                    }
                });
            }
        }
    }

    private String getFileAddedWatermark(String path, String text, String voice) {
        if (!new File(path).exists()) {
            return null;
        }
        Options options = new Options();
        options.inJustDecodeBounds = false;
        Bitmap bitmap0 = BitmapFactory.decodeFile(path, options);
        int width = options.outWidth;
        int height = options.outHeight;
        Bitmap bitmapPic = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapPic);
        canvas.drawBitmap(bitmap0, null, new Rect(0, 0, width, height), null);
        Paint textPaint = new Paint();
        textPaint.setColor(-1);
        textPaint.setTextSize((float) getTextSize());
        textPaint.setFlags(2);
        canvas.drawText(text, 0, text.length(), 30.0f, 50.0f, textPaint);
        Rect rect = new Rect(0, height - getTextBgHeight(), width, height);
        if (!isOrigPicMode) {
            Paint voiceBgPaint = new Paint();
            voiceBgPaint.setColor(getResources().getColor(2131493164));
            voiceBgPaint.setStyle(Style.FILL);
            voiceBgPaint.setFlags(2);
            canvas.drawRect(rect, voiceBgPaint);
            voiceBgPaint.setColor(-1);
            voiceBgPaint.setTextSize((float) getTextSize());
            voiceBgPaint.setTextAlign(Align.CENTER);
            FontMetricsInt fontMetrics = voiceBgPaint.getFontMetricsInt();
            int baseline = (rect.top + ((((rect.bottom - rect.top) - fontMetrics.bottom) + fontMetrics.top) / 2)) - fontMetrics.top;
            canvas.drawText(voice, (float) rect.centerX(), (float) baseline, voiceBgPaint);
        }
        return saveBitmap(bitmapPic);
    }

    private String getFileAddedHybridWatermark(String path, int logoResId, String text, String voice) {
        if (!new File(path).exists()) {
            return null;
        }
        Options options = new Options();
        options.inJustDecodeBounds = false;
        Bitmap bitmap0 = BitmapFactory.decodeFile(path, options);
        int width = options.outWidth;
        int height = options.outHeight;
        Bitmap bitmapPic = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapPic);
        canvas.drawBitmap(bitmap0, null, new Rect(0, 0, width, height), null);
        Bitmap bitmapLogo = ((BitmapDrawable) getResources().getDrawable(logoResId)).getBitmap();
        int widthLogo = bitmapLogo.getWidth();
        int heightLogo = bitmapLogo.getHeight();
        float scale = getScaleForShare();
        int widthOffset = (int) (((float) widthLogo) * scale);
        int heightOffset = (int) (((float) heightLogo) * scale);
        LogInfo.log("fornia", "widthLogo:" + widthLogo + "|heightLogo:" + heightLogo + "scale:" + scale + "text:" + text);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        LogInfo.log("fornia", "widthLogo:" + widthLogo + "|heightLogo:" + heightLogo + "widthOffset:" + widthOffset + "|heightOffset:" + heightOffset);
        if (bitmapLogo != null) {
            canvas.drawBitmap(Bitmap.createBitmap(bitmapLogo, 0, 0, widthLogo, heightLogo, matrix, true), 30.0f, 25.0f, null);
        }
        if (!TextUtils.isEmpty(text)) {
            Paint textPaint = new Paint();
            textPaint.setColor(-1);
            textPaint.setTextSize((float) getTextSize());
            textPaint.setFlags(2);
            canvas.drawText(text, 0, text.length(), (float) (widthOffset + 42), (float) (heightOffset + 22), textPaint);
        }
        Rect rect = new Rect(0, height - getTextBgHeight(), width, height);
        if (!isOrigPicMode) {
            Paint voiceBgPaint = new Paint();
            voiceBgPaint.setColor(getResources().getColor(2131493164));
            voiceBgPaint.setStyle(Style.FILL);
            voiceBgPaint.setFlags(2);
            canvas.drawRect(rect, voiceBgPaint);
            voiceBgPaint.setColor(-1);
            voiceBgPaint.setTextSize((float) getTextSize());
            voiceBgPaint.setTextAlign(Align.CENTER);
            FontMetricsInt fontMetrics = voiceBgPaint.getFontMetricsInt();
            canvas = canvas;
            String str = voice;
            canvas.drawText(str, (float) rect.centerX(), (float) ((rect.top + ((((rect.bottom - rect.top) - fontMetrics.bottom) + fontMetrics.top) / 2)) - fontMetrics.top), voiceBgPaint);
        }
        return saveBitmap(bitmapPic);
    }

    private float getScaleForShare() {
        float density = BaseApplication.getInstance().getResources().getDisplayMetrics().density;
        return 3.0f / density >= 1.0f ? 1.2f : 3.0f / density;
    }

    private int getTextBgHeight() {
        if (UIsUtils.dipToPx(TitleBar.BACKBTN_LEFT_MARGIN) < VIDEOSHOT_WATERMARK_BG_HEIGHT_DEFAULT_PX) {
            return VIDEOSHOT_WATERMARK_BG_HEIGHT_DEFAULT_PX;
        }
        return UIsUtils.dipToPx(TitleBar.BACKBTN_LEFT_MARGIN);
    }

    private int getTextSize() {
        if (UIsUtils.dipToPx(8.0f) < 26) {
            return 26;
        }
        return UIsUtils.dipToPx(8.0f);
    }

    private boolean saveFileAddedVoice(String path, String voice, String savePath) {
        LogInfo.log("fornia", "voice:" + voice);
        if (!new File(path).exists()) {
            return false;
        }
        Options options = new Options();
        options.inJustDecodeBounds = false;
        Bitmap bitmap0 = BitmapFactory.decodeFile(path, options);
        int width = options.outWidth;
        int height = options.outHeight;
        Bitmap bitmapPic = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapPic);
        canvas.drawBitmap(bitmap0, null, new Rect(0, 0, width, height), null);
        Rect targetBgRect = new Rect(0, height - getTextBgHeight(), width, height);
        if (!isOrigPicMode) {
            Paint voiceBgPaint = new Paint();
            voiceBgPaint.setColor(getResources().getColor(2131493164));
            voiceBgPaint.setStyle(Style.FILL);
            voiceBgPaint.setFlags(2);
            canvas.drawRect(targetBgRect, voiceBgPaint);
            voiceBgPaint.setColor(-1);
            voiceBgPaint.setTextSize((float) getTextSize());
            voiceBgPaint.setTextAlign(Align.CENTER);
            FontMetricsInt fontMetrics = voiceBgPaint.getFontMetricsInt();
            String str = voice;
            canvas.drawText(str, (float) targetBgRect.centerX(), (float) ((targetBgRect.top + ((((targetBgRect.bottom - targetBgRect.top) - fontMetrics.bottom) + fontMetrics.top) / 2)) - fontMetrics.top), voiceBgPaint);
        }
        return FileUtils.saveBitmapByUser(this.mContext, bitmapPic);
    }

    private String saveBitmap(Bitmap bp) {
        String fileName = StoreUtils.getLocalRestoreNomediaPath(this, StoreUtils.VIDEOSHOT_PIC_WATERMARK_PATH) + LetvDateUtils.getFormatPhotoName(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())) + ".jpg";
        if (!FileUtils.saveBitmap2File(this.mContext, fileName, bp)) {
            return null;
        }
        LogInfo.log("fornia", "getFileAddedWatermark saveBitmap fileName" + fileName);
        return fileName;
    }

    private void showConfirmDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_custom_notice_dialog, null);
        this.editNoticeDialog = new MyDialog(this, view, R.style.dialog);
        this.editNoticeDialog.setCanceledOnTouchOutside(true);
        TextView okBtn = (TextView) view.findViewById(R.id.okBtn);
        TextView cancelBtn = (TextView) view.findViewById(R.id.cancelBtn);
        TextView messageView = (TextView) view.findViewById(2131362362);
        okBtn.setText(getResources().getString(2131100151));
        cancelBtn.setText(getResources().getString(2131101087));
        messageView.setText(getResources().getString(2131101090));
        okBtn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ VideoShotEditActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                if (this.this$0.editNoticeDialog != null) {
                    this.this$0.editNoticeDialog.dismiss();
                }
                this.this$0.finish();
            }
        });
        cancelBtn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ VideoShotEditActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                if (this.this$0.editNoticeDialog != null) {
                    this.this$0.editNoticeDialog.dismiss();
                }
            }
        });
        this.editNoticeDialog.show();
    }

    public void onClick(View v) {
        boolean z = true;
        switch (v.getId()) {
            case 2131361881:
                hideSoftKeyboard();
                return;
            case R.id.iv_delete /*2131362080*/:
                hideSoftKeyboard();
                if (this.isDelEnable) {
                    this.isDelEnable = false;
                    if (this.photoFileName.getPhoto().size() == 1) {
                        ToastUtils.showToast(2131101088);
                        this.isDelEnable = true;
                        return;
                    }
                    this.currentIndex = this.jazzy.getCurrentItem();
                    this.beforeDelIndex = this.currentIndex;
                    LogInfo.log("fornia", "jazzy.getChildCount():" + this.jazzy.getChildCount() + "");
                    View view = null;
                    if (this.isDeleted) {
                        if (this.photoFileName.getPhoto().size() == 2 && this.currentIndex == 1) {
                            view = this.jazzy.getChildAt(1);
                        } else if (this.photoFileName.getPhoto().size() == 2 && this.currentIndex == 2) {
                            LogInfo.log("fornia", "printjazzy 删除 没有滑动过的情况");
                            view = this.jazzy.getChildAt(1);
                        } else if (this.currentIndex == 1 && (!this.isJazzyFlipped || this.maxIndex <= 2)) {
                            LogInfo.log("fornia", "printjazzy 删除 没有滑动过的情况");
                            view = this.jazzy.getChildAt(1);
                        } else if (this.currentIndex == 1 && this.isJazzyFlipped && this.maxIndex > 2 && this.jazzy.getChildCount() > 1) {
                            LogInfo.log("fornia", "printjazzy 删除  滑动过的情况");
                            view = this.jazzy.getChildAt(1);
                        } else if (this.jazzy.getChildCount() > 2) {
                            view = this.jazzy.getChildAt(this.jazzy.getChildCount() / 2);
                        }
                    } else if (this.photoFileName.getPhoto().size() == 2 && this.currentIndex == 1) {
                        view = this.jazzy.getChildAt(1);
                    } else if (this.photoFileName.getPhoto().size() == 2 && this.currentIndex == 2) {
                        LogInfo.log("fornia", "printjazzy 删除 没有滑动过的情况");
                        view = this.jazzy.getChildAt(1);
                    } else if (this.currentIndex == 1 && (!this.isJazzyFlipped || this.maxIndex <= 2)) {
                        LogInfo.log("fornia", "printjazzy 没有滑动过的情况");
                        view = this.jazzy.getChildAt(1);
                    } else if (this.currentIndex == 1 && this.isJazzyFlipped && this.maxIndex > 2) {
                        LogInfo.log("fornia", "printjazzy 滑动过的情况");
                        view = this.jazzy.getChildAt(1);
                    } else if (!this.isOrderChanged && this.currentIndex == 1) {
                        view = this.jazzy.getChildAt(1);
                    } else if (this.isOrderChanged && this.currentIndex == 1) {
                        view = this.jazzy.getChildAt(1);
                    } else if (!this.isOrderChanged && this.currentIndex == this.photoFileName.getPhoto().size() - 1) {
                        view = this.jazzy.getChildAt(1);
                    } else if (this.isOrderChanged && this.currentIndex == this.photoFileName.getPhoto().size() - 1) {
                        view = this.jazzy.getChildAt(0);
                    } else if (this.currentIndex == this.photoFileName.getPhoto().size()) {
                        view = this.jazzy.getChildAt(1);
                    } else if (this.jazzy.getChildCount() == 3 && !this.isOrderChanged) {
                        view = this.jazzy.getChildAt(1);
                    } else if (this.isOrderChanged && !this.isLargerMode && this.jazzy.getChildCount() == 3) {
                        view = this.jazzy.getChildAt(0);
                    } else if (this.isOrderChanged && this.isLargerMode && this.jazzy.getChildCount() == 3) {
                        view = this.jazzy.getChildAt(1);
                    }
                    if (view != null) {
                        deleteAnim(view);
                        return;
                    }
                    return;
                }
                ToastUtils.showToast(2131101079);
                return;
            case R.id.iv_download /*2131362081*/:
                hideSoftKeyboard();
                LogInfo.log("fornia", "iv_download" + this.currentPhotoPos + this.photoFileName.getPhoto().size());
                if (this.currentPhotoPos >= 0 && this.currentPhotoPos < this.photoFileName.getPhoto().size()) {
                    String name = FileUtils.getFileName(((PhotoInfoBean) this.photoFileName.getPhoto().get(this.currentPhotoPos)).photoPath);
                    StoreUtils.VIDEOSHOT_PIC_SAVE_PATH = StoreUtils.SHARE_PATH + getResources().getString(2131099758) + File.separator;
                    if (saveFileAddedVoice(((PhotoInfoBean) this.photoFileName.getPhoto().get(this.currentPhotoPos)).photoPath, ((PhotoInfoBean) this.photoFileName.getPhoto().get(this.currentPhotoPos)).getPhotoDesc(), StoreUtils.getLocalRestorePath(this.mContext, StoreUtils.VIDEOSHOT_PIC_SAVE_PATH) + name)) {
                        ToastUtils.showToast(2131101083);
                        return;
                    }
                    return;
                }
                return;
            case R.id.tv_videoshot_original /*2131362082*/:
            case R.id.iv_orig_check /*2131362083*/:
                hideSoftKeyboard();
                if (isOrigPicMode) {
                    z = false;
                }
                isOrigPicMode = z;
                this.mUseOrigPic.setSelected(isOrigPicMode);
                if (isOrigPicMode) {
                    this.mRLPopupInput.setVisibility(8);
                    return;
                } else {
                    this.mRLPopupInput.setVisibility(0);
                    return;
                }
            case R.id.iv_tab1 /*2131362086*/:
                this.mViewPager.setCurrentItem(1);
                return;
            case R.id.iv_tab0 /*2131362087*/:
                this.mViewPager.setCurrentItem(0);
                return;
            case R.id.iv_tab2 /*2131362088*/:
                this.mViewPager.setCurrentItem(2);
                return;
            case 2131362351:
                LogInfo.log("fornia", "common_nav_left onClick" + this.isContentEdited);
                if (this.isContentEdited) {
                    showConfirmDialog();
                    return;
                } else {
                    finish();
                    return;
                }
            case 2131362354:
                hideSoftKeyboard();
                share();
                return;
            default:
                return;
        }
    }

    private void hideSoftKeyboard() {
        this.isKeyboardOpened = false;
        ((InputMethodManager) getApplicationContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mRootLayout.getWindowToken(), 0);
        resumeEditTextStatus();
    }

    private void resumeEditTextStatus() {
        startFlashAnim();
        this.mDescEditText.setCursorVisible(false);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 4:
                if (this.isContentEdited) {
                    showConfirmDialog();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}
