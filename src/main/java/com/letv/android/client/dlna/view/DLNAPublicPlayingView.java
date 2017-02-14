package com.letv.android.client.dlna.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.letv.android.client.dlna.R;
import com.letv.android.client.dlna.inter.DLNAListener;
import com.letv.core.BaseApplication;

public class DLNAPublicPlayingView implements OnClickListener {
    private TextView mChangeDeviceView;
    private DLNAListener mListener;
    private TextView mRetryView;
    private View mRoot;
    private TextView mStopView;
    private TextView mTitleView;

    public DLNAPublicPlayingView(View root, DLNAListener listener) {
        if (root == null) {
            throw new NullPointerException("DLNAPublicPlayingView root is null");
        }
        this.mRoot = root;
        this.mListener = listener;
        this.mTitleView = (TextView) this.mRoot.findViewById(R.id.dlna_playing_title);
        this.mRetryView = (TextView) this.mRoot.findViewById(R.id.dlna_replay);
        this.mStopView = (TextView) this.mRoot.findViewById(R.id.dlna_stop_playing);
        this.mChangeDeviceView = (TextView) this.mRoot.findViewById(R.id.dlna_change_device);
        this.mRetryView.setOnClickListener(this);
        this.mStopView.setOnClickListener(this);
        this.mChangeDeviceView.setOnClickListener(this);
    }

    public void show() {
        this.mRoot.setVisibility(0);
    }

    public void hide() {
        this.mRoot.setVisibility(8);
    }

    public void setTitle(String deviceName) {
        this.mTitleView.setText(String.format(BaseApplication.getInstance().getString(R.string.dlna_playing), new Object[]{deviceName}));
        this.mRetryView.setVisibility(8);
    }

    public void tvExit() {
        this.mTitleView.setText(R.string.dlna_tv_exit);
        this.mRetryView.setVisibility(0);
    }

    public boolean isTvExit() {
        return this.mRetryView.getVisibility() == 0;
    }

    public void onClick(View v) {
        boolean z = false;
        int id = v.getId();
        if (id == R.id.dlna_replay) {
            if (this.mListener != null) {
                this.mListener.rePlay(this.mListener.getDevice());
            }
        } else if (id == R.id.dlna_stop_playing) {
            if (this.mListener != null) {
                DLNAListener dLNAListener = this.mListener;
                if (!isTvExit()) {
                    z = true;
                }
                dLNAListener.stop(true, z);
            }
        } else if (id == R.id.dlna_change_device && this.mListener != null) {
            this.mListener.startSearch(false);
        }
    }
}
