package com.letv.android.client.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.ViewHolder;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.download.image.ImageDownloader.BitmapStyle;
import com.letv.core.download.image.ImageDownloader.CustomConfig;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.TipUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.ltpbdata.LTStarRankModelDetailPBPKGOuterClass.LTStarRankModelDetailPB;
import java.util.ArrayList;
import java.util.List;

public class StarRankAdapter extends BaseExpandableListAdapter implements OnScrollListener {
    private boolean isScroll;
    private Context mContext;
    private ImageDownloader mImageDownloader;
    private ArrayList<LTStarRankModelDetailPB> mList;
    private SparseArray<Integer> mRankSp;

    public StarRankAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.isScroll = false;
        this.mList = new ArrayList();
        this.mRankSp = new SparseArray();
        this.mContext = context;
        this.mImageDownloader = ImageDownloader.getInstance();
    }

    public void setList(ExpandableListView listView, List<LTStarRankModelDetailPB> list) {
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

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public Object getGroup(int groupPosition) {
        return null;
    }

    public int getChildrenCount(int groupPosition) {
        if (BaseTypeUtils.isListEmpty(this.mList)) {
            return 0;
        }
        return this.mList.size();
    }

    public boolean hasStableIds() {
        return false;
    }

    public long getGroupId(int groupPosition) {
        return 0;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return Integer.valueOf(childPosition);
    }

    public int getGroupCount() {
        return 1;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return new View(this.mContext);
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = ViewHolder.get(this.mContext, convertView, R.layout.star_ranking_item);
        if (BaseTypeUtils.getElementFromList(this.mList, childPosition) == null) {
            mViewHolder.getConvertView().setVisibility(8);
            return mViewHolder.getConvertView();
        }
        LTStarRankModelDetailPB starRanking = (LTStarRankModelDetailPB) this.mList.get(childPosition);
        mViewHolder.getConvertView().setVisibility(0);
        RelativeLayout layout = (RelativeLayout) mViewHolder.getView(R.id.star_frame);
        TextView star_ranking = (TextView) mViewHolder.getView(R.id.star_ranking);
        TextView star_suport = (TextView) mViewHolder.getView(R.id.star_suport);
        View headImage = (ImageView) mViewHolder.getView(R.id.star_ranking_head);
        ((TextView) mViewHolder.getView(R.id.star_name)).setText(starRanking.nickname);
        star_suport.setText(getVoteNum(starRanking.num));
        if (childPosition >= 1) {
            if (this.mRankSp.get(childPosition - 1) != null) {
                this.mRankSp.put(childPosition, Integer.valueOf((TextUtils.equals(starRanking.num, ((LTStarRankModelDetailPB) this.mList.get(childPosition + -1)).num) ? 0 : 1) + ((Integer) this.mRankSp.get(childPosition - 1)).intValue()));
            }
            star_ranking.setText(String.valueOf(this.mRankSp.get(childPosition)));
        }
        if (childPosition == 0) {
            this.mRankSp.put(childPosition, Integer.valueOf(1));
            star_ranking.setText(String.valueOf(1));
            star_ranking.setBackgroundColor(this.mContext.getResources().getColor(2131493346));
            star_ranking.setTextColor(this.mContext.getResources().getColor(2131493377));
        } else if (childPosition == 1) {
            star_ranking.setBackgroundColor(this.mContext.getResources().getColor(2131493354));
            star_ranking.setTextColor(this.mContext.getResources().getColor(2131493377));
        } else if (childPosition == 2) {
            star_ranking.setBackgroundColor(this.mContext.getResources().getColor(2131493364));
            star_ranking.setTextColor(this.mContext.getResources().getColor(2131493377));
        } else {
            star_ranking.setBackgroundColor(this.mContext.getResources().getColor(2131493412));
            star_ranking.setTextColor(this.mContext.getResources().getColor(2131493280));
        }
        layout.setOnClickListener(new 1(this, starRanking, childPosition));
        headImage.setTag(2131361795, new CustomConfig(BitmapStyle.ROUND, 0));
        this.mImageDownloader.download(headImage, starRanking.headimg, 2130837905, !this.isScroll, true);
        return mViewHolder.getConvertView();
    }

    public String getVoteNum(String voteNum) {
        if (TextUtils.isEmpty(voteNum)) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        char[] c = voteNum.toCharArray();
        int i = 0;
        while (i < c.length) {
            sb.append(c[i]);
            if ((i + 1) % 3 == 0 && i != c.length - 1) {
                sb.append(",");
            }
            i++;
        }
        if (this.mContext == null) {
            return "";
        }
        return sb.append(" ").append(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700073, this.mContext.getResources().getString(2131101138))).toString();
    }

    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (scrollState == 0) {
            this.isScroll = false;
            notifyDataSetChanged();
        } else if (scrollState == 1) {
            this.isScroll = true;
        } else if (scrollState == 2) {
            this.isScroll = true;
        }
    }

    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
    }
}
