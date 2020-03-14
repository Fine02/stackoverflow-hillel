package com.ra.course.com.stackoverflow.configuration;

import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

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
}
