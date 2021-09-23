package com.drbaltar.springplayground.textprocessing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordCounterTest {
    HashMap<String, Integer> expected;
    WordCounter wordCounter = new WordCounter();

    @BeforeEach
    void setUp() {
        expected = new HashMap<>();
        expected.put("A", 1);
        expected.put("brown", 2);
        expected.put("cow", 1);
        expected.put("jumps", 1);
        expected.put("over", 1);
        expected.put("a", 1);
        expected.put("fox", 1);
    }

    @Test
    void shouldReturnMapOfWordsAndNumberOfOccurences() {
        var testString = "A brown cow jumps over a brown fox";
        var actual = wordCounter.count(testString);
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemovePunctuationBeforePlacingWordsInMap() {
        var testString = "A brow#n cow jumps over. a brown fox!";
        var actual = wordCounter.count(testString);
        assertEquals(expected, actual);
    }
}
