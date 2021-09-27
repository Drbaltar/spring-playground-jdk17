package com.drbaltar.springplayground.controllers;

import com.drbaltar.springplayground.models.Lesson;
import com.drbaltar.springplayground.repository.LessonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.drbaltar.springplayground.controllers.LessonsController.getDateFromString;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LessonsControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    LessonRepository repository;

    @Transactional
    @Rollback
    @Test
    void shouldGetLessonsFromTheDatabase() throws Exception {
        var lesson = populateDBWithOneEntry("Intro To Databases", "2021-10-31");

        mvc.perform(get("/lessons"))
                .andExpect(jsonPath("$[0].id", equalTo(lesson.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(lesson.getTitle())))
                .andExpect(jsonPath("$[0].deliveredOn", is("2021-10-31")));
    }

    @Transactional
    @Rollback
    @Test
    void shouldGetLessonFromTheDatabaseByID() throws Exception {
        var lesson = populateDBWithOneEntry("Intro To Databases", "2021-10-31");

        mvc.perform(get("/lessons/%d".formatted(lesson.getId())))
                .andExpect(jsonPath("$.id", equalTo(lesson.getId().intValue())))
                .andExpect(jsonPath("$.title", is(lesson.getTitle())))
                .andExpect(jsonPath("$.deliveredOn", is("2021-10-31")));
    }

    @Transactional
    @Rollback
    @Test
    void shouldSaveLessonToTheDB() throws Exception {
        var testJSON = """
                {
                    "title": "Intro to DB",
                    "deliveredOn": "2021-10-31"
                }
                """;

        var request = post("/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testJSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.title", is("Intro to DB")))
                .andExpect(jsonPath("$.deliveredOn", is("2021-10-31")));
    }

    @Transactional
    @Rollback
    @Test
    void shouldPatchExistingEntry() throws Exception {
        var testJSON = """
                {
                    "title": "Intro to DB",
                    "deliveredOn": "2021-10-31"
                }
                """;
        var lesson = populateDBWithOneEntry("Chemistry 101", "2021-9-24");

        var expected = """
                {
                    "id": %d,
                    "title": "Intro to DB",
                    "deliveredOn": "2021-10-31"
                }
                """.formatted(lesson.getId());

        var request = patch("/lessons/%d".formatted(lesson.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(testJSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldFindLessonByTitle() throws Exception {
        Lesson lesson = populateDBWithOneEntry("SQL", "2021-9-27");
        var request = get("/lessons/find/%s".formatted(lesson.getTitle()));
        var testJSONResult = """
                {
                       "id": %d,
                        "title": "SQL",
                        "deliveredOn": "2021-09-27"
                }
                """.formatted(lesson.getId());

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(testJSONResult));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldReturnLessonsThatOccurBetweenDatesInput() throws Exception {
        populateDBWithOneEntry("SQL", "2021-9-27");
        populateDBWithOneEntry("Chemistry", "2015-9-27");
        populateDBWithOneEntry("Biology", "2010-9-27");

        var request = get("/lessons/between?date1=2009-10-10&date2=2016-02-13");

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    private Lesson populateDBWithOneEntry(String title, String dateAsString) {
        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        Date deliveredOn = getDateFromString(dateAsString);
        lesson.setDeliveredOn(deliveredOn);
        repository.save(lesson);
        return lesson;
    }
}
