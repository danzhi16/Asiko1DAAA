package org.example;

public final class OpCounter {
    private static final ThreadLocal<Long> cnt = ThreadLocal.withInitial(() -> 0L);

    private OpCounter() {}

    public static void add(long x) {
        cnt.set(cnt.get() + x);
    }

    public static void inc() {
        add(1);
    }

    public static long get() {
        return cnt.get();
    }

    public static void reset() {
        cnt.set(0L);
    }
}
