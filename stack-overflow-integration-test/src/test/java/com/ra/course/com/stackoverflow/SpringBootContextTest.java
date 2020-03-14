package com.ra.course.com.stackoverflow;

import com.ra.course.com.stackoverflow.repository.*;
import com.ra.course.com.stackoverflow.service.member.MemberServiceImpl;
import com.ra.course.com.stackoverflow.service.notifaction.NotificationService;
import com.ra.course.com.stackoverflow.service.system.QuestionScoreBadgeAwarder;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteAnswerService;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteCommentService;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteQuestionService;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteWithRemarkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class SpringBootContextTest {
    @Autowired
    private ApplicationContext context;

    @Test
    void checkContextForAvailabilityOfVoteServices() {
        assertNotNull(context.getBean(VoteAnswerService.class));
        assertNotNull(context.getBean(VoteCommentService.class));
        assertNotNull(context.getBean(VoteQuestionService.class));
        assertNotNull(context.getBean(VoteWithRemarkService.class));
    }

    @Test
    void checkContextForAvailabilityOfNotificationServices() {
        assertNotNull(context.getBean(NotificationService.class));
    }

    @Test
    public void ifMemberServiceImplAddedToContextThenReturnItsBean() {
        assertEquals(MemberServiceImpl.class, context.getBean(MemberServiceImpl.class).getClass());
    }

    @Test
    public void ifQuestionScoreBadgeAwarderAddedToContextThenReturnItsBean() {
        assertEquals(QuestionScoreBadgeAwarder.class, context.getBean(QuestionScoreBadgeAwarder.class).getClass());
    }

    @TestConfiguration
    static class SpringBootContextTestConfiguration {

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
        public AnswerRepository mockedAnswerRepository() {
            return mock(AnswerRepository.class);
        }
    }
}