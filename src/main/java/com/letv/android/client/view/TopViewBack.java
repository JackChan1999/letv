package com.letv.android.client.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.utils.UIs;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class TopViewBack extends AbstractTop {
    private boolean isFromHomePage;
    private ImageView topButton;
    private TextView topTitle;

    public TopViewBack(Context context, AttributeSet attrs, int defStyle) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context, attrs, defStyle);
        this.isFromHomePage = false;
    }

    public TopViewBack(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isFromHomePage = false;
    }

    public TopViewBack(Context context) {
        super(context);
        this.isFromHomePage = false;
    }

    protected void init() {
        View topView = UIs.inflate(this.context, R.layout.top_view_back, null);
        this.topTitle = (TextView) topView.findViewById(R.id.top_title);
        this.topButton = (ImageView) topView.findViewById(R.id.top_button);
        this.topButton.setOnClickListener(this);
        addView(topView);
    }

    public void setTitle(int titleId) {
        this.topTitle.setText(titleId);
    }

    public void setTitle(String title) {
        this.topTitle.setText(title);
    }

    public void setFromHomePage(boolean isFromHomePage) {
        this.isFromHomePage = isFromHomePage;
    }

    public void onClick(View arg0) {
        if (this.context instanceof Activity) {
            this.context.finish();
        }
    }
}
