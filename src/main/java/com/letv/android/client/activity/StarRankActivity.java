package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.StarRankAdapter;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.parser.StarRankParser;
import com.letv.android.client.view.pullzoom.ObservableExpandableListView;
import com.letv.android.client.view.pullzoom.ObservableScrollViewCallbacks;
import com.letv.android.client.view.pullzoom.PullToZoomBase.OnPullZoomListener;
import com.letv.android.client.view.pullzoom.ScrollState;
import com.letv.android.client.view.pullzoom.ScrollUtils;
import com.letv.android.client.widget.StarRuleDialog;
import com.letv.android.client.widget.StarRuleDialog.StarRuleDialogListener;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.DataHull;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloadStateListener;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.download.image.ImageDownloader.BitmapStyle;
import com.letv.core.download.image.ImageDownloader.CustomConfig;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.pb.StarRankPBParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.BlurUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.ltpbdata.LTStarRankModelDetailPBPKGOuterClass.LTStarRankModelDetailPB;
import com.letv.ltpbdata.LTStarRankModelDetailPBPKGOuterClass.LTStarRankModelDetailPBPKG;
import com.letv.ltpbdata.LTStarRankModelDetailPBPKGOuterClass.LTStarRankModelPB;

public class StarRankActivity extends LetvBaseActivity implements ObservableScrollViewCallbacks, OnClickListener, OnPullZoomListener {
    private ImageView mBtnBack;
    private ImageView mBtnBackTransparent;
    private String mDate;
    private String mDateID;
    private StarRuleDialog mDialog;
    private RelativeLayout mFootSerchView;
    private Handler mHandler;
    private ImageView mHeadImage;
    private boolean mIsPullZooming;
    private boolean mIsRefrence;
    private String mLastHeadUrl;
    private ObservableExpandableListView mListView;
    private ImageView mLoadingView;
    private Animation mOperatingAnim;
    private PublicLoadLayout mRootView;
    OnScrollListener mScrollEvent;
    private Runnable mScrollRunnable;
    private RelativeLayout mSearchView;
    private StarRankAdapter mStarRankAdapter;
    private TextView mTextTip;
    private TextView mTitle;
    private int mTitleBgColor;
    private int mTitleColor;
    private View mTitleLine;
    private TextView mTitleWhite;
    private View mTtitleLayout;
    private int mZoomHeight;
    private ImageView mZoomImg;
    private TextView oldStarRank;
    private TextView starCrown;
    private TextView starRankRule;

