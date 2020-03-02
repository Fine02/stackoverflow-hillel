package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.exception.vote_service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.vote_service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DownVoteQuestionServiceTest {
    private VoteQuestionService voteQuestionService;
    private QuestionRepository questionData = mock(QuestionRepository.class);
    private MemberRepository memberData = mock(MemberRepository.class);

    private final long ID1 = 1L;
    private final long ID2 = 2L;
    private Exception exception;
    private Question questionAfterVoting;

    @BeforeEach
    void setUp() {
        voteQuestionService = new VoteQuestionService(questionData, memberData);
    }

    @Test
    public void whenQuestionIsNotFoundThenThrowsQuestionNotFoundException(){
        //given
        var member = mockMember(ID1);
        var question = mockQuestion(ID1, member);
        when(questionData.findById(ID1)).thenReturn(Optional.empty());
        //when
        try {
            questionAfterVoting = voteQuestionService.downVote(question, member);
        }
        //then
        catch (Exception ex){
            exception = ex;
        }
        assertTrue(exception instanceof QuestionNotFoundException);
        assertEquals("No such question in DB", exception.getMessage());
        verify(questionData).findById(ID1);
        verifyNoMoreInteractions(questionData);
        verifyNoInteractions(memberData);
    }

    @Test
    public void whenMemberIsNotFoundThenThrowsMemberNotFoundException(){
        //given
        var member = mockMember(ID1);
        var question = mockQuestion(ID1, member);
        when(questionData.findById(ID1)).thenReturn(Optional.of(question));
        when(memberData.findById(ID1)).thenReturn(Optional.empty());
        //
        try{
            questionAfterVoting = voteQuestionService.downVote(question, member);
        }
        //then
        catch(Exception e){
            exception = e;
        }
        assertTrue(exception instanceof MemberNotFoundException);
        assertEquals("No such member in DB", exception.getMessage());
        verify(questionData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(questionData, memberData);
    }

    @Test
    public void whenMemberTryToVoteOwnQuestionThenThrowsCannotVoteOwnPostException() {
        //given
        var member = mockMember(ID1);
        var question = mockQuestion(ID1, member);
        when(questionData.findById(ID1)).thenReturn(Optional.of(question));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        try {
            questionAfterVoting = voteQuestionService.downVote(question, member);
        }
        //then
        catch(Exception e){
            exception = e;
        }
        assertTrue(exception instanceof CannotVoteOwnPostException);
        assertEquals("Can't vote your own question", exception.getMessage());
        verify(questionData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(questionData, memberData);
    }

    @Test
    public void whenMemberIsAlreadyVotedTheQuestionThenThrowsAlreadyVotedException() {
        //given
        var wantToVoteMember = mockMember(ID1);
        wantToVoteMember.getDownVotedQuestions().add(ID1);
        var author = mockMember(ID2);
        var question = mockQuestion(ID1, author);
        when(questionData.findById(ID1)).thenReturn(Optional.of(question));
        when(memberData.findById(ID1)).thenReturn(Optional.of(wantToVoteMember));
        //when
        try {
            questionAfterVoting = voteQuestionService.downVote(question, wantToVoteMember);
        }
        //then
        catch (Exception e){
            exception = e;
        }
        assertTrue(exception instanceof AlreadyVotedException);
        assertEquals("This question is already voted", exception.getMessage());
        verify(questionData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(questionData, memberData);
    }
    @Test
    public void whenMemberVotesTheQuestionThenVoteCountDecrementAndAddReputation() throws DataBaseOperationException {
        //given
        var wantToVoteMember = mockMember(ID1);
        var author = mockMember(ID2);
        var question = mockQuestion(ID1, author);
        when(questionData.findById(ID1)).thenReturn(Optional.of(question));
        when(memberData.findById(ID1)).thenReturn(Optional.of(wantToVoteMember));
        //when
        try {
            questionAfterVoting = voteQuestionService.downVote(question, wantToVoteMember);
        }
        //then
        catch (Exception e){
            exception = e;
        }
        assertEquals(-1, questionAfterVoting.getVoteCount());
        assertEquals(5, wantToVoteMember.getReputation());
        assertTrue(wantToVoteMember.getDownVotedQuestions().contains(ID1));
        assertNull(exception);
        verify(questionData).findById(ID1);
        verify(memberData).findById(ID1);
        verify(questionData).update(any());
        verify(memberData).update(any());
        verifyNoMoreInteractions(questionData, memberData);
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
