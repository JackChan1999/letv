package com.letv.lemallsdk.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.letv.core.parser.PushDataParser;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareManager {
    private static ShareManager manager;
    private Context context;
    private String shareContent;

    public static ShareManager getInstance(Context context) {
        if (manager == null) {
            manager = new ShareManager(context);
        } else {
            manager.context = context;
        }
        return manager;
    }

    private ShareManager(Context context) {
        this.context = context;
    }

    public void setShareData(String shareContent) {
        this.shareContent = shareContent;
    }

    public void showLayout(String title, String pic, String url) {
        Intent target = new Intent();
        target.setAction("android.intent.action.SEND");
        target.setType("text/plain");
        target.putExtra("leshareWebLinkUrl", url);
        target.putExtra("android.intent.extra.TITLE", "乐视商城");
        target.putExtra("android.intent.extra.TEXT", title);
        target.setFlags(268435456);
        this.context.startActivity(Intent.createChooser(target, "分享方式"));
    }

    public void showLayout() {
        if (!TextUtils.isEmpty(this.shareContent)) {
            String str = "";
            String str2 = "";
            String str3 = "";
            StringBuffer photos = new StringBuffer();
            try {
                JSONObject jsonObj = new JSONObject(this.shareContent);
                str = jsonObj.optString("title");
                str2 = jsonObj.optString(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT);
                str3 = jsonObj.optString("url");
                JSONArray jsonArray = jsonObj.optJSONArray("photos");
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        photos.append(jsonArray.optJSONObject(i).optString(PushDataParser.PICURL));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showLayout(new StringBuilder(String.valueOf(str)).append(str2).toString(), photos.toString(), str3);
        }
    }
}
