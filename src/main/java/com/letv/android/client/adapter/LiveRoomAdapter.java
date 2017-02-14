package com.letv.android.client.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.android.client.commonlib.adapter.ViewHolder;
import com.letv.business.flow.live.LiveBookCallback;
import com.letv.business.flow.star.StarBookCallback;
import com.letv.component.utils.MD5;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.bean.LiveRemenTagBean;
import com.letv.core.bean.TipMapBean.TipBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.HashSet;
import java.util.Set;

public class LiveRoomAdapter extends LetvBaseAdapter<LiveRemenBaseBean> implements LiveBookCallback {
    private static final String TAG = "LiveRoomAdapter";
    private static final int VIEW_TYPE_COUNT = 6;
    private static final int VIEW_TYPE_HOME_CHANNEL = 5;
    private static final int VIEW_TYPE_LUNBO = 3;
    private static final int VIEW_TYPE_MUSIC_ENT = 2;
    private static final int VIEW_TYPE_SPORT = 1;
    private static final int VIEW_TYPE_TAG = 0;
    private static final int VIEW_TYPE_WEISHI = 4;
    private boolean isRemenTab;
    private Set<String> mBookedPrograms;
    private Context mContext;
    private int mFrom;
    private LayoutInflater mInflater;
    private int mSequence;
    private StarBookCallback mStarBookCallback;
    private int mTabIndex;

    public LiveRoomAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mFrom = 0;
        this.mTabIndex = 0;
        this.mBookedPrograms = new HashSet();
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public void setSequence(int sequence) {
        this.mSequence = sequence;
    }

    public void setFrom(int from) {
        this.mFrom = from;
    }

    public void setmTabIndex(int index) {
        this.mTabIndex = index;
    }

    public void setStarBookCallback(StarBookCallback starBookCallback) {
        this.mStarBookCallback = starBookCallback;
    }

    public void setBookedPrograms(Set<String> bookedPrograms) {
        this.mBookedPrograms.clear();
        if (!(bookedPrograms == null || bookedPrograms.isEmpty())) {
            this.mBookedPrograms.addAll(bookedPrograms);
        }
        notifyDataSetChanged();
    }

    public void setIsRemenTab(boolean isRemenTab) {
        this.isRemenTab = isRemenTab;
    }

    public int getViewTypeCount() {
        return 6;
    }

    private void drawItemView(LiveViewHolder viewHolder, LiveRemenBaseBean bean) {
        viewHolder.mYCPayFlag.setVisibility(8);
        viewHolder.mName.setText(bean.getName1());
        String hostName = bean.home;
        if (hostName != null && hostName.length() > 4) {
            viewHolder.mHostName.setGravity(19);
            if (hostName != null && hostName.length() > 8) {
                hostName = hostName.substring(0, 7) + "...";
            }
        }
        String guestName = bean.guest;
        if (guestName != null) {
            if (guestName.length() > 4) {
                viewHolder.mGuestName.setGravity(21);
                if (guestName.length() > 8) {
                    guestName = guestName.substring(0, 7) + "...";
                }
            } else {
                viewHolder.mGuestName.setGravity(21);
            }
        }
        viewHolder.mHostName.setText(hostName);
        viewHolder.mGuestName.setText(guestName);
        viewHolder.mVsFlag.setText(bean.getName2());
        viewHolder.mOperation.setEnabled(false);
        if (!TextUtils.isEmpty(bean.homeImgUrl)) {
            ImageDownloader.getInstance().download(viewHolder.mHostIcon, bean.homeImgUrl);
        }
        if (!TextUtils.isEmpty(bean.guestImgUrl)) {
            ImageDownloader.getInstance().download(viewHolder.mGuestIcon, bean.guestImgUrl);
        }
    }

    private void drawView(LiveViewHolder mViewHolder, LiveRemenBaseBean bean, int position) {
        String name2;
        if (getItemViewType(position) == 5) {
            name2 = bean.title;
        } else {
            name2 = bean.getName2();
        }
        mViewHolder.mYCPayFlag.setVisibility(8);
        mViewHolder.mName.setText(bean.getName1());
        mViewHolder.mName2.setText(Html.fromHtml(name2));
        mViewHolder.mOperation.setEnabled(false);
        if (!TextUtils.isEmpty(bean.typeICON)) {
            ImageDownloader.getInstance().download(mViewHolder.mItemIcon, bean.typeICON);
        }
    }

