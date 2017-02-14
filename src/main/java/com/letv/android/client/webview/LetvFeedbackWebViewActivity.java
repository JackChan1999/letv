package com.letv.android.client.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.letv.core.utils.LetvUtils;

public class LetvFeedbackWebViewActivity extends LetvBaseWebViewActivity {
    public static void launch(Context context, String url) {
        if (!LetvUtils.isGooglePlay()) {
            Intent intent = new Intent(context, LetvFeedbackWebViewActivity.class);
            intent.putExtra("url", url);
            context.startActivity(intent);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onResume() {
        super.onResume();
        this.close.setVisibility(0);
        this.back_iv.setVisibility(8);
        this.titleView.setWidth((getWindowManager().getDefaultDisplay().getWidth() * 2) / 3);
    }
}
