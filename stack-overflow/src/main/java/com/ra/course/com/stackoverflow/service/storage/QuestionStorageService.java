package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.dto.QuestionDto;

import java.util.List;

public interface QuestionStorageService {

    QuestionDto getById(Long id);

    List<QuestionDto> getByAuthorId(Long id);

    List<String> getAllTagsName();
}
