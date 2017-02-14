package com.letv.component.player.hardwaredecode;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.Surface;
import com.media.ffmpeg.FFMpegPlayer;
import java.io.IOException;
import java.nio.ByteBuffer;

public class CodecWrapper {
    private static final int DATAT_ERROR = -3;
    private static final int DECODER_FAIL = 4;
    private static final int DECODER_TIMEOUT = 3;
    private static final int INPUTBUFFER_FULL = -1;
    private static final int IO_EXCEPTION = -2;
    public static final String LOG_TAG = "MediaCodecWrapper";
    private static final int OUTPUT_BUFFERS_CHANGED = 1;
    private static final int OUTPUT_FORMAT_CHANGED = 2;
    private static final int RENDER_SUCCESS = 0;
    private boolean bRender = false;
    public MediaCodec codec = null;
    public ByteBuffer[] codecInputBuffers = null;
    public ByteBuffer[] codecOutputBuffers = null;
    private int first_frame = 0;
    public MediaFormat format = null;
    BufferInfo info = new BufferInfo();
    final long kTimeOutUs = 10000;
    private int mHeight;
    FFMpegPlayer mPlayer = null;
    private Surface mSurface;
    private int mWidth;

    public void setPlayer(FFMpegPlayer player) {
        this.mPlayer = player;
    }

    public int createDecoder(int videoWidth, int videoHeight, Surface surface) throws IOException {
        Log.d(LOG_TAG, "start create decoder, videoWidth:" + String.valueOf(videoWidth) + "videoHeight:" + String.valueOf(videoHeight));
        if (videoWidth <= 0 || videoHeight <= 0) {
            Log.d(LOG_TAG, "invalid width or height");
            return -1;
        }
        this.mWidth = videoWidth;
        this.mHeight = videoHeight;
        this.mSurface = surface;
        this.codec = MediaCodec.createDecoderByType("video/avc");
        if (this.codec == null) {
            Log.d(LOG_TAG, "Hardware codec is not available");
            return -1;
        }
        try {
            this.format = MediaFormat.createVideoFormat("video/avc", videoWidth, videoHeight);
            Log.d(LOG_TAG, "configure mediacodec");
            this.codec.configure(this.format, surface, null, 0);
            this.codec.setVideoScalingMode(1);
            Log.d(LOG_TAG, "start mediacodec");
            this.codec.start();
            Log.d(LOG_TAG, "get input and output buffer");
            this.codecInputBuffers = this.codec.getInputBuffers();
            this.codecOutputBuffers = this.codec.getOutputBuffers();
            return 1;
        } catch (IllegalStateException e) {
            Log.d(LOG_TAG, "Exception catched in createDecoder");
            return -1;
        }
    }

    public void stopCodec() {
        if (this.codec != null) {
            try {
                Log.d(LOG_TAG, "start stop codec");
                flushCodec();
                this.codec.stop();
                this.codecInputBuffers = null;
                this.codecOutputBuffers = null;
                this.format = null;
                Log.d(LOG_TAG, "after stop codec, start release codec");
                this.codec.release();
                this.codec = null;
                Log.d(LOG_TAG, "end stop codec");
            } catch (IllegalStateException e) {
                Log.d(LOG_TAG, "Exception catched in stopCodec");
            }
        }
    }

    public int flushCodec() {
        if (this.codec != null) {
            try {
                int i;
                Log.d(LOG_TAG, "flushCodec");
                if (this.codecInputBuffers != null) {
                    for (i = 0; i < this.codecInputBuffers.length; i++) {
                        if (this.codecInputBuffers[i] != null) {
                            this.codecInputBuffers[i].clear();
                        }
                    }
                }
                if (this.codecOutputBuffers != null) {
                    while (true) {
                        i = this.codec.dequeueOutputBuffer(this.info, 10000);
                        if (i < 0) {
                            break;
                        }
                        Log.d(LOG_TAG, "Release " + i + " outbuffer while flushCodec!");
                        this.codec.releaseOutputBuffer(i, false);
                    }
                    for (i = 0; i < this.codecOutputBuffers.length; i++) {
                        if (this.codecOutputBuffers[i] != null) {
                            this.codecOutputBuffers[i].clear();
                        }
                    }
                }
                this.codec.flush();
            } catch (IllegalStateException e) {
                Log.d(LOG_TAG, "Exception catched in flushCodec");
            }
        }
        return 1;
    }

