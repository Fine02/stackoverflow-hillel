package com.ra.course.com.stackoverflow.service.vote.impl;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.AnswerNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VoteAnswerService implements VoteService<Answer> {

    private transient final AnswerRepository answerData;
    private transient final MemberRepository memberData;
    private transient final static int ADDED_REPUTATION = 10;

    @Override
    public Answer upVote(final Answer answer, final Member member) {
        voteAnswer(answer, member, 1);
        return answer;
    }

    @Override
    public Answer downVote(final Answer answer, final Member member) {
        voteAnswer(answer, member, -1);
        return answer;
    }

    private void voteAnswer(final Answer answer, final Member member, final int countChanges) {
        final var answerFromDB = checkAnswer(answer);
        final var memberFromDB = checkMember(member);
        checkTheAuthorOfAnswer(answerFromDB, memberFromDB);
        checkIsAlreadyVoted(answerFromDB.getId(), countChanges > 0
                ? memberFromDB.getUpVotedAnswersId()
                : memberFromDB.getDownVotedAnswersId());
        final var voteCount = answerFromDB.getVoteCount() + countChanges;
        answerFromDB.setVoteCount(voteCount);
        answerData.update(answerFromDB);
        if (countChanges > 0) {
            memberFromDB.getUpVotedAnswersId().add(answerFromDB.getId());
        } else {
            memberFromDB.getDownVotedAnswersId().add(answerFromDB.getId());
        }
        updateMemberWithNewReputation(memberFromDB);
        answer.setVoteCount(voteCount);
    }

    private Answer checkAnswer(final Answer answer) {
        final var optionalAnswer = answerData.findById(answer.getId());
        return optionalAnswer.orElseThrow(
                () -> new AnswerNotFoundException("No such answer in DB"));
    }

    private Member checkMember(final Member member) {
        final var optionalMember = memberData.findById(member.getId());
        return optionalMember.orElseThrow(
                () -> new MemberNotFoundException("No such member in DB"));

    }

    private void checkTheAuthorOfAnswer(final Answer answer, final Member member) {
        if (answer.getAuthor().getId() == member.getId()) {
            throw new CannotVoteOwnPostException("Can't vote your own answer");
        }
    }

    private void checkIsAlreadyVoted(final long answerId, final List<Long> votedAnswers) {
        if (votedAnswers.contains(answerId)) {
            throw new AlreadyVotedException("This answer is already voted");
        }
    }

    private void updateMemberWithNewReputation(final Member member) {
        final int reputation = member.getReputation() + ADDED_REPUTATION;
        member.getAccount().setReputation(reputation);
        memberData.update(member);
    }
}
