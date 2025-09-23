package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ClosestPair2DTest {

    private static double brute(List<ClosestPair2D.Pt> pts) {
        int n = pts.size();
        if (n < 2) return Double.POSITIVE_INFINITY;
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < n; i++) {
            var a = pts.get(i);
            for (int j = i + 1; j < n; j++) {
                var b = pts.get(j);
                double dx = a.x() - b.x();
                double dy = a.y() - b.y();
                double d = Math.sqrt(dx * dx + dy * dy);
                if (d < best) best = d;
            }
        }
        return best;
    }

    @Test
    void emptyAndSingle() {
        assertEquals(Double.POSITIVE_INFINITY, ClosestPair2D.solve(List.of()));
        assertEquals(Double.POSITIVE_INFINITY, ClosestPair2D.solve(List.of(new ClosestPair2D.Pt(1, 2))));
    }

    @Test
    void twoPoints() {
        var a = new ClosestPair2D.Pt(0, 0);
        var b = new ClosestPair2D.Pt(3, 4);
        assertEquals(5.0, ClosestPair2D.solve(List.of(a, b)), 1e-9);
    }

    @Test
    void duplicatesZeroDistance() {
        var pts = List.of(
                new ClosestPair2D.Pt(1, 1),
                new ClosestPair2D.Pt(2, 2),
                new ClosestPair2D.Pt(1, 1),
                new ClosestPair2D.Pt(5, 5)
        );
        assertEquals(0.0, ClosestPair2D.solve(pts), 0.0);
    }

    @Test
    void smallRandomMatchesBrute() {
        Random rnd = new Random(42);
        for (int t = 0; t < 100; t++) {
            int n = rnd.nextInt(2, 40);
            List<ClosestPair2D.Pt> pts = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                pts.add(new ClosestPair2D.Pt(rnd.nextDouble(-100, 100), rnd.nextDouble(-100, 100)));
            }
            double expected = brute(pts);
            double got = ClosestPair2D.solve(pts);
            assertEquals(expected, got, 1e-9);
        }
    }

    @Test
    void collinearPoints() {
        List<ClosestPair2D.Pt> pts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) pts.add(new ClosestPair2D.Pt(i, 2 * i));
        assertEquals(Math.hypot(1, 2), ClosestPair2D.solve(pts), 1e-9);
    }

    @Test
    void gridPoints() {
        List<ClosestPair2D.Pt> pts = new ArrayList<>();
        for (int x = -50; x <= 50; x++) {
            for (int y = -50; y <= 50; y++) {
                pts.add(new ClosestPair2D.Pt(x, y));
            }
        }
        assertEquals(1.0, ClosestPair2D.solve(pts), 1e-9);
    }

    @Test
    void largeRandomSampledCheck() {
        Random rnd = new Random(123);
        List<ClosestPair2D.Pt> pts = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            pts.add(new ClosestPair2D.Pt(rnd.nextDouble(-1e6, 1e6), rnd.nextDouble(-1e6, 1e6)));
        }
        double d = ClosestPair2D.solve(pts);
        int m = 5000;
        List<ClosestPair2D.Pt> sample = new ArrayList<>(pts);
        sample.sort(Comparator.comparingDouble(ClosestPair2D.Pt::x));
        sample = sample.subList(0, m);
        double bruteSample = brute(sample);
        assertTrue(d <= bruteSample + 1e-9);
    }
}
