package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.Bounty;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.service.bounty.BountyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
public class BountyServiceIntegrationTest {
    @Autowired
    private BountyService bountyService;
    private Bounty bounty;
    private final Long CREATOR_ID = 1L;
    private long id = 1L;
    private long bountyId = 123L;
    private int reputation = 10;

    private Question questionWithBounty ;

    @BeforeEach
    void setUp() {
        questionWithBounty = constructQuestionWithBounty();
        bounty = new Bounty(bountyId, reputation, LocalDateTime.now(), CREATOR_ID);

    }

    @Test
    public void shouldSaveBounty() {
        Optional<Bounty> actualBounty = bountyService.addBounty(questionWithBounty, bounty);

        assertThat(actualBounty.get())
                .isEqualToIgnoringGivenFields(bounty, "id", "expiry");
    }

    private Question constructQuestionWithBounty() {
        return constructQuestionWithoutBounty();
    }

    private Question constructQuestionWithoutBounty() {
        return Question.builder()
                       .id(id)
                       .title("title")
                       .authorId(id)
                       .description("Some description")
                       .creationTime(LocalDateTime.now())
                       .updateTime(LocalDateTime.now())
                       .status(QuestionStatus.OPEN)
                       .closingRemark(QuestionClosingRemark.NOT_MARKED_FOR_CLOSING)
                       .bounty(Optional.ofNullable(bounty))
                       .build();
    }
}