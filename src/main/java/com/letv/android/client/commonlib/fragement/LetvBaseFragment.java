package com.letv.android.client.commonlib.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class LetvBaseFragment extends Fragment implements LetvFragmentListener {
    private OnFragmentStateCallback mCallback;
    protected View mContentView;
    protected Context mContext;
    protected boolean mHasInited;
    protected boolean mIsHidden = false;

    public interface OnFragmentStateCallback {
        void inited();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mHasInited = true;
        if (this.mCallback != null) {
            this.mCallback.inited();
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    protected <T extends View> T getViewById(int id) {
        return getViewById(this.mContentView, id);
    }

    protected <T extends View> T getViewById(View parent, int id) {
        if (this.mContentView != null) {
            return parent.findViewById(id);
        }
        return null;
    }

    public View getContentView() {
        return this.mContentView;
    }

    public void setCallback(OnFragmentStateCallback callback) {
        this.mCallback = callback;
    }

    public void onShow() {
    }

    public void onHide() {
    }

    public int getDisappearFlag() {
        return 0;
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.mIsHidden = hidden;
        if (this.mCallback != null && !hidden) {
            this.mCallback.inited();
        }
    }
}
