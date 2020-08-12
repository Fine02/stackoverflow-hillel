package com.ra.course.com.stackoverflow.service.search;

import com.ra.course.com.stackoverflow.dto.post.QuestionDto;

import java.util.List;

public interface SearchQuestionService {

    List<QuestionDto> searchByAuthorId(Long id);

    List<QuestionDto> searchByTag(String tagName);

    List<QuestionDto> searchInTitle(String searchPhrase);

    List<QuestionDto> searchByTitleAndTagName(String searchPhrase, String tagName);

}
