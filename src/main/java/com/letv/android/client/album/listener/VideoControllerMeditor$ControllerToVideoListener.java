package com.letv.android.client.album.listener;

import com.letv.component.player.LetvMediaPlayerControl;

public interface VideoControllerMeditor$ControllerToVideoListener {
    void buffTimeSchedule();

    LetvMediaPlayerControl getVideoView();

    void onSeekFinish(int i);

    void startHandlerTime();

    void stopHandlerTime();
}
