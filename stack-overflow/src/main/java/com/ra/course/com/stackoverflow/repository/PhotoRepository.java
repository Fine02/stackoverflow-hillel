package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Photo;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository {
    Photo save(Photo photo);

    Optional<Photo> findById(Long id);

    void deleteById(Long id);

    List<Photo> findByAnswerId(Long id);

    List<Photo> findByQuestionId(Long id);
}