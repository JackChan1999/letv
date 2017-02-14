package com.letv.android.client.adapter.channel;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.letv.android.client.adapter.PageCardAdapter;
import com.letv.android.client.adapter.PageCardAdapter.ChildHolder;
import com.letv.android.client.adapter.PageCardAdapter.ChildWapper;
import com.letv.android.client.adapter.PageCardAdapter.GroupHolder;
import com.letv.android.client.commonlib.utils.UIControllerUtils;
import com.letv.android.client.episode.callback.ChanneDetailFragmentCallBack;
import com.letv.android.client.utils.EpisodeTitleUtils;
import com.letv.android.client.view.channel.ChannelLivehallView;
import com.letv.android.client.view.home.HomeFocusView;
import com.letv.core.bean.AlbumInfo.Channel;
import com.letv.core.bean.HomeBlock;
import com.letv.core.bean.HomeMetaData;
import com.letv.core.bean.LiveRemenListBean;
import com.letv.core.bean.PageCardListBean.PageCardBlock;
import com.letv.core.bean.channel.ChannelHomeBean;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.CategoryCode;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChannelDetailExpandableListAdapter extends PageCardAdapter {
    private ChanneDetailFragmentCallBack mCallBack;
    private int mChannelId;
    private ChannelLivehallView mChannelLivehallView;
    private String mCurrentPageId;
    private HomeFocusView mFocusView;
    protected ImageDownloader mImageDownloader;
    private boolean mIsFromNet;
    private boolean mIsRehreshVipData;
    private boolean mIsVipPageUsed;
    private boolean mIsVisible;
    protected List<HomeBlock> mList;
    private int mStartRank;

    public ChannelDetailExpandableListAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mIsVipPageUsed = false;
        this.mIsFromNet = false;
        this.mIsVisible = false;
        this.mList = new ArrayList();
        this.mStartRank = 0;
        this.mIsRehreshVipData = true;
        this.mImageDownloader = ImageDownloader.getInstance();
    }

    public void initMorecallBack(ChanneDetailFragmentCallBack callBack) {
        this.mCallBack = callBack;
    }

    public void setVisibleToUser(boolean visible) {
        this.mIsVisible = visible;
    }

    public void isFomVipPage(boolean isFromVip) {
        this.mIsVipPageUsed = isFromVip;
    }

    public void setDataList(ExpandableListView listView, ChannelHomeBean homeBean, boolean isFromNet) {
        if (homeBean != null && !BaseTypeUtils.isListEmpty(homeBean.block) && listView != null) {
            this.mList.clear();
            Iterator it = homeBean.block.iterator();
            while (it.hasNext()) {
                HomeBlock block = (HomeBlock) it.next();
                if (block != null && (!BaseTypeUtils.isListEmpty(block.list) || "2".equals(block.contentStyle))) {
                    this.mList.add(block);
                }
            }
            this.mIsFromNet = isFromNet;
            if (BaseTypeUtils.getElementFromList(homeBean.block, homeBean.tabIndex) != null) {
                this.mStartRank = 1;
            } else {
                this.mStartRank = 0;
            }
            notifyDataSetChanged();
            for (int i = 0; i < getGroupCount(); i++) {
                listView.expandGroup(i);
            }
            listView.setGroupIndicator(null);
            listView.setOnGroupClickListener(new 1(this));
            this.mIsRehreshVipData = true;
        }
    }

    public void addDataList(ArrayList<HomeBlock> dataList) {
        if (dataList != null && BaseTypeUtils.getElementFromList(dataList, 0) != null && !BaseTypeUtils.isListEmpty(((HomeBlock) dataList.get(0)).list)) {
            if (this.mList == null || BaseTypeUtils.isListEmpty(this.mList) || ((HomeBlock) this.mList.get(0)).list == null) {
                this.mList = dataList;
            } else {
                ((HomeBlock) this.mList.get(0)).list.addAll(((HomeBlock) dataList.get(0)).list);
            }
            notifyDataSetChanged();
        }
    }

    public void setLiveData(LiveRemenListBean result) {
        int i = 0;
        if (result != null) {
            if (this.mChannelLivehallView == null) {
                this.mChannelLivehallView = new ChannelLivehallView(this.mContext, null);
            }
            if (this.mChannelLivehallView.getVisibility() != 0) {
                this.mChannelLivehallView.setVisibility(0);
            }
            if (!BaseTypeUtils.isListEmpty(result.mRemenList)) {
                this.mChannelLivehallView.setList(result.mRemenList);
            }
            ChannelLivehallView channelLivehallView = this.mChannelLivehallView;
            if (BaseTypeUtils.isListEmpty(result.mRemenList)) {
                i = 8;
            }
            channelLivehallView.setVisibility(i);
            notifyDataSetChanged();
        }
    }

    public ChannelLivehallView getChannelLivehallView() {
        return this.mChannelLivehallView;
    }

    public void setStatisticsInfo(int channelId, String currentPageId) {
        this.mChannelId = channelId;
        this.mCurrentPageId = currentPageId;
        if (currentPidIsVip(currentPageId)) {
            this.mIsVisible = true;
        }
    }

    public void initStatisticsStatus() {
        if (!BaseTypeUtils.isListEmpty(this.mList)) {
            for (HomeBlock block : this.mList) {
                block.mHasStatistics = false;
            }
            notifyDataSetChanged();
        }
    }

    public HomeBlock getGroup(int groupPosition) {
        return (HomeBlock) BaseTypeUtils.getElementFromList(this.mList, groupPosition);
    }

    public int getGroupCount() {
        return this.mList == null ? 0 : this.mList.size();
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = super.getGroupView(groupPosition, isExpanded, convertView, parent);
        if (BaseTypeUtils.isListEmpty(this.mList) || this.mList.size() < groupPosition + 1) {
            return convertView;
        }
        HomeBlock channelHomeBlock = (HomeBlock) this.mList.get(groupPosition);
        if (channelHomeBlock == null) {
            return convertView;
        }
        if (!"2".equals(channelHomeBlock.contentStyle)) {
            String blockName = channelHomeBlock.blockname;
            boolean isNotShowContent = isNotShowGroupContentView(channelHomeBlock);
            LogInfo.log("zhaoxiang", "-----mIsVisible" + blockName + this.mIsVisible);
            if (!isNotShowContent && this.mIsVisible) {
                statisticsVisibleBlock(channelHomeBlock, groupPosition);
            }
            GroupHolder holder = (GroupHolder) convertView.getTag();
            TextView titleView = holder.titleTextView;
            View titleLayout = holder.frame;
            TextView[] arrSubView = holder.subTitleArr;
            convertView.setVisibility(0);
            holder.moreText.setVisibility(8);
            holder.moreImage.setVisibility(8);
            for (View subTitleView : arrSubView) {
                subTitleView.setVisibility(8);
            }
            titleView.setText(blockName);
            if (isShowMoreIcon(channelHomeBlock) && holder.moreText.getTag(2131361805) == null) {
                holder.moreText.setVisibility(0);
                holder.moreImage.setVisibility(0);
                if (!BaseTypeUtils.isListEmpty(channelHomeBlock.sub_block)) {
                    doSubTitleView(arrSubView, channelHomeBlock, groupPosition, false, this.mCurrentPageId);
                }
                holder.moreText.setOnClickListener(new 2(this, channelHomeBlock));
                return convertView;
            }
            titleLayout.setOnClickListener(null);
            if (BaseTypeUtils.isListEmpty(channelHomeBlock.sub_block)) {
                return convertView;
            }
            doSubTitleView(arrSubView, channelHomeBlock, groupPosition, false, this.mCurrentPageId);
            return convertView;
        } else if (this.mChannelLivehallView == null) {
            return new View(this.mContext);
        } else {
            this.mChannelLivehallView.setTitleAndCmsId(channelHomeBlock.blockname, this.mChannelId);
            return this.mChannelLivehallView;
        }
    }

    private boolean isNotShowGroupContentView(HomeBlock channelHomeBlock) {
        if (channelHomeBlock == null) {
            return true;
        }
        String contentStyle = channelHomeBlock.contentStyle;
        String blockName = channelHomeBlock.blockname;
        if ("3".equals(contentStyle) || "46".equals(contentStyle) || "6".equals(contentStyle) || "15".equals(contentStyle) || "13".equals(contentStyle) || "14".equals(contentStyle) || "10".equals(contentStyle) || TextUtils.isEmpty(blockName) || TextUtils.isEmpty(blockName.trim()) || channelHomeBlock.list == null) {
            return true;
        }
        return false;
    }

    private boolean isShowMoreIcon(HomeBlock redirect) {
        if (redirect == null || "-1".equals(redirect.redirectType)) {
            return false;
        }
        try {
            switch (Integer.parseInt(redirect.redirectType)) {
                case 1:
                    return true;
                case 2:
                    if (TextUtils.isEmpty(redirect.redirectPageId)) {
                        return false;
                    }
                    return true;
                case 3:
                    if (TextUtils.isEmpty(redirect.redirectUrl)) {
                        return false;
                    }
                    return true;
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    protected void setViewProcess(View itemView, ImageView imageView, View titleLayout, TextView titleView, TextView subTitleView, TextView labelView, TextView rightLableView, HomeBlock channelHomeBlock, int index, int groupPosition) {
        if (channelHomeBlock != null) {
            HomeMetaData simpleBlock = (HomeMetaData) BaseTypeUtils.getElementFromList(channelHomeBlock.list, index);
            if (simpleBlock == null) {
                itemView.setVisibility(4);
                itemView.setOnClickListener(null);
                return;
            }
            labelView.setVisibility(8);
            itemView.setVisibility(0);
            boolean isStar = TextUtils.equals(channelHomeBlock.contentStyle, "77") || TextUtils.equals(channelHomeBlock.contentStyle, "76");
            this.mImageDownloader.download(imageView, simpleBlock.getPic(imageView.getTag(2131361794)), isStar ? 2130837905 : 2130838713, ScaleType.FIT_XY, !this.mIsScroll, true);
            int i = (TextUtils.isEmpty(simpleBlock.subTitle) && TextUtils.isEmpty(simpleBlock.nameCn)) ? 8 : 0;
            titleLayout.setVisibility(i);
            EpisodeTitleUtils.setTitle(simpleBlock.nameCn, titleLayout, titleView, simpleBlock.subTitle, subTitleView, channelHomeBlock.contentStyle);
            if (this.mIsVipPageUsed && TextUtils.equals(simpleBlock.leftSubscipt, this.mContext.getResources().getString(2131100642))) {
                EpisodeTitleUtils.setRightView(rightLableView, simpleBlock.extendSubscript, simpleBlock.subsciptColor);
            } else {
                EpisodeTitleUtils.setLable(labelView, simpleBlock.leftSubscipt, simpleBlock.leftSubsciptColor, rightLableView, simpleBlock.extendSubscript, simpleBlock.subsciptColor);
            }
            itemView.setOnClickListener(new 3(this, channelHomeBlock, simpleBlock, index, groupPosition));
        }
    }

    private void doItemClick(HomeBlock channelHomeBlock, HomeMetaData simpleBlock, int item, int groupPosition) {
        StatisticsUtils.setActionProperty(CategoryCode.CHANNEL_CONTENT_HOME_BLOCK, item + 1, PageIdConstant.getPageIdByChannelId(this.mChannelId), channelHomeBlock.fragId, String.valueOf(this.mCurrentPageId));
        StatisticsUtils.sPlayStatisticsRelateInfo.mReid = channelHomeBlock.reid;
        StatisticsUtils.sPlayStatisticsRelateInfo.mIsRecommend = simpleBlock.isRec();
        VideoType type = VideoType.Normal;
        if (simpleBlock.isPanorama()) {
            type = VideoType.Panorama;
        } else if (this.mChannelId == 1001) {
            type = VideoType.Dolby;
        }
        UIControllerUtils.gotoActivity(this.mContext, simpleBlock, type, this.mCurrentPageId);
        String fragId = isNotShowGroupContentView(channelHomeBlock) ? NetworkUtils.DELIMITER_LINE : channelHomeBlock.fragId;
        StatisticsUtils.statisticsActionInfo(this.mContext, currentPidIsVip(this.mCurrentPageId) ? PageIdConstant.vipCategoryPage : PageIdConstant.getPageIdByChannelId(this.mChannelId), TextUtils.equals(simpleBlock.is_rec, "true") ? "17" : "0", CategoryCode.CHANNEL_CONTENT_HOME_BLOCK, simpleBlock.nameCn, item + 1, "fragid=" + fragId + "&scid=" + this.mCurrentPageId, String.valueOf(simpleBlock.cid), String.valueOf(simpleBlock.pid), String.valueOf(simpleBlock.vid), null, null, channelHomeBlock.reid, (this.mStartRank + groupPosition) + 1, channelHomeBlock.bucket, channelHomeBlock.area, null, null, null);
    }

    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
        if (BaseTypeUtils.isListEmpty(this.mList)) {
            return convertView;
        }
        HomeBlock homeBlock = getGroup(groupPosition);
        if (homeBlock == null) {
            return convertView;
        }
        if (TextUtils.equals(homeBlock.contentStyle, "123")) {
            setFocusView(homeBlock.list);
            if (this.mFocusView != null) {
                return this.mFocusView.getFocusView();
            }
            return convertView;
        }
        ChildWapper wrap = (ChildWapper) convertView.getTag();
        PageCardBlock card = getCard(groupPosition);
        for (int i = 0; i < wrap.holderArr.length; i++) {
            ChildHolder holder = wrap.holderArr[i];
            setViewProcess(holder.root, holder.imageView, holder.titleLayout, holder.title, holder.subTitle, holder.letfStamp, holder.rightStamp, homeBlock, card.getLineBeginIndex(childPosition) + i, groupPosition);
        }
        return convertView;
    }

    protected int getGroupCardId(int groupPosition) {
        HomeBlock block = (HomeBlock) this.mList.get(groupPosition);
        return block != null ? BaseTypeUtils.stoi(block.contentStyle) : -1;
    }

    protected int getChildDataCount(int groupPosition) {
        if (BaseTypeUtils.getElementFromList(this.mList, groupPosition) == null) {
            return 0;
        }
        ArrayList<HomeMetaData> list = ((HomeBlock) this.mList.get(groupPosition)).list;
        if (BaseTypeUtils.isListEmpty(list)) {
            return 0;
        }
        return list.size();
    }

    private void statisticsVisibleBlock(HomeBlock channelHomeBlock, int groupPosition) {
        if (!channelHomeBlock.mHasStatistics && this.mIsFromNet && !BaseTypeUtils.isListEmpty(channelHomeBlock.list)) {
            StringBuilder stringBuilder = new StringBuilder("&vids=");
            stringBuilder = new StringBuilder("&pids=");
            Iterator it = channelHomeBlock.list.iterator();
            while (it.hasNext()) {
                HomeMetaData itemData = (HomeMetaData) it.next();
                if (itemData.vid > 0) {
                    stringBuilder.append(String.valueOf(itemData.vid)).append(";");
                } else if (itemData.pid > 0) {
                    stringBuilder.append(String.valueOf(itemData.pid)).append(";");
                }
            }
            String vids = stringBuilder.toString();
            if (vids.contains(";")) {
                vids = vids.substring(0, vids.length() - 1);
            }
            String pids = stringBuilder.toString();
            if (pids.contains(";")) {
                pids = pids.substring(0, pids.length() - 1);
            }
            StatisticsUtils.statisticsActionInfo(this.mContext, currentPidIsVip(this.mCurrentPageId) ? PageIdConstant.vipCategoryPage : PageIdConstant.getPageIdByChannelId(this.mChannelId), TextUtils.equals(channelHomeBlock.cms_num, String.valueOf(channelHomeBlock.list.size())) ? "19" : "25", CategoryCode.CHANNEL_CONTENT_HOME_BLOCK, channelHomeBlock.blockname, -1, "fragid=" + channelHomeBlock.fragId + "&scid=" + this.mCurrentPageId + vids + pids, String.valueOf(channelHomeBlock.cid), null, null, null, null, channelHomeBlock.reid, (this.mStartRank + groupPosition) + 1, channelHomeBlock.bucket, channelHomeBlock.area, null, null, null);
            LogInfo.LogStatistics("频道推荐曝光:name=" + channelHomeBlock.blockname + ",fragid=" + channelHomeBlock.fragId + ",reid=" + channelHomeBlock.reid + ",area=" + channelHomeBlock.area + ",bucket=" + channelHomeBlock.bucket + ",cms_num=" + channelHomeBlock.cms_num + " ,cid=" + channelHomeBlock.cid);
            channelHomeBlock.mHasStatistics = true;
        }
    }

    private boolean currentPidIsVip(String pid) {
        if (TextUtils.equals(Channel.VIP_PAGEID_TEST, pid) || TextUtils.equals(Channel.VIP_PAGEID, pid) || TextUtils.equals(Channel.VIP_PAGEID_HONGKONG, pid)) {
            return true;
        }
        return false;
    }

    protected void setFocusView(List<HomeMetaData> focusBlocks) {
        if (BaseTypeUtils.isListEmpty(focusBlocks)) {
            removeFocusView();
            return;
        }
        try {
            if (this.mFocusView == null) {
                this.mFocusView = new HomeFocusView(this.mContext, true);
            }
            if (this.mIsRehreshVipData) {
                this.mFocusView.setList(focusBlocks);
                this.mIsRehreshVipData = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void removeFocusView() {
        if (this.mFocusView != null) {
            this.mFocusView.destroy();
            this.mFocusView = null;
        }
    }

    public void destroyData() {
        if (this.mList != null) {
            this.mList.clear();
        }
        if (this.mChannelLivehallView != null) {
            this.mChannelLivehallView.clear();
            this.mChannelLivehallView = null;
        }
        removeFocusView();
    }
}
