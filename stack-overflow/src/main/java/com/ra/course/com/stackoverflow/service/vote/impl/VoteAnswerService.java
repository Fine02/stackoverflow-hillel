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
import java.util.Optional;

@AllArgsConstructor
public class VoteAnswerService implements VoteService<Answer> {

    private transient final AnswerRepository answerData;
    private transient final MemberRepository memberData;

    @Override
    public Answer upVote(final Answer answer, final Member member)
            throws DataBaseOperationException,
            CannotVoteOwnPostException, AlreadyVotedException,
            AnswerNotFoundException, MemberNotFoundException {

        final var optionalAnswer = answerData.findById(answer.getId());
        final var answerFromDB = checkAnswerFromDB(optionalAnswer);

        final var optionalMember = memberData.findById(member.getId());
        final var memberFromDB = checkMemberFromDB(optionalMember);

        checkTheAuthorOfAnswer(answerFromDB, memberFromDB);
        checkIsAlreadyVoted(answerFromDB.getId(), memberFromDB.getVotedAnswers());

        final var voteCount = answerFromDB.getVoteCount() + 1;
        answerFromDB.setVoteCount(voteCount);
        answerData.update(answerFromDB);

        memberFromDB.getVotedAnswers().add(answerFromDB.getId());
        final int reputation = memberFromDB.getReputation() + 10;
        memberFromDB.getAccount().setReputation(reputation);
        memberData.update(memberFromDB);

        answer.setVoteCount(voteCount);
        return answer;
    }

    @Override
    public Answer downVote(final Answer answer, final Member member)
            throws DataBaseOperationException,
            CannotVoteOwnPostException, AlreadyVotedException,
            AnswerNotFoundException, MemberNotFoundException{

        final var optionalAnswer = answerData.findById(answer.getId());
        final var answerFromDB = checkAnswerFromDB(optionalAnswer);

        final var optionalMember = memberData.findById(member.getId());
        final var memberFromDB = checkMemberFromDB(optionalMember);

        checkTheAuthorOfAnswer(answerFromDB, memberFromDB);
        checkIsAlreadyVoted(answerFromDB.getId(), memberFromDB.getDownVotedAnswers());

        final var voteCount = answerFromDB.getVoteCount() - 1;
        answerFromDB.setVoteCount(voteCount);
        answerData.update(answerFromDB);

        memberFromDB.getDownVotedAnswers().add(answerFromDB.getId());
        final int reputation = memberFromDB.getReputation() + 10;
        memberFromDB.getAccount().setReputation(reputation);
        memberData.update(memberFromDB);

        answer.setVoteCount(voteCount);
        return answer;
    }

    private Answer checkAnswerFromDB(final Optional<Answer> optionalAnswer) throws AnswerNotFoundException {
        if (optionalAnswer.isEmpty()) {
            throw new AnswerNotFoundException("No such answer in DB");
        } else {
            return optionalAnswer.get();
        }
    }
    private Member checkMemberFromDB(final Optional<Member> optionalMember) throws MemberNotFoundException {
        if(optionalMember.isEmpty()){
            throw new MemberNotFoundException("No such member in DB");
        } else{
            return optionalMember.get();
        }
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
}
