package com.letv.android.client.live.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.letv.android.client.R;
import com.letv.android.client.live.utils.LiveUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LiveRemenChannelAdapter extends Adapter {
    private Context mContext;

    public LiveRemenChannelAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mContext = context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(this, LayoutInflater.from(this.mContext).inflate(R.layout.item_live_remen_channel, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ViewHolder.access$000(viewHolder).setText(LiveUtils.names[position]);
        ViewHolder.access$100(viewHolder).setImageResource(LiveUtils.icons[position]);
        if (position % 2 == 0) {
            ViewHolder.access$200(viewHolder).setVisibility(8);
            if (position == 0) {
                ViewHolder.access$300(viewHolder).setVisibility(0);
                ViewHolder.access$400(viewHolder).setVisibility(8);
                ViewHolder.access$500(viewHolder).setVisibility(8);
                ViewHolder.access$600(viewHolder).setVisibility(0);
                return;
            } else if (position == 10) {
                ViewHolder.access$300(viewHolder).setVisibility(8);
                ViewHolder.access$400(viewHolder).setVisibility(8);
                ViewHolder.access$500(viewHolder).setVisibility(0);
                ViewHolder.access$600(viewHolder).setVisibility(8);
                return;
            } else {
                ViewHolder.access$300(viewHolder).setVisibility(0);
                ViewHolder.access$400(viewHolder).setVisibility(0);
                ViewHolder.access$500(viewHolder).setVisibility(8);
                ViewHolder.access$600(viewHolder).setVisibility(8);
                return;
            }
        }
        ViewHolder.access$400(viewHolder).setVisibility(8);
        ViewHolder.access$500(viewHolder).setVisibility(8);
        ViewHolder.access$600(viewHolder).setVisibility(8);
        ViewHolder.access$300(viewHolder).setVisibility(8);
        ViewHolder.access$200(viewHolder).setVisibility(0);
    }

    public int getItemCount() {
        return LiveUtils.names.length;
    }
}
