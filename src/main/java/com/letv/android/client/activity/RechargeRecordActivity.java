package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.adapter.RechargeRecordAdapter;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.RechargeRecordListBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.parser.RechargeRecordListParser;
import com.letv.core.utils.LogInfo;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RechargeRecordActivity extends PimBaseActivity {
    private RechargeRecordAdapter mAdapter;
    private TextView mErrorSubTv;
    private TextView mErrorTv;
    private ListView mListView;
    private RechargeRecordListBean mRechargeRecordsInfo;
    private RefreshData mRefreshData;

    public RechargeRecordActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mRechargeRecordsInfo = null;
        this.mAdapter = null;
        this.mRefreshData = new RefreshData(this) {
            final /* synthetic */ RechargeRecordActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.requestRechargeRecords();
            }
        };
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, RechargeRecordActivity.class));
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initUI();
        init();
        this.mRootView.setRefreshData(this.mRefreshData);
    }

    protected void onResume() {
        super.onResume();
        if (PreferencesManager.getInstance().isLogin()) {
            requestRechargeRecords();
        } else {
            showErrorLayoutMessage(2131100620);
        }
    }

    private void init() {
        this.mAdapter = new RechargeRecordAdapter(this);
        this.mAdapter.setList(this.mRechargeRecordsInfo);
        this.mListView.setAdapter(this.mAdapter);
        setTitle(2131100634);
    }

    private void updateUI() {
        this.mAdapter.setList(this.mRechargeRecordsInfo);
        this.mAdapter.notifyDataSetChanged();
    }

    public int getContentView() {
        return R.layout.pim_consume_fragment;
    }

    public void initUI() {
        super.initUI();
        this.mListView = (ListView) findViewById(R.id.consume_record_list);
        this.mErrorTv = (TextView) findViewById(R.id.consume_error_text);
        this.mErrorSubTv = (TextView) findViewById(R.id.consume_error_text_sub);
    }

    private void requestRechargeRecords() {
        this.mRootView.loading(true);
        new LetvRequest(RechargeRecordListBean.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setUrl(PlayRecordApi.getInstance().queryRecord(0, PreferencesManager.getInstance().getUserId(), PreferencesManager.getInstance().getUserName(), "0", "0", "01", "0", null, null, null)).setParser(new RechargeRecordListParser()).setCallback(new SimpleResponse<RechargeRecordListBean>(this) {
            final /* synthetic */ RechargeRecordActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<RechargeRecordListBean> volleyRequest, RechargeRecordListBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("ZSM", "requestRechargeRecords onNetworkResponse == " + state);
                this.this$0.mRootView.finish();
                if (state == NetworkResponseState.SUCCESS) {
                    this.this$0.mRechargeRecordsInfo = result;
                    if (result == null || result.size() == 0) {
                        this.this$0.rechargeError();
                        return;
                    }
                    this.this$0.mRootView.finish();
                    this.this$0.updateUI();
                } else if (state != NetworkResponseState.RESULT_ERROR) {
                    this.this$0.showNetNullMessage();
                } else if (hull.message == null || hull.message.trim().length() == 0) {
                    this.this$0.rechargeError();
                } else {
                    this.this$0.showErrorLayoutMessage(hull.message);
                }
            }

            public void onErrorReport(VolleyRequest<RechargeRecordListBean> request, String errorInfo) {
                LogInfo.log("ZSM", "mineListRequestTask onErrorReport == " + errorInfo);
                super.onErrorReport(request, errorInfo);
                DataStatistics.getInstance().sendErrorInfo(LetvApplication.getInstance(), "0", "0", LetvErrorCode.LTURLModule_UC_Recharge, null, errorInfo, null, null, null, null);
            }
        }).add();
    }

    private void rechargeError() {
        if (this.mRootView != null) {
            this.mRootView.finish();
        }
        if (this.mErrorTv != null) {
            this.mErrorTv.setVisibility(0);
            this.mErrorSubTv.setVisibility(0);
            this.mListView.setVisibility(8);
        }
    }

    public String getActivityName() {
        return RechargeRecordActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
