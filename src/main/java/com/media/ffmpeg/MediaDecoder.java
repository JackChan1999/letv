package com.media.ffmpeg;

import android.view.Surface;
import java.io.IOException;

public interface MediaDecoder {
    public static final int NOT_SUPPORT_TS = -1;
    public static final int TS_180_KTS = 128;
    public static final int TS_1_1_MTS = 16;
    public static final int TS_300KTS = 4;
    public static final int TS_600KTS = 8;
    public static final String VERSION = "20140428";

    int createDecoder(int i, int i2, Surface surface) throws IOException;

    int fillInputBuffer(byte[] bArr, long j, int i);

    int flushCodec();

    int getCapbility();

    void setPlayer(FFMpegPlayer fFMpegPlayer);

    void stopCodec();
}
