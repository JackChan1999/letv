package master.flame.danmaku.danmaku.parser;

public interface IDataSource<T> {
    public static final String SCHEME_FILE_TAG = "file";
    public static final String SCHEME_HTTPS_TAG = "https";
    public static final String SCHEME_HTTP_TAG = "http";

    T data();

    void release();
}
