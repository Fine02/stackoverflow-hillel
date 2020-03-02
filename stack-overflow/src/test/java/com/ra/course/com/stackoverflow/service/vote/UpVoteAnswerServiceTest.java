package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.AnswerNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.vote_service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.vote_service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.interfaces.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteAnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpVoteAnswerServiceTest {

    private VoteAnswerService voteAnswerService;
    private AnswerRepository answerData = mock(AnswerRepository.class);
    private MemberRepository memberData = mock(MemberRepository.class);

    private final long ID1 = 1L;
    private final long ID2 = 2L;
    private Exception exception;
    private Answer answerAfterVoting;

    @BeforeEach
    void setUp() {
        voteAnswerService = new VoteAnswerService(answerData, memberData);
    }

    @Test
    public void whenAnswerIsNotFoundThenThrowsAnswerNotFoundException(){
        //given
        var member = mockMember(ID1);
        var answer = mockAnswer(ID1, member);
        when(answerData.findById(ID1)).thenReturn(Optional.empty());
        //when
        try {
            answerAfterVoting = voteAnswerService.upVote(answer, member);
        }
        //then
        catch (Exception ex){
            exception = ex;
        }
        assertTrue(exception instanceof AnswerNotFoundException);
        assertEquals("No such answer in DB", exception.getMessage());
        verify(answerData).findById(ID1);
        verifyNoMoreInteractions(answerData);
        verifyNoInteractions(memberData);
    }

    @Test
    public void whenMemberIsNotFoundThenThrowsMemberNotFoundException(){
        //given
        var member = mockMember(ID1);
        var answer = mockAnswer(ID1, member);
        when(answerData.findById(ID1)).thenReturn(Optional.of(answer));
        when(memberData.findById(ID1)).thenReturn(Optional.empty());
        //
        try{
            answerAfterVoting = voteAnswerService.upVote(answer, member);
        }
        //then
        catch(Exception e){
            exception = e;
        }
        assertTrue(exception instanceof MemberNotFoundException);
        assertEquals("No such member in DB", exception.getMessage());
        verify(answerData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(answerData, memberData);
    }

    @Test
    public void whenMemberTryToVoteOwnAnswerThenThrowsCannotVoteOwnPostException() {
        //given
        var member = mockMember(ID1);
        var answer = mockAnswer(ID1, member);
        when(answerData.findById(ID1)).thenReturn(Optional.of(answer));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        try {
            answerAfterVoting = voteAnswerService.upVote(answer, member);
        }
        //then
        catch (Exception e){
            exception = e;
        }
        assertTrue(exception instanceof CannotVoteOwnPostException);
        assertEquals("Can't vote your own answer", exception.getMessage());
        verify(answerData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(answerData, memberData);
    }

    @Test
    public void whenMemberIsAlreadyVotedTheAnswerThenThrowsAlreadyVotedException() {
        //given
        var wantToVoteMember = mockMember(ID1);
        wantToVoteMember.getVotedAnswers().add(ID1);
        var author = mockMember(ID2);
        var answer = mockAnswer(ID1, author);
        when(answerData.findById(ID1)).thenReturn(Optional.of(answer));
        when(memberData.findById(ID1)).thenReturn(Optional.of(wantToVoteMember));
        //when
        try {
            answerAfterVoting = voteAnswerService.upVote(answer, wantToVoteMember);
        }
        //then
        catch (Exception e){
            exception = e;
        }
        assertTrue(exception instanceof AlreadyVotedException);
        assertEquals("This answer is already voted", exception.getMessage());
        verify(answerData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(answerData, memberData);
    }
    @Test
    public void whenMemberVotesTheAnswerThenVoteCountIncrementAndAddReputation() throws DataBaseOperationException {
        //given
        var wantToVoteMember = mockMember(ID1);
        var author = mockMember(ID2);
        var answer = mockAnswer(ID1, author);
        when(answerData.findById(ID1)).thenReturn(Optional.of(answer));
        when(memberData.findById(ID1)).thenReturn(Optional.of(wantToVoteMember));
        //when
        try {
            answerAfterVoting = voteAnswerService.upVote(answer, wantToVoteMember);
        }
        //then
        catch (Exception e){
            exception = e;
        }
        assertEquals(1, answerAfterVoting.getVoteCount());
        assertEquals(10, wantToVoteMember.getReputation());
        assertTrue(wantToVoteMember.getVotedAnswers().contains(ID1));
        assertNull(exception);
        verify(answerData).findById(ID1);
        verify(memberData).findById(ID1);
        verify(answerData).update(any());
        verify(memberData).update(any());
        verifyNoMoreInteractions(answerData, memberData);
    }



    private Answer mockAnswer(long id, Member member){
        return Answer.builder()
                .id(id)
                .author(member)
                .question(mockQuestion(id, member)).build();
    }
    private Question mockQuestion(long idQuestion, Member member){
        return Question.builder()
                .id(idQuestion)
                .title("title")
                .author(member).build();
    }
    private Member mockMember(long idMember){
        var account = Account.builder()
                .id(idMember)
                .password("password")
                .email("email")
                .name("name").build();
        return Member.builder()
                .id(idMember)
                .account(account).build();
    }
}
