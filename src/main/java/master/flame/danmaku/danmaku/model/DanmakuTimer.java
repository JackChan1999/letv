package master.flame.danmaku.danmaku.model;

public class DanmakuTimer {
    public long currMillisecond;
    private long lastInterval;

    public long update(long curr) {
        this.lastInterval = curr - this.currMillisecond;
        this.currMillisecond = curr;
        return this.lastInterval;
    }

    public long add(long mills) {
        return update(this.currMillisecond + mills);
    }

    public long lastInterval() {
        return this.lastInterval;
    }
}
