package com.letv.android.client.widget.videoshot;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MyDialog extends Dialog {
    private static int default_height = LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT;
    private static int default_width = 160;

    public MyDialog(Context context, View layout, int style) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, default_width, default_height, layout, style);
    }

    public MyDialog(Context context, int width, int height, View layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        LayoutParams params = window.getAttributes();
        params.gravity = 17;
        window.setAttributes(params);
    }
}
