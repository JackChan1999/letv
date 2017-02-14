package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.adapter.ElectronicTicketAdapter;
import com.letv.android.client.commonlib.utils.UIControllerUtils;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.MyElectronicTicketBean;
import com.letv.core.bean.MyElectronicTicketBean.ElectronicTicket;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.parser.MyElectronicTicketParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.sina.weibo.sdk.component.ShareRequestParam;
import java.util.ArrayList;

public class MyElectronicTicketActivity extends PimBaseActivity {
    private final int NORMAL_TICKET_TYPE;
    private final int VIP_TICKET_TYPE;
    private ElectronicTicketAdapter mElectronicTicketAdapter;
    private ListView mElectronicTicketListView;
    private LinearLayout mLoginLayout;
    private RelativeLayout mNoLoginLayout;
    private TextView mNoLoginTv;
    private TextView mTicketTipMessage;
    private TextView mTicketTipTitle;
    private ArrayList<ElectronicTicket> mTicketsList;

    public MyElectronicTicketActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.VIP_TICKET_TYPE = 0;
        this.NORMAL_TICKET_TYPE = 1;
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MyElectronicTicketActivity.class));
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initUI();
    }

    public void initUI() {
        super.initUI();
        this.mElectronicTicketListView = (ListView) findViewById(R.id.my_electronic_ticket_list);
        this.mNoLoginLayout = (RelativeLayout) findViewById(R.id.ticket_no_login_layout);
        this.mLoginLayout = (LinearLayout) findViewById(R.id.ticket_login_layout);
        this.mNoLoginTv = (TextView) findViewById(R.id.no_login_tv);
        this.mTicketTipTitle = (TextView) findViewById(R.id.ticket_tip_title);
        this.mTicketTipMessage = (TextView) findViewById(R.id.ticket_tip);
        this.mElectronicTicketAdapter = new ElectronicTicketAdapter(this);
        setTitle(2131100618);
        this.mTicketTipTitle.setText(TipUtils.getTipTitle("90052", 2131100967));
        this.mTicketTipMessage.setText(TipUtils.getTipMessage("90052", 2131100966));
        viewClick();
        StatisticsUtils.statisticsActionInfo(this, PageIdConstant.myCardTicketPage, "19", null, null, -1, null);
    }

    private void viewClick() {
        this.mNoLoginTv.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MyElectronicTicketActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                LetvLoginActivity.launch(this.this$0.getActivity());
                StatisticsUtils.statisticsActionInfo(this.this$0.getActivity(), PageIdConstant.myCardTicketPage, "0", "cd02", null, 1, null);
            }
        });
        this.mElectronicTicketListView.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ MyElectronicTicketActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ElectronicTicket mElectronicTicket = (ElectronicTicket) this.this$0.mTicketsList.get(position);
                String name = mElectronicTicket.name + this.this$0.mContext.getString(2131100962, new Object[]{mElectronicTicket.from});
                if (((ElectronicTicket) this.this$0.mTicketsList.get(position)).type == 0 && (!PreferencesManager.getInstance().isVip() || mElectronicTicket.totalNum == -1)) {
                    LetvVipActivity.launch(this.this$0.getActivity(), "");
                    StatisticsUtils.statisticsActionInfo(this.this$0.getActivity(), PageIdConstant.myCardTicketPage, "0", "cd01", name, 2, null);
                } else if (((ElectronicTicket) this.this$0.mTicketsList.get(position)).type != 1 || ((ElectronicTicket) this.this$0.mTicketsList.get(position)).subType <= 0) {
                    Channel channel = UIControllerUtils.getVipChannel(BaseApplication.getInstance());
                    if (channel != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("channel", channel);
                        bundle.putInt("from", 0);
                        Intent intent = new Intent();
                        intent.putExtra("tag", FragmentConstant.TAG_FRAGMENT_CHANNEL_DETAIL);
                        intent.putExtra(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA, bundle);
                        intent.setClass(this.this$0.mContext, MainActivity.class);
                        this.this$0.mContext.startActivity(intent);
                    }
                    StatisticsUtils.statisticsActionInfo(this.this$0.getActivity(), PageIdConstant.myCardTicketPage, "0", "cd01", name, 1, null);
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        if (PreferencesManager.getInstance().isLogin()) {
            requestTicketShow();
            this.mNoLoginLayout.setVisibility(8);
            this.mLoginLayout.setVisibility(0);
            return;
        }
        this.mNoLoginLayout.setVisibility(0);
        this.mLoginLayout.setVisibility(8);
    }

    private void requestTicketShow() {
        this.mRootView.loading(true);
        new LetvRequest(MyElectronicTicketBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PlayRecordApi.getInstance().requestTicketUrl(0, PreferencesManager.getInstance().getUserId(), "365")).setParser(new MyElectronicTicketParser()).setCallback(new SimpleResponse<MyElectronicTicketBean>(this) {
            final /* synthetic */ MyElectronicTicketActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<MyElectronicTicketBean> volleyRequest, MyElectronicTicketBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("ZSM", "requestTicketShow onNetworkResponse == " + state);
                this.this$0.mRootView.finish();
                switch (state) {
                    case SUCCESS:
                        if (result != null && result.resultCode == 0) {
                            this.this$0.mTicketsList = result.ticketsList;
                            if (this.this$0.mTicketsList.size() < 1) {
                                this.this$0.mTicketsList = result.getDefaultTicketList();
                            }
                            this.this$0.mElectronicTicketAdapter.setList(this.this$0.mTicketsList);
                            this.this$0.mElectronicTicketListView.setAdapter(this.this$0.mElectronicTicketAdapter);
                            return;
                        }
                        return;
                    case NETWORK_NOT_AVAILABLE:
                    case NETWORK_ERROR:
                        ToastUtils.showToast(2131100495);
                        break;
                    case PRE_FAIL:
                    case RESULT_ERROR:
                        break;
                    default:
                        return;
                }
                if (result == null) {
                    result = new MyElectronicTicketBean();
                }
                this.this$0.mTicketsList = result.getDefaultTicketList();
                this.this$0.mElectronicTicketAdapter.setList(result.getDefaultTicketList());
                this.this$0.mElectronicTicketListView.setAdapter(this.this$0.mElectronicTicketAdapter);
            }

            public void onErrorReport(VolleyRequest<MyElectronicTicketBean> request, String errorInfo) {
                LogInfo.log("ZSM", "mineListRequestTask onErrorReport == " + errorInfo);
                super.onErrorReport(request, errorInfo);
                DataStatistics.getInstance().sendErrorInfo(LetvApplication.getInstance(), "0", "0", LetvErrorCode.LTURLModule_UC_VoucherList, null, errorInfo, null, null, null, null);
            }
        }).add();
    }

    public int getContentView() {
        return R.layout.my_electronic_ticket_activity;
    }

    public String getActivityName() {
        return MyElectronicTicketActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
