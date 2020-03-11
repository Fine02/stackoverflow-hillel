package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MemberServiceImplTest {

    private MemberService<Question> memberService;

    private Question expectedQuestion;
    private Question givenQuestion;


    @BeforeEach
    void setUp() {
        MemberRepository mockedMemberRepository = mock(MemberRepository.class);
        QuestionRepository mockedQuestionRepository = mock(QuestionRepository.class);
        memberService = new MemberServiceImpl(mockedQuestionRepository, mockedMemberRepository);

        Member expectedMember = buildMember();
        expectedQuestion = buildQuestion(expectedMember);
        expectedMember.getQuestions().add(expectedQuestion);

        Member givenMember = buildMember();
        givenQuestion = buildQuestion(givenMember);

        when(mockedMemberRepository.update(givenMember)).thenReturn(expectedMember);
        when(mockedQuestionRepository.save(givenQuestion)).thenReturn(expectedQuestion);
    }

    @Test
    public void whenPostQuestionCalledReturnMemberWithExpectedQuestionList() {
        //when
        Question actualQuestion = memberService.postQuestion(givenQuestion);

        //then
        assertEquals(expectedQuestion.getAuthor().getQuestions(), actualQuestion.getAuthor().getQuestions());
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
                .id(42L)
                .account(Account.builder()
                        .id(1L)
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
                .author(member)
                .build();
    }
}
