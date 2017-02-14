package com.letv.android.client.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public abstract class BaseFloatViewLayout extends RelativeLayout {
    public abstract void clearVideos();

    public abstract void hide();

    public abstract void setCallBackListener(CallBackListener callBackListener);

    public abstract void show();

    public BaseFloatViewLayout(Context context, AttributeSet attrs, int defStyle) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context, attrs, defStyle);
    }

    public BaseFloatViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseFloatViewLayout(Context context) {
        super(context);
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }
}
