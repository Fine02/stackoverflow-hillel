package com.ra.course.com.stackoverflow.configuration;

import com.ra.course.com.stackoverflow.repository.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class SpringBootContextTestConfiguration {

    @Bean
    public MemberRepository mockedMemberRepository() {
        return mock(MemberRepository.class);
    }

    @Bean
    public QuestionRepository mockedQuestionRepository() {
        return mock(QuestionRepository.class);
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
    public AnswerRepository mockedAnswerRepository(){
        return mock(AnswerRepository.class);
    }
}
