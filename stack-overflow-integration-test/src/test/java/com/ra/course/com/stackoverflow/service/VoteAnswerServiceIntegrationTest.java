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
//import org.springframework.test.context.jdbc.Sql;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Sql({"classpath:schema.sql", "classpath:data.sql"})
//public class VoteAnswerServiceIntegrationTest {
//    private final long ID = 1L;
//    private Account accountID1 = createNewAccount(ID);
//    private Account accountID2 = createNewAccount(2L);
//    private Member memberID1 = createNewMember(accountID1);
//    private Member memberID2 = createNewMember(accountID2);
//    private Question questionID1 = createNewQuestion(ID, memberID1);
//    private Question questionID2 = createNewQuestion(2L, memberID2);
//    private Answer answerID1 = createNewAnswer(ID, memberID1, questionID1);
//    private Answer answerID2 = createNewAnswer(2L, memberID1, questionID2);
//    private Answer answerID3 = createNewAnswer(3L, memberID2, questionID2);
//
//    @Autowired
//    private VoteService<Answer> voteAnswerService;
//
//
//    @Test
//    @Rollback
//    @DisplayName("Integration test for VoteAnswerService to upvote then throws AlreadyVotedException")
//    public void whenMemberIsAlreadyUpVotedTheAnswerThenThrowsAlreadyVotedException(){
//
//        assertThatThrownBy(() -> voteAnswerService.upVote(answerID1, memberID2))
//                .isInstanceOf(AlreadyVotedException.class);
//    }
//
//    @Test
//    @Rollback
//    @DisplayName("Integration test for VoteAnswerService to downvote then throws AlreadyVotedException")
//    public void whenMemberIsAlreadyDownVotedTheAnswerThenThrowsAlreadyVotedException(){
//
//        assertThatThrownBy(() -> voteAnswerService.downVote(answerID2, memberID1))
//                .isInstanceOf(AlreadyVotedException.class);
//    }
//
//    @Test
//    @Rollback
//    @DisplayName("Integration test for VoteAnswerService to up/downvote then throws CannotVoteOwnPostException")
//    public void whenMemberTryToVoteOwnAnswerThenThrowsCannotVoteOwnPostException(){
//
//        assertThatThrownBy(() -> voteAnswerService.upVote(answerID1, memberID1))
//                .isInstanceOf(CannotVoteOwnPostException.class);
//        assertThatThrownBy(() -> voteAnswerService.downVote(answerID1, memberID1))
//                .isInstanceOf(CannotVoteOwnPostException.class);
//    }
//
//    @Test
//    @Rollback
//    @DisplayName("Integration test for voteAnswerService to upvote then OK")
//    public void whenMemberUpVotesTheAnswerThenVoteCountIncrement() {
//
//        int voteCountBefore = answerID3.getVoteCount();
//
//        voteAnswerService.upVote(answerID3, memberID1);
//        int voteCountAfter = answerID3.getVoteCount();
//
//        assertThat(voteCountBefore < voteCountAfter).isTrue();
//    }
//
//    @Test
//    @Rollback
//    @DisplayName("Integration test for voteAnswerService to downvote then OK")
//    public void whenMemberDownVotesTheAnswerThenVoteCountDecrement() {
//
//        int voteCountBefore = answerID3.getVoteCount();
//
//        voteAnswerService.downVote(answerID3, memberID1);
//        int voteCountAfter = answerID3.getVoteCount();
//
//        assertThat(voteCountBefore > voteCountAfter).isTrue();
//    }
//
//    private Account createNewAccount(long id) {
//        return Account.builder()
//                .id(id)
//                .password("password")
//                .email("email")
//                .name("name")
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
//                .text("some_question")
//                .title("title")
//                .author(member.getAccount().getId())
//                .build();
//    }
//
//    private Answer createNewAnswer(long id, Member member, Question question) {
//        return Answer.builder()
//                .id(id)
//                .text("answer_text")
//                .creationTime(LocalDateTime.now())
//                .author(member.getAccount().getId())
//                .question(question.getId())
//                .photos(new ArrayList<>())
//                .comments(new ArrayList<>())
//                .build();
//    }
//
//}
