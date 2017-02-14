package com.letv.android.client.widget;

import com.media.ffmpeg.FFMpegPlayer;

public interface PlayBlock$PlayBlockCallback {
    void blockTwiceAlert();

    void onBlock(FFMpegPlayer fFMpegPlayer, int i);
}
