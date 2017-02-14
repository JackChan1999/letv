package master.flame.danmaku.danmaku.parser;

import java.lang.reflect.Array;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.FBDanmaku;
import master.flame.danmaku.danmaku.model.FTDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakuIterator;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.L2RDanmaku;
import master.flame.danmaku.danmaku.model.R2LDanmaku;
import master.flame.danmaku.danmaku.model.SpecialDanmaku;
import master.flame.danmaku.danmaku.model.SpecialDanmaku.LinePath;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;

public class DanmakuFactory {
    public static final float BILI_PLAYER_HEIGHT = 438.0f;
    public static final float BILI_PLAYER_WIDTH = 682.0f;
    public static final long COMMON_DANMAKU_DURATION = 3800;
    public static final int DANMAKU_MEDIUM_TEXTSIZE = 25;
    public static final long MAX_DANMAKU_DURATION_HIGH_DENSITY = 9000;
    public static final long MIN_DANMAKU_DURATION = 4000;
    public static final float OLD_BILI_PLAYER_HEIGHT = 385.0f;
    public static final float OLD_BILI_PLAYER_WIDTH = 539.0f;
    public int CURRENT_DISP_HEIGHT = 0;
    private float CURRENT_DISP_SIZE_FACTOR = 1.0f;
    public int CURRENT_DISP_WIDTH = 0;
    public long MAX_DANMAKU_DURATION = MIN_DANMAKU_DURATION;
    public Duration MAX_Duration_Fix_Danmaku;
    public Duration MAX_Duration_Scroll_Danmaku;
    public Duration MAX_Duration_Special_Danmaku;
    public long REAL_DANMAKU_DURATION = COMMON_DANMAKU_DURATION;
    private DanmakuContext sLastConfig;
    public IDisplayer sLastDisp;
    public IDanmakus sSpecialDanmakus = new Danmakus();

    public void resetDurationsData() {
        this.sLastDisp = null;
        this.CURRENT_DISP_HEIGHT = 0;
        this.CURRENT_DISP_WIDTH = 0;
        this.sSpecialDanmakus.clear();
        this.MAX_Duration_Scroll_Danmaku = null;
        this.MAX_Duration_Fix_Danmaku = null;
        this.MAX_Duration_Special_Danmaku = null;
        this.MAX_DANMAKU_DURATION = MIN_DANMAKU_DURATION;
    }

    public void notifyDispSizeChanged(DanmakuContext context) {
        this.sLastConfig = context;
        this.sLastDisp = context.getDisplayer();
        createDanmaku(1, context);
    }

    public BaseDanmaku createDanmaku(int type) {
        return createDanmaku(type, this.sLastConfig);
    }

    public BaseDanmaku createDanmaku(int type, DanmakuContext context) {
        if (context == null) {
            return null;
        }
        this.sLastConfig = context;
        this.sLastDisp = context.getDisplayer();
        return createDanmaku(type, this.sLastDisp.getWidth(), this.sLastDisp.getHeight(), this.CURRENT_DISP_SIZE_FACTOR, context.scrollSpeedFactor);
    }

    public BaseDanmaku createDanmaku(int type, IDisplayer disp, float viewportScale, float scrollSpeedFactor) {
        if (disp == null) {
            return null;
        }
        this.sLastDisp = disp;
        return createDanmaku(type, disp.getWidth(), disp.getHeight(), viewportScale, scrollSpeedFactor);
    }

    public BaseDanmaku createDanmaku(int type, int viewportWidth, int viewportHeight, float viewportScale, float scrollSpeedFactor) {
        return createDanmaku(type, (float) viewportWidth, (float) viewportHeight, viewportScale, scrollSpeedFactor);
    }

    public BaseDanmaku createDanmaku(int type, float viewportWidth, float viewportHeight, float viewportSizeFactor, float scrollSpeedFactor) {
        boolean sizeChanged = updateViewportState(viewportWidth, viewportHeight, viewportSizeFactor);
        if (this.MAX_Duration_Scroll_Danmaku == null) {
            this.MAX_Duration_Scroll_Danmaku = new Duration(this.REAL_DANMAKU_DURATION);
            this.MAX_Duration_Scroll_Danmaku.setFactor(scrollSpeedFactor);
        } else if (sizeChanged) {
            this.MAX_Duration_Scroll_Danmaku.setValue(this.REAL_DANMAKU_DURATION);
        }
        if (this.MAX_Duration_Fix_Danmaku == null) {
            this.MAX_Duration_Fix_Danmaku = new Duration(COMMON_DANMAKU_DURATION);
        }
        if (sizeChanged && viewportWidth > 0.0f) {
            updateMaxDanmakuDuration();
            float scaleX = 1.0f;
            float scaleY = 1.0f;
            if (this.CURRENT_DISP_WIDTH > 0 && this.CURRENT_DISP_HEIGHT > 0) {
                scaleX = viewportWidth / ((float) this.CURRENT_DISP_WIDTH);
                scaleY = viewportHeight / ((float) this.CURRENT_DISP_HEIGHT);
            }
            if (viewportHeight > 0.0f) {
                updateSpecialDanmakusDate(scaleX, scaleY);
            }
        }
        switch (type) {
            case 1:
                return new R2LDanmaku(this.MAX_Duration_Scroll_Danmaku);
            case 4:
                return new FBDanmaku(this.MAX_Duration_Fix_Danmaku);
            case 5:
                return new FTDanmaku(this.MAX_Duration_Fix_Danmaku);
            case 6:
                return new L2RDanmaku(this.MAX_Duration_Scroll_Danmaku);
            case 7:
                BaseDanmaku instance = new SpecialDanmaku();
                this.sSpecialDanmakus.addItem(instance);
                return instance;
            default:
                return null;
        }
    }

