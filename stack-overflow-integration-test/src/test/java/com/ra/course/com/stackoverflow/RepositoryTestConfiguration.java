package com.ra.course.com.stackoverflow;

import com.ra.course.com.stackoverflow.repository.*;
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
    public TagRepository mockedTagRepository() {
        return mock(TagRepository.class);
    }
    @Primary
    @Bean
    public CommentRepository mockedCommentRepository() {
        return mock(CommentRepository.class);
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
    public AnswerRepository mockedAnswerRepository(){
        return mock(AnswerRepository.class);
    }
}
