package com.drbaltar.springplayground.controllers;

import com.drbaltar.springplayground.models.Lesson;
import com.drbaltar.springplayground.repository.LessonRepository;
import com.drbaltar.springplayground.views.LessonViews;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonRepository repository;

    public LessonsController(LessonRepository repository) {
        this.repository = repository;
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
    @JsonView(LessonViews.IDAndTitle.class)
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

}