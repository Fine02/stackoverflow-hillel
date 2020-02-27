package com.ra.course.com.stackoverflow.repository.interfaces;

public interface RepositoryFactory {

    AnswerRepository createAnswerRepository();

    QuestionRepository createQuestionRepository();

    MemberRepository createMemberRepository();

    TagRepository createTagRepository();
}
