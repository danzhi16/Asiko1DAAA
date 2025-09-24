package org.example;

import java.util.Arrays;

public final class DeterministicSelect {
    private DeterministicSelect() {}

    public static int select(int[] a, int k) {
        if (a == null) throw new NullPointerException("array is null");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int pIdx = medianOfMediansIndex(a, lo, hi);
            int p = partition(a, lo, hi, pIdx);
            if (k == p) return a[p];
            if (k < p) hi = p - 1; else lo = p + 1;
        }
        throw new IllegalStateException("unreachable");
    }

    private static int partition(int[] a, int lo, int hi, int pivotIdx) {
        int pivot = a[pivotIdx];
        swap(a, pivotIdx, hi);
        int i = lo;
        for (int j = lo; j < hi; j++) {
            OpCounter.inc();
            if (a[j] <= pivot) { swap(a, i, j); i++; }
        }
        swap(a, i, hi);
        return i;
    }

    private static int medianOfMediansIndex(int[] a, int lo, int hi) {
        int n = hi - lo + 1;
        if (n <= 5) {
            insertionSort(a, lo, hi);
            return lo + n / 2;
        }
        int m = 0;
        for (int i = lo; i <= hi; i += 5) {
            int r = Math.min(i + 4, hi);
            insertionSort(a, i, r);
            int med = i + (r - i) / 2;
            swap(a, lo + m, med);
            m++;
        }
        DepthCounter.push();
        int medOfMedIdx = medianOfMediansIndex(a, lo, lo + m - 1);
        DepthCounter.pop();
        return medOfMedIdx;
    }

    private static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int v = a[i], j = i - 1;
            while (j >= lo) {
                OpCounter.inc();
                if (a[j] > v) { a[j + 1] = a[j]; j--; } else break;
            }
            a[j + 1] = v;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    public static int[] kSmallest(int[] a, int k) {
        if (k < 0 || k > a.length) throw new IllegalArgumentException("k out of range");
        if (k == 0) return new int[0];
        int[] copy = Arrays.copyOf(a, a.length);
        int kth = select(copy, k - 1);
        int[] less = Arrays.stream(copy).filter(x -> x < kth).toArray();
        int needEq = k - less.length;
        int[] eq = Arrays.stream(copy).filter(x -> x == kth).limit(needEq).toArray();
        int[] out = new int[k];
        System.arraycopy(less, 0, out, 0, less.length);
        System.arraycopy(eq, 0, out, less.length, eq.length);
        Arrays.sort(out);
        return out;
    }
}
