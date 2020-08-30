package com.ra.course.com.stackoverflow.service.system.impl;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.exception.service.security.MemberRoleException;
import com.ra.course.com.stackoverflow.exception.service.security.MemberStatusException;
import com.ra.course.com.stackoverflow.exception.service.security.NotAuthorOfThePostException;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SecurityServiceImpl implements SecurityService {

    private final RepositoryUtils utils;

    @Override
    public void checkModer(final SessionMemberDto sessionMemberDto) {
        final var member = utils.getMemberFromDB(sessionMemberDto.getId());
            checkMemberStatus(member);

        final var role = member.getAccount().getRole();
        if(!role.equals(AccountRole.ADMIN) &&
                !role.equals(AccountRole.MODERATOR)){
            throw new MemberRoleException(role);
        }
    }

    @Override
    public Member checkAdminAndReturnMember(final SessionMemberDto sessionMemberDto) {
        final var member = utils.getMemberFromDB(sessionMemberDto.getId());
            checkMemberStatus(member);

        final var role = member.getAccount().getRole();
        if(!role.equals(AccountRole.ADMIN)){
            throw new MemberRoleException(role);
        }
        return member;
    }

    @Override
    public Member checkStatusAndReturnMember(final SessionMemberDto sessionMemberDto) {
        final var member = utils.getMemberFromDB(sessionMemberDto.getId());
            checkMemberStatus(member);
        return member;
    }

    @Override
    public Question checkRightOfMemberAndReturnQuestion(final SessionMemberDto sessionMemberDto, final  Long questionId) {
        final var member = utils.getMemberFromDB(sessionMemberDto.getId());
        final var question = utils.getQuestionFromDB(questionId);
            checkAuthor(question.getAuthor(), member);
        return question;
    }

    @Override
    public Answer checkRightOfMemberAndReturnAnswer(final SessionMemberDto sessionMemberDto, final Long answerId) {
        final var member = utils.getMemberFromDB(sessionMemberDto.getId());
        final var answer = utils.getAnswerFromDB(answerId);
            checkAuthor(answer.getAuthor(), member);
        return answer;
    }

    @Override
    public Comment checkRightOfMemberAndReturnComment(final SessionMemberDto sessionMemberDto, final Long commentId) {
        final var member = utils.getMemberFromDB(sessionMemberDto.getId());
        final var comment = utils.getCommentFromDB(commentId);
            checkAuthor(comment.getAuthor(), member);
        return comment;
    }

    @Override
    public Bounty checkRightOfMemberAndReturnBounty(final SessionMemberDto sessionMemberDto, final Long bountyId) {
        final var member = utils.getMemberFromDB(sessionMemberDto.getId());
        final var bounty = utils.getBountyFromDB(bountyId);
            checkAuthor(bounty.getCreatorId(), member);
        return bounty;
    }

    private void checkMemberStatus(final Member member){
        final var status = member.getAccount().getStatus();
        if(!status.equals(AccountStatus.ACTIVE)){
            throw new MemberStatusException(status);
        }
    }

    private void checkAuthor (final Long authorId, final Member member){
        checkMemberStatus(member);
        if (!authorId.equals(member.getId()) &&
                !member.getAccount().getRole().equals(AccountRole.ADMIN) &&
                !member.getAccount().getRole().equals(AccountRole.MODERATOR)){
            throw new NotAuthorOfThePostException();
        }
    }
}
