package com.letv.android.client.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.core.bean.PaymentMethodBean.PaymentMethod;
import com.letv.core.download.image.ImageDownloader;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class OrderDetailPayKindAdapter extends LetvBaseAdapter<PaymentMethod> {
    private Context mContext;
    private boolean[] mFlag;
    private int mLedianNumber;

    public OrderDetailPayKindAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mFlag = new boolean[4];
        this.mContext = context;
    }

    public void initFlag() {
        for (int i = 0; i < 4; i++) {
            this.mFlag[i] = false;
        }
    }

    public void changeFlag(int positon) {
        this.mFlag[positon] = true;
        notifyDataSetChanged();
    }

    public void setLedianNumber(int ledianNum) {
        this.mLedianNumber = ledianNum;
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(this);
            convertView = PublicLoadLayout.inflate(this.mContext, R.layout.pay_kind_listview_item, null);
            holder.mPayMethodImageView = (ImageView) convertView.findViewById(R.id.pay_kind_imageview);
            holder.mPayMethodNameTv = (TextView) convertView.findViewById(R.id.pay_kind_name_tv);
            holder.mPayMethodDescriptionTv = (TextView) convertView.findViewById(R.id.pay_kind_description_tv);
            holder.mPayMethodSeletedBtn = (Button) convertView.findViewById(R.id.pay_kind_seleted_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setDataForView(holder, position);
        return convertView;
    }

    private void setDataForView(ViewHolder holder, int position) {
        PaymentMethod mProductList = (PaymentMethod) this.mList.get(position);
        ImageDownloader.getInstance().download(holder.mPayMethodImageView, mProductList.logoUrl);
        holder.mPayMethodNameTv.setText(mProductList.title);
        holder.mPayMethodDescriptionTv.setText(mProductList.subTitle);
        if (this.mFlag[position]) {
            holder.mPayMethodSeletedBtn.setVisibility(0);
        } else {
            holder.mPayMethodSeletedBtn.setVisibility(8);
        }
        if ("0".equals(mProductList.activityStatus)) {
            holder.mPayMethodDescriptionTv.setTextColor(this.mContext.getResources().getColor(2131493280));
        } else {
            holder.mPayMethodDescriptionTv.setTextColor(this.mContext.getResources().getColor(2131493192));
        }
        if (mProductList.title.contains(this.mContext.getString(2131100233))) {
            holder.mPayMethodDescriptionTv.setText(this.mContext.getString(2131099769) + this.mLedianNumber + this.mContext.getString(2131100234));
        }
    }
}
