package com.drbaltar.springplayground.controllers;

import com.drbaltar.springplayground.textprocessing.WordCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(WordsController.class)
public class WordsControllerTest {

    private final String testString = "A brown cow jumps over a brown fox";
    private final String expected = "{" +
            "  \"A\": 1," +
            "  \"brown\": 2," +
            "  \"cow\": 1," +
            "  \"jumps\": 1," +
            "  \"over\": 1," +
            "  \"a\": 1," +
            "  \"fox\": 1" +
            "}";
    private final HashMap<String, Integer> mockCountReturn = getMockCountReturn();

    @Autowired
    MockMvc mvc;

    @MockBean
    WordCounter wordCounter;

    @BeforeEach
    void setUp() {
        when(wordCounter.count(testString)).thenReturn(mockCountReturn);
    }

    @Test
    void shouldReturnJSONOfWordCount() throws Exception {
        mvc.perform(post("/words/count").content(testString))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }


    private HashMap<String, Integer> getMockCountReturn() {
        var mockHashMap = new HashMap<String, Integer>();
        mockHashMap.put("A", 1);
        mockHashMap.put("brown", 2);
        mockHashMap.put("cow", 1);
        mockHashMap.put("jumps", 1);
        mockHashMap.put("over", 1);
        mockHashMap.put("a", 1);
        mockHashMap.put("fox", 1);
        return mockHashMap;
    }
}
