package com.letv.mobile.lebox.ui.download;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.mobile.lebox.LeBoxApp;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.heartbeat.HeartbeatManager;
import com.letv.mobile.lebox.heartbeat.HeartbeatObserver;
import com.letv.mobile.lebox.http.lebox.bean.TaskVideoBean;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;
import com.letv.mobile.lebox.ui.WrapActivity.IBatchDel;
import com.letv.mobile.lebox.utils.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyDownloadFinishFragment extends BaseDownloadFragment implements IBatchDel {
    private static final String TAG = MyDownloadFinishFragment.class.getSimpleName();
    private DownloadFinishAdapter mDownloadFinishAdapter;
    private View mHeaderLayout;
    HeartbeatObserver mHeartbeatObserver = new 3(this);
    private ListView mListView;
    private LinearLayout mNullTip;
    private View mView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Logger.d(TAG, "MyDownloadFinishFragment onCreateView");
        initView();
        return this.mView;
    }

    private void initView() {
        if (getCurrentActivity() == null) {
            this.mView = LayoutInflater.from(LeBoxApp.getApplication()).inflate(R.layout.fragment_lebox_download_template, null);
        } else {
            this.mView = getCurrentActivity().getLayoutInflater().inflate(R.layout.fragment_lebox_download_template, null);
        }
        this.mListView = (ListView) this.mView.findViewById(R.id.listv);
        this.mDownloadFinishAdapter = new DownloadFinishAdapter(this, this.mContext);
        this.mListView.setAdapter(this.mDownloadFinishAdapter);
        this.mNullTip = (LinearLayout) this.mView.findViewById(R.id.linearlayout_null_tip_download);
        this.mHeaderLayout = this.mView.findViewById(R.id.operation_bar);
        ((TextView) this.mView.findViewById(R.id.download_null_tv_id)).setText(R.string.tip_download__null_msg);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.v(TAG, "setUserVisibleHint isVisibleToUser : " + isVisibleToUser + " getCurrentActivity : " + getCurrentActivity());
    }

    public int getFragmentID() {
        return 0;
    }

    private void initData() {
        showLoadingDialog();
        HttpRequesetManager.getInstance().getAllDownloadFinishTask(new 1(this));
    }

    private void checkAdapterEmpty(boolean isEmpty) {
        if (isEmpty) {
            this.mNullTip.setVisibility(0);
            this.mListView.setVisibility(8);
            this.mHeaderLayout.setVisibility(8);
            return;
        }
        this.mNullTip.setVisibility(8);
        this.mListView.setVisibility(0);
    }

    public void onShowEditState() {
    }

    public void onCancelEditState() {
    }

    public void onDoBatchDelete() {
        Set<DownloadAlbum> albumSet = this.mDownloadFinishAdapter.getDeleteSet();
        if (albumSet.size() > 0) {
            List deleteVidList = new ArrayList();
            for (DownloadAlbum album : albumSet) {
                for (TaskVideoBean video : album.getVideoAlbum()) {
                    deleteVidList.add(video.getVid());
                }
            }
            showLoadingDialog();
            HttpRequesetManager.getInstance().deleteTask(deleteVidList, new 2(this), "1");
        }
    }

    public void onSelectAll() {
        if (this.mDownloadFinishAdapter != null) {
            this.mDownloadFinishAdapter.selectAllOrNot(true);
        }
    }

    public boolean onIsAdapterEmpty() {
        boolean isHas = false;
        if (this.mDownloadFinishAdapter != null) {
            if (this.mDownloadFinishAdapter.getCount() > 0) {
                isHas = true;
            }
            Logger.d(TAG, "isAdapterEmpty isHas : " + isHas);
            if (isHas) {
                return false;
            }
            return true;
        } else if (null == null) {
            return true;
        } else {
            return false;
        }
    }

    public void onClearSelectAll() {
        if (this.mDownloadFinishAdapter != null) {
            this.mDownloadFinishAdapter.selectAllOrNot(false);
        }
    }

    public int onSelectNum() {
        return this.mDownloadFinishAdapter.getBatchDelNum();
    }

    public void onInitData() {
        initData();
    }

    public void onStart() {
        initRegister();
        initData();
        super.onStart();
    }

    public void onStop() {
        UnRegister();
        super.onStop();
    }

    private void initRegister() {
        HeartbeatManager.getInstance().regustHeartbeatObserver(this.mHeartbeatObserver);
    }

    private void UnRegister() {
        HeartbeatManager.getInstance().unRegustHeartbeatObserver(this.mHeartbeatObserver);
    }
}
