package com.letv.android.client.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.LetvLoginActivity;
import com.letv.android.client.activity.VipOrderDetailActivity;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.core.bean.VipProductBean.ProductBean;
import com.letv.core.constant.AlipayConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.sina.weibo.sdk.component.ShareRequestParam;

public class VipPackageAdapter extends LetvBaseAdapter<ProductBean> {
    private final int THREE_MONTH_TYPE;
    private final int YEAR_MONTH_TYPE;
    private Activity mContext;
    private int mIsOnKeySignWithAlipay;
    private boolean mMobileVipFlag;
    private ProductBean mSipToOrderProductList;
    private String mVipKind;

    public VipPackageAdapter(Activity context, String vipKind) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mIsOnKeySignWithAlipay = -1;
        this.mMobileVipFlag = true;
        this.YEAR_MONTH_TYPE = 5;
        this.THREE_MONTH_TYPE = 3;
        this.mContext = context;
        this.mVipKind = vipKind;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(this);
            convertView = PublicLoadLayout.inflate(this.mContext, R.layout.vip_package_item, null);
            holder.mVipPackageNameTv = (TextView) convertView.findViewById(R.id.vip_package_name_tv);
            holder.mVipPackageDiscountTv = (TextView) convertView.findViewById(R.id.vip_package_discount_tv);
            holder.mVipPackageDescriptionTv = (TextView) convertView.findViewById(R.id.vip_package_description_tv);
            holder.mVipPackagePriceBtn = (Button) convertView.findViewById(R.id.vip_package_price_btn);
            holder.mOneMonthPriceTv = (TextView) convertView.findViewById(R.id.vip_package_one_month_price_tv);
            holder.mVipPackageItemLayout = (RelativeLayout) convertView.findViewById(R.id.vip_package_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setDataForView(holder, position);
        return convertView;
    }

    public void setIsOnKeySignWithAlipay(int isOnKeySignWithAlipay) {
        this.mIsOnKeySignWithAlipay = isOnKeySignWithAlipay;
    }

    public void setMobileVipFlag(boolean isFlag) {
        this.mMobileVipFlag = isFlag;
    }

    private void setDataForView(ViewHolder holder, int position) {
        ProductBean mProductList = (ProductBean) this.mList.get(position);
        holder.mVipPackageNameTv.setText(mProductList.mExpire);
        if (5 == mProductList.mMonthType || 3 == mProductList.mMonthType) {
            holder.mOneMonthPriceTv.setVisibility(0);
            holder.mOneMonthPriceTv.setText(this.mContext.getString(2131100579, new Object[]{Integer.valueOf((int) (mProductList.getNormalPrice() / ((float) (mProductList.mDays / 31))))}));
        } else {
            holder.mOneMonthPriceTv.setVisibility(8);
        }
        if (TextUtils.isEmpty(mProductList.mLable)) {
            holder.mVipPackageDiscountTv.setVisibility(8);
        } else {
            holder.mVipPackageDiscountTv.setVisibility(0);
            holder.mVipPackageDiscountTv.setText(mProductList.mLable);
        }
        if (!TextUtils.isEmpty(mProductList.mPackageText) || mProductList.leftQuota > 0) {
            holder.mVipPackageDescriptionTv.setVisibility(0);
            if (mProductList.leftQuota > 0) {
                holder.mVipPackageDescriptionTv.setText(mProductList.mPackageText + (TextUtils.isEmpty(mProductList.mPackageText) ? "" : ",") + this.mContext.getString(2131100578, new Object[]{Integer.valueOf(mProductList.leftQuota)}));
            } else {
                holder.mVipPackageDescriptionTv.setText(mProductList.mPackageText);
            }
        } else {
            holder.mVipPackageDescriptionTv.setVisibility(8);
        }
        holder.mVipPackagePriceBtn.setText("¥" + LetvUtils.formatDoubleNum(Double.valueOf((double) mProductList.getNormalPrice()).doubleValue(), 2));
        holder.mVipPackageItemLayout.setOnClickListener(new 1(this, mProductList));
        holder.mVipPackagePriceBtn.setOnClickListener(new 2(this, mProductList));
    }

    private void itemClick(ProductBean mProductList) {
        if (NetworkUtils.isNetworkAvailable()) {
            int statisticsPosition = 1;
            String statisticsType = "b321";
            if (mProductList.mMonthType == 2) {
                statisticsPosition = 1;
            } else if (mProductList.mMonthType == 3) {
                statisticsPosition = 2;
            } else if (mProductList.mMonthType == 5) {
                statisticsPosition = 3;
            } else if (mProductList.mMonthType == 42) {
                statisticsPosition = 4;
            }
            if (!"1".equals(this.mVipKind)) {
                statisticsType = "b322";
            } else if (statisticsPosition == 4) {
                statisticsType = "b31";
            } else {
                statisticsType = "b321";
            }
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", statisticsType, mProductList.mName, statisticsPosition, null, PageIdConstant.vipPage, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE);
            if (PreferencesManager.getInstance().isLogin()) {
                skipToOrder(mProductList);
                return;
            }
            setSipToOrderProductList(mProductList);
            LetvLoginActivity.launch(this.mContext);
            return;
        }
        ToastUtils.showToast(this.mContext, 2131100493);
    }

    public void skipToOrder(ProductBean mProductList) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("product_bean", mProductList);
        bundle.putString("vip_kind", this.mVipKind);
        intent.putExtra(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA, bundle);
        if (mProductList.mMonthType == 42 || mProductList.mMonthType == 52) {
            intent.putExtra(AlipayConstant.IS_ONE_KEY_SIGN_PAY_WITH_ALIPAY, this.mIsOnKeySignWithAlipay);
            intent.putExtra(AlipayConstant.IS_MOBILE_VIP_FLAG, this.mMobileVipFlag);
        }
        intent.putExtra("vip_kind", this.mVipKind);
        intent.setClass(this.mContext, VipOrderDetailActivity.class);
        this.mContext.startActivityForResult(intent, 0);
    }

    public ProductBean getSipToOrderProductList() {
        return this.mSipToOrderProductList;
    }

    public void setSipToOrderProductList(ProductBean mSipToOrderProductList) {
        this.mSipToOrderProductList = mSipToOrderProductList;
    }
}
