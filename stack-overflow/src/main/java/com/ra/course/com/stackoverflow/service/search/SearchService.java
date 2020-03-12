package com.ra.course.com.stackoverflow.service.search;

import com.ra.course.com.stackoverflow.entity.Question;

import java.util.List;

public interface SearchService {
    List<Question> searchByTag(final String tagName);

    List<Question> searchInTitle(final String searchPhrase);

    List<Question> searchInTitleByTag(final String searchPhrase, final String tagName);

}
