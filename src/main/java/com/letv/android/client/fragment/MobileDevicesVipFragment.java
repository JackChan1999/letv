package com.letv.android.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.VipPackageAdapter;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.core.bean.VipProductBean;
import com.letv.core.bean.VipProductBean.ProductBean;
import com.letv.core.constant.AlipayConstant;
import com.letv.core.constant.FragmentConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

public class MobileDevicesVipFragment extends LetvBaseFragment {
    private ListView mMobilePackageListView;
    private PublicLoadLayout mRootViewLayout;
    private VipPackageAdapter mVipPackageAdapter;
    private VipProductBean mVipProductBean;

    public MobileDevicesVipFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootViewLayout = PublicLoadLayout.createPage(getActivity(), (int) R.layout.mobile_devices_vip_fragment);
        return this.mRootViewLayout;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        this.mMobilePackageListView = (ListView) this.mRootViewLayout.findViewById(R.id.mobile_devices_listview);
        this.mVipPackageAdapter = new VipPackageAdapter(getActivity(), "1");
        int isOneKeySignPay = getArguments().getInt(AlipayConstant.IS_ONE_KEY_SIGN_PAY_WITH_ALIPAY, -1);
        if (this.mVipProductBean != null) {
            ArrayList<ProductBean> productBeanList = this.mVipProductBean.mProductList;
            if (productBeanList != null && isOneKeySignPay == 1) {
                int nSize = productBeanList.size();
                for (int i = 0; i < nSize; i++) {
                    if (((ProductBean) productBeanList.get(i)).mMonthType == 42) {
                        productBeanList.remove(i);
                        break;
                    }
                }
            }
            this.mVipPackageAdapter.setList(productBeanList);
        }
        this.mVipPackageAdapter.setIsOnKeySignWithAlipay(isOneKeySignPay);
        this.mVipPackageAdapter.setMobileVipFlag(getArguments().getBoolean(AlipayConstant.IS_MOBILE_VIP_FLAG));
        this.mMobilePackageListView.setAdapter(this.mVipPackageAdapter);
    }

    public void skipToOrderDetail() {
        this.mVipPackageAdapter.skipToOrder(this.mVipPackageAdapter.getSipToOrderProductList());
    }

    public String getTagName() {
        return FragmentConstant.TAG_VIP_MOBILE_DEVICES;
    }

    public int getContainerId() {
        return R.id.letv_vip_center_viewpage;
    }

    public VipProductBean getmVipProductBean() {
        return this.mVipProductBean;
    }

    public void setmVipProductBean(VipProductBean mVipProductBean) {
        this.mVipProductBean = mVipProductBean;
    }
}
