package com.ra.course.com.stackoverflow.service.vote.impl;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class VoteWithRemarkService {
    private transient final QuestionRepository questionData;
    private transient final MemberRepository memberData;

    public Question voteToClose(final Question question, final Member member, final QuestionClosingRemark remark) {
        final var questionFromDB = checkQuestion(question);
        final var memberFromDB = checkMember(member);

        checkIsAlreadyVotedToCloseOrDelete(memberFromDB.getId(),
                questionFromDB.getMembersIdsWhoVotedQuestionToClose());

        question.getMembersIdsWhoVotedQuestionToClose().put(memberFromDB.getId(), remark);
        updateQuestionWithNewRemarkToClose(questionFromDB, memberFromDB.getId(), remark);

        return question;
    }

    public Question voteToDelete(final Question question, final Member member, final QuestionClosingRemark remark) {
        final var questionFromDB = checkQuestion(question);
        final var memberFromDB = checkMember(member);

        checkIsAlreadyVotedToCloseOrDelete(memberFromDB.getId(),
                questionFromDB.getMembersIdsWhoVotedQuestionToDelete());

        question.getMembersIdsWhoVotedQuestionToDelete().put(memberFromDB.getId(), remark);
        updateQuestionWithNewRemarkToDelete(questionFromDB, memberFromDB.getId(), remark);

        return question;
    }
    private Question checkQuestion(final Question question) {
        return questionData.findById(question.getId()).orElseThrow(
                ()-> new QuestionNotFoundException("No such question in DB"));
    }

    private Member checkMember(final Member member)  {
        return memberData.findById(member.getId()).orElseThrow(
                ()-> new MemberNotFoundException("No such member in DB"));

    }
    private void checkIsAlreadyVotedToCloseOrDelete(final long memberId, final Map<Long, QuestionClosingRemark> idWithRemarks) {
        if (idWithRemarks.containsKey(memberId)) {
            throw new AlreadyVotedException("This question is already vote to close/delete");
        }
    }
    private void updateQuestionWithNewRemarkToClose(final Question question, final long memberId, final QuestionClosingRemark remark) {
        final Map<Long, QuestionClosingRemark> idWithRemarks = question.getMembersIdsWhoVotedQuestionToClose();
        idWithRemarks.put(memberId, remark);
        question.setMembersIdsWhoVotedQuestionToClose(idWithRemarks);
        questionData.update(question);
    }

    private void updateQuestionWithNewRemarkToDelete(final Question question, final long memberId, final QuestionClosingRemark remark) {
        final Map<Long, QuestionClosingRemark> idWithRemarks = question.getMembersIdsWhoVotedQuestionToDelete();
        idWithRemarks.put(memberId, remark);
        question.setMembersIdsWhoVotedQuestionToDelete(idWithRemarks);
        questionData.update(question);
    }
}
