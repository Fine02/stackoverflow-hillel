package com.ra.course.com.stackoverflow;

import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.service.member.MemberServiceImpl;
import com.ra.course.com.stackoverflow.service.system.QuestionScoreBadgeAwarder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ServiceContextTest {

    @Autowired
    ApplicationContext context;

    @MockBean
    QuestionRepository mockedQuestionRepository;

    @MockBean
    MemberRepository mockedMemberRepository;

    @Test
    public void whenMemberServiceImplAddedToContextThenReturnItsBean() {
        assertEquals(MemberServiceImpl.class, context.getBean(MemberServiceImpl.class).getClass());
    }

    @Test
    public void whenQuestionScoreBadgeAwarderAddedToContextThenReturnItsBean() {
        assertEquals(QuestionScoreBadgeAwarder.class, context.getBean(QuestionScoreBadgeAwarder.class).getClass());
    }
}
