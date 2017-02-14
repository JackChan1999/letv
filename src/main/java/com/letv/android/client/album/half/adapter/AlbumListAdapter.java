package com.letv.android.client.album.half.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.album.R;
import com.letv.android.client.commonlib.adapter.ViewHolder;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.download.image.ImageDownloader.BitmapStyle;
import com.letv.core.download.image.ImageDownloader.CustomConfig;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.UIsUtils;
import java.util.ArrayList;

public class AlbumListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<AlbumInfo> mData;

    public AlbumListAdapter(Context context, ArrayList<AlbumInfo> data) {
        this.mContext = context;
        this.mData = data;
    }

    public int getCount() {
        if (BaseTypeUtils.isListEmpty(this.mData)) {
            return 0;
        }
        int size = this.mData.size();
        if (size > 0) {
            return size % 2 == 0 ? size >> 1 : (size / 2) + 1;
        } else {
            return 0;
        }
    }

    public Object getItem(int position) {
        return this.mData.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = ViewHolder.get(this.mContext, convertView, R.layout.home_list_item_layout);
        View item0 = holder.getView(R.id.home_simple_item_0);
        View item1 = holder.getView(R.id.home_simple_item_1);
        View img0 = (ImageView) holder.getView(R.id.home_simple_item_0, R.id.home_simple_item_image);
        TextView title0 = (TextView) holder.getView(R.id.home_simple_item_0, R.id.home_simple_item_title);
        TextView subTitle0 = (TextView) holder.getView(R.id.home_simple_item_0, R.id.home_simple_item_subtitle);
        View img1 = (ImageView) holder.getView(R.id.home_simple_item_1, R.id.home_simple_item_image);
        TextView title1 = (TextView) holder.getView(R.id.home_simple_item_1, R.id.home_simple_item_title);
        TextView subTitle1 = (TextView) holder.getView(R.id.home_simple_item_1, R.id.home_simple_item_subtitle);
        UIsUtils.zoomView(148, 115, item0);
        UIsUtils.zoomView(148, 115, item1);
        UIsUtils.zoomView(148, 81, img0);
        UIsUtils.zoomView(148, 81, img1);
        if (this.mData.size() > position * 2) {
            AlbumInfo meta0 = (AlbumInfo) this.mData.get(position * 2);
            if (meta0 != null) {
                LogInfo.log("clf", "meta0.pic=" + meta0.pic);
                ImageDownloader.getInstance().download(img0, meta0.pic, R.drawable.poster_defualt_pic2, new CustomConfig(BitmapStyle.CORNER, this.mContext.getResources().getDimensionPixelSize(R.dimen.letv_dimens_2)));
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
                item0.setOnClickListener(new 1(this, meta0));
            }
        }
        if (this.mData.size() > (position * 2) + 1) {
            AlbumInfo meta1 = (AlbumInfo) this.mData.get((position * 2) + 1);
            if (meta1 != null) {
                item1.setVisibility(0);
                LogInfo.log("clf", "meta1.pic=" + meta1.pic);
                ImageDownloader.getInstance().download(img1, meta1.pic, R.drawable.poster_defualt_pic2, new CustomConfig(BitmapStyle.CORNER, this.mContext.getResources().getDimensionPixelSize(R.dimen.letv_dimens_2)));
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
                item1.setOnClickListener(new 2(this, meta1));
            } else {
                item1.setVisibility(8);
            }
        } else {
            item1.setVisibility(8);
        }
        return holder.getConvertView();
    }

    private void jump(AlbumInfo meta) {
        if (meta != null) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mContext).create(meta.pid, meta.vid, VideoType.Normal, 24, meta.noCopyright, meta.externalUrl)));
        }
    }
}
