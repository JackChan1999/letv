package master.flame.danmaku.controller;

public class UpdateThread extends Thread {
    volatile boolean mIsQuited;

    public UpdateThread(String name) {
        super(name);
    }

    public void quit() {
        this.mIsQuited = true;
    }

    public boolean isQuited() {
        return this.mIsQuited;
    }

    public void run() {
        if (!this.mIsQuited) {
        }
    }
}
