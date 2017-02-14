package master.flame.danmaku.danmaku.loader;

public class IllegalDataException extends Exception {
    private static final long serialVersionUID = 10441759254L;

    public IllegalDataException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public IllegalDataException(String detailMessage) {
        super(detailMessage);
    }

    public IllegalDataException(Throwable throwable) {
        super(throwable);
    }
}
