package com.letv.component.player.hardwaredecode;

import android.view.Surface;
import com.media.ffmpeg.FFMpegPlayer;
import com.media.ffmpeg.MediaDecoder;
import java.io.IOException;

public class MediaHardwareDecoder implements MediaDecoder {
    private CodecWrapper mDecoder = new CodecWrapper();

    public void setPlayer(FFMpegPlayer player) {
        this.mDecoder.setPlayer(player);
    }

    public int createDecoder(int videoWidth, int videoHeight, Surface surface) throws IOException {
        return this.mDecoder.createDecoder(videoWidth, videoHeight, surface);
    }

    public void stopCodec() {
        this.mDecoder.stopCodec();
    }

    public int flushCodec() {
        return this.mDecoder.flushCodec();
    }

    public int fillInputBuffer(byte[] data, long pts, int flush) {
        return this.mDecoder.fillInputBuffer(data, pts, flush);
    }

    public int getCapbility() {
        return CodecWrapper.getCapbility();
    }
}
