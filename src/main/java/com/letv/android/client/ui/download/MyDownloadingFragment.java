package com.letv.android.client.ui.download;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.BaseBatchDelActivity;
import com.letv.android.client.commonlib.activity.WrapActivity.IBatchDel;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.DialogUtil;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.download.manager.DownloadManager;
import com.letv.download.manager.StoreManager;
import com.letv.download.manager.StoreManager.StoreDeviceInfo;
import com.letv.download.util.DownloadStatisticsUtil.DownloadPauseStatistics;
import com.letv.download.util.L;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MyDownloadingFragment extends BaseDownloadFragment implements OnClickListener, IBatchDel {
    private static final String TAG = MyDownloadingFragment.class.getSimpleName();
    private boolean isVisibleToUser;
    private BaseBatchDelActivity mBaseBatchDelActivity;
    private MyDownloadingListAdapter mDownloadAdapter;
    private ListView mListView;
    private LinearLayout mNullTip;
    int mOldDownloadingNum;
    private View mOperationBarView;
    private Button mPauseAllBtn;
    private Button mStartAllBtn;
    private View mView;
    private MyDownloadActivity myDownloadActivity;

    public MyDownloadingFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mOldDownloadingNum = 0;
        this.isVisibleToUser = false;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.v(TAG, " MyDownloadingFragment onCreateView");
        this.myDownloadActivity = (MyDownloadActivity) getActivity();
        this.mBaseBatchDelActivity = (BaseBatchDelActivity) getActivity();
        this.mView = getActivity().getLayoutInflater().inflate(R.layout.fragment_my_download_template, null);
        initView();
        this.myDownloadActivity.showDownloadingNum(this.mDownloadAdapter.getCount());
        super.onCreateView(inflater, container, savedInstanceState);
        return this.mView;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        L.v(TAG, "setUserVisibleHint isVisibleToUser : " + isVisibleToUser);
    }

    private void initView() {
        this.mOperationBarView = this.mView.findViewById(2131362660);
        this.mNullTip = (LinearLayout) this.mView.findViewById(2131362662);
        this.mListView = (ListView) this.mView.findViewById(2131362661);
        this.mStartAllBtn = (Button) this.mOperationBarView.findViewById(2131362659);
        this.mPauseAllBtn = (Button) this.mOperationBarView.findViewById(2131362658);
        this.mPauseAllBtn.setOnClickListener(this);
        this.mStartAllBtn.setOnClickListener(this);
        this.mListView = (ListView) this.mView.findViewById(2131362661);
        this.mDownloadAdapter = new MyDownloadingListAdapter(this, this.mContext, null);
        this.mListView.setAdapter(this.mDownloadAdapter);
        ((TextView) this.mView.findViewById(2131362663)).setText(TipUtils.getTipMessage("700000", 2131100977));
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

    public void checkAdapterEmpty(Cursor cursor) {
        if (this.mDownloadAdapter != null) {
            if (cursor == null || cursor.getCount() == 0) {
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

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == 1) {
            if (this.mListView.getVisibility() == 8) {
                this.mListView.setVisibility(0);
            }
            if (this.mDownloadAdapter != null) {
                this.mDownloadAdapter.changeCursor(data);
            }
            if (this.myDownloadActivity != null) {
                this.myDownloadActivity.updateStoreSpace();
            }
            checkAdapterEmpty(data);
            if (!(this.mDownloadAdapter == null || !this.mDownloadAdapter.isEmpty() || this.myDownloadActivity == null || this.myDownloadActivity.isFinishing())) {
                this.myDownloadActivity.updateEditViewState();
            }
            int mNewDownloadingNum = this.mDownloadAdapter.getCount();
            if (this.mOldDownloadingNum != mNewDownloadingNum) {
                this.myDownloadActivity.showDownloadingNum(mNewDownloadingNum);
            }
            this.mOldDownloadingNum = mNewDownloadingNum;
            traversalAllDownloading();
        }
    }

    private boolean isNoVipAllVideoPause() {
        boolean isVip = PreferencesManager.getInstance().isVip();
        int count = 0;
        for (int i = 0; i < this.mDownloadAdapter.getCount(); i++) {
            Cursor cursor = (Cursor) this.mDownloadAdapter.getItem(i);
            if (!(cursor == null || cursor.isClosed())) {
                boolean isVipDownload = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadVideoTable.COLUMN_ISVIP_DOWNLOAD)) == 1;
                int status = cursor.getInt(cursor.getColumnIndexOrThrow("state"));
                LogInfo.log(TAG, " isNoVipAllVideoPause status " + status + " isVipDownload " + isVipDownload + " isVip : " + isVip);
                if (status == 3 && isVipDownload && !isVip) {
                    count++;
                }
            }
        }
        LogInfo.log(TAG, " isNoVipAllVideoPause count " + count + " getCount " + this.mDownloadAdapter.getCount());
        if (count == this.mDownloadAdapter.getCount()) {
            return true;
        }
        return false;
    }

    private void traversalAllDownloading() {
        for (int i = 0; i < this.mDownloadAdapter.getCount(); i++) {
            Cursor cursor = (Cursor) this.mDownloadAdapter.getItem(i);
            if (!(cursor == null || cursor.isClosed())) {
                if (cursor.getInt(cursor.getColumnIndexOrThrow("state")) == 1) {
                    setButtonStatus(this.mPauseAllBtn, true);
                    setButtonStatus(this.mStartAllBtn, false);
                    return;
                }
                setButtonStatus(this.mPauseAllBtn, false);
                setButtonStatus(this.mStartAllBtn, true);
            }
        }
    }

    private void showSpaceShortageDialog(String path) {
        StoreDeviceInfo phoneStoreDeviceInfo = StoreManager.getPhoneStoreDeviceInfo();
        if (!TextUtils.isEmpty(path) && phoneStoreDeviceInfo != null) {
            if (path.equals(phoneStoreDeviceInfo.mPath)) {
                DialogUtil.showDialog(getActivity(), this.mContext.getResources().getString(2131100983), this.mContext.getString(2131100002), new 1(this));
            } else {
                DialogUtil.showDialog(getActivity(), this.mContext.getResources().getString(2131100984), this.mContext.getString(2131100002), new 2(this));
            }
        }
    }

    private void setButtonStatus(Button button, boolean canClick) {
        LogInfo.log(TAG, " setButtonStatus canClick : " + canClick);
        if (canClick) {
            button.setEnabled(true);
        } else {
            button.setEnabled(false);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case 2131362658:
                DownloadManager.pauseAllDownload();
                if (this.mDownloadAdapter != null && this.mDownloadAdapter.getCount() > 0) {
                    Cursor cursor = (Cursor) this.mDownloadAdapter.getItem(0);
                    if (!(cursor == null || cursor.isClosed())) {
                        DownloadPauseStatistics.downloadPauseReport(cursor.getString(cursor.getColumnIndexOrThrow("speed")), 1);
                    }
                }
                StatisticsUtils.staticticsInfoPost(this.mContext, "0", "e31", "全部暂停", 1, null, PageIdConstant.downloadingPage, null, null, null, null, null);
                return;
            case 2131362659:
                if (NetworkUtils.isNetworkAvailable()) {
                    if (!NetworkUtils.isWifi()) {
                        if (PreferencesManager.getInstance().isAllowMobileNetwork()) {
                            ToastUtils.showToast(getActivity(), 2131100010);
                        } else {
                            DialogUtil.call(getActivity(), getActivity().getResources().getString(2131099997), new 3(this));
                            return;
                        }
                    }
                    String downloadPath = StoreManager.getDownloadPath();
                    LogInfo.log(TAG, ">>onClick downloadPath : " + downloadPath);
                    if (TextUtils.isEmpty(downloadPath) || StoreManager.isSdCardPull() || StoreDeviceInfo.getAvailableSpace(downloadPath) >= StoreManager.DEFUALT_DOWNLOAD_MINI_SIZE) {
                        DownloadManager.startAllDownload();
                    } else {
                        showSpaceShortageDialog(downloadPath);
                        return;
                    }
                }
                ToastUtils.showToast(this.mContext, 2131100332);
                StatisticsUtils.staticticsInfoPost(this.mContext, "0", "e31", "全部开始", 2, null, PageIdConstant.downloadingPage, null, null, null, null, null);
                return;
            default:
                return;
        }
    }

    public void onSelectAll() {
        if (this.mDownloadAdapter != null) {
            this.mDownloadAdapter.selectAllOrNot(true);
        }
    }

    public boolean onIsAdapterEmpty() {
        boolean isHas = DownloadManager.isHasDownloadingData();
        LogInfo.log(TAG, "isAdapterEmpty isHas : " + isHas);
        return !isHas;
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
        showLoadingDialog();
        if (DownloadManager.isServiceConnection()) {
            asynBatchDelete();
            return;
        }
        LogInfo.log(TAG, "onDoBatchDelete DownloadManager startDownloadService ");
        DownloadManager.startDownloadService(new 4(this));
    }

    private void asynBatchDelete() {
        new 5(this).execute(new Void[0]);
    }

    public void onResume() {
        super.onResume();
        this.isVisibleToUser = true;
    }

    public void onPause() {
        super.onPause();
        this.isVisibleToUser = false;
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
