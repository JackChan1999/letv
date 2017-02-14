package com.letv.android.client.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import com.letv.android.client.adapter.LoopPagerAdapter;
import com.letv.android.client.commonlib.adapter.LetvBasePagerAdapter;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.List;

public abstract class LoopViewPager<T> extends LetvBaseViewPager<T> {
    protected OnPageChangeListener mOnPageChangeListener;
    protected int mPosition;

    public LoopViewPager(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnPageChangeListener(new 1(this));
    }

    public void setLoopOnPageChangeListener(OnPageChangeListener listener) {
        this.mOnPageChangeListener = listener;
    }

    public void setDataForPager(List<T> list, int position, LetvBasePagerAdapter<T> adapter) {
        LoopPagerAdapter _adapter = new LoopPagerAdapter(adapter);
        this.mList = list;
        setAdapter(_adapter);
        setCurrentItem(position, false);
    }

    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        setCurrentItem(0);
    }

    public void next() {
        if (getAdapter() != null && getAdapter().getCount() > 0) {
            super.setCurrentItem(super.getCurrentItem() + 1, true);
        }
    }

    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        if (getAdapter() != null && getAdapter().getCount() > 0) {
            item = getOffsetAmount() + (item % getAdapter().getCount());
        }
        super.setCurrentItem(item, smoothScroll);
    }

    public int getCurrentItem() {
        int position = super.getCurrentItem();
        if (getAdapter() instanceof LoopPagerAdapter) {
            return position % ((LoopPagerAdapter) getAdapter()).getRealCount();
        }
        return super.getCurrentItem();
    }

    private int getOffsetAmount() {
        if (getAdapter() instanceof LoopPagerAdapter) {
            return ((LoopPagerAdapter) getAdapter()).getRealCount() * 100;
        }
        return 0;
    }
}
