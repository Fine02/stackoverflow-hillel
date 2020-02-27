package com.ra.course.com.stackoverflow.repository.memory;

import com.ra.course.com.stackoverflow.repository.interfaces.*;

public class InMemoryRepositoryFactory implements RepositoryFactory {
    @Override
    public AnswerRepository createAnswerRepository() {
        return new InMemoryAnswerRepository();
    }

    @Override
    public QuestionRepository createQuestionRepository() {
        return new InMemoryQuestionRepository();
    }

    @Override
    public MemberRepository createMemberRepository() {
        return new InMemoryMemberRepository();
    }

    @Override
    public TagRepository createTagRepository() {
        return new InMemoryTagRepository();
    }
}
