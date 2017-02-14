package com.letv.android.client.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.core.bean.FindChildDataAreaBean;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

public class FindActAdapter extends BaseAdapter {
    private Context mContext;
    private String mCurrentServerTime;
    private ArrayList<FindChildDataAreaBean> mFindChildDataAreaBean;

    public FindActAdapter(Context context, ArrayList<FindChildDataAreaBean> findChildDataAreaBean) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mContext = context;
        this.mFindChildDataAreaBean = findChildDataAreaBean;
    }

    public void setFindChildData(ArrayList<FindChildDataAreaBean> mFindChildDataAreaBean, String currentServerTime) {
        this.mFindChildDataAreaBean = mFindChildDataAreaBean;
        this.mCurrentServerTime = currentServerTime;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.mFindChildDataAreaBean.size();
    }

    public Object getItem(int position) {
        return this.mFindChildDataAreaBean.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null || convertView.getTag() == null) {
            mViewHolder = new ViewHolder(this);
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.find_act_item, null);
            mViewHolder.nameTv = (TextView) convertView.findViewById(R.id.find_act_item_nameCn);
            mViewHolder.timeTv = (TextView) convertView.findViewById(R.id.find_act_item_startTime);
            mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.find_act_item_imageView);
            mViewHolder.bookBlueTv = (TextView) convertView.findViewById(R.id.find_act_item_BookButton_blue);
            mViewHolder.bookRedTv = (TextView) convertView.findViewById(R.id.find_act_item_BookButton_red);
            mViewHolder.bookGreenTv = (TextView) convertView.findViewById(R.id.find_act_item_BookButton_green);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        UIsUtils.zoomViewHeight(150, mViewHolder.imageView);
        UIsUtils.zoomView(61, 25, mViewHolder.bookRedTv);
        UIsUtils.zoomView(61, 25, mViewHolder.bookGreenTv);
        UIsUtils.zoomView(61, 25, mViewHolder.bookBlueTv);
        if (!(this.mFindChildDataAreaBean == null || this.mFindChildDataAreaBean.get(position) == null)) {
            mViewHolder.nameTv.setText(((FindChildDataAreaBean) this.mFindChildDataAreaBean.get(position)).nameCn);
            String startTime = ((FindChildDataAreaBean) this.mFindChildDataAreaBean.get(position)).startTime;
            String endTime = ((FindChildDataAreaBean) this.mFindChildDataAreaBean.get(position)).endTime;
            if (!(TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime))) {
                String startStr = startTime.replaceAll("[: -]", "");
                String endStr = endTime.replaceAll("[: -]", "");
                String currentStr = "";
                if (!TextUtils.isEmpty(this.mCurrentServerTime)) {
                    currentStr = this.mCurrentServerTime.replaceAll("[: -]", "");
                }
                StringBuffer sb = new StringBuffer();
                try {
                    sb.append(startStr.substring(0, 8) + this.mContext.getString(2131099923, new Object[]{endStr.substring(0, 8)}));
                    sb.insert(4, this.mContext.getString(2131101228));
                    sb.insert(7, this.mContext.getString(2131100397));
                    sb.insert(10, this.mContext.getString(2131099922));
                    sb.insert(16, this.mContext.getString(2131101228));
                    sb.insert(19, this.mContext.getString(2131100397));
                    sb.insert(22, this.mContext.getString(2131099922));
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                    sb = new StringBuffer("");
                }
                LogInfo.log("Emerson", "-------" + sb.toString());
                mViewHolder.timeTv.setText(sb);
                if (!TextUtils.isEmpty(currentStr)) {
                    if (1 <= startStr.compareToIgnoreCase(currentStr)) {
                        mViewHolder.bookBlueTv.setVisibility(0);
                        mViewHolder.bookRedTv.setVisibility(8);
                        mViewHolder.bookGreenTv.setVisibility(8);
                    } else if (1 <= currentStr.compareToIgnoreCase(endStr)) {
                        mViewHolder.bookBlueTv.setVisibility(8);
                        mViewHolder.bookRedTv.setVisibility(0);
                        mViewHolder.bookGreenTv.setVisibility(8);
                    } else {
                        mViewHolder.bookBlueTv.setVisibility(8);
                        mViewHolder.bookRedTv.setVisibility(8);
                        mViewHolder.bookGreenTv.setVisibility(0);
                    }
                }
            }
            String picUrl = ((FindChildDataAreaBean) this.mFindChildDataAreaBean.get(position)).mobilePic;
            if (!TextUtils.isEmpty(picUrl)) {
                ImageDownloader.getInstance().download(mViewHolder.imageView, picUrl, 2130838798, ScaleType.FIT_XY);
            }
        }
        return convertView;
    }
}
