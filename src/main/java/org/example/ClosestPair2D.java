package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class ClosestPair2D {
    public static record Pt(double x, double y) {}

    private ClosestPair2D() {}

    public static double solve(List<Pt> pts) {
        if (pts == null) throw new NullPointerException("pts is null");
        int n = pts.size();
        if (n < 2) return Double.POSITIVE_INFINITY;
        List<Pt> xs = new ArrayList<>(pts);
        xs.sort(Comparator.comparingDouble(p -> p.x));
        List<Pt> ys = new ArrayList<>(xs);
        ys.sort(Comparator.comparingDouble(p -> p.y));
        DepthCounter.push();
        double dist2 = rec(xs, ys);
        DepthCounter.pop();
        return Math.sqrt(dist2);
    }

    private static double rec(List<Pt> xs, List<Pt> ys) {
        int n = xs.size();
        if (n <= 3) return brute(xs);
        int mid = n / 2;
        double midX = xs.get(mid).x;
        List<Pt> xl = xs.subList(0, mid);
        List<Pt> xr = xs.subList(mid, n);

        List<Pt> yl = new ArrayList<>(mid);
        List<Pt> yr = new ArrayList<>(n - mid);
        for (Pt p : ys) {
            if (p.x < midX || (p.x == midX && xl.contains(p))) yl.add(p);
            else yr.add(p);
        }

        DepthCounter.push();
        double dl = rec(xl, yl);
        DepthCounter.pop();
        DepthCounter.push();
        double dr = rec(xr, yr);
        DepthCounter.pop();
        double d = Math.min(dl, dr);

        List<Pt> strip = new ArrayList<>();
        double sqrtD = Math.sqrt(d);
        for (Pt p : ys) if (Math.abs(p.x - midX) <= sqrtD) strip.add(p);

        int m = strip.size();
        for (int i = 0; i < m; i++) {
            Pt a = strip.get(i);
            for (int j = i + 1; j < m && (strip.get(j).y - a.y) <= sqrtD; j++) {
                double dd = dist2(a, strip.get(j));
                if (dd < d) { d = dd; sqrtD = Math.sqrt(d); }
            }
        }
        return d;
    }

    private static double brute(List<Pt> a) {
        int n = a.size();
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < n; i++) {
            Pt p = a.get(i);
            for (int j = i + 1; j < n; j++) {
                double d = dist2(p, a.get(j));
                if (d < best) best = d;
            }
        }
        return best;
    }

    private static double dist2(Pt a, Pt b) {
        OpCounter.inc();
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return dx * dx + dy * dy;
    }
}
