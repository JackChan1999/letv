package com.letv.android.client.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import com.letv.android.client.commonlib.adapter.LetvBasePagerAdapter;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.List;

public abstract class LetvBaseViewPager<T> extends ViewPager {
    protected Context mContext;
    protected List<T> mList;
    protected LetvBaseViewPager<T> me;

    protected abstract View fetchView(T t);

    public LetvBaseViewPager(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null);
    }

    public LetvBaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.me = this;
        this.mContext = context;
    }

    public void next() {
        int next = super.getCurrentItem() + 1;
        if (getAdapter() != null && getAdapter().getCount() > next) {
            setCurrentItem(next, true);
        }
    }

    public void setDataForPager(List<T> list) {
        setDataForPager(list, 0);
    }

    public void setDataForPager(List<T> list, int position) {
        setDataForPager(list, position, fetchAdapter(this.mContext, list));
    }

    public void setDataForPager(List<T> list, int position, LetvBasePagerAdapter<T> adapter) {
        this.mList = list;
        setAdapter(adapter);
        setCurrentItem(position, false);
    }

    public LetvBasePagerAdapter<T> fetchAdapter(Context context, List<T> list) {
        return new 1(this, context, list);
    }
}
