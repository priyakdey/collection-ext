package com.priyakydey.collection.ext.heap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test::MaxHeap")
class MaxHeapTest {

    @DisplayName("No Comparator Passed")
    @Test
    void testMaxHeapNaturalOrder() {
        System.out.println("---------------------");
        Heap<Integer> heap = new MaxHeap<>();
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

        int[] expected = {10, 9, 8, 7, 6};
        assertArrayEquals(expected, actual, String.format("expected %s, got %s",
            Arrays.toString(expected), Arrays.toString(actual)));

        actual = new int[5];
        // pop 5 elements and store them in the array
        for (int i = 0; i < 5; i++) {
            actual[i] = heap.pop();
        }

        expected = new int[] {5, 4, 3, 2, 1};
        assertArrayEquals(expected, actual, String.format("expected %s, got %s",
            Arrays.toString(expected), Arrays.toString(actual)));
    }

    @DisplayName("With Comparator Passed")
    @Test
    void testMinHeapWithComparator() {
        Heap<Integer> heap = new MaxHeap<Integer>(Comparator.naturalOrder());
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

        int[] expected = {10, 9, 8, 7, 6};
        assertArrayEquals(expected, actual, String.format("expected %s, got %s",
            Arrays.toString(expected), Arrays.toString(actual)));

        actual = new int[5];
        // pop 5 elements and store them in the array
        for (int i = 0; i < 5; i++) {
            actual[i] = heap.pop();
        }

        expected = new int[] {5, 4, 3, 2, 1};
        assertArrayEquals(expected, actual, String.format("expected %s, got %s",
            Arrays.toString(expected), Arrays.toString(actual)));
    }

}