package com.google.gson.stream;

final class StringPool {
    private final String[] pool = new String[512];

    StringPool() {
    }

    public String get(char[] array, int start, int length) {
        int i;
        int hashCode = 0;
        for (i = start; i < start + length; i++) {
            hashCode = (hashCode * 31) + array[i];
        }
        hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
        int index = (hashCode ^ ((hashCode >>> 7) ^ (hashCode >>> 4))) & (this.pool.length - 1);
        String pooled = this.pool[index];
        if (pooled == null || pooled.length() != length) {
            String result = new String(array, start, length);
            this.pool[index] = result;
            return result;
        }
        for (i = 0; i < length; i++) {
            if (pooled.charAt(i) != array[start + i]) {
                result = new String(array, start, length);
                this.pool[index] = result;
                return result;
            }
        }
        return pooled;
    }
}
