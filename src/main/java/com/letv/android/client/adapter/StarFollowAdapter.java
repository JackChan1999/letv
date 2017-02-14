package com.letv.android.client.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.ViewHolder;
import com.letv.core.bean.StarFollowRankBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.download.image.ImageDownloader.BitmapStyle;
import com.letv.core.download.image.ImageDownloader.CustomConfig;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvTextUtils;
import com.letv.core.utils.TipUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import io.fabric.sdk.android.services.common.IdManager;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class StarFollowAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<StarFollowRankBean> mData;

    public StarFollowAdapter(Context context, ArrayList<StarFollowRankBean> data) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mContext = context;
        this.mData = data;
    }

    public void setList(ArrayList<StarFollowRankBean> list) {
        if (!BaseTypeUtils.isListEmpty(list)) {
            this.mData.clear();
            this.mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addMe() {
        if (this.mData.size() < 4 && PreferencesManager.getInstance().isLogin()) {
            Iterator it = this.mData.iterator();
            while (it.hasNext()) {
                if (((StarFollowRankBean) it.next()).uid.equals(PreferencesManager.getInstance().getUserId())) {
                    return;
                }
            }
            StarFollowRankBean bean = new StarFollowRankBean();
            bean.uid = PreferencesManager.getInstance().getUserId();
            bean.picture = PreferencesManager.getInstance().getPicture();
            bean.nickname = PreferencesManager.getInstance().getNickName();
            bean.num = "1";
            this.mData.add(bean);
            notifyDataSetChanged();
        }
    }

    public int getCount() {
        if (this.mData.size() > 4) {
            return 4;
        }
        return this.mData.size();
    }

    public Object getItem(int position) {
        return this.mData.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = ViewHolder.get(this.mContext, convertView, R.layout.item_star_follow);
        View follow_img = (ImageView) holder.getView(R.id.follow_img);
        ImageView follow_rank_icon = (ImageView) holder.getView(R.id.follow_rank_icon);
        TextView follow_name = (TextView) holder.getView(R.id.follow_name);
        TextView follow_vote_num = (TextView) holder.getView(R.id.follow_vote_num);
        StarFollowRankBean follow = (StarFollowRankBean) this.mData.get(position);
        if (follow == null) {
            return holder.getConvertView();
        }
        ImageDownloader.getInstance().download(follow_img, follow.picture, 2130837905, new CustomConfig(BitmapStyle.ROUND, 0));
        switch (position) {
            case 0:
                follow_rank_icon.setImageResource(2130838980);
                break;
            case 1:
                follow_rank_icon.setImageResource(2130838981);
                break;
            case 2:
                follow_rank_icon.setImageResource(2130838982);
                break;
            case 3:
                follow_rank_icon.setImageResource(2130838983);
                break;
            default:
                follow_rank_icon.setImageDrawable(null);
                break;
        }
        follow_name.setText(follow.nickname);
        if (TextUtils.isEmpty(follow.num)) {
            follow_vote_num.setVisibility(8);
        } else {
            follow_vote_num.setVisibility(0);
            String voteUnit = TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700073, 2131100883);
            String fllow_num = getVoteCount(follow.num);
            follow_vote_num.setText(LetvTextUtils.changePartialTextAppearance(fllow_num + " " + voteUnit, 0, this.mContext.getResources().getDimensionPixelSize(2131165477), this.mContext.getResources().getColor(2131493346), 0, fllow_num.length()));
        }
        return holder.getConvertView();
    }

    private String getVoteCount(String num) {
        DecimalFormat mDf = new DecimalFormat(IdManager.DEFAULT_VERSION_NAME);
        int count = -1;
        try {
            count = Integer.valueOf(num).intValue();
        } catch (Exception e) {
        }
        if (count < 0) {
            return num;
        }
        if (count < 10000) {
            return count + "";
        }
        if (count < 100000000) {
            return this.mContext.getString(2131100935, new Object[]{mDf.format(((double) count) / 10000.0d)});
        }
        return this.mContext.getString(2131099696, new Object[]{mDf.format(((double) count) / 1.0E8d)});
    }
}
