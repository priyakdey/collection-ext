package com.priyakydey.collection.ext.example;

import com.priyakydey.collection.ext.trie.Trie;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Priyak Dey
 */
public class TrieExample {

    public static void main(String[] args) {
        Trie trie = new Trie();

        List<String> words = Arrays.asList(
            "apple", "dapple", "hungry", "orange", "oranges", "watermelon", "football", "foosball",
            "water", "wafer");

        // add all the words to the trie
        trie.addWords(words);

        // dump svg
        dumpTrie(trie, "./examples/words_example_1.svg");

        trie.remove("water");
        dumpTrie(trie, "./examples/words_example_2.svg");
    }

    private static void dumpTrie(Trie trie, String filepath) {
        String svg = trie.toSvg();

        try (BufferedWriter bw = new BufferedWriter(
            new FileWriter(filepath))) {
            bw.write(svg);
        } catch (IOException ex) {
            System.err.printf("ERROR: error when writing to file.. %s\n", ex.getMessage());
        }
    }

}
