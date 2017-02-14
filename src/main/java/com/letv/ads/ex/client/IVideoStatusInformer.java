package com.letv.ads.ex.client;

import android.graphics.Rect;

public interface IVideoStatusInformer {
    void OnActivityExit();

    void OnActivityPause();

    void OnActivityResume();

    void OnChangBitStream(String str);

    void OnVideoComplate();

    void OnVideoPause(boolean z);

    void OnVideoResize(Rect rect);

    void OnVideoResume(boolean z);

    void OnVideoStart(Boolean bool);

    void destory();

    void onControlPanelVisible(boolean z);

    void onDanmulVisible(boolean z);

    void onEpisodeVisible(boolean z);

    void onForceSkipPrerollAd(int i);

    void onVideoError();
}
