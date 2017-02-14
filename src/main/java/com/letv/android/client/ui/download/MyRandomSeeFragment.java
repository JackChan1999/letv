package com.letv.android.client.ui.download;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity.IBatchDel;
import com.letv.android.client.utils.UIs;
import com.letv.android.client.view.CustomLoadingDialog;
import com.letv.android.client.worldcup.LetvAlarmService;
import com.letv.core.bean.DownloadDBListBean;
import com.letv.core.bean.DownloadDBListBean.DownloadDBBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.PlayConstant.LiveType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LogInfo;
import com.letv.download.manager.StoreManager;
import com.letv.download.util.L;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MyRandomSeeFragment extends Fragment implements MyRandomSeeDoc$onDownloadDelCallBack, IBatchDel {
    private static final String TAG = MyRandomSeeFragment.class.getSimpleName();
    protected BroadcastReceiver downloadReceiver;
    private boolean isDelete;
    private boolean isOpen;
    private boolean isViewCreated;
    private boolean isVisibleToUser;
    private MyDownloadActivity mActivity;
    private RandomSeeDownloadAdapter mAdapter;
    private CustomLoadingDialog mDialog;
    private DownloadDBListBean mDownloadList;
    private Button mFootCloseBtn;
    private View mFooterLayout;
    private ImageView mImageVWorldCupBg;
    private ListView mListView;
    private MyRandomSeeDoc mMyRandomSeeDoc;
    private ScrollView mNullScrollView;
    private LinearLayout mNullTip;
    MyRandomSeeDoc$onDownloadDelCallBack mOnDownloadDelCallBack;
    private Button mOpenWorldCupBtn;
    private View mOperationBarView;
    private Button mPauseAllBtn;
    private Button mStartAllBtn;
    private TextView mTextOpenSubTip;
    private TextView mTextOpenTip;
    private ViewPager mViewPager;
    private OnClickListener onClick;
    private View root;
    private RelativeLayout rootWatchAlertLayout;

    public MyRandomSeeFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.isViewCreated = false;
        this.isVisibleToUser = false;
        this.isOpen = false;
        this.mDownloadList = null;
        this.downloadReceiver = new 1(this);
        this.onClick = new 2(this);
        this.mOnDownloadDelCallBack = new 3(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.v(TAG, " MyRandomSeeFragment onCreateView");
        this.mActivity = (MyDownloadActivity) getActivity();
        this.root = getActivity().getLayoutInflater().inflate(R.layout.fragment_my_download_worldcup, null);
        this.mMyRandomSeeDoc = MyRandomSeeDoc.getInstance(LetvApplication.getInstance());
        findView();
        initDownloadData();
        if (getUserVisibleHint()) {
            checkAdapterEmpty();
        }
        return this.root;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            checkAdapterEmpty();
        }
    }

    public void onResume() {
        super.onResume();
        this.isVisibleToUser = true;
    }

    public void onPause() {
        super.onPause();
        this.isVisibleToUser = false;
    }

    private void findView() {
        this.mOperationBarView = this.root.findViewById(2131362660);
        this.mFooterLayout = UIs.inflate(this.mActivity, R.layout.fragment_my_download_worldcup_bottom, null);
        this.mFootCloseBtn = (Button) this.mFooterLayout.findViewById(R.id.btn_close_worldcup);
        this.mNullScrollView = (ScrollView) this.root.findViewById(R.id.scrollview_tip);
        this.mNullTip = (LinearLayout) this.root.findViewById(R.id.linearlayout_tip_download);
        this.mTextOpenTip = (TextView) this.root.findViewById(R.id.textv_open_worldcup_tip);
        ((MarginLayoutParams) this.mTextOpenTip.getLayoutParams()).topMargin = UIs.zoomWidth(LiveType.PLAY_LIVE_OTHER);
        this.mTextOpenSubTip = (TextView) this.root.findViewById(R.id.textv_open_worldcup_tip_line2);
        ((MarginLayoutParams) this.mTextOpenSubTip.getLayoutParams()).topMargin = UIs.zoomWidth(9);
        this.mListView = (ListView) this.root.findViewById(2131361938);
        this.mStartAllBtn = (Button) this.mOperationBarView.findViewById(2131362659);
        this.mPauseAllBtn = (Button) this.mOperationBarView.findViewById(2131362658);
        this.mOpenWorldCupBtn = (Button) this.root.findViewById(R.id.btn_open_close);
        this.mStartAllBtn.setOnClickListener(this.onClick);
        this.mPauseAllBtn.setOnClickListener(this.onClick);
        this.mOpenWorldCupBtn.setOnClickListener(this.onClick);
        this.mFootCloseBtn.setOnClickListener(this.onClick);
        this.mDialog = new CustomLoadingDialog(this.mActivity);
        this.mDialog.setCanceledOnTouchOutside(false);
        this.rootWatchAlertLayout = (RelativeLayout) this.root.findViewById(R.id.road_watch_ball_alert_layout);
        ((TextView) this.rootWatchAlertLayout.findViewById(R.id.road_watch_ball_alert)).setText(LetvTools.getTextFromServer(DialogMsgConstantId.CONSTANT_70013, getActivity().getResources().getString(2131100080)));
        this.rootWatchAlertLayout.setOnClickListener(this.onClick);
        setText();
    }

    public void update(long episodeId, int progress, int status) {
        if (this.isVisibleToUser) {
            int pos = -1;
            for (int i = 0; i < this.mDownloadList.size(); i++) {
                if (((long) ((DownloadDBBean) this.mDownloadList.get(i)).vid) == episodeId) {
                    pos = i;
                    break;
                }
            }
            if (pos >= 0) {
                int validPos = pos - this.mListView.getFirstVisiblePosition();
                if (validPos < 0 || this.mDownloadList == null) {
                    LogInfo.log("ljnalex", "view不在可见位置======");
                    DownloadDBBean mAdapterItem = this.mAdapter.getItem(pos);
                    DownloadDBBean downloadDBBean = this.mMyRandomSeeDoc.getDownloadByEpisodeId(episodeId);
                    if (!(mAdapterItem == null || downloadDBBean == null)) {
                        mAdapterItem.finish = downloadDBBean.finish;
                        mAdapterItem.totalsize = downloadDBBean.totalsize;
                        mAdapterItem.length = downloadDBBean.length;
                    }
                } else {
                    LogInfo.log("ljnalex", "view在可见位置======update:" + validPos);
                    DownloadDBBean bean = this.mMyRandomSeeDoc.getDownloadByEpisodeId(episodeId);
                    if (bean != null) {
                        ((DownloadDBBean) this.mDownloadList.get(pos)).finish = bean.finish;
                        View view = this.mListView.getChildAt(validPos);
                        if (view != null) {
                            this.mAdapter.updateProgress(view, progress, bean.length, bean.totalsize, pos);
                            this.mAdapter.updateState(view, bean.finish, bean.totalsize, pos);
                        }
                    } else {
                        return;
                    }
                }
                if (progress == 100) {
                    checkAdapterEmpty();
                    LogInfo.log("king", "wordCupFragment updateSdcardSpace");
                    this.mActivity.updateStoreSpace();
                }
            }
            checkPauseState();
        }
    }

    protected void showDeleteLayout() {
        if (!checkDownloadAllFinish() || this.isDelete) {
            this.mOperationBarView.setVisibility(0);
        } else {
            this.mOperationBarView.setVisibility(8);
        }
    }

    private void initDownloadData() {
        if (this.mAdapter == null) {
            this.mAdapter = new RandomSeeDownloadAdapter(this.mActivity);
        }
        this.mDownloadList = this.mMyRandomSeeDoc.getAllDownload();
        this.mAdapter.setList(this.mDownloadList);
        this.mListView.addFooterView(this.mFooterLayout);
        this.mListView.setAdapter(this.mAdapter);
        showDeleteLayout();
        checkPauseState();
        registerMyRandomSeeReceiver();
    }

    private void registerMyRandomSeeReceiver() {
        try {
            if (this.mActivity != null) {
                this.mActivity.registerReceiver(this.downloadReceiver, new IntentFilter("com.letv.android.client.worldcup.download.action_update"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unRegisterMyRandomSeeReceiver() {
        try {
            if (this.mActivity != null) {
                this.mActivity.unregisterReceiver(this.downloadReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        LogInfo.log(TAG, " MyRandomSeeFragment onDestroy >>> ");
        unRegisterMyRandomSeeReceiver();
    }

    private void setStatus(int status) {
        if (this.mAdapter != null) {
            for (int i = 0; i < this.mAdapter.getCount(); i++) {
                DownloadDBBean bean = this.mAdapter.getItem(i);
                if (!(bean == null || bean.finish == 4)) {
                    bean.finish = status;
                }
            }
        }
    }

    private void setButtonStatus(Button button, boolean canClick) {
        LogInfo.log(TAG, " setButtonStatus canClick : " + canClick);
        if (canClick) {
            button.setClickable(true);
            button.setBackgroundResource(2130837706);
            button.setTextAppearance(this.mActivity, 2131230855);
            return;
        }
        button.setClickable(false);
        button.setBackgroundResource(2130837728);
        button.setTextColor(getResources().getColor(2131493280));
    }

    private void setText() {
        this.isOpen = PreferencesManager.getInstance().getWorldCupFunc();
        LogInfo.log("ljnalex", "worldcupfragment setText isOpen = " + this.isOpen);
        if (this.isOpen) {
            String title = LetvTools.getTextFromServer(DialogMsgConstantId.CONSTANT_70010, getResources().getString(2131100079));
            String subTitle = LetvTools.getTextFromServer(DialogMsgConstantId.CONSTANT_70015, getResources().getString(2131100078));
            this.mTextOpenTip.setText(title.replace("#", "\n"));
            this.mTextOpenTip.setVisibility(0);
            this.mTextOpenSubTip.setText(subTitle.replace("#", "\n"));
            this.mTextOpenSubTip.setVisibility(0);
            this.mOpenWorldCupBtn.setText(getResources().getString(2131100077));
            return;
        }
        title = LetvTools.getTextFromServer(DialogMsgConstantId.CONSTANT_70008, getResources().getString(2131100083));
        subTitle = LetvTools.getTextFromServer(DialogMsgConstantId.CONSTANT_70009, getResources().getString(2131100082));
        this.mTextOpenTip.setText(title.replace("#", "\n"));
        this.mTextOpenSubTip.setText(subTitle.replace("#", "\n"));
        this.mTextOpenTip.setVisibility(0);
        this.mTextOpenSubTip.setVisibility(0);
        this.mOpenWorldCupBtn.setText(getResources().getString(2131100081));
    }

    private void showLoadingDialog() {
        if (this.mDialog.isShowing()) {
            this.mDialog.cancel();
        } else {
            this.mDialog.show();
        }
    }

    public void onDownloadDelPre() {
        showLoadingDialog();
    }

    public boolean checkDownloadAllFinish() {
        return this.mDownloadList != null && this.mDownloadList.size() == this.mMyRandomSeeDoc.getFinishDownloadSize();
    }

    public void resumeInit() {
    }

    public void checkAdapterEmpty() {
        if (this.mAdapter != null) {
            if (this.mAdapter.isEmpty()) {
                this.mNullTip.setVisibility(0);
                this.mNullScrollView.setVisibility(0);
                this.mOpenWorldCupBtn.setVisibility(0);
                this.mListView.setVisibility(8);
                this.mOperationBarView.setVisibility(8);
                this.mFooterLayout.setVisibility(8);
                resumeInit();
                return;
            }
            if (checkDownloadAllFinish()) {
                this.mOperationBarView.setVisibility(8);
            } else {
                this.mOperationBarView.setVisibility(0);
            }
            this.mFooterLayout.setVisibility(0);
            this.mNullTip.setVisibility(8);
            this.mNullScrollView.setVisibility(8);
            this.mOpenWorldCupBtn.setVisibility(8);
            this.mListView.setVisibility(0);
        }
    }

    public void onDownloadDelComplete() {
        this.mDownloadList.clear();
        this.mAdapter.clearItem();
        checkAdapterEmpty();
        this.mActivity.updateStoreSpace();
        showLoadingDialog();
        showSpaceAlertView(false);
        LetvAlarmService.stopPollingService(getActivity());
    }

    private void checkPauseState() {
        setButtonStatus(this.mPauseAllBtn, !this.mMyRandomSeeDoc.isAllDownloadPaused());
        boolean hasPause = this.mMyRandomSeeDoc.hasDownloadPauseData();
        if (hasPause) {
            setButtonStatus(this.mStartAllBtn, hasPause);
        } else {
            setButtonStatus(this.mStartAllBtn, this.mMyRandomSeeDoc.hasDownloadErrorData());
        }
    }

    private void updateDownloadData() {
        if (this.mDownloadList != null && this.mDownloadList.size() != this.mMyRandomSeeDoc.getAllDownload().size()) {
            this.mDownloadList = this.mMyRandomSeeDoc.getAllDownload();
            this.mAdapter.setList(this.mDownloadList);
            checkAdapterEmpty();
            checkPauseState();
        }
    }

    private void showSpaceAlertView(boolean isShow) {
        if ((isShow && this.rootWatchAlertLayout.isShown()) || !StoreManager.isStoreMounted()) {
            return;
        }
        if (StoreManager.getDefaultDownloadDeviceInfo().mAvailable <= 0) {
            this.rootWatchAlertLayout.setVisibility(0);
        } else {
            this.rootWatchAlertLayout.setVisibility(8);
        }
    }

    public void onShowEditState() {
        this.mOperationBarView.setVisibility(8);
    }

    public void onCancelEditState() {
        checkAdapterEmpty();
        if (this.mAdapter.getCount() <= 0 || checkDownloadAllFinish()) {
            this.mOperationBarView.setVisibility(8);
        } else {
            this.mOperationBarView.setVisibility(0);
        }
    }

    public void onDoBatchDelete() {
        if (this.mAdapter != null) {
            this.mAdapter.delete(this.mOnDownloadDelCallBack);
        }
    }

    public void onSelectAll() {
        if (this.mAdapter != null) {
            this.mAdapter.selectAllOrNot(true);
        }
    }

    public boolean onIsAdapterEmpty() {
        return this.mAdapter.getCount() <= 0;
    }

    public void onClearSelectAll() {
        if (this.mAdapter != null) {
            this.mAdapter.selectAllOrNot(false);
        }
    }

    public int onSelectNum() {
        return this.mAdapter.getBatchDelNum();
    }
}
