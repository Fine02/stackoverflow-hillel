package com.ra.course.com.stackoverflow;

import com.ra.course.com.stackoverflow.configuration.SpringBootContextTestConfiguration;
import com.ra.course.com.stackoverflow.service.member.MemberServiceImpl;
import com.ra.course.com.stackoverflow.service.system.QuestionScoreBadgeAwarder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SpringBootContextTestConfiguration.class)
public class SpringBootContextTest {

    @Autowired
    ApplicationContext context;

    @Test
    public void ifMemberServiceImplAddedToContextThenReturnItsBean() {
        assertEquals(MemberServiceImpl.class, context.getBean(MemberServiceImpl.class).getClass());
    }

    @Test
    public void ifQuestionScoreBadgeAwarderAddedToContextThenReturnItsBean() {
        assertEquals(QuestionScoreBadgeAwarder.class, context.getBean(QuestionScoreBadgeAwarder.class).getClass());
    }
}
