package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.dto.TagSaveDto;
import com.ra.course.com.stackoverflow.entity.implementations.Tag;

import java.util.Optional;

public interface TagRepository extends GeneralRepository<TagSaveDto, Tag>{

    Optional<Tag> findByTagName(String tagName);

}
