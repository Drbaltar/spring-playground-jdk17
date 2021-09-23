package com.drbaltar.springplayground.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void shouldReturnDefaultWelcomeMessage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from Spring!"));
    }

    @Test
    void shouldReturnWelcomeMessageWithInputName() throws Exception {
        mvc.perform(get("/?name=Kyle"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Kyle from Spring!"));
    }

    @Test
    void shouldReturnPIOnGETRequest() throws Exception {
        mvc.perform(get("/math/pi"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(Math.PI)));
    }

    @Nested
    class FormData {
        MockHttpServletRequestBuilder request;

        @BeforeEach
        void setUp() {
            request = post("/comments")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("name", "Kyle")
                    .param("city", "Austin");
        }

        @Test
        void shouldReturnCorrectStringResponse() throws Exception {
            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(content().string("Kyle lives in Austin!"));

        }
    }

    @Nested
    class MathCalculateEndpoint {
        @ParameterizedTest
        @CsvSource({
                "'/math/calculate?operation=add&x=4&y=6', '4 + 6 = 10'",
                "'/math/calculate?operation=multiply&x=4&y=6', '4 * 6 = 24'",
                "'/math/calculate?operation=subtract&x=4&y=6', '4 - 6 = -2'",
                "'/math/calculate?operation=divide&x=30&y=5', '30 / 5 = 6'",
        })
        void shouldReturnCalculateString(String testURL, String expected) throws Exception {
            mvc.perform(get(testURL))
                    .andExpect(status().isOk())
                    .andExpect(content().string(expected));
        }
    }

    @Nested // Math Volume exercise
    class volumeWithPathTests {
        @Test
        void testVolume() throws Exception {
            mvc.perform(post("/math/volume/3/4/5"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("The volume of a 3x4x5 rectangle is 60"));
        }
    }

    @Nested // Math Calculate Area exercise
    class testArea {
        @Test
        void testCircleArea() throws Exception {
            MockHttpServletRequestBuilder request = post("/math/area")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("type", "circle")
                    .param("radius", "4");
//                    .param("width","3")
//                    .param("height", "10");


            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(content().string("Area of a circle with a radius of 4 is 50.26548"));
        }

        @Test
        void testCircleMethodWithRectangleType() throws Exception {
            MockHttpServletRequestBuilder request = post("/math/area")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("type", "rectangle")
                    .param("radius", "4");
//                    .param("width","3")
//                    .param("height", "10");

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(content().string("Invalid"));
        }

        @Test
        void testCircleMethodWithNonIntegerRadius() throws Exception {
            MockHttpServletRequestBuilder request = post("/math/area")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("type", "circle")
                    .param("radius", "x");
//                    .param("width","3")
//                    .param("height", "10");

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(content().string("Invalid"));
        }

        @Test
        void testRectangleArea() throws Exception {
            MockHttpServletRequestBuilder request = post("/math/area")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("type", "rectangle")
                    //.param("radius","4");
                    .param("width", "3")
                    .param("height", "10");

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(content().string("Area of a 3x10 rectangle is 30"));
        }

        @Test
        void testRectangleAreaWithCircle() throws Exception {
            MockHttpServletRequestBuilder request = post("/math/area")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("type", "circle")
                    //.param("radius","4");
                    .param("width", "3")
                    .param("height", "10");

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(content().string("Invalid"));
        }

        @Test
        void testRectangleMethodWithNonIntegerWidthOrHeight() throws Exception {
            MockHttpServletRequestBuilder request = post("/math/area")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("type", "rectangle")
                    //.param("radius","x");
                    .param("width", "x")
                    .param("height", "10");

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(content().string("Invalid"));

            request = post("/math/area")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("type", "rectangle")
                    //.param("radius","x");
                    .param("width", "4")
                    .param("height", "y");

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(content().string("Invalid"));
        }
    }

}
