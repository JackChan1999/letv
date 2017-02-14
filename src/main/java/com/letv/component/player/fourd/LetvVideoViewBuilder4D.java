package com.letv.component.player.fourd;

import android.content.Context;
import com.letv.component.player.LetvMediaPlayerControl;
import com.letv.component.player.fourd.videoview.VideoViewH264LeMobile_4D;
import com.letv.component.player.fourd.videoview.VideoViewH264m3u8Hw_4D;
import com.letv.component.player.fourd.videoview.VideoViewH264m3u8_4D;
import com.letv.component.player.fourd.videoview.VideoViewH264mp4_4D;
import com.letv.component.player.utils.LogTag;

public class LetvVideoViewBuilder4D {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$letv$component$player$fourd$LetvVideoViewBuilder4D$Type;
    private static LetvVideoViewBuilder4D mLetvVideoViewBuilder = null;

    public enum Type {
        MOBILE_H264_MP4_4D,
        MOBILE_H264_M3U8_4D,
        MOBILE_H264_M3U8_HW_4D,
        MOBILE_H264_LE_MOBILE
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$letv$component$player$fourd$LetvVideoViewBuilder4D$Type() {
        int[] iArr = $SWITCH_TABLE$com$letv$component$player$fourd$LetvVideoViewBuilder4D$Type;
        if (iArr == null) {
            iArr = new int[Type.values().length];
            try {
                iArr[Type.MOBILE_H264_LE_MOBILE.ordinal()] = 4;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Type.MOBILE_H264_M3U8_4D.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Type.MOBILE_H264_M3U8_HW_4D.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[Type.MOBILE_H264_MP4_4D.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            $SWITCH_TABLE$com$letv$component$player$fourd$LetvVideoViewBuilder4D$Type = iArr;
        }
        return iArr;
    }

    public static LetvVideoViewBuilder4D getInstants() {
        if (mLetvVideoViewBuilder == null) {
            createBuilder();
        }
        return mLetvVideoViewBuilder;
    }

    private static synchronized void createBuilder() {
        synchronized (LetvVideoViewBuilder4D.class) {
            if (mLetvVideoViewBuilder == null) {
                mLetvVideoViewBuilder = new LetvVideoViewBuilder4D();
            }
        }
    }

    private LetvVideoViewBuilder4D() {
    }

    public LetvMediaPlayerControl4D build(Context context, Type type) {
        LogTag.i("Create player, type=" + (type != null ? type.toString() : "null") + ", version=" + 1.4f);
        switch ($SWITCH_TABLE$com$letv$component$player$fourd$LetvVideoViewBuilder4D$Type()[type.ordinal()]) {
            case 1:
                return new VideoViewH264mp4_4D(context);
            case 2:
                return new VideoViewH264m3u8_4D(context);
            case 3:
                return new VideoViewH264m3u8Hw_4D(context);
            case 4:
                return new VideoViewH264LeMobile_4D(context);
            default:
                return null;
        }
    }

    public Type getType(LetvMediaPlayerControl control) {
        Type type = Type.MOBILE_H264_MP4_4D;
        if (control instanceof VideoViewH264m3u8Hw_4D) {
            return Type.MOBILE_H264_M3U8_HW_4D;
        }
        if (control instanceof VideoViewH264m3u8_4D) {
            return Type.MOBILE_H264_M3U8_4D;
        }
        return type;
    }
}
