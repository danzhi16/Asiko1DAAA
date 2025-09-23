package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void sortsSmallUnorderedArray() {
        int[] array = {5, 2, 9, 1, 7, 3, 8};
        int[] expected = array.clone();
        Arrays.sort(expected);

        QuickSort.sort(array);

        assertArrayEquals(expected, array);
    }

    @Test
    void handlesEmptyArray() {
        int[] array = {};
        QuickSort.sort(array);
        assertArrayEquals(new int[]{}, array);
    }

    @Test
    void handlesSingleElement() {
        int[] array = {42};
        QuickSort.sort(array);
        assertArrayEquals(new int[]{42}, array);
    }

    @Test
    void handlesAlreadySortedArray() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = array.clone();

        QuickSort.sort(array);

        assertArrayEquals(expected, array);
    }

    @Test
    void handlesReverseSortedArray() {
        int[] array = {9, 7, 5, 3, 1, 0, -2};
        int[] expected = array.clone();
        Arrays.sort(expected);

        QuickSort.sort(array);

        assertArrayEquals(expected, array);
    }

    @Test
    void handlesArrayWithDuplicates() {
        int[] array = {5, 5, 3, 3, 1, 1, 3, 5, 1};
        int[] expected = array.clone();
        Arrays.sort(expected);

        QuickSort.sort(array);

        assertArrayEquals(expected, array);
    }

    @Test
    void handlesAllEqualElements() {
        int[] array = new int[1000];
        Arrays.fill(array, 7);
        int[] expected = array.clone();

        QuickSort.sort(array);

        assertArrayEquals(expected, array);
    }

    @Test
    void sortsLargeRandomArray() {
        Random random = new Random(123);
        int[] array = random.ints(50_000, -1_000_000, 1_000_000).toArray();
        int[] expected = array.clone();
        Arrays.sort(expected);

        QuickSort.sort(array);

        assertArrayEquals(expected, array);
    }
}
