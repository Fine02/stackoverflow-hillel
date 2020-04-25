package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.CommentRepository;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class VoteCommentServiceIntegrationTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private VoteService<Comment> voteCommentService;
    private Comment comment;
    private Member member;

    @Test
    @DisplayName("Integration test for VoteCommentService to upvote that throws AlreadyVotedException")
    public void whenMemberIsAlreadyUpVotedTheCommentThenThrowsAlreadyVotedException(){
        injectCommentAndMemberPair(1L, 1L);
        assertThatThrownBy(() -> voteCommentService.upVote(comment, member))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    @DisplayName("Integration test for VoteCommentService to downvote that throws AlreadyVotedException")
    public void whenMemberIsAlreadyDownVotedTheCommentThenThrowsAlreadyVotedException(){
        injectCommentAndMemberPair(2L, 2L);
        assertThatThrownBy(() -> voteCommentService.downVote(comment, member))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    @DisplayName("Integration test for VoteCommentService to up/downvote that throws CannotVoteOwnPostException")
    public void whenMemberTryToVoteOwnCommentThenThrowsCannotVoteOwnPostException(){
        injectCommentAndMemberPair(1L, 3L);
        assertThatThrownBy(() -> voteCommentService.upVote(comment, member))
                .isInstanceOf(CannotVoteOwnPostException.class);
        assertThatThrownBy(() -> voteCommentService.downVote(comment, member))
                .isInstanceOf(CannotVoteOwnPostException.class);
    }

    @Test
    @DisplayName("Integration test for VoteCommentService to upvote that OK")
    public void whenMemberUpVotesTheCommentThenVoteCountIncrementAndAddReputation() {
        injectCommentAndMemberPair(3L, 2L);
        int voteCountBefore = comment.getVoteCount();
        int reputationBefore = member.getReputation();

        voteCommentService.upVote(comment, member);
        var member = memberRepository.findById(2L).get();

        assertEquals(voteCountBefore+1, comment.getVoteCount());
        assertEquals(reputationBefore+5, member.getReputation());
        assertTrue(member.getUpVotedCommentsId().contains(comment.getId()));
    }

    @Test
    @DisplayName("Integration test for VoteCommentService to downvote that OK")
    public void whenMemberDownVotesTheCommentThenVoteCountIncrementAndAddReputation() {
        injectCommentAndMemberPair(2L, 3L);
        int voteCountBefore = comment.getVoteCount();
        int reputationBefore = member.getReputation();

        voteCommentService.downVote(comment, member);
        var memberAfter = memberRepository.findById(3L).get();

        assertEquals(voteCountBefore-1, comment.getVoteCount());
        assertEquals(reputationBefore+5, memberAfter.getReputation());
        assertTrue(memberAfter.getDownVotedCommentsId().contains(comment.getId()));

    }

    private void injectCommentAndMemberPair(long commentId, long memberId) {
        comment = commentRepository.findById(commentId).get();
        member = memberRepository.findById(memberId).get();
    }

}
