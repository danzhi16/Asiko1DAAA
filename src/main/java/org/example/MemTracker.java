package org.example;

public final class MemTracker {
    private MemTracker() {}

    public static long usedBytes() {
        Runtime r = Runtime.getRuntime();
        return r.totalMemory() - r.freeMemory();
    }
}
