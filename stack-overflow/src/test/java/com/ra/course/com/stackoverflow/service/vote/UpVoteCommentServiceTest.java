package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.CommentNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.interfaces.CommentRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpVoteCommentServiceTest {

    private VoteCommentService voteCommentService;
    private CommentRepository commentData = mock(CommentRepository.class);
    private MemberRepository memberData = mock(MemberRepository.class);

    private final long ID1 = 1L;
    private final long ID2 = 2L;


    @BeforeEach
    void setUp() {
        voteCommentService = new VoteCommentService(commentData, memberData);
    }

    @Test
    public void whenCommentIsNotFoundThenThrowsCommentNotFoundException() {
        //given
        var member = mockMember(ID1);
        var comment = mockComment(member);
        when(commentData.findById(ID1)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> voteCommentService.upVote(comment, member))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessage("No such comment in DB");
        //then
        verify(commentData).findById(ID1);
        verifyNoMoreInteractions(commentData);
        verifyNoInteractions(memberData);
    }

    @Test
    public void whenMemberIsNotFoundThenThrowsMemberNotFoundException() {
        //given
        var member = mockMember(ID1);
        var comment = mockComment(member);
        when(commentData.findById(ID1)).thenReturn(Optional.of(comment));
        when(memberData.findById(ID1)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> voteCommentService.upVote(comment, member))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("No such member in DB");
        //then
        verify(commentData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(commentData, memberData);
    }

    @Test
    public void whenMemberTryToVoteOwnCommentThenThrowsCannotVoteOwnPostException(){
        //given
        var member = mockMember(ID1);
        var comment = mockComment(member);
        when(commentData.findById(ID1)).thenReturn(Optional.of(comment));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        assertThatThrownBy(() -> voteCommentService.upVote(comment, member))
                .isInstanceOf(CannotVoteOwnPostException.class)
                .hasMessage("Can't vote your own comment");
        //then
        verify(commentData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(commentData, memberData);
    }

    @Test
    public void whenMemberIsAlreadyVotedTheCommentThenThrowsAlreadyVotedException(){
        //given
        var wantToVoteMember = mockMember(ID1);
        wantToVoteMember.getUpVotedCommentsId().add(ID1);
        var author = mockMember(ID2);
        var comment = mockComment(author);
        when(commentData.findById(ID1)).thenReturn(Optional.of(comment));
        when(memberData.findById(ID1)).thenReturn(Optional.of(wantToVoteMember));
        //when
        assertThatThrownBy(() -> voteCommentService.upVote(comment, wantToVoteMember))
                .isInstanceOf(AlreadyVotedException.class)
                .hasMessage("This comment is already voted");
        //then
        verify(commentData).findById(ID1);
        verify(memberData).findById(ID1);
        verifyNoMoreInteractions(commentData, memberData);
    }

    @Test
    public void whenMemberVotesTheCommentThenVoteCountIncrementAndAddReputation() {
        var wantToVoteMember = mockMember(ID1);
        var author = mockMember(ID2);
        var comment = mockComment(author);
        when(commentData.findById(ID1)).thenReturn(Optional.of(comment));
        when(memberData.findById(ID1)).thenReturn(Optional.of(wantToVoteMember));
        //when
        var commentAfterVoting = voteCommentService.upVote(comment, wantToVoteMember);
        //then
        assertEquals(1, commentAfterVoting.getVoteCount());
        assertEquals(5, wantToVoteMember.getReputation());
        assertTrue(wantToVoteMember.getUpVotedCommentsId().contains(ID1));

        verify(commentData).findById(ID1);
        verify(memberData).findById(ID1);
        verify(commentData).update(any());
        verify(memberData).update(any());
        verifyNoMoreInteractions(commentData, memberData);
    }


    private Comment mockComment(Member member){
        return Comment.builder()
                .id(ID1)
                .author(member)
                .commentable(mockQuestion(member)).build();
    }
    private Question mockQuestion(Member member){
        return Question.builder()
                .id(ID1)
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
