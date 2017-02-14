package com.google.gson.internal;

public final class Pair<FIRST, SECOND> {
    public final FIRST first;
    public final SECOND second;

    public Pair(FIRST first, SECOND second) {
        this.first = first;
        this.second = second;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (this.first != null ? this.first.hashCode() : 0) * 17;
        if (this.second != null) {
            i = this.second.hashCode();
        }
        return hashCode + (i * 17);
    }

    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> that = (Pair) o;
        if (equal(this.first, that.first) && equal(this.second, that.second)) {
            return true;
        }
        return false;
    }

    private static boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public String toString() {
        return String.format("{%s,%s}", new Object[]{this.first, this.second});
    }
}
