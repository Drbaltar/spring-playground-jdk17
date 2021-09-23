package com.drbaltar.springplayground.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaygroundController.class)
public class PlaygroundControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void shouldOnlyReturnFieldsInApplicableView() throws Exception {
        mvc.perform(get("/view/flight/priceOnly"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets[0].price", is(notNullValue())))
                .andExpect(jsonPath("$.tickets[0]", not(hasProperty("passenger"))))
                .andExpect(jsonPath("$", not(hasProperty("departs"))));
    }
}
