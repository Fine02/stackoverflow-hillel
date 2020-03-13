package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.repository.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@SpringBootTest
public class RepositoryTestConfiguration {

    @Bean
    public MemberRepository mockedMemberRepository() {
        return mock(MemberRepository.class);
    }
    @Bean
    public TagRepository mockedTagRepository() {
        return mock(TagRepository.class);
    }
    @Bean
    public CommentRepository mockedCommentRepository() {
        return mock(CommentRepository.class);
    }
    @Bean
    public BountyRepository mockedBountyRepository() {
        return mock(BountyRepository.class);
    }
    @Bean
    public QuestionRepository mockedQuestionRepository() {
        return mock(QuestionRepository.class);
    }
    @Bean
    public AnswerRepository mockedAnswerRepository(){
        return mock(AnswerRepository.class);
    }
}
