package master.flame.danmaku.danmaku.util;

import android.text.TextUtils;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DrawingCache;
import master.flame.danmaku.danmaku.model.android.DrawingCacheHolder;

public class DanmakuUtils {
    public static boolean willHitInDuration(IDisplayer disp, BaseDanmaku d1, BaseDanmaku d2, long duration, long currTime) {
        int type1 = d1.getType();
        if (type1 != d2.getType()) {
            return false;
        }
        if (d1.isOutside()) {
            return false;
        }
        long dTime = d2.time - d1.time;
        if (dTime < 0) {
            return true;
        }
        if (Math.abs(dTime) >= duration || d1.isTimeOut() || d2.isTimeOut()) {
            return false;
        }
        if (type1 == 5 || type1 == 4) {
            return true;
        }
        return checkHitAtTime(disp, d1, d2, currTime) || checkHitAtTime(disp, d1, d2, d1.time + d1.getDuration());
    }

    private static boolean checkHitAtTime(IDisplayer disp, BaseDanmaku d1, BaseDanmaku d2, long time) {
        float[] rectArr1 = d1.getRectAtTime(disp, time);
        float[] rectArr2 = d2.getRectAtTime(disp, time);
        if (rectArr1 == null || rectArr2 == null) {
            return false;
        }
        return checkHit(d1.getType(), d2.getType(), rectArr1, rectArr2);
    }

    private static boolean checkHit(int type1, int type2, float[] rectArr1, float[] rectArr2) {
        boolean z = true;
        if (type1 != type2) {
            return false;
        }
        if (type1 == 1) {
            if (rectArr2[0] >= rectArr1[2]) {
                z = false;
            }
            return z;
        } else if (type1 != 6) {
            return false;
        } else {
            if (rectArr2[2] <= rectArr1[0]) {
                z = false;
            }
            return z;
        }
    }

    public static DrawingCache buildDanmakuDrawingCache(BaseDanmaku danmaku, IDisplayer disp, DrawingCache cache) {
        if (cache == null) {
            cache = new DrawingCache();
        }
        cache.build((int) Math.ceil((double) danmaku.paintWidth), (int) Math.ceil((double) danmaku.paintHeight), disp.getDensityDpi(), false);
        DrawingCacheHolder holder = cache.get();
        if (holder != null) {
            ((AbsDisplayer) disp).drawDanmaku(danmaku, holder.canvas, 0.0f, 0.0f, true);
            if (disp.isHardwareAccelerated()) {
                holder.splitWith(disp.getWidth(), disp.getHeight(), disp.getMaximumCacheWidth(), disp.getMaximumCacheHeight());
            }
        }
        return cache;
    }

    public static int getCacheSize(int w, int h) {
        return (w * h) * 4;
    }

    public static final boolean isDuplicate(BaseDanmaku obj1, BaseDanmaku obj2) {
        if (obj1 == obj2) {
            return false;
        }
        if (obj1.text == obj2.text) {
            return true;
        }
        if (obj1.text == null || !obj1.text.equals(obj2.text)) {
            return false;
        }
        return true;
    }

    public static final int compare(BaseDanmaku obj1, BaseDanmaku obj2) {
        if (obj1 == obj2) {
            return 0;
        }
        if (obj1 == null) {
            return -1;
        }
        if (obj2 == null) {
            return 1;
        }
        long val = obj1.time - obj2.time;
        if (val > 0) {
            return 1;
        }
        if (val < 0) {
            return -1;
        }
        int result = obj1.index - obj2.index;
        if (result > 0) {
            return 1;
        }
        if (result < 0) {
            return -1;
        }
        result = obj1.getType() - obj2.getType();
        if (result > 0) {
            return 1;
        }
        if (result < 0 || obj1.text == null) {
            return -1;
        }
        if (obj2.text == null) {
            return 1;
        }
        int r = obj1.text.toString().compareTo(obj2.text.toString());
        if (r != 0) {
            return r;
        }
        r = obj1.textColor - obj2.textColor;
        if (r == 0) {
            r = obj1.index - obj2.index;
            if (r == 0) {
                return obj1.hashCode() - obj1.hashCode();
            }
            if (r >= 0) {
                return 1;
            }
            return -1;
        } else if (r >= 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public static final boolean isOverSize(IDisplayer disp, BaseDanmaku item) {
        return disp.isHardwareAccelerated() && (item.paintWidth > ((float) disp.getMaximumCacheWidth()) || item.paintHeight > ((float) disp.getMaximumCacheHeight()));
    }

    public static void fillText(BaseDanmaku danmaku, CharSequence text) {
        danmaku.text = text;
        if (!TextUtils.isEmpty(text) && text.toString().contains(BaseDanmaku.DANMAKU_BR_CHAR)) {
            String[] lines = String.valueOf(danmaku.text).split(BaseDanmaku.DANMAKU_BR_CHAR, -1);
            if (lines.length > 1) {
                danmaku.lines = lines;
            }
        }
    }
}
