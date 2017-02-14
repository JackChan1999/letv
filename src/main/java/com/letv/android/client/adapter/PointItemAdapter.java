package com.letv.android.client.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.utils.UIs;
import com.letv.core.bean.PointBeanList.PointBean;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

public class PointItemAdapter extends BaseAdapter {
    private ArrayList<PointBean> list;
    private Context mContext;

    public PointItemAdapter(Context context, ArrayList<PointBean> list) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.list = null;
        this.mContext = context;
        this.list = list;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return this.list.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    @SuppressLint({"ResourceAsColor"})
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = UIs.inflate(this.mContext, R.layout.point_item, null);
            vh = new ViewHolder(this);
            vh.title = (TextView) convertView.findViewById(R.id.point_title);
            vh.point = (TextView) convertView.findViewById(R.id.points_letv);
            vh.state = (TextView) convertView.findViewById(R.id.point_state);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.title.setText(((PointBean) this.list.get(position)).rname);
        vh.point.setText("+" + ((PointBean) this.list.get(position)).credit);
        if (((PointBean) this.list.get(position)).state < ((PointBean) this.list.get(position)).rewardnum) {
            vh.state.setText((Integer.parseInt(((PointBean) this.list.get(position)).credit) * ((PointBean) this.list.get(position)).state) + "/" + (Integer.parseInt(((PointBean) this.list.get(position)).credit) * ((PointBean) this.list.get(position)).rewardnum));
        } else {
            vh.state.setTextColor(2131493377);
            vh.state.setText(this.mContext.getString(2131100064));
        }
        return convertView;
    }
}
