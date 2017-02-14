package com.letv.android.client.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.android.client.utils.UIs;
import com.letv.android.client.view.LetvImageView;
import com.letv.core.bean.RecommenApp;
import com.letv.core.download.image.ImageDownloader;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.CategoryCode;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.List;
import java.util.Set;

public class RecommendlListViewAdapter extends LetvBaseAdapter {
    private Set<String> infodatas;
    private boolean lock;
    private Context mContext;
    private int whichList;

    public final class ViewHolder {
        public Button btn_install;
        public LetvImageView iv_1;
        public TextView mainTitle_1;
        public RelativeLayout relativeLayout1;
        public TextView subTitle_1;
        final /* synthetic */ RecommendlListViewAdapter this$0;

        public ViewHolder(RecommendlListViewAdapter this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
        }
    }

    public RecommendlListViewAdapter(Context context, int whichList) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.whichList = 1;
        this.mContext = context;
        this.whichList = whichList;
    }

    public void lock() {
        this.lock = true;
    }

    public void unLock() {
        this.lock = false;
    }

    public int getCount() {
        if (this.mList == null) {
            return 0;
        }
        return this.mList.size();
    }

    public List getList() {
        return this.mList;
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder(this);
            convertView = UIs.inflate(this.mContext, R.layout.recommend_listview_item, null);
            mViewHolder.relativeLayout1 = (RelativeLayout) convertView.findViewById(R.id.recommend_listview_item_i1);
            UIs.zoomView(300, 61, mViewHolder.relativeLayout1);
            mViewHolder.iv_1 = (LetvImageView) mViewHolder.relativeLayout1.findViewById(R.id.recommend_listview_item_image);
            mViewHolder.mainTitle_1 = (TextView) mViewHolder.relativeLayout1.findViewById(R.id.recommend_main_title);
            mViewHolder.subTitle_1 = (TextView) mViewHolder.relativeLayout1.findViewById(R.id.recommend_sub_title);
            mViewHolder.btn_install = (Button) mViewHolder.relativeLayout1.findViewById(R.id.recommend_btn_install);
            UIs.zoomView(44, 44, mViewHolder.iv_1);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
            clearViewData(mViewHolder);
        }
        RecommenApp app = (RecommenApp) this.mList.get(position);
        app.setFlag(false);
        if (this.infodatas.contains(app.getApp_name())) {
            app.setFlag(true);
        }
        if (app.isFlag()) {
            mViewHolder.btn_install.setText(this.mContext.getResources().getString(2131100743));
        } else {
            mViewHolder.btn_install.setText(this.mContext.getResources().getString(2131100742));
        }
        mViewHolder.btn_install.setTag(Boolean.valueOf(app.isFlag()));
        mViewHolder.mainTitle_1.setText(app.getName());
        mViewHolder.subTitle_1.setText(app.getDesc());
        ImageDownloader.getInstance().download(mViewHolder.iv_1, app.getImgUrl());
        mViewHolder.relativeLayout1.setTag(Integer.valueOf(position + 1));
        mViewHolder.btn_install.setOnClickListener(new 1(this, app, mViewHolder));
        return convertView;
    }

    private String getWhichListView(int whichList) {
        String whichListView = CategoryCode.RECOMMEND_RECOMMEND_INSTALLED;
        switch (whichList) {
            case 1:
                return CategoryCode.RECOMMEND_RECOMMEND_INSTALLED;
            case 2:
                return CategoryCode.RECOMMEND_RECOMMEND_GAMES;
            case 3:
                return "44";
            default:
                return whichListView;
        }
    }

    private void clearViewData(ViewHolder mViewHolder) {
        if (mViewHolder.iv_1 != null) {
            mViewHolder.iv_1.setImageDrawable(null);
        }
    }

    public void addList(List list) {
        if (this.mList != null) {
            this.mList.addAll(list);
        } else {
            setList(list);
        }
    }

    public void setdatas(Set<String> datas) {
        this.infodatas = datas;
    }
}
