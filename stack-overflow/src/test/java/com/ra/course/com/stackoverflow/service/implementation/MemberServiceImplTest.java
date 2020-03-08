package com.ra.course.com.stackoverflow.service.implementation;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.repository.InternalServerErrorException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.service.member.MemberService;

import com.ra.course.com.stackoverflow.service.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void setUp() throws DataBaseOperationException {
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

        expectedMember.setQuestions(Collections.singletonList(expectedQuestion));

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
    public void whenPostQuestionCalledReturnMemberWithExpectedQuestionList() throws InternalServerErrorException,
                                                                                DataBaseOperationException {
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
    public void whenDataBaseExceptionIsThrownByMemberRepositoryThrowInternalErrorException() throws
            DataBaseOperationException {

        //when
        when(mockedMemberRepository.update(givenMember)).thenThrow(DataBaseOperationException.class);
        when(mockedQuestionRepository.save(givenQuestion)).thenReturn(expectedQuestion);

        //then
        Throwable actualException = assertThrows(InternalServerErrorException.class,
                () -> memberService.postQuestion(givenQuestion));

        assertEquals("Unexpected error occurred: 500 Internal Server Error", actualException.getMessage());

        verify(mockedMemberRepository).update(givenMember);
        verify(mockedQuestionRepository).save(givenQuestion);
        verifyNoMoreInteractions(mockedMemberRepository, mockedQuestionRepository);
    }

    @Test
    public void whenPostQuestionCalledReturnExpectedQuestion() throws InternalServerErrorException,
            DataBaseOperationException {

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
    public void whenDataBaseExceptionIsThrownByQuestionRepositoryThrowInternalErrorException() throws
            DataBaseOperationException {

        //when
        when(mockedQuestionRepository.save(givenQuestion)).thenThrow(DataBaseOperationException.class);

        //then
        Throwable actualException = assertThrows(InternalServerErrorException.class,
                () -> memberService.postQuestion(givenQuestion));

        assertEquals("Unexpected error occurred: 500 Internal Server Error", actualException.getMessage());

        verify(mockedQuestionRepository).save(givenQuestion);
        verifyNoMoreInteractions(mockedMemberRepository);
    }

}
