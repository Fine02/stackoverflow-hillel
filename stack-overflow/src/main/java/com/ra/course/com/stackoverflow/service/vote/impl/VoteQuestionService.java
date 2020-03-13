package com.ra.course.com.stackoverflow.service.vote.impl;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.system.BadgeAwardService;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VoteQuestionService implements VoteService<Question> {
    @Autowired
    private transient final QuestionRepository questionData;
    @Autowired
    private transient final MemberRepository memberData;
    @Autowired
    private transient final BadgeAwardService<Question> badgeAwardService;
    private static final int ADDED_REPUTATION = 5;

    @Override
    public Question upVote(final Question question, final Member member) {
        voteQuestion(question, member, 1);
        return question;
    }

    @Override
    public Question downVote(final Question question, final Member member) {
        voteQuestion(question, member, -1);
        return question;
    }

    private void voteQuestion(final Question question, final Member member, final int countChanges) {
        final var questionFromDB = checkQuestion(question);
        final var memberFromDB = checkMember(member);
        checkTheAuthorOfQuestion(questionFromDB, memberFromDB);
        checkIsAlreadyVoted(questionFromDB.getId(), countChanges > 0
                ? memberFromDB.getUpVotedQuestionsId()
                : memberFromDB.getDownVotedQuestionsId());
        final var voteCount = questionFromDB.getVoteCount() + countChanges;
        questionFromDB.setVoteCount(voteCount);
        questionData.update(questionFromDB);
        badgeAwardService.awardMember(questionFromDB);
        if (countChanges > 0) {
            memberFromDB.getUpVotedQuestionsId().add(questionFromDB.getId());
        } else {
            memberFromDB.getDownVotedQuestionsId().add(questionFromDB.getId());
        }
        updateMemberWithNewReputation(memberFromDB);
        question.setVoteCount(voteCount);
    }

    private Question checkQuestion(final Question question) {
        final var optionalQuestion = questionData.findById(question.getId());
        return optionalQuestion.orElseThrow(
                () -> new QuestionNotFoundException("No such question in DB"));
    }

    private Member checkMember(final Member member) {
        final var optionalMember = memberData.findById(member.getId());
        return optionalMember.orElseThrow(
                () -> new MemberNotFoundException("No such member in DB"));

    }

    private void checkTheAuthorOfQuestion(final Question question, final Member member) {
        if (question.getAuthor().getId() == member.getId()) {
            throw new CannotVoteOwnPostException("Can't vote your own question");
        }
    }

    private void checkIsAlreadyVoted(final long questionId, final List<Long> votedQuestions) {
        if (votedQuestions.contains(questionId)) {
            throw new AlreadyVotedException("This question is already voted");
        }
    }

    private void updateMemberWithNewReputation(final Member member) {
        final int reputation = member.getReputation() + ADDED_REPUTATION;
        member.getAccount().setReputation(reputation);
        memberData.update(member);
    }
}
