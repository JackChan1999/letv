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

public class LiveRemenHkChannelAdapter extends Adapter {
    private Context mContext;

    public LiveRemenHkChannelAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mContext = context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(this, LayoutInflater.from(this.mContext).inflate(R.layout.item_live_remen_hk_channel, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ViewHolder.access$000(viewHolder).setText(LiveUtils.hk_names[position]);
        ViewHolder.access$100(viewHolder).setImageResource(LiveUtils.hk_icons[position]);
        if (position == getItemCount() - 1) {
            ViewHolder.access$200(viewHolder).setVisibility(8);
        } else {
            ViewHolder.access$200(viewHolder).setVisibility(0);
        }
    }

    public int getItemCount() {
        return LiveUtils.hk_names.length;
    }
}
