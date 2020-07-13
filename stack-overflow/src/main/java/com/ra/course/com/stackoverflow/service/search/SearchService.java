package com.ra.course.com.stackoverflow.service.search;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.entity.Question;

import java.util.List;

public interface SearchService {
    List<QuestionDto> searchByTag(final String tagName);

    List<QuestionDto> searchInTitle(final String searchPhrase);

    List<QuestionDto> searchInTitleByTag(final String searchPhrase, final String tagName);

}
