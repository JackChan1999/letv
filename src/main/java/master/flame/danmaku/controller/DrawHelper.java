package master.flame.danmaku.controller;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v4.internal.view.SupportMenu;
import com.tencent.open.yyb.TitleBar;

public class DrawHelper {
    public static Paint PAINT = new Paint();
    public static Paint PAINT_FPS;
    public static RectF RECT = new RectF();
    private static boolean USE_DRAWCOLOR_MODE_CLEAR = true;
    private static boolean USE_DRAWCOLOR_TO_CLEAR_CANVAS = true;

    static {
        PAINT.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        PAINT.setColor(0);
    }

    public static void useDrawColorToClearCanvas(boolean use, boolean useClearMode) {
        USE_DRAWCOLOR_TO_CLEAR_CANVAS = use;
        USE_DRAWCOLOR_MODE_CLEAR = useClearMode;
    }

    public static void drawFPS(Canvas canvas, String text) {
        if (PAINT_FPS == null) {
            PAINT_FPS = new Paint();
            PAINT_FPS.setColor(SupportMenu.CATEGORY_MASK);
            PAINT_FPS.setTextSize(30.0f);
        }
        int top = canvas.getHeight() - 50;
        clearCanvas(canvas, TitleBar.SHAREBTN_RIGHT_MARGIN, (float) (top - 50), (float) ((int) (PAINT_FPS.measureText(text) + TitleBar.BACKBTN_LEFT_MARGIN)), (float) canvas.getHeight());
        canvas.drawText(text, TitleBar.SHAREBTN_RIGHT_MARGIN, (float) top, PAINT_FPS);
    }

    public static void clearCanvas(Canvas canvas) {
        if (!USE_DRAWCOLOR_TO_CLEAR_CANVAS) {
            RECT.set(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight());
            clearCanvas(canvas, RECT);
        } else if (USE_DRAWCOLOR_MODE_CLEAR) {
            canvas.drawColor(0, Mode.CLEAR);
        } else {
            canvas.drawColor(0);
        }
    }

    public static void fillTransparent(Canvas canvas) {
        canvas.drawColor(0, Mode.CLEAR);
    }

    public static void clearCanvas(Canvas canvas, float left, float top, float right, float bottom) {
        RECT.set(left, top, right, bottom);
        clearCanvas(canvas, RECT);
    }

    private static void clearCanvas(Canvas canvas, RectF rect) {
        if (rect.width() > 0.0f && rect.height() > 0.0f) {
            canvas.drawRect(rect, PAINT);
        }
    }
}
