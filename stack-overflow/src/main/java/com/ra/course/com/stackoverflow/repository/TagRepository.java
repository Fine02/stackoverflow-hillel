package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Tag;

import java.util.Optional;

public interface TagRepository  {

    Tag save(Tag tag);

    Optional<Tag> findById(long id);

    void delete(Tag tag);

    void update (Tag tag);

    Optional<Tag> findByTagName(String tagName);

}
