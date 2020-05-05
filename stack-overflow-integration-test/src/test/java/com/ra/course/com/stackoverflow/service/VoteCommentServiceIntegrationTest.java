package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
public class VoteCommentServiceIntegrationTest {
    private Account accountID1 = createNewAccount(1L);
    private Account accountID2 = createNewAccount(2L);
    private Account accountID3 = createNewAccount(3L);
    private Member memberID1 = createNewMember(accountID1);
    private Member memberID2 = createNewMember(accountID2);
    private Member memberID3 = createNewMember(accountID3);
    private Question questionID1 = createNewQuestion(1L, memberID1);
    private Question questionID2 = createNewQuestion(2L, memberID2);
    private Comment commentID1 = createNewComment(1L, memberID1, questionID1);
    private Comment commentID2 = createNewComment(2L, memberID2, questionID2);
    private Comment commentID3 = createNewComment(3L, memberID3, questionID1);


    @Autowired
    private VoteService<Comment> voteCommentService;


    @Test
    @Rollback
    @DisplayName("Integration test for VoteCommentService to upvote then throws AlreadyVotedException")
    public void whenMemberIsAlreadyUpVotedTheCommentThenThrowsAlreadyVotedException(){

        assertThatThrownBy(() -> voteCommentService.upVote(commentID1, memberID1))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    @Rollback
    @DisplayName("Integration test for VoteCommentService to downvote then throws AlreadyVotedException")
    public void whenMemberIsAlreadyDownVotedTheCommentThenThrowsAlreadyVotedException(){

        assertThatThrownBy(() -> voteCommentService.downVote(commentID2, memberID2))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    @Rollback
    @DisplayName("Integration test for VoteCommentService to up/downvote then throws CannotVoteOwnPostException")
    public void whenMemberTryToVoteOwnCommentThenThrowsCannotVoteOwnPostException(){

        assertThatThrownBy(() -> voteCommentService.upVote(commentID1, memberID3))
                .isInstanceOf(CannotVoteOwnPostException.class);
        assertThatThrownBy(() -> voteCommentService.downVote(commentID1, memberID3))
                .isInstanceOf(CannotVoteOwnPostException.class);
    }

    @Test
    @Rollback
    @DisplayName("Integration test for VoteCommentService to upvote then OK")
    public void whenMemberUpVotesTheCommentThenVoteCountIncrement() {

        int voteCountBefore = commentID3.getVoteCount();

        voteCommentService.upVote(commentID3, memberID2);
        int voteCountAfter = commentID3.getVoteCount();

        assertThat(voteCountBefore < voteCountAfter).isTrue();
    }

    @Test
    @Rollback
    @DisplayName("Integration test for VoteCommentService to downvote then OK")
    public void whenMemberDownVotesTheCommentThenVoteCountDecrement() {

        int voteCountBefore = commentID2.getVoteCount();

        voteCommentService.downVote(commentID2, memberID3);
        int voteCountAfter = commentID2.getVoteCount();

        assertThat(voteCountBefore > voteCountAfter).isTrue();
    }


    private Account createNewAccount(long id) {
        return Account.builder()
                .id(id)
                .password("password")
                .email("email")
                .name("name")
                .reputation(10)
                .build();
    }

    private Member createNewMember(Account account) {
        return Member.builder()
                .account(account)
                .build();
    }

    private Question createNewQuestion(long id, Member member) {
        return Question.builder()
                .id(id)
                .description("some question")
                .title("title")
                .authorId(member.getAccount().getId())
                .build();
    }

    private Comment createNewComment(long id, Member member, Question question) {
        return Comment.builder()
                .id(id)
                .text("Some_comment")
                .creationDate(LocalDateTime.now())
                .authorId(member.getAccount().getId())
                .questionId(question.getId())
                .voteCount(5)
                .build();
    }
}
