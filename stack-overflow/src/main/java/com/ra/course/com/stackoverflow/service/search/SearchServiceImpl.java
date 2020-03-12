package com.ra.course.com.stackoverflow.service.search;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.exception.service.TagNotFoundException;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.repository.TagRepository;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final QuestionRepository questionRepo;
    private final TagRepository tagRepo;

    public List<Question> searchByTag(final String tagName) {
        if (tagName != null) {
            final Tag tag = getTagByTagName(tagName);
            return questionRepo.findByTag(tag);
        }
        return Collections.emptyList();
    }

    public List<Question> searchInTitle(final String searchPhrase) {
        if (searchPhrase != null) {
            return questionRepo.findByTitle(searchPhrase);
        }
        return Collections.emptyList();
    }

    public List<Question> searchInTitleByTag(final String searchPhrase, final String tagName) {
        if (searchPhrase != null && tagName != null) {
            final Tag tag = getTagByTagName(tagName);
            return questionRepo.findByTitleAndTag(searchPhrase, tag);
        }
        return Collections.emptyList();
    }

    private Tag getTagByTagName(final String tagName) {
        return tagRepo.findByTagName(tagName)
                      .orElseThrow(() -> new TagNotFoundException("There is no Tag in DB like: " + tagName));
    }
}
