package com.letv.android.client.album.half.widget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ToggleButton;
import com.letv.android.client.album.AlbumCommentDetailActivity;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.half.controller.AlbumHalfCommentController;
import com.letv.android.client.album.half.controller.AlbumHalfCommentController.AlbumHalfCommentInterface;
import com.letv.android.client.album.smilies.CommentSmiliesFragment;
import com.letv.android.client.album.smilies.SmiliesFragment;
import com.letv.android.client.album.utils.LetvPlayerContants;
import com.letv.android.client.commonlib.adapter.LetvGalleryAdapter;
import com.letv.android.client.commonlib.adapter.LetvGalleryAdapter.OnSelectImageListener;
import com.letv.core.bean.PhotoFilesInfoBean;
import com.letv.core.bean.PhotoInfoBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.FileUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StoreUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.tencent.open.yyb.TitleBar;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

public class HalfPlaySoftKeyboardFragment extends DialogFragment {
    public static final String BUNDLE_KEY_HINT = "hint";
    private static final int COMMENT_COUNT = 140;
    public static final int LOAD_FINISHED_HAS_VIDEOSHOT_PIC = 1110;
    public static final int LOAD_FINISHED_NONE_VIDEOSHOT_PIC = 1111;
    public static final int REQUESTCODE_HALF_ALBUM_COMMENT = 11;
    public static final int RESULTCODE_HALF_ALBUM_FORCE_FULLSCREEN = 12;
    public static final int TYPE_ALBUM_COMMENT_DETAIL = 2;
    public static final int TYPE_ALBUM_COMMENT_HALF = 1;
    public static final String TYPE_ININTIAL_COMMENT = "write_comment";
    public static final String TYPE_ININTIAL_COMMENT_SHOW_PIC = "pic_comment";
    public static final String VOTE_STATE = "vote_state";
    private static int commented_count = 0;
    private final int MAX_TEXT_INPUT_LENGTH = COMMENT_COUNT;
    private Bundle bundle;
    private ContentState comentState = ContentState.SOFT_STATE;
    private String comment = "";
    private int curKeyboardH;
    private String hint = "";
    private boolean isComment = false;
    private boolean isGalleryShowing = false;
    private boolean isHasPhoto = false;
    private boolean isOnCreate = true;
    private boolean isPrepareKeyBoard = true;
    private boolean isSoftKeyboardShow;
    private boolean isVideoshotBack = false;
    private boolean isVote;
    private LetvGalleryAdapter mAdapter;
    private TextView mCommentText1;
    private TextView mCommentText2;
    private LinearLayout mDetailPlayHalfKeybordLayout;
    private EditText mEditText;
    private int mFromType;
    private Button mGoVideoshot;
    private RelativeLayout mGuideLayout;
    private AlbumHalfCommentController mHalfCommentController = null;
    private TextView mImageTip;
    private boolean mIsHalfAlbumShowPic = false;
    private int mRecyclerHeight;
    private RecyclerView mRecyclerView;
    private Button mSendBtn;
    private OnSoftKeyBoardListener mSoftKeyBoardListener;
    private TextView mTipText;
    private ToggleButton mTogglePhotoButton;
    private BroadcastReceiver mVideoshotRecever = new 14(this);
    private OnEditorActionListener onEditorActionListener = new 6(this);
    private OnClickListener onEidtClickListener = new 7(this);
    private OnGlobalLayoutListener onKeyboardListener = new 5(this);
    private OnSelectImageListener onSelectImageListener = new 8(this);
    private PhotoFilesInfoBean photoFileName = new PhotoFilesInfoBean();
    private Handler previewHandler = new 2(this);
    private View root;
    private OnClickListener sendCommentClick = new 4(this);
    private CommentSmiliesFragment smiliesFragment;
    OnCheckedChangeListener toggleAddPhotoCheckChangeListener = new 3(this);
    private ToggleButton toggleButton;
    OnCheckedChangeListener toggleCheckChangeListener = new 1(this);
    private int voteState = 0;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(2, R.style.SoftDialogStyle);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogInfo.log("Emerson", "----onCreateView----");
        getDialog().getWindow().setLayout(-1, -1);
        getDialog().getWindow().setSoftInputMode(32);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.root = UIsUtils.inflate(getActivity(), R.layout.detailplay_half_keybord, null);
        View listenView = null;
        if (getActivity() instanceof AlbumPlayActivity) {
            listenView = ((AlbumPlayActivity) getActivity()).getRootView();
        } else if (getActivity() instanceof AlbumCommentDetailActivity) {
            listenView = ((AlbumCommentDetailActivity) getActivity()).getRootView();
        }
        listenView.getViewTreeObserver().addOnGlobalLayoutListener(this.onKeyboardListener);
        if (this.bundle != null) {
            LogInfo.log("fornia", ">>>>>bundle != null");
            this.mFromType = this.bundle.getInt(TYPE_ININTIAL_COMMENT);
            this.mIsHalfAlbumShowPic = this.bundle.getBoolean(TYPE_ININTIAL_COMMENT_SHOW_PIC);
            this.hint = this.bundle.getString(BUNDLE_KEY_HINT);
            LogInfo.log("fornia", "showSoftInputKey获得hint:" + this.hint);
            this.voteState = this.bundle.getInt(VOTE_STATE);
            this.isVote = this.bundle.getBoolean(AlbumHalfCommentInterface.BUNDLE_KEY_IS_VOTE);
            this.isComment = this.bundle.getBoolean(AlbumHalfCommentInterface.BUNDLE_KEY_IS_COMMENT);
            this.isVideoshotBack = this.bundle.getBoolean(AlbumHalfCommentInterface.BUNDLE_KEY_VIDEOSHOT_RETURN);
            if (this.isComment) {
                this.comment = LetvPlayerContants.getComment();
                LetvPlayerContants.setComment("");
            }
            if (this.mFromType == 1) {
                loadVideoShot();
            }
            findview();
        }
        if (this.isVideoshotBack) {
            getDialog().getWindow().setSoftInputMode(2);
            this.mTogglePhotoButton.setChecked(true);
        } else {
            getDialog().getWindow().setSoftInputMode(4);
        }
        showSoftInputKey();
        if (this.mSoftKeyBoardListener != null) {
            this.mSoftKeyBoardListener.onShow();
        }
        registerVideoshotFinishBroadcast();
        this.mRecyclerHeight = (((UIsUtils.getScreenWidth() - UIsUtils.dipToPx(40.0f)) * 9) / 16) + UIsUtils.dipToPx(TitleBar.SHAREBTN_RIGHT_MARGIN);
        return this.root;
    }

    public void setAlbumHalfCommentController(AlbumHalfCommentController halfCommentController) {
        this.mHalfCommentController = halfCommentController;
    }

    private void loadVideoShot() {
        new Thread(new 9(this)).start();
    }

    private void loadData() {
        if (this.photoFileName.getPhoto() != null) {
            this.photoFileName.getPhoto().clear();
        }
        List<File> pics = FileUtils.getFiles(getActivity(), StoreUtils.VIDEOSHOT_PIC_TEMP_PATH);
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

    private void updateChoosePicView(boolean showPic) {
        if (!this.isGalleryShowing) {
            return;
        }
        if (showPic) {
            this.mRecyclerView.setVisibility(0);
            this.mGuideLayout.setVisibility(8);
            this.mAdapter.setDataAndUpdate(this.photoFileName);
            return;
        }
        this.mRecyclerView.setVisibility(4);
        this.mGuideLayout.setVisibility(0);
        this.mCommentText1.setText(TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_NONE_VIDEOSHOT_NOTICE, R.string.detail_comment_notice1));
        this.mCommentText2.setText(TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_PREFER_VIDEOSHOT_NOTICE, R.string.detail_comment_notice2));
        this.mGoVideoshot.setOnClickListener(new 10(this));
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void findview() {
        this.mDetailPlayHalfKeybordLayout = (LinearLayout) this.root.findViewById(R.id.detailplay_half_keybord_layout);
        this.mDetailPlayHalfKeybordLayout.setOnClickListener(new 11(this));
        this.mEditText = (EditText) this.root.findViewById(R.id.comment_edit_text_view);
        this.mEditText.getViewTreeObserver().addOnPreDrawListener(new 12(this));
        this.mSendBtn = (Button) this.root.findViewById(R.id.half_comment_send_btn);
        this.mSendBtn.setOnClickListener(this.sendCommentClick);
        this.mSendBtn.setEnabled(false);
        this.mTipText = (TextView) this.root.findViewById(R.id.comment_edit_text_tip);
        this.mImageTip = (TextView) this.root.findViewById(R.id.comment_image_select_text_tip);
        this.mEditText.setFilters(new InputFilter[]{new LengthFilter(COMMENT_COUNT)});
        this.mEditText.setOnEditorActionListener(this.onEditorActionListener);
        this.mEditText.setOnClickListener(this.onEidtClickListener);
        this.mEditText.addTextChangedListener(new CommentTextWatcher(this, null));
        this.mEditText.setText(BaseTypeUtils.ensureStringValidate(this.comment));
        this.mEditText.setSelection(BaseTypeUtils.ensureStringValidate(this.comment).length());
        this.smiliesFragment = SmiliesFragment.getCommentEmojiFragment();
        this.smiliesFragment.attachSmiliesFragment(getChildFragmentManager(), R.id.emoji_container, this.mEditText);
        this.toggleButton = (ToggleButton) this.root.findViewById(R.id.emoji_keyboard_toggle_btn);
        this.toggleButton.setOnCheckedChangeListener(this.toggleCheckChangeListener);
        this.toggleButton.setOnTouchListener(new 13(this));
        this.mTogglePhotoButton = (ToggleButton) this.root.findViewById(R.id.iv_add_photo);
        this.mGuideLayout = (RelativeLayout) this.root.findViewById(R.id.detailplay_guide_layout);
        this.mCommentText1 = (TextView) this.root.findViewById(R.id.comment_text1);
        this.mCommentText2 = (TextView) this.root.findViewById(R.id.comment_text2);
        this.mGoVideoshot = (Button) this.root.findViewById(R.id.go_videoshot_btn);
        this.mTogglePhotoButton.setOnCheckedChangeListener(this.toggleAddPhotoCheckChangeListener);
        this.mRecyclerView = (RecyclerView) this.root.findViewById(R.id.id_recyclerview_horizontal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(0);
        this.mRecyclerView.setLayoutManager(linearLayoutManager);
        this.mAdapter = new LetvGalleryAdapter(getActivity(), this.photoFileName, this.onSelectImageListener);
        this.mRecyclerView.setAdapter(this.mAdapter);
        if (this.mFromType == 1 && this.isComment && this.mIsHalfAlbumShowPic) {
            this.mTogglePhotoButton.setVisibility(0);
        } else {
            this.mTogglePhotoButton.setVisibility(8);
        }
        this.mEditText.setCursorVisible(true);
        this.mEditText.requestFocus();
    }

    private void registerVideoshotFinishBroadcast() {
        IntentFilter mfFilter = new IntentFilter();
        mfFilter.addAction("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        mfFilter.addAction(FileUtils.VIDEOSHOT_DOWNLOAD_INTENT_BROADCAST);
        getActivity().registerReceiver(this.mVideoshotRecever, mfFilter);
    }

    private void unRegisterBroadcast() {
        if (this.mVideoshotRecever != null) {
            getActivity().unregisterReceiver(this.mVideoshotRecever);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (UIsUtils.isLandscape(getActivity())) {
            dismissAllowingStateLoss();
        }
        super.onConfigurationChanged(newConfig);
    }

    public void onResume() {
        super.onResume();
        LogInfo.log("Emerson", "-----halfplaySoft---onResume");
        this.mEditText.setCursorVisible(true);
        this.mEditText.requestFocus();
        if (!this.isOnCreate) {
            loadVideoShot();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void onDestroy() {
        this.hint = null;
        this.mEditText = null;
        this.mSendBtn = null;
        super.onDestroy();
        unRegisterBroadcast();
    }

    public void setOnSoftKeyBoardListener(OnSoftKeyBoardListener softKeyBoardListener) {
        this.mSoftKeyBoardListener = softKeyBoardListener;
    }

    public void showSoftInputKey() {
        if (!TextUtils.isEmpty(this.comment)) {
            this.mEditText.setText(this.comment);
            this.mEditText.setSelection(this.comment.length());
        }
        if (TextUtils.isEmpty(this.hint) || !TextUtils.isEmpty(this.comment)) {
            this.mEditText.setHint(getActivity().getResources().getString(R.string.detail_comment_text_hint_play));
            this.mTipText.setVisibility(0);
            if (!TextUtils.isEmpty(this.mEditText.getText())) {
                commented_count = this.mEditText.getText().length();
            }
            if (COMMENT_COUNT != commented_count) {
                this.mTipText.setText(String.valueOf(140 - commented_count));
                this.mTipText.setTextColor(getActivity().getResources().getColor(R.color.letv_color_ffbbbbbb));
                return;
            }
            this.mTipText.setText(R.string.detail_half_comment_edit_text_all);
            this.mTipText.setTextColor(getActivity().getResources().getColor(R.color.letv_color_ffef4444));
            return;
        }
        LogInfo.log("fornia", "showSoftInputKey显示hint:" + this.hint);
        if (this.isVote) {
            this.mEditText.setHint(StringUtils.clipStringWithellipsis(this.hint, 26));
        } else {
            this.mEditText.setHint(StringUtils.clipStringWithellipsis(this.hint, 26));
        }
    }

    public void setMyBundleParam(Bundle bundle) {
        this.bundle = bundle;
    }

    public void onDestroyView() {
        super.onDestroyView();
        LogInfo.log("king", "onDestroyView");
        if (this.mEditText != null) {
            String comment = this.mEditText.getText().toString();
            if (this.isComment) {
                LetvPlayerContants.setComment(comment);
            }
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        LogInfo.log("songhang", "----------------onDismiss--------------------");
        if (this.mSoftKeyBoardListener != null) {
            this.mSoftKeyBoardListener.onDismiss();
        }
        if (this.mEditText != null) {
            LetvPlayerContants.setComment(this.mEditText.getText().toString());
        }
    }

    private void updatePhotoViewHeight(int height) {
        this.mRecyclerView.getLayoutParams().height = height;
        this.mGuideLayout.getLayoutParams().height = height;
    }
}
