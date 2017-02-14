package com.letv.android.client.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.core.bean.MyElectronicTicketBean.ElectronicTicket;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.StringUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class ElectronicTicketAdapter extends LetvBaseAdapter<ElectronicTicket> {
    private final int EXPIRED_NARMAL_TICKET;
    private final int USED_NORMAL_TICKET;
    private final int USER_TICKETS_LAYOUT;
    private final int USER_TICKETS_USED_OR_EXPIRE_LAYOUT;
    private final int VIP_TICKETS_LAYOUT;
    private Context mContext;
    private int mDefaultLayoutId;

    public ElectronicTicketAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.USER_TICKETS_LAYOUT = 1;
        this.VIP_TICKETS_LAYOUT = 2;
        this.USER_TICKETS_USED_OR_EXPIRE_LAYOUT = 3;
        this.mDefaultLayoutId = 1;
        this.USED_NORMAL_TICKET = 2;
        this.EXPIRED_NARMAL_TICKET = 1;
        this.mContext = context;
    }

    public int getItemViewType(int position) {
        ElectronicTicket mElectronicTicket = (ElectronicTicket) this.mList.get(position);
        if (mElectronicTicket.type != 0) {
            switch (mElectronicTicket.subType) {
                case 0:
                    this.mDefaultLayoutId = 1;
                    break;
                default:
                    this.mDefaultLayoutId = 3;
                    break;
            }
        }
        this.mDefaultLayoutId = 2;
        return this.mDefaultLayoutId;
    }

    public int getViewTypeCount() {
        return 4;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TicketViewHolder holder = null;
        if (convertView != null) {
            switch (getItemViewType(position)) {
                case 1:
                    holder = (TicketViewHolder) convertView.getTag();
                    break;
                case 2:
                    holder = (TicketViewHolder) convertView.getTag();
                    break;
                case 3:
                    holder = (TicketViewHolder) convertView.getTag();
                    break;
                default:
                    break;
            }
        }
        holder = new TicketViewHolder(this);
        switch (getItemViewType(position)) {
            case 1:
                convertView = PublicLoadLayout.inflate(this.mContext, R.layout.user_tickets_item_layout, null);
                initUI(holder, convertView);
                convertView.setTag(holder);
                break;
            case 2:
                convertView = PublicLoadLayout.inflate(this.mContext, R.layout.vip_tickets_item_layout, null);
                holder.mCurrentTicketNumTv = (TextView) convertView.findViewById(R.id.current_ticket_number);
                holder.mTotalTicketNumTv = (TextView) convertView.findViewById(R.id.total_ticket_number);
                initUI(holder, convertView);
                convertView.setTag(holder);
                break;
            case 3:
                convertView = PublicLoadLayout.inflate(this.mContext, R.layout.user_tickets_used_item_layout, null);
                holder.mImageView = (ImageView) convertView.findViewById(R.id.ticket_iv);
                initUI(holder, convertView);
                convertView.setTag(holder);
                break;
        }
        updateUI(holder, getItemViewType(position), position);
        return convertView;
    }

    private void initUI(TicketViewHolder viewHolder, View convertView) {
        viewHolder.mSeeMovieOrBuyVipTv = (TextView) convertView.findViewById(R.id.see_movie_soon);
        viewHolder.mTicketNameTv = (TextView) convertView.findViewById(R.id.ticket_name);
        viewHolder.mTicketFrom = (TextView) convertView.findViewById(R.id.ticket_from);
        viewHolder.mTicketDeadlineTv = (TextView) convertView.findViewById(R.id.ticket_deadline);
        viewHolder.mTicketDesTv = (TextView) convertView.findViewById(R.id.ticket_des);
    }

    private void updateUI(TicketViewHolder viewHolder, int type, int position) {
        ElectronicTicket mElectronicTicket = (ElectronicTicket) this.mList.get(position);
        viewHolder.mTicketNameTv.setText(mElectronicTicket.name);
        viewHolder.mTicketFrom.setText(this.mContext.getString(2131100962, new Object[]{mElectronicTicket.from}));
        if (mElectronicTicket.cancelTime != 0) {
            viewHolder.mTicketDeadlineTv.setText(this.mContext.getString(2131100961, new Object[]{StringUtils.timeString(mElectronicTicket.cancelTime)}));
            viewHolder.mTicketDeadlineTv.setVisibility(0);
        } else {
            viewHolder.mTicketDeadlineTv.setVisibility(8);
        }
        viewHolder.mTicketDesTv.setText(mElectronicTicket.desc);
        switch (type) {
            case 2:
                if (!PreferencesManager.getInstance().isVip()) {
                    viewHolder.mSeeMovieOrBuyVipTv.setText(this.mContext.getString(2131100959));
                    viewHolder.mTotalTicketNumTv.setText(this.mContext.getString(2131100518));
                } else if (mElectronicTicket.totalNum == -1) {
                    viewHolder.mSeeMovieOrBuyVipTv.setText(this.mContext.getString(2131100960));
                    viewHolder.mTotalTicketNumTv.setText(this.mContext.getString(2131100518));
                } else {
                    viewHolder.mSeeMovieOrBuyVipTv.setText(this.mContext.getString(2131100965));
                    viewHolder.mTotalTicketNumTv.setText(this.mContext.getString(2131100968, new Object[]{Integer.valueOf(mElectronicTicket.totalNum)}));
                }
                viewHolder.mCurrentTicketNumTv.setText(mElectronicTicket.currentNum + "");
                return;
            case 3:
                if (mElectronicTicket.subType == 2) {
                    viewHolder.mImageView.setBackgroundResource(2130839041);
                    return;
                } else if (mElectronicTicket.subType == 1) {
                    viewHolder.mImageView.setBackgroundResource(2130839040);
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }
}
