package com.letv.business.flow.album.listener;

import com.letv.business.flow.album.listener.LoadLayoutFragmentListener.IpErrorArea;
import com.letv.core.bean.VideoBean;

public class SimpleLoadLayoutFragmentListener implements LoadLayoutFragmentListener {
    public boolean isLoadingShow() {
        return false;
    }

    public boolean isErrorTagShow() {
        return false;
    }

    public int getErrState() {
        return 0;
    }

    public void finish() {
    }

    public void noPlay() {
    }

    public void loading() {
    }

    public void loading(int showTextResId) {
    }

    public void loading(String loadingMsg) {
    }

    public void loading(boolean showText, String loadingMsg, boolean showWillPlay) {
    }

    public void requestError(String msg, String errorCode, String btnMsg) {
    }

    public void jumpError(int value) {
    }

    public void jumpError(String title, String msg, boolean showBtn) {
    }

    public void ipError(String msg, IpErrorArea area) {
    }

    public void autoJumpWeb(VideoBean video) {
    }

    public void onLeboxErr(String msg) {
    }
}
