package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class DeterministicSelectTest {

    @Test
    void selectsOnSmallArray() {
        int[] a = {7, 2, 9, 4, 1, 5, 3, 8, 6};
        int[] sorted = a.clone(); Arrays.sort(sorted);
        for (int k = 0; k < a.length; k++) {
            int v = DeterministicSelect.select(a.clone(), k);
            assertEquals(sorted[k], v);
        }
    }

    @Test
    void handlesDuplicates() {
        int[] a = {5, 5, 1, 3, 3, 3, 2, 2, 4, 5, 1};
        int[] sorted = a.clone(); Arrays.sort(sorted);
        for (int k = 0; k < a.length; k++) {
            int v = DeterministicSelect.select(a.clone(), k);
            assertEquals(sorted[k], v);
        }
    }

    @Test
    void handlesAllEqual() {
        int[] a = new int[1000];
        Arrays.fill(a, 7);
        for (int k = 0; k < a.length; k++) {
            int v = DeterministicSelect.select(a.clone(), k);
            assertEquals(7, v);
        }
    }

    @Test
    void largeRandom() {
        Random rnd = new Random(123);
        int[] a = rnd.ints(100_000, -1_000_000, 1_000_000).toArray();
        int[] sorted = a.clone(); Arrays.sort(sorted);
        for (int k : new int[]{0, 1, 2, 10, 123, 999, 5_000, 12_345, 50_000, 99_999}) {
            int v = DeterministicSelect.select(a.clone(), k);
            assertEquals(sorted[k], v);
        }
    }

    @Test
    void invalidKThrows() {
        int[] a = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(a, -1));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(a, 3));
    }

    @Test
    void emptyAndSingle() {
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{}, 0));
        int[] a = {42};
        assertEquals(42, DeterministicSelect.select(a, 0));
    }

    @Test
    void kSmallestHelper() {
        int[] a = {7, 2, 9, 4, 1, 5, 3, 8, 6, 3, 3};
        int[] out3 = DeterministicSelect.kSmallest(a, 3);
        assertArrayEquals(new int[]{1,2,3}, out3);
        int[] out5 = DeterministicSelect.kSmallest(a, 5);
        assertArrayEquals(new int[]{1,2,3,3,3}, out5);
        assertArrayEquals(new int[]{}, DeterministicSelect.kSmallest(a, 0));
    }
}
