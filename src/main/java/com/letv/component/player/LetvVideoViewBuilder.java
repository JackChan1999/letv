package com.letv.component.player;

import android.content.Context;
import com.letv.component.player.core.Configuration;
import com.letv.component.player.utils.LogTag;
import com.letv.component.player.videoview.VideoViewH264LeMobile;
import com.letv.component.player.videoview.VideoViewH264m3u8;
import com.letv.component.player.videoview.VideoViewH264m3u8Hw;
import com.letv.component.player.videoview.VideoViewH264m3u8HwLeMobile;
import com.letv.component.player.videoview.VideoViewH264mp4;
import com.letv.component.player.videoview.VideoViewTV;

public class LetvVideoViewBuilder {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$letv$component$player$LetvVideoViewBuilder$Type;
    private static LetvVideoViewBuilder mLetvVideoViewBuilder = null;

    public enum Type {
        TV,
        MOBILE_H264_MP4,
        MOBILE_H264_M3U8,
        MOBILE_H264_M3U8_HW,
        MOBILE_H264_LE_MOBILE,
        MOBILE_H264_M3U8_HW_LE_MOBILE
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$letv$component$player$LetvVideoViewBuilder$Type() {
        int[] iArr = $SWITCH_TABLE$com$letv$component$player$LetvVideoViewBuilder$Type;
        if (iArr == null) {
            iArr = new int[Type.values().length];
            try {
                iArr[Type.MOBILE_H264_LE_MOBILE.ordinal()] = 5;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Type.MOBILE_H264_M3U8.ordinal()] = 3;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Type.MOBILE_H264_M3U8_HW.ordinal()] = 4;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[Type.MOBILE_H264_M3U8_HW_LE_MOBILE.ordinal()] = 6;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[Type.MOBILE_H264_MP4.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[Type.TV.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            $SWITCH_TABLE$com$letv$component$player$LetvVideoViewBuilder$Type = iArr;
        }
        return iArr;
    }

    public static LetvVideoViewBuilder getInstants() {
        if (mLetvVideoViewBuilder == null) {
            createBuilder();
        }
        return mLetvVideoViewBuilder;
    }

    private static synchronized void createBuilder() {
        synchronized (LetvVideoViewBuilder.class) {
            if (mLetvVideoViewBuilder == null) {
                mLetvVideoViewBuilder = new LetvVideoViewBuilder();
            }
        }
    }

    private LetvVideoViewBuilder() {
    }

    public LetvMediaPlayerControl build(Context context, Type type) {
        LogTag.i("Create player, type=" + (type != null ? type.toString() : "null") + ", version=" + Configuration.SDK_VERSION_CODE);
        switch ($SWITCH_TABLE$com$letv$component$player$LetvVideoViewBuilder$Type()[type.ordinal()]) {
            case 1:
                return new VideoViewTV(context);
            case 2:
                return new VideoViewH264mp4(context);
            case 3:
                return new VideoViewH264m3u8(context);
            case 4:
                return new VideoViewH264m3u8Hw(context);
            case 5:
                return new VideoViewH264LeMobile(context);
            case 6:
                return new VideoViewH264m3u8HwLeMobile(context);
            default:
                return null;
        }
    }

    public Type getType(LetvMediaPlayerControl control) {
        Type type = Type.MOBILE_H264_MP4;
        if (control instanceof VideoViewH264m3u8Hw) {
            return Type.MOBILE_H264_M3U8_HW;
        }
        if (control instanceof VideoViewH264m3u8) {
            return Type.MOBILE_H264_M3U8;
        }
        if (control instanceof VideoViewTV) {
            return Type.TV;
        }
        if (control instanceof VideoViewH264LeMobile) {
            return Type.MOBILE_H264_LE_MOBILE;
        }
        if (control instanceof VideoViewH264m3u8HwLeMobile) {
            return Type.MOBILE_H264_M3U8_HW_LE_MOBILE;
        }
        return type;
    }
}
