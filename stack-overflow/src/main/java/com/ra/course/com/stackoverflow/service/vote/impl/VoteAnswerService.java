package com.ra.course.com.stackoverflow.service.vote.impl;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.AnswerNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.vote_service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.vote_service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.interfaces.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class VoteAnswerService implements VoteService<Answer> {

    private transient final AnswerRepository answerData;
    private transient final MemberRepository memberData;
    private transient final static int ADDED_REPUTATION = 10;

    @Override
    public Answer upVote(final Answer answer, final Member member)
            throws DataBaseOperationException,
            CannotVoteOwnPostException, AlreadyVotedException,
            AnswerNotFoundException, MemberNotFoundException {

        final var answerFromDB = checkAnswer(answer);
        final var memberFromDB = checkMember(member);

        checkTheAuthorOfAnswer(answerFromDB, memberFromDB);
        checkIsAlreadyVoted(answerFromDB.getId(), memberFromDB.getUpVotedAnswersId());

        final var voteCount = answerFromDB.getVoteCount() + 1;
        answerFromDB.setVoteCount(voteCount);
        answerData.update(answerFromDB);

        memberFromDB.getUpVotedAnswersId().add(answerFromDB.getId());
        updateMemberWithNewReputation(memberFromDB);

        answer.setVoteCount(voteCount);
        return answer;
    }

    @Override
    public Answer downVote(final Answer answer, final Member member)
            throws DataBaseOperationException,
            CannotVoteOwnPostException, AlreadyVotedException,
            AnswerNotFoundException, MemberNotFoundException{

        final var answerFromDB = checkAnswer(answer);
        final var memberFromDB = checkMember(member);

        checkTheAuthorOfAnswer(answerFromDB, memberFromDB);
        checkIsAlreadyVoted(answerFromDB.getId(), memberFromDB.getDownVotedAnswersId());

        final var voteCount = answerFromDB.getVoteCount() - 1;
        answerFromDB.setVoteCount(voteCount);
        answerData.update(answerFromDB);

        memberFromDB.getDownVotedAnswersId().add(answerFromDB.getId());
        updateMemberWithNewReputation(memberFromDB);

        answer.setVoteCount(voteCount);
        return answer;
    }

    private Answer checkAnswer(final Answer answer) throws AnswerNotFoundException {
        final var optionalAnswer = answerData.findById(answer.getId());
        return optionalAnswer.orElseThrow(
                ()-> new AnswerNotFoundException("No such answer in DB"));
    }

    private Member checkMember(final Member member) throws MemberNotFoundException {
        final var optionalMember = memberData.findById(member.getId());
        return optionalMember.orElseThrow(
                ()-> new MemberNotFoundException("No such member in DB"));

    }

    private void checkTheAuthorOfAnswer(final Answer answer, final Member member) throws CannotVoteOwnPostException {
        if (answer.getAuthor().getId() == member.getId()) {
            throw new CannotVoteOwnPostException("Can't vote your own answer");
        }
    }

    private void checkIsAlreadyVoted(final long answerId, final List<Long> votedAnswers) throws AlreadyVotedException {
        if (votedAnswers.contains(answerId)) {
            throw new AlreadyVotedException("This answer is already voted");
        }
    }

    private void updateMemberWithNewReputation (final Member member) throws DataBaseOperationException{
        final int reputation = member.getReputation() + ADDED_REPUTATION;
        member.getAccount().setReputation(reputation);
        memberData.update(member);
    }
}
