package master.flame.danmaku.danmaku.loader.android;

import master.flame.danmaku.danmaku.loader.ILoader;

public class DanmakuLoaderFactory {
    public static String TAG_ACFUN = "acfun";
    public static String TAG_BILI = "bili";

    public static ILoader create(String tag) {
        if (TAG_BILI.equalsIgnoreCase(tag)) {
            return BiliDanmakuLoader.instance();
        }
        if (TAG_ACFUN.equalsIgnoreCase(tag)) {
            return AcFunDanmakuLoader.instance();
        }
        return null;
    }
}
