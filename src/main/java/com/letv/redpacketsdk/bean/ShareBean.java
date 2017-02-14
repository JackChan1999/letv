package com.letv.redpacketsdk.bean;

import android.text.TextUtils;
import com.letv.redpacketsdk.utils.LogInfo;

public class ShareBean {
    public String sharePic;
    public String shareTitle;
    public String shareUrl;

    public boolean isEmpty() {
        LogInfo.log("ShareBean", "ShareBean url=" + this.shareUrl + "; \npic=" + this.sharePic + ";\ntitle=" + this.shareTitle);
        return TextUtils.isEmpty(this.shareTitle) || TextUtils.isEmpty(this.sharePic) || TextUtils.isEmpty(this.shareUrl);
    }
}
