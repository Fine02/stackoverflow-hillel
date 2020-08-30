//package com.ra.course.com.stackoverflow.service;
//
//import com.ra.course.com.stackoverflow.entity.*;
//import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
//import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
//import com.ra.course.com.stackoverflow.service.vote.VoteService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@SpringBootTest
//@ActiveProfiles("test")
//public class VoteQuestionServiceImplIntegrationTest {
//    private Account accountID1 = createNewAccount(1L);
//    private Account accountID2 = createNewAccount(2L);
//    private Account accountID3 = createNewAccount(3L);
//    private Member memberID1 = createNewMember(accountID1);
//    private Member memberID2 = createNewMember(accountID2);
//    private Member memberID3 = createNewMember(accountID3);
//    private Question questionID1 = createNewQuestion(1L, memberID1);
//    private Question questionID2 = createNewQuestion(2L, memberID2);
//    private Question questionID3 = createNewQuestion(3L, memberID3);
//
//
//    @Autowired
//    private VoteService<Question> voteQuestionService;
//
//
//    @Test
//    @Rollback
//    @DisplayName("Integration test for VoteQuestionService to upvote then throws AlreadyVotedException")
//    public void whenMemberIsAlreadyUpVotedTheQuestionThenThrowsAlreadyVotedException(){
//
//        assertThatThrownBy(() -> voteQuestionService.upVote(questionID1, memberID2))
//                .isInstanceOf(AlreadyVotedException.class);
//    }
//
//    @Test
//    @Rollback
//    @DisplayName("Integration test for VoteQuestionService to downvote then throws AlreadyVotedException")
//    public void whenMemberIsAlreadyDownVotedTheQuestionThenThrowsAlreadyVotedException(){
//
//        assertThatThrownBy(() -> voteQuestionService.downVote(questionID2, memberID1))
//                .isInstanceOf(AlreadyVotedException.class);
//    }
//
//    @Test
//    @Rollback
//    @DisplayName("Integration test for VoteQuestionService to up/downvote then throws CannotVoteOwnPostException")
//    public void whenMemberTryToVoteOwnQuestionThenThrowsCannotVoteOwnPostException(){
//
//        assertThatThrownBy(() -> voteQuestionService.upVote(questionID1, memberID1))
//                .isInstanceOf(CannotVoteOwnPostException.class);
//        assertThatThrownBy(() -> voteQuestionService.downVote(questionID1, memberID1))
//                .isInstanceOf(CannotVoteOwnPostException.class);
//    }
//
//    @Test
//    @Rollback
//    @DisplayName("Integration test for VoteQuestionService to upvote then OK")
//    public void whenMemberUpVotesTheQuestionThenVoteCountIncrement() {
//
//        int voteCountBefore = questionID3.getVoteCount();
//
//        voteQuestionService.upVote(questionID3, memberID1);
//        int voteCountAfter = questionID3.getVoteCount();
//
//        assertThat(voteCountBefore < voteCountAfter).isTrue();
//    }
//
//    @Test
//    @Rollback
//    @DisplayName("Integration test for VoteQuestionService to downvote then OK")
//    public void whenMemberDownVotesTheQuestionThenVoteCountDecrement() {
//
//        int voteCountBefore = questionID1.getVoteCount();
//
//        voteQuestionService.upVote(questionID1, memberID3);
//        int voteCountAfter = questionID1.getVoteCount();
//
//        assertThat(voteCountBefore < voteCountAfter).isTrue();
//    }
//
//
//    private Account createNewAccount(long id) {
//        return Account.builder()
//                .id(id)
//                .password("password")
//                .email("email")
//                .name("name")
//                .reputation(10)
//                .build();
//    }
//
//    private Member createNewMember(Account account) {
//        return Member.builder()
//                .account(account)
//                .build();
//    }
//
//    private Question createNewQuestion(long id, Member member) {
//        return Question.builder()
//                .id(id)
//                .text("some question")
//                .title("title")
//                .author(member.getAccount().getId())
//                .build();
//    }
//}
