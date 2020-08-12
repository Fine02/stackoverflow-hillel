package com.ra.course.com.stackoverflow.service.vote.impl;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.AlreadyVotedException;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VoteAnswerServiceImpl implements VoteService<Answer> {

    private final AnswerRepository answerData;
    private final MemberRepository memberData;
    private final NotificationService noteService;

    private final RepositoryUtils utils;
    private final SecurityService securityService;
    private final static int ADDED_REPUTATION = 10;

    @Override
    public void upVote(final Long postId, final SessionMemberDto sessionMemberDto) {

        final var member = securityService.checkStatusAndReturnMember(sessionMemberDto);
        final var answer = utils.getAnswerFromDB(postId);

        checkAuthor(answer, member);
        checkIsAlreadyVoted(answer, member.getUpVotedAnswersId());

        member.getUpVotedAnswersId().add(answer.getId());
        member.getDownVotedAnswersId().remove(answer.getId());

        updateMemberWithNewReputation(member);
        updateAnswerWithNewVoteCount(answer, 1);

        noteService.sendNotification(answer, "voting up");
    }

    @Override
    public void downVote(final Long postId, final SessionMemberDto sessionMemberDto) {

        final var member = securityService.checkStatusAndReturnMember(sessionMemberDto);
        final var answer = utils.getAnswerFromDB(postId);

        checkAuthor(answer, member);
        checkIsAlreadyVoted(answer, member.getDownVotedAnswersId());

        member.getDownVotedAnswersId().add(answer.getId());
        member.getUpVotedAnswersId().remove(answer.getId());

        updateMemberWithNewReputation(member);
        updateAnswerWithNewVoteCount(answer, - 1);

        noteService.sendNotification(answer, "voting down");
    }

    private void checkAuthor(final Answer answer, final Member member ){
        if(answer.getAuthor().equals(member.getId())){
            throw new CannotVoteOwnPostException();
        }
    }

    private void checkIsAlreadyVoted(final Answer answer, final List<Long> votedAnswers) {
        if (votedAnswers.contains(answer.getId())) {
            throw new AlreadyVotedException("Answer");
        }
    }

    private void updateMemberWithNewReputation(final Member member) {
        final int reputation = member.getReputation() + ADDED_REPUTATION;
        member.getAccount().setReputation(reputation);
        memberData.update(member);
    }

    private void updateAnswerWithNewVoteCount(final Answer answer, final Integer countChanges){
        final var voteCount = answer.getVoteCount() + countChanges;
        answer.setVoteCount(voteCount);
        answerData.update(answer);
    }
}
