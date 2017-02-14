package com.letv.android.client.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.android.client.utils.UIs;
import com.letv.android.client.view.LetvImageView;
import com.letv.core.bean.RecommenApp;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.download.image.ImageDownloader;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RecommGalleryAdapter extends LetvBaseAdapter {
    private static int realCount;
    private Context mContext;

    public RecommGalleryAdapter(Activity activity) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(activity);
        this.mContext = activity;
    }

    public int getCount() {
        if (this.mList == null) {
            return 0;
        }
        return Integer.MAX_VALUE;
    }

    public int getRealCount() {
        if (this.mList == null) {
            return 0;
        }
        return this.mList.size() % 2 == 0 ? this.mList.size() / 2 : (this.mList.size() / 2) + 1;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        int pos = position % this.mList.size();
        if (convertView == null) {
            convertView = UIs.inflate(this.mContext, R.layout.recomm_gallery_item, null);
            vh = new ViewHolder(this);
            vh.imageView = (LetvImageView) convertView.findViewById(R.id.gallery_item_picture);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
            clearViewData(vh);
        }
        int w = Global.displayMetrics.widthPixels;
        RecommenApp app = (RecommenApp) this.mList.get(pos);
        vh.imageView.getLayoutParams().width = UIs.zoomWidth(this.mContext.getResources().getDimensionPixelSize(2131165539)) * 2;
        vh.imageView.getLayoutParams().height = UIs.zoomWidth(this.mContext.getResources().getDimensionPixelSize(2131165538));
        ImageDownloader.getInstance().download(vh.imageView, app.getImgBigUrl());
        vh.imageView.setTag(Integer.valueOf(pos));
        vh.imageView.setOnClickListener(new 1(this));
        return convertView;
    }

    private void clearViewData(ViewHolder v) {
        if (v.imageView != null) {
            v.imageView.setImageDrawable(null);
        }
    }
}
