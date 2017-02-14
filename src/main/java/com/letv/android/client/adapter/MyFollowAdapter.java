package com.letv.android.client.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.core.bean.MyFollowItemBean;
import com.letv.core.download.image.ImageDownloader;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.List;

public class MyFollowAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyFollowItemBean> mList;

    public MyFollowAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mList = new ArrayList();
        this.mContext = context;
    }

    public void setData(List<MyFollowItemBean> list) {
        if (list == null) {
            this.mList.clear();
        } else {
            this.mList = list;
        }
        notifyDataSetInvalidated();
    }

    public void addData(List<MyFollowItemBean> list) {
        if (list != null) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int position) {
        return this.mList.get(position);
    }

    public long getItemId(int position) {
        try {
            return Long.parseLong(((MyFollowItemBean) this.mList.get(position)).follow_id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            holder = new ViewHolder(this, null);
            view = LayoutInflater.from(this.mContext).inflate(R.layout.my_follow_item, null);
            holder.image_user = (ImageView) view.findViewById(R.id.image_user);
            holder.follower_count = (TextView) view.findViewById(R.id.follower_count);
            holder.star_name = (TextView) view.findViewById(R.id.star_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        MyFollowItemBean bean = (MyFollowItemBean) getItem(position);
        if (TextUtils.isEmpty(bean.nickname)) {
            holder.star_name.setText("");
        } else {
            holder.star_name.setText(bean.nickname);
        }
        holder.follower_count.setText(String.format("%d%s", new Object[]{Integer.valueOf(bean.follow_num), this.mContext.getString(2131101297)}));
        ImageDownloader.getInstance().download(holder.image_user, bean.headimg, 2130837905);
        return view;
    }
}
