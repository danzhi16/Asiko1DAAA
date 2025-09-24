package org.example;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public final class Main {
    public static void main(String[] args) {
        try (PrintWriter out = new PrintWriter("results.csv")) {
            out.println(Csv.header());

            out.println(runMergeSort(100_000));
            out.println(runQuickSort(100_000));
            out.println(runSelect(100_000));
            out.println(runClosestPair(40_000));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String runMergeSort(int n) {
        int[] a = ThreadLocalRandom.current().ints(n, -1_000_000, 1_000_000).toArray();
        Metrics m = measure("MergeSort", n, () -> MergeSort.sort(a));
        return Csv.line(m);
    }

    private static String runQuickSort(int n) {
        int[] a = ThreadLocalRandom.current().ints(n, -1_000_000, 1_000_000).toArray();
        Metrics m = measure("QuickSort", n, () -> QuickSort.sort(a));
        return Csv.line(m);
    }

    private static String runSelect(int n) {
        int[] a = ThreadLocalRandom.current().ints(n, -1_000_000, 1_000_000).toArray();
        int k = n / 2;
        Metrics m = measure("SelectMedianOfMedians", n, () -> DeterministicSelect.select(a, k));
        return Csv.line(m);
    }

    private static String runClosestPair(int n) {
        Random rnd = new Random(123);
        List<ClosestPair2D.Pt> pts = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            pts.add(new ClosestPair2D.Pt(
                    rnd.nextDouble(-1e6, 1e6),
                    rnd.nextDouble(-1e6, 1e6)
            ));
        }
        Metrics m = measure("ClosestPair2D", n, () -> ClosestPair2D.solve(pts));
        return Csv.line(m);
    }

    private static Metrics measure(String name, int n, Runnable algo) {
        OpCounter.reset();
        DepthCounter.reset();
        long beforeMem = getUsedMemory();
        long start = System.nanoTime();
        algo.run();
        long end = System.nanoTime();
        long afterMem = getUsedMemory();
        return new Metrics(
                name,
                end - start,
                OpCounter.get(),
                DepthCounter.maxDepth(),
                Math.max(0, afterMem - beforeMem),
                n
        );
    }

    private static long getUsedMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }
}
