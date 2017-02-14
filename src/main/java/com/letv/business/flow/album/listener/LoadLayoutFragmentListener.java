package com.letv.business.flow.album.listener;

import com.letv.core.bean.VideoBean;

public interface LoadLayoutFragmentListener {

    public enum IpErrorArea {
        CN,
        HK,
        OTHER
    }

    void autoJumpWeb(VideoBean videoBean);

    void finish();

    int getErrState();

    void ipError(String str, IpErrorArea ipErrorArea);

    boolean isErrorTagShow();

    boolean isLoadingShow();

    void jumpError(int i);

    void jumpError(String str, String str2, boolean z);

    void loading();

    void loading(int i);

    void loading(String str);

    void loading(boolean z, String str, boolean z2);

    void noPlay();

    void onLeboxErr(String str);

    void requestError(String str, String str2, String str3);
}
