package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AbsListView.LayoutParams;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.LiveRoomAdapter;
import com.letv.android.client.adapter.StarAdapter;
import com.letv.android.client.adapter.StarFollowAdapter;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.utils.UIs;
import com.letv.android.client.view.SuperscriptSpanAdjuster;
import com.letv.android.client.view.pullzoom.ObservableExpandableListView;
import com.letv.android.client.view.pullzoom.ObservableScrollViewCallbacks;
import com.letv.android.client.view.pullzoom.ScrollState;
import com.letv.android.client.view.pullzoom.ScrollUtils;
import com.letv.business.flow.star.StarBookCallback;
import com.letv.business.flow.star.StarFlow;
import com.letv.business.flow.star.StarFlowCallback;
import com.letv.business.flow.star.StarFlowCallback.StarFollowType;
import com.letv.core.bean.FollowStatusMapBean;
import com.letv.core.bean.PlayVoteListBean.PlayVoteResultBean;
import com.letv.core.bean.StarBlockBean;
import com.letv.core.bean.StarFollowRankBean;
import com.letv.core.bean.StarInfoBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloadStateListener;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.download.image.ImageDownloader.BitmapStyle;
import com.letv.core.download.image.ImageDownloader.CustomConfig;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.BlurUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class StarActivity extends LetvBaseActivity implements ObservableScrollViewCallbacks, OnClickListener {
    private StarBookCallback mBooedkProgramsCallback;
    private ImageView mBtnBack;
    private ImageView mBtnBack0;
    private View mBtnStarVote;
    private StarFollowAdapter mFollowAdapter;
    private Button mFollowBtn;
    private int mFollowCount;
    private TextView mFollowNum;
    private TextView mHeadBirth;
    private TextView mHeadDesc;
    private ImageView mHeadImg;
    private TextView mHeadName;
    private boolean mIsFollowed;
    private boolean mIsVoted;
    private ObservableExpandableListView mListView;
    private LiveRoomAdapter mLiveAapter;
    private LinearLayout mLiveBlock;
    private PublicLoadLayout mRoot;
    private String mSkipUrl;
    private StarAdapter mStarAdapter;
    private ImageView mStarCms;
    private StarFlow mStarFlow;
    private StarFlowCallback mStarFlowCallback;
    private TextView mStarFollowHint;
    private GridView mStarFollowList;
    private View mStarFollowTitle;
    private String mStarId;
    private String mStarName;
    private TextView mStarRankMore;
    private TextView mStarRankNum;
    private View mStarRankTitleLt;
    private View mStarRankVoteLt;
    private TextView mStarRankVoteNum;
    private TextView mStarVote;
    private TextView mTitle;
    private int mTitleBgColor;
    private int mTitleColor;
    private View mTitleLine;
    private int mTitleLineColor;
    private View mTtitleLayout;
    private int mVoteCount;
    private int mZoomHeight;
    private ImageView mZoomImg;
    private String oldPageId;

    public StarActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mIsFollowed = false;
        this.mStarFlowCallback = new StarFlowCallback(this) {
            final /* synthetic */ StarActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onStarSuccess(StarInfoBean result) {
                if (result == null) {
                    this.this$0.mRoot.dataError(false);
                    return;
                }
                this.this$0.mRoot.finishLayout();
                this.this$0.changeAlpha(0.0f);
                this.this$0.drawHeader(result);
                this.this$0.mStarAdapter.setList(result.albumBlocks);
                this.this$0.initLiveBlock(result.albumBlocks);
                for (int i = 0; i < this.this$0.mStarAdapter.getGroupCount(); i++) {
                    ((ExpandableListView) this.this$0.mListView.getRootView()).expandGroup(i);
                }
            }

            public void netError(StarFollowType type) {
                if (type == StarFollowType.STAR_INFO) {
                    this.this$0.mRoot.netError(false);
                } else if (type == StarFollowType.FOLLOW || type == StarFollowType.VOTE) {
                    UIsUtils.showToast(2131100495);
                }
            }

            public void onVoteSuccess(PlayVoteResultBean result) {
                this.this$0.mBtnStarVote.setEnabled(true);
                if (result == null) {
                    this.this$0.mIsVoted = false;
                    UIsUtils.showToast(2131101136);
                    return;
                }
                Drawable drawable = this.this$0.getResources().getDrawable(2130838988);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                this.this$0.mStarVote.setCompoundDrawables(drawable, null, null, null);
                this.this$0.mStarVote.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700075, 2131101137));
                this.this$0.drawStarVoteNum(this.this$0.getVoteCount(StarActivity.access$1404(this.this$0)), TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700073, 2131100883));
                if (this.this$0.mFollowAdapter == null) {
                    this.this$0.mFollowAdapter = new StarFollowAdapter(this.this$0, new ArrayList());
                    this.this$0.mStarFollowList.setAdapter(this.this$0.mFollowAdapter);
                }
                this.this$0.mFollowAdapter.addMe();
                if (this.this$0.mFollowAdapter.getCount() > 0) {
                    this.this$0.mStarFollowTitle.setVisibility(0);
                    this.this$0.mStarFollowList.setVisibility(0);
                    if (PreferencesManager.getInstance().isLogin()) {
                        this.this$0.mStarFollowHint.setVisibility(8);
                    } else {
                        this.this$0.mStarFollowHint.setVisibility(0);
                    }
                }
            }

            public void onAskFollowStatus(boolean isFollowed) {
                this.this$0.mIsFollowed = isFollowed;
                this.this$0.setFollowBtnStatus();
            }

            public void onAskFollowStatusList(FollowStatusMapBean bean) {
            }

            public void onStarFollow(boolean isSuccess, int type) {
                int i = 2131100868;
                this.this$0.mFollowBtn.setEnabled(true);
                Button access$2300;
                StarActivity starActivity;
                if (type == 0) {
                    if (isSuccess) {
                        this.this$0.mIsFollowed = false;
                        this.this$0.mFollowCount = this.this$0.mFollowCount - 1;
                        this.this$0.drawFollowNum((long) this.this$0.mFollowCount);
                        access$2300 = this.this$0.mFollowBtn;
                        starActivity = this.this$0;
                        if (this.this$0.mIsFollowed) {
                            i = 2131100871;
                        }
                        access$2300.setText(starActivity.getString(i));
                        Drawable drawable = this.this$0.getResources().getDrawable(2130838260);
                        drawable.setBounds(UIs.dipToPx(8.0f), 0, drawable.getMinimumWidth() + UIs.dipToPx(8.0f), drawable.getMinimumHeight());
                        this.this$0.mFollowBtn.setCompoundDrawables(drawable, null, null, null);
                        ToastUtils.showToast(this.this$0, TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700065, 2131100880));
                        return;
                    }
                    ToastUtils.showToast(this.this$0, TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700067, 2131100879));
                } else if (isSuccess) {
                    this.this$0.mIsFollowed = true;
                    this.this$0.mFollowCount = this.this$0.mFollowCount + 1;
                    this.this$0.drawFollowNum((long) this.this$0.mFollowCount);
                    access$2300 = this.this$0.mFollowBtn;
                    starActivity = this.this$0;
                    if (this.this$0.mIsFollowed) {
                        i = 2131100871;
                    }
                    access$2300.setText(starActivity.getString(i));
                    this.this$0.mFollowBtn.setCompoundDrawables(null, null, null, null);
                    ToastUtils.showToast(this.this$0, TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700064, 2131100870));
                } else {
                    ToastUtils.showToast(this.this$0, TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700066, 2131100869));
                }
            }
        };
        this.mBooedkProgramsCallback = new StarBookCallback(this) {
            final /* synthetic */ StarActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onBookedPrograms(Set<String> programs) {
                this.this$0.mStarAdapter.setBookedPrograms(programs);
            }

            public void reAskData() {
                LogInfo.log("clf", "onBooked reAskData");
                if (this.this$0.mStarFlow != null) {
                    this.this$0.mStarFlow.requestRequestBookedPrograms(this);
                }
            }
        };
    }

    static /* synthetic */ int access$1404(StarActivity x0) {
        int i = x0.mVoteCount + 1;
        x0.mVoteCount = i;
        return i;
    }

    public static void launch(Context context, String starId, String starName, String oldPageId) {
        Intent it = new Intent(context, StarActivity.class);
        it.putExtra("starId", starId);
        it.putExtra("name", starName);
        it.putExtra("oldPageId", oldPageId);
        context.startActivity(it);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        initData();
        initViews();
    }

    private void initData() {
        this.mZoomHeight = getResources().getDimensionPixelSize(2131165558);
        this.mTitleBgColor = getResources().getColor(2131493118);
        this.mTitleColor = getResources().getColor(2131493237);
        this.mTitleLineColor = getResources().getColor(2131493384);
        this.mStarFlow = new StarFlow(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.mStarId = bundle.getString("starId");
            this.mStarName = bundle.getString("name", "");
            this.oldPageId = bundle.getString("oldPageId", "");
        }
    }

    private void initViews() {
        this.mRoot = (PublicLoadLayout) findViewById(R.id.star_load);
        this.mTtitleLayout = findViewById(R.id.star_title_lt);
        this.mBtnBack0 = (ImageView) findViewById(R.id.star_btn_back0);
        this.mBtnBack = (ImageView) findViewById(R.id.star_btn_back);
        this.mTitle = (TextView) findViewById(R.id.star_title);
        this.mTitleLine = findViewById(R.id.star_title_line);
        this.mListView = (ObservableExpandableListView) findViewById(R.id.star_list);
        this.mHeadImg = (ImageView) findViewById(R.id.star_head_img);
        this.mHeadName = (TextView) findViewById(R.id.star_head_name);
        this.mHeadBirth = (TextView) findViewById(R.id.star_head_birth);
        this.mHeadDesc = (TextView) findViewById(R.id.star_head_desc);
        this.mZoomImg = (ImageView) findViewById(R.id.zoom_iv);
        this.mFollowBtn = (Button) findViewById(R.id.star_follow_btn);
        this.mFollowNum = (TextView) findViewById(R.id.star_follow_num);
        View header = View.inflate(this, R.layout.header_star, null);
        this.mStarCms = (ImageView) header.findViewById(R.id.star_cms);
        this.mStarRankTitleLt = header.findViewById(R.id.star_rank_title_lt);
        this.mStarRankMore = (TextView) header.findViewById(R.id.star_rank_more);
        this.mStarRankNum = (TextView) header.findViewById(R.id.star_rank_num);
        this.mStarRankVoteLt = header.findViewById(R.id.star_rank_vote_lt);
        this.mStarRankVoteNum = (TextView) header.findViewById(R.id.star_rank_vote_num);
        this.mBtnStarVote = header.findViewById(R.id.star_rank_vote_btn);
        this.mStarVote = (TextView) header.findViewById(R.id.star_rank_vote);
        this.mStarFollowList = (GridView) header.findViewById(R.id.star_rank_follow_list);
        this.mStarFollowHint = (TextView) header.findViewById(R.id.star_rank_follow_login_hint);
        this.mStarFollowTitle = header.findViewById(R.id.star_follow_title_lt);
        ((ExpandableListView) this.mListView.getRootView()).addHeaderView(header);
        this.mTitle.setText(this.mStarName);
        this.mFollowBtn.setOnClickListener(this);
        this.mBtnBack.setOnClickListener(this);
        this.mStarCms.setOnClickListener(this);
        this.mStarRankMore.setOnClickListener(this);
        this.mBtnStarVote.setOnClickListener(this);
        setFollowBtnStatus();
        initListView();
        this.mRoot.loading(false);
        this.mStarFlow.requestStarInfo(this.mStarFlowCallback, this.mStarId);
        this.mRoot.setRefreshData(new RefreshData(this) {
            final /* synthetic */ StarActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.mRoot.loading(false);
                this.this$0.mStarFlow.requestStarInfo(this.this$0.mStarFlowCallback, this.this$0.mStarId);
            }
        });
        this.mTtitleLayout.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ StarActivity this$0;

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
        this.mRoot.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ StarActivity this$0;

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
    }

    private void initListView() {
        this.mStarAdapter = new StarAdapter(this, this.mStarName);
        this.mStarAdapter.setStarBookCallback(this.mBooedkProgramsCallback);
        this.mListView.setAdapter(this.mStarAdapter);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        this.mListView.setHeaderLayoutParams(new LayoutParams(screenWidth, (int) (9.0f * (((float) screenWidth) / 16.0f))));
        this.mListView.setScrollViewCallbacks(this);
        ((ExpandableListView) this.mListView.getRootView()).setGroupIndicator(null);
        ((ExpandableListView) this.mListView.getRootView()).setOnGroupClickListener(new OnGroupClickListener(this) {
            final /* synthetic */ StarActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
    }

    protected void onResume() {
        super.onResume();
        StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.starPage, "19", null, this.mStarName, -1, "ref=" + this.oldPageId + "_-_-1");
    }

    public void onClick(View view) {
        if (view.getId() == this.mBtnBack.getId()) {
            finish();
        } else if (view.getId() == this.mFollowBtn.getId()) {
            if (PreferencesManager.getInstance().isLogin()) {
                this.mFollowBtn.setEnabled(false);
                this.mStarFlow.requestStarFollow(this.mStarFlowCallback, this.mStarId, this.mIsFollowed ? 0 : 1);
            } else {
                LetvLoginActivity.launch((Activity) this);
            }
            StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.starPage, "0", "s03", this.mStarName, 1, null);
        } else if (view.getId() == this.mStarRankMore.getId()) {
            StarRankActivity.launch(this.mContext);
        } else if (view.getId() == this.mStarCms.getId()) {
            if (!TextUtils.isEmpty(this.mSkipUrl)) {
                new LetvWebViewActivityConfig(this).launch(this.mSkipUrl, getString(2131100866));
            }
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "st2", null, 2, null, PageIdConstant.starPage, null, null, null, PageIdConstant.starPage, null);
        } else if (view.getId() != this.mBtnStarVote.getId()) {
        } else {
            if (!NetworkUtils.isNetworkAvailable()) {
                ToastUtils.showToast((Context) this, 2131100493);
            } else if (this.mIsVoted) {
                ToastUtils.showToast((Context) this, TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700076, 2131100881));
            } else if (this.mStarFlow != null) {
                this.mIsVoted = true;
                this.mBtnStarVote.setEnabled(false);
                this.mStarFlow.requestStarVote(this.mStarId, this.mStarFlowCallback);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 16 && resultCode == 1) {
            this.mStarFlow.requestFollowStatus(this.mStarFlowCallback, this.mStarId);
        }
    }

    private void changeAlpha(float alpha) {
        this.mTtitleLayout.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, this.mTitleBgColor));
        this.mTitle.setTextColor(ScrollUtils.getColorWithAlpha(alpha, this.mTitleColor));
        this.mBtnBack0.setAlpha(1.0f - alpha);
        this.mBtnBack.setAlpha(alpha);
        this.mTitleLine.setAlpha(alpha);
    }

    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (this.mRoot.getVisibility() != 0) {
            changeAlpha(Math.min(1.0f, ((float) scrollY) / ((float) this.mZoomHeight)));
        }
    }

    public void onDownMotionEvent() {
    }

    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    private void applyBlur(final ImageView iv) {
        iv.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener(this) {
            final /* synthetic */ StarActivity this$0;

            public boolean onPreDraw() {
                iv.getViewTreeObserver().removeOnPreDrawListener(this);
                iv.buildDrawingCache();
                BlurUtils.star_blur(this.this$0.mContext, iv.getDrawingCache(), this.this$0.mZoomImg);
                return true;
            }
        });
    }

    private void drawHeader(StarInfoBean data) {
        ArrayList<StarBlockBean> actBlocks = data.actBlocks;
        this.mStarCms.setVisibility(8);
        this.mStarRankTitleLt.setVisibility(8);
        this.mStarRankNum.setVisibility(8);
        this.mStarRankVoteLt.setVisibility(8);
        this.mStarFollowTitle.setVisibility(8);
        this.mStarFollowList.setVisibility(8);
        this.mStarFollowHint.setVisibility(8);
        Iterator it = actBlocks.iterator();
        while (it.hasNext()) {
            StarBlockBean block = (StarBlockBean) it.next();
            if (block.star != null) {
                ImageDownloader.getInstance().download(this.mHeadImg, block.star.postS1_11_300_300, 2130837905, new CustomConfig(BitmapStyle.ROUND, 0));
                ImageDownloader.getInstance().download(block.star.backPic, new ImageDownloadStateListener(this) {
                    final /* synthetic */ StarActivity this$0;

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
                    }

                    public void loadSuccess(Bitmap bitmap, String localPath) {
                    }

                    public void loadSuccess(View view, Bitmap bitmap, String localPath) {
                    }

                    public void loadFailed() {
                    }
                });
                this.mTitle.setText(block.star.leName);
                this.mHeadName.setText(block.star.leName);
                drawFollowNum((long) block.star.fansnum);
                this.mFollowCount = block.star.fansnum;
                if (TextUtils.isEmpty(block.star.birthday)) {
                    this.mHeadBirth.setVisibility(8);
                } else {
                    this.mHeadBirth.setVisibility(0);
                    this.mHeadBirth.setText(block.star.birthday);
                }
                if (TextUtils.isEmpty(block.star.professional)) {
                    this.mHeadDesc.setVisibility(8);
                } else {
                    this.mHeadDesc.setText(block.star.professional);
                    this.mHeadDesc.setVisibility(0);
                }
            }
            if (!(block.starActivity == null || TextUtils.isEmpty(block.starActivity.mobilePic))) {
                this.mStarCms.setVisibility(0);
                UIsUtils.zoomViewHeight(getResources().getDimensionPixelSize(2131165550), this.mStarCms);
                ImageDownloader.getInstance().download(this.mStarCms, block.starActivity.mobilePic);
                this.mSkipUrl = block.starActivity.skipUrl;
                StatisticsUtils.staticticsInfoPost(this.mContext, "19", "st1", null, 2, null, PageIdConstant.starPage, null, null, null, PageIdConstant.starPage, null);
            }
            if (block.bigShot != null) {
                this.mStarRankTitleLt.setVisibility(0);
                this.mStarRankNum.setVisibility(0);
                this.mStarRankVoteLt.setVisibility(0);
                this.mStarFollowTitle.setVisibility(0);
                this.mStarFollowList.setVisibility(0);
                this.mStarFollowHint.setVisibility(0);
                Drawable drawable;
                if (data.is_vote == 1) {
                    this.mIsVoted = true;
                    drawable = getResources().getDrawable(2130838988);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    this.mStarVote.setCompoundDrawables(drawable, null, null, null);
                    this.mStarVote.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700075, 2131101137));
                } else {
                    drawable = getResources().getDrawable(2130838987);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    this.mStarVote.setCompoundDrawables(drawable, null, null, null);
                    this.mStarVote.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700074, 2131101137));
                }
                drawStarRankTx(block.bigShot.ranking);
                this.mVoteCount = block.bigShot.vote_num;
                String voteUnit = TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700073, 2131100883);
                drawStarVoteNum(getVoteCount(this.mVoteCount), voteUnit);
                ArrayList<StarFollowRankBean> starFollowRank = block.bigShot.rank;
                LogInfo.log("clf", "starFollowRank = " + starFollowRank);
                if (BaseTypeUtils.isListEmpty(starFollowRank)) {
                    this.mStarFollowTitle.setVisibility(8);
                    this.mStarFollowList.setVisibility(8);
                    this.mStarFollowHint.setVisibility(8);
                } else {
                    this.mStarFollowTitle.setVisibility(0);
                    this.mStarFollowList.setVisibility(0);
                    if (PreferencesManager.getInstance().isLogin()) {
                        this.mStarFollowHint.setVisibility(8);
                    } else {
                        this.mStarFollowHint.setVisibility(0);
                    }
                    LogInfo.log("clf", "starFollowRank.list = " + starFollowRank.size());
                    if (this.mFollowAdapter == null) {
                        this.mFollowAdapter = new StarFollowAdapter(this, starFollowRank);
                        this.mStarFollowList.setAdapter(this.mFollowAdapter);
                    } else {
                        this.mFollowAdapter.setList(starFollowRank);
                    }
                }
            }
        }
    }

    private void drawStarRankTx(String rank) {
        if (!TextUtils.isEmpty(rank)) {
            String rankTx;
            int start;
            int end;
            SpannableStringBuilder sb;
            if (rank.indexOf("+") > 0) {
                rankTx = getString(2131100874, new Object[]{rank});
                start = rankTx.indexOf("!1") + 1;
                rankTx = rankTx.replace("!1", " ").replace("!2", "").replace("+", " +");
                end = rankTx.length() - 1;
                sb = new SpannableStringBuilder(rankTx);
                sb.setSpan(new StyleSpan(1), start, end, 33);
                sb.setSpan(new SuperscriptSpanAdjuster(0.2d), 0, start, 33);
                sb.setSpan(new SuperscriptSpanAdjuster(0.5d), end, rankTx.length(), 33);
                sb.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(2131165483)), start, end, 33);
                sb.setSpan(new ForegroundColorSpan(getResources().getColor(2131493347)), start, rankTx.length(), 33);
                this.mStarRankNum.setText(sb);
                return;
            }
            rankTx = getString(2131100874, new Object[]{rank});
            start = rankTx.indexOf("!1") + 1;
            end = rankTx.indexOf("!2") - 1;
            sb = new SpannableStringBuilder(rankTx.replace("!1", " ").replace("!2", " "));
            sb.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(2131165483)), start, end, 33);
            sb.setSpan(new SuperscriptSpanAdjuster(0.2d), 0, start, 33);
            sb.setSpan(new StyleSpan(1), start, end, 33);
            sb.setSpan(new ForegroundColorSpan(getResources().getColor(2131493347)), start, end, 33);
            this.mStarRankNum.setText(sb);
        }
    }

    private void drawStarVoteNum(String num, String unit) {
        if (!TextUtils.isEmpty(num) && !TextUtils.isEmpty(unit)) {
            String str = num + " " + unit;
            int start = num.length();
            int end = str.length();
            SpannableStringBuilder sb = new SpannableStringBuilder(str);
            sb.setSpan(new ForegroundColorSpan(getResources().getColor(2131493270)), start, end, 33);
            sb.setSpan(new StyleSpan(1), 0, start, 33);
            sb.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(2131165477)), start, end, 33);
            this.mStarRankVoteNum.setText(sb);
        }
    }

    private void drawFollowNum(long num) {
        LogInfo.log("clf", "drawFollowNum....num=" + num);
        String followNum = StringUtils.getPlayCountsToStr(num);
        if (TextUtils.isEmpty(followNum)) {
            this.mFollowNum.setText("0");
            return;
        }
        String unit = "";
        String lastChar = followNum.substring(followNum.length() - 1);
        if (!StringUtils.isInt(lastChar)) {
            unit = " " + lastChar;
            followNum = followNum.substring(0, followNum.length() - 1) + unit;
        }
        int start = followNum.length() - unit.length();
        int end = followNum.length();
        SpannableStringBuilder sb = new SpannableStringBuilder(followNum);
        sb.setSpan(new StyleSpan(1), 0, start, 33);
        sb.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(2131165476)), start, end, 33);
        this.mFollowNum.setText(sb);
    }

    private String getVoteCount(int count) {
        return new DecimalFormat("#,###").format(new BigDecimal(count));
    }

    private void initLiveBlock(ArrayList<StarBlockBean> albumBlocks) {
        Iterator it = albumBlocks.iterator();
        while (it.hasNext()) {
            if (!BaseTypeUtils.isListEmpty(((StarBlockBean) it.next()).liveList)) {
                this.mStarFlow.requestRequestBookedPrograms(this.mBooedkProgramsCallback);
                return;
            }
        }
    }

    private void setFollowBtnStatus() {
        this.mFollowBtn.setText(getString(this.mIsFollowed ? 2131100871 : 2131100868));
        if (this.mIsFollowed) {
            this.mFollowBtn.setCompoundDrawables(null, null, null, null);
            return;
        }
        Drawable drawable = getResources().getDrawable(2130838260);
        drawable.setBounds(UIs.dipToPx(8.0f), 0, drawable.getMinimumWidth() + UIs.dipToPx(8.0f), drawable.getMinimumHeight());
        this.mFollowBtn.setCompoundDrawables(drawable, null, null, null);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mStarFlow.onDestroy();
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return StarActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
