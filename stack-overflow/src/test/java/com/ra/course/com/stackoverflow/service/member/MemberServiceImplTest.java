package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.InternalServerErrorException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class MemberServiceImplTest {

    private MemberService<Question> memberService;

    private MemberRepository mockedMemberRepository;
    private QuestionRepository mockedQuestionRepository;

    private Member expectedMember;
    private Question expectedQuestion;

    private Question givenQuestion;
    private Member givenMember;


    @BeforeEach
    void setUp() {
        mockedMemberRepository = mock(MemberRepository.class);
        mockedQuestionRepository = mock(QuestionRepository.class);
        memberService = new MemberServiceImpl(mockedQuestionRepository, mockedMemberRepository);

        expectedMember = Member.builder()
                .id(42L)
                .account(Account.builder()
                            .id(1L)
                            .name("Test")
                            .password("test1234")
                            .email("test@gmail.com")
                            .build())
                .build();

        expectedQuestion = Question.builder()
                .id(24L)
                .title("Test")
                .author(expectedMember)
                .build();

        expectedMember.getQuestions().add(expectedQuestion);

        givenMember = Member.builder()
                .id(42L)
                .account(Account.builder()
                        .id(1L)
                        .name("Test")
                        .password("test1234")
                        .email("test@gmail.com")
                        .build())
                .build();

        givenQuestion = Question.builder()
                .id(24L)
                .title("Test")
                .author(givenMember)
                .build();
    }

    @Test
    public void whenPostQuestionCalledReturnMemberWithExpectedQuestionList() {
        //when
        when(mockedMemberRepository.update(givenMember)).thenReturn(expectedMember);
        when(mockedQuestionRepository.save(givenQuestion)).thenReturn(expectedQuestion);
        Question actualQuestion = memberService.postQuestion(givenQuestion);

        //then
        assertEquals(expectedQuestion.getAuthor().getQuestions(), actualQuestion.getAuthor().getQuestions());
        verify(mockedMemberRepository).update(givenMember);
        verify(mockedQuestionRepository).save(givenQuestion);
        verifyNoMoreInteractions(mockedMemberRepository, mockedQuestionRepository);
    }

    @Test
    public void whenDataBaseExceptionIsThrownByMemberRepositoryThrowInternalErrorException() {

        //when
        when(mockedMemberRepository.update(givenMember)).thenThrow(DataBaseOperationException.class);
        when(mockedQuestionRepository.save(givenQuestion)).thenReturn(expectedQuestion);

        //then
        Throwable actualException = assertThrows(InternalServerErrorException.class,
                () -> memberService.postQuestion(givenQuestion));

        assertTrue(actualException.getMessage().contains("Unexpected data base error occurred: "));

        verify(mockedMemberRepository).update(givenMember);
        verify(mockedQuestionRepository).save(givenQuestion);
        verifyNoMoreInteractions(mockedMemberRepository, mockedQuestionRepository);
    }

    @Test
    public void whenPostQuestionCalledReturnExpectedQuestion() {
        //when
        when(mockedMemberRepository.update(givenMember)).thenReturn(expectedMember);
        when(mockedQuestionRepository.save(givenQuestion)).thenReturn(expectedQuestion);
        Question actualQuestion = memberService.postQuestion(givenQuestion);

        //then
        assertEquals(expectedQuestion, actualQuestion);

        verify(mockedQuestionRepository).save(givenQuestion);
        verify(mockedMemberRepository).update(givenMember);
        verifyNoMoreInteractions(mockedMemberRepository, mockedQuestionRepository);
    }

    @Test
    public void whenDataBaseExceptionIsThrownByQuestionRepositoryThrowInternalErrorException() {

        //when
        when(mockedQuestionRepository.save(givenQuestion)).thenThrow(DataBaseOperationException.class);

        //then
        Throwable actualException = assertThrows(InternalServerErrorException.class,
                () -> memberService.postQuestion(givenQuestion));

        assertTrue(actualException.getMessage().contains("Unexpected data base error occurred: "));

        verify(mockedQuestionRepository).save(givenQuestion);
        verifyNoMoreInteractions(mockedMemberRepository);
    }

}
