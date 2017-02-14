package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.adapter.MyTicketAdapter;
import com.letv.android.client.commonlib.view.ChannelListFootView;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.utils.UIs;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.TicketShowListBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.parser.TicketShowListParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MyTicketActivity extends PimBaseActivity {
    public static String IS_VIP = "isvip";
    private int currentPage;
    boolean isNetErr;
    private boolean isRequestData;
    private MyTicketAdapter mAdapter;
    private ChannelListFootView mChannelListFootView;
    private ListView mListView;
    private Button mNoTicketBeVip;
    private ImageView mNoTicketImg;
    private ScrollView mNoTicketLayout;
    private TextView mNoTicketMsg;
    RefreshData mRefreshData;
    private TicketShowListBean mTicketShowList;
    private TextView mVipNoTicketText;
    private String num;

    class ScrollEvent implements OnScrollListener {
        private int _firstVisibleItem;
        private int _visibleItemCount;
        final /* synthetic */ MyTicketActivity this$0;
        private int totalItemCount;

        ScrollEvent(MyTicketActivity this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
            this._firstVisibleItem = 0;
            this._visibleItemCount = 0;
            this.totalItemCount = 0;
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case 0:
                    if (this.this$0.mAdapter != null && !this.this$0.isRequestData) {
                        this.totalItemCount = this._firstVisibleItem + this._visibleItemCount;
                        if (this.this$0.mListView.getFooterViewsCount() > 0) {
                            this.totalItemCount--;
                        }
                        if (this.totalItemCount >= this.this$0.mTicketShowList.size() && this.totalItemCount < Integer.parseInt(this.this$0.mTicketShowList.totalSize)) {
                            if (this.this$0.isNetErr) {
                                this.this$0.showFooterLoading();
                                this.this$0.isNetErr = false;
                            }
                            this.this$0.currentPage = this.this$0.currentPage + 1;
                            this.this$0.requestTicketShow(this.this$0.currentPage);
                            return;
                        } else if (this.this$0.mListView.getFooterViewsCount() > 0 && this.totalItemCount >= Integer.parseInt(this.this$0.mTicketShowList.totalSize)) {
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
            this._firstVisibleItem = firstVisibleItem;
            this._visibleItemCount = visibleItemCount;
        }
    }

    public MyTicketActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mTicketShowList = null;
        this.num = "20";
        this.isRequestData = false;
        this.mRefreshData = new RefreshData(this) {
            final /* synthetic */ MyTicketActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.requestTicketShow(1);
            }
        };
        this.isNetErr = false;
    }

    public static void launch(Context context, boolean isVip) {
        Intent intent = new Intent(context, MyTicketActivity.class);
        intent.putExtra(IS_VIP, isVip);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initUI();
        init();
    }

    protected void onResume() {
        super.onResume();
        if (PreferencesManager.getInstance().isLogin()) {
            requestTicketShow(1);
        } else {
            showErrorLayoutMessage(2131100620);
        }
    }

    public int getContentView() {
        return R.layout.pim_my_ticket;
    }

    public void initUI() {
        super.initUI();
        this.mListView = (ListView) findViewById(R.id.my_ticket_list);
        this.mChannelListFootView = new ChannelListFootView(this);
        this.mNoTicketLayout = (ScrollView) findViewById(R.id.no_ticket_layout);
        this.mNoTicketBeVip = (Button) findViewById(R.id.no_ticket_bevip);
        this.mNoTicketImg = (ImageView) findViewById(R.id.no_ticket_img);
        this.mVipNoTicketText = (TextView) findViewById(R.id.vip_no_ticket_msg);
        this.mNoTicketMsg = (TextView) findViewById(R.id.no_ticket_msg);
        UIs.zoomView(320, 75, this.mNoTicketImg);
    }

    private void init() {
        setTitle(2131100618);
        this.mTicketShowList = new TicketShowListBean();
        this.mAdapter = new MyTicketAdapter(this);
        this.mRootView.setRefreshData(this.mRefreshData);
        this.mListView.setOnScrollListener(new ScrollEvent(this));
    }

    private void requestTicketShow(final int page) {
        if (page == 1) {
            this.mRootView.loading(true);
            new LetvRequest(TicketShowListBean.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setUrl(PlayRecordApi.getInstance().requestTicketShowList(0, PreferencesManager.getInstance().getUserId(), String.valueOf(page), this.num)).setParser(new TicketShowListParser()).setCallback(new SimpleResponse<TicketShowListBean>(this) {
                final /* synthetic */ MyTicketActivity this$0;

                public void onNetworkResponse(VolleyRequest<TicketShowListBean> volleyRequest, TicketShowListBean result, DataHull hull, NetworkResponseState state) {
                    LogInfo.log("ZSM", "requestTicketShow onNetworkResponse == " + state);
                    this.this$0.mRootView.finish();
                    switch (state) {
                        case SUCCESS:
                            if (result != null) {
                                this.this$0.currentPage = page;
                                if (page > 1) {
                                    this.this$0.mTicketShowList.addAll(result);
                                } else {
                                    this.this$0.mTicketShowList = result;
                                }
                                this.this$0.updateUI();
                                return;
                            }
                            return;
                        case NETWORK_NOT_AVAILABLE:
                            if (this.this$0.mTicketShowList.size() <= 0) {
                                this.this$0.showNetNullMessage();
                            } else if (this.this$0.mRootView != null) {
                                this.this$0.mRootView.finish();
                            }
                            this.this$0.isNetErr = true;
                            this.this$0.isRequestData = false;
                            return;
                        case NETWORK_ERROR:
                            if (this.this$0.mTicketShowList.size() <= 0) {
                                if (page == 0) {
                                    this.this$0.showNetNullMessage();
                                } else {
                                    this.this$0.noTicket();
                                }
                            } else if (this.this$0.mRootView != null) {
                                this.this$0.mRootView.finish();
                            }
                            LogInfo.log("zlb", "RequestTicketShowListTask netErr = " + hull.errMsg + " , page = " + page);
                            this.this$0.isRequestData = false;
                            this.this$0.isNetErr = true;
                            return;
                        case RESULT_ERROR:
                            if (this.this$0.mTicketShowList.size() <= 0) {
                                this.this$0.showNetNullMessage();
                            } else if (this.this$0.mRootView != null) {
                                this.this$0.mRootView.finish();
                            }
                            LogInfo.log("zlb", "RequestTicketShowListTask dataNull = " + hull.errMsg);
                            this.this$0.isRequestData = false;
                            return;
                        default:
                            return;
                    }
                }

                public void onErrorReport(VolleyRequest<TicketShowListBean> request, String errorInfo) {
                    LogInfo.log("ZSM", "mineListRequestTask onErrorReport == " + errorInfo);
                    super.onErrorReport(request, errorInfo);
                    DataStatistics.getInstance().sendErrorInfo(LetvApplication.getInstance(), "0", "0", LetvErrorCode.LTURLModule_UC_VoucherList, null, errorInfo, null, null, null, null);
                }
            }).add();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    private void updateUI() {
        if ("0".equals(this.mTicketShowList.totalSize)) {
            noTicket();
            return;
        }
        this.mListView.setVisibility(0);
        this.mNoTicketImg.setVisibility(8);
        this.mNoTicketLayout.setVisibility(8);
        this.mAdapter.setList(this.mTicketShowList);
        if (this.mListView.getLastVisiblePosition() >= this.mTicketShowList.size() - 1 || this.mTicketShowList.size() >= Integer.valueOf(this.mTicketShowList.totalSize).intValue()) {
            removeFooterView();
        } else {
            addFooterView();
        }
        this.mListView.setAdapter(this.mAdapter);
    }

    private void noTicket() {
        this.mListView.setVisibility(8);
        this.mNoTicketImg.setVisibility(0);
        ImageDownloader.getInstance().download(this.mNoTicketImg, this.mTicketShowList.mobilePic);
        if (getIntent().getBooleanExtra(IS_VIP, false)) {
            vipNoTicket();
        } else {
            commonNoTicket();
        }
        LogInfo.log("zlb", "");
    }

    private void vipNoTicket() {
        this.mNoTicketLayout.setVisibility(8);
        this.mVipNoTicketText.setVisibility(0);
    }

    private void commonNoTicket() {
        this.mNoTicketLayout.setVisibility(0);
        this.mVipNoTicketText.setVisibility(8);
        String msg = "";
        for (int i = 0; i < this.mTicketShowList.content.size(); i++) {
            msg = msg.concat((String) this.mTicketShowList.content.get(i));
            if (i != this.mTicketShowList.content.size() - 1) {
                msg = msg.concat("\n");
            }
        }
        this.mNoTicketMsg.setText(msg);
        this.mNoTicketBeVip.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MyTicketActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                LetvVipActivity.launch(this.this$0, this.this$0.mContext.getString(2131100645));
                LogInfo.LogStatistics("myticket bevip click");
                StatisticsUtils.staticticsInfoPost(this.this$0, "7133", this.this$0.getResources().getString(2131100645), 0, -1, null, null, null, null, null);
            }
        });
    }

    public void setOnScrollListener(OnScrollListener l) {
        this.mListView.setOnScrollListener(l);
    }

    public int getFooterViewsCount() {
        return this.mListView.getFooterViewsCount();
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 17 && resultCode == 3) {
            finish();
        }
    }

    public String getActivityName() {
        return MyTicketActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
