package com.priyakydey.collection.ext.heap;

import java.util.Collections;
import java.util.Comparator;

/**
 * Implementation of a min heap.
 * <p>
 * A min heap is a specialized tree-based data structure that satisfies the heap property,
 * specifically, that the key at root must be minimum among all keys present in the binary heap.
 * </p>
 *
 * @param <T> the type of elements stored in this min heap
 * @author Priyak Dey
 */
public class MinHeap<T> extends Heap<T> {

    /**
     * Default constructor initializes the min heap with elements' natural order.
     */
    public MinHeap() {
        super();
    }

    /**
     * Initializes the min heap with a custom comparator.
     *
     * @param comparator the comparator to be used for ordering heap elements
     */
    public MinHeap(Comparator<T> comparator) {
        super(comparator);
    }


    /**
     * Inserts an element into the min heap while maintaining its properties.
     * <p>
     * The insertion operation ensures that after adding a new element,
     * the properties of the min heap remain intact.
     * </p>
     *
     * @param t the element to add to the min heap
     */
    @Override
    public void push(T t) {
        data.add(t);

        if (data.size() == 1) {
            return;
        }

        // go from bottom up and place the new node in the correct place
        int currIndex = data.size() - 1;
        while (currIndex >= 1) {
            int parentIndex = getIndexOfParent(currIndex);
            T child = data.get(currIndex), parent = data.get(parentIndex);
            if (compare(child, parent) < 0) {
                Collections.swap(data, currIndex, parentIndex);
            } else {
                break;
            }

            currIndex = parentIndex;
        }

    }

    /**
     * Removes and returns the root (minimum element) of the min heap.
     * <p>
     * After removing the root, it ensures the properties of the min heap are maintained.
     * </p>
     *
     * @return the root (minimum element) of the min heap
     * @throws IllegalStateException if the heap is empty
     */
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Trying to pop from an empty heap..");
        }

        T root = data.get(0);
        Collections.swap(data, 0, data.size() - 1);
        data.remove(data.size() - 1);

        int currIndex = 0;

        while (currIndex < data.size()) {
            int leftChildIndex = getIndexOfLeftChild(currIndex), rightChildIndex =
                getIndexOfRightChild(currIndex);

            if (leftChildIndex >= data.size()) {
                break;
            }

            int swapIndex = leftChildIndex;
            if (rightChildIndex < data.size() &&
                compare(data.get(rightChildIndex), data.get(leftChildIndex)) < 0) {
                swapIndex = rightChildIndex;
            }

            if (compare(data.get(swapIndex), data.get(currIndex)) < 0) {
                Collections.swap(data, swapIndex, currIndex);
            } else {
                break;
            }

            currIndex = swapIndex;
        }

        return root;
    }
}
