package com.letv.android.client.live.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.live.adapter.LiveAllPlayingAdapter;
import com.letv.android.client.parser.ChannelLiveSportParse;
import com.letv.android.client.view.PullToRefreshBase.OnRefreshListener;
import com.letv.android.client.view.PullToRefreshListView;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.LiveRemenListBean;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LiveAllPlayingActivity extends LetvBaseActivity implements OnClickListener {
    private static final String TAG = "LiveAllPlayingActivity";
    private LiveAllPlayingAdapter mAdapter;
    private ListView mListView;
    private PullToRefreshListView mPullToRefreshListView;
    private PublicLoadLayout mRootView;
    private ImageView mTopBack;
    private TextView mTopTitle;

    public LiveAllPlayingActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LiveAllPlayingActivity.class));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataFromIntent();
        init();
        loadData(false);
    }

    private void initDataFromIntent() {
    }

    private void init() {
        setContentView(R.layout.activity_live_allplaying);
        this.mRootView = (PublicLoadLayout) findViewById(R.id.live_allplaying_content);
        this.mRootView.loading(true);
        this.mTopTitle = (TextView) findViewById(R.id.live_allplaying_top_title);
        this.mTopBack = (ImageView) findViewById(R.id.live_allplaying_top_back);
        this.mRootView.addContent(R.layout.live_allplaying_content);
        this.mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.live_allplaying_pulllistview);
        this.mListView = (ListView) this.mPullToRefreshListView.getRefreshableView();
        this.mAdapter = new LiveAllPlayingAdapter(this.mContext);
        this.mListView.setAdapter(this.mAdapter);
        this.mTopTitle.setOnClickListener(this);
        this.mTopBack.setOnClickListener(this);
        this.mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener(this) {
            final /* synthetic */ LiveAllPlayingActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onRefresh() {
                this.this$0.loadData(true);
            }
        });
        this.mListView.setOnScrollListener(new OnScrollListener(this) {
            final /* synthetic */ LiveAllPlayingActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (this.this$0.mAdapter != null) {
                    this.this$0.mAdapter.onScrollStateChanged(view, scrollState);
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (this.this$0.mAdapter != null) {
                    this.this$0.mAdapter.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
        this.mRootView.setRefreshData(new RefreshData(this) {
            final /* synthetic */ LiveAllPlayingActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.loadData(false);
            }
        });
    }

    private void loadData(final boolean isPullToRefresh) {
        Volley.getQueue().cancelWithTag(getActivityName());
        new LetvRequest(LiveRemenListBean.class).setUrl(LetvUrlMaker.getLiveAllPlayingUrl()).setParser(new ChannelLiveSportParse()).setTag(getActivityName()).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<LiveRemenListBean>(this) {
            final /* synthetic */ LiveAllPlayingActivity this$0;

            public void onNetworkResponse(VolleyRequest<LiveRemenListBean> volleyRequest, LiveRemenListBean result, DataHull hull, NetworkResponseState state) {
                if (this.this$0.mRootView != null) {
                    this.this$0.mRootView.finish();
                }
                if (isPullToRefresh) {
                    this.this$0.mPullToRefreshListView.onRefreshComplete();
                }
                switch (state) {
                    case SUCCESS:
                        LogInfo.log(LiveAllPlayingActivity.TAG, "onResponse: " + result);
                        this.this$0.mAdapter.clear();
                        if (result != null && !BaseTypeUtils.isListEmpty(result.mRemenList)) {
                            this.this$0.mAdapter.setList(result.mRemenList);
                            return;
                        } else if (this.this$0.mRootView != null) {
                            this.this$0.mRootView.showErrorMessage(this.this$0.getString(2131101570));
                            return;
                        } else {
                            return;
                        }
                    case NETWORK_NOT_AVAILABLE:
                    case NETWORK_ERROR:
                        if (this.this$0.mRootView != null) {
                            this.this$0.mRootView.netError(false);
                            return;
                        }
                        return;
                    case RESULT_ERROR:
                        if (this.this$0.mRootView != null) {
                            this.this$0.mRootView.dataError(false);
                            return;
                        }
                        return;
                    default:
                        LogInfo.log(LiveAllPlayingActivity.TAG, "Request from network LiveRemenListBean failed: " + state);
                        return;
                }
            }

            public void onCacheResponse(VolleyRequest<LiveRemenListBean> volleyRequest, LiveRemenListBean result, DataHull hull, CacheResponseState state) {
            }
        }).add();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.live_allplaying_top_back) {
            finish();
        }
    }

    public String[] getAllFragmentTags() {
        return new String[0];
    }

    public String getActivityName() {
        return getClass().getName();
    }

    public Activity getActivity() {
        return this;
    }
}