    public int getItemViewType(int position) {
        LetvBaseBean bean = (LetvBaseBean) getItem(position);
        if (this.mFrom == 2 || this.mFrom == 1) {
            return 5;
        }
        if (bean instanceof LiveRemenTagBean) {
            return 0;
        }
        if (bean instanceof LiveRemenBaseBean) {
            LiveRemenBaseBean remenBean = (LiveRemenBaseBean) bean;
            if (remenBean.ch.equals(LiveRoomConstant.LIVE_TYPE_SPORT) && remenBean.isVS != null && remenBean.isVS.equals("1")) {
                return 1;
            }
            return 2;
        } else if (bean instanceof LiveBeanLeChannel) {
            return 3;
        } else {
            return 4;
        }
    }

    private void setActionVcr(LiveViewHolder viewHolder, LiveRemenBaseBean bean) {
        viewHolder.mOperation.setText(this.mContext.getString(2131100315));
        viewHolder.mOperationIcon.setImageResource(2130838490);
        viewHolder.mOperation.setTextColor(this.mContext.getResources().getColor(2131493105));
    }

    private void setActionSubmit(LiveViewHolder viewHolder, LiveRemenBaseBean bean) {
        viewHolder.mOperation.setTextColor(this.mContext.getResources().getColor(2131493105));
        viewHolder.mOperation.setText(this.mContext.getString(2131100316));
        viewHolder.mOperationIcon.setImageResource(2130838489);
        viewHolder.mOperation.setEnabled(false);
    }

    private void updateWhenPlayOver(LiveViewHolder viewHolder, LiveRemenBaseBean bean) {
        if (TextUtils.isEmpty(bean.recordingId)) {
            setActionSubmit(viewHolder, bean);
        } else {
            setActionVcr(viewHolder, bean);
        }
    }

    private void updateWhenPlayNotStart(LiveViewHolder viewHolder, LiveRemenBaseBean bean) {
        if ("1".equals(bean.isPay)) {
            setActionBuy(viewHolder, bean);
        } else {
            setActionBooked(viewHolder, bean);
        }
    }

    private void updateWhenPlaying(LiveViewHolder viewHolder, LiveRemenBaseBean bean) {
        viewHolder.mOperation.setTextColor(this.mContext.getResources().getColor(2131493202));
        viewHolder.mOperation.setText(this.mContext.getString(2131100317));
        viewHolder.mOperationIcon.setImageResource(2130838486);
        if (bean.vipFree.equals("1")) {
            viewHolder.mYCPayFlag.setVisibility(0);
            viewHolder.mYCPayFlag.setText(this.mContext.getString(2131101108));
        } else if ("1".equals(bean.isPay)) {
            viewHolder.mYCPayFlag.setVisibility(0);
            viewHolder.mYCPayFlag.setText(this.mContext.getString(2131100551));
        }
    }

    private void setActionBuy(LiveViewHolder viewHolder, LiveRemenBaseBean bean) {
        CharSequence string;
        viewHolder.mOperationIcon.setImageResource(2130838485);
        viewHolder.mOperation.setTextColor(this.mContext.getResources().getColor(2131493105));
        TextView textView = viewHolder.mOperation;
        if (bean.authored) {
            string = this.mContext.getString(2131101191);
        } else {
            string = this.mContext.getString(2131101172);
        }
        textView.setText(string);
        if (bean.vipFree.equals("1")) {
            viewHolder.mYCPayFlag.setVisibility(0);
            viewHolder.mYCPayFlag.setText(this.mContext.getString(2131101108));
        } else if ("1".equals(bean.isPay)) {
            viewHolder.mYCPayFlag.setVisibility(0);
            viewHolder.mYCPayFlag.setText(this.mContext.getString(2131100551));
        }
    }

