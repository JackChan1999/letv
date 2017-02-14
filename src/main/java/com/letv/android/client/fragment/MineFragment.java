package com.letv.android.client.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.activity.LetvLoginActivity;
import com.letv.android.client.activity.LetvVipActivity;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.activity.PersonalInfoActivity;
import com.letv.android.client.adapter.MineListViewAdapter;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.mymessage.MyMessageActivity;
import com.letv.android.client.task.RequestUserByTokenTask;
import com.letv.android.client.view.RoundImageView;
import com.letv.android.wo.ex.WoInterface.LetvWoFlowListener;
import com.letv.business.flow.unicom.UnicomWoFlowManager;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.MyProfileListBean;
import com.letv.core.bean.MyProfileListBean.MyProfileBean;
import com.letv.core.bean.UserBean;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.LoginConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.LetvMobileParser;
import com.letv.core.parser.MyProfileListParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.Collections;
import java.util.Comparator;
import org.json.JSONObject;

public class MineFragment extends LetvBaseFragment implements OnClickListener {
    private static final int NOTIFY_SET_CHANGED = 2;
    private final String REQUEST_MY_LIST_TASK;
    private final String TAG;
    private String mAdCmdId;
    private BroadcastReceiver mBroadcastReceiver;
    private boolean mHasRequest;
    private View mHeadLayout;
    private View mHeadLoginLayout;
    private boolean mIsNewMessageVisible;
    private RelativeLayout mMessageIcon;
    private ListView mMineListView;
    private MineListViewAdapter mMineListViewAdapter;
    private PublicLoadLayout mMineRootLayout;
    private int mMyPointsAndUserInfoFlag;
    private ImageView mNewMessageIcon;
    private RefreshData mRefreshData;
    private boolean mRegisterFlag;
    private RoundImageView mRoundHead;
    private TextView mSubTitle;
    private TextView mSubTitleOther;
    private TextView mTitle;
    private UserBean mUser;

    public class ComparatorMyProfile implements Comparator<MyProfileBean> {
        final /* synthetic */ MineFragment this$0;

        public ComparatorMyProfile(MineFragment this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
        }

        public int compare(MyProfileBean myProfile0, MyProfileBean myProfile1) {
            return myProfile0.sort.compareTo(myProfile1.sort);
        }
    }

