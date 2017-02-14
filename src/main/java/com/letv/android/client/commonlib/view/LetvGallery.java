package com.letv.android.client.commonlib.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ListView;
import com.letv.core.utils.LogInfo;
import com.tencent.open.yyb.TitleBar;
import java.util.ArrayList;

public class LetvGallery extends Gallery {
    private static final int MSG_GALLERY_IAMGE_MOVE = 4097;
    public static boolean isFling = false;
    private final String TAG = LetvGallery.class.getName();
    private int delayMillis = 5000;
    private float gTouchStartX;
    private float gTouchStartY;
    private boolean isRight = true;
    private OnItemSelectedListener itemSelectedListener = new 2(this);
    private ListView listView;
    private ArrayList<OnItemSelectedListener> listeners = new ArrayList();
    private Handler mHandler = new 1(this);
    private ViewPager pager;

    public LetvGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnItemSelectedListener(this.itemSelectedListener);
    }

    public void startMove(boolean isRight, int delayMillis) {
        this.isRight = isRight;
        this.mHandler.removeMessages(4097);
        this.mHandler.sendEmptyMessageDelayed(4097, (long) delayMillis);
    }

    public void stopMove() {
        this.mHandler.removeMessages(4097);
    }

    private void move2Right() {
        try {
            onScroll(null, null, 1.0f, 0.0f);
            onKeyDown(22, null);
        } catch (Exception e) {
        }
    }

    private void move2Left() {
        try {
            onScroll(null, null, -1.0f, 0.0f);
            onKeyDown(21, null);
        } catch (Exception e) {
        }
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int f = isScrollingLeft(e1, e2);
        if (f == -1) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
        if (isFling) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
        int event;
        if (f == 0) {
            event = 21;
        } else {
            event = 22;
        }
        onKeyDown(event, null);
        return false;
    }

    private int isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        if (e1 == null || e2 == null) {
            return -1;
        }
        return e2.getX() > e1.getX() ? 0 : 1;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogInfo.log(this.TAG, "LetvGallery---onInterceptTouchEvent");
        if (this.pager != null) {
            this.pager.requestDisallowInterceptTouchEvent(true);
        }
        if (this.listView != null) {
            this.listView.requestDisallowInterceptTouchEvent(true);
        }
        super.onTouchEvent(ev);
        switch (ev.getAction()) {
            case 0:
                this.gTouchStartX = ev.getX();
                this.gTouchStartY = ev.getY();
                return false;
            case 2:
                if (this.gTouchStartX == 0.0f || this.gTouchStartY == 0.0f || Math.abs(ev.getX() - this.gTouchStartX) < TitleBar.SHAREBTN_RIGHT_MARGIN) {
                    return false;
                }
                LogInfo.log(this.TAG, "LetvGallery---onInterceptTouchEvent:ev.getX() = " + ev.getX() + "; ev.getY() = " + ev.getY() + "; touchStartX = " + this.gTouchStartX + "; touchStartY = " + this.gTouchStartY + "; touchDistancesX" + (ev.getX() - this.gTouchStartX) + "; touchDistancesY = " + (ev.getY() - this.gTouchStartY));
                return true;
            default:
                return false;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        LogInfo.log(this.TAG, "LetvGallery---onTouchEvent");
        if (this.pager != null) {
            this.pager.requestDisallowInterceptTouchEvent(true);
        }
        if (this.listView != null) {
            this.listView.requestDisallowInterceptTouchEvent(true);
        }
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case 0:
                stopMove();
                break;
            case 1:
                startMove(this.isRight, this.delayMillis);
                break;
        }
        return false;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.pager != null) {
            this.pager.requestDisallowInterceptTouchEvent(true);
        }
        if (this.listView != null) {
            this.listView.requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setViewPager(ViewPager viewPager) {
        this.pager = viewPager;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void addSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.listeners.add(itemSelectedListener);
    }

    public void removeSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.listeners.remove(itemSelectedListener);
    }

    public void clearSelectedListener() {
        this.listeners.clear();
    }

    public void destroyView() {
        setAdapter(null);
        clearSelectedListener();
        removeAllViewsInLayout();
    }
}
