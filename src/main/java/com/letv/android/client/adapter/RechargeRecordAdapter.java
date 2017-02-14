package com.letv.android.client.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.android.client.utils.UIs;
import com.letv.core.BaseApplication;
import com.letv.core.bean.RechargeRecordBean;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RechargeRecordAdapter extends LetvBaseAdapter<RechargeRecordBean> {

    final class ViewHolder {
        TextView mDateTv;
        TextView mLeDianTv;
        View mLineView;
        TextView mPriceTv;
        TextView mTypeTv;
        final /* synthetic */ RechargeRecordAdapter this$0;

        ViewHolder(RechargeRecordAdapter this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
        }
    }

    public RechargeRecordAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(this);
            convertView = UIs.inflate(this.mContext, (int) R.layout.recharge_item, null, false);
            holder.mLeDianTv = (TextView) convertView.findViewById(R.id.ledian);
            holder.mPriceTv = (TextView) convertView.findViewById(R.id.price);
            holder.mTypeTv = (TextView) convertView.findViewById(R.id.type);
            holder.mDateTv = (TextView) convertView.findViewById(R.id.date);
            holder.mLineView = convertView.findViewById(R.id.recharge_bottom_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mLineView.setVisibility(position == getCount() + -1 ? 0 : 8);
        setDataForView(holder, position);
        return convertView;
    }

    private void setDataForView(ViewHolder holder, int position) {
        RechargeRecordBean record = (RechargeRecordBean) this.mList.get(position);
        Resources resource = this.mContext.getResources();
        holder.mLeDianTv.setText(resource.getString(2131100632, new Object[]{record.point}));
        holder.mPriceTv.setText(resource.getString(2131100633, new Object[]{"￥" + String.valueOf(Double.parseDouble(record.money) / 100.0d)}));
        holder.mTypeTv.setText(resource.getString(2131100635, new Object[]{getChargetype(record.chargetype)}));
        holder.mDateTv.setText(resource.getString(2131100631, new Object[]{record.chargetime}));
    }

    private String getChargetype(String str) {
        if (str.equals("sj")) {
            return getString(2131100731);
        }
        if (str.equals("wy")) {
            return getString(2131100734);
        }
        if (str.equals("lk")) {
            return getString(2131100730);
        }
        if (str.equals("sjczk")) {
            return getString(2131100732);
        }
        if (str.equals("zfb")) {
            return getString(2131100737);
        }
        if (str.equals("syt")) {
            return getString(2131100733);
        }
        if (str.equals("xyk")) {
            return getString(2131100736);
        }
        if (str.equals("jjk")) {
            return getString(2131100729);
        }
        if (str.equals("hb")) {
            return getString(2131100728);
        }
        if (str.equals("zjcz")) {
            return getString(2131100738);
        }
        if (str.equals("czfb")) {
            return getString(2131100727);
        }
        if (str.equals("wzfb")) {
            return getString(2131100735);
        }
        return "";
    }

    public String getString(int id) {
        return BaseApplication.getInstance().getString(id);
    }
}
