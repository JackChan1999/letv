package com.letv.android.client.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.thirdpartlogin.HongKongLoginWebview;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PayCenterApi;
import com.letv.core.bean.HongkongOrderBean;
import com.letv.core.bean.VipProductBean.ProductBean;
import com.letv.core.constant.VipProductContant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.HongkongOrderParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

public class HongKongVipPackageAdapter extends LetvBaseAdapter<ProductBean> {
    private Activity mContext;
    private boolean sAgreementShip;

    public HongKongVipPackageAdapter(Activity context, String vipKind) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.sAgreementShip = true;
        this.mContext = context;
    }

    public void setAgreeMemberShip(boolean agreeMemberShip) {
        this.sAgreementShip = agreeMemberShip;
    }

    public ArrayList<ProductBean> getProductList() {
        return (ArrayList) this.mList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(this);
            convertView = PublicLoadLayout.inflate(this.mContext, R.layout.hongkong_vip_package_item, null);
            holder.mHongKongPackageName = (TextView) convertView.findViewById(R.id.hongkong_vip_package_name_tv);
            holder.mHongkongNormalPriceTv = (TextView) convertView.findViewById(R.id.hongkong_vip_normal_price);
            holder.mHongKongPriceBtn = (Button) convertView.findViewById(R.id.hongkong_vip_package_price_btn);
            holder.mRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.hongkong_vip_package_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setDataForView(holder, position);
        return convertView;
    }

    private void setDataForView(ViewHolder holder, int position) {
        ProductBean mProduct = (ProductBean) this.mList.get(position);
        holder.mHongKongPackageName.setText(mProduct.mExpire);
        holder.mHongKongPriceBtn.setText(LetvApplication.getInstance().getString(2131101097, new Object[]{Integer.valueOf((int) mProduct.getNormalPrice())}));
        holder.mHongkongNormalPriceTv.setText(LetvApplication.getInstance().getString(2131101096, new Object[]{Integer.valueOf((int) mProduct.currentPrice)}));
        holder.mRelativeLayout.setOnClickListener(new 1(this, mProduct));
        holder.mHongKongPriceBtn.setOnClickListener(new 2(this, mProduct));
    }

    private void itemClick(ProductBean mProduct) {
        if (!this.sAgreementShip) {
            ToastUtils.showToast(2131099709);
        } else if (NetworkUtils.isNetworkAvailable()) {
            int statisticsPosition = 1;
            String statisticsType = "b321";
            if (mProduct.mMonthType == 2) {
                statisticsPosition = 1;
            } else if (mProduct.mMonthType == 3) {
                statisticsPosition = 2;
            } else if (mProduct.mMonthType == 5) {
                statisticsPosition = 3;
            }
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "b322", mProduct.mName, statisticsPosition, null, PageIdConstant.vipPage, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE);
            if (PreferencesManager.getInstance().isLogin()) {
                requestOrder(mProduct);
            } else {
                HongKongLoginWebview.launch(this.mContext);
            }
        } else {
            ToastUtils.showToast(this.mContext, 2131100493);
        }
    }

    private void requestOrder(ProductBean productBean) {
        LogInfo.log("LetvVipActivity", "requestOrder start == " + PayCenterApi.getInstance().requestHongkongOrder(productBean.mMonthType + "", "9", productBean.getNormalPrice() + "", productBean.mName, productBean.mVipDesc, "808", VipProductContant.ACTION_HONGKONG_VIP_PAY));
        new LetvRequest(HongkongOrderBean.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setCache(new VolleyNoCache()).setUrl(PayCenterApi.getInstance().requestHongkongOrder(productBean.mMonthType + "", "9", productBean.getNormalPrice() + "", productBean.mName, productBean.mVipDesc, "808", VipProductContant.ACTION_HONGKONG_VIP_PAY)).setParser(new HongkongOrderParser()).setCallback(new 3(this, productBean)).add();
    }
}
