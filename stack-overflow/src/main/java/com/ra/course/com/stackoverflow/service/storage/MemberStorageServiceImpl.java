package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.mapper.Mapper;
import com.ra.course.com.stackoverflow.dto.mapper.impl.MemberMapper;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccountException;
import com.ra.course.com.stackoverflow.exception.service.LoginException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MemberStorageServiceImpl implements MemberStorageService {

    private transient final MemberRepository memberRepository;
    private transient final MemberMapper mapper;


    @Override
    public MemberDto findMemberById(final long id) {
        final var member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("No such member"));

        return mapper.dtoFromEntity(member);
    }

    @Override
    public MemberDto loginMember(final String email, final String password){

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isEmpty()){
            throw new LoginException("No account with email " + email);
        } else if(!optionalMember.get().getAccount().getPassword().equals(password)){
            throw new LoginException("Wrong password, try once more!");
        } else {
            return mapper.dtoFromEntity(optionalMember.get());
        }
    }

    @Override
    public void deleteMember(final Member member) {
        memberRepository.delete(member);
    }

    @Override
    public void updateMember(final Member member) {
        memberRepository.update(member);
    }

    @Override
    public List<MemberDto> findByMemberName(final String name) {

        final var byMemberName = memberRepository.findByMemberName(name);

        return mapper.dtoFromEntity(byMemberName);
    }

    @Override
    public MemberDto saveMemberToDB(final MemberDto memberDto){
        final var newMember = mapper.entityFromDto(memberDto);
        if(memberRepository.findByEmail(newMember.getAccount().getEmail()).isEmpty()) {
            Member savedMember = memberRepository.save(newMember);
            return mapper.dtoFromEntity(savedMember);
        } else {
            throw new AlreadyExistAccountException("Account with such email is already exist");
        }
    }
}
