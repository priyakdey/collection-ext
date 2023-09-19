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

    @DisplayName("should not add word when input is empty")
    @Test
    void addEmptyWord() {
        Trie trie = new Trie();
        trie.addWord("");
        int actual = trie.size();
        assertEquals(0, actual, String.format("expected 0, but got %d", actual));
    }

    @DisplayName("should add a list of valid english words")
    @Test
    void addWords() {
        Trie trie = new Trie();

        List<String> dictionary =
            List.of("apple", "dapple", "hungry", "orange", "oranges", "watermelon",
                "football", "foosball", "water", "wafer");
        trie.addWords(dictionary);

        int actual = trie.size();

        assertEquals(dictionary.size(), actual,
            String.format("expected %d, but got %d", dictionary.size(), actual));
    }

    @DisplayName("should return true/false if word is present/not present")
    @Test
    void contains() {
        Trie trie = new Trie();

        List<String> words =
            List.of("apple", "dapple", "hungry", "orange", "oranges", "watermelon",
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
        assertFalse(trie.contains(""), "contains \"\": expected false");
    }


    @DisplayName("should return if any word/s starts with a prefix")
    @Test
    void startsWithAPrefix() {
        Trie trie = new Trie();

        List<String> words =
            List.of("apple", "application", "appetite", "blue", "bluetooth", "blunder",
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

    @DisplayName("should return 'true' if prefix is empty string and words present")
    @Test
    void startsWithANonEmptyPrefixForNonEmptyTrie() {
        Trie trie = new Trie();

        List<String> words =
            List.of("apple", "application", "appetite", "blue", "bluetooth", "blunder",
                "carrot", "carriage", "carpet", "dogma", "dogwood", "dolphin", "elephant",
                "elevator", "electron", "frog", "frost", "frown", "green", "greet");

        // add all the words to the trie
        trie.addWords(words);

        assertTrue(trie.startsWith(""), "startsWith \"\" - expected true");
    }

    @DisplayName("should return 'false' if prefix is empty string and no words present")
    @Test
    void startsWithANonEmptyPrefix() {
        assertFalse(new Trie().startsWith(""), "startsWith \"\" - expected true");
    }

    @DisplayName("should delete a word which exists - 1")
    @Test
    void removeExistingWord1() {
        List<String> words =
            List.of("apple", "dapple", "hungry", "orange", "oranges", "watermelon",
                "football", "foosball", "water", "wafer");

        Trie trie = new Trie();
        trie.addWords(words);

        // word exists and will be removed
        trie.remove("water");
        assertTrue(trie.contains("wafer"), "contains wafer - expected true");
        assertTrue(trie.contains("watermelon"), "contains watermelon - expected true");

        assertFalse(trie.contains("water"), "contains water - expected false");

        assertEquals(trie.size(), words.size() - 1,
            String.format("expected %d, but got %d", words.size() - 1, trie.size()));

    }

    @DisplayName("should delete a word which exists - 2")
    @Test
    void removeExistingWord2() {
        List<String> words =
            List.of("apple", "dapple", "hungry", "orange", "oranges", "watermelon",
                "football", "foosball", "water", "wafer");

        Trie trie = new Trie();
        trie.addWords(words);

        // word exists and will be removed
        trie.remove("watermelon");
        assertTrue(trie.contains("wafer"), "contains wafer - expected true");
        assertTrue(trie.contains("water"), "contains water - expected true");

        assertFalse(trie.contains("watermelon"), "contains watermelon - expected false");

        assertEquals(trie.size(), words.size() - 1,
            String.format("expected %d, but got %d", words.size() - 1, trie.size()));

    }

    @DisplayName("should not delete a prefix")
    @Test
    void removePrefix() {
        List<String> words =
            List.of("apple", "dapple", "hungry", "orange", "oranges", "watermelon",
                "football", "foosball", "water", "wafer");

        Trie trie = new Trie();
        trie.addWords(words);

        // try deleting a prefix
        trie.remove("wa");

        assertTrue(trie.contains("water"), "contains water - expected true");
        assertTrue(trie.contains("wafer"), "contains wafer - expected true");
        assertTrue(trie.contains("watermelon"), "contains watermelon - expected true");

        assertEquals(trie.size(), words.size(),
            String.format("expected %d, but got %d", words.size(), trie.size()));
    }


    @DisplayName("should not delete a non existing word")
    @Test
    void removeNonExistentWord() {
        List<String> words =
            List.of("apple", "dapple", "hungry", "orange", "oranges", "watermelon",
                "football", "foosball", "water", "wafer");

        Trie trie = new Trie();
        trie.addWords(words);

        // try deleting a non-existing word
        trie.remove("hope");

        assertTrue(trie.contains("water"), "contains water - expected true");
        assertTrue(trie.contains("wafer"), "contains wafer - expected true");
        assertTrue(trie.contains("watermelon"), "contains watermelon - expected true");

        assertEquals(trie.size(), words.size(),
            String.format("expected %d, but got %d", words.size(), trie.size()));
    }


    @DisplayName("should not delete when input is empty string")
    @Test
    void removeEmptyString() {
        List<String> words =
            List.of("apple", "dapple", "hungry", "orange", "oranges", "watermelon",
                "football", "foosball", "water", "wafer");

        Trie trie = new Trie();
        trie.addWords(words);

        trie.remove("");

        assertEquals(trie.size(), words.size(),
            String.format("expected %d, but got %d", words.size(), trie.size()));
    }

    @DisplayName("should return 'n' recommendations when prefix present - 1")
    @Test
    void getRecommendationsWhenPrefixPresent() {
        List<String> words =
            List.of("rainbow", "raincoat", "rainfall", "raindrop", "rainforest",
                "rainstorm", "rainwater", "rainy", "raincheck", "rained", "rainmaker", "apple",
                "butterfly", "chocolate", "dragon", "elephant", "frog", "guitar", "hamburger");


        Trie trie = new Trie();
        trie.addWords(words);

        int n = 5;
        List<String> recommendations = trie.getRecommendations("rain", n);
        assertEquals(n, recommendations.size(),
            String.format("expected %d, got %d", n, recommendations.size()));
    }

    @DisplayName("should return 'n' recommendations when prefix present - 2")
    @Test
    void getRecommendationsWhenPrefixPresent2() {
        List<String> words =
            List.of("rain", "rainbow", "raincoat", "rainfall", "raindrop", "rainforest",
                "rainstorm", "rainwater", "rainy", "raincheck", "rained", "rainmaker", "apple",
                "butterfly", "chocolate", "dragon", "elephant", "frog", "guitar", "hamburger");


        Trie trie = new Trie();
        trie.addWords(words);

        int n = 25;
        List<String> recommendations = trie.getRecommendations("rain", n);

        int expected = (int) words.stream().filter(word -> word.startsWith("rain")).count();

        assertEquals(expected, recommendations.size(),
            String.format("expected %d, got %d", expected, recommendations.size()));
    }

    @DisplayName("should return '0' recommendations when prefix not present")
    @Test
    void getRecommendationsWhenPrefixNotPresent() {
        List<String> words =
            List.of("rainbow", "raincoat", "rainfall", "raindrop", "rainforest",
                "rainstorm", "rainwater", "rainy", "raincheck", "rained", "rainmaker", "apple",
                "butterfly", "chocolate", "dragon", "elephant", "frog", "guitar", "hamburger");


        Trie trie = new Trie();
        trie.addWords(words);

        int n = 5;
        List<String> recommendations = trie.getRecommendations("hopper", n);
        assertEquals(0, recommendations.size(),
            String.format("expected 0, got %d", recommendations.size()));
    }

}