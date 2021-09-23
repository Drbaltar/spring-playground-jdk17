package com.drbaltar.springplayground.textprocessing;

import java.util.HashMap;

public class WordCounter {

    public HashMap<String, Integer> count(String inputString) {
        var countHashMap = new HashMap<String, Integer>();

        for (String word : getArrayOfWordsInString(inputString))
            updateWordCountInMap(countHashMap, word);

        return countHashMap;
    }

    private void updateWordCountInMap(HashMap<String, Integer> countHashMap, String word) {
        if (countHashMap.containsKey(word))
            countHashMap.put(word, countHashMap.get(word) + 1);
        else
            countHashMap.put(word, 1);
    }

    private String[] getArrayOfWordsInString(String testString) {
        var wordArray = testString.split(" ");
        removeAllPunctuation(wordArray);
        return wordArray;
    }

    private void removeAllPunctuation(String[] wordArray) {
        for (int i = 0; i < wordArray.length; i++)
            wordArray[i] = wordArray[i].replaceAll("\\p{Punct}", "");
    }
}
