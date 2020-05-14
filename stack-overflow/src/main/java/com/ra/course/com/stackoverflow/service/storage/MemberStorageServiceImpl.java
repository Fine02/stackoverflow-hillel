package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccount;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MemberStorageServiceImpl implements MemberStorageService {

    private transient final MemberRepository memberRepository;

    @Override
    public Optional<Member> findMemberById(final long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> findMemberByEmail(final String email){
        return memberRepository.findByEmail(email);
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
    public List<Member> findByMemberName(final String name) {
        return memberRepository.findByMemberName(name);
    }

    @Override
    public Member saveMemberToDB(final Member member){
        if(memberRepository.findByEmail(member.getAccount().getEmail()).isEmpty()) {
            return memberRepository.save(member);
        } else {
            throw new AlreadyExistAccount("Account with such email is already exist");
        }
    }
}
