package com.letv.android.client.live.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.HashMap;

public class LiveRemenLivePagerAdapter extends PagerAdapter {
    public static final int COUNT = 1000;
    private Context mContext;
    private ArrayList mData;
    ViewHolder mViewHolder;
    private HashMap<Integer, View> mViews;

    public class ViewHolder {
        public ImageView mIcon;
        public LinearLayout mLayoutTitle;
        public RelativeLayout mLayoutTop;
        public RelativeLayout mLayoutVideoview;
        public TextView mTvPeople;
        public TextView mTvTitle;
        final /* synthetic */ LiveRemenLivePagerAdapter this$0;

        public ViewHolder(LiveRemenLivePagerAdapter this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
        }
    }

    public LiveRemenLivePagerAdapter(Context context, ArrayList data) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mContext = context;
        this.mData = data;
        this.mViews = new HashMap();
    }

    public int getCount() {
        return (this.mData == null || this.mData.size() <= 0) ? 0 : 1000;
    }

    public View getViewByIndex(int position) {
        if (this.mViews == null) {
            return null;
        }
        return (View) this.mViews.get(Integer.valueOf(position));
    }

    public void destroy() {
        if (this.mViews != null) {
            this.mViews.clear();
        }
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        LogInfo.log("pjf", "instantiateItem " + position);
        View view = getView(null, container, position);
        this.mViews.put(Integer.valueOf(position), view);
        container.addView(view);
        RxBus.getInstance().send(new InstantiateItemEvent(position));
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        this.mViews.remove(Integer.valueOf(position));
        container.removeView(view);
    }

    private View getView(View view, ViewGroup parent, int position) {
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.item_live_remen_live, parent, false);
            this.mViewHolder = new ViewHolder(this);
            this.mViewHolder.mIcon = (ImageView) view.findViewById(R.id.item_live_remen_live_icon);
            this.mViewHolder.mLayoutTop = (RelativeLayout) view.findViewById(R.id.item_live_remen_live_layout_top);
            this.mViewHolder.mLayoutVideoview = (RelativeLayout) view.findViewById(R.id.item_live_remen_live_layout_videoview);
            this.mViewHolder.mTvTitle = (TextView) view.findViewById(R.id.item_live_remen_live_tv_title);
            this.mViewHolder.mLayoutTitle = (LinearLayout) view.findViewById(R.id.item_live_remen_live_layout_title);
            this.mViewHolder.mTvPeople = (TextView) view.findViewById(R.id.item_live_remen_live_tv_people);
            view.setTag(this.mViewHolder);
        } else {
            this.mViewHolder = (ViewHolder) view.getTag();
        }
        int width = UIsUtils.getScreenWidth() - UIsUtils.dipToPx(40.0f);
        view.setLayoutParams(new LayoutParams(width, (width * 240) / 320));
        int height = (width * 180) / 320;
        LayoutParams params = new LayoutParams(width, height);
        this.mViewHolder.mIcon.setLayoutParams(params);
        this.mViewHolder.mLayoutVideoview.setLayoutParams(params);
        params = (LayoutParams) this.mViewHolder.mLayoutTitle.getLayoutParams();
        params.setMargins(0, height, 0, 0);
        height = (width * 60) / 320;
        params.width = width;
        params.height = height;
        this.mViewHolder.mLayoutTitle.setLayoutParams(params);
        LetvBaseBean baseBean = (LetvBaseBean) this.mData.get(position % this.mData.size());
        if (baseBean instanceof LiveBeanLeChannel) {
            LiveBeanLeChannel channel = (LiveBeanLeChannel) baseBean;
            ImageDownloader.getInstance().download(this.mViewHolder.mIcon, channel.channelIcon, 2130838798, ScaleType.FIT_XY);
            this.mViewHolder.mTvTitle.setText(channel.channelName);
        } else {
            LiveRemenBaseBean mBaseBean = (LiveRemenBaseBean) baseBean;
            ImageDownloader.getInstance().download(this.mViewHolder.mIcon, mBaseBean.focusPic, 2130838798, ScaleType.FIT_XY);
            this.mViewHolder.mTvTitle.setText(mBaseBean.title);
        }
        this.mViewHolder.mLayoutTitle.setOnClickListener(new 1(this, baseBean));
        return view;
    }
}
