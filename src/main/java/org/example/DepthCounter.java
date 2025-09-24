package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public final class DepthCounter {
    private static final ThreadLocal<Integer> cur = ThreadLocal.withInitial(() -> 0);
    private static final ThreadLocal<Integer> peak = ThreadLocal.withInitial(() -> 0);
    private DepthCounter() {}
    public static void push() {
        int d = cur.get() + 1;
        cur.set(d);
        if (d > peak.get()) peak.set(d);
    }
    public static void pop() {
        cur.set(cur.get() - 1);
    }
    public static int maxDepth() { return peak.get(); }
    public static void reset() { cur.set(0); peak.set(0); }
}
