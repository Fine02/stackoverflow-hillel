package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.dto.QuestionDto;

public interface QuestionStorageService {

    QuestionDto getById(Long id);
}
