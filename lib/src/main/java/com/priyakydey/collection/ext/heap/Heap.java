package com.priyakydey.collection.ext.heap;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * Represents an abstract heap data structure.
 * A heap can be either a min-heap or max-heap, based on its concrete implementation.
 * This class serves as a foundation for all types of heaps.
 * </p>
 * <p>
 * <a href="https://en.wikipedia.org/wiki/Heap_(data_structure)">Heap Data Structure</a>
 * </p>
 *
 * @param <T> the type of elements stored in this heap
 * @author Priyak Dey
 */
public abstract class Heap<T> {

    /**
     * Internal storage for heap elements.
     */
    protected final List<T> data;

    /**
     * Optional comparator for comparing heap elements.
     */
    protected final Comparator<T> comparator;

    /**
     * Default constructor initializes the heap with no comparator.
     * This requires the elements to be inherently {@link Comparable}.
     */
    protected Heap() {
        this(null);
    }

    /**
     * Initializes the heap with a custom comparator.
     *
     * @param comparator the comparator to be used for ordering heap elements
     */
    protected Heap(Comparator<T> comparator) {
        this.data = new ArrayList<>();
        this.comparator = comparator;
    }


    /**
     * Pushes a new element onto the heap.
     * Concrete implementations will determine the exact position based on heap properties.
     *
     * @param t the element to add to the heap
     */
    public abstract void push(T t);

    /**
     * Removes and returns the root of the heap.
     * For a min-heap, this would be the smallest element, and for a max-heap, the largest.
     *
     * @return the root of the heap
     */
    public abstract T pop();

    /**
     * Retrieves the current number of elements in the heap.
     *
     * @return the number of elements in the heap
     */
    public int size() {
        return this.data.size();
    }

    /**
     * Checks if the heap is empty.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.data.isEmpty();
    }


    /**
     * Converts the heap data into an SVG representation using Graphviz.
     * <p>
     * This method visualizes the heap as a directed graph where each node
     * represents an element of the heap, and each edge connects a parent to its
     * left or right child. Nodes are color-coded in red.
     * </p>
     *
     * @return A string containing the SVG representation of the heap.
     */
    public String toSvg() {
        MutableGraph graph = Factory.mutGraph().setDirected(true);

        for (int i = 0; i < data.size(); i++) {
            MutableNode parent = Factory.mutNode(data.get(i).toString()).add(Color.RED);
            graph.add(parent);

            int leftChildIndex = getIndexOfLeftChild(i);
            if (leftChildIndex < data.size()) {
                MutableNode leftChild =
                    Factory.mutNode(data.get(leftChildIndex).toString()).add(Color.RED);
                parent.addLink(leftChild);
            }

            int rightChildIndex = getIndexOfRightChild(i);
            if (rightChildIndex < data.size()) {
                MutableNode rightChild =
                    Factory.mutNode(data.get(rightChildIndex).toString()).add(Color.RED);
                parent.addLink(rightChild);
            }
        }

        return Graphviz.fromGraph(graph).render(Format.SVG).toString();
    }

    /**
     * Compares two elements using the provided comparator or their natural order if no comparator was provided.
     *
     * @param t1 the first element to compare
     * @param t2 the second element to compare
     * @return 0 if both elements are equal, a positive value if t1 is greater than t2, and a negative value if t1 is less than t2
     */
    @SuppressWarnings("unchecked")
    protected int compare(T t1, T t2) {
        if (comparator != null) {
            return comparator.compare(t1, t2);
        } else if (t1 instanceof Comparable<?>) {
            return ((Comparable<T>) t1).compareTo(t2);
        } else {
            throw new IllegalArgumentException("Elements are not comparable.");
        }
    }

    /**
     * Computes the index of the parent of a node at the given index.
     *
     * @param index the index of the node
     * @return the index of the parent node
     */
    protected static int getIndexOfParent(int index) {
        return (index - 1) / 2;
    }

    /**
     * Computes the index of the left child of a node at the given index.
     *
     * @param index the index of the node
     * @return the index of the left child node
     */
    protected static int getIndexOfLeftChild(int index) {
        return 2 * index + 1;
    }

    /**
     * Computes the index of the right child of a node at the given index.
     *
     * @param index the index of the node
     * @return the index of the right child node
     */
    protected static int getIndexOfRightChild(int index) {
        return 2 * index + 2;
    }

}
