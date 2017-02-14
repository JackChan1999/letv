package com.letv.android.client.album;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.album.half.adapter.CommentDetailReplyAdapter;
import com.letv.android.client.album.half.controller.AlbumHalfCommentController.AlbumHalfCommentInterface;
import com.letv.android.client.album.half.parser.CommentDetailParser;
import com.letv.android.client.album.half.parser.ReplyAddParser;
import com.letv.android.client.album.half.parser.ReplyListParser;
import com.letv.android.client.album.half.widget.HalfPlaySoftKeyboardFragment;
import com.letv.android.client.album.half.widget.LetvToastPlayerLibs;
import com.letv.android.client.album.listener.OnCommentItemClickListener;
import com.letv.android.client.album.utils.AnimUtilsPlayerLibs;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.config.AlbumCommentDetailActivityConfig;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.messagemodel.ShareWindowProtocol;
import com.letv.android.client.commonlib.utils.EpisodeUtils;
import com.letv.android.client.commonlib.view.ChannelListFootView;
import com.letv.android.client.commonlib.view.RoundedImageView;
import com.letv.android.client.commonlib.view.RoundedPagerDrawable;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.AlbumCardList;
import com.letv.core.bean.CommentAddBean;
import com.letv.core.bean.CommentBean.User;
import com.letv.core.bean.CommentDetailBean;
import com.letv.core.bean.CommentLikeBean;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.ReplyBean;
import com.letv.core.bean.ReplyListBean;
import com.letv.core.bean.VideoBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloadStateListener;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.download.image.ImageDownloader.BitmapStyle;
import com.letv.core.download.image.ImageDownloader.CustomConfig;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.messagebus.task.LeMessageTask;
import com.letv.core.messagebus.task.LeMessageTask.TaskRunnable;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.tencent.open.yyb.TitleBar;
import java.util.LinkedList;

