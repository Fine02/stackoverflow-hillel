package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
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
public class VoteQuestionServiceIntegrationTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private VoteService<Question> voteQuestionService;
    private Question question;
    private Member member;

    @Test
    @DisplayName("Integration test for VoteQuestionService to upvote then throws AlreadyVotedException")
    public void whenMemberIsAlreadyUpVotedTheQuestionThenThrowsAlreadyVotedException(){
        injectQuestionAndMemberPair(1L, 2L);
        assertThatThrownBy(() -> voteQuestionService.upVote(question, member))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    @DisplayName("Integration test for VoteQuestionService to downvote then throws AlreadyVotedException")
    public void whenMemberIsAlreadyDownVotedTheQuestionThenThrowsAlreadyVotedException(){
        injectQuestionAndMemberPair(2L, 1L);
        assertThatThrownBy(() -> voteQuestionService.downVote(question, member))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    @DisplayName("Integration test for VoteQuestionService to up/downvote then throws CannotVoteOwnPostException")
    public void whenMemberTryToVoteOwnQuestionThenThrowsCannotVoteOwnPostException(){
        injectQuestionAndMemberPair(1L, 1L);
        assertThatThrownBy(() -> voteQuestionService.upVote(question, member))
                .isInstanceOf(CannotVoteOwnPostException.class);
        assertThatThrownBy(() -> voteQuestionService.downVote(question, member))
                .isInstanceOf(CannotVoteOwnPostException.class);
    }

    @Test
    @DisplayName("Integration test for VoteQuestionService to upvote then OK")
    public void whenMemberUpVotesTheQuestionThenVoteCountIncrementAndAddReputation() {
        injectQuestionAndMemberPair(3L, 1L);
        int voteCountBefore = question.getVoteCount();
        int reputationBefore = member.getReputation();

        voteQuestionService.upVote(question, member);
        var member = memberRepository.findById(1L).get();

        assertEquals(voteCountBefore+1, question.getVoteCount());
        assertEquals(reputationBefore+5, member.getReputation());
        assertTrue(member.getUpVotedQuestionsId().contains(question.getId()));
    }

    @Test
    @DisplayName("Integration test for VoteQuestionService to downvote then OK")
    public void whenMemberDownVotesTheQuestionThenVoteCountIncrementAndAddReputation() {
        injectQuestionAndMemberPair(1L, 3L);
        int voteCountBefore = question.getVoteCount();
        int reputationBefore = member.getReputation();

        voteQuestionService.downVote(question, member);
        var memberAfter = memberRepository.findById(3L).get();

        assertEquals(voteCountBefore-1, question.getVoteCount());
        assertEquals(reputationBefore+5, memberAfter.getReputation());
        assertTrue(memberAfter.getDownVotedQuestionsId().contains(question.getId()));

    }

    private void injectQuestionAndMemberPair(long commentId, long memberId) {
        question = questionRepository.findById(commentId).get();
        member = memberRepository.findById(memberId).get();
    }

}
