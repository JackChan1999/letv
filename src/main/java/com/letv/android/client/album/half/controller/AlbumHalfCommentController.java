package com.letv.android.client.album.half.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.ClipboardManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.half.AlbumHalfFragment;
import com.letv.android.client.album.half.adapter.AlbumHalfAdapter;
import com.letv.android.client.album.half.fragment.HalfPlayCommentLikeFragment;
import com.letv.android.client.album.half.parser.CommentAddParser;
import com.letv.android.client.album.half.parser.CommentListParser;
import com.letv.android.client.album.half.parser.ReplyAddParser;
import com.letv.android.client.album.half.widget.HalfPlaySoftKeyboardFragment;
import com.letv.android.client.album.listener.AlbumHalfStatisticsInterface;
import com.letv.android.client.album.listener.OnCommentItemClickListener;
import com.letv.android.client.commonlib.config.AlbumCommentDetailActivityConfig;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.utils.EpisodeUtils;
import com.letv.android.client.commonlib.view.ChannelListFootView;
import com.letv.business.flow.album.AlbumPlayFlowObservable.RequestCombineParams;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.AlbumPageCard;
import com.letv.core.bean.CommentAddBean;
import com.letv.core.bean.CommentBean;
import com.letv.core.bean.CommentBean.User;
import com.letv.core.bean.CommentListBean;
import com.letv.core.bean.CommentNumberBean;
import com.letv.core.bean.ReplyBean;
import com.letv.core.bean.ReplyListBean;
import com.letv.core.bean.TimestampBean;
import com.letv.core.bean.UploadFileBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.download.image.ImageDownloader.BitmapStyle;
import com.letv.core.download.image.ImageDownloader.CustomConfig;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.task.LeMessageTask;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.CommentNumberParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.EncryptUtils;
import com.letv.core.utils.FileUtils;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.pp.utils.NetworkUtils;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class AlbumHalfCommentController extends AlbumCompositeInterface implements OnCommentItemClickListener, AlbumHalfStatisticsInterface, OnScrollListener {
    public static final int HEAD_POSITION = 10000;
    private static final int REFRESH_DELAY = 1234;
    private static final int SHOW_NO_NET_DELAY = 1235;
    private static final String TAG = AlbumHalfCommentController.class.getSimpleName();
    private AlbumHalfAdapter adapter;
    private TextView barCommentTextView;
    private RequestCombineParams combineParams;
    private String commentId = "";
    private int commentNode;
    public HalfPlaySoftKeyboardFragment commentSoftKeyboard;
    private String content;
    public Context context;
    private TextView copyTextView;
    private int curPage = 1;
    public DataState dataState = DataState.NULL;
    public AlbumHalfFragment fragment;
    private String hintShot;
    public boolean isForceAlbumFullScreen = false;
    private boolean isReachBottom;
    private boolean isRequesting;
    private boolean isShowComment;
    private boolean isVoteShot;
    private LinearLayout listFootLayout;
    public ChannelListFootView listFootView;
    private ListView listView;
    private ClipboardManager mClipBoard;
    private AlbumHalfCommentHeadController mCommentHeadController;
    private ImageView mCommentRedDot = null;
    protected Handler mHandler = new 1(this);
    private HalfPlayCommentLikeFragment mLikeFragment;
    private String mPicName;
    private String mPicTimeStemp;
    private TextView mTotalText;
    private int moreCount = 0;
    private PopupWindow morePopupWindow;
    private View popupContentView;
    private String replyId = "";
    private int replyNode;
    private int[] sendHeadLocation = new int[2];
    private int[] sendOutLocation = new int[2];
    private int total = 0;
    private int voteStateShot;

    public interface AlbumHalfCommentInterface {
        public static final String BUNDLE_KEY_COMMENT_CONTENT = "comment_content";
        public static final String BUNDLE_KEY_IS_COMMENT = "is_comment";
        public static final String BUNDLE_KEY_IS_FROM_BARRAGE = "is_from_barrage";
        public static final String BUNDLE_KEY_IS_VOTE = "is_vote";
        public static final String BUNDLE_KEY_PIC_NAME = "comment_pic_name";
        public static final String BUNDLE_KEY_PIC_TIMESTEMP = "comment_pic_time";
        public static final String BUNDLE_KEY_VIDEOSHOT_RETURN = "comment_videoshot_return";
        public static final String BUNDLE_KEY_VOTE = "bundle_key_vote";
    }

    public AlbumHalfCommentController(Context context, AlbumHalfFragment albumHalfFragment) {
        super(context, albumHalfFragment);
        this.fragment = albumHalfFragment;
        this.context = context;
        initView();
    }

    public void setHalfCommentHeadController(AlbumHalfCommentHeadController mHalfCommentHeadController) {
        this.mCommentHeadController = mHalfCommentHeadController;
    }

    private void initView() {
        this.listView = this.fragment.getListView();
        this.mCommentRedDot = (ImageView) ((Activity) this.context).findViewById(R.id.iv_comment_dot);
        this.barCommentTextView = (TextView) ((Activity) this.context).findViewById(R.id.album_half_comment_count_text);
        this.mTotalText = (TextView) ((Activity) this.context).findViewById(R.id.tv_total_text);
        this.mTotalText.setText("总评论");
        this.mTotalText.setVisibility(8);
        this.listFootView = new ChannelListFootView(this.context);
        this.listFootLayout = new LinearLayout(this.context);
        this.listFootLayout.setLayoutParams(new LayoutParams(-1, -2));
        this.listFootLayout.addView(this.listFootView);
        this.popupContentView = UIsUtils.inflate(this.context, R.layout.detailplay_half_comment_item_toast_playerlibs, null);
        this.copyTextView = (TextView) this.popupContentView.findViewById(R.id.textv_copy);
        this.adapter = this.fragment.getAdapter();
        this.listView.addFooterView(this.listFootLayout);
        this.listView.setOnScrollListener(this);
        this.adapter.setOnCommentItemListener(this);
    }

    public void setCommentShow(boolean show) {
        this.isShowComment = show;
        this.listView.removeFooterView(this.listFootLayout);
        if (this.isShowComment) {
            this.listView.addFooterView(this.listFootLayout);
        }
    }

    public AlbumHalfCommentHeadController getCommentHeadController() {
        return this.mCommentHeadController;
    }

    public void onCommentItemClickEvent(View itemView, TextView likeV, int commentPos, ImageView likeImage, boolean isLike) {
        CommentBean commentItem = this.adapter.getItem(commentPos);
        if ((this.context instanceof AlbumPlayActivity) && commentItem != null) {
            this.fragment.mOpenCommentDetail = true;
            AlbumHalfFragment fragment = ((AlbumPlayActivity) this.context).getHalfFragment();
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumCommentDetailActivityConfig(this.context).create(commentItem._id, commentItem.level, fragment.getCurrPlayingVideo(), fragment.getAlbumCardList())));
        }
    }

    public void onReplyItemClickEvent(View itemView, TextView likeV, int commentPos, int replyPos, ImageView likeImage, boolean isLike) {
        CommentBean commentBean = this.adapter.getItem(commentPos);
        if (commentBean == null) {
            LogInfo.log(TAG, "onReplyItemClickEvent commentBean == null");
            return;
        }
        List<ReplyBean> replyList = commentBean.replys;
        if (replyList == null) {
            LogInfo.log(TAG, "onReplyItemClickEvent replyList == null");
            return;
        }
        ReplyBean replyItem = (ReplyBean) BaseTypeUtils.getElementFromList(replyList, replyPos);
        if (replyItem == null) {
            LogInfo.log(TAG, "onReplyItemClickEvent replyItem == null");
            return;
        }
        replyText(this.context.getString(R.string.detail_half_comment_edit_text_hint_reply, new Object[]{replyItem.user.username}), false);
        this.commentNode = commentPos;
        this.replyNode = replyPos;
        this.commentId = replyItem.commentid;
        this.replyId = replyItem._id;
    }

    public void onExpendMoreReplyClickEvent(int commentPos, String commentId) {
        this.commentNode = commentPos;
        CommentBean commentItem = this.adapter.getItem(commentPos);
        if (this.context instanceof AlbumPlayActivity) {
            this.fragment.mOpenCommentDetail = true;
            AlbumHalfFragment fragment = ((AlbumPlayActivity) this.context).getHalfFragment();
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumCommentDetailActivityConfig(this.context).create(commentId, commentItem.level, fragment.getCurrPlayingVideo(), fragment.getAlbumCardList())));
        }
        StatisticsUtils.staticticsInfoPost(this.context, "0", "87", null, this.moreCount, null, PageIdConstant.halpPlayPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
    }

    public void onCommentEditClickEvent(int commentPos, int replyPos, boolean isComment) {
        if (isComment) {
            replyText(this.context.getString(R.string.detail_half_comment_edit_text_hint_reply, new Object[]{this.adapter.getItem(commentPos).user.username}), false);
            this.commentNode = commentPos;
            this.replyNode = -1;
            this.commentId = commentItem._id;
            return;
        }
        ReplyBean replyItem = null;
        if (this.adapter.getItem(commentPos).replys != null) {
            replyItem = (ReplyBean) this.adapter.getItem(commentPos).replys.get(replyPos);
        }
        Context context = this.context;
        int i = R.string.detail_half_comment_edit_text_hint_reply;
        Object[] objArr = new Object[1];
        objArr[0] = this.adapter.getItem(commentPos).replys != null ? replyItem.user.username : "";
        replyText(context.getString(i, objArr), isComment);
        this.commentNode = commentPos;
        this.replyNode = replyPos;
        this.commentId = replyItem.commentid;
        this.replyId = replyItem._id;
    }

    public void onLogoutLikeClickEvent() {
        logoutLikeDialog();
    }

    public void onItemLongClickEvent(View itemView, int commentPos, int replyPos, boolean isComment) {
        showPopupWindow(itemView);
        LogInfo.log("fornia", "commentPos:" + commentPos + "|replyPos:" + replyPos);
        if (isComment) {
            CommentBean item = this.adapter.getItem(commentPos);
            if (item == null) {
                LogInfo.log("fornia", "commentPos:" + commentPos + "|replyPos:" + replyPos + " replyItem == null");
                return;
            }
            LogInfo.log("fornia", "commentPos:" + commentPos + "|replyPos:" + replyPos + " replyItem:" + item.content);
            this.copyTextView.setOnClickListener(new 2(this, item));
            return;
        }
        ReplyBean replyItem = (ReplyBean) this.adapter.getItem(commentPos).replys.get(replyPos);
        if (replyItem == null) {
            LogInfo.log("fornia", "commentPos:" + commentPos + "|replyPos:" + replyPos + " replyItem == null");
            return;
        }
        LogInfo.log("fornia", "commentPos:" + commentPos + "|replyPos:" + replyPos + " replyItem:" + replyItem.content);
        this.copyTextView.setOnClickListener(new 3(this, replyItem));
    }

    private void logoutLikeDialog() {
        if (PreferencesManager.getInstance().isLogoutCommentLikeDialogVisible()) {
            FragmentManager fm = ((FragmentActivity) this.context).getSupportFragmentManager();
            if (fm != null) {
                FragmentTransaction ft = fm.beginTransaction();
                this.mLikeFragment = (HalfPlayCommentLikeFragment) fm.findFragmentByTag("showLikeLogout");
                if (this.mLikeFragment == null) {
                    this.mLikeFragment = new HalfPlayCommentLikeFragment();
                } else {
                    ft.remove(this.mLikeFragment);
                }
                ft.add(this.mLikeFragment, "showLikeLogout");
                ft.commitAllowingStateLoss();
            }
        }
        PreferencesManager.getInstance().setLogoutCommentLikeCount();
    }

    private void replyListResponse(ReplyListBean result) {
        if (result != null && result.total > 0) {
            CommentBean commentBean = this.adapter.getItem(this.commentNode);
            commentBean.replynum = result.total;
            if (commentBean.replyPage == 1) {
                commentBean.replys.clear();
            }
            LinkedList<ReplyBean> data = result.data;
            if (data != null) {
                commentBean.replys.addAll(data);
                commentBean.replyPage++;
                this.adapter.notifyDataSetChanged();
            }
        }
    }

    public void replyText(String hint, boolean isComment) {
        replyText(0, hint, isComment, false);
    }

    public void replyText(int voteState, String hint, boolean isComment, boolean isVote) {
        this.voteStateShot = voteState;
        this.hintShot = hint;
        if (!PreferencesManager.getInstance().isLogin()) {
            LogInfo.log("Emerson", "评论登录");
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvLoginActivityConfig(this.context).create(0, 10002)));
        } else if (!com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            ToastUtils.showToast(LetvTools.getTextFromServer("500003", this.context.getString(R.string.network_unavailable)));
        } else if (PreferencesManager.getInstance().getUserPhoneNumberBindState()) {
            showCommentSoftKeyboard(voteState, hint, isComment, isVote, false);
        } else {
            LeMessageManager.getInstance().registerTask(new LeMessageTask(3, new 4(this, voteState, hint, isComment, isVote)));
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(2, this.context));
        }
    }

    private void showCommentSoftKeyboard(int voteState, String hint, boolean isComment, boolean isVote, boolean isVideoshotBack) {
        FragmentManager fm = ((FragmentActivity) this.context).getSupportFragmentManager();
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            this.commentSoftKeyboard = (HalfPlaySoftKeyboardFragment) fm.findFragmentByTag("showSoftKeybord");
            if (this.commentSoftKeyboard == null) {
                this.commentSoftKeyboard = new HalfPlaySoftKeyboardFragment();
            } else {
                ft.remove(this.commentSoftKeyboard);
            }
            Bundle bundle = new Bundle();
            bundle.putString(HalfPlaySoftKeyboardFragment.BUNDLE_KEY_HINT, hint);
            bundle.putInt(HalfPlaySoftKeyboardFragment.VOTE_STATE, voteState);
            bundle.putBoolean(AlbumHalfCommentInterface.BUNDLE_KEY_IS_COMMENT, isComment);
            bundle.putBoolean(AlbumHalfCommentInterface.BUNDLE_KEY_IS_VOTE, isVote);
            bundle.putInt(HalfPlaySoftKeyboardFragment.TYPE_ININTIAL_COMMENT, 1);
            AlbumPlayActivity albumPlayActivity = this.fragment.getAlbumPlayActivity();
            if (albumPlayActivity.getFlow() == null || albumPlayActivity.getFlow().mIsDownloadFile || albumPlayActivity.getFlow().isLocalFile() || albumPlayActivity.mIsPanoramaVideo || albumPlayActivity.mIs4dVideo || albumPlayActivity.mIsDolbyVideo || PreferencesManager.getInstance().getShareWords().equalsIgnoreCase("0")) {
                bundle.putBoolean(HalfPlaySoftKeyboardFragment.TYPE_ININTIAL_COMMENT_SHOW_PIC, false);
            } else {
                bundle.putBoolean(HalfPlaySoftKeyboardFragment.TYPE_ININTIAL_COMMENT_SHOW_PIC, true);
            }
            bundle.putBoolean(AlbumHalfCommentInterface.BUNDLE_KEY_VIDEOSHOT_RETURN, isVideoshotBack);
            this.commentSoftKeyboard.setMyBundleParam(bundle);
            this.commentSoftKeyboard.setAlbumHalfCommentController(this);
            this.commentSoftKeyboard.setOnSoftKeyBoardListener(new 5(this));
            ft.add(this.commentSoftKeyboard, "showSoftKeybord");
            ft.commitAllowingStateLoss();
        }
    }

    private void judgeDataState() {
        int curDataCount = this.adapter.getCommentListSize();
        int pageSize = BaseTypeUtils.stoi("20");
        if (this.total == 0) {
            this.dataState = DataState.NULL;
            LogInfo.log("songhang", "空数据");
            this.listFootView.setVisibility(8);
        } else if (this.total <= curDataCount || curDataCount < pageSize) {
            this.dataState = DataState.NOMORE;
            LogInfo.log("songhang", "没有更多");
            this.listFootView.showNoMore(this.context.getResources().getString(R.string.channel_list_foot_no_more_comment));
        } else {
            this.curPage++;
            this.dataState = DataState.HASMORE;
            LogInfo.log("songhang", "加载中...");
            this.listFootView.showLoading();
        }
    }

    public void showPopupWindow(View target) {
        if (target != null) {
            if (this.morePopupWindow == null) {
                this.morePopupWindow = new PopupWindow(this.context);
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
            LogInfo.log("fornia", "location[0]:" + location[0] + "  location[1]:" + location[1]);
            this.morePopupWindow.showAtLocation(target, 0, location[0] + ((target.getWidth() - UIsUtils.dipToPx(40.0f)) / 2), (location[1] + (target.getHeight() / 2)) - UIsUtils.dipToPx(60.0f));
            this.morePopupWindow.update();
        }
    }

    public void dismissPopupWin() {
        if (this.morePopupWindow != null) {
            this.morePopupWindow.dismiss();
        }
    }

    public void refreshComment(AlbumPageCard albumPageCard) {
        if (albumPageCard == null) {
            LogInfo.log("fornia", "评论 pagecard == null");
            return;
        }
        LogInfo.log("fornia", "评论 pagecard ！= null");
        this.curPage = 1;
        this.mCommentHeadController.setData(albumPageCard);
        requestCommentList(true, false);
    }

    private void loadMore() {
        if (this.dataState == DataState.HASMORE) {
            requestCommentList(false, false);
        }
    }

    private void setData(CommentListBean result, boolean isSwitchedTab) {
        if (this.adapter != null && this.mCommentHeadController.emptyCommentView != null && result != null && com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            long j;
            if (isSwitchedTab) {
                this.adapter.clearData();
                if (result == null || result.total <= 0) {
                    this.mCommentHeadController.mNetNullView.setVisibility(8);
                    this.mCommentHeadController.emptyCommentView.setVisibility(0);
                    this.total = result == null ? 0 : result.total;
                } else {
                    this.mCommentHeadController.emptyCommentView.setVisibility(8);
                    this.total = result.total;
                    this.adapter.setTopCount(result.topCount);
                    this.adapter.setVideoList(result.data);
                }
            } else if (result == null || (result.total <= 0 && (result.total != 0 || this.curPage != 1 || result.data == null || result.data.size() <= 0))) {
                if (result == null || result.total > 0 || this.curPage == 1) {
                    this.total = result.total;
                    this.mCommentHeadController.emptyCommentView.setVisibility(0);
                    this.mCommentHeadController.mNetNullView.setVisibility(8);
                } else {
                    this.mCommentHeadController.emptyCommentView.setVisibility(8);
                }
                if (this.curPage == 1) {
                    this.adapter.clearData();
                }
            } else {
                this.mCommentHeadController.emptyCommentView.setVisibility(8);
                this.total = result.total;
                if (!(result.data == null || result.data.size() <= 0 || result.topCount == 0)) {
                    this.adapter.setTopCount(result.topCount);
                }
                if (this.curPage == 1) {
                    this.adapter.setVideoList(result.data);
                    if (result.data == null || result.data.size() <= 0 || !(((CommentBean) result.data.get(0)).level == 1 || ((CommentBean) result.data.get(0)).level == 2)) {
                        this.adapter.setTopCount(0);
                    } else {
                        this.adapter.setTopCount(result.topCount);
                    }
                } else {
                    this.adapter.insertList(result.data);
                }
            }
            this.adapter.notifyDataSetChanged();
            if (result == null) {
                j = 0;
            } else {
                j = result.comment_total;
            }
            updatePageCardCommentNumber(EpisodeUtils.getPlayCountsToStrNoZero(Math.max(j, 0)));
        }
    }

    private void setCommentCount(String vidCount, String pidCount) {
        if (this.mCommentHeadController.isShowCommentVidAndPid()) {
            if ((TextUtils.isEmpty(vidCount) || vidCount.equals("0")) && (TextUtils.isEmpty(pidCount) || pidCount.equals("0"))) {
                this.barCommentTextView.setText(R.string.episode_comment_text);
                this.mTotalText.setVisibility(8);
            } else if (TextUtils.isEmpty(vidCount) || vidCount.equals("0")) {
                this.barCommentTextView.setText(Html.fromHtml(this.context.getResources().getString(R.string.detail_half_player_comment_count_pid_only, new Object[]{pidCount})));
                this.mTotalText.setVisibility(0);
            } else if (TextUtils.isEmpty(pidCount) || pidCount.equals("0")) {
                if (PreferencesManager.getInstance().isShowCommentRedDot()) {
                    updateCommentDot(true);
                }
                this.barCommentTextView.setText(this.context.getResources().getString(R.string.detail_half_player_comment_count_vid_only, new Object[]{vidCount}));
                this.mTotalText.setVisibility(8);
            } else {
                if (PreferencesManager.getInstance().isShowCommentRedDot()) {
                    updateCommentDot(true);
                }
                this.barCommentTextView.setText(Html.fromHtml(this.context.getResources().getString(R.string.detail_half_player_comment_count_vid_pid, new Object[]{vidCount, pidCount})));
                this.mTotalText.setVisibility(0);
            }
        } else if (TextUtils.isEmpty(vidCount) || vidCount.equals("0")) {
            this.barCommentTextView.setText(R.string.episode_comment_text);
            this.mTotalText.setVisibility(8);
        } else {
            if (PreferencesManager.getInstance().isShowCommentRedDot()) {
                updateCommentDot(true);
            }
            this.barCommentTextView.setText(this.context.getResources().getString(R.string.detail_half_player_comment_count_vid_only, new Object[]{vidCount}));
            this.mTotalText.setVisibility(8);
        }
    }

    private void updatePageCardCommentNumber(String commentTotal) {
        if (this.mCommentHeadController.headSubtitle != null) {
            this.mCommentHeadController.headSubtitle.setText(BaseTypeUtils.ensureStringValidate(commentTotal));
            this.mCommentHeadController.headSubtitle.setTextColor(this.context.getResources().getColor(R.color.letv_color_a1a1a1));
        }
    }

    public void addComment(Bundle bundle) {
        LogInfo.log("Emerson", "-----notify :bundle ");
        if (bundle != null) {
            this.content = bundle.getString(AlbumHalfCommentInterface.BUNDLE_KEY_COMMENT_CONTENT);
            boolean isComment = bundle.getBoolean(AlbumHalfCommentInterface.BUNDLE_KEY_IS_COMMENT);
            boolean isFromBarrage = bundle.getBoolean(AlbumHalfCommentInterface.BUNDLE_KEY_IS_FROM_BARRAGE, false);
            int voteState = bundle.getInt(AlbumHalfCommentInterface.BUNDLE_KEY_VOTE);
            this.mPicTimeStemp = bundle.getString(AlbumHalfCommentInterface.BUNDLE_KEY_PIC_TIMESTEMP);
            this.mPicName = bundle.getString(AlbumHalfCommentInterface.BUNDLE_KEY_PIC_NAME);
            LogInfo.log("fornia", "截图时间图片----videoshot mPicName---:" + this.mPicName + "mPicTimeStemp:" + this.mPicTimeStemp);
            if (isComment) {
                requestAddComment(voteState, isFromBarrage, this.mPicName, this.mPicTimeStemp);
            } else {
                requestReplyComment();
            }
        }
    }

    private void addCommentPrexcute(int voteState, String imageUrl) {
        if (!this.fragment.isAlbum()) {
            return;
        }
        if (voteState == 0 || !this.mCommentHeadController.isShowCommentVidAndPid() || this.mCommentHeadController.isVidModeSelected) {
            if (this.adapter.getCommentListSize() == 0 && this.mCommentHeadController.emptyCommentView != null) {
                this.mCommentHeadController.emptyCommentView.setVisibility(8);
            }
            if (this.adapter.getTopCount() == 0) {
                LogInfo.log("Emerson", "---------getTopCount = 0 ");
                this.adapter.getCommentList().addFirst(createCommentBean(voteState, this.content, imageUrl));
            } else {
                LogInfo.log("Emerson", "---------getTopCount >>>>>");
                if (this.adapter.getCommentList().size() > this.adapter.getTopCount()) {
                    this.adapter.getCommentList().add(this.adapter.getTopCount(), createCommentBean(voteState, this.content, imageUrl));
                }
            }
            this.adapter.notifyDataSetChanged();
            pinLastCommentPosition();
            return;
        }
        switch2VidAndLocation(true);
    }

    private void pinLastCommentPosition() {
        new Handler(Looper.getMainLooper()).post(new 6(this));
    }

    private CommentBean createCommentBean(int voteState, String content, String imageUrl) {
        CommentBean newComment = new CommentBean();
        newComment.content = content;
        newComment.user = new User();
        newComment.user.uid = PreferencesManager.getInstance().getUserId();
        newComment.user.photo = PreferencesManager.getInstance().getUserIcon();
        newComment.user.username = PreferencesManager.getInstance().getNickName();
        newComment.vtime = this.context.getResources().getString(R.string.record_date_now);
        newComment.img = imageUrl;
        LogInfo.log("fornia", "volley 网络图片地址newComment.img:" + newComment.img);
        newComment.voteFlag = voteState;
        return newComment;
    }

    private void replyCommentPreexcute(int rule) {
        createReplyBean(this.commentNode, this.replyNode, rule);
        this.adapter.notifyDataSetChanged();
    }

    private void createReplyBean(int commentPos, int replyPos, int rule) {
        if (this.adapter != null) {
            CommentBean bean = this.adapter.getItem(commentPos);
            if (bean != null) {
                ReplyBean replyBean = new ReplyBean();
                replyBean.commentid = bean._id;
                replyBean.content = this.content;
                replyBean.vtime = this.context.getResources().getString(R.string.record_date_now);
                replyBean.isOpen = rule == 2;
                replyBean.user = new User();
                replyBean.user.uid = PreferencesManager.getInstance().getUserId();
                replyBean.user.photo = PreferencesManager.getInstance().getUserIcon();
                replyBean.user.username = PreferencesManager.getInstance().getNickName();
                if (bean.replys == null) {
                    bean.replys = new LinkedList();
                } else if (replyPos == -1) {
                    replyBean.reply = null;
                } else {
                    replyBean.reply = new ReplyBean();
                    replyBean.reply._id = ((ReplyBean) bean.replys.get(replyPos))._id;
                    replyBean.reply.commentid = ((ReplyBean) bean.replys.get(replyPos)).commentid;
                    replyBean.reply.content = ((ReplyBean) bean.replys.get(replyPos)).content;
                    replyBean.reply.user = new User();
                    replyBean.reply.user.uid = ((ReplyBean) bean.replys.get(replyPos)).user.uid;
                    replyBean.reply.user.photo = ((ReplyBean) bean.replys.get(replyPos)).user.photo;
                    replyBean.reply.user.username = ((ReplyBean) bean.replys.get(replyPos)).user.username;
                    replyBean.reply.user.ssouid = ((ReplyBean) bean.replys.get(replyPos)).user.ssouid;
                }
                bean.replys.addLast(replyBean);
            }
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == 0 && this.isReachBottom && this.isShowComment && this.mCommentHeadController != null && this.mCommentHeadController.emptyCommentView != null) {
            loadMore();
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        boolean z;
        int i = 0;
        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            z = true;
        } else {
            z = false;
        }
        this.isReachBottom = z;
        int commentHeadIndex = this.fragment.getControllerList().size() - 1;
        if (this.mCommentHeadController != null && this.mCommentHeadController.sendHeadView != null) {
            boolean aboveTop;
            this.mCommentHeadController.sendHeadView.getLocationOnScreen(this.sendHeadLocation);
            this.listView.getLocationOnScreen(this.sendOutLocation);
            if (firstVisibleItem < commentHeadIndex || this.sendHeadLocation[1] >= this.sendOutLocation[1]) {
                aboveTop = false;
            } else {
                aboveTop = true;
            }
            if (aboveTop && this.mCommentHeadController.mSendOutsideUserHeadView != null) {
                ImageDownloader.getInstance().download(this.mCommentHeadController.mSendOutsideUserHeadView, PreferencesManager.getInstance().getUserIcon(), R.drawable.bg_head_default, new CustomConfig(BitmapStyle.ROUND, 0));
            }
            this.mCommentHeadController.sendOutsideView.setVisibility(aboveTop ? 0 : 8);
            View view2 = this.mCommentHeadController.sendHeadView;
            if (aboveTop) {
                i = 4;
            }
            view2.setVisibility(i);
        }
    }

    public void refreshCommentList() {
        this.mHandler.sendEmptyMessageDelayed(REFRESH_DELAY, 300);
    }

    private void refreshCommentDelay() {
        this.mCommentHeadController.showLoadingProgress();
        this.curPage = 1;
        requestCommentList(true, false);
    }

    public void requestCommentList(boolean isSwitchedTab, boolean isNeedLocation) {
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            if (this.barCommentTextView != null && this.barCommentTextView.getText().toString().equals(this.context.getResources().getString(R.string.episode_comment_text))) {
                requestCommentNumber();
            }
            if (this.mCommentHeadController.mNetNullView != null) {
                this.mCommentHeadController.mNetNullView.setVisibility(8);
            }
            if (!this.isRequesting) {
                if (isSwitchedTab) {
                    this.curPage = 1;
                }
                this.isRequesting = true;
                this.combineParams = this.fragment.getCombineParams();
                if (this.combineParams != null) {
                    new LetvRequest(CommentListBean.class).setRequestType(RequestManner.NETWORK_ONLY).setNeedCheckToken(true).setUrl(MediaAssetApi.getInstance().requestCommentListUrl(this.mCommentHeadController.isVidModeSelected ? "" : this.combineParams.pid, this.mCommentHeadController.isVidModeSelected ? this.combineParams.vid : "", this.curPage, this.combineParams.cid)).setParser(new CommentListParser()).setTag("requestCommentList").setCallback(new 7(this, isSwitchedTab, isNeedLocation)).add();
                    return;
                }
                return;
            }
            return;
        }
        this.mHandler.sendEmptyMessageDelayed(SHOW_NO_NET_DELAY, 1000);
    }

    private void switch2VidAndLocation(boolean isNeedLocation) {
        this.mCommentHeadController.switch2Vid(isNeedLocation);
    }

    public void requestCommentNumber() {
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            this.combineParams = this.fragment.getCombineParams();
            if (this.combineParams != null) {
                new LetvRequest(CommentNumberBean.class).setRequestType(RequestManner.NETWORK_ONLY).setNeedCheckToken(true).setUrl(MediaAssetApi.getInstance().requestCommmentNumberUrl(this.combineParams.vid, this.combineParams.pid)).setParser(new CommentNumberParser()).setTag("requestCommentNumber").setCallback(new 8(this)).add();
                return;
            }
            return;
        }
        setCommentCount("", "");
    }

    public void updateCommentDot(boolean show) {
        if (show && PreferencesManager.getInstance().isShowCommentRedDot()) {
            PreferencesManager.getInstance().setShowCommentRedDot(false);
            this.mCommentRedDot.setVisibility(0);
        } else if (!show && !PreferencesManager.getInstance().isShowCommentRedDot()) {
            this.mCommentRedDot.setVisibility(8);
        }
    }

    private void updateCommentViews(CommentNumberBean result) {
        if (result != null && this.fragment != null && this.fragment.getAlbumInfo() != null) {
            setCommentCount(EpisodeUtils.getPlayCountsToStrNoZero(Math.max(result.vcomm_total, 0)), EpisodeUtils.getPlayCountsToStrNoZero(Math.max(result.pcomm_total, 0)));
        }
    }

    public void requestAddComment(int voteSate, boolean isFromBarrage, String picName, String time) {
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            LogInfo.log("fornia", "请求评论参数----videoshot voteSate---:" + voteSate + "picName:" + picName + "time:" + time);
            if (this.content != null && !"".equals(this.content) && this.combineParams != null) {
                this.content = this.content.trim();
                if (!"".equals(this.content.trim())) {
                    String pid = TextUtils.isEmpty(this.combineParams.pid) ? "0" : this.combineParams.pid;
                    String vid = TextUtils.isEmpty(this.combineParams.vid) ? "0" : this.combineParams.vid;
                    String cid = TextUtils.isEmpty(this.combineParams.cid) ? "0" : this.combineParams.cid;
                    if (TextUtils.isEmpty(picName)) {
                        new LetvRequest(CommentAddBean.class).setRequestType(RequestManner.NETWORK_ONLY).addPostParams(MediaAssetApi.getInstance().addCommentUrlParam(pid, vid, cid, "cmt", this.content, voteSate + "", "", "")).setUrl(MediaAssetApi.getInstance().addCommentUrl()).setNeedCheckToken(true).setCache(new VolleyDiskCache("AddComment")).setParser(new CommentAddParser()).setCallback(new 10(this, voteSate, isFromBarrage)).add();
                        return;
                    }
                    UploadFileBean[] files = new UploadFileBean[1];
                    if (new File(picName).exists() && new File(picName).canRead()) {
                        files[0] = new UploadFileBean(new File(picName), "img", "image/jpeg");
                        LogInfo.log("fornia", "截图时间图片---- FileUtils.getFileName(picName):" + FileUtils.getFileName(picName) + "time:" + time);
                        String url = MediaAssetApi.getInstance().addCommentUrl();
                        new LetvRequest(CommentAddBean.class).setRequestType(RequestManner.NETWORK_ONLY).addPostParams(MediaAssetApi.getInstance().addCommentUrlParam(pid, vid, cid, "img", this.content, voteSate + "", picName, time)).addPostParam("TK", EncryptUtils.letvEncrypt(((long) TimestampBean.getTm().getCurServerTime()) * 1, url)).setFilePostParam(files).setUrl(url).setNeedCheckToken(false).setCache(new VolleyDiskCache("AddCommentPic")).setParser(new CommentAddParser()).setCallback(new 9(this, voteSate, isFromBarrage)).add();
                        return;
                    }
                    LogInfo.log("fornia", "截图时间图片!new File(picName).exists() || !new File(picName).canRead()");
                    return;
                }
                return;
            }
            return;
        }
        ToastUtils.showToast(this.context.getResources().getString(R.string.network_unavailable));
    }

    public void requestReplyComment() {
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            new LetvRequest(CommentAddBean.class).setRequestType(RequestManner.NETWORK_ONLY).addPostParams(MediaAssetApi.getInstance().replyCommentUrlParams(String.valueOf(this.combineParams.pid), String.valueOf(this.combineParams.vid), String.valueOf(this.combineParams.cid), this.commentId, this.replyId, this.content)).setUrl(MediaAssetApi.getInstance().replyCommentUrl()).setNeedCheckToken(true).setCache(new VolleyDiskCache("ReplyComment")).setParser(new ReplyAddParser()).setCallback(new 11(this)).add();
        } else {
            ToastUtils.showToast(this.context.getResources().getString(R.string.network_unavailable));
        }
    }

    public void onStart() {
        super.onStart();
        this.mCommentHeadController.updateSendViewState();
    }

    public void onPlayVideo() {
        super.onPlayVideo();
        updateCommentDot(false);
        if (this.commentSoftKeyboard != null && this.commentSoftKeyboard.isVisible()) {
            this.commentSoftKeyboard.dismissAllowingStateLoss();
        }
    }

    public void statisticsCardExposure() {
    }

    public void statisticsCardExpandMore() {
    }

    public void updateUserHeadPhoto() {
        if (this.mCommentHeadController.mUserCommentHeadView != null) {
            ImageDownloader.getInstance().download(this.mCommentHeadController.mUserCommentHeadView, PreferencesManager.getInstance().getUserIcon(), R.drawable.bg_head_default, new CustomConfig(BitmapStyle.ROUND, 0));
        }
    }

    public void forceAlbumFullScreen(boolean isVote) {
        this.isVoteShot = isVote;
        if (this.context != null && ((AlbumPlayActivity) this.context).getController() != null) {
            this.isForceAlbumFullScreen = true;
            ((AlbumPlayActivity) this.context).getController().full();
        }
    }

    public void resumeSoftKeyboardFragment() {
        this.isForceAlbumFullScreen = false;
        showCommentSoftKeyboard(this.voteStateShot, this.hintShot, true, this.isVoteShot, true);
    }
}
