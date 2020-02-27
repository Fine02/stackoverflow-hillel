package com.ra.course.com.stackoverflow.repository.memory;

import com.ra.course.com.stackoverflow.dto.AnswerSaveDto;
import com.ra.course.com.stackoverflow.entity.implementations.Answer;
import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.entity.implementations.Question;
import com.ra.course.com.stackoverflow.repository.interfaces.AnswerRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InMemoryAnswerRepository extends InMemoryGeneralRepository<AnswerSaveDto, Answer> implements AnswerRepository {

    @Override
    public List<Answer> listAnswersForQuestion(final Question question) {
        return findAll().stream()
                .filter(Objects::nonNull)
                .filter(a -> a.getQuestion().getId()==question.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Answer> findAllMemberAnswers(final Member member) {
        return getData().values().stream()
                .filter(Objects::nonNull)
                .filter(answer -> answer.getQuestion().getId() == member.getId())
                .collect(Collectors.toList());
    }
}
