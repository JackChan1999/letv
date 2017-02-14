package com.letv.jarlibs.chat.ex;

public interface FutureCallback<T> {
    void onCompleted(Exception exception, T t);
}
