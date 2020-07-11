package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.dto.UpdateDto;
import com.ra.course.com.stackoverflow.dto.mapper.impl.MemberMapper;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccountException;
import com.ra.course.com.stackoverflow.exception.service.LoginMemberException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.WrongPasswordException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MemberStorageServiceImpl implements MemberStorageService {

    private transient final MemberRepository memberRepository;
    private transient final MemberMapper memberMapper;

    private final static String DEFAULT_PASSWORD = "Default_Password1";


    @Override
    public MemberDto findMemberById(final long id) {
        final var member = getMemberFromDB(id);
        return memberMapper.dtoFromEntity(member);
    }

    @Override
    public MemberDto loginMember(final LogInDto dto){

        final var member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new LoginMemberException("No account with email " + dto.getEmail()));

        if (member.getAccount().getStatus()!= AccountStatus.ACTIVE){
            throw new LoginMemberException("OoUPS! Account status is " + member.getAccount().getStatus().toString());
        }

        checkPassword(dto.getPassword(), member);
        return memberMapper.dtoFromEntity(member);
    }

    @Override
    public void deleteMember(final long id, final String password) {
        final var member = getMemberFromDB(id);
        checkPassword(password, member);
        memberRepository.delete(member);
    }

    @Override
    public MemberDto updateMember(final UpdateDto updateDto, final String password) {
        final var member = getMemberFromDB(updateDto.getId());
        checkPassword(password, member);
        member.getAccount().setName(updateDto.getName());
        if(!updateDto.getPassword().equals(DEFAULT_PASSWORD)) {
            member.getAccount().setPassword(updateDto.getPassword());
        }
        memberRepository.update(member);
        return memberMapper.dtoFromEntity(member);
    }

    @Override
    public List<MemberDto> findByMemberName(final String name) {

        final var byMemberName = memberRepository.findByMemberName(name.toLowerCase());

        return memberMapper.dtoFromEntity(byMemberName);
    }

    @Override
    public MemberDto registerMember(final RegisterDto registerDto){

        if(memberRepository.findByEmail(registerDto.getEmail()).isEmpty()) {
            final var newMember = memberMapper.entityFromRegisterDto(registerDto);
            final var savedMember = memberRepository.save(newMember);
            return memberMapper.dtoFromEntity(savedMember);
        } else {
            throw new AlreadyExistAccountException("Account with email " + registerDto.getEmail() + " is already exist");
        }
    }

    private Member getMemberFromDB(final Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("No such member"));
    }

    private void checkPassword(final String password, final Member member){
        if(!member.getAccount().getPassword().equals(password)){
            throw new WrongPasswordException("Wrong password!");
        }
    }
}
