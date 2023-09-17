package com.priyakydey.collection.ext.heap;

import java.util.Collections;
import java.util.Comparator;

/**
 * Implementation of a max heap.
 * <p>
 * A max heap is a specialized tree-based data structure that satisfies the heap property,
 * specifically, that the key at root must be maximum among all keys present in the binary heap.
 * </p>
 *
 * @param <T> the type of elements stored in this max heap
 * @author Priyak Dey
 */
public class MaxHeap<T> extends Heap<T> {

    /**
     * Default constructor initializes the max heap with elements' natural order.
     */
    public MaxHeap() {
        super();
    }

    /**
     * Initializes the max heap with a custom comparator.
     *
     * @param comparator the comparator to be used for ordering heap elements
     */
    public MaxHeap(Comparator<T> comparator) {
        super(comparator);
    }

    /**
     * Inserts an element into the max heap while maintaining its properties.
     * <p>
     * The insertion operation ensures that after adding a new element,
     * the properties of the max heap remain intact.
     * </p>
     *
     * @param t the element to add to the max heap
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
            if (compare(child, parent) > 0) {
                Collections.swap(data, currIndex, parentIndex);
            } else {
                break;
            }

            currIndex = parentIndex;
        }

    }

    /**
     * Removes and returns the root (maximum element) of the max heap.
     * <p>
     * After removing the root, it ensures the properties of the max heap are maintained.
     * </p>
     *
     * @return the root (maximum element) of the max heap
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
                compare(data.get(rightChildIndex), data.get(leftChildIndex)) > 0) {
                swapIndex = rightChildIndex;
            }

            if (compare(data.get(swapIndex), data.get(currIndex)) > 0) {
                Collections.swap(data, swapIndex, currIndex);
            } else {
                break;
            }

            currIndex = swapIndex;
        }

        return root;
    }

}
