package io.fabric.sdk.android;

public interface InitializationCallback<T> {
    public static final InitializationCallback EMPTY = new Empty();

    public static class Empty implements InitializationCallback<Object> {
        private Empty() {
        }

        public void success(Object object) {
        }

        public void failure(Exception exception) {
        }
    }

    void failure(Exception exception);

    void success(T t);
}