    public int fillInputBuffer(byte[] data, long pts, int flush) {
        int sampleSize = data.length;
        if (sampleSize <= 0) {
            return -3;
        }
        try {
            int inputBufIndex = this.codec.dequeueInputBuffer(10000);
            if (inputBufIndex >= 0) {
                ByteBuffer dstBuf = this.codecInputBuffers[inputBufIndex];
                if (dstBuf.capacity() < sampleSize) {
                    Log.d(LOG_TAG, "Input buffer too small " + dstBuf.capacity() + " vs " + sampleSize);
                    return getOutputBuffer();
                }
                dstBuf.clear();
                dstBuf.put(data);
                long presentationTimeUs = pts;
                if (pts < 0) {
                    presentationTimeUs = 0;
                }
                if (this.first_frame == 0) {
                    this.first_frame = 1;
                    this.codec.queueInputBuffer(inputBufIndex, 0, sampleSize, 1, 2);
                    return 0;
                }
                int i;
                MediaCodec mediaCodec = this.codec;
                if (false) {
                    i = 4;
                } else {
                    i = 0;
                }
                mediaCodec.queueInputBuffer(inputBufIndex, 0, sampleSize, presentationTimeUs, i);
                return getOutputBuffer();
            }
            getOutputBuffer();
            return -1;
        } catch (IllegalStateException e) {
            Log.d(LOG_TAG, "IllegalStateException catched in fillInputBuffer");
            return -2;
        }
    }

