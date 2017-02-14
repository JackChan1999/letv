package master.flame.danmaku.danmaku.model;

import master.flame.danmaku.danmaku.util.DanmakuUtils;

public class Danmaku extends BaseDanmaku {
    public Danmaku(CharSequence text) {
        DanmakuUtils.fillText(this, text);
    }

    public boolean isShown() {
        return false;
    }

    public void layout(IDisplayer displayer, float x, float y) {
    }

    public float[] getRectAtTime(IDisplayer displayer, long time) {
        return null;
    }

    public float getLeft() {
        return 0.0f;
    }

    public float getTop() {
        return 0.0f;
    }

    public float getRight() {
        return 0.0f;
    }

    public float getBottom() {
        return 0.0f;
    }

    public int getType() {
        return 0;
    }
}
