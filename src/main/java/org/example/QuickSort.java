package org.example;

import java.util.concurrent.ThreadLocalRandom;

public final class QuickSort {
    private QuickSort() {}

    public static void sort(int[] a) {
        if (a == null) throw new NullPointerException("array is null");
        if (a.length < 2) return;
        quickSort(a, 0, a.length - 1);
    }

    private static void quickSort(int[] a, int lo, int hi) {
        while (lo < hi) {
            int p = partitionRandom(a, lo, hi);
            int leftSize = p - lo;
            int rightSize = hi - p;
            if (leftSize < rightSize) {
                if (lo < p - 1) { DepthCounter.push(); quickSort(a, lo, p - 1); DepthCounter.pop(); }
                lo = p + 1;
            } else {
                if (p + 1 < hi) { DepthCounter.push(); quickSort(a, p + 1, hi); DepthCounter.pop(); }
                hi = p - 1;
            }
        }
    }

    private static int partitionRandom(int[] a, int lo, int hi) {
        int pivotIdx = ThreadLocalRandom.current().nextInt(lo, hi + 1);
        swap(a, pivotIdx, hi);
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            OpCounter.inc(); // comparison a[j] <= pivot
            if (a[j] <= pivot) {
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, hi);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