    private void setActionBooked(LiveViewHolder viewHolder, LiveRemenBaseBean bean) {
        viewHolder.mOperation.setTextColor(this.mContext.getResources().getColor(2131493105));
        String programName = bean.title;
        String liveType = bean.liveType;
        if (this.mBookedPrograms.contains(MD5.toMd5(programName + liveType + (bean.getFullPlayDate() + " " + bean.getPlayTime())))) {
            viewHolder.mOperation.setText(this.mContext.getString(2131100326));
            viewHolder.mOperationIcon.setImageResource(2130838484);
            return;
        }
        viewHolder.mOperation.setText(this.mContext.getString(2131100325));
        viewHolder.mOperationIcon.setImageResource(2130838483);
    }

    private void setStatus(LiveViewHolder viewHolder, LiveRemenBaseBean bean) {
        if ("3".equals(bean.status)) {
            updateWhenPlayOver(viewHolder, bean);
        } else if ("2".equals(bean.status)) {
            updateWhenPlaying(viewHolder, bean);
        } else if ("1".equals(bean.status)) {
            updateWhenPlayNotStart(viewHolder, bean);
        } else {
            LogInfo.log(TAG, "unknown play status: " + bean.status);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        long now = System.currentTimeMillis();
        LetvBaseBean bean = (LetvBaseBean) getItem(position);
        int viewType = getItemViewType(position);
        LiveViewHolder liveHolder;
        LiveRemenBaseBean remenBean;
        switch (viewType) {
            case 0:
                if (this.isRemenTab) {
                    holder = ViewHolder.get(this.mContext, convertView, R.layout.live_room_remen_group_tag);
                } else {
                    holder = ViewHolder.get(this.mContext, convertView, R.layout.live_room_group_tag);
                }
                TextView name = (TextView) holder.getView(R.id.live_group_tag);
                LiveRemenTagBean tagBean = (LiveRemenTagBean) bean;
                String tagName = tagBean.tagName;
                if (!(tagBean == null || TextUtils.isEmpty(tagName))) {
                    name.setText(tagBean.tagName);
                    break;
                }
            case 1:
                holder = ViewHolder.get(this.mContext, convertView, R.layout.live_sport_item);
                if (holder.bindObj == null) {
                    liveHolder = new LiveViewHolder();
                    holder.bindObj = liveHolder;
                } else {
                    liveHolder = holder.bindObj;
                }
                liveHolder.mName = (TextView) holder.getView(R.id.name1);
                liveHolder.mHostName = (TextView) holder.getView(R.id.hostName);
                liveHolder.mHostIcon = (ImageView) holder.getView(R.id.hostIcon);
                liveHolder.mGuestIcon = (ImageView) holder.getView(R.id.guestIcon);
                liveHolder.mGuestName = (TextView) holder.getView(R.id.guestName);
                liveHolder.mVsFlag = (TextView) holder.getView(R.id.vs);
                liveHolder.mOperation = (TextView) holder.getView(R.id.operate_btn);
                liveHolder.mOperationIcon = (ImageView) holder.getView(R.id.operate_btn_icon);
                liveHolder.mYCPayFlag = (TextView) holder.getView(R.id.yc_pay_icon);
                remenBean = (LiveRemenBaseBean) bean;
                drawItemView(liveHolder, remenBean);
                setStatus(liveHolder, remenBean);
                break;
            case 2:
                holder = ViewHolder.get(this.mContext, convertView, R.layout.live_ent_or_music_item);
                if (holder.bindObj == null) {
                    liveHolder = new LiveViewHolder();
                    holder.bindObj = liveHolder;
                } else {
                    liveHolder = holder.bindObj;
                }
                liveHolder.mName = (TextView) holder.getView(R.id.name1);
                liveHolder.mName2 = (TextView) holder.getView(R.id.name2);
                liveHolder.mItemIcon = (ImageView) holder.getView(R.id.icon);
                liveHolder.mOperation = (TextView) holder.getView(R.id.operate_btn);
                liveHolder.mOperationIcon = (ImageView) holder.getView(R.id.operate_btn_icon);
                liveHolder.mYCPayFlag = (TextView) holder.getView(R.id.yc_pay_icon);
                remenBean = (LiveRemenBaseBean) bean;
                drawView(liveHolder, remenBean, position);
                setStatus(liveHolder, remenBean);
                break;
            case 5:
                holder = ViewHolder.get(this.mContext, convertView, R.layout.live_home_cms_item);
                if (holder.bindObj == null) {
                    liveHolder = new LiveViewHolder();
                    holder.bindObj = liveHolder;
                } else {
                    liveHolder = holder.bindObj;
                }
                liveHolder.mName = (TextView) holder.getView(R.id.name1);
                liveHolder.mName2 = (TextView) holder.getView(R.id.name2);
                liveHolder.mItemIcon = (ImageView) holder.getView(R.id.icon);
                liveHolder.mOperation = (TextView) holder.getView(R.id.operate_btn);
                liveHolder.mOperationIcon = (ImageView) holder.getView(R.id.operate_btn_icon);
                liveHolder.mYCPayFlag = (TextView) holder.getView(R.id.yc_pay_icon);
                holder.getConvertView().setBackgroundColor(this.mContext.getResources().getColor(2131493377));
                remenBean = (LiveRemenBaseBean) bean;
                drawView(liveHolder, remenBean, position);
                setStatus(liveHolder, remenBean);
                break;
            default:
                LogInfo.log(TAG, "unknown view type: " + viewType);
                break;
        }
        holder.getConvertView().setOnClickListener(new 1(this, position));
        LogInfo.log(TAG, "getView: " + (System.currentTimeMillis() - now));
        return holder.getConvertView();
    }

    public void onBooked(String playTime, String pName, String channelCode, String channelName, String id, boolean booked, boolean isSuccess) {
        TipBean dialogMsgByMsg;
        if (booked && isSuccess) {
            dialogMsgByMsg = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_20003);
            if (dialogMsgByMsg == null) {
                ToastUtils.showToast(this.mContext, this.mContext.getString(2131100324));
                StatisticsUtils.staticticsInfoPost(this.mContext, "a55", null, 4, -1, null, null, null, null, null);
            } else {
                ToastUtils.showToast(this.mContext, dialogMsgByMsg.message);
            }
        } else if (booked && !isSuccess) {
            UIsUtils.showToast(2131100323);
            return;
        } else if (!booked && isSuccess) {
            dialogMsgByMsg = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_20004);
            if (dialogMsgByMsg == null) {
                ToastUtils.showToast(this.mContext, this.mContext.getString(2131100329));
            } else {
                ToastUtils.showToast(this.mContext, dialogMsgByMsg.message);
            }
        } else if (!(booked || isSuccess)) {
            dialogMsgByMsg = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_20026);
            if (dialogMsgByMsg == null) {
                ToastUtils.showToast(this.mContext, this.mContext.getString(2131100323));
                return;
            } else {
                ToastUtils.showToast(this.mContext, dialogMsgByMsg.message);
                return;
            }
        }
        new 2(this, channelCode, booked, pName, channelName, playTime, id).start();
    }

    private String normalizeLiveType(String liveType) {
        if (TextUtils.isEmpty(liveType) || !liveType.startsWith("ent")) {
            return liveType;
        }
        return "ent";
    }

    private void liveStatisticData(int sequence, int position, LiveRemenBaseBean bean) {
        String fl;
        String pageId;
        int wz = position + 1;
        if (this.mFrom == 2) {
            String str;
            fl = "h05";
            pageId = PageIdConstant.sportCategoryPage;
            Context context = this.mContext;
            String str2 = "0";
            if (bean != null) {
                str = bean.id;
            } else {
                str = null;
            }
            StatisticsUtils.statisticsActionInfo(context, pageId, str2, fl, null, wz, null, null, null, null, null, str);
        } else if (this.mFrom == 3) {
            fl = "st3";
            pageId = PageIdConstant.starPage;
            StatisticsUtils.statisticsActionInfo(this.mContext, pageId, "0", fl, bean != null ? bean.getName2() : "", wz, null, null, null, null, null, null, null, sequence, null, null, null, null, null);
        } else {
            fl = "c21";
            pageId = StatisticsUtils.getLivePageId(this.mTabIndex);
        }
        StatisticsUtils.setActionProperty(fl, wz, pageId);
    }
}
