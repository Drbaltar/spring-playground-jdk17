package com.drbaltar.springplayground.repository;

import com.drbaltar.springplayground.models.Lesson;
import org.springframework.data.repository.CrudRepository;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
}
