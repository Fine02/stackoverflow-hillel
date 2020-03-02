package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.CommentNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.vote_service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.vote_service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.interfaces.CommentRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpVoteCommentServiceTest {

    private VoteCommentService voteCommentService;
    private CommentRepository commentData = mock(CommentRepository.class);
    private MemberRepository memberData = mock(MemberRepository.class);

    private final long ID1 = 1L;
    private final long ID2 = 2L;
    private Exception exception;
    private Comment commentAfterVoting;


    @BeforeEach
    void setUp() {
        voteCommentService = new VoteCommentService(commentData, memberData);
    }

    @Test
    public void whenCommentIsNotFoundThenThrowsCommentNotFoundException() {
        //given
        var member = mockMember(ID1);
        var comment = mockComment(ID1, member);
        when(commentData.findById(ID1)).thenReturn(Optional.empty());
        //when
        try{
            commentAfterVoting = voteCommentService.upVote(comment, member);
        }
        //then
        catch(Exception e){
            exception = e;
        }
        assertTrue(exception instanceof CommentNotFoundException);
        assertEquals("No such comment in DB", exception.getMessage());
        verify(commentData).findById(ID1);
        verifyNoMoreInteractions(commentData);
        verifyNoInteractions(memberData);
    }

    @Test
    public void whenMemberIsNotFoundThenThrowsMemberNotFoundException() {
        //given
        var member = mockMember(ID1);
        var comment = mockComment(ID1, member);
        when(commentData.findById(ID1)).thenReturn(Optional.of(comment));
        when(memberData.findById(ID1)).thenReturn(Optional.empty());
        //
        try {
            commentAfterVoting = voteCommentService.upVote(comment, member);
        }
        //then
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof MemberNotFoundException);
        assertEquals("No such member in DB", exception.getMessage());
        verify(commentData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(commentData, memberData);
    }

    @Test
    public void whenMemberTryToVoteOwnCommentThenThrowsCannotVoteOwnPostException(){
        //given
        var member = mockMember(ID1);
        var comment = mockComment(ID1, member);
        when(commentData.findById(ID1)).thenReturn(Optional.of(comment));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        try{
            commentAfterVoting = voteCommentService.upVote(comment, member);
        }
        //then
        catch (Exception e){
            exception = e;
        }
        assertTrue(exception instanceof CannotVoteOwnPostException);
        assertEquals("Can't vote your own comment", exception.getMessage());
        verify(commentData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(commentData, memberData);
    }

    @Test
    public void whenMemberIsAlreadyVotedTheCommentThenThrowsAlreadyVotedException(){
        //given
        var wantToVoteMember = mockMember(ID1);
        wantToVoteMember.getVotedComments().add(ID1);
        var author = mockMember(ID2);
        var comment = mockComment(ID1, author);
        when(commentData.findById(ID1)).thenReturn(Optional.of(comment));
        when(memberData.findById(ID1)).thenReturn(Optional.of(wantToVoteMember));
        //when
        try{
            commentAfterVoting = voteCommentService.upVote(comment, wantToVoteMember);
        }
        //then
        catch(Exception e){
            exception = e;
        }
        assertTrue(exception instanceof AlreadyVotedException);
        assertEquals("This comment is already voted", exception.getMessage());
        verify(commentData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(commentData, memberData);
    }

    @Test
    public void whenMemberVotesTheCommentThenVoteCountIncrementAndAddReputation() throws DataBaseOperationException {
        var wantToVoteMember = mockMember(ID1);
        var author = mockMember(ID2);
        var comment = mockComment(ID1, author);
        when(commentData.findById(ID1)).thenReturn(Optional.of(comment));
        when(memberData.findById(ID1)).thenReturn(Optional.of(wantToVoteMember));
        //when
        try{
            commentAfterVoting = voteCommentService.upVote(comment, wantToVoteMember);
        }
        //then
        catch(Exception e){
            exception = e;
        }
        assertEquals(1, commentAfterVoting.getVoteCount());
        assertEquals(5, wantToVoteMember.getReputation());
        assertTrue(wantToVoteMember.getVotedComments().contains(ID1));
        assertNull(exception);
        verify(commentData).findById(ID1);
        verify(memberData).findById(ID1);
        verify(commentData).update(any());
        verify(memberData).update(any());
        verifyNoMoreInteractions(commentData, memberData);
    }


    private Comment mockComment(long id, Member member){
        return Comment.builder()
                .id(id)
                .author(member)
                .commentable(mockQuestion(id, member)).build();
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
