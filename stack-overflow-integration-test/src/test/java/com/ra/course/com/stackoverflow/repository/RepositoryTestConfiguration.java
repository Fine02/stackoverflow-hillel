package com.ra.course.com.stackoverflow.repository;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class RepositoryTestConfiguration {
    @Primary
    @Bean
    public MemberRepository mockedMemberRepository() {
        return mock(MemberRepository.class);
    }

    @Primary
    @Bean
    public BountyRepository mockedBountyRepository() {
        return mock(BountyRepository.class);
    }

    @Primary
    @Bean
    public QuestionRepository mockedQuestionRepository() {
        return mock(QuestionRepository.class);
    }

    @Primary
    @Bean
    public AnswerRepository mockedAnswerRepository() {
        return mock(AnswerRepository.class);
    }
}
