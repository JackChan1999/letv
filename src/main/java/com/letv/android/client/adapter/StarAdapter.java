package com.letv.android.client.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.ViewHolder;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.business.flow.star.StarBookCallback;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.bean.StarBlockBean;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.download.image.ImageDownloader.BitmapStyle;
import com.letv.core.download.image.ImageDownloader.CustomConfig;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class StarAdapter extends BaseExpandableListAdapter {
    public static final int MAX_LINE = 2;
    private Context mContext;
    private ArrayList<StarBlockBean> mData;
    private LiveRoomAdapter mLiveAapter;
    private String mStarName;

    public StarAdapter(Context context, String starName) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mData = new ArrayList();
        this.mContext = context;
        this.mLiveAapter = new LiveRoomAdapter(this.mContext);
        this.mLiveAapter.setFrom(3);
        this.mStarName = starName;
    }

    public void setList(ArrayList<StarBlockBean> data) {
        if (!BaseTypeUtils.isListEmpty(data)) {
            this.mData.clear();
            this.mData.addAll(data);
            Iterator it = this.mData.iterator();
            while (it.hasNext()) {
                StarBlockBean block = (StarBlockBean) it.next();
                if (!BaseTypeUtils.isListEmpty(block.liveList)) {
                    this.mLiveAapter.setList(block.liveList);
                    this.mLiveAapter.setSequence(block.sequence);
                    break;
                }
            }
            notifyDataSetChanged();
        }
    }

    public void setBookedPrograms(Set<String> bookedPrograms) {
        if (this.mLiveAapter != null) {
            this.mLiveAapter.setBookedPrograms(bookedPrograms);
            notifyDataSetChanged();
        }
    }

    public void setStarBookCallback(StarBookCallback starBookCallback) {
        if (this.mLiveAapter != null) {
            this.mLiveAapter.setStarBookCallback(starBookCallback);
        }
    }

    public int getGroupCount() {
        return this.mData.size();
    }

    public int getChildrenCount(int groupPosition) {
        if (this.mData.get(groupPosition) == null) {
            return 0;
        }
        if (!BaseTypeUtils.isListEmpty(((StarBlockBean) this.mData.get(groupPosition)).albumList)) {
            int size = ((StarBlockBean) this.mData.get(groupPosition)).albumList.size();
            if (size >= 4) {
                return 2;
            }
            return size;
        } else if (BaseTypeUtils.isListEmpty(((StarBlockBean) this.mData.get(groupPosition)).liveList)) {
            return 0;
        } else {
            return ((StarBlockBean) this.mData.get(groupPosition)).liveList.size();
        }
    }

    public StarBlockBean getGroup(int groupPosition) {
        return (StarBlockBean) this.mData.get(groupPosition);
    }

    public ArrayList<?> getChild(int groupPosition, int childPosition) {
        if (this.mData.get(groupPosition) == null) {
            return null;
        }
        if (BaseTypeUtils.isListEmpty(((StarBlockBean) this.mData.get(groupPosition)).albumList)) {
            return !BaseTypeUtils.isListEmpty(((StarBlockBean) this.mData.get(groupPosition)).liveList) ? ((StarBlockBean) this.mData.get(groupPosition)).liveList : null;
        } else {
            return ((StarBlockBean) this.mData.get(groupPosition)).albumList;
        }
    }

    public long getGroupId(int groupPosition) {
        return (long) groupPosition;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return (long) childPosition;
    }

    public boolean hasStableIds() {
        return true;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = ViewHolder.get(this.mContext, convertView, R.layout.item_home_list_group);
        View view = holder.getView(R.id.home_listview_item_head_divider);
        TextView title = (TextView) holder.getView(R.id.home_listview_item_title);
        TextView more = (TextView) holder.getView(R.id.home_listview_item_more);
        View divider = holder.getView(R.id.home_listview_item_divider);
        View divider_bottom = holder.getView(R.id.home_listview_item_bottom_divider);
        if (groupPosition == 0) {
            view.setVisibility(0);
            divider.setVisibility(8);
        } else {
            view.setVisibility(8);
            divider.setVisibility(0);
        }
        StarBlockBean block = getGroup(groupPosition);
        if (block == null) {
            return holder.getConvertView();
        }
        title.setText(block.title);
        if (block.liveList != null) {
            more.setVisibility(8);
            if (!block.mHasStatistics) {
                block.mHasStatistics = true;
                StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.starPage, "19", "st3", null, -1, null, null, null, null, null, null, null, block.sequence, null, null, null, null, null);
            }
        } else {
            if (hasMore(block)) {
                more.setVisibility(0);
                more.setOnClickListener(new 1(this, block));
            } else {
                more.setVisibility(8);
            }
            if (block.videoNum < 4) {
                divider_bottom.setVisibility(8);
            } else {
                divider_bottom.setVisibility(0);
            }
            ArrayList<?> list = getChild(groupPosition, 0);
            try {
                if (!block.mHasStatistics) {
                    String type = "";
                    if ("MusicList".equals(block.flag)) {
                        type = "st4";
                    } else if ("RingList".equals(block.flag)) {
                        type = "st5";
                    } else if ("StarIdVideo".equals(block.flag)) {
                        type = "st2";
                    } else if ("VideoList".equals(block.flag)) {
                        type = "st7";
                    } else if ("AlbumList".equals(block.flag)) {
                        type = "st6";
                    }
                    if (!TextUtils.isEmpty(type)) {
                        StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.starPage, "19", type, this.mStarName, -1, null, null, null, null, null, null, null, block.sequence, null, null, null, null, null);
                    }
                    block.mHasStatistics = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return holder.getConvertView();
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        ArrayList<?> list = getChild(groupPosition, childPosition);
        if (BaseTypeUtils.isListEmpty(list)) {
            return new View(this.mContext);
        }
        if (list.get(0) instanceof LiveRemenBaseBean) {
            View view = this.mLiveAapter.getView(childPosition, convertView, null);
            View live_divider = view.findViewById(R.id.live_divider_h);
            if (live_divider != null) {
                if (childPosition == list.size() - 1) {
                    live_divider.setVisibility(8);
                } else {
                    live_divider.setVisibility(0);
                }
            }
            view.setBackgroundResource(2130837655);
            return view;
        } else if (!(list.get(0) instanceof AlbumInfo)) {
            return new View(this.mContext);
        } else {
            ViewHolder holder;
            if (list.size() >= 4) {
                holder = ViewHolder.get(this.mContext, convertView, 2130903232);
                View item0 = holder.getView(2131362812);
                View item1 = holder.getView(2131362813);
                View img0 = (ImageView) holder.getView(2131362812, 2131362829);
                TextView title0 = (TextView) holder.getView(2131362812, 2131362831);
                TextView subTitle0 = (TextView) holder.getView(2131362812, 2131362832);
                View img1 = (ImageView) holder.getView(2131362813, 2131362829);
                TextView title1 = (TextView) holder.getView(2131362813, 2131362831);
                TextView subTitle1 = (TextView) holder.getView(2131362813, 2131362832);
                UIsUtils.zoomView(148, 115, item0);
                UIsUtils.zoomView(148, 115, item1);
                UIsUtils.zoomView(148, 81, img0);
                UIsUtils.zoomView(148, 81, img1);
                int position = childPosition;
                LogInfo.log("Iris", "position = " + childPosition);
                if (list.size() > childPosition * 2) {
                    AlbumInfo meta0 = (AlbumInfo) list.get(childPosition * 2);
                    if (meta0 != null) {
                        ImageDownloader.getInstance().download(img0, meta0.pic, 2130838796, new CustomConfig(BitmapStyle.CORNER, this.mContext.getResources().getDimensionPixelSize(2131165347)));
                        if (TextUtils.isEmpty(meta0.nameCn)) {
                            title0.setVisibility(8);
                        } else {
                            title0.setVisibility(0);
                            if (TextUtils.isEmpty(meta0.subTitle)) {
                                title0.setSingleLine(false);
                                title0.setMaxLines(2);
                            } else {
                                title0.setSingleLine(true);
                            }
                            title0.setText(meta0.nameCn);
                        }
                        if (TextUtils.isEmpty(meta0.subTitle)) {
                            subTitle0.setVisibility(8);
                        } else {
                            subTitle0.setVisibility(0);
                            subTitle0.setText(meta0.subTitle);
                        }
                        item0.setOnClickListener(new 2(this, position, meta0, groupPosition));
                    }
                }
                if (list.size() > (childPosition * 2) + 1) {
                    AlbumInfo meta1 = (AlbumInfo) list.get((childPosition * 2) + 1);
                    if (meta1 != null) {
                        item1.setVisibility(0);
                        ImageDownloader.getInstance().download(img1, meta1.pic, 2130838796, new CustomConfig(BitmapStyle.CORNER, this.mContext.getResources().getDimensionPixelSize(2131165347)));
                        if (TextUtils.isEmpty(meta1.nameCn)) {
                            title1.setVisibility(8);
                        } else {
                            title1.setVisibility(0);
                            if (TextUtils.isEmpty(meta1.subTitle)) {
                                title1.setSingleLine(false);
                                title1.setMaxLines(2);
                            } else {
                                title1.setSingleLine(true);
                            }
                            title1.setText(meta1.nameCn);
                        }
                        if (TextUtils.isEmpty(meta1.subTitle)) {
                            subTitle1.setVisibility(8);
                        } else {
                            subTitle1.setVisibility(0);
                            subTitle1.setText(meta1.subTitle);
                        }
                        item1.setOnClickListener(new 3(this, position, meta1, groupPosition));
                    } else {
                        item1.setVisibility(8);
                    }
                } else {
                    item1.setVisibility(8);
                }
                return holder.getConvertView();
            }
            holder = ViewHolder.get(this.mContext, convertView, R.layout.item_star_video_list);
            AlbumInfo album = (AlbumInfo) list.get(childPosition);
            if (album == null) {
                return holder.getConvertView();
            }
            View img = (ImageView) holder.getView(R.id.item_video_img);
            TextView title = (TextView) holder.getView(R.id.item_video_title);
            TextView subTtitle = (TextView) holder.getView(R.id.item_video_subtitle);
            View divider = holder.getView(R.id.item_video_divider);
            ImageDownloader.getInstance().download(img, album.pic, 2130838795, new CustomConfig(BitmapStyle.CORNER, this.mContext.getResources().getDimensionPixelSize(2131165347)));
            if (TextUtils.isEmpty(album.nameCn)) {
                title.setVisibility(8);
            } else {
                title.setVisibility(0);
                if (TextUtils.isEmpty(album.pic)) {
                    title.setSingleLine(false);
                    title.setMaxLines(2);
                } else {
                    title.setSingleLine(true);
                }
                title.setEllipsize(TruncateAt.END);
                title.setText(album.nameCn);
            }
            if (TextUtils.isEmpty(album.subTitle)) {
                subTtitle.setVisibility(8);
            } else {
                subTtitle.setVisibility(0);
                subTtitle.setText(album.subTitle);
            }
            if (childPosition == list.size() - 1) {
                divider.setVisibility(0);
            } else {
                divider.setVisibility(8);
            }
            holder.getConvertView().setOnClickListener(new 4(this, childPosition, album, groupPosition));
            return holder.getConvertView();
        }
    }

    private boolean hasMore(StarBlockBean block) {
        return block.videoNum > 4;
    }

    private void jump(AlbumInfo meta, int position, int groupPosition) {
        try {
            if (this.mData.get(groupPosition) != null) {
                String tag = ((StarBlockBean) this.mData.get(groupPosition)).flag;
                String type = "";
                if ("MusicList".equals(tag)) {
                    type = "st4";
                }
                if ("RingList".equals(tag)) {
                    type = "st5";
                } else if ("StarIdVideo".equals(tag)) {
                    type = "st2";
                }
                StatisticsUtils.setActionProperty(DataUtils.getUnEmptyData(type), position + 1, PageIdConstant.starPage);
                if (!TextUtils.isEmpty(type)) {
                    StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.starPage, "0", type, meta.nameCn, position + 1, null, String.valueOf(meta.cid), String.valueOf(meta.pid), String.valueOf(meta.vid), null, null, null, ((StarBlockBean) this.mData.get(groupPosition)).sequence, null, null, null, null, null);
                }
                if ("VideoList".equals(tag)) {
                    type = "st7";
                } else if ("AlbumList".equals(tag)) {
                    type = "st6";
                }
                if ("VideoList".equals(tag) || "AlbumList".equals(tag)) {
                    StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.starPage, "0", type, meta.nameCn, position + 1, null, null, null, null, null, null, null, ((StarBlockBean) this.mData.get(groupPosition)).sequence, null, null, null, null, null);
                    LogInfo.log("Iris", "Have Clicked");
                }
                LogInfo.log("clf", "jump meta.pid=" + meta.pid + ",meta.vid=" + meta.vid);
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mContext).create(meta.pid, meta.vid, 24, meta.noCopyright, meta.externalUrl)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