    public int getOutputBuffer() {
        int res = this.codec.dequeueOutputBuffer(this.info, 10000);
        if (res >= 0) {
            int ret;
            int outputBufIndex = res;
            int i = this.info.size;
            int sync_status = 0;
            if (this.mPlayer != null) {
                try {
                    sync_status = this.mPlayer._native_sync(this.info.presentationTimeUs);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sync_status > 0) {
                if (!this.bRender) {
                    this.mPlayer.startHwRender();
                    this.bRender = true;
                }
                this.codec.releaseOutputBuffer(outputBufIndex, true);
                ret = 0;
            } else if (sync_status == -1) {
                int i2;
                this.codec.releaseOutputBuffer(outputBufIndex, false);
                if (this.codecInputBuffers != null) {
                    for (i2 = 0; i2 < this.codecInputBuffers.length; i2++) {
                        if (this.codecInputBuffers[i2] != null) {
                            this.codecInputBuffers[i2].clear();
                        }
                    }
                }
                if (this.codecOutputBuffers != null) {
                    for (i2 = 0; i2 < this.codecOutputBuffers.length; i2++) {
                        if (this.codecOutputBuffers[i2] != null) {
                            this.codecOutputBuffers[i2].clear();
                        }
                    }
                }
                this.codec.flush();
                ret = 3;
            } else {
                this.codec.releaseOutputBuffer(outputBufIndex, false);
                ret = 3;
            }
            int i3 = this.info.flags;
            return ret;
        } else if (res == -3) {
            this.codecOutputBuffers = this.codec.getOutputBuffers();
            return 1;
        } else if (res != -2) {
            return 4;
        } else {
            MediaFormat oformat = this.codec.getOutputFormat();
            return 2;
        }
    }

    public static int getCapbility() {
        int maxProfile = 0;
        int tsType = -1;
        Log.d(LOG_TAG, "getCapbility()->Build.VERSION.SDK_INT:" + String.valueOf(VERSION.SDK_INT));
        if (VERSION.SDK_INT < 16) {
            return -1;
        }
        int mediaCodecListCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < mediaCodecListCount; i++) {
            MediaCodecInfo mediaCodecInfo = MediaCodecList.getCodecInfoAt(i);
            if (!(mediaCodecInfo.isEncoder() || mediaCodecInfo.getName().startsWith("OMX.google") || mediaCodecInfo.getName().startsWith("OMX.TI."))) {
                Log.d(LOG_TAG, "getCapbility()->name:" + mediaCodecInfo.getName());
                for (String type : mediaCodecInfo.getSupportedTypes()) {
                    if (type.contains("avc")) {
                        Log.d(LOG_TAG, "getCapbility()->type:" + type);
                        try {
                            CodecCapabilities codecCapabilities = mediaCodecInfo.getCapabilitiesForType(type);
                            for (int colorFormat : codecCapabilities.colorFormats) {
                                Log.d(LOG_TAG, "getCapbility()->Color Format: " + colorFormat + " " + colorFormatToString(colorFormat));
                            }
                            for (CodecProfileLevel codecProfileLevel : codecCapabilities.profileLevels) {
                                String level = "unknown type";
                                String sprofile = "unknown type";
                                Log.d(LOG_TAG, "getCapbility()->Codec Profile Level:" + avcLevelToString(codecProfileLevel.level) + " profile:" + avcProfileToString(codecProfileLevel.profile));
                                if (codecProfileLevel.profile > maxProfile) {
                                    maxProfile = codecProfileLevel.profile;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        Log.d(LOG_TAG, "getCapbility()->Max profile:" + maxProfile + " " + avcProfileToString(maxProfile));
        if (maxProfile >= 8) {
            tsType = 16;
        }
        return tsType;
    }

    public static int getAVCLevel() {
        int maxAVCLevel = 0;
        if (VERSION.SDK_INT >= 16) {
            int mediaCodecListCount = MediaCodecList.getCodecCount();
            for (int i = 0; i < mediaCodecListCount; i++) {
                MediaCodecInfo mediaCodecInfo = MediaCodecList.getCodecInfoAt(i);
                if (!(mediaCodecInfo.isEncoder() || mediaCodecInfo.getName().startsWith("OMX.google") || mediaCodecInfo.getName().startsWith("OMX.TI."))) {
                    Log.d(LOG_TAG, "getAVCLevel()->name:" + mediaCodecInfo.getName());
                    for (String type : mediaCodecInfo.getSupportedTypes()) {
                        if (type.contains("avc")) {
                            Log.d(LOG_TAG, "getAVCLevel()->type:" + type);
                            try {
                                CodecCapabilities codecCapabilities = mediaCodecInfo.getCapabilitiesForType(type);
                                for (int colorFormat : codecCapabilities.colorFormats) {
                                    Log.d(LOG_TAG, "getAVCLevel()->Color Format: " + colorFormat + " " + colorFormatToString(colorFormat));
                                }
                                for (CodecProfileLevel codecProfileLevel : codecCapabilities.profileLevels) {
                                    String level = "unknown type";
                                    String sprofile = "unknown type";
                                    Log.d(LOG_TAG, "getAVCLevel()->Codec Profile Level:" + avcLevelToString(codecProfileLevel.level) + " profile:" + avcProfileToString(codecProfileLevel.profile));
                                    if (codecProfileLevel.level > maxAVCLevel) {
                                        maxAVCLevel = codecProfileLevel.level;
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            Log.d(LOG_TAG, "getAVCLevel()->Max AVCLevel:" + maxAVCLevel + " " + avcProfileToString(maxAVCLevel));
        }
        return maxAVCLevel;
    }

    public static int getProfile() {
        int maxProfile = 0;
        Log.d(LOG_TAG, "getProfile()->Build.VERSION.SDK_INT:" + String.valueOf(VERSION.SDK_INT));
        if (VERSION.SDK_INT >= 16) {
            int mediaCodecListCount = MediaCodecList.getCodecCount();
            for (int i = 0; i < mediaCodecListCount; i++) {
                MediaCodecInfo mediaCodecInfo = MediaCodecList.getCodecInfoAt(i);
                if (!(mediaCodecInfo.isEncoder() || mediaCodecInfo.getName().startsWith("OMX.google") || mediaCodecInfo.getName().startsWith("OMX.TI."))) {
                    Log.d(LOG_TAG, "getProfile()->name:" + mediaCodecInfo.getName());
                    for (String type : mediaCodecInfo.getSupportedTypes()) {
                        if (type.contains("avc")) {
                            Log.d(LOG_TAG, "getProfile()->type:" + type);
                            try {
                                CodecCapabilities codecCapabilities = mediaCodecInfo.getCapabilitiesForType(type);
                                for (int colorFormat : codecCapabilities.colorFormats) {
                                    Log.d(LOG_TAG, "getProfile()->Color Format: " + colorFormat + " " + colorFormatToString(colorFormat));
                                }
                                for (CodecProfileLevel codecProfileLevel : codecCapabilities.profileLevels) {
                                    String level = "unknown type";
                                    String sprofile = "unknown type";
                                    Log.d(LOG_TAG, "getProfile()->Codec Profile Level:" + avcLevelToString(codecProfileLevel.level) + " profile:" + avcProfileToString(codecProfileLevel.profile));
                                    if (codecProfileLevel.profile > maxProfile) {
                                        maxProfile = codecProfileLevel.profile;
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            Log.d(LOG_TAG, "getProfile()->Max profile:" + maxProfile + " " + avcProfileToString(maxProfile));
        }
        return maxProfile;
    }

    private static String colorFormatToString(int colorFormat) {
        String ret = "not found(" + colorFormat + ")";
        switch (colorFormat) {
            case 1:
                return "COLOR_FormatMonochrome";
            case 2:
                return "COLOR_Format8bitRGB332";
            case 3:
                return "COLOR_Format12bitRGB444";
            case 4:
                return "COLOR_Format16bitARGB4444";
            case 5:
                return "COLOR_Format16bitARGB1555";
            case 6:
                return "COLOR_Format16bitRGB565";
            case 7:
                return "COLOR_Format16bitBGR565";
            case 8:
                return "COLOR_Format18bitRGB666";
            case 9:
                return "COLOR_Format18bitARGB1665";
            case 10:
                return "COLOR_Format19bitARGB1666";
            case 11:
                return "COLOR_Format24bitRGB888";
            case 12:
                return "COLOR_Format24bitBGR888";
            case 13:
                return "COLOR_Format24bitARGB1887";
            case 14:
                return "COLOR_Format25bitARGB1888";
            case 15:
                return "COLOR_Format32bitBGRA8888";
            case 16:
                return "COLOR_Format32bitARGB8888";
            case 17:
                return "COLOR_FormatYUV411Planar";
            case 18:
                return "COLOR_FormatYUV411PackedPlanar";
            case 19:
                return "COLOR_FormatYUV420Planar";
            case 20:
                return "COLOR_FormatYUV420PackedPlanar";
            case 21:
                return "COLOR_FormatYUV420SemiPlanar";
            case 22:
                return "COLOR_FormatYUV422Planar";
            case 23:
                return "COLOR_FormatYUV422PackedPlanar";
            case 24:
                return "COLOR_FormatYUV422SemiPlanar";
            case 25:
                return "COLOR_FormatYCbYCr";
            case 26:
                return "COLOR_FormatYCrYCb";
            case 27:
                return "COLOR_FormatCbYCrY";
            case 28:
                return "COLOR_FormatCrYCbY";
            case 29:
                return "COLOR_FormatYUV444Interleaved";
            case 30:
                return "COLOR_FormatRawBayer8bit";
            case 31:
                return "COLOR_FormatRawBayer10bit";
            case 32:
                return "COLOR_FormatRawBayer8bitcompressed";
            case 33:
                return "COLOR_FormatL2";
            case 34:
                return "COLOR_FormatL4";
            case 35:
                return "COLOR_FormatL8";
            case 36:
                return "COLOR_FormatL16";
            case 37:
                return "COLOR_FormatL24";
            case 38:
                return "COLOR_FormatL32";
            case 39:
                return "COLOR_FormatYUV420PackedSemiPlanar";
            case MotionEventCompat.AXIS_GENERIC_9 /*40*/:
                return "COLOR_FormatYUV422PackedSemiPlanar";
            case MotionEventCompat.AXIS_GENERIC_10 /*41*/:
                return "COLOR_Format18BitBGR666";
            case 42:
                return "COLOR_Format24BitARGB6666";
            case MotionEventCompat.AXIS_GENERIC_12 /*43*/:
                return "COLOR_Format24BitABGR6666";
            case 2130706688:
                return "COLOR_TI_FormatYUV420PackedSemiPlanar";
            case 2141391872:
                return "COLOR_QCOM_FormatYUV420SemiPlanar";
            default:
                return ret;
        }
    }

    private static String avcProfileToString(int profile) {
        String ret = "not found(" + profile + ")";
        switch (profile) {
            case 1:
                return "AVCProfileBaseline";
            case 2:
                return "AVCProfileMain";
            case 4:
                return "AVCProfileExtended";
            case 8:
                return "AVCProfileHigh";
            case 16:
                return "AVCProfileHigh10";
            case 32:
                return "AVCProfileHigh422";
            case 64:
                return "AVCProfileHigh444";
            default:
                return ret;
        }
    }

    private static String avcLevelToString(int level) {
        String ret = "not found(" + level + ")";
        switch (level) {
            case 1:
                return "AVCLevel1";
            case 2:
                return "AVCLevel1b";
            case 4:
                return "AVCLevel11";
            case 8:
                return "AVCLevel12";
            case 16:
                return "AVCLevel13";
            case 32:
                return "AVCLevel2";
            case 64:
                return "AVCLevel21";
            case 128:
                return "AVCLevel22";
            case 256:
                return "AVCLevel3";
            case 512:
                return "AVCLevel31";
            case 1024:
                return "AVCLevel32";
            case 2048:
                return "AVCLevel4";
            case 4096:
                return "AVCLevel41";
            case 8192:
                return "AVCLevel42";
            case 16384:
                return "AVCLevel5";
            case 32768:
                return "AVCLevel51";
            default:
                return ret;
        }
    }
}
