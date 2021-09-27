package com.drbaltar.springplayground.controllers;

import com.drbaltar.springplayground.models.Lesson;
import com.drbaltar.springplayground.repository.LessonRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonRepository repository;

    public LessonsController(LessonRepository repository) {
        this.repository = repository;
    }

    public static Date getDateFromString(String dateAsString) {
        String[] dateValues = dateAsString.split("-");
        return new Date(Integer.parseInt(dateValues[0]) - 1900, Integer.parseInt(dateValues[1]) - 1, Integer.parseInt(dateValues[2]));
    }

    @GetMapping("")
    public Iterable<Lesson> all() {
        return this.repository.findAll();
    }

    @PostMapping("")
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @GetMapping("/{id}")
    public Optional<Lesson> getLessonById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteLessonById(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            return "Lesson " + id + " was deleted successfully!";
        } catch (EmptyResultDataAccessException e) {
            return "Lesson %d cannot be deleted because it was not found!".formatted(id);
        }
    }

    @PatchMapping("/{id}")
    public Lesson updateLesson(@PathVariable Long id, @RequestBody HashMap<String, String> lessonMap) {
        Optional<Lesson> lessonReturn = repository.findById(id);

        if (lessonReturn.isPresent()) {
            Lesson lesson = lessonReturn.get();
            if (lessonMap.containsKey("title")) {
                lesson.setTitle(lessonMap.get("title"));
            }
            if (lessonMap.containsKey("deliveredOn")) {
                Date deliveredOn = getDateFromString(lessonMap.get("deliveredOn"));
                lesson.setDeliveredOn(deliveredOn);
            }
            return repository.save(lesson);
        } else {
            return null;
        }
    }

    @GetMapping("/find/{title}")
    public Lesson findLessonByTitle(@PathVariable String title) {
        return repository.findByTitle(title);
    }

    @GetMapping("/between")
    public Iterable<Lesson> getLessonsThatOccurBetweenDates(@RequestParam(value = "date1") String from, @RequestParam(value = "date2") String to) {
        Date fromDate = getDateFromString(from);
        Date toDate = getDateFromString(to);
        return repository.findAllByDeliveredOnBetween(fromDate, toDate);
    }
}