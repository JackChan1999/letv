package master.flame.danmaku.danmaku.loader.android;

import android.net.Uri;
import java.io.InputStream;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.parser.android.JSONSource;

public class AcFunDanmakuLoader implements ILoader {
    private static volatile AcFunDanmakuLoader instance;
    private JSONSource dataSource;

    private AcFunDanmakuLoader() {
    }

    public static ILoader instance() {
        if (instance == null) {
            synchronized (AcFunDanmakuLoader.class) {
                if (instance == null) {
                    instance = new AcFunDanmakuLoader();
                }
            }
        }
        return instance;
    }

    public JSONSource getDataSource() {
        return this.dataSource;
    }

    public void load(String uri) throws IllegalDataException {
        try {
            this.dataSource = new JSONSource(Uri.parse(uri));
        } catch (Throwable e) {
            throw new IllegalDataException(e);
        }
    }

    public void load(InputStream in) throws IllegalDataException {
        try {
            this.dataSource = new JSONSource(in);
        } catch (Throwable e) {
            throw new IllegalDataException(e);
        }
    }
}
