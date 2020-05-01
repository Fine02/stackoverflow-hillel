package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class MemberServiceIntegrationTest {
    private long ID = 1L;
    private Question questionWithBounty ;

    @Autowired
    private MemberService<Question> memberService;

    @BeforeEach
    void setUp() {
        questionWithBounty = constructQuestionWithoutBounty();

    }

    @Test
    public void whenFindMemberByIdAndMemberPresentInDataBaseThenReturnMember() {
        var question = memberService.postQuestion(questionWithBounty);

        assertThat(question.getId()).isGreaterThan(0);

    }

    private Question constructQuestionWithoutBounty() {
        return Question.builder()
                       .title("title")
                       .authorId(ID)
                       .build();
    }
}
