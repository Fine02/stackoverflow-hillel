package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.AnswerRepository;
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
public class VoteAnswerServiceIntegrationTest {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private VoteService<Answer> voteAnswerService;
    private Answer answer;
    private Member member;

    @Test
    @DisplayName("Integration test for VoteAnswerService to upvote then throws AlreadyVotedException")
    public void whenMemberIsAlreadyUpVotedTheAnswerThenThrowsAlreadyVotedException(){
        injectAnswerAndMemberPair(1L, 2L);
        assertThatThrownBy(() -> voteAnswerService.upVote(answer, member))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    @DisplayName("Integration test for VoteAnswerService to downvote then throws AlreadyVotedException")
    public void whenMemberIsAlreadyDownVotedTheAnswerThenThrowsAlreadyVotedException(){
        injectAnswerAndMemberPair(2L, 1L);
        assertThatThrownBy(() -> voteAnswerService.downVote(answer, member))
                .isInstanceOf(AlreadyVotedException.class);
    }

    @Test
    @DisplayName("Integration test for VoteAnswerService to up/downvote then throws CannotVoteOwnPostException")
    public void whenMemberTryToVoteOwnAnswerThenThrowsCannotVoteOwnPostException(){
        injectAnswerAndMemberPair(1L, 1L);
        assertThatThrownBy(() -> voteAnswerService.upVote(answer, member))
                .isInstanceOf(CannotVoteOwnPostException.class);
        assertThatThrownBy(() -> voteAnswerService.downVote(answer, member))
                .isInstanceOf(CannotVoteOwnPostException.class);
    }

    @Test
    @DisplayName("Integration test for voteAnswerService to upvote then OK")
    public void whenMemberUpVotesTheAnswerThenVoteCountIncrementAndAddReputation() {
        injectAnswerAndMemberPair(3L, 1L);
        int voteCountBefore = answer.getVoteCount();
        int reputationBefore = member.getReputation();

        voteAnswerService.upVote(answer, member);
        var member = memberRepository.findById(1L).get();

        assertEquals(voteCountBefore+1, answer.getVoteCount());
        assertEquals(reputationBefore+10, member.getReputation());
        assertTrue(member.getUpVotedAnswersId().contains(answer.getId()));
    }

    @Test
    @DisplayName("Integration test for voteAnswerService to downvote then OK")
    public void whenMemberDownVotesTheAnswerThenVoteCountIncrementAndAddReputation() {
        injectAnswerAndMemberPair(1L, 3L);
        int voteCountBefore = answer.getVoteCount();
        int reputationBefore = member.getReputation();

        voteAnswerService.downVote(answer, member);
        var member = memberRepository.findById(3L).get();

        assertEquals(voteCountBefore-1, answer.getVoteCount());
        assertEquals(reputationBefore+10, member.getReputation());
        assertTrue(member.getDownVotedAnswersId().contains(answer.getId()));
    }

    private void injectAnswerAndMemberPair(long answerId, long memberId) {
        answer = answerRepository.findById(answerId).get();
        member = memberRepository.findById(memberId).get();
    }

}
