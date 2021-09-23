package com.drbaltar.springplayground;

import com.drbaltar.springplayground.textprocessing.WordCounter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigClass {

    @Bean
    public WordCounter getWordCounter() {
        return new WordCounter();
    }
}
