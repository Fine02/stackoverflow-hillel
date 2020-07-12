package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository  {

    Tag save(Tag tag);

    Optional<Tag> findById(Long id);

    void delete(Tag tag);

    void update (Tag tag);

    Optional<Tag> findByTagName(String tagName);

    List<Tag> findByQuestionId(Long id);

    List<Tag> findAll();

}
