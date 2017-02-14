package com.letv.android.client.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.MyCollectActivity;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.core.bean.FavouriteBean;
import com.letv.core.db.DBManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class MyCollectListAdapter extends LetvBaseAdapter {
    private Context mContext;
    private HashSet<FavouriteBean> mDeleteItems;
    private ArrayList<FavouriteBean> mItems;
    private OnDeleteListener onDeleteListener;

    public interface OnDeleteListener {
        void onDeleteAll();

        void onDeleteSelected();
    }

    public MyCollectListAdapter(Context context, OnDeleteListener onDeleteListener) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mDeleteItems = new HashSet();
        this.mItems = new ArrayList();
        this.mContext = context;
        this.onDeleteListener = onDeleteListener;
    }

    public void setList(List list) {
        this.mItems.clear();
        if (list != null && list.size() > 0) {
            for (FavouriteBean fav : list) {
                this.mItems.add(fav);
            }
        }
        super.setList(this.mItems);
    }

    public void editOrNot(boolean edit) {
        if (!edit) {
            this.mDeleteItems.clear();
        }
        notifyDataSetChanged();
    }

    public void selectAllOrNot(boolean select) {
        if (select) {
            this.mDeleteItems.addAll(this.mItems);
        } else {
            this.mDeleteItems.clear();
        }
        notifyDataSetChanged();
    }

    public void deleteLocalDbFavorite() {
        if (this.mDeleteItems.size() > 0) {
            LogInfo.log("songhang", "本地数据库 - 批量删除收藏成功");
            new Thread(new 1(this)).start();
        }
    }

    public void deleteCloudFavorite() {
        if (this.mDeleteItems.size() > 0) {
            Long[] ids = new Long[this.mDeleteItems.size()];
            int i = 0;
            Iterator it = this.mDeleteItems.iterator();
            while (it.hasNext()) {
                int i2 = i + 1;
                ids[i] = Long.valueOf(((FavouriteBean) it.next()).favoriteId);
                i = i2;
            }
            DBManager.getInstance().getFavoriteTrace().requestGetMultideleteFavourite(TextUtils.join(",", ids), new 2(this));
        }
    }

    private void notifyFavouriteChanged() {
        Iterator it = this.mDeleteItems.iterator();
        while (it.hasNext()) {
            this.mItems.remove((FavouriteBean) it.next());
        }
        this.mDeleteItems.clear();
        notifyDataSetChanged();
        if ((this.mContext instanceof MyCollectActivity) && this.mItems.size() == 0 && this.onDeleteListener != null) {
            this.onDeleteListener.onDeleteAll();
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mListItem;
        FavouriteBean itemBean = (FavouriteBean) this.mItems.get(position);
        if (convertView == null) {
            mListItem = new ViewHolder();
            convertView = UIsUtils.inflate(this.mContext, R.layout.fragment_my_download_finish_item_new, parent, false);
            mListItem.mCheckBox = (ImageView) convertView.findViewById(2131362639);
            mListItem.mAlbumImageView = (ImageView) convertView.findViewById(2131362641);
            mListItem.mPlayStatusImageView = (ImageView) convertView.findViewById(2131362643);
            mListItem.mAlbumNameTextView = (TextView) convertView.findViewById(2131362642);
            mListItem.mSubTitleTextView = (TextView) convertView.findViewById(2131362644);
            mListItem.mDescriptionTextView = (TextView) convertView.findViewById(2131362645);
            mListItem.mPlayStatusImageView.setVisibility(8);
            convertView.setTag(mListItem);
        } else {
            mListItem = (ViewHolder) convertView.getTag();
            clearViewContent(mListItem);
        }
        if (this.mContext instanceof MyCollectActivity) {
            int i;
            MyCollectActivity activity = this.mContext;
            ImageView imageView = mListItem.mCheckBox;
            if (activity.isEditing()) {
                i = 0;
            } else {
                i = 8;
            }
            imageView.setVisibility(i);
            if (activity.isSelectAll() || this.mDeleteItems.contains(itemBean)) {
                mListItem.mCheckBox.setImageResource(2130837822);
            } else {
                mListItem.mCheckBox.setImageResource(2130837823);
            }
        }
        if (itemBean != null) {
            ImageDownloader.getInstance().download(mListItem.mAlbumImageView, itemBean.pic);
            mListItem.mAlbumNameTextView.setText(itemBean.nameCn + " ");
            mListItem.mSubTitleTextView.setText(itemBean.getActorAndSingerInfo());
            mListItem.mDescriptionTextView.setText(itemBean.getAboutEpisode());
            convertView.setOnClickListener(new 3(this, itemBean, mListItem.mCheckBox));
        }
        return convertView;
    }

    private void clearViewContent(ViewHolder listItem) {
        if (listItem.mAlbumImageView != null) {
            listItem.mAlbumImageView.setImageDrawable(null);
        }
    }
}
