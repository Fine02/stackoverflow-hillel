package com.ra.course.com.stackoverflow.repository.memory;

import com.ra.course.com.stackoverflow.repository.interfaces.*;

public class InMemoryRepositoryFactory implements RepositoryFactory {
    @Override
    public AnswerRepository createAnswerRepository() {
        return InMemoryAnswerRepository.getInstanceOf();
    }

    @Override
    public QuestionRepository createQuestionRepository() {
        return InMemoryQuestionRepository.getInstanceOf();
    }

    @Override
    public MemberRepository createMemberRepository() {
        return InMemoryMemberRepository.getInstanceOf();
    }

    @Override
    public TagRepository createTagRepository() {
        return InMemoryTagRepository.getInstanceOf();
    }
}
