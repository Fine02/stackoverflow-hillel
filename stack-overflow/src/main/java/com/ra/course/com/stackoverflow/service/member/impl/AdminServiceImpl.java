package com.ra.course.com.stackoverflow.service.member.impl;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.member.AdminService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;
    private final SecurityService securityService;

    @Override
    public void blockMember(final Long memberId, final SessionMemberDto memberDto) {
        final var member = securityService.checkAdminAndReturnMember(memberDto);
        updateStatus(member, AccountStatus.BLOCKED);
    }

    @Override
    public void unblockMember(final Long memberId, final SessionMemberDto memberDto) {
        final var member = securityService.checkAdminAndReturnMember(memberDto);
        updateStatus(member, AccountStatus.ACTIVE);
    }

    @Override
    public void setMemberAsModer(final Long memberId, final SessionMemberDto memberDto) {
        final var member = securityService.checkAdminAndReturnMember(memberDto);
        updateRole(member, AccountRole.MODERATOR);
    }

    @Override
    public void setMemberAsAdmin(final Long memberId, final SessionMemberDto memberDto) {
        final var member = securityService.checkAdminAndReturnMember(memberDto);
        updateRole(member, AccountRole.ADMIN);
    }

    @Override
    public void setMemberAsUser(final Long memberId, final SessionMemberDto memberDto) {
        final var member = securityService.checkAdminAndReturnMember(memberDto);
        updateRole(member, AccountRole.USER);
    }

    private void updateStatus(final Member member, final AccountStatus status){
        member.getAccount().setStatus(status);
        memberRepository.update(member);
    }

    private void updateRole(final Member member, final AccountRole role){
        member.getAccount().setRole(role);
        memberRepository.update(member);
    }
}
