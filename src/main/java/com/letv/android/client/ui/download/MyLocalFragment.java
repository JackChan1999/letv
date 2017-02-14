package com.letv.android.client.ui.download;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.DownloadLocalListAdapter;
import com.letv.android.client.commonlib.activity.WrapActivity.IBatchDel;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.service.ScanningMediaService;
import com.letv.core.BaseApplication;
import com.letv.core.bean.DownloadLocalVideoItemBean;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.db.DBManager;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.download.util.L;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MyLocalFragment extends LetvBaseFragment implements Observer, OnClickListener, IBatchDel {
    private static long lastClickTime;
    private final String TAG;
    Handler handler;
    private DownloadLocalListAdapter mAdapter;
    private Context mContext;
    private View mDownloadRootLayout;
    private ListView mListView;
    private boolean mLoadingData;
    private List<DownloadLocalVideoItemBean> mLocalVideoList;
    private int mLocalVideoNumber;
    private MyDownloadActivity mMyDownloadActivity;
    private RelativeLayout mScanLayout;
    private TextView mScanTextView;
    private LinearLayout mScaningLayout;
    private TextView mScaningTextView;
    private boolean mScanning;
    public Object obj;

    public MyLocalFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.TAG = FragmentConstant.TAG_FRAGMENT_DOWNLOAD_LOCAL;
        this.mLoadingData = true;
        this.obj = new Object();
        this.mLocalVideoList = null;
        this.mLocalVideoNumber = 0;
        this.mScanning = false;
        this.handler = new 1(this);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mDownloadRootLayout = PublicLoadLayout.inflate(getActivity(), R.layout.fragment_my_local, null);
        this.mContext = getActivity().getApplicationContext();
        this.mMyDownloadActivity = (MyDownloadActivity) getActivity();
        initUI();
        L.v(FragmentConstant.TAG_FRAGMENT_DOWNLOAD_LOCAL, " MyLocalFragment onCreateView");
        return this.mDownloadRootLayout;
    }

    private void initUI() {
        this.mLocalVideoList = DBManager.getInstance().getLocalVideoTrace().getLocalVideoList();
        this.mLocalVideoNumber = this.mLocalVideoList.size();
        this.mScanLayout = (RelativeLayout) this.mDownloadRootLayout.findViewById(R.id.layout_top);
        this.mScaningLayout = (LinearLayout) this.mDownloadRootLayout.findViewById(R.id.layout_scaning);
        this.mScanTextView = (TextView) this.mDownloadRootLayout.findViewById(R.id.textv_scan);
        this.mScaningTextView = (TextView) this.mDownloadRootLayout.findViewById(R.id.textv_scaning);
        this.mListView = (ListView) this.mDownloadRootLayout.findViewById(R.id.listv_local);
        this.mScanTextView.setVisibility(0);
        this.mScaningLayout.setVisibility(8);
        this.mScanLayout.setOnClickListener(this);
        if (this.mAdapter == null) {
            this.mAdapter = new DownloadLocalListAdapter(BaseApplication.getInstance().getApplicationContext());
        }
        this.mAdapter.setList(this.mLocalVideoList);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setRecyclerListener(this.mAdapter);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        L.v(FragmentConstant.TAG_FRAGMENT_DOWNLOAD_LOCAL, "setUserVisibleHint isVisibleToUser : " + isVisibleToUser);
        if (isVisibleToUser) {
            checkAdapterEmpty();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        stopScan();
        this.mAdapter = null;
        this.mLocalVideoList = null;
        this.handler = null;
    }

    private void stopScan() {
        this.mScanning = false;
        ScanningMediaService.stopScanning(getActivity());
        checkAdapterEmpty();
    }

    private void startScan() {
        ScanningMediaService.startScanning(getActivity(), this.handler);
    }

    public void deleteLocalVideo() {
        new 2(this).execute(new Void[0]);
    }

    public static synchronized boolean isFastClick() {
        boolean z;
        synchronized (MyLocalFragment.class) {
            long time = System.currentTimeMillis();
            long timeD = time - lastClickTime;
            if (0 >= timeD || timeD >= 800) {
                lastClickTime = time;
                z = false;
            } else {
                z = true;
            }
        }
        return z;
    }

    public void onClick(View v) {
        if (!isFastClick()) {
            switch (v.getId()) {
                case R.id.layout_top /*2131362684*/:
                    if (this.mScanning) {
                        this.mScanning = false;
                        stopScan();
                        this.mScaningLayout.setVisibility(8);
                        this.mScanTextView.setVisibility(0);
                    } else {
                        this.mScanning = true;
                        startScan();
                        this.mScanTextView.setVisibility(8);
                        this.mScaningLayout.setVisibility(0);
                        this.mScaningTextView.setText(getActivity().getResources().getString(2131100942));
                    }
                    StatisticsUtils.staticticsInfoPost(getActivity(), "0", "e34", "扫描本地视频", 1, null, PageIdConstant.localPage, null, null, null, null, null);
                    return;
                default:
                    return;
            }
        }
    }

    public void update(Observable observable, Object data) {
        if (((Bundle) data).getInt("state", 0) == 3) {
            deleteLocalVideo();
        }
    }

    public void checkAdapterEmpty() {
        if (this.mAdapter != null) {
            this.mMyDownloadActivity.initBatchDelState(2);
        }
    }

    public String getTagName() {
        return FragmentConstant.TAG_FRAGMENT_DOWNLOAD_LOCAL;
    }

    public int getContainerId() {
        return 0;
    }

    public void onShowEditState() {
    }

    public void onCancelEditState() {
    }

    public void onDoBatchDelete() {
    }

    public void onSelectAll() {
    }

    public boolean onIsAdapterEmpty() {
        return this.mLocalVideoList == null || this.mLocalVideoList.size() <= 0;
    }

    public void onClearSelectAll() {
    }

    public int onSelectNum() {
        return 0;
    }
}
