package com.ra.course.com.stackoverflow.repository.memory;

import com.ra.course.com.stackoverflow.dto.QuestionSaveDto;
import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.entity.implementations.Question;
import com.ra.course.com.stackoverflow.entity.implementations.Tag;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class InMemoryQuestionRepository extends InMemoryGeneralRepository<QuestionSaveDto, Question> implements QuestionRepository {
    @Override
    public List<Question> findInTitle(final String textToSearch) {
        return getData().values().stream()
                .filter(Objects::nonNull)
                .filter(q -> q.getTitle().toLowerCase(Locale.ENGLISH).contains(textToSearch.toLowerCase(Locale.ENGLISH)))
                .collect(Collectors.toList());
    }


    @Override
    public List<Tag> findTagsByQuestion(final Question question) {
        return question.getTagList();
    }

    @Override
    public List<Question> findMemberQuestions(final Member member) {
        return getData().values().stream()
                .filter(q -> q.getAuthor().getId() == member.getId())
                .collect(Collectors.toList());
    }
}