    public StarRankActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mScrollRunnable = null;
        this.mScrollEvent = new OnScrollListener(this) {
            final /* synthetic */ StarRankActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (this.this$0.mStarRankAdapter instanceof OnScrollListener) {
                    this.this$0.mStarRankAdapter.onScrollStateChanged(view, scrollState);
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (this.this$0.mStarRankAdapter instanceof OnScrollListener) {
                    this.this$0.mStarRankAdapter.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        };
    }

    public static void launch(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, StarRankActivity.class));
        }
    }

    public static void launch(Context context, String dateId, String date) {
        if (context != null) {
            Intent intent = new Intent(context, StarRankActivity.class);
            intent.putExtra("dateId", dateId);
            intent.putExtra("date", date);
            context.startActivity(intent);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_ranking);
        initData();
        findView();
        initListView();
        getStarRankList(false);
    }

    private void findView() {
        this.mRootView = (PublicLoadLayout) findViewById(R.id.star_rank_load);
        this.mTitleLine = findViewById(R.id.top_navigation_line);
        this.mTtitleLayout = findViewById(R.id.star_title_lt);
        this.mBtnBackTransparent = (ImageView) findViewById(R.id.star_btn_back0);
        this.mBtnBack = (ImageView) findViewById(R.id.star_btn_back);
        this.mBtnBackTransparent.setOnClickListener(this);
        this.mBtnBack.setOnClickListener(this);
        this.mTitle = (TextView) findViewById(R.id.star_title);
        this.mTitleWhite = (TextView) findViewById(R.id.star_title_white);
        this.mListView = (ObservableExpandableListView) findViewById(R.id.star_rank_list);
        this.mLoadingView = (ImageView) findViewById(2131361884);
        this.mHeadImage = (ImageView) findViewById(R.id.star_ranking_head);
        this.mZoomImg = (ImageView) findViewById(R.id.zoom_iv);
        this.starCrown = (TextView) findViewById(R.id.star_crown);
        this.starCrown.bringToFront();
        if (TextUtils.isEmpty(this.mDate)) {
            this.oldStarRank = (TextView) findViewById(R.id.old_ranking);
            this.oldStarRank.setOnClickListener(this);
            this.starRankRule = (TextView) findViewById(R.id.star_ranking_rule);
            this.starRankRule.setVisibility(0);
            this.starRankRule.setOnClickListener(this);
        }
        if (TextUtils.isEmpty(this.mDate)) {
            this.mFootSerchView = PublicLoadLayout.createPage(this.mContext, (int) R.layout.star_ranking_footview, true);
            this.mTextTip = (TextView) this.mFootSerchView.findViewById(R.id.foot_text1);
            this.mTextTip.setText(this.mContext.getResources().getString(2131100877).replace("#", "\n"));
            this.mSearchView = (RelativeLayout) this.mFootSerchView.findViewById(R.id.search_box_layout);
            this.mSearchView.setOnClickListener(this);
            ((ExpandableListView) this.mListView.getRootView()).addFooterView(this.mFootSerchView);
        }
        if (TextUtils.isEmpty(this.mDate)) {
            this.mTitle.setText(this.mContext.getResources().getString(2131100237));
            this.mTitleWhite.setText(this.mContext.getResources().getString(2131100237));
        } else {
            this.mTitle.setText(this.mDate);
            this.mTitleWhite.setText(this.mDate);
        }
        this.mRootView.setRefreshData(new RefreshData(this) {
            final /* synthetic */ StarRankActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.mRootView.setVisibility(0);
                this.this$0.mRootView.loading(false);
                this.this$0.getStarRankList(false);
                LogInfo.log("zhaoxiang", System.currentTimeMillis() + "");
            }
        });
        this.mTtitleLayout.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ StarRankActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        this.mRootView.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ StarRankActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        this.mListView.setOnScrollListener(this.mScrollEvent);
        this.mListView.setOnPullZoomListener(this);
        StatisticsUtils.staticticsInfoPost(this, "19", null, null, -1, null, PageIdConstant.startTopPage, null, null, null, PageIdConstant.startTopPage, null);
    }

    private void initData() {
        this.mZoomHeight = getResources().getDimensionPixelSize(2131165558);
        this.mTitleBgColor = getResources().getColor(2131493333);
        this.mTitleColor = getResources().getColor(2131493237);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.mDate = bundle.getString("date");
            this.mDateID = bundle.getString("dateId");
        }
        this.mOperatingAnim = AnimationUtils.loadAnimation(this, R.anim.star_refrence_loading);
        this.mOperatingAnim.setInterpolator(new LinearInterpolator());
    }

    private void initListView() {
        this.mStarRankAdapter = new StarRankAdapter(this.mContext);
        this.mListView.setAdapter(this.mStarRankAdapter);
        this.mListView.setHeaderLayoutParams(new LayoutParams(UIsUtils.getScreenWidth(), (int) (9.0f * (((float) UIsUtils.getScreenWidth()) / 16.0f))));
        this.mListView.setScrollViewCallbacks(this);
        ((ExpandableListView) this.mListView.getRootView()).setGroupIndicator(null);
        ((ExpandableListView) this.mListView.getRootView()).setOnGroupClickListener(null);
    }

    private void getStarRankList(boolean isReference) {
        if (!isReference) {
            this.mRootView.loading(false);
        }
        boolean pdOpen = PreferencesManager.getInstance().getBooleanProtoBuf();
        Volley.getQueue().cancelWithTag(getVolleyTag());
        new LetvRequest(LTStarRankModelDetailPBPKG.class).setCache(new VolleyDiskCache(getCacheFileName())).setIsPB(pdOpen).setUrl(LetvUrlMaker.getStarRankUrl(TextUtils.isEmpty(this.mDateID) ? "" : this.mDateID, "20")).setParser(pdOpen ? new StarRankPBParser() : new StarRankParser()).setCallback(new SimpleResponse<LTStarRankModelDetailPBPKG>(this) {
            final /* synthetic */ StarRankActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<LTStarRankModelDetailPBPKG> volleyRequest, LTStarRankModelDetailPBPKG result, DataHull hull, NetworkResponseState state) {
                if (state == NetworkResponseState.SUCCESS && result != null) {
                    this.this$0.refreshView(result.data);
                } else if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE || state == NetworkResponseState.NETWORK_ERROR) {
                    this.this$0.showTitleView(false);
                    this.this$0.mRootView.netError(false);
                } else if (state == NetworkResponseState.RESULT_ERROR) {
                    this.this$0.showTitleView(false);
                    this.this$0.mRootView.dataError(false);
                }
            }

            public void onCacheResponse(VolleyRequest<LTStarRankModelDetailPBPKG> volleyRequest, LTStarRankModelDetailPBPKG result, DataHull hull, CacheResponseState state) {
                if (state == CacheResponseState.SUCCESS && result != null) {
                    this.this$0.refreshView(result.data);
                }
            }

            public void onErrorReport(VolleyRequest<LTStarRankModelDetailPBPKG> volleyRequest, String errorInfo) {
            }
        }).add();
    }

    private void showTitleView(boolean requestSuccess) {
        int color;
        this.mTitleLine.setVisibility(requestSuccess ? 8 : 0);
        TextView textView = this.mTitleWhite;
        if (requestSuccess) {
            color = this.mContext.getResources().getColor(2131493377);
        } else {
            color = this.mContext.getResources().getColor(2131493246);
        }
        textView.setTextColor(color);
    }

    private void refreshView(LTStarRankModelPB result) {
        if (result == null || BaseTypeUtils.isListEmpty(result.data)) {
            LogInfo.log("zhaoxiang", System.currentTimeMillis() + "");
            showTitleView(false);
            this.mRootView.dataError(false);
            return;
        }
        showTitleView(true);
        this.mRootView.finishLayout();
        this.mLoadingView.clearAnimation();
        this.mLoadingView.setVisibility(8);
        this.mIsRefrence = false;
        if (this.mStarRankAdapter == null) {
            this.mStarRankAdapter = new StarRankAdapter(this.mContext);
        }
        if (this.oldStarRank != null) {
            if (TextUtils.equals(result.is_rank, "1")) {
                this.oldStarRank.setVisibility(0);
            } else {
                this.oldStarRank.setVisibility(8);
            }
        }
        if (BaseTypeUtils.getElementFromList(result.data, 0) != null) {
            addHeadView((LTStarRankModelDetailPB) result.data.get(0));
        }
        this.mListView.setAdapter(this.mStarRankAdapter);
        this.mStarRankAdapter.setList((ExpandableListView) this.mListView.getRootView(), result.data);
    }

    private void addHeadView(LTStarRankModelDetailPB rankingBean) {
        if (rankingBean != null && !TextUtils.equals(this.mLastHeadUrl, rankingBean.headimg)) {
            this.mLastHeadUrl = rankingBean.headimg;
            this.mHeadImage.setTag(2131361795, new CustomConfig(BitmapStyle.ROUND, 0));
            ImageDownloader.getInstance().download(this.mHeadImage, rankingBean.headimg, 2130837905);
            ImageDownloader.getInstance().download(rankingBean.headimg, new ImageDownloadStateListener(this) {
                final /* synthetic */ StarRankActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void loading() {
                }

                public void loadSuccess(Bitmap bitmap) {
                    this.this$0.mZoomImg.setImageBitmap(bitmap);
                    this.this$0.applyBlur(this.this$0.mZoomImg);
                }

                public void loadSuccess(Bitmap bitmap, String localPath) {
                }

                public void loadSuccess(View view, Bitmap bitmap, String localPath) {
                }

                public void loadFailed() {
                }
            });
        }
    }

    private void applyBlur(final ImageView iv) {
        iv.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener(this) {
            final /* synthetic */ StarRankActivity this$0;

            public boolean onPreDraw() {
                iv.getViewTreeObserver().removeOnPreDrawListener(this);
                iv.buildDrawingCache();
                BlurUtils.star_blur(this.this$0.mContext, iv.getDrawingCache(), this.this$0.mZoomImg);
                return true;
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.star_btn_back0 /*2131362042*/:
            case R.id.star_btn_back /*2131362043*/:
                finish();
                return;
            case R.id.search_box_layout /*2131362788*/:
                SearchMainActivity.launch(this.mContext, "");
                StatisticsUtils.staticticsInfoPost(this, "0", "rl03", null, 1, null, PageIdConstant.startTopPage, null, null, null, PageIdConstant.startTopPage, null);
                return;
            case R.id.star_ranking_rule /*2131364345*/:
                String rule = TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700068, 2131100987);
                if (rule.isEmpty()) {
                    ToastUtils.showToast(this.mContext, 2131100332);
                    return;
                }
                if (this.mDialog == null) {
                    this.mDialog = new StarRuleDialog(this.mContext, rule, R.layout.star_ranking_rule, 2131230964, new StarRuleDialogListener(this) {
                        final /* synthetic */ StarRankActivity this$0;

                        {
                            if (HotFix.PREVENT_VERIFY) {
                                System.out.println(VerifyLoad.class);
                            }
                            this.this$0 = this$0;
                        }

                        public void onClick(boolean isClose) {
                            this.this$0.mDialog.cancel();
                        }
                    });
                }
                this.mDialog.show();
                return;
            case R.id.old_ranking /*2131364346*/:
                StarOldRankListActivity.launch(this.mContext, 0);
                StatisticsUtils.staticticsInfoPost(this, "0", "rl01", null, 1, null, PageIdConstant.startTopPage, null, null, null, PageIdConstant.startTopPage, null);
                return;
            default:
                return;
        }
    }

    public void onPullZoomEnd() {
        this.mIsPullZooming = false;
        if (!this.mIsRefrence) {
            this.mLoadingView.clearAnimation();
            this.mLoadingView.setVisibility(8);
        }
        LogInfo.log("zhaoxiang", "onPullZoomEnd");
    }

    public void onPullZooming(int newScrollValue) {
        this.mIsPullZooming = true;
        Volley.getQueue().cancelWithTag(getVolleyTag());
    }

    private String getCacheFileName() {
        return "starRankActivity_cacheFileName_id" + (TextUtils.isEmpty(this.mDateID) ? "" : this.mDateID);
    }

    private String getVolleyTag() {
        return "starRankActivity_volleyTag";
    }

    public void onDownMotionEvent() {
    }

    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (TextUtils.isEmpty(this.mDate)) {
            this.mIsRefrence = false;
            if (scrollState == ScrollState.UP_REFRENCE) {
                if (this.mScrollRunnable != null) {
                    Volley.getQueue().cancelWithTag(getVolleyTag());
                    this.mHandler.removeCallbacks(this.mScrollRunnable);
                } else {
                    this.mScrollRunnable = new Runnable(this) {
                        final /* synthetic */ StarRankActivity this$0;

                        {
                            if (HotFix.PREVENT_VERIFY) {
                                System.out.println(VerifyLoad.class);
                            }
                            this.this$0 = this$0;
                        }

                        public void run() {
                            if (!this.this$0.mIsPullZooming) {
                                this.this$0.getStarRankList(true);
                            }
                        }
                    };
                }
                if (NetworkUtils.isNetworkAvailable()) {
                    this.mLoadingView.setVisibility(0);
                    this.mLoadingView.startAnimation(this.mOperatingAnim);
                    this.mIsRefrence = true;
                    this.mHandler.postDelayed(this.mScrollRunnable, 200);
                    return;
                }
                ToastUtils.showToast(getActivity(), 2131100332);
            }
        }
    }

    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (this.mRootView.getVisibility() != 0) {
            float alpha = Math.min(1.0f, ((float) scrollY) / ((float) this.mZoomHeight));
            this.mTtitleLayout.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, this.mTitleBgColor));
            this.mTitle.setTextColor(ScrollUtils.getColorWithAlpha(alpha, this.mTitleColor));
            this.mBtnBackTransparent.setAlpha(1.0f - alpha);
            this.mTitleWhite.setAlpha(1.0f - alpha);
            this.mBtnBack.setAlpha(alpha);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mDialog != null) {
            this.mDialog = null;
        }
        if (this.mOperatingAnim != null) {
            this.mLoadingView.clearAnimation();
        }
        Volley.getQueue().cancelWithTag(getVolleyTag());
        this.mListView.setOnScrollListener(null);
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return StarRankActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
