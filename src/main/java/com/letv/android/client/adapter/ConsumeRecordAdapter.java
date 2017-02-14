package com.letv.android.client.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.core.bean.SaleNoteBean;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StringUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class ConsumeRecordAdapter extends LetvBaseAdapter<SaleNoteBean> {
    public ConsumeRecordAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(this);
            convertView = PublicLoadLayout.inflate(this.mContext, R.layout.consume_record_item, null);
            holder.mConsumeName = (TextView) convertView.findViewById(R.id.consume_name);
            holder.mConsumePrice = (TextView) convertView.findViewById(R.id.consume_price);
            holder.mConsumeExpire = (TextView) convertView.findViewById(R.id.consume_expire);
            holder.mConsumeOrderId = (TextView) convertView.findViewById(R.id.consume_order_id);
            holder.mConsumeBuyTime = (TextView) convertView.findViewById(R.id.consume_buy_time);
            holder.mConsumeOrderStatus = (TextView) convertView.findViewById(R.id.consume_order_status);
            holder.mLine = convertView.findViewById(R.id.consume_bottom_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mLine.setVisibility(position == getCount() + -1 ? 0 : 8);
        setDataForView(holder, position);
        return convertView;
    }

    private void setDataForView(ViewHolder holder, int position) {
        SaleNoteBean note = (SaleNoteBean) this.mList.get(position);
        String payType = note.payType;
        String textPrice = "";
        Resources res = this.mContext.getResources();
        if (!TextUtils.isEmpty(payType)) {
            if (payType.equals("1")) {
                textPrice = res.getString(2131099895) + StringUtils.subZeroAndDot(note.money) + note.moneyDes;
            } else if (payType.equals("2")) {
                textPrice = res.getString(2131099897) + StringUtils.subZeroAndDot(note.money) + note.moneyDes;
            } else if (payType.equals("3")) {
                textPrice = res.getString(2131099896);
            } else if (payType.equals("4")) {
                textPrice = res.getString(2131099898);
            }
            LogInfo.log("GLH", "textPrice.length()" + textPrice.length() + ",textPrice=" + textPrice);
        }
        long expire_time = 0;
        long buy_time = 0;
        if (!(TextUtils.isEmpty(note.cancelTime) || note.cancelTime.trim().equals("0"))) {
            expire_time = Long.valueOf(note.cancelTime.trim()).longValue();
        }
        if (!(TextUtils.isEmpty(note.addTime) || note.addTime.trim().equals("0"))) {
            buy_time = Long.valueOf(note.addTime.trim()).longValue();
        }
        holder.mConsumeName.setText(this.mContext.getResources().getString(2131100610, new Object[]{note.orderName}));
        SpannableStringBuilder style = new SpannableStringBuilder(this.mContext.getResources().getString(2131100613, new Object[]{textPrice}));
        style.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(2131493337)), 5, style.length(), 34);
        holder.mConsumePrice.setText(style);
        if (expire_time != 0) {
            holder.mConsumeExpire.setVisibility(0);
            holder.mConsumeExpire.setText(this.mContext.getResources().getString(2131100609, new Object[]{StringUtils.timeStringByMinutes(expire_time)}));
        } else {
            holder.mConsumeExpire.setVisibility(8);
        }
        holder.mConsumeOrderId.setText(this.mContext.getResources().getString(2131100611, new Object[]{note.id}));
        if (buy_time != 0) {
            holder.mConsumeBuyTime.setVisibility(0);
            holder.mConsumeBuyTime.setText(this.mContext.getResources().getString(2131100608, new Object[]{StringUtils.timeStringByMinutes(buy_time)}));
        } else {
            holder.mConsumeBuyTime.setVisibility(8);
        }
        if (note.status.equals("1")) {
            holder.mConsumeOrderStatus.setText(this.mContext.getResources().getString(2131100612, new Object[]{res.getString(2131100902)}));
            return;
        }
        holder.mConsumeOrderStatus.setText(this.mContext.getResources().getString(2131100612, new Object[]{res.getString(2131100523)}));
    }
}
