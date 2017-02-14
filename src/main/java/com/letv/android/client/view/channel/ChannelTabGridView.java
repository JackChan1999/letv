package com.letv.android.client.view.channel;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;
import com.letv.android.remotedevice.Constant.ControlAction;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.tencent.open.yyb.TitleBar;

public class ChannelTabGridView extends GridView {
    private int mDownX;
    private int mDownY;
    private ViewPager mView;

    public ChannelTabGridView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null);
    }

    public ChannelTabGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFootView(ViewPager view) {
        this.mView = view;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0:
                LogInfo.log("zhaoxiang", ControlAction.ACTION_KEY_DOWN + ev.getX());
                this.mDownX = (int) ev.getX();
                this.mDownY = (int) ev.getY();
                break;
            case 1:
            case 3:
                this.mDownY = 0;
                this.mDownX = 0;
                break;
            case 2:
                if (Math.abs(ev.getX() - ((float) this.mDownX)) > ((float) UIsUtils.dipToPx(TitleBar.BACKBTN_LEFT_MARGIN)) || Math.abs(ev.getY() - ((float) this.mDownY)) > Math.abs(ev.getX() - ((float) this.mDownX))) {
                    if (this.mView == null) {
                        return false;
                    }
                    this.mView.onInterceptTouchEvent(ev);
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
