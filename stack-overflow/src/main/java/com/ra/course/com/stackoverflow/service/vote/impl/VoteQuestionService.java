package com.ra.course.com.stackoverflow.service.vote.impl;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.exception.vote_service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.vote_service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class VoteQuestionService implements VoteService<Question> {

    private transient final QuestionRepository questionData;
    private transient final MemberRepository memberData;


    @Override
    public Question upVote( final Question question, final Member member)
            throws DataBaseOperationException,
            CannotVoteOwnPostException, AlreadyVotedException,
            QuestionNotFoundException, MemberNotFoundException {

        final var questionFromDB = checkQuestion(question);
        final var memberFromDB = checkMember(member);

        checkTheAuthorOfQuestion(questionFromDB, memberFromDB);
        checkIsAlreadyVoted(questionFromDB.getId(), memberFromDB.getVotedQuestions());

        final var voteCount = questionFromDB.getVoteCount() + 1;
        questionFromDB.setVoteCount(voteCount);
        questionData.update(questionFromDB);

        memberFromDB.getVotedQuestions().add(questionFromDB.getId());
        final int reputation = memberFromDB.getReputation() + 5;
        memberFromDB.getAccount().setReputation(reputation);
        memberData.update(memberFromDB);

        question.setVoteCount(voteCount);
        return question;
    }

    @Override
    public Question downVote(final Question question, final Member member)
            throws DataBaseOperationException,
            CannotVoteOwnPostException, AlreadyVotedException,
            QuestionNotFoundException, MemberNotFoundException {

        final var questionFromDB = checkQuestion(question);
        final var memberFromDB = checkMember(member);

        checkTheAuthorOfQuestion(questionFromDB, memberFromDB);
        checkIsAlreadyVoted(questionFromDB.getId(), memberFromDB.getDownVotedQuestions());

        final var voteCount = questionFromDB.getVoteCount() - 1;
        questionFromDB.setVoteCount(voteCount);
        questionData.update(questionFromDB);

        memberFromDB.getDownVotedQuestions().add(questionFromDB.getId());
        final int reputation = memberFromDB.getReputation() + 5;
        memberFromDB.getAccount().setReputation(reputation);
        memberData.update(memberFromDB);

        question.setVoteCount(voteCount);
        return question;
    }

    private Question checkQuestion(final Question question) throws QuestionNotFoundException {
        final var optionalQuestion = questionData.findById(question.getId());
        return optionalQuestion.orElseThrow(
                ()-> new QuestionNotFoundException("No such question in DB"));
    }

    private Member checkMember(final Member member) throws MemberNotFoundException {
        final var optionalMember = memberData.findById(member.getId());
        return optionalMember.orElseThrow(
                ()-> new MemberNotFoundException("No such member in DB"));

    }

    private void checkTheAuthorOfQuestion(final Question question, final Member member) throws CannotVoteOwnPostException {
        if (question.getAuthor().getId() == member.getId()) {
            throw new CannotVoteOwnPostException("Can't vote your own question");
        }
    }

    private void checkIsAlreadyVoted(final long questionId, final List<Long> votedQuestions) throws AlreadyVotedException {
        if (votedQuestions.contains(questionId)) {
            throw new AlreadyVotedException("This question is already voted");
        }
    }

}
