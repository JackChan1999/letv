package com.letv.mobile.lebox.ui.download;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.heartbeat.HeartbeatManager;
import com.letv.mobile.lebox.heartbeat.HeartbeatObserver;
import com.letv.mobile.lebox.http.lebox.bean.TaskVideoBean;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;
import com.letv.mobile.lebox.ui.WrapActivity.IBatchDel;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyDownloadingFragment extends BaseDownloadFragment implements OnClickListener, IBatchDel {
    private static final String TAG = MyDownloadingFragment.class.getSimpleName();
    private BaseBatchDelActivity mBaseBatchDelActivity;
    private MyDownloadingAdapter mDownloadAdapter;
    HeartbeatObserver mHeartbeatObserver = new 5(this);
    private ListView mListView;
    private LinearLayout mNullTip;
    int mOldDownloadingNum = 0;
    private View mOperationBarView;
    private Button mPauseAllBtn;
    private Button mStartAllBtn;
    private View mView;
    private MyDownloadActivity myDownloadActivity;

    @SuppressLint({"InflateParams"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Logger.d(TAG, " MyDownloadingFragment onCreateView");
        this.myDownloadActivity = (MyDownloadActivity) getCurrentActivity();
        this.mBaseBatchDelActivity = getCurrentActivity();
        this.mView = getCurrentActivity().getLayoutInflater().inflate(R.layout.fragment_lebox_download_template, null);
        initView();
        return this.mView;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.d(TAG, "setUserVisibleHint isVisibleToUser : " + isVisibleToUser);
    }

    private void initView() {
        this.mOperationBarView = this.mView.findViewById(R.id.operation_bar);
        this.mNullTip = (LinearLayout) this.mView.findViewById(R.id.linearlayout_null_tip_download);
        this.mListView = (ListView) this.mView.findViewById(R.id.listv_unfinish);
        this.mStartAllBtn = (Button) this.mOperationBarView.findViewById(R.id.btn_start_all);
        this.mPauseAllBtn = (Button) this.mOperationBarView.findViewById(R.id.btn_pause_all);
        this.mPauseAllBtn.setOnClickListener(this);
        this.mStartAllBtn.setOnClickListener(this);
        this.mListView = (ListView) this.mView.findViewById(R.id.listv_unfinish);
        this.mDownloadAdapter = new MyDownloadingAdapter(this, this.mContext);
        this.mListView.setAdapter(this.mDownloadAdapter);
        ((TextView) this.mView.findViewById(R.id.download_null_tv_id)).setText(R.string.tip_download__null_msg);
    }

    private void initData() {
        HttpRequesetManager.getInstance().getAllUnfinishTask(new 1(this));
    }

    private void initButton() {
        if (this.mDownloadAdapter.getAllTaskStatus() == 0) {
            setButtonStatus(this.mPauseAllBtn, false);
            setButtonStatus(this.mStartAllBtn, true);
            return;
        }
        setButtonStatus(this.mPauseAllBtn, true);
        setButtonStatus(this.mStartAllBtn, false);
    }

    public void checkAdapterEmpty() {
        if (this.mDownloadAdapter != null) {
            if (this.mDownloadAdapter.isEmpty()) {
                this.mNullTip.setVisibility(0);
                this.mListView.setVisibility(8);
                this.mOperationBarView.setVisibility(8);
                return;
            }
            this.mNullTip.setVisibility(8);
            this.mListView.setVisibility(0);
            if (!this.mBaseBatchDelActivity.isEditing()) {
                this.mOperationBarView.setVisibility(0);
            }
        }
    }

    public int getFragmentID() {
        return 1;
    }

    private void setButtonStatus(Button button, boolean canClick) {
        Logger.d(TAG, " setButtonStatus canClick : " + canClick);
        if (canClick) {
            button.setClickable(true);
            button.setBackgroundResource(R.drawable.btn_blue_selecter);
            button.setTextAppearance(this.mContext, R.style.letv_text_13_blue_white);
            return;
        }
        button.setClickable(false);
        button.setBackgroundResource(R.drawable.btn_grey);
        button.setTextColor(Util.getContext().getResources().getColor(R.color.letv_color_ffa1a1a1));
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_start_all) {
            HttpRequesetManager.getInstance().setTaskStart(this.mDownloadAdapter.getAllVid(), new 2(this));
        } else if (v.getId() == R.id.btn_pause_all) {
            HttpRequesetManager.getInstance().setTaskPause(this.mDownloadAdapter.getAllVid(), new 3(this));
        }
    }

    public void onSelectAll() {
        if (this.mDownloadAdapter != null) {
            this.mDownloadAdapter.selectAllOrNot(true);
        }
    }

    public boolean onIsAdapterEmpty() {
        boolean isHas = false;
        if (this.mDownloadAdapter != null) {
            if (this.mDownloadAdapter.getCount() > 0) {
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
        if (this.mDownloadAdapter != null) {
            this.mDownloadAdapter.selectAllOrNot(false);
        }
    }

    public int onSelectNum() {
        return this.mDownloadAdapter.getBatchDelNum();
    }

    public void onShowEditState() {
        this.mOperationBarView.setVisibility(8);
    }

    public void onCancelEditState() {
        checkAdapterEmpty();
    }

    public void onDoBatchDelete() {
        Set<TaskVideoBean> videoSet = this.mDownloadAdapter.getDeleteSet();
        if (videoSet.size() > 0) {
            List deleteVidList = new ArrayList();
            for (TaskVideoBean video : videoSet) {
                deleteVidList.add(video.getVid());
            }
            showLoadingDialog();
            HttpRequesetManager.getInstance().deleteTask(deleteVidList, new 4(this), "2");
        }
    }

    public void onStart() {
        super.onStart();
        initRegister();
        initData();
        this.myDownloadActivity.showDownloadingNum(this.mDownloadAdapter.getCount());
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public void onStop() {
        super.onStop();
        UnRegister();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onInitData() {
        initData();
    }

    private void initRegister() {
        HeartbeatManager.getInstance().regustHeartbeatObserver(this.mHeartbeatObserver);
    }

    private void UnRegister() {
        HeartbeatManager.getInstance().unRegustHeartbeatObserver(this.mHeartbeatObserver);
    }
}
