package com.letv.android.client.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

@TargetApi(11)
@SuppressLint({"NewApi"})
public class FeedBackAnimFragment extends Fragment implements OnClickListener {
    public static final int PHOTO_ALBUM_WIDGET = 0;
    public static final int PHOTO_DELETE_WIDGET = 1;
    private TextView mAlbumCancel;
    private LinearLayout mAlbumLayout;
    private TranslateAnimation mAnimation;
    private TextView mDeleteCancel;
    private LinearLayout mDeleteLayout;
    private View mFeedBackLayout;
    private LinearLayout mFloatLayout;
    private FlowCallBack mFlowCallBack;
    private TextView mOpenAlbumTv;
    private TextView mPicDelete;
    private TextView mPicReselect;

    public FeedBackAnimFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public LinearLayout getmFloatLayout() {
        return this.mFloatLayout;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mFeedBackLayout = inflater.inflate(R.layout.feedback_anim, null, false);
        return this.mFeedBackLayout;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void selectAnimWidget(int type) {
        if (this.mAlbumLayout.getVisibility() != 0 && this.mDeleteLayout.getVisibility() != 0) {
            switch (type) {
                case 0:
                    startAnim(this.mAlbumLayout);
                    return;
                case 1:
                    startAnim(this.mDeleteLayout);
                    return;
                default:
                    return;
            }
        }
    }

    public void set_album_wedget_gone() {
        this.mAlbumLayout.setVisibility(8);
    }

    public void set_delete_wedgit_gone() {
        this.mDeleteLayout.setVisibility(8);
    }

    public void setmFlowCallBack(FlowCallBack mFlowCallBack) {
        this.mFlowCallBack = mFlowCallBack;
    }

    private void initView() {
        this.mFloatLayout = (LinearLayout) this.mFeedBackLayout.findViewById(R.id.float_layout);
        this.mFloatLayout.setOnClickListener(this);
        this.mAlbumLayout = (LinearLayout) this.mFeedBackLayout.findViewById(R.id.photo_album);
        this.mDeleteLayout = (LinearLayout) this.mFeedBackLayout.findViewById(R.id.photo_delete);
        this.mAlbumCancel = (TextView) this.mFeedBackLayout.findViewById(R.id.album_cancel);
        this.mAlbumCancel.setOnClickListener(this);
        this.mOpenAlbumTv = (TextView) this.mFeedBackLayout.findViewById(R.id.open_album);
        this.mOpenAlbumTv.setOnClickListener(this);
        this.mPicDelete = (TextView) this.mFeedBackLayout.findViewById(R.id.pic_delete);
        this.mPicDelete.setOnClickListener(this);
        this.mPicReselect = (TextView) this.mFeedBackLayout.findViewById(R.id.pic_reselect);
        this.mPicReselect.setOnClickListener(this);
        this.mDeleteCancel = (TextView) this.mFeedBackLayout.findViewById(R.id.pic_delete_cancel);
        this.mDeleteCancel.setOnClickListener(this);
    }

    private void startAnim(View widget) {
        int distance = widget.getHeight();
        widget.setVisibility(0);
        this.mFloatLayout.setVisibility(0);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(200);
        alphaAnimation.setStartOffset(200);
        this.mFloatLayout.setAnimation(alphaAnimation);
        this.mAnimation = new TranslateAnimation(0.0f, 0.0f, (float) distance, 0.0f);
        this.mAnimation.setDuration(200);
        this.mAnimation.setStartOffset(200);
        widget.setAnimation(this.mAnimation);
        this.mAnimation.start();
        alphaAnimation.start();
    }

    public void onClick(View view) {
        this.mFloatLayout.setVisibility(8);
        this.mAlbumLayout.setVisibility(8);
        this.mDeleteLayout.setVisibility(8);
        switch (view.getId()) {
            case R.id.open_album /*2131362558*/:
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                getActivity().startActivityForResult(intent, 0);
                return;
            case R.id.pic_delete /*2131362561*/:
                this.mFlowCallBack.pic_Delete();
                return;
            case R.id.pic_reselect /*2131362562*/:
                this.mFlowCallBack.pic_Reselect();
                return;
            default:
                return;
        }
    }
}
