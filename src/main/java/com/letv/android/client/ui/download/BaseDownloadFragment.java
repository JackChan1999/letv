package com.letv.android.client.ui.download;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.letv.android.client.utils.CursorLoader;
import com.letv.android.client.view.CustomLoadingDialog;
import com.letv.download.manager.DownloadManager;
import com.letv.download.util.L;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public abstract class BaseDownloadFragment extends Fragment implements LoaderCallbacks<Cursor> {
    public static final int DOWNLOADFINISH_FRAGMENT_ID = 0;
    public static final int DOWNLOADFINISH_HALD_PLAY_LANDSCAPE_ID = 4;
    public static final int DOWNLOADFINISH_HALD_PLAY_PORTRAIT_ID = 5;
    public static final int DOWNLOADING_FRAGMENT_ID = 1;
    public static final int LOCAL_FRAGMENT_ID = 2;
    public static final int RANDOMSEE_FRAGMENT_ID = 3;
    public Context mContext;
    public CustomLoadingDialog mDialog;

    public abstract int getFragmentID();

    public BaseDownloadFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = getActivity().getApplicationContext();
        this.mDialog = new CustomLoadingDialog(getActivity());
        this.mDialog.setCanceledOnTouchOutside(false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initLoader();
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
    }

    private void initLoader() {
        switch (getFragmentID()) {
            case 0:
                getActivity().getSupportLoaderManager().initLoader(0, null, this);
                return;
            case 1:
                getActivity().getSupportLoaderManager().initLoader(1, null, this);
                return;
            default:
                return;
        }
    }

    public void stopLoading() {
        if (getActivity() != null) {
            getActivity().getSupportLoaderManager().getLoader(getFragmentID()).stopLoading();
        }
    }

    public void forceLoading() {
        if (getActivity() != null) {
            getActivity().getSupportLoaderManager().getLoader(getFragmentID()).forceLoad();
        }
    }

    public void startLoading() {
        if (getActivity() != null) {
            getActivity().getSupportLoaderManager().getLoader(getFragmentID()).startLoading();
        }
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        L.v("", "onCreateLoader id >> " + id + " getFragmentID " + getFragmentID());
        switch (getFragmentID()) {
            case 0:
                return new CursorLoader(this.mContext, DownloadManager.DOWNLOAD_ALBUM_URI, null, "albumVideoNum != 0", null, "timestamp DESC ");
            case 1:
                String order = "timestamp ACS ";
                return new CursorLoader(this.mContext, DownloadManager.DOWNLOAD_VIDEO_URI, null, "state != 4", null, null);
            default:
                return null;
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
