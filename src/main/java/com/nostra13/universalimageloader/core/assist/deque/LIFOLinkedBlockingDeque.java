package com.nostra13.universalimageloader.core.assist.deque;

public class LIFOLinkedBlockingDeque<T> extends LinkedBlockingDeque<T> {
    private static final long serialVersionUID = -4114786347960826192L;

    public boolean offer(T e) {
        return super.offerFirst(e);
    }

    public T remove() {
        return super.removeFirst();
    }
}