    public MineFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.TAG = FragmentConstant.TAG_FRAGMENT_MINE;
        this.mAdCmdId = LoginConstant.MINE_AD_CMS_ID_ONLINE;
        this.mUser = null;
        this.mMyPointsAndUserInfoFlag = 0;
        this.mIsNewMessageVisible = false;
        this.REQUEST_MY_LIST_TASK = "request_my_list_task";
        this.mRefreshData = new RefreshData(this) {
            final /* synthetic */ MineFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.mineListRequestTask(RequestManner.CACHE_THEN_NETROWK);
            }
        };
        this.mBroadcastReceiver = new BroadcastReceiver(this) {
            final /* synthetic */ MineFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onReceive(Context context, Intent intent) {
                if (!this.this$0.mRegisterFlag) {
                    this.this$0.mRegisterFlag = true;
                } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction()) && NetworkUtils.isNetworkConnected(context)) {
                    this.this$0.requestSupportProvince();
                    this.this$0.isLogin();
                    this.this$0.mineListRequestTask(RequestManner.CACHE_THEN_NETROWK);
                }
            }
        };
    }

    public void onCreate(Bundle savedInstanceState) {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            this.mAdCmdId = LoginConstant.MINE_AD_CMS_ID_TEST;
        } else {
            this.mAdCmdId = LoginConstant.MINE_AD_CMS_ID_ONLINE;
        }
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mMineRootLayout = PublicLoadLayout.createPage(getActivity(), (int) R.layout.mine_listview_fragment);
        initUI();
        return this.mMineRootLayout;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mMineRootLayout.setRefreshData(this.mRefreshData);
    }

    private void initUI() {
        int i;
        this.mMineListView = (ListView) this.mMineRootLayout.findViewById(R.id.my_list_view);
        this.mHeadLayout = PublicLoadLayout.inflate(getActivity(), R.layout.mine_head_layout, null);
        this.mRoundHead = (RoundImageView) this.mHeadLayout.findViewById(R.id.btn_head_login);
        this.mTitle = (TextView) this.mHeadLayout.findViewById(R.id.head_login_title);
        this.mSubTitle = (TextView) this.mHeadLayout.findViewById(R.id.head_login_subtitle);
        this.mSubTitleOther = (TextView) this.mHeadLayout.findViewById(R.id.head_login_subtitle_other);
        this.mHeadLoginLayout = this.mHeadLayout.findViewById(R.id.layout_head_login);
        this.mMessageIcon = (RelativeLayout) this.mHeadLayout.findViewById(R.id.message_icon);
        this.mNewMessageIcon = (ImageView) this.mHeadLayout.findViewById(R.id.new_message_title);
        ImageView imageView = this.mNewMessageIcon;
        if (this.mIsNewMessageVisible) {
            i = 0;
        } else {
            i = 8;
        }
        imageView.setVisibility(i);
        this.mHeadLayout.findViewById(R.id.title_main_search).setVisibility(8);
        this.mMineListView.addHeaderView(this.mHeadLayout);
        View mFooterView = new View(getActivity());
        mFooterView.setLayoutParams(new LayoutParams(-1, UIsUtils.zoomWidth(15)));
        mFooterView.setBackgroundColor(getActivity().getResources().getColor(2131493362));
        this.mMineListView.addFooterView(mFooterView);
        this.mMineListViewAdapter = new MineListViewAdapter(getActivity());
        this.mMineListView.setAdapter(this.mMineListViewAdapter);
        this.mRoundHead.setOnClickListener(this);
        this.mHeadLoginLayout.setOnClickListener(this);
        this.mMessageIcon.setOnClickListener(this);
        showDefaultProfileList();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_head_login /*2131363867*/:
                onHeadIconClick(v, false);
                return;
            case R.id.btn_head_login /*2131363868*/:
                onHeadIconClick(v, true);
                return;
            case R.id.message_icon /*2131363873*/:
                refreshNewMessageVisible(false);
                this.mNewMessageIcon.setVisibility(8);
                ((MainActivity) getActivity()).setMineRedPointVisible(false);
                MyMessageActivity.launch(getActivity());
                return;
            default:
                return;
        }
    }

    private void onHeadIconClick(View view, boolean headFlag) {
        if (!NetworkUtils.isNetworkAvailable() && this.mUser == null && PreferencesManager.getInstance().isLogin()) {
            ToastUtils.showToast(this.mContext, 2131100493);
            return;
        }
        String name = "";
        if (!PreferencesManager.getInstance().isLogin() || this.mUser == null) {
            name = getActivity().getString(2131101045);
            LetvLoginActivity.launch(getActivity());
        } else {
            name = getActivity().getString(2131100344);
            PersonalInfoActivity.launch(this.mContext, this.mUser);
        }
        if (headFlag) {
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "d31", name, 1, null, "033", null, null, null, null, null);
            return;
        }
        LogInfo.LogStatistics(getActivity().getString(2131100379));
        StatisticsUtils.staticticsInfoPost(this.mContext, "0", "d31", getActivity().getString(2131100379), 2, null, "033", null, null, null, null, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onStart() {
        super.onStart();
        LogInfo.log(FragmentConstant.TAG_FRAGMENT_MINE, "onStart");
        mineListRequestTask(RequestManner.CACHE_THEN_NETROWK);
        requestSupportProvince();
    }

    public void onResume() {
        super.onResume();
        LogInfo.log(FragmentConstant.TAG_FRAGMENT_MINE, "onResume");
        isLogin();
        registerReceiver();
        this.mRegisterFlag = false;
    }

    public void onPause() {
        super.onPause();
    }

    public void onStop() {
        super.onStop();
        LogInfo.log(FragmentConstant.TAG_FRAGMENT_MINE, "onStop");
        try {
            getActivity().unregisterReceiver(this.mBroadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        LogInfo.log(FragmentConstant.TAG_FRAGMENT_MINE, "onDestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        LogInfo.log(FragmentConstant.TAG_FRAGMENT_MINE, "onDestroy");
        Volley.getQueue().cancelWithTag("request_my_list_task");
        Volley.getQueue().cancelWithTag(RequestUserByTokenTask.REQUEST_USER_TASK);
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && this.mUser == null) {
            isLogin();
        }
    }

    public void requestSupportProvince() {
        LogInfo.log("ZSM", "requestSupportProvince...1");
        UnicomWoFlowManager.getInstance().checkUnicomWoFreeFlow(this.mContext, new LetvWoFlowListener(this) {
            final /* synthetic */ MineFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                if (this.this$0.mMineListViewAdapter != null) {
                    this.this$0.mMineListViewAdapter.setWoEntity(isSupportProvince, isOrder);
                    MyProfileListBean tempMyProfileList = this.this$0.mMineListViewAdapter.getFullList();
                    if (tempMyProfileList != null && tempMyProfileList.list.size() > 0) {
                        this.this$0.mMineListViewAdapter.setList(tempMyProfileList.list);
                    }
                }
            }
        });
    }

    private void isLogin() {
        if (PreferencesManager.getInstance().isLogin()) {
            this.mMyPointsAndUserInfoFlag = 0;
            RequestUserByTokenTask.getUserByTokenTask(getActivity(), PreferencesManager.getInstance().getSso_tk(), new SimpleResponse<UserBean>(this) {
                final /* synthetic */ MineFragment this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onCacheResponse(VolleyRequest<UserBean> volleyRequest, UserBean result, DataHull hull, CacheResponseState state) {
                    if (state == CacheResponseState.SUCCESS) {
                        if (result != null) {
                            this.this$0.mUser = result;
                            this.this$0.mMineListViewAdapter.setUser(this.this$0.mUser);
                            this.this$0.mMineListViewAdapter.notifyDataSetChanged();
                            this.this$0.mMyPointsAndUserInfoFlag = this.this$0.mMyPointsAndUserInfoFlag + 1;
                            if (this.this$0.mMyPointsAndUserInfoFlag == 2) {
                                this.this$0.mMyPointsAndUserInfoFlag = 0;
                                this.this$0.mMineListViewAdapter.notifyDataSetChanged();
                            }
                        }
                        this.this$0.updateHeaderUI();
                    }
                }
            });
            return;
        }
        showNonLoginStatus();
    }

    private void updateHeaderUI() {
        if (!PreferencesManager.getInstance().isLogin() || this.mUser == null) {
            showNonLoginStatus();
            return;
        }
        String userName;
        if (TextUtils.isEmpty(this.mUser.nickname)) {
            userName = this.mUser.username;
        } else {
            userName = this.mUser.nickname;
        }
        LogInfo.log("ZSM mineFragment userName = " + userName);
        this.mTitle.setText(userName);
        this.mTitle.setPadding(0, 0, 0, 0);
        String icon = this.mUser.picture;
        String tag = (String) this.mRoundHead.getTag();
        if (!TextUtils.isEmpty(tag) && TextUtils.isEmpty(icon) && tag.equalsIgnoreCase(icon)) {
            this.mRoundHead.setImageDrawable(this.mContext.getResources().getDrawable(2130837633));
        } else {
            PreferencesManager.getInstance().setPicture(this.mUser.picture);
            ImageDownloader.getInstance().download(this.mRoundHead, icon);
            this.mRoundHead.setTag(icon);
        }
        if ("1".equals(this.mUser.isvip)) {
            boolean z;
            int vipType = this.mUser.vipInfo.vipType;
            this.mSubTitle.setTextAppearance(this.mContext, R.style.letv_text_13_ffec8e1f);
            this.mSubTitle.setText(vipType == 2 ? 2131100637 : 2131100641);
            Drawable drawable = this.mContext.getResources().getDrawable(2130838628);
            drawable.setBounds(0, 0, 13, 13);
            this.mSubTitle.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            this.mSubTitle.setBackgroundColor(0);
            this.mSubTitle.setCompoundDrawablePadding(4);
            if (vipType == 2) {
                z = true;
            } else {
                z = false;
            }
            setDiscount(z);
            return;
        }
        this.mSubTitleOther.setVisibility(8);
        this.mSubTitle.setTextAppearance(this.mContext, R.style.letv_text_11_ffffffff);
        this.mSubTitle.setText(2131100605);
        this.mSubTitle.setBackgroundResource(2130838912);
        this.mSubTitle.setCompoundDrawables(null, null, null, null);
        this.mSubTitle.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MineFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                this.this$0.rechargeOrBeMember(false, false);
            }
        });
    }

    public void registerReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            getActivity().registerReceiver(this.mBroadcastReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDiscount(final boolean isSeniorVip) {
        this.mSubTitleOther.setVisibility(0);
        String discount = PreferencesManager.getInstance().getContinueDiscount(PreferencesManager.getInstance().getUserId());
        long lastdays = PreferencesManager.getInstance().getLastdays();
        if (Math.abs(lastdays) > PreferencesManager.getInstance().getChkvipday() || Integer.parseInt(discount) == 0) {
            this.mSubTitleOther.setText(2131099695);
            this.mSubTitleOther.setBackgroundResource(2130838912);
            this.mSubTitleOther.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ MineFragment this$0;

                public void onClick(View v) {
                    this.this$0.rechargeOrBeMember(true, isSeniorVip);
                }
            });
            return;
        }
        discount = String.format(getString(2131100606), new Object[]{discount});
        this.mSubTitleOther.setBackgroundResource(2130838912);
        this.mSubTitleOther.setText(discount);
        this.mSubTitleOther.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MineFragment this$0;

            public void onClick(View v) {
                this.this$0.rechargeOrBeMember(true, isSeniorVip);
            }
        });
    }

    private void rechargeOrBeMember(boolean isVip, boolean isSeniorVip) {
        if (NetworkUtils.isNetworkAvailable()) {
            String title = getString(isVip ? 2131100646 : 2131100645);
            if (PreferencesManager.getInstance().isLogin()) {
                String logMsg = isVip ? getActivity().getString(2131099694) : getActivity().getString(2131100482);
                LogInfo.LogStatistics(logMsg);
                StatisticsUtils.staticticsInfoPost(this.mContext, "0", "d31", logMsg, 3, null, "033", null, null, null, null, null);
                LetvVipActivity.launch(getActivity(), title);
                return;
            }
            return;
        }
        ToastUtils.showToast(LetvApplication.getInstance(), 2131100493);
    }

    private void showNonLoginStatus() {
        this.mRoundHead.setImageResource(2130837633);
        this.mSubTitle.setText(2131100623);
        this.mSubTitle.setCompoundDrawables(null, null, null, null);
        this.mSubTitle.setBackgroundColor(0);
        this.mSubTitle.setTextAppearance(this.mContext, 2131230878);
        this.mSubTitleOther.setVisibility(8);
        this.mTitle.setText(2131099877);
        this.mTitle.setPadding(UIsUtils.dipToPx(4.0f), 0, 0, 0);
    }

    private void mineListRequestTask(RequestManner requestType) {
        this.mHasRequest = true;
        new LetvRequest(MyProfileListBean.class).setRequestType(requestType).setCache(new VolleyDiskCache("MineListRequestTask")).setParser(new LetvMobileParser()).setCallback(new SimpleResponse<MyProfileListBean>(this) {
            final /* synthetic */ MineFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<MyProfileListBean> volleyRequest, MyProfileListBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("ZSM", "mineListRequestTask state ==" + state);
                if (state == NetworkResponseState.SUCCESS && result != null && result.list.size() > 1 && this.this$0.mMineListViewAdapter != null) {
                    Collections.sort(result.list, new ComparatorMyProfile(this.this$0));
                    this.this$0.mMineListViewAdapter.setList(result.list);
                }
            }

            public void onCacheResponse(VolleyRequest<MyProfileListBean> request, MyProfileListBean result, DataHull hull, CacheResponseState state) {
                if (state == CacheResponseState.SUCCESS) {
                    if (result != null && result.list.size() > 1) {
                        Collections.sort(result.list, new ComparatorMyProfile(this.this$0));
                    }
                    if (result.list.size() > 0 && this.this$0.mMineListViewAdapter != null) {
                        Collections.sort(result.list, new ComparatorMyProfile(this.this$0));
                        this.this$0.mMineListViewAdapter.setList(result.list);
                    }
                }
                LogInfo.log("ZSM", "mineListRequestTask url ==" + PlayRecordApi.getInstance().getMineListUrl(hull.markId, PreferencesManager.getInstance().getUserId(), PreferencesManager.getInstance().getSso_tk()));
                request.setUrl(PlayRecordApi.getInstance().getMineListUrl(hull.markId, PreferencesManager.getInstance().getUserId(), PreferencesManager.getInstance().getSso_tk()));
                request.setTag("request_my_list_task");
            }

            public void onErrorReport(VolleyRequest<MyProfileListBean> request, String errorInfo) {
                super.onErrorReport(request, errorInfo);
            }
        }).add();
    }

    private void showDefaultProfileList() {
        MyProfileListBean myProfileList = null;
        try {
            myProfileList = new MyProfileListParser().parse(new JSONObject(getString(2131100378)).getJSONObject("body"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (myProfileList != null && myProfileList.list.size() > 1) {
            Collections.sort(myProfileList.list, new ComparatorMyProfile(this));
        }
        if (myProfileList != null && this.mMineListViewAdapter != null) {
            this.mMineListViewAdapter.setList(myProfileList.list);
        }
    }

    public int getContainerId() {
        return R.id.main_content;
    }

    public String getTagName() {
        return FragmentConstant.TAG_FRAGMENT_MINE;
    }

    public void refreshNewMessageVisible(boolean visible) {
        boolean z;
        int i = 0;
        this.mIsNewMessageVisible = visible;
        String str = "wdm";
        StringBuilder append = new StringBuilder().append("view是否为空：");
        if (this.mNewMessageIcon == null) {
            z = true;
        } else {
            z = false;
        }
        LogInfo.log(str, append.append(z).toString());
        if (this.mNewMessageIcon != null) {
            ImageView imageView = this.mNewMessageIcon;
            if (!visible) {
                i = 8;
            }
            imageView.setVisibility(i);
        }
    }

    public void setReload() {
        if (this.mHasRequest) {
            Volley.getQueue().cancelWithTag("request_my_list_task");
            mineListRequestTask(RequestManner.CACHE_THEN_NETROWK);
        }
    }
}
