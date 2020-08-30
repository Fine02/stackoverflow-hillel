package com.ra.course.com.stackoverflow.service.vote.impl;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.exception.service.security.QuestionStatusException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.BadgeAwardService;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VoteQuestionServiceImpl implements VoteService<Question> {

    private final QuestionRepository questionData;
    private final MemberRepository memberData;
    private final NotificationService noteService;

    private final RepositoryUtils utils;
    private final SecurityService securityService;
    private final BadgeAwardService<Question> badgeService;

    private final static int ADDED_REPUTATION = 10;

    @Override
    public void upVote(final Long postId, final SessionMemberDto sessionMemberDto) {

        final var member = securityService.checkStatusAndReturnMember(sessionMemberDto);
        final var question = checkQuestion(postId);

        checkAuthor(question, member);
        checkIsAlreadyVoted(question, member.getUpVotedQuestionsId());

        member.getUpVotedQuestionsId().add(question.getId());
        member.getDownVotedQuestionsId().remove(question.getId());

        updateMemberWithNewReputation(member);
        updateQuestionWithNewVoteCount(question, 1);

        badgeService.awardMember(question);
        noteService.sendNotification(question, "voting up");
    }

    @Override
    public void downVote(final Long postId, final SessionMemberDto sessionMemberDto) {

        final var member = securityService.checkStatusAndReturnMember(sessionMemberDto);
        final var question = checkQuestion(postId);

        checkAuthor(question, member);
        checkIsAlreadyVoted(question, member.getDownVotedQuestionsId());

        member.getDownVotedQuestionsId().add(question.getId());
        member.getUpVotedQuestionsId().remove(question.getId());

        updateMemberWithNewReputation(member);
        updateQuestionWithNewVoteCount(question, - 1);

        noteService.sendNotification(question, "voting down");
    }

    private void checkAuthor(final Question question, final Member member ){
        if(question.getAuthor().equals(member.getId())){
            throw new CannotVoteOwnPostException();
        }
    }

    private void checkIsAlreadyVoted(final Question question, final List<Long> votedAnswers) {
        if (votedAnswers.contains(question.getId())) {
            throw new AlreadyVotedException("Question");
        }
    }

    private void updateMemberWithNewReputation(final Member member) {
        final int reputation = member.getReputation() + ADDED_REPUTATION;
        member.getAccount().setReputation(reputation);
        memberData.update(member);
    }

    private void updateQuestionWithNewVoteCount(final Question question, final Integer countChanges){
        final var voteCount = question.getVoteCount() + countChanges;
        question.setVoteCount(voteCount);
        questionData.update(question);
    }

    private Question checkQuestion(final Long questionId){
        final var question = utils.getQuestionFromDB(questionId);
        if(!question.getStatus().equals(QuestionStatus.OPEN)){
            throw new QuestionStatusException(question.getStatus());
        }
        return question;
    }
}
