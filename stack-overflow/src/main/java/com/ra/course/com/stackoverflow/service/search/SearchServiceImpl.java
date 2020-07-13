package com.ra.course.com.stackoverflow.service.search;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.dto.mapper.Mapper;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.exception.service.TagNotFoundException;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    private final QuestionRepository questionRepo;
    private final TagRepository tagRepo;
    private final Mapper<Question, QuestionDto> mapper;

    public List<QuestionDto> searchByTag(final String tagName) {
        final Tag tag = getTagByTagName(tagName);
        return mapper.dtoFromEntity(questionRepo.findByTag(tag));
    }

    public List<QuestionDto> searchInTitle(final String searchPhrase) {
        return mapper.dtoFromEntity(questionRepo.findByTitle(searchPhrase.toLowerCase()));
    }

    public List<QuestionDto> searchInTitleByTag(final String searchPhrase, final String tagName) {
        final Tag tag = getTagByTagName(tagName);
        return mapper.dtoFromEntity(questionRepo.findByTitleAndTag(searchPhrase.toLowerCase(), tag));
    }

    private Tag getTagByTagName(final String tagName) {
        return tagRepo.findByTagName(tagName)
                .orElseThrow(() -> new TagNotFoundException("There is no Tag in DB like: " + tagName));
    }
}
