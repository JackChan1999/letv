package com.letv.android.client.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.core.bean.HomeBlock;
import com.letv.core.bean.HomeMetaData;
import com.letv.core.bean.PageCardListBean;
import com.letv.core.bean.PageCardListBean.PageCardBlock;
import com.letv.core.bean.PageCardListBean.PageCardRecyclerBean;
import com.letv.core.pagecard.LayoutParser;
import com.letv.core.pagecard.PageCardInflate;
import com.letv.core.pagecard.PageCardRowRender;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

public abstract class PageCardAdapter extends BaseExpandableListAdapter implements OnScrollListener {
    protected Context mContext;
    protected boolean mIsScroll;
    protected PageCardListBean mPageCardList;

    protected abstract int getChildDataCount(int i);

    protected abstract int getGroupCardId(int i);

    public PageCardAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mIsScroll = false;
        this.mContext = context;
    }

    public void setPageCardList(PageCardListBean pageCardList) {
        this.mPageCardList = pageCardList;
        notifyDataSetChanged();
    }

    protected PageCardBlock getCard(int groupPosition) {
        String cardId = "card_" + getGroupCardId(groupPosition);
        if (this.mPageCardList == null) {
            return new PageCardBlock();
        }
        if (!BaseTypeUtils.isMapContainsKey(this.mPageCardList.map, cardId)) {
            cardId = "card_-1";
        }
        PageCardBlock cardBean = (PageCardBlock) this.mPageCardList.map.get(cardId);
        if (cardBean == null) {
            return new PageCardBlock();
        }
        return cardBean;
    }

    protected PageCardRecyclerBean getGroupRecycler(int groupPosition) {
        PageCardBlock card = getCard(groupPosition);
        if (card == null) {
            return null;
        }
        return card.group;
    }

    protected PageCardRecyclerBean getChildRecycler(int groupPosition, int childPosition) {
        PageCardBlock card = getCard(groupPosition);
        if (card == null) {
            return null;
        }
        if (PageCardListBean.isLineSame(card.cardId)) {
            return (PageCardRecyclerBean) BaseTypeUtils.getElementFromList(card.childList, 0);
        }
        PageCardRecyclerBean recyclerBean = (PageCardRecyclerBean) BaseTypeUtils.getElementFromList(card.childList, childPosition);
        if (recyclerBean == null && this.mPageCardList != null) {
            recyclerBean = this.mPageCardList.getDefaultRecyclerBean();
        }
        return recyclerBean;
    }

    public int getChildrenCount(int groupPosition) {
        PageCardBlock cardBean = getCard(groupPosition);
        if (cardBean == null || BaseTypeUtils.isListEmpty(cardBean.childList)) {
            return 0;
        }
        return cardBean.getLine(getChildDataCount(groupPosition), this.mPageCardList);
    }

    public int getGroupType(int groupPosition) {
        PageCardRecyclerBean recycler = getGroupRecycler(groupPosition);
        if (recycler == null) {
            return 0;
        }
        int count = getGroupTypeCount();
        return recycler.type >= count ? count - 1 : recycler.type;
    }

    public int getGroupTypeCount() {
        if (this.mPageCardList == null) {
            return super.getGroupTypeCount();
        }
        return this.mPageCardList.groupTypeCount;
    }

    public int getChildType(int groupPosition, int childPosition) {
        PageCardRecyclerBean recycler = getChildRecycler(groupPosition, childPosition);
        if (recycler == null) {
            return 0;
        }
        int count = getChildTypeCount();
        return recycler.type >= count ? count - 1 : recycler.type;
    }

    public int getChildTypeCount() {
        if (this.mPageCardList == null) {
            return super.getChildTypeCount();
        }
        return this.mPageCardList.childTypeCount;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null || !(convertView.getTag() instanceof GroupHolder)) {
            PageCardInflate inflate = new PageCardRowRender(this.mContext).inflate(this.mPageCardList.rootXML, "", isHot(groupPosition) ? "title_more" : getGroupRecycler(groupPosition).name);
            convertView = inflate.getRoot();
            GroupHolder holder = new GroupHolder(this);
            holder.frame = inflate.getViewByName("head_body", new View(this.mContext));
            holder.titleTextView = (TextView) inflate.getViewByName("head_label", new TextView(this.mContext));
            holder.moreText = inflate.getViewByName("head_more", new View(this.mContext));
            holder.moreImage = inflate.getViewByName("head_more_image", new View(this.mContext));
            for (int i = 0; i < 3; i++) {
                holder.subTitleArr[i] = (TextView) inflate.getViewByName("subtitle_" + i, new TextView(this.mContext));
            }
            if (convertView == null) {
                convertView = new View(this.mContext);
            }
            convertView.setTag(holder);
        }
        convertView.setLayoutParams(new LayoutParams(convertView.getLayoutParams().width, convertView.getLayoutParams().height));
        return convertView;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null || !(convertView.getTag() instanceof ChildWapper)) {
            PageCardRecyclerBean recycler = getChildRecycler(groupPosition, childPosition);
            if (recycler == null) {
                recycler = new PageCardRecyclerBean();
                recycler.count = 1;
            }
            ChildWapper wapper = new ChildWapper(this);
            wapper.setHolderCount(recycler.count);
            PageCardInflate inflate = new PageCardRowRender(this.mContext).inflate(this.mPageCardList.rootXML, recycler.name, "");
            convertView = inflate.getRoot();
            fillChildHoler(wapper, inflate);
            if (convertView == null) {
                convertView = new View(this.mContext);
            }
            convertView.setTag(wapper);
        }
        convertView.setLayoutParams(new LayoutParams(-1, -2));
        return convertView;
    }

    private void fillChildHoler(ChildWapper wapper, LayoutParser parser) {
        for (int i = 0; i < wapper.holderArr.length; i++) {
            ChildHolder holder = wapper.holderArr[i];
            String parent = "element_" + i;
            holder.root = parser.getViewByName(parent, new View(this.mContext));
            holder.imageView = (ImageView) parser.getViewByName(parent, "image", new ImageView(this.mContext));
            holder.title = (TextView) parser.getViewByName(parent, "title", new TextView(this.mContext));
            holder.subTitle = (TextView) parser.getViewByName(parent, "sub_title", new TextView(this.mContext));
            holder.letfStamp = (TextView) parser.getViewByName(parent, "left_stamp", new TextView(this.mContext));
            holder.rightStamp = (TextView) parser.getViewByName(parent, "right_stamp", new TextView(this.mContext));
            holder.titleLayout = parser.getViewByName(parent, "titlebody", new View(this.mContext));
            if (holder.root.getTag(2131361806) instanceof Integer) {
                holder.imageView.getLayoutParams().height = ((Integer) holder.root.getTag(2131361806)).intValue();
                parser.getViewByName(parent, "image_cover", new View(this.mContext)).getLayoutParams().height = holder.imageView.getLayoutParams().height;
            }
        }
    }

    protected void doSubTitleView(TextView[] arrText, HomeBlock channelHomeBlock, int position, boolean isFromHome, String scid) {
        if (BaseTypeUtils.getElementFromList(channelHomeBlock.sub_block, 0) != null) {
            ArrayList<HomeMetaData> mListSimpleBlock = ((HomeBlock) channelHomeBlock.sub_block.get(0)).list;
            if (!BaseTypeUtils.isListEmpty(mListSimpleBlock)) {
                int i = 0;
                while (i < mListSimpleBlock.size() && i < 3) {
                    HomeMetaData subSimpleBlock = (HomeMetaData) mListSimpleBlock.get(i);
                    if (subSimpleBlock != null) {
                        int index = i;
                        arrText[i].setText(subSimpleBlock.nameCn);
                        arrText[i].setVisibility(0);
                        arrText[i].setOnClickListener(new 1(this, subSimpleBlock, channelHomeBlock, isFromHome, scid, index, position));
                        i++;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
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

    protected boolean isHot(int groupPosition) {
        return false;
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

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
