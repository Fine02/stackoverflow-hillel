package com.ra.course.com.stackoverflow.service.search.impl;

import com.ra.course.com.stackoverflow.dto.post.QuestionDto;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.dto.mapper.QuestionMapper;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.search.SearchQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Service
public class SearchQuestionServiceImpl implements SearchQuestionService {

    private final transient QuestionRepository questionData;
    private final transient RepositoryUtils utils;

    @Override
    public List<QuestionDto> searchByTag(final String tagName) {
        final Tag tag = utils.getTagFromDBByTagName(tagName);
        final var questions = questionData.findByTag(tag);
        return QuestionMapper.MAPPER.toQuestionDto(questions);
    }

    @Override
    public List<QuestionDto> searchInTitle(final String searchPhrase) {
        final var questions = questionData.findByTitle(searchPhrase.toLowerCase(Locale.US));
        return QuestionMapper.MAPPER.toQuestionDto(questions);
    }

    @Override
    public List<QuestionDto> searchByTitleAndTagName(final String searchPhrase, final String tagName) {
        final Tag tag = utils.getTagFromDBByTagName(tagName);
        final var questions = questionData.findByTitleAndTag(searchPhrase.toLowerCase(Locale.US), tag);
        return QuestionMapper.MAPPER.toQuestionDto(questions);
    }

    @Override
    public List<QuestionDto> searchByAuthorId(final Long id) {
        final var questions = questionData.findByMemberId(id);
        return QuestionMapper.MAPPER.toQuestionDto(questions);
    }

}
