package org.example;

public final class MergeSort {
    private MergeSort() {}

    public static void sort(int[] a) {
        if (a == null) throw new NullPointerException("array is null");
        if (a.length < 2) return;
        int[] tmp = new int[a.length];
        DepthCounter.push();
        sort(a, tmp, 0, a.length - 1);
        DepthCounter.pop();
    }

    private static void sort(int[] a, int[] tmp, int lo, int hi) {
        if (lo >= hi) return;
        int mid = (lo + hi) >>> 1;

        DepthCounter.push();
        sort(a, tmp, lo, mid);
        DepthCounter.pop();

        DepthCounter.push();
        sort(a, tmp, mid + 1, hi);
        DepthCounter.pop();

        merge(a, tmp, lo, mid, hi);
    }

    private static void merge(int[] a, int[] tmp, int lo, int mid, int hi) {
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            OpCounter.inc(); // одно сравнение
            if (a[i] <= a[j]) tmp[k++] = a[i++];
            else tmp[k++] = a[j++];
        }
        while (i <= mid) tmp[k++] = a[i++];
        while (j <= hi) tmp[k++] = a[j++];
        for (int t = lo; t <= hi; t++) a[t] = tmp[t];
    }
}
