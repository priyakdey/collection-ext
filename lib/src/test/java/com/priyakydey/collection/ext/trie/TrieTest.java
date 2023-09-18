package com.priyakydey.collection.ext.trie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Trie")
class TrieTest {


    @DisplayName("contains a word")
    @Test
    void contains() {
        Trie trie = new Trie();

        List<String> words =
            Arrays.asList("apple", "dapple", "hungry", "orange", "oranges", "watermelon",
                "football", "foosball", "water", "wafer");

        // add all the words to the trie
        trie.addWords(words);

        // ----- all positive assertions below -----
        assertTrue(trie.contains("apple"), "contains apple - expected true");
        assertTrue(trie.contains("dapple"), "contains dapple - expected true");
        assertTrue(trie.contains("water"), "contains water - expected true");
        assertTrue(trie.contains("watermelon"), "contains watermelon - expected true");

        // ----- all negative assertions below -----
        assertFalse(trie.contains("bapple"), "contains bapple: expected false");
        assertFalse(trie.contains("wate"), "contains wate: expected false");
    }


    @DisplayName("if any word/s starts with a prefix")
    @Test
    void startsWith() {
        Trie trie = new Trie();

        List<String> words =
            Arrays.asList("apple", "application", "appetite", "blue", "bluetooth", "blunder",
                "carrot", "carriage", "carpet", "dogma", "dogwood", "dolphin", "elephant",
                "elevator", "electron", "frog", "frost", "frown", "green", "greet");

        // add all the words to the trie
        trie.addWords(words);

        // ----- all positive assertions below -----
        assertTrue(trie.startsWith("appl"), "startsWith appl - expected true");
        assertTrue(trie.startsWith("app"), "startsWith app - expected true");
        assertTrue(trie.startsWith("blue"), "startsWith blue - expected true");
        assertTrue(trie.startsWith("blu"), "startsWith blu - expected true");
        assertTrue(trie.startsWith("car"), "startsWith car - expected true");
        assertTrue(trie.startsWith("carr"), "startsWith carr - expected true");
        assertTrue(trie.startsWith("dog"), "startsWith dog - expected true");
        assertTrue(trie.startsWith("ele"), "startsWith ele - expected true");


        // ----- all negative assertions below -----
        assertFalse(trie.startsWith("abc"), "startsWith abc - expected false");
        assertFalse(trie.startsWith("applo"), "startsWith applo - expected false");
        assertFalse(trie.startsWith("dogi"), "startsWith dogi - expected false");
        assertFalse(trie.startsWith("doll"), "startsWith doll - expected false");
    }

    @DisplayName("delete a word")
    @Test
    void remove() {
        List<String> words =
            Arrays.asList("apple", "dapple", "hungry", "orange", "oranges", "watermelon",
                "football", "foosball", "water", "wafer");

        Trie trie = new Trie();
        trie.addWords(words);

        trie.remove("water");

        assertTrue(trie.contains("wafer"), "contains wafer - expected true");
        assertTrue(trie.contains("watermelon"), "contains watermelon - expected true");

        assertFalse(trie.contains("water"), "contains water - expected false");

    }

    @DisplayName("get recommendations")
    @Test
    void getRecommendations() {
        List<String> words =
            Arrays.asList("rainbow", "raincoat", "rainfall", "raindrop", "rainforest",
                "rainstorm", "rainwater", "rainy", "raincheck", "rained", "rainmaker", "apple",
                "butterfly", "chocolate", "dragon", "elephant", "frog", "guitar", "hamburger");


        Trie trie = new Trie();
        trie.addWords(words);

        List<String> rain = trie.getRecommendations("rain", 5);
        System.out.println(rain);
        assertEquals(5, rain.size(), "expected 5, got 5");
        assertEquals(0, trie.getRecommendations("hooper", 5).size(), "expected 0, got 0");

    }

}