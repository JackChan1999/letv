package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.FindActAdapter;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.commonlib.utils.UIControllerUtils;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.view.PullToRefreshBase.OnRefreshListener;
import com.letv.android.client.view.PullToRefreshListView;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.FindChildDataAreaBean;
import com.letv.core.bean.FindDataAreaBean;
import com.letv.core.bean.FindDataBean;
import com.letv.core.bean.FindListDataBean;
import com.letv.core.bean.HomeMetaData;
import com.letv.core.bean.LiveDateInfo;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.listener.OnPreExecuteListener;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.List;

public class FindActActivity extends WrapActivity {
    private Context mContext;
    private FindActAdapter mFindActAdapter;
    private ArrayList<FindChildDataAreaBean> mFindChildDataAreaBean;
    private boolean mIsPullToRefresh;
    private PullToRefreshListView mPullListView;
    private PublicLoadLayout mRoot;
    OnRefreshListener onRefreshListener;

    public FindActActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mFindChildDataAreaBean = new ArrayList();
        this.onRefreshListener = new OnRefreshListener(this) {
            final /* synthetic */ FindActActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onRefresh() {
                this.this$0.mIsPullToRefresh = true;
                if (NetworkUtils.isNetworkAvailable()) {
                    this.this$0.requestDate(true);
                    return;
                }
                ToastUtils.showToast(this.this$0.mContext, 2131100493);
                this.this$0.mPullListView.onRefreshComplete();
                this.this$0.mIsPullToRefresh = false;
            }
        };
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, FindActActivity.class);
        if (!(context instanceof Activity)) {
            intent.setFlags(268435456);
        }
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogInfo.log(getActivityName() + "||wlx", "OnCreate");
        this.mContext = this;
        setContentView(R.layout.activity_find_act);
        findView();
        requestDate(false);
    }

    private void findView() {
        this.mRoot = (PublicLoadLayout) findViewById(R.id.find_act_content_container_layout);
        this.mRoot.addContent(R.layout.activity_find_act_content);
        this.mPullListView = (PullToRefreshListView) this.mRoot.findViewById(R.id.find_act_listView);
        ListView mFindActListView = (ListView) this.mPullListView.getRefreshableView();
        this.mPullListView.setOnRefreshListener(this.onRefreshListener);
        this.mPullListView.setParams(Boolean.valueOf(true), "FindActActivity");
        this.mFindActAdapter = new FindActAdapter(this.mContext, this.mFindChildDataAreaBean);
        mFindActListView.setAdapter(this.mFindActAdapter);
        mFindActListView.setTag(this.mFindActAdapter);
        mFindActListView.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ FindActActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                LogInfo.log("Emerson", "------------position = " + position + "---id = " + id);
                FindChildDataAreaBean fcda = (FindChildDataAreaBean) this.this$0.mFindChildDataAreaBean.get((int) id);
                HomeMetaData homeMetaData = new HomeMetaData();
                homeMetaData.at = Integer.valueOf(fcda.at).intValue();
                homeMetaData.mobilePic = fcda.mobilePic;
                homeMetaData.webViewUrl = fcda.webViewUrl;
                homeMetaData.webUrl = fcda.webUrl;
                homeMetaData.nameCn = fcda.nameCn;
                homeMetaData.subTitle = fcda.subTitle;
                homeMetaData.cmsid = fcda.cmsid;
                homeMetaData.duration = fcda.duration;
                homeMetaData.episode = fcda.episode;
                homeMetaData.nowEpisodes = fcda.nowEpisodes;
                homeMetaData.zid = fcda.zid;
                homeMetaData.streamUrl = fcda.streamUrl;
                homeMetaData.streamCode = fcda.streamCode;
                homeMetaData.tag = fcda.tag;
                UIControllerUtils.gotoActivity(this.this$0.mContext, homeMetaData);
            }
        });
        ((ImageView) findViewById(R.id.topic_back_btn)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ FindActActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                this.this$0.finish();
            }
        });
        this.mRoot.setRefreshData(new RefreshData(this) {
            final /* synthetic */ FindActActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                if (NetworkUtils.isNetworkAvailable()) {
                    this.this$0.requestDate(false);
                } else {
                    ToastUtils.showToast(this.this$0.mContext, 2131100493);
                }
            }
        });
    }

    public String getActivityName() {
        return getClass().getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }

    private void requestDate(final boolean isPullToRefresh) {
        new LetvRequest(LiveDateInfo.class).setUrl(MediaAssetApi.getInstance().getDateUrl()).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setOnPreExecuteListener(new OnPreExecuteListener(this) {
            final /* synthetic */ FindActActivity this$0;

            public boolean onPreExecute() {
                if (!(isPullToRefresh || this.this$0.mRoot == null)) {
                    this.this$0.mRoot.loading(false);
                }
                return true;
            }
        }).setCallback(new SimpleResponse<LiveDateInfo>(this) {
            final /* synthetic */ FindActActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<LiveDateInfo> volleyRequest, LiveDateInfo result, DataHull hull, NetworkResponseState state) {
                LogInfo.log(this.this$0.getActivityName() + "||wlx", "state=" + state);
                if (state == NetworkResponseState.SUCCESS) {
                    this.this$0.requestFindData(result.date);
                    return;
                }
                if (this.this$0.mRoot != null) {
                    this.this$0.mRoot.netError(false);
                }
                if (this.this$0.mIsPullToRefresh) {
                    this.this$0.mIsPullToRefresh = false;
                    this.this$0.mPullListView.onRefreshComplete();
                }
            }

            public void onCacheResponse(VolleyRequest<LiveDateInfo> volleyRequest, LiveDateInfo result, DataHull hull, CacheResponseState state) {
            }

            public void onErrorReport(VolleyRequest<LiveDateInfo> volleyRequest, String errorInfo) {
                LogInfo.log(this.this$0.getActivityName() + "||wlx", "errorInfo=" + errorInfo);
            }
        }).add();
    }

    private void requestFindData(final String date) {
        new LetvRequest(FindListDataBean.class).setUrl(MediaAssetApi.getInstance().getFindUrl(null)).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<FindListDataBean>(this) {
            final /* synthetic */ FindActActivity this$0;

            public void onNetworkResponse(VolleyRequest<FindListDataBean> volleyRequest, FindListDataBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log(this.this$0.getActivityName() + "||wlx", "state=" + state);
                if (state == NetworkResponseState.SUCCESS) {
                    LogInfo.log(this.this$0.getActivityName() + "||wlx", "date = " + date);
                    this.this$0.refreshView(result, date);
                    return;
                }
                if (this.this$0.mRoot != null) {
                    this.this$0.mRoot.netError(false);
                }
                if (this.this$0.mIsPullToRefresh) {
                    this.this$0.mIsPullToRefresh = false;
                    this.this$0.mPullListView.onRefreshComplete();
                }
            }

            public void onCacheResponse(VolleyRequest<FindListDataBean> volleyRequest, FindListDataBean result, DataHull hull, CacheResponseState state) {
            }

            public void onErrorReport(VolleyRequest<FindListDataBean> volleyRequest, String errorInfo) {
                LogInfo.log(this.this$0.getActivityName() + "||wlx", "errorInfo=" + errorInfo);
            }
        }).add();
    }

    private void refreshView(FindListDataBean result, String date) {
        if (this.mIsPullToRefresh) {
            this.mIsPullToRefresh = false;
            this.mPullListView.onRefreshComplete();
        }
        if (this.mRoot != null) {
            this.mRoot.finish();
        }
        List<FindDataBean> findDataListBean = result.getData();
        if (findDataListBean != null && findDataListBean.size() > 0) {
            int i = 0;
            while (i < findDataListBean.size()) {
                if ("3".equals(((FindDataBean) findDataListBean.get(i)).area) && ((FindDataBean) findDataListBean.get(i)).getData() != null && ((FindDataBean) findDataListBean.get(i)).getData().size() > 0) {
                    int j = 0;
                    while (j < ((FindDataBean) findDataListBean.get(i)).getData().size()) {
                        if ("201".equals(((FindDataAreaBean) ((FindDataBean) findDataListBean.get(i)).getData().get(j)).type) && ((FindDataAreaBean) ((FindDataBean) findDataListBean.get(i)).getData().get(j)).data != null && ((FindDataAreaBean) ((FindDataBean) findDataListBean.get(i)).getData().get(j)).data.size() > 0) {
                            LogInfo.log("Emerson", "------FindAct 有数据刷新 ---");
                            this.mFindChildDataAreaBean = ((FindDataAreaBean) ((FindDataBean) findDataListBean.get(i)).getData().get(j)).data;
                            this.mFindActAdapter.setFindChildData(((FindDataAreaBean) ((FindDataBean) findDataListBean.get(i)).getData().get(j)).data, date);
                            break;
                        }
                        j++;
                    }
                }
                i++;
            }
        }
    }
}
