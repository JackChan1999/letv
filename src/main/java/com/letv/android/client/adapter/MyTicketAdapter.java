package com.letv.android.client.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.core.bean.TicketShowListBean.TicketShow;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StringUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MyTicketAdapter extends LetvBaseAdapter<TicketShow> {
    private Context mContext;

    public MyTicketAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mContext = null;
        this.mContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(this);
            convertView = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.ticketshow_list_item, null);
            holder.mName = (TextView) convertView.findViewById(R.id.ticket_name_source);
            holder.mUsed = (TextView) convertView.findViewById(R.id.ticket_used);
            holder.mExpired = (TextView) convertView.findViewById(R.id.ticket_expired);
            holder.mUseNowBtn = (Button) convertView.findViewById(R.id.ticket_use_now);
            holder.mLineView = convertView.findViewById(R.id.ticket_bottom_line);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mLineView.setVisibility(position == getCount() + -1 ? 0 : 8);
        setData(holder, (TicketShow) this.mList.get(position));
        convertView.setTag(holder);
        return convertView;
    }

    private void setData(ViewHolder holder, TicketShow ticket) {
        LogInfo.log("zlb", "getView setData ticket = " + ticket + " , size = " + this.mList.size());
        if (ticket != null) {
            String name = ticket.ticketName;
            String source = ticket.ticketSource;
            if (TextUtils.isEmpty(source)) {
                source = "";
            } else {
                source = "(" + source + ")";
            }
            String usedNum = ticket.usedNumber;
            String totalNum = ticket.totalNumber;
            String expiredTime = ticket.endTime;
            holder.mName.setText(name + source);
            holder.mUsed.setText(this.mContext.getString(2131099755, new Object[]{usedNum + "/" + totalNum}));
            if (!TextUtils.isEmpty(expiredTime)) {
                holder.mExpired.setText(this.mContext.getString(2131099921) + StringUtils.timeString(Long.parseLong(expiredTime)));
            }
            if (!ticket.isExpired.equals("0") || usedNum.equals(totalNum)) {
                holder.mUseNowBtn.setVisibility(8);
                holder.mName.setTextColor(this.mContext.getResources().getColor(2131493280));
                return;
            }
            holder.mUseNowBtn.setVisibility(0);
            holder.mUseNowBtn.setOnClickListener(new 1(this));
            holder.mName.setTextColor(this.mContext.getResources().getColor(2131493237));
        }
    }
}
