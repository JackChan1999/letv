package com.letv.jarlibs.chat.ex;

public interface ConnectCallback {
    void onConnectCompleted(Exception exception, EventEmitter eventEmitter);
}
