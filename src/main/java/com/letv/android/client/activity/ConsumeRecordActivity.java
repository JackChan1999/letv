package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.adapter.ConsumeRecordAdapter;
import com.letv.android.client.commonlib.view.ChannelListFootView;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.utils.UIs;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.SaleNoteListBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.parser.SaleNoteNewListParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class ConsumeRecordActivity extends PimBaseActivity {
    public static final int STATE_ALL = 0;
    public static final int STATE_NOPAY = 1;
    public static final int STATE_PAYGONE = 3;
    public static final int STATE_SUCCESS = 2;
    private int currentPage;
    private String current_date;
    private TextView error;
    boolean isNetErr;
    private boolean isRequestData;
    private ConsumeRecordAdapter mAdapter;
    private ChannelListFootView mChannelListFootView;
    private ListView mListView;
    private RefreshData mRefreshData;
    private String num;
    private SaleNoteListBean saleNoteList;

    class ScrollEvent implements OnScrollListener {
        private int mFirstVisibleItem;
        private int mTotalItemCount;
        private int mVisibleItemCount;
        final /* synthetic */ ConsumeRecordActivity this$0;

        ScrollEvent(ConsumeRecordActivity this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
            this.mFirstVisibleItem = 0;
            this.mVisibleItemCount = 0;
            this.mTotalItemCount = 0;
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case 0:
                    if (this.this$0.saleNoteList != null && this.this$0.mAdapter != null && !this.this$0.isRequestData) {
                        this.mTotalItemCount = this.mFirstVisibleItem + this.mVisibleItemCount;
                        if (this.this$0.mListView.getFooterViewsCount() > 0) {
                            this.mTotalItemCount--;
                        }
                        if (this.mTotalItemCount >= this.this$0.saleNoteList.size() && this.mTotalItemCount < Integer.parseInt(this.this$0.saleNoteList.totalCount)) {
                            if (this.this$0.isNetErr) {
                                this.this$0.showFooterLoading();
                                this.this$0.isNetErr = false;
                            }
                            this.this$0.currentPage = this.this$0.currentPage + 1;
                            this.this$0.requestConsumerRecords(this.this$0.currentPage);
                            return;
                        } else if (this.this$0.mListView.getFooterViewsCount() > 0 && this.mTotalItemCount >= Integer.parseInt(this.this$0.saleNoteList.totalCount)) {
                            this.this$0.removeFooterView();
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                default:
                    return;
            }
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            this.mFirstVisibleItem = firstVisibleItem;
            this.mVisibleItemCount = visibleItemCount;
        }
    }

    public ConsumeRecordActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.current_date = "0";
        this.currentPage = 1;
        this.saleNoteList = null;
        this.num = "20";
        this.isRequestData = false;
        this.mRefreshData = new RefreshData(this) {
            final /* synthetic */ ConsumeRecordActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.requestConsumerRecords(1);
            }
        };
        this.isNetErr = false;
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initUI();
        init();
        if (PreferencesManager.getInstance().isLogin()) {
            requestConsumerRecords(1);
        } else {
            showErrorLayoutMessage(2131100620);
        }
        this.mRootView.setRefreshData(this.mRefreshData);
    }

    private void init() {
        this.mAdapter = new ConsumeRecordAdapter(this);
        this.mAdapter.setList(this.saleNoteList);
        this.mListView.addFooterView(this.mChannelListFootView);
        this.mChannelListFootView.setVisibility(8);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnScrollListener(new ScrollEvent(this));
        setTitle(2131100614);
    }

    private void updateUi() {
        this.mAdapter.setList(this.saleNoteList);
        this.mAdapter.notifyDataSetChanged();
        if (this.mListView.getLastVisiblePosition() >= this.saleNoteList.size() - 1 || this.saleNoteList.size() >= Integer.valueOf(this.saleNoteList.totalCount).intValue()) {
            hideFooterView();
        } else {
            showFooterView();
        }
    }

    public int getContentView() {
        return R.layout.pim_consume_fragment;
    }

    public void initUI() {
        super.initUI();
        this.mListView = (ListView) findViewById(R.id.consume_record_list);
        this.error = (TextView) findViewById(R.id.consume_error_text);
        this.mChannelListFootView = new ChannelListFootView(this);
    }

    private void requestConsumerRecords(final int page) {
        this.mRootView.loading(true);
        new LetvRequest(SaleNoteListBean.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setUrl(PlayRecordApi.getInstance().saleNotes(0, PreferencesManager.getInstance().getUserId(), "2", "" + String.valueOf(this.current_date), "" + page, "" + this.num)).setParser(new SaleNoteNewListParser()).setCallback(new SimpleResponse<SaleNoteListBean>(this) {
            final /* synthetic */ ConsumeRecordActivity this$0;

            public void onNetworkResponse(VolleyRequest<SaleNoteListBean> volleyRequest, SaleNoteListBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("ZSM", "requestConsumerRecords onNetworkResponse == " + state);
                switch (state) {
                    case SUCCESS:
                        if (result != null) {
                            LogInfo.log("ZSM", "requestConsumerRecords result == " + result.size());
                            this.this$0.mRootView.finish();
                            if (result.size() == 0 && page == 1) {
                                this.this$0.consumeError();
                            } else {
                                this.this$0.hideErrorLayoutMessage();
                                if (this.this$0.mListView.getVisibility() == 8) {
                                    this.this$0.mListView.setVisibility(0);
                                }
                                this.this$0.currentPage = page;
                                if (page > 1) {
                                    this.this$0.saleNoteList.addAll(result);
                                } else {
                                    this.this$0.saleNoteList = result;
                                }
                                this.this$0.updateUi();
                            }
                            this.this$0.isRequestData = false;
                            this.this$0.isNetErr = false;
                            return;
                        }
                        return;
                    case NETWORK_NOT_AVAILABLE:
                        this.this$0.showNetNullMessage();
                        this.this$0.isRequestData = false;
                        this.this$0.isNetErr = true;
                        return;
                    case NETWORK_ERROR:
                        this.this$0.showErrorLayoutMessage(hull.message);
                        this.this$0.isRequestData = false;
                        this.this$0.isNetErr = true;
                        return;
                    case RESULT_ERROR:
                        if (hull.message == null || hull.message.trim().length() == 0) {
                            this.this$0.consumeError();
                        } else {
                            this.this$0.showErrorLayoutMessage(hull.message);
                        }
                        this.this$0.isRequestData = false;
                        return;
                    default:
                        return;
                }
            }

            public void onErrorReport(VolleyRequest<SaleNoteListBean> request, String errorInfo) {
                LogInfo.log("ZSM", "mineListRequestTask onErrorReport == " + errorInfo);
                super.onErrorReport(request, errorInfo);
                DataStatistics.getInstance().sendErrorInfo(LetvApplication.getInstance(), "0", "0", LetvErrorCode.LTURLModule_UC_Consume, null, errorInfo, null, null, null, null);
            }
        }).add();
    }

    private void consumeError() {
        if (this.error != null) {
            this.error.setVisibility(0);
            this.mListView.setVisibility(8);
            this.error.setText(2131100619);
            Drawable drawable = getResources().getDrawable(2130838069);
            drawable.setBounds(0, 0, UIs.dipToPx(72.0f), UIs.dipToPx(72.0f));
            this.error.setCompoundDrawables(null, drawable, null, null);
            this.error.setCompoundDrawablePadding(UIs.dipToPx(14.0f));
        }
    }

    public void setOnScrollListener(OnScrollListener l) {
        this.mListView.setOnScrollListener(l);
    }

    public int getFooterViewsCount() {
        return this.mListView.getFooterViewsCount();
    }

    public void showFooterView() {
        this.mChannelListFootView.setVisibility(0);
        this.mChannelListFootView.invalidate();
    }

    public void hideFooterView() {
        this.mChannelListFootView.setVisibility(8);
        this.mChannelListFootView.invalidate();
    }

    public void removeFooterView() {
        if (getFooterViewsCount() > 0) {
            this.mListView.removeFooterView(this.mChannelListFootView);
        }
    }

    public void addFooterView() {
        if (getFooterViewsCount() == 0) {
            this.mListView.addFooterView(this.mChannelListFootView);
        }
    }

    public void showFooterLoading() {
        this.mChannelListFootView.showLoading();
        this.mChannelListFootView.invalidate();
    }

    public void showLoadingRefresh(boolean isNew) {
        this.mChannelListFootView.showRefresh();
        this.mChannelListFootView.invalidate();
        if (!isNew) {
            ToastUtils.showToast((Context) this, 2131099843);
        }
    }

    public String getActivityName() {
        return ConsumeRecordActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
