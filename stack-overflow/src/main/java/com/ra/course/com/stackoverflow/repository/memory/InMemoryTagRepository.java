package com.ra.course.com.stackoverflow.repository.memory;

import com.ra.course.com.stackoverflow.dto.TagSaveDto;
import com.ra.course.com.stackoverflow.entity.implementations.Question;
import com.ra.course.com.stackoverflow.entity.implementations.Tag;
import com.ra.course.com.stackoverflow.repository.interfaces.TagRepository;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryTagRepository extends InMemoryGeneralRepository<TagSaveDto, Tag> implements TagRepository {

    transient final private Map<Long, Tag> data = super.getData();

    @Override
    public Optional<Tag> findByTagName(final String tagName) {
        return data.values().stream()
                .filter(Objects::nonNull)
                .filter(t -> t.getName().equalsIgnoreCase(tagName))
                .findFirst();
    }

}
