package com.ra.course.com.stackoverflow.service.implementation;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.exception.service.TagNotFoundException;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.TagRepository;
import com.ra.course.com.stackoverflow.service.SearchService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final QuestionRepository questionRepo;
    private final TagRepository tagRepo;

    public List<Question> searchByTag(final String tagName) throws TagNotFoundException {
        if (tagName == null){
            throw new IllegalArgumentException("Tag name is null");
        }

        final Tag tag = getTagByTagName(tagName);

        return questionRepo.findByTag(tag);
    }

    public List<Question> searchInTitle(final String searchPhrase) {
        if (searchPhrase == null){
            throw new IllegalArgumentException("Search phrase is null");
        }

        return questionRepo.findByTitle(searchPhrase);
    }

    public List<Question> searchInTitleByTag(final String searchPhrase, final String tagName) throws TagNotFoundException {
        if (searchPhrase == null || tagName == null){
            throw new IllegalArgumentException("searchInTitleByTag() contains null - searchPhrase is: "+ searchPhrase + " , tagName: " + tagName);
        }

        final Tag tag = getTagByTagName(tagName);

        return questionRepo.findByTitleAndTag(searchPhrase, tag);
    }

    private Tag getTagByTagName(final String tagName) throws TagNotFoundException {
        return tagRepo.findByTagName(tagName)
                      .orElseThrow(() -> new TagNotFoundException("There is no Tag in DB like: " + tagName));
    }
}
