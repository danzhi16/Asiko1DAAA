package org.example;

public record Metrics(
        String algorithmName,
        long runTimeNanos,
        long counter,
        int depth,
        long allocationBytes,
        int n
) {}
