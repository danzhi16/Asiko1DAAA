package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public final class DepthCounter {
    private static final AtomicInteger current = new AtomicInteger(0);
    private static final AtomicInteger peak = new AtomicInteger(0);

    private DepthCounter() {}

    public static void push() {
        int d = current.incrementAndGet();
        peak.getAndUpdate(max -> Math.max(max, d));
    }

    public static void pop() {
        current.decrementAndGet();
    }

    public static int maxDepth() {
        return peak.get();
    }

    public static void clear() {
        current.set(0);
        peak.set(0);
    }
}

