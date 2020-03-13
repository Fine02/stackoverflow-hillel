package com.ra.course.com.stackoverflow;

import com.ra.course.com.stackoverflow.service.notifaction.NotificationService;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteAnswerService;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteCommentService;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteQuestionService;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteWithRemarkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes =  RepositoryTestConfiguration.class)
public class SpringBootContextTest {
    @Autowired
    private ApplicationContext context;

    @Test
    void checkContextForAvailabilityOfVoteServices(){
        assertNotNull(context.getBean(VoteAnswerService.class));
        assertNotNull(context.getBean(VoteCommentService.class));
        assertNotNull(context.getBean(VoteQuestionService.class));
        assertNotNull(context.getBean(VoteWithRemarkService.class));
    }

    @Test
    void checkContextForAvailabilityOfNotificationServices() {
        assertNotNull(context.getBean(NotificationService.class));
    }

}
