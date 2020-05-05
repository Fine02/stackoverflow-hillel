package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MemberStorageServiceImpl implements MemberStorageService {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> findMemberByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    @Override
    public Member saveMemberToDB(Member member){
        return memberRepository.save(member);
    }
}
