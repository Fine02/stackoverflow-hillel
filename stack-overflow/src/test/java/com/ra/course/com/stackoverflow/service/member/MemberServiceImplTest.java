package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MemberServiceImplTest {

    private MemberService<Question> memberService;

    private Question expectedQuestion;
    private Question givenQuestion;
    private Member expectedMember;
    MemberRepository mockedMemberRepository;


    @BeforeEach
    void setUp() {
        mockedMemberRepository = mock(MemberRepository.class);
        QuestionRepository mockedQuestionRepository = mock(QuestionRepository.class);
        memberService = new MemberServiceImpl(mockedQuestionRepository, mockedMemberRepository);

        expectedMember = buildMember();
        expectedQuestion = buildQuestion(expectedMember);
        expectedMember.getQuestions().add(expectedQuestion);

        Member givenMember = buildMember();
        givenQuestion = buildQuestion(givenMember);

        when(mockedQuestionRepository.save(givenQuestion)).thenReturn(expectedQuestion);
        when(mockedMemberRepository.findById(42L)).thenReturn(Optional.ofNullable(expectedMember));
    }

    @Test
    public void whenPostQuestionCalledReturnMemberWithExpectedQuestionList() {
        //when
        Question actualQuestion = memberService.postQuestion(givenQuestion);

        //then
        assertTrue(expectedMember.getQuestions().contains(actualQuestion));
    }

    @Test
    public void whenPostQuestionCalledButMemberNotFoundInDBThenThrowException() {
        when(mockedMemberRepository.findById(expectedMember.getAccount().getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.postQuestion(expectedQuestion))
            .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    public void whenPostQuestionCalledReturnExpectedQuestion() {
        //when
        Question actualQuestion = memberService.postQuestion(givenQuestion);

        //then
        assertEquals(expectedQuestion, actualQuestion);
    }

    @Test
    public void whenNullArgumentPassedThenThrowNpe() {
        assertThrows(NullPointerException.class, () -> memberService.postQuestion(null));
    }

    private Member buildMember() {
        return Member.builder()
                .account(Account.builder()
                        .id(42L)
                        .name("Test")
                        .password("test1234")
                        .email("test@gmail.com")
                        .build())
                .build();
    }

    private Question buildQuestion(Member member) {
        return Question.builder()
                .id(24L)
                .title("Test")
                .authorId(member.getAccount().getId())
                .build();
    }
}