public class AlbumCommentDetailActivity extends LetvBaseActivity implements OnClickListener, OnCommentItemClickListener {
    private static final int SHOW_NO_NET_DELAY = 1235;
    public static HalfPlaySoftKeyboardFragment sCommentSoftKeyboard;
    private TextView commentCountText;
    private LinearLayout commentLayoutView;
    private RoundedImageView commentPhotoView;
    private ImageView commentUserStarView;
    private ImageView commentUserStatueView;
    private ImageView commentUserSupportView;
    private TextView commitTimeTextView;
    private String content;
    private TextView contentTextView;
    private TextView copyTextView;
    private int curPage = 1;
    private DataState dataState = DataState.NULL;
    boolean isLike = false;
    private boolean isReachBottom;
    private boolean isShowComment = true;
    private ImageView likeImageView;
    private LinearLayout likeLayoutView;
    private TextView likeTextView;
    private LinearLayout listFootLayout;
    private ChannelListFootView listFootView;
    private RelativeLayout loading_layout;
    private CommentDetailReplyAdapter mAdapter;
    private AlbumCardList mAlbum;
    private ImageView mBackImageView;
    private ClipboardManager mClipBoard;
    private CommentDetailBean mCommentDetail;
    private String mCommentId;
    private int mCommentLevel = -1;
    private String mCommentType;
    protected Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AlbumCommentDetailActivity.SHOW_NO_NET_DELAY /*1235*/:
                    LogInfo.log("fornia", "SHOW_NO_NET_DELAY SHOW_NO_NET_DELAY");
                    AlbumCommentDetailActivity.this.rl_root_layout.setVisibility(8);
                    AlbumCommentDetailActivity.this.loading_layout.setVisibility(8);
                    AlbumCommentDetailActivity.this.mNetError.setVisibility(0);
                    return;
                default:
                    return;
            }
        }
    };
    private LetvToastPlayerLibs mLetvToastPlayerLibs;
    private ListView mListView;
    private RelativeLayout mNetError;
    private Button mReplyButton;
    private int mReplyPage = 1;
    private ShareWindowProtocol mShareWindowProtocol;
    private TextView mTitleView;
    private VideoBean mVideo;
    private TextView mVideoshotTime;
    private PopupWindow morePopupWindow;
    private View popupContentView;
    private int replyPos = -1;
    private RelativeLayout rl_root_layout;
    private RelativeLayout rl_videoshot_time_layout;
    private int sInitialFrom = -1;
    private LinearLayout shareLayoutView;
    private int total = 0;
    private ImageView userHeadImageView;
    private TextView userNickNameTextView;

    private enum DataState {
        NULL,
        HASMORE,
        NOMORE
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_comment_detail);
        if (getIntent() != null) {
            this.mCommentLevel = getIntent().getIntExtra(AlbumCommentDetailActivityConfig.LEVEL, -1);
            this.mCommentId = getIntent().getStringExtra("id");
            LogInfo.log("fornia", "类型  mCommentType:" + this.mCommentType + "| mCommentId:" + this.mCommentId);
        }
        this.mVideo = AlbumCommentDetailActivityConfig.sVideo;
        this.mAlbum = AlbumCommentDetailActivityConfig.sAlbum;
        initView();
        initData();
    }

    public View getRootView() {
        return this.rl_root_layout;
    }

    public void sendCommet(Bundle bundle) {
        addCommet(bundle);
    }

    public void addCommet(Bundle bundle) {
        LogInfo.log("Emerson", "-----notify :bundle ");
        if (bundle != null) {
            this.content = bundle.getString(AlbumHalfCommentInterface.BUNDLE_KEY_COMMENT_CONTENT);
            requestReplyComment();
        }
    }

    public void requestReplyComment() {
        if (NetworkUtils.isNetworkAvailable()) {
            String replyId = "";
            if (this.replyPos != -1) {
                replyId = ((ReplyBean) this.mCommentDetail.data.get(this.replyPos))._id;
            }
            new LetvRequest(CommentAddBean.class).setRequestType(RequestManner.NETWORK_ONLY).addPostParams(MediaAssetApi.getInstance().replyCommentUrlParams(String.valueOf(this.mCommentDetail.pid), String.valueOf(this.mCommentDetail.vid), String.valueOf(this.mCommentDetail.cid), this.mCommentDetail._id, replyId, this.content)).setUrl(MediaAssetApi.getInstance().replyCommentUrl()).setNeedCheckToken(true).setCache(new VolleyDiskCache("ReplyComment")).setParser(new ReplyAddParser()).setCallback(new SimpleResponse<CommentAddBean>() {
                public void onNetworkResponse(VolleyRequest<CommentAddBean> volleyRequest, CommentAddBean result, DataHull hull, NetworkResponseState state) {
                    if (state == NetworkResponseState.SUCCESS) {
                        if ("200".equals(result.result)) {
                            AlbumCommentDetailActivity.this.replyCommentPreexcute(result.rule);
                        }
                        ToastUtils.showToastString(result.getResultToastString(false));
                    }
                }

                public void onErrorReport(VolleyRequest<CommentAddBean> request, String errorInfo) {
                    super.onErrorReport(request, errorInfo);
                }
            }).add();
            return;
        }
        ToastUtils.showToast(getResources().getString(R.string.network_unavailable));
    }

    private void replyCommentPreexcute(int rule) {
        createReplyBean(this.replyPos, rule);
        setData(this.mCommentDetail, false);
    }

    private void createReplyBean(int replyPos, int rule) {
        if (this.mCommentDetail != null) {
            ReplyBean replyBean = new ReplyBean();
            replyBean.commentid = this.mCommentDetail._id;
            replyBean.content = this.content;
            replyBean.vtime = BaseApplication.getInstance().getResources().getString(R.string.record_date_now);
            replyBean.isOpen = rule == 2;
            replyBean.user = new User();
            replyBean.user.uid = PreferencesManager.getInstance().getUserId();
            replyBean.user.photo = PreferencesManager.getInstance().getUserIcon();
            replyBean.user.username = PreferencesManager.getInstance().getNickName();
            if (this.mCommentDetail.data == null) {
                this.mCommentDetail.data = new LinkedList();
            } else if (replyPos == -1) {
                replyBean.reply = null;
            } else {
                replyBean.reply = new ReplyBean();
                replyBean.reply._id = ((ReplyBean) this.mCommentDetail.data.get(replyPos))._id;
                replyBean.reply.commentid = ((ReplyBean) this.mCommentDetail.data.get(replyPos)).commentid;
                replyBean.reply.content = ((ReplyBean) this.mCommentDetail.data.get(replyPos)).content;
                replyBean.reply.user = new User();
                replyBean.reply.user.uid = ((ReplyBean) this.mCommentDetail.data.get(replyPos)).user.uid;
                replyBean.reply.user.photo = ((ReplyBean) this.mCommentDetail.data.get(replyPos)).user.photo;
                replyBean.reply.user.username = ((ReplyBean) this.mCommentDetail.data.get(replyPos)).user.username;
                replyBean.reply.user.ssouid = ((ReplyBean) this.mCommentDetail.data.get(replyPos)).user.ssouid;
            }
            this.mCommentDetail.data.addLast(replyBean);
        }
        judgeDataState();
    }

    private void initView() {
        this.loading_layout = (RelativeLayout) findViewById(R.id.comment_detail_loading_layout);
        this.rl_root_layout = (RelativeLayout) findViewById(R.id.rl_root_layout);
        this.mBackImageView = (ImageView) findViewById(R.id.common_nav_left);
        this.mTitleView = (TextView) findViewById(R.id.common_nav_title);
        this.mListView = (ListView) findViewById(R.id.comment_detail_listview);
        this.mReplyButton = (Button) findViewById(R.id.bt_reply);
        this.popupContentView = UIsUtils.inflate(BaseApplication.getInstance(), R.layout.detailplay_half_comment_item_toast_playerlibs, null);
        this.copyTextView = (TextView) this.popupContentView.findViewById(R.id.textv_copy);
        this.mNetError = (RelativeLayout) findViewById(R.id.error);
        View headView = UIsUtils.inflate(this, R.layout.comment_detail_head_layout, null);
        this.rl_videoshot_time_layout = (RelativeLayout) headView.findViewById(R.id.rl_videoshot_time_layout);
        this.userHeadImageView = (ImageView) headView.findViewById(R.id.imagev_user_head);
        this.userNickNameTextView = (TextView) headView.findViewById(R.id.comment_user_name);
        this.commitTimeTextView = (TextView) headView.findViewById(R.id.comment_user_relaedate);
        this.likeLayoutView = (LinearLayout) headView.findViewById(R.id.ll_comment_like_layout);
        this.shareLayoutView = (LinearLayout) headView.findViewById(R.id.ll_comment_share_layout);
        this.commentLayoutView = (LinearLayout) headView.findViewById(R.id.ll_comment_number_layout);
        this.likeTextView = (TextView) headView.findViewById(R.id.like_number_text);
        this.likeImageView = (ImageView) headView.findViewById(R.id.icon_like);
        this.contentTextView = (TextView) headView.findViewById(R.id.comment_user_info);
        this.commentCountText = (TextView) headView.findViewById(R.id.comment_number_text);
        this.commentUserStatueView = (ImageView) headView.findViewById(R.id.comment_user_statue);
        this.commentUserStarView = (ImageView) headView.findViewById(R.id.comment_star_view);
        this.commentUserSupportView = (ImageView) headView.findViewById(R.id.comment_user_support);
        this.commentPhotoView = (RoundedImageView) headView.findViewById(R.id.comment_photo_edit);
        this.mVideoshotTime = (TextView) headView.findViewById(R.id.videoshot_time_text);
        this.mBackImageView.setOnClickListener(this);
        this.rl_videoshot_time_layout.setOnClickListener(this);
        if (getIntent() != null) {
            this.sInitialFrom = getIntent().getIntExtra("from", 1);
        }
        if (this.sInitialFrom == 1) {
            this.shareLayoutView.setVisibility(0);
        } else if (this.sInitialFrom == 2) {
            this.shareLayoutView.setVisibility(4);
            headView.findViewById(R.id.v_comment_divider_v1).setVisibility(4);
            headView.findViewById(R.id.v_comment_divider_v2).setVisibility(4);
        }
        this.mAdapter = new CommentDetailReplyAdapter(this);
        this.mListView.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                AlbumCommentDetailActivity.this.onScrollStateChanged(view, scrollState);
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                AlbumCommentDetailActivity.this.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
        this.mListView.addHeaderView(headView);
        this.listFootView = new ChannelListFootView(this);
        this.listFootLayout = new LinearLayout(this);
        this.listFootLayout.setLayoutParams(new LayoutParams(-1, -2));
        this.listFootLayout.addView(this.listFootView);
        this.mListView.addFooterView(this.listFootLayout);
        this.mListView.setAdapter(this.mAdapter);
    }

    private void initData() {
        this.mTitleView.setText(getResources().getString(R.string.episode_video_comment_detail));
        this.loading_layout.setVisibility(0);
        this.mNetError.setVisibility(8);
        this.mNetError.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AlbumCommentDetailActivity.this.loading_layout.setVisibility(0);
                AlbumCommentDetailActivity.this.mNetError.setVisibility(8);
                AlbumCommentDetailActivity.this.requestCommentDetail();
            }
        });
        requestCommentDetail();
    }

    private void requestCommentDetail() {
        if (NetworkUtils.isNetworkAvailable()) {
            String commentDetailUrl = MediaAssetApi.getInstance().requestCommmentDetailUrl(this.mCommentId, this.mReplyPage);
            if (!TextUtils.isEmpty(commentDetailUrl)) {
                new LetvRequest().setUrl(commentDetailUrl).setParser(new CommentDetailParser()).setMaxRetries(2).setCache(new VolleyDiskCache("CommentDetail")).setRequestType(RequestManner.NETWORK_ONLY).setCallback(new SimpleResponse<CommentDetailBean>() {
                    public void onNetworkResponse(VolleyRequest<CommentDetailBean> volleyRequest, CommentDetailBean result, DataHull hull, NetworkResponseState state) {
                        if (result == null) {
                            AlbumCommentDetailActivity.this.rl_root_layout.setVisibility(8);
                            AlbumCommentDetailActivity.this.loading_layout.setVisibility(8);
                            AlbumCommentDetailActivity.this.mNetError.setVisibility(0);
                            return;
                        }
                        AlbumCommentDetailActivity.this.loading_layout.setVisibility(8);
                        if (state == NetworkResponseState.SUCCESS) {
                            AlbumCommentDetailActivity.this.mCommentDetail = result;
                            CommentDetailBean access$500 = AlbumCommentDetailActivity.this.mCommentDetail;
                            access$500.replyPage++;
                            AlbumCommentDetailActivity.this.updateDetailView(result);
                        }
                        if (state == NetworkResponseState.SUCCESS) {
                        }
                    }

                    public void onErrorReport(VolleyRequest<CommentDetailBean> request, String errorInfo) {
                        super.onErrorReport(request, errorInfo);
                        LogInfo.log("fornia", "requestCommentDetail 失败 errorInfo:" + errorInfo);
                    }
                }).add();
                return;
            }
            return;
        }
        ToastUtils.showToast(BaseApplication.getInstance().getResources().getString(R.string.network_unavailable));
        this.mHandler.sendEmptyMessageDelayed(SHOW_NO_NET_DELAY, 1000);
    }

    public void requestReplyList(CommentDetailBean detailBean) {
        if (!NetworkUtils.isNetworkAvailable()) {
            ToastUtils.showToast(getResources().getString(R.string.network_unavailable));
        } else if (detailBean != null) {
            new LetvRequest(ReplyListBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(MediaAssetApi.getInstance().requestReplyListUrl(detailBean._id, detailBean.replyPage)).setCache(new VolleyDiskCache("TabReplyList")).setParser(new ReplyListParser()).setCallback(new SimpleResponse<ReplyListBean>() {
                public void onNetworkResponse(VolleyRequest<ReplyListBean> volleyRequest, ReplyListBean result, DataHull hull, NetworkResponseState state) {
                    if (state == NetworkResponseState.SUCCESS) {
                        AlbumCommentDetailActivity.this.replyListResponse(result);
                    }
                }
            }).add();
        } else {
            LogInfo.log("songhang", "requestReplyList() commentBean == null");
        }
    }

    private void replyListResponse(ReplyListBean result) {
        if (result != null && result.total > 0) {
            this.mCommentDetail.replynum = result.total;
            LinkedList<ReplyBean> data = result.data;
            if (data != null) {
                this.mCommentDetail.data.addAll(data);
                CommentDetailBean commentDetailBean = this.mCommentDetail;
                commentDetailBean.replyPage++;
                setData(this.mCommentDetail, false);
            }
        }
    }

    private void setData(CommentDetailBean result, boolean isSwitchedTab) {
        if (this.mAdapter != null && result != null) {
            if (isSwitchedTab) {
                this.mAdapter.clearData();
                if (result.replynum > 0) {
                    this.total = result.replynum;
                    this.mAdapter.setVideoList(result.data);
                } else {
                    this.total = result.replynum;
                }
            } else if (result.replynum > 0 || (result.replynum == 0 && this.curPage == 1 && result.data != null && result.data.size() > 0)) {
                this.total = result.replynum;
                this.mAdapter.setVideoList(result.data);
            } else {
                if (result.replynum > 0 || this.curPage == 1) {
                    this.total = result.replynum;
                }
                if (this.curPage == 1) {
                    this.mAdapter.clearData();
                }
            }
            this.mAdapter.notifyDataSetChanged();
            this.mListView.post(new Runnable() {
                public void run() {
                    AlbumCommentDetailActivity.this.judgeDataState();
                }
            });
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == 0 && this.isReachBottom && this.isShowComment) {
            loadMore();
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.isReachBottom = firstVisibleItem + visibleItemCount == totalItemCount;
    }

    private void loadMore() {
        if (this.dataState == DataState.HASMORE) {
            requestReplyList(this.mCommentDetail);
        }
    }

    private boolean isOverListView() {
        if (this.mListView == null) {
            return false;
        }
        int last = this.mListView.getLastVisiblePosition();
        View lastView = this.mListView.getChildAt(last);
        if (lastView == null) {
            return true;
        }
        if (last != this.mListView.getCount() - 1 || lastView.getBottom() > this.mListView.getHeight()) {
            return true;
        }
        return false;
    }

    private void judgeDataState() {
        if (this.total == 0) {
            this.dataState = DataState.NULL;
            LogInfo.log("songhang", "空数据");
            this.listFootView.setVisibility(8);
        } else if (this.total <= this.mAdapter.getCount()) {
            this.dataState = DataState.NOMORE;
            LogInfo.log("songhang", "没有更多");
            if (isOverListView()) {
                this.listFootView.showNoMoreForReply(getResources().getString(R.string.channel_list_foot_no_more_reply));
            } else {
                this.listFootView.setVisibility(8);
            }
        } else {
            this.curPage++;
            this.dataState = DataState.HASMORE;
            LogInfo.log("songhang", "加载中...");
            this.listFootView.showLoading();
        }
    }

    private void updateDetailView(final CommentDetailBean result) {
        if (result != null) {
            this.mAdapter.setOnComentItemListener(this);
            this.rl_root_layout.setVisibility(0);
            this.mReplyButton.setVisibility(0);
            this.mReplyButton.setText(getString(R.string.detail_half_comment_button_hint_reply, new Object[]{result.user.username}));
            this.mReplyButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    AlbumCommentDetailActivity.this.replyPos = -1;
                    AlbumCommentDetailActivity.this.replyText(0, AlbumCommentDetailActivity.this.getString(R.string.detail_half_comment_edit_text_hint_reply, new Object[]{result.user.username}), true, false);
                }
            });
            String imgUrl = TextUtils.isEmpty(result.imgOri) ? TextUtils.isEmpty(result.img310) ? TextUtils.isEmpty(result.img180) ? result.img : result.img180 : result.img310 : result.imgOri;
            if (TextUtils.isEmpty(imgUrl) || imgUrl.equalsIgnoreCase("false") || this.sInitialFrom != 1) {
                this.rl_videoshot_time_layout.setVisibility(8);
                this.commentPhotoView.setVisibility(8);
            } else {
                String str;
                String name = "";
                if (!(this.mVideo == null || TextUtils.isEmpty(this.mVideo.nameCn))) {
                    name = this.mVideo.nameCn + " - ";
                }
                TextView textView = this.mVideoshotTime;
                StringBuilder append = new StringBuilder().append(name);
                if (result.htime <= 0) {
                    str = "00:00:00";
                } else {
                    str = BaseTypeUtils.ensureStringValidate(StringUtils.getNumberTime((long) result.htime));
                }
                textView.setText(append.append(str).toString());
                this.rl_videoshot_time_layout.setVisibility(0);
                ViewGroup.LayoutParams layoutParams = this.commentPhotoView.getLayoutParams();
                layoutParams.width = UIsUtils.getScreenWidth() - UIsUtils.dipToPx(TitleBar.BACKBTN_LEFT_MARGIN);
                layoutParams.height = (layoutParams.width * 9) / 16;
                this.commentPhotoView.setLayoutParams(layoutParams);
                this.commentPhotoView.setVisibility(0);
                ImageDownloader.getInstance().download(this.commentPhotoView, imgUrl, new ImageDownloadStateListener() {
                    public void loading() {
                    }

                    public void loadSuccess(Bitmap bitmap) {
                    }

                    public void loadSuccess(Bitmap bitmap, String localPath) {
                    }

                    public void loadSuccess(View view, Bitmap bitmap, String localPath) {
                        if (view != null && bitmap != null) {
                            view.setVisibility(0);
                        }
                    }

                    public void loadFailed() {
                    }
                }, new CustomConfig(BitmapStyle.CORNER, BaseApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.letv_dimens_2)));
            }
            ImageDownloader.getInstance().download(this.userHeadImageView, result.user.photo, R.drawable.bg_head_default, new CustomConfig(BitmapStyle.ROUND, 0));
            setStatueView(this.mCommentLevel, this.commentUserStatueView);
            setStarViewVisibility(result.user, this.commentUserStarView);
            setSupportView(this.mCommentDetail.voteFlag + "", this.commentUserSupportView);
            this.userNickNameTextView.setText(BaseTypeUtils.ensureStringValidate(result.user.username));
            this.contentTextView.setText(BaseTypeUtils.ensureStringValidate(result.content));
            this.commitTimeTextView.setText(BaseTypeUtils.ensureStringValidate(result.vtime));
            this.commentCountText.setText(BaseTypeUtils.ensureStringValidate(EpisodeUtils.getPlayCountsToStrNoZero((long) Math.max(result.replynum, 0))));
            setLikeView(result.like, result.isLike, true, 0, this.likeTextView, this.likeImageView, this.likeLayoutView);
            final TextView likeView = this.likeTextView;
            final ImageView likeImage = this.likeImageView;
            this.isLike = this.mCommentDetail.isLike;
            this.contentTextView.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View view) {
                    AlbumCommentDetailActivity.this.onItemLongClickEvent(view, 0, -1, true);
                    return true;
                }
            });
            setData(result, true);
            this.likeLayoutView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (AlbumCommentDetailActivity.this.isLike) {
                        AlbumCommentDetailActivity.this.showLetvToast(TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_FAVOUR_REPEAT, R.string.detail_comment_toast_unlike_play));
                        return;
                    }
                    AlbumCommentDetailActivity.this.isLike = AlbumCommentDetailActivity.this.actionLike(likeView, true, 0, result.like, likeImage);
                }
            });
            this.shareLayoutView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!LetvUtils.isInHongKong() || AlbumCommentDetailActivity.this.sInitialFrom == 1) {
                        AlbumCommentDetailActivity.this.share(AlbumCommentDetailActivity.this.mCommentDetail.content);
                    } else {
                        UIsUtils.showToast(R.string.share_copyright_disable);
                    }
                }
            });
            this.commentLayoutView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    AlbumCommentDetailActivity.this.replyPos = -1;
                    AlbumCommentDetailActivity.this.replyText(0, AlbumCommentDetailActivity.this.getString(R.string.detail_half_comment_edit_text_hint_reply, new Object[]{result.user.username}), true, false);
                }
            });
            if (this.sInitialFrom == 2) {
                this.rl_videoshot_time_layout.setVisibility(8);
            }
        }
    }

    public void replyText(int voteState, String hint, boolean isComment, boolean isVote) {
        if (!PreferencesManager.getInstance().isLogin()) {
            LogInfo.log("Emerson", "评论登录");
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvLoginActivityConfig(this).create(0, 10002)));
        } else if (!NetworkUtils.isNetworkAvailable()) {
            ToastUtils.showToast(LetvTools.getTextFromServer("500003", getString(R.string.network_unavailable)));
        } else if (PreferencesManager.getInstance().getUserPhoneNumberBindState()) {
            showCommentSoftKeyboard(voteState, hint, isComment, isVote, false);
        } else {
            final int i = voteState;
            final String str = hint;
            final boolean z = isComment;
            final boolean z2 = isVote;
            LeMessageManager.getInstance().registerTask(new LeMessageTask(3, new TaskRunnable() {
                public LeResponseMessage run(LeMessage message) {
                    AlbumCommentDetailActivity.this.showCommentSoftKeyboard(i, str, z, z2, false);
                    return null;
                }
            }));
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(2, this));
        }
    }

    private void showCommentSoftKeyboard(int voteState, String hint, boolean isComment, boolean isVote, boolean isVideoshotBack) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            sCommentSoftKeyboard = (HalfPlaySoftKeyboardFragment) fm.findFragmentByTag("showSoftKeybord");
            if (sCommentSoftKeyboard == null) {
                sCommentSoftKeyboard = new HalfPlaySoftKeyboardFragment();
            } else {
                ft.remove(sCommentSoftKeyboard);
            }
            Bundle bundle = new Bundle();
            bundle.putString(HalfPlaySoftKeyboardFragment.BUNDLE_KEY_HINT, hint);
            bundle.putInt(HalfPlaySoftKeyboardFragment.VOTE_STATE, voteState);
            bundle.putBoolean(AlbumHalfCommentInterface.BUNDLE_KEY_IS_COMMENT, isComment);
            bundle.putBoolean(AlbumHalfCommentInterface.BUNDLE_KEY_IS_VOTE, isVote);
            bundle.putInt(HalfPlaySoftKeyboardFragment.TYPE_ININTIAL_COMMENT, 2);
            bundle.putBoolean(AlbumHalfCommentInterface.BUNDLE_KEY_VIDEOSHOT_RETURN, isVideoshotBack);
            sCommentSoftKeyboard.setMyBundleParam(bundle);
            ft.add(sCommentSoftKeyboard, "showSoftKeybord");
            ft.commitAllowingStateLoss();
        }
    }

    public void share(String comment) {
        if (this.sInitialFrom == 1) {
            if (this.mShareWindowProtocol == null) {
                LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(this, new LeMessage(109));
                if (LeResponseMessage.checkResponseMessageValidity(response, ShareWindowProtocol.class)) {
                    this.mShareWindowProtocol = (ShareWindowProtocol) response.getData();
                }
            }
            if (this.mShareWindowProtocol != null) {
                this.mShareWindowProtocol.share(5, comment, this.rl_root_layout, this.mVideo, this.mAlbum);
            }
        }
    }

    public void onCommentItemClickEvent(View itemView, TextView likeV, int commentPos, ImageView likeImage, boolean isLike) {
    }

    public void onReplyItemClickEvent(View itemView, TextView likeV, int commentPos, int replyPos, ImageView likeImage, boolean isLike) {
        ReplyBean replyItem = (ReplyBean) this.mCommentDetail.data.get(replyPos);
        replyText(BaseApplication.getInstance().getString(R.string.detail_half_comment_edit_text_hint_reply, new Object[]{replyItem.user.username}), false);
        this.replyPos = replyPos;
    }

    private void replyText(String hint, boolean isComment) {
        replyText(0, hint, isComment, false);
    }

    public void onExpendMoreReplyClickEvent(int commentPos, String commentId) {
        requestReplyList(this.mCommentDetail);
    }

    public void onCommentEditClickEvent(int commentPos, int replyPos, boolean isComment) {
    }

    public void onLogoutLikeClickEvent() {
    }

    public void onItemLongClickEvent(View itemView, int commentPos, int replyPos, boolean isComment) {
        showPopupWindow(itemView);
        if (isComment) {
            this.copyTextView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    AlbumCommentDetailActivity.this.dismissPopupWin();
                    if (AlbumCommentDetailActivity.this.mClipBoard == null) {
                        AlbumCommentDetailActivity.this.mClipBoard = (ClipboardManager) BaseApplication.getInstance().getSystemService("clipboard");
                    }
                    AlbumCommentDetailActivity.this.mClipBoard.setText(AlbumCommentDetailActivity.this.mCommentDetail.content);
                    ToastUtils.showToast(BaseApplication.getInstance().getResources().getString(R.string.detail_comment_toast_copy_play));
                }
            });
            return;
        }
        final ReplyBean replyItem = (ReplyBean) this.mCommentDetail.data.get(replyPos);
        if (replyItem != null) {
            this.copyTextView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    AlbumCommentDetailActivity.this.dismissPopupWin();
                    if (AlbumCommentDetailActivity.this.mClipBoard == null) {
                        AlbumCommentDetailActivity.this.mClipBoard = (ClipboardManager) BaseApplication.getInstance().getSystemService("clipboard");
                    }
                    AlbumCommentDetailActivity.this.mClipBoard.setText(replyItem.content);
                    LogInfo.log("fornia", "复制评论到剪贴板:" + replyItem.content);
                    ToastUtils.showToast(BaseApplication.getInstance().getResources().getString(R.string.detail_comment_toast_copy_play));
                }
            });
        }
    }

    public void showPopupWindow(View target) {
        if (target != null) {
            if (this.morePopupWindow == null) {
                this.morePopupWindow = new PopupWindow(BaseApplication.getInstance());
                this.morePopupWindow.setBackgroundDrawable(new BitmapDrawable());
                this.morePopupWindow.setWidth(UIsUtils.zoomWidth(60));
                this.morePopupWindow.setHeight(UIsUtils.zoomWidth(40));
                this.morePopupWindow.setOutsideTouchable(true);
                this.morePopupWindow.setFocusable(true);
                this.morePopupWindow.setContentView(this.popupContentView);
            }
            int[] location = new int[2];
            target.getLocationOnScreen(location);
            LogInfo.log("Emerson", "-----------location[1] = " + location[1]);
            this.morePopupWindow.showAtLocation(target, 0, location[0] + ((target.getWidth() - UIsUtils.dipToPx(40.0f)) / 2), (location[1] + (target.getHeight() / 2)) - UIsUtils.dipToPx(60.0f));
            this.morePopupWindow.update();
        }
    }

    public void dismissPopupWin() {
        if (this.morePopupWindow != null) {
            this.morePopupWindow.dismiss();
        }
    }

    private void setLikeView(int like, boolean isLike, boolean isCommentLike, int replyPos, TextView likev, ImageView likeImage, LinearLayout likeLayout) {
        if (likev != null) {
            if (like != 0) {
                likev.setText(BaseTypeUtils.ensureStringValidate(EpisodeUtils.toCommentLikeCountText(like)));
                likev.setVisibility(0);
            } else {
                likev.setText("");
                likev.setVisibility(0);
            }
            if (likeImage != null) {
                if (isLike) {
                    likeImage.setImageDrawable(getResources().getDrawable(R.drawable.comment_like_normal_selected));
                } else {
                    likeImage.setImageDrawable(getResources().getDrawable(R.drawable.comment_like_normal));
                }
            }
            final boolean z = isLike;
            final TextView textView = likev;
            final boolean z2 = isCommentLike;
            final int i = replyPos;
            final int i2 = like;
            final ImageView imageView = likeImage;
            likeLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (z) {
                        AlbumCommentDetailActivity.this.showLetvToast(TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_FAVOUR_REPEAT, R.string.detail_comment_toast_unlike_play));
                        return;
                    }
                    AlbumCommentDetailActivity.this.actionLike(textView, z2, i, i2, imageView);
                }
            });
        }
    }

    public void showLetvToast(String msg) {
        if (this.mLetvToastPlayerLibs == null) {
            this.mLetvToastPlayerLibs = new LetvToastPlayerLibs(BaseApplication.getInstance());
            this.mLetvToastPlayerLibs.setDuration(0);
        }
        this.mLetvToastPlayerLibs.setErr(false);
        this.mLetvToastPlayerLibs.setMsg(msg);
        this.mLetvToastPlayerLibs.show();
    }

    public boolean actionLike(TextView view, boolean isCommentLike, int replyPos, int likeNum, ImageView likeImage) {
        if (!checkNet()) {
            return false;
        }
        String commentId = "";
        if (this.mCommentDetail != null) {
            if (isCommentLike) {
                commentId = this.mCommentDetail._id;
                this.mCommentDetail.isLike = true;
                CommentDetailBean commentDetailBean = this.mCommentDetail;
                commentDetailBean.like++;
            } else {
                commentId = ((ReplyBean) this.mCommentDetail.data.get(replyPos))._id;
                ((ReplyBean) this.mCommentDetail.data.get(replyPos)).isLike = true;
                ReplyBean replyBean = (ReplyBean) this.mCommentDetail.data.get(replyPos);
                replyBean.like++;
            }
        }
        int num = likeNum + 1;
        view.setVisibility(0);
        view.setText(EpisodeUtils.toCommentLikeCountText(num));
        AnimUtilsPlayerLibs.animTop(BaseApplication.getInstance(), likeImage);
        likeImage.setImageDrawable(getResources().getDrawable(R.drawable.comment_like_normal_selected));
        requestCommentLikeTask(commentId, isCommentLike);
        return true;
    }

    public boolean checkNet() {
        if (NetworkUtils.isNetworkAvailable()) {
            return true;
        }
        ToastUtils.showToast(LetvTools.getTextFromServer("100008", BaseApplication.getInstance().getString(R.string.network_unavailable)));
        return false;
    }

    public void requestCommentLikeTask(String commentId, boolean isCommentLike) {
        if (NetworkUtils.isNetworkAvailable()) {
            new LetvRequest(CommentLikeBean.class).setRequestType(RequestManner.NETWORK_ONLY).addPostParams(MediaAssetApi.getInstance().getLikeCommentUrlParams(0, commentId, true, isCommentLike)).setUrl(MediaAssetApi.getInstance().getLikeCommentUrl(true)).setNeedCheckToken(true).setCache(new VolleyNoCache()).add();
            return;
        }
        ToastUtils.showToast(getResources().getString(R.string.network_unavailable));
    }

    private void setStarViewVisibility(CommentDetailBean.User user, ImageView commentUserStarView) {
        int i = (user == null || !user.isStar) ? 8 : 0;
        commentUserStarView.setVisibility(i);
    }

    @SuppressLint({"ResourceAsColor"})
    private void setStatueView(int level, ImageView statue) {
        if (level == 1) {
            statue.setVisibility(0);
            statue.setEnabled(true);
        } else if (level == 2) {
            statue.setVisibility(0);
            statue.setEnabled(false);
        } else {
            statue.setVisibility(8);
        }
    }

    private void setSupportView(String voteFlag, ImageView supportView) {
        if (TextUtils.isEmpty(voteFlag) || voteFlag.equals("0")) {
            supportView.setVisibility(8);
        } else if (voteFlag.equals("1")) {
            supportView.setVisibility(0);
            supportView.setEnabled(true);
        } else if (voteFlag.equals("2")) {
            supportView.setVisibility(0);
            supportView.setEnabled(false);
        }
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
        AlbumCommentDetailActivityConfig.sVideo = null;
        AlbumCommentDetailActivityConfig.sAlbum = null;
        if (this.mShareWindowProtocol != null) {
            this.mShareWindowProtocol.hideShareDialog();
        }
        this.mShareWindowProtocol = null;
        if (this.commentPhotoView != null) {
            Drawable drawable = this.commentPhotoView.getDrawable();
            if (drawable != null && (drawable instanceof RoundedPagerDrawable)) {
                Bitmap bitmap = ((RoundedPagerDrawable) drawable).getSourceBitmap();
                if (!(bitmap == null || bitmap.isRecycled())) {
                    LogInfo.log("fornia", "----bitmap：" + bitmap.getByteCount());
                    bitmap.recycle();
                    drawable.setCallback(null);
                }
            }
            Drawable recycleBgDw = this.commentPhotoView.getBackground();
            Bitmap recycleBp;
            if (recycleBgDw != null && (recycleBgDw instanceof RoundedPagerDrawable)) {
                recycleBp = ((RoundedPagerDrawable) recycleBgDw).getSourceBitmap();
                if (recycleBp != null && !recycleBp.isRecycled()) {
                    recycleBp.recycle();
                    recycleBgDw.setCallback(null);
                }
            } else if (recycleBgDw != null && (recycleBgDw instanceof BitmapDrawable)) {
                recycleBp = ((BitmapDrawable) recycleBgDw).getBitmap();
                if (recycleBp != null && !recycleBp.isRecycled()) {
                    recycleBp.recycle();
                    recycleBgDw.setCallback(null);
                }
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public String[] getAllFragmentTags() {
        return new String[0];
    }

    public String getActivityName() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.common_nav_left) {
            finish();
        } else if (id == R.id.rl_videoshot_time_layout && this.mCommentDetail != null) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mContext).create(this.mCommentDetail.pid, this.mCommentDetail.vid, 27, ((long) this.mCommentDetail.htime) * 1000)));
        }
    }
}
