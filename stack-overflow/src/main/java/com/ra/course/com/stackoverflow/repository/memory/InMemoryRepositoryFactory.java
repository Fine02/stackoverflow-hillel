package com.ra.course.com.stackoverflow.repository.memory;

import com.ra.course.com.stackoverflow.repository.interfaces.*;

public class InMemoryRepositoryFactory implements RepositoryFactory {
    private final transient InMemoryAnswerRepository answerRepo = new InMemoryAnswerRepository();
    private final transient InMemoryQuestionRepository questionRepo = new InMemoryQuestionRepository();
    private final transient InMemoryMemberRepository memberRepo = new InMemoryMemberRepository();
    private final transient InMemoryTagRepository tagRepo = new InMemoryTagRepository();

    @Override
    public AnswerRepository createAnswerRepository() {
        return answerRepo;
    }

    @Override
    public QuestionRepository createQuestionRepository() {
        return questionRepo;
    }

    @Override
    public MemberRepository createMemberRepository() {
        return memberRepo;
    }

    @Override
    public TagRepository createTagRepository() {
        return tagRepo;
    }
}
