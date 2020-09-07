package com.ra.course.com.stackoverflow.service.member.impl;

import com.ra.course.com.stackoverflow.dto.mapper.MemberMapper;
import com.ra.course.com.stackoverflow.dto.member.*;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccountException;
import com.ra.course.com.stackoverflow.exception.service.LoginMemberException;
import com.ra.course.com.stackoverflow.exception.service.WrongPasswordException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.member.MemberService;
import com.ra.course.com.stackoverflow.service.system.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final SecurityService securityService;
    private final RepositoryUtils utils;

    @Override
    public SessionMemberDto loginMember(final LogInDto dto) {

        final var member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new LoginMemberException("No account with email " + dto.getEmail()));

        if (member.getAccount().getStatus() != AccountStatus.ACTIVE) {
            throw new LoginMemberException("OoUPS! Account status is " + member.getAccount().getStatus().toString());
        }

        checkPassword(dto.getPassword(), member);
        return MemberMapper.MAPPER.toSessionMember(member);
    }

    @Override
    public void deleteMember(final SessionMemberDto sessionMemberDto, final String password) {
        final var member = securityService.checkStatusAndReturnMember(sessionMemberDto);

        checkPassword(password, member);
        memberRepository.delete(member);
    }

    @Override
    public void updateMember(final UpdateDto updateDto, final SessionMemberDto sessionMemberDto,
                             final String password) {
        final var member = securityService.checkStatusAndReturnMember(sessionMemberDto);
        checkPassword(password, member);

        member.getAccount().setName(updateDto.getName());
        member.getAccount().setPassword(updateDto.getPassword());
        memberRepository.update(member);
    }

    @Override
    public SessionMemberDto registerMember(final RegisterDto registerDto) {

        if (memberRepository.findByEmail(registerDto.getEmail()).isEmpty()) {
            final var newMember = MemberMapper.MAPPER.toMember(registerDto);
            final var savedMember = memberRepository.save(newMember);
            return MemberMapper.MAPPER.toSessionMember(savedMember);
        } else {
            throw new AlreadyExistAccountException("Account with email " + registerDto.getEmail() + " is already exist");
        }
    }

    @Override
    public MemberDto getMemberById(final Long id) {
        final var member = utils.getMemberFromDB(id);
        return MemberMapper.MAPPER.toMemberDto(member);
    }

    private void checkPassword(final String password, final Member member) {
        if (!member.getAccount().getPassword().equals(password)) {
            throw new WrongPasswordException("Wrong password!");
        }
    }
}
