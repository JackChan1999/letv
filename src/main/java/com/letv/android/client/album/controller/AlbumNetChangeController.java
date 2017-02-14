package com.letv.android.client.album.controller;

import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.controller.AlbumErrorTopController.AlbumErrorFlag;
import com.letv.business.flow.album.AlbumPlayFlowObservable;
import com.letv.business.flow.album.AlbumPlayFlowObservable.VideoTitleNotify;
import com.letv.core.utils.TipUtils;
import java.util.Observable;
import java.util.Observer;

public class AlbumNetChangeController implements Observer {
    private AlbumPlayActivity mActivity;
    private View mClickView;
    private View mContainView;
    private View mContinueView;
    private View mInterputView;
    private boolean mIsAfterPlayCombieAd;

    public AlbumNetChangeController(AlbumPlayActivity activity) {
        this.mActivity = activity;
        init();
    }

    private void init() {
        this.mContainView = this.mActivity.findViewById(R.id.album_net_frame);
        this.mInterputView = this.mActivity.findViewById(R.id.net_top_interput_click);
        this.mContinueView = this.mActivity.findViewById(R.id.album_net_change_continue);
        this.mClickView = this.mActivity.findViewById(R.id.album_net_change_click_frame);
        ((TextView) this.mActivity.findViewById(R.id.album_net_change_text1)).setText(TipUtils.getTipMessage("100073", R.string.play_view_text_top));
        TextView continueView = (TextView) this.mActivity.findViewById(R.id.album_net_change_continue);
        continueView.setText(TipUtils.getTipMessage("100074", R.string.album_net_change_continue));
        if (this.mActivity.mIsPlayingNonCopyright) {
            r2 = new int[2][];
            r2[0] = new int[]{16842919};
            r2[1] = new int[0];
            continueView.setTextColor(new ColorStateList(r2, new int[]{-1, this.mActivity.getResources().getColor(R.color.letv_color_noncopyright)}));
            continueView.setBackgroundResource(R.drawable.noncopyright_btn_selector);
        }
        this.mInterputView.setOnClickListener(new 1(this));
        this.mContinueView.setOnClickListener(new 2(this));
    }

    public void setIsAfterPlayCombieAd(boolean isAfterPlayCombieAd) {
        this.mIsAfterPlayCombieAd = isAfterPlayCombieAd;
    }

    private void show3gLayout(boolean isBlack) {
        this.mContainView.setVisibility(0);
        if (isBlack) {
            this.mClickView.setVisibility(8);
        } else {
            this.mClickView.setVisibility(0);
        }
        this.mActivity.getErrorTopController().show(AlbumErrorFlag.NetError);
        this.mInterputView.setVisibility(0);
    }

    private void hide3gLayout() {
        this.mContainView.setVisibility(8);
        this.mActivity.getErrorTopController().hide(AlbumErrorFlag.NetError);
    }

    public void update(Observable observable, Object data) {
        if (data instanceof String) {
            String notify = (String) data;
            if (TextUtils.equals(AlbumPlayFlowObservable.ON_SHOW_3G, notify)) {
                show3gLayout(false);
            } else if (TextUtils.equals(AlbumPlayFlowObservable.ON_SHOW_3G_WITH_BLACK, notify)) {
                show3gLayout(true);
            } else if (TextUtils.equals(AlbumPlayFlowObservable.ON_HIDE_3G, notify)) {
                hide3gLayout();
            } else if (TextUtils.equals(AlbumPlayFlowObservable.ON_NETWORK_DISCONNECTED, notify) && this.mActivity.getVideoController().mDlnaProtocol != null) {
                this.mActivity.getVideoController().mDlnaProtocol.protocolDisconnect();
            }
        } else if (data instanceof VideoTitleNotify) {
            this.mActivity.getErrorTopController().setTitle(((VideoTitleNotify) data).title);
        }
    }
}
