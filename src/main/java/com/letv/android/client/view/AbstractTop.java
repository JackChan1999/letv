package com.letv.android.client.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.utils.UIs;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public abstract class AbstractTop extends RelativeLayout implements OnClickListener {
    protected Context context;

    protected abstract void init();

    public AbstractTop(Context context, AttributeSet attrs, int defStyle) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public AbstractTop(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public AbstractTop(Context context) {
        super(context);
        this.context = context;
        init();
    }

    protected void setTextView(View view, int textId) {
        ((TextView) view.findViewById(textId)).setText(textId);
    }

    protected void setImageView(View view, int imageId) {
        ((ImageView) view.findViewById(imageId)).setImageResource(imageId);
    }

    protected void setOnClickListener(View view, int viewId) {
        view.findViewById(viewId).setOnClickListener(this);
    }

    protected View getTopView(int resource) {
        return UIs.inflate(this.context, resource, null);
    }
}
