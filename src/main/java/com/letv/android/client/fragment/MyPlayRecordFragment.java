package com.letv.android.client.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.MyPlayRecordActivity;
import com.letv.android.client.adapter.MyPlayRecordListAdapter;
import com.letv.android.client.adapter.MyPlayRecordListAdapter.IDeleteItemObserver;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.utils.FootViewUtil;
import com.letv.android.client.utils.LetvPlayRecordFunction.SubmitPlayTracesThread;
import com.letv.android.client.utils.UIs;
import com.letv.android.client.view.CustomLoadingDialog;
import com.letv.android.client.view.PullToRefreshBase.OnLastItemVisibleListener;
import com.letv.android.client.view.PullToRefreshBase.OnRefreshListener;
import com.letv.android.client.view.PullToRefreshListView;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.PlayRecordList;
import com.letv.core.bean.TipMapBean.TipBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.parser.PlayRecordParser;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.TipUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.lang.reflect.Field;
import java.util.Observable;
import java.util.Observer;

public class MyPlayRecordFragment extends MyBaseFragment implements Observer {
    private static final int UPDATEUI = 1001;
    private final int PAGE_SIZE;
    private final String TAG;
    private IDeleteItemObserver deleteObserver;
    private boolean isLoadMore;
    private boolean isRefernce;
    private boolean isSyncSuccess;
    private MyPlayRecordActivity mActivity;
    private RelativeLayout mBottomLogin;
    private Button mBtnDelete;
    private Button mBtnDeleteAll;
    private Context mContext;
    private String mDefaultTextNorecord;
    private PlayRecordList mDelRecordList;
    private CustomLoadingDialog mDialog;
    private FootViewUtil mFootViewUtil;
    private Handler mHandler;
    private View mHeaderLayout;
    private boolean mIsDelete;
    private boolean mIsLogin;
    private PlayRecordList mList;
    private MyPlayRecordListAdapter mListAdapter;
    private ListView mListView;
    private TextView mNullTextTip;
    private PullToRefreshListView mPullListView;
    private SubmitPlayTracesThread mSubmitPlayTrace;
    private PublicLoadLayout mViewRoot;
    private OnClickListener onClick;
    private OnLastItemVisibleListener onLastItemVisibleListener;
    private OnRefreshListener onRefreshListener;
    private int page;
    private int total;

