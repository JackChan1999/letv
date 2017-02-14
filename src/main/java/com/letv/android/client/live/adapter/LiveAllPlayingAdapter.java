package com.letv.android.client.live.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.android.client.commonlib.adapter.ViewHolder;
import com.letv.android.client.live.utils.LiveUtils;
import com.letv.android.client.utils.LiveLaunchUtils;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LiveAllPlayingAdapter extends LetvBaseAdapter<LiveRemenBaseBean> implements OnScrollListener {
    boolean mIsScroll;

    public LiveAllPlayingAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mIsScroll = false;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(this.mContext, convertView, R.layout.live_allplaying_item);
        TextView title = (TextView) viewHolder.getView(R.id.live_allplaying_item_title);
        TextView channel = (TextView) viewHolder.getView(R.id.live_allplaying_item_channel);
        TextView userCount = (TextView) viewHolder.getView(R.id.live_allplaying_item_user_count);
        ImageView icon = (ImageView) viewHolder.getView(R.id.live_allplaying_item_icon);
        ImageView coverView = (ImageView) viewHolder.getView(R.id.live_allplaying_item_cover);
        TextView vipTag = (TextView) viewHolder.getView(R.id.tag);
        coverView.getLayoutParams().height = (UIsUtils.getScreenWidth() * LeMessageIds.MSG_MAIN_GO_TO_LIVE_ROOM) / 355;
        LiveRemenBaseBean baseBean = (LiveRemenBaseBean) getItem(position);
        if (baseBean != null) {
            if (TextUtils.equals(baseBean.ch, LiveRoomConstant.LIVE_TYPE_SPORT)) {
                title.setText(TextUtils.equals(baseBean.isVS, "1") ? baseBean.level2 + baseBean.home + "VS" + baseBean.guest : baseBean.title);
            } else {
                title.setText(baseBean.title);
            }
            channel.setText(baseBean.getChannelName(this.mContext.getString(2131099817)));
            vipTag.setVisibility(8);
            if (baseBean.vipFree.equals("1")) {
                vipTag.setVisibility(0);
                vipTag.setText(this.mContext.getString(2131101108));
            } else if ("1".equals(baseBean.isPay)) {
                vipTag.setVisibility(0);
                vipTag.setText(this.mContext.getString(2131100551));
            }
            userCount.setText(String.valueOf(baseBean.userOnlineNum > 0 ? baseBean.userOnlineNum : 0));
            ImageDownloader.getInstance().download(coverView, baseBean.focusPic, 2130838798, ScaleType.FIT_XY, !this.mIsScroll, true);
            icon.setImageResource(LiveUtils.getIconByChannelType(baseBean.ch));
            coverView.setOnClickListener(new 1(this, baseBean));
        }
        return viewHolder.getConvertView();
    }

    private void doItemClick(LiveRemenBaseBean baseBean) {
        String liveType = baseBean.liveType;
        if (!TextUtils.isEmpty(liveType) && liveType.startsWith("ent")) {
            liveType = "ent";
        }
        boolean isPay = "1".equals(baseBean.isPay);
        String liveId = baseBean.id;
        if (baseBean.isPanoramicView == 1) {
            LiveLaunchUtils.lauchPanoramaVideo(this.mContext, baseBean.id, false, VideoType.Panorama);
        } else {
            LiveLaunchUtils.launchFocusPicLive(this.mContext, 0, liveType, "", "", isPay, liveId, baseBean.allowVote, baseBean.partId);
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == 0) {
            this.mIsScroll = false;
            notifyDataSetChanged();
        } else if (scrollState == 1) {
            this.mIsScroll = true;
        } else if (scrollState == 2) {
            this.mIsScroll = true;
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
}
