package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.implementations.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository<T>  {

    long save(T t);

    Optional<Tag> findById(long id);

    void delete(Tag tag);

    Tag update (Tag tag);

    List<Tag> findAll();

    Optional<Tag> findByTagName(String tagName);

}
