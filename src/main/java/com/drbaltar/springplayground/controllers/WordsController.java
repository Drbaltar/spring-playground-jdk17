package com.drbaltar.springplayground.controllers;

import com.drbaltar.springplayground.textprocessing.WordCounter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/words/count")
public class WordsController {
    private final WordCounter wordCounter;

    WordsController(WordCounter wordCounter) {
        this.wordCounter = wordCounter;
    }

    @PostMapping()
    HashMap<String, Integer> getWordCount(@RequestBody String input) {
        return wordCounter.count(input);
    }
}

