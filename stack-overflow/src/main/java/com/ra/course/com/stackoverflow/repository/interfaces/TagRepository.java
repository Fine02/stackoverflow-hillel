package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository  {

    Tag save(Tag tag);

    long getNextId();

    Optional<Tag> findById(long id);

    void delete(Tag tag);

    Tag update (Tag tag);

    List<Tag> findAll();

    Optional<Tag> findByTagName(String tagName);

    long findIdByName(String tag);
}
