package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.CommentNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.CommentRepository;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.vote.impl.VoteCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpVoteCommentServiceTest {
    private final CommentRepository commentData = mock(CommentRepository.class);
    private final MemberRepository memberData = mock(MemberRepository.class);
    private final long ID1 = 1;
    private final long ID2 = 2;

    private VoteCommentService voteCommentService;
    private Member member;
    private Comment comment;

    @BeforeEach
    void setUp() {
        voteCommentService = new VoteCommentService(commentData, memberData);
        member = mockMember(ID1);
        comment = mockComment(mockMember(ID2));
    }
    @Test
    public void whenCommentIsNotFoundThenThrowsCommentNotFoundException() {
        //given
        when(commentData.findById(ID1)).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> voteCommentService.upVote(comment, member))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessage("No such comment in DB");
    }

    @Test
    public void whenMemberIsNotFoundThenThrowsMemberNotFoundException() {
        //given
        when(commentData.findById(ID1)).thenReturn(Optional.of(comment));
        when(memberData.findById(ID1)).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> voteCommentService.upVote(comment, member))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("No such member in DB");
    }

    @Test
    public void whenMemberTryToVoteOwnCommentThenThrowsCannotVoteOwnPostException(){
        //given
        var commentWithTheSameAuthor = mockComment(member);
        when(commentData.findById(ID1)).thenReturn(Optional.of(commentWithTheSameAuthor));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        //then
        assertThatThrownBy(() -> voteCommentService.upVote(commentWithTheSameAuthor, member))
                .isInstanceOf(CannotVoteOwnPostException.class)
                .hasMessage("Can't vote your own comment");
    }

    @Test
    public void whenMemberIsAlreadyVotedTheCommentThenThrowsAlreadyVotedException(){
        //given
        member.getUpVotedCommentsId().add(comment.getId());
        when(commentData.findById(ID1)).thenReturn(Optional.of(comment));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        //then
        assertThatThrownBy(() -> voteCommentService.upVote(comment, member))
                .isInstanceOf(AlreadyVotedException.class)
                .hasMessage("This comment is already voted");
    }

    @Test
    public void whenMemberVotesTheCommentThenVoteCountIncrementAndAddReputation() {
        //given
        when(commentData.findById(ID1)).thenReturn(Optional.of(comment));
        when(memberData.findById(ID1)).thenReturn(Optional.of(member));
        //when
        var commentAfterVoting = voteCommentService.upVote(comment, member);
        //then
        assertEquals(1, commentAfterVoting.getVoteCount());
        assertEquals(5, member.getReputation());
        assertTrue(member.getUpVotedCommentsId().contains(comment.getId()));
    }

    private Comment mockComment(Member member){
        return Comment.builder()
                .id(ID1)
                .creationDate(LocalDateTime.now())
                .text("text")
                .authorId(member.getAccount().getId())
                .questionId(ID2).build();
    }
    private Member mockMember(long idMember){
        var account = Account.builder()
                .id(idMember)
                .password("password")
                .email("email")
                .name("name").build();
        return Member.builder()
                .account(account).build();
    }
}
