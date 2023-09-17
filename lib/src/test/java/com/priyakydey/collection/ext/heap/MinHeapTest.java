package com.priyakydey.collection.ext.heap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test::MinHeap")
class MinHeapTest {

    @DisplayName("No Comparator Passed")
    @Test
    void testMinHeapNaturalOrder() {
        Heap<Integer> heap = new MinHeap<>();
        assertTrue(heap.isEmpty(), "expected true, got false");

        // insert some elements
        for (int i = 1; i <= 10; i++) {
            heap.push(i);
        }

        int size = heap.size();
        assertEquals(10, size, String.format("expected 10, got %d", size));

        int[] actual = new int[5];
        // pop 5 elements and store them in the array
        for (int i = 0; i < 5; i++) {
            actual[i] = heap.pop();
        }

        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, actual, String.format("expected %s, got %s",
            Arrays.toString(expected), Arrays.toString(actual)));

        actual = new int[5];
        // pop 5 elements and store them in the array
        for (int i = 0; i < 5; i++) {
            actual[i] = heap.pop();
        }

        expected = new int[] {6, 7, 8, 9, 10};
        assertArrayEquals(expected, actual, String.format("expected %s, got %s",
            Arrays.toString(expected), Arrays.toString(actual)));
    }

    @DisplayName("With Comparator Passed")
    @Test
    void testMinHeapWithComparator() {
        Heap<Integer> heap = new MinHeap<Integer>(Comparator.naturalOrder());
        assertTrue(heap.isEmpty(), "expected true, got false");

        // insert some elements
        for (int i = 1; i <= 10; i++) {
            heap.push(i);
        }

        int[] actual = new int[5];
        // pop 5 elements and store them in the array
        for (int i = 0; i < 5; i++) {
            actual[i] = heap.pop();
        }

        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, actual, String.format("expected %s, got %s",
            Arrays.toString(expected), Arrays.toString(actual)));

        actual = new int[5];
        // pop 5 elements and store them in the array
        for (int i = 0; i < 5; i++) {
            actual[i] = heap.pop();
        }

        expected = new int[] {6, 7, 8, 9, 10};
        assertArrayEquals(expected, actual, String.format("expected %s, got %s",
            Arrays.toString(expected), Arrays.toString(actual)));
    }

}
