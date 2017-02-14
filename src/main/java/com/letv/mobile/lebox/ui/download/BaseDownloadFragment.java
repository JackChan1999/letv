package com.letv.mobile.lebox.ui.download;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.letv.mobile.lebox.LeBoxApp;
import com.letv.mobile.lebox.view.CustomLoadingDialog;

public abstract class BaseDownloadFragment extends Fragment {
    public static final int DOWNLOADFINISH_FRAGMENT_ID = 0;
    public static final int DOWNLOADFINISH_HALD_PLAY_LANDSCAPE_ID = 4;
    public static final int DOWNLOADFINISH_HALD_PLAY_PORTRAIT_ID = 5;
    public static final int DOWNLOADING_FRAGMENT_ID = 1;
    public static final int LOCAL_FRAGMENT_ID = 2;
    public static final int RANDOMSEE_FRAGMENT_ID = 3;
    private static final String TAG = BaseDownloadFragment.class.getSimpleName();
    public Context mContext;
    public CustomLoadingDialog mDialog;

    public abstract int getFragmentID();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mContext = LeBoxApp.getApplication();
        this.mDialog = new CustomLoadingDialog(getActivity());
        this.mDialog.setCanceledOnTouchOutside(false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void showLoadingDialog() {
        try {
            if (this.mDialog.isShowing()) {
                this.mDialog.cancel();
            } else {
                this.mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelLoadingDialog() {
        try {
            if (this.mDialog != null && this.mDialog.isShowing()) {
                this.mDialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPause() {
        super.onPause();
        try {
            if (this.mDialog.isShowing()) {
                this.mDialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BaseBatchDelActivity getCurrentActivity() {
        if (getActivity() == null) {
            return BaseBatchDelActivity.getActivity();
        }
        return (BaseBatchDelActivity) getActivity();
    }
}
