package com.drbaltar.springplayground.repository;

import com.drbaltar.springplayground.models.Lesson;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
    Lesson findByTitle(String title);

    Iterable<Lesson> findAllByDeliveredOnBetween(Date from, Date to);
}
