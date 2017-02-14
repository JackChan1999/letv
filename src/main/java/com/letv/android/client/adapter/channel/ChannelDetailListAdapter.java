package com.letv.android.client.adapter.channel;

import android.annotation.SuppressLint;
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
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.utils.EpisodeTitleUtils;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.PageCardListBean.PageCardBlock;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.CategoryCode;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"NewApi"})
public class ChannelDetailListAdapter extends PageCardAdapter {
    private int mChannelId;
    private ImageDownloader mImageDownloader;
    protected List<AlbumInfo> mList;

    public ChannelDetailListAdapter(Context context, int channelId) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mList = new ArrayList();
        this.mChannelId = channelId;
        this.mImageDownloader = ImageDownloader.getInstance();
    }

    public void setList(ExpandableListView listView, ArrayList<AlbumInfo> list) {
        if (list != null && listView != null) {
            this.mList.clear();
            this.mList.addAll(list);
            notifyDataSetChanged();
            for (int i = 0; i < getGroupCount(); i++) {
                listView.expandGroup(i);
            }
            listView.setGroupIndicator(null);
        }
    }

    public void addList(List<AlbumInfo> dataList) {
        if (!BaseTypeUtils.isListEmpty(dataList)) {
            this.mList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public int getChildrenCount(int groupPosition) {
        if (BaseTypeUtils.isListEmpty(this.mList) || this.mPageCardList == null) {
            return 0;
        }
        return (this.mList.size() + 1) / 2;
    }

    public Object getGroup(int groupPosition) {
        return null;
    }

    public int getGroupCount() {
        if (BaseTypeUtils.isListEmpty(this.mList)) {
            return 0;
        }
        return 1;
    }

    protected int getChildDataCount(int groupPosition) {
        if (this.mList == null) {
            return 0;
        }
        return this.mList.size();
    }

    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
        ChildWapper wrap = (ChildWapper) convertView.getTag();
        PageCardBlock card = getCard(groupPosition);
        for (int i = 0; i < wrap.holderArr.length; i++) {
            ChildHolder holder = wrap.holderArr[i];
            setViewProcess(holder.root, holder.imageView, holder.titleLayout, holder.title, holder.subTitle, holder.letfStamp, holder.rightStamp, this.mList, card.getLineBeginIndex(childPosition) + i);
        }
        return convertView;
    }

    protected int getGroupCardId(int groupPosition) {
        return 3;
    }

    protected void setViewProcess(View itemView, ImageView imageView, View titleLayout, TextView titleView, TextView subTitleView, TextView labelView, TextView rightStamp, List<AlbumInfo> mList, int position) {
        if (mList == null || BaseTypeUtils.getElementFromList(mList, position) == null) {
            itemView.setVisibility(4);
            itemView.setOnClickListener(null);
            return;
        }
        AlbumInfo info = (AlbumInfo) BaseTypeUtils.getElementFromList(mList, position);
        itemView.setVisibility(0);
        String imageUrl = "";
        if (!TextUtils.isEmpty(info.icon_16_9)) {
            imageUrl = info.icon_16_9;
        } else if (!TextUtils.isEmpty(info.icon_400_300)) {
            imageUrl = info.icon_400_300;
        } else if (!TextUtils.isEmpty(info.icon_200_150)) {
            imageUrl = info.icon_200_150;
        }
        this.mImageDownloader.download(imageView, imageUrl, 2130838713, ScaleType.FIT_XY, !this.mIsScroll, true);
        int i = (TextUtils.isEmpty(info.subTitle) && TextUtils.isEmpty(info.nameCn)) ? 8 : 0;
        titleLayout.setVisibility(i);
        showTitleForChannel(this.mContext, titleLayout, info, titleView, subTitleView, labelView);
        EpisodeTitleUtils.setLable(labelView, info.leftSubscipt, info.leftSubscipt, rightStamp, info.extendSubscript, info.subsciptColor);
        itemView.setOnClickListener(new 1(this, info, position));
    }

    private void doItemClick(AlbumInfo album, int index) {
        if (album != null) {
            VideoType videoType = VideoType.Normal;
            if (this.mChannelId == 1001) {
                videoType = VideoType.Dolby;
            } else if (album.isPanorama()) {
                videoType = VideoType.Panorama;
            }
            StatisticsUtils.setActionProperty(CategoryCode.CHANNEL_CONTENT_FILTER, index + 1, PageIdConstant.getPageIdByChannelId(this.mChannelId));
            if (album.type == 1) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mContext).create(album.pid, 0, videoType, 1, album.noCopyright, album.externalUrl)));
            } else {
                if (album.type == 3) {
                    long vid = album.vid;
                    long aid = album.pid;
                    if (aid != vid) {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mContext).create(aid, vid, videoType, 1, album.noCopyright, album.externalUrl)));
                        return;
                    }
                }
                int i = 1;
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(i, new AlbumPlayActivityConfig(this.mContext).create(0, album.vid, videoType, 1, album.noCopyright, album.externalUrl)));
            }
            StatisticsUtils.staticticsInfoPost(this.mContext, CategoryCode.CHANNEL_CONTENT_FILTER, null, index, this.mChannelId, this.mChannelId + "", album.pid + "", album.vid + "", null, album.vid + "");
        }
    }

    private void showTitleForChannel(Context context, View titleLayout, AlbumInfo block, TextView titleView, TextView subTitleView, TextView lableView) {
        if (context != null && block != null) {
            if (block.cid == 1001) {
                titleView.setSingleLine(false);
                subTitleView.setVisibility(8);
            } else if (block.type == 3) {
                switch (this.mChannelId) {
                    case 9:
                        if (!TextUtils.isEmpty(block.subTitle)) {
                            titleView.setText(block.subTitle);
                        } else if (!TextUtils.isEmpty(block.nameCn)) {
                            titleView.setText(block.nameCn);
                        }
                        titleView.setSingleLine(false);
                        titleView.setMaxLines(2);
                        subTitleView.setVisibility(8);
                        return;
                    default:
                        EpisodeTitleUtils.setTitle(block.nameCn, titleLayout, titleView, "", subTitleView);
                        return;
                }
            } else {
                switch (this.mChannelId) {
                    case 2:
                    case 5:
                        if (block.isEnd == 1) {
                            EpisodeTitleUtils.setTitle(block.nameCn, titleLayout, titleView, block.subTitle, subTitleView);
                            return;
                        } else if (BaseTypeUtils.stof(block.nowEpisodes, 0.0f) <= 0.0f) {
                            EpisodeTitleUtils.setTitle(block.nameCn, titleLayout, titleView, block.subTitle, subTitleView);
                            return;
                        } else {
                            titleView.setText(block.nameCn);
                            subTitleView.setText(this.mContext.getString(2131099840, new Object[]{block.nowEpisodes}));
                            return;
                        }
                    case 4:
                    case 9:
                        EpisodeTitleUtils.setTitle(block.nameCn, titleLayout, titleView, "", subTitleView);
                        return;
                    default:
                        EpisodeTitleUtils.setTitle(block.nameCn, titleLayout, titleView, block.subTitle, subTitleView);
                        return;
                }
            }
        }
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
