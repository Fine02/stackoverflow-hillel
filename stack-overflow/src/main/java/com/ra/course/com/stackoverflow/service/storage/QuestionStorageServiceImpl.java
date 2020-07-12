package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.dto.mapper.impl.QuestionMapper;
import com.ra.course.com.stackoverflow.dto.mapper.impl.TagMapper;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionStorageServiceImpl implements QuestionStorageService {

    private final transient QuestionRepository questionRepository;
    private final transient TagRepository tagRepository;
    private final transient QuestionMapper questionMapper;

    @Override
    public List<QuestionDto> getByAuthorId(final Long id) {
        final var questions = questionRepository.findByMemberId(id);
        questions.forEach(this::addTagList);
        return questionMapper.dtoFromEntity(questions);
    }

    @Override
    public QuestionDto getById(final Long id) {
        final var question = addTagList(getQuestionFromDb(id));
        return questionMapper.dtoFromEntity(question);
    }

    private Question getQuestionFromDb (final Long id){
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("No such question"));
    }

    private Question addTagList(final Question question){
        final var tags = tagRepository.findByQuestionId(question.getId());
        question.setTagList(tags);
        return question;
    }

    @Override
    public List<String> getAllTagsName() {
        return tagRepository.findAll().stream()
                .map(Tag::getName).collect(Collectors.toList());
    }
}
