package com.priyakydey.collection.ext.trie;

import guru.nidi.graphviz.attribute.Attributes;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Trie or Prefix Tree data structure for storing and manipulating a large set of strings.
 * <p>
 * This Trie implementation provides methods for adding, checking existence, and generating visual representation
 * of stored strings.
 * </p>
 * <p>
 * Reference - <a href="https://en.wikipedia.org/wiki/Trie">Trie</a>
 * </p>
 * <p>
 * <strong>This implementation only supports English lowercase letters in the range of ASCII 97-122.
 * It does not support UTF-8 or upper case english letter. Adding such strings to the Trie might
 * make it behave in weird ways.</strong>
 * </p>
 *
 * @author Priyak Dey
 */
public class Trie {

    private final Node root;

    /* keeps track of current count of words in the dictionary */
    private int size;

    public Trie() {
        this.root = new Node();
    }

    /**
     * Adds a list of words to the trie.
     *
     * @param words the list of words to be added
     */
    public void addWords(List<String> words) {
        for (String word : words) {
            this.addWord(word);
        }
    }

    /**
     * Adds a single word to the trie.
     *
     * @param word the word to be added
     */
    public void addWord(String word) {
        if (word.isEmpty()) {
            return;
        }

        Node curr = root;

        for (char ch : word.toCharArray()) {
            if (curr.children[ch - 'a'] == null) {
                curr.children[ch - 'a'] = new Node();
            }
            curr = curr.children[ch - 'a'];
        }

        curr.isWord = true;
        size++;
    }

    /**
     * Checks if the word exists in the trie.
     *
     * @param word the word to check
     * @return true if the word exists, false otherwise
     */
    public boolean contains(String word) {
        if (word.isEmpty()) {
            return false;
        }

        Node curr = root;
        for (char ch : word.toCharArray()) {
            Node node = curr.children[ch - 'a'];
            if (node == null) {
                return false;
            }
            curr = node;
        }

        return curr.isWord;
    }

    /**
     * Checks if any word in the trie starts with the given prefix.
     *
     * @param prefix the prefix to check
     * @return true if any word starts with the prefix, false otherwise
     */
    public boolean startsWith(String prefix) {
        if (prefix.isEmpty()) {
            return size != 0;
        }

        Node curr = root;
        for (char ch : prefix.toCharArray()) {
            Node node = curr.children[ch - 'a'];
            if (node == null) {
                return false;
            }
            curr = node;
        }

        return true;
    }

    /**
     * Removes a word from the trie.
     *
     * @param word the word to be removed
     */
    public void remove(String word) {
        if (word.isEmpty()) {
            return;
        }

        Node curr = root;
        List<Node> visited = new ArrayList<>();

        for (char ch : word.toCharArray()) {
            Node node = curr.children[ch - 'a'];
            if (node == null) {
                return;
            }

            visited.add(node);
            curr = node;
        }

        if (!curr.isWord) {
            return;
        }

        // mark ending of word as false
        curr.isWord = false;

        // iterate over the visited list and remove any nodes if it not does have a branch of not
        // word marker
        for (int i = visited.size() - 1; i >= 1; i--) {
            Node node = visited.get(i);
            if (Stream.of(node.children).noneMatch(Objects::nonNull) && !node.isWord) {
                Node parent = visited.get(i - 1);
                parent.children[word.charAt(i) - 'a'] = null;
            }
        }
        size--;
    }

    /**
     * Returns a list of recommended words based on the provided prefix.
     *
     * <p>
     * This method fetches a list of words stored in the Trie that start with the given prefix.
     * The number of recommendations returned is limited by the parameter {@code n}.
     * If no words match the prefix, the search will return an empty list.
     * </p>
     *
     * @param prefix The string prefix to search for.
     * @param n      The maximum number of recommendations to return.
     * @return A list containing up to n words that start with the provided prefix.
     */
    public List<String> getRecommendations(String prefix, int n) {
        Node curr = root;
        for (char ch : prefix.toCharArray()) {
            Node node = curr.children[ch - 'a'];
            if (node == null) {
                return new ArrayList<>();
            }
            curr = node;
        }

        List<String> recommendations = new ArrayList<>(n);
        if (curr.isWord) {
            recommendations.add(prefix);
        }

        StringBuilder buf = new StringBuilder(prefix);

        generateWords(curr, buf, recommendations, n);

        return recommendations;
    }

    /**
     * Returns the total count of words in the trie.
     *
     * @return count of words in the trie.
     */
    public int size() {
        return size;
    }

    /**
     * Converts the trie into an SVG representation using Graphviz.
     *
     * @return SVG string representation of the trie
     */
    public String toSvg() {
        MutableGraph graph = Factory.mutGraph().setDirected(true);
        MutableNode parent = Factory.mutNode("ROOT").add(Color.BLACK);
        graph.add(parent);

        traverseTree(root, parent, 1, new Counter());

        return Graphviz.fromGraph(graph).render(Format.SVG).toString();
    }

    // ---- Internal helpers ----

    /**
     * Recursively traverses the trie nodes and updates the graph nodes using Graphviz.
     *
     * @param curr    Current trie node being traversed.
     * @param parent  Parent node in the graph.
     * @param depth   Current depth in the trie.
     * @param counter Counter for unique node IDs in the graph.
     */
    private void traverseTree(Node curr, MutableNode parent, int depth, Counter counter) {
        if (curr == null) {
            return;
        }

        for (int i = 0; i < curr.children.length; i++) {
            Node child = curr.children[i];
            if (child != null) {
                String label = Character.toString((char) (i + 'a'));
                String id = label + "_" + depth + "_" + counter.count++;
                MutableNode node = Factory.mutNode(id).add(Attributes.attr("label", label));

                if (child.isWord) {
                    node.add(Color.RED);
                } else {
                    node.add(Color.BLACK);
                }

                parent.addLink(node);
                traverseTree(child, node, depth + 1, counter);
            }
        }
    }

    /**
     * Recursively generates words from the Trie based on the current node and the current prefix.
     *
     * <p>
     * This helper method assists in fetching word recommendations from the Trie.
     * The method traverses the Trie, adding characters to the current buffer to construct potential words.
     * If a valid word is formed (indicated by the {@code isWord} flag in a node), the word is added to the list of words.
     * </p>
     *
     * <p>
     * The search and word generation will terminate if the number of words in the list reaches the specified maximum size.
     * </p>
     *
     * @param node    The current Trie node being inspected.
     * @param buf     The current buffer containing the string being formed.
     * @param words   The list where valid words are added.
     * @param maxSize The maximum number of words the list should contain.
     */
    private void generateWords(Node node, StringBuilder buf, List<String> words, int maxSize) {
        if (words.size() == maxSize) {
            return;
        }

        Node[] children = node.children;
        for (int i = 0; i < children.length; i++) {
            Node child = children[i];
            if (child != null) {
                char ch = (char) (i + 'a');
                buf.append(ch);
                if (child.isWord) {
                    words.add(buf.toString());
                }
                if (words.size() == maxSize) {
                    return;
                }
                generateWords(child, buf, words, maxSize);
                buf.deleteCharAt(buf.length() - 1);
            }
        }
    }

    /*
     * Represents a single node in the Trie.
     */
    private static class Node {
        Node[] children;
        boolean isWord;

        public Node() {
            this.children = new Node[26];
            this.isWord = false;
        }
    }

    /*
     * Utility class for counting during traversal.
     */
    private static class Counter {
        int count = 0;
    }

}
