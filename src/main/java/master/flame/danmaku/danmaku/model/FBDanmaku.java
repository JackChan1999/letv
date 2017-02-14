package master.flame.danmaku.danmaku.model;

public class FBDanmaku extends FTDanmaku {
    public FBDanmaku(Duration duration) {
        super(duration);
    }

    public int getType() {
        return 4;
    }
}
