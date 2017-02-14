package com.letv.jarlibs.chat.ex;

public interface Cancellable {
    boolean cancel();

    boolean isCancelled();

    boolean isDone();
}
