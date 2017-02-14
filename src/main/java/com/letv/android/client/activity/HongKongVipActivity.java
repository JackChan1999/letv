package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.HongKongVipPackageAdapter;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.bean.RedPacketFrom;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.utils.UIs;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PayCenterApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.VipProductBean;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.VipProductParser;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class HongKongVipActivity extends LetvBaseActivity implements OnClickListener {
    private ImageView mAgreeMembershipImageView;
    private TextView mAgreeMembershipTv;
    private ImageView mBackIv;
    private Button mExchangeBtn;
    private HongKongVipPackageAdapter mHongKongVipPackageAdapter;
    private ListView mPackageListView;
    private ImageView mProductImageView;
    private RefreshData mRefreshData;
    private PublicLoadLayout mRootView;
    private boolean sAgreeMembership;

    public HongKongVipActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.sAgreeMembership = true;
        this.mRefreshData = new RefreshData(this) {
            final /* synthetic */ HongKongVipActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.mRootView.loading(false);
                this.this$0.requsetProductsTask("9");
            }
        };
    }

    public static void launch(Activity context, String title) {
        Intent intent = new Intent(context, HongKongVipActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("isSeniorVip", PreferencesManager.getInstance().isSViP());
        context.startActivityForResult(intent, 102);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRootView = PublicLoadLayout.createPage((Context) this, (int) R.layout.hongkong_vip_activity);
        this.mRootView.setRefreshData(this.mRefreshData);
        setContentView(this.mRootView);
        initUI();
    }

    private void initUI() {
        setRedPacketFrom(new RedPacketFrom(0));
        this.mRootView.loading(false);
        this.mBackIv = (ImageView) findViewById(R.id.hongkong_vip_back_btn);
        this.mBackIv.setOnClickListener(this);
        this.mProductImageView = (ImageView) findViewById(R.id.sproduct_pic);
        UIs.zoomView(320, 98, this.mProductImageView);
        this.mPackageListView = (ListView) this.mRootView.findViewById(R.id.hongkong_vip_listview);
        this.mHongKongVipPackageAdapter = new HongKongVipPackageAdapter(getActivity(), "1");
        this.mPackageListView.setAdapter(this.mHongKongVipPackageAdapter);
        requsetProductsTask("9");
        this.mExchangeBtn = (Button) findViewById(R.id.hongkong_exchange_btn);
        this.mExchangeBtn.setOnClickListener(this);
        this.mAgreeMembershipTv = (TextView) findViewById(R.id.agree_service_protocol_textview);
        this.mAgreeMembershipTv.setOnClickListener(this);
        this.mAgreeMembershipImageView = (ImageView) findViewById(R.id.agree_service_protocol_check_iv);
        this.mAgreeMembershipImageView.setOnClickListener(this);
    }

    private void requsetProductsTask(final String svip) {
        LogInfo.log("LetvVipActivity", "requsetProductsTask start == " + PayCenterApi.getInstance().requestVipProduct(svip, ""));
        new LetvRequest(VipProductBean.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setCache(new VolleyDiskCache("HongKongVipPackage")).setParser(new VipProductParser()).setCallback(new SimpleResponse<VipProductBean>(this) {
            final /* synthetic */ HongKongVipActivity this$0;

            public void onNetworkResponse(VolleyRequest<VipProductBean> volleyRequest, VipProductBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("LetvVipActivity", "requsetProductsTask onNetworkResponse == " + state);
                this.this$0.mRootView.finish();
                switch (state) {
                    case SUCCESS:
                        if (result != null) {
                            this.this$0.mHongKongVipPackageAdapter.setList(result.mProductList);
                            this.this$0.mHongKongVipPackageAdapter.notifyDataSetChanged();
                            return;
                        }
                        return;
                    case PRE_FAIL:
                    case NETWORK_NOT_AVAILABLE:
                    case NETWORK_ERROR:
                        this.this$0.mRootView.netError(false);
                        return;
                    case RESULT_ERROR:
                        this.this$0.mRootView.dataError(false);
                        return;
                    default:
                        return;
                }
            }

            public void onCacheResponse(VolleyRequest<VipProductBean> request, VipProductBean result, DataHull hull, CacheResponseState state) {
                LogInfo.log("LetvVipActivity", "requsetProductsTask onCacheResponse == " + state);
                if (state == CacheResponseState.SUCCESS) {
                    this.this$0.mRootView.finish();
                    if (result != null) {
                        this.this$0.mHongKongVipPackageAdapter.setList(result.mProductList);
                        this.this$0.mHongKongVipPackageAdapter.notifyDataSetChanged();
                    }
                }
                request.setUrl(PayCenterApi.getInstance().requestVipProduct(svip, hull.markId));
            }
        }).add();
    }

    public String[] getAllFragmentTags() {
        return FragmentConstant.TAG_VIP;
    }

    public String getActivityName() {
        return HongKongVipActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }

    public void onClick(View v) {
        boolean z = false;
        switch (v.getId()) {
            case R.id.hongkong_vip_back_btn /*2131362843*/:
                finish();
                return;
            case R.id.hongkong_exchange_btn /*2131362850*/:
                new LetvWebViewActivityConfig(this).launch("http://minisite.letv.com/msite/coffeecode/payhk/index.shtml", getString(2131100111), false, false);
                return;
            case R.id.agree_service_protocol_check_iv /*2131362852*/:
                if (!this.sAgreeMembership) {
                    z = true;
                }
                this.sAgreeMembership = z;
                if (this.sAgreeMembership) {
                    this.mAgreeMembershipImageView.setBackgroundResource(2130837871);
                } else {
                    this.mAgreeMembershipImageView.setBackgroundResource(2130837870);
                }
                this.mHongKongVipPackageAdapter.setAgreeMemberShip(this.sAgreeMembership);
                return;
            case R.id.agree_service_protocol_textview /*2131362853*/:
                new LetvWebViewActivityConfig(this).launch("http://minisite.letv.com/zt2016/8527/zt1003366319/index.shtml", getString(2131100111), false, false);
                return;
            default:
                return;
        }
    }
}
