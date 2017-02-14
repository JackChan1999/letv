package com.letv.android.client.ui.download;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity.IBatchDel;
import com.letv.android.client.view.CustomLoadingDialog;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.TipUtils;
import com.letv.download.manager.DownloadManager;
import com.letv.download.util.L;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;

public class MyDownloadFinishFragment extends BaseDownloadFragment implements IBatchDel {
    private static final String TAG = MyDownloadFinishFragment.class.getSimpleName();
    private static boolean isUpdate = true;
    private MyDownloadActivity mActivity;
    private CustomLoadingDialog mDialog;
    private DownloadFinishAdapter mDownloadFinishAdapter;
    Handler mHandler;
    private View mHeaderLayout;
    private ListView mListView;
    private LinearLayout mNullTip;
    private View mView;

    public MyDownloadFinishFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mHandler = new Handler();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.v(TAG, "MyDownloadFinishFragment onCreateView");
        this.mActivity = (MyDownloadActivity) getActivity();
        this.mView = this.mActivity.getLayoutInflater().inflate(R.layout.fragment_my_download_template, null);
        initView();
        this.mDialog = new CustomLoadingDialog(this.mActivity);
        this.mDialog.setCanceledOnTouchOutside(false);
        super.onCreateView(inflater, container, savedInstanceState);
        return this.mView;
    }

    private void initView() {
        this.mListView = (ListView) this.mView.findViewById(2131361938);
        this.mDownloadFinishAdapter = new DownloadFinishAdapter(this, this.mContext, null);
        this.mListView.setAdapter(this.mDownloadFinishAdapter);
        this.mNullTip = (LinearLayout) this.mView.findViewById(2131362662);
        this.mHeaderLayout = this.mView.findViewById(2131362660);
        ((TextView) this.mView.findViewById(2131362663)).setText(TipUtils.getTipMessage("700000", 2131100977));
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        L.v(TAG, "setUserVisibleHint isVisibleToUser : " + isVisibleToUser + " getActivity : " + getActivity());
    }

    public int getFragmentID() {
        return 0;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        L.v(TAG, " MyDownloadFinishFragment onLoadFinished loader.getId " + loader.getId());
        if (loader.getId() == 0) {
            if (this.mListView.getVisibility() == 8) {
                this.mListView.setVisibility(0);
            }
            if (this.mDownloadFinishAdapter != null) {
                this.mDownloadFinishAdapter.changeCursor(data);
            }
            checkAdapterEmpty(data);
            if (this.mActivity != null) {
                this.mActivity.updateStoreSpace();
            }
            this.mHandler.postDelayed(new 1(this), DanmakuFactory.MIN_DANMAKU_DURATION);
        }
    }

    private void checkAdapterEmpty(Cursor cursor) {
        if (this.mDownloadFinishAdapter != null) {
            if (cursor == null || cursor.getCount() == 0) {
                this.mNullTip.setVisibility(0);
                this.mHeaderLayout.setVisibility(8);
                return;
            }
            LogInfo.log(TAG, "checkAdapterEmpty cursor getcount != 0");
            this.mNullTip.setVisibility(8);
        }
    }

    public void onShowEditState() {
    }

    public void onCancelEditState() {
    }

    public void onDoBatchDelete() {
        new 2(this).execute(new Void[0]);
    }

    public void onSelectAll() {
        if (this.mDownloadFinishAdapter != null) {
            this.mDownloadFinishAdapter.selectAllOrNot(true);
        }
    }

    public boolean onIsAdapterEmpty() {
        boolean isHas = DownloadManager.isHasDownloadedData();
        LogInfo.log(TAG, "isAdapterEmpty isHas : " + isHas);
        return !isHas;
    }

    public void onClearSelectAll() {
        if (this.mDownloadFinishAdapter != null) {
            this.mDownloadFinishAdapter.selectAllOrNot(false);
        }
    }

    public int onSelectNum() {
        return this.mDownloadFinishAdapter.getBatchDelNum();
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