    public boolean updateViewportState(float viewportWidth, float viewportHeight, float viewportSizeFactor) {
        if (this.CURRENT_DISP_WIDTH == ((int) viewportWidth) && this.CURRENT_DISP_HEIGHT == ((int) viewportHeight) && this.CURRENT_DISP_SIZE_FACTOR == viewportSizeFactor) {
            return false;
        }
        this.REAL_DANMAKU_DURATION = (long) (3800.0f * ((viewportSizeFactor * viewportWidth) / BILI_PLAYER_WIDTH));
        this.REAL_DANMAKU_DURATION = Math.min(MAX_DANMAKU_DURATION_HIGH_DENSITY, this.REAL_DANMAKU_DURATION);
        this.REAL_DANMAKU_DURATION = Math.max(MIN_DANMAKU_DURATION, this.REAL_DANMAKU_DURATION);
        this.CURRENT_DISP_WIDTH = (int) viewportWidth;
        this.CURRENT_DISP_HEIGHT = (int) viewportHeight;
        this.CURRENT_DISP_SIZE_FACTOR = viewportSizeFactor;
        return true;
    }

    private void updateSpecialDanmakusDate(float scaleX, float scaleY) {
        IDanmakuIterator it = this.sSpecialDanmakus.iterator();
        while (it.hasNext()) {
            SpecialDanmaku speicalDanmaku = (SpecialDanmaku) it.next();
            fillTranslationData(speicalDanmaku, speicalDanmaku.beginX, speicalDanmaku.beginY, speicalDanmaku.endX, speicalDanmaku.endY, speicalDanmaku.translationDuration, speicalDanmaku.translationStartDelay, scaleX, scaleY);
            LinePath[] linePaths = speicalDanmaku.linePaths;
            if (linePaths != null && linePaths.length > 0) {
                int length = linePaths.length;
                float[][] points = (float[][]) Array.newInstance(Float.TYPE, new int[]{length + 1, 2});
                for (int j = 0; j < length; j++) {
                    points[j] = linePaths[j].getBeginPoint();
                    points[j + 1] = linePaths[j].getEndPoint();
                }
                fillLinePathData(speicalDanmaku, points, scaleX, scaleY);
            }
        }
    }

    public void updateMaxDanmakuDuration() {
        long maxScrollDuration = this.MAX_Duration_Scroll_Danmaku == null ? 0 : this.MAX_Duration_Scroll_Danmaku.value;
        long maxFixDuration = this.MAX_Duration_Fix_Danmaku == null ? 0 : this.MAX_Duration_Fix_Danmaku.value;
        long maxSpecialDuration = this.MAX_Duration_Special_Danmaku == null ? 0 : this.MAX_Duration_Special_Danmaku.value;
        this.MAX_DANMAKU_DURATION = Math.max(maxScrollDuration, maxFixDuration);
        this.MAX_DANMAKU_DURATION = Math.max(this.MAX_DANMAKU_DURATION, maxSpecialDuration);
        this.MAX_DANMAKU_DURATION = Math.max(COMMON_DANMAKU_DURATION, this.MAX_DANMAKU_DURATION);
        this.MAX_DANMAKU_DURATION = Math.max(this.REAL_DANMAKU_DURATION, this.MAX_DANMAKU_DURATION);
    }

    public void updateDurationFactor(float f) {
        if (this.MAX_Duration_Scroll_Danmaku != null && this.MAX_Duration_Fix_Danmaku != null) {
            this.MAX_Duration_Scroll_Danmaku.setFactor(f);
            updateMaxDanmakuDuration();
        }
    }

    public void fillTranslationData(BaseDanmaku item, float beginX, float beginY, float endX, float endY, long translationDuration, long translationStartDelay, float scaleX, float scaleY) {
        if (item.getType() == 7) {
            ((SpecialDanmaku) item).setTranslationData(beginX * scaleX, beginY * scaleY, endX * scaleX, endY * scaleY, translationDuration, translationStartDelay);
            updateSpecicalDanmakuDuration(item);
        }
    }

    public static void fillLinePathData(BaseDanmaku item, float[][] points, float scaleX, float scaleY) {
        if (item.getType() == 7 && points.length != 0 && points[0].length == 2) {
            for (int i = 0; i < points.length; i++) {
                float[] fArr = points[i];
                fArr[0] = fArr[0] * scaleX;
                fArr = points[i];
                fArr[1] = fArr[1] * scaleY;
            }
            ((SpecialDanmaku) item).setLinePathData(points);
        }
    }

    public void fillAlphaData(BaseDanmaku item, int beginAlpha, int endAlpha, long alphaDuraion) {
        if (item.getType() == 7) {
            ((SpecialDanmaku) item).setAlphaData(beginAlpha, endAlpha, alphaDuraion);
            updateSpecicalDanmakuDuration(item);
        }
    }

    private void updateSpecicalDanmakuDuration(BaseDanmaku item) {
        if (this.MAX_Duration_Special_Danmaku == null || (item.duration != null && item.duration.value > this.MAX_Duration_Special_Danmaku.value)) {
            this.MAX_Duration_Special_Danmaku = item.duration;
            updateMaxDanmakuDuration();
        }
    }
}
