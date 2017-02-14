package com.letv.android.client.commonlib.adapter;

import android.content.Context;
import android.widget.BaseAdapter;
import com.letv.core.utils.BaseTypeUtils;
import java.util.ArrayList;
import java.util.List;

public abstract class LetvBaseAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected int mItemCountInOneLine = 1;
    protected List<T> mList = new ArrayList();

    public LetvBaseAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void addList(List<T> list) {
        if (!BaseTypeUtils.isListEmpty(list)) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void setData(T t) {
        if (t != null) {
            this.mList.clear();
            this.mList.add(t);
            notifyDataSetChanged();
        }
    }

    public void addData(T t) {
        if (t != null) {
            this.mList.add(t);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    public int getCount() {
        if (BaseTypeUtils.isListEmpty(this.mList) || this.mItemCountInOneLine == 0) {
            return 0;
        }
        return getCount(this.mList, this.mItemCountInOneLine);
    }

    protected int getCount(List<?> list, int itemCountInOneLine) {
        return ((list.size() + itemCountInOneLine) - 1) / itemCountInOneLine;
    }

    public T getItem(int position) {
        if (this.mItemCountInOneLine == 1) {
            return this.mList.get(position);
        }
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
}
