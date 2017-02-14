package com.letv.android.client.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.GarbageCleanActivity;
import com.letv.android.client.activity.GarbageCleanActivity.GARBAGE_FILE_TYPE;
import com.letv.android.client.activity.GarbageCleanActivity.GarbageFileInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

public class GarbageFileListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<GARBAGE_FILE_TYPE> mGarbageFileTypeSelectionList;
    private ArrayList<GarbageFileInfo> mList;

    public GarbageFileListAdapter(Context context, ArrayList<GarbageFileInfo> list) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mGarbageFileTypeSelectionList = null;
        this.mContext = context;
        this.mList = list;
    }

    public int getCount() {
        return this.mList.size();
    }

    public GarbageFileInfo getItem(int position) {
        return (GarbageFileInfo) this.mList.get(position);
    }

    public GarbageFileInfo getItem(GARBAGE_FILE_TYPE type) {
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            GarbageFileInfo info = (GarbageFileInfo) this.mList.get(i);
            if (type.equals(info.type)) {
                return info;
            }
        }
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        boolean z = true;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.garbage_file_item, null);
            ViewHolder.access$002(holder, (TextView) convertView.findViewById(R.id.tv_garbage_file_item_type));
            ViewHolder.access$102(holder, (TextView) convertView.findViewById(R.id.tv_garbage_file_item_size));
            ViewHolder.access$202(holder, (ImageView) convertView.findViewById(R.id.iv_garbage_file_selection));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!(this.mList == null || this.mList.get(position) == null)) {
            boolean z2;
            ViewHolder.access$000(holder).setText(getNameByType(((GarbageFileInfo) this.mList.get(position)).type));
            TextPaint tp = ViewHolder.access$000(holder).getPaint();
            if (position == 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            tp.setFakeBoldText(z2);
            tp = ViewHolder.access$100(holder).getPaint();
            if (position != 0) {
                z = false;
            }
            tp.setFakeBoldText(z);
            StringBuilder sb = new StringBuilder();
            sb.append(GarbageCleanActivity.transformShortType(((GarbageFileInfo) this.mList.get(position)).size));
            sb.append(GarbageCleanActivity.transformUnit(((GarbageFileInfo) this.mList.get(position)).size));
            ViewHolder.access$100(holder).setText(sb.toString());
        }
        if (this.mGarbageFileTypeSelectionList.contains(((GarbageFileInfo) this.mList.get(position)).type)) {
            ViewHolder.access$200(holder).setVisibility(0);
        } else {
            ViewHolder.access$200(holder).setVisibility(4);
        }
        return convertView;
    }

    public String getNameByType(GARBAGE_FILE_TYPE type) {
        String name = "";
        switch (1.$SwitchMap$com$letv$android$client$activity$GarbageCleanActivity$GARBAGE_FILE_TYPE[type.ordinal()]) {
            case 1:
                return this.mContext.getResources().getString(2131100145);
            case 2:
                return this.mContext.getResources().getString(2131100141);
            case 3:
                return this.mContext.getResources().getString(2131100143);
            case 4:
                return this.mContext.getResources().getString(2131100144);
            case 5:
                return this.mContext.getResources().getString(2131100146);
            case 6:
                return this.mContext.getResources().getString(2131100142);
            default:
                return name;
        }
    }

    public void setSelectionList(ArrayList<GARBAGE_FILE_TYPE> selectionList) {
        this.mGarbageFileTypeSelectionList = selectionList;
    }
}