    public MyPlayRecordFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mIsLogin = false;
        this.PAGE_SIZE = 20;
        this.page = 1;
        this.total = -1;
        this.mIsDelete = false;
        this.TAG = MyPlayRecordFragment.class.getName();
        this.mHandler = new 1(this);
        this.onClick = new 3(this);
        this.deleteObserver = new 4(this);
        this.onRefreshListener = new 6(this);
        this.onLastItemVisibleListener = new 9(this);
    }

    public void setObservable(Observable o) {
        o.addObserver(this);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        this.mIsLogin = PreferencesManager.getInstance().isLogin();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mActivity = (MyPlayRecordActivity) getActivity();
        this.mContext = getActivity();
        this.mViewRoot = PublicLoadLayout.createPage(getActivity(), (int) R.layout.fragment_my_record);
        this.mViewRoot.setBackgroundColor(getResources().getColor(2131493217));
        return this.mViewRoot;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void onStart() {
        super.onStart();
        this.mIsLogin = PreferencesManager.getInstance().isLogin();
        this.mViewRoot.loading(false);
        if (!this.isSyncSuccess && this.mIsLogin && NetworkUtils.isNetworkAvailable()) {
            this.mSubmitPlayTrace = new SubmitPlayTracesThread(BaseApplication.getInstance(), false);
            this.mSubmitPlayTrace.setSyncPlayTraces(new 2(this));
            this.mSubmitPlayTrace.start();
            return;
        }
        loadDBPlayTrace();
    }

    private void loadDBPlayTrace() {
        this.mList = DBManager.getInstance().getPlayTrace().getAllPlayTrace();
        this.mFootViewUtil.removeFootView();
        updateUI(0);
    }

    public void refreshLogin() {
        this.mIsLogin = PreferencesManager.getInstance().isLogin();
        upDateLogin();
    }

    public void setAllPick(boolean isAllPick) {
        this.mListAdapter.setAllPick(isAllPick);
    }

    public void onViewPageScrollChangeEvent() {
        if (this.mIsDelete) {
            this.mIsDelete = false;
            this.mListAdapter.setDeleteState(false);
        }
        showDeleteLayout();
    }

    private void findView() {
        this.mBottomLogin = (RelativeLayout) this.mViewRoot.findViewById(R.id.bottom_login);
        TipBean dialogLogin = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_100061);
        this.mDefaultTextNorecord = TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700044, 2131100987);
        if (dialogLogin != null) {
            ((TextView) this.mBottomLogin.findViewById(R.id.account_login)).setText(dialogLogin.message);
        }
        this.mPullListView = (PullToRefreshListView) this.mViewRoot.findViewById(R.id.listv_record);
        this.mPullListView.setParams(Boolean.valueOf(true), getClass().getSimpleName());
        this.mPullListView.setOnRefreshListener(this.onRefreshListener);
        this.mListView = (ListView) this.mPullListView.getRefreshableView();
        this.mNullTextTip = (TextView) this.mViewRoot.findViewById(R.id.textv_tip_record);
        if (!TextUtils.isEmpty(this.mDefaultTextNorecord)) {
            this.mNullTextTip.setText(this.mDefaultTextNorecord);
        }
        this.mHeaderLayout = this.mViewRoot.findViewById(R.id.bottom_delete_all_layout);
        this.mBtnDeleteAll = (Button) this.mHeaderLayout.findViewById(2131362349);
        this.mBtnDelete = (Button) this.mHeaderLayout.findViewById(2131362350);
        this.mFootViewUtil = new FootViewUtil(this.mPullListView);
        this.mPullListView.setOnLastItemVisibleListener(this.onLastItemVisibleListener);
        this.mBottomLogin.setOnClickListener(this.onClick);
        this.mHeaderLayout.setOnClickListener(this.onClick);
        this.mBtnDelete.setOnClickListener(this.onClick);
        this.mBtnDeleteAll.setOnClickListener(this.onClick);
        this.mListView.setOnItemClickListener(new ItemClickEvent(this, null));
        this.mDialog = new CustomLoadingDialog(this.mContext);
        this.mDialog.setCanceledOnTouchOutside(false);
    }

    public void checkAdapterEmpty() {
        if (this.mListAdapter != null) {
            if (this.mListAdapter.isEmpty()) {
                this.mNullTextTip.setVisibility(0);
                this.mListView.setVisibility(8);
                this.mHeaderLayout.setVisibility(8);
                this.mActivity.showDelBtn(false);
                return;
            }
            this.mNullTextTip.setVisibility(8);
            this.mListView.setVisibility(0);
            this.mActivity.showDelBtn(true);
        }
    }

    private void delRecord() {
        showLoadingDialog();
        this.mHandler.post(new 5(this));
    }

    public void requestPlayRecord(Context context) {
        PlayRecordApi instance = PlayRecordApi.getInstance();
        String uid = LetvUtils.getUID();
        String valueOf = this.isLoadMore ? String.valueOf(this.page) : "1";
        String valueOf2 = (this.isLoadMore && this.page == 3) ? String.valueOf(10) : String.valueOf(20);
        String url = instance.getPlayTraces(0, uid, valueOf, valueOf2, PreferencesManager.getInstance().getSso_tk());
        Volley.getQueue().cancelWithTag(this.TAG + "play_record");
        new LetvRequest().setUrl(url).setRequestType(RequestManner.NETWORK_THEN_CACHE).setParser(new PlayRecordParser()).setNeedCheckToken(true).setTag(this.TAG + "play_record").setCache(new 8(this)).setCallback(new 7(this, context)).add();
    }

    public void updateUI(int i) {
        this.mViewRoot.finish();
        if (this.mListView != null) {
            this.mPullListView.onRefreshComplete();
        }
        if (this.mListAdapter == null) {
            this.mListAdapter = new MyPlayRecordListAdapter(this.mContext);
            this.mListView.setAdapter(this.mListAdapter);
        }
        this.mListAdapter.setList(this.mList);
        this.mListAdapter.notifyDataSetChanged();
        this.mListAdapter.setObserver(this.deleteObserver);
        checkAdapterEmpty();
        if (this.mList == null || this.mList.size() <= 0) {
            this.mActivity.showDelBtn(false);
        } else {
            this.mActivity.showDelBtn(true);
        }
        if (!this.mIsLogin || this.mListAdapter.getCount() <= 10) {
            this.mFootViewUtil.removeFootView();
        } else {
            this.mFootViewUtil.showFootView();
        }
        showDeleteLayout();
        upDateLogin();
    }

    private void upDateLogin() {
        LayoutParams params = (LayoutParams) this.mPullListView.getLayoutParams();
        if (this.mIsLogin) {
            this.mBottomLogin.setVisibility(8);
            params.bottomMargin = 0;
        } else {
            this.mBottomLogin.setVisibility(0);
            params.bottomMargin = this.mBottomLogin.getHeight();
        }
        this.mPullListView.setLayoutParams(params);
    }

    private void showLoadingDialog() {
        if (this.mDialog != null) {
            if (this.mDialog.isShowing()) {
                this.mDialog.cancel();
            } else {
                this.mDialog.show();
            }
        }
    }

    protected void showDeleteLayout() {
        if (getActivity() != null && this.mHeaderLayout != null) {
            LayoutParams params = (LayoutParams) this.mPullListView.getLayoutParams();
            if (!this.mIsDelete || this.mListAdapter == null || this.mListAdapter.getCount() <= 0) {
                params.bottomMargin = 0;
                this.mHeaderLayout.setVisibility(8);
                upDateLogin();
                return;
            }
            this.mHeaderLayout.setVisibility(0);
            if (this.mHeaderLayout.getHeight() == 0) {
                params.bottomMargin = ((UIs.zoomRealHeight(50) * 3) / 4) + 1;
            } else {
                params.bottomMargin = this.mHeaderLayout.getHeight();
            }
            this.mPullListView.setLayoutParams(params);
        }
    }

    public BaseAdapter getAdapter() {
        return this.mListAdapter;
    }

    public void update(Observable observable, Object data) {
        Bundle bundle = (Bundle) data;
        if (bundle.getInt("state", 0) == 101) {
            this.mIsDelete = bundle.getBoolean("mIsDelete", false);
            this.mListAdapter.setDeleteState(this.mIsDelete);
            this.mListAdapter.clearDeletes();
            this.mBtnDeleteAll.setText(2131099791);
            this.mPullListView.setPullToRefreshEnabled(!this.mIsDelete);
            updateUI(false);
            this.mListAdapter.notifyDataSetChanged();
            if (this.mDelRecordList != null) {
                this.mDelRecordList.clear();
            }
            showDeleteLayout();
        }
    }

    private void updateUI(boolean isClickable) {
        if (isClickable) {
            if (this.mDelRecordList.size() == this.mList.size()) {
                this.mBtnDeleteAll.setText(2131099785);
            } else {
                this.mBtnDeleteAll.setText(2131099791);
            }
            this.mBtnDelete.setEnabled(true);
            this.mBtnDelete.setText(getResources().getString(2131099787) + "(" + this.mDelRecordList.size() + ")");
            return;
        }
        this.mBtnDelete.setEnabled(false);
        this.mBtnDelete.setText(2131099787);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mSubmitPlayTrace != null) {
            this.mSubmitPlayTrace.cancleAllQuest();
            this.mSubmitPlayTrace.setSyncPlayTraces(null);
            this.mSubmitPlayTrace = null;
        }
        this.mHandler.removeMessages(1001);
        Volley.getQueue().cancelWithTag(this.TAG + "play_record");
        if (this.mPullListView != null) {
            this.mPullListView.removeAllViews();
            this.mPullListView = null;
        }
        if (this.mViewRoot != null) {
            this.mViewRoot.removeAllViews();
            this.mViewRoot = null;
        }
        if (this.mList != null) {
            this.mList.clear();
            this.mList = null;
        }
        if (this.mDelRecordList != null) {
            this.mDelRecordList.clear();
            this.mDelRecordList = null;
        }
    }

    public String getTagName() {
        return "Emerson";
    }

    public int getContainerId() {
        return 0;
    }
}
