package com.letv.android.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import com.letv.android.client.R;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.adapter.FindAdapter;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.FindListDataBean;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.FindListDataParser;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

public class FindFragment extends LetvBaseFragment {
    public static final String LETVFINDID = "10";
    private final String TAG;
    private FindListDataBean findListDataBean;
    private FindAdapter mFindAdapter;
    private ExpandableListView mFindListView;
    private boolean mHasRequest;
    private PublicLoadLayout mRoot;
    private MainActivity mainActivity;

    public FindFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.TAG = LetvBaseFragment.class.getName();
    }

    public void setFindListDataBean(FindListDataBean findListDataBean) {
        this.findListDataBean = findListDataBean;
    }

    public void saveFindSpreadTimeStamp() {
        if (this.findListDataBean != null) {
            this.findListDataBean.saveSpreadBeanTimeStamp();
        }
    }

    public String getTagName() {
        return FragmentConstant.TAG_FRAGMENT_FIND;
    }

    public int getDisappearFlag() {
        return 0;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.mRoot == null) {
            this.mRoot = PublicLoadLayout.createPage(getActivity(), (int) R.layout.find_fragment_layout);
        }
        LogInfo.log(getTagName() + "||wlx", "onCreateView");
        return this.mRoot;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findview();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mainActivity = (MainActivity) getActivity();
        if (this.findListDataBean == null) {
            request();
        }
    }

    private void findview() {
        this.mFindListView = (ExpandableListView) this.mRoot.findViewById(R.id.find_listView);
        this.mFindAdapter = new FindAdapter(getActivity(), new ArrayList());
        this.mFindAdapter.setListView(this.mFindListView);
        this.mFindListView.setAdapter(this.mFindAdapter);
        refreshView(this.findListDataBean);
        this.mFindListView.setOnGroupClickListener(new OnGroupClickListener(this) {
            final /* synthetic */ FindFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        this.mRoot.setRefreshData(new RefreshData(this) {
            final /* synthetic */ FindFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.request();
            }
        });
    }

    private void request() {
        if (this.mRoot != null) {
            this.mRoot.loading(false);
        }
        this.mHasRequest = true;
        Volley.getQueue().cancelWithTag(this.TAG);
        new LetvRequest(FindListDataBean.class).setCache(new VolleyDiskCache(FindListDataBean.CACHE_KEY)).setParser(new FindListDataParser()).setTag(this.TAG).setCallback(new SimpleResponse<FindListDataBean>(this) {
            final /* synthetic */ FindFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<FindListDataBean> volleyRequest, FindListDataBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log(this.this$0.getTagName() + "||wlx", "state=" + state);
                if (state == NetworkResponseState.SUCCESS) {
                    this.this$0.findListDataBean = result;
                    this.this$0.refreshView(result);
                    if (this.this$0.mainActivity != null) {
                        this.this$0.mainActivity.setNewFindVisible(result.hasNewSpread());
                    }
                } else if (state == NetworkResponseState.RESULT_NOT_UPDATE) {
                    LogInfo.log(this.this$0.getTagName() + "||wlx", "发现数据没有更新");
                    if (this.this$0.mRoot != null) {
                        this.this$0.mRoot.finish();
                    }
                } else if (this.this$0.mRoot != null) {
                    this.this$0.mRoot.netError(false);
                }
            }

            public void onCacheResponse(VolleyRequest<FindListDataBean> request, FindListDataBean result, DataHull hull, CacheResponseState state) {
                LogInfo.log(this.this$0.getTagName() + "||wlx", "state=" + state);
                request.setUrl(MediaAssetApi.getInstance().getFindUrl(hull.markId));
                if (state == CacheResponseState.SUCCESS) {
                    this.this$0.findListDataBean = result;
                    this.this$0.refreshView(result);
                    if (this.this$0.mRoot != null) {
                        this.this$0.mRoot.finish();
                    }
                }
            }

            public void onErrorReport(VolleyRequest<FindListDataBean> volleyRequest, String errorInfo) {
                LogInfo.log(this.this$0.getTagName() + "||wlx", "errorInfo=" + errorInfo);
            }
        }).add();
    }

    private void refreshView(FindListDataBean result) {
        if (result != null && result.getData().size() > 0) {
            this.mFindAdapter.setFindDataListBean(result.getData());
            this.mFindListView.setAdapter(this.mFindAdapter);
            this.mFindListView.expandGroup(0);
            if (this.mRoot != null) {
                this.mRoot.finish();
            }
        }
    }

    public int getContainerId() {
        return R.id.main_content;
    }

    public void onResume() {
        super.onResume();
        LogInfo.log(getTagName() + "||wlx", "onResume");
        request();
    }

    public void setReload() {
        if (this.mHasRequest) {
            request();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Volley.getQueue().cancelWithTag(this.TAG);
    }
}
